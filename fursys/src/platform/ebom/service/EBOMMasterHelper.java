package platform.ebom.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import platform.ebom.entity.EBOM;
import platform.ebom.entity.EBOMECOLink;
import platform.ebom.entity.EBOMMaster;
import platform.ebom.entity.EBOMMasterColumns;
import platform.ebom.entity.EBOMMasterLink;
import platform.echange.eco.entity.ECO;
import platform.part.service.PartHelper;
import platform.util.CommonUtils;
import platform.util.DateUtils;
import platform.util.IBAUtils;
import platform.util.PageUtils;
import platform.util.StringUtils;
import platform.util.ThumbnailUtils;
import wt.epm.EPMDocument;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.org.WTUser;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.part.WTPartUsageLink;
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

public class EBOMMasterHelper {

	public static final String EBOM_CREATE_NOCONFIRM = "EBOM 작성중(검증미완료)";
	public static final String EBOM_CREATE_CONFIRM = "EBOM 작성중(검증완료)";
	public static final String PART_LIST_CREATE = "PART LIST 작성중";
	public static final String PART_LIST_TEMP = "PART LIST 임시저장";
	public static final String EBOM_APPROVAL = "EBOM 승인중";
	public static final String EBOM_APPROVED = "EBOM 승인됨";
	public static final String EBOM_TEMP = "EBOM 임시저장";
	public static final String EBOM_DERIVED = "EBOM 파생중";
	public static final String DEFAULT_COLOR = "-";
	public static final String checkString = "-";

//	public static final String EBOM_SET_TYPE = "EBOM_SET";
//	public static final String EBOM_ITEM_TYPE = "EBOM_ITEM";
//	public static final String EBOM_TYPE = "EBOM";

	public static final EBOMMasterService service = ServiceFactory.getService(EBOMMasterService.class);
	public static final EBOMMasterHelper manager = new EBOMMasterHelper();

	public JSONArray loadETree(String oid) throws Exception {
		DecimalFormat df = new DecimalFormat("###0.000");
		ReferenceFactory rf = new ReferenceFactory();
		EBOMMaster header = (EBOMMaster) rf.getReference(oid).getObject();
		WTPart headerPart = PartHelper.manager.getLatest(header.getPart());
		JSONArray jsonArray = new JSONArray();
		JSONObject rootNode = new JSONObject();
		rootNode.put("thumb", ThumbnailUtils.thumbnails(headerPart)[1]);
//		rootNode.put("title", headerPart.getNumber());
//		rootNode.put("title", headerPart.getNumber());
		rootNode.put("itemName", IBAUtils.getStringValue(headerPart, "ITEM_NAME"));
		rootNode.put("partType", PartHelper.manager.partTypeToDisplay(headerPart));
		rootNode.put("partTypeCd", IBAUtils.getStringValue(headerPart, "PART_TYPE"));
		rootNode.put("partNo", IBAUtils.getStringValue(headerPart, "PART_NO"));
		rootNode.put("name", IBAUtils.getStringValue(headerPart, "PART_NAME"));
		rootNode.put("unit", headerPart.getDefaultUnit().getDisplay());
		rootNode.put("version", headerPart.getVersionIdentifier().getSeries().getValue());
		rootNode.put("state", headerPart.getLifeCycleState().getDisplay());
//		rootNode.put("amount", header.getAmount());
		rootNode.put("amount", df.format(header.getAmount()));
		rootNode.put("material", IBAUtils.getStringValue(headerPart, "MATERIAL"));
		String erpCode = IBAUtils.getStringValue(headerPart, "ERP_CODE");
		if (PartHelper.manager.isMaterialPart(headerPart)) {
			// 자재
			if (StringUtils.isNotNull(erpCode)) {
				rootNode.put("erpCode", erpCode);
				rootNode.put("title", erpCode);
			} else {
				rootNode.put("erpCode", IBAUtils.getStringValue(headerPart, "PART_NO"));
				rootNode.put("title", IBAUtils.getStringValue(headerPart, "PART_NO"));
			}
		} else {
			// 세트 단품
			if (StringUtils.isNotNull(erpCode)) {
				rootNode.put("erpCode", erpCode);
				rootNode.put("title", erpCode);
			} else {
				String itemName = IBAUtils.getStringValue(headerPart, "ITEM_NAME");
				rootNode.put("erpCode", itemName);
				rootNode.put("title", itemName);
			}
		}
		EPMDocument epm = PartHelper.manager.getEPMDocument(headerPart);
		if (epm != null) {
			rootNode.put("eoid", epm.getPersistInfo().getObjectIdentifier().getStringValue());
		}
		rootNode.put("oid", headerPart.getPersistInfo().getObjectIdentifier().getStringValue());
		rootNode.put("expanded", true);
		rootNode.put("icon", "/Windchill/jsp/images/part.gif");
		rootNode.put("level", 1);
		rootNode.put("library", headerPart.getContainerName().equalsIgnoreCase(PartHelper.LIBRARY_CONTAINER));

		JSONArray array = new JSONArray();
		ArrayList<EBOMMasterLink> result = getEBOMMasterLink(header);
		for (EBOMMasterLink link : result) {
			EBOMMaster child = link.getChild();
			WTPart childPart = PartHelper.manager.getLatest(child.getPart());
			boolean isBom = IBAUtils.getBooleanValue(childPart, "BOM");
			if (!isBom) {
//				continue;
			}
			JSONObject node = new JSONObject();
			node.put("thumb", ThumbnailUtils.thumbnails(childPart)[1]);
//			node.put("title", childPart.getNumber());
			node.put("itemName", IBAUtils.getStringValue(childPart, "ITEM_NAME"));
			node.put("partType", PartHelper.manager.partTypeToDisplay(childPart));
			node.put("partTypeCd", IBAUtils.getStringValue(childPart, "PART_TYPE"));
			node.put("partNo", IBAUtils.getStringValue(childPart, "PART_NO"));
			node.put("name", IBAUtils.getStringValue(childPart, "PART_NAME"));
			node.put("unit", childPart.getDefaultUnit().getDisplay());
			node.put("version", childPart.getVersionIdentifier().getSeries().getValue());
			node.put("state", childPart.getLifeCycleState().getDisplay());
//			node.put("amount", child.getAmount());
			node.put("amount", df.format(child.getAmount()));
			node.put("material", IBAUtils.getStringValue(childPart, "MATERIAL"));
			String _erpCode = IBAUtils.getStringValue(childPart, "ERP_CODE");
			if (PartHelper.manager.isMaterialPart(childPart)) {
				// 자재
				if (StringUtils.isNotNull(_erpCode)) {
					node.put("erpCode", _erpCode);
					node.put("title", _erpCode);
				} else {
					node.put("erpCode", IBAUtils.getStringValue(childPart, "PART_NO"));
					node.put("title", IBAUtils.getStringValue(childPart, "PART_NO"));
				}
			} else {
				// 세트 단품
				if (StringUtils.isNotNull(_erpCode)) {
					node.put("erpCode", _erpCode);
					node.put("title", _erpCode);
				} else {
					String itemName = IBAUtils.getStringValue(childPart, "ITEM_NAME");
					node.put("erpCode", itemName);
					node.put("title", itemName);
				}
			}
			EPMDocument ee = PartHelper.manager.getEPMDocument(childPart);
			if (ee != null) {
				node.put("eoid", ee.getPersistInfo().getObjectIdentifier().getStringValue());
			}
			node.put("oid", childPart.getPersistInfo().getObjectIdentifier().getStringValue());
			node.put("expanded", true);
			node.put("icon", "/Windchill/jsp/images/part.gif");
			node.put("level", link.getDepth());
			node.put("library", childPart.getContainerName().equalsIgnoreCase(PartHelper.LIBRARY_CONTAINER));
			loadETree(child, node);
			array.add(node);
		}
		rootNode.put("children", array);
		jsonArray.add(rootNode);
		return jsonArray;
	}

