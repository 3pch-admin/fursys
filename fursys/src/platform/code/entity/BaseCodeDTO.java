package platform.code.entity;

import platform.util.CommonUtils;

public class BaseCodeDTO {

	private String oid;
	private String poid;
	private String code;
	private String name;
	private String description;
	private int sort;
	private boolean enable;
	private String parentCodeType;
	private String parentCode;
	private String parentName;
	private String codeType;

	public BaseCodeDTO() {

	}

	public BaseCodeDTO(BaseCode baseCode) throws Exception {
		setOid(CommonUtils.oid(baseCode));
		setPoid(baseCode.getParent() != null ? CommonUtils.oid(baseCode.getParent()) : "");
		setCode(baseCode.getCode());
		setName(baseCode.getName());
		setDescription(baseCode.getDescription());
		setSort(baseCode.getSort());
		setEnable(baseCode.getEnable());
		setParentCodeType(baseCode.getParent() != null ? baseCode.getParent().getCodeType().getDisplay() : "");
		setParentCode(baseCode.getParent() != null ? baseCode.getParent().getCode() : "");
		setParentName(baseCode.getParent() != null ? baseCode.getParent().getName() : "");
		setCodeType(baseCode.getCodeType().getDisplay());
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getPoid() {
		return poid;
	}

	public void setPoid(String poid) {
		this.poid = poid;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public String getParentCodeType() {
		return parentCodeType;
	}

	public void setParentCodeType(String parentCodeType) {
		this.parentCodeType = parentCodeType;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getCodeType() {
		return codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

}
