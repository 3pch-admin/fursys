package platform.part.service;

import net.sf.json.JSONObject;
import platform.part.entity.PartDTO;
import wt.method.RemoteInterface;
import wt.part.WTPart;

@RemoteInterface
public interface PartService {

	public WTPart create(PartDTO params) throws Exception;

	public WTPart modify(PartDTO params) throws Exception;

	public JSONObject attach(PartDTO params) throws Exception;

}
