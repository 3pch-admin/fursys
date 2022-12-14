package platform.department.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import platform.department.entity.Department;
import platform.department.entity.ParentChildLink;
import platform.user.entity.User;
import platform.user.entity.UserInfo;
import platform.util.CommonUtils;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.query.ClassAttribute;
import wt.query.OrderBy;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.services.ServiceFactory;
import wt.util.WTAttributeNameIfc;

public class DepartmentHelper {

	public static final DepartmentService service = ServiceFactory.getService(DepartmentService.class);
	public static final DepartmentHelper manager = new DepartmentHelper();

	public Department getRoot() throws Exception {
		Department department = null;
		try {

			QuerySpec query = new QuerySpec();
			int idx = query.appendClassList(Department.class, true);
			SearchCondition sc = new SearchCondition(Department.class, Department.CODE, "=", "0");
			query.appendWhere(sc, new int[] { idx });
			QueryResult result = PersistenceHelper.manager.find(query);
			if (result.hasMoreElements()) {
				Object[] obj = (Object[]) result.nextElement();
				department = (Department) obj[0];
			}
			if (department == null) {
				department = DepartmentHelper.service.make();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return department;
	}

	public JSONArray tree() throws Exception {
		Department root = getRoot();
		JSONArray array = new JSONArray();
		JSONObject rootNode = new JSONObject();
		rootNode.put("oid", root.getPersistInfo().getObjectIdentifier().getStringValue());
		rootNode.put("title", root.getName());
		rootNode.put("name", root.getName());
		rootNode.put("expanded", true);
		rootNode.put("folder", true);
		rootNode.put("icon", "/Windchill/jsp/images/group.gif");
		rootNode.put("type", "root");
		getChildrens(root, rootNode);
		array.add(rootNode);
		return array;
	}

	private static void getChildrens(Department root, JSONObject rootNode) throws Exception {

		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(Department.class, true);
		SearchCondition sc = new SearchCondition(Department.class, "parentReference.key.id", "=",
				root.getPersistInfo().getObjectIdentifier().getId());
		query.appendWhere(sc, new int[] { idx });
		query.appendAnd();
		sc = new SearchCondition(Department.class, Department.USES, SearchCondition.IS_TRUE, true);
		query.appendWhere(sc, new int[] { idx });

		ClassAttribute ca = new ClassAttribute(Department.class, Department.SORT);
		OrderBy orderBy = new OrderBy(ca, false);
		query.appendOrderBy(orderBy, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find(query);

		JSONArray children = new JSONArray();
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			Department child = (Department) obj[0];
			JSONObject childNode = new JSONObject();
			childNode.put("oid", child.getPersistInfo().getObjectIdentifier().getStringValue());
			childNode.put("title", child.getName());
			rootNode.put("name", root.getName());
			childNode.put("expanded", false);
			childNode.put("folder", true);
			childNode.put("type", "child");

			QueryResult rs = PersistenceHelper.manager.navigate(child, "child", ParentChildLink.class);
			if (rs.size() == 0) {
				childNode.put("icon", "/Windchill/jsp/images/user.gif");
			} else {
				childNode.put("icon", "/Windchill/jsp/images/group.gif");
			}

			getChildrens(child, childNode);
			children.add(childNode);
		}
		rootNode.put("children", children);
	}

	public ArrayList<UserInfo> deptUser(Map<String, Object> params) throws Exception {
		ArrayList<UserInfo> list = new ArrayList<UserInfo>();
		String oid = (String) params.get("oid");
		Department department = null;
		QuerySpec query = null;
		try {

			query = new QuerySpec();
			int idx = query.appendClassList(User.class, true);

			SearchCondition sc = null;
			ClassAttribute ca = null;

			if (query.getConditionCount() > 0)
				query.appendAnd();

			department = (Department) CommonUtils.persistable(oid);
			long ids = department.getPersistInfo().getObjectIdentifier().getId();

			query.appendOpenParen();

			sc = new SearchCondition(User.class, "departmentReference.key.id", "=", ids);
			query.appendWhere(sc, new int[] { idx });

			ArrayList<Department> deptList = getSubDepartment(department, new ArrayList<Department>());
			for (int i = 0; i < deptList.size(); i++) {
				Department sub = (Department) deptList.get(i);
				query.appendOr();
				long sfid = sub.getPersistInfo().getObjectIdentifier().getId();
				query.appendWhere(new SearchCondition(User.class, "departmentReference.key.id", "=", sfid),
						new int[] { idx });
			}
			query.appendCloseParen();

			ca = new ClassAttribute(User.class, User.USER_NAME);
			OrderBy by = new OrderBy(ca, false);
			query.appendOrderBy(by, new int[] { idx });

			QueryResult result = PersistenceHelper.manager.find(query);

			while (result.hasMoreElements()) {
				Object[] obj = (Object[]) result.nextElement();
				User user = (User) obj[0];
				UserInfo info = new UserInfo(user);
				list.add(info);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	public ArrayList<Department> getSubDepartment(Department dd, ArrayList<Department> departments) throws Exception {
		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(Department.class, true);

		SearchCondition sc = new SearchCondition(Department.class,
				Department.PARENT_REFERENCE + "." + WTAttributeNameIfc.REF_OBJECT_ID, "=",
				dd.getPersistInfo().getObjectIdentifier().getId());
		query.appendWhere(sc, new int[] { idx });

		query.appendAnd();
		sc = new SearchCondition(Department.class, Department.USES, SearchCondition.IS_TRUE, true);
		query.appendWhere(sc, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find(query);
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			Department sub = (Department) obj[0];
			departments.add(sub);
			getSubDepartment(sub, departments);
		}
		return departments;
	}

	public Department getDepartment(String code) throws Exception {
		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(Department.class, true);

		SearchCondition sc = new SearchCondition(Department.class, Department.CODE, "=", code);
		query.appendWhere(sc, new int[] { idx });
		query.appendAnd();
		sc = new SearchCondition(Department.class, Department.USES, SearchCondition.IS_TRUE, true);
		query.appendWhere(sc, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find(query);
		Department department = null;
		if (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			department = (Department) obj[0];
		}
		return department;
	}

	public ArrayList<Map<String, Object>> getDeptMap() throws Exception {
		ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(Department.class, true);

		SearchCondition sc = new SearchCondition(Department.class, Department.USES, SearchCondition.IS_TRUE, true);
		query.appendWhere(sc, new int[] { idx });

		ClassAttribute ca = new ClassAttribute(Department.class, Department.SORT);
		OrderBy orderBy = new OrderBy(ca, false);
		query.appendOrderBy(orderBy, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find(query);
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			Department department = (Department) obj[0];
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", department.getName());
//			map.put("oid", baseCode.getPersistInfo().getObjectIdentifier().getStringValue());
			map.put("code", department.getCode());
			list.add(map);
		}
		return list;
	}
}
