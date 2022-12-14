package platform.epm.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import platform.epm.entity.EpmColumns;
import platform.part.service.PartHelper;
import platform.util.DateUtils;
import platform.util.IBAUtils;
import platform.util.PageUtils;
import platform.util.StringUtils;
import wt.enterprise.RevisionControlled;
import wt.epm.EPMDocument;
import wt.epm.EPMDocumentMaster;
import wt.epm.build.EPMBuildHistory;
import wt.epm.build.EPMBuildRule;
import wt.epm.structure.EPMReferenceLink;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.part.WTPart;
import wt.part.WTPartHelper;
import wt.part.WTPartStandardConfigSpec;
import wt.query.ClassAttribute;
import wt.query.ColumnExpression;
import wt.query.ConstantExpression;
import wt.query.OrderBy;
import wt.query.QuerySpec;
import wt.query.SQLFunction;
import wt.query.SearchCondition;
import wt.services.ServiceFactory;
import wt.util.WTAttributeNameIfc;
import wt.vc.ControlBranch;
import wt.vc.VersionControlHelper;
import wt.vc.struct.StructHelper;
import wt.vc.views.View;
import wt.vc.views.ViewHelper;
import wt.vc.wip.WorkInProgressHelper;

public class EpmHelper {

	public static final String ROOT = "/Default/도면";

	public static final EpmService service = ServiceFactory.getService(EpmService.class);
	public static final EpmHelper manager = new EpmHelper();

	public EPMDocument getEPM2D(EPMDocument ee) throws Exception {
		if (ee == null) {
			return null;
		}
		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(EPMReferenceLink.class, true);

		EPMDocumentMaster master = (EPMDocumentMaster) ee.getMaster();
		long id = master.getPersistInfo().getObjectIdentifier().getId();

		SearchCondition sc = new SearchCondition(EPMReferenceLink.class, "roleBObjectRef.key.id", "=", id);
		query.appendWhere(sc, new int[] { idx });
		query.appendAnd();

		sc = new SearchCondition(EPMReferenceLink.class, EPMReferenceLink.REFERENCE_TYPE, "=", "DRAWING");
		query.appendWhere(sc, new int[] { idx });

		EPMDocument epm2d = null;
		QueryResult result = PersistenceHelper.manager.find(query);
		if (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			EPMReferenceLink link = (EPMReferenceLink) obj[0];
			epm2d = link.getReferencedBy();
		}
		return epm2d;
	}

	public Map<String, Object> list(Map<String, Object> params) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<EpmColumns> list = new ArrayList<EpmColumns>();
		String name = (String) params.get("name");
		String number = (String) params.get("number");
		String latest = (String) params.get("latest");
		String unit = (String) params.get("unit");
		String state = (String) params.get("state");
		String company = (String) params.get("company");
		String brand = (String) params.get("brand");
		String cat_l = (String) params.get("cat_l");
		String cat_m = (String) params.get("cat_m");
		String partType = (String) params.get("partType");
		String startCreatedDate = (String) params.get("startCreatedDate");
		String endCreatedDate = (String) params.get("endCreatedDate");
		String updateStartDate = (String) params.get("updateStartDate");
		String updateEndDate = (String) params.get("updateEndDate");
		String location = (String) params.get("location");
		String part_width = (String) params.get("part_width");
		String part_depth = (String) params.get("part_depth");
		String part_height = (String) params.get("part_height");
		String standard_code = (String) params.get("standard_code");
		String purchase_yn = (String) params.get("purchase_yn");
		String use_type_code = (String) params.get("use_type_code");
		String dummy_unit_price = (String) params.get("dummy_unit_price");

		String erpCode = (String) params.get("erpCode");
		String material = (String) params.get("material");
		String process = (String) params.get("process");
		String finish = (String) params.get("finish");
		String dtWoodGrain = (String) params.get("dtWoodGrain");
		String packType = (String) params.get("packType");
		String imCamGcode = (String) params.get("imCamGcode");
		String docType = (String) params.get("docType");

		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(EPMDocument.class, true);
		int idx_master = query.appendClassList(EPMDocumentMaster.class, false);

		SearchCondition sc = null;
		ClassAttribute ca = null;

		sc = WorkInProgressHelper.getSearchCondition_CI(EPMDocument.class);
		query.appendWhere(sc, new int[] { idx });
		query.appendAnd();

