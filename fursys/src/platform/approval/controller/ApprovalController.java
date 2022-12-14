package platform.approval.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import platform.approval.entity.ApprovalDTO;
import platform.approval.entity.ApprovalLine;
import platform.approval.service.ApprovalHelper;
import platform.util.CommonUtils;
import wt.org.WTUser;
import wt.session.SessionHelper;

@Controller
@RequestMapping(value = "/app/**")
public class ApprovalController {

	@RequestMapping(value = "/receiveSave")
	@ResponseBody
	public Map<String, Object> receiveSave(@RequestBody Map<String, Object> params) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			ApprovalHelper.service.receiveSave(params);
			result.put("result", true);
			result.put("msg", "결재가 기안처리 되었습니다.");
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
		try {
			ApprovalHelper.service.save(params);
			result.put("result", true);
			result.put("msg", "결재가 기안처리 되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam String type) {
		ModelAndView model = new ModelAndView();

		if ("A".equals(type)) {
			model.setViewName("/jsp/approval/complete-list.jsp");
		} else if ("B".equals(type)) {
			model.setViewName("/jsp/approval/ing-list.jsp");
		} else if ("C".equals(type)) {
			model.setViewName("/jsp/approval/approval-list.jsp");
		} else if ("D".equals(type)) {
			model.setViewName("/jsp/approval/receive-list.jsp");
		} else if ("E".equals(type)) {
			model.setViewName("/jsp/approval/return-list.jsp");
		}
		return model;
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> list(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		String type = (String) params.get("type");
		try {

			if ("A".equals(type)) {
				result = ApprovalHelper.manager.complete(params);
			} else if ("B".equals(type)) {

			} else if ("C".equals(type)) {
				result = ApprovalHelper.manager.app(params);
			} else if ("D".equals(type)) {
				result = ApprovalHelper.manager.receive(params);
			} else if ("E".equals(type)) {
				result = ApprovalHelper.manager.rtn(params);
			}
			result.put("result", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@RequestMapping(value = "/popup", method = RequestMethod.GET)
	public ModelAndView popup() throws Exception {
		ModelAndView model = new ModelAndView();
		model.setViewName("popup:/approval/addLine");
		return model;
	}

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam String oid) throws Exception {
		ModelAndView model = new ModelAndView();
		ApprovalLine line = (ApprovalLine) CommonUtils.persistable(oid);
		ApprovalDTO dto = new ApprovalDTO(line);
		model.addObject("dto", dto);
		model.setViewName("popup:/approval/approval-view");
		return model;
	}

	@RequestMapping(value = "/rtn")
	@ResponseBody
	public Map<String, Object> rtn(@RequestBody Map<String, Object> params) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			ApprovalHelper.service.rtn(params);
			result.put("result", true);
			result.put("msg", "반려 되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@RequestMapping(value = "/approval")
	@ResponseBody
	public Map<String, Object> approval(@RequestBody Map<String, Object> params) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			ApprovalHelper.service.approval(params);
			result.put("result", true);
			result.put("msg", "승인 되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@RequestMapping(value = "/confirm")
	@ResponseBody
	public Map<String, Object> confirm(@RequestBody Map<String, Object> params) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			ApprovalHelper.service.confirm(params);
			result.put("result", true);
			result.put("msg", "확인 되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register(@RequestParam String oid) throws Exception {
		ModelAndView model = new ModelAndView();
		WTUser sessionUser = (WTUser) SessionHelper.manager.getPrincipal();
		model.addObject("oid", oid);
		model.addObject("sessionUser", sessionUser);
		model.setViewName("popup:/approval/register");
		return model;
	}

	@RequestMapping(value = "/receiveRegister", method = RequestMethod.GET)
	public ModelAndView receiveRegister(@RequestParam String oid) throws Exception {
		ModelAndView model = new ModelAndView();
		WTUser sessionUser = (WTUser) SessionHelper.manager.getPrincipal();
		ApprovalLine line = (ApprovalLine) CommonUtils.persistable(oid);
		model.addObject("oid", line.getMaster().getPersist().getPersistInfo().getObjectIdentifier().getStringValue());
		model.addObject("line", oid);
		model.addObject("sessionUser", sessionUser);
		model.setViewName("popup:/approval/receive-register");
		return model;
	}

	@RequestMapping(value = "/addUser", method = RequestMethod.GET)
	public ModelAndView addUser(@RequestParam String index, @RequestParam String type, @RequestParam String name,
			@RequestParam String callBack) {
		ModelAndView model = new ModelAndView();
		model.addObject("index", index);
		model.addObject("type", type);
		model.addObject("name", name);
		model.addObject("callBack", callBack);
		model.setViewName("popup:/approval/user-addUser");
		return model;
	}
}
