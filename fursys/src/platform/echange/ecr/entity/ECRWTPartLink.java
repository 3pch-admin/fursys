package platform.echange.ecr.entity;

import com.ptc.windchill.annotations.metadata.Cardinality;
import com.ptc.windchill.annotations.metadata.GenAsBinaryLink;
import com.ptc.windchill.annotations.metadata.GeneratedRole;

import wt.fc.ObjectToObjectLink;
import wt.ownership.Ownable;
import wt.part.WTPart;
import wt.util.WTException;

@GenAsBinaryLink(superClass = ObjectToObjectLink.class, interfaces = { Ownable.class },

		roleA = @GeneratedRole(name = "ecr", type = ECR.class),

		roleB = @GeneratedRole(name = "part", type = WTPart.class, cardinality = Cardinality.MANY)

)
public class ECRWTPartLink extends _ECRWTPartLink {

	static final long serialVersionUID = 1;

	public static ECRWTPartLink newECRequestWTPartLink(ECR ecr, WTPart part) throws WTException {
		ECRWTPartLink instance = new ECRWTPartLink();
		instance.initialize(ecr, part);
		return instance;
	}
}