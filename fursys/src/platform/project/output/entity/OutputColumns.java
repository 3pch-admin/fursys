package platform.project.output.entity;

import lombok.Getter;
import lombok.Setter;
import platform.util.CommonUtils;
import wt.doc.WTDocument;
import wt.session.SessionHelper;

@Getter
@Setter
public class OutputColumns {

	private String oid;
	private int no;
	private String name;
	private String location;
	private String docName;
	private String createdDate;
	private String creator;
	private String taskName;
	private String projectName;
	private String templateName;
	private String description;
	private String version;
	private String state;
	private String toid;
	private String poid;
	private String doid;

	public OutputColumns() {

	}

	public OutputColumns(Output output) throws Exception {
		WTDocument d = output.getDocument();
		setOid(CommonUtils.oid(output));
		setName(output.getName());
		setLocation(output.getLocation());
		setDocName(d != null ? d.getName() : "");
		setCreatedDate(d != null ? d.getCreateTimestamp().toString().substring(0, 10) : "");
		setCreator(d != null ? d.getCreatorFullName() : "");
		setTaskName(output.getTask().getName());
		setTemplateName(output.getTemplate() != null ? output.getTemplate().getName() : "");
		setProjectName(output.getProject() != null ? output.getProject().getName() : "");
		setDescription(output.getDescription());
		setState(d != null ? d.getLifeCycleState().getDisplay(SessionHelper.getLocale()) : "");
		setVersion(
				d != null
						? d.getVersionIdentifier().getSeries().getValue() + "."
								+ d.getIterationIdentifier().getSeries().getValue()
						: "");
		setToid(CommonUtils.oid(output.getTask()));
		setPoid(CommonUtils.oid(output.getTask().getProject()));
		setDoid(d != null ? CommonUtils.oid(d) : "");
	}
}
