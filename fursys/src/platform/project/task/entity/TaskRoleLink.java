package platform.project.task.entity;

import com.ptc.windchill.annotations.metadata.Cardinality;
import com.ptc.windchill.annotations.metadata.ForeignKeyRole;
import com.ptc.windchill.annotations.metadata.GenAsBinaryLink;
import com.ptc.windchill.annotations.metadata.GeneratedForeignKey;
import com.ptc.windchill.annotations.metadata.GeneratedRole;
import com.ptc.windchill.annotations.metadata.MyRole;
import com.ptc.windchill.annotations.metadata.PropertyConstraints;

import platform.code.entity.BaseCode;
import platform.project.entity.Project;
import platform.project.template.entity.Template;
import wt.fc.ObjectToObjectLink;
import wt.org.WTUser;
import wt.util.WTException;

@GenAsBinaryLink(superClass = ObjectToObjectLink.class,

		roleA = @GeneratedRole(name = "task", type = Task.class),

		roleB = @GeneratedRole(name = "role", type = BaseCode.class),

		foreignKeys = {

				@GeneratedForeignKey(name = "UserRoleLink",

						foreignKeyRole = @ForeignKeyRole(name = "user", type = WTUser.class,

								constraints = @PropertyConstraints(required = false)),

						myRole = @MyRole(name = "role", cardinality = Cardinality.ONE)),

				@GeneratedForeignKey(name = "TemplateRoleLink",

						foreignKeyRole = @ForeignKeyRole(name = "template", type = Template.class,

								constraints = @PropertyConstraints(required = false)),

						myRole = @MyRole(name = "role", cardinality = Cardinality.ONE)),

				@GeneratedForeignKey(name = "ProjectRoleLink",

						foreignKeyRole = @ForeignKeyRole(name = "project", type = Project.class,

								constraints = @PropertyConstraints(required = false)),

						myRole = @MyRole(name = "role", cardinality = Cardinality.ONE)) })
public class TaskRoleLink extends _TaskRoleLink {
	static final long serialVersionUID = 1;

	public static TaskRoleLink newTaskRoleLink(Task task, BaseCode role) throws WTException {
		TaskRoleLink instance = new TaskRoleLink();
		instance.initialize(task, role);
		return instance;
	}
}
