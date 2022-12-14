package platform.user.entity;

import platform.util.CommonUtils;

public class UserInfo {
	private String oid;
	private String woid;
	private String userId;
	private String userName;
	private String duty;
	private String deptName;
	private String email;
	private String to;

	public UserInfo() {

	}

	public UserInfo(User user) {
		setOid(CommonUtils.oid(user));
		setWoid(CommonUtils.oid(user.getWtUser()));
		setUserId(user.getUserId());
		setUserName(user.getUserName());
		setDuty(user.getDuty() != null ? user.getDuty() : "지정안됨");
		setDeptName(user.getDepartment() != null ? user.getDepartment().getName() : "지정안됨");
		setEmail(user.getEmail() != null ? user.getEmail() : "");
		setTo(user);
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getWoid() {
		return woid;
	}

	public void setWoid(String woid) {
		this.woid = woid;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTo() {
		return to;
	}

	public void setTo(User user) {
		String split = "&";
		StringBuffer sb = new StringBuffer();
		sb.append(oid);
		sb.append(split + woid);
		sb.append(split + userName);
		sb.append(split + userId);
		sb.append(split + duty);
		sb.append(split + deptName);
		sb.append(split + email);
		sb.append(split);
		this.to = sb.toString();
	}
}
