package platform.project.task.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;

import platform.project.entity.Project;
import platform.project.service.ProjectHelper;
import platform.project.task.entity.PreTaskPostTaskLink;
import platform.project.task.entity.Task;
import platform.project.task.entity.TaskRoleLink;
import platform.project.template.service.TemplateHelper;
import platform.util.CommonUtils;
import platform.util.DateUtils;
import platform.util.StringUtils;
import wt.fc.PersistenceHelper;
import wt.org.WTUser;
import wt.pom.Transaction;
import wt.services.StandardManager;
import wt.util.WTException;

public class StandardTaskService extends StandardManager implements TaskService {

	public static StandardTaskService newStandardTaskService() throws WTException {
		StandardTaskService instance = new StandardTaskService();
		instance.initialize();
		return instance;
	}

	@Override
	public void pre(Map<String, Object> params) throws Exception {
		ArrayList<String> arr = (ArrayList<String>) params.get("arr");
		String oid = (String) params.get("oid");
		Transaction trs = new Transaction();
		try {
			trs.start();

			Task task = (Task) CommonUtils.persistable(oid);

			ArrayList<PreTaskPostTaskLink> list = TaskHelper.manager.getPreTaskPostTaskLinks(task);
			for (PreTaskPostTaskLink link : list) {
				PersistenceHelper.manager.delete(link);
			}

			for (String o : arr) {
				Task preTask = (Task) CommonUtils.persistable(o);
				PreTaskPostTaskLink link = PreTaskPostTaskLink.newPreTaskPostTaskLink(preTask, task);
				PersistenceHelper.manager.save(link);
			}

			if (task.getTemplate() != null) {
				TemplateHelper.service.calculation(task.getTemplate());
			}

			if (task.getProject() != null) {
				ProjectHelper.service.calculation(task.getProject());
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
	public void disconnect(Map<String, Object> params) throws Exception {
		ArrayList<String> list = (ArrayList<String>) params.get("list");
		Transaction trs = new Transaction();
		try {
			trs.start();

			for (String oid : list) {
				PreTaskPostTaskLink link = (PreTaskPostTaskLink) CommonUtils.persistable(oid);
				PersistenceHelper.manager.delete(link);
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
	public Task complete(Map<String, Object> params) throws Exception {
		String oid = (String) params.get("oid");
		Task task = null;
		Transaction trs = new Transaction();
		try {
			trs.start();

			Timestamp today = DateUtils.today();

			task = (Task) CommonUtils.persistable(oid);
			task.setEndDate(today);
			task.setProgress(100D);
			task.setState(TaskHelper.COMPLETE);
			PersistenceHelper.manager.modify(task);

			_calculation(task);

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

		return task;
	}

	@Override
	public Task stop(Map<String, Object> params) throws Exception {
		String oid = (String) params.get("oid");
		Task task = null;
		Transaction trs = new Transaction();
		try {
			trs.start();

			task = (Task) CommonUtils.persistable(oid);
			task.setState(TaskHelper.STOP);
			PersistenceHelper.manager.modify(task);

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

		return task;
	}

	@Override
	public Task start(Map<String, Object> params) throws Exception {
		String oid = (String) params.get("oid");
		Task task = null;
		Transaction trs = new Transaction();
		try {
			trs.start();
			task = (Task) CommonUtils.persistable(oid);
			Timestamp today = DateUtils.today();
			task.setStartDate(today);
			task.setState(TaskHelper.START);
			PersistenceHelper.manager.modify(task);

			Project project = task.getProject();
			project.setStartDate(today);
			project.setState(TaskHelper.START);
			project = (Project) PersistenceHelper.manager.modify(project);
			_calculation(task);

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
		return task;
	}

	@Override
	public void _calculation(Task task) throws Exception {
		Transaction trs = new Transaction();
		try {
			trs.start();

			Task parentTask = task.getParentTask();
			ArrayList<Task> list = new ArrayList<>();
			if (parentTask != null) {

				list = TaskHelper.manager.getChildren(parentTask);
				int size = list.size();
				double d = 0D;
				for (Task t : list) {
					d += t.getProgress();
				}

				Timestamp today = DateUtils.today();
				double progress = (double) (d / size);
				parentTask.setProgress(progress);

				if (parentTask.getStartDate() == null) {
					parentTask.setStartDate(today);
					parentTask.setState(TaskHelper.START);
				}

				if (progress == 100D) {
					parentTask.setState(TaskHelper.COMPLETE);
					parentTask.setEndDate(today);
				}

				PersistenceHelper.manager.modify(parentTask);
				_calculation(parentTask);

			}

			Project project = task.getProject();
			project = (Project) PersistenceHelper.manager.refresh(project);
			ArrayList<Task> ll = TaskHelper.manager.getAllProjectTask(project);
			int size = ll.size();
			double d = 0D;
			for (Task t : ll) {
				d += t.getProgress();
			}
			if (size == 0) {
				project.setProgress(0D);
			} else {
				project.setProgress((double) (d / size));
				project.setState("완료됨");
				project.setEndDate(DateUtils.today());
			}
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
	public void progress(Map<String, Object> params) throws Exception {
		String oid = (String) params.get("oid");
		String progress = (String) params.get("progress");
		Task task = null;
		Transaction trs = new Transaction();
		try {
			trs.start();

			double pp = Double.parseDouble(progress);

			task = (Task) CommonUtils.persistable(oid);
			if (pp == 100D) {
				task.setProgress(pp);
				task.setState("완료됨");
				task.setEndDate(DateUtils.today());
			} else {
				task.setProgress(pp);
			}

			PersistenceHelper.manager.modify(task);

			Project project = task.getProject();
			PersistenceHelper.manager.modify(project);

			_calculation(task);

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
	public void role(Map<String, Object> params) throws Exception {
		ArrayList<String> arr = (ArrayList<String>) params.get("arr"); // wtuser
		Transaction trs = new Transaction();
		try {
			trs.start();

			for (int i = 0; i < arr.size(); i++) {
				String v = (String) arr.get(i);
				String[] ss = v.split("&");
				String s1 = ss[0]; // loid
				String s2 = ss[1]; // useroid
				if (s2.indexOf("WTUser") > -1) {
					WTUser user = (WTUser) CommonUtils.persistable(s2);
					TaskRoleLink link = (TaskRoleLink) CommonUtils.persistable(s1);
					link.setUser(user);
					PersistenceHelper.manager.modify(link);
				}
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
	public void dependency(Project project) throws Exception {
		Transaction trs = new Transaction();
		try {
			trs.start();

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
	public Task modify(Map<String, Object> params) throws Exception {
		Task task = null;
		String name = (String) params.get("name");
		String planStartDate = (String) params.get("planStartDate");
		String planEndDate = (String) params.get("planEndDate");
		String duration = (String) params.get("duration");
		String labor = (String) params.get("labor");
		String material = (String) params.get("material");
		String etc = (String) params.get("etc");
		String oid = (String) params.get("oid");
		String description = (String) params.get("description");
		Transaction trs = new Transaction();
		try {
			trs.start();

			task = (Task) CommonUtils.persistable(oid);
			task.setName(name);
			task.setDescription(description);

			Timestamp start = DateUtils.startTimestamp(planStartDate);
			task.setPlanStartDate(start);

			Timestamp end = DateUtils.startTimestamp(planEndDate);
			task.setPlanEndDate(end);
			task.setDuration(Integer.parseInt(duration));

			if (StringUtils.isNotNull(labor)) {
				task.setLabor(Double.parseDouble(labor.replace(",", "")));
			}

			if (StringUtils.isNotNull(etc)) {
				task.setEtc(Double.parseDouble(etc.replaceAll(",", "")));
			}

			if (StringUtils.isNotNull(material)) {
				task.setMaterial(Double.parseDouble(material.replaceAll(",", "")));
			}

			PersistenceHelper.manager.modify(task);

			ProjectHelper.service.calculation(task.getProject());

			// 돈 계산
			TaskHelper.manager.sum(task);

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
		return task;
	}
}
