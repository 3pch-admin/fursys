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

				@GeneratedProperty(name = "sendType", type = String.class),
				
				@GeneratedProperty(name = "resultMessage", type = String.class, constraints = @PropertyConstraints(upperLimit = 4000)),

				@GeneratedProperty(name = "sendResult", type = Boolean.class) })
public class ERPSendHistory extends _ERPSendHistory {
	static final long serialVersionUID = 1;

	public static ERPSendHistory newERPSendHistory() throws WTException {
		ERPSendHistory instance = new ERPSendHistory();
		instance.initialize();
		return instance;
	}

}
