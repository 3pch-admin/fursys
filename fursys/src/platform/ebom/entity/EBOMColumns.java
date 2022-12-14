package platform.ebom.entity;

import com.ptc.tml.utils.IBAUtils;

import lombok.Getter;
import lombok.Setter;
import platform.part.service.PartHelper;
import platform.util.CommonUtils;
import platform.util.ThumbnailUtils;
import wt.part.WTPart;

@Getter
@Setter
public class EBOMColumns {

	private String oid;
	private int no;
	private String p;
	private String t;
	private String number;
	private String partType;
	private String erpCode;
	private String version;
	private String state;
	private String creator;
	private String createdDate;

	public EBOMColumns() {

	}

	public EBOMColumns(EBOM ebom) throws Exception {
		setOid(CommonUtils.oid(ebom));
		setP("/Windchill/netmarkets/images/part.gif");
		setState(ebom.getState());
		setCreator(ebom.getOwnership().getOwner().getFullName());
		setCreatedDate(ebom.getCreateTimestamp().toString().substring(0, 10));
		setNumber(ebom.getWtpartMaster().getNumber());
		WTPart latest = PartHelper.manager.getLatest(ebom.getWtpartMaster());
		setT(ThumbnailUtils.thumbnails(latest)[1]);
		setPartType(PartHelper.manager.partTypeToDisplay(latest));
		setErpCode(IBAUtils.getStringIBAValue(latest, "ERP_CODE"));
		setVersion(latest.getVersionIdentifier().getSeries().getValue());
	}
}
