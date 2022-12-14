package platform.util.entity;

import java.io.File;

import com.ptc.windchill.annotations.metadata.ColumnProperties;
import com.ptc.windchill.annotations.metadata.ColumnType;
import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.PropertyConstraints;

import wt.fc.WTObject;
import wt.ownership.Ownable;
import wt.util.WTException;

@GenAsPersistable(superClass = WTObject.class, interfaces = { Ownable.class },

		properties = {

				@GeneratedProperty(name = "ecoNumber", type = String.class),

				@GeneratedProperty(name = "version", type = String.class),

				@GeneratedProperty(name = "color", type = String.class),

				@GeneratedProperty(name = "erpCode", type = String.class),

				@GeneratedProperty(name = "dtmg", type = String.class, constraints = @PropertyConstraints(upperLimit = 4000))

		}

)
public class DTMG extends _DTMG {
	static final long serialVersionUID = 1;

	public static DTMG newDTMG() throws WTException {
		DTMG instance = new DTMG();
		instance.initialize();
		return instance;
	}
}
