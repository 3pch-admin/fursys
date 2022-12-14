package platform.util.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import platform.ebom.entity.EBOM;
import platform.ebom.entity.EBOMLink;
import platform.ebom.entity.EBOMMaster;
import platform.ebom.entity.EBOMMasterLink;
import platform.ebom.service.EBOMHelper;
import platform.ebom.service.EBOMMasterHelper;
import platform.part.service.PartHelper;
import platform.util.CommonUtils;
import platform.util.IBAUtils;
import platform.util.StringUtils;
import platform.util.entity.DTMG;
import platform.util.entity.FileDTO;
import wt.clients.folder.FolderTaskLogic;
import wt.content.ApplicationData;
import wt.content.ContentHelper;
import wt.content.ContentItem;
import wt.content.ContentRoleType;
import wt.content.ContentServerHelper;
import wt.epm.EPMDocument;
import wt.fc.PersistenceHelper;
import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult;
import wt.folder.Folder;
import wt.folder.FolderEntry;
import wt.folder.FolderHelper;
import wt.folder.SubFolder;
import wt.fv.FileFolder;
import wt.fv.FvHelper;
import wt.fv.FvMount;
import wt.fv.FvProperties;
import wt.fv.FvTransaction;
import wt.fv.FvVault;
import wt.fv.StandardFvService;
import wt.fv.StoreStreamListener;
import wt.fv.StoredItem;
import wt.fv.Vault;
import wt.fv.master.ReplicaVault;
import wt.fv.uploadtocache.BackupedFile;
import wt.fv.uploadtocache.CacheDescriptor;
import wt.fv.uploadtocache.CachedContentDescriptor;
import wt.fv.uploadtocache.UploadToCacheHelper;
import wt.lifecycle.State;
import wt.method.MethodContext;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.objectstorage.ContentFileWriter;
import wt.objectstorage.ContentManagerFactory;
import wt.objectstorage.ContentStorageManager;
import wt.objectstorage.exception.ContentFileAlreadyExistsException;
import wt.objectstorage.exception.ContentFileCanNotBeStoredException;
import wt.part.WTPart;
import wt.part.WTPartHelper;
import wt.part.WTPartStandardConfigSpec;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.services.ServiceFactory;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.session.SessionServerHelper;
import wt.util.EncodingConverter;
import wt.util.WTException;
import wt.vc.views.View;
import wt.vc.views.ViewHelper;

public class UtilHelper implements RemoteAccess {

	public static final UtilService service = ServiceFactory.getService(UtilService.class);
	public static final UtilHelper manager = new UtilHelper();

	public JSONArray tree(String location, String context) throws Exception {
		if (!StringUtils.isNotNull(context)) {
			context = "FURSYS";
		}
		Folder f = FolderTaskLogic.getFolder(location, CommonUtils.getContainer(context));
		JSONArray jsonArray = new JSONArray();
		JSONObject rootNode = new JSONObject();
		rootNode.put("key", f.getPersistInfo().getObjectIdentifier().getStringValue());
		rootNode.put("title", f.getName());
		rootNode.put("type", "root");
		rootNode.put("folder", true);
		rootNode.put("location", f.getFolderPath());
		rootNode.put("expanded", true);
		tree(f, rootNode);
		jsonArray.add(rootNode);
		return jsonArray;
	}

	private void tree(Folder f, JSONObject rootNode) throws Exception {
		Enumeration children = FolderTaskLogic.getSubFolders(f);
		JSONArray jsonChildren = new JSONArray();
		while (children.hasMoreElements()) {
			Folder child = (Folder) children.nextElement();
			JSONObject node = new JSONObject();
			node.put("type", "childrens");
			node.put("key", child.getPersistInfo().getObjectIdentifier().getStringValue());
			node.put("title", child.getName());
			node.put("expanded", false);
			node.put("location", child.getFolderPath());
			node.put("folder", true);
			tree(child, node);
			jsonChildren.add(node);
		}
		rootNode.put("children", jsonChildren);
	}
	
