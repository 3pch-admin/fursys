package platform.echange.ecn.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import platform.echange.ecn.entity.ECN;
import platform.echange.ecn.entity.ECNColumns;
import platform.echange.ecn.entity.ECNWTDocumentLink;
import platform.util.CommonUtils;
import platform.util.DateUtils;
import platform.util.PageUtils;
import platform.util.StringUtils;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.org.WTUser;
import wt.query.ClassAttribute;
import wt.query.ColumnExpression;
import wt.query.ConstantExpression;
import wt.query.OrderBy;
import wt.query.QuerySpec;
import wt.query.SQLFunction;
import wt.query.SearchCondition;
import wt.services.ServiceFactory;
import wt.util.WTAttributeNameIfc;

public class ECNHelper {

	public static final ECNService service = ServiceFactory.getService(ECNService.class);
	public static final ECNHelper manager = new ECNHelper();

	public Map<String, Object> list(Map<String, Object> params) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<ECNColumns> list = new ArrayList<ECNColumns>();

		String number = (String) params.get("number");
		String name = (String) params.get("name");
		String notiType = (String) params.get("notiType");
		String ecnApplyTime = (String) params.get("ecnApplyTime"); // 적용시점
		String state = (String) params.get("state");
		String creatorOid = (String) params.get("creatorOid");
		String createStartDate = (String) params.get("createStartDate");
		String createEndDate = (String) params.get("createEndDate");
		String plant = (String) params.get("plant");
		String applicationDate = (String) params.get("applicationDate"); // 실제적용일
		String partOid = (String) params.get("partOid");

		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(ECN.class, true);

		SearchCondition sc = null;
		ClassAttribute ca = null;

		if (StringUtils.isNotNull(name)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			ca = new ClassAttribute(ECN.class, ECN.NAME);
			ColumnExpression ce = ConstantExpression.newExpression("%" + name.toUpperCase() + "%");
			SQLFunction function = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca);
			sc = new SearchCondition(function, SearchCondition.LIKE, ce);
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(number)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			ca = new ClassAttribute(ECN.class, ECN.NUMBER);
			ColumnExpression ce = ConstantExpression.newExpression("%" + number.toUpperCase() + "%");
			SQLFunction function = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca);
			sc = new SearchCondition(function, SearchCondition.LIKE, ce);
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(state)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(ECN.class, ECN.STATE, "=", state);
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(notiType)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(ECN.class, ECN.NOTI_TYPE, "=", notiType);
			query.appendWhere(sc, new int[] { idx });
		}
		// 작성일자
		if (StringUtils.isNotNull(createStartDate)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(ECN.class, WTAttributeNameIfc.CREATE_STAMP_NAME, ">=",
					DateUtils.startTimestamp(createStartDate));
			query.appendWhere(sc, new int[] { idx });
		}
		if (StringUtils.isNotNull(createEndDate)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(ECN.class, WTAttributeNameIfc.CREATE_STAMP_NAME, "<=",
					DateUtils.endTimestamp(createEndDate));
			query.appendWhere(sc, new int[] { idx });
		}
		// 적용시점
		if (StringUtils.isNotNull(ecnApplyTime)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(ECN.class, ECN.ECN_APPLY_TIME, "=", ecnApplyTime);
			query.appendWhere(sc, new int[] { idx });
		}
		// 실제적용일
		if (StringUtils.isNotNull(applicationDate)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(ECN.class, WTAttributeNameIfc.CREATE_STAMP_NAME, "=",
					DateUtils.startTimestamp(applicationDate));
			query.appendWhere(sc, new int[] { idx });
		}
		// 사업장
		if (StringUtils.isNotNull(plant)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(ECN.class, ECN.PLANT, "=", plant);
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(creatorOid)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			WTUser creator = (WTUser) CommonUtils.persistable(creatorOid);
			sc = new SearchCondition(ECN.class, "ownership.owner.key.id", "=",
					creator.getPersistInfo().getObjectIdentifier().getId());
			query.appendWhere(sc, new int[] { idx });
		}

		ca = new ClassAttribute(ECN.class, ECN.CREATE_TIMESTAMP);
		OrderBy by = new OrderBy(ca, true);
		query.appendOrderBy(by, new int[] { idx });

		PageUtils pager = new PageUtils(params, query);
		QueryResult result = pager.find();
		int total = pager.getTotal();
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			ECNColumns columns = new ECNColumns((ECN) obj[0]);
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
		int idx = query.appendClassList(ECN.class, true);

		SearchCondition sc = new SearchCondition(ECN.class, ECN.NUMBER, "LIKE", "ECN-" + number.toUpperCase() + "%");
		query.appendWhere(sc, new int[] { idx });

		ClassAttribute attr = new ClassAttribute(ECN.class, ECN.NUMBER);
		OrderBy orderBy = new OrderBy(attr, true);
		query.appendOrderBy(orderBy, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find(query);

		if (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			ECN ecn = (ECN) obj[0];

			String s = ecn.getNumber().substring(12);

			int ss = Integer.parseInt(s) + 1;
			DecimalFormat d = new DecimalFormat("00");
			number += d.format(ss);
		} else {
			number += "01";
		}
		return "ECN-" + number;
	}

	public ArrayList<ECNWTDocumentLink> getEcnDocLink(ECN ecn) throws Exception {
		ArrayList<ECNWTDocumentLink> list = new ArrayList<ECNWTDocumentLink>();

		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(ECNWTDocumentLink.class, true);

		SearchCondition sc = new SearchCondition(ECNWTDocumentLink.class, "roleAObjectRef.key.id", "=",
				ecn.getPersistInfo().getObjectIdentifier().getId());
		query.appendWhere(sc, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find(query);
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			ECNWTDocumentLink link = (ECNWTDocumentLink) obj[0];
			list.add(link);
		}
		return list;
	}

	public ArrayList<ECNWTDocumentLink> getDocLinks(ECN ecn) throws Exception {
		ArrayList<ECNWTDocumentLink> list = new ArrayList<ECNWTDocumentLink>();

		QueryResult result = PersistenceHelper.manager.navigate(ecn, "doc", ECNWTDocumentLink.class, false);
		while (result.hasMoreElements()) {
			ECNWTDocumentLink link = (ECNWTDocumentLink) result.nextElement();
			list.add(link);
		}

		return list;
	}

}
