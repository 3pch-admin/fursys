package platform.util;

import java.math.BigDecimal;
import java.util.ArrayList;

import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.iba.definition.BooleanDefinition;
import wt.iba.definition.BooleanDefinitionReference;
import wt.iba.definition.FloatDefinition;
import wt.iba.definition.FloatDefinitionReference;
import wt.iba.definition.IntegerDefinition;
import wt.iba.definition.IntegerDefinitionReference;
import wt.iba.definition.StringDefinition;
import wt.iba.definition.StringDefinitionReference;
import wt.iba.definition.litedefinition.AttributeDefDefaultView;
import wt.iba.definition.service.IBADefinitionHelper;
import wt.iba.value.BooleanValue;
import wt.iba.value.FloatValue;
import wt.iba.value.IBAHolder;
import wt.iba.value.IBAHolderReference;
import wt.iba.value.IntegerValue;
import wt.iba.value.StringValue;
import wt.pds.StatementSpec;
import wt.query.ClassAttribute;
import wt.query.ColumnExpression;
import wt.query.ConstantExpression;
import wt.query.OrderBy;
import wt.query.QuerySpec;
import wt.query.SQLFunction;
import wt.query.SearchCondition;

public class IBAUtils {

	private IBAUtils() {

	}

	public static String getStringValue(IBAHolder holder, String attrName) throws Exception {
		if (holder == null) {
			return "";
		}

		QuerySpec query = new QuerySpec();

		int ii = query.appendClassList(holder.getClass(), false);
		int jj = query.appendClassList(StringValue.class, false);
		int kk = query.appendClassList(StringDefinition.class, false);

		long key = ((Persistable) holder).getPersistInfo().getObjectIdentifier().getId();
		SearchCondition sc = new SearchCondition(holder.getClass(), "thePersistInfo.theObjectIdentifier.id", "=", key);
		query.appendWhere(sc, new int[] { ii });
		query.appendAnd();

		ClassAttribute ca = new ClassAttribute(StringValue.class, "value");
		query.appendSelect(ca, new int[] { jj }, false);

		ca = new ClassAttribute(StringValue.class, "theIBAHolderReference.key.id");
		sc = new SearchCondition(ca, "=",
				new ClassAttribute(holder.getClass(), "thePersistInfo.theObjectIdentifier.id"));
		query.appendWhere(sc, new int[] { jj, ii });
		query.appendAnd();

		ca = new ClassAttribute(StringValue.class, "definitionReference.hierarchyID");
		sc = new SearchCondition(ca, "=", new ClassAttribute(StringDefinition.class, "hierarchyID"));
		query.appendWhere(sc, new int[] { jj, kk });
		query.appendAnd();

		sc = new SearchCondition(StringDefinition.class, "name", "=", attrName);
		query.appendWhere(sc, new int[] { kk });

		ca = new ClassAttribute(StringValue.class, StringValue.CREATE_TIMESTAMP);
		OrderBy by = new OrderBy(ca, true);
		query.appendOrderBy(by, new int[] { jj });

		QueryResult result = PersistenceHelper.manager.find((StatementSpec) query);
		String value = null;
		if (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			value = (String) obj[0];
		}
		return value != null ? value.trim() : "";
	}

	public static float getFloatValue(IBAHolder holder, String attrName) throws Exception {
		if (holder == null) {
			return 0f;
		}

		QuerySpec query = new QuerySpec();

		int ii = query.appendClassList(holder.getClass(), false);
		int jj = query.appendClassList(FloatValue.class, false);
		int kk = query.appendClassList(FloatDefinition.class, false);

		long key = ((Persistable) holder).getPersistInfo().getObjectIdentifier().getId();
		SearchCondition sc = new SearchCondition(holder.getClass(), "thePersistInfo.theObjectIdentifier.id", "=", key);
		query.appendWhere(sc, new int[] { ii });
		query.appendAnd();

		ClassAttribute ca = new ClassAttribute(FloatValue.class, "value");
		query.appendSelect(ca, new int[] { jj }, false);

		ca = new ClassAttribute(FloatValue.class, "theIBAHolderReference.key.id");
		sc = new SearchCondition(ca, "=",
				new ClassAttribute(holder.getClass(), "thePersistInfo.theObjectIdentifier.id"));
		query.appendWhere(sc, new int[] { jj, ii });
		query.appendAnd();

		ca = new ClassAttribute(FloatValue.class, "definitionReference.hierarchyID");
		sc = new SearchCondition(ca, "=", new ClassAttribute(FloatDefinition.class, "hierarchyID"));
		query.appendWhere(sc, new int[] { jj, kk });
		query.appendAnd();

		sc = new SearchCondition(FloatDefinition.class, "name", "=", attrName);
		query.appendWhere(sc, new int[] { kk });

		ca = new ClassAttribute(FloatValue.class, FloatValue.CREATE_TIMESTAMP);
		OrderBy by = new OrderBy(ca, true);
		query.appendOrderBy(by, new int[] { jj });

		QueryResult result = PersistenceHelper.manager.find((StatementSpec) query);
		float value = 0f;
		if (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			value = ((BigDecimal) obj[0]).floatValue();
		}
		return value;
	}

