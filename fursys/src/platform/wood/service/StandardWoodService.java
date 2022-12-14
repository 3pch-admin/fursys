package platform.wood.service;

import wt.services.StandardManager;
import wt.util.WTException;

public class StandardWoodService extends StandardManager implements WoodService {

	public static StandardWoodService newStandardWoodService() throws WTException {
		StandardWoodService instance = new StandardWoodService();
		instance.initialize();
		return instance;
	}
}
