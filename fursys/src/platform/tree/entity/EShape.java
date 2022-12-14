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

				@GeneratedProperty(name = "shape", type = String.class),

		})
public class EShape extends _EShape {

	static final long serialVersionUID = 1;

	public static EShape newEShape() throws WTException {
		EShape instance = new EShape();
		instance.initialize();
		return instance;
	}

}
