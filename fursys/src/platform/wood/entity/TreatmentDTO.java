package platform.wood.entity;

import platform.util.CommonUtils;

public class TreatmentDTO {

	private String oid;
	private String code;
	private String rank;
	private String treatment;

	public TreatmentDTO() {

	}

	public TreatmentDTO(Treatment treatment) throws Exception {
		setOid(CommonUtils.oid(treatment));
		setCode(treatment.getCode());
		setRank(treatment.getRank());
		setTreatment(treatment.getTreatment());
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

	public String getTreatment() {
		return treatment;
	}

	public void setTreatment(String treatment) {
		this.treatment = treatment;
	}

}
