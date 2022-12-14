package platform.approval.entity;

import java.sql.Timestamp;

import com.ptc.windchill.annotations.metadata.Cardinality;
import com.ptc.windchill.annotations.metadata.ForeignKeyRole;
import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedForeignKey;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.MyRole;
import com.ptc.windchill.annotations.metadata.PropertyConstraints;

import wt.fc.WTObject;
import wt.org.WTUser;
import wt.ownership.Ownable;
import wt.util.WTException;

@GenAsPersistable(superClass = WTObject.class, interfaces = { Ownable.class },

		properties = {

				@GeneratedProperty(name = "role", type = String.class, javaDoc = "결재 역할"),

				@GeneratedProperty(name = "lineType", type = String.class, javaDoc = "결재 타입"),

				@GeneratedProperty(name = "sort", type = Integer.class, javaDoc = "결재 순서"),

				@GeneratedProperty(name = "state", type = String.class, javaDoc = "결재 상태"),

				@GeneratedProperty(name = "limit", type = Timestamp.class, javaDoc = "의견 만기일"),

				@GeneratedProperty(name = "startTime", type = Timestamp.class, javaDoc = "결재 시작 시간"),

				@GeneratedProperty(name = "completeTime", type = Timestamp.class, javaDoc = "결재 완료 시간"),

				@GeneratedProperty(name = "description", type = String.class, javaDoc = "결재 의견"),

		},

		foreignKeys = {

				@GeneratedForeignKey(name = "LineMasterLink",

						foreignKeyRole = @ForeignKeyRole(name = "master", type = ApprovalMaster.class,

								constraints = @PropertyConstraints(required = true)),

						myRole = @MyRole(name = "line", cardinality = Cardinality.ONE_TO_MANY)),

				@GeneratedForeignKey(name = "LineUserLink",

						foreignKeyRole = @ForeignKeyRole(name = "user", type = WTUser.class,

								constraints = @PropertyConstraints(required = true)),

						myRole = @MyRole(name = "line", cardinality = Cardinality.ONE))

		})
public class ApprovalLine extends _ApprovalLine {
	static final long serialVersionUID = 1;

	public static ApprovalLine newApprovalLine() throws WTException {
		ApprovalLine instance = new ApprovalLine();
		instance.initialize();
		return instance;
	}
}
