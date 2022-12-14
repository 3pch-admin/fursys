package platform.ebom.vo;

import org.mozilla.universalchardet.UnicodeBOMInputStream.BOM;

import lombok.Getter;
import lombok.Setter;
import platform.part.service.PartHelper;
import platform.util.IBAUtils;
import wt.part.WTPart;

@Getter
@Setter
public class BOMCompareNode {

	private String number; // cad file name.
	private String partType;
	private String partName;
	private String itemName;
	private String partNo;
	private double cqty = 0D;
	private double eqty = 0D;
	private double compare = 0D;

	public BOMCompareNode() {

	}

	public BOMCompareNode(WTPart part, double cqty, double eqty) throws Exception {
		setNumber(part.getNumber());
		setPartType(PartHelper.manager.partTypeToDisplay(part));
		setItemName(IBAUtils.getStringValue(part, "ITEM_NAME"));
		setPartNo(IBAUtils.getStringValue(part, "PART_NO"));
		setPartName(IBAUtils.getStringValue(part, "PART_NAME"));
		setCqty(cqty);
		setEqty(eqty);
		setCompare(cqty - eqty);
	}

	public void add(double eqty, double cqty) {
		setEqty(getEqty() + eqty);
		setCqty(getCqty() + cqty);
		setCompare(getCqty() - getEqty());
	}
}
