package platform.dist.entity;

import com.ptc.windchill.annotations.metadata.GenAsBinaryLink;
import com.ptc.windchill.annotations.metadata.GeneratedRole;

import wt.fc.ObjectToObjectLink;
import wt.util.WTException;

@GenAsBinaryLink(superClass = ObjectToObjectLink.class,

		roleA = @GeneratedRole(name = "disPart", type = DistPartLink.class),

		roleB = @GeneratedRole(name = "distUser", type = DistributorUser.class)

)
public class DistPartDistributorUserLink extends _DistPartDistributorUserLink {
	static final long serialVersionUID = 1;

	public static DistPartDistributorUserLink newDistPartLink(DistPartLink disPart, DistributorUser disUser) throws WTException {
		DistPartDistributorUserLink instance = new DistPartDistributorUserLink();
		instance.initialize(disPart, disUser);
		return instance;
	}
}
