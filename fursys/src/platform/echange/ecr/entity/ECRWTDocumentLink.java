package platform.echange.ecr.entity;

import com.ptc.windchill.annotations.metadata.GenAsBinaryLink;
import com.ptc.windchill.annotations.metadata.GeneratedRole;

import wt.doc.WTDocument;
import wt.fc.ObjectToObjectLink;
import wt.ownership.Ownable;
import wt.util.WTException;

@GenAsBinaryLink(superClass = ObjectToObjectLink.class, interfaces = { Ownable.class },

		roleA = @GeneratedRole(name = "ecr", type = ECR.class),

		roleB = @GeneratedRole(name = "doc", type = WTDocument.class))
public class ECRWTDocumentLink extends _ECRWTDocumentLink {

	static final long serialVersionUID = 1;

	public static ECRWTDocumentLink newECRequestWTDocumentLink(ECR ecr, WTDocument doc) throws WTException {
		ECRWTDocumentLink instance = new ECRWTDocumentLink();
		instance.initialize(ecr, doc);
		return instance;
	}
}