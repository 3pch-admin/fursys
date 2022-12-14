package platform.echange.ecn.entity;

import java.sql.Timestamp;

import platform.code.entity.BaseCode;
import platform.code.service.BaseCodeHelper;

public class ECNColumns {
	private String oid;
	private int no;
	private String number;
	private String name;
	private String notiType;
	private String notiTypeNm;
	private String applyTime;
	private String applyTimeNm;
	private String applicationDate;
	private String state;
	private String plant;
	private String plantNm;
	private String company;
	private String companyNm;
	private String brand;
	private String brandNm;
	private String creator;
	private Timestamp createdDate;

	public ECNColumns() {

	}

	public ECNColumns(ECN ecn) throws Exception {
		setOid(ecn.getPersistInfo().getObjectIdentifier().getStringValue());
		setNumber(ecn.getNumber());
		setName(ecn.getName());
		setCreator(ecn.getOwnership().getOwner().getFullName());
		setCreatedDate(ecn.getCreateTimestamp());
		setState(ecn.getState());
		setPlant(ecn.getPlant());
		setApplyTime(ecn.getEcnApplyTime());
		setNotiType(ecn.getNotiType());
		setCompany(ecn.getCompany());
		setBrand(ecn.getBrand());
		setApplicationDate(ecn.getApplicationDate().toString().substring(0, 10));
		setPlantNm(this.plant);
		setCompanyNm(this.company);
		setBrandNm(this.brand);
		setNotiTypeNm(this.notiType);
		setApplyTimeNm(this.applyTime);
	}

	public String getApplicationDate() {
		return applicationDate;
	}

	public void setApplicationDate(String applicationDate) {
		this.applicationDate = applicationDate;
	}

	public String getPlant() {
		return plant;
	}

	public void setPlant(String plant) {
		this.plant = plant;
	}

	public String getPlantNm() {
		return plantNm;
	}

	public void setPlantNm(String plantCode) throws Exception {
		BaseCode c = BaseCodeHelper.manager.getBaseCodeByCodeTypeAndCode("PLANT", plantCode);
		this.plantNm = c != null ? c.getName() : "";
	}

	public String getNotiTypeNm() {
		return notiTypeNm;
	}

	public void setNotiTypeNm(String notiTypeCode) throws Exception {
		BaseCode c = BaseCodeHelper.manager.getBaseCodeByCodeTypeAndCode("ECO_NOTI_TYPE", notiTypeCode);
		this.notiTypeNm = c != null ? c.getName() : "";
	}

	public String getApplyTimeNm() {
		return applyTimeNm;
	}

	public void setApplyTimeNm(String applyTimeCode) throws Exception {
		BaseCode c = BaseCodeHelper.manager.getBaseCodeByCodeTypeAndCode("ECO_APPLY_TIME", applyTimeCode);
		this.applyTimeNm = c != null ? c.getName() : "";
	}

	public void setCompanyNm(String companyCode) throws Exception {
		BaseCode c = BaseCodeHelper.manager.getBaseCodeByCodeTypeAndCode("COMPANY", companyCode);
		this.companyNm = c != null ? c.getName() : "";
	}

	public String getCompanyNm() {
		return companyNm;
	}

	public String getBrandNm() {
		return brandNm;
	}

	public void setBrandNm(String brandCode) throws Exception {
		BaseCode c = BaseCodeHelper.manager.getBaseCodeByCodeTypeAndCode("BRAND", brandCode);
		this.brandNm = c != null ? c.getName() : "";
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

	public String getNotiType() {
		return notiType;
	}

	public void setNotiType(String notiType) {
		this.notiType = notiType;
	}

	public String getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

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

}
