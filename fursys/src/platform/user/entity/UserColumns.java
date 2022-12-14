package platform.user.entity;

import lombok.Getter;
import lombok.Setter;
import platform.util.CommonUtils;

@Getter
@Setter
public class UserColumns {

	private int no;
	private String woid;
	private String oid;
	private String userId;
	private String userName;
	private String duty;
	private String brand;
	private String deptName;
	private String department;
	private String brandNm;
	private String email;

	public UserColumns() {

	}

	public UserColumns(User user) throws Exception {
		setWoid(CommonUtils.oid(user.getWtUser()));
		setOid(CommonUtils.oid(user));
		setUserId(user.getUserId());
		setUserName(user.getUserName());
		setDuty(user.getDuty() != null ? user.getDuty() : "지정안됨");
		setDeptName(user.getDepartment() != null ? user.getDepartment().getName() : "지정안됨");
		setEmail(user.getEmail() != null ? user.getEmail() : "");
		setBrand(user.getBrand() != null ? user.getBrand().getCode() : "");
		setBrandNm(user.getBrand() != null ? user.getBrand().getName() : "");
		setDepartment(user.getDepartment() != null ? user.getDepartment().getCode() : "");
	}
}