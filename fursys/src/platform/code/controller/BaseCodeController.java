package platform.code.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import platform.code.entity.BaseCodeDTO;
import platform.code.entity.BaseCodeType;
import platform.code.service.BaseCodeHelper;
import platform.tree.entity.EdgeDTO;
import platform.tree.service.TreeHelper;

@Controller
@RequestMapping(value = "/baseCode/**")
public class BaseCodeController {
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() throws Exception {
		BaseCodeType[] types = BaseCodeType.getBaseCodeTypeSet();
		JSONArray codeTypes = new JSONArray();
		for (BaseCodeType t : types) {
			JSONObject node = new JSONObject();
			node.put("label", t.getDisplay());
			node.put("value", t.toString());
			codeTypes.put(node);
		}
		ModelAndView model = new ModelAndView();
		model.addObject("types", types);
		model.addObject("codeTypes", codeTypes);
		model.setViewName("/jsp/admin/baseCode/baseCode-list.jsp");
		return model;
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> list(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<BaseCodeDTO> list = new ArrayList<BaseCodeDTO>();
		try {
			list = BaseCodeHelper.manager.list(params);
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
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public Map<String, Object> save(@RequestBody HashMap<String, ArrayList<LinkedHashMap<String, Object>>> param) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<BaseCodeDTO> list = new ArrayList<BaseCodeDTO>();
		try {
			ArrayList<LinkedHashMap<String, Object>> addRows = param.get("addRows");
			ArrayList<LinkedHashMap<String, Object>> editRows = param.get("editRows");
			ArrayList<LinkedHashMap<String, Object>> removeRows = param.get("removeRows");
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			ArrayList<BaseCodeDTO> addList = new ArrayList<>();
			for (LinkedHashMap<String, Object> map : addRows) {
				BaseCodeDTO dto = mapper.convertValue(map, BaseCodeDTO.class);
				addList.add(dto);
			}
			ArrayList<BaseCodeDTO> editList = new ArrayList<>();
			for (LinkedHashMap<String, Object> map : editRows) {
				BaseCodeDTO dto = mapper.convertValue(map, BaseCodeDTO.class);
				editList.add(dto);
			}
			ArrayList<BaseCodeDTO> removeList = new ArrayList<>();
			for (LinkedHashMap<String, Object> map : removeRows) {
				BaseCodeDTO dto = mapper.convertValue(map, BaseCodeDTO.class);
				removeList.add(dto);
			}
			HashMap<String, List<BaseCodeDTO>> paramsMap = new HashMap<>();
			paramsMap.put("addRows", addList);
			paramsMap.put("editRows", editList);
			paramsMap.put("removeRows", removeList);
			BaseCodeHelper.service.save(paramsMap);
			result.put("result", true);
			result.put("msg", "저장 되었습니다.");
			result.put("list", list);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@RequestMapping(value = "/popup", method = RequestMethod.GET)
	public ModelAndView popup(@RequestParam String codeType) throws Exception {
		BaseCodeType[] types = BaseCodeType.getBaseCodeTypeSet();
		JSONArray codeTypes = new JSONArray();
		for (BaseCodeType t : types) {
			JSONObject node = new JSONObject();
			node.put("label", t.getDisplay());
			node.put("value", t.toString());
			codeTypes.put(node);
		}
		ModelAndView model = new ModelAndView();
		model.addObject("types", types);
		model.addObject("codeTypes", codeTypes);
		model.addObject("codeType", codeType);
		model.setViewName("popup:/admin/baseCode/baseCode-popup-list");
		return model;
	}
}
