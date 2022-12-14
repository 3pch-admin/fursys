package platform.project.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import platform.code.entity.BaseCode;
import platform.project.entity.Project;
import platform.project.entity.ProjectColumns;
import platform.project.task.entity.Task;
import platform.project.task.entity.TaskRoleLink;
import platform.project.task.service.TaskHelper;
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
import wt.session.SessionHelper;
import wt.util.WTAttributeNameIfc;

public class ProjectHelper {

	public static final ProjectService service = ServiceFactory.getService(ProjectService.class);
	public static final ProjectHelper manager = new ProjectHelper();

	public Map<String, Object> list(Map<String, Object> params) throws Exception {
		List<ProjectColumns> list = new ArrayList<>();
		Map<String, Object> map = new HashMap<String, Object>();

		String name = (String) params.get("name");
		String number = (String) params.get("number");
		String pm = (String) params.get("project_manager");
		String state = (String) params.get("state");
		String company = (String) params.get("company");
		String department = (String) params.get("department");
		String startProjectStartDate = (String) params.get("projectStartDate");
		String endProjectStartDate = (String) params.get("projectEndDate");
		String startProjectEndDate = (String) params.get("startProjectEndDate");
		String endProjectEndDate = (String) params.get("endProjectEndDate");

		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(Project.class, true);

		SearchCondition sc = null;
		ClassAttribute ca = null;

		if (StringUtils.isNotNull(name)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			ca = new ClassAttribute(Project.class, Project.NAME);
			ColumnExpression ce = ConstantExpression.newExpression("%" + name.toUpperCase() + "%");
			SQLFunction function = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca);
			sc = new SearchCondition(function, SearchCondition.LIKE, ce);
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(number)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			ca = new ClassAttribute(Project.class, Project.NUMBER);
			ColumnExpression ce = ConstantExpression.newExpression("%" + number.toUpperCase() + "%");
			SQLFunction function = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca);
			sc = new SearchCondition(function, SearchCondition.LIKE, ce);
			query.appendWhere(sc, new int[] { idx });
		}
		// 진행상태
		if (StringUtils.isNotNull(state)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(Project.class, Project.STATE, "=", state);
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(startProjectStartDate)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(Project.class, WTAttributeNameIfc.CREATE_STAMP_NAME, ">=",
					DateUtils.startTimestamp(startProjectStartDate));
			query.appendWhere(sc, new int[] { idx });
		}
		if (StringUtils.isNotNull(endProjectStartDate)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(Project.class, WTAttributeNameIfc.CREATE_STAMP_NAME, "<=",
					DateUtils.startTimestamp(endProjectStartDate));
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(startProjectEndDate)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(Project.class, WTAttributeNameIfc.CREATE_STAMP_NAME, ">=",
					DateUtils.startTimestamp(startProjectEndDate));
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(endProjectEndDate)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(Project.class, WTAttributeNameIfc.CREATE_STAMP_NAME, "<=",
					DateUtils.startTimestamp(endProjectEndDate));
			query.appendWhere(sc, new int[] { idx });
		}

		ca = new ClassAttribute(Project.class, Project.CREATE_TIMESTAMP);
		OrderBy by = new OrderBy(ca, true);
		query.appendOrderBy(by, new int[] { idx });

