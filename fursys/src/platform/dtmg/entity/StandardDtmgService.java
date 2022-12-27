package platform.dtmg.entity;

import wt.services.StandardManager;
import wt.util.WTException;

public class StandardDtmgService extends StandardManager implements DtmgService {

	public static StandardDtmgService newStandardDtmgService() throws WTException {
		StandardDtmgService instance = new StandardDtmgService();
		instance.initialize();
		return instance;
	}
}
