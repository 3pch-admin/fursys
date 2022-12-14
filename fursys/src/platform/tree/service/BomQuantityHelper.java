package platform.tree.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import platform.tree.entity.BomQuantity;
import platform.tree.entity.BomQuantityDTO;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.query.ClassAttribute;
import wt.query.OrderBy;
import wt.query.QuerySpec;
import wt.services.ServiceFactory;
import wt.session.SessionContext;
import wt.session.SessionHelper;

public class BomQuantityHelper {

	public static final BomQuantityService service = ServiceFactory.getService(BomQuantityService.class);
	public static final BomQuantityHelper manager = new BomQuantityHelper();

	public List<BomQuantityDTO> list(Map<String, Object> params) throws Exception {
		// 검색 조건
		SessionContext prev = SessionContext.newContext();
		List<BomQuantityDTO> list = new ArrayList<BomQuantityDTO>();
		try {
			SessionHelper.manager.setAdministrator();
			QuerySpec query = new QuerySpec();
			int idx = query.appendClassList(BomQuantity.class, true);

			ClassAttribute ca = new ClassAttribute(BomQuantity.class, BomQuantity.SORT);
			OrderBy by = new OrderBy(ca, false);
			query.appendOrderBy(by, new int[] { idx });
			QueryResult result = PersistenceHelper.manager.find(query);

			while (result.hasMoreElements()) {
				Object[] obj = (Object[]) result.nextElement();
				BomQuantity quantity = (BomQuantity) obj[0];
				list.add(new BomQuantityDTO(quantity));
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
