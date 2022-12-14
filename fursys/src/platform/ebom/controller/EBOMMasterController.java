package platform.ebom.controller;

import java.util.ArrayList;
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
import platform.ebom.entity.EBOMMaster;
import platform.ebom.service.EBOMHelper;
import platform.ebom.service.EBOMMasterHelper;
import platform.part.service.PartHelper;
import platform.util.CommonUtils;
import platform.util.StringUtils;
import wt.part.WTPart;
import wt.part.WTPartMaster;

@Controller
@RequestMapping(value = "/ebomMaster/**")
public class EBOMMasterController {

	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> modify(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			EBOMMaster header = EBOMMasterHelper.service.modify(params);
			result.put("result", true);
			result.put("msg", header.getNumber() + "가 수정 되었습니다.");
			result.put("oid", header.getPersistInfo().getObjectIdentifier().getStringValue());
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
		model.setViewName("/jsp/ebom/ebom-list.jsp");
		return model;
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> list(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result = EBOMMasterHelper.manager.list(params);
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
			list = PartHelper.manager.loadTree(oid);
			result.put("result", true);
			result.put("list", list);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@RequestMapping(value = "/load")
	@ResponseBody
	public Map<String, Object> load(@RequestParam String oid) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		JSONArray list = new JSONArray();
		try {
			list = PartHelper.manager.load(oid);
			result.put("result", true);
			result.put("list", list);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@RequestMapping(value = "/loadETree")
	@ResponseBody
	public JSONArray loadETree(@RequestParam String oid) throws Exception {
		return EBOMMasterHelper.manager.loadETree(oid);
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView model = new ModelAndView();
		model.setViewName("popup:/ebom/ebom-create");
		return model;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> create(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String oid = (String) params.get("poid");
			WTPart p = (WTPart) CommonUtils.persistable(oid);
			EBOMMaster m = EBOMMasterHelper.manager.getEBOMMaster(p.getMaster());
			if (m == null) {
				EBOMMasterHelper.service.create(params);
			}
			result.put("result", true);
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
		boolean latest = EBOMHelper.manager.latest(oid);
		model.addObject("latest", latest);
		model.addObject("oid", oid);
		model.setViewName("popup:/ebom/ebom-modify");
		return model;
	}

	@RequestMapping(value = "/partlist", method = RequestMethod.GET)
	public ModelAndView partlist(@RequestParam String oid) throws Exception {
		ModelAndView model = new ModelAndView();
		model.addObject("oid", oid);
		model.setViewName("popup:/ebom/ebom-partlist");
		return model;
	}

	@RequestMapping(value = "/partlist", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> partlist(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		String oid = (String) params.get("oid");
		try {

			EBOMMaster m = (EBOMMaster) CommonUtils.persistable(oid);
			if (!StringUtils.isNotNull(m.getDerivedColor())) {
				result.put("result", false);
				result.put("msg", "파생할 색상이 선택 되지 않앗습니다.");
				return result;
			}

			EBOMMaster header = EBOMMasterHelper.service.partlist(params);

			result.put("result", true);
			result.put("msg", header.getNumber() + " PART LIST 가 등록 되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@RequestMapping(value = "/loadPTree")
	@ResponseBody
	public JSONArray loadPTree(@RequestParam String oid, @RequestParam String color) throws Exception {
		return EBOMMasterHelper.manager.loadPTree(oid, color);
	}

	@RequestMapping(value = "/reloadPTree")
	@ResponseBody
	public Map<String, Object> reloadPTree(@RequestParam String oid, @RequestParam String color) throws Exception {
		JSONArray data = EBOMMasterHelper.manager.loadPTree(oid, color);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", true);
		map.put("data", data);
		return map;
	}

	@RequestMapping(value = "/color")
	@ResponseBody
	public Map<String, Object> color(@RequestBody Map<String, Object> params) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			EBOMMasterHelper.service.color(params);
			result.put("result", true);
			result.put("msg", "변경사항이 저장되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@RequestMapping(value = "/reloadETree")
	@ResponseBody
	public Map<String, Object> reloadETree(@RequestParam String oid) throws Exception {
		JSONArray data = EBOMMasterHelper.manager.loadETree(oid);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", true);
		map.put("data", data);
		return map;
	}

	@RequestMapping(value = "/popup", method = RequestMethod.GET)
	public ModelAndView popup() throws Exception {
		ModelAndView model = new ModelAndView();
		model.setViewName("popup:/ebom/partlist-popup-list");
		return model;
	}

	@RequestMapping(value = "/plist", method = RequestMethod.GET)
	public ModelAndView plist() {
		ModelAndView model = new ModelAndView();
		model.setViewName("/jsp/ebom/partlist-list.jsp");
		return model;
	}

	@RequestMapping(value = "/plist", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> plist(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result = EBOMMasterHelper.manager.plist(params);
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
		JSONArray list = new JSONArray();
		try {
			list = EBOMMasterHelper.manager.info(params);
			result.put("list", list);
			result.put("result", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@RequestMapping(value = "/derived")
	@ResponseBody
	public Map<String, Object> derived(@RequestBody Map<String, Object> params) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		EBOMMaster derived = null;
		try {
			derived = EBOMMasterHelper.service.derived(params);
			result.put("msg", derived.getNumber() + "가 파생 되었습니다.");
			result.put("result", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@RequestMapping(value = "/derived", method = RequestMethod.GET)
	public ModelAndView derived(@RequestParam String oid) throws Exception {
		ModelAndView model = new ModelAndView();
		EBOMMaster m = (EBOMMaster) CommonUtils.persistable(oid);
		WTPart p = PartHelper.manager.getLatest(m.getPart());
		String poid = p.getPersistInfo().getObjectIdentifier().getStringValue();
		model.addObject("oid", oid);
		model.addObject("poid", poid);
		model.setViewName("popup:/ebom/ebom-derived");
		return model;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> delete(@RequestParam String oid) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			EBOMMaster header = EBOMMasterHelper.service.delete(oid);
			result.put("result", true);
			result.put("msg", header.getNumber() + "가 삭제 되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@RequestMapping(value = "/save")
	@ResponseBody
	public Map<String, Object> save(@RequestBody Map<String, Object> params) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		EBOMMaster derived = null;
		try {
			derived = EBOMMasterHelper.service.save(params);
			result.put("msg", derived.getNumber() + "가 등록 되었습니다.");
			result.put("result", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@RequestMapping(value = "/compare", method = RequestMethod.GET)
	public ModelAndView compare(@RequestParam String oid) throws Exception {
		ModelAndView model = new ModelAndView();
		WTPart part = (WTPart) CommonUtils.persistable(oid);
		WTPartMaster m = (WTPartMaster) part.getMaster();
		EBOMMaster header = EBOMMasterHelper.manager.getEBOMMaster(m);
		ArrayList<Map<String, Object>> list = EBOMMasterHelper.manager.compare(header, new ArrayList<>());
		model.addObject("list", list);
		model.addObject("oid", oid);
		model.setViewName("popup:/ebom/ebom-compare");
		return model;
	}

}
