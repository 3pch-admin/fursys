package platform.erp.controller;

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

import platform.erp.service.ERPHelper;

@Controller
@RequestMapping(value = "/erp/**")
public class ERPController {

	@ResponseBody
	@RequestMapping(value = "/structure", method = RequestMethod.POST)
	public Map<String, Object> structure(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			ERPHelper.service.structure(params);
			result.put("result", true);
			result.put("msg", "원/부자재가 생성 되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/material", method = RequestMethod.POST)
	public Map<String, Object> material(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<String> list = new ArrayList<String>();
		try {
			list = ERPHelper.service.material(params);
			result.put("result", true);
			result.put("list", list);
			result.put("msg", "ERP 자재코드가 생성 되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

//	@ResponseBody
//	@RequestMapping(value = "/structure", method = RequestMethod.POST)
//	public Map<String, Object> structure(@RequestBody Map<String, Object> params) {
//		Map<String, Object> result = new HashMap<String, Object>();
//		try {
//			ERPHelper.service.structure(params);
//			result.put("result", true);
//			result.put("msg", "원/부자재가 생성 되었습니다.");
//		} catch (Exception e) {
//			e.printStackTrace();
//			result.put("result", false);
//			result.put("msg", e.toString());
//		}
//		return result;
//	}

	@ResponseBody
	@RequestMapping(value = "/getErpCode")
	public Map<String, Object> getErpCode(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = ERPHelper.manager.getErpCode(params);
			result.put("result", true);
			result.put("list", list);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@RequestMapping(value = "/getErpCode", method = RequestMethod.GET)
	public ModelAndView getErpCode() {
		ModelAndView model = new ModelAndView();
		model.setViewName("popup:/erp/erp-code-list");
		return model;
	}

	@RequestMapping(value = "/getStock", method = RequestMethod.GET)
	public ModelAndView getStock() {
		ModelAndView model = new ModelAndView();
		model.setViewName("popup:/erp/erp-stock-list");
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/getStock")
	public Map<String, Object> getStock(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = ERPHelper.manager.getStock(params);
			result.put("result", true);
			result.put("list", list);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() throws Exception {
		ModelAndView model = new ModelAndView();
		model.setViewName("/jsp/erp/erp-list.jsp");
		return model;
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> list(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result = ERPHelper.manager.list(params);
			result.put("result", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/getMCost")
	public Map<String, Object> getMCost(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		long mcost = 0L;
		try {
//			moeny = ERPHelper.manager.getMCost(params);
			result.put("result", true);
			result.put("mcost", mcost);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/getDCost")
	public Map<String, Object> getDCost(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result = ERPHelper.service.getDCost(params);
			result.put("result", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/categoryL", method = RequestMethod.POST)
	public Map<String, Object> categoryL(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			ArrayList<Map<String, Object>> list = ERPHelper.manager.categoryL(params);
			result.put("result", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/categoryM", method = RequestMethod.POST)
	public Map<String, Object> categoryM(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			ArrayList<Map<String, Object>> list = ERPHelper.manager.categoryM(params);
			result.put("result", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/categoryS", method = RequestMethod.POST)
	public Map<String, Object> categoryS(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			ArrayList<Map<String, Object>> list = ERPHelper.manager.categoryS(params);
			result.put("result", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}
}
