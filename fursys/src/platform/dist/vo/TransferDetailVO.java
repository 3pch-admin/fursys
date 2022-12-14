package platform.dist.vo;

import java.util.ArrayList;
import java.util.List;

public class TransferDetailVO {

	private String siteTypeCd;
	private String siteCd;
	private String partOid;
	private String partName;
	private String partNumber;
	private String partVersion;
	private String partSpec;

	private String epmOid;
	private String erpCd;
	private String cadFolderOid;
	private String prdEcoNo;
	private String ecnNo;
	private String ecnTitle;

	private TransferFileVO pdfFile;
	private TransferFileVO dwgFile;
	private TransferFileVO stpFile;
	private TransferFileVO excelFile;
	private TransferFileVO partPdfFile;
	private TransferFileVO jpg2DFile;
	private TransferFileVO jpg2DSmallFile;
	private TransferFileVO jpg3DFile;
	private TransferFileVO jpg3DSmallFile;

	private String releaseDate;

	private List<DistUserVO> users = new ArrayList<>();

	public void addUsers(DistUserVO user) {
		users.add(user);
	}

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

	public String getPartOid() {
		return partOid;
	}

	public void setPartOid(String partOid) {
		this.partOid = partOid;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public String getPartVersion() {
		return partVersion;
	}

	public void setPartVersion(String partVersion) {
		this.partVersion = partVersion;
	}

	public String getPartSpec() {
		return partSpec;
	}

	public void setPartSpec(String partSpec) {
		this.partSpec = partSpec;
	}

	public String getEpmOid() {
		return epmOid;
	}

	public void setEpmOid(String epmOid) {
		this.epmOid = epmOid;
	}

	public String getErpCd() {
		return erpCd;
	}

	public void setErpCd(String erpCd) {
		this.erpCd = erpCd;
	}

	public String getCadFolderOid() {
		return cadFolderOid;
	}

	public void setCadFolderOid(String cadFolderOid) {
		this.cadFolderOid = cadFolderOid;
	}

	public String getPrdEcoNo() {
		return prdEcoNo;
	}

	public void setPrdEcoNo(String prdEcoNo) {
		this.prdEcoNo = prdEcoNo;
	}

	public String getEcnNo() {
		return ecnNo;
	}

	public void setEcnNo(String ecnNo) {
		this.ecnNo = ecnNo;
	}

	public String getEcnTitle() {
		return ecnTitle;
	}

	public void setEcnTitle(String ecnTitle) {
		this.ecnTitle = ecnTitle;
	}

	public TransferFileVO getPdfFile() {
		return pdfFile;
	}

	public void setPdfFile(TransferFileVO pdfFile) {
		this.pdfFile = pdfFile;
	}

	public TransferFileVO getDwgFile() {
		return dwgFile;
	}

	public void setDwgFile(TransferFileVO dwgFile) {
		this.dwgFile = dwgFile;
	}

	public TransferFileVO getStpFile() {
		return stpFile;
	}

	public void setStpFile(TransferFileVO stpFile) {
		this.stpFile = stpFile;
	}

	public TransferFileVO getExcelFile() {
		return excelFile;
	}

	public void setExcelFile(TransferFileVO excelFile) {
		this.excelFile = excelFile;
	}

	public TransferFileVO getPartPdfFile() {
		return partPdfFile;
	}

	public void setPartPdfFile(TransferFileVO partPdfFile) {
		this.partPdfFile = partPdfFile;
	}

	public TransferFileVO getJpg2DFile() {
		return jpg2DFile;
	}

	public void setJpg2DFile(TransferFileVO jpg2dFile) {
		jpg2DFile = jpg2dFile;
	}

	public TransferFileVO getJpg2DSmallFile() {
		return jpg2DSmallFile;
	}

	public void setJpg2DSmallFile(TransferFileVO jpg2dSmallFile) {
		jpg2DSmallFile = jpg2dSmallFile;
	}

	public TransferFileVO getJpg3DFile() {
		return jpg3DFile;
	}

	public void setJpg3DFile(TransferFileVO jpg3dFile) {
		jpg3DFile = jpg3dFile;
	}

	public TransferFileVO getJpg3DSmallFile() {
		return jpg3DSmallFile;
	}

	public void setJpg3DSmallFile(TransferFileVO jpg3dSmallFile) {
		jpg3DSmallFile = jpg3dSmallFile;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public List<DistUserVO> getUsers() {
		return users;
	}

	public void setUsers(List<DistUserVO> users) {
		this.users = users;
	}
}
