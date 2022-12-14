
package platform.echange.ecr.controller;

import java.util.ArrayList;
import java.util.HashMap;
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
import platform.echange.ecr.entity.ECR;
import platform.echange.ecr.entity.ECRDTO;
import platform.echange.ecr.service.ECRHelper;
import platform.util.CommonUtils;

@Controller
@RequestMapping(value = "/ecr/*")
public class ECRController {

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() throws Exception {
		ArrayList<BaseCode> brand = BaseCodeHelper.manager.getBaseCodeByCodeType("BRAND"); // 브랜드
		ArrayList<BaseCode> company = BaseCodeHelper.manager.getBaseCodeByCodeType("COMPANY"); // 회사
		ArrayList<BaseCode> reqType = BaseCodeHelper.manager.getBaseCodeByCodeType("ECR_TYPE");
		ModelAndView model = new ModelAndView();
		model.addObject("brand", brand);
		model.addObject("company", company);
		model.addObject("reqType", reqType);
		model.setViewName("/jsp/echange/ecr/ecr-list.jsp");
		return model;
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> list(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result = ECRHelper.manager.list(params);
			result.put("result", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() throws Exception {
		ModelAndView model = new ModelAndView();
		ArrayList<BaseCode> company = BaseCodeHelper.manager.getBaseCodeByCodeType("COMPANY");
		ArrayList<BaseCode> brand = BaseCodeHelper.manager.getBaseCodeByCodeType("BRAND");
		// 요청유형
		ArrayList<BaseCode> reqType = BaseCodeHelper.manager.getBaseCodeByCodeType("ECR_TYPE");
		model.addObject("company", company);
		model.addObject("brand", brand);
		model.addObject("reqType", reqType);
		model.setViewName("popup:/echange/ecr/ecr-create");
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public Map<String, Object> create(@RequestBody ECRDTO params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			ECR ecr = ECRHelper.service.create(params);
			result.put("result", true);
			result.put("msg", ecr.getName() + " ECR이 등록 되었습니다.");
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
			ECR ecr = ECRHelper.service.delete(oid);
			result.put("result", true);
			result.put("msg", ecr.getName() + " ECR이 삭제 되었습니다.");
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
		ECR ecr = (ECR) CommonUtils.persistable(oid);
		ECRDTO dto = new ECRDTO(ecr);
		model.addObject("dto", dto);
		model.setViewName("popup:/echange/ecr/ecr-view");
		return model;
	}

	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public ModelAndView modify(@RequestParam String oid) throws Exception {
		ModelAndView model = new ModelAndView();
		ArrayList<BaseCode> company = BaseCodeHelper.manager.getBaseCodeByCodeType("COMPANY");
		ArrayList<BaseCode> brand = BaseCodeHelper.manager.getBaseCodeByCodeType("BRAND");
		// 요청유형
		ArrayList<BaseCode> reqType = BaseCodeHelper.manager.getBaseCodeByCodeType("ECR_TYPE");
		ECR ecr = (ECR) CommonUtils.persistable(oid);
		ECRDTO dto = new ECRDTO(ecr);
		model.addObject("dto", dto);
		model.addObject("company", company);
		model.addObject("brand", brand);
		model.addObject("reqType", reqType);
		model.setViewName("popup:/echange/ecr/ecr-modify");
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public Map<String, Object> modify(@RequestBody ECRDTO params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			ECR ecr = ECRHelper.service.modify(params);
			result.put("result", true);
			result.put("msg", ecr.getName() + "ECR이 수정 되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}
}
