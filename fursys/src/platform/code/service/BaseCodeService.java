package platform.code.service;

import java.util.HashMap;
import java.util.List;

import platform.code.entity.BaseCodeDTO;
import wt.method.RemoteInterface;
import wt.util.WTException;

@RemoteInterface
public interface BaseCodeService {

	public void initialize(String path) throws WTException;

	public void save(HashMap<String, List<BaseCodeDTO>> paramsMap) throws Exception;
}
