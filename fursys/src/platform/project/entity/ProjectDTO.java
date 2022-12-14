package platform.project.entity;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;
import platform.code.service.BaseCodeHelper;
import platform.project.service.ProjectHelper;
import platform.project.task.entity.Task;
import platform.project.task.service.TaskHelper;
import platform.util.DateUtils;
import platform.util.StringUtils;

@Getter
@Setter
public class ProjectDTO {

	private Project project;
	private String oid;
	private String name;
	private String number;
	private String state;
	private String description;
	private String customer;
	private String customerNm;
	private String pm;
	private int duration;
	private String projectType;
	private String projectTypeNm;
	private double budget;
	private double progress;
	private String creator;
	private String createdDate;
	private String planStartDate;
	private String planEndDate;
	private String startDate;
	private String endDate;
	private ArrayList<Task> childrens = new ArrayList<>();
	private ArrayList<ProjectRoleLink> roleLinks = new ArrayList<>();
	private boolean isMember = false;

	private double labor = 0D;
	private double material = 0D;
	private double etc = 0D;
	private double total = 0D;

	public ProjectDTO() {

	}

	public ProjectDTO(Project project) throws Exception {
		setProject(project);
		setOid(project.getPersistInfo().getObjectIdentifier().getStringValue());
		setName(project.getName());
		setDescription(StringUtils.convertToStr(project.getDescription(), ""));
		setNumber(project.getNumber());
		setState(project.getState());
		setBudget(project.getBudget());
		setDuration(DateUtils.getDuration(project.getPlanStartDate(), project.getPlanEndDate()));
		setProgress(project.getProgress());
		setProjectType(project.getProjectType());
		setCustomer(project.getCustomer());
		setPm(project.getPm().getFullName());
		setCreatedDate(project.getCreateTimestamp().toString().substring(0, 10));
		setCreator(project.getOwnership().getOwner().getFullName());
		setCustomerNm(BaseCodeHelper.manager.getNameByCodeTypeAndCode("CUSTOMER", this.customer));
		setProjectTypeNm(BaseCodeHelper.manager.getNameByCodeTypeAndCode("PROJECTTYPE", this.projectType));
		setPlanStartDate(project.getPlanStartDate().toString().substring(0, 10));
		setPlanEndDate(project.getPlanEndDate().toString().substring(0, 10));
		setStartDate(project.getStartDate() != null ? project.getStartDate().toString().substring(0, 10) : "");
		setEndDate(project.getEndDate() != null ? project.getEndDate().toString().substring(0, 10) : "");
		setChildrens(ProjectHelper.manager.getTasks(project));
		setRoleLinks(TaskHelper.manager.getProjectRoleLink(project));
		setEtc(project.getEtc());
		setLabor(project.getLabor());
		setMaterial(project.getMaterial());
	}
}
