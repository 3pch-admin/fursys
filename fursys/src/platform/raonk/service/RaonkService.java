package platform.raonk.service;

import platform.raonk.entity.Raonk;
import platform.raonk.entity.RaonkDTO;
import wt.method.RemoteInterface;

@RemoteInterface
public interface RaonkService {

	public Raonk create(RaonkDTO params) throws Exception;

	public Raonk modify(RaonkDTO params) throws Exception;

	public Raonk delete(String oid) throws Exception;

}
