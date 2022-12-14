package platform.doc.entity;

import lombok.Getter;
import lombok.Setter;
import platform.approval.service.ApprovalHelper;
import platform.code.service.BaseCodeHelper;
import platform.util.CommonUtils;
import platform.util.ContentUtils;
import platform.util.IBAUtils;
import wt.doc.WTDocument;

@Getter
@Setter
public class DocumentColumns {
	private int no;
	private String oid;
	private String number;
	private String name;
	private String version;
	private String creator;
	private String createdDate;
	private String modifier;
	private String modifiedDate;
	private String company;
	private String companyNm;
	private String brand;
	private String brandNm;
	private String state;
	private String primary;
	private String url;
	private String receive;

	public DocumentColumns() {

	}

	public DocumentColumns(WTDocument doc) throws Exception {
		setOid(CommonUtils.oid(doc));
		setNumber(doc.getNumber());
		setName(doc.getName());
		setVersion(doc.getVersionIdentifier().getSeries().getValue() + "."
				+ doc.getIterationIdentifier().getSeries().getValue());
		setCreator(doc.getCreatorFullName());
		setCreatedDate(doc.getCreateTimestamp().toString());
		setModifier(doc.getModifierFullName());
		setModifiedDate(doc.getModifyTimestamp().toString());
		setCompany(IBAUtils.getStringValue(doc, "COMPANY_CODE"));
		setBrand(IBAUtils.getStringValue(doc, "BRAND_CODE"));
		setCompanyNm(BaseCodeHelper.manager.getNameByCodeTypeAndCode("COMPANY", this.company));
		setBrandNm(BaseCodeHelper.manager.getNameByCodeTypeAndCode("BRAND", this.brand));
		setState(doc.getLifeCycleState().getDisplay());
		setPrimary(ContentUtils.getPrimary(doc)[4]);
		setUrl(ContentUtils.getPrimary(doc)[5]);
		setReceive(ApprovalHelper.manager.getReceiveUser(doc));
	}
}