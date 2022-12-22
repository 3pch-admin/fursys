package platform.mbom.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import platform.ebom.service.EBOMHelper;
import platform.echange.eco.entity.ECO;
import platform.mbom.entity.MBOM;
import platform.mbom.entity.MBOMColumns;
import platform.mbom.entity.MBOMECOLink;
import platform.mbom.entity.MBOMLink;
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

public class MBOMHelper {

	public static final String COLOR_DEFAULT = "WW";

	public static final MBOMService service = ServiceFactory.getService(MBOMService.class);
	public static final MBOMHelper manager = new MBOMHelper();

	public static final String MBOM_CREATE = "MBOM 작성중";
	public static final String MBOM_APPROVAL = "MBOM 승인중";
	public static final String MBOM_APPROVED = "MBOM 승인됨";
	public static final String MBOM_FINISH = "MBOM 작성완료";
	public static final String MBOM_DERIVE = "MBOM 파생중";

	public static final String MBOM_SET_TYPE = "MBOM_SET";
	public static final String MBOM_ITEM_TYPE = "MBOM_ITEM";
	public static final String MBOM_TYPE = "MBOM";

	public Map<String, Object> list(Map<String, Object> params) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<MBOMColumns> list = new ArrayList<MBOMColumns>();
		String number = (String) params.get("number");//부품번호
		String ecoNumber = (String) params.get("ecoNumber");//ECO번호
		String state = (String) params.get("state"); //상태
		String erpCode = (String) params.get("erpCode");//ERP CODE
		String creatorOid = (String) params.get("creatorOid");
		String startCreatedDate = (String) params.get("startCreatedDate");
		String endCreatedDate = (String) params.get("endCreatedDate");
		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(MBOM.class, true);

		WTUser manager = (WTUser) SessionHelper.manager.getPrincipal();
		SearchCondition sc = null;
		ClassAttribute ca = null;

