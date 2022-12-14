package platform.attr.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import platform.attr.entity.AttrDTO;
import platform.attr.service.AttrHelper;

@Controller
@RequestMapping(value = "/attr/**")
public class AttrController {

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView model = new ModelAndView();
		model.setViewName("/jsp/attr/attr-list.jsp");
		return model;
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> list(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<AttrDTO> list = new ArrayList<AttrDTO>();
		try {
			// 조합
			list = AttrHelper.manager.list(params);
			result.put("list", list);
			result.put("result", true);

		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public Map<String, Object> create(@RequestBody HashMap<String, ArrayList<LinkedHashMap<String, Object>>> param) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<AttrDTO> list = new ArrayList<AttrDTO>();
		try {
			ArrayList<LinkedHashMap<String, Object>> addRows = param.get("addRows");
			ArrayList<LinkedHashMap<String, Object>> editRows = param.get("editRows");
			ArrayList<LinkedHashMap<String, Object>> removeRows = param.get("removeRows");
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			ArrayList<AttrDTO> addList = new ArrayList<>();
			for (LinkedHashMap<String, Object> map : addRows) {
				AttrDTO dto = mapper.convertValue(map, AttrDTO.class);
				addList.add(dto);
			}
			ArrayList<AttrDTO> editList = new ArrayList<>();
			for (LinkedHashMap<String, Object> map : editRows) {
				AttrDTO dto = mapper.convertValue(map, AttrDTO.class);
				editList.add(dto);
			}
			ArrayList<AttrDTO> removeList = new ArrayList<>();
			for (LinkedHashMap<String, Object> map : removeRows) {
				AttrDTO dto = mapper.convertValue(map, AttrDTO.class);
				removeList.add(dto);
			}
			HashMap<String, List<AttrDTO>> paramsMap = new HashMap<>();
			paramsMap.put("addRows", addList);
			paramsMap.put("editRows", editList);
			paramsMap.put("removeRows", removeList);
			AttrHelper.service.create(paramsMap);
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
}
