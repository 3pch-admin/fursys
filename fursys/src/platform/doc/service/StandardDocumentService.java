package platform.doc.service;

import java.util.ArrayList;
import java.util.Map;

import platform.approval.entity.ApprovalMaster;
import platform.approval.service.ApprovalHelper;
import platform.doc.entity.DocumentDTO;
import platform.doc.entity.WTDocumentWTPartLink;
import platform.part.entity.PartColumns;
import platform.util.CommonUtils;
import platform.util.ContentUtils;
import platform.util.IBAUtils;
import platform.util.StringUtils;
import wt.clients.folder.FolderTaskLogic;
import wt.clients.vc.CheckInOutTaskLogic;
import wt.doc.WTDocument;
import wt.doc.WTDocumentMaster;
import wt.doc.WTDocumentMasterIdentity;
import wt.doc.WTDocumentTypeInfo;
import wt.fc.IdentityHelper;
import wt.fc.PersistenceHelper;
import wt.folder.Folder;
import wt.folder.FolderEntry;
import wt.folder.FolderHelper;
import wt.org.WTUser;
import wt.part.WTPart;
import wt.pom.Transaction;
import wt.services.StandardManager;
import wt.session.SessionHelper;
import wt.util.WTException;
import wt.vc.VersionControlHelper;
import wt.vc.wip.CheckoutLink;
import wt.vc.wip.WorkInProgressHelper;

public class StandardDocumentService extends StandardManager implements DocumentService {

	public static StandardDocumentService newStandardDocumentService() throws WTException {
		StandardDocumentService instance = new StandardDocumentService();
		instance.initialize();
		return instance;
	}

	@Override
	public WTDocument create(DocumentDTO params) throws Exception {
		String name = params.getName();
		String company = params.getCompany();
		String brand = params.getBrand();
		String primary = params.getPrimary();
		String content = params.getContent();
		String location = params.getLocation();
		ArrayList<String> secondary = params.getSecondary();
		WTDocument doc = null;
		Transaction trs = new Transaction();
		try {

			trs.start();

			WTDocumentTypeInfo info = WTDocumentTypeInfo.newWTDocumentTypeInfo();
			info.setPtc_rht_1(content);

			doc = WTDocument.newWTDocument();
			doc.setName(name);
			doc.setTypeInfoWTDocument(info);
			doc.setNumber(DocumentHelper.manager.getNextNumber());

			if (!StringUtils.isNotNull(location)) {
				location = DocumentHelper.ROOT;
			}

			Folder folder = FolderTaskLogic.getFolder(location, CommonUtils.getContainer("DOCUMENT"));
			FolderHelper.assignLocation((FolderEntry) doc, folder);

			PersistenceHelper.manager.save(doc);

			ContentUtils.updatePrimary(primary, doc);
			ContentUtils.updateSecondary(secondary, doc);

			// 브랜드
			IBAUtils.createIBA(doc, "s", "BRAND_CODE", brand);
			// 회사
			IBAUtils.createIBA(doc, "s", "COMPANY_CODE", company);

			for (PartColumns map : params.getPartList()) {
				WTPart part = (WTPart) CommonUtils.persistable(map.getOid());
				WTDocumentWTPartLink link = WTDocumentWTPartLink.newWTDocumentWTPartLink(doc, part);
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
		return doc;
	}

	@Override
	public WTDocument modify(DocumentDTO params) throws Exception {
		String oid = params.getOid();
		String name = params.getName();
		String company = params.getCompany();
		String brand = params.getBrand();
		String primary = params.getPrimary();
		String content = params.getContent();
		String location = params.getLocation();
		ArrayList<String> secondary = params.getSecondary();
		WTDocument doc = null;
		Transaction trs = new Transaction();
		try {
			trs.start();

			WTDocumentTypeInfo info = WTDocumentTypeInfo.newWTDocumentTypeInfo();
			info.setPtc_rht_1(content); // 라온케이 템플릿 내용

			doc = (WTDocument) CommonUtils.persistable(oid);

			Folder cFolder = CheckInOutTaskLogic.getCheckoutFolder();
			CheckoutLink clink = WorkInProgressHelper.service.checkout(doc, cFolder, "문서 수정 체크 아웃");
			doc = (WTDocument) clink.getWorkingCopy(); // 복사본

			WTDocumentMaster master = (WTDocumentMaster) doc.getMaster();
			WTDocumentMasterIdentity identity = (WTDocumentMasterIdentity) master.getIdentificationObject();
			identity.setName(name);
			master = (WTDocumentMaster) IdentityHelper.service.changeIdentity(master, identity);

			// 라온케이
			doc.setTypeInfoWTDocument(info);

			WTUser user = (WTUser) SessionHelper.manager.getPrincipal();
			String msg = user.getFullName() + " 사용자가 문서를 수정 하였습니다.";
			// 필요하면 수정 사유로 대체
			doc = (WTDocument) WorkInProgressHelper.service.checkin(doc, msg);

			// 브랜드
			IBAUtils.createIBA(doc, "s", "BRAND_CODE", brand);
			// 회사
			IBAUtils.createIBA(doc, "s", "COMPANY_CODE", company);

			if (!StringUtils.isNotNull(location)) {
				location = DocumentHelper.ROOT;
			}

			Folder folder = FolderTaskLogic.getFolder(location, CommonUtils.getContainer("DOCUMENT"));
			FolderHelper.service.changeFolder((FolderEntry) doc, folder);

			ContentUtils.updatePrimary(primary, doc);
			ContentUtils.updateSecondary(secondary, doc);

			for (PartColumns map : params.getPartList()) {
				WTPart part = (WTPart) CommonUtils.persistable(map.getOid());
				WTDocumentWTPartLink link = WTDocumentWTPartLink.newWTDocumentWTPartLink(doc, part);
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
		return doc;
	}

	@Override
	public WTDocument delete(String oid) throws Exception {
		WTDocument doc = null;
		Transaction trs = new Transaction();
		try {

			doc = (WTDocument) CommonUtils.persistable(oid);

			ArrayList<WTDocumentWTPartLink> partLinks = DocumentHelper.manager.getPartLinks(doc);
			for (WTDocumentWTPartLink link : partLinks) {
				PersistenceHelper.manager.delete(link);
			}

			doc = (WTDocument) PersistenceHelper.manager.delete(doc);

		} catch (Exception e) {
			e.printStackTrace();
			trs.rollback();
			throw e;
		} finally {
			if (trs != null)
				trs.rollback();
		}
		return doc;
	}

	@Override
	public WTDocument revise(DocumentDTO params) throws Exception {
		String oid = params.getOid();
		WTDocument doc = null;
		Transaction trs = new Transaction();
		try {

			doc = (WTDocument) CommonUtils.persistable(oid);

			WTUser user = (WTUser) SessionHelper.manager.getPrincipal();

			doc = (WTDocument) VersionControlHelper.service.newVersion(doc);

			VersionControlHelper.setNote(doc, "사용자 " + user.getFullName() + "가 기술문서를 개정함");

			doc = (WTDocument) PersistenceHelper.manager.save(doc);

		} catch (Exception e) {
			e.printStackTrace();
			trs.rollback();
			throw e;
		} finally {
			if (trs != null)
				trs.rollback();
		}
		return doc;
	}
}
