package platform.partlist.service;

import wt.services.StandardManager;
import wt.util.WTException;

public class StandardPartlistService extends StandardManager implements PartlistService {

	public static StandardPartlistService newStandardPartlistService() throws WTException {
		StandardPartlistService instance = new StandardPartlistService();
		instance.initialize();
		return instance;
	}
}
