package platform.partlist.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import platform.ebom.service.EBOMHelper;
import platform.partlist.service.PartlistHelper;

@Controller
@RequestMapping(value = "/partlist/**")
public class PartlistController {

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() throws Exception {
		ModelAndView model = new ModelAndView();
		model.setViewName("/jsp/partlist/partlist-list.jsp");
		return model;
	}

	@RequestMapping(value = "/color", method = RequestMethod.GET)
	public ModelAndView color() throws Exception {
		ModelAndView model = new ModelAndView();
		model.setViewName("popup:/partlist/partlist-color");
		return model;
	}

	@RequestMapping(value = "/seprate", method = RequestMethod.GET)
	public ModelAndView seprate() throws Exception {
		ModelAndView model = new ModelAndView();
		model.setViewName("popup:/partlist/partlist-seprate");
		return model;
	}

	@RequestMapping(value = "/batch", method = RequestMethod.GET)
	public ModelAndView batch() throws Exception {
		ModelAndView model = new ModelAndView();
		model.setViewName("popup:/partlist/partlist-batch");
		return model;
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> list(@RequestBody Map<String, Object> params) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result = PartlistHelper.manager.list(params);
			result.put("result", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView model = new ModelAndView();
		model.setViewName("popup:/partlist/partlist-create");
		return model;
	}
}
