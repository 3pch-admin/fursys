package platform.tree.service;

import java.util.HashMap;
import java.util.List;

import platform.tree.entity.CombinationDTO;
import platform.tree.entity.EMaterialInfoDTO;
import platform.tree.entity.EdgeDTO;
import platform.tree.entity.MaterialInfoDTO;
import platform.tree.entity.SurfaceDTO;
import platform.tree.entity.TreatmentDTO;
import wt.method.RemoteInterface;

@RemoteInterface
public interface TreeService {

	public void combination(HashMap<String, List<CombinationDTO>> paramsMap) throws Exception;

	public void treatment(HashMap<String, List<TreatmentDTO>> paramsMap) throws Exception;

	public void material(HashMap<String, List<MaterialInfoDTO>> paramsMap) throws Exception;

	public void surface(HashMap<String, List<SurfaceDTO>> paramsMap) throws Exception;

	public void edge(HashMap<String, List<EdgeDTO>> paramsMap) throws Exception;


}
