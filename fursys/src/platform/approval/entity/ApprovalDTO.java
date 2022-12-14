package platform.approval.entity;

import lombok.Getter;
import lombok.Setter;
import wt.fc.Persistable;

@Getter
@Setter
public class ApprovalDTO {

	private ApprovalLine line;
	private ApprovalMaster master;
	private String moid;
	private String oid;
	private String name;
	private String state;
	private String lineType;
	private String role;
	private String submiter;
	private String startTime;
	private String completeTime;
	private String description;
	private String creator;
	private Persistable per;

	public ApprovalDTO(ApprovalLine line) throws Exception {
		setLine(line);
		setMaster(line.getMaster());
		setMoid(line.getMaster().getPersistInfo().getObjectIdentifier().getStringValue());
		setOid(line.getPersistInfo().getObjectIdentifier().getStringValue());
		setName(line.getMaster().getName());
		setState(line.getState());
		setSubmiter(line.getMaster().getOwnership().getOwner().getFullName());
		setStartTime(line.getMaster().getCreateTimestamp().toString().substring(0, 10));
		setCompleteTime(line.getCompleteTime() != null ? line.getCompleteTime().toString().substring(0, 10) : "");
		setDescription(line.getDescription() != null ? line.getDescription() : "");
		setCreator(line.getOwnership().getOwner().getFullName());
		setPer(line.getMaster().getPersist());
		setLineType(line.getLineType());
		setRole(line.getRole());
	}

	public ApprovalLine getLine() {
		return line;
	}

	public void setLine(ApprovalLine line) {
		this.line = line;
	}

	public ApprovalMaster getMaster() {
		return master;
	}

	public void setMaster(ApprovalMaster master) {
		this.master = master;
	}

	public String getMoid() {
		return moid;
	}

	public void setMoid(String moid) {
		this.moid = moid;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getSubmiter() {
		return submiter;
	}

	public void setSubmiter(String submiter) {
		this.submiter = submiter;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(String completeTime) {
		this.completeTime = completeTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Persistable getPer() {
		return per;
	}

	public void setPer(Persistable per) {
		this.per = per;
	}

	public String getLineType() {
		return lineType;
	}

	public void setLineType(String lineType) {
		this.lineType = lineType;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}