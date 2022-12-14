package platform.message.entity;

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

				@GeneratedProperty(name = "description", type = String.class, constraints = @PropertyConstraints(upperLimit = 2000)),

				@GeneratedProperty(name = "hit", type = Boolean.class)

		},

		foreignKeys = { @GeneratedForeignKey(name = "MessageUserLink",

				foreignKeyRole = @ForeignKeyRole(name = "user", type = WTUser.class,

						constraints = @PropertyConstraints(required = false)),

				myRole = @MyRole(name = "message", cardinality = Cardinality.ONE))

		})

public class Message extends _Message {
	static final long serialVersionUID = 1;

	public static Message newMessage() throws WTException {
		Message instance = new Message();
		instance.initialize();
		return instance;
	}
}