	public static int getIntegerValue(IBAHolder holder, String attrName) throws Exception {
		if (holder == null) {
			return 0;
		}

		QuerySpec query = new QuerySpec();

		int ii = query.appendClassList(holder.getClass(), false);
		int jj = query.appendClassList(IntegerValue.class, false);
		int kk = query.appendClassList(IntegerDefinition.class, false);

		long key = ((Persistable) holder).getPersistInfo().getObjectIdentifier().getId();
		SearchCondition sc = new SearchCondition(holder.getClass(), "thePersistInfo.theObjectIdentifier.id", "=", key);
		query.appendWhere(sc, new int[] { ii });
		query.appendAnd();

		ClassAttribute ca = new ClassAttribute(IntegerValue.class, "value");
		query.appendSelect(ca, new int[] { jj }, false);

		ca = new ClassAttribute(IntegerValue.class, "theIBAHolderReference.key.id");
		sc = new SearchCondition(ca, "=",
				new ClassAttribute(holder.getClass(), "thePersistInfo.theObjectIdentifier.id"));
		query.appendWhere(sc, new int[] { jj, ii });
		query.appendAnd();

		ca = new ClassAttribute(IntegerValue.class, "definitionReference.hierarchyID");
		sc = new SearchCondition(ca, "=", new ClassAttribute(IntegerDefinition.class, "hierarchyID"));
		query.appendWhere(sc, new int[] { jj, kk });
		query.appendAnd();

		sc = new SearchCondition(IntegerDefinition.class, "name", "=", attrName);
		query.appendWhere(sc, new int[] { kk });

		ca = new ClassAttribute(IntegerValue.class, IntegerValue.CREATE_TIMESTAMP);
		OrderBy by = new OrderBy(ca, true);
		query.appendOrderBy(by, new int[] { jj });

		QueryResult result = PersistenceHelper.manager.find((StatementSpec) query);
		int value = 0;
		if (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			value = ((BigDecimal) obj[0]).intValue();
		}
		return value;
	}

	public static boolean getBooleanValue(IBAHolder holder, String attrName) throws Exception {
		if (holder == null) {
			return false;
		}

		QuerySpec query = new QuerySpec();

		int ii = query.appendClassList(holder.getClass(), false);
		int jj = query.appendClassList(BooleanValue.class, false);
		int kk = query.appendClassList(BooleanDefinition.class, false);

		long key = ((Persistable) holder).getPersistInfo().getObjectIdentifier().getId();
		SearchCondition sc = new SearchCondition(holder.getClass(), "thePersistInfo.theObjectIdentifier.id", "=", key);
		query.appendWhere(sc, new int[] { ii });
		query.appendAnd();

		ClassAttribute ca = new ClassAttribute(BooleanValue.class, "value");
		query.appendSelect(ca, new int[] { jj }, false);

		ca = new ClassAttribute(BooleanValue.class, "theIBAHolderReference.key.id");
		sc = new SearchCondition(ca, "=",
				new ClassAttribute(holder.getClass(), "thePersistInfo.theObjectIdentifier.id"));
		query.appendWhere(sc, new int[] { jj, ii });
		query.appendAnd();

		ca = new ClassAttribute(BooleanValue.class, "definitionReference.hierarchyID");
		sc = new SearchCondition(ca, "=", new ClassAttribute(BooleanDefinition.class, "hierarchyID"));
		query.appendWhere(sc, new int[] { jj, kk });
		query.appendAnd();

		sc = new SearchCondition(BooleanDefinition.class, "name", "=", attrName);
		query.appendWhere(sc, new int[] { kk });

		ca = new ClassAttribute(BooleanValue.class, BooleanValue.CREATE_TIMESTAMP);
		OrderBy by = new OrderBy(ca, true);
		query.appendOrderBy(by, new int[] { jj });
		QueryResult result = PersistenceHelper.manager.find((StatementSpec) query);
		if (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			BigDecimal bd = (BigDecimal) obj[0];
			return bd.intValue() == 1 ? true : false;
		}
		return false;
	}

