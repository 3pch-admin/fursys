package platform.ebom.entity;

import com.ptc.windchill.annotations.metadata.Cardinality;
import com.ptc.windchill.annotations.metadata.ForeignKeyRole;
import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedForeignKey;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.MyRole;
import com.ptc.windchill.annotations.metadata.PropertyConstraints;

import wt.fc.WTObject;
import wt.ownership.Ownable;
import wt.part.WTPartMaster;
import wt.util.WTException;

@GenAsPersistable(superClass = WTObject.class, interfaces = { Ownable.class },

		properties = {

				@GeneratedProperty(name = "state", type = String.class),

				@GeneratedProperty(name = "bomType", type = String.class),

				@GeneratedProperty(name = "amount", type = Double.class)

		},

		foreignKeys = {

				@GeneratedForeignKey(name = "EBOMWTPartMasterLink",

						foreignKeyRole = @ForeignKeyRole(name = "wtpartMaster", type = WTPartMaster.class,

								constraints = @PropertyConstraints(required = true)),

						myRole = @MyRole(name = "ebom", cardinality = Cardinality.ONE)), }

)
public class EBOM extends _EBOM {
	static final long serialVersionUID = 1;

	public static EBOM newEBOM() throws WTException {
		EBOM instance = new EBOM();
		instance.initialize();
		return instance;
	}

}