	private void loadETree(EBOMMaster parent, JSONObject rootNode) throws Exception {
		DecimalFormat df = new DecimalFormat("###0.000");
		JSONArray jsonChildren = new JSONArray();
		ArrayList<EBOMMasterLink> result = getEBOMMasterLink(parent);
		for (EBOMMasterLink link : result) {
			EBOMMaster child = link.getChild();
			WTPart childPart = PartHelper.manager.getLatest(child.getPart());
			boolean isBom = IBAUtils.getBooleanValue(childPart, "BOM");
			if (!isBom) {
				// bom 처리
//				continue;
			}
			JSONObject node = new JSONObject();
			node.put("pId", link.getParent().getPersistInfo().getObjectIdentifier().getId());
			node.put("thumb", ThumbnailUtils.thumbnails(childPart)[1]);
			node.put("number", childPart.getNumber());
			node.put("itemName", IBAUtils.getStringValue(childPart, "ITEM_NAME"));
			node.put("partType", PartHelper.manager.partTypeToDisplay(childPart));
			node.put("partTypeCd", IBAUtils.getStringValue(childPart, "PART_TYPE"));
			node.put("partNo", IBAUtils.getStringValue(childPart, "PART_NO"));
			node.put("name", IBAUtils.getStringValue(childPart, "PART_NAME"));
			node.put("unit", childPart.getDefaultUnit().getDisplay());
			node.put("version", childPart.getVersionIdentifier().getSeries().getValue());
//			node.put("amount", child.getAmount());'
			node.put("amount", df.format(child.getAmount()));
			node.put("material", IBAUtils.getStringValue(childPart, "MATERIAL"));
			String erpCode = IBAUtils.getStringValue(childPart, "ERP_CODE");
			if (PartHelper.manager.isMaterialPart(childPart)) {
				// 자재
				if (StringUtils.isNotNull(erpCode)) {
					node.put("erpCode", erpCode);
					node.put("title", erpCode);
				} else {
					// partlist 전까지는 무조건 생성된다
					String partNo = IBAUtils.getStringValue(childPart, "PART_NO");
					// null 이거나 - 일경우는 부품번호로 세팅
					node.put("erpCode", partNo);
					node.put("title", partNo);
				}
			} else {
				// 세트 단품
				if (StringUtils.isNotNull(erpCode)) {
					node.put("erpCode", erpCode);
					node.put("title", erpCode);
				} else {
					String itemName = IBAUtils.getStringValue(childPart, "ITEM_NAME");
					node.put("erpCode", itemName);
					node.put("title", itemName);
				}
			}
			EPMDocument ee = PartHelper.manager.getEPMDocument(childPart);
			if (ee != null) {
				node.put("eoid", ee.getPersistInfo().getObjectIdentifier().getStringValue());
			}
			node.put("state", childPart.getLifeCycleState().getDisplay());
			node.put("oid", childPart.getPersistInfo().getObjectIdentifier().getStringValue());
			node.put("expanded", true);
			node.put("icon", "/Windchill/jsp/images/part.gif");
			node.put("level", link.getDepth());
			node.put("link", link.getPersistInfo().getObjectIdentifier().getStringValue());
			node.put("library", childPart.getContainerName().equalsIgnoreCase(PartHelper.LIBRARY_CONTAINER));
			loadETree(child, node);
			jsonChildren.add(node);
		}
		rootNode.put("children", jsonChildren);
	}

