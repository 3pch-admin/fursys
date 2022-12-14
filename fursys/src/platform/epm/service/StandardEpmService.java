package platform.epm.service;

import java.sql.Timestamp;

import com.ptc.wvs.common.ui.PublishResult;
import com.ptc.wvs.server.publish.Publish;
import com.ptc.wvs.server.util.PublishUtils;

import platform.part.service.PartHelper;
import platform.util.DateUtils;
import platform.util.IBAUtils;
import platform.util.StringUtils;
import wt.content.ContentHelper;
import wt.content.ContentItem;
import wt.content.ContentRoleType;
import wt.content.ContentServerHelper;
import wt.epm.EPMDocument;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.org.WTUser;
import wt.part.WTPart;
import wt.pom.Transaction;
import wt.representation.Representation;
import wt.services.StandardManager;
import wt.session.SessionHelper;
import wt.util.WTException;
import wt.vc.config.ConfigSpec;

public class StandardEpmService extends StandardManager implements EpmService {

	public static StandardEpmService newStandardEpmService() throws WTException {
		StandardEpmService instance = new StandardEpmService();
		instance.initialize();
		return instance;
	}

	@Override
	public void afterAction(WTPart p) throws Exception {
		Transaction trs = new Transaction();
		try {
			trs.start();

			WTUser user = (WTUser) SessionHelper.manager.getPrincipal();
			Timestamp today = DateUtils.today();
			EPMDocument epm = PartHelper.manager.getEPMDocument(p);

			if (epm != null) {

				Representation representation = PublishUtils.getRepresentation(epm);

				if (representation != null) {
					PersistenceHelper.manager.delete(representation);
				}

				String APPROVED_BY = IBAUtils.getStringValue(epm, "APPROVED_BY");
				String APPROVED_DATE = IBAUtils.getStringValue(epm, "APPROVED_DATE");
				if (!StringUtils.isNotNull(APPROVED_DATE)) {
					IBAUtils.createIBA(epm, "s", "APPROVED_DATE", today.toString().substring(0, 10));
				}

				if (!StringUtils.isNotNull(APPROVED_BY)) {
					IBAUtils.createIBA(epm, "s", "APPROVED_BY", user.getFullName());
				}

				ConfigSpec configspec = null;
				PublishResult rs = Publish.doPublish(false, true, epm, configspec, null, false, null, null, 1, null, 2,
						null);
				if (rs.isSuccessful()) {
					System.out.println("Publish Success = " + epm.getNumber());
				}
			}

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
