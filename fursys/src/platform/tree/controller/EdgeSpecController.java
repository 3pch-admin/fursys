package platform.tree.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tika.Tika;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import platform.tree.entity.EMaterialDTO;
import platform.tree.entity.EShapeDTO;
import platform.tree.service.EdgeSpecHelper;

@Controller
@RequestMapping(value = "/edgespec/**")
public class EdgeSpecController {

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> list(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		String type = (String) params.get("type");
		try {
			if("ematerial".equals(type)) {
				List<EMaterialDTO> list = EdgeSpecHelper.manager.ematerial(params);
				result.put("list", list);
			} else if ("eshape".equals(type)) {
				List<EShapeDTO> list = EdgeSpecHelper.manager.eshape(params);
				result.put("list", list);
			}
			result.put("result", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result",  false);
			result.put("msg",  e.toString());
		}
		return result;
	}
	
	@RequestMapping(value="/ematerial", method = RequestMethod.GET)
	public ModelAndView ematerial() {
		ModelAndView model = new ModelAndView();
		model.setViewName("/jsp/tree/edgeSpec-mat.jsp");
		return model;
	}
	
	@ResponseBody
	@RequestMapping(value = "/ematerial", method = RequestMethod.POST)
	public Map<String, Object> ematerial(@RequestBody HashMap<String, ArrayList<LinkedHashMap<String, Object>>> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<EMaterialDTO> list = new ArrayList<EMaterialDTO>();
		try {
			ArrayList<LinkedHashMap<String, Object>> addRows = params.get("addRows");
			ArrayList<LinkedHashMap<String, Object>> editRows = params.get("editRows");
			ArrayList<LinkedHashMap<String, Object>> removeRows = params.get("removeRows");
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			
			ArrayList<EMaterialDTO> addList = new ArrayList<>();
			for(LinkedHashMap<String,Object> map : addRows) {
				EMaterialDTO dto = mapper.convertValue(map, EMaterialDTO.class);
				addList.add(dto);
			}
			ArrayList<EMaterialDTO> editList = new ArrayList<>();
			for(LinkedHashMap<String, Object> map : editRows) {
				EMaterialDTO dto = mapper.convertValue(map,  EMaterialDTO.class);
				editList.add(dto);
			}
			ArrayList<EMaterialDTO> removeList  = new ArrayList<>();
			for(LinkedHashMap<String, Object> map : removeRows) {
				EMaterialDTO dto = mapper.convertValue(map,  EMaterialDTO.class);
				removeList.add(dto);
			}
			HashMap<String, List<EMaterialDTO>> paramsMap = new HashMap<>();
			paramsMap.put("addRows", addList);
			paramsMap.put("editRows", editList);
			paramsMap.put("removeRows", removeList);
			EdgeSpecHelper.service.ematerial(paramsMap);
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
	
	@RequestMapping(value ="/eshape", method = RequestMethod.GET)
	public ModelAndView eshape() {
		ModelAndView model = new ModelAndView();
		model.setViewName("/jsp/tree/edgeSpec-shape.jsp");
		return model;
	}
	
	@ResponseBody
	@RequestMapping(value = "/eshape", method = RequestMethod.POST)
	public Map<String, Object> eshape(@RequestBody HashMap<String, ArrayList<LinkedHashMap<String, Object>>> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<EShapeDTO> list = new ArrayList<EShapeDTO>();
		try {
			ArrayList<LinkedHashMap<String, Object>> addRows = params.get("addRows");
			ArrayList<LinkedHashMap<String, Object>> editRows = params.get("editRows");
			ArrayList<LinkedHashMap<String, Object>> removeRows = params.get("removeRows");
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			ArrayList<EShapeDTO> addList = new ArrayList<>();
			for(LinkedHashMap<String, Object> map : addRows) {
				EShapeDTO dto = mapper.convertValue(map, EShapeDTO.class);
				addList.add(dto);
			}
			ArrayList<EShapeDTO> editList = new ArrayList<>();
			for(LinkedHashMap<String, Object> map : editRows) {
				EShapeDTO dto = mapper.convertValue(map,  EShapeDTO.class);
				editList.add(dto);
			}
			ArrayList<EShapeDTO> removeList = new ArrayList<>();
			for(LinkedHashMap<String, Object> map : removeRows) {
				EShapeDTO dto = mapper.convertValue(map,  EShapeDTO.class);
				removeList.add(dto);
			}
			HashMap<String, List<EShapeDTO>> paramsMap = new HashMap<>();
			paramsMap.put("addRows", addList);
			paramsMap.put("editRows", editList);
			paramsMap.put("removeRows", removeList);
			EdgeSpecHelper.service.eshape(paramsMap);
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