	public ArrayList<EBOMMasterLink> getEBOMMasterLink(EBOMMaster item) throws Exception {
		ArrayList<EBOMMasterLink> list = new ArrayList<EBOMMasterLink>();

		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(EBOMMasterLink.class, true);

		SearchCondition sc = new SearchCondition(EBOMMasterLink.class, "roleAObjectRef.key.id", "=",
				item.getPersistInfo().getObjectIdentifier().getId());
		query.appendWhere(sc, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find(query);
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			EBOMMasterLink link = (EBOMMasterLink) obj[0];
			list.add(link);
		}
		return list;
	}

	public EBOMMaster getEBOMMaster(WTPartMaster part) throws Exception {
		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(EBOMMaster.class, true);

		SearchCondition sc = new SearchCondition(EBOMMaster.class, "partReference.key.id", "=",
				part.getPersistInfo().getObjectIdentifier().getId());
		query.appendWhere(sc, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find(query);
		EBOMMaster ebom = null;
		if (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			ebom = (EBOMMaster) obj[0];
		}
		return ebom;
	}

	public EBOMMaster getParentEBOMMaster(WTPartMaster part) throws Exception {
		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(EBOMMaster.class, true);
		int idx_link = query.appendClassList(EBOMMasterLink.class, true);

		SearchCondition sc = new SearchCondition(EBOMMaster.class, "partReference.key.id", "=",
				part.getPersistInfo().getObjectIdentifier().getId());
		query.appendWhere(sc, new int[] { idx });
		query.appendAnd();

		sc = new SearchCondition(EBOMMaster.class, "thePersistInfo.theObjectIdentifier.id", EBOMMasterLink.class,
				"roleAObjectRef.key.id");
		query.appendWhere(sc, new int[] { idx, idx_link });

		QueryResult result = PersistenceHelper.manager.find(query);
		EBOMMaster parent = null;
		if (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			EBOMMasterLink link = (EBOMMasterLink) obj[1];
			parent = link.getParent();
		}
		return parent;
	}

	public boolean isExistLink(EBOMMaster parent, EBOMMaster child) throws Exception {
		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(EBOMMasterLink.class, true);

		SearchCondition sc = new SearchCondition(EBOMMasterLink.class, "roleAObjectRef.key.id", "=",
				parent.getPersistInfo().getObjectIdentifier().getId());
		query.appendWhere(sc, new int[] { idx });
		query.appendAnd();

		sc = new SearchCondition(EBOMMasterLink.class, "roleBObjectRef.key.id", "=",
				child.getPersistInfo().getObjectIdentifier().getId());
		query.appendWhere(sc, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find(query);
		return result.size() > 0;
	}

	public Map<String, Object> list(Map<String, Object> params) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<EBOMMasterColumns> list = new ArrayList<EBOMMasterColumns>();
		String state = (String) params.get("state");
		String number = (String) params.get("number");
		String ecoNumber = (String) params.get("ecoNumber");
		String creatorOid = (String) params.get("creatorOid");
		String startCreatedDate = (String) params.get("startCreatedDate");
		String endCreatedDate = (String) params.get("endCreatedDate");
		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(EBOMMaster.class, true);

		SearchCondition sc = null;
		ClassAttribute ca = null;

		if (StringUtils.isNotNull(ecoNumber)) {
//			if (query.getConditionCount() > 0) {
//				query.appendAnd();
//			}
//			int idx_link = query.appendClassList(EBOMHeaderECOLink.class, false);
//			int idx_eco = query.appendClassList(ECO.class, false);
//
//			sc = new SearchCondition(EBOMHeader.class, "thePersistInfo.theObjectIdentifier.id", EBOMHeaderECOLink.class,
//					"roleAObjectRef.key.id");
//			query.appendWhere(sc, new int[] { idx, idx_link });
//			query.appendAnd();
//
//			sc = new SearchCondition(ECO.class, "thePersistInfo.theObjectIdentifier.id", EBOMHeaderECOLink.class,
//					"roleBObjectRef.key.id");
//			query.appendWhere(sc, new int[] { idx_eco, idx_link });
//			query.appendAnd();
//
//			ca = new ClassAttribute(ECO.class, ECO.NUMBER);
//			ColumnExpression ce = ConstantExpression.newExpression("%" + ecoNumber.toUpperCase() + "%");
//			SQLFunction function = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca);
//			sc = new SearchCondition(function, SearchCondition.LIKE, ce);
//			query.appendWhere(sc, new int[] { idx_eco });
		}

		if (StringUtils.isNotNull(number)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			ca = new ClassAttribute(EBOMMaster.class, EBOMMaster.NUMBER);
			ColumnExpression ce = ConstantExpression.newExpression("%" + number.toUpperCase() + "%");
			SQLFunction function = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca);
			sc = new SearchCondition(function, SearchCondition.LIKE, ce);
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(state)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(EBOMMaster.class, EBOMMaster.STATE, "=", state);
			query.appendWhere(sc, new int[] { idx });
		}
		// 작성일자
		if (StringUtils.isNotNull(startCreatedDate)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(EBOMMaster.class, WTAttributeNameIfc.CREATE_STAMP_NAME, ">=",
					DateUtils.startTimestamp(startCreatedDate));
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(endCreatedDate)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(EBOMMaster.class, WTAttributeNameIfc.CREATE_STAMP_NAME, "<=",
					DateUtils.endTimestamp(endCreatedDate));
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(creatorOid)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}

			WTUser creator = (WTUser) CommonUtils.persistable(creatorOid);
			sc = new SearchCondition(EBOMMaster.class, "ownership.owner.key.id", "=",
					creator.getPersistInfo().getObjectIdentifier().getId());
			query.appendWhere(sc, new int[] { idx });
		}

