package platform.part.entity;

import java.util.ArrayList;

import com.ptc.tml.utils.IBAUtils;

import lombok.Getter;
import lombok.Setter;
import platform.code.service.BaseCodeHelper;
import platform.doc.entity.DocumentColumns;
import platform.echange.eco.entity.ECO;
import platform.echange.eco.service.ECOHelper;
import platform.part.service.PartHelper;
import platform.util.CommonUtils;
import platform.util.ThumbnailUtils;
import wt.epm.EPMDocument;
import wt.part.WTPart;

@Getter
@Setter
public class PartDTO {

	private String oid;
	private String name;
	private String number;
	private String thumb;
	private String version;
	private String state;
	private String creator;
	private String createdDate;
	private String modifier;
	private String modifiedDate;
	private String location;
	private String partType;
	private String partTypeNm;
	private String brand;
	private String company;
	private String color;
	private String part_height;
	private String part_depth;
	private String part_width;
	private String purchase_yn;
	private String dummy_unit_price;
	private String use_type_code;
	private String standard_code;
	private String ref;
	private String part_name;
	private String part_name_en;
	private String unit;
	private String erpCode;
	private String ecoNumber = "";
	private String companyNm;
	private String brandNm;
	private String eoid;
	private String node;
	private String cat_l;
	private String cat_m;
	private String cat_lNm;
	private String cat_mNm;
	
	private ArrayList<DocumentColumns> docList = new ArrayList<DocumentColumns>();

	public PartDTO() {

	}

	public PartDTO(WTPart part) throws Exception {
		EPMDocument epm = PartHelper.manager.getEPMDocument(part);
		setOid(CommonUtils.oid(part));
		setName(part.getName());
		setNumber(part.getNumber());
		setThumb(ThumbnailUtils.thumbnails(part)[1]);
		setVersion(part.getVersionIdentifier().getSeries().getValue() + "."
				+ part.getIterationIdentifier().getSeries().getValue());
		setState(part.getLifeCycleState().getDisplay());
		setCreator(part.getCreatorFullName());
		setCreatedDate(part.getCreateTimestamp().toString().substring(0, 10));
		setModifier(part.getModifierFullName());
		setModifiedDate(part.getModifyTimestamp().toString().substring(0, 10));
		setLocation(part.getLocation());
		setColor(IBAUtils.getStringIBAValue(part, "COLOR"));
		setPartTypeNm(PartHelper.manager.partTypeToDisplay(part));
		setBrand(IBAUtils.getStringIBAValue(part, "BRAND_CODE"));
		setCompany(IBAUtils.getStringIBAValue(part, "COMPANY_CODE"));
		setPart_name(IBAUtils.getStringIBAValue(part, "PART_NAME"));
		setPart_name_en(IBAUtils.getStringIBAValue(part, "PART_NAME_EN"));
		setErpCode(IBAUtils.getStringIBAValue(part, "ERP_CODE"));
		setPart_height(IBAUtils.getStringIBAValue(part, "PART_HEIGHT"));
		setPart_depth(IBAUtils.getStringIBAValue(part, "PART_DEPTH"));
		setPart_width(IBAUtils.getStringIBAValue(part, "PART_WIDTH"));
		setPurchase_yn(IBAUtils.getStringIBAValue(part, "PURCHASE_YN"));
		setDummy_unit_price(IBAUtils.getStringIBAValue(part, "DUMMY_UNIT_PRICE"));
		setUse_type_code(IBAUtils.getStringIBAValue(part, "USE_TYPE_CODE"));
		setStandard_code(IBAUtils.getStringIBAValue(part, "STANDARD_CODE"));
		setUnit(part.getDefaultUnit().getDisplay());
		WTPart refPart = PartHelper.manager.refPart(part);
		setRef(refPart != null ? CommonUtils.oid(refPart) : "");
		setCompanyNm(BaseCodeHelper.manager.getNameByCodeTypeAndCode("COMPANY_CODE", this.company));
		setBrandNm(BaseCodeHelper.manager.getNameByCodeTypeAndCode("BRAND_CODE", this.brand));
		setCat_l(IBAUtils.getStringIBAValue(part, "CAT_L"));
		setCat_m(IBAUtils.getStringIBAValue(part, "CAT_M"));
		setCat_lNm(BaseCodeHelper.manager.getNameByCodeTypeAndCode("CAT_L", this.cat_l));
		setCat_mNm(BaseCodeHelper.manager.getNameByCodeTypeAndCode("CAT_M", this.cat_m));
		
		
		ECO eco = ECOHelper.manager.getRefECO(part);
		if (eco != null) {
			setEcoNumber(eco.getNumber());
		}

		if (epm != null) {
			setEoid(epm.getPersistInfo().getObjectIdentifier().getStringValue());
		}
	}
}