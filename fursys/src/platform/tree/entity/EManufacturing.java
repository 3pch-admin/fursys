package platform.tree.entity;

import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;

import wt.fc.WTObject;
import wt.ownership.Ownable;
import wt.util.WTException;

@GenAsPersistable(superClass = WTObject.class, interfaces = { Ownable.class },

properties = {

		@GeneratedProperty(name = "code", type = String.class),

		@GeneratedProperty(name = "rank", type = Integer.class),

		@GeneratedProperty(name = "manufacturing", type = String.class),

}

)
public class EManufacturing extends _EManufacturing {

static final long serialVersionUID = 1;

public static EManufacturing newEManufacturing() throws WTException {
	EManufacturing instance = new EManufacturing();
instance.initialize();
return instance;
}
}