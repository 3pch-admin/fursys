
package platform.echange.eco.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import platform.doc.entity.DocumentColumns;
import platform.echange.eco.entity.ECO;
import platform.echange.eco.entity.ECOColumns;
import platform.echange.eco.entity.ECOWTDocumentLink;
import platform.echange.eco.entity.ECOWTPartLink;
import platform.mbom.entity.MBOM;
import platform.mbom.entity.MBOMECOLink;
import platform.part.entity.PartColumns;
import platform.util.CommonUtils;
import platform.util.DateUtils;
import platform.util.PageUtils;
import platform.util.StringUtils;
import wt.doc.WTDocument;
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
import wt.util.WTAttributeNameIfc;

public class ECOHelper {

	public static final ECOService service = ServiceFactory.getService(ECOService.class);
	public static final ECOHelper manager = new ECOHelper();

	public Map<String, Object> list(Map<String, Object> params) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<ECOColumns> list = new ArrayList<>();
		String number = (String) params.get("number");
		String name = (String) params.get("name");
		String partOid = (String) params.get("partOid");
		String state = (String) params.get("state");
		String creatorOid = (String) params.get("createOid");
		String devType = (String) params.get("devType");
		String lot = (String) params.get("lot");
		String notiType = (String) params.get("notiType");
		String applyTime = (String) params.get("applyTime");
		String ecoType = (String) params.get("ecoType");
		String applicationDate = (String) params.get("applicationDate");
		String applicationEndDate = (String) params.get("applicationEndDate");
		ReferenceFactory rf = new ReferenceFactory();

		SearchCondition sc = null;
		ClassAttribute ca = null;

		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(ECO.class, true);

