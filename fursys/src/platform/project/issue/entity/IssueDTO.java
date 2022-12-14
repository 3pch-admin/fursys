package platform.project.issue.entity;

import lombok.Getter;
import lombok.Setter;
import platform.code.service.BaseCodeHelper;
import platform.project.entity.Project;
import platform.project.task.entity.Task;
import platform.util.CommonUtils;

@Getter
@Setter
public class IssueDTO {

	private String oid;
	private String name;
	private String description;
	private String manager;
	private String creator;
	private String createdDate;
	private String state;
	private String issueType;
	private String issueTypeNm;
	private Project project;
	private Task task;
	private Issue issue;
	private boolean isComplete = false;
	private Solution solution;

	public IssueDTO() {

	}

	public IssueDTO(Issue issue) throws Exception {
		setOid(CommonUtils.oid(issue));
		setName(issue.getName());
		setDescription(issue.getDescription());
		setManager(issue.getManager() != null ? issue.getManager().getFullName() : "");
		setCreatedDate(issue.getCreateTimestamp().toString().substring(0, 10));
		setCreator(issue.getOwnership().getOwner().getFullName());
		setState(issue.getState());
		setIssueType(issue.getIssueType());
		setIssueTypeNm(BaseCodeHelper.manager.getNameByCodeTypeAndCode("ISSUETYPE", this.issueType));
		setProject(issue.getProject());
		setTask(issue.getTask());
		setIssue(issue);
		setSolution(issue.getSolution());
		setComplete(issue.getState().equals("해결완료"));
	}
}