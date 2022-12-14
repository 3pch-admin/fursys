package platform.project.task.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import platform.code.entity.BaseCode;
import platform.project.entity.Project;
import platform.project.entity.ProjectRoleLink;
import platform.project.service.ProjectHelper;
import platform.project.task.entity.ParentTaskChildTaskLink;
import platform.project.task.entity.PreTaskPostTaskLink;
import platform.project.task.entity.Task;
import platform.project.task.entity.TaskColumns;
import platform.project.task.entity.TaskRoleLink;
import platform.project.template.entity.Template;
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

public class TaskHelper {

	public static final TaskService service = ServiceFactory.getService(TaskService.class);
	public static final TaskHelper manager = new TaskHelper();

	public static final String READY = "대기중";
	public static final String START = "진행중";
	public static final String COMPLETE = "완료됨";
	public static final String STOP = "중지됨";

	public ArrayList<TaskRoleLink> getTaskRoleLink(Template template, Task task) throws Exception {
		ArrayList<TaskRoleLink> list = new ArrayList<TaskRoleLink>();

		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(TaskRoleLink.class, true);
		int idx_t = query.appendClassList(Task.class, false);

		SearchCondition sc = new SearchCondition(TaskRoleLink.class, "roleAObjectRef.key.id", Task.class,
				WTAttributeNameIfc.ID_NAME);
		query.appendWhere(sc, new int[] { idx, idx_t });
		query.appendAnd();

		sc = new SearchCondition(TaskRoleLink.class, "templateReference.key.id", "=",
				template.getPersistInfo().getObjectIdentifier().getId());
		query.appendWhere(sc, new int[] { idx });
		query.appendAnd();

		sc = new SearchCondition(TaskRoleLink.class, "roleAObjectRef.key.id", "=",
				task.getPersistInfo().getObjectIdentifier().getId());
		query.appendWhere(sc, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find(query);
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			TaskRoleLink link = (TaskRoleLink) obj[0];
			list.add(link);
		}
		return list;
	}

	public ArrayList<TaskRoleLink> getTaskRoleLink(Project project, Task task) throws Exception {
		ArrayList<TaskRoleLink> list = new ArrayList<TaskRoleLink>();

		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(TaskRoleLink.class, true);
		int idx_t = query.appendClassList(Task.class, false);

		SearchCondition sc = new SearchCondition(TaskRoleLink.class, "roleAObjectRef.key.id", Task.class,
				WTAttributeNameIfc.ID_NAME);
		query.appendWhere(sc, new int[] { idx, idx_t });
		query.appendAnd();

		sc = new SearchCondition(TaskRoleLink.class, "projectReference.key.id", "=",
				project.getPersistInfo().getObjectIdentifier().getId());
		query.appendWhere(sc, new int[] { idx });
		query.appendAnd();

		sc = new SearchCondition(TaskRoleLink.class, "roleAObjectRef.key.id", "=",
				task.getPersistInfo().getObjectIdentifier().getId());
		query.appendWhere(sc, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find(query);
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			TaskRoleLink link = (TaskRoleLink) obj[0];
			list.add(link);
		}
		return list;
	}

