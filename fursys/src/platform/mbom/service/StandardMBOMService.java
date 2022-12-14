package platform.mbom.service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import platform.ebom.entity.EBOM;
import platform.ebom.entity.EBOMLink;
import platform.ebom.service.EBOMHelper;
import platform.ebom.vo.BOMTreeNode;
import platform.mbom.entity.MBOM;
import platform.mbom.entity.MBOMLink;
import platform.part.service.PartHelper;
import platform.util.CommonUtils;
import platform.util.IBAUtils;
import platform.util.StringUtils;
import wt.epm.EPMDocument;
import wt.fc.PersistenceHelper;
import wt.org.WTPrincipal;
import wt.ownership.Ownership;
import wt.part.WTPart;
import wt.pom.Transaction;
import wt.services.StandardManager;
import wt.session.SessionHelper;
import wt.util.WTException;

public class StandardMBOMService extends StandardManager implements MBOMService {

	public static StandardMBOMService newStandardMBOMService() throws WTException {
		StandardMBOMService instance = new StandardMBOMService();
		instance.initialize();
		return instance;
	}

	@Override
	public void generate(EBOM genData) throws Exception {
		Transaction trs = new Transaction();
		try {
			trs.start();
			WTPrincipal prin = SessionHelper.manager.getPrincipal();
			ArrayList<EBOMLink> list = EBOMHelper.manager.getAllEBOMLink(genData);
			for (EBOMLink link : list) {
				EBOM _parent = link.getParent();
				MBOM parent = MBOMHelper.manager.getMBOM(_parent.getPart(), _parent.getColor());
				if (parent == null) {
					parent = MBOM.newMBOM();
					parent.setName(_parent.getName());
					parent.setNumber(_parent.getNumber());
					parent.setVer(_parent.getVer());
					parent.setDerivedColor(null);
					parent.setColor(_parent.getColor());
					parent.setApplyColor(_parent.getApplyColor());
					parent.setAmount(_parent.getAmount());
					parent.setState(MBOMHelper.MBOM_CREATE);
					parent.setBomType(_parent.getBomType().replaceFirst("E", "M"));
					parent.setManager(_parent.getManager());
					parent.setOwnership(Ownership.newOwnership(prin));
					parent.setPart(_parent.getPart());
					parent.setEbom(_parent);
					parent = (MBOM) PersistenceHelper.manager.save(parent);
				}
				EBOM _child = link.getChild();
				MBOM child = MBOMHelper.manager.getMBOM(_child.getPart(), _child.getColor());
				if (child == null) {
					child = MBOM.newMBOM();
					child.setName(_child.getName());
					child.setNumber(_child.getNumber());
					child.setVer(_child.getVer());
					child.setApplyColor(_child.getApplyColor());
					child.setDerivedColor(null);
					child.setColor(_child.getColor());
					child.setAmount(_child.getAmount());
					child.setState(MBOMHelper.MBOM_CREATE);
					child.setBomType(_child.getBomType().replaceFirst("E", "M"));
					child.setManager(_child.getManager());
					child.setOwnership(Ownership.newOwnership(prin));
					child.setPart(_child.getPart());
					child.setEbom(_child);
					child = (MBOM) PersistenceHelper.manager.save(child);
				}

				boolean exist = MBOMHelper.manager.isExistLink(parent, child);
				// 없다면?
				if (!exist) {
					MBOMLink mlink = MBOMLink.newMBOMLink(parent, child);
					mlink.setOwnership(Ownership.newOwnership(prin));
					mlink.setDepth(link.getDepth());
					mlink.setSort(link.getSort());
					PersistenceHelper.manager.save(mlink);
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
	public MBOM modify(Map<String, Object> params) throws Exception {
		MBOM header = null;
		MBOM newHeader = null;
		String json = (String) params.get("json");
		String oid = (String) params.get("oid");
		String color = (String) params.get("color");
		Transaction trs = new Transaction();
		try {
			trs.start();

			header = (MBOM) CommonUtils.persistable(oid);

			WTPrincipal prin = SessionHelper.manager.getPrincipal();
			WTPart headerPart = header.getPart();

			// 수정단계 모든 내용은 다 삭제 하고 다시 새로 만든다..
			ArrayList<MBOMLink> links = MBOMHelper.manager.getAllMBOMLink(header);
			ArrayList<MBOM> mlinks = new ArrayList<>();
			for (MBOMLink link : links) {
				MBOM _parent = link.getParent();
				MBOM _child = link.getChild();
				PersistenceHelper.manager.delete(link);
				if (!mlinks.contains(_child)) {
					mlinks.add(_child);
				}
				if (!mlinks.contains(_parent)) {
					mlinks.add(_parent);
				}
			}

			for (MBOM mbom : mlinks) {
				PersistenceHelper.manager.delete(mbom);
			}

			newHeader = MBOM.newMBOM();
			if ("MAT".equals(IBAUtils.getStringValue(headerPart, "PART_TYPE"))) {
				newHeader.setName(StringUtils.convertToStr(IBAUtils.getStringValue(headerPart, "PART_NO"), "-"));
			} else {
				newHeader.setName(StringUtils.convertToStr(IBAUtils.getStringValue(headerPart, "ITEM_NAME"), "-"));
			}
			newHeader.setNumber(headerPart.getNumber());
			newHeader.setVer(headerPart.getVersionIdentifier().getSeries().getValue());
			newHeader.setApplyColor(header.getApplyColor());
			newHeader.setDerivedColor(null);
			newHeader.setColor(color); // 파생색상..
			newHeader.setAmount(header.getAmount());
			newHeader.setState(header.getState());
			newHeader.setBomType(header.getBomType());
			newHeader.setOwnership(Ownership.newOwnership(prin));
			newHeader.setPart(header.getPart());
			newHeader.setManager(header.getManager());
			newHeader = (MBOM) PersistenceHelper.manager.save(newHeader);

			String jsonStr = new String(DatatypeConverter.parseBase64Binary(json), "UTF-8");
			Type listType = new TypeToken<ArrayList<BOMTreeNode>>() {
			}.getType();

			Gson gson = new Gson();
			List<BOMTreeNode> nodes = gson.fromJson(jsonStr, listType);

			for (BOMTreeNode node : nodes) {

				String key = node.getKey().replaceAll("_", "");
				WTPart part = (WTPart) CommonUtils.persistable(node.getPoid()); // EBOM객체랑 연결될 파트들..
				EPMDocument epm = PartHelper.manager.getEPMDocument(part);
				int level = node.getLevel();
				String version = node.getVersion();
				String amount = node.getAmount();
				String partTypeCd = node.getPartTypeCd();
				String erpCode = node.getErpCode();

				if (!StringUtils.isNotNull(amount)) {
					amount = "1";
				}

				IBAUtils.createIBA(part, "s", "PART_TYPE", partTypeCd);
				if (epm != null) {
					IBAUtils.createIBA(epm, "s", "PART_TYPE", partTypeCd);
				}

				IBAUtils.createIBA(part, "s", "ERP_CODE", erpCode);
				if (epm != null) {
					IBAUtils.createIBA(epm, "s", "ERP_CODE", erpCode);
				}

				if ("MAT".equals(partTypeCd)) {
					IBAUtils.createIBA(part, "s", "PART_NO", erpCode);
					if (epm != null) {
						IBAUtils.createIBA(epm, "s", "PART_NO", erpCode);
					}
				} else {
					IBAUtils.createIBA(part, "s", "ITEM_NAME", erpCode);
					if (epm != null) {
						IBAUtils.createIBA(epm, "s", "ITEM_NAME", erpCode);
					}
				}

				// 부모가 없을 경우 헤더랑..
				if (StringUtils.isNotNull(node.getParent())) {
					WTPart parentPart = (WTPart) CommonUtils.persistable(node.getParent());
					MBOM parent = MBOMHelper.manager.getMBOM(parentPart, color);
					MBOM child = MBOMHelper.manager.getMBOM(part, color);
					if (child == null) {
						child = MBOM.newMBOM();
						child.setName(StringUtils.convertToStr(node.getItemName(), "-"));
						child.setNumber(part.getNumber()); // 번호가 멀로 될지??
						child.setVer(version);
						child.setApplyColor(node.getApplyColor());
						child.setDerivedColor(null);
						child.setColor(color);
						child.setAmount(Double.parseDouble(amount));
						child.setState(MBOMHelper.MBOM_APPROVAL);
						if ("MAT".equals(IBAUtils.getStringValue(part, "PART_TYPE"))) {
							child.setBomType(MBOMHelper.MBOM_TYPE);
						} else {
							child.setBomType(MBOMHelper.MBOM_ITEM_TYPE);
						}
						child.setOwnership(Ownership.newOwnership(prin));
						child.setPart(part);
						child = (MBOM) PersistenceHelper.manager.save(child);
					}
					boolean exist = MBOMHelper.manager.isExistLink(parent, child);
					// 없다면?
					if (!exist) {
						MBOMLink link = MBOMLink.newMBOMLink(parent, child);
						link.setSort(Integer.parseInt(key));
						link.setDepth(level);
						link.setOwnership(Ownership.newOwnership(prin));
						PersistenceHelper.manager.save(link);
					}
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
		return newHeader;
	}
}