		if (!CommonUtils.isAdmin()) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}

			sc = new SearchCondition(MBOM.class, "managerReference.key.id", "=",
					manager.getPersistInfo().getObjectIdentifier().getId());
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(ecoNumber)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			int idx_link = query.appendClassList(MBOM.class, false);
			int idx_eco = query.appendClassList(ECO.class, false);

			sc = new SearchCondition(MBOM.class, "thePersistInfo.theObjectIdentifier.id", MBOMECOLink.class,
					"roleAObjectRef.key.id");
			query.appendWhere(sc, new int[] { idx, idx_link });
			query.appendAnd();

			sc = new SearchCondition(ECO.class, "thePersistInfo.theObjectIdentifier.id", MBOMECOLink.class,
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
			ca = new ClassAttribute(MBOM.class, MBOM.NUMBER);
			ColumnExpression ce = ConstantExpression.newExpression("%" + number.toUpperCase() + "%");
			SQLFunction function = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca);
			sc = new SearchCondition(function, SearchCondition.LIKE, ce);
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(state)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(MBOM.class, MBOM.STATE, "=", state);
			query.appendWhere(sc, new int[] { idx });
		}
		
		//ERPCODE
		if(StringUtils.isNotNull(erpCode)) {
			IBAUtils.equals(query, MBOM.class, idx, "ERP_CODE", erpCode);
		}
		
		// 작성일자
		if (StringUtils.isNotNull(startCreatedDate)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(MBOM.class, WTAttributeNameIfc.CREATE_STAMP_NAME, ">=",
					DateUtils.startTimestamp(startCreatedDate));
			query.appendWhere(sc, new int[] { idx });
		}
		if (StringUtils.isNotNull(endCreatedDate)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(MBOM.class, WTAttributeNameIfc.CREATE_STAMP_NAME, "<=",
					DateUtils.endTimestamp(endCreatedDate));
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(creatorOid)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}

			WTUser creator = (WTUser) CommonUtils.persistable(creatorOid);
			sc = new SearchCondition(MBOM.class, "ownership.owner.key.id", "=",
					creator.getPersistInfo().getObjectIdentifier().getId());
			query.appendWhere(sc, new int[] { idx });
		}

		if (query.getConditionCount() > 0) {
			query.appendAnd();
		}

		sc = new SearchCondition(MBOM.class, "managerReference.key.id", "<>", 0L);
		query.appendWhere(sc, new int[] { idx });

		ca = new ClassAttribute(MBOM.class, MBOM.CREATE_TIMESTAMP);
		OrderBy by = new OrderBy(ca, true);
		query.appendOrderBy(by, new int[] { idx });

		PageUtils pager = new PageUtils(params, query);
		QueryResult result = pager.find();
		int total = pager.getTotal();
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			MBOMColumns columns = new MBOMColumns((MBOM) obj[0]);
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

	public JSONArray loadMTree(String oid) throws Exception {
		DecimalFormat df = new DecimalFormat("###.####");
		ReferenceFactory rf = new ReferenceFactory();
		MBOM header = (MBOM) rf.getReference(oid).getObject();
		WTPart headerPart = header.getPart();
		JSONArray array = new JSONArray();
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
		rootNode.put("amount", header.getAmount());
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
				rootNode.put("title", erpCode);
			}
		}

		EPMDocument epm = PartHelper.manager.getEPMDocument(headerPart);
		if (epm != null) {
			rootNode.put("eoid", epm.getPersistInfo().getObjectIdentifier().getStringValue());
		}

		rootNode.put("height", StringUtils.convertToStr(IBAUtils.getStringValue(headerPart, "PART_HEIGHT"), "0 (mm)"));
		rootNode.put("depth", StringUtils.convertToStr(IBAUtils.getStringValue(headerPart, "PART_DEPTH"), "0 (mm)"));
		rootNode.put("width", StringUtils.convertToStr(IBAUtils.getStringValue(headerPart, "PART_WIDTH"), "0 (mm)"));
		rootNode.put("oid", header.getPersistInfo().getObjectIdentifier().getStringValue());
		rootNode.put("poid", headerPart.getPersistInfo().getObjectIdentifier().getStringValue());
		rootNode.put("expanded", true);
		rootNode.put("icon", "/Windchill/jsp/images/part.gif");
		rootNode.put("level", 1);
		rootNode.put("library", headerPart.getContainerName().equalsIgnoreCase(PartHelper.LIBRARY_CONTAINER));

		ArrayList<MBOMLink> result = getMBOMLink(header);
		for (MBOMLink link : result) {
			MBOM child = link.getChild();
			WTPart childPart = child.getPart();
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
			node.put("height", StringUtils.convertToStr(IBAUtils.getStringValue(childPart, "PART_HEIGHT"), "0 (mm)"));
			node.put("depth", StringUtils.convertToStr(IBAUtils.getStringValue(childPart, "PART_DEPTH"), "0 (mm)"));
			node.put("width", StringUtils.convertToStr(IBAUtils.getStringValue(childPart, "PART_WIDTH"), "0 (mm)"));
			node.put("oid", child.getPersistInfo().getObjectIdentifier().getStringValue());
			node.put("poid", childPart.getPersistInfo().getObjectIdentifier().getStringValue());
			node.put("expanded", true);
			node.put("icon", "/Windchill/jsp/images/part.gif");
			node.put("level", link.getDepth());
			node.put("library", childPart.getContainerName().equalsIgnoreCase(PartHelper.LIBRARY_CONTAINER));
			loadMTree(child, node);
			array.add(node);
		}
		rootNode.put("children", array);
		jsonArray.add(rootNode);
		return jsonArray;
	}

	private void loadMTree(MBOM parent, JSONObject rootNode) throws Exception {
		DecimalFormat df = new DecimalFormat("###.####");
		JSONArray jsonChildren = new JSONArray();
		ArrayList<MBOMLink> result = getMBOMLink(parent);
		for (MBOMLink link : result) {
			MBOM child = link.getChild();
			WTPart childPart = child.getPart();
			boolean isBom = IBAUtils.getBooleanValue(childPart, "BOM");
			if (!isBom) {
				// bom 처리
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
			node.put("height", StringUtils.convertToStr(IBAUtils.getStringValue(childPart, "PART_HEIGHT"), "0 (mm)"));
			node.put("depth", StringUtils.convertToStr(IBAUtils.getStringValue(childPart, "PART_DEPTH"), "0 (mm)"));
			node.put("width", StringUtils.convertToStr(IBAUtils.getStringValue(childPart, "PART_WIDTH"), "0 (mm)"));
			node.put("oid", childPart.getPersistInfo().getObjectIdentifier().getStringValue());
			node.put("poid", childPart.getPersistInfo().getObjectIdentifier().getStringValue());
			node.put("expanded", true);
			node.put("icon", "/Windchill/jsp/images/part.gif");
			node.put("level", link.getDepth());
			node.put("link", link.getPersistInfo().getObjectIdentifier().getStringValue());
			node.put("library", childPart.getContainerName().equalsIgnoreCase(PartHelper.LIBRARY_CONTAINER));
			loadMTree(child, node);
			jsonChildren.add(node);
		}
		rootNode.put("children", jsonChildren);
	}

	public ArrayList<MBOMLink> getMBOMLink(MBOM item) throws Exception {
		ArrayList<MBOMLink> list = new ArrayList<MBOMLink>();

		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(MBOMLink.class, true);

		SearchCondition sc = new SearchCondition(MBOMLink.class, "roleAObjectRef.key.id", "=",
				item.getPersistInfo().getObjectIdentifier().getId());
		query.appendWhere(sc, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find(query);
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			MBOMLink link = (MBOMLink) obj[0];
			list.add(link);
		}
		return list;
	}

	public MBOM getMBOM(WTPart part) throws Exception {
		return getMBOM(part, EBOMHelper.DEFAULT_COLOR);
	}

	public MBOM getMBOM(WTPart part, String color) throws Exception {
		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(MBOM.class, true);

		SearchCondition sc = new SearchCondition(MBOM.class, "partReference.key.id", "=",
				part.getPersistInfo().getObjectIdentifier().getId());
		query.appendWhere(sc, new int[] { idx });
		query.appendAnd();

		sc = new SearchCondition(MBOM.class, MBOM.VER, "=", part.getVersionIdentifier().getSeries().getValue());
		query.appendWhere(sc, new int[] { idx });
		query.appendAnd();

		sc = new SearchCondition(MBOM.class, MBOM.COLOR, "=", color);
		query.appendWhere(sc, new int[] { idx });
		QueryResult result = PersistenceHelper.manager.find(query);
		MBOM mbom = null;
		if (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			mbom = (MBOM) obj[0];
		}
		return mbom;
	}

	public List<MBOMColumns> info(Map<String, Object> params) throws Exception {
		List<MBOMColumns> data = new ArrayList<MBOMColumns>();
		String oid = (String) params.get("oid");
		MBOM header = (MBOM) CommonUtils.persistable(oid);
		ArrayList<MBOMLink> list = getAllMBOMLink(header);
		MBOMColumns dd = new MBOMColumns(header);
		data.add(dd);
		for (MBOMLink link : list) {
			MBOM mbom = link.getChild();
			MBOMColumns dto = new MBOMColumns(mbom);
			data.add(dto);
		}
		return data;
	}

	public ArrayList<MBOMLink> getAllMBOMLink(MBOM parent) throws Exception {
		ArrayList<MBOMLink> list = new ArrayList<MBOMLink>();
		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(MBOMLink.class, true);

		SearchCondition sc = null;
		sc = new SearchCondition(MBOMLink.class, "roleAObjectRef.key.id", "=",
				parent.getPersistInfo().getObjectIdentifier().getId());
		query.appendWhere(sc, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find(query);
		MBOMLink link = null;
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			link = (MBOMLink) obj[0];
			MBOM child = link.getChild();
			list.add(link);
			getAllMBOMLink(child, list);
		}
		return list;
	}

	private void getAllMBOMLink(MBOM parent, ArrayList<MBOMLink> list) throws Exception {
		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(MBOMLink.class, true);

		SearchCondition sc = null;
		sc = new SearchCondition(MBOMLink.class, "roleAObjectRef.key.id", "=",
				parent.getPersistInfo().getObjectIdentifier().getId());
		query.appendWhere(sc, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find(query);
		MBOMLink link = null;
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			link = (MBOMLink) obj[0];
			MBOM child = link.getChild();
			list.add(link);
			getAllMBOMLink(child, list);
		}
	}

	public boolean isExistLink(MBOM parent, MBOM child) throws Exception {
		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(MBOMLink.class, true);

		SearchCondition sc = new SearchCondition(MBOMLink.class, "roleAObjectRef.key.id", "=",
				parent.getPersistInfo().getObjectIdentifier().getId());
		query.appendWhere(sc, new int[] { idx });
		query.appendAnd();

		sc = new SearchCondition(MBOMLink.class, "roleBObjectRef.key.id", "=",
				child.getPersistInfo().getObjectIdentifier().getId());
		query.appendWhere(sc, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find(query);
		return result.size() > 0;
	}

	public Map<String, Object> popup(Map<String, Object> params) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<MBOMColumns> list = new ArrayList<MBOMColumns>();
		String number = (String) params.get("number");
		String ecoNumber = (String) params.get("ecoNumber");
		String erpCode = (String) params.get("erpCode");
		String creatorOid = (String) params.get("creatorOid");
		String startCreatedDate = (String) params.get("startCreatedDate");
		String endCreatedDate = (String) params.get("endCreatedDate");
		String state = (String) params.get("state");

		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(MBOM.class, true);

		WTUser manager = (WTUser) SessionHelper.manager.getPrincipal();

		SearchCondition sc = null;
		ClassAttribute ca = null;

		if (!CommonUtils.isAdmin()) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(MBOM.class, "managerReference.key.id", "=",
					manager.getPersistInfo().getObjectIdentifier().getId());
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(ecoNumber)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			int idx_link = query.appendClassList(MBOM.class, false);
			int idx_eco = query.appendClassList(ECO.class, false);

			sc = new SearchCondition(MBOM.class, "thePersistInfo.theObjectIdentifier.id", MBOMECOLink.class,
					"roleAObjectRef.key.id");
			query.appendWhere(sc, new int[] { idx, idx_link });
			query.appendAnd();

			sc = new SearchCondition(ECO.class, "thePersistInfo.theObjectIdentifier.id", MBOMECOLink.class,
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
			ca = new ClassAttribute(MBOM.class, MBOM.NUMBER);
			ColumnExpression ce = ConstantExpression.newExpression("%" + number.toUpperCase() + "%");
			SQLFunction function = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca);
			sc = new SearchCondition(function, SearchCondition.LIKE, ce);
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(state)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(MBOM.class, MBOM.STATE, "=", state);
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(startCreatedDate)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(MBOM.class, WTAttributeNameIfc.CREATE_STAMP_NAME, ">=",
					DateUtils.startTimestamp(startCreatedDate));
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(endCreatedDate)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(MBOM.class, WTAttributeNameIfc.CREATE_STAMP_NAME, "<=",
					DateUtils.endTimestamp(endCreatedDate));
			query.appendWhere(sc, new int[] { idx });
		}
		
		if (StringUtils.isNotNull(creatorOid)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			
			WTUser creator = (WTUser) CommonUtils.persistable(creatorOid);
			sc = new SearchCondition(MBOM.class, "ownership.owner.key.id", "=",
					creator.getPersistInfo().getObjectIdentifier().getId());
			query.appendWhere(sc, new int [] { idx });
		}
		
		if (query.getConditionCount() > 0) {
			query.appendAnd();
		}
		
		sc = new SearchCondition(MBOM.class, "managerReference.key.id", "<>", 0L);
		query.appendWhere(sc, new int [] { idx });
		
		ca = new ClassAttribute(MBOM.class, MBOM.CREATE_TIMESTAMP);
		OrderBy by = new OrderBy(ca, true);
		query.appendOrderBy(by, new int [] { idx });
		
		PageUtils pager = new PageUtils(params, query);
		QueryResult result = pager.find();
		int total = pager.getTotal();
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			MBOMColumns columns = new MBOMColumns((MBOM) obj[0]);
			columns.setNo(total--);
			list.add(columns);
		}
		map.put("list", list);
		map.put("topListCount", pager.getTotal());
		map.put("sessionid", pager.getSessionId());
		map.put("curPage", pager.getCpage());
		map.put("pageSize",  pager.getPsize());
		map.put("total", pager.getTotalSize());
		return map;
	}
}
