package platform.util.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import platform.ebom.entity.EBOMMaster;
import platform.part.service.PartHelper;
import platform.user.service.UserHelper;
import platform.util.CommonUtils;
import platform.util.StringUtils;
import platform.util.ZipUtils;
import platform.util.entity.FileDTO;
import platform.util.service.UtilHelper;
import wt.content.ApplicationData;
import wt.content.ContentHelper;
import wt.content.ContentHolder;
import wt.content.ContentRoleType;
import wt.content.HolderToContent;
import wt.epm.EPMDocument;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.fv.uploadtocache.CacheDescriptor;
import wt.fv.uploadtocache.CachedContentDescriptor;
import wt.org.WTUser;
import wt.part.WTPart;
import wt.pdmlink.PDMLinkProduct;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.session.SessionHelper;
import wt.util.WTProperties;
import wt.viewmarkup.DerivedImage;

@Controller
@RequestMapping(value = "/util/**")
public class UtilController {

	@ResponseBody
	@RequestMapping("/download")
	public void download(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String oid = request.getParameter("oid");
		String color = request.getParameter("color");
		EBOMMaster m = (EBOMMaster) CommonUtils.persistable(oid);
		WTPart p = PartHelper.manager.getLatest(m.getPart());
		String partNumber = p.getNumber();
		String version = p.getVersionIdentifier().getSeries().getValue();
		Set<FileDTO> fileSet = UtilHelper.service.getCadFileDataList(oid, color);
		response.setContentType("application/octer-stream");
		response.setHeader("Content-Transfer-Encoding", "binary;");
		response.setHeader("Content-Disposition",
				"attachment; filename=\"" + partNumber + "-" + version + "-" + color + ".zip" + "\"");

		ZipUtils.writeZipFile(fileSet, response.getOutputStream());

	}

	@ResponseBody
	@RequestMapping(value = "/tree")
	public JSONArray tree(@RequestParam String location, @RequestParam String context) throws Exception {
		return UtilHelper.manager.tree(location, context);
	}
	
