package platform.dist.entity;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;
import platform.code.service.BaseCodeHelper;
import platform.dist.service.DistHelper;
import platform.dist.vo.DistFileVO;
import platform.epm.service.EpmHelper;
import platform.part.service.PartHelper;
import platform.util.CommonUtils;
import platform.util.ContentUtils;
import platform.util.ThumbnailUtils;
import wt.epm.EPMDocument;
import wt.part.WTPart;
import wt.vc.VersionControlHelper;

@Getter
@Setter
public class DistPartColumns {
	private String oid;
	private String linkOid;
	private String docType;
	private String name;
	private String number;
	private String version;
	private boolean pdf = false;
	private boolean dwg = false;
	private boolean step = false;
	private boolean linkPdf = false;
	private boolean linkDwg = false;
	private boolean linkStep = false;
	private String s;
	private String t;
	private String type;
	private String distributor;
	private String user;
	private String uoid;
	
	private String pdfValue;
	private String dwgValue;
	private String stepValue;

	private String thum_3d = "";
	private String thum_2d = "";
	
	private String eoid = "";
	private String eoid2d = "";
	
	public DistPartColumns() {

	}

	public DistPartColumns(WTPart part) throws Exception {
		
		EPMDocument epm = PartHelper.manager.getEPMDocument(part);
		if (epm != null) {
			setEoid(epm.getPersistInfo().getObjectIdentifier().getStringValue());
			setThum_3d(ThumbnailUtils.thumbnails(part)[1]);
			DistFileVO fileVo = DistHelper.manager.getDistFileVO(epm);
			setLinkStep(fileVo.getStpFile()!=null?true:false);
		}
		EPMDocument epm2d = EpmHelper.manager.getEPM2D(epm);
		if (epm2d != null) {
			setEoid2d(epm2d.getPersistInfo().getObjectIdentifier().getStringValue());
			setThum_2d(ThumbnailUtils.thumbnails(epm2d)[1]);
			DistFileVO fileVo = DistHelper.manager.getDistFileVO(epm2d);
			setPdf(fileVo.getPdfFile()!=null?true:false);
			setDwg(fileVo.getDwgFile()!=null?true:false);
			setStep(fileVo.getStpFile()!=null?true:false);
			
			setLinkPdf(fileVo.getPdfFile()!=null?true:false);
			setLinkDwg(fileVo.getDwgFile()!=null?true:false);
		}
		
		setOid(CommonUtils.oid(part));
		setDocType(part.getType().toString());
		setName(part.getName());
		setNumber(part.getNumber());
		setVersion(VersionControlHelper.getIterationDisplayIdentifier(part).toString());
		setS(ContentUtils.getStandardIcon(part));
		setT(ThumbnailUtils.thumbnails(part)[1]);
		
	}
	
	public DistPartColumns(DistPartLink link) throws Exception {
		setLinkOid(CommonUtils.oid(link));
		WTPart part = link.getPart();
		
		EPMDocument epm = PartHelper.manager.getEPMDocument(part);
		if (epm != null) {
			setEoid(epm.getPersistInfo().getObjectIdentifier().getStringValue());
			setThum_3d(ThumbnailUtils.thumbnails(part)[1]);
			DistFileVO fileVo = DistHelper.manager.getDistFileVO(epm);
			//setPdf(fileVo.getPdfFile()!=null?true:false);
			//setDwg(fileVo.getDwgFile()!=null?true:false);
			setStep(fileVo.getStpFile()!=null?true:false);
		}
		EPMDocument epm2d = EpmHelper.manager.getEPM2D(epm);
		if (epm2d != null) {
			setEoid2d(epm2d.getPersistInfo().getObjectIdentifier().getStringValue());
			setThum_2d(ThumbnailUtils.thumbnails(epm2d)[1]);
			DistFileVO fileVo = DistHelper.manager.getDistFileVO(epm2d);
			setPdf(fileVo.getPdfFile()!=null?true:false);
			setDwg(fileVo.getDwgFile()!=null?true:false);
			//setStep(fileVo.getStpFile()!=null?true:false);
		}
		
		setOid(CommonUtils.oid(part));
		setDocType(part.getType().toString());
		setName(part.getName());
		setNumber(part.getNumber());
		setVersion(VersionControlHelper.getIterationDisplayIdentifier(part).toString());
		setS(ContentUtils.getStandardIcon(part));
		setT(ThumbnailUtils.thumbnails(part)[1]);
		
		
		String type = link.getDistributorType();
		
		System.out.println("type=="+type);
		
		
		setDistributor(link.getDistributorName());
		setUser(link.getDistributorUserName());
		
		ArrayList<DistPartDistributorUserLink> distList = DistHelper.manager.getDistPartDistributorUserLinks(link);
		String distType = "";
		String distName = "";
		String distUser = "";
		
		
		for( DistPartDistributorUserLink userLink : distList ){
			if( userLink.getDistUser() != null ){
				
				System.out.println("type 222=="+userLink.getDistUser().getType());
				System.out.println("type 333=="+userLink.getDistUser().getName());
				System.out.println("type 444=="+userLink.getDistUser().getUserName());
				if( userLink.getDistUser().getDistributor() != null) {
					System.out.println("type 555=="+userLink.getDistUser().getDistributor().getName());
					System.out.println("type 666=="+userLink.getDistUser().getDistributor().getUserName());
				}
				
				String linkType = userLink.getDistUser().getType();
				
				if( distType.length() > 0 ){
					 distType +="," ;
				}
				
				if( "IN".equals(linkType)) {
					distType += "사내";
				}else if( "OUT".equals(linkType)) {
					distType += "사외";
				}
				
				if( distName.length() > 0 ){
					distName +=",";
				 }
				
				if( "IN".equals(linkType)) {
					distName += BaseCodeHelper.manager.getNameByCodeTypeAndCode("FACTORY_CODE", userLink.getDistUser().getName());
				}else if( "OUT".equals(linkType)) {
					distName += userLink.getDistUser().getDistributor().getName();
				}
				
				if( distUser.length() > 0 ){
					distUser +=",";
				 }
				distUser += userLink.getDistUser().getUserName() ;
				
			}
		}
		
		if( link.getDistributorType()== null) {
			setType(distType);
		}
		if( link.getDistributorName()== null) {
			setDistributor(distName);
		}
		if( link.getDistributorUserName()== null) {
			setUser(distUser);
		}
		
		
		//		if (this.docType.equals("CADDRAWING")) {
//			setPdf(true);
//			setDwg(true);
//			setStep(false);
//		} else {
							setLinkPdf(link.isPdf());
							setLinkDwg(link.isDwg());
							setLinkStep(link.isStep());
//		}
	}
}
