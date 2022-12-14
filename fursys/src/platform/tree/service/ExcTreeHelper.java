package platform.tree.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import platform.tree.entity.EManufacturing;
import platform.tree.entity.EManufacturingDTO;
import platform.tree.entity.EMaterialInfo;
import platform.tree.entity.EMaterialInfoDTO;
import platform.tree.entity.ESurface;
import platform.tree.entity.ESurfaceDTO;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.query.ClassAttribute;
import wt.query.OrderBy;
import wt.query.QuerySpec;
import wt.services.ServiceFactory;
import wt.session.SessionContext;
import wt.session.SessionHelper;

public class ExcTreeHelper {

	public static final ExcTreeService service = ServiceFactory.getService(ExcTreeService.class);
	public static final ExcTreeHelper manager = new ExcTreeHelper();

	public List<EManufacturingDTO> emanufacturing(Map<String, Object> params) throws Exception {
		SessionContext prev = SessionContext.newContext();
		// 검색 조건
		List<EManufacturingDTO> list = new ArrayList<EManufacturingDTO>();
		try {
			SessionHelper.manager.setAdministrator();
			QuerySpec query = new QuerySpec();
			int idx = query.appendClassList(EManufacturing.class, true);

			ClassAttribute ca = new ClassAttribute(EManufacturing.class, EManufacturing.RANK);
			OrderBy by = new OrderBy(ca, false);
			query.appendOrderBy(by, new int[] { idx });
			QueryResult result = PersistenceHelper.manager.find(query);

			while (result.hasMoreElements()) {
				Object[] obj = (Object[]) result.nextElement();
				EManufacturing emanufacturing = (EManufacturing) obj[0];
				list.add(new EManufacturingDTO(emanufacturing));
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			SessionContext.setContext(prev);
		}
		return list;
	}


	public List<ESurfaceDTO> esurface(Map<String, Object> params) throws Exception {
		SessionContext prev = SessionContext.newContext();
		List<ESurfaceDTO> list = new ArrayList<ESurfaceDTO>();
		try {
			SessionHelper.manager.setAdministrator();
			QuerySpec query = new QuerySpec();
			int idx = query.appendClassList(ESurface.class, true);

			ClassAttribute ca = new ClassAttribute(ESurface.class, ESurface.RANK);
			OrderBy by = new OrderBy(ca, false);
			query.appendOrderBy(by, new int[] { idx });
			QueryResult result = PersistenceHelper.manager.find(query);

			while (result.hasMoreElements()) {
				Object[] obj = (Object[]) result.nextElement();
				ESurface esurface = (ESurface) obj[0];
				list.add(new ESurfaceDTO(esurface));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			SessionContext.setContext(prev);
		}
		return list;
	}

	public List<EMaterialInfoDTO> ematerial(Map<String, Object> params) throws Exception {
		SessionContext prev = SessionContext.newContext();
		List<EMaterialInfoDTO> list = new ArrayList<EMaterialInfoDTO>();
		try {
			SessionHelper.manager.setAdministrator();
			QuerySpec query = new QuerySpec();
			int idx = query.appendClassList(EMaterialInfo.class, true);
			ClassAttribute ca = null;

			ca = new ClassAttribute(EMaterialInfo.class,  EMaterialInfo.CAT_RANK);
			OrderBy by = new OrderBy(ca, false);
			query.appendOrderBy(by, new int[] { idx });
			
			ca = new ClassAttribute(EMaterialInfo.class, EMaterialInfo.RANK);
			OrderBy by2 = new OrderBy(ca, false);
			query.appendOrderBy(by2, new int[] { idx });
			QueryResult result = PersistenceHelper.manager.find(query);

			while (result.hasMoreElements()) {
				Object[] obj = (Object[]) result.nextElement();
				EMaterialInfo ematerialInfo = (EMaterialInfo) obj[0];
				list.add(new EMaterialInfoDTO(ematerialInfo));
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
