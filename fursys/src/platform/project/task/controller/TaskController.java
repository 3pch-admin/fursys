
package platform.project.task.controller;

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
import platform.doc.entity.DocumentDTO;
import platform.project.task.entity.ParentTaskChildTaskLink;
import platform.project.task.entity.Task;
import platform.project.task.service.TaskHelper;
import platform.raonk.entity.Raonk;
import platform.raonk.service.RaonkHelper;
import platform.util.CommonUtils;
import wt.doc.WTDocument;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;

@Controller
@RequestMapping(value = "/task/**")
public class TaskController {

	@RequestMapping(value = "/addPreTask", method = RequestMethod.GET)
	public ModelAndView addPreTask(@RequestParam String oid) throws Exception {
		ModelAndView model = new ModelAndView();
		model.addObject("oid", oid);
		model.setViewName("popup:/project/task/addPreTask");
		return model;
	}

	@RequestMapping(value = "/addPostTask", method = RequestMethod.GET)
	public ModelAndView addPostTask(@RequestParam String oid) throws Exception {
		ModelAndView model = new ModelAndView();
		model.addObject("oid", oid);
		model.setViewName("popup:/project/task/addPostTask");
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/pre", method = RequestMethod.POST)
	public Map<String, Object> pre(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			TaskHelper.service.pre(params);
			result.put("result", true);
//			result.put("msg", doc.getName() + " 문서가 등록 되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/disconnect", method = RequestMethod.POST)
	public Map<String, Object> disconnect(@RequestBody Map<String, Object> params) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			TaskHelper.service.disconnect(params);
			result.put("msg", "선행태스크 연결을 삭제 하였습니다.");
			result.put("result", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/start", method = RequestMethod.POST)
	public Map<String, Object> start(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Task t = (Task) CommonUtils.persistable((String) params.get("oid"));
			ArrayList<Task> pres = TaskHelper.manager.getPreTasks(t);
			for (Task pre : pres) {
				if (!pre.getState().equals(TaskHelper.COMPLETE)) {
					result.put("result", true);
					result.put("msg", "선행태스크[" + pre.getName().trim() + "]가 완료 되지 않았습니다.");
					return result;
				}
			}

			Task task = TaskHelper.service.start(params);

			result.put("result", true);
			result.put("msg", task.getName() + " 태스크가 시작 되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/stop", method = RequestMethod.POST)
	public Map<String, Object> stop(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Task task = TaskHelper.service.stop(params);
			result.put("result", true);
			result.put("msg", task.getName() + " 태스크가 중단 되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/complete", method = RequestMethod.POST)
	public Map<String, Object> complete(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Task task = TaskHelper.service.complete(params);
			result.put("result", true);
			result.put("msg", task.getName() + " 태스크가 완료 되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/progress", method = RequestMethod.POST)
	public Map<String, Object> progress(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			TaskHelper.service.progress(params);
			result.put("result", true);
			result.put("msg", "진행율이 저장 되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/role", method = RequestMethod.POST)
	public Map<String, Object> role(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			TaskHelper.service.role(params);
			result.put("result", true);
			result.put("msg", "지정한 담당자들이 저장 되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@RequestMapping(value = "/my", method = RequestMethod.GET)
	public ModelAndView my() throws Exception {
		ModelAndView model = new ModelAndView();
		model.setViewName("/jsp/project/task/task-my.jsp");
		return model;
	}

	@RequestMapping(value = "/my", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> my(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result = TaskHelper.manager.my(params);
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
		Task task = (Task) CommonUtils.persistable(oid);
		boolean isLast = false;
		QueryResult result = PersistenceHelper.manager.navigate(task, "childTask", ParentTaskChildTaskLink.class);
		isLast = result.size() == 0;
		model.addObject("isLast", isLast);
		model.addObject("task", task);
		model.addObject("oid", oid);
		model.setViewName("popup:/project/task/task-modify");
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public Map<String, Object> modify(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Task task = TaskHelper.service.modify(params);
			result.put("result", true);
			result.put("msg", task.getName() + " 태스크가 수정 되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}
}
