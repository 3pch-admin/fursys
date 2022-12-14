package platform.doc.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import platform.approval.entity.OpenPersistableLink;
import platform.doc.entity.DocumentColumns;
import platform.doc.entity.WTDocumentWTPartLink;
import platform.part.entity.PartColumns;
import platform.util.CommonUtils;
import platform.util.DateUtils;
import platform.util.IBAUtils;
import platform.util.PageUtils;
import platform.util.StringUtils;
import platform.util.service.UtilHelper;
import wt.clients.folder.FolderTaskLogic;
import wt.doc.WTDocument;
import wt.doc.WTDocumentMaster;
import wt.enterprise.RevisionControlled;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.folder.Folder;
import wt.folder.IteratedFolderMemberLink;
import wt.org.WTUser;
import wt.part.WTPart;
import wt.query.ClassAttribute;
import wt.query.ColumnExpression;
import wt.query.ConstantExpression;
import wt.query.OrderBy;
import wt.query.QuerySpec;
import wt.query.SQLFunction;
import wt.query.SearchCondition;
import wt.services.ServiceFactory;
import wt.session.SessionHelper;
import wt.util.WTAttributeNameIfc;
import wt.vc.ControlBranch;
import wt.vc.VersionControlHelper;
import wt.vc.wip.WorkInProgressHelper;

public class DocumentHelper {

	public static final String ROOT = "/Default/문서";
	public static final DocumentService service = ServiceFactory.getService(DocumentService.class);
	public static final DocumentHelper manager = new DocumentHelper();

	public Map<String, Object> list(Map<String, Object> params) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<DocumentColumns> list = new ArrayList<>();
		QuerySpec query = new QuerySpec();
		String number = (String) params.get("number");
		String name = (String) params.get("name");
		String state = (String) params.get("state");
		String company = (String) params.get("company");
		String brand = (String) params.get("brand");
		String latest = (String) params.get("latest");
		String location = (String) params.get("location");
		String creatorOid = (String) params.get("creatorOid");
		String modifierOid = (String) params.get("modifierOid");
		String startCreatedDate = (String) params.get("startCreatedDate");
		String endCreatedDate = (String) params.get("endCreatedDate");
		String startModifiedDate = (String) params.get("startModifiedDate");
		String endModifiedDate = (String) params.get("endModifiedDate");
		String projectOid = (String) params.get("projectOid");
		String partOid = (String) params.get("partOid");
		int idx = query.appendClassList(WTDocument.class, true);
		int idx_master = query.appendClassList(WTDocumentMaster.class, false);

		SearchCondition sc = null;
		ClassAttribute ca = null;

		sc = WorkInProgressHelper.getSearchCondition_CI(WTDocument.class);
		query.appendWhere(sc, new int[] { idx });
		query.appendAnd();

		sc = new SearchCondition(WTDocument.class, "masterReference.key.id", WTDocumentMaster.class,
				"thePersistInfo.theObjectIdentifier.id");
		query.appendWhere(sc, new int[] { idx, idx_master });

