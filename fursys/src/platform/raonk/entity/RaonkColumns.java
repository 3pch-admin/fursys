package platform.raonk.entity;

import lombok.Getter;
import lombok.Setter;
import platform.util.CommonUtils;

@Setter
@Getter
public class RaonkColumns {
	private String oid;
	private String name;
//	private String location;
//	private String raonkType;
//	private String description;
	private String enable;
	private String creator;
	private String createdDate;

	public RaonkColumns() {

	}

	public RaonkColumns(Raonk raonk) throws Exception {
		setOid(CommonUtils.oid(raonk));
		setName(raonk.getName());
//		setRaonkType(raonk.getRaonkType());
//		setDescription(raonk.getDescription());
		setEnable(raonk.getEnable() == true ? "사용" : "사용안함");
		setCreator(raonk.getOwnership().getOwner().getFullName());
		setCreatedDate(raonk.getCreateTimestamp().toString().substring(0, 10));
//		setLocation(raonk.getFolder().getLocation());
	}

}
