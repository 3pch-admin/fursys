package platform.tree.service;

import java.util.HashMap;
import java.util.List;

import platform.tree.entity.EMaterial;
import platform.tree.entity.EMaterialDTO;
import platform.tree.entity.EShape;
import platform.tree.entity.EShapeDTO;
import platform.util.CommonUtils;
import wt.fc.PersistenceHelper;
import wt.org.WTPrincipal;
import wt.ownership.Ownership;
import wt.pom.Transaction;
import wt.services.StandardManager;
import wt.session.SessionHelper;
import wt.util.WTException;


public class StandardEdgeSpecService extends StandardManager implements EdgeSpecService{

	public static StandardEdgeSpecService newStandardEdgeSpecService() throws WTException{
		StandardEdgeSpecService instance = new StandardEdgeSpecService();
		instance.initialize();
		return instance;
	}
	
	@Override
	public void ematerial(HashMap<String, List<EMaterialDTO>> paramsMap) throws Exception {
		Transaction trs = new Transaction();
		try {
			trs.start();
			
			WTPrincipal prin = SessionHelper.manager.getPrincipal();
			
			List<EMaterialDTO> addRows = paramsMap.get("addRows");
			for(EMaterialDTO dto : addRows) {
				EMaterial  ematerial = EMaterial.newEMaterial();
				ematerial.setCode(dto.getCode());
				ematerial.setRank(dto.getRank());
				ematerial.setMaterial(dto.getMaterial());
				ematerial.setOwnership(Ownership.newOwnership(prin));
				PersistenceHelper.manager.save(ematerial);
			}
			
			List<EMaterialDTO> editRows = paramsMap.get("editRows");
			for(EMaterialDTO dto : editRows) {
				EMaterial ematerial = (EMaterial) CommonUtils.persistable(dto.getOid());
				ematerial.setCode(dto.getCode());
				ematerial.setRank(dto.getRank());
				ematerial.setMaterial(dto.getMaterial());
				PersistenceHelper.manager.modify(ematerial);
			}
			
			List<EMaterialDTO> removeRows = paramsMap.get("removeRows");
			for(EMaterialDTO dto : removeRows) {
				EMaterial ematerial = (EMaterial) CommonUtils.persistable(dto.getOid());
				PersistenceHelper.manager.delete(ematerial);
			}
			
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
	}
	
	@Override
	public void eshape(HashMap<String, List<EShapeDTO>> paramsMap) throws Exception {
		Transaction trs = new Transaction();
		try {
			trs.start();
			
			WTPrincipal prin = SessionHelper.manager.getPrincipal();
			
			List<EShapeDTO> addRows = paramsMap.get("addRows");
			for(EShapeDTO dto : addRows) {
				EShape  eshape = EShape.newEShape();
				eshape.setCode(dto.getCode());
				eshape.setRank(dto.getRank());
				eshape.setShape(dto.getShape());
				eshape.setOwnership(Ownership.newOwnership(prin));
				PersistenceHelper.manager.save(eshape);
			}
			
			List<EShapeDTO> editRows = paramsMap.get("editRows");
			for(EShapeDTO dto : editRows) {
				EShape eshape = (EShape) CommonUtils.persistable(dto.getOid());
				eshape.setCode(dto.getCode());
				eshape.setRank(dto.getRank());
				eshape.setShape(dto.getShape());
				PersistenceHelper.manager.modify(eshape);
			}
			
			List<EShapeDTO> removeRows = paramsMap.get("removeRows");
			for(EShapeDTO dto : removeRows) {
				EShape eshape = (EShape) CommonUtils.persistable(dto.getOid());
				PersistenceHelper.manager.delete(eshape);
			}
			
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
	}
}
