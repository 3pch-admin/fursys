package platform.echange.eco.service;

import java.util.ArrayList;

import platform.doc.entity.DocumentColumns;
import platform.ebom.entity.EBOM;
import platform.ebom.entity.EBOMECOLink;
import platform.ebom.entity.EBOMLink;
import platform.ebom.entity.EBOMMaster;
import platform.ebom.entity.EBOMMasterColumns;
import platform.ebom.service.EBOMHelper;
import platform.echange.eco.entity.ECO;
import platform.echange.eco.entity.ECODTO;
import platform.echange.eco.entity.ECOWTDocumentLink;
import platform.echange.eco.entity.ECOWTPartLink;
import platform.epm.service.EpmHelper;
import platform.mbom.entity.MBOM;
import platform.mbom.entity.MBOMECOLink;
import platform.mbom.service.MBOMHelper;
import platform.part.service.PartHelper;
import platform.util.CommonUtils;
import platform.util.ContentUtils;
import platform.util.StringUtils;
import platform.util.service.UtilHelper;
import wt.doc.WTDocument;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.fc.WTReference;
import wt.lifecycle.LifeCycleHelper;
import wt.lifecycle.LifeCycleManaged;
import wt.lifecycle.State;
import wt.org.WTPrincipal;
import wt.ownership.Ownership;
import wt.part.WTPart;
import wt.pom.Transaction;
import wt.services.StandardManager;
import wt.session.SessionHelper;
import wt.util.WTException;

public class StandardECOService extends StandardManager implements ECOService {

	public static StandardECOService newStandardECOService() throws WTException {
		StandardECOService instance = new StandardECOService();
		instance.initialize();
		return instance;
	}

