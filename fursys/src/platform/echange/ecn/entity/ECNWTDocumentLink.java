package platform.echange.ecn.entity;

import com.ptc.windchill.annotations.metadata.GenAsBinaryLink;
import com.ptc.windchill.annotations.metadata.GeneratedRole;

import wt.doc.WTDocument;
import wt.fc.ObjectToObjectLink;
import wt.ownership.Ownable;
import wt.util.WTException;

@GenAsBinaryLink(superClass = ObjectToObjectLink.class, interfaces = { Ownable.class },

		roleA = @GeneratedRole(name = "ecn", type = ECN.class),

		roleB = @GeneratedRole(name = "doc", type = WTDocument.class))
public class ECNWTDocumentLink extends _ECNWTDocumentLink {

	static final long serialVersionUID = 1;

	public static ECNWTDocumentLink newECNWTDocumentLink(ECN ecn, WTDocument doc) throws WTException {
		ECNWTDocumentLink instance = new ECNWTDocumentLink();
		instance.initialize(ecn, doc);
		return instance;
	}
}
