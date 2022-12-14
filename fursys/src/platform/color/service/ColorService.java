package platform.color.service;

import java.util.Map;

import platform.color.entity.Color;
import wt.method.RemoteInterface;

@RemoteInterface
public interface ColorService {

	public void create(Map<String, Object> params) throws Exception;

	public Color delete(String oid) throws Exception;

}