		if (StringUtils.isNotNull(name)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			ca = new ClassAttribute(WTDocument.class, WTDocument.NAME);
			ColumnExpression ce = ConstantExpression.newExpression("%" + name.toUpperCase() + "%");
			SQLFunction function = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca);
			sc = new SearchCondition(function, SearchCondition.LIKE, ce);
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(number)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			ca = new ClassAttribute(WTDocument.class, WTDocument.NUMBER);
			ColumnExpression ce = ConstantExpression.newExpression("%" + number.toUpperCase() + "%");
			SQLFunction function = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca);
			sc = new SearchCondition(function, SearchCondition.LIKE, ce);
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(partOid)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			WTPart part = (WTPart) CommonUtils.persistable(partOid);
			int idx_p = query.appendClassList(WTDocumentWTPartLink.class, false);
			int idx_part = query.appendClassList(WTPart.class, false);

			sc = new SearchCondition(WTDocument.class, "thePersistInfo.theObjectIdentifier.id",
					WTDocumentWTPartLink.class, "roleAObjectRef.key.id");
			query.appendWhere(sc, new int[] { idx, idx_p });
			query.appendAnd();

			sc = new SearchCondition(WTPart.class, "thePersistInfo.theObjectIdentifier.id", WTDocumentWTPartLink.class,
					"roleBObjectRef.key.id");
			query.appendWhere(sc, new int[] { idx_part, idx_p });
			query.appendAnd();

			sc = new SearchCondition(WTDocumentWTPartLink.class, "roleBObjectRef.key.id", "=",
					part.getPersistInfo().getObjectIdentifier().getId());
			query.appendWhere(sc, new int[] { idx_p });
		}

		if (StringUtils.isNotNull(state)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(WTDocument.class, "state.state", "=", state);
			query.appendWhere(sc, new int[] { idx });
		}

		// 작성일자
		if (StringUtils.isNotNull(startCreatedDate)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(WTDocument.class, WTAttributeNameIfc.CREATE_STAMP_NAME, ">=",
					DateUtils.startTimestamp(startCreatedDate));
			query.appendWhere(sc, new int[] { idx });
		}
		if (StringUtils.isNotNull(endCreatedDate)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(WTDocument.class, WTAttributeNameIfc.CREATE_STAMP_NAME, "<=",
					DateUtils.endTimestamp(endCreatedDate));
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(company)) {
			IBAUtils.equals(query, WTDocument.class, idx, "COMPANY_CODE", company);
		}

		if (StringUtils.isNotNull(brand)) {
			IBAUtils.equals(query, WTDocument.class, idx, "BRAND_CODE", brand);
		}

		if (StringUtils.isNotNull(projectOid)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
//			Project project = (Project) rf.getReference(projectOid).getObject();
//			int idx_o = query.appendClassList(Output.class, false);
//			int idx_p = query.appendClassList(Project.class, false);
//			sc = new SearchCondition(Output.class, "projectReference.key.id", Project.class,
//					"thePersistInfo.theObjectIdentifier.id");
//			query.appendWhere(sc, new int[] { idx_o, idx_p });
//			query.appendAnd();
//			sc = new SearchCondition(WTDocument.class, "thePersistInfo.theObjectIdentifier.id", Output.class,
//					"documentReference.key.id");
//			query.appendWhere(sc, new int[] { idx, idx_o });
//			query.appendAnd();
//
//			sc = new SearchCondition(Output.class, "projectReference.key.id", "=",
//					project.getPersistInfo().getObjectIdentifier().getId());
//			query.appendWhere(sc, new int[] { idx_o });

		}

		// 수정일자
		if (StringUtils.isNotNull(startModifiedDate)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(WTDocument.class, WTAttributeNameIfc.MODIFY_STAMP_NAME, ">=",
					DateUtils.startTimestamp(startModifiedDate));
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(endModifiedDate)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(WTDocument.class, WTAttributeNameIfc.MODIFY_STAMP_NAME, "<=",
					DateUtils.endTimestamp(endModifiedDate));
			query.appendWhere(sc, new int[] { idx });
		}

		// 위치..
		Folder f = null;
		if (!StringUtils.isNotNull(location)) {
			f = FolderTaskLogic.getFolder(ROOT, CommonUtils.getContainer("DOCUMENT"));
		} else {
			f = FolderTaskLogic.getFolder(location, CommonUtils.getContainer("DOCUMENT"));
		}

		if (f != null) {
			if (query.getConditionCount() > 0)
				query.appendAnd();
			int f_idx = query.appendClassList(IteratedFolderMemberLink.class, false);
			ClassAttribute fca = new ClassAttribute(IteratedFolderMemberLink.class, "roleBObjectRef.key.branchId");
			SearchCondition fsc = new SearchCondition(fca, "=",
					new ClassAttribute(WTDocument.class, "iterationInfo.branchId"));
			fsc.setFromIndicies(new int[] { f_idx, idx }, 0);
			fsc.setOuterJoin(0);
			query.appendWhere(fsc, new int[] { f_idx, idx });
			query.appendAnd();

			query.appendOpenParen();
			long fid = f.getPersistInfo().getObjectIdentifier().getId();
			query.appendWhere(new SearchCondition(IteratedFolderMemberLink.class, "roleAObjectRef.key.id", "=", fid),
					new int[] { f_idx });

			ArrayList<Folder> folders = UtilHelper.getSubFolders(f, new ArrayList<Folder>());
			for (int i = 0; i < folders.size(); i++) {
				Folder sub = (Folder) folders.get(i);
				query.appendOr();
				long sfid = sub.getPersistInfo().getObjectIdentifier().getId();
				query.appendWhere(
						new SearchCondition(IteratedFolderMemberLink.class, "roleAObjectRef.key.id", "=", sfid),
						new int[] { f_idx });
			}
			query.appendCloseParen();
		}

		if ("true".equals(latest)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = VersionControlHelper.getSearchCondition(WTDocument.class, true);
			query.appendWhere(sc, new int[] { idx });

			int branchIdx = query.appendClassList(ControlBranch.class, false);
			int childBranchIdx = query.appendClassList(ControlBranch.class, false);

			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}

			sc = new SearchCondition(WTDocument.class, RevisionControlled.BRANCH_IDENTIFIER, ControlBranch.class,
					WTAttributeNameIfc.ID_NAME);
			query.appendWhere(sc, new int[] { idx, branchIdx });

			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}

			SearchCondition outerJoin = new SearchCondition(ControlBranch.class, WTAttributeNameIfc.ID_NAME,
					ControlBranch.class, "predecessorReference.key.id");
			outerJoin.setOuterJoin(SearchCondition.RIGHT_OUTER_JOIN);
			query.appendWhere(outerJoin, new int[] { branchIdx, childBranchIdx });

			ca = new ClassAttribute(ControlBranch.class, WTAttributeNameIfc.ID_NAME);
			query.appendSelect(ca, new int[] { childBranchIdx }, false);

			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}

			sc = new SearchCondition(ca, SearchCondition.IS_NULL);
			query.appendWhere(sc, new int[] { childBranchIdx });
		}

		if (StringUtils.isNotNull(creatorOid)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}

			WTUser creator = (WTUser) CommonUtils.persistable(creatorOid);
			sc = new SearchCondition(WTDocument.class, "iterationInfo.creator.key.id", "=",
					creator.getPersistInfo().getObjectIdentifier().getId());
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(modifierOid)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			WTUser modifier = (WTUser) CommonUtils.persistable(modifierOid);
			sc = new SearchCondition(WTDocument.class, "iterationInfo.modifier.key.id", "=",
					modifier.getPersistInfo().getObjectIdentifier().getId());
			query.appendWhere(sc, new int[] { idx });
		}

		ca = new ClassAttribute(WTDocument.class, WTAttributeNameIfc.CREATE_STAMP_NAME);
		OrderBy orderBy = new OrderBy(ca, true);
		query.appendOrderBy(orderBy, new int[] { idx });

