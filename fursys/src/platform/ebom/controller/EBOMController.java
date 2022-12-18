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
import platform.doc.service.DocumentHelper;
import platform.ebom.entity.EBOM;
import platform.ebom.service.EBOMHelper;
import platform.ebom.vo.BOMCompareNode;
import platform.part.service.PartHelper;
import platform.util.CommonUtils;
import wt.doc.WTDocument;
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

	@RequestMapping(value = "/verify", method = RequestMethod.GET)
	public ModelAndView verify(@RequestParam String oid) throws Exception {
		ModelAndView model = new ModelAndView();
		ArrayList<BOMCompareNode> list = EBOMHelper.manager.compare(oid);
		model.addObject("list", list);
		model.addObject("oid", oid);
		model.setViewName("popup:/ebom/ebom-verify");
		return model;
	}

	@RequestMapping(value = "/confirm", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> confirm(@RequestParam String oid) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			EBOMHelper.service.confirm(oid);
			result.put("msg", "검증 완료 하였습니다.");
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
		String oid = (String) params.get("oid");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			WTPart part = (WTPart) CommonUtils.persistable(oid);
			WTPartMaster master = part.getMaster();
			EBOM header = EBOMHelper.manager.getHeader(master);
			if (header == null) {
				header = EBOMHelper.service.create(params);
			}
			result.put("oid", header.getPersistInfo().getObjectIdentifier().getStringValue());
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
			result.put("msg", "EBOM이 수정 되었습니다.");
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

	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public Map<String, Object> delete(@RequestParam String oid) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			EBOMHelper.service.delete(oid);
			result.put("result", true);
			result.put("msg", "EBOM이 삭제 되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam String oid) throws Exception {
		ModelAndView model = new ModelAndView();
		EBOM ebom = (EBOM) CommonUtils.persistable(oid);
		WTPartMaster master = ebom.getWtpartMaster();
		WTPart part = PartHelper.manager.getLatest(master);
		model.addObject("oid", oid);
		model.addObject("part", part);
		model.addObject("ebom", ebom);
		model.setViewName("popup:/ebom/ebom-view");
		return model;
	}

}