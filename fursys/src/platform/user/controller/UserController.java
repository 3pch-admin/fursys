package platform.user.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import platform.attr.entity.AttrDTO;
import platform.code.service.BaseCodeHelper;
import platform.department.service.DepartmentHelper;
import platform.user.entity.UserColumns;
import platform.user.entity.UserInfo;
import platform.user.service.UserHelper;

@Controller
@RequestMapping(value = "/user/**")
public class UserController {

	@RequestMapping(value = "/info")
	@ResponseBody
	public Map<String, Object> info(@RequestBody Map<String, Object> params) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		List<UserInfo> list = new ArrayList<UserInfo>();
		try {
			list = UserHelper.manager.info(params);
			result.put("result", true);
			result.put("info", list);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@RequestMapping(value = "/info", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> info(@RequestParam String oid) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		UserInfo info = null;
		try {
			info = UserHelper.manager.info(oid);
			result.put("result", true);
			result.put("info", info);
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
		model.addObject("list", BaseCodeHelper.manager.getCodeMap("BRAND"));
		model.addObject("dlist", DepartmentHelper.manager.getDeptMap());
		model.setViewName("/jsp/user/user-list.jsp");
		return model;
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> list(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result = UserHelper.manager.list(params);
			result.put("result", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@RequestMapping(value = "/popup", method = RequestMethod.GET)
	public ModelAndView popup(@RequestParam String target) {
		ModelAndView model = new ModelAndView();
		model.addObject("target", target);
		model.setViewName("popup:/user/user-popup-list");
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public Map<String, Object> modify(@RequestBody HashMap<String, ArrayList<LinkedHashMap<String, Object>>> param) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<AttrDTO> list = new ArrayList<AttrDTO>();
		try {
//			ArrayList<LinkedHashMap<String, Object>> addRows = param.get("addRows");
			ArrayList<LinkedHashMap<String, Object>> editRows = param.get("editRows");
//			ArrayList<LinkedHashMap<String, Object>> removeRows = param.get("removeRows");
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//			ArrayList<AttrDTO> addList = new ArrayList<>();
//			for (LinkedHashMap<String, Object> map : addRows) {
//				AttrDTO dto = mapper.convertValue(map, AttrDTO.class);
//				addList.add(dto);
//			}
			ArrayList<UserColumns> editList = new ArrayList<>();
			for (LinkedHashMap<String, Object> map : editRows) {
				UserColumns dto = mapper.convertValue(map, UserColumns.class);
				editList.add(dto);
			}
//			ArrayList<AttrDTO> removeList = new ArrayList<>();
//			for (LinkedHashMap<String, Object> map : removeRows) {
//				AttrDTO dto = mapper.convertValue(map, AttrDTO.class);
//				removeList.add(dto);
//			}
			HashMap<String, List<UserColumns>> paramsMap = new HashMap<>();
//			paramsMap.put("addRows", addList);
			paramsMap.put("editRows", editList);
//			paramsMap.put("removeRows", removeList);
			UserHelper.service.modify(paramsMap);
			result.put("result", true);
			result.put("msg", "수정 되었습니다.");
			result.put("list", list);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}
}
