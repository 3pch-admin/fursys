package platform.mbom.entity;

import com.ptc.windchill.annotations.metadata.Cardinality;
import com.ptc.windchill.annotations.metadata.ColumnProperties;
import com.ptc.windchill.annotations.metadata.ForeignKeyRole;
import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedForeignKey;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.MyRole;
import com.ptc.windchill.annotations.metadata.PropertyConstraints;

import platform.ebom.entity.EBOM;
import platform.echange.ecn.entity.ECN;
import platform.echange.eco.entity.ECO;
import wt.fc.WTObject;
import wt.org.WTUser;
import wt.ownership.Ownable;
import wt.part.WTPart;
import wt.util.WTException;

@GenAsPersistable(superClass = WTObject.class, interfaces = { Ownable.class },

		properties = {

				@GeneratedProperty(name = "name", type = String.class, javaDoc = "MBOM 아이템 이름", constraints = @PropertyConstraints(required = true)),

				@GeneratedProperty(name = "number", type = String.class, javaDoc = "MBOM 아이템 번호", constraints = @PropertyConstraints(required = true),

						columnProperties = @ColumnProperties(columnName = "mbomnumber")),

				@GeneratedProperty(name = "ver", type = String.class, javaDoc = "MBOM 버전"),

				@GeneratedProperty(name = "applyColor", type = String.class, javaDoc = "적용 색상"),

				@GeneratedProperty(name = "color", type = String.class, javaDoc = "파생된 색상"),

				@GeneratedProperty(name = "derivedColor", type = String.class, javaDoc = "파생할 색상 리스트.."),

				@GeneratedProperty(name = "amount", type = Double.class, javaDoc = "MBOM 수량", initialValue = "0D"),

				@GeneratedProperty(name = "dcost", type = Double.class, javaDoc = "재료비", initialValue = "0D"),

				@GeneratedProperty(name = "pcost", type = Double.class, javaDoc = "가공비", initialValue = "0D"),

				@GeneratedProperty(name = "state", type = String.class, javaDoc = "MBOM 상태"),

				@GeneratedProperty(name = "bomType", type = String.class, javaDoc = "MBOM 타입(MBOM, MBOM_ITEM, MBOM_SET)"),

		},

		foreignKeys = {
				// 관계 되어지는 대상의 클래스 명을 뒤에 적는다
				@GeneratedForeignKey(name = "MBOMWTPartLink",

						foreignKeyRole = @ForeignKeyRole(name = "part", type = WTPart.class,

								constraints = @PropertyConstraints(required = true)),

						myRole = @MyRole(name = "mbom", cardinality = Cardinality.ONE)),

				@GeneratedForeignKey(name = "MBOMECOLink",

						foreignKeyRole = @ForeignKeyRole(name = "eco", type = ECO.class,

								constraints = @PropertyConstraints(required = true)),

						myRole = @MyRole(name = "mbom", cardinality = Cardinality.ONE)),

				@GeneratedForeignKey(name = "MBOMECNLink",

						foreignKeyRole = @ForeignKeyRole(name = "ecn", type = ECN.class,

								constraints = @PropertyConstraints(required = true)),

						myRole = @MyRole(name = "mbom", cardinality = Cardinality.ONE)),

				@GeneratedForeignKey(name = "MBOMEBOMLink",

						foreignKeyRole = @ForeignKeyRole(name = "ebom", type = EBOM.class,

								constraints = @PropertyConstraints(required = true)),

						myRole = @MyRole(name = "mbom", cardinality = Cardinality.ONE)),

				@GeneratedForeignKey(name = "MBOMManagerLink",

						foreignKeyRole = @ForeignKeyRole(name = "manager", type = WTUser.class,

								constraints = @PropertyConstraints(required = false)),

						myRole = @MyRole(name = "mbom", cardinality = Cardinality.ONE)),

		})
public class MBOM extends _MBOM {
	static final long serialVersionUID = 1;

	public static MBOM newMBOM() throws WTException {
		MBOM instance = new MBOM();
		instance.initialize();
		return instance;
	}

}
