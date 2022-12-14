package platform.color.entity;

import com.ptc.windchill.annotations.metadata.Cardinality;
import com.ptc.windchill.annotations.metadata.ForeignKeyRole;
import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedForeignKey;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.MyRole;
import com.ptc.windchill.annotations.metadata.PropertyConstraints;

import wt.fc.WTObject;
import wt.ownership.Ownable;
import wt.ownership.Ownership;
import wt.part.WTPart;
import wt.util.WTException;

@GenAsPersistable(superClass = WTObject.class, interfaces = { Ownable.class },

		properties = {

				@GeneratedProperty(name = "color", type = String.class),
				
				@GeneratedProperty(name = "colorType", type = String.class)

		},

		foreignKeys = {
				// 관계 되어지는 대상의 클래스 명을 뒤에 적는다
				@GeneratedForeignKey(name = "ColorWTPartLink",

						foreignKeyRole = @ForeignKeyRole(name = "part", type = WTPart.class,

								constraints = @PropertyConstraints(required = true)),

						myRole = @MyRole(name = "color", cardinality = Cardinality.ONE)),

		})
public class Color extends _Color {
	static final long serialVersionUID = 1;

	public static Color newColor() throws WTException {
		Color instance = new Color();
		instance.initialize();
		return instance;
	}
}
