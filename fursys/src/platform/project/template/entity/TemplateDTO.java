package platform.project.template.entity;

import java.sql.Timestamp;
import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;
import platform.code.service.BaseCodeHelper;
import platform.project.task.entity.Task;
import platform.project.template.service.TemplateHelper;
import platform.util.CommonUtils;
import platform.util.StringUtils;

@Getter
@Setter
public class TemplateDTO {

	private String oid;
	private String name;
	private String number;
	private String description;
	private int duration;
	private Timestamp createdDate;
	private String creator;
	private String company;
	private String companyNm;
	private boolean enable;
	private ArrayList<Task> childrens = new ArrayList<>();

	public TemplateDTO() {

	}

	public TemplateDTO(Template template) throws Exception {
		setOid(CommonUtils.oid(template));
		setName(template.getName());
		setNumber(template.getNumber());
		setDescription(template.getDescription());
		setCompany(template.getCompany());
		setEnable(template.getEnable());
		setDescription(StringUtils.convertToStr(template.getDescription(), ""));
		setDuration(template.getDuration());
		setCreatedDate(template.getCreateTimestamp());
		setCreator(template.getOwnership().getOwner().getFullName());
		setChildrens(TemplateHelper.manager.getTasks(template));
		setCompanyNm(BaseCodeHelper.manager.getNameByCodeTypeAndCode("COMPANY", this.company));
	}
}
