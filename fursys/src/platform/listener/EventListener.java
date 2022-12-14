package platform.listener;

import wt.epm.EPMDocument;
import wt.events.KeyedEvent;
import wt.fc.PersistenceManagerEvent;
import wt.part.WTPart;
import wt.services.ServiceEventListenerAdapter;
import wt.vc.wip.WorkInProgressServiceEvent;

public class EventListener extends ServiceEventListenerAdapter {

	private static final String POST_STORE = PersistenceManagerEvent.POST_STORE;

	public EventListener(String s) {
		super(s);
	}

	public void notifyVetoableEvent(Object obj) throws Exception {
		if (!(obj instanceof KeyedEvent)) {
			return;
		}

		KeyedEvent keyedEvent = (KeyedEvent) obj;
		Object target = keyedEvent.getEventTarget();
		String type = keyedEvent.getEventType();

//		System.out.println("type=" + type);
//		System.out.println("tage=" + target.getClass());

		if (target instanceof EPMDocument) {
			if (POST_STORE.equals(type)) {
				EPMDocument epm = (EPMDocument) target;
				System.out.println("체크인(도면) 매개변수 입력...");
				EventHelper.service.transferTo(epm);
			}
		}

		if (target instanceof WTPart) {
			if (POST_STORE.equals(type)) {
				WTPart part = (WTPart) target;
				System.out.println("체크인(부품) 매개변수 입력...");
				EventHelper.service.transferTo(part);
			}
		}
	}
}