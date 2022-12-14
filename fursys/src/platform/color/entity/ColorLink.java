package platform.color.entity;

import com.ptc.windchill.annotations.metadata.GenAsBinaryLink;
import com.ptc.windchill.annotations.metadata.GeneratedRole;

import wt.fc.ObjectToObjectLink;
import wt.ownership.Ownable;
import wt.util.WTException;

@GenAsBinaryLink(superClass = ObjectToObjectLink.class, interfaces = { Ownable.class },

		roleA = @GeneratedRole(name = "parent", type = Color.class, javaDoc = "Color 부모"),

		roleB = @GeneratedRole(name = "child", type = Color.class, javaDoc = "Color 자식")

)
public class ColorLink extends _ColorLink {
	static final long serialVersionUID = 1;

	public static ColorLink newColorLink(Color parent, Color child) throws WTException {
		ColorLink instance = new ColorLink();
		instance.initialize(parent, child);
		return instance;
	}
}
