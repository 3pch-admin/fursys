package platform.project.entity;

import java.sql.Timestamp;

import com.ptc.windchill.annotations.metadata.Cardinality;
import com.ptc.windchill.annotations.metadata.ColumnProperties;
import com.ptc.windchill.annotations.metadata.ForeignKeyRole;
import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedForeignKey;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.MyRole;
import com.ptc.windchill.annotations.metadata.PropertyConstraints;

import wt.fc.WTObject;
import wt.org.WTUser;
import wt.ownership.Ownable;
import wt.util.WTException;

@GenAsPersistable(superClass = WTObject.class, interfaces = { Ownable.class },

		properties = {

				@GeneratedProperty(name = "name", type = String.class, javaDoc = "프로젝트 이름"),

				@GeneratedProperty(name = "number", type = String.class, javaDoc = "프로젝트 번호", columnProperties = @ColumnProperties(columnName = "projectNumber")),

				@GeneratedProperty(name = "state", type = String.class, javaDoc = "진행상태"),

				@GeneratedProperty(name = "progress", type = Double.class, javaDoc = "진행율"),

				@GeneratedProperty(name = "budget", type = Double.class, javaDoc = "목표(예산)"),

				@GeneratedProperty(name = "customer", type = String.class, javaDoc = "고객사"),

				@GeneratedProperty(name = "projectType", type = String.class, javaDoc = "프로젝트 타입"),

				@GeneratedProperty(name = "planStartDate", type = Timestamp.class, javaDoc = "계획시작일"),

				@GeneratedProperty(name = "planEndDate", type = Timestamp.class, javaDoc = "계획종료일"),

				@GeneratedProperty(name = "startDate", type = Timestamp.class, javaDoc = "시작일자"),

				@GeneratedProperty(name = "endDate", type = Timestamp.class, javaDoc = "종료일자"),

				@GeneratedProperty(name = "description", type = String.class, constraints = @PropertyConstraints(upperLimit = 4000)),

				@GeneratedProperty(name = "enable", type = Boolean.class, javaDoc = "사용여부", initialValue = "true"),

				@GeneratedProperty(name = "etc", type = Double.class, initialValue = "0D"),

				@GeneratedProperty(name = "labor", type = Double.class, initialValue = "0D"),

				@GeneratedProperty(name = "material", type = Double.class, initialValue = "0D"),

				@GeneratedProperty(name = "total", type = Double.class, initialValue = "0D"),

		},

		foreignKeys = { @GeneratedForeignKey(name = "PmProjectLink",

				foreignKeyRole = @ForeignKeyRole(name = "pm", type = WTUser.class,

						constraints = @PropertyConstraints(required = true)),

				myRole = @MyRole(name = "project", cardinality = Cardinality.ONE)),

		}

)
public class Project extends _Project {

	static final long serialVersionUID = 1;

	public static Project newProject() throws WTException {
		Project instance = new Project();
		instance.initialize();
		return instance;
	}
}
