package platform.project.output.controller;

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

import platform.code.entity.BaseCode;
import platform.code.service.BaseCodeHelper;
import platform.project.output.entity.Output;
import platform.project.output.entity.OutputDTO;
import platform.project.output.service.OutputHelper;
import platform.raonk.entity.Raonk;
import platform.raonk.service.RaonkHelper;
import platform.util.CommonUtils;
import wt.doc.WTDocument;

@Controller
@RequestMapping(value = "/output/**")
public class OutputController {

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam String oid) throws Exception {
		ModelAndView model = new ModelAndView();
		model.addObject("oid", oid);
		model.setViewName("popup:/project/output/output-create");
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public Map<String, Object> create(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Output output = OutputHelper.service.create(params);
			result.put("result", true);
			result.put("msg", output.getName() + " 산출물이 정의 되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public Map<String, Object> delete(@RequestBody Map<String, Object> params) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			OutputHelper.service.delete(params);
			result.put("result", true);
			result.put("msg", "정의된 산출물이 삭제 되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/disconnect", method = RequestMethod.GET)
	public Map<String, Object> disconnect(@RequestParam String oid) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			OutputHelper.service.disconnect(oid);
			result.put("msg", "산출물 연결을 삭제 하였습니다.");
			result.put("result", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@RequestMapping(value = "/direct", method = RequestMethod.GET)
	public ModelAndView direct(@RequestParam String oid, @RequestParam String location) throws Exception {
		// 태스크 OID 만 전달한다.
		ModelAndView model = new ModelAndView();
		ArrayList<BaseCode> company = BaseCodeHelper.manager.getBaseCodeByCodeType("COMPANY");
		ArrayList<BaseCode> brand = BaseCodeHelper.manager.getBaseCodeByCodeType("BRAND");
		ArrayList<Raonk> raonk = RaonkHelper.manager.get();
		model.addObject("company", company);
		model.addObject("brand", brand);
		model.addObject("raonk", raonk);
		model.addObject("oid", oid);
		model.addObject("location", location);
		model.setViewName("popup:/project/output/output-direct");
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/direct", method = RequestMethod.POST)
	public Map<String, Object> direct(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			WTDocument doc = OutputHelper.service.direct(params);
			result.put("result", true);
			result.put("msg", doc.getName() + " 산출물 문서가 등록 되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@RequestMapping(value = "/connect", method = RequestMethod.GET)
	public ModelAndView connect(@RequestParam String oid, @RequestParam String location) throws Exception {
		// 태스크 OID 만 전달한다.
		ModelAndView model = new ModelAndView();
		ArrayList<BaseCode> company = BaseCodeHelper.manager.getBaseCodeByCodeType("COMPANY");
		ArrayList<BaseCode> brand = BaseCodeHelper.manager.getBaseCodeByCodeType("BRAND");
		ArrayList<Raonk> raonk = RaonkHelper.manager.get();
		model.addObject("company", company);
		model.addObject("brand", brand);
		model.addObject("raonk", raonk);
		model.addObject("oid", oid);
		model.addObject("location", location);
		model.setViewName("popup:/project/output/output-connect");
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/connect", method = RequestMethod.POST)
	public Map<String, Object> connect(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			OutputHelper.service.connect(params);
			result.put("result", true);
			result.put("msg", "산출물 문서가 등록 되었습니다.");
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
		Output output = (Output) CommonUtils.persistable(oid);
		OutputDTO dto = new OutputDTO(output);
		model.addObject("dto", dto);
		model.setViewName("popup:/project/output/output-view");
		return model;
	}

	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public ModelAndView modify(@RequestParam String oid) throws Exception {
		ModelAndView model = new ModelAndView();
		Output output = (Output) CommonUtils.persistable(oid);
		model.addObject("oid", oid);
		model.addObject("output", output);
		model.setViewName("popup:/project/output/output-modify");
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public Map<String, Object> modify(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			OutputHelper.service.modify(params);
			result.put("result", true);
			result.put("msg", "산출물이 수정 되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

}
