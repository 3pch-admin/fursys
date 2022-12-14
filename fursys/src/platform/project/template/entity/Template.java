package platform.project.template.entity;

import java.sql.Timestamp;

import com.ptc.windchill.annotations.metadata.ColumnProperties;
import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.PropertyConstraints;

import wt.fc.WTObject;
import wt.ownership.Ownable;
import wt.util.WTException;

@GenAsPersistable(superClass = WTObject.class, interfaces = { Ownable.class },

		properties = {

				@GeneratedProperty(name = "name", type = String.class, javaDoc = "템플릿 이름"),

				@GeneratedProperty(name = "number", type = String.class, javaDoc = "템플릿 번호", columnProperties = @ColumnProperties(columnName = "templateNumber")),

				@GeneratedProperty(name = "duration", type = Integer.class, javaDoc = "기간"),

				@GeneratedProperty(name = "company", type = String.class, javaDoc = "회사"),

				@GeneratedProperty(name = "planStartDate", type = Timestamp.class, javaDoc = "계획시작일"),

				@GeneratedProperty(name = "planEndDate", type = Timestamp.class, javaDoc = "계획종료일"),

				@GeneratedProperty(name = "enable", type = Boolean.class, javaDoc = "사용여부", initialValue = "true"),

				@GeneratedProperty(name = "description", type = String.class, javaDoc = "설명", constraints = @PropertyConstraints(upperLimit = 4000)), })

public class Template extends _Template {

	static final long serialVersionUID = 1;

	public static Template newTemplate() throws WTException {
		Template instance = new Template();
		instance.initialize();
		return instance;
	}
}