	public static void createIBA(IBAHolder holder, String type, String attrName, String value) throws Exception {
		if (value == null) {
			return;
		}

		if ("s".equalsIgnoreCase(type) || "string".equalsIgnoreCase(type)) {
			StringValue sv = new StringValue();
			StringDefinition sd = getStringDefinition(attrName);
			if (sd == null) {
				return;
			}

			// 삭제 로직 추가
			deleteIBA(holder, type, attrName);

			sv.setValue(value);
			sv.setDefinitionReference((StringDefinitionReference) sd.getAttributeDefinitionReference());
			sv.setIBAHolderReference(IBAHolderReference.newIBAHolderReference(holder));
			sv = (StringValue) PersistenceHelper.manager.save(sv);
		} else if ("b".equalsIgnoreCase(type) || "boolean".equalsIgnoreCase(type)) {
			BooleanValue bv = new BooleanValue();
			BooleanDefinition bd = getBooleanDefinition(attrName);
			if (bd == null) {
				return;
			}

			// 삭제 로직 추가
			deleteIBA(holder, type, attrName);

			bv.setValue(Boolean.parseBoolean(value));
			bv.setDefinitionReference((BooleanDefinitionReference) bd.getAttributeDefinitionReference());
			bv.setIBAHolderReference(IBAHolderReference.newIBAHolderReference(holder));
			bv = (BooleanValue) PersistenceHelper.manager.save(bv);
		} else if ("i".equalsIgnoreCase(type) || "int".equalsIgnoreCase(type)) {
			IntegerValue iv = new IntegerValue();
			IntegerDefinition id = getIntegerDefinition(attrName);
			if (id == null) {
				return;
			}

			// 삭제 로직 추가
			deleteIBA(holder, type, attrName);
			iv.setValue(Integer.parseInt(value));
			iv.setDefinitionReference((IntegerDefinitionReference) id.getAttributeDefinitionReference());
			iv.setIBAHolderReference(IBAHolderReference.newIBAHolderReference(holder));
			iv = (IntegerValue) PersistenceHelper.manager.save(iv);
		} else if ("f".equalsIgnoreCase(type) || "float".equalsIgnoreCase(type)) {
			FloatValue fv = new FloatValue();
			FloatDefinition fd = getFloatDefinition(attrName);
			if (fd == null) {
				return;
			}

			// 삭제 로직 추가
			deleteIBA(holder, type, attrName);
			fv.setValue(Integer.parseInt(value));
			fv.setDefinitionReference((FloatDefinitionReference) fd.getAttributeDefinitionReference());
			fv.setIBAHolderReference(IBAHolderReference.newIBAHolderReference(holder));
			fv = (FloatValue) PersistenceHelper.manager.save(fv);
		}
	}