		if (query.getConditionCount() > 0) {
			query.appendAnd();
		}

		sc = new SearchCondition(EBOMMaster.class, EBOMMaster.BOM_TYPE, "=", "SET");
		query.appendWhere(sc, new int[] { idx });

		ca = new ClassAttribute(EBOMMaster.class, EBOMMaster.CREATE_TIMESTAMP);
		OrderBy by = new OrderBy(ca, true);
		query.appendOrderBy(by, new int[] { idx });

		PageUtils pager = new PageUtils(params, query);
		QueryResult result = pager.find();
		int total = pager.getTotal();
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			EBOMMasterColumns columns = new EBOMMasterColumns((EBOMMaster) obj[0]);
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

	public ArrayList<EBOMMasterLink> getAllEBOMMasterLink(EBOMMaster parent) throws Exception {
		ArrayList<EBOMMasterLink> list = new ArrayList<EBOMMasterLink>();
		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(EBOMMasterLink.class, true);

		SearchCondition sc = null;
		sc = new SearchCondition(EBOMMasterLink.class, "roleAObjectRef.key.id", "=",
				parent.getPersistInfo().getObjectIdentifier().getId());
		query.appendWhere(sc, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find(query);
		EBOMMasterLink link = null;
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			link = (EBOMMasterLink) obj[0];
			EBOMMaster child = link.getChild();
			list.add(link);
			getAllEBOMMasterLink(child, list);
		}
		return list;
	}

	private void getAllEBOMMasterLink(EBOMMaster parent, ArrayList<EBOMMasterLink> list) throws Exception {
		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(EBOMMasterLink.class, true);

		SearchCondition sc = null;
		sc = new SearchCondition(EBOMMasterLink.class, "roleAObjectRef.key.id", "=",
				parent.getPersistInfo().getObjectIdentifier().getId());
		query.appendWhere(sc, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find(query);
		EBOMMasterLink link = null;
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			link = (EBOMMasterLink) obj[0];
			EBOMMaster child = link.getChild();
			list.add(link);
			getAllEBOMMasterLink(child, list);
		}
	}

