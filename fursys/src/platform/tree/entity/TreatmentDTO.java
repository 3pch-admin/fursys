package platform.tree.entity;

import platform.util.CommonUtils;

public class TreatmentDTO {
	private String oid;
	private String code;
	private int rank;
	private String treatment;
	private String createdDate;
	private String creator;
	private String dtmg_treatment;

	public TreatmentDTO() {

	}

	public TreatmentDTO(Treatment treatment) throws Exception {
		setOid(CommonUtils.oid(treatment));
		setCode(treatment.getCode());
		setRank(treatment.getRank());
		setTreatment(treatment.getTreatment());
		setCreatedDate(treatment.getCreateTimestamp().toString().substring(0, 10));
		setCreator(treatment.getOwnership().getOwner().getFullName());
		setDtmg_treatment(treatment.getDtmg_treatment());
	}

	public String getDtmg_treatment() {
		return dtmg_treatment;
	}

	public void setDtmg_treatment(String dtmg_treatment) {
		this.dtmg_treatment = dtmg_treatment;
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

	public String getTreatment() {
		return treatment;
	}

	public void setTreatment(String treatment) {
		this.treatment = treatment;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

}
