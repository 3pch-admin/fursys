package platform.department.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONArray;
import platform.department.service.DepartmentHelper;
import platform.user.entity.UserInfo;

@Controller
@RequestMapping(value = "/dept/**")
public class DepartmentController {

	@RequestMapping(value = "tree")
	@ResponseBody
	public JSONArray tree() throws Exception {
		return DepartmentHelper.manager.tree();
	}

	@ResponseBody
	@RequestMapping(value = "/deptUser")
	public Map<String, Object> deptUser(@RequestBody Map<String, Object> params) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		List<UserInfo> list = new ArrayList<UserInfo>();
		try {
			list = DepartmentHelper.manager.deptUser(params);
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