	public JSONArray loadPTree(String oid, String color) throws Exception {
		DecimalFormat df = new DecimalFormat("###0.000");
		ReferenceFactory rf = new ReferenceFactory();
		EBOMMaster eHeader = (EBOMMaster) rf.getReference(oid).getObject();
		WTPart headerPart = PartHelper.manager.getLatest(eHeader.getPart());
		EBOMMaster header = getEBOMMaster(headerPart.getMaster(), color);
		JSONArray jsonArray = new JSONArray();
		JSONObject rootNode = new JSONObject();
		rootNode.put("thumb", ThumbnailUtils.thumbnails(headerPart)[1]);
//		rootNode.put("title", headerPart.getNumber());
		rootNode.put("itemName", IBAUtils.getStringValue(headerPart, "ITEM_NAME"));
		rootNode.put("partType", PartHelper.manager.partTypeToDisplay(headerPart));
		rootNode.put("partTypeCd", IBAUtils.getStringValue(headerPart, "PART_TYPE"));
		rootNode.put("partNo", IBAUtils.getStringValue(headerPart, "PART_NO"));
		rootNode.put("name", IBAUtils.getStringValue(headerPart, "PART_NAME"));
		rootNode.put("unit", headerPart.getDefaultUnit().getDisplay());
		rootNode.put("version", headerPart.getVersionIdentifier().getSeries().getValue());
		rootNode.put("amount", df.format(header.getAmount()));
		rootNode.put("applyColor", header.getApplyColor());
		rootNode.put("managerName", header.getManager() != null ? header.getManager().getFullName() : "");
		rootNode.put("material", IBAUtils.getStringValue(headerPart, "MATERIAL"));
		String erpCode = IBAUtils.getStringValue(headerPart, "ERP_CODE");
		if (PartHelper.manager.isMaterialPart(headerPart)) {
			// 자재
			if (StringUtils.isNotNull(erpCode)) {
				rootNode.put("erpCode", erpCode);
				rootNode.put("title", erpCode);
			} else {
				rootNode.put("erpCode", IBAUtils.getStringValue(headerPart, "PART_NO"));
				rootNode.put("title", IBAUtils.getStringValue(headerPart, "PART_NO"));
			}
		} else {
			// 세트 단품
			if (StringUtils.isNotNull(erpCode)) {
				rootNode.put("erpCode", erpCode);
				rootNode.put("title", erpCode);
			} else {
				String itemName = IBAUtils.getStringValue(headerPart, "ITEM_NAME");
				rootNode.put("erpCode", itemName);
				rootNode.put("title", itemName);
			}
		}
		EPMDocument epm = PartHelper.manager.getEPMDocument(headerPart);
		if (epm != null) {
			rootNode.put("eoid", epm.getPersistInfo().getObjectIdentifier().getStringValue());
		}
		rootNode.put("number", headerPart.getNumber());
		rootNode.put("poid", header.getPart().getPersistInfo().getObjectIdentifier().getStringValue());
		rootNode.put("oid", header.getPersistInfo().getObjectIdentifier().getStringValue());
		rootNode.put("expanded", true);
		rootNode.put("icon", "/Windchill/jsp/images/part.gif");
		rootNode.put("level", 1);
		rootNode.put("library", headerPart.getContainerName().equalsIgnoreCase(PartHelper.LIBRARY_CONTAINER));

		JSONArray array = new JSONArray();
		ArrayList<EBOMMasterLink> result = getEBOMMasterLink(header);
		for (EBOMMasterLink link : result) {
			EBOMMaster child = link.getChild();
			WTPart childPart = PartHelper.manager.getLatest(child.getPart());
			boolean isBom = IBAUtils.getBooleanValue(childPart, "BOM");
			if (!isBom) {
//				continue;
			}
			JSONObject node = new JSONObject();
			node.put("thumb", ThumbnailUtils.thumbnails(childPart)[1]);
//			node.put("title", childPart.getNumber());
			node.put("itemName", IBAUtils.getStringValue(childPart, "ITEM_NAME"));
			node.put("partType", PartHelper.manager.partTypeToDisplay(childPart));
			node.put("partTypeCd", IBAUtils.getStringValue(childPart, "PART_TYPE"));
			node.put("partNo", IBAUtils.getStringValue(childPart, "PART_NO"));
			node.put("name", IBAUtils.getStringValue(childPart, "PART_NAME"));
			node.put("unit", childPart.getDefaultUnit().getDisplay());
			node.put("version", childPart.getVersionIdentifier().getSeries().getValue());
			node.put("amount", df.format(child.getAmount()));
			node.put("applyColor", child.getApplyColor());
			node.put("managerName", child.getManager() != null ? child.getManager().getFullName() : "");
			node.put("material", IBAUtils.getStringValue(childPart, "MATERIAL"));
			String _erpCode = IBAUtils.getStringValue(childPart, "ERP_CODE");
			if (PartHelper.manager.isMaterialPart(childPart)) {
				// 자재
				if (StringUtils.isNotNull(_erpCode)) {
					node.put("erpCode", _erpCode);
					node.put("title", _erpCode);
				} else {
					node.put("erpCode", IBAUtils.getStringValue(childPart, "PART_NO"));
					node.put("title", IBAUtils.getStringValue(childPart, "PART_NO"));
				}
			} else {
				// 세트 단품
				if (StringUtils.isNotNull(_erpCode)) {
					node.put("erpCode", _erpCode);
					node.put("title", _erpCode);
				} else {
					String itemName = IBAUtils.getStringValue(childPart, "ITEM_NAME");
					node.put("erpCode", itemName);
					node.put("title", itemName);
				}
			}
			EPMDocument ee = PartHelper.manager.getEPMDocument(childPart);
			if (ee != null) {
				node.put("eoid", ee.getPersistInfo().getObjectIdentifier().getStringValue());
			}
			node.put("number", childPart.getNumber());
			node.put("poid", child.getPart().getPersistInfo().getObjectIdentifier().getStringValue());
			node.put("oid", child.getPersistInfo().getObjectIdentifier().getStringValue());
			node.put("expanded", true);
			node.put("icon", "/Windchill/jsp/images/part.gif");
			node.put("level", link.getDepth());
			node.put("library", childPart.getContainerName().equalsIgnoreCase(PartHelper.LIBRARY_CONTAINER));
			loadPTree(child, node);
			array.add(node);
		}
		rootNode.put("children", array);
		jsonArray.add(rootNode);
		return jsonArray;
	}

