package platform.tree.service;

import java.util.HashMap;
import java.util.List;

import platform.tree.entity.BomQuantityDTO;
import wt.method.RemoteInterface;

@RemoteInterface
public interface BomQuantityService {

	public void create(HashMap<String, List<BomQuantityDTO>> paramsMap) throws Exception;
}
