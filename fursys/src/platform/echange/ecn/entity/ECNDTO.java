package platform.echange.ecn.entity;

import java.sql.Timestamp;
import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;
import platform.code.service.BaseCodeHelper;
import platform.doc.entity.DocumentColumns;
import platform.part.entity.PartColumns;
import platform.util.CommonUtils;

@Setter
@Getter
public class ECNDTO {
	private String oid;
	private String number;
	private String name;
	private String creator;
	private Timestamp createdDate;
	private String state;
	private String notiType;
	private String notiTypeNm;
	private String applyTime;
	private String applyTimeNm;
	private String plant;
	private String plantNm;
	private Timestamp applicationDate;
	private String brand;
	private String brandNm;
	private String company;
	private String companyNm;
	private ArrayList<String> secondary = new ArrayList<String>();
	private String eoid;
	private ArrayList<DocumentColumns> docList = new ArrayList<DocumentColumns>();
	private ArrayList<PartColumns> partList = new ArrayList<PartColumns>();

	public ECNDTO() {

	}

	public ECNDTO(ECN ecn) throws Exception {
		setOid(CommonUtils.oid(ecn));
		setNumber(ecn.getNumber());
		setName(ecn.getName());
		setCreator(ecn.getOwnership().getOwner().getFullName());
		setCreatedDate(ecn.getCreateTimestamp());
		setState(ecn.getState());
		setNotiType(ecn.getNotiType());
		setApplyTime(ecn.getEcnApplyTime());
		setPlant(ecn.getPlant());
		setApplicationDate(ecn.getApplicationDate());
		setBrand(ecn.getBrand());
		setCompany(ecn.getCompany());
		setPlantNm(this.plant);
		setPlantNm(BaseCodeHelper.manager.getNameByCodeTypeAndCode("PLANT", this.plant));
		setCompanyNm(BaseCodeHelper.manager.getNameByCodeTypeAndCode("COMPANY", this.company));
		setBrandNm(BaseCodeHelper.manager.getNameByCodeTypeAndCode("BRAND", this.brand));
		setNotiTypeNm(BaseCodeHelper.manager.getNameByCodeTypeAndCode("ECO_NOTI_TYPE", this.notiType));
		setApplyTimeNm(BaseCodeHelper.manager.getNameByCodeTypeAndCode("ECO_APPLY_TIME", this.applyTime));
	}
}