package platform.tree.service;

import java.util.HashMap;
import java.util.List;

import platform.doc.entity.DocumentDTO;
import platform.tree.entity.Combination;
import platform.tree.entity.CombinationDTO;
import platform.tree.entity.Edge;
import platform.tree.entity.EdgeDTO;
import platform.tree.entity.MaterialInfo;
import platform.tree.entity.MaterialInfoDTO;
import platform.tree.entity.Surface;
import platform.tree.entity.SurfaceDTO;
import platform.tree.entity.Treatment;
import platform.tree.entity.TreatmentDTO;
import platform.util.CommonUtils;
import wt.fc.PersistenceHelper;
import wt.org.WTPrincipal;
import wt.ownership.Ownership;
import wt.pom.Transaction;
import wt.services.StandardManager;
import wt.session.SessionHelper;
import wt.util.WTException;

public class StandardTreeService extends StandardManager implements TreeService {

	public static StandardTreeService newStandardTreeService() throws WTException {
		StandardTreeService instance = new StandardTreeService();
		instance.initialize();
		return instance;
	}

	@Override
	public void combination(HashMap<String, List<CombinationDTO>> paramsMap) throws Exception {
		Transaction trs = new Transaction();
		try {
			trs.start();

			WTPrincipal prin = SessionHelper.manager.getPrincipal();

			List<CombinationDTO> addRows = paramsMap.get("addRows");
			for (CombinationDTO dto : addRows) {
				Combination combination = Combination.newCombination();
				combination.setRank(dto.getRank());
				combination.setBasic_code(dto.getBasic_code());
				combination.setWidth(dto.getWidth());
				combination.setMaterial(dto.getMaterial());
				combination.setSurface_code(dto.getSurface_code());
				combination.setCode(dto.getCode());
				combination.setCombination(dto.getCombination());
				combination.setCat_l(dto.getCat_l());
				combination.setErp_l(dto.getErp_l());
				combination.setCat_m(dto.getCat_m());
				combination.setErp_m(dto.getErp_m());
				combination.setCat_s(dto.getCat_s());
				combination.setErp_s(dto.getErp_s());
				combination.setMaterialType(dto.getMaterialType());
				combination.setMaterialTypeCode(dto.getMaterialTypeCode());
				combination.setOwnership(Ownership.newOwnership(prin));
				PersistenceHelper.manager.save(combination);
			}

			List<CombinationDTO> editRows = paramsMap.get("editRows");
			for (CombinationDTO dto : editRows) {
				Combination combination = (Combination) CommonUtils.persistable(dto.getOid());
				combination.setRank(dto.getRank());
				combination.setBasic_code(dto.getBasic_code());
				combination.setWidth(dto.getWidth());
				combination.setMaterial(dto.getMaterial());
				combination.setSurface_code(dto.getSurface_code());
				combination.setCode(dto.getCode());
				combination.setCombination(dto.getCombination());
				combination.setCat_l(dto.getCat_l());
				combination.setErp_l(dto.getErp_l());
				combination.setCat_m(dto.getCat_m());
				combination.setErp_m(dto.getErp_m());
				combination.setCat_s(dto.getCat_s());
				combination.setErp_s(dto.getErp_s());
				combination.setMaterialType(dto.getMaterialType());
				combination.setMaterialTypeCode(dto.getMaterialTypeCode());
				combination.setOwnership(Ownership.newOwnership(prin));

				PersistenceHelper.manager.modify(combination);
			}

			List<CombinationDTO> removeRows = paramsMap.get("removeRows");
			for (CombinationDTO dto : removeRows) {
				Combination combination = (Combination) CommonUtils.persistable(dto.getOid());
				PersistenceHelper.manager.delete(combination);
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
	public void treatment(HashMap<String, List<TreatmentDTO>> paramsMap) throws Exception {
		Transaction trs = new Transaction();
		try {
			trs.start();

			WTPrincipal prin = SessionHelper.manager.getPrincipal();

			List<TreatmentDTO> addRows = paramsMap.get("addRows");
			for (TreatmentDTO dto : addRows) {
				Treatment treatment = Treatment.newTreatment();
				treatment.setCode(dto.getCode());
				treatment.setRank(dto.getRank());
				treatment.setOwnership(Ownership.newOwnership(prin));
				treatment.setTreatment(dto.getTreatment());
				treatment.setDtmg_treatment(dto.getDtmg_treatment());
				PersistenceHelper.manager.save(treatment);
			}

			List<TreatmentDTO> editRows = paramsMap.get("editRows");
			for (TreatmentDTO dto : editRows) {
				Treatment treatment = (Treatment) CommonUtils.persistable(dto.getOid());
				treatment.setCode(dto.getCode());
				treatment.setRank(dto.getRank());
				treatment.setTreatment(dto.getTreatment());
				treatment.setDtmg_treatment(dto.getDtmg_treatment());
				treatment.setOwnership(Ownership.newOwnership(prin));
				PersistenceHelper.manager.modify(treatment);
			}

			List<TreatmentDTO> removeRows = paramsMap.get("removeRows");
			for (TreatmentDTO dto : removeRows) {
				Treatment treatment = (Treatment) CommonUtils.persistable(dto.getOid());
				PersistenceHelper.manager.delete(treatment);
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
	public void material(HashMap<String, List<MaterialInfoDTO>> paramsMap) throws Exception {
		Transaction trs = new Transaction();
		try {
			trs.start();

			WTPrincipal prin = SessionHelper.manager.getPrincipal();

			List<MaterialInfoDTO> addRows = paramsMap.get("addRows");
			for (MaterialInfoDTO dto : addRows) {
				MaterialInfo info = MaterialInfo.newMaterialInfo();
				info.setRank(dto.getRank());
				info.setBasic_code(dto.getBasic_code());
				info.setWidth(dto.getWidth());
				info.setMaterial_code(dto.getMaterial_code());
				info.setCode(dto.getCode());
				info.setMaterial(dto.getMaterial());
				info.setOutside(dto.getOutside());
				info.setOwnership(Ownership.newOwnership(prin));
				PersistenceHelper.manager.save(info);
			}

			List<MaterialInfoDTO> editRows = paramsMap.get("editRows");
			for (MaterialInfoDTO dto : editRows) {
				MaterialInfo info = (MaterialInfo) CommonUtils.persistable(dto.getOid());
				info.setRank(dto.getRank());
				info.setBasic_code(dto.getBasic_code());
				info.setWidth(dto.getWidth());
				info.setMaterial_code(dto.getMaterial_code());
				info.setCode(dto.getCode());
				info.setMaterial(dto.getMaterial());
				info.setOutside(dto.getOutside());
				info.setOwnership(Ownership.newOwnership(prin));
				PersistenceHelper.manager.modify(info);
			}

			List<MaterialInfoDTO> removeRows = paramsMap.get("removeRows");
			for (MaterialInfoDTO dto : removeRows) {
				MaterialInfo info = (MaterialInfo) CommonUtils.persistable(dto.getOid());
				PersistenceHelper.manager.delete(info);
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
	public void surface(HashMap<String, List<SurfaceDTO>> paramsMap) throws Exception {
		Transaction trs = new Transaction();
		try {
			trs.start();

			WTPrincipal prin = SessionHelper.manager.getPrincipal();

			List<SurfaceDTO> addRows = paramsMap.get("addRows");
			for (SurfaceDTO dto : addRows) {
				Surface surface = Surface.newSurface();
				surface.setCode(dto.getCode());
				surface.setRank(dto.getRank());
				surface.setMaterial(dto.getMaterial());
				surface.setOwnership(Ownership.newOwnership(prin));
				PersistenceHelper.manager.save(surface);
			}

			List<SurfaceDTO> editRows = paramsMap.get("editRows");
			for (SurfaceDTO dto : editRows) {
				Surface surface = (Surface) CommonUtils.persistable(dto.getOid());
				surface.setCode(dto.getCode());
				surface.setRank(dto.getRank());
				surface.setMaterial(dto.getMaterial());
				surface.setOwnership(Ownership.newOwnership(prin));
				PersistenceHelper.manager.modify(surface);
			}

			List<SurfaceDTO> removeRows = paramsMap.get("removeRows");
			for (SurfaceDTO dto : removeRows) {
				Surface surface = (Surface) CommonUtils.persistable(dto.getOid());
				PersistenceHelper.manager.delete(surface);
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
	public void edge(HashMap<String, List<EdgeDTO>> paramsMap) throws Exception {
		Transaction trs = new Transaction();
		try {
			trs.start();

			WTPrincipal prin = SessionHelper.manager.getPrincipal();

			List<EdgeDTO> addRows = paramsMap.get("addRows");
			for (EdgeDTO dto : addRows) {
				Edge edge = Edge.newEdge();
				edge.setRank(dto.getRank());
				edge.setDtmg_treatment(dto.getDtmg_treatment());
				edge.setCode(dto.getCode());
				edge.setEdgeType(dto.getEdgeType());
				edge.setEtc(dto.getEtc());
				edge.setOwnership(Ownership.newOwnership(prin));
				PersistenceHelper.manager.save(edge);
			}

			List<EdgeDTO> editRows = paramsMap.get("editRows");
			for (EdgeDTO dto : editRows) {
				Edge edge = (Edge) CommonUtils.persistable(dto.getOid());
				edge.setRank(dto.getRank());
				edge.setDtmg_treatment(dto.getDtmg_treatment());
				edge.setEtc(dto.getEtc());
				edge.setCode(dto.getCode());
				edge.setEdgeType(dto.getEdgeType());
				edge.setOwnership(Ownership.newOwnership(prin));
				PersistenceHelper.manager.modify(edge);
			}

			List<EdgeDTO> removeRows = paramsMap.get("removeRows");
			for (EdgeDTO dto : removeRows) {
				Edge edge = (Edge) CommonUtils.persistable(dto.getOid());
				PersistenceHelper.manager.delete(edge);
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
