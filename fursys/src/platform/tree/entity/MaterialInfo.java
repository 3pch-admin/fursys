package platform.tree.entity;

import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;

import wt.fc.WTObject;
import wt.ownership.Ownable;
import wt.util.WTException;

@GenAsPersistable(superClass = WTObject.class, interfaces = { Ownable.class },

		properties = {

				@GeneratedProperty(name = "code", type = String.class),
				
				@GeneratedProperty(name = "basic_code", type = String.class),
				
				@GeneratedProperty(name = "width", type = Integer.class),
				
				@GeneratedProperty(name = "material_code", type = String.class),
				//재질코드

				@GeneratedProperty(name = "rank", type = Integer.class),

				@GeneratedProperty(name = "material", type = String.class),

				@GeneratedProperty(name = "outside", type = String.class),

		}

)
public class MaterialInfo extends _MaterialInfo {

	static final long serialVersionUID = 1;

	public static MaterialInfo newMaterialInfo() throws WTException {
		MaterialInfo instance = new MaterialInfo();
		instance.initialize();
		return instance;
	}
}