	public ArrayList<Task> getAllTemplateTask(Template template) throws Exception {
		ArrayList<Task> list = new ArrayList<>();

		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(Task.class, true);

		SearchCondition sc = new SearchCondition(Task.class, "templateReference.key.id", "=",
				template.getPersistInfo().getObjectIdentifier().getId());
		query.appendWhere(sc, new int[] { idx });

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

	public int getSort(Template template) throws Exception {
		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(Task.class, true);
		SearchCondition sc = new SearchCondition(Task.class, "templateReference.key.id", "=",
				template.getPersistInfo().getObjectIdentifier().getId());
		query.appendWhere(sc, new int[] { idx });
		query.appendAnd();

		sc = new SearchCondition(Task.class, Task.SORT, false);
		query.appendWhere(sc, new int[] { idx });

//
//		sc = new SearchCondition(Task.class, Task.DEPTH, "=", depth);
//		query.appendWhere(sc, new int[] { idx });

		ClassAttribute ca = new ClassAttribute(Task.class, Task.SORT);
		OrderBy by = new OrderBy(ca, true);
		query.appendOrderBy(by, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find(query);
		int sort = 0;
		if (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			Task t = (Task) obj[0];
			if (t.getSort() == null) {
				sort = 0;
			} else {
				sort = t.getSort() + 1;
			}
		}
		return sort;
	}

	public int getSort(Project project) throws Exception {
		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(Task.class, true);
		SearchCondition sc = new SearchCondition(Task.class, "projectReference.key.id", "=",
				project.getPersistInfo().getObjectIdentifier().getId());
		query.appendWhere(sc, new int[] { idx });
		query.appendAnd();

		sc = new SearchCondition(Task.class, Task.SORT, false);
		query.appendWhere(sc, new int[] { idx });
//		query.appendAnd();
//
//		sc = new SearchCondition(Task.class, Task.DEPTH, "=", depth);
//		query.appendWhere(sc, new int[] { idx });

		ClassAttribute ca = new ClassAttribute(Task.class, Task.SORT);
		OrderBy by = new OrderBy(ca, true);
		query.appendOrderBy(by, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find(query);
		int sort = 0;
		if (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			Task t = (Task) obj[0];
			if (t.getSort() == null) {
				sort = 0;
			} else {
				sort = t.getSort() + 1;
			}
		}
		return sort;
	}

	public ArrayList<Task> getPreTasks(Task postTask) throws Exception {
		ArrayList<Task> list = new ArrayList<>();
		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(PreTaskPostTaskLink.class, true);

		SearchCondition sc = new SearchCondition(PreTaskPostTaskLink.class, "roleBObjectRef.key.id", "=",
				postTask.getPersistInfo().getObjectIdentifier().getId());
		query.appendWhere(sc, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find(query);
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			PreTaskPostTaskLink link = (PreTaskPostTaskLink) obj[0];
			list.add(link.getPreTask());
		}
		return list;
	}

	public ArrayList<PreTaskPostTaskLink> getPreTaskPostTaskLinks(Task postTask) throws Exception {
		ArrayList<PreTaskPostTaskLink> list = new ArrayList<>();
		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(PreTaskPostTaskLink.class, true);

		SearchCondition sc = new SearchCondition(PreTaskPostTaskLink.class, "roleBObjectRef.key.id", "=",
				postTask.getPersistInfo().getObjectIdentifier().getId());
		query.appendWhere(sc, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find(query);
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			PreTaskPostTaskLink link = (PreTaskPostTaskLink) obj[0];
			list.add(link);
		}
		return list;
	}

	public ArrayList<Task> getAllProjectTask(Project project) throws Exception {
		ArrayList<Task> list = new ArrayList<>();

		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(Task.class, true);

		SearchCondition sc = new SearchCondition(Task.class, "projectReference.key.id", "=",
				project.getPersistInfo().getObjectIdentifier().getId());
		query.appendWhere(sc, new int[] { idx });

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

	public ArrayList<Task> getChildren(Task parentTask) throws Exception {
		ArrayList<Task> list = new ArrayList<>();

		QueryResult result = PersistenceHelper.manager.navigate(parentTask, "childTask", ParentTaskChildTaskLink.class);
		while (result.hasMoreElements()) {
			Task t = (Task) result.nextElement();
			list.add(t);
		}
		return list;
	}

	public ArrayList<ProjectRoleLink> getProjectRoleLink(Project project) throws Exception {
		ArrayList<ProjectRoleLink> list = new ArrayList<>();

		QueryResult result = PersistenceHelper.manager.navigate(project, "role", ProjectRoleLink.class, false);
		while (result.hasMoreElements()) {
			ProjectRoleLink link = (ProjectRoleLink) result.nextElement();
			list.add(link);
		}
		return list;
	}

	public ArrayList<TaskRoleLink> getTaskRoleLink(Task task) throws Exception {
		ArrayList<TaskRoleLink> list = new ArrayList<>();

		QueryResult result = PersistenceHelper.manager.navigate(task, "role", TaskRoleLink.class, false);
		while (result.hasMoreElements()) {
			TaskRoleLink link = (TaskRoleLink) result.nextElement();
			list.add(link);
		}
		return list;
	}

	public ArrayList<WTUser> getProjectUser(Project project, BaseCode role) throws Exception {
		ArrayList<WTUser> list = new ArrayList<>();

		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(TaskRoleLink.class, true);

		SearchCondition sc = null;

		sc = new SearchCondition(TaskRoleLink.class, "projectReference.key.id", "=",
				project.getPersistInfo().getObjectIdentifier().getId());
		query.appendWhere(sc, new int[] { idx });
		query.appendAnd();

		sc = new SearchCondition(TaskRoleLink.class, "roleBObjectRef.key.id", "=",
				role.getPersistInfo().getObjectIdentifier().getId());
		query.appendWhere(sc, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find(query);

		System.out.println("qiuery=" + query);
		System.out.println("size=" + result.size());

		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			TaskRoleLink link = (TaskRoleLink) obj[0];
			WTUser user = link.getUser();
			if (user != null && !list.contains(user)) {
				list.add(user);
			}
		}
		return list;
	}

	public Map<String, Object> my(Map<String, Object> params) throws Exception {
		List<TaskColumns> list = new ArrayList<>();
		Map<String, Object> map = new HashMap<String, Object>();

		String pjtname = (String) params.get("pjtName");
		String taskName = (String) params.get("taskName");
		String pjtState = (String) params.get("pjtState");
		String taskState = (String) params.get("taskState");
		String startProjectStartDate = (String) params.get("startProjectDate");
		String endProjectStartDate = (String) params.get("endProjectDate");
		String startProjectEndDate = (String) params.get("startFinishDate");
		String endProjectEndDate = (String) params.get("endFinishDate");

		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(TaskRoleLink.class, true);
		int idx_p = query.appendClassList(Project.class, false);
		int idx_t = query.appendClassList(Task.class, false);

		SearchCondition sc = null;
		ClassAttribute ca = null;

		sc = new SearchCondition(TaskRoleLink.class, "projectReference.key.id", Project.class,
				"thePersistInfo.theObjectIdentifier.id");
		query.appendWhere(sc, new int[] { idx, idx_p });
		query.appendAnd();

		sc = new SearchCondition(TaskRoleLink.class, "roleAObjectRef.key.id", Task.class,
				"thePersistInfo.theObjectIdentifier.id");
		query.appendWhere(sc, new int[] { idx, idx_t });

		WTUser user = (WTUser) SessionHelper.manager.getPrincipal();

		if (!CommonUtils.isAdmin()) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(TaskRoleLink.class, "userReference.key.id", "=",
					user.getPersistInfo().getObjectIdentifier().getId());
			query.appendWhere(sc, new int[] { idx });
		}

		if (query.getConditionCount() > 0) {
			query.appendAnd();
		}

		sc = new SearchCondition(TaskRoleLink.class, "userReference.key.id", "<>", 0L);
		query.appendWhere(sc, new int[] { idx });

		if (StringUtils.isNotNull(pjtname)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}

			ca = new ClassAttribute(Project.class, Project.NAME);
			ColumnExpression ce = ConstantExpression.newExpression("%" + pjtname.toUpperCase() + "%");
			SQLFunction function = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca);
			sc = new SearchCondition(function, SearchCondition.LIKE, ce);
			query.appendWhere(sc, new int[] { idx_p });
		}

		if (StringUtils.isNotNull(taskName)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}

			ca = new ClassAttribute(Task.class, Task.NAME);
			ColumnExpression ce = ConstantExpression.newExpression("%" + taskName.toUpperCase() + "%");
			SQLFunction function = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca);
			sc = new SearchCondition(function, SearchCondition.LIKE, ce);
			query.appendWhere(sc, new int[] { idx_t });
		}
		// 프로젝트 진행상태
		if (StringUtils.isNotNull(pjtState)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(Project.class, Project.STATE, "=", pjtState);
			query.appendWhere(sc, new int[] { idx_p });
		}
		// 태스크 진행상태
		if (StringUtils.isNotNull(taskState)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(Task.class, Task.STATE, "=", taskState);
			query.appendWhere(sc, new int[] { idx_t });
		}

