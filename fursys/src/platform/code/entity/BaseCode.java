package platform.code.entity;

import com.ptc.windchill.annotations.metadata.Cardinality;
import com.ptc.windchill.annotations.metadata.ForeignKeyRole;
import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedForeignKey;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.MyRole;
import com.ptc.windchill.annotations.metadata.PropertyConstraints;

import wt.fc.WTObject;
import wt.util.WTException;

@GenAsPersistable(superClass = WTObject.class,

		properties = {

				@GeneratedProperty(name = "name", type = String.class, javaDoc = "코드 명", constraints = @PropertyConstraints(required = true)),

				@GeneratedProperty(name = "description", type = String.class, javaDoc = "설명", constraints = @PropertyConstraints(upperLimit = 2000)),

				@GeneratedProperty(name = "code", type = String.class, javaDoc = "코드", constraints = @PropertyConstraints(required = true)),

				@GeneratedProperty(name = "enable", type = Boolean.class, javaDoc = "사용여부", initialValue = "true"),

				@GeneratedProperty(name = "sort", type = Integer.class, javaDoc = "정렬"),

				@GeneratedProperty(name = "codeType", type = BaseCodeType.class, javaDoc = "코드타입")

		},

		foreignKeys = { @GeneratedForeignKey(name = "ParentChildLink",

				foreignKeyRole = @ForeignKeyRole(name = "parent", type = BaseCode.class,

						constraints = @PropertyConstraints(required = false)),

				myRole = @MyRole(name = "child", cardinality = Cardinality.ZERO_TO_ONE))

		}

)
public class BaseCode extends _BaseCode {
	static final long serialVersionUID = 1;

	public static BaseCode newBaseCode() throws WTException {
		BaseCode instance = new BaseCode();
		instance.initialize();
		return instance;
	}

}
