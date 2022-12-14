package platform.dist.entity;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;
import net.sf.json.JSONArray;
import platform.dist.service.DistHelper;
import platform.util.CommonUtils;

@Getter
@Setter
public class DistDTO {
	private String oid;
	private String name;
	private String number;
	private String description;
	private String distributor;
	private int duration;
	private String creator;
	private String createdDate;
	private String material_type;
	private ArrayList<DistPartColumns> partList = new ArrayList<DistPartColumns>();
	private ArrayList<String> secondary = new ArrayList<String>();
	private JSONArray partJson = new JSONArray();

	public DistDTO() {

	}

	public DistDTO(Dist dist) throws Exception {
		setOid(CommonUtils.oid(dist));
		setName(dist.getName());
		setNumber(dist.getNumber());
		setDescription(dist.getDescription());
//		setDistributor(dist.getDistributor());
		setDuration(dist.getDuration());
		setCreatedDate(dist.getCreateTimestamp().toString().substring(0, 10));
		setCreator(dist.getOwnership().getOwner().getFullName());
		setMaterial_type(dist.getMaterial_type());
		setPartJson(JSONArray.fromObject(DistHelper.manager.getParts(dist)));
	}
}
