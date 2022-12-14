package platform.tree.service;

import java.util.HashMap;
import java.util.List;

import platform.tree.entity.BomQuantity;
import platform.tree.entity.BomQuantityDTO;
import platform.tree.entity.Combination;
import platform.tree.entity.CombinationDTO;
import platform.util.CommonUtils;
import wt.fc.PersistenceHelper;
import wt.org.WTPrincipal;
import wt.ownership.Ownership;
import wt.pom.Transaction;
import wt.services.StandardManager;
import wt.session.SessionHelper;
import wt.util.WTException;

public class StandardBomQuantityService extends StandardManager implements BomQuantityService {

	public static StandardBomQuantityService newStandardBomQuantityService() throws WTException {
		StandardBomQuantityService instance = new StandardBomQuantityService();
		instance.initialize();
		return instance;
	}

	@Override
	public void create(HashMap<String, List<BomQuantityDTO>> paramsMap) throws Exception {
		Transaction trs = new Transaction();
		try {
			trs.start();

			WTPrincipal prin = SessionHelper.manager.getPrincipal();

			List<BomQuantityDTO> addRows = paramsMap.get("addRows");
			for (BomQuantityDTO dto : addRows) {
				BomQuantity quantity = BomQuantity.newBomQuantity();
				quantity.setCommonCode(dto.getCommonCode());
				quantity.setSort(dto.getSort());
				quantity.setCommonName(dto.getCommonName());
				quantity.setMaterialInfo(dto.getMaterialInfo());
				quantity.setProcessInfo(dto.getProcessInfo());
				quantity.setTreatment(dto.getTreatment());
				quantity.setBomLevel(dto.getBomLevel());
				quantity.setRequiredProcess(dto.getRequiredProcess());
				quantity.setMaterialCode(dto.getMaterialCode());
				quantity.setColor(dto.getColor());
				quantity.setMaterialName(dto.getMaterialName());
				quantity.setBom_unit(dto.getBom_unit());
				quantity.setX1(dto.getX1());
				quantity.setX2(dto.getX2());
				quantity.setProcessMargin(dto.getProcessMargin());
				quantity.setProcessValue(dto.getProcessValue());
				quantity.setLoss(dto.getLoss());
				quantity.setUnitProcessAmount(dto.getUnitProcessAmount());
				quantity.setUnitConversion(dto.getUnitConversion());
				quantity.setConversionFactor(dto.getConversionFactor());
				quantity.setOwnership(Ownership.newOwnership(prin));
				PersistenceHelper.manager.save(quantity);
			}

			List<BomQuantityDTO> editRows = paramsMap.get("editRows");
			for (BomQuantityDTO dto : editRows) {
				BomQuantity quantity = (BomQuantity) CommonUtils.persistable(dto.getOid());
				quantity.setCommonCode(dto.getCommonCode());
				quantity.setSort(dto.getSort());
				quantity.setCommonName(dto.getCommonName());
				quantity.setMaterialInfo(dto.getMaterialInfo());
				quantity.setProcessInfo(dto.getProcessInfo());
				quantity.setTreatment(dto.getTreatment());
				quantity.setBomLevel(dto.getBomLevel());
				quantity.setRequiredProcess(dto.getRequiredProcess());
				quantity.setMaterialCode(dto.getMaterialCode());
				quantity.setColor(dto.getColor());
				quantity.setMaterialName(dto.getMaterialName());
				quantity.setBom_unit(dto.getBom_unit());
				quantity.setX1(dto.getX1());
				quantity.setX2(dto.getX2());
				quantity.setProcessMargin(dto.getProcessMargin());
				quantity.setProcessValue(dto.getProcessValue());
				quantity.setLoss(dto.getLoss());
				quantity.setUnitProcessAmount(dto.getUnitProcessAmount());
				quantity.setUnitConversion(dto.getUnitConversion());
				quantity.setConversionFactor(dto.getConversionFactor());
				PersistenceHelper.manager.modify(quantity);
			}

			List<BomQuantityDTO> removeRows = paramsMap.get("removeRows");
			for (BomQuantityDTO dto : removeRows) {
				BomQuantity quantity = (BomQuantity) CommonUtils.persistable(dto.getOid());
				PersistenceHelper.manager.delete(quantity);
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
