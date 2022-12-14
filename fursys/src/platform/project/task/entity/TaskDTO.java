package platform.project.task.entity;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;
import platform.project.entity.Project;
import platform.project.issue.entity.Issue;
import platform.project.issue.service.IssueHelper;
import platform.project.output.entity.Output;
import platform.project.output.service.OutputHelper;
import platform.project.task.service.TaskHelper;
import platform.project.template.entity.Template;
import platform.util.CommonUtils;
import platform.util.DateUtils;
import platform.util.StringUtils;

@Getter
@Setter
public class TaskDTO {

	private Task task;
	private Template template;
	private Project project;
	private String oid;
	private String name;
	private String state;
	private int duration;
	private String planStartDate;
	private String planEndDate;
	private String startDate;
	private String endDate;
	private String creator;
	private String createdDate;
	private double progress;
	private String description;
	private ArrayList<Output> outputs = new ArrayList<>();
	private ArrayList<Issue> issues = new ArrayList<>();
	private ArrayList<PreTaskPostTaskLink> pres = new ArrayList<>();
	private ArrayList<TaskRoleLink> roleLinks = new ArrayList<>();

	private double etc;
	private double material;
	private double labor;

	private boolean isPjtStart = false;
	private boolean isStart = false;
	private boolean isComplete = false;
	private boolean isStop = false;
	private boolean isReady = false;

	private boolean isLast = false;

	public TaskDTO() {

	}

	public TaskDTO(Task task) throws Exception {
		setTask(task);
		setTemplate(task.getTemplate());
		setProject(task.getProject());
		setOid(CommonUtils.oid(task));
		setName(task.getName());
		setState(task.getState());
//		setDuration(task.getDuration());
		setDuration(DateUtils.getDuration(task.getPlanStartDate(), task.getPlanEndDate()));
		setPlanStartDate(task.getPlanStartDate() != null ? task.getPlanStartDate().toString().substring(0, 10) : "");
		setPlanEndDate(task.getPlanEndDate() != null ? task.getPlanEndDate().toString().substring(0, 10) : "");
		setStartDate(task.getStartDate() != null ? task.getStartDate().toString().substring(0, 10) : "");
		setEndDate(task.getEndDate() != null ? task.getEndDate().toString().substring(0, 10) : "");
		setCreator(task.getOwnership().getOwner().getFullName());
		setCreatedDate(task.getCreateTimestamp().toString().substring(0, 10));
		setProgress(task.getProgress());
		setDescription(StringUtils.convertToStr(task.getDescription(), ""));
		setOutputs(OutputHelper.manager.getOutputs(this.template, this.project, this.task));
		setIssues(IssueHelper.manager.getIssues(this.task));
		setPres(TaskHelper.manager.getPreTaskPostTaskLinks(this.task));
		setRoleLinks(TaskHelper.manager.getTaskRoleLink(task));
		setAuth(task);
		setEtc(task.getEtc());
		setLabor(task.getLabor());
		setMaterial(task.getMaterial());
		setLast(TaskHelper.manager.isLast(task));
	}

	private void setAuth(Task task) throws Exception {
		Project pjt = task.getProject();
		if (pjt != null && pjt.getState().equals("진행중")) {
			isPjtStart = true;
		}
		String state = task.getState();
		if (TaskHelper.READY.equals(state)) {
			isReady = true;
		} else if (TaskHelper.STOP.equals(state)) {
			isStop = true;
		} else if (TaskHelper.COMPLETE.equals(state)) {
			isComplete = true;
		} else if (TaskHelper.START.equals(state)) {
			isStart = true;
		}
	}
}