		sc = new SearchCondition(EPMDocument.class, "masterReference.key.id", EPMDocumentMaster.class,
				"thePersistInfo.theObjectIdentifier.id");
		query.appendWhere(sc, new int[] { idx, idx_master });

		// erpCode
		if (StringUtils.isNotNull(erpCode)) {
			IBAUtils.like(query, EPMDocument.class, idx, "ERP_CODE", erpCode);
		}

		// part_name
		if (StringUtils.isNotNull(name)) {
			IBAUtils.like(query, EPMDocument.class, idx, "PART_NAME", name);
		}

//		if (StringUtils.isNotNull(name)) {
//			if (query.getConditionCount() > 0) {
//				query.appendAnd();
//			}
//			ca = new ClassAttribute(EPMDocument.class, EPMDocument.NAME);
//			ColumnExpression ce = ConstantExpression.newExpression("%" + name.toUpperCase() + "%");
//			SQLFunction function = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca);
//			sc = new SearchCondition(function, SearchCondition.LIKE, ce);
//			query.appendWhere(sc, new int[] { idx });
//		}

		// CAD 파일명 ㅇ
		if (StringUtils.isNotNull(number)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			ca = new ClassAttribute(EPMDocument.class, EPMDocument.CADNAME);
			ColumnExpression ce = ConstantExpression.newExpression("%" + number.toUpperCase() + "%");
			SQLFunction function = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca);
			sc = new SearchCondition(function, SearchCondition.LIKE, ce);
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(unit)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(EPMDocument.class, EPMDocument.DEFAULT_UNIT, "=", unit);
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(state)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(EPMDocument.class, EPMDocument.STATE, "=", state);
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(company)) {
			IBAUtils.equals(query, EPMDocument.class, idx, "COMPANY_CODE", company);
		}

		if (StringUtils.isNotNull(brand)) {
			IBAUtils.equals(query, EPMDocument.class, idx, "BRAND_CODE", brand);
		}

		if (StringUtils.isNotNull(cat_l)) {
			IBAUtils.equals(query, EPMDocument.class, idx, "CAT_L_CODE", cat_l);
		}

		if (StringUtils.isNotNull(cat_m)) {
			IBAUtils.equals(query, EPMDocument.class, idx, "CAT_M_CODE", cat_m);
		}

		// 자재속성
		if (StringUtils.isNotNull(part_width)) {
			IBAUtils.like(query, EPMDocument.class, idx, "PART_WIDTH", part_width);
		}
		if (StringUtils.isNotNull(part_depth)) {
			IBAUtils.equals(query, EPMDocument.class, idx, "PART_DEPTH", part_depth);
		}
		if (StringUtils.isNotNull(part_height)) {
			IBAUtils.equals(query, EPMDocument.class, idx, "PART_HEIGHT", part_height);
		}
		if (StringUtils.isNotNull(standard_code)) {
			IBAUtils.equals(query, EPMDocument.class, idx, "STANDARD_CODE", standard_code);
		}
		if (StringUtils.isNotNull(purchase_yn)) {
			IBAUtils.equals(query, EPMDocument.class, idx, "PURCHASE_YN", purchase_yn);
		}
		if (StringUtils.isNotNull(use_type_code)) {
			IBAUtils.equals(query, EPMDocument.class, idx, "USE_TYPE_CODE", use_type_code);
		}
		if (StringUtils.isNotNull(dummy_unit_price)) {
			IBAUtils.equals(query, EPMDocument.class, idx, "DUMMY_UNIT_PRICE", dummy_unit_price);
		}

		// 설계속성(공통)
		if (StringUtils.isNotNull(material)) {
			IBAUtils.equals(query, EPMDocument.class, idx, "MATERIAL", material);
		}
		if (StringUtils.isNotNull(process)) {
			IBAUtils.equals(query, EPMDocument.class, idx, "PROCESS", process);
		}
		if (StringUtils.isNotNull(finish)) {
			IBAUtils.equals(query, EPMDocument.class, idx, "FINISH", finish);
		}
		if (StringUtils.isNotNull(dtWoodGrain)) {
			IBAUtils.equals(query, EPMDocument.class, idx, "DT_WOODGRAIN", dtWoodGrain);
		}
		if (StringUtils.isNotNull(packType)) {
			IBAUtils.equals(query, EPMDocument.class, idx, "PACK_TYPE", packType);
		}
		if (StringUtils.isNotNull(imCamGcode)) {
			IBAUtils.equals(query, EPMDocument.class, idx, "IM_CAM_GCODE", imCamGcode);
		}

		// 위치..
