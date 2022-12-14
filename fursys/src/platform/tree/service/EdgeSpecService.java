package platform.tree.service;

import java.util.HashMap;
import java.util.List;

import platform.tree.entity.EMaterialDTO;
import platform.tree.entity.EShapeDTO;
import wt.method.RemoteInterface;

@RemoteInterface
public interface EdgeSpecService {
	
	public void ematerial(HashMap<String, List<EMaterialDTO>> paramsMap) throws Exception;
	
	public void eshape(HashMap<String, List<EShapeDTO>> paramsMap) throws Exception;

}
