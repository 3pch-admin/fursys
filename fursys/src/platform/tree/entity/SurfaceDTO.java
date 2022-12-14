package platform.tree.entity;

import platform.util.CommonUtils;

public class SurfaceDTO {

	private String oid;
	private String code;
	private int rank;
	private String material;
	private String createdDate;
	private String creator;

	public SurfaceDTO() {

	}

	public SurfaceDTO(Surface surface) throws Exception {
		setOid(CommonUtils.oid(surface));
		setCode(surface.getCode());
		setRank(surface.getRank());
		setMaterial(surface.getMaterial());
		setCreatedDate(surface.getCreateTimestamp().toString().substring(0, 10));
		setCreator(surface.getOwnership().getOwner().getFullName());
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

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
}
