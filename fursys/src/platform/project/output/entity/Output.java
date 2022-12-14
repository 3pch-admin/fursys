package platform.project.output.entity;

import com.ptc.windchill.annotations.metadata.Cardinality;
import com.ptc.windchill.annotations.metadata.ForeignKeyRole;
import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedForeignKey;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.MyRole;
import com.ptc.windchill.annotations.metadata.PropertyConstraints;

import platform.project.entity.Project;
import platform.project.task.entity.Task;
import platform.project.template.entity.Template;
import wt.doc.WTDocument;
import wt.fc.WTObject;
import wt.ownership.Ownable;
import wt.util.WTException;

@GenAsPersistable(superClass = WTObject.class, interfaces = { Ownable.class },

		properties = {

				@GeneratedProperty(name = "location", type = String.class, javaDoc = "산출물 저장 위치"),

				@GeneratedProperty(name = "name", type = String.class, javaDoc = "이름"),

				@GeneratedProperty(name = "description", type = String.class, constraints = @PropertyConstraints(upperLimit = 2000)),

		},

		foreignKeys = {

				@GeneratedForeignKey(name = "WTDocumentOutputLink",

						foreignKeyRole = @ForeignKeyRole(name = "document", type = WTDocument.class,

								constraints = @PropertyConstraints(required = false)),

						myRole = @MyRole(name = "output", cardinality = Cardinality.ONE)),

				@GeneratedForeignKey(name = "ProjectOutputLink",

						foreignKeyRole = @ForeignKeyRole(name = "project", type = Project.class,

								constraints = @PropertyConstraints(required = false)),

						myRole = @MyRole(name = "output", cardinality = Cardinality.ONE)),

				@GeneratedForeignKey(name = "TemplateOutputLink",

						foreignKeyRole = @ForeignKeyRole(name = "template", type = Template.class,

								constraints = @PropertyConstraints(required = false)),

						myRole = @MyRole(name = "output", cardinality = Cardinality.ONE)),

				@GeneratedForeignKey(name = "TaskOutputLink",

						foreignKeyRole = @ForeignKeyRole(name = "task", type = Task.class,

								constraints = @PropertyConstraints(required = false)),

						myRole = @MyRole(name = "output", cardinality = Cardinality.ONE)) }

)
public class Output extends _Output {
	static final long serialVersionUID = 1;

	public static Output newOutput() throws WTException {
		Output instance = new Output();
		instance.initialize();
		return instance;
	}
}
