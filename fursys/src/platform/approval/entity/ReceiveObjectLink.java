package platform.approval.entity;

import com.ptc.windchill.annotations.metadata.GenAsBinaryLink;
import com.ptc.windchill.annotations.metadata.GeneratedRole;

import wt.fc.ObjectToObjectLink;
import wt.fc.WTObject;
import wt.org.WTUser;
import wt.util.WTException;

@GenAsBinaryLink(superClass = ObjectToObjectLink.class,

		roleA = @GeneratedRole(name = "receive", type = WTUser.class),

		roleB = @GeneratedRole(name = "obj", type = WTObject.class)

)
public class ReceiveObjectLink extends _ReceiveObjectLink {
	static final long serialVersionUID = 1;

	public static ReceiveObjectLink newReceiveObjectLink(WTUser wtUser, WTObject obj) throws WTException {
		ReceiveObjectLink instance = new ReceiveObjectLink();
		instance.initialize(wtUser, obj);
		return instance;
	}
}
