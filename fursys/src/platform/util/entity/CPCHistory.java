package platform.util.entity;

import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.PropertyConstraints;

import wt.fc.WTObject;
import wt.ownership.Ownable;
import wt.util.WTException;

@GenAsPersistable(superClass = WTObject.class, interfaces = { Ownable.class },

		properties = {

				@GeneratedProperty(name = "targetOid", type = String.class),

				@GeneratedProperty(name = "moduleName", type = String.class),

				@GeneratedProperty(name = "description", type = String.class),

				@GeneratedProperty(name = "sendQuery", type = String.class, constraints = @PropertyConstraints(upperLimit = 4000))

		}

)
public class CPCHistory extends _CPCHistory {
	static final long serialVersionUID = 1;

	public static CPCHistory newCPCHistory() throws WTException {
		CPCHistory instance = new CPCHistory();
		instance.initialize();
		return instance;
	}
}
