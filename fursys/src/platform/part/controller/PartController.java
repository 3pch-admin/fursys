package platform.part.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONObject;
import platform.code.entity.BaseCode;
import platform.code.service.BaseCodeHelper;
import platform.ebom.entity.EBOM;
import platform.ebom.service.EBOMHelper;
import platform.part.entity.PartColumns;
import platform.part.entity.PartDTO;
import platform.part.service.PartHelper;
import platform.util.CommonUtils;
import wt.part.QuantityUnit;
import wt.part.WTPart;
import wt.part.WTPartMaster;

@Controller
@RequestMapping(value = "/part/**")
public class PartController {

//	@RequestMapping(value = "/modify", method = RequestMethod.GET)
//	public ModelAndView modify(@RequestParam String oid) throws Exception {
//		ModelAndView model = new ModelAndView();
//		WTPart part = (WTPart) CommonUtils.persistable(oid);
//		PartDTO dto = new PartDTO(part);
//		model.addObject("dto", dto);
//		ArrayList<BaseCode> company = BaseCodeHelper.manager.getBaseCodeByCodeType("COMPANY");
//		ArrayList<BaseCode> brand = BaseCodeHelper.manager.getBaseCodeByCodeType("BRAND");
//		QuantityUnit[] units = QuantityUnit.getQuantityUnitSet();
//		model.addObject("units", units);
//		model.addObject("company", company);
//		model.addObject("brand", brand);
//		model.setViewName("popup:/part/part-modify");
//		return model;
//	}

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam String oid) throws Exception {
		ModelAndView model = new ModelAndView();
		WTPart part = (WTPart) CommonUtils.persistable(oid);
		PartDTO dto = new PartDTO(part);
		model.addObject("dto", dto);
		model.setViewName("popup:/part/part-view");
		return model;
	}

	@RequestMapping(value = "/info")
	@ResponseBody
	public Map<String, Object> info(@RequestBody Map<String, Object> params) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		String oid = (String) params.get("oid");
		List<PartColumns> list = new ArrayList<PartColumns>();
		try {

			WTPart part = (WTPart) CommonUtils.persistable(oid);
			WTPartMaster master = part.getMaster();
			EBOM header = EBOMHelper.manager.getHeader(master);
			if (header != null) {
				result.put("result", false);
				result.put("msg", "이미 등록된 EBOM이 존재합니다.");
				return result;
			}

			list = PartHelper.manager.info(params);
			result.put("result", true);
			result.put("info", list);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@RequestMapping(value = "/popup", method = RequestMethod.GET)
	public ModelAndView popup() throws Exception {
		ModelAndView model = new ModelAndView();
		ArrayList<BaseCode> brand = BaseCodeHelper.manager.getBaseCodeByCodeType("BRAND"); // 브랜드
		ArrayList<BaseCode> company = BaseCodeHelper.manager.getBaseCodeByCodeType("COMPANY"); // 회사
		ArrayList<BaseCode> cat_l = BaseCodeHelper.manager.getBaseCodeByCodeType("CAT_L"); // 회사
		ArrayList<BaseCode> cat_m = BaseCodeHelper.manager.getBaseCodeByCodeType("CAT_M"); // 회사
		QuantityUnit[] units = QuantityUnit.getQuantityUnitSet();
		model.addObject("brand", brand);
		model.addObject("company", company);
		model.addObject("cat_l", cat_l);
		model.addObject("cat_m", cat_m);
		model.addObject("units", units);
		model.setViewName("popup:/part/part-popup-list");
		return model;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() throws Exception {
		ModelAndView model = new ModelAndView();
		ArrayList<BaseCode> brand = BaseCodeHelper.manager.getBaseCodeByCodeType("BRAND"); // 브랜드
		ArrayList<BaseCode> company = BaseCodeHelper.manager.getBaseCodeByCodeType("COMPANY"); // 회사
		ArrayList<BaseCode> cat_l = BaseCodeHelper.manager.getBaseCodeByCodeType("CAT_L"); // 회사
		ArrayList<BaseCode> cat_m = BaseCodeHelper.manager.getBaseCodeByCodeType("CAT_M"); // 회사
		QuantityUnit[] units = QuantityUnit.getQuantityUnitSet();
		model.addObject("brand", brand);
		model.addObject("company", company);
		model.addObject("cat_l", cat_l);
		model.addObject("cat_m", cat_m);
		model.addObject("units", units);
		model.setViewName("/jsp/part/part-list.jsp");
		return model;
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> list(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result = PartHelper.manager.list(params);
			result.put("result", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public Map<String, Object> create(@RequestBody PartDTO params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			WTPart part = PartHelper.service.create(params);
			result.put("result", true);
			result.put("msg", part.getName() + " 부품이 등록 되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() throws Exception {
		ModelAndView model = new ModelAndView();
		ArrayList<BaseCode> company = BaseCodeHelper.manager.getBaseCodeByCodeType("COMPANY");
		ArrayList<BaseCode> brand = BaseCodeHelper.manager.getBaseCodeByCodeType("BRAND");
		ArrayList<BaseCode> cat_l = BaseCodeHelper.manager.getBaseCodeByCodeType("CAT_L");
		ArrayList<BaseCode> cat_m = BaseCodeHelper.manager.getBaseCodeByCodeType("CAT_M");
		QuantityUnit[] units = QuantityUnit.getQuantityUnitSet();
		model.addObject("units", units);
		model.addObject("company", company);
		model.addObject("brand", brand);
		model.addObject("cat_l", cat_l);
		model.addObject("cat_m", cat_m);
		model.setViewName("popup:/part/part-create");
		return model;
	}
	
	@ResponseBody
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public Map<String, Object> modify(@RequestBody PartDTO params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			WTPart part = PartHelper.service.modify(params);
			result.put("result", true);
			result.put("msg", part.getName() + " 부품이 수정 되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}
	
	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public ModelAndView modify(@RequestParam String oid) throws Exception {
		ModelAndView model = new ModelAndView();
		ArrayList<BaseCode> company = BaseCodeHelper.manager.getBaseCodeByCodeType("COMPANY");
		ArrayList<BaseCode> brand = BaseCodeHelper.manager.getBaseCodeByCodeType("BRAND");
		ArrayList<BaseCode> cat_l = BaseCodeHelper.manager.getBaseCodeByCodeType("CAT_L");
		ArrayList<BaseCode> cat_m = BaseCodeHelper.manager.getBaseCodeByCodeType("CAT_M");
		QuantityUnit[] units = QuantityUnit.getQuantityUnitSet();
		WTPart part = (WTPart) CommonUtils.persistable(oid);
		PartDTO dto = new PartDTO(part);
		model.addObject("dto", dto);
		model.addObject("units", units);
		model.addObject("company", company);
		model.addObject("brand", brand);
		model.addObject("cat_l", cat_l);
		model.addObject("cat_m", cat_m);
		model.setViewName("popup:/part/part-modify");
		return model;
	}

	@RequestMapping(value = "/append", method = RequestMethod.GET)
	public ModelAndView append(@RequestParam String partTypeCd) throws Exception {
		ModelAndView model = new ModelAndView();
		ArrayList<BaseCode> company = BaseCodeHelper.manager.getBaseCodeByCodeType("COMPANY");
		ArrayList<BaseCode> brand = BaseCodeHelper.manager.getBaseCodeByCodeType("BRAND");

		QuantityUnit[] units = QuantityUnit.getQuantityUnitSet();

		model.addObject("units", units);
		model.addObject("company", company);
		model.addObject("brand", brand);
		model.addObject("partTypeCd", partTypeCd);
		model.setViewName("popup:/part/part-append-create");
		return model;
	}

	@RequestMapping(value = "/top", method = RequestMethod.GET)
	public ModelAndView top(@RequestParam String partTypeCd) throws Exception {
		ModelAndView model = new ModelAndView();
		ArrayList<BaseCode> company = BaseCodeHelper.manager.getBaseCodeByCodeType("COMPANY");
		ArrayList<BaseCode> brand = BaseCodeHelper.manager.getBaseCodeByCodeType("BRAND");

		QuantityUnit[] units = QuantityUnit.getQuantityUnitSet();

		model.addObject("units", units);
		model.addObject("company", company);
		model.addObject("brand", brand);
		model.addObject("partTypeCd", partTypeCd);
		model.setViewName("popup:/part/part-top-create");
		return model;
	}

	@RequestMapping(value = "/exist", method = RequestMethod.GET)
	public ModelAndView exist(@RequestParam String partTypeCd) throws Exception {
		ModelAndView model = new ModelAndView();
		ArrayList<BaseCode> brand = BaseCodeHelper.manager.getBaseCodeByCodeType("BRAND"); // 브랜드
		ArrayList<BaseCode> company = BaseCodeHelper.manager.getBaseCodeByCodeType("COMPANY"); // 회사
		ArrayList<BaseCode> cat_l = BaseCodeHelper.manager.getBaseCodeByCodeType("CAT_L"); // 회사
		ArrayList<BaseCode> cat_m = BaseCodeHelper.manager.getBaseCodeByCodeType("CAT_M"); // 회사
		QuantityUnit[] units = QuantityUnit.getQuantityUnitSet();
		model.addObject("brand", brand);
		model.addObject("company", company);
		model.addObject("cat_l", cat_l);
		model.addObject("cat_m", cat_m);
		model.addObject("units", units);
		model.addObject("partTypeCd", partTypeCd);
		model.setViewName("popup:/part/part-exist-list");
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/attach", method = RequestMethod.POST)
	public Map<String, Object> attach(@RequestBody PartDTO params) {
		Map<String, Object> result = new HashMap<String, Object>();
		String partType = params.getPartType();
		try {
			JSONObject node = PartHelper.service.attach(params);
			result.put("result", true);
			result.put("node", node);
			result.put("msg", "등록 되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/exist", method = RequestMethod.POST)
	public Map<String, Object> exist(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			JSONObject node = PartHelper.manager.exist(params);
			result.put("result", true);
			result.put("node", node);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}
}