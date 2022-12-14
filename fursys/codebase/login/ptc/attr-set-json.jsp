<%@page import="org.json.simple.JSONArray"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.json.simple.parser.JSONParser"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.lang.reflect.Type"%>
<%@page import="com.google.gson.reflect.TypeToken"%>
<%@page import="javax.xml.bind.DatatypeConverter"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.util.Map"%>
<%@page import="platform.attr.service.AttrHelper"%>
<%@page import="platform.attr.entity.AttrDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
// String json = (String) request.getParameter("json");
// String s = "[{\"im_edge_2nd_outin\":\"1(SPB)\",\"im_pocket_outside_b\":\"1\",\"im_pocket_outside_c\":\"1\",\"im_router_leng_1\":\"6\",\"im_edge_width\":\"4\",\"im_pocket_outside_a\":\"1\",\"im_pocket_outside_d\":\"1\",\"im_assy_line\":\"1\",\"im_edge_curved_leng\":\"46\",\"im_edge_curved_num\":\"43\",\"im_form_junction\":\"464\",\"im_edge_depth\":\"44\",\"im_router_spec_3\":\"1654\",\"im_router_spec_2\":\"432\",\"im_router_spec_1\":\"46\",\"im_boring_thru\":\"\",\"im_uv_coat_1st\":\"1(일면)\",\"im_form_comp\":\"9465\",\"im_router_outside_leng\":\"42\",\"im_form_3d\":\"64\",\"im_asm_hw\":\"54654\",\"im_turn_key\":\"64\",\"im_sper_hw\":\"6\",\"im_coat_strip_leng\":\"61\",\"im_edge_coat_area\":\"6\",\"im_edge_total_leng\":\"654\",\"im_pack_part\":\"46\",\"im_boring_nothru\":\"21\",\"im_router_inside\":\"4654\",\"im_height\":\"54\",\"im_surf_finish_first\":\"465\",\"im_form_cut\":\"987\",\"im_edge_coat_type\":\"1(FX1)\",\"im_depth\":\"4\",\"im_pocket_inside_leng\":\"321\",\"im_frame_count\":\"646\",\"oid\":\"platform.attr.entity.Attr:1398360\",\"im_wash_face\":\"0(무)\",\"im_tenoner_leng\":\"64\",\"im_casing_screw_d\":\"46\",\"im_edge_coat\":\"1\",\"im_assy_time\":\"54\",\"im_casing_screw_b\":\"13\",\"im_casing_screw_c\":\"165\",\"im_width\":\"4\",\"im_repair_coat\":\"1(UV도장)\",\"im_urethane_2nd\":\"2(클로즈)\",\"im_wood_frame\":\"0\",\"cad_key\":\"4\",\"im_film_leng\":\"46\",\"im_sawcut_leng_ac\":\"123\",\"im_boring_side_y\":\"1\",\"im_uv_coat_2nd\":\"2(양면)\",\"im_edge_1st_outin\":\"1(SPB)\",\"im_boring_both\":\"1\",\"im_junction_type\":\"0\",\"im_casing_screw_a\":\"6543\",\"im_edge_height\":\"13\",\"im_boring_side_x\":\"1\",\"im_slant_process\":\"1면\",\"im_urethane_vol\":\"64\",\"im_boring_dia_d\":\"464\",\"im_boring_dia_c\":\"45465\",\"im_boring_dia_b\":\"54\",\"im_boring_dia_a\":\"321\",\"im_edge_spec_c\":\"31\",\"im_edge_spec_d\":\"65\",\"im_edge_spec_a\":\"654\",\"im_edge_trim_a\":\"L\",\"im_edge_spec_b\":\"42\",\"im_coat_area\":\"64\",\"im_edge_trim_c\":\"L\",\"im_edge_trim_b\":\"L\",\"im_urethane_1st\":\"1(오픈)\",\"im_casing_housing\":\"4\",\"im_router_leng_2\":\"432\",\"im_edge_trim_d\":\"L\",\"im_router_leng_3\":\"16\",\"im_sawcut_leng_bd\":\"56\"}]";

// JSONParser parser = new JSONParser();
// Object obj = parser.parse(json);
// JSONArray jsonObj = (JSONArray) obj;

// AttrHelper.service.create(jsonObj);


StringBuffer sb = new StringBuffer();
String line = null;
BufferedReader reader = request.getReader();
while ((line = reader.readLine()) != null) sb.append(line);

JSONParser parser = new JSONParser();
Object obj = parser.parse(sb.toString());
JSONArray jsonObj = new JSONArray();
jsonObj.add(obj);
System.out.println(jsonObj);
AttrHelper.service.create(jsonObj);

// for (int i = 0; i < jsonObj.size(); i++) {
// 	JSONObject ss = (JSONObject) jsonObj.get(i);
// 	out.println(ss.get("im_edge_2nd_outin"));
// }
%>