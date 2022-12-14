package platform.dist.entity;

import lombok.Getter;
import lombok.Setter;
import platform.code.service.BaseCodeHelper;
import platform.util.CommonUtils;

@Getter
@Setter
public class DistributorDTO {

	private String oid;
	private String userId;
	private String userName;
	private String email;
	private String name;
	private String description;
	private String number;
	private Boolean enable;
	private String type;
	private String factoryNm; // 사업장 한글

	public DistributorDTO() {

	}

	public DistributorDTO(Distributor distributor) throws Exception {
		setOid(CommonUtils.oid(distributor));
		setUserId(distributor.getUserId());
		setUserName(distributor.getUserName());
		setEmail(distributor.getEmail());
		setName(distributor.getName()); //업체명
		setDescription(distributor.getDescription());
		setNumber(distributor.getNumber());
		setEnable(distributor.getEnable());
		setType(distributor.getType());
		
	}
}