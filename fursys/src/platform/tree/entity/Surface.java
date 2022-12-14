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

				@GeneratedProperty(name = "material", type = String.class),

		}

)
public class Surface extends _Surface {

	static final long serialVersionUID = 1;

	public static Surface newSurface() throws WTException {
		Surface instance = new Surface();
		instance.initialize();
		return instance;
	}
}
