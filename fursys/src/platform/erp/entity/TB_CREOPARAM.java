package platform.erp.entity;

import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;

import wt.fc.WTObject;
import wt.util.WTException;

@GenAsPersistable(superClass = WTObject.class,

		properties = { 
				@GeneratedProperty(name = "CAD_KEY", type = String.class),
				@GeneratedProperty(name = "ITEM_NAME", type = String.class),
				@GeneratedProperty(name = "PART_NO", type = String.class),
				@GeneratedProperty(name = "PART_NAME", type = String.class),
				@GeneratedProperty(name = "IM_ASM_HW", type = String.class),
				@GeneratedProperty(name = "IM_ASSY_LINE", type = String.class),
				@GeneratedProperty(name = "IM_ASSY_TIME", type = String.class),
				@GeneratedProperty(name = "IM_BORING_BOTH", type = String.class),
				@GeneratedProperty(name = "IM_BORING_DIA_A", type = String.class),
				@GeneratedProperty(name = "IM_BORING_DIA_B", type = String.class),
				@GeneratedProperty(name = "IM_BORING_DIA_C", type = String.class),
				@GeneratedProperty(name = "IM_BORING_DIA_D", type = String.class),
				@GeneratedProperty(name = "IM_BORING_NOTHRU", type = String.class),
				@GeneratedProperty(name = "IM_BORING_SIDE_X", type = String.class),
				@GeneratedProperty(name = "IM_BORING_SIDE_Y", type = String.class),
				@GeneratedProperty(name = "IM_BORING_THRU", type = String.class),
				@GeneratedProperty(name = "IM_CASING_HOUSING", type = String.class),
				@GeneratedProperty(name = "IM_CASING_SCREW_A", type = String.class),
				@GeneratedProperty(name = "IM_CASING_SCREW_B", type = String.class),
				@GeneratedProperty(name = "IM_CASING_SCREW_C", type = String.class),
				@GeneratedProperty(name = "IM_CASING_SCREW_D", type = String.class),
				@GeneratedProperty(name = "IM_COAT_AREA", type = String.class),
				@GeneratedProperty(name = "IM_COAT_STRIP_LENG", type = String.class),
				@GeneratedProperty(name = "IM_DEPTH", type = String.class),
				@GeneratedProperty(name = "IM_EDGE_1ST_OUTIN", type = String.class),
				@GeneratedProperty(name = "IM_EDGE_2ND_OUTIN", type = String.class),
				@GeneratedProperty(name = "IM_EDGE_COAT", type = String.class),
				@GeneratedProperty(name = "IM_EDGE_COAT_AREA", type = String.class),
				@GeneratedProperty(name = "IM_EDGE_COAT_TYPE", type = String.class),
				@GeneratedProperty(name = "IM_EDGE_CURVED_LENG", type = String.class),
				@GeneratedProperty(name = "IM_EDGE_CURVED_NUM", type = String.class),
				@GeneratedProperty(name = "IM_EDGE_DEPTH", type = String.class),
				@GeneratedProperty(name = "IM_EDGE_HEIGHT", type = String.class),
				@GeneratedProperty(name = "IM_EDGE_SPEC_A", type = String.class),
				@GeneratedProperty(name = "IM_EDGE_SPEC_B", type = String.class),
				@GeneratedProperty(name = "IM_EDGE_SPEC_C", type = String.class),
				@GeneratedProperty(name = "IM_EDGE_SPEC_D", type = String.class),
				@GeneratedProperty(name = "IM_EDGE_TOTAL_LENG", type = String.class),
				@GeneratedProperty(name = "IM_EDGE_TRIM_A", type = String.class),
				@GeneratedProperty(name = "IM_EDGE_TRIM_B", type = String.class),
				@GeneratedProperty(name = "IM_EDGE_TRIM_C", type = String.class),
				@GeneratedProperty(name = "IM_EDGE_TRIM_D", type = String.class),
				@GeneratedProperty(name = "IM_EDGE_WIDTH", type = String.class),
				@GeneratedProperty(name = "IM_FILM_LENG", type = String.class),
				@GeneratedProperty(name = "IM_FORM_3D", type = String.class),
				@GeneratedProperty(name = "IM_FORM_COMP", type = String.class),
				@GeneratedProperty(name = "IM_FORM_CUT", type = String.class),
				@GeneratedProperty(name = "IM_FORM_JUNCTION", type = String.class),
				@GeneratedProperty(name = "IM_FRAME_COUNT", type = String.class),
				@GeneratedProperty(name = "IM_HEIGHT", type = String.class),
				@GeneratedProperty(name = "IM_JUNCTION_TYPE", type = String.class),
				@GeneratedProperty(name = "IM_PACK_PART", type = String.class),
				@GeneratedProperty(name = "IM_POCKET_INSIDE_LENG", type = String.class),
				@GeneratedProperty(name = "IM_POCKET_OUTSIDE_A", type = String.class),
				@GeneratedProperty(name = "IM_POCKET_OUTSIDE_B", type = String.class),
				@GeneratedProperty(name = "IM_POCKET_OUTSIDE_C", type = String.class),
				@GeneratedProperty(name = "IM_POCKET_OUTSIDE_D", type = String.class),
				@GeneratedProperty(name = "IM_REPAIR_COAT", type = String.class),
				@GeneratedProperty(name = "IM_ROUTER_INSIDE", type = String.class),
				@GeneratedProperty(name = "IM_ROUTER_LENG_1", type = String.class),
				@GeneratedProperty(name = "IM_ROUTER_LENG_2", type = String.class),
				@GeneratedProperty(name = "IM_ROUTER_LENG_3", type = String.class),
				@GeneratedProperty(name = "IM_ROUTER_OUTSIDE_LENG", type = String.class),
				@GeneratedProperty(name = "IM_ROUTER_SPEC_1", type = String.class),
				@GeneratedProperty(name = "IM_ROUTER_SPEC_2", type = String.class),
				@GeneratedProperty(name = "IM_ROUTER_SPEC_3", type = String.class),
				@GeneratedProperty(name = "IM_SAWCUT_LENG_AC", type = String.class),
				@GeneratedProperty(name = "IM_SAWCUT_LENG_BD", type = String.class),
				@GeneratedProperty(name = "IM_SLANT_PROCESS", type = String.class),
				@GeneratedProperty(name = "IM_SPER_HW", type = String.class),
				@GeneratedProperty(name = "IM_SURF_FINISH_FIRST", type = String.class),
				@GeneratedProperty(name = "IM_TENONER_LENG", type = String.class),
				@GeneratedProperty(name = "IM_TURN_KEY", type = String.class),
				@GeneratedProperty(name = "IM_URETHANE_1ST", type = String.class),
				@GeneratedProperty(name = "IM_URETHANE_2ND", type = String.class),
				@GeneratedProperty(name = "IM_URETHANE_VOL", type = String.class),
				@GeneratedProperty(name = "IM_UV_COAT_1ST", type = String.class),
				@GeneratedProperty(name = "IM_UV_COAT_2ND", type = String.class),
				@GeneratedProperty(name = "IM_WASH_FACE", type = String.class),
				@GeneratedProperty(name = "IM_WIDTH", type = String.class),
				@GeneratedProperty(name = "IM_WOOD_FRAME", type = String.class), })
public class TB_CREOPARAM extends _TB_CREOPARAM {
	static final long serialVersionUID = 1;

	public static TB_CREOPARAM newTB_CREOPARAM() throws WTException {
		TB_CREOPARAM instance = new TB_CREOPARAM();
		instance.initialize();
		return instance;
	}

}
