package platform.approval.entity;

import com.ptc.windchill.annotations.metadata.GenAsBinaryLink;
import com.ptc.windchill.annotations.metadata.GeneratedRole;

import wt.fc.ObjectToObjectLink;
import wt.fc.Persistable;
import wt.util.WTException;

@GenAsBinaryLink(superClass = ObjectToObjectLink.class,

		roleA = @GeneratedRole(name = "line", type = ApprovalLine.class),

		roleB = @GeneratedRole(name = "per", type = Persistable.class)

)
public class ApprovalHistoryLink extends _ApprovalHistoryLink {
	static final long serialVersionUID = 1;

	public static ApprovalHistoryLink newApprovalHistoryLink(ApprovalLine line, Persistable per) throws WTException {
		ApprovalHistoryLink instance = new ApprovalHistoryLink();
		instance.initialize(line, per);
		return instance;
	}

}