	private void loadPTree(EBOMMaster parent, JSONObject rootNode) throws Exception {
		DecimalFormat df = new DecimalFormat("###0.000");
		JSONArray jsonChildren = new JSONArray();
		ArrayList<EBOMMasterLink> result = getEBOMMasterLink(parent);
		for (EBOMMasterLink link : result) {
			EBOMMaster child = link.getChild();
			WTPart childPart = PartHelper.manager.getLatest(child.getPart());
			boolean isBom = IBAUtils.getBooleanValue(childPart, "BOM");
			if (!isBom) {
				// bom 처리
//				continue;
			}
			JSONObject node = new JSONObject();
			node.put("thumb", ThumbnailUtils.thumbnails(childPart)[1]);
			node.put("number", childPart.getNumber());
			node.put("itemName", IBAUtils.getStringValue(childPart, "ITEM_NAME"));
			node.put("partType", PartHelper.manager.partTypeToDisplay(childPart));
			node.put("partTypeCd", IBAUtils.getStringValue(childPart, "PART_TYPE"));
			node.put("partNo", IBAUtils.getStringValue(childPart, "PART_NO"));
			node.put("name", IBAUtils.getStringValue(childPart, "PART_NAME"));
			node.put("unit", childPart.getDefaultUnit().getDisplay());
			node.put("version", childPart.getVersionIdentifier().getSeries().getValue());
//			node.put("amount", child.getAmount());
			node.put("amount", df.format(child.getAmount()));
			node.put("applyColor", child.getApplyColor());
			node.put("managerName", child.getManager() != null ? child.getManager().getFullName() : "");
			node.put("material", IBAUtils.getStringValue(childPart, "MATERIAL"));
			String erpCode = IBAUtils.getStringValue(childPart, "ERP_CODE");
			if (PartHelper.manager.isMaterialPart(childPart)) {
				// 자재
				if (StringUtils.isNotNull(erpCode)) {
					node.put("erpCode", erpCode);
					node.put("title", erpCode);
				} else {
					// partlist 전까지는 무조건 생성된다
					String partNo = IBAUtils.getStringValue(childPart, "PART_NO");
					// null 이거나 - 일경우는 부품번호로 세팅
					node.put("erpCode", partNo);
					node.put("title", partNo);
				}
			} else {
				// 세트 단품
				if (StringUtils.isNotNull(erpCode)) {
					node.put("erpCode", erpCode);
					node.put("title", erpCode);
				} else {
					String itemName = IBAUtils.getStringValue(childPart, "ITEM_NAME");
					node.put("erpCode", itemName);
					node.put("title", itemName);
				}
			}
			EPMDocument ee = PartHelper.manager.getEPMDocument(childPart);
			if (ee != null) {
				node.put("eoid", ee.getPersistInfo().getObjectIdentifier().getStringValue());
			}
//			node.put("number", childPart.getNumber());
			node.put("poid", child.getPart().getPersistInfo().getObjectIdentifier().getStringValue());
			node.put("oid", child.getPersistInfo().getObjectIdentifier().getStringValue());
			node.put("expanded", true);
			node.put("icon", "/Windchill/jsp/images/part.gif");
			node.put("level", link.getDepth());
			node.put("link", link.getPersistInfo().getObjectIdentifier().getStringValue());
			node.put("library", childPart.getContainerName().equalsIgnoreCase(PartHelper.LIBRARY_CONTAINER));
			loadPTree(child, node);
			jsonChildren.add(node);
		}
		rootNode.put("children", jsonChildren);
	}

	public Map<String, Object> plist(Map<String, Object> params) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<EBOMMasterColumns> list = new ArrayList<EBOMMasterColumns>();
		String state = (String) params.get("state");
		String number = (String) params.get("number");
		String ecoNumber = (String) params.get("ecoNumber");
		String creatorOid = (String) params.get("creatorOid");
		String startCreatedDate = (String) params.get("startCreatedDate");
		String endCreatedDate = (String) params.get("endCreatedDate");
		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(EBOMMaster.class, true);

		SearchCondition sc = null;
		ClassAttribute ca = null;

		WTUser manager = (WTUser) SessionHelper.manager.getPrincipal();

