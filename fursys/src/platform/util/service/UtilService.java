package platform.util.service;

import java.util.Set;

import platform.ebom.entity.EBOM;
import platform.ebom.entity.EBOMMaster;
import platform.echange.eco.entity.ECO;
import platform.util.entity.FileDTO;
import wt.method.RemoteInterface;
import wt.util.WTException;

@RemoteInterface
public interface UtilService {

	public Set<FileDTO> getCadFileDataList(String oid, String color) throws WTException;

	public void transferToDTMG(EBOM ebom, ECO eco) throws Exception;

}
