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

import platform.tree.entity.EManufacturingDTO;
import platform.tree.entity.EMaterialInfoDTO;
import platform.tree.entity.ESurfaceDTO;
import platform.tree.service.ExcTreeHelper;

@Controller
@RequestMapping(value = "/exctree/**")
public class ExcTreeController {

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> list(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		String type = (String) params.get("type");
		try {
			// 조합
			if ("info".equals(type)) {
				List<EMaterialInfoDTO> list = ExcTreeHelper.manager.ematerial(params);
				result.put("list", list);
				// 엣지
			} else if ("emanufacturing".equals(type)) {
				List<EManufacturingDTO> list = ExcTreeHelper.manager.emanufacturing(params);
				result.put("list", list);
				// 표면
			} else if ("esurface".equals(type)) {
				List<ESurfaceDTO> list = ExcTreeHelper.manager.esurface(params);
				result.put("list", list);
			}
			result.put("result", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public ModelAndView info() {
		ModelAndView model = new ModelAndView();
		model.setViewName("/jsp/tree/exctree-info.jsp");
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/info", method = RequestMethod.POST)
	public Map<String, Object> info(@RequestBody HashMap<String, ArrayList<LinkedHashMap<String, Object>>> param) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<EMaterialInfoDTO> list = new ArrayList<EMaterialInfoDTO>();
		try {
			ArrayList<LinkedHashMap<String, Object>> addRows = param.get("addRows");
			ArrayList<LinkedHashMap<String, Object>> editRows = param.get("editRows");
			ArrayList<LinkedHashMap<String, Object>> removeRows = param.get("removeRows");
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

			ArrayList<EMaterialInfoDTO> addList = new ArrayList<>();
			for (LinkedHashMap<String, Object> map : addRows) {
				EMaterialInfoDTO dto = mapper.convertValue(map, EMaterialInfoDTO.class);
				addList.add(dto);
			}
			ArrayList<EMaterialInfoDTO> editList = new ArrayList<>();
			for (LinkedHashMap<String, Object> map : editRows) {
				EMaterialInfoDTO dto = mapper.convertValue(map, EMaterialInfoDTO.class);
				editList.add(dto);
			}
			ArrayList<EMaterialInfoDTO> removeList = new ArrayList<>();
			for (LinkedHashMap<String, Object> map : removeRows) {
				EMaterialInfoDTO dto = mapper.convertValue(map, EMaterialInfoDTO.class);
				removeList.add(dto);
			}
			HashMap<String, List<EMaterialInfoDTO>> paramsMap = new HashMap<>();
			paramsMap.put("addRows", addList);
			paramsMap.put("editRows", editList);
			paramsMap.put("removeRows", removeList);
			ExcTreeHelper.service.ematerial(paramsMap);
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

	@RequestMapping(value = "/emanufacturing", method = RequestMethod.GET)
	public ModelAndView emanufacturing() {
		ModelAndView model = new ModelAndView();
		model.setViewName("/jsp/tree/exctree-manufacturing.jsp");
		return model;
	}

	@RequestMapping(value = "/esurface", method = RequestMethod.GET)
	public ModelAndView surface() {
		ModelAndView model = new ModelAndView();
		model.setViewName("/jsp/tree/exctree-surface.jsp");
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/emanufacturing", method = RequestMethod.POST)
	public Map<String, Object> emanufacturing(
			@RequestBody HashMap<String, ArrayList<LinkedHashMap<String, Object>>> param) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<EManufacturingDTO> list = new ArrayList<EManufacturingDTO>();
		try {
			ArrayList<LinkedHashMap<String, Object>> addRows = param.get("addRows");
			ArrayList<LinkedHashMap<String, Object>> editRows = param.get("editRows");
			ArrayList<LinkedHashMap<String, Object>> removeRows = param.get("removeRows");
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			ArrayList<EManufacturingDTO> addList = new ArrayList<>();
			for (LinkedHashMap<String, Object> map : addRows) {
				EManufacturingDTO dto = mapper.convertValue(map, EManufacturingDTO.class);
				addList.add(dto);
			}
			ArrayList<EManufacturingDTO> editList = new ArrayList<>();
			for (LinkedHashMap<String, Object> map : editRows) {
				EManufacturingDTO dto = mapper.convertValue(map, EManufacturingDTO.class);
				editList.add(dto);
			}
			ArrayList<EManufacturingDTO> removeList = new ArrayList<>();
			for (LinkedHashMap<String, Object> map : removeRows) {
				EManufacturingDTO dto = mapper.convertValue(map, EManufacturingDTO.class);
				removeList.add(dto);
			}
			HashMap<String, List<EManufacturingDTO>> paramsMap = new HashMap<>();
			paramsMap.put("addRows", addList);
			paramsMap.put("editRows", editList);
			paramsMap.put("removeRows", removeList);
			ExcTreeHelper.service.emanufacturing(paramsMap);
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

	@ResponseBody
	@RequestMapping(value = "/esurface", method = RequestMethod.POST)
	public Map<String, Object> esurface(@RequestBody HashMap<String, ArrayList<LinkedHashMap<String, Object>>> param) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<ESurfaceDTO> list = new ArrayList<ESurfaceDTO>();
		try {
			ArrayList<LinkedHashMap<String, Object>> addRows = param.get("addRows");
			ArrayList<LinkedHashMap<String, Object>> editRows = param.get("editRows");
			ArrayList<LinkedHashMap<String, Object>> removeRows = param.get("removeRows");
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			ArrayList<ESurfaceDTO> addList = new ArrayList<>();
			for (LinkedHashMap<String, Object> map : addRows) {
				ESurfaceDTO dto = mapper.convertValue(map, ESurfaceDTO.class);
				addList.add(dto);
			}
			ArrayList<ESurfaceDTO> editList = new ArrayList<>();
			for (LinkedHashMap<String, Object> map : editRows) {
				ESurfaceDTO dto = mapper.convertValue(map, ESurfaceDTO.class);
				editList.add(dto);
			}
			ArrayList<ESurfaceDTO> removeList = new ArrayList<>();
			for (LinkedHashMap<String, Object> map : removeRows) {
				ESurfaceDTO dto = mapper.convertValue(map, ESurfaceDTO.class);
				removeList.add(dto);
			}
			HashMap<String, List<ESurfaceDTO>> paramsMap = new HashMap<>();
			paramsMap.put("addRows", addList);
			paramsMap.put("editRows", editList);
			paramsMap.put("removeRows", removeList);
			ExcTreeHelper.service.esurface(paramsMap);
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
