package platform.part.service;

import java.util.UUID;

import net.sf.json.JSONObject;
import platform.doc.entity.DocumentColumns;
import platform.doc.entity.WTDocumentWTPartLink;
import platform.part.entity.PartDTO;
import platform.util.CommonUtils;
import platform.util.IBAUtils;
import platform.util.StringUtils;
import platform.util.ThumbnailUtils;
import wt.clients.folder.FolderTaskLogic;
import wt.doc.WTDocument;
import wt.enterprise.MadeFromLink;
import wt.fc.PersistenceHelper;
import wt.folder.Folder;
import wt.folder.FolderEntry;
import wt.folder.FolderHelper;
import wt.part.QuantityUnit;
import wt.part.WTPart;
import wt.pom.Transaction;
import wt.services.StandardManager;
import wt.util.WTException;
import wt.vc.views.View;
import wt.vc.views.ViewHelper;

public class StandardPartService extends StandardManager implements PartService {

	public static StandardPartService newStandardPartService() throws WTException {
		StandardPartService instance = new StandardPartService();
		instance.initialize();
		return instance;
	}

	@Override
	public WTPart create(PartDTO params) throws Exception {
		Transaction trs = new Transaction();
		String part_name = params.getPart_name();
		String part_name_en = params.getPart_name_en();
		String unit = params.getUnit();
		String partType = params.getPartType();
		String erpCode = params.getErpCode();
		String location = params.getLocation();
		String brand = params.getBrand();
		String company = params.getCompany();
		String cat_l = params.getCat_l();
		String cat_m = params.getCat_m();
		String part_height = params.getPart_height();
		String part_depth = params.getPart_depth();
		String part_width = params.getPart_width();
		String purchase_yn = params.getPurchase_yn();
		String dummy_unit_price = params.getDummy_unit_price();
		String use_type_code = params.getUse_type_code();
		String standard_code = params.getStandard_code();
		String ref = params.getRef();
		
		WTPart part = null;
		System.out.println("######create");
		try {
			trs.start();
			part = WTPart.newWTPart();

			part.setName(part_name);
			part.setNumber(PartHelper.manager.getNextNumber(partType + "-"));
			// 단위

			View view = ViewHelper.service.getView("Design");
			ViewHelper.assignToView(part, view);

			part.setDefaultUnit(QuantityUnit.toQuantityUnit(unit));

			Folder folder = FolderTaskLogic.getFolder("/Default/부품", CommonUtils.getContainer("fursys"));
			FolderHelper.assignLocation((FolderEntry) part, folder);

			PersistenceHelper.manager.save(part);

			IBAUtils.createIBA(part, "s", "PART_NAME", part_name);
			IBAUtils.createIBA(part, "s", "PART_NAME_EN", part_name_en);

			IBAUtils.createIBA(part, "s", "PART_TYPE", partType);
			IBAUtils.createIBA(part, "s", "BRAND_CODE", brand);
			IBAUtils.createIBA(part, "s", "COMPANY_CODE", company);
			IBAUtils.createIBA(part, "s", "ERP_CODE", erpCode);
			IBAUtils.createIBA(part, "s", "CAT_L", cat_l);
			IBAUtils.createIBA(part, "s", "CAT_M", cat_m);
			
			System.out.println("##### cat_l: " + cat_l);
			System.out.println("##### cat_m: " + cat_m);

			IBAUtils.createIBA(part, "s", "PART_HEIGHT", part_height);
			IBAUtils.createIBA(part, "s", "PART_DEPTH", part_depth);
			IBAUtils.createIBA(part, "s", "PART_WIDTH", part_width);
			IBAUtils.createIBA(part, "s", "PURCHASE_YN", purchase_yn);
			IBAUtils.createIBA(part, "s", "DUMMY_UNIT_PRICE", dummy_unit_price);
			IBAUtils.createIBA(part, "s", "USE_TYPE_CODE", use_type_code);
			IBAUtils.createIBA(part, "s", "STANDARD_CODE", standard_code);

			// bom
			IBAUtils.createIBA(part, "b", "BOM", "true");

			if (StringUtils.isNotNull(ref)) {
				WTPart refPart = (WTPart) CommonUtils.persistable(ref);
				// a copy b original
				MadeFromLink link = MadeFromLink.newMadeFromLink(refPart, part);
				PersistenceHelper.manager.save(link);
			}

			for (DocumentColumns data : params.getDocList()) {
				WTDocument doc = (WTDocument) CommonUtils.persistable(data.getOid());
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
		return part;
	}

	@Override
	public WTPart modify(PartDTO params) throws Exception {
		String oid = params.getOid();
		System.out.println("### oid : " + oid);
		String part_name = params.getPart_name();
		String part_name_en = params.getPart_name_en();
		String unit = params.getUnit();
		String partType = params.getPartType();
		String erpCode = params.getErpCode();
		String location = params.getLocation();
		String brand = params.getBrand();
		String company = params.getCompany();
		String cat_l = params.getCat_l();
		String cat_m = params.getCat_m();
		String part_height = params.getPart_height();
		String part_depth = params.getPart_depth();
		String part_width = params.getPart_width();
		String purchase_yn = params.getPurchase_yn();
		String dummy_unit_price = params.getDummy_unit_price();
		String use_type_code = params.getUse_type_code();
		String standard_code = params.getStandard_code();
		String ref = params.getRef();
		WTPart part = null;
		Transaction trs = new Transaction();
		try {
			trs.start();
			part = (WTPart) CommonUtils.persistable(oid);

			part.setName(part_name);
			part.setNumber(PartHelper.manager.getNextNumber(partType + "-"));
			// 단위

			View view = ViewHelper.service.getView("Design");
			ViewHelper.assignToView(part, view);

			part.setDefaultUnit(QuantityUnit.toQuantityUnit(unit));

			Folder folder = FolderTaskLogic.getFolder("/Default/부품", CommonUtils.getContainer("fursys"));
			FolderHelper.assignLocation((FolderEntry) part, folder);

			PersistenceHelper.manager.save(part);

			IBAUtils.createIBA(part, "s", "PART_NAME", part_name);
			IBAUtils.createIBA(part, "s", "PART_NAME_EN", part_name_en);

			IBAUtils.createIBA(part, "s", "PART_TYPE", partType);
			IBAUtils.createIBA(part, "s", "BRAND_CODE", brand);
			IBAUtils.createIBA(part, "s", "COMPANY_CODE", company);
			IBAUtils.createIBA(part, "s", "ERP_CODE", erpCode);
			IBAUtils.createIBA(part, "s", "CAT_L", cat_l);
			IBAUtils.createIBA(part, "s", "CAT_M", cat_m);

			IBAUtils.createIBA(part, "s", "PART_HEIGHT", part_height);
			IBAUtils.createIBA(part, "s", "PART_DEPTH", part_depth);
			IBAUtils.createIBA(part, "s", "PART_WIDTH", part_width);
			IBAUtils.createIBA(part, "s", "PURCHASE_YN", purchase_yn);
			IBAUtils.createIBA(part, "s", "DUMMY_UNIT_PRICE", dummy_unit_price);
			IBAUtils.createIBA(part, "s", "USE_TYPE_CODE", use_type_code);
			IBAUtils.createIBA(part, "s", "STANDARD_CODE", standard_code);

			// bom
			IBAUtils.createIBA(part, "b", "BOM", "true");

			if (StringUtils.isNotNull(ref)) {
				WTPart refPart = (WTPart) CommonUtils.persistable(ref);
				// a copy b original
				MadeFromLink link = MadeFromLink.newMadeFromLink(refPart, part);
				PersistenceHelper.manager.save(link);
			}

			for (DocumentColumns data : params.getDocList()) {
				WTDocument doc = (WTDocument) CommonUtils.persistable(data.getOid());
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
		return part;
	}
	
	@Override
	public JSONObject attach(PartDTO params) throws Exception {
		Transaction trs = new Transaction();
		String part_name = params.getPart_name();
		String part_name_en = params.getPart_name_en();
		String unit = params.getUnit();
		String obj = params.getNode();
		String partType = params.getPartType();
		String erpCode = params.getErpCode();
		String location = params.getLocation();
		String brand = params.getBrand();
		String company = params.getCompany();
		String part_height = params.getPart_height();
		String part_depth = params.getPart_depth();
		String part_width = params.getPart_width();
		String purchase_yn = params.getPurchase_yn();
		String dummy_unit_price = params.getDummy_unit_price();
		String use_type_code = params.getUse_type_code();
		String standard_code = params.getStandard_code();
		String ref = params.getRef();
		String cat_l = params.getCat_l();
		String cat_m = params.getCat_m();
		JSONObject node = new JSONObject();
		WTPart part = null;
		try {
			trs.start();
			part = WTPart.newWTPart();

			part.setName(part_name);
			if (StringUtils.isNotNull(erpCode)) {
				part.setNumber(erpCode);
			} else {
				part.setNumber(PartHelper.manager.getNextNumber(partType + "-"));
			}
			// 단위

			View view = ViewHelper.service.getView("Design");
			ViewHelper.assignToView(part, view);

			part.setDefaultUnit(QuantityUnit.toQuantityUnit(unit));

			WTPart pp = (WTPart) CommonUtils.persistable(obj);

			Folder folder = FolderTaskLogic.getFolder(pp.getLocation(), pp.getContainerReference());
			FolderHelper.assignLocation((FolderEntry) part, folder);

			PersistenceHelper.manager.save(part);

			if (partType.equals("MAT")) {
				IBAUtils.createIBA(part, "s", "PART_NO", erpCode);
			} else {
				IBAUtils.createIBA(part, "s", "ITEM_NAME", erpCode);
			}

			IBAUtils.createIBA(part, "s", "PART_NAME", part_name);
			IBAUtils.createIBA(part, "s", "PART_NAME_EN", part_name_en);

			IBAUtils.createIBA(part, "s", "PART_TYPE", partType);
			IBAUtils.createIBA(part, "s", "BRAND_CODE", brand);
			IBAUtils.createIBA(part, "s", "COMPANY_CODE", company);
			IBAUtils.createIBA(part, "s", "ERP_CODE", erpCode);
			IBAUtils.createIBA(part, "s", "CAT_L", cat_l);
			IBAUtils.createIBA(part, "s", "CAT_M", cat_m);

			IBAUtils.createIBA(part, "s", "PART_HEIGHT", part_height);
			IBAUtils.createIBA(part, "s", "PART_DEPTH", part_depth);
			IBAUtils.createIBA(part, "s", "PART_WIDTH", part_width);
			IBAUtils.createIBA(part, "s", "PURCHASE_YN", purchase_yn);
			IBAUtils.createIBA(part, "s", "DUMMY_UNIT_PRICE", dummy_unit_price);
			IBAUtils.createIBA(part, "s", "USE_TYPE_CODE", use_type_code);
			IBAUtils.createIBA(part, "s", "STANDARD_CODE", standard_code);

			// bom
			IBAUtils.createIBA(part, "b", "BOM", "true");

			if (StringUtils.isNotNull(ref)) {
				WTPart refPart = (WTPart) CommonUtils.persistable(ref);
				// a copy b original
				MadeFromLink link = MadeFromLink.newMadeFromLink(refPart, part);
				PersistenceHelper.manager.save(link);
			}

			for (DocumentColumns data : params.getDocList()) {
				WTDocument doc = (WTDocument) CommonUtils.persistable(data.getOid());
				WTDocumentWTPartLink link = WTDocumentWTPartLink.newWTDocumentWTPartLink(doc, part);
				PersistenceHelper.manager.save(link);
			}

			node.put("thumb", ThumbnailUtils.thumbnails(part)[1]);
			node.put("partName", IBAUtils.getStringValue(part, "PART_NAME"));
			node.put("number", part.getNumber());
			node.put("itemName", IBAUtils.getStringValue(part, "ITEM_NAME"));
			node.put("partNo", IBAUtils.getStringValue(part, "PART_NO"));
			node.put("version", part.getVersionIdentifier().getSeries().getValue());
			node.put("partType", PartHelper.manager.partTypeToDisplay(part));
			node.put("state", part.getLifeCycleState().getDisplay());
			node.put("amount", 1);
			node.put("partTypeCd", IBAUtils.getStringValue(part, "PART_TYPE"));
			node.put("oid", part.getPersistInfo().getObjectIdentifier().getStringValue());
			node.put("library", PartHelper.manager.isLibrary(part));
			node.put("uid", UUID.randomUUID());

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
		return node;
	}
}
