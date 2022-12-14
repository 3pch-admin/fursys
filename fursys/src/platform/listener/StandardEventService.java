package platform.listener;

import java.sql.Timestamp;

import com.ptc.wvs.common.ui.PublishResult;
import com.ptc.wvs.server.publish.Publish;
import com.ptc.wvs.server.util.PublishUtils;

import platform.util.DateUtils;
import platform.util.IBAUtils;
import platform.util.StringUtils;
import wt.epm.EPMDocument;
import wt.fc.PersistenceHelper;
import wt.fc.PersistenceManagerEvent;
import wt.org.WTUser;
import wt.part.WTPart;
import wt.pom.Transaction;
import wt.representation.Representation;
import wt.services.ManagerException;
import wt.services.StandardManager;
import wt.session.SessionHelper;
import wt.util.WTException;
import wt.vc.config.ConfigSpec;

public class StandardEventService extends StandardManager implements EventService {

	private static final String POST_STORE = PersistenceManagerEvent.POST_STORE;

	public static StandardEventService newStandardEventService() throws WTException {
		StandardEventService instance = new StandardEventService();
		instance.initialize();
		return instance;
	}

	protected synchronized void performStartupProcess() throws ManagerException {
		super.performStartupProcess();
		EventListener listener = new EventListener(StandardEventService.class.getName());
		getManagerService().addEventListener(listener, PersistenceManagerEvent.generateEventKey(POST_STORE));
	}

	@Override
	public void transferTo(EPMDocument epm) throws Exception {
		Transaction trs = new Transaction();
		try {
			trs.start();

			WTUser user = (WTUser) SessionHelper.manager.getPrincipal();
			Timestamp today = DateUtils.today();
			String docType = epm.getDocType().toString();
			System.out.println("EPM = " + epm.getNumber() + ", docType = " + docType);
			if ("CADASSEMBLY".equals(docType) || "CADCOMPONENT".equals(docType)) {
				Representation representation = PublishUtils.getRepresentation(epm);

				if (representation != null) {
					PersistenceHelper.manager.delete(representation);
				}

				String DRAWN_BY = IBAUtils.getStringValue(epm, "DRAWN_BY");
				String DRAWN_DATE = IBAUtils.getStringValue(epm, "DRAWN_DATE");
				if (!StringUtils.isNotNull(DRAWN_DATE)) {
					IBAUtils.createIBA(epm, "s", "DRAWN_DATE", today.toString().substring(0, 10));
				}

				if (!StringUtils.isNotNull(DRAWN_BY)) {
					IBAUtils.createIBA(epm, "s", "DRAWN_BY", user.getFullName());
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

	@Override
	public void transferTo(WTPart part) throws Exception {
		Transaction trs = new Transaction();
		try {
			trs.start();

			WTUser user = (WTUser) SessionHelper.manager.getPrincipal();
			Timestamp today = DateUtils.today();
			System.out.println("PART = " + part.getNumber());
			Representation representation = PublishUtils.getRepresentation(part);

			if (representation != null) {
				PersistenceHelper.manager.delete(representation);
			}

			String DRAWN_BY = IBAUtils.getStringValue(part, "DRAWN_BY");
			String DRAWN_DATE = IBAUtils.getStringValue(part, "DRAWN_DATE");
			if (!StringUtils.isNotNull(DRAWN_DATE)) {
				IBAUtils.createIBA(part, "s", "DRAWN_DATE", today.toString().substring(0, 10));
			}

			if (!StringUtils.isNotNull(DRAWN_BY)) {
				IBAUtils.createIBA(part, "s", "DRAWN_BY", user.getFullName());
			}

			ConfigSpec configspec = null;
			PublishResult rs = Publish.doPublish(false, true, part, configspec, null, false, null, null, 1, null, 2,
					null);
			if (rs.isSuccessful()) {
				System.out.println("Publish Success = " + part.getNumber());
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
