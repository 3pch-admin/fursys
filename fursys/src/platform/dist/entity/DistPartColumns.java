package platform.dist.entity;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;
import platform.util.CommonUtils;
import platform.util.ContentUtils;
import platform.util.ThumbnailUtils;
import wt.part.WTPart;
import wt.vc.VersionControlHelper;

@Getter
@Setter
public class DistPartColumns {
	private String oid;
	private String docType;
	private String name;
	private String number;
	private String version;
	private boolean pdf;
	private boolean dwg;
	private boolean step;
	private String s;
	private String t;
	private String distributor;
	private String uoid;
	
	private String distributorType;
	private String distributorName;
	private String distributorUserName;

	public DistPartColumns() {

	}

	public DistPartColumns(WTPart part) throws Exception {
		setOid(CommonUtils.oid(part));
		setDocType(part.getType().toString());
		setName(part.getName());
		setNumber(part.getNumber());
		setVersion(VersionControlHelper.getIterationDisplayIdentifier(part).toString());
		setS(ContentUtils.getStandardIcon(part));
		setT(ThumbnailUtils.thumbnails(part)[1]);
		setPdf(true);
		setDwg(true);
		setStep(true);
		
		DistributorUser user = null;
		user = DistributorUser.newDistributorUser();
		setDistributorType(user.getType());
		setDistributorName(user.getName());
		setDistributorUserName(user.getUserName());
		
//		if (this.docType.equals("CADDRAWING")) {
//			setPdf(true);
//			setDwg(true);
//			setStep(false);
//		} else {
//			setPdf(false);
//			setDwg(false);
//			setStep(true);
//		}
	}
}
