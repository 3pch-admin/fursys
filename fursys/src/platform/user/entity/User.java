package platform.user.entity;

import com.ptc.windchill.annotations.metadata.Cardinality;
import com.ptc.windchill.annotations.metadata.ForeignKeyRole;
import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedForeignKey;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.MyRole;
import com.ptc.windchill.annotations.metadata.PropertyConstraints;
import com.ptc.windchill.annotations.metadata.TableProperties;

import platform.code.entity.BaseCode;
import platform.department.entity.Department;
import wt.fc.WTObject;
import wt.org.WTUser;
import wt.ownership.Ownable;
import wt.util.WTException;

@GenAsPersistable(superClass = WTObject.class, interfaces = { Ownable.class },

		properties = {

				@GeneratedProperty(name = "userId", type = String.class),

				@GeneratedProperty(name = "userName", type = String.class),

				@GeneratedProperty(name = "email", type = String.class),

				@GeneratedProperty(name = "duty", type = String.class),

				@GeneratedProperty(name = "uses", type = Boolean.class),

		},

		foreignKeys = {

				@GeneratedForeignKey(name = "UserWTUserLink",

						foreignKeyRole = @ForeignKeyRole(name = "wtUser", type = WTUser.class,

								constraints = @PropertyConstraints(required = true)),

						myRole = @MyRole(name = "user", cardinality = Cardinality.ONE)),

				@GeneratedForeignKey(name = "UserBrandLink",

						foreignKeyRole = @ForeignKeyRole(name = "brand", type = BaseCode.class,

								constraints = @PropertyConstraints(required = false)),

						myRole = @MyRole(name = "user", cardinality = Cardinality.ONE)),

				@GeneratedForeignKey(name = "UserDepartmentLink",

						foreignKeyRole = @ForeignKeyRole(name = "department", type = Department.class,

								constraints = @PropertyConstraints(required = false)),

						myRole = @MyRole(name = "user", cardinality = Cardinality.ONE))

		},

		tableProperties = @TableProperties(tableName = "P_USER")

)
public class User extends _User {
	static final long serialVersionUID = 1;

	public static User newUser() throws WTException {
		User instance = new User();
		instance.initialize();
		return instance;
	}
}
