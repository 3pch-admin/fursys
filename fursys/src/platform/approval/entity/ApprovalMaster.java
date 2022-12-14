package platform.approval.entity;

import java.sql.Timestamp;

import com.ptc.windchill.annotations.metadata.Cardinality;
import com.ptc.windchill.annotations.metadata.ForeignKeyRole;
import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedForeignKey;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.MyRole;
import com.ptc.windchill.annotations.metadata.PropertyConstraints;

import wt.fc.Persistable;
import wt.fc.WTObject;
import wt.org.WTUser;
import wt.ownership.Ownable;
import wt.util.WTException;

@GenAsPersistable(superClass = WTObject.class, interfaces = { Ownable.class },

		properties = {

				@GeneratedProperty(name = "name", type = String.class, javaDoc = "결재 제목"),

				@GeneratedProperty(name = "state", type = String.class, javaDoc = "결재 상태"),
				
				@GeneratedProperty(name = "objType", type = String.class, javaDoc = "결재 타입"),

				@GeneratedProperty(name = "completeTime", type = Timestamp.class, javaDoc = "결재 완료 시간"),

				@GeneratedProperty(name = "startTime", type = Timestamp.class, javaDoc = "결재 시작 시간"),

		},

		foreignKeys = { @GeneratedForeignKey(name = "PersistableLineMasterLink",

				foreignKeyRole = @ForeignKeyRole(name = "persist", type = Persistable.class,

						constraints = @PropertyConstraints(required = true)),

				myRole = @MyRole(name = "lineMaster", cardinality = Cardinality.ONE)),

				@GeneratedForeignKey(name = "MasterUserLink",

						foreignKeyRole = @ForeignKeyRole(name = "user", type = WTUser.class,

								constraints = @PropertyConstraints(required = false)),

						myRole = @MyRole(name = "lineMaster", cardinality = Cardinality.ONE))

		}

)

public class ApprovalMaster extends _ApprovalMaster {

	static final long serialVersionUID = 1;

	public static ApprovalMaster newApprovalMaster() throws WTException {
		ApprovalMaster instance = new ApprovalMaster();
		instance.initialize();
		return instance;
	}
}
