package platform.ebom.service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import platform.ebom.entity.EBOMMaster;
import platform.ebom.entity.EBOMMasterLink;
import platform.ebom.vo.BOMTreeNode;
import platform.part.service.PartHelper;
import platform.util.CommonUtils;
import platform.util.IBAUtils;
import platform.util.StringUtils;
import wt.epm.EPMDocument;
import wt.fc.PersistenceHelper;
import wt.org.WTPrincipal;
import wt.org.WTUser;
import wt.ownership.Ownership;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.pom.Transaction;
import wt.services.StandardManager;
import wt.session.SessionHelper;
import wt.util.WTException;

public class StandardEBOMMasterService extends StandardManager implements EBOMMasterService {

	public static StandardEBOMMasterService newStandardEBOMMasterService() throws WTException {
		StandardEBOMMasterService instance = new StandardEBOMMasterService();
		instance.initialize();
		return instance;
	}

	@Override
	public void create(Map<String, Object> params) throws Exception {
		String json = (String) params.get("json");
		String poid = (String) params.get("poid"); // 해더 파트
		String msg = null;
		EBOMMaster parent = null;
		Transaction trs = new Transaction();
		try {
			trs.start();

			WTPrincipal prin = SessionHelper.manager.getPrincipal();

			WTPart headerPart = (WTPart) CommonUtils.persistable(poid);
			msg = headerPart.getNumber();

			String jsonStr = new String(DatatypeConverter.parseBase64Binary(json), "UTF-8");
			Type listType = new TypeToken<ArrayList<BOMTreeNode>>() {
			}.getType();

			Gson gson = new Gson();
			List<BOMTreeNode> nodes = gson.fromJson(jsonStr, listType);

			for (BOMTreeNode node : nodes) {
				WTPart parentPart = (WTPart) CommonUtils.persistable(node.getOid()); // EBOM객체랑 연결될 파트들..
				String amount = node.getAmount();
				ArrayList<BOMTreeNode> childrens = node.getChildren();

				 parent = EBOMMasterHelper.manager.getEBOMMaster(parentPart.getMaster());
				if (parent == null) {
					parent = EBOMMaster.newEBOMMaster();
					if ("MAT".equals(node.getPartTypeCd())) {
						parent.setName(StringUtils.convertToStr(IBAUtils.getStringValue(parentPart, "PART_NO"), "-"));
					} else {
						parent.setName(StringUtils.convertToStr(IBAUtils.getStringValue(parentPart, "ITEM_NAME"), "-"));
					}
					parent.setNumber(parentPart.getNumber());
					parent.setVer(parentPart.getVersionIdentifier().getSeries().getValue());
					parent.setAmount(Double.parseDouble(amount));
					parent.setState(EBOMMasterHelper.EBOM_CREATE_NOCONFIRM);
					parent.setBomType(node.getPartTypeCd());
					parent.setOwnership(Ownership.newOwnership(prin));
					parent.setPart((WTPartMaster) headerPart.getMaster());
					parent = (EBOMMaster) PersistenceHelper.manager.save(parent);
				}

				saveTree(parent, childrens, 0);
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
//		return msg;
	}

	@Override
	public EBOMMaster modify(Map<String, Object> params) throws Exception {
		EBOMMaster header = null;
		EBOMMaster newHeader = null;
		String json = (String) params.get("json");
		String oid = (String) params.get("oid");
		String colorSet = (String) params.get("colorSet");
		String type = (String) params.get("type");
		Transaction trs = new Transaction();
		try {
			trs.start();

			header = (EBOMMaster) CommonUtils.persistable(oid);

			WTPrincipal prin = SessionHelper.manager.getPrincipal();
			WTPart headerPart = PartHelper.manager.getLatest(header.getPart());

			// 수정단계 모든 내용은 다 삭제 하고 다시 새로 만든다..
			ArrayList<EBOMMasterLink> links = EBOMMasterHelper.manager.getAllEBOMMasterLink(header);
			ArrayList<EBOMMaster> elinks = new ArrayList<>();
			for (EBOMMasterLink link : links) {
				EBOMMaster _parent = link.getParent();
				EBOMMaster _child = link.getChild();
				PersistenceHelper.manager.delete(link);
				if (!elinks.contains(_child)) {
					elinks.add(_child);
				}
				if (!elinks.contains(_parent)) {
					elinks.add(_parent);
				}
			}

			for (EBOMMaster ebom : elinks) {
				PersistenceHelper.manager.delete(ebom);
			}

			newHeader = EBOMMaster.newEBOMMaster();
			if ("MAT".equals(IBAUtils.getStringValue(headerPart, "PART_TYPE"))) {
				newHeader.setName(StringUtils.convertToStr(IBAUtils.getStringValue(headerPart, "PART_NO"), "-"));
			} else {
				newHeader.setName(StringUtils.convertToStr(IBAUtils.getStringValue(headerPart, "ITEM_NAME"), "-"));
			}
			newHeader.setNumber(headerPart.getNumber());
			newHeader.setVer(headerPart.getVersionIdentifier().getSeries().getValue());
			newHeader.setApplyColor(null);
			newHeader.setDerivedColor(colorSet);
			newHeader.setColor(EBOMMasterHelper.DEFAULT_COLOR);
			newHeader.setAmount(1D);
			if ("t".equals(type)) {
				newHeader.setState(EBOMMasterHelper.EBOM_TEMP);
			} else {
				newHeader.setState(EBOMMasterHelper.EBOM_CREATE);
			}
			newHeader.setBomType(header.getBomType());
			newHeader.setOwnership(Ownership.newOwnership(prin));
			newHeader.setPart(header.getPart());
			newHeader.setIsNew(header.getIsNew());
			newHeader = (EBOMMaster) PersistenceHelper.manager.save(newHeader);

			String jsonStr = new String(DatatypeConverter.parseBase64Binary(json), "UTF-8");
			Type listType = new TypeToken<ArrayList<BOMTreeNode>>() {
			}.getType();

			Gson gson = new Gson();
			List<BOMTreeNode> nodes = gson.fromJson(jsonStr, listType);

			for (BOMTreeNode node : nodes) {
				String isColorSet = node.getIsColorSet();
				String key = node.getKey().replaceAll("_", "");
				WTPart part = (WTPart) CommonUtils.persistable(node.getOid()); // EBOM객체랑 연결될 파트들..
				EPMDocument epm = PartHelper.manager.getEPMDocument(part);
				int level = node.getLevel();
				String version = node.getVersion();
				String amount = node.getAmount();
				String partTypeCd = node.getPartTypeCd();
				String erpCode = node.getErpCode();
				boolean isNew = node.isNew();

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
					EBOMMaster parent = EBOMMasterHelper.manager.getEBOMMaster(parentPart.getMaster());
					EBOMMaster child = EBOMMasterHelper.manager.getEBOMMaster(part.getMaster());
					if (child == null) {
						child = EBOMMaster.newEBOMMaster();
						child.setName(StringUtils.convertToStr(node.getItemName(), "-"));
						child.setNumber(part.getNumber()); // 번호가 멀로 될지??
						child.setVer(version);
						child.setIsColorSet(Boolean.parseBoolean(isColorSet));
						child.setApplyColor(null);
						child.setDerivedColor(null);
						child.setColor(EBOMMasterHelper.DEFAULT_COLOR);
						child.setAmount(Double.parseDouble(amount));
						child.setState(EBOMMasterHelper.EBOM_CREATE);
						if ("MAT".equals(IBAUtils.getStringValue(part, "PART_TYPE"))) {
							child.setBomType(EBOMMasterHelper.EBOM_TYPE);
						} else {
							child.setBomType(EBOMMasterHelper.EBOM_ITEM_TYPE);
						}
						child.setIsNew(isNew);
						child.setOwnership(Ownership.newOwnership(prin));
						child.setPart(part.getMaster());
						child = (EBOMMaster) PersistenceHelper.manager.save(child);
					}
					boolean exist = EBOMMasterHelper.manager.isExistLink(parent, child);
					// 없다면?
					if (!exist) {
						EBOMMasterLink link = EBOMMasterLink.newEBOMMasterLink(parent, child);
						link.setSort(Integer.parseInt(key));
						link.setDepth(level);
						link.setOwnership(Ownership.newOwnership(prin));
						PersistenceHelper.manager.save(link);
					}
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
		return newHeader;
	}

	@Override
	public EBOMMaster partlist(Map<String, Object> params) throws Exception {
		EBOMMaster header = null;
		String oid = (String) params.get("oid");
		Transaction trs = new Transaction();
		try {
			trs.start();

			WTPrincipal prin = SessionHelper.manager.getPrincipal();

			header = (EBOMMaster) CommonUtils.persistable(oid);
			header.setState(EBOMMasterHelper.PART_LIST_CREATE);
			header = (EBOMMaster) PersistenceHelper.manager.modify(header);

			String[] derivedColors = header.getDerivedColor().split(",");

			ArrayList<EBOMMasterLink> links = EBOMMasterHelper.manager.getAllEBOMMasterLink(header);

			for (String derivedColor : derivedColors) {
				for (EBOMMasterLink ll : links) {
					EBOMMaster _parent = ll.getParent();
					EBOMMaster _child = ll.getChild();

					EBOMMaster parent = EBOMMasterHelper.manager.getEBOMMaster(_parent.getPart(), derivedColor);
					if (parent == null) {
						parent = EBOMMaster.newEBOMMaster();
						parent.setName(_parent.getName());
						parent.setNumber(_parent.getNumber());
						parent.setVer(_parent.getVer());
						parent.setDerivedColor(null);
						parent.setColor(derivedColor);
						parent.setAmount(_parent.getAmount());
						parent.setState(_parent.getState());
						parent.setBomType(_parent.getBomType());
						if (_parent.getBomType().equals(EBOMMasterHelper.EBOM_SET_TYPE) || _parent.getIsNew()) {
							parent.setApplyColor(derivedColor);
						} else {
							parent.setApplyColor(null);
						}
						parent.setOwnership(Ownership.newOwnership(prin));
						parent.setPart(_parent.getPart());
						parent.setEbomMaster(_parent);
//						parent.setEbom(_parent.);
						parent = (EBOMMaster) PersistenceHelper.manager.save(parent);
					} else {
//						parent.setEbom(_parent);
//						parent = (EBOMMaster) PersistenceHelper.manager.modify(parent);
					}

					EBOMMaster child = EBOMMasterHelper.manager.getEBOMMaster(_child.getPart(), derivedColor);
					if (child == null) {
						child = EBOMMaster.newEBOMMaster();
						child.setName(_child.getName());
						child.setNumber(_child.getNumber());
						child.setVer(_child.getVer());
						if (_child.getIsNew()) {
							child.setApplyColor(derivedColor);
						} else {
							child.setApplyColor(null);
						}
						child.setDerivedColor(null);
						child.setColor(derivedColor);
						child.setAmount(_child.getAmount());
						child.setState(_child.getState());
						child.setBomType(_child.getBomType());
						child.setOwnership(Ownership.newOwnership(prin));
						child.setPart(_child.getPart());
						child.setEbomMaster(_child);
//						child.setEbom(_child);
						child = (EBOMMaster) PersistenceHelper.manager.save(child);
					} else {
//						child.setEbom(_child);
//						child = (EBOMMaster) PersistenceHelper.manager.modify(child);
					}
					boolean exist = EBOMMasterHelper.manager.isExistLink(parent, child);
					if (!exist) {
						EBOMMasterLink link = EBOMMasterLink.newEBOMMasterLink(parent, child);
						link.setOwnership(Ownership.newOwnership(prin));
						link.setDepth(ll.getDepth());
						link.setSort(ll.getSort());
						PersistenceHelper.manager.save(link);
					}
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
		return header;
	}

	@Override
	public void color(Map<String, Object> params) throws Exception {
		String json = (String) params.get("json");
		Transaction trs = new Transaction();
		try {
			trs.start();

			String jsonStr = new String(DatatypeConverter.parseBase64Binary(json), "UTF-8");
			Type listType = new TypeToken<ArrayList<BOMTreeNode>>() {
			}.getType();

			Gson gson = new Gson();
			List<BOMTreeNode> nodes = gson.fromJson(jsonStr, listType);

			for (BOMTreeNode node : nodes) {
				String oid = node.getOid();
				String manager = node.getManager();
				String applyColor = node.getApplyColor();

				EBOMMaster item = (EBOMMaster) CommonUtils.persistable(oid);
				item.setApplyColor(applyColor);
				if (StringUtils.isNotNull(manager)) {
					WTUser m = (WTUser) CommonUtils.persistable(manager);
					item.setManager(m);
				}
				PersistenceHelper.manager.modify(item);
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
	public EBOMMaster derived(Map<String, Object> params) throws Exception {
		EBOMMaster derived = null;
		String oid = (String) params.get("oid");
		String poid = (String) params.get("poid"); // 선택한 part oid
		Transaction trs = new Transaction();
		try {
			trs.start();

			EBOMMaster org = (EBOMMaster) CommonUtils.persistable(oid); // 복사할 원본..

			WTPrincipal prin = SessionHelper.manager.getPrincipal();
			WTPart headerPart = (WTPart) CommonUtils.persistable(poid); // 헤더 연관된 부품

			derived = EBOMMaster.newEBOMMaster();
			if ("MAT".equals(IBAUtils.getStringValue(headerPart, "PART_TYPE"))) {
				derived.setName(IBAUtils.getStringValue(headerPart, "PART_NO"));
			} else {
				derived.setName(IBAUtils.getStringValue(headerPart, "ITEM_NAME"));
			}
			derived.setNumber(headerPart.getNumber());
			derived.setVer(headerPart.getVersionIdentifier().getSeries().getValue());
			derived.setApplyColor(org.getApplyColor());
			derived.setDerivedColor(org.getDerivedColor());
			derived.setColor(org.getColor());
			derived.setAmount(1D);
			derived.setState(EBOMMasterHelper.EBOM_DERIVED);
//			derived.setState(EBOMMasterHelper.PART_LIST_CREATE);
			derived.setBomType(org.getBomType());
			derived.setOwnership(org.getOwnership());
			derived.setPart((WTPartMaster) headerPart.getMaster());
			derived = (EBOMMaster) PersistenceHelper.manager.save(derived);

			ArrayList<EBOMMasterLink> links = EBOMMasterHelper.manager.getAllEBOMMasterLink(org);
			for (EBOMMasterLink link : links) {
				EBOMMaster _parent = link.getParent();
				EBOMMaster _child = link.getChild();

				if (_parent.getPart().getPersistInfo().getObjectIdentifier().getId() == org.getPart().getPersistInfo()
						.getObjectIdentifier().getId()) {
					_parent = derived;
				}

				EBOMMaster parent = EBOMMasterHelper.manager.getEBOMMaster(_parent.getPart());
				if (parent == null) {
					parent = EBOMMaster.newEBOMMaster();
					parent.setName(_parent.getName());
					parent.setNumber(_parent.getNumber());
					parent.setVer(_parent.getVer());
					parent.setApplyColor(_parent.getApplyColor());
					parent.setDerivedColor(_parent.getDerivedColor());
					parent.setColor(_parent.getColor());
					parent.setAmount(_parent.getAmount());
					parent.setState(_parent.getState());
					parent.setBomType(_parent.getBomType());
					parent.setOwnership(_parent.getOwnership());
					parent.setPart(_parent.getPart());
					parent.setEbomMaster(_parent);
					parent = (EBOMMaster) PersistenceHelper.manager.save(parent);
				}

				EBOMMaster child = EBOMMasterHelper.manager.getEBOMMaster(_child.getPart());
				if (child == null) {
					child = EBOMMaster.newEBOMMaster();
					child.setName(_child.getName());
					child.setNumber(_child.getNumber());
					child.setVer(_child.getVer());
					child.setApplyColor(_child.getApplyColor());
					child.setDerivedColor(_child.getDerivedColor());
					child.setColor(_child.getColor());
					child.setAmount(_child.getAmount());
					child.setState(_child.getState());
					child.setBomType(_child.getBomType());
					child.setOwnership(_child.getOwnership());
					child.setPart(_child.getPart());
					child.setEbomMaster(_child);
					child = (EBOMMaster) PersistenceHelper.manager.save(child);
				}
				boolean exist = EBOMMasterHelper.manager.isExistLink(parent, child);
				if (!exist) {
					EBOMMasterLink newLink = EBOMMasterLink.newEBOMMasterLink(parent, child);
					newLink.setOwnership(Ownership.newOwnership(prin));
					newLink.setDepth(link.getDepth());
					newLink.setSort(link.getSort());
					PersistenceHelper.manager.save(newLink);
				}
			}

			String[] derivedColors = org.getDerivedColor().split(",");
			ArrayList<EBOMMasterLink> newLinks = EBOMMasterHelper.manager.getAllEBOMMasterLink(org);
//			for (String derivedColor : derivedColors) {
//				for (EBOMMasterLink link : newLinks) {
//					EBOMMaster _parent = link.getParent();
//					EBOMMaster _child = link.getChild();
//					EBOMMaster parent = null;
//
//					WTPartMaster orgPart = _parent.getPart();
//
//					if (_parent.getPart().getPersistInfo().getObjectIdentifier().getId() == org.getPart()
//							.getPersistInfo().getObjectIdentifier().getId()) {
//						orgPart = derived.getPart();
//						System.out.println("교체..");
//					}
//
//					parent = EBOMMasterHelper.manager.getEBOMMaster(orgPart, derivedColor);
//
//					if (parent == null) {
//						parent = EBOMMaster.newEBOMMaster();
//						parent.setName(_parent.getName());
//						parent.setNumber(_parent.getNumber());
//						parent.setVer(_parent.getVer());
//						parent.setApplyColor(_parent.getApplyColor());
//						parent.setDerivedColor(_parent.getDerivedColor());
//						parent.setColor(_parent.getColor());
//						parent.setAmount(_parent.getAmount());
//						parent.setState(_parent.getState());
//						parent.setBomType(_parent.getBomType());
//						parent.setOwnership(_parent.getOwnership());
//						parent.setPart(orgPart);
//						parent.setManager(_parent.getManager());
//						parent.setEbomMaster(_parent);
//						parent = (EBOMMaster) PersistenceHelper.manager.save(parent);
//					}
//
//					EBOMMaster child = EBOMMasterHelper.manager.getEBOMMaster(_child.getPart(), derivedColor);
//					if (child == null) {
//						child = EBOMMaster.newEBOMMaster();
//						child.setName(_child.getName());
//						child.setNumber(_child.getNumber());
//						child.setVer(_child.getVer());
//						child.setApplyColor(_child.getApplyColor());
//						child.setDerivedColor(_child.getDerivedColor());
//						child.setColor(_child.getColor());
//						child.setAmount(_child.getAmount());
//						child.setState(_child.getState());
//						child.setBomType(_child.getBomType());
//						child.setOwnership(_child.getOwnership());
//						child.setPart(_child.getPart());
//						child.setManager(_child.getManager());
//						child.setEbomMaster(_child);
//						child = (EBOMMaster) PersistenceHelper.manager.save(child);
//					}
//					
//					boolean exist = EBOMMasterHelper.manager.isExistLink(parent, child);
//					if (!exist) {
//						EBOMMasterLink newLink = EBOMMasterLink.newEBOMMasterLink(parent, child);
//						newLink.setOwnership(Ownership.newOwnership(prin));
//						newLink.setDepth(link.getDepth());
//						newLink.setSort(link.getSort());
//						PersistenceHelper.manager.save(newLink);
//					}
//				}
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
		return derived;
	}

	@Override
	public EBOMMaster delete(String oid) throws Exception {
		Transaction trs = new Transaction();
		EBOMMaster header = null;
		try {
			trs.start();

			header = (EBOMMaster) CommonUtils.persistable(oid);

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
	public EBOMMaster save(Map<String, Object> params) throws Exception {
		EBOMMaster m = null;
		String oid = (String) params.get("oid");
		String json = (String) params.get("json");
		Transaction trs = new Transaction();
		try {
			trs.start();

			m = (EBOMMaster) CommonUtils.persistable(oid);
			WTPrincipal prin = SessionHelper.manager.getPrincipal();

			String jsonStr = new String(DatatypeConverter.parseBase64Binary(json), "UTF-8");
			Type listType = new TypeToken<ArrayList<BOMTreeNode>>() {
			}.getType();

			Gson gson = new Gson();
			List<BOMTreeNode> nodes = gson.fromJson(jsonStr, listType);

			for (BOMTreeNode node : nodes) {
				String key = node.getKey().replaceAll("_", "");
				WTPart part = (WTPart) CommonUtils.persistable(node.getOid()); // EBOM객체랑 연결될 파트들..
				int level = node.getLevel();
				String version = node.getVersion();
				String amount = node.getAmount();

				if (!StringUtils.isNotNull(amount)) {
					amount = "1";
				}

				if (StringUtils.isNotNull(node.getParent())) {
					WTPart parentPart = (WTPart) CommonUtils.persistable(node.getParent());
					EBOMMaster parent = EBOMMasterHelper.manager.getEBOMMaster((WTPartMaster) parentPart.getMaster());
					EBOMMaster child = EBOMMasterHelper.manager.getEBOMMaster((WTPartMaster) part.getMaster());
					if (child == null) {
						child = EBOMMaster.newEBOMMaster();
						child.setName(StringUtils.convertToStr(node.getItemName(), "-"));
						child.setNumber(part.getNumber()); // 번호가 멀로 될지??
						child.setVer(version);
						child.setApplyColor(null);
						child.setDerivedColor(null);
						child.setColor(EBOMMasterHelper.DEFAULT_COLOR);
						child.setAmount(Double.parseDouble(amount));
						child.setState(EBOMMasterHelper.EBOM_CREATE);
						if ("MAT".equals(IBAUtils.getStringValue(part, "PART_TYPE"))) {
							child.setBomType(EBOMMasterHelper.EBOM_TYPE);
						} else {
							child.setBomType(EBOMMasterHelper.EBOM_ITEM_TYPE);
						}
						child.setOwnership(Ownership.newOwnership(prin));
						child.setPart((WTPartMaster) part.getMaster());
						child = (EBOMMaster) PersistenceHelper.manager.save(child);
					}

					boolean exist = EBOMMasterHelper.manager.isExistLink(parent, child);
					// 없다면?
					if (!exist) {
						EBOMMasterLink link = EBOMMasterLink.newEBOMMasterLink(parent, child);
						link.setSort(Integer.parseInt(key));
						link.setDepth(level);
						link.setOwnership(Ownership.newOwnership(prin));
						PersistenceHelper.manager.save(link);
					}
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

		return m;
	}

	@Override
	public void saveTree(EBOMMaster parent, ArrayList<BOMTreeNode> childrens, int sort) throws Exception {

		Transaction trs = new Transaction();
		try {
			trs.start();

			WTPrincipal prin = SessionHelper.manager.getPrincipal();
			for (BOMTreeNode node : childrens) {
				int depth = node.get_$depth();
				WTPart part = (WTPart) CommonUtils.persistable(node.getOid()); // EBOM객체랑 연결될 파트들..
				String amount = node.getAmount();
				EBOMMaster child = EBOMMasterHelper.manager.getEBOMMaster(part.getMaster());
				if (child == null) {
					child = EBOMMaster.newEBOMMaster();
					if ("MAT".equals(node.getPartTypeCd())) {
						child.setName(StringUtils.convertToStr(IBAUtils.getStringValue(part, "PART_NO"), "-"));
					} else {
						child.setName(StringUtils.convertToStr(IBAUtils.getStringValue(part, "ITEM_NAME"), "-"));
					}
					child.setNumber(part.getNumber());
					child.setVer(part.getVersionIdentifier().getSeries().getValue());
					child.setAmount(Double.parseDouble(amount));
					child.setState(EBOMMasterHelper.EBOM_CREATE_NOCONFIRM);
					child.setBomType(node.getPartTypeCd());
					child.setOwnership(Ownership.newOwnership(prin));
					child.setPart((WTPartMaster) part.getMaster());
					child = (EBOMMaster) PersistenceHelper.manager.save(child);
				}

				boolean exist = EBOMMasterHelper.manager.isExistLink(parent, child);
				// 없다면?
				if (!exist) {
					EBOMMasterLink link = EBOMMasterLink.newEBOMMasterLink(parent, child);
					link.setSort(sort);
					link.setDepth(depth);
					link.setOwnership(Ownership.newOwnership(prin));
					PersistenceHelper.manager.save(link);
				}
				sort++;
				saveTree(child, node.getChildren(), sort);
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
