package platform.echange.ecn.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import platform.code.entity.BaseCode;
import platform.code.service.BaseCodeHelper;
import platform.echange.ecn.entity.ECN;
import platform.echange.ecn.entity.ECNColumns;
import platform.echange.ecn.entity.ECNDTO;
import platform.echange.ecn.service.ECNHelper;
import platform.util.CommonUtils;

@Controller
@RequestMapping(value = "/ecn/**")
public class ECNController {

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() throws Exception {
		ModelAndView model = new ModelAndView();
		ArrayList<BaseCode> company = BaseCodeHelper.manager.getBaseCodeByCodeType("COMPANY");
		ArrayList<BaseCode> brand = BaseCodeHelper.manager.getBaseCodeByCodeType("BRAND");
		ArrayList<BaseCode> notiType = BaseCodeHelper.manager.getBaseCodeByCodeType("ECO_NOTI_TYPE");
		ArrayList<BaseCode> plant = BaseCodeHelper.manager.getBaseCodeByCodeType("PLANT");
		ArrayList<BaseCode> applyTime = BaseCodeHelper.manager.getBaseCodeByCodeType("ECO_APPLY_TIME");

		model.addObject("company", company);
		model.addObject("brand", brand);
		model.addObject("plant", plant);
		model.addObject("notiTypes", notiType);
		model.addObject("applyTimes", applyTime);
		model.setViewName("/jsp/echange/ecn/ecn-list.jsp");
		return model;
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> list(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result = ECNHelper.manager.list(params);
			result.put("result", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public Map<String, Object> delete(@RequestParam String oid) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			ECN ecn = ECNHelper.service.delete(oid);
			result.put("result", true);
			result.put("msg", ecn.getName() + " ECN이 삭제 되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@RequestMapping(value = "create", method = RequestMethod.GET)
	public ModelAndView create() throws Exception {
		ModelAndView model = new ModelAndView();
		ArrayList<BaseCode> company = BaseCodeHelper.manager.getBaseCodeByCodeType("COMPANY");
		ArrayList<BaseCode> brand = BaseCodeHelper.manager.getBaseCodeByCodeType("BRAND");
		ArrayList<BaseCode> plant = BaseCodeHelper.manager.getBaseCodeByCodeType("PLANT");
		ArrayList<BaseCode> notiType = BaseCodeHelper.manager.getBaseCodeByCodeType("ECO_NOTI_TYPE");
		ArrayList<BaseCode> applyTime = BaseCodeHelper.manager.getBaseCodeByCodeType("ECO_APPLY_TIME");

		model.addObject("company", company);
		model.addObject("brand", brand);
		model.addObject("plant", plant);
		model.addObject("notiTypes", notiType);
		model.addObject("applyTimes", applyTime);
		model.setViewName("popup:/echange/ecn/ecn-create");
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public Map<String, Object> create(@RequestBody ECNDTO params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			ECN ecn = ECNHelper.service.create(params);
			result.put("result", true);
			result.put("msg", ecn.getName() + " ECN이 등록 되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam String oid) throws Exception {
		ModelAndView model = new ModelAndView();
		ECN ecn = (ECN) CommonUtils.persistable(oid);
		ECNDTO dto = new ECNDTO(ecn);
		model.addObject("dto", dto);
		model.setViewName("popup:/echange/ecn/ecn-view");
		return model;
	}
}
