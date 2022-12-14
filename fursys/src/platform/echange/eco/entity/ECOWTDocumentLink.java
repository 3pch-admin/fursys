package platform.echange.eco.entity;

import com.ptc.windchill.annotations.metadata.GenAsBinaryLink;
import com.ptc.windchill.annotations.metadata.GeneratedRole;

import wt.doc.WTDocument;
import wt.fc.ObjectToObjectLink;
import wt.ownership.Ownable;
import wt.util.WTException;

@GenAsBinaryLink(superClass = ObjectToObjectLink.class, interfaces = { Ownable.class },

		roleA = @GeneratedRole(name = "eco", type = ECO.class),

		roleB = @GeneratedRole(name = "doc", type = WTDocument.class))
public class ECOWTDocumentLink extends _ECOWTDocumentLink {

	static final long serialVersionUID = 1;

	public static ECOWTDocumentLink newECOWTDocumentLink(ECO eco, WTDocument doc) throws WTException {
		ECOWTDocumentLink instance = new ECOWTDocumentLink();
		instance.initialize(eco, doc);
		return instance;
	}
}
