package platform.tree.entity;

import platform.util.CommonUtils;

public class EdgeDTO {

	private String oid;
	private String code;
	private String Dtmg_treatment;
	private String etc;
	private int rank;
	private String edgeType;
	private String createdDate;
	private String creator;

	public EdgeDTO() {

	}

	public EdgeDTO(Edge edge) throws Exception {
		setOid(CommonUtils.oid(edge));
		setCode(edge.getCode());
		setRank(edge.getRank());
		setDtmg_treatment(edge.getDtmg_treatment());
		setEdgeType(edge.getEdgeType());
		setEtc(edge.getEtc());
		setCreatedDate(edge.getCreateTimestamp().toString().substring(0, 10));
		setCreator(edge.getOwnership().getOwner().getFullName());
	}

	
	public String getEtc() {
		return etc;
	}

	public void setEtc(String etc) {
		this.etc = etc;
	}

	public String getDtmg_treatment() {
		return Dtmg_treatment;
	}

	public void setDtmg_treatment(String dtmg_treatment) {
		Dtmg_treatment = dtmg_treatment;
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

	public String getEdgeType() {
		return edgeType;
	}

	public void setEdgeType(String edgeType) {
		this.edgeType = edgeType;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
}
