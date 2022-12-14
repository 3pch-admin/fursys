package platform.attr.service;

import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import platform.attr.entity.Attr;
import platform.attr.entity.AttrDTO;
import platform.util.CommonUtils;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.org.WTPrincipal;
import wt.ownership.Ownership;
import wt.pom.Transaction;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.services.StandardManager;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.util.WTException;

public class StandardAttrService extends StandardManager implements AttrService {

	public static StandardAttrService newStandardAttrService() throws WTException {
		StandardAttrService instance = new StandardAttrService();
		instance.initialize();
		return instance;
	}

	@Override
	public void create(HashMap<String, List<AttrDTO>> paramsMap) throws Exception {
		Transaction trs = new Transaction();
		try {
			trs.start();

			WTPrincipal prin = SessionHelper.manager.getPrincipal();

			List<AttrDTO> addRows = paramsMap.get("addRows");
			for (AttrDTO dto : addRows) {
				Attr attr = Attr.newAttr();
				attr.setCad_key(dto.getCad_key());
				attr.setIm_edge_width(dto.getIm_edge_width());
				attr.setIm_edge_depth(dto.getIm_edge_depth());
				attr.setIm_width(dto.getIm_width());
				attr.setIm_depth(dto.getIm_depth());
				attr.setIm_height(dto.getIm_height());
				attr.setIm_edge_total_leng(dto.getIm_edge_total_leng());
				attr.setIm_urethane_vol(dto.getIm_urethane_vol());
				attr.setIm_coat_area(dto.getIm_coat_area());
				attr.setIm_edge_coat_area(dto.getIm_edge_coat_area());
				attr.setIm_sawcut_leng_ac(dto.getIm_sawcut_leng_ac());
				attr.setIm_sawcut_leng_bd(dto.getIm_sawcut_leng_bd());
				attr.setIm_edge_curved_leng(dto.getIm_edge_curved_leng());
				attr.setIm_router_outside_leng(dto.getIm_router_outside_leng());
				attr.setIm_router_inside(dto.getIm_router_inside());
				attr.setIm_coat_strip_leng(dto.getIm_coat_strip_leng());
				attr.setIm_pocket_inside_leng(dto.getIm_pocket_inside_leng());
				attr.setIm_edge_spec_a(dto.getIm_edge_spec_a());
				attr.setIm_edge_spec_b(dto.getIm_edge_spec_b());
				attr.setIm_edge_spec_c(dto.getIm_edge_spec_c());
				attr.setIm_edge_spec_d(dto.getIm_edge_spec_d());
				attr.setIm_router_spec_1(dto.getIm_router_spec_1());
				attr.setIm_router_spec_2(dto.getIm_router_spec_2());
				attr.setIm_router_spec_3(dto.getIm_router_spec_3());
				attr.setIm_router_leng_1(dto.getIm_router_leng_1());
				attr.setIm_router_leng_2(dto.getIm_router_leng_2());
				attr.setIm_router_leng_3(dto.getIm_router_leng_3());
				attr.setIm_film_leng(dto.getIm_film_leng());
				attr.setIm_boring_both(dto.getIm_boring_both());
				attr.setIm_slant_process(dto.getIm_slant_process());
				attr.setIm_edge_coat(dto.getIm_edge_coat());
				attr.setIm_edge_curved_num(dto.getIm_edge_curved_num());
				attr.setIm_edge_height(dto.getIm_edge_height());
				attr.setIm_boring_nothru(dto.getIm_boring_nothru());
				attr.setIm_boring_thru(dto.getIm_boring_thru());
				attr.setIm_casing_screw_a(dto.getIm_casing_screw_a());
				attr.setIm_casing_screw_b(dto.getIm_casing_screw_b());
				attr.setIm_casing_screw_c(dto.getIm_casing_screw_c());
				attr.setIm_casing_screw_d(dto.getIm_casing_screw_d());
				attr.setIm_casing_housing(dto.getIm_casing_housing());
				attr.setIm_boring_dia_a(dto.getIm_boring_dia_a());
				attr.setIm_boring_dia_b(dto.getIm_boring_dia_b());
				attr.setIm_boring_dia_c(dto.getIm_boring_dia_c());
				attr.setIm_boring_dia_d(dto.getIm_boring_dia_d());
				// DB

				attr.setIm_edge_trim_a(dto.getIm_edge_trim_a());
				attr.setIm_edge_trim_b(dto.getIm_edge_trim_b());
				attr.setIm_edge_trim_c(dto.getIm_edge_trim_c());
				attr.setIm_edge_trim_d(dto.getIm_edge_trim_d());
				attr.setIm_boring_side_x(dto.getIm_boring_side_x());
				attr.setIm_boring_side_y(dto.getIm_boring_side_y());
				attr.setIm_pocket_outside_a(dto.getIm_pocket_outside_a());
				attr.setIm_pocket_outside_b(dto.getIm_pocket_outside_b());
				attr.setIm_pocket_outside_c(dto.getIm_pocket_outside_c());
				attr.setIm_pocket_outside_d(dto.getIm_pocket_outside_d());
				attr.setIm_junction_type(dto.getIm_junction_type());
				attr.setIm_uv_coat_1st(dto.getIm_uv_coat_1st());
				attr.setIm_uv_coat_2nd(dto.getIm_uv_coat_2nd());
				attr.setIm_edge_1st_outin(dto.getIm_edge_1st_outin());
				attr.setIm_edge_2nd_outin(dto.getIm_edge_2nd_outin());
				attr.setIm_urethane_1st(dto.getIm_urethane_1st());
				attr.setIm_urethane_2nd(dto.getIm_urethane_2nd());
				attr.setIm_repair_coat(dto.getIm_repair_coat());
				attr.setIm_wash_face(dto.getIm_wash_face());
				attr.setIm_form_cut(dto.getIm_form_cut());
				attr.setIm_form_comp(dto.getIm_form_comp());
				attr.setIm_form_junction(dto.getIm_form_junction());
				attr.setIm_form_3d(dto.getIm_form_3d());
				attr.setIm_tenoner_leng(dto.getIm_tenoner_leng());
				attr.setIm_turn_key(dto.getIm_turn_key());
				attr.setIm_wood_frame(dto.getIm_wood_frame());
				attr.setIm_edge_coat_type(dto.getIm_edge_coat_type());
				attr.setIm_surf_finish_first(dto.getIm_surf_finish_first());
				attr.setIm_assy_line(dto.getIm_assy_line());
				attr.setIm_assy_time(dto.getIm_assy_time());
				attr.setIm_frame_count(dto.getIm_frame_count());
				attr.setIm_asm_hw(dto.getIm_asm_hw());
				attr.setIm_sper_hw(dto.getIm_sper_hw());
				attr.setIm_pack_part(dto.getIm_pack_part());
				attr.setOwnership(Ownership.newOwnership(prin));
				PersistenceHelper.manager.save(attr);
			}

			List<AttrDTO> editRows = paramsMap.get("editRows");
			for (AttrDTO dto : editRows) {
				Attr attr = (Attr) CommonUtils.persistable(dto.getOid());
				attr.setCad_key(dto.getCad_key());
				attr.setIm_edge_width(dto.getIm_edge_width());
				attr.setIm_edge_depth(dto.getIm_edge_depth());
				attr.setIm_width(dto.getIm_width());
				attr.setIm_depth(dto.getIm_depth());
				attr.setIm_height(dto.getIm_height());
				attr.setIm_edge_total_leng(dto.getIm_edge_total_leng());
				attr.setIm_urethane_vol(dto.getIm_urethane_vol());
				attr.setIm_coat_area(dto.getIm_coat_area());
				attr.setIm_edge_coat_area(dto.getIm_edge_coat_area());
				attr.setIm_sawcut_leng_ac(dto.getIm_sawcut_leng_ac());
				attr.setIm_sawcut_leng_bd(dto.getIm_sawcut_leng_bd());
				attr.setIm_edge_curved_leng(dto.getIm_edge_curved_leng());
				attr.setIm_router_outside_leng(dto.getIm_router_outside_leng());
				attr.setIm_router_inside(dto.getIm_router_inside());
				attr.setIm_coat_strip_leng(dto.getIm_coat_strip_leng());
				attr.setIm_pocket_inside_leng(dto.getIm_pocket_inside_leng());
				attr.setIm_edge_spec_a(dto.getIm_edge_spec_a());
				attr.setIm_edge_spec_b(dto.getIm_edge_spec_b());
				attr.setIm_edge_spec_c(dto.getIm_edge_spec_c());
				attr.setIm_edge_spec_d(dto.getIm_edge_spec_d());
				attr.setIm_router_spec_1(dto.getIm_router_spec_1());
				attr.setIm_router_spec_2(dto.getIm_router_spec_2());
				attr.setIm_router_spec_3(dto.getIm_router_spec_3());
				attr.setIm_router_leng_1(dto.getIm_router_leng_1());
				attr.setIm_router_leng_2(dto.getIm_router_leng_2());
				attr.setIm_router_leng_3(dto.getIm_router_leng_3());
				attr.setIm_film_leng(dto.getIm_film_leng());
				attr.setIm_boring_both(dto.getIm_boring_both());
				attr.setIm_slant_process(dto.getIm_slant_process());
				attr.setIm_edge_coat(dto.getIm_edge_coat());
				attr.setIm_edge_curved_num(dto.getIm_edge_curved_num());
				attr.setIm_edge_height(dto.getIm_edge_height());
				attr.setIm_boring_nothru(dto.getIm_boring_nothru());
				attr.setIm_boring_thru(dto.getIm_boring_thru());
				attr.setIm_casing_screw_a(dto.getIm_casing_screw_a());
				attr.setIm_casing_screw_b(dto.getIm_casing_screw_b());
				attr.setIm_casing_screw_c(dto.getIm_casing_screw_c());
				attr.setIm_casing_screw_d(dto.getIm_casing_screw_d());
				attr.setIm_casing_housing(dto.getIm_casing_housing());
				attr.setIm_boring_dia_a(dto.getIm_boring_dia_a());
				attr.setIm_boring_dia_b(dto.getIm_boring_dia_b());
				attr.setIm_boring_dia_c(dto.getIm_boring_dia_c());
				attr.setIm_boring_dia_d(dto.getIm_boring_dia_d());
				attr.setIm_edge_trim_a(dto.getIm_edge_trim_a());
				attr.setIm_edge_trim_b(dto.getIm_edge_trim_b());
				attr.setIm_edge_trim_c(dto.getIm_edge_trim_c());
				attr.setIm_edge_trim_d(dto.getIm_edge_trim_d());
				attr.setIm_boring_side_x(dto.getIm_boring_side_x());
				attr.setIm_boring_side_y(dto.getIm_boring_side_y());
				attr.setIm_pocket_outside_a(dto.getIm_pocket_outside_a());
				attr.setIm_pocket_outside_b(dto.getIm_pocket_outside_b());
				attr.setIm_pocket_outside_c(dto.getIm_pocket_outside_c());
				attr.setIm_pocket_outside_d(dto.getIm_pocket_outside_d());
				attr.setIm_junction_type(dto.getIm_junction_type());
				attr.setIm_uv_coat_1st(dto.getIm_uv_coat_1st());
				attr.setIm_uv_coat_2nd(dto.getIm_uv_coat_2nd());
				attr.setIm_edge_1st_outin(dto.getIm_edge_1st_outin());
				attr.setIm_edge_2nd_outin(dto.getIm_edge_2nd_outin());
				attr.setIm_urethane_1st(dto.getIm_urethane_1st());
				attr.setIm_urethane_2nd(dto.getIm_urethane_2nd());
				attr.setIm_repair_coat(dto.getIm_repair_coat());
				attr.setIm_wash_face(dto.getIm_wash_face());
				attr.setIm_form_cut(dto.getIm_form_cut());
				attr.setIm_form_comp(dto.getIm_form_comp());
				attr.setIm_form_junction(dto.getIm_form_junction());
				attr.setIm_form_3d(dto.getIm_form_3d());
				attr.setIm_tenoner_leng(dto.getIm_tenoner_leng());
				attr.setIm_turn_key(dto.getIm_turn_key());
				attr.setIm_wood_frame(dto.getIm_wood_frame());
				attr.setIm_edge_coat_type(dto.getIm_edge_coat_type());
				attr.setIm_surf_finish_first(dto.getIm_surf_finish_first());
				attr.setIm_assy_line(dto.getIm_assy_line());
				attr.setIm_assy_time(dto.getIm_assy_time());
				attr.setIm_frame_count(dto.getIm_frame_count());
				attr.setIm_asm_hw(dto.getIm_asm_hw());
				attr.setIm_sper_hw(dto.getIm_sper_hw());
				attr.setIm_pack_part(dto.getIm_pack_part());
				PersistenceHelper.manager.modify(attr);
			}

			List<AttrDTO> removeRows = paramsMap.get("removeRows");
			for (AttrDTO dto : removeRows) {
				Attr attr = (Attr) CommonUtils.persistable(dto.getOid());
				PersistenceHelper.manager.delete(attr);
			}

			trs.commit();
			trs = null;
		} catch (Exception e) {
			e.printStackTrace();
			trs.rollback();
			throw e;
		} finally {
			if (trs != null)
				trs.rollback();
		}
	}

