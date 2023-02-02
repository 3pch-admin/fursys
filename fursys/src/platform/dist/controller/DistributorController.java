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
import platform.dist.entity.Distributor;
import platform.dist.entity.DistributorDTO;
import platform.dist.entity.DistributorUser;
import platform.dist.entity.DistributorUserDTO;
import platform.dist.service.DistributorHelper;
import platform.user.service.UserHelper;
import platform.util.CommonUtils;
import platform.util.service.CPCHistoryHelper;
import wt.part.QuantityUnit;

@Controller
@RequestMapping(value = "/distributor/**")
public class DistributorController {

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() throws Exception {
//		ArrayList<BaseCode> brand = BaseCodeHelper.manager.getBaseCodeByCodeType("BRAND"); // 브랜드
//		ArrayList<BaseCode> company = BaseCodeHelper.manager.getBaseCodeByCodeType("COMPANY"); // 회사
		ModelAndView model = new ModelAndView();
//		model.addObject("brand", brand);
//		model.addObject("company", company);
		model.setViewName("/jsp/dist/distributor-list.jsp");
		return model;
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> list(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result = DistributorHelper.manager.list(params);
			result.put("result", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@RequestMapping(value = "/userList", method = RequestMethod.GET)
	public ModelAndView userList() throws Exception {
//		ArrayList<BaseCode> brand = BaseCodeHelper.manager.getBaseCodeByCodeType("BRAND"); // 브랜드
//		ArrayList<BaseCode> company = BaseCodeHelper.manager.getBaseCodeByCodeType("COMPANY"); // 회사
		ModelAndView model = new ModelAndView();
//		model.addObject("brand", brand);
//		model.addObject("company", company);
		model.setViewName("/jsp/dist/distributor-userList.jsp");
		return model;
	}

	@RequestMapping(value = "/userList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> userList(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result = DistributorHelper.manager.userList(params);
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
		ArrayList<BaseCode> factory = BaseCodeHelper.manager.getBaseCodeByCodeType("FACTORY_CODE");
		model.addObject("factory", factory);
		model.setViewName("popup:/dist/distributor-create");
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public Map<String, Object> create(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Distributor distributor = DistributorHelper.service.create(params);
			result.put("result", true);
			result.put("msg", distributor.getName() + " 배포처가 등록 되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@RequestMapping(value = "/userCreate", method = RequestMethod.GET)
	public ModelAndView userCreate() throws Exception {
		ModelAndView model = new ModelAndView();
		ArrayList<BaseCode> factory = BaseCodeHelper.manager.getBaseCodeByCodeType("FACTORY_CODE");
		model.addObject("factory", factory);
		model.setViewName("popup:/dist/distributor-userCreate");
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/userCreate", method = RequestMethod.POST)
	public Map<String, Object> userCreate(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			DistributorUser distUser = DistributorHelper.service.userCreate(params);

			String reMsg = "[" + distUser.getName() + "] " + distUser.getUserName() + " 배포처 사용자가 등록 되었습니다.";
			if ("OUT".equals(distUser.getType())) {
				reMsg = "[" + distUser.getName() + "] " + distUser.getUserName()
						+ " 배포처 사용자가 등록 되었습니다.";
			}

			result.put("result", true);
			result.put("msg", reMsg);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/userDelete", method = RequestMethod.GET)
	public Map<String, Object> userDelete(@RequestParam String oid) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			DistributorUser distributor = DistributorHelper.service.userDelete(oid);
			result.put("result", true);
			result.put("msg", distributor.getName() + " 배포처 사용자가 비활성화 되었습니다.");
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
		Distributor distributor = (Distributor) CommonUtils.persistable(oid);
		DistributorDTO dto = new DistributorDTO(distributor);
//		model.addObject("distributor", dto);
		model.addObject("dto", dto);
		model.setViewName("popup:/dist/distributor-view");
		return model;
	}

	@RequestMapping(value = "/userView", method = RequestMethod.GET)
	public ModelAndView userView(@RequestParam String oid) throws Exception {
		ModelAndView model = new ModelAndView();
		DistributorUser distributor = (DistributorUser) CommonUtils.persistable(oid);
		DistributorUserDTO dto = new DistributorUserDTO(distributor);
//		model.addObject("distributor", dto);
		model.addObject("dto", dto);
		model.setViewName("popup:/dist/distributor-userView");
		return model;
	}

	@RequestMapping(value = "/info")
	@ResponseBody
	public Map<String, Object> info(@RequestBody Map<String, Object> params) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, Object> list = DistributorHelper.manager.info(params);
			result.put("list", list);
			result.put("result", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}
	
	@RequestMapping(value = "/userInfo")
	@ResponseBody
	public Map<String, Object> userInfo(@RequestBody Map<String, Object> params) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			ArrayList<Map<String, Object>> list = DistributorHelper.manager.userInfo(params);
			result.put("list", list);
			result.put("result", true);
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
		Distributor dist = (Distributor) CommonUtils.persistable(oid);
		DistributorDTO dto = new DistributorDTO(dist);
//		ArrayList<BaseCode> plant = BaseCodeHelper.manager.getBaseCodeByCodeType("PLANT");
		ArrayList<BaseCode> factory = BaseCodeHelper.manager.getBaseCodeByCodeType("FACTORY_CODE");
		model.addObject("dto", dto);
		model.addObject("factory", factory);
		model.setViewName("popup:/dist/distributor-modify");
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public Map<String, Object> modify(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Distributor distributor = DistributorHelper.service.modify(params);
			result.put("result", true);
			result.put("msg", distributor.getName() + "배포처가 수정 되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@RequestMapping(value = "/userModify", method = RequestMethod.GET)
	public ModelAndView userModify(@RequestParam String oid) throws Exception {
		ModelAndView model = new ModelAndView();
		DistributorUser dist = (DistributorUser) CommonUtils.persistable(oid);
		DistributorUserDTO dto = new DistributorUserDTO(dist);
		ArrayList<BaseCode> plant = BaseCodeHelper.manager.getBaseCodeByCodeType("PLANT");
		model.addObject("dto", dto);
		model.addObject("plant", plant);
		model.setViewName("popup:/dist/distributor-userModify");
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/userModify", method = RequestMethod.POST)
	public Map<String, Object> userModify(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			DistributorUser distributor = DistributorHelper.service.userModify(params);
			result.put("result", true);
			result.put("msg", distributor.getName() + "배포 사용자가 수정 되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/getDistributor")
	public Map<String, Object> getDistributor(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = DistributorHelper.manager.getDistributorList(params);
			result.put("result", true);
			result.put("list", list);
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
		model.setViewName("popup:/dist/distributor-popup-list");

		return model;
	}
	
	@RequestMapping(value = "/popupUser", method = RequestMethod.GET)
	public ModelAndView popupUser() throws Exception {
		ModelAndView model = new ModelAndView();
		model.setViewName("popup:/dist/distributor-popup-userList");

		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public Map<String, Object> delete(@RequestParam String oid) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			//사용여부 on/off
			Distributor distributor = DistributorHelper.service.delete(oid);
			result.put("result", true);
			result.put("msg", distributor.getName() + "사용 여부가 변경되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}
	
	@RequestMapping(value = "/userInfoList")
	@ResponseBody
	public Map<String, Object> userInfoList(@RequestBody Map<String, Object> params) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			ArrayList<String> list = (ArrayList<String>) params.get("list");
			ArrayList<Map<String, Object>> reList = DistributorHelper.manager.getDistributorUserColumns(list);
			result.put("list", reList);		
			result.put("result", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/sendDistributor", method = RequestMethod.POST)
	public Map<String, Object> sendDistributor(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			
			int sendResult = DistributorHelper.service.sendDistributor(params);	
			result.put("result", true);
			result.put("msg", " 전송 등록 되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/sendDistributorUser", method = RequestMethod.POST)
	public Map<String, Object> sendDistributorUser(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			
			int sendResult = DistributorHelper.service.sendDistributorUser(params);	
			result.put("result", true);
			result.put("msg", " 전송 등록 되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}
	

}
