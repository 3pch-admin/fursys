package platform.echange.ecn.entity;

import java.sql.Timestamp;

import com.ptc.windchill.annotations.metadata.Cardinality;
import com.ptc.windchill.annotations.metadata.ColumnProperties;
import com.ptc.windchill.annotations.metadata.ForeignKeyRole;
import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedForeignKey;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.MyRole;
import com.ptc.windchill.annotations.metadata.PropertyConstraints;

import platform.echange.eco.entity.ECO;
import wt.content.ContentHolder;
import wt.fc.WTObject;
import wt.ownership.Ownable;
import wt.util.WTException;

@GenAsPersistable(superClass = WTObject.class, interfaces = { ContentHolder.class, Ownable.class },

		properties = {

				@GeneratedProperty(name = "name", type = String.class, javaDoc = "ECN 이름"),

				@GeneratedProperty(name = "number", type = String.class, javaDoc = "ECN 번호", columnProperties = @ColumnProperties(columnName = "ecnNumber")),

				@GeneratedProperty(name = "brand", type = String.class, javaDoc = "브랜드"),

				@GeneratedProperty(name = "company", type = String.class, javaDoc = "회사"),

				@GeneratedProperty(name = "state", type = String.class, javaDoc = "상태"),

				@GeneratedProperty(name = "plant", type = String.class, javaDoc = "사업장"),

				@GeneratedProperty(name = "ecnApplyTime", type = String.class, javaDoc = "적용시점"),

				@GeneratedProperty(name = "notiType", type = String.class, javaDoc = "통보유형"),

				@GeneratedProperty(name = "applicationDate", type = Timestamp.class, javaDoc = "실제 적용일"),

				@GeneratedProperty(name = "modifier", type = String.class, javaDoc = "수정자"),

				@GeneratedProperty(name = "modifiedDate", type = Timestamp.class, javaDoc = "수정일"),

		},

		foreignKeys = {

				@GeneratedForeignKey(name = "EcnEcoLink",

						foreignKeyRole = @ForeignKeyRole(name = "eco", type = ECO.class,

								constraints = @PropertyConstraints(required = false)),

						myRole = @MyRole(name = "ecn", cardinality = Cardinality.ONE))

		}

)

public class ECN extends _ECN {

	static final long serialVersionUID = 1;

	public static ECN newECN() throws WTException {
		ECN instance = new ECN();
		instance.initialize();
		return instance;
	}
}
