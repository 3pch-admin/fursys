package platform.raonk.entity;

import platform.util.CommonUtils;

public class RaonkDTO {

	private String oid;
	private String name;
//	private String raonkType;
//	private String description;
	private String enable;
	private String contents;
	private String location;
	private String creator;
	private String createdDate;

	public RaonkDTO() {

	}

	public RaonkDTO(Raonk raonk) throws Exception {
		setOid(CommonUtils.oid(raonk));
		setName(raonk.getName());
		setEnable(raonk.getEnable() == true ? "사용" : "사용안함");
		setContents(raonk.getContents());
		setCreator(raonk.getOwnership().getOwner().getFullName());
		setCreatedDate(raonk.getCreateTimestamp().toString().substring(0, 10));
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

	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
