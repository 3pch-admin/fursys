package platform.echange.eco.controller;

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
import platform.echange.eco.entity.ECO;
import platform.echange.eco.entity.ECODTO;
import platform.echange.eco.service.ECOHelper;
import platform.part.entity.PartColumns;
import platform.part.service.PartHelper;
import platform.util.CommonUtils;
import wt.part.QuantityUnit;

@Controller
@RequestMapping(value = "/eco/*")
public class ECOController {

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam String oid) throws Exception {
		ModelAndView model = new ModelAndView();
		ECO eco = (ECO) CommonUtils.persistable(oid);
		ECODTO dto = new ECODTO(eco);
		model.addObject("dto", dto);
		model.setViewName("popup:/echange/eco/eco-view");
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public Map<String, Object> create(@RequestBody ECODTO params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			ECO eco = ECOHelper.service.create(params);
			result.put("result", true);
			result.put("msg", eco.getName() + " ECO가 등록 되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam String ecoType) throws Exception {
		ModelAndView model = new ModelAndView();
		ArrayList<BaseCode> company = BaseCodeHelper.manager.getBaseCodeByCodeType("COMPANY");
		ArrayList<BaseCode> brand = BaseCodeHelper.manager.getBaseCodeByCodeType("BRAND");
		ArrayList<BaseCode> notiTypes = BaseCodeHelper.manager.getBaseCodeByCodeType("ECO_NOTI_TYPE");
		ArrayList<BaseCode> devTypes = BaseCodeHelper.manager.getBaseCodeByCodeType("ECO_DEV_TYPE");
		ArrayList<BaseCode> applyTimes = BaseCodeHelper.manager.getBaseCodeByCodeType("ECO_APPLY_TIME");
		ArrayList<BaseCode> ecoTypes = BaseCodeHelper.manager.getBaseCodeByCodeType("ECO_TYPE");
		ArrayList<BaseCode> lots = BaseCodeHelper.manager.getBaseCodeByCodeType("LOT_MGMT");

		model.addObject("ecoType", ecoType);
		model.addObject("notiTypes", notiTypes);
		model.addObject("lots", lots);
		model.addObject("ecoTypes", ecoTypes);
		model.addObject("devTypes", devTypes);
		model.addObject("applyTimes", applyTimes);
		model.addObject("company", company);
		model.addObject("brand", brand);
		model.setViewName("popup:/echange/eco/eco-create");
		return model;
	}

	@RequestMapping(value = "/design", method = RequestMethod.GET)
	public ModelAndView design() throws Exception {
		ModelAndView model = new ModelAndView();
		ArrayList<BaseCode> ecoTypes = BaseCodeHelper.manager.getBaseCodeByCodeType("ECO_TYPE");
		ArrayList<BaseCode> notiTypes = BaseCodeHelper.manager.getBaseCodeByCodeType("ECO_NOTI_TYPE");
		ArrayList<BaseCode> lots = BaseCodeHelper.manager.getBaseCodeByCodeType("LOT_MGMT");
		ArrayList<BaseCode> applyTimes = BaseCodeHelper.manager.getBaseCodeByCodeType("ECO_APPLY_TIME");
		ArrayList<BaseCode> devTypes = BaseCodeHelper.manager.getBaseCodeByCodeType("ECO_DEV_TYPE");

		model.addObject("notiTypes", notiTypes);
		model.addObject("lots", lots);
		model.addObject("ecoTypes", ecoTypes);
		model.addObject("devTypes", devTypes);
		model.addObject("applyTimes", applyTimes);
		model.setViewName("/jsp/echange/eco/eco-design-list.jsp");
		return model;
	}

	@RequestMapping(value = "/prod", method = RequestMethod.GET)
	public ModelAndView prod() throws Exception {
		ModelAndView model = new ModelAndView();
		ArrayList<BaseCode> ecoTypes = BaseCodeHelper.manager.getBaseCodeByCodeType("ECO_TYPE");
		ArrayList<BaseCode> notiTypes = BaseCodeHelper.manager.getBaseCodeByCodeType("ECO_NOTI_TYPE");
		ArrayList<BaseCode> lots = BaseCodeHelper.manager.getBaseCodeByCodeType("LOT_MGMT");
		ArrayList<BaseCode> applyTimes = BaseCodeHelper.manager.getBaseCodeByCodeType("ECO_APPLY_TIME");
		ArrayList<BaseCode> devTypes = BaseCodeHelper.manager.getBaseCodeByCodeType("ECO_DEV_TYPE");

		model.addObject("notiTypes", notiTypes);
		model.addObject("lots", lots);
		model.addObject("ecoTypes", ecoTypes);
		model.addObject("devTypes", devTypes);
		model.addObject("applyTimes", applyTimes);
		model.setViewName("/jsp/echange/eco/eco-prod-list.jsp");
		return model;
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> list(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result = ECOHelper.manager.list(params);
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
			ECO eco = ECOHelper.service.delete(oid);
			result.put("result", true);
			result.put("msg", eco.getName() + " ECO가 삭제 되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@RequestMapping(value = "/popup", method = RequestMethod.GET)
	public ModelAndView popup() throws Exception {
		ModelAndView model = new ModelAndView();
		ArrayList<BaseCode> ecoTypes = BaseCodeHelper.manager.getBaseCodeByCodeType("ECO_TYPE");
		ArrayList<BaseCode> notiTypes = BaseCodeHelper.manager.getBaseCodeByCodeType("ECO_NOTI_TYPE");
		ArrayList<BaseCode> lots = BaseCodeHelper.manager.getBaseCodeByCodeType("LOT_MGMT");
		ArrayList<BaseCode> applyTimes = BaseCodeHelper.manager.getBaseCodeByCodeType("ECO_APPLY_TIME");
		ArrayList<BaseCode> devTypes = BaseCodeHelper.manager.getBaseCodeByCodeType("ECO_DEV_TYPE");
		model.addObject("notiTypes", notiTypes);
		model.addObject("lots", lots);
		model.addObject("ecoTypes", ecoTypes);
		model.addObject("devTypes", devTypes);
		model.addObject("applyTimes", applyTimes);
		model.setViewName("popup:/echange/eco/eco-popup-list");
		return model;
	}

	@RequestMapping(value = "/info")
	@ResponseBody
	public Map<String, Object> info(@RequestBody Map<String, Object> params) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		List<PartColumns> list = new ArrayList<PartColumns>();
		try {
			list = ECOHelper.manager.info(params);
			result.put("result", true);
			result.put("info", list);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@RequestMapping(value = "/prodPart", method = RequestMethod.GET)
	public ModelAndView prodPart() throws Exception {
		ModelAndView model = new ModelAndView();
		ArrayList<BaseCode> brand = BaseCodeHelper.manager.getBaseCodeByCodeType("BRAND"); // 브랜드
		ArrayList<BaseCode> company = BaseCodeHelper.manager.getBaseCodeByCodeType("COMPANY"); // 회사
		ArrayList<BaseCode> cat_l = BaseCodeHelper.manager.getBaseCodeByCodeType("CAT_L"); // 회사
		ArrayList<BaseCode> cat_m = BaseCodeHelper.manager.getBaseCodeByCodeType("CAT_M"); // 회사
		QuantityUnit[] units = QuantityUnit.getQuantityUnitSet();
		model.addObject("brand", brand);
		model.addObject("company", company);
		model.addObject("cat_l", cat_l);
		model.addObject("cat_m", cat_m);
		model.addObject("units", units);
		model.setViewName("popup:/echange/eco/part-prod-popup-list");
		return model;
	}

	@RequestMapping(value = "/designPart", method = RequestMethod.GET)
	public ModelAndView designPart() throws Exception {
		ModelAndView model = new ModelAndView();
		ArrayList<BaseCode> brand = BaseCodeHelper.manager.getBaseCodeByCodeType("BRAND"); // 브랜드
		ArrayList<BaseCode> company = BaseCodeHelper.manager.getBaseCodeByCodeType("COMPANY"); // 회사
		ArrayList<BaseCode> cat_l = BaseCodeHelper.manager.getBaseCodeByCodeType("CAT_L"); // 회사
		ArrayList<BaseCode> cat_m = BaseCodeHelper.manager.getBaseCodeByCodeType("CAT_M"); // 회사
		QuantityUnit[] units = QuantityUnit.getQuantityUnitSet();
		model.addObject("brand", brand);
		model.addObject("company", company);
		model.addObject("cat_l", cat_l);
		model.addObject("cat_m", cat_m);
		model.addObject("units", units);
		model.setViewName("popup:/echange/eco/part-design-popup-list");
		return model;
	}

	@RequestMapping(value = "/attach")
	@ResponseBody
	public Map<String, Object> attach(@RequestBody Map<String, Object> params) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		List<PartColumns> list = new ArrayList<PartColumns>();
		try {
			list = PartHelper.manager.attach(params);
			result.put("result", true);
			result.put("info", list);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}
}