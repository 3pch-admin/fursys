package platform.user.service;

import platform.util.EventKeys;
import wt.events.KeyedEvent;
import wt.org.WTUser;
import wt.services.ServiceEventListenerAdapter;

public class UserListener extends ServiceEventListenerAdapter {

	public UserListener(String var) {
		super(var);
	}

	public void notifyVetoableEvent(Object obj) throws Exception {
		if (!(obj instanceof KeyedEvent)) {
			return;
		}

		KeyedEvent keyedEvent = (KeyedEvent) obj;
		Object target = keyedEvent.getEventTarget();
		String type = keyedEvent.getEventType();

		if (target instanceof WTUser) {
			if (type.equals(EventKeys.POST_STORE)) {
				WTUser wtUser = (WTUser) target;
				System.out.println("신규 유저 생성후 호출!");
				UserHelper.service.postSave(wtUser);
			}

			if (type.equals(EventKeys.POST_MODIFY)) {
				WTUser wtUser = (WTUser) target;
				UserHelper.service.postModify(wtUser);
			}
		}
	}
}