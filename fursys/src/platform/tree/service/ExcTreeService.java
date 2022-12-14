package platform.tree.service;

import java.util.HashMap;
import java.util.List;

import platform.tree.entity.EManufacturingDTO;
import platform.tree.entity.EMaterialInfoDTO;
import platform.tree.entity.ESurfaceDTO;

public interface ExcTreeService {

	public void ematerial(HashMap<String, List<EMaterialInfoDTO>> paramsMap) throws Exception;

	public void emanufacturing(HashMap<String, List<EManufacturingDTO>> paramsMap) throws Exception;

	public void esurface(HashMap<String, List<ESurfaceDTO>> paramsMap) throws Exception;

}
