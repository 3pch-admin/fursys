package platform.dist.controller;

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
import platform.dist.entity.Dist;
import platform.dist.entity.DistDTO;
import platform.dist.entity.DistPartColumns;
import platform.dist.entity.DistributorUserColumns;
import platform.dist.service.DistHelper;
import platform.dist.service.DistributorHelper;
import platform.epm.entity.EpmColumns;
import platform.epm.service.EpmHelper;
import platform.util.CommonUtils;
import wt.part.QuantityUnit;
import wt.part.WTPart;

@Controller
@RequestMapping(value = "/dist/**")
public class DistController {

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() throws Exception {
		ModelAndView model = new ModelAndView();
		model.setViewName("/jsp/dist/dist-list.jsp");
		return model;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() throws Exception {
		ModelAndView model = new ModelAndView();
//		ArrayList<Map<String, Object>> list = DistributorHelper.manager.getDistributorUser();
//		model.addObject("list", list);
		model.setViewName("popup:/dist/dist-create");
		return model;
	}
	
	@RequestMapping(value = "/matCreate", method = RequestMethod.GET)
	public ModelAndView matCreate() throws Exception {
		ModelAndView model = new ModelAndView();
//		ArrayList<Map<String, Object>> list = DistributorHelper.manager.getDistributorUser();
//		model.addObject("list", list);
		model.setViewName("popup:/dist/dist-matCreate");
		return model;
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> list(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result = DistHelper.manager.list(params);
			result.put("result", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}
	
	@RequestMapping(value = "/matList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> matList(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result = DistHelper.manager.list(params);
			result.put("result", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}
	
	@RequestMapping(value = "/matList", method = RequestMethod.GET)
	public ModelAndView matList() throws Exception {
		ModelAndView model = new ModelAndView();
		model.setViewName("/jsp/dist/dist-matList.jsp");
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public Map<String, Object> create(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {	
			Dist dist = DistHelper.service.create(params);
			result.put("result", true);
			result.put("msg", dist.getName() + " 배포가 등록 되었습니다.");
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
		Dist dist = (Dist) CommonUtils.persistable(oid);
		DistDTO dto = new DistDTO(dist);
		model.addObject("dto", dto);
		model.setViewName("popup:/dist/dist-view");
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public Map<String, Object> delete(@RequestParam String oid) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Dist dist = DistHelper.service.delete(oid);
			result.put("result", true);
			result.put("msg", dist.getName() + "삭제 되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public ModelAndView modify(@RequestParam String oid) throws Exception {
		ModelAndView model = new ModelAndView();
		Dist dist = (Dist) CommonUtils.persistable(oid);
		DistDTO dto = new DistDTO(dist);
		ArrayList<Map<String, Object>> list = DistributorHelper.manager.getDistributor();
		model.addObject("dto", dto);
		model.addObject("list", list);
		model.setViewName("popup:/dist/dist-modify");
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public Map<String, Object> modify(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Dist dist = DistHelper.service.modify(params);
			result.put("result", true);
			result.put("msg", dist.getName() + "배포가 수정 되었습니다.");
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
		model.setViewName("popup:/dist/dist-part-list");
		return model;
	}
	
	@RequestMapping(value = "/popup_mat", method = RequestMethod.GET)
	public ModelAndView popup_mat() throws Exception {
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
		model.setViewName("popup:/dist/dist-mat-list");
		return model;
	}

	@RequestMapping(value = "/info")
	@ResponseBody
	public Map<String, Object> info(@RequestBody Map<String, Object> params) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		List<DistPartColumns> list = new ArrayList<DistPartColumns>();
		try {
			list = DistHelper.manager.info(params);
			result.put("result", true);
			result.put("info", list);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}
	
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public ModelAndView detail(@RequestParam(name="oid") String oid) throws Exception {
		ModelAndView model = new ModelAndView();
		WTPart part = (WTPart) CommonUtils.persistable(oid);
		ArrayList<EpmColumns> list = EpmHelper.manager.detail(part, new ArrayList<>());
		model.addObject("list", list);
		System.out.println("list=" + list.size());
		model.setViewName("popup:/dist/dist-detail-view");
		return model;
	}
	
	@RequestMapping(value = "/partList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> partList(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<DistPartColumns> list = new ArrayList<DistPartColumns>();
		try {
			
			String oid = (String) params.get("oid");
			
			Dist di = (Dist)CommonUtils.persistable(oid);
			
			list = DistHelper.manager.getPartColumnLinks(di);
			result.put("result", true);
			result.put("list", list);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}
	
	@RequestMapping(value = "/distributortList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> distributortList(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<DistributorUserColumns> list = new ArrayList<DistributorUserColumns>();
		try {
			
			String oid = (String) params.get("oid");
			
			Dist di = (Dist)CommonUtils.persistable(oid);
			
			list = DistHelper.manager.getDistributorUserColumnLinks(di);
			result.put("result", true);
			result.put("list", list);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/sendEp", method = RequestMethod.POST)
	public Map<String, Object> sendEp(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {	
			System.out.println("####controller###############==sendEp==");
			//Dist dist = DistHelper.service.create(params);
			
			
			result.put("result", true);
			result.put("msg", "배포(단품)가 등록 되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/sendEp2", method = RequestMethod.POST)
	public ModelAndView sendEp2(@RequestBody Map<String, Object> params) throws Exception {
		
		String oid = (String)params.get("oid");
		
		System.out.println("####controller==sendEp2=="+oid);
		
		Map<String, Object> sendMap = new HashMap<String, Object>(); //DistributorHelper.manager.getDistributorUser();
		
		//sendMap.put("upn", "ljh1103@fursys.com");
		sendMap.put("upn", "dc1995@fursys.com");
		//sendMap.put("upn", "youngsoo_shim@fursys.com");
		
		sendMap.put("microsoftAppId", "0f74ef2f-72f1-4a58-90d7-0135013f0bda");
		sendMap.put("title", "titleV");
		
		Map<String, Object> titleDesignMap = new HashMap<String, Object>();
		titleDesignMap.put("color", "1");
		titleDesignMap.put("weight", "1");
		titleDesignMap.put("size", "1");
		
		sendMap.put("titleDesign", titleDesignMap);
			
		sendMap.put("sendDt", "2022-07-12 14:20");
		sendMap.put("sender", "");
		sendMap.put("regId", "dc1995@fursys.com");
		sendMap.put("contents", "");
		
		sendMap.put("userImgUrl", "");
		sendMap.put("notificationMessage", "");
		
		Map<String, Object> linkButtonsMap = new HashMap<String, Object>();
		linkButtonsMap.put("linkUrl", "www.naver.com");
		linkButtonsMap.put("linkTitle", "네이벙");
		
		sendMap.put("linkButtons", linkButtonsMap);
		
		Map<String, Object> descriptionItemsMap = new HashMap<String, Object>();
		descriptionItemsMap.put("title", "1");
		descriptionItemsMap.put("value", "1");
		
		sendMap.put("descriptionItems", descriptionItemsMap);
			
		
		
		ModelAndView model = new ModelAndView();
		model.addObject(sendMap);
		model.setViewName("https://api.fursys.com/teamsbot/send");
		
		
		return model;
	}
	
	@ResponseBody
	@RequestMapping(value = "/sendCPC", method = RequestMethod.POST)
	public void sendCPC(@RequestBody Map<String, Object> params) throws Exception {
		
		String oid = (String)params.get("oid");
		
		System.out.println("####controller==sendCPC=="+oid);
		Dist di = (Dist)CommonUtils.persistable(oid);
		String type = "DIST";
		
		DistHelper.service.afterAction(di, type);
		
	}
	
}
