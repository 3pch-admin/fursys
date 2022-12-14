package platform.tree.entity;

import platform.util.CommonUtils;

public class ESurfaceDTO {

	private String oid;
	private String code;
	private int rank;
	private String surface;
	private String createdDate;
	private String creator;
	private String DTMG_esurface;

	public ESurfaceDTO() {

	}

	public ESurfaceDTO(ESurface esurface) throws Exception {
		setOid(CommonUtils.oid(esurface));
		setCode(esurface.getCode());
		setRank(esurface.getRank());
		setSurface(esurface.getSurface());
		setCreatedDate(esurface.getCreateTimestamp().toString().substring(0, 10));
		setCreator(esurface.getOwnership().getOwner().getFullName());
		setDTMG_esurface(esurface.getDTMG_esurface());
	}

	public String getDTMG_esurface() {
		return DTMG_esurface;
	}

	public void setDTMG_esurface(String dTMG_esurface) {
		DTMG_esurface = dTMG_esurface;
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

	public String getSurface() {
		return surface;
	}

	public void setSurface(String surface) {
		this.surface = surface;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	
}