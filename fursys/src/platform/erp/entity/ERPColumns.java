package platform.erp.entity;

import platform.util.CommonUtils;

public class ERPColumns {

	private int no;
	private String oid;
	private String name;
	private String resultMessage;
	private String sendType;
	private String sendResult;

	public ERPColumns() {

	}

	public ERPColumns(ERPSendHistory history) throws Exception {
		setOid(CommonUtils.oid(history));
		setName(history.getName());
		setResultMessage(history.getResultMessage());
		setSendType(history.getSendType());
		setSendResult(history.getSendResult() == true ? "성공" : "실패");
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	public String getSendType() {
		return sendType;
	}

	public void setSendType(String sendType) {
		this.sendType = sendType;
	}

	public String getSendResult() {
		return sendResult;
	}

	public void setSendResult(String sendResult) {
		this.sendResult = sendResult;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

}
