package platform.project.template.entity;

import lombok.Getter;
import lombok.Setter;
import platform.code.service.BaseCodeHelper;
import platform.util.CommonUtils;

@Setter
@Getter
public class TemplateColumns {

	private String oid;
	private int no;
	private boolean enable;
	private String name;
	private String number;
	private String duration;
	private String createdDate;
	private String creator;
	private String company;
	private String companyNm;

	public TemplateColumns() {

	}

	public TemplateColumns(Template template) throws Exception {
		setOid(CommonUtils.oid(template));
		setName(template.getName());
		setNumber(template.getNumber());
		setDuration(template.getDuration() + "일");
		setCreatedDate(template.getCreateTimestamp().toString().substring(0, 10));
		setCreator(template.getOwnership().getOwner().getFullName());
		setCompany(template.getCompany());
		setCompanyNm(BaseCodeHelper.manager.getNameByCodeTypeAndCode("COMPANY", this.company));
	}
}