	public JSONObject createFolder(String location, String context) throws Exception {
		System.out.println("###ui helper--createFolder");
		JSONObject reValue = new JSONObject();
		String nFolderName = "새폴더";
		
		try {
			 Folder parentFolder = FolderHelper.service.getFolder(location, CommonUtils.getContainer(context));
			 SubFolder subfolder = SubFolder.newSubFolder(nFolderName);
			 
			FolderHelper.assignLocation((FolderEntry) subfolder, parentFolder);
			reValue.put("result", "SUCCESS");
		}catch (Exception e) {
			reValue.put("result", "FAIL");
		}
		return reValue;
	}
	
	public JSONObject updateFolder(String location, String context) throws Exception {
		System.out.println("###ui helper--updateFolder");
		JSONObject reValue = new JSONObject();
		String nFolderName = "새폴더";
		
		 Folder parentFolder = FolderHelper.service.getFolder(location, CommonUtils.getContainer(context));
		
		//availableFolder(context);
		
		 SubFolder subfolder = SubFolder.newSubFolder(nFolderName);
		 
		FolderHelper.assignLocation((FolderEntry) subfolder, parentFolder);
		
		return reValue;
	}
	
	public JSONObject deleteFolder(String location, String context) throws Exception {
		System.out.println("###ui helper--deleteFolder");
		JSONObject reValue = new JSONObject();
		String nFolderName = "새폴더";
		
		try {
			 Folder parentFolder = FolderHelper.service.getFolder(location, CommonUtils.getContainer(context));
			
			//availableFolder(context);
			
			 SubFolder subfolder = SubFolder.newSubFolder(nFolderName);
			 
			FolderHelper.assignLocation((FolderEntry) subfolder, parentFolder);
			reValue.put("result", "SUCCESS");
		}catch (Exception e) {
			reValue.put("result", "FAIL");
		}
		return reValue;
	}
	
	public static boolean availableFolder(String s){
        boolean exist = false;
        Folder folder = null;
        try
        {
        	folder = FolderHelper.service.getFolder(s, CommonUtils.getContainer(s));
            if (folder != null)
                exist = true;
            else
                return false;
        }
        catch (Exception e)
        {}
        return exist;
    }

	public static CacheDescriptor getCacheDescriptor(int number, boolean bool, String userId, String masterHost)
			throws Exception {
		URL url = new URL(masterHost);
		RemoteMethodServer server = RemoteMethodServer.getInstance(url);
		Class argTypes[] = new Class[] { int.class, boolean.class, String.class };
		Object args[] = new Object[] { number, bool, userId };
		return (CacheDescriptor) server.invoke("_getCacheDescriptor", UtilHelper.class.getName(), null, argTypes, args);
	}

	public static CacheDescriptor _getCacheDescriptor(int number, boolean bool, String userId) {
		boolean _bool = SessionServerHelper.manager.setAccessEnforced(false);
		SessionContext sessioncontext = SessionContext.newContext();
		try {
			SessionHelper.manager.setAuthenticatedPrincipal(userId);
			return _getCacheDescriptor(number, bool);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SessionContext.setContext(sessioncontext);
			SessionServerHelper.manager.setAccessEnforced(_bool);
		}
		return null;
	}

	public static CachedContentDescriptor doUploadToCache(CacheDescriptor localCacheDs, File file, boolean isMainServer)
			throws Exception {
		CachedContentDescriptor descriptor = null;
		try {
			long folderId = localCacheDs.getFolderId();
			long fileName = localCacheDs.getFileNames()[0];
			long vaultId = localCacheDs.getVaultId();
			long streamId = localCacheDs.getStreamIds()[0];
			InputStream[] streams = new InputStream[1];
			streams[0] = new FileInputStream(file);
			long[] fileSize = new long[1];
			fileSize[0] = file.length();
			String[] paths = new String[1];
			paths[0] = file.getPath();
			if (isMainServer) {
				Vault vault = UtilHelper.getLocalVault(vaultId);
				storeContentInVaultOnMaster(vault, folderId, fileName, 0, streams[0], fileSize);
			} else {
//				storeContentInVaultOnRemote(masterUrl, vaultId, folderId, fileName, 0, streams[0], fileSize);
			}
			descriptor = new CachedContentDescriptor(streamId, folderId, fileSize[0], 0, file.getPath());
		} catch (FileNotFoundException e) {
			throw new WTException(e);
		}
		return descriptor;
	}

