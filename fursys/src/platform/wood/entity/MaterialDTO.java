package platform.wood.entity;

import platform.util.CommonUtils;

public class MaterialDTO {

	private String oid;
	private String code;
	private String rank;
	private String material;
	private String cat_l;
	private String erp_l;
	private String cat_m;
	private String erp_m;
	private String cat_s;
	private String erp_s;
	private String materialType;
	private String materialTypeCode;
	private String createdDate;

	public MaterialDTO() {

	}

	public MaterialDTO(Material material) throws Exception {
		setOid(CommonUtils.oid(material));
		setCode(material.getCode());
		setRank(material.getRank());
		setMaterial(material.getMaterial());
		setCat_l(material.getCat_l());
		setErp_l(material.getErp_l());
		setCat_m(material.getCat_m());
		setErp_m(material.getErp_m());
		setCat_s(material.getCat_s());
		setErp_s(material.getErp_s());
		setMaterialType(material.getMaterialType());
		setMaterialTypeCode(material.getMaterialTypeCode());
		setCreatedDate(material.getCreateTimestamp().toString().substring(0, 10));
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

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
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
