package platform.ebom.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONArray;
import platform.ebom.entity.EBOM;
import platform.ebom.service.EBOMHelper;
import platform.part.service.PartHelper;
import platform.util.CommonUtils;
import wt.part.WTPart;
import wt.part.WTPartMaster;

@Controller
@RequestMapping(value = "/ebom/**")
public class EBOMController {

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() throws Exception {
		ModelAndView model = new ModelAndView();
		model.setViewName("/jsp/ebom/ebom-list.jsp");
		return model;
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> list(@RequestBody Map<String, Object> params) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result = EBOMHelper.manager.list(params);
			result.put("result", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView model = new ModelAndView();
		model.setViewName("popup:/ebom/ebom-create");
		return model;
	}

	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public ModelAndView modify(@RequestParam String oid) throws Exception {
		ModelAndView model = new ModelAndView();
		EBOM ebom = (EBOM) CommonUtils.persistable(oid);
		WTPartMaster m = ebom.getWtpartMaster();
		WTPart part = PartHelper.manager.getLatest(m);
		model.addObject("oid", oid);
		model.addObject("part", part);
		model.setViewName("popup:/ebom/ebom-modify");
		return model;
	}

	@RequestMapping(value = "/right")
	@ResponseBody
	public Map<String, Object> right(@RequestParam String oid) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		JSONArray list = new JSONArray();
		try {
			list = PartHelper.manager.right(oid);
			result.put("result", true);
			result.put("list", list);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@RequestMapping(value = "/left")
	@ResponseBody
	public Map<String, Object> left(@RequestParam String oid) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		JSONArray list = new JSONArray();
		try {
			list = PartHelper.manager.left(oid);
			result.put("result", true);
			result.put("list", list);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> create(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			EBOM header = EBOMHelper.service.create(params);
			result.put("oid", header.getPersistInfo().getObjectIdentifier().getStringValue());
			result.put("save", "save");
			result.put("result", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> modify(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			EBOM header = EBOMHelper.service.modify(params);
			result.put("oid", header.getPersistInfo().getObjectIdentifier().getStringValue());
			result.put("save", "save");
			result.put("result", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@RequestMapping(value = "/loadTree")
	@ResponseBody
	public Map<String, Object> loadTree(@RequestParam String oid) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		JSONArray list = new JSONArray();
		try {
			list = EBOMHelper.manager.loadTree(oid);
			result.put("result", true);
			result.put("list", list);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@RequestMapping(value = "/verify", method = RequestMethod.GET)
	public ModelAndView verify(@RequestParam String oid) {
		ModelAndView model = new ModelAndView();
		model.setViewName("popup:/ebom/ebom-verify");
		return model;
	}
}