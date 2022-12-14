package platform.attr.service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import platform.attr.entity.Attr;
import platform.attr.entity.AttrDTO;
import platform.util.StringUtils;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.query.ClassAttribute;
import wt.query.OrderBy;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.services.ServiceFactory;
import wt.session.SessionContext;
import wt.session.SessionHelper;

public class AttrHelper {

	public static final AttrService service = ServiceFactory.getService(AttrService.class);
	public static final AttrHelper manager = new AttrHelper();

	public List<AttrDTO> list(Map<String, Object> params) throws Exception {
		String cadKey = (String) params.get("cadKey");
		SessionContext prev = SessionContext.newContext();
		// 검색 조건
		List<AttrDTO> list = new ArrayList<AttrDTO>();
		try {
			SessionHelper.manager.setAdministrator();
			QuerySpec query = new QuerySpec();
			int idx = query.appendClassList(Attr.class, true);

			SearchCondition sc = null;
			if (StringUtils.isNotNull(cadKey)) {
				sc = new SearchCondition(Attr.class, Attr.CAD_KEY, "=", cadKey);
				query.appendWhere(sc, new int[] { idx });
			}

			ClassAttribute ca = new ClassAttribute(Attr.class, Attr.CAD_KEY);
			OrderBy by = new OrderBy(ca, true);
			query.appendOrderBy(by, new int[] { idx });

			QueryResult result = PersistenceHelper.manager.find(query);

			while (result.hasMoreElements()) {
				Object[] obj = (Object[]) result.nextElement();
				Attr attr = (Attr) obj[0];
				list.add(new AttrDTO(attr));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			SessionContext.setContext(prev);
		}
		return list;
	}

	public float getValue(String cadKey, String field) throws Exception {

		String suffix = field.substring(0, 1).toUpperCase();
		String prefix = field.substring(1).toLowerCase();

		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(Attr.class, true);

		SearchCondition sc = new SearchCondition(Attr.class, Attr.CAD_KEY, "=", cadKey.trim());
		query.appendWhere(sc, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find(query);
		if (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			AttrDTO dto = new AttrDTO((Attr) obj[0]);
			Class<AttrDTO> dtoClass = AttrDTO.class;

			Method getPosition = dtoClass.getMethod("get" + suffix + prefix);
			float position = Float.parseFloat((String) getPosition.invoke(dto, null));
			return position;
		}
		return 0f;
	}

}
