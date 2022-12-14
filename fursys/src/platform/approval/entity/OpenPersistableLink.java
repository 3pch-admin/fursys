package platform.approval.entity;

import com.ptc.windchill.annotations.metadata.GenAsBinaryLink;
import com.ptc.windchill.annotations.metadata.GeneratedRole;

import wt.fc.ObjectToObjectLink;
import wt.fc.Persistable;
import wt.org.WTUser;
import wt.util.WTException;

@GenAsBinaryLink(superClass = ObjectToObjectLink.class,

		roleA = @GeneratedRole(name = "wtUser", type = WTUser.class),

		roleB = @GeneratedRole(name = "persist", type = Persistable.class)

)

public class OpenPersistableLink extends _OpenPersistableLink {

	static final long serialVersionUID = 1;

	public static OpenPersistableLink newOpenPersistableLink(WTUser wtUser, Persistable persist) throws WTException {
		OpenPersistableLink instance = new OpenPersistableLink();
		instance.initialize(wtUser, persist);
		return instance;
	}

}
