package platform.project.task.entity;

import lombok.Getter;
import lombok.Setter;
import platform.util.CommonUtils;
import platform.util.DateUtils;

@Getter
@Setter
public class TaskColumns {

	private String oid;
	private int no;
	private String taskName;
	private String projectName;
	private String state;
	private double progress;
	private String planStartDate;
	private String planEndDate;
	private String startDate;
	private String endDate;
	private int duration;
	private String poid;
	private String user;
	private String role;

	public TaskColumns() {
	}

	public TaskColumns(Task task) throws Exception {
		setOid(CommonUtils.oid(task));
		setTaskName(task.getName());
		setProjectName(task.getProject().getName());
		setState(task.getState());
		setProgress(task.getProgress());
		setPlanStartDate(task.getPlanStartDate().toString().substring(0, 10));
		setPlanEndDate(task.getPlanEndDate().toString().substring(0, 10));
		setStartDate(task.getStartDate() != null ? task.getStartDate().toString().substring(0, 10) : "");
		setEndDate(task.getEndDate() != null ? task.getEndDate().toString().substring(0, 10) : "");
		setDuration(DateUtils.getDuration(task.getPlanStartDate(), task.getPlanEndDate()));
		setPoid(task.getProject().getPersistInfo().getObjectIdentifier().getStringValue());
	}
}
