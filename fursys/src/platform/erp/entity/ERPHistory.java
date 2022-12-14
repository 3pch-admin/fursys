package platform.erp.entity;

import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.PropertyConstraints;

import wt.fc.WTObject;
import wt.ownership.Ownable;
import wt.util.WTException;

@GenAsPersistable(superClass = WTObject.class, interfaces = { Ownable.class },

		properties = {

				@GeneratedProperty(name = "name", type = String.class),

				@GeneratedProperty(name = "createType", type = String.class),

				@GeneratedProperty(name = "resultMessage", type = String.class, constraints = @PropertyConstraints(upperLimit = 4000)),

				@GeneratedProperty(name = "createResult", type = Boolean.class) })
public class ERPHistory extends _ERPHistory {
	static final long serialVersionUID = 1;

	public static ERPHistory newERPHistory() throws WTException {
		ERPHistory instance = new ERPHistory();
		instance.initialize();
		return instance;
	}

}