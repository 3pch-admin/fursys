package platform.tree.controller;

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

import platform.tree.entity.BomQuantityDTO;
import platform.tree.service.BomQuantityHelper;

@Controller
@RequestMapping(value = "/quantity/**")
public class BomQuantityController {

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView model = new ModelAndView();
		model.setViewName("/jsp/tree/quantity-list.jsp");
		return model;
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> list(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<BomQuantityDTO> list = new ArrayList<BomQuantityDTO>();
		try {
			list = BomQuantityHelper.manager.list(params);
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
		List<BomQuantityDTO> list = new ArrayList<BomQuantityDTO>();
		try {
			ArrayList<LinkedHashMap<String, Object>> addRows = param.get("addRows");
			ArrayList<LinkedHashMap<String, Object>> editRows = param.get("editRows");
			ArrayList<LinkedHashMap<String, Object>> removeRows = param.get("removeRows");
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			ArrayList<BomQuantityDTO> addList = new ArrayList<>();
			for (LinkedHashMap<String, Object> map : addRows) {
				BomQuantityDTO dto = mapper.convertValue(map, BomQuantityDTO.class);
				addList.add(dto);
			}
			ArrayList<BomQuantityDTO> editList = new ArrayList<>();
			for (LinkedHashMap<String, Object> map : editRows) {
				BomQuantityDTO dto = mapper.convertValue(map, BomQuantityDTO.class);
				editList.add(dto);
			}
			ArrayList<BomQuantityDTO> removeList = new ArrayList<>();
			for (LinkedHashMap<String, Object> map : removeRows) {
				BomQuantityDTO dto = mapper.convertValue(map, BomQuantityDTO.class);
				removeList.add(dto);
			}
			HashMap<String, List<BomQuantityDTO>> paramsMap = new HashMap<>();
			paramsMap.put("addRows", addList);
			paramsMap.put("editRows", editList);
			paramsMap.put("removeRows", removeList);
			BomQuantityHelper.service.create(paramsMap);
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
