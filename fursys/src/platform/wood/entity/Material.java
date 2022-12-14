package platform.wood.entity;

import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;

import platform.tree.entity.Combination;
import platform.tree.entity._Combination;
import wt.fc.WTObject;
import wt.ownership.Ownable;
import wt.util.WTException;

@GenAsPersistable(superClass = WTObject.class, interfaces = { Ownable.class },

		properties = {

				@GeneratedProperty(name = "code", type = String.class),

				@GeneratedProperty(name = "rank", type = String.class),

				@GeneratedProperty(name = "material", type = String.class),

				@GeneratedProperty(name = "cat_l", type = String.class),

				@GeneratedProperty(name = "erp_l", type = String.class),

				@GeneratedProperty(name = "cat_m", type = String.class),

				@GeneratedProperty(name = "erp_m", type = String.class),

				@GeneratedProperty(name = "cat_s", type = String.class),

				@GeneratedProperty(name = "erp_s", type = String.class),

				@GeneratedProperty(name = "materialType", type = String.class),

				@GeneratedProperty(name = "materialTypeCode", type = String.class),

		}

)
public class Material extends _Material {

	static final long serialVersionUID = 1;

	public static Material newCombination() throws WTException {
		Material instance = new Material();
		instance.initialize();
		return instance;
	}
}
