package platform.mbom.entity;

import lombok.Getter;
import lombok.Setter;
import platform.echange.eco.entity.ECO;
import platform.part.service.PartHelper;
import platform.util.CommonUtils;
import platform.util.IBAUtils;
import platform.util.ThumbnailUtils;
import wt.epm.EPMDocument;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.part.WTPart;

@Getter
@Setter
public class MBOMColumns {

	private String oid;
	private int no;
	private String p;
	private String t;
	private String name;
	private String number;
	private String version;
	private String manager;
	private String state;
	private String creator;
	private String createdDate;
	private String ecoNumber;
	private String org;
	private String partType;
	private String erpCode;
	private String poid;
	private String eoid = "";
	private String toid = "";
	private String color;

	public MBOMColumns() {

	}

	public MBOMColumns(MBOM header) throws Exception {
		WTPart part = header.getPart();
		EPMDocument epm = PartHelper.manager.getEPMDocument(part);
		setOid(CommonUtils.oid(header));
		setP("/Windchill/netmarkets/images/part.gif");
		setT(ThumbnailUtils.thumbnails(part)[1]);
		setName(header.getName());
		setPartType(PartHelper.manager.partTypeToDisplay(part));
		setNumber(header.getNumber());
		setVersion(header.getVer());
		setState(header.getState());
		setCreator(header.getOwnership().getOwner().getFullName());
		setCreatedDate(header.getCreateTimestamp().toString().substring(0, 10));
		setOrg("");
		setErpCode(IBAUtils.getStringValue(part, "ERP_CODE"));
		setPoid(header.getPart().getPersistInfo().getObjectIdentifier().getStringValue());
		QueryResult result = PersistenceHelper.manager.navigate(header, "eco", MBOMECOLink.class);
		ECO eco = null;
		if (result.hasMoreElements()) {
			eco = (ECO) result.nextElement();
		}
		if (eco != null) {
			setEcoNumber(eco.getNumber());
			setEoid(CommonUtils.oid(eco));
		}
		if (epm != null) {
			setToid(epm.getPersistInfo().getObjectIdentifier().getStringValue());
		}
		setManager(header.getManager() != null ? header.getManager().getFullName() : "");
		setColor(header.getColor());
	}
}