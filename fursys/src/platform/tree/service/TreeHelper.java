package platform.tree.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.query.ClassAttribute;
import wt.query.OrderBy;
import wt.query.QuerySpec;
import wt.services.ServiceFactory;
import wt.session.SessionContext;
import wt.session.SessionHelper;

public class TreeHelper {

	public static final TreeService service = ServiceFactory.getService(TreeService.class);
	public static final TreeHelper manager = new TreeHelper();

	public List<CombinationDTO> combination(Map<String, Object> params) throws Exception {
		SessionContext prev = SessionContext.newContext();
		// 검색 조건
		List<CombinationDTO> list = new ArrayList<CombinationDTO>();
		try {
			SessionHelper.manager.setAdministrator();
			QuerySpec query = new QuerySpec();
			int idx = query.appendClassList(Combination.class, true);

			ClassAttribute ca = new ClassAttribute(Combination.class, Combination.RANK);
			OrderBy by = new OrderBy(ca, false);
			query.appendOrderBy(by, new int[] { idx });
			QueryResult result = PersistenceHelper.manager.find(query);

			while (result.hasMoreElements()) {
				Object[] obj = (Object[]) result.nextElement();
				Combination combination = (Combination) obj[0];
				list.add(new CombinationDTO(combination));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			SessionContext.setContext(prev);
		}
		return list;
	}

	public List<EdgeDTO> edge(Map<String, Object> params) throws Exception {
		SessionContext prev = SessionContext.newContext();
		List<EdgeDTO> list = new ArrayList<EdgeDTO>();
		try {
			SessionHelper.manager.setAdministrator();
			QuerySpec query = new QuerySpec();
			int idx = query.appendClassList(Edge.class, true);
			

			ClassAttribute ca = new ClassAttribute(Edge.class, Edge.RANK);
			OrderBy by = new OrderBy(ca, false);
			query.appendOrderBy(by, new int[] { idx });
			QueryResult result = PersistenceHelper.manager.find(query);
			while (result.hasMoreElements()) {
				Object[] obj = (Object[]) result.nextElement();
				Edge edge = (Edge) obj[0];
				list.add(new EdgeDTO(edge));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			SessionContext.setContext(prev);
		}
		return list;
	}

	public List<SurfaceDTO> surface(Map<String, Object> params) throws Exception {
		SessionContext prev = SessionContext.newContext();
		List<SurfaceDTO> list = new ArrayList<SurfaceDTO>();
		try {
			SessionHelper.manager.setAdministrator();
			QuerySpec query = new QuerySpec();
			int idx = query.appendClassList(Surface.class, true);
			

			ClassAttribute ca = new ClassAttribute(Surface.class, Surface.RANK);
			OrderBy by = new OrderBy(ca, false);
			query.appendOrderBy(by, new int[] { idx });
			QueryResult result = PersistenceHelper.manager.find(query);
			while (result.hasMoreElements()) {
				Object[] obj = (Object[]) result.nextElement();
				Surface surface = (Surface) obj[0];
				list.add(new SurfaceDTO(surface));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}  finally {
			SessionContext.setContext(prev);
		}
		return list;
	}

	public List<TreatmentDTO> treatment(Map<String, Object> params) throws Exception {
		SessionContext prev = SessionContext.newContext();
		List<TreatmentDTO> list = new ArrayList<TreatmentDTO>();
		try {
			SessionHelper.manager.setAdministrator();
			QuerySpec query = new QuerySpec();
			int idx = query.appendClassList(Treatment.class, true);

			ClassAttribute ca = new ClassAttribute(Treatment.class, Treatment.RANK);
			OrderBy by = new OrderBy(ca, false);
			query.appendOrderBy(by, new int[] { idx });
			QueryResult result = PersistenceHelper.manager.find(query);

			while (result.hasMoreElements()) {
				Object[] obj = (Object[]) result.nextElement();
				Treatment treatment = (Treatment) obj[0];
				list.add(new TreatmentDTO(treatment));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			SessionContext.setContext(prev);
		}
		return list;
	}

	public List<MaterialInfoDTO> material(Map<String, Object> params) throws Exception {
		SessionContext prev = SessionContext.newContext();
		List<MaterialInfoDTO> list = new ArrayList<MaterialInfoDTO>();
		try {
			SessionHelper.manager.setAdministrator();
			QuerySpec query = new QuerySpec();
			int idx = query.appendClassList(MaterialInfo.class, true);

			ClassAttribute ca = new ClassAttribute(MaterialInfo.class, MaterialInfo.RANK);
			OrderBy by = new OrderBy(ca, false);
			query.appendOrderBy(by, new int[] { idx });
			QueryResult result = PersistenceHelper.manager.find(query);

			while (result.hasMoreElements()) {
				Object[] obj = (Object[]) result.nextElement();
				MaterialInfo materialInfo = (MaterialInfo) obj[0];
				list.add(new MaterialInfoDTO(materialInfo));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			SessionContext.setContext(prev);
		}
		return list;
	}
}
