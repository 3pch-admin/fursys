package platform.echange.eco.entity;

import java.sql.Timestamp;

import com.ptc.windchill.annotations.metadata.Cardinality;
import com.ptc.windchill.annotations.metadata.ColumnProperties;
import com.ptc.windchill.annotations.metadata.ColumnType;
import com.ptc.windchill.annotations.metadata.ForeignKeyRole;
import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedForeignKey;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.MyRole;
import com.ptc.windchill.annotations.metadata.PropertyConstraints;

import wt.content.ContentHolder;
import wt.fc.WTObject;
import wt.ownership.Ownable;
import wt.util.WTException;

@GenAsPersistable(superClass = WTObject.class, interfaces = { Ownable.class, ContentHolder.class },

		properties = {

				@GeneratedProperty(name = "name", type = String.class, javaDoc = "ECO 이름"),

				@GeneratedProperty(name = "number", type = String.class, javaDoc = "ECO 번호", columnProperties = @ColumnProperties(columnName = "ecoNumber")),

				@GeneratedProperty(name = "ecoType", type = String.class, javaDoc = "ECO 유형"),

				@GeneratedProperty(name = "brand", type = String.class, javaDoc = "브랜드"),

				@GeneratedProperty(name = "company", type = String.class, javaDoc = "회사"),

				@GeneratedProperty(name = "lot", type = String.class, javaDoc = "제품 LOT"),

				@GeneratedProperty(name = "state", type = String.class, javaDoc = "상태"),

				@GeneratedProperty(name = "devType", type = String.class, javaDoc = "개발구분"),

				@GeneratedProperty(name = "notiType", type = String.class, javaDoc = "통보유형"),

				@GeneratedProperty(name = "applyTime", type = String.class, javaDoc = "적용시점"),

				@GeneratedProperty(name = "reason", type = String.class, javaDoc = "요청사유",

						constraints = @PropertyConstraints(upperLimit = 4000),

						columnProperties = @ColumnProperties(columnType = ColumnType.BLOB)

				),

				@GeneratedProperty(name = "description", type = String.class, javaDoc = "요청내용",

						constraints = @PropertyConstraints(upperLimit = 4000),

						columnProperties = @ColumnProperties(columnType = ColumnType.BLOB)

				),

				@GeneratedProperty(name = "expectationTime", type = Timestamp.class, javaDoc = "예상적용일"), },

		foreignKeys = { @GeneratedForeignKey(name = "EcoEcrLink",

				foreignKeyRole = @ForeignKeyRole(name = "ecr", type = ECO.class,

						constraints = @PropertyConstraints(required = false)),

				myRole = @MyRole(name = "eco", cardinality = Cardinality.ZERO_TO_ONE))

		})

public class ECO extends _ECO {

	static final long serialVersionUID = 1;

	public static ECO newECO() throws WTException {
		ECO instance = new ECO();
		instance.initialize();
		return instance;
	}
}