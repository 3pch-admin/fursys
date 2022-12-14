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

	public DistributorUserDTO() {

	}

	public DistributorUserDTO(DistributorUser distUser) throws Exception {
		setOid(CommonUtils.oid(distUser));
		setUserId(distUser.getUserId());
		setUserName(distUser.getUserName());
		setEmail(StringUtils.convertToStr(distUser.getEmail(), ""));
		if ("IN".equals(distUser.getType())) {
			setName(BaseCodeHelper.manager.getNameByCodeTypeAndCode("FACTORY_CODE", distUser.getName()));
		} else {
			setName(distUser.getDistributor().getName());
		}

		setDescription(distUser.getDescription());
		setNumber(distUser.getNumber());
		setEnable(distUser.getEnable());
		setType("IN".equals(distUser.getType()) == true ? "사내" : "사외");
	}
}