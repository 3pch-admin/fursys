package platform.tree.entity;

import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;

import wt.fc.WTObject;
import wt.ownership.Ownable;
import wt.util.WTException;

@GenAsPersistable(superClass = WTObject.class, interfaces = { Ownable.class },

		properties = {

				@GeneratedProperty(name = "commonCode", type = String.class, javaDoc = "표준유형코드"),

				@GeneratedProperty(name = "sort", type = String.class, javaDoc = "항번"),

				@GeneratedProperty(name = "commonName", type = String.class, javaDoc = "표준유형명"),

				@GeneratedProperty(name = "materialInfo", type = String.class, javaDoc = "재질정보"),

				@GeneratedProperty(name = "processInfo", type = String.class, javaDoc = "가공정보"),

				@GeneratedProperty(name = "treatment", type = String.class, javaDoc = "표면처리"),

				@GeneratedProperty(name = "bomLevel", type = String.class, javaDoc = "BOM레벨"),

				@GeneratedProperty(name = "requiredProcess", type = String.class, javaDoc = "소요공정"),

				@GeneratedProperty(name = "materialCode", type = String.class, javaDoc = "자재코드"),

				@GeneratedProperty(name = "color", type = String.class, javaDoc = "색상"),

				@GeneratedProperty(name = "materialName", type = String.class, javaDoc = "자재명"),
				
				@GeneratedProperty(name = "bom_unit",  type = String.class, javaDoc = "단위"),

				@GeneratedProperty(name = "x1", type = String.class, javaDoc = "변수(X1)"),

				@GeneratedProperty(name = "x2", type = String.class, javaDoc = " 변수(X2)"),

				@GeneratedProperty(name = "processMargin", type = String.class, javaDoc = "가공마진(M)"),

				@GeneratedProperty(name = "processValue", type = String.class, javaDoc = "가공값(A)"),

				@GeneratedProperty(name = "loss", type = String.class, javaDoc = "LOSS 율(B)"),

				@GeneratedProperty(name = "unitProcessAmount", type = String.class, javaDoc = " 단위가공량(C) "),

				@GeneratedProperty(name = "unitConversion", type = String.class, javaDoc = "단위환산(D)"),

				@GeneratedProperty(name = "conversionFactor", type = String.class, javaDoc = "환산계수(E)")

		}

)

public class BomQuantity extends _BomQuantity {
	static final long serialVersionUID = 1;

	public static BomQuantity newBomQuantity() throws WTException {
		BomQuantity instance = new BomQuantity();
		instance.initialize();
		return instance;
	}
}
