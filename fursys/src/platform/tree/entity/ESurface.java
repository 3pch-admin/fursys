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

				@GeneratedProperty(name = "surface", type = String.class),
				
				@GeneratedProperty(name="DTMG_esurface", type = String.class)

		}

)
public class ESurface extends _ESurface {

	static final long serialVersionUID = 1;

	public static ESurface newESurface() throws WTException {
		ESurface instance = new ESurface();
		instance.initialize();
		return instance;
	}
}
