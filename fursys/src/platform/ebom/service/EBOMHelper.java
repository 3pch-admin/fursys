package platform.ebom.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import platform.ebom.entity.EBOM;
import platform.ebom.entity.EBOMColumns;
import platform.ebom.entity.EBOMLink;
import platform.part.service.PartHelper;
import platform.util.CommonUtils;
import platform.util.IBAUtils;
import platform.util.PageUtils;
import platform.util.ThumbnailUtils;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.query.ClassAttribute;
import wt.query.OrderBy;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.services.ServiceFactory;

public class EBOMHelper {

	public static final EBOMService service = ServiceFactory.getService(EBOMService.class);
	public static final EBOMHelper manager = new EBOMHelper();

	public static final String HEADER = "HEADER";
	public static final String CHILD = "CHILD";

	public static final String EBOM_CREATE = "EBOM 작성중";
	public static final String EBOM_TEMP = "EBOM 임시저장";
	public static final String EBOM_APPROVAL = "EBOM 승인됨";

	public Map<String, Object> list(Map<String, Object> params) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<EBOMColumns> list = new ArrayList<>();
		QuerySpec query = new QuerySpec();

		int idx = query.appendClassList(EBOM.class, true);

		SearchCondition sc = null;
		ClassAttribute ca = null;

		sc = new SearchCondition(EBOM.class, EBOM.BOM_TYPE, "=", HEADER);
		query.appendWhere(sc, new int[] { idx });

		ca = new ClassAttribute(EBOM.class, EBOM.CREATE_TIMESTAMP);
		OrderBy by = new OrderBy(ca, false);
		query.appendOrderBy(by, new int[] { idx });

		PageUtils pager = new PageUtils(params, query);
		QueryResult result = pager.find();
		int total = pager.getTotal();
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			EBOM ebom = (EBOM) obj[0];
			EBOMColumns columns = new EBOMColumns(ebom);
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

	public JSONArray loadTree(String oid) throws Exception {
		EBOM ebom = (EBOM) CommonUtils.persistable(oid);
		WTPartMaster m = ebom.getWtpartMaster();
		WTPart part = PartHelper.manager.getLatest(m);
		JSONArray jsonArray = new JSONArray();
		JSONObject rootNode = new JSONObject();
		rootNode.put("thumb", ThumbnailUtils.thumbnails(part)[1]);
		rootNode.put("oid", part.getPersistInfo().getObjectIdentifier().getStringValue());
		rootNode.put("partName", IBAUtils.getStringValue(part, "PART_NAME"));
		rootNode.put("number", part.getNumber());
		rootNode.put("itemName", IBAUtils.getStringValue(part, "ITEM_NAME"));
		rootNode.put("partNo", IBAUtils.getStringValue(part, "PART_NO"));
		rootNode.put("version", part.getVersionIdentifier().getSeries().getValue());
		rootNode.put("partType", PartHelper.manager.partTypeToDisplay(part));
		rootNode.put("amount", 1);
		rootNode.put("partTypeCd", IBAUtils.getStringValue(part, "PART_TYPE"));
		rootNode.put("state", part.getLifeCycleState().getDisplay());
		rootNode.put("id", UUID.randomUUID());
		rootNode.put("library", PartHelper.manager.isLibrary(part));
		JSONArray array = new JSONArray();

		ArrayList<EBOMLink> result = getLinks(ebom);
		for (EBOMLink link : result) {
			EBOM child = link.getChild();
			WTPartMaster mm = child.getWtpartMaster();
			WTPart childPart = PartHelper.manager.getLatest(mm);
			JSONObject node = new JSONObject();
			node.put("thumb", ThumbnailUtils.thumbnails(childPart)[1]);
			node.put("oid", childPart.getPersistInfo().getObjectIdentifier().getStringValue());
			node.put("partName", IBAUtils.getStringValue(childPart, "PART_NAME"));
			node.put("number", childPart.getNumber());
			node.put("itemName", IBAUtils.getStringValue(childPart, "ITEM_NAME"));
			node.put("partNo", IBAUtils.getStringValue(childPart, "PART_NO"));
			node.put("version", childPart.getVersionIdentifier().getSeries().getValue());
			node.put("partType", PartHelper.manager.partTypeToDisplay(childPart));
			node.put("partTypeCd", IBAUtils.getStringValue(childPart, "PART_TYPE"));
			node.put("amount", link.getAmount());
			node.put("state", childPart.getLifeCycleState().getDisplay());
			node.put("id", UUID.randomUUID());
			node.put("library", PartHelper.manager.isLibrary(childPart));
			loadTree(child, node);
			array.add(node);
		}
		rootNode.put("children", array);
		jsonArray.add(rootNode);
		return jsonArray;
	}

	private void loadTree(EBOM parent, JSONObject rootNode) throws Exception {
		JSONArray jsonChildren = new JSONArray();
		ArrayList<EBOMLink> result = getLinks(parent);
		for (EBOMLink link : result) {
			EBOM child = link.getChild();
			WTPartMaster mm = child.getWtpartMaster();
			WTPart childPart = PartHelper.manager.getLatest(mm);
			JSONObject node = new JSONObject();
			node.put("thumb", ThumbnailUtils.thumbnails(childPart)[1]);
			node.put("partName", IBAUtils.getStringValue(childPart, "PART_NAME"));
			node.put("number", childPart.getNumber());
			node.put("itemName", IBAUtils.getStringValue(childPart, "ITEM_NAME"));
			node.put("partNo", IBAUtils.getStringValue(childPart, "PART_NO"));
			node.put("partTypeCd", IBAUtils.getStringValue(childPart, "PART_TYPE"));
			node.put("version", childPart.getVersionIdentifier().getSeries().getValue());
			node.put("partType", PartHelper.manager.partTypeToDisplay(childPart));
			node.put("amount", link.getAmount());
			node.put("oid", childPart.getPersistInfo().getObjectIdentifier().getStringValue());
			node.put("state", childPart.getLifeCycleState().getDisplay());
			node.put("id", UUID.randomUUID());
			node.put("library", PartHelper.manager.isLibrary(childPart));
			loadTree(child, node);
			jsonChildren.add(node);
		}
		rootNode.put("children", jsonChildren);
	}

	public ArrayList<EBOM> getAllLinks(EBOM parent, ArrayList<EBOM> list) throws Exception {
		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(EBOMLink.class, true);

		SearchCondition sc = new SearchCondition(EBOMLink.class, "roleAObjectRef.key.id", "=",
				parent.getPersistInfo().getObjectIdentifier().getId());
		query.appendWhere(sc, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find(query);
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			EBOMLink link = (EBOMLink) obj[0];
			list.add(link.getChild());
			getAllLinks(link.getChild(), list);
		}
		return list;
	}

	public ArrayList<EBOMLink> getLinks(EBOM parent) throws Exception {
		ArrayList<EBOMLink> list = new ArrayList<>();

		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(EBOMLink.class, true);

		SearchCondition sc = new SearchCondition(EBOMLink.class, "roleAObjectRef.key.id", "=",
				parent.getPersistInfo().getObjectIdentifier().getId());
		query.appendWhere(sc, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find(query);
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			EBOMLink link = (EBOMLink) obj[0];
			list.add(link);
		}
		return list;
	}

}