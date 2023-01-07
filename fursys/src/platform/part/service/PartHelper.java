package platform.part.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import platform.doc.entity.DocumentColumns;
import platform.doc.entity.WTDocumentWTPartLink;
import platform.part.entity.PartColumns;
import platform.util.CommonUtils;
import platform.util.DateUtils;
import platform.util.IBAUtils;
import platform.util.PageUtils;
import platform.util.StringUtils;
import platform.util.ThumbnailUtils;
import wt.doc.WTDocument;
import wt.enterprise.MadeFromLink;
import wt.enterprise.RevisionControlled;
import wt.epm.EPMDocument;
import wt.epm.build.EPMBuildHistory;
import wt.epm.build.EPMBuildRule;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.lifecycle.State;
import wt.part.WTPart;
import wt.part.WTPartHelper;
import wt.part.WTPartMaster;
import wt.part.WTPartStandardConfigSpec;
import wt.part.WTPartUsageLink;
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
import wt.vc.views.View;
import wt.vc.views.ViewHelper;
import wt.vc.wip.WorkInProgressHelper;

public class PartHelper {

	public static final String LIBRARY_CONTAINER = "Library-Fursys";

	public static final String ROOT = "/Default/";

	public static final String MAT = "MAT"; // 자재
	public static final String ITEM = "ITEM"; // 단품
	public static final String SET = "SET"; // 세트
	public static final String ALL = "ALL"; // 전체

	public static final PartService service = ServiceFactory.getService(PartService.class);
	public static final PartHelper manager = new PartHelper();

	public boolean isDerived(WTPart original) throws Exception {
		QueryResult result = PersistenceHelper.manager.navigate(original, "copy", MadeFromLink.class);
		return result.hasMoreElements();
	}

	public EPMDocument getEPMDocument(WTPart part) throws Exception {
		EPMDocument epm = null;
		try {
			if (part == null) {
				return epm;
			}

			QueryResult result = null;

			if (VersionControlHelper.isLatestIteration(part)) {
				result = PersistenceHelper.manager.navigate(part, "buildSource", EPMBuildRule.class);
			} else {
				result = PersistenceHelper.manager.navigate(part, "builtBy", EPMBuildHistory.class);
			}

			while (result.hasMoreElements()) {
				epm = (EPMDocument) result.nextElement();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return epm;
	}

	public String partTypeToDisplay(WTPart part) throws Exception {
		String display = "";
		String value = IBAUtils.getStringValue(part, "PART_TYPE");
		if ("MAT".equalsIgnoreCase(value)) {
			display = "자재";
		} else if ("ITEM".equalsIgnoreCase(value)) {
			display = "단품";
		} else if ("SET".equalsIgnoreCase(value)) {
			display = "세트";
		}
		return display;
	}

	public Map<String, Object> list(Map<String, Object> params) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<PartColumns> list = new ArrayList<PartColumns>();
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
		String createStartDate = (String) params.get("createStartDate");
		String createEndDate = (String) params.get("createEndDate");
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

		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(WTPart.class, true);
		int idx_master = query.appendClassList(WTPartMaster.class, false);

		SearchCondition sc = null;
		ClassAttribute ca = null;

		sc = WorkInProgressHelper.getSearchCondition_CI(WTPart.class);
		query.appendWhere(sc, new int[] { idx });
		query.appendAnd();

		sc = new SearchCondition(WTPart.class, "masterReference.key.id", WTPartMaster.class,
				"thePersistInfo.theObjectIdentifier.id");
		query.appendWhere(sc, new int[] { idx, idx_master });

		if (StringUtils.isNotNull(erpCode)) {
			IBAUtils.equals(query, WTPart.class, idx, "ERP_CODE", brand);
		}

		if (StringUtils.isNotNull(name)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			ca = new ClassAttribute(WTPart.class, WTPart.NAME);
			ColumnExpression ce = ConstantExpression.newExpression("%" + name.toUpperCase() + "%");
			SQLFunction function = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca);
			sc = new SearchCondition(function, SearchCondition.LIKE, ce);
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(number)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			ca = new ClassAttribute(WTPart.class, WTPart.NUMBER);
			ColumnExpression ce = ConstantExpression.newExpression("%" + number.toUpperCase() + "%");
			SQLFunction function = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca);
			sc = new SearchCondition(function, SearchCondition.LIKE, ce);
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(unit)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(WTPart.class, WTPart.DEFAULT_UNIT, "=", unit);
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(state)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(WTPart.class, "state.state", "=", state);
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(company)) {
			IBAUtils.equals(query, WTPart.class, idx, "COMPANY_CODE", company);
		}

