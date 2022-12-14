package platform.project.template.controller;

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
import platform.code.entity.BaseCode;
import platform.code.service.BaseCodeHelper;
import platform.project.task.entity.Task;
import platform.project.task.entity.TaskDTO;
import platform.project.template.entity.Template;
import platform.project.template.entity.TemplateDTO;
import platform.project.template.service.TemplateHelper;
import platform.util.CommonUtils;

@Controller
@RequestMapping(value = "/template/**")
public class TemplateController {
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() throws Exception {
		ModelAndView model = new ModelAndView();
//		ArrayList<BaseCode> brand = BaseCodeHelper.manager.getBaseCodeByCodeType("BRAND"); // 브랜드
		ArrayList<BaseCode> company = BaseCodeHelper.manager.getBaseCodeByCodeType("COMPANY"); // 회사
//		model.addObject("brand", brand);
		model.addObject("company", company);
		model.setViewName("/jsp/project/template/template-list.jsp");
		return model;
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> list(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result = TemplateHelper.manager.list(params);
			result.put("result", true);
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
		model.addObject("company", company);
		model.setViewName("popup:/project/template/template-create");
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public Map<String, Object> create(@RequestBody TemplateDTO dto) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Template template = TemplateHelper.service.create(dto);
			result.put("result", true);
			result.put("msg", template.getName() + " 템플릿이 등록 되었습니다.");
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
			Template template = TemplateHelper.service.delete(oid);
			result.put("result", true);
			result.put("msg", template.getName() + " 템플릿이 삭제 되었습니다.");
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
		ArrayList<Map<String, Object>> list = BaseCodeHelper.manager.getCodeMap("PROJECTROLE");
		model.addObject("oid", oid);
		model.addObject("list", list);
		model.setViewName("popup:/project/template/template-view");
		return model;
	}

	@RequestMapping(value = "/load")
	@ResponseBody
	public Map<String, Object> load(@RequestParam String oid) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		JSONArray list = new JSONArray();
		try {
			list = TemplateHelper.manager.load(oid);
			result.put("result", true);
			result.put("list", list);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@RequestMapping(value = "/saveTree", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveTree(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			TemplateHelper.service.saveTree(params);
			result.put("result", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@RequestMapping(value = "/templateView", method = RequestMethod.GET)
	public ModelAndView templateView(@RequestParam String oid) throws Exception {
		ModelAndView model = new ModelAndView();
		Template template = (Template) CommonUtils.persistable(oid);
		TemplateDTO dto = new TemplateDTO(template);
		model.addObject("dto", dto);
		model.setViewName("popup:/project/template/templateView");
		return model;
	}

	@RequestMapping(value = "/templateTaskView", method = RequestMethod.GET)
	public ModelAndView templateTaskView(@RequestParam String oid) throws Exception {
		ModelAndView model = new ModelAndView();
		Task task = (Task) CommonUtils.persistable(oid);
		TaskDTO dto = new TaskDTO(task);
		model.addObject("dto", dto);
		model.setViewName("popup:/project/template/templateTaskView");
		return model;
	}

	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public ModelAndView modify(@RequestParam String oid) throws Exception {
		ModelAndView model = new ModelAndView();
		Template template = (Template) CommonUtils.persistable(oid);
		ArrayList<BaseCode> company = BaseCodeHelper.manager.getBaseCodeByCodeType("COMPANY");
		model.addObject("company", company);
		model.addObject("template", template);
		model.setViewName("popup:/project/template/template-modify");
		return model;
	}
	
	@ResponseBody
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public Map<String, Object> modify(@RequestBody TemplateDTO dto) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Template template = TemplateHelper.service.modify(dto);
			result.put("result", true);
			result.put("msg", template.getName() + " 템플릿이 수정 되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/reset", method = RequestMethod.POST)
	public Map<String, Object> reset(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			TemplateHelper.service.reset(params);
			result.put("result", true);
			result.put("msg", "WBS 구조 및 전체 일정이 초기화 되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}
}
