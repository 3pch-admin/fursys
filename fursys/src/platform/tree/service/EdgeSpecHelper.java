package platform.tree.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import platform.tree.entity.EMaterial;
import platform.tree.entity.EMaterialDTO;
import platform.tree.entity.EShape;
import platform.tree.entity.EShapeDTO;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.query.ClassAttribute;
import wt.query.OrderBy;
import wt.query.QuerySpec;
import wt.services.ServiceFactory;
import wt.session.SessionContext;
import wt.session.SessionHelper;

public class EdgeSpecHelper {

	public static final EdgeSpecService service = ServiceFactory.getService(EdgeSpecService.class);
	public static final EdgeSpecHelper manager = new EdgeSpecHelper();

	public List<EMaterialDTO> ematerial(Map<String, Object> params) throws Exception {
		// 검색조건
		SessionContext prev = SessionContext.newContext();
		List<EMaterialDTO> list = new ArrayList<EMaterialDTO>();
		try {
			SessionHelper.manager.setAdministrator();
			QuerySpec query = new QuerySpec();
			int idx = query.appendClassList(EMaterial.class, true);

			ClassAttribute ca = new ClassAttribute(EMaterial.class, EMaterial.RANK);
			OrderBy by = new OrderBy(ca, true);
			query.appendOrderBy(by, new int[] { idx });
			QueryResult result = PersistenceHelper.manager.find(query);

			while (result.hasMoreElements()) {
				Object[] obj = (Object[]) result.nextElement();
				EMaterial ematerial = (EMaterial) obj[0];
				list.add(new EMaterialDTO(ematerial));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			SessionContext.setContext(prev);
		}
		return list;
	}

	public List<EShapeDTO> eshape(Map<String, Object> params) throws Exception {
		SessionContext prev = SessionContext.newContext();
		List<EShapeDTO> list = new ArrayList<EShapeDTO>();
		try {
			SessionHelper.manager.setAdministrator();
			QuerySpec query = new QuerySpec();
			int idx = query.appendClassList(EShape.class, true);

			ClassAttribute ca = new ClassAttribute(EShape.class, EShape.RANK);
			OrderBy by = new OrderBy(ca, true);
			query.appendOrderBy(by, new int[] { idx });
			QueryResult result = PersistenceHelper.manager.find(query);

			while (result.hasMoreElements()) {
				Object[] obj = (Object[]) result.nextElement();
				EShape eshape = (EShape) obj[0];
				list.add(new EShapeDTO(eshape));
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