//		Folder f = null;
//		if (!StringUtils.isNotNull(location)) {
//			f = FolderTaskLogic.getFolder(ROOT, CommonUtils.getContainer("FURSYS"));
//		} else {
//			f = FolderTaskLogic.getFolder(location, CommonUtils.getContainer("FURSYS"));
//		}

//		if (f != null) {
//			if (query.getConditionCount() > 0)
//				query.appendAnd();
//			int f_idx = query.appendClassList(IteratedFolderMemberLink.class, false);
//			ClassAttribute fca = new ClassAttribute(IteratedFolderMemberLink.class, "roleBObjectRef.key.branchId");
//			SearchCondition fsc = new SearchCondition(fca, "=",
//					new ClassAttribute(EPMDocument.class, "iterationInfo.branchId"));
//			fsc.setFromIndicies(new int[] { f_idx, idx }, 0);
//			fsc.setOuterJoin(0);
//			query.appendWhere(fsc, new int[] { f_idx, idx });
//			query.appendAnd();
//
//			query.appendOpenParen();
//			long fid = f.getPersistInfo().getObjectIdentifier().getId();
//			query.appendWhere(new SearchCondition(IteratedFolderMemberLink.class, "roleAObjectRef.key.id", "=", fid),
//					new int[] { f_idx });
//
//			ArrayList<Folder> folders = UtilHelper.getSubFolders(f, new ArrayList<Folder>());
//			for (int i = 0; i < folders.size(); i++) {
//				Folder sub = (Folder) folders.get(i);
//				query.appendOr();
//				long sfid = sub.getPersistInfo().getObjectIdentifier().getId();
//				query.appendWhere(
//						new SearchCondition(IteratedFolderMemberLink.class, "roleAObjectRef.key.id", "=", sfid),
//						new int[] { f_idx });
//			}
//			query.appendCloseParen();
//		}

		// 작성일자
		if (StringUtils.isNotNull(startCreatedDate)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(EPMDocument.class, WTAttributeNameIfc.CREATE_STAMP_NAME, ">=",
					DateUtils.startTimestamp(startCreatedDate));
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(endCreatedDate)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(EPMDocument.class, WTAttributeNameIfc.CREATE_STAMP_NAME, "<=",
					DateUtils.endTimestamp(endCreatedDate));
			query.appendWhere(sc, new int[] { idx });
		}

		// 수정일자
		if (StringUtils.isNotNull(updateStartDate)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(EPMDocument.class, WTAttributeNameIfc.MODIFY_STAMP_NAME, ">=",
					DateUtils.startTimestamp(updateStartDate));
			query.appendWhere(sc, new int[] { idx });
		}
		if (StringUtils.isNotNull(updateEndDate)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(EPMDocument.class, WTAttributeNameIfc.MODIFY_STAMP_NAME, "<=",
					DateUtils.endTimestamp(updateEndDate));
			query.appendWhere(sc, new int[] { idx });
		}

		if ("true".equals(latest)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = VersionControlHelper.getSearchCondition(EPMDocument.class, true);
			query.appendWhere(sc, new int[] { idx });

			int branchIdx = query.appendClassList(ControlBranch.class, false);
			int childBranchIdx = query.appendClassList(ControlBranch.class, false);

			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}

			sc = new SearchCondition(EPMDocument.class, RevisionControlled.BRANCH_IDENTIFIER, ControlBranch.class,
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

//		if (query.getConditionCount() > 0) {
//			query.appendAnd();
//		}
//
//		sc = new SearchCondition(EPMDocument.class, EPMDocument.DOC_TYPE, "<>", "CADDRAWING");
//		query.appendWhere(sc, new int[] { idx });

		if (StringUtils.isNotNull(docType)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(EPMDocument.class, EPMDocument.DOC_TYPE, "=", docType);
			query.appendWhere(sc, new int[] { idx });
		}

		ca = new ClassAttribute(EPMDocument.class, WTAttributeNameIfc.CREATE_STAMP_NAME);
		OrderBy orderBy = new OrderBy(ca, true);
		query.appendOrderBy(orderBy, new int[] { idx });

//		QueryResult result = PersistenceHelper.manager.find(query);

		PageUtils pager = new PageUtils(params, query);
		QueryResult result = pager.find();
		int total = pager.getTotal();
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			EpmColumns columns = new EpmColumns((EPMDocument) obj[0]);
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

	public EPMDocument getEPMDocument(String number) throws Exception {
		EPMDocument epm = null;

		try {
			QuerySpec qs = new QuerySpec();
			int i = qs.appendClassList(EPMDocument.class, true);

			// 최신 이터레이션
			qs.appendWhere(VersionControlHelper.getSearchCondition(EPMDocument.class, true), new int[] { i });
			// 최신 버젼
			int branchIdx = qs.appendClassList(ControlBranch.class, false);
			int childBranchIdx = qs.appendClassList(ControlBranch.class, false);

			if (qs.getConditionCount() > 0) {
				qs.appendAnd();
			}

			SearchCondition sc = new SearchCondition(EPMDocument.class, RevisionControlled.BRANCH_IDENTIFIER,
					ControlBranch.class, WTAttributeNameIfc.ID_NAME);
			qs.appendWhere(sc, new int[] { i, branchIdx });

			if (qs.getConditionCount() > 0) {
				qs.appendAnd();
			}

			SearchCondition outerJoin = new SearchCondition(ControlBranch.class, WTAttributeNameIfc.ID_NAME,
					ControlBranch.class, "predecessorReference.key.id");
			outerJoin.setOuterJoin(SearchCondition.RIGHT_OUTER_JOIN);
			qs.appendWhere(outerJoin, new int[] { branchIdx, childBranchIdx });

			ClassAttribute ca = new ClassAttribute(ControlBranch.class, WTAttributeNameIfc.ID_NAME);
			qs.appendSelect(ca, new int[] { childBranchIdx }, false);

			if (qs.getConditionCount() > 0) {
				qs.appendAnd();
			}

			sc = new SearchCondition(ca, SearchCondition.IS_NULL);
			qs.appendWhere(sc, new int[] { childBranchIdx });

			qs.appendAnd();
			qs.appendWhere(new SearchCondition(EPMDocument.class, EPMDocument.NUMBER, SearchCondition.EQUAL, number),
					new int[] { i });
			QueryResult qr = PersistenceHelper.manager.find(qs);
			while (qr.hasMoreElements()) {

				Object obj[] = (Object[]) qr.nextElement();
				epm = (EPMDocument) obj[0];

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return epm;
	}

	public void getter(EPMDocument epm, ArrayList<EPMDocument> list) throws Exception {
		QueryResult result = StructHelper.service.navigateUses(epm);
		while (result.hasMoreElements()) {
			EPMDocumentMaster m = (EPMDocumentMaster) result.nextElement();
			EPMDocument e = getEPMDocument(m.getNumber());
			if (!list.contains(e)) {
				list.add(e);
			}
			getter(e, list);
		}
	}

	public WTPart getPart(EPMDocument epm) throws Exception {
		WTPart part = null;
		if (epm == null) {
			return part;
		}

		QueryResult result = null;
		if (VersionControlHelper.isLatestIteration(epm)) {
			result = PersistenceHelper.manager.navigate(epm, "buildTarget", EPMBuildRule.class);
		} else {
			result = PersistenceHelper.manager.navigate(epm, "built", EPMBuildHistory.class);
		}

		while (result.hasMoreElements()) {
			part = (WTPart) result.nextElement();
		}
		return part;
	}

	public ArrayList<EpmColumns> detail(WTPart part, ArrayList<EpmColumns> list) throws Exception {

		EPMDocument epm = PartHelper.manager.getEPMDocument(part);
		if (epm != null) {
			list.add(new EpmColumns(epm));
			EPMDocument epm2d = getEPM2D(epm);
			if (epm2d != null) {
				list.add(new EpmColumns(epm2d));
			}
		}

		String viewName = part.getViewName();
		if (!StringUtils.isNotNull(viewName)) {
			viewName = "Design";
		}
		View view = ViewHelper.service.getView(viewName);
		WTPartStandardConfigSpec configSpec = WTPartStandardConfigSpec.newWTPartStandardConfigSpec(view, null);
		QueryResult result = WTPartHelper.service.getUsesWTParts(part, configSpec);
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			if (!(obj[1] instanceof WTPart)) {
				continue;
			}
			WTPart childPart = (WTPart) obj[1];

			EPMDocument e = PartHelper.manager.getEPMDocument(childPart);
			if (e != null) {
				list.add(new EpmColumns(e));
				EPMDocument _epm2d = getEPM2D(e);
				if (_epm2d != null) {
					list.add(new EpmColumns(_epm2d));
				}
			}
			detail(childPart, list);
		}
		return list;
	}
}
