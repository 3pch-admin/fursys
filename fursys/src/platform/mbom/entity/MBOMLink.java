package platform.mbom.entity;

import com.ptc.windchill.annotations.metadata.GenAsBinaryLink;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.GeneratedRole;

import wt.fc.ObjectToObjectLink;
import wt.ownership.Ownable;
import wt.util.WTException;

@GenAsBinaryLink(superClass = ObjectToObjectLink.class, interfaces = { Ownable.class },

		roleA = @GeneratedRole(name = "parent", type = MBOM.class, javaDoc = "MBOM 부모"),

		roleB = @GeneratedRole(name = "child", type = MBOM.class, javaDoc = "MBOM 자식"),

		properties = {

				@GeneratedProperty(name = "sort", type = Integer.class, javaDoc = "MBOMLink 정렬", initialValue = "0"),

				@GeneratedProperty(name = "depth", type = Integer.class, javaDoc = "MBOMLink 레벨", initialValue = "0"),

//				@GeneratedProperty(name = "isNon", type = Boolean.class, javaDoc = "NON BOM 여부"),

		}

)
public class MBOMLink extends _MBOMLink {
	static final long serialVersionUID = 1;

	public static MBOMLink newMBOMLink(MBOM parent, MBOM child) throws WTException {
		MBOMLink instance = new MBOMLink();
		instance.initialize(parent, child);
		return instance;
	}
}
