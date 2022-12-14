package platform.tree.entity;

import lombok.Getter;
import lombok.Setter;
import platform.util.CommonUtils;

@Getter
@Setter
public class CombinationDTO {

	private String oid;
	private String code;
	private String basic_code;
	private int width;
	private String material;
	private String surface_code;
	private int rank;
	private String combination;
	private String cat_l;
	private String erp_l;
	private String cat_m;
	private String erp_m;
	private String cat_s;
	private String erp_s;
	private String materialType;
	private String materialTypeCode;
	private String createdDate;
	private String creator;

	public CombinationDTO() {

	}

	public CombinationDTO(Combination combination) throws Exception {
		setOid(CommonUtils.oid(combination));
		setCode(combination.getCode());
		setBasic_code(combination.getBasic_code());
		setWidth(combination.getWidth());
		setMaterial(combination.getMaterial());
		setSurface_code(combination.getSurface_code());
		setRank(combination.getRank());
		setCombination(combination.getCombination());
		setCat_l(combination.getCat_l());
		setErp_l(combination.getErp_l());
		setCat_m(combination.getCat_m());
		setErp_m(combination.getErp_m());
		setCat_s(combination.getCat_s());
		setErp_s(combination.getErp_s());
		setMaterialType(combination.getMaterialType());
		setMaterialTypeCode(combination.getMaterialTypeCode());
		setCreatedDate(combination.getCreateTimestamp().toString().substring(0, 10));
		setCreator(combination.getOwnership().getOwner().getFullName());
	}
}
