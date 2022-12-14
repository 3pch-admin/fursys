package platform.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import platform.department.entity.Department;
import platform.user.entity.User;
import platform.user.entity.UserColumns;
import platform.user.entity.UserInfo;
import platform.util.CommonUtils;
import platform.util.PageUtils;
import platform.util.StringUtils;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.query.ClassAttribute;
import wt.query.ColumnExpression;
import wt.query.ConstantExpression;
import wt.query.OrderBy;
import wt.query.QuerySpec;
import wt.query.SQLFunction;
import wt.query.SearchCondition;
import wt.services.ServiceFactory;

public class UserHelper {

	public static final UserService service = ServiceFactory.getService(UserService.class);
	public static final UserHelper manager = new UserHelper();

	public List<Map<String, Object>> user(Map<String, Object> params) throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String key = (String) params.get("key");
		try {
			QuerySpec query = new QuerySpec();
			int idx = query.appendClassList(User.class, true);

			SQLFunction function = null;
			ColumnExpression ce = null;
			SearchCondition sc = null;
			ClassAttribute ca = null;
			if (StringUtils.isNotNull(key)) {
				query.appendOpenParen();
				ca = new ClassAttribute(User.class, User.USER_NAME);
				ce = ConstantExpression.newExpression("%" + key.toUpperCase() + "%");
				function = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca);
				sc = new SearchCondition(function, SearchCondition.LIKE, ce);
				query.appendWhere(sc, new int[] { idx });
				query.appendOr();

				ca = new ClassAttribute(User.class, User.USER_ID);
				ce = ConstantExpression.newExpression("%" + key.toUpperCase() + "%");
				function = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca);
				sc = new SearchCondition(function, SearchCondition.LIKE, ce);
				query.appendWhere(sc, new int[] { idx });
				query.appendCloseParen();
			}

			ca = new ClassAttribute(User.class, User.USER_NAME);
			OrderBy by = new OrderBy(ca, false);
			query.appendOrderBy(by, new int[] { idx });
			QueryResult qr = PersistenceHelper.manager.find(query);
			while (qr.hasMoreElements()) {
				Object[] obj = (Object[]) qr.nextElement();
				User user = (User) obj[0];
				Map<String, Object> result = new HashMap<String, Object>();
				result.put("name", user.getUserName()); // 부서명 추가
				result.put("value", user.getWtUser().getPersistInfo().getObjectIdentifier().getStringValue());
				list.add(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	public List<UserInfo> info(Map<String, Object> params) throws Exception {
		ArrayList<UserInfo> list = new ArrayList<UserInfo>();
		String key = (String) params.get("key");
		try {

			QuerySpec query = new QuerySpec();
			int idx = query.appendClassList(User.class, true);

			SearchCondition sc = null;
			ClassAttribute ca = null;
			ColumnExpression ce = null;
			SQLFunction function = null;
			if (StringUtils.isNotNull(key)) {
				ca = new ClassAttribute(User.class, User.USER_NAME);
				ce = ConstantExpression.newExpression("%" + key.toUpperCase() + "%");
				function = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca);
				sc = new SearchCondition(function, SearchCondition.LIKE, ce);
				query.appendWhere(sc, new int[] { idx });

				query.appendOr();

				ca = new ClassAttribute(User.class, User.USER_ID);
				ce = ConstantExpression.newExpression("%" + key.toUpperCase() + "%");
				function = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca);
				sc = new SearchCondition(function, SearchCondition.LIKE, ce);
				query.appendWhere(sc, new int[] { idx });
			}

			ca = new ClassAttribute(User.class, User.USER_NAME);
			OrderBy by = new OrderBy(ca, false);
			query.appendOrderBy(by, new int[] { idx });
			QueryResult result = PersistenceHelper.manager.find(query);
			while (result.hasMoreElements()) {
				Object[] obj = (Object[]) result.nextElement();
				User User = (User) obj[0];
				UserInfo info = new UserInfo(User);
				list.add(info);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	public UserInfo info(String oid) throws Exception {
		User user = null;
		UserInfo userInfo = null;
		try {

			user = (User) CommonUtils.persistable(oid);
			userInfo = new UserInfo(user);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return userInfo;
	}

	public Map<String, Object> list(Map<String, Object> params) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<UserColumns> list = new ArrayList<UserColumns>();
		String name = (String) params.get("name");
		String username = (String) params.get("username");
		String deptOid = (String) params.get("deptOid");

		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(User.class, true);

		if (StringUtils.isNotNull(name)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			ClassAttribute ca = new ClassAttribute(User.class, User.USER_NAME);
			ColumnExpression ce = ConstantExpression.newExpression("%" + name.toUpperCase() + "%");
			SQLFunction function = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca);
			SearchCondition sc = new SearchCondition(function, SearchCondition.LIKE, ce);
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(username)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			ClassAttribute ca = new ClassAttribute(User.class, User.USER_ID);
			ColumnExpression ce = ConstantExpression.newExpression("%" + username.toUpperCase() + "%");
			SQLFunction function = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca);
			SearchCondition sc = new SearchCondition(function, SearchCondition.LIKE, ce);
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(deptOid)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			Department department = (Department) CommonUtils.persistable(deptOid);
			SearchCondition sc = new SearchCondition(User.class, "departmentReference.key.id", "=",
					department.getPersistInfo().getObjectIdentifier().getId());
			query.appendWhere(sc, new int[] { idx });
		}

		ClassAttribute ca = new ClassAttribute(User.class, User.USER_NAME);
		OrderBy orderBy = new OrderBy(ca, false);
		query.appendOrderBy(orderBy, new int[] { idx });
		PageUtils pager = new PageUtils(params, query);
		QueryResult result = pager.find();
		int total = pager.getTotal();
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			UserColumns columns = new UserColumns((User) obj[0]);
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

	public ArrayList<Map<String, Object>> gridToUser() throws Exception {
		ArrayList<Map<String, Object>> list = new ArrayList<>();

		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(User.class, true);

		ClassAttribute ca = new ClassAttribute(User.class, User.USER_NAME);
		OrderBy orderBy = new OrderBy(ca, false);
		query.appendOrderBy(orderBy, new int[] { idx });
		QueryResult result = PersistenceHelper.manager.find(query);
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			User u = (User) obj[0];
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("oid", u.getWtUser().getPersistInfo().getObjectIdentifier().getStringValue());
			data.put("name", u.getUserName());
			list.add(data);
		}
		return list;
	}
}
