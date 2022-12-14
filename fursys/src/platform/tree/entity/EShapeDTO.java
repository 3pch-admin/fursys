package platform.tree.entity;

import platform.util.CommonUtils;

public class EShapeDTO {
	
	private String oid;
	private String code;
	private int rank;
	private String shape;
	private String createdDate;
	private String creator;

	public EShapeDTO() {
		
	}
	
	public EShapeDTO(EShape eshape) throws Exception {
		setOid(CommonUtils.oid(eshape));
		setCode(eshape.getCode());
		setRank(eshape.getRank());
		setShape(eshape.getShape());
		setCreatedDate(eshape.getCreateTimestamp().toString().substring(0, 10));
		setCreator(eshape.getOwnership().getOwner().getFullName());
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

	public String getShape() {
		return shape;
	}

	public void setShape(String shape) {
		this.shape = shape;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	
	
}
