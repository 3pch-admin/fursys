package platform.project.service;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import platform.code.entity.BaseCode;
import platform.code.service.BaseCodeHelper;
import platform.project.entity.Project;
import platform.project.entity.ProjectRoleLink;
import platform.project.output.entity.Output;
import platform.project.output.service.OutputHelper;
import platform.project.task.entity.ParentTaskChildTaskLink;
import platform.project.task.entity.PreTaskPostTaskLink;
import platform.project.task.entity.Task;
import platform.project.task.entity.TaskRoleLink;
import platform.project.task.entity.TaskTreeNode;
import platform.project.task.service.TaskHelper;
import platform.project.template.entity.Template;
import platform.util.CommonUtils;
import platform.util.DateUtils;
import platform.util.StringUtils;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.org.WTPrincipal;
import wt.org.WTUser;
import wt.ownership.Ownership;
import wt.pom.Transaction;
import wt.services.StandardManager;
import wt.session.SessionHelper;
import wt.util.WTException;

public class StandardProjectService extends StandardManager implements ProjectService {

	public static StandardProjectService newStandardProjectService() throws WTException {
		StandardProjectService instance = new StandardProjectService();
		instance.initialize();
		return instance;
	}

	@Override
	public Project create(Map<String, Object> params) throws Exception {
		Project project = null;
		String name = (String) params.get("name");
		String description = (String) params.get("description");
		String pmOid = (String) params.get("pmOid");
		String templateOid = (String) params.get("templateOid");
		String customer = (String) params.get("customer");
		String projectType = (String) params.get("projectType");
		String planStartDate = (String) params.get("planStartDate");
		String budget = (String) params.get("budget");
		Transaction trs = new Transaction();
		try {
			trs.start();

			WTPrincipal prin = SessionHelper.manager.getPrincipal();
			WTUser pm = (WTUser) CommonUtils.persistable(pmOid);
			project = Project.newProject();
			project.setName(name);
			project.setPm(pm);
			project.setProjectType(projectType);
			project.setPlanStartDate(DateUtils.startTimestamp(planStartDate));
			project.setPlanEndDate(DateUtils.endTimestamp(planStartDate));
			project.setOwnership(Ownership.newOwnership(prin));
			project.setNumber(ProjectHelper.manager.getNextNumber());
			project.setDescription(description);
			project.setBudget(Double.parseDouble(budget.replaceAll(",", "")));
			project.setCustomer(customer);
			project.setProgress(0D);
			project.setEtc(0D);
			project.setMaterial(0D);
			project.setLabor(0D);
			project.setTotal(0D);
			project.setState(TaskHelper.READY);

			PersistenceHelper.manager.save(project);

			if (StringUtils.isNotNull(templateOid)) {
				Template template = (Template) CommonUtils.persistable(templateOid);
				copys(project, template);
			}

			project = (Project) PersistenceHelper.manager.refresh(project);
			calculation(project);

			trs.commit();
			trs = null;
		} catch (Exception e) {
			e.printStackTrace();
			trs.rollback();
			throw e;
		} finally {
			if (trs != null)
				trs.rollback();
		}
		return project;
	}

