package platform.color.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import platform.color.entity.Color;
import platform.color.entity.ColorColumns;
import platform.color.entity.ColorWTPartLink;
import platform.util.PageUtils;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.part.WTPart;
import wt.query.ClassAttribute;
import wt.query.OrderBy;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.services.ServiceFactory;

public class ColorHelper {

	public static final ColorService service = ServiceFactory.getService(ColorService.class);
	public static final ColorHelper manager = new ColorHelper();

	public Map<String, Object> list(Map<String, Object> params) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<ColorColumns> list = new ArrayList<ColorColumns>();
		QuerySpec query = new QuerySpec();
		String number = (String) params.get("number");
		String name = (String) params.get("name");
		int idx = query.appendClassList(Color.class, true);

		SearchCondition sc = new SearchCondition(Color.class, Color.COLOR_TYPE, "=", "MASTER");
		query.appendWhere(sc, new int[] { idx });

		ClassAttribute ca = new ClassAttribute(Color.class, Color.CREATE_TIMESTAMP);
		OrderBy by = new OrderBy(ca, false);
		query.appendOrderBy(by, new int[] { idx });

		PageUtils pager = new PageUtils(params, query);
		QueryResult result = pager.find();
		int total = pager.getTotal();
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			ColorColumns columns = new ColorColumns((Color) obj[0]);
			columns.setNo(total--);
			list.add(columns);
		}
		map.put("list", list);
		map.put("topListCount", pager.getTotal());
		map.put("sessionid", pager.getSessionId());
		map.put("curPage", pager.getCpage());
		map.put("pageSize", pager.getPsize());
		map.put("total", pager.getTotalSize());
		return map;
	}

	public Color getColor(WTPart part) throws Exception {
		Color color = null;
		QueryResult result = PersistenceHelper.manager.navigate(part, "color", ColorWTPartLink.class);
		if (result.hasMoreElements()) {
			color = (Color) result.nextElement();
		}
		return color;
	}

	public boolean isColorMaster(WTPart part, String applyColor) throws Exception {
		Color color = null;
		QueryResult result = PersistenceHelper.manager.navigate(part, "color", ColorWTPartLink.class);
		if (result.hasMoreElements()) {
			color = (Color) result.nextElement();
		}
		if (color == null) {
			return false;
		} else {
			if (applyColor.equals(color.getColor())) {
				return true;
			}
		}
		return false;
	}
}
