package platform.echange.ecr.entity;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;
import net.sf.json.JSONArray;
import platform.code.service.BaseCodeHelper;
import platform.doc.entity.DocumentColumns;
import platform.echange.ecr.service.ECRHelper;
import platform.part.entity.PartColumns;
import platform.util.CommonUtils;

@Getter
@Setter
public class ECRDTO {

	private ECR ecr;
	private String oid;
	private String number;
	private String name;
	private String reqType;
	private String limit;
	private String state;
	private String company;
	private String companyNm;
	private String brand;
	private String brandNm;
	private String creatorFullName;
	private String createTimestamp;
	private String reason;
	private String description;
	private String primary;
	private ArrayList<String> secondary = new ArrayList<String>();
	private ArrayList<DocumentColumns> docList = new ArrayList<DocumentColumns>();
	private ArrayList<PartColumns> partList = new ArrayList<PartColumns>();

	private JSONArray partJson = new JSONArray();
	private JSONArray docJson = new JSONArray();

	public ECRDTO() {

	}

	public ECRDTO(ECR ecr) throws Exception {
		setOid(CommonUtils.oid(ecr));
		setName(ecr.getName());
		setNumber(ecr.getNumber());
		setName(ecr.getName());
		setReqType(ecr.getReqType());
		setLimit(ecr.getLimit().toString().substring(0, 10));
		setState(ecr.getState());
		setCompany(ecr.getCompany());
		setBrand(ecr.getBrand());
		setCreatorFullName(ecr.getOwnership().getOwner().getFullName());
		setCreateTimestamp(ecr.getCreateTimestamp().toString().substring(0, 10));
		setReason(ecr.getReason());
		setDescription(ecr.getDescription());
		setCompanyNm(BaseCodeHelper.manager.getNameByCodeTypeAndCode("COMPANY", this.company));
		setBrandNm(BaseCodeHelper.manager.getNameByCodeTypeAndCode("BRAND", this.brand));
		setPartJson(JSONArray.fromObject(ECRHelper.manager.getParts(ecr)));
		setDocJson(JSONArray.fromObject(ECRHelper.manager.getDocs(ecr)));
	}
}