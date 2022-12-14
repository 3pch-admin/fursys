package platform.raonk.service;

import platform.raonk.entity.Raonk;
import platform.raonk.entity.RaonkDTO;
import platform.util.CommonUtils;
import wt.fc.PersistenceHelper;
import wt.folder.Folder;
import wt.folder.FolderHelper;
import wt.org.WTPrincipal;
import wt.ownership.Ownership;
import wt.pom.Transaction;
import wt.services.StandardManager;
import wt.session.SessionHelper;
import wt.util.WTException;

public class StandardRaonkService extends StandardManager implements RaonkService {

	public static StandardRaonkService newStandardRaonkService() throws WTException {
		StandardRaonkService instance = new StandardRaonkService();
		instance.initialize();
		return instance;
	}

	@Override
	public Raonk create(RaonkDTO params) throws Exception {
		String name = params.getName();
		String contents = params.getContents();
//		String location = params.getLocation();
		Raonk raonk = null;
		Transaction trs = new Transaction();
		try {

			WTPrincipal prin = SessionHelper.manager.getPrincipal();

			raonk = Raonk.newRaonk();
			raonk.setName(name);
			raonk.setOwnership(Ownership.newOwnership(prin));
			raonk.setContents(contents);
			raonk.setEnable(true);

//			Folder folder = FolderHelper.service.getFolder(location, CommonUtils.getContainer("FURSYS"));
//			raonk.setFolder(folder);

			PersistenceHelper.manager.save(raonk);

		} catch (Exception e) {
			e.printStackTrace();
			trs.rollback();
			throw e;
		} finally {
			if (trs != null)
				trs.rollback();
		}
		return raonk;
	}

	@Override
	public Raonk modify(RaonkDTO params) throws Exception {
		String oid = params.getOid();
		String name = params.getName();
		String contents = params.getContents();
		String enable = params.getEnable();
		String location = params.getLocation();
		Raonk raonk = null;
		Transaction trs = new Transaction();
		try {
			trs.start();
			raonk = (Raonk) CommonUtils.persistable(oid);
			
			
			raonk.setName(name);
//			raonk.setRaonkType(raonkType);
			raonk.setContents(contents);
//			raonk.setDescription(description);
			raonk.setEnable(Boolean.parseBoolean(enable));
//			Folder folder = FolderHelper.service.getFolder(location, CommonUtils.getContainer("FURSYS"));
//			raonk.setFolder(folder);
			PersistenceHelper.manager.modify(raonk);

			trs.commit();
			trs=null;
			
			
		} catch (Exception e) {
			e.printStackTrace();
			trs.rollback();
			throw e;
		} finally {
			if (trs != null)
				trs.rollback();
		}
		return raonk;
	}

	@Override
	public Raonk delete(String oid) throws Exception {
		Raonk raonk = null;
		Transaction trs = new Transaction();
		try {

			raonk = (Raonk) CommonUtils.persistable(oid);
			PersistenceHelper.manager.delete(raonk);

		} catch (Exception e) {
			e.printStackTrace();
			trs.rollback();
			throw e;
		} finally {
			if (trs != null)
				trs.rollback();
		}
		return raonk;
	}
}
