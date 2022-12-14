package platform.attr.entity;

import lombok.ToString;
import platform.util.CommonUtils;

@ToString
public class AttrDTO {

	private String oid;
	private String cad_key;
	private String im_edge_width;
	private String im_edge_depth;
	private String im_width;
	private String im_depth;
	private String im_height;
	private String im_edge_total_leng;
	private String im_urethane_vol;
	private String im_coat_area;
	private String im_edge_coat_area;
	private String im_sawcut_leng_ac;
	private String im_sawcut_leng_bd;
	private String im_edge_curved_leng;
	private String im_router_outside_leng;
	private String im_router_inside;
	private String im_coat_strip_leng;
	private String im_pocket_inside_leng;
	private String im_edge_spec_a;
	private String im_edge_spec_b;
	private String im_edge_spec_c;
	private String im_edge_spec_d;
	private String im_router_spec_1;
	private String im_router_spec_2;
	private String im_router_spec_3;
	private String im_router_leng_1;
	private String im_router_leng_2;
	private String im_router_leng_3;
	private String im_film_leng;
	private String im_boring_both;
	private String im_slant_process;
	private String im_edge_coat;
	private String im_edge_curved_num;
	private String im_edge_height;
	private String im_boring_nothru;
	private String im_boring_thru;
	private String im_casing_screw_a;
	private String im_casing_screw_b;
	private String im_casing_screw_c;
	private String im_casing_screw_d;
	private String im_casing_housing;
	private String im_boring_dia_a;
	private String im_boring_dia_b;
	private String im_boring_dia_c;
	private String im_boring_dia_d;

	// 생산속성
	private String im_edge_trim_a;
	private String im_edge_trim_b;
	private String im_edge_trim_c;
	private String im_edge_trim_d;
	private String im_boring_side_x;
	private String im_boring_side_y;
	private String im_pocket_outside_a;
	private String im_pocket_outside_b;
	private String im_pocket_outside_c;
	private String im_pocket_outside_d;
	private String im_junction_type;
	private String im_uv_coat_1st;
	private String im_uv_coat_2nd;
	private String im_edge_1st_outin;
	private String im_edge_2nd_outin;
	private String im_urethane_1st;
	private String im_urethane_2nd;
	private String im_repair_coat;
	private String im_wash_face;
	private String im_form_cut;
	private String im_form_comp;
	private String im_form_junction;
	private String im_form_3d;
	private String im_tenoner_leng;
	private String im_turn_key;
	private String im_wood_frame;
	private String im_edge_coat_type;
	private String im_surf_finish_first;
	private String im_assy_line;
	private String im_assy_time;
	private String im_frame_count;
	private String im_asm_hw;
	private String im_sper_hw;
	private String im_pack_part;

	// end 확장속성
	public AttrDTO() {
	}