	@ResponseBody
	@RequestMapping(value = "/createFolder")
	public JSONObject createFolder(@RequestParam String location, @RequestParam String context) throws Exception {
		System.out.println("###uicontrol--createFolder");
		System.out.println("###uicontrol--updateFolder=location="+location);
		System.out.println("###uicontrol--updateFolder=context="+context);
		return UtilHelper.manager.createFolder(location, context);
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateFolder")
	public JSONObject updateFolder(@RequestParam String location, @RequestParam String context) throws Exception {
		System.out.println("###uicontrol--updateFolder");
		System.out.println("###uicontrol--updateFolder=location="+location);
		System.out.println("###uicontrol--updateFolder=context="+context);
		return UtilHelper.manager.updateFolder(location, context);
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteFolder")
	public JSONObject deleteFolder(@RequestParam String location, @RequestParam String context) throws Exception {
		System.out.println("###uicontrol--deleteFolder");
		System.out.println("###uicontrol--updateFolder=location="+location);
		System.out.println("###uicontrol--updateFolder=context="+context);
		return UtilHelper.manager.deleteFolder(location, context);
	}

	
	
	@RequestMapping(value = "/folder", method = RequestMethod.GET)
	public ModelAndView folder(@RequestParam String location, @RequestParam String target, @RequestParam String context)
			throws Exception {
		ModelAndView model = new ModelAndView();
		model.addObject("location", location);
		model.addObject("target", target);
		model.addObject("context", context);
		model.setViewName("popup:/common/folder-popup");
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/user")
	public Map<String, Object> user(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = UserHelper.manager.user(params);
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
	@RequestMapping(value = "/upload")
	public String upload(HttpServletRequest request) throws Exception {
		String savePath = WTProperties.getServerProperties().getProperty("wt.temp");
		savePath = savePath + "/platform";
		File tempFolder = new File(savePath);
		if (!tempFolder.exists()) {
			tempFolder.mkdirs();
		}

		int sizeLimit = (1024 * 1024 * 500);
		MultipartRequest multi = new MultipartRequest(request, savePath, sizeLimit, "UTF-8",
				new DefaultFileRenamePolicy());

		String name = multi.getOriginalFileName("data");
		File uploadFile = multi.getFile("data");
		JSONObject jsonReturn = new JSONObject();
		jsonReturn.put("name", name);
		jsonReturn.put("path", uploadFile.getPath());
		jsonReturn.put("value", name + "&" + uploadFile.getPath());
		jsonReturn.put("result", "SUCCESS");
		return jsonReturn.toString();
	}

	@ResponseBody
	@RequestMapping(value = "/delete")
	public JSONObject delete(HttpServletRequest param) throws Exception {
		JSONObject result = new JSONObject();
		result.put("status", "0");
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/list")
	public String list(HttpServletRequest param) throws Exception {
		JSONObject list = new JSONObject();
		String oid = (String) param.getParameter("oid");
		String roleType = (String) param.getParameter("roleType");

		if (StringUtils.isNotNull(oid)) {
			ReferenceFactory rf = new ReferenceFactory();
			ContentHolder holder = (ContentHolder) rf.getReference(oid).getObject();
			if ("p".equalsIgnoreCase(roleType) || "primary".equalsIgnoreCase(roleType)) {
				QueryResult result = ContentHelper.service.getContentsByRole(holder, ContentRoleType.PRIMARY);
				if (result.hasMoreElements()) {
					ApplicationData data = (ApplicationData) result.nextElement();
					JSONObject obj = new JSONObject();
					obj.put("_id_", "AXUpload_Single");
					obj.put("name", data.getFileName());
					obj.put("saveName", data.getFileName());
					obj.put("fileSize", data.getFileSize());
//					obj.put("type", FileUtil.getExtension(data.getFileName());
					obj.put("uploadedPath", data.getUploadedFromPath());
					obj.put("thumbUrl", "");
					obj.put("roleType", roleType);
					list.put("primaryFile", obj);
				}
			} else if ("s".equalsIgnoreCase(roleType) || "secondary".equalsIgnoreCase(roleType)) {
				JSONArray array = new JSONArray();
				QueryResult result = ContentHelper.service.getContentsByRole(holder, ContentRoleType.SECONDARY);
				int count = 0;
				while (result.hasMoreElements()) {
					ApplicationData data = (ApplicationData) result.nextElement();
					JSONObject obj = new JSONObject();
					obj.put("_id_", "AXUpload_" + count++);
					obj.put("name", data.getFileName());
					obj.put("saveName", data.getFileName());
					obj.put("fileSize", data.getFileSize());
					obj.put("uploadedPath", data.getUploadedFromPath());
					obj.put("thumbUrl", "");
					obj.put("roleType", roleType);
					array.add(obj);
					list.put("secondaryFile", array);
				}
			}
		}
		return list.toString();
	}

	@RequestMapping(value = "/openCreoView", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> openCreoView(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> data = new HashMap<String, Object>();
		String oid = request.getParameter("oid");
		EPMDocument epm = (EPMDocument) CommonUtils.persistable(oid);

		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(EPMDocument.class, true);
		int idx_d = query.appendClassList(DerivedImage.class, true);
		int idx_h = query.appendClassList(HolderToContent.class, false);
		int idx_a = query.appendClassList(ApplicationData.class, true);

		SearchCondition sc = new SearchCondition(EPMDocument.class, "thePersistInfo.theObjectIdentifier.id",
				DerivedImage.class, "derivedFromReference.key.id");
		query.appendWhere(sc, new int[] { idx, idx_d });
		query.appendAnd();

		sc = new SearchCondition(DerivedImage.class, "thePersistInfo.theObjectIdentifier.id", HolderToContent.class,
				"roleAObjectRef.key.id");
		query.appendWhere(sc, new int[] { idx_d, idx_h });
		query.appendAnd();

		sc = new SearchCondition(ApplicationData.class, "thePersistInfo.theObjectIdentifier.id", HolderToContent.class,
				"roleBObjectRef.key.id");
		query.appendWhere(sc, new int[] { idx_a, idx_h });
		query.appendAnd();

		sc = new SearchCondition(EPMDocument.class, "thePersistInfo.theObjectIdentifier.id", "=",
				epm.getPersistInfo().getObjectIdentifier().getId());
		query.appendWhere(sc, new int[] { idx });
		query.appendAnd();

		sc = new SearchCondition(ApplicationData.class, ApplicationData.FILE_NAME, "LIKE", "%.pvs");
		query.appendWhere(sc, new int[] { idx_a });

		QueryResult qr = PersistenceHelper.manager.find(query);
		String doid = "";
		String aoid = "";
		String fileName = "";
		if (qr.hasMoreElements()) {
			Object[] obj = (Object[]) qr.nextElement();
			DerivedImage image = (DerivedImage) obj[1];
			ApplicationData dd = (ApplicationData) obj[2];
			doid = image.getPersistInfo().getObjectIdentifier().getStringValue();
			aoid = dd.getPersistInfo().getObjectIdentifier().getStringValue();
			fileName = dd.getFileName();
		}

		String name = epm.getContainer().getName();
		PDMLinkProduct product = CommonUtils.getPDMLinkProduct(name);

		String url = request.getRequestURL().toString();
		url = url.substring(0, url.indexOf(request.getContextPath()));

		StringBuilder sb = new StringBuilder(url);
		sb.append(request.getContextPath());
		sb.append("/servlet/WindchillAuthGW/com.ptc.wvs.server.util.WVSContentHelper/redirectDownload/");
		sb.append(fileName);
		sb.append("?ContentHolder=").append(doid);
		sb.append("&HttpOperationItem=").append(aoid);
		sb.append("&u8=1&objref=").append(doid);
		sb.append("&ContainerOid=").append(product.toString());

		data.put("result", true);
		data.put("url", sb.toString());
		return data;
	}
}
