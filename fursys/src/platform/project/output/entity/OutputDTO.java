package platform.project.output.entity;

import lombok.Getter;
import lombok.Setter;
import platform.util.CommonUtils;
import platform.util.StringUtils;

@Getter
@Setter
public class OutputDTO {

	private String oid;
	private String name;
	private String description;
	private String location;

	public OutputDTO() {

	}

	public OutputDTO(Output output) throws Exception {
		setOid(CommonUtils.oid(output));
		setName(output.getName());
		setDescription(StringUtils.convertToStr(output.getDescription(), ""));
		setLocation(output.getLocation());
	}
}
