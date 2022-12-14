package platform.project.entity;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import platform.code.service.BaseCodeHelper;
import platform.util.DateUtils;

@Getter
@Setter
public class ProjectColumns {

	private String oid;
	private int no;
	private String name;
	private String number;
	private String state;
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

	public ProjectColumns() {

	}

	public ProjectColumns(Project project) throws Exception {
		setOid(project.getPersistInfo().getObjectIdentifier().getStringValue());
		setName(project.getName());
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
	}
}
