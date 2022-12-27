package platform.dtmg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/dtmg/**")
public class DtmgController {

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() throws Exception {
		ModelAndView model = new ModelAndView();
		model.setViewName("/jsp/dtmg/dtmg-list.jsp");
		return model;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView model = new ModelAndView();
		model.setViewName("popup:/dtmg/dtmg-create");
		return model;
	}
}
