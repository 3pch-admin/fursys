package platform.mbom.controller;

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

import net.sf.json.JSONArray;
import platform.code.entity.BaseCode;
import platform.code.service.BaseCodeHelper;
import platform.mbom.entity.MBOM;
import platform.mbom.entity.MBOMColumns;
import platform.mbom.service.MBOMHelper;
import wt.part.QuantityUnit;

@Controller
@RequestMapping(value = "/mbom/**")
public class MBOMController {
	
	@RequestMapping(value = "/reference", method = RequestMethod.GET)
	public ModelAndView reference() throws Exception{
		ModelAndView model = new ModelAndView();
		model.setViewName("popup:/mbom/mbom-reference");
		return model;
	}
	@RequestMapping(value = "/derived", method = RequestMethod.GET)
	public ModelAndView derived() throws Exception{
		ModelAndView model = new ModelAndView();
		model.setViewName("popup:/mbom/mbom-derived");
		return model;
	}
	@RequestMapping(value = "/matBatch", method = RequestMethod.GET)
	public ModelAndView matBatch() throws Exception{
		ModelAndView model = new ModelAndView();
		model.setViewName("popup:/mbom/mbom-matBatch");
		return model;
	}

	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> modify(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			MBOM header = MBOMHelper.service.modify(params);
			result.put("result", true);
			result.put("msg", header.getNumber() + "가 수정 되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView model = new ModelAndView();
		model.setViewName("/jsp/mbom/mbom-list.jsp");
		return model;
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> list(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result = MBOMHelper.manager.list(params);
			result.put("result", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

//	@RequestMapping(value = "/create", method = RequestMethod.POST)
//	@ResponseBody
//	public Map<String, Object> create(@RequestBody Map<String, Object> params) {
//		Map<String, Object> result = new HashMap<String, Object>();
//		try {
//			MBOMHeader header = MBOMHelper.service.create(params);
//			result.put("result", true);
//			result.put("msg", header.getNumber() + " MBOM이 등록 되었습니다.");
//		} catch (Exception e) {
//			e.printStackTrace();
//			result.put("result", false);
//			result.put("msg", e.toString());
//		}
//		return result;
//	}

	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public ModelAndView modify() throws Exception {
		ModelAndView model = new ModelAndView();
//		model.addObject("oid", oid);
		model.setViewName("popup:/mbom/mbom-modify");
		return model;
	}

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam String oid) throws Exception {
		ModelAndView model = new ModelAndView();
		model.addObject("oid", oid);
		model.setViewName("popup:/mbom/mbom-view");
		return model;
	}

	@RequestMapping(value = "/loadMTree")
	@ResponseBody
	public JSONArray loadMTree(@RequestParam String oid) throws Exception {
		JSONArray arr = MBOMHelper.manager.loadMTree(oid);
		System.out.println(arr);
		return arr;
	}

//	@RequestMapping(value = "/reloadMTree")
//	@ResponseBody
//	public Map<String, Object> reloadMTree(@RequestParam String oid, @RequestParam String color) throws Exception {
//		JSONArray data = MBOMHelper.manager.loadMTree(oid, color);
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("result", true);
//		map.put("data", data);
//		return map;
//	}

	@RequestMapping(value = "/popup", method = RequestMethod.GET)
	public ModelAndView popup() throws Exception {
		ModelAndView model = new ModelAndView();
		model.setViewName("popup:/mbom/mbom-popup-list");
		return model;
	}

	@RequestMapping(value = "/info")
	@ResponseBody
	public Map<String, Object> info(@RequestBody Map<String, Object> params) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		List<MBOMColumns> list = new ArrayList<MBOMColumns>();
		try {
			list = MBOMHelper.manager.info(params);
			result.put("list", list);
			result.put("result", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
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
		model.setViewName("popup:/mbom/mpart-append-create");
		return model;
	}

}
