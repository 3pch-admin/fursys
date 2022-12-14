package platform.dist.entity;

import com.ptc.windchill.annotations.metadata.GenAsBinaryLink;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.GeneratedRole;

import wt.fc.ObjectToObjectLink;
import wt.part.WTPart;
import wt.util.WTException;

@GenAsBinaryLink(superClass = ObjectToObjectLink.class,

		roleA = @GeneratedRole(name = "dist", type = Dist.class),

		roleB = @GeneratedRole(name = "part", type = WTPart.class),

		properties = {

				@GeneratedProperty(name = "pdf", type = boolean.class),

				@GeneratedProperty(name = "dwg", type = boolean.class),

				@GeneratedProperty(name = "step", type = boolean.class),
				
				@GeneratedProperty(name = "distributorType", type = String.class),
				@GeneratedProperty(name = "distributorName", type = String.class),
				@GeneratedProperty(name = "distributorUserName", type = String.class),

		}

)
public class DistPartLink extends _DistPartLink {
	static final long serialVersionUID = 1;

	public static DistPartLink newDistPartLink(Dist dist, WTPart part) throws WTException {
		DistPartLink instance = new DistPartLink();
		instance.initialize(dist, part);
		return instance;
	}
}
