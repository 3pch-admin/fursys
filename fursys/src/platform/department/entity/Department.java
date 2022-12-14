package platform.department.entity;

import com.ptc.windchill.annotations.metadata.Cardinality;
import com.ptc.windchill.annotations.metadata.ForeignKeyRole;
import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedForeignKey;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.MyRole;
import com.ptc.windchill.annotations.metadata.PropertyConstraints;

import wt.fc.WTObject;
import wt.ownership.Ownable;
import wt.util.WTException;

@GenAsPersistable(superClass = WTObject.class, interfaces = { Ownable.class },

		properties = {

				@GeneratedProperty(name = "name", type = String.class),

				@GeneratedProperty(name = "code", type = String.class),

				@GeneratedProperty(name = "sort", type = Integer.class),

				@GeneratedProperty(name = "uses", type = Boolean.class)

		},

		foreignKeys = { @GeneratedForeignKey(name = "ParentChildLink",

				foreignKeyRole = @ForeignKeyRole(name = "parent", type = Department.class,

						constraints = @PropertyConstraints(required = false)),

				myRole = @MyRole(name = "child", cardinality = Cardinality.ZERO_TO_ONE))

		})
public class Department extends _Department {
	static final long serialVersionUID = 1;

	public static Department newDepartment() throws WTException {
		Department instance = new Department();
		instance.initialize();
		return instance;
	}
}
