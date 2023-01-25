package platform.dist.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import platform.code.service.BaseCodeHelper;
import platform.dist.entity.Distributor;
import platform.dist.entity.DistributorColumns;
import platform.dist.entity.DistributorUser;
import platform.dist.entity.DistributorUserColumns;
import platform.user.entity.User;
import platform.util.CommonUtils;
import platform.util.DateUtils;
import platform.util.PageUtils;
import platform.util.StringUtils;
import platform.util.entity.CPCHistory;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.org.WTUser;
import wt.query.ClassAttribute;
import wt.query.ColumnExpression;
import wt.query.ConstantExpression;
import wt.query.OrderBy;
import wt.query.QuerySpec;
import wt.query.SQLFunction;
import wt.query.SearchCondition;
import wt.services.ServiceFactory;

public class DistributorHelper {
	public static DistributorService service = ServiceFactory.getService(DistributorService.class);
	public static DistributorHelper manager = new DistributorHelper();

	public Map<String, Object> list(Map<String, Object> params) throws Exception {
		List<DistributorColumns> list = new ArrayList<>();
		Map<String, Object> map = new HashMap<String, Object>();

		String name = (String) params.get("name");
		String type = (String) params.get("type");
		String userId = (String) params.get("userId");
		String userName = (String) params.get("userName");
		String email = (String) params.get("email");
		String enable = (String) params.get("enable");
		String creator = (String) params.get("creatorOid");
		String startCreatedDate = (String) params.get("startCreatedDate");
		String endCreatedDate = (String) params.get("endCreatedDate");
		ReferenceFactory rf = new ReferenceFactory();

		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(Distributor.class, true);
		SearchCondition sc = null;
		ClassAttribute ca = null;

		if (StringUtils.isNotNull(name)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			ca = new ClassAttribute(Distributor.class, Distributor.NAME);
			ColumnExpression ce = ConstantExpression.newExpression("%" + name.toUpperCase() + "%");
			SQLFunction function = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca);
			sc = new SearchCondition(function, SearchCondition.LIKE, ce);
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(userName)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			ca = new ClassAttribute(Distributor.class, Distributor.USER_NAME);
			ColumnExpression ce = ConstantExpression.newExpression("%" + userName.toUpperCase() + "%");
			SQLFunction function = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca);
			sc = new SearchCondition(function, SearchCondition.LIKE, ce);
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(type)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(Distributor.class, Distributor.TYPE, "=", type);
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(userId)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			ca = new ClassAttribute(Distributor.class, Distributor.USER_ID);
			ColumnExpression ce = ConstantExpression.newExpression("%" + userId.toUpperCase() + "%");
			SQLFunction function = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca);
			sc = new SearchCondition(function, SearchCondition.LIKE, ce);
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(email)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			ca = new ClassAttribute(Distributor.class, Distributor.EMAIL);
			ColumnExpression ce = ConstantExpression.newExpression("%" + email.toUpperCase() + "%");
			SQLFunction function = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca);
			sc = new SearchCondition(function, SearchCondition.LIKE, ce);
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(enable)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			if (Boolean.parseBoolean(enable)) {
				sc = new SearchCondition(Distributor.class, Distributor.ENABLE, SearchCondition.IS_TRUE);
				query.appendWhere(sc, new int[] { idx });
			} else if (!Boolean.parseBoolean(enable)) {
				sc = new SearchCondition(Distributor.class, Distributor.ENABLE, SearchCondition.IS_FALSE);
				query.appendWhere(sc, new int[] { idx });
			}
		}
