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

import platform.tree.entity.CombinationDTO;
import platform.tree.entity.EdgeDTO;
import platform.tree.entity.MaterialInfoDTO;
import platform.tree.entity.SurfaceDTO;
import platform.tree.entity.TreatmentDTO;
import platform.tree.service.TreeHelper;

@Controller
@RequestMapping(value = "/tree/**")
public class TreeController {

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> list(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		String type = (String) params.get("type");
		try {
			// 조합
			if ("combination".equals(type)) {
				List<CombinationDTO> list = TreeHelper.manager.combination(params);
				result.put("list", list);
				// 엣지
			} else if ("edge".equals(type)) {
				List<EdgeDTO> list = TreeHelper.manager.edge(params);
				result.put("list", list);
				// 표면
			} else if ("surface".equals(type)) {
				List<SurfaceDTO> list = TreeHelper.manager.surface(params);
				result.put("list", list);
				// 처리
			} else if ("treatment".equals(type)) {
				List<TreatmentDTO> list = TreeHelper.manager.treatment(params);
				result.put("list", list);
			} else if ("material".equals(type)) {
				List<MaterialInfoDTO> list = TreeHelper.manager.material(params);
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

	@RequestMapping(value = "/combination", method = RequestMethod.GET)
	public ModelAndView combination() {
		ModelAndView model = new ModelAndView();
		model.setViewName("/jsp/tree/tree-combination.jsp");
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/combination", method = RequestMethod.POST)
	public Map<String, Object> combination(
			@RequestBody HashMap<String, ArrayList<LinkedHashMap<String, Object>>> param) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<CombinationDTO> list = new ArrayList<CombinationDTO>();
		try {
			ArrayList<LinkedHashMap<String, Object>> addRows = param.get("addRows");
			ArrayList<LinkedHashMap<String, Object>> editRows = param.get("editRows");
			ArrayList<LinkedHashMap<String, Object>> removeRows = param.get("removeRows");
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			ArrayList<CombinationDTO> addList = new ArrayList<>();
			for (LinkedHashMap<String, Object> map : addRows) {
				CombinationDTO dto = mapper.convertValue(map, CombinationDTO.class);
				addList.add(dto);
			}
			ArrayList<CombinationDTO> editList = new ArrayList<>();
			for (LinkedHashMap<String, Object> map : editRows) {
				CombinationDTO dto = mapper.convertValue(map, CombinationDTO.class);
				editList.add(dto);
			}
			ArrayList<CombinationDTO> removeList = new ArrayList<>();
			for (LinkedHashMap<String, Object> map : removeRows) {
				CombinationDTO dto = mapper.convertValue(map, CombinationDTO.class);
				removeList.add(dto);
			}
			HashMap<String, List<CombinationDTO>> paramsMap = new HashMap<>();
			paramsMap.put("addRows", addList);
			paramsMap.put("editRows", editList);
			paramsMap.put("removeRows", removeList);
			TreeHelper.service.combination(paramsMap);
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

	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public ModelAndView info() {
		ModelAndView model = new ModelAndView();
		model.setViewName("/jsp/tree/tree-info.jsp");
		return model;
	}

	@RequestMapping(value = "/edge", method = RequestMethod.GET)
	public ModelAndView edge() {
		ModelAndView model = new ModelAndView();
		model.setViewName("/jsp/tree/tree-edge.jsp");
		return model;
	}

	@RequestMapping(value = "/surface", method = RequestMethod.GET)
	public ModelAndView surface() {
		ModelAndView model = new ModelAndView();
		model.setViewName("/jsp/tree/tree-surface.jsp");
		return model;
	}

	@RequestMapping(value = "/treatment", method = RequestMethod.GET)
	public ModelAndView treatment() {
		ModelAndView model = new ModelAndView();
		model.setViewName("/jsp/tree/tree-treatment.jsp");
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/edge", method = RequestMethod.POST)
	public Map<String, Object> edge(@RequestBody HashMap<String, ArrayList<LinkedHashMap<String, Object>>> param) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<EdgeDTO> list = new ArrayList<EdgeDTO>();
		try {
			ArrayList<LinkedHashMap<String, Object>> addRows = param.get("addRows");
			ArrayList<LinkedHashMap<String, Object>> editRows = param.get("editRows");
			ArrayList<LinkedHashMap<String, Object>> removeRows = param.get("removeRows");
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			ArrayList<EdgeDTO> addList = new ArrayList<>();
			for (LinkedHashMap<String, Object> map : addRows) {
				EdgeDTO dto = mapper.convertValue(map, EdgeDTO.class);
				addList.add(dto);
			}
			ArrayList<EdgeDTO> editList = new ArrayList<>();
			for (LinkedHashMap<String, Object> map : editRows) {
				EdgeDTO dto = mapper.convertValue(map, EdgeDTO.class);
				editList.add(dto);
			}
			ArrayList<EdgeDTO> removeList = new ArrayList<>();
			for (LinkedHashMap<String, Object> map : removeRows) {
				EdgeDTO dto = mapper.convertValue(map, EdgeDTO.class);
				removeList.add(dto);
			}
			HashMap<String, List<EdgeDTO>> paramsMap = new HashMap<>();
			paramsMap.put("addRows", addList);
			paramsMap.put("editRows", editList);
			paramsMap.put("removeRows", removeList);
			TreeHelper.service.edge(paramsMap);
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
	@RequestMapping(value = "/surface", method = RequestMethod.POST)
	public Map<String, Object> surface(
			@RequestBody HashMap<String, ArrayList<LinkedHashMap<String, Object>>> param) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<SurfaceDTO> list = new ArrayList<SurfaceDTO>();
		try {
			ArrayList<LinkedHashMap<String, Object>> addRows = param.get("addRows");
			ArrayList<LinkedHashMap<String, Object>> editRows = param.get("editRows");
			ArrayList<LinkedHashMap<String, Object>> removeRows = param.get("removeRows");
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			ArrayList<SurfaceDTO> addList = new ArrayList<>();
			for (LinkedHashMap<String, Object> map : addRows) {
				SurfaceDTO dto = mapper.convertValue(map, SurfaceDTO.class);
				addList.add(dto);
			}
			ArrayList<SurfaceDTO> editList = new ArrayList<>();
			for (LinkedHashMap<String, Object> map : editRows) {
				SurfaceDTO dto = mapper.convertValue(map, SurfaceDTO.class);
				editList.add(dto);
			}
			ArrayList<SurfaceDTO> removeList = new ArrayList<>();
			for (LinkedHashMap<String, Object> map : removeRows) {
				SurfaceDTO dto = mapper.convertValue(map, SurfaceDTO.class);
				removeList.add(dto);
			}
			HashMap<String, List<SurfaceDTO>> paramsMap = new HashMap<>();
			paramsMap.put("addRows", addList);
			paramsMap.put("editRows", editList);
			paramsMap.put("removeRows", removeList);
			TreeHelper.service.surface(paramsMap);
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
	@RequestMapping(value = "/info", method = RequestMethod.POST)
	public Map<String, Object> material(@RequestBody HashMap<String, ArrayList<LinkedHashMap<String, Object>>> param) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<MaterialInfoDTO> list = new ArrayList<MaterialInfoDTO>();
		try {
			ArrayList<LinkedHashMap<String, Object>> addRows = param.get("addRows");
			ArrayList<LinkedHashMap<String, Object>> editRows = param.get("editRows");
			ArrayList<LinkedHashMap<String, Object>> removeRows = param.get("removeRows");
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			ArrayList<MaterialInfoDTO> addList = new ArrayList<>();
			for (LinkedHashMap<String, Object> map : addRows) {
				MaterialInfoDTO dto = mapper.convertValue(map, MaterialInfoDTO.class);
				addList.add(dto);
			}
			ArrayList<MaterialInfoDTO> editList = new ArrayList<>();
			for (LinkedHashMap<String, Object> map : editRows) {
				MaterialInfoDTO dto = mapper.convertValue(map, MaterialInfoDTO.class);
				editList.add(dto);
			}
			ArrayList<MaterialInfoDTO> removeList = new ArrayList<>();
			for (LinkedHashMap<String, Object> map : removeRows) {
				MaterialInfoDTO dto = mapper.convertValue(map, MaterialInfoDTO.class);
				removeList.add(dto);
			}
			HashMap<String, List<MaterialInfoDTO>> paramsMap = new HashMap<>();
			paramsMap.put("addRows", addList);
			paramsMap.put("editRows", editList);
			paramsMap.put("removeRows", removeList);
			TreeHelper.service.material(paramsMap);
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
	@RequestMapping(value = "/treatment", method = RequestMethod.POST)
	public Map<String, Object> treatment(
			@RequestBody HashMap<String, ArrayList<LinkedHashMap<String, Object>>> param) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<TreatmentDTO> list = new ArrayList<TreatmentDTO>();
		try {
			ArrayList<LinkedHashMap<String, Object>> addRows = param.get("addRows");
			ArrayList<LinkedHashMap<String, Object>> editRows = param.get("editRows");
			ArrayList<LinkedHashMap<String, Object>> removeRows = param.get("removeRows");
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			ArrayList<TreatmentDTO> addList = new ArrayList<>();
			for (LinkedHashMap<String, Object> map : addRows) {
				TreatmentDTO dto = mapper.convertValue(map, TreatmentDTO.class);
				addList.add(dto);
			}
			ArrayList<TreatmentDTO> editList = new ArrayList<>();
			for (LinkedHashMap<String, Object> map : editRows) {
				TreatmentDTO dto = mapper.convertValue(map, TreatmentDTO.class);
				editList.add(dto);
			}
			ArrayList<TreatmentDTO> removeList = new ArrayList<>();
			for (LinkedHashMap<String, Object> map : removeRows) {
				TreatmentDTO dto = mapper.convertValue(map, TreatmentDTO.class);
				removeList.add(dto);
			}
			HashMap<String, List<TreatmentDTO>> paramsMap = new HashMap<>();
			paramsMap.put("addRows", addList);
			paramsMap.put("editRows", editList);
			paramsMap.put("removeRows", removeList);
			TreeHelper.service.treatment(paramsMap);
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
