package platform.project.template.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import platform.code.entity.BaseCode;
import platform.echange.eco.entity.ECO;
import platform.project.task.entity.Task;
import platform.project.task.entity.TaskRoleLink;
import platform.project.task.service.TaskHelper;
import platform.project.template.entity.Template;
import platform.project.template.entity.TemplateColumns;
import platform.util.CommonUtils;
import platform.util.DateUtils;
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

public class TemplateHelper {

	public static final TemplateService service = ServiceFactory.getService(TemplateService.class);
	public static final TemplateHelper manager = new TemplateHelper();

	public Map<String, Object> list(Map<String, Object> params) throws Exception {
		List<TemplateColumns> list = new ArrayList<>();
		Map<String, Object> map = new HashMap<String, Object>();

		String name = (String) params.get("name");
		String company = (String) params.get("company");
		String enable = (String) params.get("enable");

		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(Template.class, true);

		if (StringUtils.isNotNull(name)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			ClassAttribute ca = new ClassAttribute(Template.class, Template.NAME);
			ColumnExpression ce = ConstantExpression.newExpression("%" + name.toUpperCase() + "%");
			SQLFunction function = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca);
			SearchCondition sc = new SearchCondition(function, SearchCondition.LIKE, ce);
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(company)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			SearchCondition sc = new SearchCondition(Template.class, Template.COMPANY, "=", company);
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(enable)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			SearchCondition sc = new SearchCondition(Template.class, Template.ENABLE, "=", enable);
			query.appendWhere(sc, new int[] { idx });
		}

		ClassAttribute ca = new ClassAttribute(Template.class, Template.CREATE_TIMESTAMP);
		OrderBy by = new OrderBy(ca, true);
		query.appendOrderBy(by, new int[] { idx });
//		QueryResult result = PersistenceHelper.manager.find(query);
		PageUtils pager = new PageUtils(params, query);
		QueryResult result = pager.find();
		int total = pager.getTotal();
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			TemplateColumns columns = new TemplateColumns((Template) obj[0]);
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
		return "TEMPLATE-" + number;
	}

	public JSONArray load(String oid) throws Exception {
		Template template = (Template) CommonUtils.persistable(oid);
		JSONArray jsonArray = new JSONArray();
		JSONObject rootNode = new JSONObject();
		rootNode.put("oid", template.getPersistInfo().getObjectIdentifier().getStringValue());
		rootNode.put("id", template.getPersistInfo().getObjectIdentifier().getId());
		rootNode.put("name", template.getName());
		rootNode.put("description", template.getDescription());
		rootNode.put("planStartDate", template.getPlanStartDate().toString().substring(0, 10));
		rootNode.put("planEndDate", template.getPlanEndDate().toString().substring(0, 10));
		rootNode.put("duration", template.getDuration());
//		rootNode.put("duration", DateUtils.getDuration(template.getPlanStartDate(), template.getPlanEndDate()));
		rootNode.put("isNew", false);

		JSONArray array = new JSONArray();
		ArrayList<Task> list = getTasks(template);
		for (Task t : list) {
			String roles = "";
			ArrayList<TaskRoleLink> links = TaskHelper.manager.getTaskRoleLink(template, t);
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
			node.put("duration", t.getDuration());
//			node.put("duration", DateUtils.getDuration(t.getPlanStartDate(), t.getPlanEndDate()));
			node.put("isNew", false);
			node.put("roles", roles);
			node.put("preTask", preTask);
			load(node, template, t);
			array.add(node);
		}
		rootNode.put("children", array);
		jsonArray.add(rootNode);
		return jsonArray;
	}

	private void load(JSONObject n, Template template, Task parent) throws Exception {
		JSONArray jsonChildren = new JSONArray();
		ArrayList<Task> list = getTasks(template, parent);
		for (Task t : list) {
			JSONObject node = new JSONObject();
			String roles = "";
			ArrayList<TaskRoleLink> links = TaskHelper.manager.getTaskRoleLink(template, t);
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
			node.put("duration", t.getDuration());
//			node.put("duration", DateUtils.getDuration(t.getPlanStartDate(), t.getPlanEndDate()));
			node.put("isNew", false);
			node.put("roles", roles);
			node.put("preTask", preTask);
			load(node, template, t);
			jsonChildren.add(node);
		}
		n.put("children", jsonChildren);
	}

	public ArrayList<Task> getTasks(Template template) throws Exception {
		return getTasks(template, null);
	}

	public ArrayList<Task> getTasks(Template template, Task task) throws Exception {
		ArrayList<Task> list = new ArrayList<>();

		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(Task.class, true);

		SearchCondition sc = new SearchCondition(Task.class, "templateReference.key.id", "=",
				template.getPersistInfo().getObjectIdentifier().getId());
		query.appendWhere(sc, new int[] { idx });
		query.appendAnd();

		sc = new SearchCondition(Task.class, "projectReference.key.id", "=", 0L);
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

	public ArrayList<Map<String, Object>> getTemplateMap() throws Exception {
		ArrayList<Map<String, Object>> list = new ArrayList<>();
		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(Template.class, true);
		SearchCondition sc = new SearchCondition(Template.class, Template.ENABLE, SearchCondition.IS_TRUE);
		query.appendWhere(sc, new int[] { idx });

		ClassAttribute ca = new ClassAttribute(Template.class, Template.NAME);
		OrderBy by = new OrderBy(ca, true);
		query.appendOrderBy(by, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find(query);
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			Template template = (Template) obj[0];
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("value", template.getPersistInfo().getObjectIdentifier().getStringValue());
			map.put("name", template.getName());
			list.add(map);
		}
		return list;
	}
}
