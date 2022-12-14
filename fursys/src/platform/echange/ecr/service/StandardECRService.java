package platform.echange.ecr.service;

import java.util.ArrayList;

import platform.doc.entity.DocumentColumns;
import platform.echange.ecr.entity.ECR;
import platform.echange.ecr.entity.ECRDTO;
import platform.echange.ecr.entity.ECRWTDocumentLink;
import platform.echange.ecr.entity.ECRWTPartLink;
import platform.part.entity.PartColumns;
import platform.util.CommonUtils;
import platform.util.ContentUtils;
import platform.util.DateUtils;
import wt.doc.WTDocument;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.org.WTPrincipal;
import wt.ownership.Ownership;
import wt.part.WTPart;
import wt.pom.Transaction;
import wt.services.StandardManager;
import wt.session.SessionHelper;
import wt.util.WTException;

public class StandardECRService extends StandardManager implements ECRService {

	public static StandardECRService newStandardECRService() throws WTException {
		StandardECRService instance = new StandardECRService();
		instance.initialize();
		return instance;
	}

	@Override
	public ECR create(ECRDTO params) throws Exception {
		String name = params.getName();
		String company = params.getCompany();
		String brand = params.getBrand();
		String reqType = params.getReqType();
		String reason = params.getReason();
		String description = params.getDescription();
		String limit = params.getLimit();
		String primary = params.getPrimary();
		ArrayList<String> secondary = params.getSecondary();
		ECR ecr = null;
		Transaction trs = new Transaction();
		try {
			trs.start();

			WTPrincipal prin = SessionHelper.manager.getPrincipal();

			ecr = ECR.newECR();
			ecr.setNumber(ECRHelper.manager.getNextNumber());
			ecr.setName(name);
			ecr.setState("작업중");
			ecr.setReason(reason);
			ecr.setDescription(description);
			ecr.setReqType(reqType);
			ecr.setLimit(DateUtils.endTimestamp(limit));
			ecr.setOwnership(Ownership.newOwnership(prin));
			ecr.setBrand(brand);
			ecr.setCompany(company);

			ecr = (ECR) PersistenceHelper.manager.save(ecr);

			ContentUtils.updateSecondary(secondary, ecr);

			for (PartColumns data : params.getPartList()) {
				WTPart part = (WTPart) CommonUtils.persistable(data.getOid());
				ECRWTPartLink link = ECRWTPartLink.newECRequestWTPartLink(ecr, part);
				PersistenceHelper.manager.save(link);
			}

			for (DocumentColumns data : params.getDocList()) {
				WTDocument doc = (WTDocument) CommonUtils.persistable(data.getOid());
				ECRWTDocumentLink link = ECRWTDocumentLink.newECRequestWTDocumentLink(ecr, doc);
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
		return ecr;
	}

	@Override
	public ECR modify(ECRDTO params) throws Exception {
		Transaction trs = new Transaction();
		ECR ecr = null;
		try {
			trs.start();

			WTPrincipal prin = SessionHelper.manager.getPrincipal();

			ecr = (ECR) CommonUtils.persistable(params.getOid());
			ecr.setNumber(ECRHelper.manager.getNextNumber());
			ecr.setName(params.getName());
			ecr.setBrand(params.getBrand());
			ecr.setState("작업 중");
			ecr.setCompany(params.getCompany());
			ecr.setReason(params.getReason());
			ecr.setDescription(params.getDescription());
			ecr.setReqType(params.getReqType());
			ecr.setLimit(DateUtils.endTimestamp(params.getLimit()));
			ecr.setOwnership(Ownership.newOwnership(prin));

			ecr = (ECR) PersistenceHelper.manager.modify(ecr);

//			ContentUtils.updateSecondary(secondary, ecr);

//			for (String oid : poids) {
//				WTPart part = (WTPart) CommonUtils.persistable(oid);
//				ECRWTPartLink link = ECRWTPartLink.newECRequestWTPartLink(ecr, part);
//				PersistenceHelper.manager.save(link);
//			}
//
//			for (String oid : doids) {
//				WTDocument doc = (WTDocument) CommonUtils.persistable(oid);
//				ECRWTDocumentLink link = ECRWTDocumentLink.newECRequestWTDocumentLink(ecr, doc);
//				PersistenceHelper.manager.save(link);
//			}

//			if (appOid.size() > 0) {
//				ApprovalHelper.service.submit(ecr, appOid, refOid, applimit);
//			}

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
	public ECR delete(String oid) throws Exception {
		ECR ecr = null;
		Transaction trs = new Transaction();
		try {

			ecr = (ECR) CommonUtils.persistable(oid);

			QueryResult result = PersistenceHelper.manager.navigate(ecr, "part", ECRWTPartLink.class, false);
			while (result.hasMoreElements()) {
				ECRWTPartLink link = (ECRWTPartLink) result.nextElement();
				PersistenceHelper.manager.delete(link);
			}

			result.reset();
			result = PersistenceHelper.manager.navigate(ecr, "doc", ECRWTDocumentLink.class, false);
			while (result.hasMoreElements()) {
				ECRWTDocumentLink link = (ECRWTDocumentLink) result.nextElement();
				PersistenceHelper.manager.delete(link);
			}

			ecr = (ECR) PersistenceHelper.manager.delete(ecr);

		} catch (Exception e) {
			e.printStackTrace();
			trs.rollback();
			throw e;
		} finally {
			if (trs != null)
				trs.rollback();
		}
		return ecr;
	}
}
