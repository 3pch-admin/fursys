package platform.epm.entity;

import lombok.Getter;
import lombok.Setter;
import platform.code.service.BaseCodeHelper;
import platform.epm.service.EpmHelper;
import platform.part.service.PartHelper;
import platform.util.CommonUtils;
import platform.util.ContentUtils;
import platform.util.IBAUtils;
import platform.util.StringUtils;
import platform.util.ThumbnailUtils;
import wt.epm.EPMDocument;
import wt.part.WTPart;
import wt.session.SessionHelper;

@Setter
@Getter
public class EpmColumns {
	private int no;
	private String oid;
	private String p;
	private String t;
	private String number;
	private String name;
	private String version;
	private String creator;
	private String createdDate;
	private String modifier;
	private String modifiedDate;
	private String state;
	private String erpCode;
	private String company;
	private String companyNm;
	private String brand;
	private String brandNm;
	private String unit;
	private String order;
	private String use;
	private String w;
	private String d;
	private String h;
	private int stock;
	private String manager;
	

	public EpmColumns() {

	}

	public EpmColumns(EPMDocument epm) throws Exception {
		setOid(CommonUtils.oid(epm));
		setP(ContentUtils.getStandardIcon(epm));
		setT(ThumbnailUtils.thumbnails(epm)[1]);
//		setNumber(epm.getNumber());
		setNumber(epm.getCADName());
//		setName(IBAUtils.getStringValue(epm2d, name))
//		setName(epm.getName());
		setName(IBAUtils.getStringValue(epm, "PART_NAME"));
		setVersion(epm.getVersionIdentifier().getSeries().getValue() + "."
				+ epm.getIterationIdentifier().getSeries().getValue());
		setCreator(epm.getCreatorFullName());
		setCreatedDate(epm.getCreateTimestamp().toString().substring(0, 10));
		setModifier(epm.getModifierFullName());
		setModifiedDate(epm.getModifyTimestamp().toString().substring(0, 10));
		setState(epm.getLifeCycleState().getDisplay(SessionHelper.manager.getLocale()));
		setErpCode(IBAUtils.getStringValue(epm, "ERP_CODE"));
		setCompany(IBAUtils.getStringValue(epm, "COMPANY_CODE"));
		setBrand(IBAUtils.getStringValue(epm, "BRAND_CODE"));
		setUnit(epm.getDefaultUnit().getDisplay(SessionHelper.manager.getLocale()));
		setOrder(IBAUtils.getStringValue(epm, "PURCHASE_YN"));
		setUse(IBAUtils.getStringValue(epm, "USE_TYPE_CODE"));
		setW(StringUtils.convertToStr(IBAUtils.getStringValue(epm, "PART_WIDTH"), "0") + " (mm)");
		setD(StringUtils.convertToStr(IBAUtils.getStringValue(epm, "PART_DEPTH"), "0") + " (mm)");
		setH(StringUtils.convertToStr(IBAUtils.getStringValue(epm, "PART_HEIGHT"), "0") + " (mm)");
		setCompanyNm(BaseCodeHelper.manager.getNameByCodeTypeAndCode("COMPANY_CODE", this.company));
		setBrandNm(BaseCodeHelper.manager.getNameByCodeTypeAndCode("BRAND_CODE", this.brand));
	}
}