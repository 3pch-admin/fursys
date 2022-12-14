package platform.tree.service;

import java.util.HashMap;
import java.util.List;

import platform.tree.entity.EManufacturing;
import platform.tree.entity.EManufacturingDTO;
import platform.tree.entity.EMaterialInfo;
import platform.tree.entity.EMaterialInfoDTO;
import platform.tree.entity.ESurface;
import platform.tree.entity.ESurfaceDTO;
import platform.util.CommonUtils;
import wt.fc.PersistenceHelper;
import wt.org.WTPrincipal;
import wt.ownership.Ownership;
import wt.pom.Transaction;
import wt.services.StandardManager;
import wt.session.SessionHelper;
import wt.util.WTException;

public class StandardExcTreeService extends StandardManager implements ExcTreeService {

	public static StandardExcTreeService newStandardExcTreeService() throws WTException {
		StandardExcTreeService instance = new StandardExcTreeService();
		instance.initialize();
		return instance;
	}

	@Override
	public void ematerial(HashMap<String, List<EMaterialInfoDTO>> paramsMap) throws Exception {
		Transaction trs = new Transaction();
		try {
			trs.start();

			WTPrincipal prin = SessionHelper.manager.getPrincipal();

			List<EMaterialInfoDTO> addRows = paramsMap.get("addRows");
			for (EMaterialInfoDTO dto : addRows) {
				EMaterialInfo ematerialinfo = EMaterialInfo.newEMaterialInfo();
				ematerialinfo.setCat_rank(dto.getCat_rank());
				ematerialinfo.setRank(dto.getRank());
				ematerialinfo.setCode(dto.getCode());
				ematerialinfo.setMaterial(dto.getMaterial());
				ematerialinfo.setCat_l(dto.getCat_l());
				ematerialinfo.setErp_l(dto.getErp_l());
				ematerialinfo.setCat_m(dto.getCat_m());
				ematerialinfo.setErp_m(dto.getErp_m());
				ematerialinfo.setCat_s(dto.getCat_s());
				ematerialinfo.setErp_s(dto.getErp_s());
				ematerialinfo.setMaterialType(dto.getMaterialType());
				ematerialinfo.setMaterial_name(dto.getMaterial_name());
				ematerialinfo.setMaterialTypeCode(dto.getMaterialTypeCode());
				ematerialinfo.setOwnership(Ownership.newOwnership(prin));
				PersistenceHelper.manager.save(ematerialinfo);
			}

			List<EMaterialInfoDTO> editRows = paramsMap.get("editRows");
			for (EMaterialInfoDTO dto : editRows) {
				EMaterialInfo ematerialinfo = (EMaterialInfo) CommonUtils.persistable(dto.getOid());
				ematerialinfo.setCat_rank(dto.getCat_rank());
				ematerialinfo.setRank(dto.getRank());
				ematerialinfo.setCode(dto.getCode());
				ematerialinfo.setMaterial(dto.getMaterial());
				ematerialinfo.setMaterial_name(dto.getMaterial_name());
				ematerialinfo.setCat_l(dto.getCat_l());
				ematerialinfo.setErp_l(dto.getErp_l());
				ematerialinfo.setCat_m(dto.getCat_m());
				ematerialinfo.setErp_m(dto.getErp_m());
				ematerialinfo.setCat_s(dto.getCat_s());
				ematerialinfo.setErp_s(dto.getErp_s());
				ematerialinfo.setMaterialType(dto.getMaterialType());
				ematerialinfo.setMaterialTypeCode(dto.getMaterialTypeCode());
				PersistenceHelper.manager.modify(ematerialinfo);
			}

			List<EMaterialInfoDTO> removeRows = paramsMap.get("removeRows");
			for (EMaterialInfoDTO dto : removeRows) {
				EMaterialInfo ematerialinfo = (EMaterialInfo) CommonUtils.persistable(dto.getOid());
				PersistenceHelper.manager.delete(ematerialinfo);
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
	public void esurface(HashMap<String, List<ESurfaceDTO>> paramsMap) throws Exception {
		Transaction trs = new Transaction();
		try {
			trs.start();

			WTPrincipal prin = SessionHelper.manager.getPrincipal();

			List<ESurfaceDTO> addRows = paramsMap.get("addRows");
			for (ESurfaceDTO dto : addRows) {
				ESurface esurface = ESurface.newESurface();
				esurface.setCode(dto.getCode());
				esurface.setRank(dto.getRank());
				esurface.setSurface(dto.getSurface());
				esurface.setDTMG_esurface(dto.getDTMG_esurface());
				esurface.setOwnership(Ownership.newOwnership(prin));
				PersistenceHelper.manager.save(esurface);
			}

			List<ESurfaceDTO> editRows = paramsMap.get("editRows");
			for (ESurfaceDTO dto : editRows) {
				ESurface esurface = (ESurface) CommonUtils.persistable(dto.getOid());
				esurface.setCode(dto.getCode());
				esurface.setRank(dto.getRank());
				esurface.setSurface(dto.getSurface());
				esurface.setDTMG_esurface(dto.getDTMG_esurface());
				PersistenceHelper.manager.modify(esurface);
			}

			List<ESurfaceDTO> removeRows = paramsMap.get("removeRows");
			for (ESurfaceDTO dto : removeRows) {
				ESurface esurface = (ESurface) CommonUtils.persistable(dto.getOid());
				PersistenceHelper.manager.delete(esurface);
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
	public void emanufacturing(HashMap<String, List<EManufacturingDTO>> paramsMap) throws Exception {
		Transaction trs = new Transaction();
		try {
			trs.start();

			WTPrincipal prin = SessionHelper.manager.getPrincipal();

			List<EManufacturingDTO> addRows = paramsMap.get("addRows");
			for (EManufacturingDTO dto : addRows) {
				EManufacturing emanufacturing = EManufacturing.newEManufacturing();
				emanufacturing.setRank(dto.getRank());
				emanufacturing.setCode(dto.getCode());
				emanufacturing.setManufacturing(dto.getManufacturing());
				emanufacturing.setOwnership(Ownership.newOwnership(prin));
				PersistenceHelper.manager.save(emanufacturing);
			}

			List<EManufacturingDTO> editRows = paramsMap.get("editRows");
			for (EManufacturingDTO dto : editRows) {
				EManufacturing emanufacturing = (EManufacturing) CommonUtils.persistable(dto.getOid());
				emanufacturing.setRank(dto.getRank());
				emanufacturing.setCode(dto.getCode());
				emanufacturing.setManufacturing(dto.getManufacturing());
				PersistenceHelper.manager.modify(emanufacturing);
			}

			List<EManufacturingDTO> removeRows = paramsMap.get("removeRows");
			for (EManufacturingDTO dto : removeRows) {
				EManufacturing emanufacturing = (EManufacturing) CommonUtils.persistable(dto.getOid());
				PersistenceHelper.manager.delete(emanufacturing);
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
