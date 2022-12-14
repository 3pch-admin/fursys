package platform.raonk.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import platform.raonk.entity.Raonk;
import platform.raonk.entity.RaonkColumns;
import platform.util.CommonUtils;
import platform.util.DateUtils;
import platform.util.StringUtils;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.query.ClassAttribute;
import wt.query.ColumnExpression;
import wt.query.ConstantExpression;
import wt.query.OrderBy;
import wt.query.QuerySpec;
import wt.query.SQLFunction;
import wt.query.SearchCondition;
import wt.services.ServiceFactory;
import wt.util.WTAttributeNameIfc;

public class RaonkHelper {

	public static final RaonkService service = ServiceFactory.getService(RaonkService.class);
	public static final RaonkHelper manager = new RaonkHelper();

	public List<RaonkColumns> list(Map<String, Object> params) throws Exception {
		List<RaonkColumns> list = new ArrayList<>();
		QuerySpec query = new QuerySpec();

		String name = (String) params.get("name");
		String raonkType = (String) params.get("raonkType");
		String description = (String) params.get("description");
		String enable = (String) params.get("enable");
		String startCreatedDate = (String) params.get("startCreatedDate");
		String endCreatedDate = (String) params.get("endCreatedDate");
		int idx = query.appendClassList(Raonk.class, true);

		SearchCondition sc = null;
		ClassAttribute ca = null;

		if (StringUtils.isNotNull(name)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			ca = new ClassAttribute(Raonk.class, Raonk.NAME);
			ColumnExpression ce = ConstantExpression.newExpression("%" + name.toUpperCase() + "%");
			SQLFunction function = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca);
			sc = new SearchCondition(function, SearchCondition.LIKE, ce);
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(enable)) {
			if (query.getConditionCount() > 0)
				query.appendAnd();
			if (Boolean.parseBoolean(enable)) {
				sc = new SearchCondition(Raonk.class, Raonk.ENABLE, SearchCondition.IS_TRUE);
				query.appendWhere(sc, new int[] { idx });
			} else if (!Boolean.parseBoolean(enable)) {
				sc = new SearchCondition(Raonk.class, Raonk.ENABLE, SearchCondition.IS_FALSE);
				query.appendWhere(sc, new int[] { idx });
			}
		}

		if (StringUtils.isNotNull(startCreatedDate)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(Raonk.class, WTAttributeNameIfc.CREATE_STAMP_NAME, ">=",
					DateUtils.startTimestamp(startCreatedDate));
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(endCreatedDate)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(Raonk.class, WTAttributeNameIfc.CREATE_STAMP_NAME, "<=",
					DateUtils.endTimestamp(endCreatedDate));
			query.appendWhere(sc, new int[] { idx });
		}

		ca = new ClassAttribute(Raonk.class, WTAttributeNameIfc.CREATE_STAMP_NAME);
		OrderBy orderBy = new OrderBy(ca, true);
		query.appendOrderBy(orderBy, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find(query);
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			RaonkColumns columns = new RaonkColumns((Raonk) obj[0]);
			list.add(columns);
		}
		return list;
	}

	public ArrayList<Raonk> get() throws Exception {
		ArrayList<Raonk> list = new ArrayList<Raonk>();
		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(Raonk.class, true);

		ClassAttribute ca = new ClassAttribute(Raonk.class, Raonk.NAME);
		OrderBy by = new OrderBy(ca, false);
		query.appendOrderBy(by, new int[] { idx });
		QueryResult result = PersistenceHelper.manager.find(query);
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			Raonk raonk = (Raonk) obj[0];
			list.add(raonk);
		}
		return list;
	}

	public String content(String oid) throws Exception {
		Raonk raonk = (Raonk) CommonUtils.persistable(oid);
		return raonk.getContents();
	}
}
