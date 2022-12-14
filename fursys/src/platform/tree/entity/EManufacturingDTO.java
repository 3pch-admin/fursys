package platform.tree.entity;

import platform.util.CommonUtils;

public class EManufacturingDTO {

	
	private String oid;
	private String code;
	private int rank;
	private String manufacturing;
	private String createdDate;
	private String creator;

	public EManufacturingDTO() {

	}

	public EManufacturingDTO(EManufacturing emanufacturing) throws Exception {
		setOid(CommonUtils.oid(emanufacturing));
		setCode(emanufacturing.getCode());
		setRank(emanufacturing.getRank());
		setManufacturing(emanufacturing.getManufacturing());
		setCreatedDate(emanufacturing.getCreateTimestamp().toString().substring(0, 10));
		setCreator(emanufacturing.getOwnership().getOwner().getFullName());
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

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public String getManufacturing() {
		return manufacturing;
	}

	public void setManufacturing(String manufacturing) {
		this.manufacturing = manufacturing;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	
	
}
