package platform.dist.vo;

public class DistUserVO {

	private String siteTypeCd;
	private String siteCd;
	private String siteNm;
	private String id;
	private String userName;
	private String email;

	public String getSiteTypeCd() {
		return siteTypeCd;
	}

	public void setSiteTypeCd(String siteTypeCd) {
		this.siteTypeCd = siteTypeCd;
	}

	public String getSiteCd() {
		return siteCd;
	}

	public void setSiteCd(String siteCd) {
		this.siteCd = siteCd;
	}

	public String getSiteNm() {
		return siteNm;
	}

	public void setSiteNm(String siteNm) {
		this.siteNm = siteNm;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}