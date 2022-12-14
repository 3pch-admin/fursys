package platform.echange.ecr.entity;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import platform.code.service.BaseCodeHelper;
import platform.util.CommonUtils;

@Getter
@Setter
public class ECRColumns {
	private int no;
	private String oid;
	private String number;
	private String name;
	private String reqType;
	private Timestamp limit;
	private String state;
	private String company;
	private String brand;
	private String companyNm;
	private String brandNm;
	private String creator;
	private String createdDate;
	private String modifier;
	private String modifiedDate;

	public ECRColumns() {

	}

	public ECRColumns(ECR ecr) throws Exception {
		setOid(CommonUtils.oid(ecr));
		setNumber(ecr.getNumber());
		setName(ecr.getName());
		setReqType(ecr.getReqType());
		setLimit(ecr.getLimit());
		setState(ecr.getState());
		setCreator(ecr.getOwnership().getOwner().getFullName());
		setCreatedDate(ecr.getCreateTimestamp().toString().substring(0, 10));
		setBrand(ecr.getBrand());
		setCompany(ecr.getCompany());
		setCompanyNm(BaseCodeHelper.manager.getNameByCodeTypeAndCode("COMPANY", this.company));
		setBrandNm(BaseCodeHelper.manager.getNameByCodeTypeAndCode("BRAND", this.brand));
		setModifier(ecr.getOwnership().getOwner().getFullName());
		setModifiedDate(ecr.getModifyTimestamp().toString().substring(0, 10));
	}
}