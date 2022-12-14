package platform.echange.eco.entity;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;
import net.sf.json.JSONArray;
import platform.code.service.BaseCodeHelper;
import platform.doc.entity.DocumentColumns;
import platform.ebom.entity.EBOMMasterColumns;
import platform.echange.eco.service.ECOHelper;
import platform.util.CommonUtils;
import wt.org.WTUser;
import wt.session.SessionHelper;

@Setter
@Getter
public class ECODTO {

	private String oid;
	private String number;
	private String name;
	private String ecoType;
	private String ecoTypeNm;
	private String devType;
	private String devTypeNm;
	private String notiType;
	private String notiTypeNm;
	private String lot;
	private String lotNm;
	private String company;
	private String companyNm;
	private String brand;
	private String brandNm;
	private String applyTime;
	private String applyTimeNm;
//	private String expectationTime; // 코드로 할지 값으로 할지
	private String creator;
	private String createdDate;
	private String state;
	private String description;
	private String reason;
	private ArrayList<String> secondary = new ArrayList<String>();
	private ArrayList<EBOMMasterColumns> ebomMasterList = new ArrayList<EBOMMasterColumns>();
	private ArrayList<DocumentColumns> docList = new ArrayList<DocumentColumns>();
	private JSONArray partJson = new JSONArray();
	private JSONArray docJson = new JSONArray();
	private boolean isModify = false;
	private boolean isDelete = false;

	public ECODTO() {

	}

	public ECODTO(ECO eco) throws Exception {
		setOid(CommonUtils.oid(eco));
		setNumber(eco.getNumber());
		setName(eco.getName());
		setEcoType(eco.getEcoType());
		setDevType(eco.getDevType());
		setNotiType(eco.getNotiType());
		setLot(eco.getLot());
		setCompany(eco.getCompany());
		setBrand(eco.getBrand());
		setApplyTime(eco.getApplyTime());
//		setExpectationTime(eco.getExpectationTime().toString().substring(0, 10));
		setCreator(eco.getOwnership().getOwner().getFullName());
		setCreatedDate(eco.getCreateTimestamp().toString().substring(0, 10));
		setState(eco.getState());
		setDescription(eco.getDescription());
		setReason(eco.getReason());
		setEcoTypeNm(BaseCodeHelper.manager.getNameByCodeTypeAndCode("ECO_TYPE", this.ecoType));
		setDevTypeNm(BaseCodeHelper.manager.getNameByCodeTypeAndCode("ECO_DEV_TYPE", this.devType));
		setNotiTypeNm(BaseCodeHelper.manager.getNameByCodeTypeAndCode("ECO_NOTI_TYPE", this.notiType));
		setLotNm(BaseCodeHelper.manager.getNameByCodeTypeAndCode("LOT_MGMT", this.lot));
		setCompanyNm(BaseCodeHelper.manager.getNameByCodeTypeAndCode("COMPANY", this.company));
		setBrandNm(BaseCodeHelper.manager.getNameByCodeTypeAndCode("BRAND", this.brand));
		setApplyTimeNm(BaseCodeHelper.manager.getNameByCodeTypeAndCode("ECO_APPLY_TIME", this.applyTime));
		setPartJson(JSONArray.fromObject(ECOHelper.manager.getParts(eco)));
		setDocJson(JSONArray.fromObject(ECOHelper.manager.getDocs(eco)));
		setAuth(eco);
	}

	private void setAuth(ECO eco) throws Exception {
		WTUser user = (WTUser) SessionHelper.manager.getPrincipal();
		if (CommonUtils.isAdmin()) {
			isDelete = true;
			isModify = true;
		} else {
			if (eco.getOwnership().getOwner().getName().equals(user.getName())) {
				if (eco.getState().equals("")) {
					isDelete = true;
					isModify = true;
				}

				if (eco.getState().equals("RETURN")) {
					isModify = true;
				}

			}
		}
	}
}