		if (StringUtils.isNotNull(brand)) {
			IBAUtils.equals(query, WTPart.class, idx, "BRAND_CODE", brand);
		}

		if (StringUtils.isNotNull(cat_l)) {
			IBAUtils.equals(query, WTPart.class, idx, "CAT_L_CODE", cat_l);
		}

		if (StringUtils.isNotNull(cat_m)) {
			IBAUtils.equals(query, WTPart.class, idx, "CAT_M_CODE", cat_m);
		}

		if (StringUtils.isNotNull(partType) && !partType.contains(ALL)) {
			IBAUtils.equals(query, WTPart.class, idx, "PART_TYPE", partType);
		}

		// 자재속성
		if (StringUtils.isNotNull(part_width)) {
			IBAUtils.like(query, WTPart.class, idx, "PART_WIDTH", part_width);
		}
		if (StringUtils.isNotNull(part_depth)) {
			IBAUtils.equals(query, WTPart.class, idx, "PART_DEPTH", part_depth);
		}
		if (StringUtils.isNotNull(part_height)) {
			IBAUtils.equals(query, WTPart.class, idx, "PART_HEIGHT", part_height);
		}
		if (StringUtils.isNotNull(standard_code)) {
			IBAUtils.equals(query, WTPart.class, idx, "STANDARD_CODE", standard_code);
		}
		if (StringUtils.isNotNull(purchase_yn)) {
			IBAUtils.equals(query, WTPart.class, idx, "PURCHASE_YN", purchase_yn);
		}
		if (StringUtils.isNotNull(use_type_code)) {
			IBAUtils.equals(query, WTPart.class, idx, "USE_TYPE_CODE", use_type_code);
		}
		if (StringUtils.isNotNull(dummy_unit_price)) {
			IBAUtils.equals(query, WTPart.class, idx, "DUMMY_UNIT_PRICE", dummy_unit_price);
		}

