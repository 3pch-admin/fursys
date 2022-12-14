package platform.approval.entity;

import com.ptc.windchill.annotations.metadata.GenAsBinaryLink;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.GeneratedRole;

import wt.fc.ObjectToObjectLink;
import wt.org.WTUser;
import wt.util.WTException;

@GenAsBinaryLink(superClass = ObjectToObjectLink.class,

		roleA = @GeneratedRole(name = "wtUser", type = WTUser.class),

		roleB = @GeneratedRole(name = "lineMaster", type = ApprovalMaster.class),

		properties = {

				@GeneratedProperty(name = "index1", type = String.class, javaDoc = "검토1"),

				@GeneratedProperty(name = "index2", type = String.class, javaDoc = "검토2"),

				@GeneratedProperty(name = "index3", type = String.class, javaDoc = "검토3"),

				@GeneratedProperty(name = "index4", type = String.class, javaDoc = "검토4"),

				@GeneratedProperty(name = "index5", type = String.class, javaDoc = "승인"),

		}

)
public class LatestApprovalLine extends _LatestApprovalLine {
	static final long serialVersionUID = 1;

	public static LatestApprovalLine newLatestApprovalLine(WTUser wtUser, ApprovalMaster lineMaster)
			throws WTException {
		LatestApprovalLine instance = new LatestApprovalLine();
		instance.initialize(wtUser, lineMaster);
		return instance;
	}
}
