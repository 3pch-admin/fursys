package platform.ebom.service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ptc.windchill.uwgm.soap.uwgm.UwgmTransactionResult;

import platform.ebom.entity.EBOM;
import platform.ebom.entity.EBOMLink;
import platform.ebom.vo.BOMTreeNode;
import platform.util.CommonUtils;
import platform.util.StringUtils;
import wt.fc.PersistenceHelper;
import wt.log4j.LogR;
import wt.org.WTPrincipal;
import wt.ownership.Ownership;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.part.WTPartUsageLink;
import wt.pom.Transaction;
import wt.services.StandardManager;
import wt.session.SessionHelper;
import wt.util.WTException;

public class StandardEBOMService extends StandardManager implements EBOMService {

	public static StandardEBOMService newStandardEBOMService() throws WTException {
		StandardEBOMService instance = new StandardEBOMService();
		instance.initialize();
		return instance;
	}

	@Override
	public EBOM create(Map<String, Object> params) throws Exception {
		EBOM header = null;
		String oid = (String) params.get("oid");
		String json = (String) params.get("json");
		Transaction trs = new Transaction();
		try {
			trs.start();

			WTPrincipal prin = SessionHelper.manager.getPrincipal();

			WTPart part = (WTPart) CommonUtils.persistable(oid);
			WTPartMaster master = part.getMaster();

			String jsonStr = new String(DatatypeConverter.parseBase64Binary(json), "UTF-8");
			Type listType = new TypeToken<ArrayList<BOMTreeNode>>() {
			}.getType();

			Gson gson = new Gson();
			List<BOMTreeNode> nodes = gson.fromJson(jsonStr, listType);

			for (BOMTreeNode node : nodes) {

				header = EBOMHelper.manager.getEBOM(master);
				if (header == null) {
					header = EBOM.newEBOM();
					header.setWtpartMaster(master);
					header.setBomType(EBOMHelper.HEADER);
					header.setState(EBOMHelper.EBOM_TEMP);
					header.setOwnership(Ownership.newOwnership(prin));
					PersistenceHelper.manager.save(header);
				} else {
					header.setWtpartMaster(master);
					header.setBomType(EBOMHelper.HEADER);
					header.setState(EBOMHelper.EBOM_TEMP);
					header.setOwnership(Ownership.newOwnership(prin));
					PersistenceHelper.manager.modify(header);
				}

				saveTree(header, node.getChildren());
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
		return header;
	}

	@Override
	public void saveTree(EBOM parent, ArrayList<BOMTreeNode> children) throws Exception {
		Transaction trs = new Transaction();
		try {
			trs.start();
			WTPrincipal prin = SessionHelper.manager.getPrincipal();
			for (BOMTreeNode node : children) {
				WTPart part = (WTPart) CommonUtils.persistable(node.getOid());
				WTPartMaster master = part.getMaster();
				EBOM header = EBOMHelper.manager.getEBOM(master);
				if (header == null) {
					header = EBOM.newEBOM();
					header.setWtpartMaster(master);
					header.setBomType(EBOMHelper.CHILD);
					header.setState(EBOMHelper.EBOM_TEMP);
					header.setOwnership(Ownership.newOwnership(prin));
					PersistenceHelper.manager.save(header);
				} else {
					header.setWtpartMaster(master);
					header.setBomType(EBOMHelper.CHILD);
					header.setState(EBOMHelper.EBOM_TEMP);
					header.setOwnership(Ownership.newOwnership(prin));
					PersistenceHelper.manager.modify(header);
				}

				EBOMLink link = EBOMHelper.manager.getEBOMLink(parent, header);
				WTPartUsageLink usageLink = null;
				System.out.println("node=" + node.getNumber() + ", link=" + node.getLink());
				if (StringUtils.isNotNull(node.getLink())) {
					usageLink = (WTPartUsageLink) CommonUtils.persistable(node.getLink());
				}
				if (link == null) {
					link = EBOMLink.newEBOMLink(parent, header);
					link.setAmount(node.getAmount());
					link.setUsageLink(usageLink);
					PersistenceHelper.manager.save(link);
				} else {
					link.setAmount(node.getAmount());
					link.setUsageLink(usageLink);
					PersistenceHelper.manager.modify(link);
				}
				saveTree(header, node.getChildren());
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
	public EBOM modify(Map<String, Object> params) throws Exception {
		EBOM newHeader = null;
		String oid = (String) params.get("oid"); // ebom..
		String json = (String) params.get("json");
		Transaction trs = new Transaction();
		try {
			trs.start();

			WTPrincipal prin = SessionHelper.manager.getPrincipal();

			EBOM header = (EBOM) CommonUtils.persistable(oid);
			WTPartMaster master = header.getWtpartMaster();

			ArrayList<EBOM> arr = new ArrayList<>();
			arr.add(header);
			ArrayList<EBOM> list = EBOMHelper.manager.getAllEBOM(header, arr);
			for (EBOM data : list) {
				PersistenceHelper.manager.delete(data);
			}

			String jsonStr = new String(DatatypeConverter.parseBase64Binary(json), "UTF-8");
			Type listType = new TypeToken<ArrayList<BOMTreeNode>>() {
			}.getType();

			Gson gson = new Gson();
			List<BOMTreeNode> nodes = gson.fromJson(jsonStr, listType);

			for (BOMTreeNode node : nodes) {
				newHeader = EBOMHelper.manager.getEBOM(master);
				if (newHeader == null) {
					newHeader = EBOM.newEBOM();
				}
				newHeader.setWtpartMaster(master);
				newHeader.setBomType(EBOMHelper.HEADER);
				newHeader.setState(EBOMHelper.EBOM_TEMP);
				newHeader.setOwnership(Ownership.newOwnership(prin));
				PersistenceHelper.manager.save(newHeader);
				saveTree(newHeader, node.getChildren());
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
		return newHeader;
	}

	@Override
	public void delete(String oid) throws Exception {
		EBOM header = null;
		Transaction trs = new Transaction();
		try {
			trs.start();

			header = (EBOM) CommonUtils.persistable(oid);

			ArrayList<EBOMLink> list = new ArrayList<>();
			ArrayList<EBOMLink> links = EBOMHelper.manager.getAllEBOMLink(header, list);

			ArrayList<EBOM> arr = new ArrayList<>();
			ArrayList<EBOM> data = EBOMHelper.manager.getAllEBOM(header, arr);

			for (EBOMLink link : links) {
				PersistenceHelper.manager.delete(link);
			}

			for (EBOM dd : data) {
				PersistenceHelper.manager.delete(dd);
			}

			PersistenceHelper.manager.delete(header);

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