//		else {
//			if (query.getConditionCount() > 0) {
//				query.appendAnd();
//			}
//			sc = new SearchCondition(Distributor.class, Distributor.ENABLE, SearchCondition.IS_TRUE);
//			query.appendWhere(sc, new int[] { idx });
//		}

		if (StringUtils.isNotNull(creator)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			WTUser creatorO = (WTUser) CommonUtils.persistable(creator);
			sc = new SearchCondition(Distributor.class, "ownership.owner.key.id", "=",
					creatorO.getPersistInfo().getObjectIdentifier().getId());
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(startCreatedDate)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(Distributor.class, Distributor.CREATE_TIMESTAMP, ">=",
					DateUtils.startTimestamp(startCreatedDate));
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(endCreatedDate)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(Distributor.class, Distributor.CREATE_TIMESTAMP, "<=",
					DateUtils.startTimestamp(endCreatedDate));
			query.appendWhere(sc, new int[] { idx });
		}

		ca = new ClassAttribute(Distributor.class, Distributor.CREATE_TIMESTAMP);
		OrderBy by = new OrderBy(ca, true);
		query.appendOrderBy(by, new int[] { idx });

//		QueryResult result = PersistenceHelper.manager.find(query);
		PageUtils pager = new PageUtils(params, query);
		QueryResult result = pager.find();
		int total = pager.getTotal();
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			DistributorColumns columns = new DistributorColumns((Distributor) obj[0]);
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

	public Map<String, Object> userList(Map<String, Object> params) throws Exception {
		List<DistributorUserColumns> list = new ArrayList<>();
		Map<String, Object> map = new HashMap<String, Object>();

		String name = (String) params.get("name");
		String type = (String) params.get("type");
		String userId = (String) params.get("userId");
		String userName = (String) params.get("userName");
		String email = (String) params.get("email");
		String enable = (String) params.get("enable");

		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(DistributorUser.class, true);
		SearchCondition sc = null;
		ClassAttribute ca = null;

		if (StringUtils.isNotNull(name)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			ca = new ClassAttribute(DistributorUser.class, DistributorUser.NAME);
			ColumnExpression ce = ConstantExpression.newExpression("%" + name.toUpperCase() + "%");
			SQLFunction function = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca);
			sc = new SearchCondition(function, SearchCondition.LIKE, ce);
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(userName)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			ca = new ClassAttribute(DistributorUser.class, DistributorUser.USER_NAME);
			ColumnExpression ce = ConstantExpression.newExpression("%" + userName.toUpperCase() + "%");
			SQLFunction function = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca);
			sc = new SearchCondition(function, SearchCondition.LIKE, ce);
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(type)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(DistributorUser.class, DistributorUser.TYPE, "=", type);
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(userId)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			ca = new ClassAttribute(DistributorUser.class, DistributorUser.USER_ID);
			ColumnExpression ce = ConstantExpression.newExpression("%" + userId.toUpperCase() + "%");
			SQLFunction function = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca);
			sc = new SearchCondition(function, SearchCondition.LIKE, ce);
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(email)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			ca = new ClassAttribute(DistributorUser.class, DistributorUser.EMAIL);
			ColumnExpression ce = ConstantExpression.newExpression("%" + email.toUpperCase() + "%");
			SQLFunction function = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca);
			sc = new SearchCondition(function, SearchCondition.LIKE, ce);
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(enable)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			if (Boolean.parseBoolean(enable)) {
				sc = new SearchCondition(DistributorUser.class, DistributorUser.ENABLE, SearchCondition.IS_TRUE);
				query.appendWhere(sc, new int[] { idx });
			} else if (!Boolean.parseBoolean(enable)) {
				sc = new SearchCondition(DistributorUser.class, DistributorUser.ENABLE, SearchCondition.IS_FALSE);
				query.appendWhere(sc, new int[] { idx });
			}
		}
		/*
		 * else { if (query.getConditionCount() > 0) { query.appendAnd(); } sc = new
		 * SearchCondition(DistributorUser.class, DistributorUser.ENABLE,
		 * SearchCondition.IS_TRUE); query.appendWhere(sc, new int[] { idx }); }
		 */

		ca = new ClassAttribute(DistributorUser.class, DistributorUser.CREATE_TIMESTAMP);
		OrderBy by = new OrderBy(ca, true);
		query.appendOrderBy(by, new int[] { idx });