//		QueryResult result = PersistenceHelper.manager.find(query);
		PageUtils pager = new PageUtils(params, query);
		QueryResult result = pager.find();
		int total = pager.getTotal();
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			DocumentColumns columns = new DocumentColumns((WTDocument) obj[0]);
			columns.setNo(total--);
			list.add(columns);
		}
		map.put("list", list);
		map.put("topListCount", pager.getTotal());
		map.put("sessionid", pager.getSessionId());
		map.put("curPage", pager.getCpage());
		map.put("pageSize", pager.getPsize());
		map.put("total", pager.getTotalSize());
		return map;
	}

	public String getNextNumber() throws Exception {

		Calendar ca = Calendar.getInstance();
		int day = ca.get(Calendar.DATE);
		int month = ca.get(Calendar.MONTH) + 1;
		int year = ca.get(Calendar.YEAR);
		DecimalFormat df = new DecimalFormat("00");
		String number = df.format(year) + df.format(month) + df.format(day) + "-";

		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(WTDocumentMaster.class, true);

		SearchCondition sc = new SearchCondition(WTDocumentMaster.class, WTDocumentMaster.NUMBER, "LIKE",
				number.toUpperCase() + "%");
		query.appendWhere(sc, new int[] { idx });

		ClassAttribute attr = new ClassAttribute(WTDocumentMaster.class, WTDocumentMaster.NUMBER);
		OrderBy orderBy = new OrderBy(attr, true);
		query.appendOrderBy(orderBy, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find(query);

		if (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			WTDocumentMaster document = (WTDocumentMaster) obj[0];

			String s = document.getNumber().substring(document.getNumber().lastIndexOf("-") + 1);

			int ss = Integer.parseInt(s) + 1;
			DecimalFormat d = new DecimalFormat("000");
			number += d.format(ss);
		} else {
			number += "001";
		}
		return number;
	}

	public ArrayList<WTDocumentWTPartLink> getPartLinks(WTDocument doc) throws Exception {
		ArrayList<WTDocumentWTPartLink> list = new ArrayList<WTDocumentWTPartLink>();

		QueryResult result = PersistenceHelper.manager.navigate(doc, "part", WTDocumentWTPartLink.class, false);
		while (result.hasMoreElements()) {
			WTDocumentWTPartLink link = (WTDocumentWTPartLink) result.nextElement();
			list.add(link);
		}

		return list;
	}

	public ArrayList<PartColumns> getParts(WTDocument doc) throws Exception {
		ArrayList<PartColumns> list = new ArrayList<PartColumns>();

		QueryResult result = PersistenceHelper.manager.navigate(doc, "part", WTDocumentWTPartLink.class, false);
		while (result.hasMoreElements()) {
			WTDocumentWTPartLink link = (WTDocumentWTPartLink) result.nextElement();
			list.add(new PartColumns(link.getPart()));
		}

		return list;
	}

	public ArrayList<DocumentColumns> info(Map<String, Object> params) throws Exception {
		ArrayList<String> list = (ArrayList<String>) params.get("list");
		ArrayList<DocumentColumns> data = new ArrayList<DocumentColumns>();
		try {
			for (String oid : list) {
				// 부품번호 부품명칭 버전 작성자 작성일자
				WTDocument doc = (WTDocument) CommonUtils.persistable(oid);
				DocumentColumns info = new DocumentColumns(doc);
				data.add(info);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return data;
	}

	public Map<String, Object> open(Map<String, Object> params) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<DocumentColumns> list = new ArrayList<>();
		QuerySpec query = new QuerySpec();
		String number = (String) params.get("number");
		String name = (String) params.get("name");
		String state = (String) params.get("state");
		String latest = (String) params.get("latest");
		String location = (String) params.get("location");
		String creatorOid = (String) params.get("creatorOid");
		String modifierOid = (String) params.get("modifierOid");
		String startCreatedDate = (String) params.get("startCreatedDate");
		String endCreatedDate = (String) params.get("endCreatedDate");
		String startModifiedDate = (String) params.get("startModifiedDate");
		String endModifiedDate = (String) params.get("endModifiedDate");
		String projectOid = (String) params.get("projectOid");
		String partOid = (String) params.get("partOid");
		int idx = query.appendClassList(WTDocument.class, true);
		int idx_master = query.appendClassList(WTDocumentMaster.class, false);
		int idx_open = query.appendClassList(OpenPersistableLink.class, false);

		SearchCondition sc = null;
		ClassAttribute ca = null;

		sc = WorkInProgressHelper.getSearchCondition_CI(WTDocument.class);
		query.appendWhere(sc, new int[] { idx });
		query.appendAnd();

		sc = new SearchCondition(WTDocument.class, "masterReference.key.id", WTDocumentMaster.class,
				"thePersistInfo.theObjectIdentifier.id");
		query.appendWhere(sc, new int[] { idx, idx_master });
		query.appendAnd();

		sc = new SearchCondition(WTDocument.class, "thePersistInfo.theObjectIdentifier.id", OpenPersistableLink.class,
				"roleBObjectRef.key.id");
		query.appendWhere(sc, new int[] { idx, idx_open });
		query.appendAnd();

		WTUser user = (WTUser) SessionHelper.manager.getPrincipal();
		sc = new SearchCondition(OpenPersistableLink.class, "roleAObjectRef.key.id", "=",
				user.getPersistInfo().getObjectIdentifier().getId());
		query.appendWhere(sc, new int[] { idx_open });

		if (StringUtils.isNotNull(name)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			ca = new ClassAttribute(WTDocument.class, WTDocument.NAME);
			ColumnExpression ce = ConstantExpression.newExpression("%" + name.toUpperCase() + "%");
			SQLFunction function = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca);
			sc = new SearchCondition(function, SearchCondition.LIKE, ce);
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(number)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			ca = new ClassAttribute(WTDocument.class, WTDocument.NUMBER);
			ColumnExpression ce = ConstantExpression.newExpression("%" + number.toUpperCase() + "%");
			SQLFunction function = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca);
			sc = new SearchCondition(function, SearchCondition.LIKE, ce);
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(partOid)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			WTPart part = (WTPart) CommonUtils.persistable(partOid);
			int idx_p = query.appendClassList(WTDocumentWTPartLink.class, false);
			int idx_part = query.appendClassList(WTPart.class, false);

			sc = new SearchCondition(WTDocument.class, "thePersistInfo.theObjectIdentifier.id",
					WTDocumentWTPartLink.class, "roleAObjectRef.key.id");
			query.appendWhere(sc, new int[] { idx, idx_p });
			query.appendAnd();

			sc = new SearchCondition(WTPart.class, "thePersistInfo.theObjectIdentifier.id", WTDocumentWTPartLink.class,
					"roleBObjectRef.key.id");
			query.appendWhere(sc, new int[] { idx_part, idx_p });
			query.appendAnd();

			sc = new SearchCondition(WTDocumentWTPartLink.class, "roleBObjectRef.key.id", "=",
					part.getPersistInfo().getObjectIdentifier().getId());
			query.appendWhere(sc, new int[] { idx_p });
		}

		if (StringUtils.isNotNull(state)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(WTDocument.class, "state.state", "=", state);
			query.appendWhere(sc, new int[] { idx });
		}

		// 작성일자
		if (StringUtils.isNotNull(startCreatedDate)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(WTDocument.class, WTAttributeNameIfc.CREATE_STAMP_NAME, ">=",
					DateUtils.startTimestamp(startCreatedDate));
			query.appendWhere(sc, new int[] { idx });
		}
		if (StringUtils.isNotNull(endCreatedDate)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(WTDocument.class, WTAttributeNameIfc.CREATE_STAMP_NAME, "<=",
					DateUtils.endTimestamp(endCreatedDate));
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(projectOid)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
//			Project project = (Project) rf.getReference(projectOid).getObject();
//			int idx_o = query.appendClassList(Output.class, false);
//			int idx_p = query.appendClassList(Project.class, false);
//			sc = new SearchCondition(Output.class, "projectReference.key.id", Project.class,
//					"thePersistInfo.theObjectIdentifier.id");
//			query.appendWhere(sc, new int[] { idx_o, idx_p });
//			query.appendAnd();
//			sc = new SearchCondition(WTDocument.class, "thePersistInfo.theObjectIdentifier.id", Output.class,
//					"documentReference.key.id");
//			query.appendWhere(sc, new int[] { idx, idx_o });
//			query.appendAnd();
//
//			sc = new SearchCondition(Output.class, "projectReference.key.id", "=",
//					project.getPersistInfo().getObjectIdentifier().getId());
//			query.appendWhere(sc, new int[] { idx_o });

		}

		// 수정일자
		if (StringUtils.isNotNull(startModifiedDate)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(WTDocument.class, WTAttributeNameIfc.MODIFY_STAMP_NAME, ">=",
					DateUtils.startTimestamp(startModifiedDate));
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(endModifiedDate)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(WTDocument.class, WTAttributeNameIfc.MODIFY_STAMP_NAME, "<=",
					DateUtils.endTimestamp(endModifiedDate));
			query.appendWhere(sc, new int[] { idx });
		}

		// 위치..
		Folder f = null;
		if (!StringUtils.isNotNull(location)) {
			f = FolderTaskLogic.getFolder(ROOT, CommonUtils.getContainer("FURSYS"));
		} else {
			f = FolderTaskLogic.getFolder(location, CommonUtils.getContainer("FURSYS"));
		}

		if (f != null) {
			if (query.getConditionCount() > 0)
				query.appendAnd();
			int f_idx = query.appendClassList(IteratedFolderMemberLink.class, false);
			ClassAttribute fca = new ClassAttribute(IteratedFolderMemberLink.class, "roleBObjectRef.key.branchId");
			SearchCondition fsc = new SearchCondition(fca, "=",
					new ClassAttribute(WTDocument.class, "iterationInfo.branchId"));
			fsc.setFromIndicies(new int[] { f_idx, idx }, 0);
			fsc.setOuterJoin(0);
			query.appendWhere(fsc, new int[] { f_idx, idx });
			query.appendAnd();

			query.appendOpenParen();
			long fid = f.getPersistInfo().getObjectIdentifier().getId();
			query.appendWhere(new SearchCondition(IteratedFolderMemberLink.class, "roleAObjectRef.key.id", "=", fid),
					new int[] { f_idx });

			ArrayList<Folder> folders = UtilHelper.getSubFolders(f, new ArrayList<Folder>());
			for (int i = 0; i < folders.size(); i++) {
				Folder sub = (Folder) folders.get(i);
				query.appendOr();
				long sfid = sub.getPersistInfo().getObjectIdentifier().getId();
				query.appendWhere(
						new SearchCondition(IteratedFolderMemberLink.class, "roleAObjectRef.key.id", "=", sfid),
						new int[] { f_idx });
			}
			query.appendCloseParen();
		}

		if ("true".equals(latest)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = VersionControlHelper.getSearchCondition(WTDocument.class, true);
			query.appendWhere(sc, new int[] { idx });

			int branchIdx = query.appendClassList(ControlBranch.class, false);
			int childBranchIdx = query.appendClassList(ControlBranch.class, false);

			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}

			sc = new SearchCondition(WTDocument.class, RevisionControlled.BRANCH_IDENTIFIER, ControlBranch.class,
					WTAttributeNameIfc.ID_NAME);
			query.appendWhere(sc, new int[] { idx, branchIdx });

			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}

			SearchCondition outerJoin = new SearchCondition(ControlBranch.class, WTAttributeNameIfc.ID_NAME,
					ControlBranch.class, "predecessorReference.key.id");
			outerJoin.setOuterJoin(SearchCondition.RIGHT_OUTER_JOIN);
			query.appendWhere(outerJoin, new int[] { branchIdx, childBranchIdx });

			ca = new ClassAttribute(ControlBranch.class, WTAttributeNameIfc.ID_NAME);
			query.appendSelect(ca, new int[] { childBranchIdx }, false);

			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}

			sc = new SearchCondition(ca, SearchCondition.IS_NULL);
			query.appendWhere(sc, new int[] { childBranchIdx });
		}

		if (StringUtils.isNotNull(creatorOid)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}

			WTUser creator = (WTUser) CommonUtils.persistable(creatorOid);
			sc = new SearchCondition(WTDocument.class, "iterationInfo.creator.key.id", "=",
					creator.getPersistInfo().getObjectIdentifier().getId());
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(modifierOid)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			WTUser modifier = (WTUser) CommonUtils.persistable(modifierOid);
			sc = new SearchCondition(WTDocument.class, "iterationInfo.modifier.key.id", "=",
					modifier.getPersistInfo().getObjectIdentifier().getId());
			query.appendWhere(sc, new int[] { idx });
		}

		ca = new ClassAttribute(WTDocument.class, WTAttributeNameIfc.CREATE_STAMP_NAME);
		OrderBy orderBy = new OrderBy(ca, true);
		query.appendOrderBy(orderBy, new int[] { idx });
		PageUtils pager = new PageUtils(params, query);
		QueryResult result = pager.find();
		int total = pager.getTotal();
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			DocumentColumns columns = new DocumentColumns((WTDocument) obj[0]);
			columns.setNo(total--);
			list.add(columns);
		}
		map.put("list", list);
		map.put("topListCount", pager.getTotal());
		map.put("sessionid", pager.getSessionId());
		map.put("curPage", pager.getCpage());
		map.put("pageSize", pager.getPsize());
		map.put("total", pager.getTotalSize());
		return map;
	}

	public Map<String, Object> my(Map<String, Object> params) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<DocumentColumns> list = new ArrayList<>();
		QuerySpec query = new QuerySpec();
		String number = (String) params.get("number");
		String name = (String) params.get("name");
		String state = (String) params.get("state");
		String latest = (String) params.get("latest");
		String location = (String) params.get("location");
		String startCreatedDate = (String) params.get("startCreatedDate");
		String endCreatedDate = (String) params.get("endCreatedDate");
		String startModifiedDate = (String) params.get("startModifiedDate");
		String endModifiedDate = (String) params.get("endModifiedDate");
		String projectOid = (String) params.get("projectOid");
		String partOid = (String) params.get("partOid");
		int idx = query.appendClassList(WTDocument.class, true);
		int idx_master = query.appendClassList(WTDocumentMaster.class, false);

		SearchCondition sc = null;
		ClassAttribute ca = null;

		sc = WorkInProgressHelper.getSearchCondition_CI(WTDocument.class);
		query.appendWhere(sc, new int[] { idx });
		query.appendAnd();

		sc = new SearchCondition(WTDocument.class, "masterReference.key.id", WTDocumentMaster.class,
				"thePersistInfo.theObjectIdentifier.id");
		query.appendWhere(sc, new int[] { idx, idx_master });

		if (StringUtils.isNotNull(name)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			ca = new ClassAttribute(WTDocument.class, WTDocument.NAME);
			ColumnExpression ce = ConstantExpression.newExpression("%" + name.toUpperCase() + "%");
			SQLFunction function = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca);
			sc = new SearchCondition(function, SearchCondition.LIKE, ce);
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(number)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			ca = new ClassAttribute(WTDocument.class, WTDocument.NUMBER);
			ColumnExpression ce = ConstantExpression.newExpression("%" + number.toUpperCase() + "%");
			SQLFunction function = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca);
			sc = new SearchCondition(function, SearchCondition.LIKE, ce);
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(partOid)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			WTPart part = (WTPart) CommonUtils.persistable(partOid);
			int idx_p = query.appendClassList(WTDocumentWTPartLink.class, false);
			int idx_part = query.appendClassList(WTPart.class, false);

			sc = new SearchCondition(WTDocument.class, "thePersistInfo.theObjectIdentifier.id",
					WTDocumentWTPartLink.class, "roleAObjectRef.key.id");
			query.appendWhere(sc, new int[] { idx, idx_p });
			query.appendAnd();

			sc = new SearchCondition(WTPart.class, "thePersistInfo.theObjectIdentifier.id", WTDocumentWTPartLink.class,
					"roleBObjectRef.key.id");
			query.appendWhere(sc, new int[] { idx_part, idx_p });
			query.appendAnd();

			sc = new SearchCondition(WTDocumentWTPartLink.class, "roleBObjectRef.key.id", "=",
					part.getPersistInfo().getObjectIdentifier().getId());
			query.appendWhere(sc, new int[] { idx_p });
		}

		if (StringUtils.isNotNull(state)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(WTDocument.class, "state.state", "=", state);
			query.appendWhere(sc, new int[] { idx });
		}

		// 작성일자
		if (StringUtils.isNotNull(startCreatedDate)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(WTDocument.class, WTAttributeNameIfc.CREATE_STAMP_NAME, ">=",
					DateUtils.startTimestamp(startCreatedDate));
			query.appendWhere(sc, new int[] { idx });
		}
		if (StringUtils.isNotNull(endCreatedDate)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(WTDocument.class, WTAttributeNameIfc.CREATE_STAMP_NAME, "<=",
					DateUtils.endTimestamp(endCreatedDate));
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(projectOid)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
//			Project project = (Project) rf.getReference(projectOid).getObject();
//			int idx_o = query.appendClassList(Output.class, false);
//			int idx_p = query.appendClassList(Project.class, false);
//			sc = new SearchCondition(Output.class, "projectReference.key.id", Project.class,
//					"thePersistInfo.theObjectIdentifier.id");
//			query.appendWhere(sc, new int[] { idx_o, idx_p });
//			query.appendAnd();
//			sc = new SearchCondition(WTDocument.class, "thePersistInfo.theObjectIdentifier.id", Output.class,
//					"documentReference.key.id");
//			query.appendWhere(sc, new int[] { idx, idx_o });
//			query.appendAnd();
//
//			sc = new SearchCondition(Output.class, "projectReference.key.id", "=",
//					project.getPersistInfo().getObjectIdentifier().getId());
//			query.appendWhere(sc, new int[] { idx_o });

		}

		// 수정일자
		if (StringUtils.isNotNull(startModifiedDate)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(WTDocument.class, WTAttributeNameIfc.MODIFY_STAMP_NAME, ">=",
					DateUtils.startTimestamp(startModifiedDate));
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(endModifiedDate)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(WTDocument.class, WTAttributeNameIfc.MODIFY_STAMP_NAME, "<=",
					DateUtils.endTimestamp(endModifiedDate));
			query.appendWhere(sc, new int[] { idx });
		}

		// 위치..
		Folder f = null;
		if (!StringUtils.isNotNull(location)) {
			f = FolderTaskLogic.getFolder(ROOT, CommonUtils.getContainer("FURSYS"));
		} else {
			f = FolderTaskLogic.getFolder(location, CommonUtils.getContainer("FURSYS"));
		}

		if (f != null) {
			if (query.getConditionCount() > 0)
				query.appendAnd();
			int f_idx = query.appendClassList(IteratedFolderMemberLink.class, false);
			ClassAttribute fca = new ClassAttribute(IteratedFolderMemberLink.class, "roleBObjectRef.key.branchId");
			SearchCondition fsc = new SearchCondition(fca, "=",
					new ClassAttribute(WTDocument.class, "iterationInfo.branchId"));
			fsc.setFromIndicies(new int[] { f_idx, idx }, 0);
			fsc.setOuterJoin(0);
			query.appendWhere(fsc, new int[] { f_idx, idx });
			query.appendAnd();

			query.appendOpenParen();
			long fid = f.getPersistInfo().getObjectIdentifier().getId();
			query.appendWhere(new SearchCondition(IteratedFolderMemberLink.class, "roleAObjectRef.key.id", "=", fid),
					new int[] { f_idx });

			ArrayList<Folder> folders = UtilHelper.getSubFolders(f, new ArrayList<Folder>());
			for (int i = 0; i < folders.size(); i++) {
				Folder sub = (Folder) folders.get(i);
				query.appendOr();
				long sfid = sub.getPersistInfo().getObjectIdentifier().getId();
				query.appendWhere(
						new SearchCondition(IteratedFolderMemberLink.class, "roleAObjectRef.key.id", "=", sfid),
						new int[] { f_idx });
			}
			query.appendCloseParen();
		}

		if ("true".equals(latest)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = VersionControlHelper.getSearchCondition(WTDocument.class, true);
			query.appendWhere(sc, new int[] { idx });

			int branchIdx = query.appendClassList(ControlBranch.class, false);
			int childBranchIdx = query.appendClassList(ControlBranch.class, false);

			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}

			sc = new SearchCondition(WTDocument.class, RevisionControlled.BRANCH_IDENTIFIER, ControlBranch.class,
					WTAttributeNameIfc.ID_NAME);
			query.appendWhere(sc, new int[] { idx, branchIdx });

			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}

			SearchCondition outerJoin = new SearchCondition(ControlBranch.class, WTAttributeNameIfc.ID_NAME,
					ControlBranch.class, "predecessorReference.key.id");
			outerJoin.setOuterJoin(SearchCondition.RIGHT_OUTER_JOIN);
			query.appendWhere(outerJoin, new int[] { branchIdx, childBranchIdx });

			ca = new ClassAttribute(ControlBranch.class, WTAttributeNameIfc.ID_NAME);
			query.appendSelect(ca, new int[] { childBranchIdx }, false);

			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}

			sc = new SearchCondition(ca, SearchCondition.IS_NULL);
			query.appendWhere(sc, new int[] { childBranchIdx });
		}

		if (query.getConditionCount() > 0) {
			query.appendAnd();
		}

		WTUser creator = (WTUser) SessionHelper.manager.getPrincipal();
		sc = new SearchCondition(WTDocument.class, "iterationInfo.creator.key.id", "=",
				creator.getPersistInfo().getObjectIdentifier().getId());
		query.appendWhere(sc, new int[] { idx });

		ca = new ClassAttribute(WTDocument.class, WTAttributeNameIfc.CREATE_STAMP_NAME);
		OrderBy orderBy = new OrderBy(ca, true);
		query.appendOrderBy(orderBy, new int[] { idx });

//		QueryResult result = PersistenceHelper.manager.find(query);
		PageUtils pager = new PageUtils(params, query);
		QueryResult result = pager.find();
		int total = pager.getTotal();
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			DocumentColumns columns = new DocumentColumns((WTDocument) obj[0]);
			columns.setNo(total--);
			list.add(columns);
		}
		map.put("list", list);
		map.put("topListCount", pager.getTotal());
		map.put("sessionid", pager.getSessionId());
		map.put("curPage", pager.getCpage());
		map.put("pageSize", pager.getPsize());
		map.put("total", pager.getTotalSize());
		return map;
	}

}