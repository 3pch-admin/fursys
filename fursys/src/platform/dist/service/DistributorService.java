package platform.dist.service;

import java.util.Map;

import platform.dist.entity.Distributor;
import platform.dist.entity.DistributorUser;

public interface DistributorService {

	public Distributor create(Map<String, Object> params) throws Exception;

	public Distributor modify(Map<String, Object> params) throws Exception;

	public Distributor delete(String oid) throws Exception;

	public DistributorUser userCreate(Map<String, Object> params) throws Exception;

	public DistributorUser userModify(Map<String, Object> params) throws Exception;

	public DistributorUser userDelete(String oid) throws Exception;

	public void send(Distributor distributor) throws Exception;

	public void _send(Distributor distributor) throws Exception;

	public void __send(Distributor distributor) throws Exception;
}
