package platform.doc.entity;

import java.util.ArrayList;

import com.ptc.tml.utils.IBAUtils;

import lombok.Getter;
import lombok.Setter;
import net.sf.json.JSONArray;
import platform.code.service.BaseCodeHelper;
import platform.doc.service.DocumentHelper;
import platform.part.entity.PartColumns;
import platform.util.CommonUtils;
import wt.doc.WTDocument;
import wt.org.WTUser;
import wt.session.SessionHelper;

@Getter
@Setter
public class DocumentDTO {

	private String oid;
	private String name;
	private String number;
	private String location;
	private String company;
	private String companyNm;
	private String brand;
	private String brandNm;
	private String content;
	private String version;
	private String state;
	private String creator;
	private String createdDate;
	private String modifier;
	private String modifiedDate;
	private ArrayList<String> secondary = new ArrayList<String>();
	private String primary;
	private ArrayList<PartColumns> partList = new ArrayList<PartColumns>();
	private JSONArray partJson = new JSONArray();
	private boolean isModify = false;
	private boolean isDelete = false;
	private boolean isRevise = false;

	public DocumentDTO() {

	}

	public DocumentDTO(WTDocument doc) throws Exception {
		setOid(CommonUtils.oid(doc));
		setName(doc.getName());
		setNumber(doc.getNumber());
		setLocation(doc.getLocation());
		setCompany(IBAUtils.getStringIBAValue(doc, "COMPANY_CODE"));
		setBrand(IBAUtils.getStringIBAValue(doc, "BRAND_CODE"));
		setCompanyNm(BaseCodeHelper.manager.getNameByCodeTypeAndCode("COMPANY", this.company));
		setBrandNm(BaseCodeHelper.manager.getNameByCodeTypeAndCode("BRAND", this.brand));
		setContent(doc.getTypeInfoWTDocument().getPtc_rht_1());
		setVersion(doc.getVersionIdentifier().getSeries().getValue() + "."
				+ doc.getIterationIdentifier().getSeries().getValue());
		setState(doc.getLifeCycleState().getDisplay());
		setCreator(doc.getCreatorFullName());
		setCreatedDate(doc.getCreateTimestamp().toString().substring(0, 10));
		setModifier(doc.getModifierFullName());
		setModifiedDate(doc.getModifyTimestamp().toString().substring(0, 10));
//		setPartList(DocumentHelper.manager.getParts(doc));
		setPartJson(JSONArray.fromObject(DocumentHelper.manager.getParts(doc)));
		setAuth(doc);
	}

	private void setAuth(WTDocument doc) throws Exception {
		WTUser user = (WTUser) SessionHelper.manager.getPrincipal();
		if (CommonUtils.isAdmin()) {
			isDelete = true;
			isModify = true;
			isRevise = true;
		} else {
			if (doc.getCreatorName().equals(user.getName())) {
				if (doc.getLifeCycleState().toString().equals("INWORK")) {
					isDelete = true;
					isModify = true;
				}
				
				if (doc.getLifeCycleState().toString().equals("RETURN")) {
					isModify = true;
				}

				if (doc.getLifeCycleState().toString().equals("RELEASED")) {
					isRevise = true;
				}
			}
		}
	}
}