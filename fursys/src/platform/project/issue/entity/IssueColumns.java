package platform.project.issue.entity;

import lombok.Getter;
import lombok.Setter;
import platform.code.service.BaseCodeHelper;
import platform.util.CommonUtils;

@Getter
@Setter
public class IssueColumns {

	private String oid;
	private int no;
	private String name;
	private String description;
	private String manager;
	private String creator;
	private String createdDate;
	private String state;
	private String issueType;
	private String issueTypeNm;
	private String projectName;
	private String taskName;
	private String poid;
	private String toid;
	private String tstate;

	public IssueColumns() {

	}

	public IssueColumns(Issue issue) throws Exception {
		setOid(CommonUtils.oid(issue));
		setName(issue.getName());
		setDescription(issue.getDescription());
		setManager(issue.getManager() != null ? issue.getManager().getFullName() : "");
		setCreatedDate(issue.getCreateTimestamp().toString().substring(0, 10));
		setCreator(issue.getOwnership().getOwner().getFullName());
		setState(issue.getState());
		setIssueType(issue.getIssueType());
		setIssueTypeNm(BaseCodeHelper.manager.getNameByCodeTypeAndCode("ISSUETYPE", this.issueType));
		setProjectName(issue.getProject().getName());
		setTaskName(issue.getTask().getName());
		setPoid(issue.getProject().getPersistInfo().getObjectIdentifier().getStringValue());
		setToid(issue.getTask().getPersistInfo().getObjectIdentifier().getStringValue());
		setTstate(issue.getTask().getState());
	}
}