	@Override
	public void create(JSONArray array) throws Exception {
		SessionContext prev = SessionContext.newContext();
		Transaction trs = new Transaction();
		try {
			SessionHelper.manager.setAdministrator();
			trs.start();

			for (int i = 0; i < array.size(); i++) {
				JSONObject obj = (JSONObject) array.get(i);
				// ex
				String cad_key = (String) obj.get("cad_key");

				String im_edge_width = (String) obj.get("im_edge_width");
				String im_edge_depth = (String) obj.get("im_edge_depth");
				String im_width = (String) obj.get("im_width");
				String im_depth = (String) obj.get("im_depth");
				String im_height = (String) obj.get("im_height");
				String im_edge_total_leng = (String) obj.get("im_edge_total_leng");
				String im_urethane_vol = (String) obj.get("im_urethane_vol");
				String im_coat_area = (String) obj.get("im_coat_area");
				String im_edge_coat_area = (String) obj.get("im_edge_coat_area");
				String im_sawcut_leng_ac = (String) obj.get("im_sawcut_leng_ac");
				String im_sawcut_leng_bd = (String) obj.get("im_sawcut_leng_bd");
				String im_edge_curved_leng = (String) obj.get("im_edge_curved_leng");
				String im_router_outside_leng = (String) obj.get("im_router_outside_leng");
				String im_router_inside = (String) obj.get("im_router_inside");
				String im_coat_strip_leng = (String) obj.get("im_coat_strip_leng");
				String im_pocket_inside_leng = (String) obj.get("im_pocket_inside_leng");
				String im_edge_spec_a = (String) obj.get("im_edge_spec_a");
				String im_edge_spec_b = (String) obj.get("im_edge_spec_b");
				String im_edge_spec_c = (String) obj.get("im_edge_spec_c");
				String im_edge_spec_d = (String) obj.get("im_edge_spec_d");
				String im_router_spec_1 = (String) obj.get("im_router_spec_1");
				String im_router_spec_2 = (String) obj.get("im_router_spec_2");
				String im_router_spec_3 = (String) obj.get("im_router_spec_3");
				String im_router_leng_1 = (String) obj.get("im_router_leng_1");
				String im_router_leng_2 = (String) obj.get("im_router_leng_2");
				String im_router_leng_3 = (String) obj.get("im_router_leng_3");
				String im_film_leng = (String) obj.get("im_film_leng");
				String im_boring_both = (String) obj.get("im_boring_both");
				String im_slant_process = (String) obj.get("im_slant_process");
				String im_edge_coat = (String) obj.get("im_edge_coat");
				String im_edge_curved_num = (String) obj.get("im_edge_curved_num");
				String im_edge_height = (String) obj.get("im_edge_height");
				String im_boring_nothru = (String) obj.get("im_boring_nothru");
				String im_boring_thru = (String) obj.get("im_boring_thru");
				String im_casing_screw_a = (String) obj.get("im_casing_screw_a");
				String im_casing_screw_b = (String) obj.get("im_casing_screw_b");
				String im_casing_screw_c = (String) obj.get("im_casing_screw_c");
				String im_casing_screw_d = (String) obj.get("im_casing_screw_d");
				String im_casing_housing = (String) obj.get("im_casing_housing");
				String im_boring_dia_a = (String) obj.get("im_boring_dia_a");
				String im_boring_dia_b = (String) obj.get("im_boring_dia_b");
				String im_boring_dia_c = (String) obj.get("im_boring_dia_c");
				String im_boring_dia_d = (String) obj.get("im_boring_dia_d");
				// 생산속성

				String im_edge_trim_a = (String) obj.get("im_edge_trim_a");
				String im_edge_trim_b = (String) obj.get("im_edge_trim_b");
				String im_edge_trim_c = (String) obj.get("im_edge_trim_c");
				String im_edge_trim_d = (String) obj.get("im_edge_trim_d");
				String im_boring_side_x = (String) obj.get("im_boring_side_x");
				String im_boring_side_y = (String) obj.get("im_boring_side_y");
				String im_pocket_outside_a = (String) obj.get("im_pocket_outside_a");
				String im_pocket_outside_b = (String) obj.get("im_pocket_outside_b");
				String im_pocket_outside_c = (String) obj.get("im_pocket_outside_c");
				String im_pocket_outside_d = (String) obj.get("im_pocket_outside_d");
				String im_junction_type = (String) obj.get("im_junction_type");
				String im_uv_coat_1st = (String) obj.get("im_uv_coat_1st");
				String im_uv_coat_2nd = (String) obj.get("im_uv_coat_2nd");
				String im_edge_1st_outin = (String) obj.get("im_edge_1st_outin");
				String im_edge_2nd_outin = (String) obj.get("im_edge_2nd_outin");
				String im_urethane_1st = (String) obj.get("im_urethane_1st");
				String im_urethane_2nd = (String) obj.get("im_urethane_2nd");
				String im_repair_coat = (String) obj.get("im_repair_coat");
				String im_wash_face = (String) obj.get("im_wash_face");
				String im_form_cut = (String) obj.get("im_form_cut");
				String im_form_comp = (String) obj.get("im_form_comp");
				String im_form_junction = (String) obj.get("im_form_junction");
				String im_form_3d = (String) obj.get("im_form_3d");
				String im_tenoner_leng = (String) obj.get("im_tenoner_leng");
				String im_turn_key = (String) obj.get("im_turn_key");
				String im_wood_frame = (String) obj.get("im_wood_frame");
				String im_edge_coat_type = (String) obj.get("im_edge_coat_type");
				String im_surf_finish_first = (String) obj.get("im_surf_finish_first");
				String im_assy_line = (String) obj.get("im_assy_line");
				String im_assy_time = (String) obj.get("im_assy_time");
				String im_frame_count = (String) obj.get("im_frame_count");
				String im_asm_hw = (String) obj.get("im_asm_hw");
				String im_sper_hw = (String) obj.get("im_sper_hw");
				String im_pack_part = (String) obj.get("im_pack_part");
				// 확장속성

				QuerySpec query = new QuerySpec();
				int idx = query.appendClassList(Attr.class, true);
				SearchCondition sc = new SearchCondition(Attr.class, Attr.CAD_KEY, "=", cad_key);
				query.appendWhere(sc, new int[] { idx });

				QueryResult result = PersistenceHelper.manager.find(query);
				if (result.hasMoreElements()) {
					Object[] oo = (Object[]) result.nextElement();
					Attr attr = (Attr) oo[0];
					attr.setCad_key(cad_key);
					attr.setIm_edge_width(im_edge_width);
					attr.setIm_edge_depth(im_edge_depth);
					attr.setIm_width(im_width);
					attr.setIm_depth(im_depth);
					attr.setIm_height(im_height);
					attr.setIm_edge_total_leng(im_edge_total_leng);
					attr.setIm_urethane_vol(im_urethane_vol);
					attr.setIm_coat_area(im_coat_area);
					attr.setIm_edge_coat_area(im_edge_coat_area);
					attr.setIm_sawcut_leng_ac(im_sawcut_leng_ac);
					attr.setIm_sawcut_leng_bd(im_sawcut_leng_bd);
					attr.setIm_edge_curved_leng(im_edge_curved_leng);
					attr.setIm_router_outside_leng(im_router_outside_leng);
					attr.setIm_router_inside(im_router_inside);
					attr.setIm_coat_strip_leng(im_coat_strip_leng);
					attr.setIm_pocket_inside_leng(im_pocket_inside_leng);
					attr.setIm_edge_spec_a(im_edge_spec_a);
					attr.setIm_edge_spec_b(im_edge_spec_b);
					attr.setIm_edge_spec_c(im_edge_spec_c);
					attr.setIm_edge_spec_d(im_edge_spec_d);
					attr.setIm_router_spec_1(im_router_spec_1);
					attr.setIm_router_spec_2(im_router_spec_2);
					attr.setIm_router_spec_3(im_router_spec_3);
					attr.setIm_router_leng_1(im_router_leng_1);
					attr.setIm_router_leng_2(im_router_leng_2);
					attr.setIm_router_leng_3(im_router_leng_3);
					attr.setIm_film_leng(im_film_leng);
					attr.setIm_boring_both(im_boring_both);
					attr.setIm_slant_process(im_slant_process);
					attr.setIm_edge_coat(im_edge_coat);
					attr.setIm_edge_curved_num(im_edge_curved_num);
					attr.setIm_edge_height(im_edge_height);
					attr.setIm_boring_nothru(im_boring_nothru);
					attr.setIm_boring_thru(im_boring_thru);
					attr.setIm_casing_screw_a(im_casing_screw_a);
					attr.setIm_casing_screw_b(im_casing_screw_b);
					attr.setIm_casing_screw_c(im_casing_screw_c);
					attr.setIm_casing_screw_d(im_casing_screw_d);
					attr.setIm_casing_housing(im_casing_housing);
					attr.setIm_boring_dia_a(im_boring_dia_a);
					attr.setIm_boring_dia_b(im_boring_dia_b);
					attr.setIm_boring_dia_c(im_boring_dia_c);
					attr.setIm_boring_dia_d(im_boring_dia_d);
					attr.setIm_edge_trim_a(im_edge_trim_a);
					attr.setIm_edge_trim_b(im_edge_trim_b);
					attr.setIm_edge_trim_c(im_edge_trim_c);
					attr.setIm_edge_trim_d(im_edge_trim_d);
					attr.setIm_boring_side_x(im_boring_side_x);
					attr.setIm_boring_side_y(im_boring_side_y);
					attr.setIm_pocket_outside_a(im_pocket_outside_a);
					attr.setIm_pocket_outside_b(im_pocket_outside_b);
					attr.setIm_pocket_outside_c(im_pocket_outside_c);
					attr.setIm_pocket_outside_d(im_pocket_outside_d);
					attr.setIm_junction_type(im_junction_type);
					attr.setIm_uv_coat_1st(im_uv_coat_1st);
					attr.setIm_uv_coat_2nd(im_uv_coat_2nd);
					attr.setIm_edge_1st_outin(im_edge_1st_outin);
					attr.setIm_edge_2nd_outin(im_edge_2nd_outin);
					attr.setIm_urethane_1st(im_urethane_1st);
					attr.setIm_urethane_2nd(im_urethane_2nd);
					attr.setIm_repair_coat(im_repair_coat);
					attr.setIm_wash_face(im_wash_face);
					attr.setIm_form_cut(im_form_cut);
					attr.setIm_form_comp(im_form_comp);
					attr.setIm_form_junction(im_form_junction);
					attr.setIm_form_3d(im_form_3d);
					attr.setIm_tenoner_leng(im_tenoner_leng);
					attr.setIm_turn_key(im_turn_key);
					attr.setIm_wood_frame(im_wood_frame);
					attr.setIm_edge_coat_type(im_edge_coat_type);
					attr.setIm_surf_finish_first(im_surf_finish_first);
					attr.setIm_assy_line(im_assy_line);
					attr.setIm_assy_time(im_assy_time);
					attr.setIm_frame_count(im_frame_count);
					attr.setIm_asm_hw(im_asm_hw);
					attr.setIm_sper_hw(im_sper_hw);
					attr.setIm_pack_part(im_pack_part);

					PersistenceHelper.manager.modify(attr);
				}

				if (result.size() == 0) {

					Attr attr = Attr.newAttr();
					attr.setCad_key(cad_key);
					attr.setIm_edge_width(im_edge_width);
					attr.setIm_edge_depth(im_edge_depth);
					attr.setIm_width(im_width);
					attr.setIm_depth(im_depth);
					attr.setIm_height(im_height);
					attr.setIm_edge_total_leng(im_edge_total_leng);
					attr.setIm_urethane_vol(im_urethane_vol);
					attr.setIm_coat_area(im_coat_area);
					attr.setIm_edge_coat_area(im_edge_coat_area);
					attr.setIm_sawcut_leng_ac(im_sawcut_leng_ac);
					attr.setIm_sawcut_leng_bd(im_sawcut_leng_bd);
					attr.setIm_edge_curved_leng(im_edge_curved_leng);
					attr.setIm_router_outside_leng(im_router_outside_leng);
					attr.setIm_router_inside(im_router_inside);
					attr.setIm_coat_strip_leng(im_coat_strip_leng);
					attr.setIm_pocket_inside_leng(im_pocket_inside_leng);
					attr.setIm_edge_spec_a(im_edge_spec_a);
					attr.setIm_edge_spec_b(im_edge_spec_b);
					attr.setIm_edge_spec_c(im_edge_spec_c);
					attr.setIm_edge_spec_d(im_edge_spec_d);
					attr.setIm_router_spec_1(im_router_spec_1);
					attr.setIm_router_spec_2(im_router_spec_2);
					attr.setIm_router_spec_3(im_router_spec_3);
					attr.setIm_router_leng_1(im_router_leng_1);
					attr.setIm_router_leng_2(im_router_leng_2);
					attr.setIm_router_leng_3(im_router_leng_3);
					attr.setIm_film_leng(im_film_leng);
					attr.setIm_boring_both(im_boring_both);
					attr.setIm_slant_process(im_slant_process);
					attr.setIm_edge_coat(im_edge_coat);
					attr.setIm_edge_curved_num(im_edge_curved_num);
					attr.setIm_edge_height(im_edge_height);
					attr.setIm_boring_nothru(im_boring_nothru);
					attr.setIm_boring_thru(im_boring_thru);
					attr.setIm_casing_screw_a(im_casing_screw_a);
					attr.setIm_casing_screw_b(im_casing_screw_b);
					attr.setIm_casing_screw_c(im_casing_screw_c);
					attr.setIm_casing_screw_d(im_casing_screw_d);
					attr.setIm_casing_housing(im_casing_housing);
					attr.setIm_boring_dia_a(im_boring_dia_a);
					attr.setIm_boring_dia_b(im_boring_dia_b);
					attr.setIm_boring_dia_c(im_boring_dia_c);
					attr.setIm_boring_dia_d(im_boring_dia_d);
					attr.setIm_edge_trim_a(im_edge_trim_a);
					attr.setIm_edge_trim_b(im_edge_trim_b);
					attr.setIm_edge_trim_c(im_edge_trim_c);
					attr.setIm_edge_trim_d(im_edge_trim_d);
					attr.setIm_boring_side_x(im_boring_side_x);
					attr.setIm_boring_side_y(im_boring_side_y);
					attr.setIm_pocket_outside_a(im_pocket_outside_a);
					attr.setIm_pocket_outside_b(im_pocket_outside_b);
					attr.setIm_pocket_outside_c(im_pocket_outside_c);
					attr.setIm_pocket_outside_d(im_pocket_outside_d);
					attr.setIm_junction_type(im_junction_type);
					attr.setIm_uv_coat_1st(im_uv_coat_1st);
					attr.setIm_uv_coat_2nd(im_uv_coat_2nd);
					attr.setIm_edge_1st_outin(im_edge_1st_outin);
					attr.setIm_edge_2nd_outin(im_edge_2nd_outin);
					attr.setIm_urethane_1st(im_urethane_1st);
					attr.setIm_urethane_2nd(im_urethane_2nd);
					attr.setIm_repair_coat(im_repair_coat);
					attr.setIm_wash_face(im_wash_face);
					attr.setIm_form_cut(im_form_cut);
					attr.setIm_form_comp(im_form_comp);
					attr.setIm_form_junction(im_form_junction);
					attr.setIm_form_3d(im_form_3d);
					attr.setIm_tenoner_leng(im_tenoner_leng);
					attr.setIm_turn_key(im_turn_key);
					attr.setIm_wood_frame(im_wood_frame);
					attr.setIm_edge_coat_type(im_edge_coat_type);
					attr.setIm_surf_finish_first(im_surf_finish_first);
					attr.setIm_assy_line(im_assy_line);
					attr.setIm_assy_time(im_assy_time);
					attr.setIm_frame_count(im_frame_count);
					attr.setIm_asm_hw(im_asm_hw);
					attr.setIm_sper_hw(im_sper_hw);
					attr.setIm_pack_part(im_pack_part);

					PersistenceHelper.manager.save(attr);
				}

			}

			trs.commit();
			trs = null;
		} catch (Exception e) {
			e.printStackTrace();
			trs.rollback();
			throw e;
		} finally {
			if (trs != null)
				trs.rollback();
			SessionContext.setContext(prev);
		}
	}
}