		if (!CommonUtils.isAdmin()) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}

			sc = new SearchCondition(EBOMMaster.class, "managerReference.key.id", "=",
					manager.getPersistInfo().getObjectIdentifier().getId());
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(ecoNumber)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			int idx_link = query.appendClassList(EBOMECOLink.class, false);
			int idx_eco = query.appendClassList(ECO.class, false);

			sc = new SearchCondition(EBOM.class, "thePersistInfo.theObjectIdentifier.id", EBOMECOLink.class,
					"roleAObjectRef.key.id");
			query.appendWhere(sc, new int[] { idx, idx_link });
			query.appendAnd();

			sc = new SearchCondition(ECO.class, "thePersistInfo.theObjectIdentifier.id", EBOMECOLink.class,
					"roleBObjectRef.key.id");
			query.appendWhere(sc, new int[] { idx_eco, idx_link });
			query.appendAnd();

			ca = new ClassAttribute(ECO.class, ECO.NUMBER);
			ColumnExpression ce = ConstantExpression.newExpression("%" + ecoNumber.toUpperCase() + "%");
			SQLFunction function = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca);
			sc = new SearchCondition(function, SearchCondition.LIKE, ce);
			query.appendWhere(sc, new int[] { idx_eco });
		}

		if (StringUtils.isNotNull(number)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			ca = new ClassAttribute(EBOMMaster.class, EBOMMaster.NUMBER);
			ColumnExpression ce = ConstantExpression.newExpression("%" + number.toUpperCase() + "%");
			SQLFunction function = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca);
			sc = new SearchCondition(function, SearchCondition.LIKE, ce);
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(state)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(EBOMMaster.class, EBOMMaster.STATE, "=", state);
			query.appendWhere(sc, new int[] { idx });
		}
		// 작성일자
		if (StringUtils.isNotNull(startCreatedDate)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(EBOMMaster.class, WTAttributeNameIfc.CREATE_STAMP_NAME, ">=",
					DateUtils.startTimestamp(startCreatedDate));
			query.appendWhere(sc, new int[] { idx });
		}
		if (StringUtils.isNotNull(endCreatedDate)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(EBOMMaster.class, WTAttributeNameIfc.CREATE_STAMP_NAME, "<=",
					DateUtils.endTimestamp(endCreatedDate));
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(creatorOid)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}

			WTUser creator = (WTUser) CommonUtils.persistable(creatorOid);
			sc = new SearchCondition(EBOMMaster.class, "ownership.owner.key.id", "=",
					creator.getPersistInfo().getObjectIdentifier().getId());
			query.appendWhere(sc, new int[] { idx });
		}

		if (query.getConditionCount() > 0) {
			query.appendAnd();
		}

		sc = new SearchCondition(EBOMMaster.class, "managerReference.key.id", "<>", 0L);
		query.appendWhere(sc, new int[] { idx });

		ca = new ClassAttribute(EBOMMaster.class, EBOMMaster.CREATE_TIMESTAMP);
		OrderBy by = new OrderBy(ca, true);
		query.appendOrderBy(by, new int[] { idx });

		PageUtils pager = new PageUtils(params, query);
		QueryResult result = pager.find();
		int total = pager.getTotal();
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			EBOMMasterColumns columns = new EBOMMasterColumns((EBOMMaster) obj[0]);
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

	public JSONArray info(Map<String, Object> params) throws Exception {
		String oid = (String) params.get("oid");
		EBOMMaster header = (EBOMMaster) CommonUtils.persistable(oid);
		WTPart headerPart = PartHelper.manager.getLatest(header.getPart());
		JSONArray jsonArray = new JSONArray();
		JSONObject rootNode = new JSONObject();
		rootNode.put("thumb", ThumbnailUtils.thumbnails(headerPart)[1]);
		rootNode.put("number", headerPart.getNumber());
		rootNode.put("itemName", IBAUtils.getStringValue(headerPart, "ITEM_NAME"));
		rootNode.put("partType", PartHelper.manager.partTypeToDisplay(headerPart));
		rootNode.put("partTypeCd", IBAUtils.getStringValue(headerPart, "PART_TYPE"));
		rootNode.put("partNo", IBAUtils.getStringValue(headerPart, "PART_NO"));
		rootNode.put("name", IBAUtils.getStringValue(headerPart, "PART_NAME"));
		rootNode.put("unit", headerPart.getDefaultUnit().getDisplay());
		rootNode.put("version", headerPart.getVersionIdentifier().getSeries().getValue());
		rootNode.put("amount", header.getAmount());
		rootNode.put("applyColor", header.getApplyColor());
		rootNode.put("managerName", header.getManager() != null ? header.getManager().getFullName() : "");
		rootNode.put("manager",
				header.getManager() != null
						? header.getManager().getPersistInfo().getObjectIdentifier().getStringValue()
						: "");
		rootNode.put("material", IBAUtils.getStringValue(headerPart, "MATERIAL"));
		String erpCode = IBAUtils.getStringValue(headerPart, "ERP_CODE");
		if (PartHelper.manager.isMaterialPart(headerPart)) {
			// 자재
			if (StringUtils.isNotNull(erpCode)) {
				rootNode.put("erpCode", erpCode);
				rootNode.put("title", erpCode);
			} else {
				rootNode.put("erpCode", IBAUtils.getStringValue(headerPart, "PART_NO"));
				rootNode.put("title", IBAUtils.getStringValue(headerPart, "PART_NO"));
			}
		} else {
			// 세트 단품
			if (StringUtils.isNotNull(erpCode)) {
				rootNode.put("erpCode", erpCode);
				rootNode.put("title", erpCode);
			} else {
				String itemName = IBAUtils.getStringValue(headerPart, "ITEM_NAME");
				rootNode.put("erpCode", itemName);
				rootNode.put("title", itemName);
			}
		}
		EPMDocument epm = PartHelper.manager.getEPMDocument(headerPart);
		if (epm != null) {
			rootNode.put("eoid", epm.getPersistInfo().getObjectIdentifier().getStringValue());
		}
		rootNode.put("poid", header.getPart().getPersistInfo().getObjectIdentifier().getStringValue());
		rootNode.put("oid", header.getPersistInfo().getObjectIdentifier().getStringValue());
		rootNode.put("expanded", true);
		rootNode.put("icon", "/Windchill/jsp/images/part.gif");
		rootNode.put("level", 1);
		rootNode.put("library", headerPart.getContainerName().equalsIgnoreCase(PartHelper.LIBRARY_CONTAINER));
		rootNode.put("pId", 0);

		JSONArray array = new JSONArray();
		ArrayList<EBOMMasterLink> result = getEBOMMasterLink(header);
		for (EBOMMasterLink link : result) {
			EBOMMaster child = link.getChild();
			WTPart childPart = PartHelper.manager.getLatest(child.getPart());
			boolean isBom = IBAUtils.getBooleanValue(childPart, "BOM");
			if (!isBom) {
//				continue;
			}
			JSONObject node = new JSONObject();
			node.put("pId", link.getParent().getPersistInfo().getObjectIdentifier().getId());
			node.put("thumb", ThumbnailUtils.thumbnails(childPart)[1]);
			node.put("number", childPart.getNumber());
			node.put("itemName", IBAUtils.getStringValue(childPart, "ITEM_NAME"));
			node.put("partType", PartHelper.manager.partTypeToDisplay(childPart));
			node.put("partTypeCd", IBAUtils.getStringValue(childPart, "PART_TYPE"));
			node.put("partNo", IBAUtils.getStringValue(childPart, "PART_NO"));
			node.put("name", IBAUtils.getStringValue(childPart, "PART_NAME"));
			node.put("unit", childPart.getDefaultUnit().getDisplay());
			node.put("version", childPart.getVersionIdentifier().getSeries().getValue());
			node.put("amount", child.getAmount());
			node.put("material", IBAUtils.getStringValue(childPart, "MATERIAL"));
			String _erpCode = IBAUtils.getStringValue(childPart, "ERP_CODE");
			if (PartHelper.manager.isMaterialPart(childPart)) {
				// 자재
				if (StringUtils.isNotNull(_erpCode)) {
					node.put("erpCode", _erpCode);
					node.put("title", _erpCode);
				} else {
					node.put("erpCode", IBAUtils.getStringValue(childPart, "PART_NO"));
					node.put("title", IBAUtils.getStringValue(childPart, "PART_NO"));
				}
			} else {
				// 세트 단품
				if (StringUtils.isNotNull(_erpCode)) {
					node.put("erpCode", _erpCode);
					node.put("title", _erpCode);
				} else {
					String itemName = IBAUtils.getStringValue(childPart, "ITEM_NAME");
					node.put("erpCode", itemName);
					node.put("title", itemName);
				}
			}
			EPMDocument ee = PartHelper.manager.getEPMDocument(childPart);
			if (ee != null) {
				node.put("eoid", ee.getPersistInfo().getObjectIdentifier().getStringValue());
			}
			node.put("applyColor", child.getApplyColor());
			node.put("isColorSet", child.getIsColorSet());
			node.put("oid", childPart.getPersistInfo().getObjectIdentifier().getStringValue());
			node.put("expanded", true);
			node.put("icon", "/Windchill/jsp/images/part.gif");
			node.put("level", link.getDepth());
			node.put("library", childPart.getContainerName().equalsIgnoreCase(PartHelper.LIBRARY_CONTAINER));
			loadPTree(child, node);
			array.add(node);
		}
		rootNode.put("children", array);
		jsonArray.add(rootNode);
		return jsonArray;
	}

	public ArrayList<EBOMMaster> getFirst(EBOMMaster mm, ArrayList<EBOMMaster> list) throws Exception {
		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(EBOMMasterLink.class, true);
		SearchCondition sc = new SearchCondition(EBOMMasterLink.class, "roleBObjectRef.key.id", "=",
				mm.getPersistInfo().getObjectIdentifier().getId());
		query.appendWhere(sc, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find(query);

		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			EBOMMasterLink link = (EBOMMasterLink) obj[0];
			EBOMMaster parent = link.getParent();
			getFirst(parent, list);
		}

		if (result.size() == 0) {
			list.add(mm);
		}

		return list;
	}

	public ArrayList<Map<String, Object>> compare(EBOMMaster header, ArrayList<Map<String, Object>> list)
			throws Exception {
//		DecimalFormat df = new DecimalFormat("###0.000");
		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(EBOMMaster.class, true);
		int idx_l = query.appendClassList(EBOMMasterLink.class, true);

		SearchCondition sc = new SearchCondition(EBOMMaster.class, WTAttributeNameIfc.ID_NAME, EBOMMasterLink.class,
				"roleAObjectRef.key.id");
		query.appendWhere(sc, new int[] { idx, idx_l });
		query.appendAnd();

		sc = new SearchCondition(EBOMMasterLink.class, "roleAObjectRef.key.id", "=",
				header.getPersistInfo().getObjectIdentifier().getId());
		query.appendWhere(sc, new int[] { idx_l });

		// header == parent
		WTPartMaster parentMaster = header.getPart();
		WTPart parentPart = PartHelper.manager.getLatest(parentMaster);
		QueryResult result = PersistenceHelper.manager.find(query);
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			EBOMMasterLink l = (EBOMMasterLink) obj[1];
			EBOMMaster child = l.getChild();
			WTPartMaster master = child.getPart();
			WTPart part = PartHelper.manager.getLatest(master);
			EPMDocument epm = PartHelper.manager.getEPMDocument(part);
			WTPartUsageLink link = PartHelper.manager.getLink(parentPart, master);

			double camount = link != null ? link.getQuantity().getAmount() : 0D;

			Map<String, Object> map = new HashMap<String, Object>();

			map.put("cadName", epm != null ? epm.getNumber() : "");
			map.put("partType", PartHelper.manager.partTypeToDisplay(part));
//			map.put("ref", epm != null ? "O" : "X");
			map.put("itemName", IBAUtils.getStringValue(part, "ITEM_NAME"));
			map.put("partName", IBAUtils.getStringValue(part, "PART_NAME"));
			map.put("partNo", IBAUtils.getStringValue(part, "ERP_CODE"));
			map.put("camount", camount);
			map.put("eamount", child.getAmount());
			map.put("compare", camount - child.getAmount());

			
			
			list.add(map);

			compare(child, list);

		}

		return list;
	}
}