//		QueryResult result = PersistenceHelper.manager.find(query);
		PageUtils pager = new PageUtils(params, query);
		QueryResult result = pager.find();
		int total = pager.getTotal();
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			ProjectColumns columns = new ProjectColumns((Project) obj[0]);
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
		int idx = query.appendClassList(Project.class, true);

		SearchCondition sc = new SearchCondition(Project.class, Project.NUMBER, "LIKE",
				"PJT-" + number.toUpperCase() + "%");
		query.appendWhere(sc, new int[] { idx });

		ClassAttribute attr = new ClassAttribute(Project.class, Project.NUMBER);
		OrderBy orderBy = new OrderBy(attr, true);
		query.appendOrderBy(orderBy, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find(query);

		if (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			Project project = (Project) obj[0];

			String s = project.getNumber().substring(12);

			int ss = Integer.parseInt(s) + 1;
			DecimalFormat d = new DecimalFormat("00");
			number += d.format(ss);
		} else {
			number += "01";
		}
		return "PJT-" + number;
	}

	public JSONArray load(String oid) throws Exception {
		Project project = (Project) CommonUtils.persistable(oid);
		JSONArray jsonArray = new JSONArray();
		JSONObject rootNode = new JSONObject();
		rootNode.put("oid", project.getPersistInfo().getObjectIdentifier().getStringValue());
		rootNode.put("id", project.getPersistInfo().getObjectIdentifier().getId());
		rootNode.put("name", project.getName());
		rootNode.put("description", project.getDescription());
		rootNode.put("planStartDate", project.getPlanStartDate().toString().substring(0, 10));
		rootNode.put("planEndDate", project.getPlanEndDate().toString().substring(0, 10));
		rootNode.put("startDate",
				project.getStartDate() != null ? project.getStartDate().toString().substring(0, 10) : "");
		rootNode.put("endDate", project.getEndDate() != null ? project.getEndDate().toString().substring(0, 10) : "");
		rootNode.put("state", project.getState());
		rootNode.put("progress", project.getProgress());
		rootNode.put("duration", DateUtils.getDuration(project.getPlanStartDate(), project.getPlanEndDate()));

		JSONArray array = new JSONArray();
		ArrayList<Task> list = getTasks(project);
		for (Task t : list) {
			String roles = "";
			ArrayList<TaskRoleLink> links = TaskHelper.manager.getTaskRoleLink(project, t);
			for (TaskRoleLink link : links) {
				BaseCode c = link.getRole();
				roles += c.getCode() + ",";
			}

			String preTask = "";
			ArrayList<Task> pres = TaskHelper.manager.getPreTasks(t);
			for (Task pre : pres) {
				preTask += pre.getName() + ",";
			}

			JSONObject node = new JSONObject();
			node.put("oid", t.getPersistInfo().getObjectIdentifier().getStringValue());
			node.put("id", t.getPersistInfo().getObjectIdentifier().getId());
			node.put("name", t.getName());
			node.put("description", t.getDescription());
			node.put("planStartDate",
					t.getPlanStartDate() != null ? t.getPlanStartDate().toString().substring(0, 10) : "");
			node.put("planEndDate", t.getPlanEndDate() != null ? t.getPlanEndDate().toString().substring(0, 10) : "");
//			node.put("duration", t.getDuration());
			node.put("duration", DateUtils.getDuration(t.getPlanStartDate(), t.getPlanEndDate()));
			node.put("startDate", t.getStartDate() != null ? t.getStartDate().toString().substring(0, 10) : "");
			node.put("endDate", t.getEndDate() != null ? t.getEndDate().toString().substring(0, 10) : "");
			node.put("state", t.getState());
			node.put("roles", roles);
			node.put("progress", t.getProgress());
			node.put("preTask", preTask);
			load(node, project, t);
			array.add(node);
		}
		rootNode.put("children", array);
		jsonArray.add(rootNode);
		return jsonArray;
	}

	private void load(JSONObject n, Project project, Task parent) throws Exception {
		JSONArray jsonChildren = new JSONArray();
		ArrayList<Task> list = getTasks(project, parent);
		for (Task t : list) {
			JSONObject node = new JSONObject();
			String roles = "";
			ArrayList<TaskRoleLink> links = TaskHelper.manager.getTaskRoleLink(project, t);
			for (TaskRoleLink link : links) {
				BaseCode c = link.getRole();
				roles += c.getCode() + ",";
			}

			String preTask = "";
			ArrayList<Task> pres = TaskHelper.manager.getPreTasks(t);
			for (Task pre : pres) {
				preTask += pre.getName() + ",";
			}

			node.put("oid", t.getPersistInfo().getObjectIdentifier().getStringValue());
			node.put("id", t.getPersistInfo().getObjectIdentifier().getId());
			node.put("name", t.getName());
			node.put("description", t.getDescription());
			node.put("planStartDate",
					t.getPlanStartDate() != null ? t.getPlanStartDate().toString().substring(0, 10) : "");
			node.put("planEndDate", t.getPlanEndDate() != null ? t.getPlanEndDate().toString().substring(0, 10) : "");
//			node.put("duration", t.getDuration());
			node.put("duration", DateUtils.getDuration(t.getPlanStartDate(), t.getPlanEndDate()));
			node.put("startDate", t.getStartDate() != null ? t.getStartDate().toString().substring(0, 10) : "");
			node.put("endDate", t.getEndDate() != null ? t.getEndDate().toString().substring(0, 10) : "");
			node.put("state", t.getState());
			node.put("roles", roles);
			node.put("progress", t.getProgress());
			node.put("preTask", preTask);
			load(node, project, t);
			jsonChildren.add(node);
		}
		n.put("children", jsonChildren);
	}

	public ArrayList<Task> getTasks(Project project) throws Exception {
		return getTasks(project, null);
	}

	public ArrayList<Task> getTasks(Project project, Task task) throws Exception {
		ArrayList<Task> list = new ArrayList<>();

		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(Task.class, true);

		SearchCondition sc = new SearchCondition(Task.class, "projectReference.key.id", "=",
				project.getPersistInfo().getObjectIdentifier().getId());
		query.appendWhere(sc, new int[] { idx });
		query.appendAnd();

		sc = new SearchCondition(Task.class, "templateReference.key.id", "=", 0L);
		query.appendWhere(sc, new int[] { idx });

		if (StringUtils.isNotNull(task)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(Task.class, "parentTaskReference.key.id", "=",
					task.getPersistInfo().getObjectIdentifier().getId());
			query.appendWhere(sc, new int[] { idx });
		} else {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(Task.class, "parentTaskReference.key.id", "=", 0L);
			query.appendWhere(sc, new int[] { idx });
		}

		ClassAttribute ca = new ClassAttribute(Task.class, Task.SORT);
		OrderBy by = new OrderBy(ca, false);
		query.appendOrderBy(by, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find(query);
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			Task t = (Task) obj[0];
			list.add(t);
		}
		return list;
	}

	public boolean isPM(Project project) throws Exception {
		WTUser pm = project.getPm();
		WTUser user = (WTUser) SessionHelper.manager.getPrincipal();

		if (pm.getName().equals(user.getName())) {
			return true;
		}
		return false;
	}

	public Map<String, Object> my(Map<String, Object> params) throws Exception {
		List<ProjectColumns> list = new ArrayList<>();
		Map<String, Object> map = new HashMap<String, Object>();

		String name = (String) params.get("name");
		String number = (String) params.get("number");
		String state = (String) params.get("state");
		String startProjectStartDate = (String) params.get("startProjectDate");
		String endProjectStartDate = (String) params.get("endProjectDate");
		String startProjectEndDate = (String) params.get("startFinishDate");
		String endProjectEndDate = (String) params.get("endFinishDate");

		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(Project.class, true);

		SearchCondition sc = null;
		ClassAttribute ca = null;

		if (StringUtils.isNotNull(name)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			ca = new ClassAttribute(Project.class, Project.NAME);
			ColumnExpression ce = ConstantExpression.newExpression("%" + name.toUpperCase() + "%");
			SQLFunction function = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca);
			sc = new SearchCondition(function, SearchCondition.LIKE, ce);
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(number)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			ca = new ClassAttribute(Project.class, Project.NUMBER);
			ColumnExpression ce = ConstantExpression.newExpression("%" + number.toUpperCase() + "%");
			SQLFunction function = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca);
			sc = new SearchCondition(function, SearchCondition.LIKE, ce);
			query.appendWhere(sc, new int[] { idx });
		}
		// 진행상태
		if (StringUtils.isNotNull(state)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(Project.class, Project.STATE, "=", state);
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(startProjectStartDate)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(Project.class, Project.PLAN_START_DATE, ">=",
					DateUtils.startTimestamp(startProjectStartDate));
			query.appendWhere(sc, new int[] { idx });
		}
		if (StringUtils.isNotNull(endProjectStartDate)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(Project.class, Project.PLAN_START_DATE, "<=",
					DateUtils.startTimestamp(endProjectStartDate));
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(startProjectEndDate)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(Project.class, Project.PLAN_END_DATE, ">=",
					DateUtils.startTimestamp(startProjectEndDate));
			query.appendWhere(sc, new int[] { idx });
		}
		if (StringUtils.isNotNull(endProjectEndDate)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(Project.class, Project.PLAN_END_DATE, "<=",
					DateUtils.startTimestamp(endProjectEndDate));
			query.appendWhere(sc, new int[] { idx });
		}

		ca = new ClassAttribute(Project.class, Project.CREATE_TIMESTAMP);
		OrderBy by = new OrderBy(ca, true);
		query.appendOrderBy(by, new int[] { idx });

//		QueryResult result = PersistenceHelper.manager.find(query);
		PageUtils pager = new PageUtils(params, query);
		QueryResult result = pager.find();
		int total = pager.getTotal();
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			ProjectColumns columns = new ProjectColumns((Project) obj[0]);
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

	public void sum(Project project) throws Exception {

		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(Task.class, true);

		SearchCondition sc = new SearchCondition(Task.class, "projectReference.key.id", "=",
				project.getPersistInfo().getObjectIdentifier().getId());
		query.appendWhere(sc, new int[] { idx });
		query.appendAnd();

		sc = new SearchCondition(Task.class, "parentTaskReference.key.id", "=", 0L);
		query.appendWhere(sc, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find(query);
		double labor = 0D;
		double material = 0D;
		double etc = 0D;
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			Task t = (Task) obj[0];
			labor += t.getLabor();
			material += t.getMaterial();
			etc += t.getEtc();
		}

		project.setEtc(etc);
		project.setLabor(labor);
		project.setMaterial(material);
		PersistenceHelper.manager.modify(project);
	}
}
