package platform.part.entity;

import lombok.Getter;
import lombok.Setter;
import platform.code.service.BaseCodeHelper;
import platform.epm.service.EpmHelper;
import platform.part.service.PartHelper;
import platform.util.CommonUtils;
import platform.util.IBAUtils;
import platform.util.StringUtils;
import platform.util.ThumbnailUtils;
import wt.epm.EPMDocument;
import wt.part.WTPart;
import wt.session.SessionHelper;

@Setter
@Getter
public class PartColumns {
	private int no;
	private String oid;
	private String p;
	private String s;
	private String _3d;
	private String _2d;
	private String partType;
	private String number;
	private String name;
	private String version;
	private String ref;
	private String derived;
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
	private String eoid;
	private String eoid2d;
	private String cat_l;
	private String cat_m;

	public PartColumns() {

	}

	public PartColumns(WTPart part) throws Exception {
		EPMDocument epm = PartHelper.manager.getEPMDocument(part);
		if (epm != null) {
			setEoid(epm.getPersistInfo().getObjectIdentifier().getStringValue());
		}
		EPMDocument epm2d = EpmHelper.manager.getEPM2D(epm);
		if (epm2d != null) {
			setEoid2d(epm2d.getPersistInfo().getObjectIdentifier().getStringValue());
		}

		setOid(CommonUtils.oid(part));
		setP("/Windchill/netmarkets/images/part.gif");
		set_3d(ThumbnailUtils.thumbnails(part)[1]);
		set_2d(ThumbnailUtils.thumbnails(epm2d)[1]);
		setPartType(PartHelper.manager.partTypeToDisplay(part));
		setNumber(part.getNumber());
//		setName(IBAUtils.getStringValue(epm2d, name))
		setName(part.getName());
		setVersion(part.getVersionIdentifier().getSeries().getValue() + "."
				+ part.getIterationIdentifier().getSeries().getValue());
		setRef(epm != null ? "유" : "무");
		setDerived(PartHelper.manager.isDerived(part) == true ? "O" : "X");
		setCreator(part.getCreatorFullName());
		setCreatedDate(part.getCreateTimestamp().toString().substring(0, 10));
		setModifier(part.getModifierFullName());
		setModifiedDate(part.getModifyTimestamp().toString().substring(0, 10));
		setState(part.getLifeCycleState().getDisplay(SessionHelper.manager.getLocale()));
		setErpCode(IBAUtils.getStringValue(part, "ERP_CODE"));
		setCompany(IBAUtils.getStringValue(part, "COMPANY_CODE"));
		setBrand(IBAUtils.getStringValue(part, "BRAND_CODE"));
		setUnit(part.getDefaultUnit().getDisplay(SessionHelper.manager.getLocale()));
		setOrder(IBAUtils.getStringValue(part, "PURCHASE_YN"));
		setUse(IBAUtils.getStringValue(part, "USE_TYPE_CODE"));
		setW(StringUtils.convertToStr(IBAUtils.getStringValue(part, "PART_WIDTH"), "0") + " (mm)");
		setD(StringUtils.convertToStr(IBAUtils.getStringValue(part, "PART_DEPTH"), "0") + " (mm)");
		setH(StringUtils.convertToStr(IBAUtils.getStringValue(part, "PART_HEIGHT"), "0") + " (mm)");
		setCompanyNm(BaseCodeHelper.manager.getNameByCodeTypeAndCode("COMPANY_CODE", this.company));
		setBrandNm(BaseCodeHelper.manager.getNameByCodeTypeAndCode("BRAND_CODE", this.brand));
		setCat_l(BaseCodeHelper.manager.getNameByCodeTypeAndCode("CAT_L", this.cat_l));
		setCat_m(BaseCodeHelper.manager.getNameByCodeTypeAndCode("CAT_M", this.cat_m));
	}
}