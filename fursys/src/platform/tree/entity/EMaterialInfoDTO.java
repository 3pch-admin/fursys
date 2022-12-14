package platform.tree.entity;

import platform.util.CommonUtils;

public class EMaterialInfoDTO {
	private String oid;
	private String code;
	private int cat_rank;
	private int rank;
	private String material;
	private String material_name;
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
	
	public EMaterialInfoDTO() {
		
	}
	
	public EMaterialInfoDTO(EMaterialInfo EMaterialInfo) throws Exception {
		setOid(CommonUtils.oid(EMaterialInfo));
		setCode(EMaterialInfo.getCode());
		setCat_rank(EMaterialInfo.getCat_rank());
		setRank(EMaterialInfo.getRank());
		setMaterial(EMaterialInfo.getMaterial());
		setMaterial_name(EMaterialInfo.getMaterial_name());
		setCat_l(EMaterialInfo.getCat_l());
		setErp_l(EMaterialInfo.getErp_l());
		setCat_m(EMaterialInfo.getCat_m());
		setErp_m(EMaterialInfo.getErp_m());
		setCat_s(EMaterialInfo.getCat_s());
		setErp_s(EMaterialInfo.getErp_s());
		setMaterialType(EMaterialInfo.getMaterialType());
		setMaterialTypeCode(EMaterialInfo.getMaterialTypeCode());
		setCreatedDate(EMaterialInfo.getCreateTimestamp().toString().substring(0, 10));
		setCreator(EMaterialInfo.getOwnership().getOwner().getFullName());
	}

	public String getMaterial_name() {
		return material_name;
	}

	public void setMaterial_name(String material_name) {
		this.material_name = material_name;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getCat_rank() {
		return cat_rank;
	}

	public void setCat_rank(int cat_rank) {
		this.cat_rank = cat_rank;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getCat_l() {
		return cat_l;
	}

	public void setCat_l(String cat_l) {
		this.cat_l = cat_l;
	}

	public String getErp_l() {
		return erp_l;
	}

	public void setErp_l(String erp_l) {
		this.erp_l = erp_l;
	}

	public String getCat_m() {
		return cat_m;
	}

	public void setCat_m(String cat_m) {
		this.cat_m = cat_m;
	}

	public String getErp_m() {
		return erp_m;
	}

	public void setErp_m(String erp_m) {
		this.erp_m = erp_m;
	}

	public String getCat_s() {
		return cat_s;
	}

	public void setCat_s(String cat_s) {
		this.cat_s = cat_s;
	}

	public String getErp_s() {
		return erp_s;
	}

	public void setErp_s(String erp_s) {
		this.erp_s = erp_s;
	}

	public String getMaterialType() {
		return materialType;
	}

	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}

	public String getMaterialTypeCode() {
		return materialTypeCode;
	}

	public void setMaterialTypeCode(String materialTypeCode) {
		this.materialTypeCode = materialTypeCode;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

}