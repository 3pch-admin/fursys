package platform.dist.service;

import platform.dist.entity.Dist;
import platform.dist.entity.DistDTO;
import wt.method.RemoteInterface;

@RemoteInterface
public interface DistService {

	public Dist create(DistDTO params) throws Exception;

	public void afterAction(Dist dist, String types) throws Exception;

	public Dist delete(String oid) throws Exception;
	
	public Dist modify(DistDTO params) throws Exception;

	public Dist matCreate(DistDTO params) throws Exception;
}
