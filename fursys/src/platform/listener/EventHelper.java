package platform.listener;

import wt.services.ServiceFactory;

public class EventHelper {

	public static final EventHelper manager = new EventHelper();
	public static final EventService service = ServiceFactory.getService(EventService.class);
}
