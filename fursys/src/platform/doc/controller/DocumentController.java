package platform.doc.controller;

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
import platform.doc.entity.DocumentColumns;
import platform.doc.entity.DocumentDTO;
import platform.doc.service.DocumentHelper;
import platform.raonk.entity.Raonk;
import platform.raonk.service.RaonkHelper;
import platform.util.CommonUtils;
import wt.doc.WTDocument;

@Controller
@RequestMapping(value = "/doc/**")
public class DocumentController {

	@RequestMapping(value = "/my", method = RequestMethod.GET)
	public ModelAndView my() throws Exception {
		ArrayList<BaseCode> brand = BaseCodeHelper.manager.getBaseCodeByCodeType("BRAND"); // 브랜드
		ArrayList<BaseCode> company = BaseCodeHelper.manager.getBaseCodeByCodeType("COMPANY"); // 회사
		ModelAndView model = new ModelAndView();
		model.addObject("brand", brand);
		model.addObject("company", company);
		model.setViewName("/jsp/doc/doc-my.jsp");
		return model;
	}

	@RequestMapping(value = "/open", method = RequestMethod.GET)
	public ModelAndView open() throws Exception {
		ArrayList<BaseCode> brand = BaseCodeHelper.manager.getBaseCodeByCodeType("BRAND"); // 브랜드
		ArrayList<BaseCode> company = BaseCodeHelper.manager.getBaseCodeByCodeType("COMPANY"); // 회사
		ModelAndView model = new ModelAndView();
		model.addObject("brand", brand);
		model.addObject("company", company);
		model.setViewName("/jsp/doc/doc-open.jsp");
		return model;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() throws Exception {
		ArrayList<BaseCode> brand = BaseCodeHelper.manager.getBaseCodeByCodeType("BRAND"); // 브랜드
		ArrayList<BaseCode> company = BaseCodeHelper.manager.getBaseCodeByCodeType("COMPANY"); // 회사
		ModelAndView model = new ModelAndView();
		model.addObject("brand", brand);
		model.addObject("company", company);
		model.setViewName("/jsp/doc/doc-list.jsp");
		return model;
	}

	@RequestMapping(value = "/my", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> my(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result = DocumentHelper.manager.my(params);
			result.put("result", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@RequestMapping(value = "/open", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> open(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result = DocumentHelper.manager.open(params);
			result.put("result", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> list(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result = DocumentHelper.manager.list(params);
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
		ArrayList<Raonk> raonk = RaonkHelper.manager.get();
		model.addObject("company", company);
		model.addObject("brand", brand);
		model.addObject("raonk", raonk);
		model.setViewName("popup:/doc/doc-create");
		return model;
	}

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam String oid) throws Exception {
		ModelAndView model = new ModelAndView();
		WTDocument doc = (WTDocument) CommonUtils.persistable(oid);
		DocumentDTO dto = new DocumentDTO(doc);
//		Dist dist = (Dist) CommonUtils.persistable(oid);
//		model.addObject("dist", dist);
		model.addObject("dto", dto);
		model.setViewName("popup:/doc/doc-view");
		return model;
	}

	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public ModelAndView modify(@RequestParam String oid) throws Exception {
		ModelAndView model = new ModelAndView();
		ArrayList<BaseCode> company = BaseCodeHelper.manager.getBaseCodeByCodeType("COMPANY");
		ArrayList<BaseCode> brand = BaseCodeHelper.manager.getBaseCodeByCodeType("BRAND");
		ArrayList<Raonk> raonk = RaonkHelper.manager.get();
		WTDocument doc = (WTDocument) CommonUtils.persistable(oid);
		DocumentDTO dto = new DocumentDTO(doc);
		model.addObject("dto", dto);
		model.addObject("company", company);
		model.addObject("brand", brand);
		model.addObject("raonk", raonk);
		model.setViewName("popup:/doc/doc-modify");
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public Map<String, Object> create(@RequestBody DocumentDTO params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			WTDocument doc = DocumentHelper.service.create(params);
			result.put("result", true);
			result.put("msg", doc.getName() + " 문서가 등록 되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public Map<String, Object> modify(@RequestBody DocumentDTO params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			WTDocument doc = DocumentHelper.service.modify(params);
			result.put("result", true);
			result.put("msg", doc.getName() + " 문서가 수정 되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/revise", method = RequestMethod.POST)
	public Map<String, Object> revise(@RequestBody DocumentDTO params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			WTDocument doc = DocumentHelper.service.revise(params);
			result.put("result", true);
			result.put("msg", doc.getName() + " 문서가 개정 되었습니다.");
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
			WTDocument doc = DocumentHelper.service.delete(oid);
			result.put("result", true);
			result.put("msg", doc.getName() + " 문서가 삭제 되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

	@RequestMapping(value = "/popup", method = RequestMethod.GET)
	public ModelAndView popup() throws Exception {
		ArrayList<BaseCode> brand = BaseCodeHelper.manager.getBaseCodeByCodeType("BRAND"); // 브랜드
		ArrayList<BaseCode> company = BaseCodeHelper.manager.getBaseCodeByCodeType("COMPANY"); // 회사
		ModelAndView model = new ModelAndView();
		model.addObject("brand", brand);
		model.addObject("company", company);
		model.setViewName("popup:/doc/doc-popup-list");
		return model;
	}
	
	

	@RequestMapping(value = "/info")
	@ResponseBody
	public Map<String, Object> info(@RequestBody Map<String, Object> params) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		List<DocumentColumns> list = new ArrayList<DocumentColumns>();
		try {
			list = DocumentHelper.manager.info(params);
			result.put("result", true);
			result.put("info", list);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}
	
	@RequestMapping(value = "/createPartToDoc", method = RequestMethod.GET)
	public ModelAndView createPartToDoc(@RequestParam String oid) throws Exception {
		System.out.println("#####################");
		ModelAndView model = new ModelAndView();
		model.addObject("partOid", oid);
		model.setViewName("popup:/doc/doc-create-part");
		return model;
	}
	
	@ResponseBody
	@RequestMapping(value = "/createPartToDoc", method = RequestMethod.POST)
	public Map<String, Object> createPartToDoc(@RequestBody DocumentDTO params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			WTDocument doc = DocumentHelper.service.create(params);
			result.put("result", true);
			result.put("msg", doc.getName() + " 문서가 등록 되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("msg", e.toString());
		}
		return result;
	}

}
