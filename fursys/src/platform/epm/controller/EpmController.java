package platform.epm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import platform.code.entity.BaseCode;
import platform.code.service.BaseCodeHelper;
import platform.dist.entity.DistEpmColumns;
import platform.epm.service.EpmHelper;
import wt.part.QuantityUnit;

@Controller
@RequestMapping(value = "/epm/**")
public class EpmController {

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
		model.setViewName("/jsp/epm/epm-list.jsp");
		return model;
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
		model.setViewName("popup:/epm/epm-popup-list");
		return model;
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> list(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result = EpmHelper.manager.list(params);
			result.put("result", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@RequestMapping(value = "/info")
	@ResponseBody
	public Map<String, Object> info(@RequestBody Map<String, Object> params) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		List<DistEpmColumns> list = new ArrayList<DistEpmColumns>();
		try {
			list = EpmHelper.manager.info(params);
			result.put("result", true);
			result.put("info", list);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}
}
