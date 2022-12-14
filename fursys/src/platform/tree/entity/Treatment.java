package platform.tree.entity;

import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.TableProperties;

import wt.fc.WTObject;
import wt.ownership.Ownable;
import wt.util.WTException;

@GenAsPersistable(superClass = WTObject.class, interfaces = { Ownable.class },

		properties = {

				@GeneratedProperty(name = "code", type = String.class),

				@GeneratedProperty(name = "rank", type = int.class),

				@GeneratedProperty(name = "treatment", type = String.class),
				
				@GeneratedProperty(name="Dtmg_treatment", type = String.class)

		},

		tableProperties = @TableProperties(tableName = "T_TREATMENT")

)
public class Treatment extends _Treatment {

	static final long serialVersionUID = 1;

	public static Treatment newTreatment() throws WTException {
		Treatment instance = new Treatment();
		instance.initialize();
		return instance;
	}
}
