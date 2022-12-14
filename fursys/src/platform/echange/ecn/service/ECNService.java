package platform.echange.ecn.service;

import platform.echange.ecn.entity.ECN;
import platform.echange.ecn.entity.ECNDTO;
import wt.method.RemoteInterface;

@RemoteInterface
public interface ECNService {

	public ECN create(ECNDTO params) throws Exception;

	public ECN modify(ECNDTO params) throws Exception;

	public ECN delete(String oid) throws Exception;

	public void afterAction(ECN ecn) throws Exception;

}