		if (StringUtils.isNotNull(startProjectStartDate)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(Project.class, Project.PLAN_START_DATE, ">=",
					DateUtils.startTimestamp(startProjectStartDate));
			query.appendWhere(sc, new int[] { idx_p });
		}
		if (StringUtils.isNotNull(endProjectStartDate)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(Project.class, Project.PLAN_START_DATE, "<=",
					DateUtils.startTimestamp(endProjectStartDate));
			query.appendWhere(sc, new int[] { idx_p });
		}

		if (StringUtils.isNotNull(startProjectEndDate)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(Project.class, Project.PLAN_END_DATE, ">=",
					DateUtils.startTimestamp(startProjectEndDate));
			query.appendWhere(sc, new int[] { idx_p });
		}
		if (StringUtils.isNotNull(endProjectEndDate)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(Project.class, Project.PLAN_END_DATE, "<=",
					DateUtils.startTimestamp(endProjectEndDate));
			query.appendWhere(sc, new int[] { idx_p });
		}

		ca = new ClassAttribute(Task.class, Task.CREATE_TIMESTAMP);
		OrderBy by = new OrderBy(ca, true);
		query.appendOrderBy(by, new int[] { idx_t });

//		QueryResult result = PersistenceHelper.manager.find(query);
		PageUtils pager = new PageUtils(params, query);
		QueryResult result = pager.find();
		int total = pager.getTotal();
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			TaskRoleLink link = (TaskRoleLink) obj[0];
			TaskColumns columns = new TaskColumns(link.getTask());
			columns.setRole(link.getRole().getName());
			columns.setUser(link.getUser().getFullName());
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

	public boolean isLast(Task parentTask) throws Exception {
		QueryResult result = PersistenceHelper.manager.navigate(parentTask, "childTask", ParentTaskChildTaskLink.class);
		return result.size() == 0;
	}

	public void sum(Task task) throws Exception {

		Task parentTask = task.getParentTask();
		ArrayList<Task> list = new ArrayList<>();
		if (parentTask != null) {

			list = TaskHelper.manager.getChildren(parentTask);
			double etc = 0D;
			double material = 0D;
			double labor = 0D;

			for (Task t : list) {
				etc += t.getEtc();
				material += t.getMaterial();
				labor += t.getLabor();
			}

			parentTask.setEtc(etc);
			parentTask.setMaterial(material);
			parentTask.setLabor(labor);
			PersistenceHelper.manager.modify(parentTask);

			sum(parentTask);
		}
		Project project = (Project) PersistenceHelper.manager.refresh(task.getProject());
		ProjectHelper.manager.sum(project);
	}
}