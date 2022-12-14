package platform.echange.eco.entity;

import java.sql.Timestamp;

import platform.code.entity.BaseCode;
import platform.code.service.BaseCodeHelper;
import platform.util.CommonUtils;

public class ECOColumns {
	private String oid;
	private int no;
	private String number;
	private String name;
	private String ecoType;
	private String ecoTypeNm;
	private String devType;
	private String devTypeNm;
	private String notiType;
	private String notiTypeNm;
	private String lot;
	private String lotNm;
	private String applyTime;
	private String applyTimeNm;
//	private Timestamp expectationTime; // 코드로 할지 값으로 할지
	private String creator;
	private Timestamp createdDate;
	private String state;

	public ECOColumns() {

	}

	public ECOColumns(ECO eco) throws Exception {
		setOid(CommonUtils.oid(eco));
		setNumber(eco.getNumber());
		setName(eco.getName());
		setEcoType(eco.getEcoType());
		setDevType(eco.getDevType());
		setNotiType(eco.getNotiType());
		setLot(eco.getLot());
		setApplyTime(eco.getApplyTime());
//		setExpectationTime(eco.getExpectationTime());
		setCreator(eco.getOwnership().getOwner().getFullName());
		setCreatedDate(eco.getCreateTimestamp());
		setState(eco.getState());
		setEcoTypeNm(this.ecoType);
		setDevTypeNm(this.devType);
		setNotiTypeNm(this.notiType);
		setLotNm(this.lot);
		setApplyTimeNm(this.applyTime);
	}

	public String getEcoTypeNm() {
		return ecoTypeNm;
	}

	public void setEcoTypeNm(String ecoTypeCode) throws Exception {
		BaseCode c = BaseCodeHelper.manager.getBaseCodeByCodeTypeAndCode("ECO_TYPE", ecoTypeCode);
		this.ecoTypeNm = c != null ? c.getName() : "";
	}

	public String getDevTypeNm() {
		return devTypeNm;
	}

	public void setDevTypeNm(String devTypeCode) throws Exception {
		BaseCode c = BaseCodeHelper.manager.getBaseCodeByCodeTypeAndCode("ECO_DEV_TYPE", devTypeCode);
		this.devTypeNm = c != null ? c.getName() : "";
	}

	public String getNotiTypeNm() {
		return notiTypeNm;
	}

	public void setNotiTypeNm(String notiTypeCode) throws Exception {
		BaseCode c = BaseCodeHelper.manager.getBaseCodeByCodeTypeAndCode("ECO_NOTI_TYPE", notiTypeCode);
		this.notiTypeNm = c != null ? c.getName() : "";
	}

	public String getLotNm() {
		return lotNm;
	}

	public void setLotNm(String lotCode) throws Exception {
		BaseCode c = BaseCodeHelper.manager.getBaseCodeByCodeTypeAndCode("LOT_MGMT", lotCode);
		this.lotNm = c != null ? c.getName() : "";
	}

	public String getApplyTimeNm() {
		return applyTimeNm;
	}

	public void setApplyTimeNm(String applyTimeCode) throws Exception {
		BaseCode c = BaseCodeHelper.manager.getBaseCodeByCodeTypeAndCode("ECO_APPLY_TIME", applyTimeCode);
		this.applyTimeNm = c != null ? c.getName() : "";
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEcoType() {
		return ecoType;
	}

	public void setEcoType(String ecoType) {
		this.ecoType = ecoType;
	}

	public String getDevType() {
		return devType;
	}

	public void setDevType(String devType) {
		this.devType = devType;
	}

	public String getNotiType() {
		return notiType;
	}

	public void setNotiType(String notiType) {
		this.notiType = notiType;
	}

	public String getLot() {
		return lot;
	}

	public void setLot(String lot) {
		this.lot = lot;
	}

	public String getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}

//	public Timestamp getExpectationTime() {
//		return expectationTime;
//	}
//
//	public void setExpectationTime(Timestamp expectationTime) {
//		this.expectationTime = expectationTime;
//	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