	private static long storeContentInVaultOnMaster(Vault vault, long folderId, long contentFileName,
			long contentStreamId, InputStream contentStream, long[] contentSize) throws WTException {
		long arg22 = 1;
		boolean current = SessionServerHelper.manager.setAccessEnforced(false);
		FileFolder folder = null;
		StoreStreamListener listener = new StoreStreamListener();
		FvTransaction trx = new FvTransaction();
		try {
			trx.start();
			trx.addTransactionListener(listener);
			if (vault == null) {
			}
			listener.prepareToGetFolder(vault);
			SessionContext previous = SessionContext.newContext();
			try {
				SessionHelper.manager.setAdministrator();
				folder = StandardFvService.getActiveFolder(vault);
			} finally {
				SessionContext.setContext(previous);
			}
			if (folder == null) {

			}
			listener.informGotFolderOk();
			FvMount mount = StandardFvService.getLocalMount(folder);
			if (mount == null) {

			}
			String path = mount.getPath();
			String fn = StoredItem.buildFileName(contentFileName);
			String mountType = FvHelper.service.getMountType(mount);
			BackupedFile contentFile = new BackupedFile(mountType, path, fn);
			try {
				listener.prepareToUpload(folder, contentFile, path, fn);
			} catch (ContentFileCanNotBeStoredException arg37) {
				throw arg37;
			}
			ContentStorageManager arg18 = ContentManagerFactory.getContentManager(mountType);
			ContentFileWriter arg19 = arg18.getContentFileWriter(contentFile.getFirstContentFile());
			long arg20 = -1L;
			try {
				MethodContext.releaseInactiveConnectionStatic();
			} catch (Exception arg39) {
				if (arg39 instanceof WTException) {
					throw (WTException) arg39;
				}
				throw new WTException(arg39);
			}
			try {
				arg20 = arg19.storeStream(contentStream, contentFile, contentSize[0], false);
				if (contentSize[0] <= 0L) {
					contentSize[0] = arg20;
				}
			} catch (ContentFileAlreadyExistsException arg40) {
				listener.setContentFile((BackupedFile) null);
				throw arg40;
			} catch (ContentFileCanNotBeStoredException arg41) {
				if (arg19.isWriteException()) {
				}
				throw arg41;
			}
			trx.commit();
			trx = null;
			arg22 = PersistenceHelper.getObjectIdentifier(folder).getId();
		} finally {
			if (trx != null) {
				trx.rollback();
			}
			SessionServerHelper.manager.setAccessEnforced(current);
		}
		return arg22;
	}

	public static CacheDescriptor _getCacheDescriptor(int number, boolean bool) throws Exception {
		CacheDescriptor localCacheDescriptor = UploadToCacheHelper.service.getCacheDescriptor(number, bool);
		UploadToCacheHelper.service.getCacheDescriptor(number, bool);
		return localCacheDescriptor;
	}

	public static Vault getLocalVault(long paramLong) throws Exception {
		Class localReplicaVault = (FvProperties.FORCE_ONE_VAULT) ? FvVault.class : ReplicaVault.class;
		QuerySpec localQuerySpec = new QuerySpec(localReplicaVault);
		localQuerySpec.appendWhere(
				new SearchCondition(Vault.class, "thePersistInfo.theObjectIdentifier.id", "=", paramLong), new int[0]);
		QueryResult localQueryResult = PersistenceServerHelper.manager.query(localQuerySpec);
		if (!(localQueryResult.hasMoreElements())) {
			return null;
		}
		Vault localVault = (Vault) localQueryResult.nextElement();
		return localVault;
	}

