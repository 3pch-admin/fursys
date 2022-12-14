package platform.attr.service;

import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;

import platform.attr.entity.AttrDTO;
import wt.method.RemoteInterface;

@RemoteInterface
public interface AttrService {

	public void create(HashMap<String, List<AttrDTO>> paramsMap) throws Exception;

	public void create(JSONArray array) throws Exception;

}