//		QueryResult result = PersistenceHelper.manager.find(query);
		PageUtils pager = new PageUtils(params, query);
		QueryResult result = pager.find();
		int total = pager.getTotal();
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			DistributorUserColumns columns = new DistributorUserColumns((DistributorUser) obj[0]);
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
		int month = ca.get(Calendar.MONTH) + 1;
		int year = ca.get(Calendar.YEAR);
		DecimalFormat df4 = new DecimalFormat("0000");
		DecimalFormat df2 = new DecimalFormat("00");
		String number = df4.format(year) + "-" + df2.format(month) + "-";

		System.out.println("### getNextNumber=="+number);
		
		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(Distributor.class, true);

		SearchCondition sc = new SearchCondition(Distributor.class, Distributor.NUMBER, SearchCondition.LIKE, 
				"SU-" + number.toUpperCase() + "%");
		query.appendWhere(sc, new int[] { idx });

		ClassAttribute attr = new ClassAttribute(Distributor.class, Distributor.NUMBER);
		OrderBy orderBy = new OrderBy(attr, true);
		query.appendOrderBy(orderBy, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find(query);

		//SU-2023-01-00001
		
		
		if (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			Distributor distributor = (Distributor) obj[0];

			String s = distributor.getNumber().split("-")[3];
			System.out.println("### s=="+s);
			int ss = Integer.parseInt(s) + 1;
			DecimalFormat df5 = new DecimalFormat("00000");
			number += df5.format(ss);
			System.out.println("### ss=="+ss);
		} else {
			number += "00001";
		}

		return "SU-" + number;
	}

	public ArrayList<Map<String, Object>> getDistributor() throws Exception {
		ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(Distributor.class, true);

		SearchCondition sc = new SearchCondition(Distributor.class, Distributor.ENABLE, SearchCondition.IS_TRUE);
		query.appendWhere(sc, new int[] { idx });

		ClassAttribute ca = new ClassAttribute(Distributor.class, Distributor.NAME);
		OrderBy by = new OrderBy(ca, true);
		query.appendOrderBy(by, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find(query);

		Map<String, Object> empty = new HashMap<String, Object>();

		empty.put("name", "");
		empty.put("oid", "");
		list.add(empty);

		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			Distributor distributor = (Distributor) obj[0];
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", distributor.getName());
			map.put("oid", distributor.getPersistInfo().getObjectIdentifier().getStringValue());
			list.add(map);
		}

		return list;
	}
	
	public Distributor getDistributor(String plantCode) throws Exception {
		Distributor reValue = null;
		
		if( plantCode != null && plantCode.length() > 0 ) {
		
			QuerySpec query = new QuerySpec();
			int idx = query.appendClassList(Distributor.class, true);
	
			SearchCondition sc = new SearchCondition(Distributor.class, Distributor.NUMBER, "=", plantCode);
			query.appendWhere(sc, new int[] { idx });
	
			QueryResult result = PersistenceHelper.manager.find(query);
	
			while (result.hasMoreElements()) {
				Object[] obj = (Object[]) result.nextElement();
				Distributor distributor = (Distributor) obj[0];
				return distributor;
			}
		}

		return reValue;
	}

	public ArrayList<Map<String, Object>> getDistributorUser() throws Exception {
		ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(DistributorUser.class, true);

		SearchCondition sc = new SearchCondition(DistributorUser.class, Distributor.ENABLE, SearchCondition.IS_TRUE);
		query.appendWhere(sc, new int[] { idx });

		ClassAttribute ca = new ClassAttribute(DistributorUser.class, Distributor.NAME);
		OrderBy by = new OrderBy(ca, true);
		query.appendOrderBy(by, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find(query);

		Map<String, Object> empty = new HashMap<String, Object>();

		empty.put("name", "");
		empty.put("oid", "");
		list.add(empty);

		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			DistributorUser distributorUser = (DistributorUser) obj[0];
			Map<String, Object> map = new HashMap<String, Object>();

			map.put("name", distributorUser.getName());
			map.put("type", distributorUser.getType());
			map.put("userName", distributorUser.getUserName());
			map.put("oid", distributorUser.getPersistInfo().getObjectIdentifier().getStringValue());
			list.add(map);
		}

		return list;
	}

	public Map<String, Object> info(Map<String, Object> params) throws Exception {
		String oid = (String) params.get("oid");
		System.out.println("doid: " + oid);
		Distributor distributor = (Distributor) CommonUtils.persistable(oid);

		Map<String, Object> result = new HashMap<>();
		result.put("type", "OUT".equals(distributor.getType()) == true ? "사외" : "사내");
		result.put("userName", distributor.getUserName());
		result.put("email", distributor.getEmail());
		result.put("name", distributor.getName());
		return result;
	}

	public ArrayList<Map<String, Object>> userInfo(Map<String, Object> params) throws Exception {
		ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		ArrayList<String> list = (ArrayList<String>) params.get("list");
		for (String oid : list) {
			DistributorUser user = (DistributorUser) CommonUtils.persistable(oid);

			Map<String, Object> result = new HashMap<>();
			result.put("type", "OUT".equals(user.getType()) == true ? "사외" : "사내");
			result.put("userName", user.getUserName());
			result.put("email", user.getEmail());

			if ("IN".equals(user.getType())) {
				result.put("name", BaseCodeHelper.manager.getNameByCodeTypeAndCode("FACTORY_CODE", user.getName()));
			} else {
				result.put("name", user.getName());
				if (user.getDistributor() != null) {
					result.put("name", user.getDistributor().getName());
				}
			}

			result.put("uoid", CommonUtils.oid(user));
			data.add(result);
		}
		return data;
	}

	public List<Map<String, Object>> getDistributorList(Map<String, Object> params) throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String key = (String) params.get("key");
		try {
			QuerySpec query = new QuerySpec();
			int idx = query.appendClassList(Distributor.class, true);

			SQLFunction function = null;
			ColumnExpression ce = null;
			SearchCondition sc = null;
			ClassAttribute ca = null;
			if (StringUtils.isNotNull(key)) {

				ca = new ClassAttribute(Distributor.class, Distributor.USER_NAME);
				ce = ConstantExpression.newExpression("%" + key.toUpperCase() + "%");
				function = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca);
				sc = new SearchCondition(function, SearchCondition.LIKE, ce);
				query.appendWhere(sc, new int[] { idx });

			}

			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}

			query.appendWhere(new SearchCondition(Distributor.class, Distributor.TYPE, "=", "OUT"), new int[] { idx });
			query.appendAnd();
			query.appendWhere(new SearchCondition(Distributor.class, Distributor.ENABLE, SearchCondition.IS_TRUE),
					new int[] { idx });

			ca = new ClassAttribute(Distributor.class, User.USER_NAME);
			OrderBy by = new OrderBy(ca, false);
			query.appendOrderBy(by, new int[] { idx });
			QueryResult qr = PersistenceHelper.manager.find(query);
			while (qr.hasMoreElements()) {
				Object[] obj = (Object[]) qr.nextElement();
				Distributor dist = (Distributor) obj[0];
				Map<String, Object> result = new HashMap<String, Object>();
				result.put("name", dist.getName());
				result.put("value", dist.getPersistInfo().getObjectIdentifier().getStringValue());
				list.add(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}
	
	public ArrayList<DistributorUser> getDistributorUser(Distributor di) throws Exception {
		ArrayList<DistributorUser> list = new ArrayList<DistributorUser>();

		QuerySpec qs = new QuerySpec();
		int idx = qs.appendClassList(DistributorUser.class, true);

		qs.appendWhere(new SearchCondition(DistributorUser.class, Distributor.ENABLE, SearchCondition.IS_TRUE), new int[] { idx });
		
		qs.appendAnd();
		
		qs.appendWhere(new SearchCondition(DistributorUser.class, "distributorReference.key.id", "=", CommonUtils.longValue(di)), new int[] { idx });

		ClassAttribute ca = new ClassAttribute(DistributorUser.class, Distributor.NAME);
		OrderBy by = new OrderBy(ca, true);
		qs.appendOrderBy(by, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find(qs);

		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			DistributorUser distributorUser = (DistributorUser) obj[0];

			list.add(distributorUser);
		}

		return list;
	}
	
	public ArrayList<DistributorUserColumns> getDistributorUserColumns2(Distributor di) throws Exception {
		ArrayList<DistributorUserColumns> list = new ArrayList<DistributorUserColumns>();

		QuerySpec qs = new QuerySpec();
		int idx = qs.appendClassList(DistributorUser.class, true);

		qs.appendWhere(new SearchCondition(DistributorUser.class, Distributor.ENABLE, SearchCondition.IS_TRUE), new int[] { idx });
		
		qs.appendAnd();
		
		qs.appendWhere(new SearchCondition(DistributorUser.class, "distributorReference.key.id", "=", CommonUtils.longValue(di)), new int[] { idx });

		ClassAttribute ca = new ClassAttribute(DistributorUser.class, Distributor.NAME);
		OrderBy by = new OrderBy(ca, true);
		qs.appendOrderBy(by, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find(qs);

		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			DistributorUser distributorUser = (DistributorUser) obj[0];

			list.add(new DistributorUserColumns( distributorUser));
		}

		return list;
	}
	
	public ArrayList<Map<String, Object>> getDistributorUserColumns(ArrayList<String> distributorList) throws Exception {
		ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

		for (String oid : distributorList) {
		
			Distributor di = (Distributor)CommonUtils.persistable(oid);
		
			QuerySpec qs = new QuerySpec();
			int idx = qs.appendClassList(DistributorUser.class, true);
	
			qs.appendWhere(new SearchCondition(DistributorUser.class, Distributor.ENABLE, SearchCondition.IS_TRUE), new int[] { idx });
			
			qs.appendAnd();
			
			qs.appendWhere(new SearchCondition(DistributorUser.class, "distributorReference.key.id", "=", CommonUtils.longValue(di)), new int[] { idx });
	
			ClassAttribute ca = new ClassAttribute(DistributorUser.class, Distributor.NAME);
			OrderBy by = new OrderBy(ca, true);
			qs.appendOrderBy(by, new int[] { idx });
	
			QueryResult qr = PersistenceHelper.manager.find(qs);
	
			while (qr.hasMoreElements()) {
				Object[] obj = (Object[]) qr.nextElement();
				DistributorUser user = (DistributorUser) obj[0];
	
				Map<String, Object> result = new HashMap<>();
				result.put("type", "OUT".equals(user.getType()) == true ? "사외" : "사내");
				result.put("userName", user.getUserName());
				result.put("email", user.getEmail());
	
				if ("IN".equals(user.getType())) {
					result.put("name", BaseCodeHelper.manager.getNameByCodeTypeAndCode("FACTORY_CODE", user.getName()));
				} else {
					result.put("name", user.getName());
					if (user.getDistributor() != null) {
						result.put("name", user.getDistributor().getName());
					}
				}
	
				result.put("uoid", CommonUtils.oid(user));
				data.add(result);
			}
		}
		return data;
	}
	
	public ArrayList<DistributorUser> getDistributorUser(String diOid) throws Exception {
		Distributor di = (Distributor)CommonUtils.persistable(diOid);
		return getDistributorUser(di);
	}
	
	public ArrayList<CPCHistory> getCPCHistory(String oid) throws Exception {
		ArrayList<CPCHistory> list = new ArrayList<CPCHistory>();

		//select * from cpchis where targetoid='oid';
		
		
		QuerySpec qs = new QuerySpec();
		int idx = qs.appendClassList(CPCHistory.class, true);
		
		qs.appendWhere(new SearchCondition(CPCHistory.class, CPCHistory.TARGET_OID, "=", oid ), new int[] { idx });

		ClassAttribute ca = new ClassAttribute(CPCHistory.class, Distributor.CREATE_TIMESTAMP);
		OrderBy by = new OrderBy(ca, true);
		qs.appendOrderBy(by, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find(qs);

		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			CPCHistory cpcHistory = (CPCHistory) obj[0];

			list.add(cpcHistory);
		}

		return list;
	}
	
}