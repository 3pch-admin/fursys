package platform.raonk.controller;

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

import platform.raonk.entity.Raonk;
import platform.raonk.entity.RaonkColumns;
import platform.raonk.entity.RaonkDTO;
import platform.raonk.service.RaonkHelper;
import platform.util.CommonUtils;

@Controller
@RequestMapping(value = "/raonk/*")
public class RaonkController {

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView model = new ModelAndView();
		model.setViewName("/jsp/admin/raonk/raonk-list.jsp");
		return model;
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> list(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<RaonkColumns> list = new ArrayList<RaonkColumns>();
		try {
			list = RaonkHelper.manager.list(params);
			result.put("result", true);
			result.put("list", list);
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
		model.setViewName("popup:/admin/raonk/raonk-create");
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public Map<String, Object> create(@RequestBody RaonkDTO params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Raonk raonk = RaonkHelper.service.create(params);
			result.put("result", true);
			result.put("msg", raonk.getName() + " 라온케이 템플릿이 등록 되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public Map<String, Object> delete(@RequestParam String oid) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Raonk raonk = RaonkHelper.service.delete(oid);
			result.put("result", true);
			result.put("msg", raonk.getName() + " 라온케이 템플릿이 삭제 되었습니다.");
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
		Raonk raonk = (Raonk) CommonUtils.persistable(oid);
		RaonkDTO dto = new RaonkDTO(raonk);
		model.addObject("dto", dto);
		model.setViewName("popup:/admin/raonk/raonk-modify");
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public Map<String, Object> modify(@RequestBody RaonkDTO params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Raonk raonk = RaonkHelper.service.modify(params);
			result.put("result", true);
			result.put("msg", raonk.getName() + "라온케이 템플릿이 수정 되었습니다.");
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
		Raonk raonk = (Raonk) CommonUtils.persistable(oid);
		RaonkDTO dto = new RaonkDTO(raonk);
		model.addObject("dto", dto);
		model.setViewName("popup:/admin/raonk/raonk-view");
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/content", method = RequestMethod.GET)
	public Map<String, Object> content(@RequestParam String oid) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String content = RaonkHelper.manager.content(oid);
			result.put("content", content);
			result.put("result", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}
}
