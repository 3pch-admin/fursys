package platform.echange.ecn.entity;

import com.ptc.windchill.annotations.metadata.GenAsBinaryLink;
import com.ptc.windchill.annotations.metadata.GeneratedRole;

import wt.fc.ObjectToObjectLink;
import wt.ownership.Ownable;
import wt.part.WTPart;
import wt.util.WTException;

@GenAsBinaryLink(superClass = ObjectToObjectLink.class, interfaces = { Ownable.class },

		roleA = @GeneratedRole(name = "ecn", type = ECN.class),

		roleB = @GeneratedRole(name = "part", type = WTPart.class))
public class ECNWTPartLink extends _ECNWTPartLink {

	static final long serialVersionUID = 1;

	public static ECNWTPartLink newECNWTPartLink(ECN ecn, WTPart part) throws WTException {
		ECNWTPartLink instance = new ECNWTPartLink();
		instance.initialize(ecn, part);
		return instance;
	}
}
