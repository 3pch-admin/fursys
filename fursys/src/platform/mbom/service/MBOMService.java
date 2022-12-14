package platform.mbom.service;

import java.util.Map;

import platform.ebom.entity.EBOM;
import platform.mbom.entity.MBOM;
import platform.ebom.entity.EBOM;
import platform.ebom.entity.EBOM;
import wt.method.RemoteInterface;

@RemoteInterface
public interface MBOMService {

	public void generate(EBOM ebom) throws Exception;

	public MBOM modify(Map<String, Object> params) throws Exception;
}
