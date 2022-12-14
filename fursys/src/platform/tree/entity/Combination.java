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
				
				@GeneratedProperty(name = "material", type = String.class),
				
				@GeneratedProperty(name = "surface_code", type = String.class),

				@GeneratedProperty(name = "rank", type = Integer.class),

				@GeneratedProperty(name = "combination", type = String.class),

				@GeneratedProperty(name = "cat_l", type = String.class),

				@GeneratedProperty(name = "erp_l", type = String.class),

				@GeneratedProperty(name = "cat_m", type = String.class),

				@GeneratedProperty(name = "erp_m", type = String.class),

				@GeneratedProperty(name = "cat_s", type = String.class),

				@GeneratedProperty(name = "erp_s", type = String.class),

				@GeneratedProperty(name = "materialType", type = String.class),
				//DTMG 재질 정보

				@GeneratedProperty(name = "materialTypeCode", type = String.class),

		}

)
public class Combination extends _Combination {

	static final long serialVersionUID = 1;

	public static Combination newCombination() throws WTException {
		Combination instance = new Combination();
		instance.initialize();
		return instance;
	}
}
