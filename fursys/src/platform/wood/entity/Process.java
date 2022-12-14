package platform.wood.entity;

import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;

import platform.tree.entity.Edge;
import platform.tree.entity._Edge;
import wt.fc.WTObject;
import wt.ownership.Ownable;
import wt.util.WTException;

@GenAsPersistable(superClass = WTObject.class, interfaces = { Ownable.class },

		properties = {

				@GeneratedProperty(name = "code", type = String.class),

				@GeneratedProperty(name = "rank", type = String.class),

				@GeneratedProperty(name = "process", type = String.class),

		}

)
public class Process extends _Process {

	static final long serialVersionUID = 1;

	public static Process newProcess() throws WTException {
		Process instance = new Process();
		instance.initialize();
		return instance;
	}
}
