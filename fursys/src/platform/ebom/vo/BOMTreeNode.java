package platform.ebom.vo;

import java.util.ArrayList;

import lombok.ToString;

@ToString
public class BOMTreeNode {

	private String oid;
	private String number;
	private String partName;
	private String partNo;
	private String itemName;
	private double amount = 0D;

	private ArrayList<BOMTreeNode> children = new ArrayList<>();

	public BOMTreeNode() {

	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public String getPartNo() {
		return partNo;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public ArrayList<BOMTreeNode> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<BOMTreeNode> children) {
		this.children = children;
	}
}