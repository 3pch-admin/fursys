package platform.project.task.entity;

import com.ptc.windchill.annotations.metadata.GenAsBinaryLink;
import com.ptc.windchill.annotations.metadata.GeneratedRole;

import wt.fc.ObjectToObjectLink;
import wt.util.WTException;

@GenAsBinaryLink(superClass = ObjectToObjectLink.class,

		roleA = @GeneratedRole(name = "preTask", type = Task.class),

		roleB = @GeneratedRole(name = "postTask", type = Task.class)

)
public class PreTaskPostTaskLink extends _PreTaskPostTaskLink {
	static final long serialVersionUID = 1;

	public static PreTaskPostTaskLink newPreTaskPostTaskLink(Task preTask, Task postTask) throws WTException {
		PreTaskPostTaskLink instance = new PreTaskPostTaskLink();
		instance.initialize(preTask, postTask);
		return instance;
	}

}
