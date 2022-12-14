package platform.main.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import platform.approval.service.ApprovalHelper;
import platform.message.entity.Message;
import platform.message.service.MessageHelper;

@Controller
public class MainController {

	@RequestMapping(value = "/main")
	public ModelAndView main() {
		ModelAndView model = new ModelAndView();
		model.setViewName("main:/main/*");
		return model;
	}

	@RequestMapping(value = "/header")
	public ModelAndView header() throws Exception {
		ModelAndView model = new ModelAndView();
		ArrayList<Message> messages = MessageHelper.manager.getMessages();
		model.addObject("messages", messages);
		model.setViewName("/jsp/common/layouts/header.jsp");
		return model;
	}

	@RequestMapping(value = "/menu")
	public ModelAndView menu() throws Exception {
		ModelAndView model = new ModelAndView();
		int appCount = ApprovalHelper.manager.getAppCount();
		int receiveCount = ApprovalHelper.manager.getReceiveCount();
		int completeCount = ApprovalHelper.manager.getCompleteCount();
		int rtnCount = ApprovalHelper.manager.getRtnCount();
		model.addObject("rtnCount", rtnCount);
		model.addObject("completeCount", completeCount);
		model.addObject("appCount", appCount);
		model.addObject("receiveCount", receiveCount);
		model.setViewName("/jsp/common/layouts/menu.jsp");
		return model;
	}

	@RequestMapping(value = "/footer")
	public ModelAndView footer() {
		ModelAndView model = new ModelAndView();
		model.setViewName("/jsp/common/layouts/footer.jsp");
		return model;
	}
}