	private static StringDefinition getStringDefinition(String attrName) throws Exception {
		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(StringDefinition.class, true);
		SearchCondition sc = new SearchCondition(StringDefinition.class, StringDefinition.NAME, "=", attrName);
		query.appendWhere(sc, new int[] { idx });
		QueryResult result = PersistenceHelper.manager.find((StatementSpec) query);
		StringDefinition sd = null;
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			sd = (StringDefinition) obj[0];
		}
		return sd;
	}

	private static BooleanDefinition getBooleanDefinition(String attrName) throws Exception {
		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(BooleanDefinition.class, true);
		SearchCondition sc = new SearchCondition(BooleanDefinition.class, BooleanDefinition.NAME, "=", attrName);
		query.appendWhere(sc, new int[] { idx });
		QueryResult result = PersistenceHelper.manager.find((StatementSpec) query);
		BooleanDefinition bd = null;
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			bd = (BooleanDefinition) obj[0];
		}
		return bd;
	}

	private static IntegerDefinition getIntegerDefinition(String attrName) throws Exception {
		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(IntegerDefinition.class, true);
		SearchCondition sc = new SearchCondition(IntegerDefinition.class, IntegerDefinition.NAME, "=", attrName);
		query.appendWhere(sc, new int[] { idx });
		QueryResult result = PersistenceHelper.manager.find((StatementSpec) query);
		IntegerDefinition id = null;
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			id = (IntegerDefinition) obj[0];
		}
		return id;
	}

	private static FloatDefinition getFloatDefinition(String attrName) throws Exception {
		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(FloatDefinition.class, true);
		SearchCondition sc = new SearchCondition(FloatDefinition.class, FloatDefinition.NAME, "=", attrName);
		query.appendWhere(sc, new int[] { idx });
		QueryResult result = PersistenceHelper.manager.find((StatementSpec) query);
		FloatDefinition fd = null;
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			fd = (FloatDefinition) obj[0];
		}
		return fd;
	}

	private static ArrayList<StringValue> getStringValues(IBAHolder holder, StringDefinition sd) throws Exception {
		ArrayList<StringValue> list = new ArrayList<StringValue>();
		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(StringValue.class, true);

		Persistable per = (Persistable) holder;

		SearchCondition sc = new SearchCondition(StringValue.class, "theIBAHolderReference.key.id", "=",
				per.getPersistInfo().getObjectIdentifier().getId());
		query.appendWhere(sc, new int[] { idx });
		query.appendAnd();

		sc = new SearchCondition(StringValue.class, "definitionReference.key.id", "=",
				sd.getPersistInfo().getObjectIdentifier().getId());
		query.appendWhere(sc, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find((StatementSpec) query);
		StringValue sv = null;
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			sv = (StringValue) obj[0];
			list.add(sv);
		}
		return list;
	}

	private static ArrayList<FloatValue> getFloatValues(IBAHolder holder, FloatDefinition fd) throws Exception {
		ArrayList<FloatValue> list = new ArrayList<FloatValue>();
		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(FloatValue.class, true);

		Persistable per = (Persistable) holder;

		SearchCondition sc = new SearchCondition(FloatValue.class, "theIBAHolderReference.key.id", "=",
				per.getPersistInfo().getObjectIdentifier().getId());
		query.appendWhere(sc, new int[] { idx });
		query.appendAnd();

		sc = new SearchCondition(FloatValue.class, "definitionReference.key.id", "=",
				fd.getPersistInfo().getObjectIdentifier().getId());
		query.appendWhere(sc, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find((StatementSpec) query);
		FloatValue fv = null;
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			fv = (FloatValue) obj[0];
			list.add(fv);
		}
		return list;
	}

	private static ArrayList<IntegerValue> getIntegerValues(IBAHolder holder, IntegerDefinition id) throws Exception {
		ArrayList<IntegerValue> list = new ArrayList<IntegerValue>();
		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(IntegerValue.class, true);

		Persistable per = (Persistable) holder;

		SearchCondition sc = new SearchCondition(IntegerValue.class, "theIBAHolderReference.key.id", "=",
				per.getPersistInfo().getObjectIdentifier().getId());
		query.appendWhere(sc, new int[] { idx });
		query.appendAnd();

		sc = new SearchCondition(IntegerValue.class, "definitionReference.key.id", "=",
				id.getPersistInfo().getObjectIdentifier().getId());
		query.appendWhere(sc, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find((StatementSpec) query);
		IntegerValue iv = null;
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			iv = (IntegerValue) obj[0];
			list.add(iv);
		}
		return list;
	}

	private static ArrayList<BooleanValue> getBooleanValues(IBAHolder holder, BooleanDefinition bd) throws Exception {
		ArrayList<BooleanValue> list = new ArrayList<BooleanValue>();
		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(BooleanValue.class, true);

		Persistable per = (Persistable) holder;

		SearchCondition sc = new SearchCondition(BooleanValue.class, "theIBAHolderReference.key.id", "=",
				per.getPersistInfo().getObjectIdentifier().getId());
		query.appendWhere(sc, new int[] { idx });
		query.appendAnd();

		sc = new SearchCondition(BooleanValue.class, "definitionReference.key.id", "=",
				bd.getPersistInfo().getObjectIdentifier().getId());
		query.appendWhere(sc, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find((StatementSpec) query);
		BooleanValue bv = null;
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			bv = (BooleanValue) obj[0];
			list.add(bv);
		}
		return list;
	}

	public static void deleteIBA(IBAHolder holder, String type, String attrName) throws Exception {

		if ("s".equals(type) || "string".equals(type)) {
			StringDefinition sd = getStringDefinition(attrName);
			ArrayList<StringValue> list = getStringValues(holder, sd);
			for (StringValue sv : list) {
				PersistenceHelper.manager.delete(sv);
			}
		} else if ("f".equals(type) || "float".equals(type)) {
			FloatDefinition fd = getFloatDefinition(attrName);
			ArrayList<FloatValue> list = getFloatValues(holder, fd);
			for (FloatValue fv : list) {
				PersistenceHelper.manager.delete(fv);
			}
		} else if ("b".equals(type) || "boolean".equals(type)) {
			BooleanDefinition bd = getBooleanDefinition(attrName);
			ArrayList<BooleanValue> list = getBooleanValues(holder, bd);
			for (BooleanValue bv : list) {
				PersistenceHelper.manager.delete(bv);
			}
		} else if ("i".equals(type) || "integer".equals(type)) {
			IntegerDefinition id = getIntegerDefinition(attrName);
			ArrayList<IntegerValue> list = getIntegerValues(holder, id);
			for (IntegerValue iv : list) {
				PersistenceHelper.manager.delete(iv);
			}
		}
	}

	public static void equals(QuerySpec _query, Class clazz, int _idx, String name, String value) throws Exception {
		AttributeDefDefaultView aview = IBADefinitionHelper.service.getAttributeDefDefaultViewByPath(name);

		if (aview != null) {
			if (_query.getConditionCount() > 0) {
				_query.appendAnd();
			}

			int idx = _query.appendClassList(StringValue.class, false);
			SearchCondition sc = new SearchCondition(
					new ClassAttribute(StringValue.class, "theIBAHolderReference.key.id"), "=",
					new ClassAttribute(clazz, "thePersistInfo.theObjectIdentifier.id"));
			sc.setFromIndicies(new int[] { idx, _idx }, 0);
			sc.setOuterJoin(0);
			_query.appendWhere(sc, new int[] { idx, _idx });
			_query.appendAnd();
			sc = new SearchCondition(StringValue.class, "definitionReference.key.id", "=", aview.getObjectID().getId());
			_query.appendWhere(sc, new int[] { idx });
			_query.appendAnd();

			sc = new SearchCondition(StringValue.class, StringValue.VALUE, "=", value);
			_query.appendWhere(sc, new int[] { idx });
		}
	}

	public static void like(QuerySpec _query, Class clazz, int _idx, String name, String value) throws Exception {
		AttributeDefDefaultView aview = IBADefinitionHelper.service.getAttributeDefDefaultViewByPath(name);

		if (aview != null) {
			if (_query.getConditionCount() > 0) {
				_query.appendAnd();
			}

			int idx = _query.appendClassList(StringValue.class, false);
			SearchCondition sc = new SearchCondition(
					new ClassAttribute(StringValue.class, "theIBAHolderReference.key.id"), "=",
					new ClassAttribute(clazz, "thePersistInfo.theObjectIdentifier.id"));
			sc.setFromIndicies(new int[] { idx, _idx }, 0);
			sc.setOuterJoin(0);
			_query.appendWhere(sc, new int[] { idx, _idx });
			_query.appendAnd();
			sc = new SearchCondition(StringValue.class, "definitionReference.key.id", "=", aview.getObjectID().getId());
			_query.appendWhere(sc, new int[] { idx });
			_query.appendAnd();

			sc = new SearchCondition(StringValue.class, StringValue.VALUE, "LIKE", "%" + value + "%");
			_query.appendWhere(sc, new int[] { idx });
		}
	}

	public static Object getAttrValues(IBAHolder holder, String type, String attrName) throws Exception {
		Object value = "";
		if ("s".equalsIgnoreCase(type) || "string".equalsIgnoreCase(type)) {
			value = getStringValue(holder, attrName);
		} else if ("b".equalsIgnoreCase(type) || "boolean".equalsIgnoreCase(type)) {
			value = getBooleanValue(holder, attrName);
		} else if ("f".equalsIgnoreCase(type) || "float".equalsIgnoreCase(type)) {
			value = getFloatValue(holder, attrName);
		} else if ("i".equalsIgnoreCase(type) || "int".equalsIgnoreCase(type)) {
			value = getIntegerValue(holder, attrName);
		}
		return value;
	}

}