	@Override
	public ECO create(ECODTO params) throws Exception {
		Transaction trs = new Transaction();
		ECO eco = null;
		try {
			trs.start();

			WTPrincipal prin = SessionHelper.manager.getPrincipal();

			String number = ECOHelper.manager.getNextNumber();

			eco = ECO.newECO();
			eco.setName(params.getName());
			eco.setNumber(number);
			eco.setEcoType(params.getEcoType());
			eco.setNotiType(params.getNotiType());
			eco.setBrand(params.getBrand());
			eco.setCompany(params.getCompany());
//			eco.setExpectationTime(DateUtils.startTimestamp(params.getExpectationTime()));
			eco.setDescription(params.getDescription());
			eco.setReason(params.getReason());
			eco.setLot(params.getLot());
			eco.setApplyTime(params.getApplyTime());
			eco.setDevType(params.getDevType());
			eco.setOwnership(Ownership.newOwnership(prin));
			eco.setState("작업중");

			// ㅇ 설계 m 생산
			eco = (ECO) PersistenceHelper.manager.save(eco);

			ContentUtils.updateSecondary(params.getSecondary(), eco);

			for (EBOMMasterColumns map : params.getEbomMasterList()) {
				if (StringUtils.isNotNull(map.getManager())) {

					if ("D".equals(params.getEcoType())) {
						EBOMHelper.service.genRefEbom(map.getOid(), eco);
					} else {
						// 생산
						MBOM mbom = (MBOM) CommonUtils.persistable(map.getOid());
						mbom.setEco(eco);
						PersistenceHelper.manager.modify(mbom);
					}
				}
			}

			ReferenceFactory rf = new ReferenceFactory();
			for (EBOMMasterColumns map : params.getEbomMasterList()) {
				WTReference ref = (WTReference) rf.getReference(map.getOid());
				if (ref.getObject() instanceof EBOMMaster) {
					EBOMMaster ebom = (EBOMMaster) ref.getObject();
					WTPart part = PartHelper.manager.getLatest(ebom.getPart());
					ECOWTPartLink link = ECOWTPartLink.newECOWTPartLink(eco, part); // 저장당시...
					PersistenceHelper.manager.save(link);
				} else if (ref.getObject() instanceof MBOM) {
					MBOM mbom = (MBOM) ref.getObject();
					mbom.setEco(eco);
					PersistenceHelper.manager.modify(mbom);
					ECOWTPartLink link = ECOWTPartLink.newECOWTPartLink(eco, mbom.getPart());
					PersistenceHelper.manager.save(link);
				}
			}

			for (DocumentColumns map : params.getDocList()) {
				WTDocument doc = (WTDocument) CommonUtils.persistable(map.getOid());
				ECOWTDocumentLink link = ECOWTDocumentLink.newECOWTDocumentLink(eco, doc);
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
		return eco;
	}

	@Override
	public ECO modify(ECODTO params) throws Exception {
		Transaction trs = new Transaction();
		try {
			trs.start();

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
		return null;
	}

	@Override
	public ECO delete(String oid) throws Exception {
		ECO eco = null;
		Transaction trs = new Transaction();
		try {
			trs.start();

			eco = (ECO) CommonUtils.persistable(oid);

			ArrayList<ECOWTPartLink> partLinks = ECOHelper.manager.getPartLinks(eco);
			ArrayList<ECOWTDocumentLink> docLinks = ECOHelper.manager.getDocLinks(eco);

			for (ECOWTPartLink link : partLinks) {
				PersistenceHelper.manager.delete(link);
			}

			for (ECOWTDocumentLink link : docLinks) {
				PersistenceHelper.manager.delete(link);
			}
			PersistenceHelper.manager.delete(eco);

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
		return eco;
	}

	@Override
	public void afterAction(ECO eco) throws Exception {
		Transaction trs = new Transaction();
		try {
			trs.start();

			// 설계
			if ("D".equals(eco.getEcoType())) {
				QueryResult result = PersistenceHelper.manager.navigate(eco, "ebom", EBOMECOLink.class);
				while (result.hasMoreElements()) {
					// eco랑 역인 데이터..
					EBOM ebom = (EBOM) result.nextElement();
					ebom.setState(EBOMHelper.EBOM_APPROVED);
					PersistenceHelper.manager.modify(ebom);

					WTPart p = ebom.getPart();
					LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) p, State.toState("RELEASED"));

					// mbom gen...
					if (ebom.getManager() != null) {
						
						EpmHelper.service.afterAction(p);
						UtilHelper.service.transferToDTMG(ebom, eco);
						MBOMHelper.service.generate(ebom);
					}

					ArrayList<EBOMLink> list = EBOMHelper.manager.getAllEBOMLink(ebom);
					for (EBOMLink link : list) {
						EBOM child = link.getChild();
						child = (EBOM) PersistenceHelper.manager.refresh(child);
						child.setState(EBOMHelper.EBOM_APPROVED);
						PersistenceHelper.manager.modify(child);

						WTPart pp = child.getPart();
						pp = (WTPart) PersistenceHelper.manager.refresh(pp);
						LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) pp, State.toState("RELEASED"));
					}
				}

				// dtmg 전송..

			} else if ("M".equals(eco.getEcoType())) {
				QueryResult result = PersistenceHelper.manager.navigate(eco, "mbom", MBOMECOLink.class);
				while (result.hasMoreElements()) {
					MBOM mbom = (MBOM) result.nextElement();
					mbom.setState(MBOMHelper.MBOM_APPROVED);
					PersistenceHelper.manager.modify(mbom);
				}
			}

			trs.commit();
			trs = null;
		} catch (

		Exception e) {
			e.printStackTrace();
			trs.rollback();
			throw e;
		} finally {
			if (trs != null)
				trs.rollback();
		}
	}

	@Override
	public void preAction(ECO eco) throws Exception {
		Transaction trs = new Transaction();
		try {
			trs.start();

			QueryResult result = PersistenceHelper.manager.navigate(eco, "ebom", EBOMECOLink.class);
			while (result.hasMoreElements()) {
				// eco랑 역인 데이터..
				EBOM ebom = (EBOM) result.nextElement();
				ebom.setState(EBOMHelper.EBOM_APPROVAL);
				PersistenceHelper.manager.modify(ebom);

				WTPart p = ebom.getPart();
				LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) p, State.toState("INAPPROVE"));

				ArrayList<EBOMLink> list = EBOMHelper.manager.getAllEBOMLink(ebom);
				for (EBOMLink link : list) {
					EBOM child = link.getChild();
					child = (EBOM) PersistenceHelper.manager.refresh(child);
					child.setState(EBOMHelper.EBOM_APPROVAL);
					PersistenceHelper.manager.modify(child);

					WTPart pp = child.getPart();
					pp = (WTPart) PersistenceHelper.manager.refresh(pp);
					LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) pp, State.toState("INAPPROVE"));
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
