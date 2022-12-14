package platform.erp.entity;

import platform.attr.service.AttrHelper;
import platform.tree.entity.BomQuantity;
import platform.util.StringUtils;
import wt.part.WTPart;

public class NonBomInfo {

	private BomQuantity bq;
	private String erpCode; // IBA
	private String partName; // IBA
	private int level;

	public NonBomInfo() {

	}

	// materalcode process code finishcode
	public NonBomInfo(BomQuantity bq) throws Exception {
		setBq(bq);
		setErpCode(bq.getMaterialCode());
		setPartName(bq.getMaterialName());
		setLevel(Integer.parseInt(bq.getBomLevel()));
	}

	public BomQuantity getBq() {
		return bq;
	}

	public void setBq(BomQuantity bq) {
		this.bq = bq;
	}

	public String getErpCode() {
		return erpCode;
	}

	public void setErpCode(String erpCode) {
		this.erpCode = erpCode;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public double getTotalQty(BomQuantity bq, WTPart part) throws Exception {
		String x1 = bq.getX1();
		double x1Value = 0;
		if (StringUtils.isNotNull(x1)) {
			x1Value = AttrHelper.manager.getValue(part.getNumber(), x1);
		}

		String x2 = bq.getX2();
		double x2Vaule = 0;
		if (StringUtils.isNotNull(x2)) {
			x2Vaule = AttrHelper.manager.getValue(part.getNumber(), x2);
		}

		if (x1Value == 0f && x1Value == 0f) {
			return 0f;
		}

		String processMargin = bq.getProcessMargin();
		int processsMarginValue = 0; // 가공값
		if (StringUtils.isNotNull(processMargin)) {
			processsMarginValue = Integer.parseInt(processMargin);
		}

		String calc = bq.getProcessValue(); // 계산식..

		double rtnValue = calculation(calc, x1Value, x2Vaule, processsMarginValue);
		if (rtnValue == 0f) {
			return rtnValue;
		}

		String loss = bq.getLoss(); // 로스율
		String unitProcessAmount = bq.getUnitProcessAmount(); // 단위가공량
		String unitConversionn = bq.getUnitConversion(); // 단위환산

		double lossValue = Double.parseDouble(loss) + 1;// 로스율
		double unitProcessValue = Double.parseDouble(unitProcessAmount);// 단위가공량
		double unitConvrValue = Double.parseDouble(unitConversionn); // 단위환산

		System.out.println("로스율 = " + lossValue);
		System.out.println("단위가공량 = " + unitProcessValue);
		System.out.println("단위환산 = " + unitConvrValue);

		return Math.ceil(rtnValue * lossValue * unitProcessValue * unitConvrValue) / 1000.0;

	}

	private double calculation(String calc, double x1, double x2, double m) throws Exception {
		calc = calc.trim();

		if ("X1".equals(calc)) {
			return x1;
		} else if ("X1+M".equals(calc)) {
			return x1 + m;
		} else if ("X1*X2".equals(calc)) {
			return x1 * x2;
		} else if ("(X2+M)*X1".equals(calc)) {
			return (x2 + m) * x1;
		} else if ("1".equals(calc)) {
			return 1f;
		} else if ("1/(round((x1+10)/2440,0)*round((x2+10)/1220),0))".equals(calc)) {
			return 1f;
		} else if ("1/(round((x1+10)/3050,0)*round((x2+10)/1300),0))".equals(calc)) {
			return 1f;
		}
		return 0f;
	}
}
