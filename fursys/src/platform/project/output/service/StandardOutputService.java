package platform.project.output.service;

import java.util.ArrayList;
import java.util.Map;

import platform.doc.entity.WTDocumentWTPartLink;
import platform.doc.service.DocumentHelper;
import platform.part.entity.PartColumns;
import platform.project.output.entity.Output;
import platform.project.task.entity.Task;
import platform.util.CommonUtils;
import platform.util.ContentUtils;
import platform.util.IBAUtils;
import platform.util.StringUtils;
import wt.clients.folder.FolderTaskLogic;
import wt.doc.WTDocument;
import wt.doc.WTDocumentTypeInfo;
import wt.fc.PersistenceHelper;
import wt.folder.Folder;
import wt.folder.FolderEntry;
import wt.folder.FolderHelper;
import wt.org.WTPrincipal;
import wt.ownership.Ownership;
import wt.part.WTPart;
import wt.pom.Transaction;
import wt.services.StandardManager;
import wt.session.SessionHelper;
import wt.util.WTException;

public class StandardOutputService extends StandardManager implements OutputService {

	public static StandardOutputService newStandardOutputService() throws WTException {
		StandardOutputService instance = new StandardOutputService();
		instance.initialize();
		return instance;
	}

	@Override
	public Output create(Map<String, Object> params) throws Exception {
		Output output = null;
		String location = (String) params.get("location");
		String name = (String) params.get("name");
		String oid = (String) params.get("oid");
		String description = (String) params.get("description");
		Transaction trs = new Transaction();
		try {
			trs.start();

			Task task = (Task) CommonUtils.persistable(oid);
			WTPrincipal prin = SessionHelper.manager.getPrincipal();
			output = Output.newOutput();
			output.setName(name);
			output.setLocation(location);
			output.setDescription(description);
			output.setTask(task);
			output.setProject(task.getProject());
			output.setTemplate(task.getTemplate());
			output.setOwnership(Ownership.newOwnership(prin));
			PersistenceHelper.manager.save(output);

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
		return output;
	}

	@Override
	public void delete(Map<String, Object> params) throws Exception {
		ArrayList<String> list = (ArrayList<String>) params.get("list");
		Transaction trs = new Transaction();
		try {
			trs.start();

			for (String oid : list) {
				Output output = (Output) CommonUtils.persistable(oid);
				PersistenceHelper.manager.delete(output);
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
	public void disconnect(String oid) throws Exception {
		Transaction trs = new Transaction();
		try {
			trs.start();

			Output output = (Output) CommonUtils.persistable(oid);
			output.setDocument(null);
			PersistenceHelper.manager.modify(output);

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
	public WTDocument direct(Map<String, Object> params) throws Exception {
		String name = (String) params.get("name");
		String company = (String) params.get("company");
		String brand = (String) params.get("brand");
		String primary = (String) params.get("primary");
		String content = (String) params.get("content");
		String location = (String) params.get("location");
		ArrayList<String> secondary = (ArrayList<String>) params.get("secondary");
		ArrayList<PartColumns> partList = (ArrayList<PartColumns>) params.get("partList");
		String oid = (String) params.get("oid");

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

			for (PartColumns map : partList) {
				WTPart part = (WTPart) CommonUtils.persistable(map.getOid());
				WTDocumentWTPartLink link = WTDocumentWTPartLink.newWTDocumentWTPartLink(doc, part);
				PersistenceHelper.manager.save(link);
			}

			Output output = (Output) CommonUtils.persistable(oid);
			output.setDocument(doc);
			PersistenceHelper.manager.modify(output);

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
	public void connect(Map<String, Object> params) throws Exception {
		ArrayList<String> list = (ArrayList<String>) params.get("list");
		String oid = (String) params.get("oid");
		Transaction trs = new Transaction();
		try {

			trs.start();
			Output output = (Output) CommonUtils.persistable(oid);
			for (String s : list) {
				WTDocument doc = (WTDocument) CommonUtils.persistable(s);
				output.setDocument(doc);
				PersistenceHelper.manager.modify(output);
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
	public void modify(Map<String, Object> params) throws Exception {
		Output output = null;
		String location = (String) params.get("location");
		String name = (String) params.get("name");
		String oid = (String) params.get("oid");
		String description = (String) params.get("description");
		Transaction trs = new Transaction();
		try {
			trs.start();

			output = (Output) CommonUtils.persistable(oid);
			output.setName(name);
			output.setLocation(location);
			output.setDescription(description);
			PersistenceHelper.manager.save(output);

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
