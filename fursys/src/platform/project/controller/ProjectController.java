package platform.project.controller;

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
import platform.project.entity.Project;
import platform.project.entity.ProjectDTO;
import platform.project.issue.service.IssueHelper;
import platform.project.output.service.OutputHelper;
import platform.project.service.ProjectHelper;
import platform.project.task.entity.Task;
import platform.project.task.entity.TaskDTO;
import platform.project.template.service.TemplateHelper;
import platform.util.CommonUtils;

@Controller
@RequestMapping(value = "/project/**")
public class ProjectController {

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() throws Exception {
		ModelAndView model = new ModelAndView();
		ArrayList<BaseCode> company = BaseCodeHelper.manager.getBaseCodeByCodeType("COMPANY"); // 회사
		model.addObject("company", company);
		model.setViewName("/jsp/project/project-list.jsp");
		return model;
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> list(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result = ProjectHelper.manager.list(params);
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
//		ArrayList<BaseCode> company = BaseCodeHelper.manager.getBaseCodeByCodeType("COMPANY");
		ArrayList<BaseCode> pjtType = BaseCodeHelper.manager.getBaseCodeByCodeType("PROJECTTYPE");
		ArrayList<BaseCode> customer = BaseCodeHelper.manager.getBaseCodeByCodeType("CUSTOMER");
		ArrayList<Map<String, Object>> list = TemplateHelper.manager.getTemplateMap();
		model.addObject("pjtType", pjtType);
		model.addObject("customer", customer);
		model.addObject("list", list);
		model.setViewName("popup:/project/project-create");
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public Map<String, Object> create(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Project project = ProjectHelper.service.create(params);
			result.put("result", true);
			result.put("msg", project.getName() + " 프로젝트가 등록 되었습니다.");
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
			list = ProjectHelper.manager.load(oid);
			result.put("result", true);
			result.put("list", list);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam String oid, @RequestParam(required = false) String toid) throws Exception {
		ModelAndView model = new ModelAndView();
		ArrayList<Map<String, Object>> list = BaseCodeHelper.manager.getCodeMap("PROJECTROLE");
		Project project = (Project) CommonUtils.persistable(oid);

		boolean isProgress = project.getState().equals("진행중");
		boolean isComplete = project.getState().equals("완료됨");
		boolean isReady = project.getState().equals("대기중");
		model.addObject("outputJson", JSONArray.fromObject(OutputHelper.manager.getOutputs(project)));
		model.addObject("issueJson", JSONArray.fromObject(IssueHelper.manager.getIssues(project)));
		model.addObject("ganttJson", ProjectHelper.manager.load(oid));

		model.addObject("toid", toid);
		model.addObject("oid", oid);
		model.addObject("list", list);
		model.addObject("project", project);
		model.addObject("isProgress", isProgress);
		model.addObject("isComplete", isComplete);
		model.addObject("isReady", isReady);
		model.setViewName("popup:/project/project-view");
		return model;
	}

	@RequestMapping(value = "/projectView", method = RequestMethod.GET)
	public ModelAndView projectView(@RequestParam String oid) throws Exception {
		ModelAndView model = new ModelAndView();
		Project project = (Project) CommonUtils.persistable(oid);
		ProjectDTO dto = new ProjectDTO(project);
		boolean isPM = ProjectHelper.manager.isPM(project);
		model.addObject("dto", dto);
		model.addObject("isPM", isPM);
		model.setViewName("popup:/project/projectView");
		return model;
	}

	@RequestMapping(value = "/projectTaskView", method = RequestMethod.GET)
	public ModelAndView projectTaskView(@RequestParam String oid) throws Exception {
		ModelAndView model = new ModelAndView();
		Task task = (Task) CommonUtils.persistable(oid);
		TaskDTO dto = new TaskDTO(task);
		boolean isPM = ProjectHelper.manager.isPM(task.getProject());
		model.addObject("dto", dto);
		model.addObject("dto", dto);
		model.addObject("isPM", isPM);
		model.setViewName("popup:/project/projectTaskView");
		return model;
	}

	@RequestMapping(value = "/my", method = RequestMethod.GET)
	public ModelAndView my() throws Exception {
		ModelAndView model = new ModelAndView();
		model.setViewName("/jsp/project/project-my.jsp");
		return model;
	}

	@RequestMapping(value = "/my", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> my(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result = ProjectHelper.manager.my(params);
			result.put("result", true);
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
			ProjectHelper.service.saveTree(params);
			result.put("result", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/complete", method = RequestMethod.GET)
	public Map<String, Object> complete(@RequestParam String oid) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			ProjectHelper.service.complete(oid);
			result.put("result", true);
			result.put("msg", "프로젝트가 완료 되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/start", method = RequestMethod.GET)
	public Map<String, Object> start(@RequestParam String oid) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			ProjectHelper.service.start(oid);
			result.put("result", true);
			result.put("msg", "프로젝트가 시작 되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}
}
