package platform.tree.entity;

import platform.util.CommonUtils;

public class BomQuantityDTO {

	private String oid;
	private String commonCode;
	private String sort;
	private String commonName;
	private String materialInfo;
	private String processInfo;
	private String treatment;
	private String bomLevel;
	private String requiredProcess;
	private String materialCode;
	private String color;
	private String materialName;
	private String bom_unit;
	private String x1;
	private String x2;
	private String processMargin;
	private String processValue;
	private String loss;
	private String unitProcessAmount;
	private String unitConversion;
	private String conversionFactor;

	public BomQuantityDTO() {

	}

	public BomQuantityDTO(BomQuantity quantity) throws Exception {
		setOid(CommonUtils.oid(quantity));
		setCommonCode(quantity.getCommonCode());
		setSort(quantity.getSort());
		setCommonName(quantity.getCommonName());
		setMaterialInfo(quantity.getMaterialInfo());
		setProcessInfo(quantity.getProcessInfo());
		setTreatment(quantity.getTreatment());
		setBomLevel(quantity.getBomLevel());
		setRequiredProcess(quantity.getRequiredProcess());
		setMaterialCode(quantity.getMaterialCode());
		setColor(quantity.getColor());
		setMaterialName(quantity.getMaterialName());
		setBom_unit(quantity.getBom_unit());
		setX1(quantity.getX1());
		setX2(quantity.getX2());
		setProcessMargin(quantity.getProcessMargin());
		setProcessValue(quantity.getProcessValue());
		setLoss(quantity.getLoss());
		setUnitProcessAmount(quantity.getUnitProcessAmount());
		setUnitConversion(quantity.getUnitConversion());
		setConversionFactor(quantity.getConversionFactor());
	}

	public String getBom_unit() {
		return bom_unit;
	}

	public void setBom_unit(String bom_unit) {
		this.bom_unit = bom_unit;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getCommonCode() {
		return commonCode;
	}

	public void setCommonCode(String commonCode) {
		this.commonCode = commonCode;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getCommonName() {
		return commonName;
	}

	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}

	public String getMaterialInfo() {
		return materialInfo;
	}

	public void setMaterialInfo(String materialInfo) {
		this.materialInfo = materialInfo;
	}

	public String getProcessInfo() {
		return processInfo;
	}

	public void setProcessInfo(String processInfo) {
		this.processInfo = processInfo;
	}

	public String getTreatment() {
		return treatment;
	}

	public void setTreatment(String treatment) {
		this.treatment = treatment;
	}

	public String getBomLevel() {
		return bomLevel;
	}

	public void setBomLevel(String bomLevel) {
		this.bomLevel = bomLevel;
	}

	public String getRequiredProcess() {
		return requiredProcess;
	}

	public void setRequiredProcess(String requiredProcess) {
		this.requiredProcess = requiredProcess;
	}

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getX1() {
		return x1;
	}

	public void setX1(String x1) {
		this.x1 = x1;
	}

	public String getX2() {
		return x2;
	}

	public void setX2(String x2) {
		this.x2 = x2;
	}

	public String getProcessMargin() {
		return processMargin;
	}

	public void setProcessMargin(String processMargin) {
		this.processMargin = processMargin;
	}

	public String getProcessValue() {
		return processValue;
	}

	public void setProcessValue(String processValue) {
		this.processValue = processValue;
	}

	public String getLoss() {
		return loss;
	}

	public void setLoss(String loss) {
		this.loss = loss;
	}

	public String getUnitProcessAmount() {
		return unitProcessAmount;
	}

	public void setUnitProcessAmount(String unitProcessAmount) {
		this.unitProcessAmount = unitProcessAmount;
	}

	public String getUnitConversion() {
		return unitConversion;
	}

	public void setUnitConversion(String unitConversion) {
		this.unitConversion = unitConversion;
	}

	public String getConversionFactor() {
		return conversionFactor;
	}

	public void setConversionFactor(String conversionFactor) {
		this.conversionFactor = conversionFactor;
	}

}
