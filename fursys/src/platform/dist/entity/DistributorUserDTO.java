package platform.dist.entity;

import lombok.Getter;
import lombok.Setter;
import platform.code.service.BaseCodeHelper;
import platform.util.CommonUtils;
import platform.util.StringUtils;

@Getter
@Setter
public class DistributorUserDTO {

	private String oid;
	private String userId;
	private String userName;
	private String email;
	private String name;
	private String description;
	private String number;
	private Boolean enable;
	private String type;
	private String enableString;
	public DistributorUserDTO() {

	}

	public DistributorUserDTO(DistributorUser distUser) throws Exception {
		setOid(CommonUtils.oid(distUser));
		setUserId(distUser.getUserId());
		setUserName(distUser.getUserName());
		setEmail(StringUtils.convertToStr(distUser.getEmail(), ""));
		setName(distUser.getDistributor().getName());
		if (distUser.getDescription() == null) {
			setDescription("");
		} else {
			setDescription(distUser.getDescription());
		}
		setNumber(distUser.getNumber());
		if( distUser.getEnable()) {
			setEnableString("사용");
		}else {
			setEnableString("사용안함");
		}
		setEnable(distUser.getEnable());
		setType("IN".equals(distUser.getType()) == true ? "사내" : "사외");
	}
}