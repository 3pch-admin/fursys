package platform.dist.entity;

import lombok.Getter;
import lombok.Setter;
import platform.code.service.BaseCodeHelper;
import platform.util.CommonUtils;

@Getter
@Setter
public class DistributorUserColumns {

	private String oid;
	private int no;
	private String name;
	private String number;
	private String userId;
	private String userName;
	private String email;
	private String description;
	private String creator;
	private String createdDate;
	private String type;
	private String enable;

	public DistributorUserColumns() {

	}

	public DistributorUserColumns(DistributorUser distUser) throws Exception {
		setOid(CommonUtils.oid(distUser));

		if ("IN".equals(distUser.getType())) {
			setName(BaseCodeHelper.manager.getNameByCodeTypeAndCode("FACTORY_CODE", distUser.getName()));
		} else {
			setName(distUser.getName());
			if( distUser.getDistributor() != null) {
				setName(distUser.getDistributor().getName());
			}
		}
		setNumber(distUser.getNumber());
		setDescription(distUser.getDescription());
		setCreatedDate(distUser.getCreateTimestamp().toString().substring(0, 10));
		setCreator(distUser.getOwnership().getOwner().getFullName());
		setType("IN".equals(distUser.getType()) == true ? "사내" : "사외");
		setEnable(distUser.getEnable() ? "사용" : "사용안함");
		setUserId(distUser.getUserId());
		setUserName(distUser.getUserName());
		setEmail(distUser.getEmail());
	}
}
