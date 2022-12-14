package platform.wood.entity;

import platform.util.CommonUtils;

public class ProcessDTO {

	private String oid;
	private String code;
	private String rank;
	private String process;

	public ProcessDTO() {

	}

	public ProcessDTO(Process process) throws Exception {
		setOid(CommonUtils.oid(process));
		setCode(process.getCode());
		setRank(process.getRank());
		setProcess(process.getProcess());
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

	public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		this.process = process;
	}

}