	@Override
	public void copys(Project project, Template template) throws Exception {
		Transaction trs = new Transaction();
		try {
			trs.start();
			WTPrincipal prin = SessionHelper.manager.getPrincipal();

			ArrayList<Task> list = TaskHelper.manager.getAllTemplateTask(template);
			HashMap<Task, Task> parentMap = new HashMap<Task, Task>();
			for (int i = 0; i < list.size(); i++) {
				Task org = (Task) list.get(i);

				Task newTask = Task.newTask();
				newTask.setName(org.getName());
				newTask.setState(TaskHelper.READY);
				newTask.setSort(org.getSort());
				newTask.setDepth(org.getDepth());
				newTask.setProgress(0D);
				newTask.setProject(project);
				newTask.setTemplate(null);
//				newTask.setDuration(org.getDuration());
				newTask.setOwnership(Ownership.newOwnership(prin));
				newTask.setDescription(org.getDescription());

				Calendar ca = Calendar.getInstance();
				ca.setTimeInMillis(project.getPlanStartDate().getTime());

				Timestamp newPlanStartDate = new Timestamp(ca.getTime().getTime());
				newTask.setPlanStartDate(newPlanStartDate);

				int du = DateUtils.getDuration(org.getPlanStartDate(), org.getPlanEndDate());
				ca.add(Calendar.DATE, du);
				Timestamp newPlanEndDate = new Timestamp(ca.getTime().getTime());
				newTask.setPlanEndDate(newPlanEndDate);

				Task parent = (Task) parentMap.get(org.getParentTask());
				newTask.setParentTask(parent);
				newTask = (Task) PersistenceHelper.manager.save(newTask);

				ArrayList<Output> l = OutputHelper.manager.getTemplateOutput(template, org);
				for (Output output : l) {
					Output newOutput = Output.newOutput();
					newOutput.setName(output.getName());
					newOutput.setTask(newTask);
					newOutput.setProject(project);
					newOutput.setTemplate(null);
					newOutput.setLocation(output.getLocation());
					newOutput.setOwnership(Ownership.newOwnership(prin));
					newOutput.setDescription(output.getDescription());
					PersistenceHelper.manager.save(newOutput);
				}

				parentMap.put(org, newTask);
			}

			HashMap<String, Object> map = new HashMap<String, Object>();

			for (int i = 0; i < list.size(); i++) {
				Task orgTask = (Task) list.get(i);
				Task newTask = (Task) parentMap.get(orgTask);
				// 선후행..
				QueryResult result = PersistenceHelper.manager.navigate(orgTask, "preTask", PreTaskPostTaskLink.class);
				while (result.hasMoreElements()) {
					Task preTask = (Task) result.nextElement();
					Task newPreTask = (Task) parentMap.get(preTask);
					PreTaskPostTaskLink link = PreTaskPostTaskLink.newPreTaskPostTaskLink(newPreTask, newTask);
					PersistenceHelper.manager.save(link);
				}

				ArrayList<TaskRoleLink> orgRoleLink = TaskHelper.manager.getTaskRoleLink(orgTask.getTemplate(),
						orgTask);
				for (TaskRoleLink link : orgRoleLink) {
					BaseCode role = link.getRole();
					TaskRoleLink ll = TaskRoleLink.newTaskRoleLink(newTask, role);
					ll.setTemplate(null);
					ll.setProject(project);
					PersistenceHelper.manager.save(ll);

					if (!map.containsKey(role.getCode())) {
						map.put(role.getCode(), role);
					}
				}
			}

			Iterator<String> it = map.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				BaseCode role = (BaseCode) map.get(key);
				ProjectRoleLink ll = ProjectRoleLink.newProjectRoleLink(project, role);
				PersistenceHelper.manager.save(ll);
			}

			trs.commit();
			trs = null;
		} catch (Exception e) {
			e.printStackTrace();
			trs.rollback();
			throw e;
		} finally {
			if (trs != null)
				trs.rollback();
		}

	}

	@Override
	public void calculation(Project project) throws Exception {
		Transaction trs = new Transaction();
		try {
			trs.start();

			// 선후행 정리
			ArrayList<Task> list = TaskHelper.manager.getAllProjectTask(project);
			for (Task task : list) {
				// 후행 태스크의 계획 시작일
				Timestamp preStart = null;
				// 후행 태스크의 계획 종료일
				Timestamp preEnd = null;

				boolean isEdit = false;

				ArrayList<PreTaskPostTaskLink> link = TaskHelper.manager.getPreTaskPostTaskLinks(task);
				for (PreTaskPostTaskLink data : link) {
					Task preTask = data.getPreTask(); // 선행
					Task postTask = data.getPostTask();
					int duration = DateUtils.getDuration(postTask.getPlanStartDate(), postTask.getPlanEndDate());

					// 선행 태스크의 계획 종료일 세팅
					Calendar sCa = Calendar.getInstance();
					sCa.setTime(preTask.getPlanEndDate());

					// 선후행 간격 설정
//					sCa.add(Calendar.DATE, data.getGap());

					Timestamp start = new Timestamp(sCa.getTime().getTime());

					Calendar eCa = Calendar.getInstance();
					eCa.setTime(start);
					for (int k = 0; k < duration; k++) {
						eCa.add(Calendar.DATE, 1);
					}
					Timestamp end = new Timestamp(eCa.getTime().getTime());

					if (preStart == null || (preStart.getTime() < start.getTime())) {
						preStart = start;
						isEdit = true;
					}

					// 후행 태스크의 계획 종료일 null
					// 후행 2000-01-02 선행 2000-01-03
					if (preEnd == null || (preEnd.getTime() < end.getTime())) {
						preEnd = end;
						isEdit = true;
					}
				}

				if (isEdit) {
					task.setPlanStartDate(preStart);
					task.setPlanEndDate(preEnd);
					task = (Task) PersistenceHelper.manager.modify(task);
				}
			}

			// 모자 관계 정리
			for (int i = list.size() - 1; i >= 0; i--) {
				Task task = (Task) list.get(i);
				// 상위 계획 시작
				Timestamp start = null;
				// 상위 계획 종료
				Timestamp end = null;

				boolean isEdit = false;
				QueryResult result = PersistenceHelper.manager.navigate(task, "childTask",
						ParentTaskChildTaskLink.class);
				while (result.hasMoreElements()) {
					Task child = (Task) result.nextElement();

					// 하위 계획 시작
					Timestamp cstart = child.getPlanStartDate();
					// 하위 계획 종료
					Timestamp cend = child.getPlanEndDate();
					// 상위 계획 시작일이 null
					if (start == null || (start.getTime() > cstart.getTime())) {
						start = cstart;
						isEdit = true;
					}

					if (end == null || (end.getTime() < cend.getTime())) {
						end = cend;
						isEdit = true;
					}
				}

				if (isEdit) {
					task.setPlanStartDate(start);
					task.setPlanEndDate(end);
					PersistenceHelper.manager.modify(task);
				}
			}

			// 프로젝트 전체 일정 정리.
			Timestamp start = null;
			Timestamp end = null;
			boolean edit = false;

			for (int i = list.size() - 1; i >= 0; i--) {
				Task child = (Task) list.get(i);

				Timestamp cstart = child.getPlanStartDate();
				Timestamp cend = child.getPlanEndDate();

				if (start == null || (start.getTime() > cstart.getTime())) {
					start = cstart;
					edit = true;
				}

				if (end == null || (end.getTime() < cend.getTime())) {
					end = cend;
					edit = true;
				}
			}

			if (edit) {
				project.setPlanStartDate(start);
				project.setPlanEndDate(end);
				PersistenceHelper.manager.modify(project);
			}

			trs.commit();
			trs = null;
		} catch (Exception e) {
			e.printStackTrace();
			trs.rollback();
			throw e;
		} finally {
			if (trs != null)
				trs.rollback();
		}
	}

	@Override
	public void saveTree(Map<String, Object> params) throws Exception {
		String json = (String) params.get("json");
		ArrayList<Map<String, Object>> _deleted = (ArrayList<Map<String, Object>>) params.get("_deleted");
		Transaction trs = new Transaction();
		try {
			trs.start();

			for (Map<String, Object> dd : _deleted) {
				String oid = (String) dd.get("oid");
				Task tt = (Task) CommonUtils.persistable(oid);
				PersistenceHelper.manager.delete(tt);
			}

			String jsonStr = new String(DatatypeConverter.parseBase64Binary(json), "UTF-8");
			Type listType = new TypeToken<ArrayList<TaskTreeNode>>() {
			}.getType();

			Gson gson = new Gson();
			List<TaskTreeNode> nodes = gson.fromJson(jsonStr, listType);
			Project project = null;
			for (TaskTreeNode node : nodes) {
				String oid = node.getOid();
				ArrayList<TaskTreeNode> childrens = node.getChildren();
				// 템플릿은 새로 생성없음..
				project = (Project) CommonUtils.persistable(oid);
				saveTree(project, null, childrens);
			}

			trs.commit();
			trs = null;
		} catch (Exception e) {
			e.printStackTrace();
			trs.rollback();
			throw e;
		} finally {
			if (trs != null)
				trs.rollback();
		}
	}

	@Override
	public void saveTree(Project project, Task parentTask, ArrayList<TaskTreeNode> childrens) throws Exception {
		Transaction trs = new Transaction();
		try {
			trs.start();

			WTPrincipal prin = SessionHelper.manager.getPrincipal();
			for (TaskTreeNode node : childrens) {
				String roles = node.getRoles();
				int depth = node.get_$depth();
				String oid = node.getOid();
				String name = node.getName();
				String description = node.getDescription();
				int duration = node.getDuration();
				boolean isNew = node.isNew();
				ArrayList<TaskTreeNode> n = node.getChildren();
				String planStartDate = node.getPlanStartDate();
				String planEndDate = node.getPlanEndDate();

				int sort = TaskHelper.manager.getSort(project);
				Task t = null;
				if (isNew) {
					t = Task.newTask();
					t.setName(name);
					t.setDepth(depth);
					t.setDescription(description);
//					t.setDuration(duration);
					t.setOwnership(Ownership.newOwnership(prin));
					t.setParentTask(parentTask);
					t.setProject(project);
					t.setPlanStartDate(DateUtils.startTimestamp(planStartDate));
					t.setPlanEndDate(DateUtils.endTimestamp(planEndDate));
					t.setSort(sort);
					t.setEtc(0D);
					t.setMaterial(0D);
					t.setLabor(0D);
					PersistenceHelper.manager.save(t);
				} else {
					t = (Task) CommonUtils.persistable(oid);
					t.setName(name);
					t.setDepth(depth);
					t.setDescription(description);
//					t.setDuration(duration);
					t.setOwnership(Ownership.newOwnership(prin));
					t.setParentTask(parentTask);
					t.setProject(project);
					t.setPlanStartDate(DateUtils.startTimestamp(planStartDate));
					t.setPlanEndDate(DateUtils.endTimestamp(planEndDate));
					t.setSort(sort);
					t.setEtc(0D);
					t.setMaterial(0D);
					t.setLabor(0D);
					PersistenceHelper.manager.modify(t);
				}

				ArrayList<TaskRoleLink> links = TaskHelper.manager.getTaskRoleLink(project, t);
				for (TaskRoleLink l : links) {
					PersistenceHelper.manager.delete(l);
				}
				if (StringUtils.isNotNull(roles)) {
					String[] rr = roles.split(",");
					for (String r : rr) {
						BaseCode roleCode = BaseCodeHelper.manager.getBaseCodeByCodeTypeAndCode("PROJECTROLE", r);
						TaskRoleLink link = TaskRoleLink.newTaskRoleLink(t, roleCode);
						link.setProject(project);
						link.setTemplate(null);
						PersistenceHelper.manager.save(link);
					}
				}
				saveTree(project, t, n);
			}

			trs.commit();
			trs = null;
		} catch (Exception e) {
			e.printStackTrace();
			trs.rollback();
			throw e;
		} finally {
			if (trs != null)
				trs.rollback();
		}
	}

	@Override
	public void start(String oid) throws Exception {
		Transaction trs = new Transaction();
		try {
			trs.start();

			Project project = (Project) CommonUtils.persistable(oid);
			project.setStartDate(DateUtils.today());
			project.setState("진행중");
			PersistenceHelper.manager.modify(project);

			trs.commit();
			trs = null;
		} catch (Exception e) {
			e.printStackTrace();
			trs.rollback();
			throw e;
		} finally {
			if (trs != null)
				trs.rollback();
		}

	}

	@Override
	public void complete(String oid) throws Exception {
		Transaction trs = new Transaction();
		try {
			trs.start();

			Project project = (Project) CommonUtils.persistable(oid);
			project.setEndDate(DateUtils.today());
			project.setProgress(100D);
			project.setState("완료됨");
			PersistenceHelper.manager.modify(project);

			trs.commit();
			trs = null;
		} catch (Exception e) {
			e.printStackTrace();
			trs.rollback();
			throw e;
		} finally {
			if (trs != null)
				trs.rollback();
		}

	}
}
