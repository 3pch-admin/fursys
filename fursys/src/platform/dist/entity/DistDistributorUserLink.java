package platform.dist.entity;

import com.ptc.windchill.annotations.metadata.GenAsBinaryLink;
import com.ptc.windchill.annotations.metadata.GeneratedRole;

import wt.fc.ObjectToObjectLink;
import wt.util.WTException;

@GenAsBinaryLink(superClass = ObjectToObjectLink.class,

		roleA = @GeneratedRole(name = "dist", type = Dist.class),

		roleB = @GeneratedRole(name = "distUser", type = DistributorUser.class)

)
public class DistDistributorUserLink extends _DistDistributorUserLink {
	static final long serialVersionUID = 1;

	public static DistDistributorUserLink newDistDistributorUserLink(Dist dist, DistributorUser disUser) throws WTException {
		DistDistributorUserLink instance = new DistDistributorUserLink();
		instance.initialize(dist, disUser);
		return instance;
	}
}
