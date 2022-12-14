package platform.tree.entity;

import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;

import wt.fc.WTObject;
import wt.ownership.Ownable;
import wt.util.WTException;

@GenAsPersistable(superClass = WTObject.class, interfaces = { Ownable.class },

		properties = {

				@GeneratedProperty(name = "code", type = String.class),
				
				@GeneratedProperty(name = "cat_rank",  type = Integer.class),

				@GeneratedProperty(name = "rank", type = Integer.class),

				@GeneratedProperty(name = "material", type = String.class),
				
				@GeneratedProperty(name = "material_name", type = String.class),

				@GeneratedProperty(name = "cat_l", type = String.class),

				@GeneratedProperty(name = "erp_l", type = String.class),

				@GeneratedProperty(name = "cat_m", type = String.class),

				@GeneratedProperty(name = "erp_m", type = String.class),

				@GeneratedProperty(name = "cat_s", type = String.class),

				@GeneratedProperty(name = "erp_s", type = String.class),

				@GeneratedProperty(name = "materialType", type = String.class),
				//DTMG 재질정보

				@GeneratedProperty(name = "materialTypeCode", type = String.class),
				
		}

)
public class EMaterialInfo extends _EMaterialInfo {

	static final long serialVersionUID = 1;

	public static EMaterialInfo newEMaterialInfo() throws WTException {
		EMaterialInfo instance = new EMaterialInfo();
		instance.initialize();
		return instance;
	}
}
