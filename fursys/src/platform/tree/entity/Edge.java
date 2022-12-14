package platform.tree.entity;

import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;

import wt.fc.WTObject;
import wt.ownership.Ownable;
import wt.util.WTException;

@GenAsPersistable(superClass = WTObject.class, interfaces = { Ownable.class },

		properties = {

				@GeneratedProperty(name = "code", type = String.class),

				@GeneratedProperty(name = "rank", type = Integer.class),

				@GeneratedProperty(name = "edgeType", type = String.class),
				
				@GeneratedProperty(name="Dtmg_treatment", type = String.class),
				
				@GeneratedProperty(name = "etc" , type = String.class)

		}

)
public class Edge extends _Edge {

	static final long serialVersionUID = 1;

	public static Edge newEdge() throws WTException {
		Edge instance = new Edge();
		instance.initialize();
		return instance;
	}
}
