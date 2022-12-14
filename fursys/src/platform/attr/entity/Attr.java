package platform.attr.entity;

import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;

import wt.fc.WTObject;
import wt.ownership.Ownable;
import wt.util.WTException;

@GenAsPersistable(superClass = WTObject.class, interfaces = { Ownable.class },

		properties = {

				@GeneratedProperty(name = "cad_key", type = String.class),

				@GeneratedProperty(name = "im_edge_width", type = String.class),

				@GeneratedProperty(name = "im_edge_depth", type = String.class),

				@GeneratedProperty(name = "im_width", type = String.class),

				@GeneratedProperty(name = "im_depth", type = String.class),

				@GeneratedProperty(name = "im_height", type = String.class),

				@GeneratedProperty(name = "im_edge_total_leng", type = String.class),

				@GeneratedProperty(name = "im_urethane_vol", type = String.class),

				@GeneratedProperty(name = "im_coat_area", type = String.class),

				@GeneratedProperty(name = "im_edge_coat_area", type = String.class),

				@GeneratedProperty(name = "im_sawcut_leng_ac", type = String.class),

				@GeneratedProperty(name = "im_sawcut_leng_bd", type = String.class),

				@GeneratedProperty(name = "im_edge_curved_leng", type = String.class),

				@GeneratedProperty(name = "im_router_outside_leng", type = String.class),

				@GeneratedProperty(name = "im_router_inside", type = String.class),

				@GeneratedProperty(name = "im_coat_strip_leng", type = String.class),

				@GeneratedProperty(name = "im_pocket_inside_leng", type = String.class),

				@GeneratedProperty(name = "im_edge_spec_a", type = String.class),

				@GeneratedProperty(name = "im_edge_spec_b", type = String.class),

				@GeneratedProperty(name = "im_edge_spec_c", type = String.class),

				@GeneratedProperty(name = "im_edge_spec_d", type = String.class),

				@GeneratedProperty(name = "im_router_spec_1", type = String.class),

				@GeneratedProperty(name = "im_router_spec_2", type = String.class),

				@GeneratedProperty(name = "im_router_spec_3", type = String.class),

				@GeneratedProperty(name = "im_router_leng_1", type = String.class),

				@GeneratedProperty(name = "im_router_leng_2", type = String.class),

				@GeneratedProperty(name = "im_router_leng_3", type = String.class),

				@GeneratedProperty(name = "im_film_leng", type = String.class),

				@GeneratedProperty(name = "im_boring_both", type = String.class),

				@GeneratedProperty(name = "im_slant_process", type = String.class),

				@GeneratedProperty(name = "im_edge_coat", type = String.class),

				@GeneratedProperty(name = "im_edge_curved_num", type = String.class),

				@GeneratedProperty(name = "im_edge_height", type = String.class),

				@GeneratedProperty(name = "im_boring_nothru", type = String.class),

				@GeneratedProperty(name = "im_boring_thru", type = String.class),

				@GeneratedProperty(name = "im_casing_screw_a", type = String.class),

				@GeneratedProperty(name = "im_casing_screw_b", type = String.class),

				@GeneratedProperty(name = "im_casing_screw_c", type = String.class),

				@GeneratedProperty(name = "im_casing_screw_d", type = String.class),

				@GeneratedProperty(name = "im_casing_housing", type = String.class),

				@GeneratedProperty(name = "im_boring_dia_a", type = String.class),

				@GeneratedProperty(name = "im_boring_dia_b", type = String.class),

				@GeneratedProperty(name = "im_boring_dia_c", type = String.class),

				@GeneratedProperty(name = "im_boring_dia_d", type = String.class),

				@GeneratedProperty(name = "im_edge_trim_a", type = String.class),

				@GeneratedProperty(name = "im_edge_trim_b", type = String.class),

				@GeneratedProperty(name = "im_edge_trim_c", type = String.class),

				@GeneratedProperty(name = "im_edge_trim_d", type = String.class),

				@GeneratedProperty(name = "im_boring_side_x", type = String.class),

				@GeneratedProperty(name = "im_boring_side_y", type = String.class),

				@GeneratedProperty(name = "im_pocket_outside_a", type = String.class),

				@GeneratedProperty(name = "im_pocket_outside_b", type = String.class),

				@GeneratedProperty(name = "im_pocket_outside_c", type = String.class),

				@GeneratedProperty(name = "im_pocket_outside_d", type = String.class),

				@GeneratedProperty(name = "im_junction_type", type = String.class),

				@GeneratedProperty(name = "im_uv_coat_1st", type = String.class),

				@GeneratedProperty(name = "im_uv_coat_2nd", type = String.class),

				@GeneratedProperty(name = "im_edge_1st_outin", type = String.class),

				@GeneratedProperty(name = "im_edge_2nd_outin", type = String.class),

				@GeneratedProperty(name = "im_urethane_1st", type = String.class),

				@GeneratedProperty(name = "im_urethane_2nd", type = String.class),

				@GeneratedProperty(name = "im_repair_coat", type = String.class),

				@GeneratedProperty(name = "im_wash_face", type = String.class),

				@GeneratedProperty(name = "im_form_cut", type = String.class),

				@GeneratedProperty(name = "im_form_comp", type = String.class),

				@GeneratedProperty(name = "im_form_junction", type = String.class),

				@GeneratedProperty(name = "im_form_3d", type = String.class),

				@GeneratedProperty(name = "im_tenoner_leng", type = String.class),

				@GeneratedProperty(name = "im_turn_key", type = String.class),

				@GeneratedProperty(name = "im_wood_frame", type = String.class),

				@GeneratedProperty(name = "im_edge_coat_type", type = String.class),

				@GeneratedProperty(name = "im_surf_finish_first", type = String.class),

				@GeneratedProperty(name = "im_assy_line", type = String.class),

				@GeneratedProperty(name = "im_assy_time", type = String.class),

				@GeneratedProperty(name = "im_frame_count", type = String.class),

				@GeneratedProperty(name = "im_asm_hw", type = String.class),

				@GeneratedProperty(name = "im_sper_hw", type = String.class),

				@GeneratedProperty(name = "im_pack_part", type = String.class),

		}

)
public class Attr extends _Attr {
	static final long serialVersionUID = 1;

	public static Attr newAttr() throws WTException {
		Attr instance = new Attr();
		instance.initialize();
		return instance;
	}

}
