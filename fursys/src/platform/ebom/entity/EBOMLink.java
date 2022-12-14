package platform.ebom.entity;

import com.ptc.windchill.annotations.metadata.GenAsBinaryLink;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.GeneratedRole;

import wt.fc.ObjectToObjectLink;
import wt.util.WTException;

@GenAsBinaryLink(superClass = ObjectToObjectLink.class,

		roleA = @GeneratedRole(name = "parent", type = EBOM.class),

		roleB = @GeneratedRole(name = "child", type = EBOM.class),

		properties = {

				@GeneratedProperty(name = "amount", type = Double.class)

		}

)
public class EBOMLink extends _EBOMLink {
	static final long serialVersionUID = 1;

	public static EBOMLink newEBOMLink(EBOM parent, EBOM child) throws WTException {
		EBOMLink instance = new EBOMLink();
		instance.initialize(parent, child);
		return instance;
	}
}
