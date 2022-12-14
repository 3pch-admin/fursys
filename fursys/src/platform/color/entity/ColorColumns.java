package platform.color.entity;

import lombok.Getter;
import lombok.Setter;
import platform.util.CommonUtils;

@Getter
@Setter
public class ColorColumns {

	private int no;
	private String oid;
	private String name;
	private String number;

	public ColorColumns() {

	}

	public ColorColumns(Color colorSet) throws Exception {
		setOid(CommonUtils.oid(colorSet));
		setName(colorSet.getPart().getName());
		setNumber(colorSet.getPart().getNumber());
	}
}
