package platform.message.service;

import java.util.ArrayList;

import platform.message.entity.Message;
import platform.util.CommonUtils;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.org.WTUser;
import wt.query.ClassAttribute;
import wt.query.OrderBy;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.services.ServiceFactory;
import wt.session.SessionHelper;

public class MessageHelper {

	public static final MessageService service = ServiceFactory.getService(MessageService.class);
	public static final MessageHelper manager = new MessageHelper();

	public ArrayList<Message> getMessages() throws Exception {
		ArrayList<Message> messages = new ArrayList<Message>();

		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(Message.class, true);

		SearchCondition sc = null;

		sc = new SearchCondition(Message.class, Message.HIT, SearchCondition.IS_FALSE);
		query.appendWhere(sc, new int[] { idx });

		if (!CommonUtils.isAdmin()) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			WTUser user = (WTUser) SessionHelper.manager.getPrincipal();
			sc = new SearchCondition(Message.class, "ownership.owner.key.id", "=",
					user.getPersistInfo().getObjectIdentifier().getId());
			query.appendWhere(sc, new int[] { idx });
		}

		ClassAttribute ca = new ClassAttribute(Message.class, Message.CREATE_TIMESTAMP);
		OrderBy by = new OrderBy(ca, true);
		query.appendOrderBy(by, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find(query);
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			Message message = (Message) obj[0];
			messages.add(message);
		}
		return messages;
	}
}