		if (StringUtils.isNotNull(name)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			ca = new ClassAttribute(ECO.class, ECO.NAME);
			ColumnExpression ce = ConstantExpression.newExpression("%" + name.toUpperCase() + "%");
			SQLFunction function = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca);
			sc = new SearchCondition(function, SearchCondition.LIKE, ce);
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(applyTime)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(ECO.class, ECO.APPLY_TIME, "=", applyTime);
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(number)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			ca = new ClassAttribute(ECO.class, ECO.NUMBER);
			ColumnExpression ce = ConstantExpression.newExpression("%" + number.toUpperCase() + "%");
			SQLFunction function = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca);
			sc = new SearchCondition(function, SearchCondition.LIKE, ce);
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(devType)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(ECO.class, ECO.DEV_TYPE, "=", devType);
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(notiType)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(ECO.class, ECO.NOTI_TYPE, "=", notiType);
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(lot)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(ECO.class, ECO.LOT, "=", lot);
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(ecoType)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(ECO.class, ECO.ECO_TYPE, "=", ecoType);
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(state)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(ECO.class, ECO.STATE, "=", state);
			query.appendWhere(sc, new int[] { idx });
		}
		// 예상 적용일
		if (StringUtils.isNotNull(applicationDate)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(ECO.class, WTAttributeNameIfc.CREATE_STAMP_NAME, ">=",
					DateUtils.startTimestamp(applicationDate));
			query.appendWhere(sc, new int[] { idx });
		}
		if (StringUtils.isNotNull(applicationEndDate)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(ECO.class, WTAttributeNameIfc.CREATE_STAMP_NAME, "<=",
					DateUtils.endTimestamp(applicationEndDate));
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(partOid)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			WTPart part = (WTPart) rf.getReference(partOid).getObject();
			int idx_p = query.appendClassList(WTPart.class, false);
			int idx_link = query.appendClassList(ECOWTPartLink.class, false);

			sc = new SearchCondition(ECO.class, "thePersistInfo.theObjectIdentifier.id", ECOWTPartLink.class,
					"roleAObjectRef.key.id");
			query.appendWhere(sc, new int[] { idx, idx_p });
			query.appendAnd();

			sc = new SearchCondition(WTPart.class, "thePersistInfo.theObjectIdentifier.id", ECOWTPartLink.class,
					"roleBObjectRef.key.id");
			query.appendWhere(sc, new int[] { idx_link, idx_p });
			query.appendAnd();

			sc = new SearchCondition(ECOWTPartLink.class, "roleBObjectRef.key.id", "=",
					part.getPersistInfo().getObjectIdentifier().getId());
			query.appendWhere(sc, new int[] { idx_p });
		}

		if (StringUtils.isNotNull(creatorOid)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}

			WTUser creator = (WTUser) rf.getReference(creatorOid).getObject();
			sc = new SearchCondition(ECO.class, "ownership.owner.key.id", "=",
					creator.getPersistInfo().getObjectIdentifier().getId());
			query.appendWhere(sc, new int[] { idx });
		}

		ca = new ClassAttribute(ECO.class, ECO.CREATE_TIMESTAMP);
		OrderBy by = new OrderBy(ca, true);
		query.appendOrderBy(by, new int[] { idx });

		PageUtils pager = new PageUtils(params, query);
		QueryResult result = pager.find();
		int total = pager.getTotal();
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			ECOColumns columns = new ECOColumns((ECO) obj[0]);
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
		String number = df.format(year) + df.format(month) + df.format(day);

		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(ECO.class, true);

		SearchCondition sc = new SearchCondition(ECO.class, ECO.NUMBER, "LIKE", "ECO-" + number.toUpperCase() + "%");
		query.appendWhere(sc, new int[] { idx });

		ClassAttribute attr = new ClassAttribute(ECO.class, ECO.NUMBER);
		OrderBy orderBy = new OrderBy(attr, true);
		query.appendOrderBy(orderBy, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find(query);
		if (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			ECO eco = (ECO) obj[0];

			String s = eco.getNumber().substring(12);

			int ss = Integer.parseInt(s) + 1;
			DecimalFormat d = new DecimalFormat("00");
			number += d.format(ss);
		} else {
			number += "01";
		}
		return "ECO-" + number;
	}

	public ArrayList<ECOWTPartLink> getPartLinks(ECO eco) throws Exception {
		ArrayList<ECOWTPartLink> list = new ArrayList<ECOWTPartLink>();

		QueryResult result = PersistenceHelper.manager.navigate(eco, "part", ECOWTPartLink.class, false);
		while (result.hasMoreElements()) {
			ECOWTPartLink link = (ECOWTPartLink) result.nextElement();
			list.add(link);
		}

		return list;
	}

	public ArrayList<ECOWTDocumentLink> getDocLinks(ECO eco) throws Exception {
		ArrayList<ECOWTDocumentLink> list = new ArrayList<ECOWTDocumentLink>();

		QueryResult result = PersistenceHelper.manager.navigate(eco, "doc", ECOWTDocumentLink.class, false);
		while (result.hasMoreElements()) {
			ECOWTDocumentLink link = (ECOWTDocumentLink) result.nextElement();
			list.add(link);
		}

		return list;
	}

	public ECO getRefECO(WTPart part) throws Exception {
		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(ECOWTPartLink.class, true);
		int idx_p = query.appendClassList(WTPart.class, false);

		SearchCondition sc = null;

		sc = new SearchCondition(ECOWTPartLink.class, "roleBObjectRef.key.id", WTPart.class,
				"thePersistInfo.theObjectIdentifier.id");
		query.appendWhere(sc, new int[] { idx, idx_p });
		query.appendAnd();

		sc = new SearchCondition(ECOWTPartLink.class, "roleBObjectRef.key.id", "=",
				part.getPersistInfo().getObjectIdentifier().getId());
		query.appendWhere(sc, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find(query);
		if (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			ECOWTPartLink link = (ECOWTPartLink) obj[0];
			return link.getEco();
		}
		return null;
	}

	public List<PartColumns> info(Map<String, Object> params) throws Exception {
		ArrayList<String> list = (ArrayList<String>) params.get("list");
		ArrayList<PartColumns> data = new ArrayList<PartColumns>();
		try {
			for (String oid : list) {
				ECO eco = (ECO) CommonUtils.persistable(oid);
				QueryResult result = PersistenceHelper.manager.navigate(eco, "mbom", MBOMECOLink.class);
				System.out.println("re=" + result.size());
				while (result.hasMoreElements()) {
					MBOM mbom = (MBOM) result.nextElement();
					WTPart part = mbom.getPart();
					data.add(new PartColumns(part));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return data;
	}

	public ArrayList<PartColumns> getParts(ECO eco) throws Exception {
		ArrayList<PartColumns> list = new ArrayList<PartColumns>();
		QueryResult result = PersistenceHelper.manager.navigate(eco, "part", ECOWTPartLink.class, false);
		while (result.hasMoreElements()) {
			ECOWTPartLink link = (ECOWTPartLink) result.nextElement();
			WTPart part = link.getPart();
			PartColumns data = new PartColumns(part);
			list.add(data);
		}
		return list;
	}

	public ArrayList<DocumentColumns> getDocs(ECO eco) throws Exception {
		ArrayList<DocumentColumns> list = new ArrayList<DocumentColumns>();
		QueryResult result = PersistenceHelper.manager.navigate(eco, "doc", ECOWTDocumentLink.class, false);
		while (result.hasMoreElements()) {
			ECOWTDocumentLink link = (ECOWTDocumentLink) result.nextElement();
			WTDocument doc = link.getDoc();
			DocumentColumns data = new DocumentColumns(doc);
			list.add(data);
		}
		return list;
	}
}