		// 설계속성(공통)
		if (StringUtils.isNotNull(material)) {
			IBAUtils.equals(query, WTPart.class, idx, "MATERIAL", material);
		}
		if (StringUtils.isNotNull(process)) {
			IBAUtils.equals(query, WTPart.class, idx, "PROCESS", process);
		}
		if (StringUtils.isNotNull(finish)) {
			IBAUtils.equals(query, WTPart.class, idx, "FINISH", finish);
		}
		if (StringUtils.isNotNull(dtWoodGrain)) {
			IBAUtils.equals(query, WTPart.class, idx, "DT_WOODGRAIN", dtWoodGrain);
		}
		if (StringUtils.isNotNull(packType)) {
			IBAUtils.equals(query, WTPart.class, idx, "PACK_TYPE", packType);
		}
		if (StringUtils.isNotNull(imCamGcode)) {
			IBAUtils.equals(query, WTPart.class, idx, "IM_CAM_GCODE", imCamGcode);
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
//					new ClassAttribute(WTPart.class, "iterationInfo.branchId"));
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
		if (StringUtils.isNotNull(createStartDate)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(WTPart.class, WTAttributeNameIfc.CREATE_STAMP_NAME, ">=",
					DateUtils.startTimestamp(createStartDate));
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(createEndDate)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(WTPart.class, WTAttributeNameIfc.CREATE_STAMP_NAME, "<=",
					DateUtils.endTimestamp(createEndDate));
			query.appendWhere(sc, new int[] { idx });
		}

		// 수정일자
		if (StringUtils.isNotNull(updateStartDate)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(WTPart.class, WTAttributeNameIfc.MODIFY_STAMP_NAME, ">=",
					DateUtils.startTimestamp(updateStartDate));
			query.appendWhere(sc, new int[] { idx });
		}
		if (StringUtils.isNotNull(updateEndDate)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(WTPart.class, WTAttributeNameIfc.MODIFY_STAMP_NAME, "<=",
					DateUtils.endTimestamp(updateEndDate));
			query.appendWhere(sc, new int[] { idx });
		}

		if ("true".equals(latest)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = VersionControlHelper.getSearchCondition(WTPart.class, true);
			query.appendWhere(sc, new int[] { idx });

			int branchIdx = query.appendClassList(ControlBranch.class, false);
			int childBranchIdx = query.appendClassList(ControlBranch.class, false);

			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}

			sc = new SearchCondition(WTPart.class, RevisionControlled.BRANCH_IDENTIFIER, ControlBranch.class,
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

		ca = new ClassAttribute(WTPart.class, WTAttributeNameIfc.CREATE_STAMP_NAME);
		OrderBy orderBy = new OrderBy(ca, true);
		query.appendOrderBy(orderBy, new int[] { idx });

//		QueryResult result = PersistenceHelper.manager.find(query);

		PageUtils pager = new PageUtils(params, query);
		QueryResult result = pager.find();
		int total = pager.getTotal();
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			PartColumns columns = new PartColumns((WTPart) obj[0]);
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

	public String getNextNumber(String number) throws Exception {

		Calendar ca = Calendar.getInstance();
		int month = ca.get(Calendar.MONTH) + 1;
		int year = ca.get(Calendar.YEAR);
		DecimalFormat df = new DecimalFormat("00");
		number = number + df.format(year).substring(2) + df.format(month) + "-";

		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(WTPartMaster.class, true);

		SearchCondition sc = new SearchCondition(WTPartMaster.class, WTPartMaster.NUMBER, "LIKE",
				number.toUpperCase() + "%");
		query.appendWhere(sc, new int[] { idx });

		ClassAttribute attr = new ClassAttribute(WTPartMaster.class, WTPartMaster.NUMBER);
		OrderBy orderBy = new OrderBy(attr, true);
		query.appendOrderBy(orderBy, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find(query);
		if (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			WTPartMaster document = (WTPartMaster) obj[0];

			String s = document.getNumber().substring(document.getNumber().lastIndexOf("-") + 1);

			int ss = Integer.parseInt(s) + 1;
			DecimalFormat d = new DecimalFormat("0000");
			number += d.format(ss);
		} else {
			number += "0001";
		}
		return number;
	}

	public WTPart refPart(WTPart part) throws Exception {
		QueryResult result = PersistenceHelper.manager.navigate(part, "copy", MadeFromLink.class);
		WTPart ref = null;
		if (result.hasMoreElements()) {
			ref = (WTPart) result.nextElement();
		}
		return ref;
	}

	public ArrayList<PartColumns> info(Map<String, Object> params) throws Exception {
		String oid = (String) params.get("oid");
		ArrayList<PartColumns> data = new ArrayList<PartColumns>();
		try {
			// 부품번호 부품명칭 버전 작성자 작성일자
			WTPart part = (WTPart) CommonUtils.persistable(oid);
			PartColumns info = new PartColumns(part);
			data.add(info);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return data;
	}

	public WTPart getPart(String partNumber, String revision) throws Exception {
		WTPart part = null;

		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(WTPart.class, true);

		SearchCondition sc = null;

		sc = new SearchCondition(WTPart.class, WTPart.NUMBER, "=", partNumber);
		query.appendWhere(sc, new int[] { idx });
		query.appendAnd();

		sc = new SearchCondition(WTPart.class, "versionInfo.identifier.versionId", "=", revision);
		query.appendWhere(sc, new int[] { idx });
		query.appendAnd();

		sc = VersionControlHelper.getSearchCondition(WTPart.class, true);
		query.appendWhere(sc, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find(query);
		if (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			part = (WTPart) obj[0];
		}

		return part;
	}

	public JSONArray left(String oid) throws Exception {
		ReferenceFactory rf = new ReferenceFactory();
		WTPart part = (WTPart) rf.getReference(oid).getObject();
		JSONArray jsonArray = new JSONArray();
		JSONObject rootNode = new JSONObject();
		rootNode.put("thumb", ThumbnailUtils.thumbnails(part)[1]);
		rootNode.put("partName", IBAUtils.getStringValue(part, "PART_NAME"));
		rootNode.put("number", part.getNumber());
		rootNode.put("itemName", IBAUtils.getStringValue(part, "ITEM_NAME"));
		rootNode.put("partNo", IBAUtils.getStringValue(part, "PART_NO"));
		rootNode.put("version", part.getVersionIdentifier().getSeries().getValue());
		rootNode.put("partType", partTypeToDisplay(part));
		rootNode.put("amount", 1);
		rootNode.put("partTypeCd", IBAUtils.getStringValue(part, "PART_TYPE"));
		rootNode.put("state", part.getLifeCycleState().getDisplay());
		rootNode.put("oid", part.getPersistInfo().getObjectIdentifier().getStringValue());
		rootNode.put("uid", UUID.randomUUID().toString());
		rootNode.put("library", isLibrary(part));
		JSONArray array = new JSONArray();
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
			WTPartUsageLink link = (WTPartUsageLink) obj[0];
			WTPart childPart = (WTPart) obj[1];
			boolean isBom = IBAUtils.getBooleanValue(childPart, "BOM");
			if (!isBom) {
				// bom 처리
				continue;
			}
			JSONObject node = new JSONObject();
			node.put("thumb", ThumbnailUtils.thumbnails(childPart)[1]);
			node.put("partName", IBAUtils.getStringValue(childPart, "PART_NAME"));
			node.put("number", childPart.getNumber());
			node.put("itemName", IBAUtils.getStringValue(childPart, "ITEM_NAME"));
			node.put("partNo", IBAUtils.getStringValue(childPart, "PART_NO"));
			node.put("version", childPart.getVersionIdentifier().getSeries().getValue());
			node.put("partType", partTypeToDisplay(childPart));
			node.put("partTypeCd", IBAUtils.getStringValue(childPart, "PART_TYPE"));
			node.put("amount", link != null ? link.getQuantity().getAmount() : 1);
			node.put("state", childPart.getLifeCycleState().getDisplay());
			node.put("oid", childPart.getPersistInfo().getObjectIdentifier().getStringValue());
			node.put("uid", UUID.randomUUID().toString());
			node.put("library", isLibrary(childPart));
			node.put("link", link.getPersistInfo().getObjectIdentifier().getStringValue());
			left(childPart, node);
			array.add(node);
		}
		rootNode.put("children", array);
		jsonArray.add(rootNode);
		return jsonArray;
	}

	private void left(WTPart part, JSONObject rootNode) throws Exception {
		String viewName = part.getViewName();
		if (!StringUtils.isNotNull(viewName)) {
			viewName = "Design";
		}
		View view = ViewHelper.service.getView(viewName);
		WTPartStandardConfigSpec configSpec = WTPartStandardConfigSpec.newWTPartStandardConfigSpec(view, null);
		QueryResult result = WTPartHelper.service.getUsesWTParts(part, configSpec);
		JSONArray jsonChildren = new JSONArray();
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			if (!(obj[1] instanceof WTPart)) {
				continue;
			}
			WTPartUsageLink link = (WTPartUsageLink) obj[0];
			WTPart childPart = (WTPart) obj[1];
			boolean isBom = IBAUtils.getBooleanValue(childPart, "BOM");
			if (!isBom) {
				// bom 처리
				continue;
			}
			JSONObject node = new JSONObject();
			node.put("thumb", ThumbnailUtils.thumbnails(childPart)[1]);
			node.put("partName", IBAUtils.getStringValue(childPart, "PART_NAME"));
			node.put("number", childPart.getNumber());
			node.put("itemName", IBAUtils.getStringValue(childPart, "ITEM_NAME"));
			node.put("partNo", IBAUtils.getStringValue(childPart, "PART_NO"));
			node.put("partTypeCd", IBAUtils.getStringValue(childPart, "PART_TYPE"));
			node.put("version", childPart.getVersionIdentifier().getSeries().getValue());
			node.put("partType", partTypeToDisplay(childPart));
			node.put("amount", link != null ? link.getQuantity().getAmount() : 1);
			node.put("oid", childPart.getPersistInfo().getObjectIdentifier().getStringValue());
			node.put("state", childPart.getLifeCycleState().getDisplay());
			node.put("uid", UUID.randomUUID().toString());
			node.put("library", isLibrary(childPart));
			node.put("link", link.getPersistInfo().getObjectIdentifier().getStringValue());
			left(childPart, node);
			jsonChildren.add(node);
		}
		rootNode.put("children", jsonChildren);
	}

	public JSONObject exist(Map<String, Object> params) throws Exception {
		String oid = (String) params.get("oid");
		WTPart part = (WTPart) CommonUtils.persistable(oid);
		JSONObject node = new JSONObject();
		try {
			node.put("thumb", ThumbnailUtils.thumbnails(part)[1]);
			node.put("partName", IBAUtils.getStringValue(part, "PART_NAME"));
			node.put("number", part.getNumber());
			node.put("itemName", IBAUtils.getStringValue(part, "ITEM_NAME"));
			node.put("partNo", IBAUtils.getStringValue(part, "PART_NO"));
			node.put("version", part.getVersionIdentifier().getSeries().getValue());
			node.put("partType", partTypeToDisplay(part));
			node.put("state", part.getLifeCycleState().getDisplay());
			node.put("amount", 1);
			node.put("partTypeCd", IBAUtils.getStringValue(part, "PART_TYPE"));
			node.put("oid", part.getPersistInfo().getObjectIdentifier().getStringValue());
			node.put("library", isLibrary(part));
			node.put("uid", UUID.randomUUID());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return node;
	}

	public JSONArray right(String oid) throws Exception {
		ReferenceFactory rf = new ReferenceFactory();
		WTPart part = (WTPart) rf.getReference(oid).getObject();
		JSONArray jsonArray = new JSONArray();
		JSONObject rootNode = new JSONObject();
		rootNode.put("thumb", ThumbnailUtils.thumbnails(part)[1]);
		rootNode.put("partName", IBAUtils.getStringValue(part, "PART_NAME"));
		rootNode.put("number", part.getNumber());
		rootNode.put("itemName", IBAUtils.getStringValue(part, "ITEM_NAME"));
		rootNode.put("partNo", IBAUtils.getStringValue(part, "PART_NO"));
		rootNode.put("version", part.getVersionIdentifier().getSeries().getValue());
		rootNode.put("partType", partTypeToDisplay(part));
		rootNode.put("state", part.getLifeCycleState().getDisplay());
		rootNode.put("amount", 1);
		rootNode.put("partTypeCd", IBAUtils.getStringValue(part, "PART_TYPE"));
		rootNode.put("oid", part.getPersistInfo().getObjectIdentifier().getStringValue());
		rootNode.put("library", isLibrary(part));
		rootNode.put("uid", UUID.randomUUID());
		jsonArray.add(rootNode);
		return jsonArray;
	}

	public ArrayList<WTPartUsageLink> getterLinks(WTPart parent, ArrayList<WTPartUsageLink> list) throws Exception {
		View view = ViewHelper.service.getView(parent.getViewName());
		State state = parent.getLifeCycleState();
		WTPartStandardConfigSpec configSpec = WTPartStandardConfigSpec.newWTPartStandardConfigSpec(view, state);
		QueryResult result = WTPartHelper.service.getUsesWTParts(parent, configSpec);
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			if (!(obj[1] instanceof WTPart)) {
				continue;
			}
			WTPartUsageLink link = (WTPartUsageLink) obj[0];
			WTPart childPart = (WTPart) obj[1];
			list.add(link);
			getterLinks(childPart, list);
		}
		return list;
	}

	public ArrayList<WTPart> getter(WTPart root) throws Exception {
		ArrayList<WTPart> list = new ArrayList<WTPart>();
		list.add(root);
		getter(root, list);
		return list;
	}

	private void getter(WTPart parent, ArrayList<WTPart> list) throws Exception {
		String viewName = parent.getViewName();
		if (!StringUtils.isNotNull(viewName)) {
			viewName = "Design";
		}
		View view = ViewHelper.service.getView(viewName);
		State state = parent.getLifeCycleState();
		WTPartStandardConfigSpec configSpec = WTPartStandardConfigSpec.newWTPartStandardConfigSpec(view, null);
		QueryResult result = WTPartHelper.service.getUsesWTParts(parent, configSpec);
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			if (!(obj[1] instanceof WTPart)) {
				continue;
			}
			WTPart childPart = (WTPart) obj[1];
			list.add(childPart);
			getter(childPart, list);
		}
	}

	public boolean isMaterialPart(WTPart part) throws Exception {
		String partType = IBAUtils.getStringValue(part, "PART_TYPE");
		if ("MAT".equals(partType)) {
			return true;
		}
		return false;
	}

	public List<PartColumns> attach(Map<String, Object> params) throws Exception {
		List<PartColumns> data = new ArrayList<>();
		WTPart part = (WTPart) CommonUtils.persistable((String) params.get("oid"));
		ArrayList<WTPart> list = getter(part);
		for (WTPart p : list) {
			PartColumns col = new PartColumns(p);
			data.add(col);
		}
		return data;
	}

	public WTPart getLatest(WTPartMaster master) throws Exception {
		String number = master.getNumber();
		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(WTPart.class, true);
		int idx_master = query.appendClassList(WTPartMaster.class, false);

		SearchCondition sc = null;

		sc = WorkInProgressHelper.getSearchCondition_CI(WTPart.class);
		query.appendWhere(sc, new int[] { idx });
		query.appendAnd();

		sc = VersionControlHelper.getSearchCondition(WTPart.class, true);
		query.appendWhere(sc, new int[] { idx });
		query.appendAnd();

		sc = new SearchCondition(WTPart.class, "masterReference.key.id", WTPartMaster.class,
				"thePersistInfo.theObjectIdentifier.id");
		query.appendWhere(sc, new int[] { idx, idx_master });
		query.appendAnd();

		sc = new SearchCondition(WTPartMaster.class, WTPartMaster.NUMBER, "=", number);
		query.appendWhere(sc, new int[] { idx_master });

		QueryResult result = PersistenceHelper.manager.find(query);
		WTPart part = null;
		if (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			part = (WTPart) obj[0];
		}

		return (WTPart) CommonUtils.getLatestVersion(part);
	}

	public WTPartUsageLink getLink(WTPart parent, WTPartMaster child) throws Exception {
		WTPartUsageLink link = null;

		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(WTPartUsageLink.class, true);

		SearchCondition sc = new SearchCondition(WTPartUsageLink.class, "roleAObjectRef.key.id", "=",
				parent.getPersistInfo().getObjectIdentifier().getId());
		query.appendWhere(sc, new int[] { idx });
		query.appendAnd();

		sc = new SearchCondition(WTPartUsageLink.class, "roleBObjectRef.key.id", "=",
				child.getPersistInfo().getObjectIdentifier().getId());
		query.appendWhere(sc, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find(query);
		if (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			link = (WTPartUsageLink) obj[0];
		}

		return link;
	}

	public boolean isLibrary(WTPart part) throws Exception {
		String containerName = part.getContainerName();
		if (containerName.equals(LIBRARY_CONTAINER)) {
			return true;
		}
		return false;
	}
	
	public List<DocumentColumns> getWTDocuments(WTPart part) throws Exception {
		
		List<DocumentColumns> reValue = new ArrayList<>();
		try {
			if (part == null) {
				return reValue;
			}
			
			QueryResult qr = PersistenceHelper.manager.navigate(part, "document", WTDocumentWTPartLink.class);
			while (qr.hasMoreElements()) {
				WTDocumentWTPartLink link = (WTDocumentWTPartLink) qr.nextElement();
				
				reValue.add(new DocumentColumns(link.getDocument()));
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reValue;
	}
	
}