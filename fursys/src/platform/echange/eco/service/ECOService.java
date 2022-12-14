package platform.echange.eco.service;

import platform.echange.eco.entity.ECO;
import platform.echange.eco.entity.ECODTO;
import wt.method.RemoteInterface;

@RemoteInterface
public interface ECOService {

	public ECO create(ECODTO params) throws Exception;

	public ECO modify(ECODTO params) throws Exception;

	public ECO delete(String oid) throws Exception;

	public void afterAction(ECO eco) throws Exception;

	public void preAction(ECO eco) throws Exception;

}
