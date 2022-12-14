package platform.erp.service;

import java.util.List;
import java.util.Map;

import platform.echange.ecn.entity.ECN;
import wt.method.RemoteInterface;

@RemoteInterface
public interface ERPService {

	public List<String> material(Map<String, Object> params) throws Exception;

	public void sendErpToECN(ECN ecn) throws Exception;

	public void sendErpToPart(ECN ecn) throws Exception;

	public void sendErpToBom(ECN ecn) throws Exception;

	public void createSendHistory(String name, String resultMessage, String sendType, boolean sendResult)
			throws Exception;

	public void createHistory(String resultMessage, String createType, boolean createResult) throws Exception;

	public Map<String, Object> getDCost(Map<String, Object> params) throws Exception;

	public void structure(Map<String, Object> params) throws Exception;

}
