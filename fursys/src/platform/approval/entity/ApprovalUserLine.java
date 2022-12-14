package platform.approval.entity;

import java.util.ArrayList;

import com.ptc.windchill.annotations.metadata.ColumnProperties;
import com.ptc.windchill.annotations.metadata.ColumnType;
import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;

import wt.fc.WTObject;
import wt.ownership.Ownable;
import wt.util.WTException;

@GenAsPersistable(superClass = WTObject.class, interfaces = { Ownable.class },

		properties = {

				@GeneratedProperty(name = "name", type = String.class),

				@GeneratedProperty(name = "appList", type = ArrayList.class, columnProperties = @ColumnProperties(columnType = ColumnType.BLOB)),

				@GeneratedProperty(name = "viaList", type = ArrayList.class, columnProperties = @ColumnProperties(columnType = ColumnType.BLOB)),

				@GeneratedProperty(name = "refList", type = ArrayList.class, columnProperties = @ColumnProperties(columnType = ColumnType.BLOB))

		}

)

public class ApprovalUserLine extends _ApprovalUserLine {
	static final long serialVersionUID = 1;

	public static ApprovalUserLine newApprovalUserLine() throws WTException {
		ApprovalUserLine instance = new ApprovalUserLine();
		instance.initialize();
		return instance;
	}
}
