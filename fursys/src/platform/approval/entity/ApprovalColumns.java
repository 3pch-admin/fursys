package platform.approval.entity;

import lombok.Getter;
import lombok.Setter;
import platform.util.CommonUtils;

@Getter
@Setter
public class ApprovalColumns {

	private int no;
	private String moid;
	private String lineType;
	private String objType;
	private String oid;
	private String name;
	private String state;
	private String submiter;
	private String startTime;
	private String completeTime;

	public ApprovalColumns() {

	}

	public ApprovalColumns(ApprovalMaster master) throws Exception {
		setOid(CommonUtils.oid(master));
		setName(master.getName());
		setObjType(master.getObjType());
		setState(master.getState());
		setSubmiter(master.getOwnership().getOwner().getFullName()); // 마스터 작성자 = 기안자
		setStartTime(master.getCreateTimestamp().toString().substring(0, 10)); // 마스터 작성일 = 기안일
		setCompleteTime(master.getCompleteTime() != null ? master.getCompleteTime().toString().substring(0, 10) : "");
	}

	public ApprovalColumns(ApprovalLine line) throws Exception {
		setOid(CommonUtils.oid(line));
		setObjType(line.getMaster().getObjType());
		setMoid(CommonUtils.oid(line.getMaster()));
		setLineType(line.getLineType());
		setName(line.getMaster().getName());
		setState(line.getState());
		setSubmiter(line.getMaster().getOwnership().getOwner().getFullName()); // 마스터 작성자 = 기안자
		setStartTime(line.getMaster().getCreateTimestamp().toString().substring(0, 10)); // 마스터 작성일 = 기안일
		setCompleteTime(line.getCompleteTime() != null ? line.getCompleteTime().toString().substring(0, 10) : "");
	}
}