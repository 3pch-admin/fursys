package platform.partlist.service;

import java.util.Map;

import wt.services.ServiceFactory;

public class PartlistHelper {

	public static final PartlistService service = ServiceFactory.getService(PartlistService.class);

	public static final PartlistHelper manager = new PartlistHelper();

	public Map<String, Object> list(Map<String, Object> params) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
