package platform.echange.ecr.entity;

import java.sql.Timestamp;

import com.ptc.windchill.annotations.metadata.ColumnProperties;
import com.ptc.windchill.annotations.metadata.ColumnType;
import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.PropertyConstraints;

import wt.content.ContentHolder;
import wt.fc.WTObject;
import wt.iba.value.IBAHolder;
import wt.ownership.Ownable;
import wt.util.WTException;

@GenAsPersistable(superClass = WTObject.class, interfaces = { Ownable.class, ContentHolder.class, IBAHolder.class },

properties = {

		@GeneratedProperty(name = "name", type = String.class, javaDoc = "ECR 이름"),

		@GeneratedProperty(name = "number", type = String.class, javaDoc = "ECR 번호", columnProperties = @ColumnProperties(columnName = "ecrNumber")),

		@GeneratedProperty(name = "brand", type = String.class, javaDoc = "브랜드"),

		@GeneratedProperty(name = "company", type = String.class, javaDoc = "회사"),

		@GeneratedProperty(name = "state", type = String.class, javaDoc = "상태"),

		@GeneratedProperty(name = "reqType", type = String.class, javaDoc = "요청유형"),

		@GeneratedProperty(name = "limit", type = Timestamp.class, javaDoc = "처리기한"),

		@GeneratedProperty(name = "reason", type = String.class, javaDoc = "요청사유",

				constraints = @PropertyConstraints(upperLimit = 4000),

				columnProperties = @ColumnProperties(columnType = ColumnType.BLOB)

		),

		@GeneratedProperty(name = "description", type = String.class, javaDoc = "요청내용",

				constraints = @PropertyConstraints(upperLimit = 4000),

				columnProperties = @ColumnProperties(columnType = ColumnType.BLOB)),

})

public class ECR extends _ECR {

	static final long serialVersionUID = 1;

	public static ECR newECR() throws WTException {
		ECR instance = new ECR();
		instance.initialize();
		return instance;
	}
}
