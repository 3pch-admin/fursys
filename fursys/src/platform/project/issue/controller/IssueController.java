package platform.project.issue.controller;

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
import platform.project.issue.entity.Issue;
import platform.project.issue.entity.IssueDTO;
import platform.project.issue.service.IssueHelper;
import platform.util.CommonUtils;

@Controller
@RequestMapping(value = "/issue/**")
public class IssueController {

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam String oid) throws Exception {
		ModelAndView model = new ModelAndView();
		ArrayList<BaseCode> issueTypes = BaseCodeHelper.manager.getBaseCodeByCodeType("ISSUETYPE");
		model.addObject("issueTypes", issueTypes);
		model.addObject("oid", oid);
		model.setViewName("popup:/project/issue/issue-create");
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public Map<String, Object> create(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Issue issue = IssueHelper.service.create(params);
			result.put("result", true);
			result.put("msg", issue.getName() + " 이슈가 등록 되었습니다.");
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
		ArrayList<BaseCode> issueTypes = BaseCodeHelper.manager.getBaseCodeByCodeType("ISSUETYPE");
		model.addObject("issueTypes", issueTypes);
		model.setViewName("/jsp/project/issue/issue-list.jsp");
		return model;
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> list(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result = IssueHelper.manager.list(params);
			result.put("result", true);
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
		Issue issue = (Issue) CommonUtils.persistable(oid);
		IssueDTO dto = new IssueDTO(issue);
		model.addObject("dto", dto);
		model.setViewName("popup:/project/issue/issue-view");
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public Map<String, Object> delete(@RequestParam String oid) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Issue issue = IssueHelper.service.delete(oid);
			result.put("result", true);
			result.put("msg", issue.getName() + " 이슈가 삭제 되었습니다.");
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
			IssueHelper.service.delete(params);
			result.put("result", true);
			result.put("msg", "이슈가 삭제 되었습니다.");
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
		ArrayList<BaseCode> issueTypes = BaseCodeHelper.manager.getBaseCodeByCodeType("ISSUETYPE");
		Issue issue = (Issue) CommonUtils.persistable(oid);
		IssueDTO dto = new IssueDTO(issue);
		model.addObject("dto", dto);
		model.addObject("issueTypes", issueTypes);
		model.setViewName("popup:/project/issue/issue-modify");
		return model;
	}
}
