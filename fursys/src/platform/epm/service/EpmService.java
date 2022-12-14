package platform.epm.service;

import wt.method.RemoteInterface;
import wt.part.WTPart;

@RemoteInterface
public interface EpmService {

	public void afterAction(WTPart p) throws Exception;

}
