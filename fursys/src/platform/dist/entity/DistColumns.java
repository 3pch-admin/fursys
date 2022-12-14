package platform.dist.entity;

import lombok.Getter;
import lombok.Setter;
import platform.util.CommonUtils;

@Getter
@Setter
public class DistColumns {

	private String oid;
	private int no;
	private String fileName;
	private String number;
	private String name;
	private String duration;
	private String state;
	private String creator;
	private String createdDate;
	private String types;
	private boolean pdf;
	private boolean dwg;
	private boolean step;
	private String user;
	private String distributor;
	private String doid;
	private String version;
	private String material_type;

	public DistColumns() {

	}

	public DistColumns(Dist dist) throws Exception {
		//Dist dist = link.getDist();
		setOid(CommonUtils.oid(dist));
		setName(dist.getName());
		setNumber(dist.getNumber());
		setState(dist.getState());
		setCreatedDate(dist.getCreateTimestamp().toString().substring(0, 10));
		setCreator(dist.getOwnership().getOwner().getFullName());
		setDuration(dist.getDuration() + "주");
		setMaterial_type(dist.getMaterial_type());
	}
}
