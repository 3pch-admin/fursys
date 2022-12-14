package platform.project.entity;

import com.ptc.windchill.annotations.metadata.GenAsBinaryLink;
import com.ptc.windchill.annotations.metadata.GeneratedRole;

import platform.code.entity.BaseCode;
import wt.fc.ObjectToObjectLink;
import wt.util.WTException;

@GenAsBinaryLink(superClass = ObjectToObjectLink.class,

		roleA = @GeneratedRole(name = "project", type = Project.class),

		roleB = @GeneratedRole(name = "role", type = BaseCode.class)

)

public class ProjectRoleLink extends _ProjectRoleLink {

	static final long serialVersionUID = 1;

	public static ProjectRoleLink newProjectRoleLink(Project project, BaseCode role) throws WTException {
		ProjectRoleLink instance = new ProjectRoleLink();
		instance.initialize(project, role);
		return instance;
	}
}
