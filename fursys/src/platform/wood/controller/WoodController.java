package platform.wood.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import platform.wood.entity.MaterialDTO;
import platform.wood.entity.ProcessDTO;
import platform.wood.entity.TreatmentDTO;
import platform.wood.service.WoodHelper;

@Controller
@RequestMapping(value = "/wood/**")
public class WoodController {

	@RequestMapping(value = "/material", method = RequestMethod.GET)
	public ModelAndView material() {
		ModelAndView model = new ModelAndView();
		model.setViewName("/jsp/wood/wood-material.jsp");
		return model;
	}

	@RequestMapping(value = "/process", method = RequestMethod.GET)
	public ModelAndView process() {
		ModelAndView model = new ModelAndView();
		model.setViewName("/jsp/wood/wood-process.jsp");
		return model;
	}

	@RequestMapping(value = "/treatment", method = RequestMethod.GET)
	public ModelAndView treatment() {
		ModelAndView model = new ModelAndView();
		model.setViewName("/jsp/wood/wood-treatment.jsp");
		return model;
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> list(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		String type = (String) params.get("type");
		try {
			// 조합
			if ("material".equals(type)) {
				List<MaterialDTO> list = WoodHelper.manager.material(params);
				result.put("list", list);
				// 엣지
			} else if ("treatment".equals(type)) {
				List<TreatmentDTO> list = WoodHelper.manager.treatment(params);
				result.put("list", list);
			} else if ("process".equals(type)) {
				List<ProcessDTO> list = WoodHelper.manager.process(params);
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
}
