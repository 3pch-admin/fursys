package platform.dist.entity;

import com.ptc.windchill.annotations.metadata.ColumnProperties;
import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.PropertyConstraints;

import wt.content.ContentHolder;
import wt.fc.Item;
import wt.ownership.Ownable;
import wt.util.WTException;

@GenAsPersistable(superClass = Item.class, interfaces = { Ownable.class, ContentHolder.class },

		properties = {

				@GeneratedProperty(name = "name", type = String.class),

				@GeneratedProperty(name = "number", type = String.class, columnProperties = @ColumnProperties(columnName = "dNumber")),

				@GeneratedProperty(name = "description", type = String.class, constraints = @PropertyConstraints(upperLimit = 4000)),

				@GeneratedProperty(name = "state", type = String.class),

				@GeneratedProperty(name = "duration", type = Integer.class),
				
				@GeneratedProperty(name = "material_type", type = String.class)

		}

)
public class Dist extends _Dist {
	static final long serialVersionUID = 1;

	public static Dist newDist() throws WTException {
		Dist instance = new Dist();
		instance.initialize();
		return instance;
	}
}