	public AttrDTO(Attr attr) throws Exception {
		setOid(CommonUtils.oid(attr));
		setCad_key(attr.getCad_key());
		setIm_edge_width(attr.getIm_edge_width());
		setIm_edge_depth(attr.getIm_edge_depth());
		setIm_width(attr.getIm_width());
		setIm_depth(attr.getIm_depth());
		setIm_height(attr.getIm_height());
		setIm_edge_total_leng(attr.getIm_edge_total_leng());
		setIm_urethane_vol(attr.getIm_urethane_vol());
		setIm_coat_area(attr.getIm_coat_area());
		setIm_edge_coat_area(attr.getIm_edge_coat_area());
		setIm_sawcut_leng_ac(attr.getIm_sawcut_leng_ac());
		setIm_sawcut_leng_bd(attr.getIm_sawcut_leng_bd());
		setIm_edge_curved_leng(attr.getIm_edge_curved_leng());
		setIm_router_outside_leng(attr.getIm_router_outside_leng());
		setIm_router_inside(attr.getIm_router_inside());
		setIm_coat_strip_leng(attr.getIm_coat_strip_leng());
		setIm_pocket_inside_leng(attr.getIm_pocket_inside_leng());
		setIm_edge_spec_a(attr.getIm_edge_spec_a());
		setIm_edge_spec_b(attr.getIm_edge_spec_b());
		setIm_edge_spec_c(attr.getIm_edge_spec_c());
		setIm_edge_spec_d(attr.getIm_edge_spec_d());
		setIm_router_spec_1(attr.getIm_router_spec_1());
		setIm_router_spec_2(attr.getIm_router_spec_2());
		setIm_router_spec_3(attr.getIm_router_spec_3());
		setIm_router_leng_1(attr.getIm_router_leng_1());
		setIm_router_leng_2(attr.getIm_router_leng_2());
		setIm_router_leng_3(attr.getIm_router_leng_3());
		setIm_film_leng(attr.getIm_film_leng());
		setIm_boring_both(attr.getIm_boring_both());
		setIm_slant_process(attr.getIm_slant_process());
		setIm_edge_coat(attr.getIm_edge_coat());
		setIm_edge_curved_num(attr.getIm_edge_curved_num());
		setIm_edge_height(attr.getIm_edge_height());
		setIm_boring_nothru(attr.getIm_boring_nothru());
		setIm_boring_thru(attr.getIm_boring_thru());
		setIm_casing_screw_a(attr.getIm_casing_screw_a());
		setIm_casing_screw_b(attr.getIm_casing_screw_b());
		setIm_casing_screw_c(attr.getIm_casing_screw_c());
		setIm_casing_screw_d(attr.getIm_casing_screw_d());
		setIm_casing_housing(attr.getIm_casing_housing());
		setIm_boring_dia_a(attr.getIm_boring_dia_a());
		setIm_boring_dia_b(attr.getIm_boring_dia_b());
		setIm_boring_dia_c(attr.getIm_boring_dia_c());
		setIm_boring_dia_d(attr.getIm_boring_dia_d());
		setIm_edge_trim_a(attr.getIm_edge_trim_a());
		setIm_edge_trim_b(attr.getIm_edge_trim_b());
		setIm_edge_trim_c(attr.getIm_edge_trim_c());
		setIm_edge_trim_d(attr.getIm_edge_trim_d());
		setIm_boring_side_x(attr.getIm_boring_side_x());
		setIm_boring_side_y(attr.getIm_boring_side_y());
		setIm_pocket_outside_a(attr.getIm_pocket_outside_a());
		setIm_pocket_outside_b(attr.getIm_pocket_outside_b());
		setIm_pocket_outside_c(attr.getIm_pocket_outside_c());
		setIm_pocket_outside_d(attr.getIm_pocket_outside_d());
		setIm_junction_type(attr.getIm_junction_type());
		setIm_uv_coat_1st(attr.getIm_uv_coat_1st());
		setIm_uv_coat_2nd(attr.getIm_uv_coat_2nd());
		setIm_edge_1st_outin(attr.getIm_edge_1st_outin());
		setIm_edge_2nd_outin(attr.getIm_edge_2nd_outin());
		setIm_urethane_1st(attr.getIm_urethane_1st());
		setIm_urethane_2nd(attr.getIm_urethane_2nd());
		setIm_repair_coat(attr.getIm_repair_coat());
		setIm_wash_face(attr.getIm_wash_face());
		setIm_form_cut(attr.getIm_form_cut());
		setIm_form_comp(attr.getIm_form_comp());
		setIm_form_junction(attr.getIm_form_junction());
		setIm_form_3d(attr.getIm_form_3d());
		setIm_tenoner_leng(attr.getIm_tenoner_leng());
		setIm_turn_key(attr.getIm_turn_key());
		setIm_wood_frame(attr.getIm_wood_frame());
		setIm_edge_coat_type(attr.getIm_edge_coat_type());
		setIm_surf_finish_first(attr.getIm_surf_finish_first());
		setIm_assy_line(attr.getIm_assy_line());
		setIm_assy_time(attr.getIm_assy_time());
		setIm_frame_count(attr.getIm_frame_count());
		setIm_asm_hw(attr.getIm_asm_hw());
		setIm_sper_hw(attr.getIm_sper_hw());
		setIm_pack_part(attr.getIm_pack_part());
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getCad_key() {
		return cad_key;
	}

	public void setCad_key(String cad_key) {
		this.cad_key = cad_key;
	}

	public String getIm_edge_width() {
		return im_edge_width;
	}

	public void setIm_edge_width(String im_edge_width) {
		this.im_edge_width = im_edge_width;
	}

	public String getIm_edge_depth() {
		return im_edge_depth;
	}

	public void setIm_edge_depth(String im_edge_depth) {
		this.im_edge_depth = im_edge_depth;
	}

	public String getIm_width() {
		return im_width;
	}

	public void setIm_width(String im_width) {
		this.im_width = im_width;
	}

	public String getIm_depth() {
		return im_depth;
	}

	public void setIm_depth(String im_depth) {
		this.im_depth = im_depth;
	}

	public String getIm_height() {
		return im_height;
	}

	public void setIm_height(String im_height) {
		this.im_height = im_height;
	}

	public String getIm_edge_total_leng() {
		return im_edge_total_leng;
	}

	public void setIm_edge_total_leng(String im_edge_total_leng) {
		this.im_edge_total_leng = im_edge_total_leng;
	}

	public String getIm_urethane_vol() {
		return im_urethane_vol;
	}

	public void setIm_urethane_vol(String im_urethane_vol) {
		this.im_urethane_vol = im_urethane_vol;
	}

	public String getIm_coat_area() {
		return im_coat_area;
	}

	public void setIm_coat_area(String im_coat_area) {
		this.im_coat_area = im_coat_area;
	}

	public String getIm_edge_coat_area() {
		return im_edge_coat_area;
	}

	public void setIm_edge_coat_area(String im_edge_coat_area) {
		this.im_edge_coat_area = im_edge_coat_area;
	}

	public String getIm_sawcut_leng_ac() {
		return im_sawcut_leng_ac;
	}

	public void setIm_sawcut_leng_ac(String im_sawcut_leng_ac) {
		this.im_sawcut_leng_ac = im_sawcut_leng_ac;
	}

	public String getIm_sawcut_leng_bd() {
		return im_sawcut_leng_bd;
	}

	public void setIm_sawcut_leng_bd(String im_sawcut_leng_bd) {
		this.im_sawcut_leng_bd = im_sawcut_leng_bd;
	}

	public String getIm_edge_curved_leng() {
		return im_edge_curved_leng;
	}

	public void setIm_edge_curved_leng(String im_edge_curved_leng) {
		this.im_edge_curved_leng = im_edge_curved_leng;
	}

	public String getIm_router_outside_leng() {
		return im_router_outside_leng;
	}

	public void setIm_router_outside_leng(String im_router_outside_leng) {
		this.im_router_outside_leng = im_router_outside_leng;
	}

	public String getIm_router_inside() {
		return im_router_inside;
	}

	public void setIm_router_inside(String im_router_inside) {
		this.im_router_inside = im_router_inside;
	}

	public String getIm_coat_strip_leng() {
		return im_coat_strip_leng;
	}

	public void setIm_coat_strip_leng(String im_coat_strip_leng) {
		this.im_coat_strip_leng = im_coat_strip_leng;
	}

	public String getIm_pocket_inside_leng() {
		return im_pocket_inside_leng;
	}

	public void setIm_pocket_inside_leng(String im_pocket_inside_leng) {
		this.im_pocket_inside_leng = im_pocket_inside_leng;
	}

	public String getIm_edge_spec_a() {
		return im_edge_spec_a;
	}

	public void setIm_edge_spec_a(String im_edge_spec_a) {
		this.im_edge_spec_a = im_edge_spec_a;
	}

	public String getIm_edge_spec_b() {
		return im_edge_spec_b;
	}

	public void setIm_edge_spec_b(String im_edge_spec_b) {
		this.im_edge_spec_b = im_edge_spec_b;
	}

	public String getIm_edge_spec_c() {
		return im_edge_spec_c;
	}

	public void setIm_edge_spec_c(String im_edge_spec_c) {
		this.im_edge_spec_c = im_edge_spec_c;
	}

	public String getIm_edge_spec_d() {
		return im_edge_spec_d;
	}

	public void setIm_edge_spec_d(String im_edge_spec_d) {
		this.im_edge_spec_d = im_edge_spec_d;
	}

	public String getIm_router_spec_1() {
		return im_router_spec_1;
	}

	public void setIm_router_spec_1(String im_router_spec_1) {
		this.im_router_spec_1 = im_router_spec_1;
	}

	public String getIm_router_spec_2() {
		return im_router_spec_2;
	}

	public void setIm_router_spec_2(String im_router_spec_2) {
		this.im_router_spec_2 = im_router_spec_2;
	}

	public String getIm_router_spec_3() {
		return im_router_spec_3;
	}

	public void setIm_router_spec_3(String im_router_spec_3) {
		this.im_router_spec_3 = im_router_spec_3;
	}

	public String getIm_router_leng_1() {
		return im_router_leng_1;
	}

	public void setIm_router_leng_1(String im_router_leng_1) {
		this.im_router_leng_1 = im_router_leng_1;
	}

	public String getIm_router_leng_2() {
		return im_router_leng_2;
	}

	public void setIm_router_leng_2(String im_router_leng_2) {
		this.im_router_leng_2 = im_router_leng_2;
	}

	public String getIm_router_leng_3() {
		return im_router_leng_3;
	}

	public void setIm_router_leng_3(String im_router_leng_3) {
		this.im_router_leng_3 = im_router_leng_3;
	}

	public String getIm_film_leng() {
		return im_film_leng;
	}

	public void setIm_film_leng(String im_film_leng) {
		this.im_film_leng = im_film_leng;
	}

	public String getIm_boring_both() {
		return im_boring_both;
	}

	public void setIm_boring_both(String im_boring_both) {
		this.im_boring_both = im_boring_both;
	}

	public String getIm_slant_process() {
		return im_slant_process;
	}

	public void setIm_slant_process(String im_slant_process) {
		this.im_slant_process = im_slant_process;
	}

	public String getIm_edge_coat() {
		return im_edge_coat;
	}

	public void setIm_edge_coat(String im_edge_coat) {
		this.im_edge_coat = im_edge_coat;
	}

	public String getIm_edge_curved_num() {
		return im_edge_curved_num;
	}

	public void setIm_edge_curved_num(String im_edge_curved_num) {
		this.im_edge_curved_num = im_edge_curved_num;
	}

	public String getIm_edge_height() {
		return im_edge_height;
	}

	public void setIm_edge_height(String im_edge_height) {
		this.im_edge_height = im_edge_height;
	}

	public String getIm_boring_nothru() {
		return im_boring_nothru;
	}

	public void setIm_boring_nothru(String im_boring_nothru) {
		this.im_boring_nothru = im_boring_nothru;
	}

	public String getIm_boring_thru() {
		return im_boring_thru;
	}

	public void setIm_boring_thru(String im_boring_thru) {
		this.im_boring_thru = im_boring_thru;
	}

	public String getIm_casing_screw_a() {
		return im_casing_screw_a;
	}

	public void setIm_casing_screw_a(String im_casing_screw_a) {
		this.im_casing_screw_a = im_casing_screw_a;
	}

	public String getIm_casing_screw_b() {
		return im_casing_screw_b;
	}

	public void setIm_casing_screw_b(String im_casing_screw_b) {
		this.im_casing_screw_b = im_casing_screw_b;
	}

	public String getIm_casing_screw_c() {
		return im_casing_screw_c;
	}

	public void setIm_casing_screw_c(String im_casing_screw_c) {
		this.im_casing_screw_c = im_casing_screw_c;
	}

	public String getIm_casing_screw_d() {
		return im_casing_screw_d;
	}

	public void setIm_casing_screw_d(String im_casing_screw_d) {
		this.im_casing_screw_d = im_casing_screw_d;
	}

	public String getIm_casing_housing() {
		return im_casing_housing;
	}

	public void setIm_casing_housing(String im_casing_housing) {
		this.im_casing_housing = im_casing_housing;
	}

	public String getIm_boring_dia_a() {
		return im_boring_dia_a;
	}

	public void setIm_boring_dia_a(String im_boring_dia_a) {
		this.im_boring_dia_a = im_boring_dia_a;
	}

	public String getIm_boring_dia_b() {
		return im_boring_dia_b;
	}

	public void setIm_boring_dia_b(String im_boring_dia_b) {
		this.im_boring_dia_b = im_boring_dia_b;
	}

	public String getIm_boring_dia_c() {
		return im_boring_dia_c;
	}

	public void setIm_boring_dia_c(String im_boring_dia_c) {
		this.im_boring_dia_c = im_boring_dia_c;
	}

	public String getIm_boring_dia_d() {
		return im_boring_dia_d;
	}

	public void setIm_boring_dia_d(String im_boring_dia_d) {
		this.im_boring_dia_d = im_boring_dia_d;
	}

	public String getIm_edge_trim_a() {
		return im_edge_trim_a;
	}

	public void setIm_edge_trim_a(String im_edge_trim_a) {
		this.im_edge_trim_a = im_edge_trim_a;
	}

	public String getIm_edge_trim_b() {
		return im_edge_trim_b;
	}

	public void setIm_edge_trim_b(String im_edge_trim_b) {
		this.im_edge_trim_b = im_edge_trim_b;
	}

	public String getIm_edge_trim_c() {
		return im_edge_trim_c;
	}

	public void setIm_edge_trim_c(String im_edge_trim_c) {
		this.im_edge_trim_c = im_edge_trim_c;
	}

	public String getIm_edge_trim_d() {
		return im_edge_trim_d;
	}

	public void setIm_edge_trim_d(String im_edge_trim_d) {
		this.im_edge_trim_d = im_edge_trim_d;
	}

	public String getIm_boring_side_x() {
		return im_boring_side_x;
	}

	public void setIm_boring_side_x(String im_boring_side_x) {
		this.im_boring_side_x = im_boring_side_x;
	}

	public String getIm_boring_side_y() {
		return im_boring_side_y;
	}

	public void setIm_boring_side_y(String im_boring_side_y) {
		this.im_boring_side_y = im_boring_side_y;
	}

	public String getIm_pocket_outside_a() {
		return im_pocket_outside_a;
	}

	public void setIm_pocket_outside_a(String im_pocket_outside_a) {
		this.im_pocket_outside_a = im_pocket_outside_a;
	}

	public String getIm_pocket_outside_b() {
		return im_pocket_outside_b;
	}

	public void setIm_pocket_outside_b(String im_pocket_outside_b) {
		this.im_pocket_outside_b = im_pocket_outside_b;
	}

	public String getIm_pocket_outside_c() {
		return im_pocket_outside_c;
	}

	public void setIm_pocket_outside_c(String im_pocket_outside_c) {
		this.im_pocket_outside_c = im_pocket_outside_c;
	}

	public String getIm_pocket_outside_d() {
		return im_pocket_outside_d;
	}

	public void setIm_pocket_outside_d(String im_pocket_outside_d) {
		this.im_pocket_outside_d = im_pocket_outside_d;
	}

	public String getIm_junction_type() {
		return im_junction_type;
	}

	public void setIm_junction_type(String im_junction_type) {
		this.im_junction_type = im_junction_type;
	}

	public String getIm_uv_coat_1st() {
		return im_uv_coat_1st;
	}

	public void setIm_uv_coat_1st(String im_uv_coat_1st) {
		this.im_uv_coat_1st = im_uv_coat_1st;
	}

	public String getIm_uv_coat_2nd() {
		return im_uv_coat_2nd;
	}

	public void setIm_uv_coat_2nd(String im_uv_coat_2nd) {
		this.im_uv_coat_2nd = im_uv_coat_2nd;
	}

	public String getIm_edge_1st_outin() {
		return im_edge_1st_outin;
	}

	public void setIm_edge_1st_outin(String im_edge_1st_outin) {
		this.im_edge_1st_outin = im_edge_1st_outin;
	}

	public String getIm_edge_2nd_outin() {
		return im_edge_2nd_outin;
	}

	public void setIm_edge_2nd_outin(String im_edge_2nd_outin) {
		this.im_edge_2nd_outin = im_edge_2nd_outin;
	}

	public String getIm_urethane_1st() {
		return im_urethane_1st;
	}

	public void setIm_urethane_1st(String im_urethane_1st) {
		this.im_urethane_1st = im_urethane_1st;
	}

	public String getIm_urethane_2nd() {
		return im_urethane_2nd;
	}

	public void setIm_urethane_2nd(String im_urethane_2nd) {
		this.im_urethane_2nd = im_urethane_2nd;
	}

	public String getIm_repair_coat() {
		return im_repair_coat;
	}

	public void setIm_repair_coat(String im_repair_coat) {
		this.im_repair_coat = im_repair_coat;
	}

	public String getIm_wash_face() {
		return im_wash_face;
	}

	public void setIm_wash_face(String im_wash_face) {
		this.im_wash_face = im_wash_face;
	}

	public String getIm_form_cut() {
		return im_form_cut;
	}

	public void setIm_form_cut(String im_form_cut) {
		this.im_form_cut = im_form_cut;
	}

	public String getIm_form_comp() {
		return im_form_comp;
	}

	public void setIm_form_comp(String im_form_comp) {
		this.im_form_comp = im_form_comp;
	}

	public String getIm_form_junction() {
		return im_form_junction;
	}

	public void setIm_form_junction(String im_form_junction) {
		this.im_form_junction = im_form_junction;
	}

	public String getIm_form_3d() {
		return im_form_3d;
	}

	public void setIm_form_3d(String im_form_3d) {
		this.im_form_3d = im_form_3d;
	}

	public String getIm_tenoner_leng() {
		return im_tenoner_leng;
	}

	public void setIm_tenoner_leng(String im_tenoner_leng) {
		this.im_tenoner_leng = im_tenoner_leng;
	}

	public String getIm_turn_key() {
		return im_turn_key;
	}

	public void setIm_turn_key(String im_turn_key) {
		this.im_turn_key = im_turn_key;
	}

	public String getIm_wood_frame() {
		return im_wood_frame;
	}

	public void setIm_wood_frame(String im_wood_frame) {
		this.im_wood_frame = im_wood_frame;
	}

	public String getIm_edge_coat_type() {
		return im_edge_coat_type;
	}

	public void setIm_edge_coat_type(String im_edge_coat_type) {
		this.im_edge_coat_type = im_edge_coat_type;
	}

	public String getIm_surf_finish_first() {
		return im_surf_finish_first;
	}

	public void setIm_surf_finish_first(String im_surf_finish_first) {
		this.im_surf_finish_first = im_surf_finish_first;
	}

	public String getIm_assy_line() {
		return im_assy_line;
	}

	public void setIm_assy_line(String im_assy_line) {
		this.im_assy_line = im_assy_line;
	}

	public String getIm_assy_time() {
		return im_assy_time;
	}

	public void setIm_assy_time(String im_assy_time) {
		this.im_assy_time = im_assy_time;
	}

	public String getIm_frame_count() {
		return im_frame_count;
	}

	public void setIm_frame_count(String im_frame_count) {
		this.im_frame_count = im_frame_count;
	}

	public String getIm_asm_hw() {
		return im_asm_hw;
	}

	public void setIm_asm_hw(String im_asm_hw) {
		this.im_asm_hw = im_asm_hw;
	}

	public String getIm_sper_hw() {
		return im_sper_hw;
	}

	public void setIm_sper_hw(String im_sper_hw) {
		this.im_sper_hw = im_sper_hw;
	}

	public String getIm_pack_part() {
		return im_pack_part;
	}

	public void setIm_pack_part(String im_pack_part) {
		this.im_pack_part = im_pack_part;
	}

}
