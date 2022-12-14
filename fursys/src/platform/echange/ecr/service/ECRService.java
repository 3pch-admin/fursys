package platform.echange.ecr.service;

import platform.echange.ecr.entity.ECR;
import platform.echange.ecr.entity.ECRDTO;
import wt.method.RemoteInterface;

@RemoteInterface
public interface ECRService {

	public ECR create(ECRDTO params) throws Exception;
	
	public ECR modify(ECRDTO params) throws Exception;
	
	public ECR delete(String oid) throws Exception;

}
