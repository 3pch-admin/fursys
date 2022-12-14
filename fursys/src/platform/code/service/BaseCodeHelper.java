package platform.code.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import platform.code.entity.BaseCode;
import platform.code.entity.BaseCodeDTO;
import platform.util.StringUtils;
import wt.fc.PersistenceHelper;
import wt.fc.PersistenceServerHelper;
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

public class BaseCodeHelper {

	public static final BaseCodeService service = ServiceFactory.getService(BaseCodeService.class);
	public static final BaseCodeHelper manager = new BaseCodeHelper();

	public ArrayList<BaseCode> getBaseCodeByCodeType(String codeType) throws Exception {
		ArrayList<BaseCode> list = new ArrayList<BaseCode>();

		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(BaseCode.class, true);

		SearchCondition sc = new SearchCondition(BaseCode.class, BaseCode.CODE_TYPE, "=", codeType);
		query.appendWhere(sc, new int[] { idx });

		ClassAttribute ca = new ClassAttribute(BaseCode.class, WTAttributeNameIfc.CREATE_STAMP_NAME);
		OrderBy by = new OrderBy(ca, false);
		query.appendOrderBy(by, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find(query);
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			BaseCode baseCode = (BaseCode) obj[0];
			list.add(baseCode);
		}
		return list;
	}

	public int getSort(String codeType) throws Exception {

		int sort = 0;
		QuerySpec query = new QuerySpec();

		int idx = query.appendClassList(BaseCode.class, false);
		query.setAdvancedQueryEnabled(true);
		SearchCondition sc = null;

		ClassAttribute ca = new ClassAttribute(BaseCode.class, BaseCode.SORT);
		SQLFunction function = SQLFunction.newSQLFunction(SQLFunction.MAXIMUM, ca);
		query.appendSelect(function, false);

		sc = new SearchCondition(BaseCode.class, BaseCode.CODE_TYPE, "=", codeType);
		query.appendWhere(sc, new int[] { idx });

		QueryResult result = PersistenceServerHelper.manager.query(query);
		if (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			if (obj[0] == null) {
				sort = 0;
			} else {
				sort = ((BigDecimal) obj[0]).intValue() + 1;
			}
		}
		return sort;
	}

	public String getNameByCodeTypeAndCode(String codeType, String code) throws Exception {

		if (!StringUtils.isNotNull(code)) {
			code = "";
		}

		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(BaseCode.class, true);

		SearchCondition sc = new SearchCondition(BaseCode.class, BaseCode.CODE_TYPE, "=", codeType);
		query.appendWhere(sc, new int[] { idx });
		query.appendAnd();

		sc = new SearchCondition(BaseCode.class, BaseCode.CODE, "=", code);
		query.appendWhere(sc, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find(query);
		BaseCode baseCode = null;
		if (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			baseCode = (BaseCode) obj[0];
		}
		return baseCode != null ? baseCode.getName() : "";
	}

	public BaseCode getBaseCodeByCodeTypeAndCode(String codeType, String code) throws Exception {

		if (!StringUtils.isNotNull(code)) {
			code = "";
		}

		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(BaseCode.class, true);

		SearchCondition sc = new SearchCondition(BaseCode.class, BaseCode.CODE_TYPE, "=", codeType);
		query.appendWhere(sc, new int[] { idx });
		query.appendAnd();

		sc = new SearchCondition(BaseCode.class, BaseCode.CODE, "=", code);
		query.appendWhere(sc, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find(query);
		BaseCode baseCode = null;
		if (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			baseCode = (BaseCode) obj[0];
		}
		return baseCode;
	}

	public List<BaseCodeDTO> list(Map<String, Object> params) throws Exception {
		List<BaseCodeDTO> list = new ArrayList<BaseCodeDTO>();
		String codeType = (String) params.get("codeType");
		String name = (String) params.get("name");
		String code = (String) params.get("code");
		String enable = (String) params.get("enable");
		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(BaseCode.class, true);
		SearchCondition sc = null;
		ClassAttribute ca = null;

		if (StringUtils.isNotNull(name)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			ca = new ClassAttribute(BaseCode.class, BaseCode.NAME);
			ColumnExpression ce = ConstantExpression.newExpression("%" + name.toUpperCase() + "%");
			SQLFunction function = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca);
			sc = new SearchCondition(function, SearchCondition.LIKE, ce);
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(code)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
//			ca = new ClassAttribute(BaseCode.class, BaseCode.CODE);
//			ColumnExpression ce = ConstantExpression.newExpression("%" + code.toUpperCase() + "%");
//			SQLFunction function = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca);
//			sc = new SearchCondition(function, SearchCondition.LIKE, ce);
			sc = new SearchCondition(BaseCode.class, BaseCode.CODE, "=", code.toUpperCase());
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(codeType)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(BaseCode.class, BaseCode.CODE_TYPE, "=", codeType);
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(enable)) {
			if (query.getConditionCount() > 0)
				query.appendAnd();
			if (Boolean.parseBoolean(enable)) {
				sc = new SearchCondition(BaseCode.class, BaseCode.ENABLE, SearchCondition.IS_TRUE);
				query.appendWhere(sc, new int[] { idx });
			} else if (!Boolean.parseBoolean(enable)) {
				sc = new SearchCondition(BaseCode.class, BaseCode.ENABLE, SearchCondition.IS_FALSE);
				query.appendWhere(sc, new int[] { idx });
			}
		}

		ca = new ClassAttribute(BaseCode.class, BaseCode.SORT);
		OrderBy orderBy = new OrderBy(ca, false);
		query.appendOrderBy(orderBy, new int[] { idx });
		QueryResult result = PersistenceHelper.manager.find(query);
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			BaseCodeDTO dto = new BaseCodeDTO((BaseCode) obj[0]);
			list.add(dto);
		}
		return list;
	}

	public ArrayList<Map<String, Object>> getCodeMap(String codeType) throws Exception {
		ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(BaseCode.class, true);

		SearchCondition sc = new SearchCondition(BaseCode.class, BaseCode.CODE_TYPE, "=", codeType);
		query.appendWhere(sc, new int[] { idx });
		query.appendAnd();

//		sc = new SearchCondition(BaseCode.class, BaseCode.CODE, "=", code);
//		query.appendWhere(sc, new int[] { idx });
//		query.appendAnd();

		sc = new SearchCondition(BaseCode.class, BaseCode.ENABLE, SearchCondition.IS_TRUE);
		query.appendWhere(sc, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find(query);
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			BaseCode baseCode = (BaseCode) obj[0];
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", baseCode.getName());
//			map.put("oid", baseCode.getPersistInfo().getObjectIdentifier().getStringValue());
			map.put("code", baseCode.getCode());
			list.add(map);
		}
		return list;
	}
}
