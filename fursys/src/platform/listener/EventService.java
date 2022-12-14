package platform.listener;

import wt.epm.EPMDocument;
import wt.method.RemoteInterface;
import wt.part.WTPart;

@RemoteInterface
public interface EventService {

	public void transferTo(EPMDocument epm) throws Exception;

	public void transferTo(WTPart part) throws Exception;
}
