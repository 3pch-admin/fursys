package platform.echange.ecn.service;

import java.util.ArrayList;

import platform.doc.entity.DocumentColumns;
import platform.echange.ecn.entity.ECN;
import platform.echange.ecn.entity.ECNDTO;
import platform.echange.ecn.entity.ECNWTDocumentLink;
import platform.echange.ecn.entity.ECNWTPartLink;
import platform.echange.eco.entity.ECO;
import platform.erp.service.ERPHelper;
import platform.mbom.entity.MBOM;
import platform.mbom.entity.MBOMECOLink;
import platform.part.entity.PartColumns;
import platform.util.CommonUtils;
import platform.util.ContentUtils;
import platform.util.DateUtils;
import platform.util.entity.DTMG;
import wt.doc.WTDocument;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.org.WTPrincipal;
import wt.org.WTUser;
import wt.ownership.Ownership;
import wt.part.WTPart;
import wt.pom.Transaction;
import wt.services.StandardManager;
import wt.session.SessionHelper;
import wt.util.WTException;
import wt.util.WTProperties;

public class StandardECNService extends StandardManager implements ECNService {

	public static StandardECNService newStandardECNService() throws WTException {
		StandardECNService instance = new StandardECNService();
		instance.initialize();
		return instance;
	}

	@Override
	public ECN create(ECNDTO params) throws Exception {
		Transaction trs = new Transaction();
		ECN ecn = null;
		ArrayList<String> secondary = params.getSecondary();
		try {
			trs.start();

			WTPrincipal prin = SessionHelper.manager.getPrincipal();
			ECO eco = (ECO) CommonUtils.persistable(params.getEoid());
			ecn = ECN.newECN();
			ecn.setNumber(ECNHelper.manager.getNextNumber());
			ecn.setName(params.getName());
			ecn.setBrand(params.getBrand());
			ecn.setCompany(params.getCompany());
			ecn.setPlant(params.getPlant());
			ecn.setEco(eco);
			ecn.setNotiType(params.getNotiType());
			ecn.setEcnApplyTime(params.getApplyTime());
			ecn.setApplicationDate(params.getApplicationDate());
			ecn.setState("작업중");
			ecn.setOwnership(Ownership.newOwnership(prin));

			WTUser user = (WTUser) prin;
			ecn.setModifier(user.getName()); // ID???
			ecn.setModifiedDate(DateUtils.today());

			ecn = (ECN) PersistenceHelper.manager.save(ecn);

			ContentUtils.updateSecondary(secondary, ecn);

			QueryResult result = PersistenceHelper.manager.navigate(eco, "mbom", MBOMECOLink.class);
			while (result.hasMoreElements()) {
				MBOM mbom = (MBOM) result.nextElement();
				mbom.setEcn(ecn);
				PersistenceHelper.manager.modify(mbom);
			}

			for (PartColumns map : params.getPartList()) {
				WTPart part = (WTPart) CommonUtils.persistable(map.getOid());
				ECNWTPartLink link = ECNWTPartLink.newECNWTPartLink(ecn, part);
				PersistenceHelper.manager.save(link);
			}

			for (DocumentColumns map : params.getDocList()) {
				WTDocument doc = (WTDocument) CommonUtils.persistable((String) map.getOid());
				ECNWTDocumentLink link = ECNWTDocumentLink.newECNWTDocumentLink(ecn, doc);
				PersistenceHelper.manager.save(link);
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
		return ecn;
	}

	@Override
	public ECN modify(ECNDTO params) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ECN delete(String oid) throws Exception {
		ECN ecn = null;
		Transaction trs = new Transaction();
		try {
			trs.start();

			ecn = (ECN) CommonUtils.persistable(oid);

			ArrayList<ECNWTDocumentLink> docLinks = ECNHelper.manager.getDocLinks(ecn);

			for (ECNWTDocumentLink link : docLinks) {
				PersistenceHelper.manager.delete(link);
			}
			PersistenceHelper.manager.delete(ecn);

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
		return ecn;
	}

	@Override
	public void afterAction(ECN ecn) throws Exception {
//		Connection con = null;
		Transaction trs = new Transaction();
		try {
			trs.start();

			ERPHelper.service.sendErpToECN(ecn);
			ERPHelper.service.sendErpToPart(ecn);
			ERPHelper.service.sendErpToBom(ecn);

			trs.commit();
			trs = null;
		} catch (Exception e) {
			e.printStackTrace();
			trs.rollback();
			throw e;
		} finally {
			if (trs != null)
				trs.rollback();
//			DBCPManager.freeConnection(con, null, null);
		}
	}
}
