package platform.echange.ecr.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import platform.doc.entity.DocumentColumns;
import platform.doc.entity.WTDocumentWTPartLink;
import platform.echange.eco.entity.ECO;
import platform.echange.eco.entity.ECOColumns;
import platform.echange.ecr.entity.ECR;
import platform.echange.ecr.entity.ECRColumns;
import platform.echange.ecr.entity.ECRWTDocumentLink;
import platform.echange.ecr.entity.ECRWTPartLink;
import platform.part.entity.PartColumns;
import platform.util.DateUtils;
import platform.util.PageUtils;
import platform.util.StringUtils;
import wt.doc.WTDocument;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.org.WTUser;
import wt.part.WTPart;
import wt.query.ClassAttribute;
import wt.query.ColumnExpression;
import wt.query.ConstantExpression;
import wt.query.OrderBy;
import wt.query.QuerySpec;
import wt.query.SQLFunction;
import wt.query.SearchCondition;
import wt.services.ServiceFactory;
import wt.util.WTAttributeNameIfc;

public class ECRHelper {

	public static final ECRService service = ServiceFactory.getService(ECRService.class);
	public static final ECRHelper manager = new ECRHelper();

	public Map<String, Object> list(Map<String, Object> params) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<ECRColumns> list = new ArrayList<>();
		String number = (String) params.get("number");
		String name = (String) params.get("name");
		String reqType = (String) params.get("reqType");
		String state = (String) params.get("state");
		String creatorOid = (String) params.get("creator");
		String partOid = (String) params.get("partOid");
		String createStartDate = (String) params.get("createStartDate");
		String createEndDate = (String) params.get("createEndDate");
		ReferenceFactory rf = new ReferenceFactory();

		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(ECR.class, true);

		SearchCondition sc = null;
		ClassAttribute ca = null;

		if (StringUtils.isNotNull(name)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			ca = new ClassAttribute(ECR.class, ECR.NAME);
			ColumnExpression ce = ConstantExpression.newExpression("%" + name.toUpperCase() + "%");
			SQLFunction function = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca);
			sc = new SearchCondition(function, SearchCondition.LIKE, ce);
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(number)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			ca = new ClassAttribute(ECR.class, ECR.NUMBER);
			ColumnExpression ce = ConstantExpression.newExpression("%" + number.toUpperCase() + "%");
			SQLFunction function = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca);
			sc = new SearchCondition(function, SearchCondition.LIKE, ce);
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(state)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(ECR.class, ECR.STATE, "=", state);
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(reqType)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(ECR.class, ECR.REQ_TYPE, "=", reqType);
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(partOid)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			WTPart part = (WTPart) rf.getReference(partOid).getObject();
			int idx_p = query.appendClassList(WTPart.class, false);
			int idx_link = query.appendClassList(ECRWTPartLink.class, false);

			sc = new SearchCondition(ECR.class, "thePersistInfo.theObjectIdentifier.id", ECRWTPartLink.class,
					"roleAObjectRef.key.id");
			query.appendWhere(sc, new int[] { idx, idx_p });
			query.appendAnd();

			sc = new SearchCondition(WTPart.class, "thePersistInfo.theObjectIdentifier.id", ECRWTPartLink.class,
					"roleBObjectRef.key.id");
			query.appendWhere(sc, new int[] { idx_link, idx_p });
			query.appendAnd();

			sc = new SearchCondition(ECRWTPartLink.class, "roleBObjectRef.key.id", "=",
					part.getPersistInfo().getObjectIdentifier().getId());
			query.appendWhere(sc, new int[] { idx_p });
		}

		// 작성일자
		if (StringUtils.isNotNull(createStartDate)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(ECR.class, WTAttributeNameIfc.CREATE_STAMP_NAME, ">=",
					DateUtils.startTimestamp(createStartDate));
			query.appendWhere(sc, new int[] { idx });
		}
		if (StringUtils.isNotNull(createEndDate)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(ECR.class, WTAttributeNameIfc.CREATE_STAMP_NAME, "<=",
					DateUtils.endTimestamp(createEndDate));
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(creatorOid)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}

			WTUser creator = (WTUser) rf.getReference(creatorOid).getObject();
			sc = new SearchCondition(ECR.class, "ownership.owner.key.id", "=",
					creator.getPersistInfo().getObjectIdentifier().getId());
			query.appendWhere(sc, new int[] { idx });
		}

		ca = new ClassAttribute(ECR.class, WTAttributeNameIfc.CREATE_STAMP_NAME);
		OrderBy by = new OrderBy(ca, true);
		query.appendOrderBy(by, new int[] { idx });

		PageUtils pager = new PageUtils(params, query);
		QueryResult result = pager.find();
		int total = pager.getTotal();
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			ECRColumns columns = new ECRColumns((ECR) obj[0]);
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

	public String getNextNumber() throws Exception {

		Calendar ca = Calendar.getInstance();
		int day = ca.get(Calendar.DATE);
		int month = ca.get(Calendar.MONTH) + 1;
		int year = ca.get(Calendar.YEAR);
		DecimalFormat df = new DecimalFormat("00");
		String number = df.format(year) + df.format(month) + df.format(day);

		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(ECR.class, true);

		SearchCondition sc = new SearchCondition(ECR.class, ECR.NUMBER, "LIKE", "ECR-" + number.toUpperCase() + "%");
		query.appendWhere(sc, new int[] { idx });

		ClassAttribute attr = new ClassAttribute(ECR.class, ECR.NUMBER);
		OrderBy orderBy = new OrderBy(attr, true);
		query.appendOrderBy(orderBy, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find(query);
		if (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			ECR ecr = (ECR) obj[0];

			String s = ecr.getNumber().substring(12);

			int ss = Integer.parseInt(s) + 1;
			DecimalFormat d = new DecimalFormat("00");
			number += d.format(ss);
		} else {
			number += "01";
		}
		return "ECR-" + number;
	}

	public ArrayList<PartColumns> getParts(ECR ecr) throws Exception {
		ArrayList<PartColumns> list = new ArrayList<PartColumns>();
		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(ECRWTPartLink.class, true);
		SearchCondition sc = new SearchCondition(ECRWTPartLink.class, "roleAObjectRef.key.id", "=",
				ecr.getPersistInfo().getObjectIdentifier().getId());
		query.appendWhere(sc, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find(query);
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			ECRWTPartLink link = (ECRWTPartLink) obj[0];
			list.add(new PartColumns(link.getPart()));
		}
		return list;
	}

	public ArrayList<DocumentColumns> getDocs(ECR ecr) throws Exception {
		ArrayList<DocumentColumns> list = new ArrayList<DocumentColumns>();

		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(ECRWTDocumentLink.class, true);

		SearchCondition sc = new SearchCondition(ECRWTDocumentLink.class, "roleAObjectRef.key.id", "=",
				ecr.getPersistInfo().getObjectIdentifier().getId());
		query.appendWhere(sc, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find(query);
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			ECRWTDocumentLink link = (ECRWTDocumentLink) obj[0];
			list.add(new DocumentColumns(link.getDoc()));
		}
		return list;
	}
}
