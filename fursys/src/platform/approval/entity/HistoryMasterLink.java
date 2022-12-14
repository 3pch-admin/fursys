package platform.approval.entity;

import com.ptc.windchill.annotations.metadata.GenAsBinaryLink;
import com.ptc.windchill.annotations.metadata.GeneratedRole;

import wt.fc.ObjectToObjectLink;
import wt.util.WTException;
import wt.vc.Mastered;

@GenAsBinaryLink(superClass = ObjectToObjectLink.class,

		roleA = @GeneratedRole(name = "line", type = ApprovalLine.class),

		roleB = @GeneratedRole(name = "master", type = Mastered.class)

)
public class HistoryMasterLink extends _HistoryMasterLink {
	static final long serialVersionUID = 1;

	public static HistoryMasterLink newHistoryMasterLink(ApprovalLine line, Mastered master) throws WTException {
		HistoryMasterLink instance = new HistoryMasterLink();
		instance.initialize(line, master);
		return instance;
	}

}
