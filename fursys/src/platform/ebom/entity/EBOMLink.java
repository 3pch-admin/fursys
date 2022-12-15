package platform.ebom.entity;

import com.ptc.windchill.annotations.metadata.Cardinality;
import com.ptc.windchill.annotations.metadata.ForeignKeyRole;
import com.ptc.windchill.annotations.metadata.GenAsBinaryLink;
import com.ptc.windchill.annotations.metadata.GeneratedForeignKey;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.GeneratedRole;
import com.ptc.windchill.annotations.metadata.MyRole;
import com.ptc.windchill.annotations.metadata.PropertyConstraints;

import wt.fc.ObjectToObjectLink;
import wt.part.WTPartUsageLink;
import wt.util.WTException;

@GenAsBinaryLink(superClass = ObjectToObjectLink.class,

		roleA = @GeneratedRole(name = "parent", type = EBOM.class),

		roleB = @GeneratedRole(name = "child", type = EBOM.class),

		properties = {

				@GeneratedProperty(name = "amount", type = Double.class)

		},

		foreignKeys = {

				@GeneratedForeignKey(name = "EBOMLinkWTPartUsageLink",

						foreignKeyRole = @ForeignKeyRole(name = "usageLink", type = WTPartUsageLink.class,

								constraints = @PropertyConstraints(required = false)),

						myRole = @MyRole(name = "ebomLink", cardinality = Cardinality.ONE)), }

)
public class EBOMLink extends _EBOMLink {
	static final long serialVersionUID = 1;

	public static EBOMLink newEBOMLink(EBOM parent, EBOM child) throws WTException {
		EBOMLink instance = new EBOMLink();
		instance.initialize(parent, child);
		return instance;
	}
}
