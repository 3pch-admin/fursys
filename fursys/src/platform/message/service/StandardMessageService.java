package platform.message.service;

import platform.message.entity.Message;
import wt.fc.PersistenceHelper;
import wt.org.WTPrincipal;
import wt.org.WTUser;
import wt.ownership.Ownership;
import wt.pom.Transaction;
import wt.services.StandardManager;
import wt.session.SessionHelper;
import wt.util.WTException;

public class StandardMessageService extends StandardManager implements MessageService {

	public static StandardMessageService newStandardMessageService() throws WTException {
		StandardMessageService instance = new StandardMessageService();
		instance.initialize();
		return instance;
	}

	@Override
	public void create(String description, Ownership ownership) throws Exception {
		Message message = null;
		Transaction trs = new Transaction();
		try {
			trs.start();

			WTPrincipal prin = SessionHelper.manager.getPrincipal();

			message = Message.newMessage();
			message.setDescription(description);
			message.setUser((WTUser) prin);
			message.setOwnership(ownership); // 작성자가 메세지를 보내는 사람
			// ownership 은 메세지를 받을 사람으로 한다..
			PersistenceHelper.manager.save(message);

			trs.commit();
			trs = null;
		} catch (Exception e) {
			e.printStackTrace();
			trs.rollback();
			throw e;
		} finally {
			if (trs != null)
				trs.rollback();
		}
	}
}