	public File getFileFromCacheId(String cacheId) throws WTException {
		System.out.println("cacheId=" + cacheId);
		File uploadedFile = null;
		if (StringUtils.isNotNull(cacheId)) {
			String tmp = cacheId.split("/")[0];
			EncodingConverter localEncodingConverter = new EncodingConverter();
			String str = localEncodingConverter.decode(tmp);
			System.out.println("str=" + str);

			String[] arrayOfString = str.split(":");
			String cachePath = arrayOfString[arrayOfString.length - 2] + ":" + arrayOfString[arrayOfString.length - 1];
			System.out.println("cachePath=" + cachePath);
			uploadedFile = new File(cachePath);
			Long.parseLong(arrayOfString[0]);
		}
		return uploadedFile;
	}

	public static ArrayList<Folder> getSubFolders(Folder root, ArrayList<Folder> folders) throws Exception {
		QueryResult result = FolderHelper.service.findSubFolders(root);
		while (result.hasMoreElements()) {
			SubFolder sub = (SubFolder) result.nextElement();
			folders.add(sub);
			getSubFolders(sub, folders);
		}
		return folders;
	}

	public void dtmgPart(WTPart rootPart, Set<WTPart> list) throws Exception {
		String viewName = rootPart.getViewName();
		if (!StringUtils.isNotNull(viewName)) {
			viewName = "Design";
		}
		View view = ViewHelper.service.getView(viewName);
//		State state = rootPart.getLifeCycleState();
		WTPartStandardConfigSpec configSpec = WTPartStandardConfigSpec.newWTPartStandardConfigSpec(view, null);
		QueryResult result = WTPartHelper.service.getUsesWTParts(rootPart, configSpec);

		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			if (!(obj[1] instanceof WTPart)) {
				continue;
			}
			WTPart childPart = (WTPart) obj[1];
			list.add(childPart);
			dtmgPart(childPart, list);
		}
	}

	public void getAllParts(EBOMMaster header, String color, Set<FileDTO> list) throws Exception {
		ArrayList<EBOMMasterLink> link = EBOMMasterHelper.manager.getAllEBOMMasterLink(header);
		System.out.println("link=" + link.size());
		for (EBOMMasterLink l : link) {
			WTPart p = PartHelper.manager.getLatest(l.getChild().getPart());
			EPMDocument epm = PartHelper.manager.getEPMDocument(p);

			if (epm != null) {
				QueryResult qr = ContentHelper.service.getContentsByRole(epm, ContentRoleType.PRIMARY);
				if (qr.hasMoreElements()) {
					ContentItem item = (ContentItem) qr.nextElement();
					if (item instanceof ApplicationData) {
						ApplicationData data = (ApplicationData) item;

						String erpCode = IBAUtils.getStringValue(epm, "ERP_CODE");
						String finish_code = IBAUtils.getStringValue(epm, "FINISH_CODE");
						String material_code = IBAUtils.getStringValue(epm, "MATERIAL_CODE");

						FileDTO dto = new FileDTO(epm.getCADName(),
								ContentServerHelper.service.findLocalContentStream(data), data.getFileSize(),
								p.getNumber(), p.getVersionInfo().getIdentifier().getSeries().getValue(),
								l.getChild().getApplyColor(), erpCode, finish_code, material_code);

						// 기존 데이터 없을시..
						if (!list.contains(dto)) {
							list.add(dto);
						}
					}
				}
			}
		}
	}

	public boolean exist(String erpCode, String color, String version, String ecoNo) throws Exception {
		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(DTMG.class, true);

		SearchCondition sc = new SearchCondition(DTMG.class, DTMG.ERP_CODE, "=", erpCode);
		query.appendWhere(sc, new int[] { idx });
		query.appendAnd();

		sc = new SearchCondition(DTMG.class, DTMG.COLOR, "=", color);
		query.appendWhere(sc, new int[] { idx });
		query.appendAnd();

		sc = new SearchCondition(DTMG.class, DTMG.VERSION, "=", version);
		query.appendWhere(sc, new int[] { idx });
		query.appendAnd();

		sc = new SearchCondition(DTMG.class, DTMG.ECO_NUMBER, "=", ecoNo);
		query.appendWhere(sc, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find(query);
		return result.size() > 0;
	}
}
