package platform.raonk.entity;

import com.ptc.windchill.annotations.metadata.Cardinality;
import com.ptc.windchill.annotations.metadata.ColumnProperties;
import com.ptc.windchill.annotations.metadata.ColumnType;
import com.ptc.windchill.annotations.metadata.ForeignKeyRole;
import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedForeignKey;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.MyRole;
import com.ptc.windchill.annotations.metadata.PropertyConstraints;

import wt.fc.WTObject;
import wt.folder.Folder;
import wt.ownership.Ownable;
import wt.util.WTException;

@GenAsPersistable(superClass = WTObject.class, interfaces = { Ownable.class },

		properties = {

				@GeneratedProperty(name = "name", type = String.class, javaDoc = "라온케이 명", constraints = @PropertyConstraints(required = true)),

//				@GeneratedProperty(name = "description", type = String.class, javaDoc = "설명", constraints = @PropertyConstraints(upperLimit = 2000)),

				@GeneratedProperty(name = "enable", type = Boolean.class, javaDoc = "사용여부", initialValue = "true"),

//				@GeneratedProperty(name = "raonkType", type = String.class, javaDoc = "라온케이 타입"),

				@GeneratedProperty(name = "contents", type = String.class, javaDoc = "라온케이 템플릿", constraints = @PropertyConstraints(required = true), columnProperties = @ColumnProperties(columnType = ColumnType.BLOB))

		}

//		foreignKeys = {
//				// 관계 되어지는 대상의 클래스 명을 뒤에 적는다
//				@GeneratedForeignKey(name = "RaonkFolderLink",
//
//						foreignKeyRole = @ForeignKeyRole(name = "folder", type = Folder.class,
//
//								constraints = @PropertyConstraints(required = true)),
//
//						myRole = @MyRole(name = "raonk", cardinality = Cardinality.ONE)),
//
//		}

)
public class Raonk extends _Raonk {
	static final long serialVersionUID = 1;

	public static Raonk newRaonk() throws WTException {
		Raonk instance = new Raonk();
		instance.initialize();
		return instance;
	}
}
