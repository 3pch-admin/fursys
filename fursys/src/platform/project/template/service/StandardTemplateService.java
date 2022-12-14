package platform.project.template.service;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import platform.code.entity.BaseCode;
import platform.code.service.BaseCodeHelper;
import platform.project.task.entity.ParentTaskChildTaskLink;
import platform.project.task.entity.PreTaskPostTaskLink;
import platform.project.task.entity.Task;
import platform.project.task.entity.TaskRoleLink;
import platform.project.task.entity.TaskTreeNode;
import platform.project.task.service.TaskHelper;
import platform.project.template.entity.Template;
import platform.project.template.entity.TemplateDTO;
import platform.util.CommonUtils;
import platform.util.DateUtils;
import platform.util.StringUtils;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.org.WTPrincipal;
import wt.ownership.Ownership;
import wt.pom.Transaction;
import wt.services.StandardManager;
import wt.session.SessionHelper;
import wt.util.WTException;

public class StandardTemplateService extends StandardManager implements TemplateService {

	private final String startDate = "2030-01-01";

	public static StandardTemplateService newStandardTemplateService() throws WTException {
		StandardTemplateService instance = new StandardTemplateService();
		instance.initialize();
		return instance;
	}

	@Override
	public Template create(TemplateDTO dto) throws Exception {
		Template template = null;
		Transaction trs = new Transaction();
		try {
			trs.start();

			WTPrincipal prin = SessionHelper.manager.getPrincipal();

			template = Template.newTemplate();
			template.setOwnership(Ownership.newOwnership(prin));
			template.setName(dto.getName());
			template.setNumber(TemplateHelper.manager.getNextNumber());
			template.setDescription(dto.getDescription());
			template.setEnable(dto.isEnable());
			template.setCompany(dto.getCompany());
			template.setPlanStartDate(DateUtils.startTimestamp(startDate));
			template.setPlanEndDate(DateUtils.endTimestamp(startDate));
			template.setDuration(1);
			PersistenceHelper.manager.save(template);

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
		return template;
	}

	@Override
	public Template delete(String oid) throws Exception {
		Template template = null;
		Transaction trs = new Transaction();
		try {
			trs.start();

			template = (Template) CommonUtils.persistable(oid);
			template = (Template) PersistenceHelper.manager.delete(template);

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
		return template;
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
			Template template = null;
			for (TaskTreeNode node : nodes) {
				String oid = node.getOid();
				ArrayList<TaskTreeNode> childrens = node.getChildren();
				// 템플릿은 새로 생성없음..

				String name = node.getName();
				String d = node.getDescription();
				template = (Template) CommonUtils.persistable(oid);
				template.setName(name);
				template.setDescription(d);
				PersistenceHelper.manager.modify(template);
				saveTree(template, null, childrens);
			}

			template = (Template) PersistenceHelper.manager.refresh(template);
			calculation(template);

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
	public void saveTree(Template template, Task parentTask, ArrayList<TaskTreeNode> childrens) throws Exception {
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

				int sort = TaskHelper.manager.getSort(template);
				Task t = null;
				if (isNew) {
					t = Task.newTask();
					t.setName(name);
					t.setDepth(depth);
					t.setDescription(description);
					t.setDuration(duration);
					t.setOwnership(Ownership.newOwnership(prin));
					t.setParentTask(parentTask);
					t.setTemplate(template);
					Calendar ca = Calendar.getInstance();
					ca.setTimeInMillis(DateUtils.startTimestamp(startDate).getTime());
					t.setPlanStartDate(new Timestamp(ca.getTime().getTime()));

					for (int i = 0; i < duration; i++) {
						ca.add(Calendar.DATE, 1);
					}
					t.setPlanEndDate(new Timestamp(ca.getTime().getTime()));
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
					t.setDuration(duration);
					t.setOwnership(Ownership.newOwnership(prin));
					t.setParentTask(parentTask);
					t.setTemplate(template);
					Calendar ca = Calendar.getInstance();
					ca.setTimeInMillis(DateUtils.startTimestamp(startDate).getTime());
					t.setPlanStartDate(new Timestamp(ca.getTime().getTime()));

					for (int i = 0; i < duration; i++) {
						ca.add(Calendar.DATE, 1);
					}
					t.setPlanEndDate(new Timestamp(ca.getTime().getTime()));
					t.setSort(sort);
					t.setEtc(0D);
					t.setMaterial(0D);
					t.setLabor(0D);
					PersistenceHelper.manager.modify(t);
				}

				ArrayList<TaskRoleLink> links = TaskHelper.manager.getTaskRoleLink(template, t);
				for (TaskRoleLink l : links) {
					PersistenceHelper.manager.delete(l);
				}
				if (StringUtils.isNotNull(roles)) {
					String[] rr = roles.split(",");
					for (String r : rr) {
						BaseCode roleCode = BaseCodeHelper.manager.getBaseCodeByCodeTypeAndCode("PROJECTROLE", r);
						TaskRoleLink link = TaskRoleLink.newTaskRoleLink(t, roleCode);
						link.setTemplate(template);
						link.setProject(null);
						PersistenceHelper.manager.save(link);
					}
				}
				saveTree(template, t, n);
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
	public void calculation(Template template) throws Exception {
		Transaction trs = new Transaction();
		try {
			trs.start();

			// 선후행 재배치
			ArrayList<Task> list = TaskHelper.manager.getAllTemplateTask(template);
//			ArrayList<Task> list = TemplateHelper.manager.expand(template, new ArrayList<Task>())

			for (Task task : list) {
				// 후행 태스크의 계획 시작일
				Timestamp preStart = null;
				// 후행 태스크의 계획 종료일
				Timestamp preEnd = null;

				boolean isEdit = false;

				ArrayList<PreTaskPostTaskLink> link = TaskHelper.manager.getPreTaskPostTaskLinks(task);
				for (PreTaskPostTaskLink data : link) {
					Task preTask = data.getPreTask();
					Task postTask = data.getPostTask();
					int duration = postTask.getDuration();

					// 선행 태스크의 계획 종료일 세팅
					Calendar sCa = Calendar.getInstance();
					sCa.setTime(preTask.getPlanEndDate());

					// 선후행 간격 설정
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
					task.setDuration(DateUtils.getDuration(start, end));
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
				template.setPlanStartDate(start);
				template.setPlanEndDate(end);
				template.setDuration(DateUtils.getDuration(start, end));
				PersistenceHelper.manager.modify(template);
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
	public Template modify(TemplateDTO dto) throws Exception {
		Template template = null;
		Transaction trs = new Transaction();
		try {
			trs.start();

			template = (Template) CommonUtils.persistable(dto.getOid());
			template.setName(dto.getName());
			template.setDescription(dto.getDescription());
			template.setEnable(dto.isEnable());
			template.setCompany(dto.getCompany());
			PersistenceHelper.manager.modify(template);

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
		return template;
	}

	@Override
	public void reset(Map<String, Object> params) throws Exception {
		Template template = null;
		String oid = (String) params.get("oid");
		Transaction trs = new Transaction();
		try {
			trs.start();

			Timestamp planStartDate = DateUtils.startTimestamp(startDate);

			template = (Template) CommonUtils.persistable(oid);
			template.setPlanStartDate(planStartDate);
			template.setPlanEndDate(planStartDate);
			template.setDuration(1);
			PersistenceHelper.manager.modify(template);

			ArrayList<Task> list = TaskHelper.manager.getAllTemplateTask(template);
			for (Task t : list) {
				t.setPlanStartDate(planStartDate);
				t.setPlanEndDate(planStartDate);
				t.setDuration(1);
				t.setParentTask(null);
				PersistenceHelper.manager.modify(t);
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
}
