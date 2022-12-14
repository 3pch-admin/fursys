package platform.tree.entity;

import lombok.Getter;
import lombok.Setter;
import platform.util.CommonUtils;

@Getter
@Setter
public class MaterialInfoDTO {

	private String oid;
	private String code;
	private String basic_code;
	private int width;
	private String material_code;
	private int rank;
	private String material;
	private String outside;
	private String creator;
	private String createdDate;

	public MaterialInfoDTO() {

	}

	public MaterialInfoDTO(MaterialInfo info) throws Exception {
		setOid(CommonUtils.oid(info));
		setCode(info.getCode());
		setBasic_code(info.getBasic_code());
		setWidth(info.getWidth());
		setMaterial_code(info.getMaterial_code());
		setRank(info.getRank());
		setMaterial(info.getMaterial());
		setOutside(info.getOutside());
		setCreatedDate(info.getCreateTimestamp().toString().substring(0, 10));
		setCreator(info.getOwnership().getOwner().getFullName());
	}
}
