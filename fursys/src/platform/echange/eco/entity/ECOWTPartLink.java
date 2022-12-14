package platform.echange.eco.entity;

import com.ptc.windchill.annotations.metadata.GenAsBinaryLink;
import com.ptc.windchill.annotations.metadata.GeneratedRole;

import wt.fc.ObjectToObjectLink;
import wt.ownership.Ownable;
import wt.part.WTPart;
import wt.util.WTException;

@GenAsBinaryLink(superClass = ObjectToObjectLink.class, interfaces = { Ownable.class },

		roleA = @GeneratedRole(name = "eco", type = ECO.class),

		roleB = @GeneratedRole(name = "part", type = WTPart.class))
public class ECOWTPartLink extends _ECOWTPartLink {

	static final long serialVersionUID = 1;

	public static ECOWTPartLink newECOWTPartLink(ECO eco, WTPart part) throws WTException {
		ECOWTPartLink instance = new ECOWTPartLink();
		instance.initialize(eco, part);
		return instance;
	}
}
