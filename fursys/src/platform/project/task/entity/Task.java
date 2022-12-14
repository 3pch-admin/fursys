package platform.project.task.entity;

import java.sql.Timestamp;

import com.ptc.windchill.annotations.metadata.Cardinality;
import com.ptc.windchill.annotations.metadata.ForeignKeyRole;
import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedForeignKey;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.MyRole;
import com.ptc.windchill.annotations.metadata.PropertyConstraints;
import com.ptc.windchill.annotations.metadata.TableProperties;

import platform.project.entity.Project;
import platform.project.template.entity.Template;
import wt.fc.WTObject;
import wt.ownership.Ownable;
import wt.util.WTException;

@GenAsPersistable(superClass = WTObject.class, interfaces = { Ownable.class },

		tableProperties = @TableProperties(tableName = "E_TASK"),

		properties = { @GeneratedProperty(name = "name", type = String.class, javaDoc = "태스크 명"),

				@GeneratedProperty(name = "sort", type = Integer.class, javaDoc = "태스크 순서", initialValue = "0"),

				@GeneratedProperty(name = "depth", type = Integer.class),

				@GeneratedProperty(name = "state", type = String.class, javaDoc = "상태"),

				@GeneratedProperty(name = "progress", type = Double.class, initialValue = "0D"),

				@GeneratedProperty(name = "planStartDate", type = Timestamp.class),

				@GeneratedProperty(name = "duration", type = Integer.class),

				@GeneratedProperty(name = "planEndDate", type = Timestamp.class),

				@GeneratedProperty(name = "startDate", type = Timestamp.class),

				@GeneratedProperty(name = "endDate", type = Timestamp.class),

				@GeneratedProperty(name = "etc", type = Double.class, initialValue = "0D"),

				@GeneratedProperty(name = "labor", type = Double.class, initialValue = "0D"),

				@GeneratedProperty(name = "material", type = Double.class, initialValue = "0D"),

				@GeneratedProperty(name = "description", type = String.class, constraints = @PropertyConstraints(upperLimit = 4000)) },

		foreignKeys = { @GeneratedForeignKey(name = "ParentTaskChildTaskLink",

				foreignKeyRole = @ForeignKeyRole(name = "parentTask", type = Task.class,

						constraints = @PropertyConstraints(required = false)),

				myRole = @MyRole(name = "childTask", cardinality = Cardinality.MANY)),

				@GeneratedForeignKey(name = "TaskProjectLink",

						foreignKeyRole = @ForeignKeyRole(name = "project", type = Project.class,

								constraints = @PropertyConstraints(required = false)),

						myRole = @MyRole(name = "task", cardinality = Cardinality.ONE)),

				@GeneratedForeignKey(name = "TaskTemplateLink",

						foreignKeyRole = @ForeignKeyRole(name = "template", type = Template.class,

								constraints = @PropertyConstraints(required = false)),

						myRole = @MyRole(name = "task", cardinality = Cardinality.ONE)) }

)
public class Task extends _Task {

	static final long serialVersionUID = 1;

	public static Task newTask() throws WTException {
		Task instance = new Task();
		instance.initialize();
		return instance;
	}
}
