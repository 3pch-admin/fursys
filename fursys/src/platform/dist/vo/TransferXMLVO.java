package platform.dist.vo;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "tDistributeMasterVO")
public class TransferXMLVO {
	private String distTypeCd; // DIST, ECN
	private String oid;
	private String number;
	private String name;
	private String senderName;
	private String senderEmail;
	private String content;
	private String releaseDate;
	private String expirationDate;
	private String distributeDate;

	private List<TransferFileVO> files = new ArrayList<>();
	private List<TransferDetailVO> distributeDetails = new ArrayList<>();

	public void addFile(TransferFileVO file) {
		files.add(file);
	}

	public void addDistributeDetail(TransferDetailVO distributeDetail) {
		distributeDetails.add(distributeDetail);
	}

	public void addAllDistributeDetail(List<TransferDetailVO> distributeDetails) {
		this.distributeDetails.addAll(distributeDetails);
	}

	public List<TransferDetailVO> getDistributeDetails() {
		return distributeDetails;
	}

	public void setDistributeDetails(List<TransferDetailVO> distributeDetails) {
		this.distributeDetails = distributeDetails;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getSenderEmail() {
		return senderEmail;
	}

	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<TransferFileVO> getFiles() {
		return files;
	}

	public void setFiles(List<TransferFileVO> files) {
		this.files = files;
	}

	public String getDistTypeCd() {
		return distTypeCd;
	}

	public void setDistTypeCd(String distTypeCd) {
		this.distTypeCd = distTypeCd;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getDistributeDate() {
		return distributeDate;
	}

	public void setDistributeDate(String distributeDate) {
		this.distributeDate = distributeDate;
	}
}
