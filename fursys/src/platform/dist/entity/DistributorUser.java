package platform.dist.entity;

import com.ptc.windchill.annotations.metadata.Cardinality;
import com.ptc.windchill.annotations.metadata.ColumnProperties;
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

				@GeneratedProperty(name = "type", type = String.class, javaDoc = "사내, 사외", columnProperties = @ColumnProperties(columnName = "types")),

				@GeneratedProperty(name = "userId", type = String.class, javaDoc = "아이디"),
				
				@GeneratedProperty(name = "userName", type = String.class, javaDoc = "이름"),
				
				@GeneratedProperty(name = "email", type = String.class, javaDoc = "이메일"),
				
				@GeneratedProperty(name = "name", type = String.class, javaDoc = "업체명"),

				@GeneratedProperty(name = "description", type = String.class, javaDoc = "설명", constraints = @PropertyConstraints(upperLimit = 4000)),

				@GeneratedProperty(name = "number", type = String.class, javaDoc = "업체코드", columnProperties = @ColumnProperties(columnName = "dnumber")),

				@GeneratedProperty(name = "enable", type = Boolean.class, javaDoc = "사용여부")

		},
		
				foreignKeys = {

						@GeneratedForeignKey(name = "DistributorDistributorUserLink",

								foreignKeyRole = @ForeignKeyRole(name = "distributor", type = Distributor.class,

										constraints = @PropertyConstraints(required = false)),

								myRole = @MyRole(name = "dist", cardinality = Cardinality.ONE)),

				}
		
		)
public class DistributorUser extends _DistributorUser {
	static final long serialVersionUID = 1;

	public static DistributorUser newDistributorUser() throws WTException {
		DistributorUser instance = new DistributorUser();
		instance.initialize();
		return instance;
	}

}
