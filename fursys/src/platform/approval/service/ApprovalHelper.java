package platform.approval.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import platform.approval.entity.ApprovalColumns;
import platform.approval.entity.ApprovalHistoryLink;
import platform.approval.entity.ApprovalLine;
import platform.approval.entity.ApprovalMaster;
import platform.approval.entity.HistoryMasterLink;
import platform.approval.entity.LatestApprovalLine;
import platform.approval.entity.PersistableLineMasterLink;
import platform.approval.entity.ReceiveObjectLink;
import platform.code.service.BaseCodeHelper;
import platform.dist.entity.Dist;
import platform.echange.ecn.entity.ECN;
import platform.echange.eco.entity.ECO;
import platform.echange.ecr.entity.ECR;
import platform.util.CommonUtils;
import platform.util.DateUtils;
import platform.util.PageUtils;
import platform.util.StringUtils;
import wt.doc.WTDocument;
import wt.enterprise.RevisionControlled;
import wt.epm.EPMDocument;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.WTObject;
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
import wt.session.SessionHelper;
import wt.vc.Mastered;

public class ApprovalHelper {

	public static final ApprovalService service = ServiceFactory.getService(ApprovalService.class);
	public static final ApprovalHelper manager = new ApprovalHelper();

	public static final String SUBMIT_LINE = "기안";
	public static final String APP_LINE = "결재";
	public static final String VIA_LINE = "경유";
	public static final String EXAM_LINE = "검토";
	public static final String REF_LINE = "참조";
	public static final String RECEIVE_LINE = "수신";

	public static final String LINE_SUBMIT_COMPLETE = "제출됨";
	public static final String LINE_APPROVING = "승인중";
	public static final String LINE_APPROVAL_COMPLETE = "결재완료";
	public static final String LINE_VIA_COMPLETE = "경유확인완료";
	public static final String LINE_RETURN_COMPLETE = "반려됨";
	public static final String LINE_STAND = "대기중";
	public static final String LINE_VIA_STAND = "경유확인중";
	public static final String LINE_RECEIVE_STAND = "접수중";
	public static final String LINE_RECEIVE_COMPLETE = "접수완료";
	public static final String LINE_EXAM = "검토중";
	public static final String LINE_EXAM_COMPLETE = "검토완료";

	public static final String SUBMIT_ROLE = "기안자";
	public static final String APP_ROLE = "결재자";
	public static final String EXAM_ROLE = "검토자";
	public static final String VIA_ROLE = "경유자";
	public static final String RECEIVE_ROLE = "수신자";

	public String getName(Persistable per) throws Exception {
		StringBuffer sb = new StringBuffer();

		if (per instanceof WTDocument) {
			WTDocument doc = (WTDocument) per;
			sb.append("[문서결재] " + doc.getName());
		} else if (per instanceof ECR) {
			ECR ecr = (ECR) per;
			sb.append("[ECR결재] " + ecr.getName());
		} else if (per instanceof ECO) {
			ECO eco = (ECO) per;
			String ecoType = BaseCodeHelper.manager.getBaseCodeByCodeTypeAndCode("ECO_TYPE", eco.getEcoType())
					.getName();
			sb.append("[" + ecoType + " ECO결재] " + eco.getName());
		} else if (per instanceof ECN) {
			ECN ecn = (ECN) per;
			sb.append("[ECN결재] " + ecn.getName());
		} else if (per instanceof Dist) {
			Dist dist = (Dist) per;
			sb.append("[배포결재]" + dist.getName());
		}

		return sb.toString();
	}

	public ApprovalMaster getMaster(Persistable per) throws Exception {
		ApprovalMaster master = null;
		QueryResult result = PersistenceHelper.manager.navigate(per, "lineMaster", PersistableLineMasterLink.class);
		if (result.hasMoreElements()) {
			master = (ApprovalMaster) result.nextElement();
		}
		return master;
	}

	public ArrayList<ApprovalLine> getAppLines(ApprovalMaster master) throws Exception {
		ArrayList<ApprovalLine> appLines = new ArrayList<ApprovalLine>();

		if (master == null) {
			return appLines;
		}

		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(ApprovalLine.class, true);
		SearchCondition sc = null;

		query.appendOpenParen();
		sc = new SearchCondition(ApprovalLine.class, ApprovalLine.LINE_TYPE, "=", ApprovalHelper.APP_LINE);
		query.appendWhere(sc, new int[] { idx });
		query.appendOr();

		sc = new SearchCondition(ApprovalLine.class, ApprovalLine.LINE_TYPE, "=", ApprovalHelper.VIA_LINE);
		query.appendWhere(sc, new int[] { idx });
		query.appendCloseParen();

		query.appendAnd();
		long ids = master.getPersistInfo().getObjectIdentifier().getId();
		sc = new SearchCondition(ApprovalLine.class, "masterReference.key.id", "=", ids);
		query.appendWhere(sc, new int[] { idx });

		ClassAttribute ca = new ClassAttribute(ApprovalLine.class, ApprovalLine.SORT);
		OrderBy orderBy = new OrderBy(ca, false);
		query.appendOrderBy(orderBy, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find(query);
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			ApprovalLine line = (ApprovalLine) obj[0];
			appLines.add(line);
		}

		return appLines;
	}

	public ArrayList<ApprovalLine> getExamAndViaLines(ApprovalMaster master) throws Exception {
		ArrayList<ApprovalLine> examLines = new ArrayList<ApprovalLine>();

		if (master == null) {
			return examLines;
		}

		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(ApprovalLine.class, true);
		SearchCondition sc = null;

		query.appendOpenParen();
		sc = new SearchCondition(ApprovalLine.class, ApprovalLine.LINE_TYPE, "=", ApprovalHelper.EXAM_LINE);
		query.appendWhere(sc, new int[] { idx });
		query.appendOr();

		sc = new SearchCondition(ApprovalLine.class, ApprovalLine.LINE_TYPE, "=", ApprovalHelper.VIA_LINE);
		query.appendWhere(sc, new int[] { idx });
		query.appendCloseParen();

		query.appendAnd();
		long ids = master.getPersistInfo().getObjectIdentifier().getId();
		sc = new SearchCondition(ApprovalLine.class, "masterReference.key.id", "=", ids);
		query.appendWhere(sc, new int[] { idx });

		ClassAttribute ca = new ClassAttribute(ApprovalLine.class, ApprovalLine.SORT);
		OrderBy orderBy = new OrderBy(ca, false);
		query.appendOrderBy(orderBy, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find(query);
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			ApprovalLine line = (ApprovalLine) obj[0];
			examLines.add(line);
		}

		return examLines;
	}

	public ArrayList<ApprovalLine> getViaLines(ApprovalMaster master) throws Exception {
		ArrayList<ApprovalLine> viaLines = new ArrayList<ApprovalLine>();

		if (master == null) {
			return viaLines;
		}

		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(ApprovalLine.class, true);
		SearchCondition sc = null;

		sc = new SearchCondition(ApprovalLine.class, ApprovalLine.LINE_TYPE, "=", ApprovalHelper.VIA_LINE);
		query.appendWhere(sc, new int[] { idx });

		query.appendAnd();
		long ids = master.getPersistInfo().getObjectIdentifier().getId();
		sc = new SearchCondition(ApprovalLine.class, "masterReference.key.id", "=", ids);
		query.appendWhere(sc, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find(query);
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			ApprovalLine line = (ApprovalLine) obj[0];
			viaLines.add(line);
		}

		return viaLines;
	}

	public ArrayList<ApprovalLine> getRefLines(ApprovalMaster master) throws Exception {
		ArrayList<ApprovalLine> appLines = new ArrayList<ApprovalLine>();
		if (master == null) {
			return appLines;
		}
		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(ApprovalLine.class, true);
		SearchCondition sc = new SearchCondition(ApprovalLine.class, ApprovalLine.LINE_TYPE, "=",
				ApprovalHelper.REF_LINE);
		query.appendWhere(sc, new int[] { idx });
		query.appendAnd();

		long ids = master.getPersistInfo().getObjectIdentifier().getId();
		sc = new SearchCondition(ApprovalLine.class, "masterReference.key.id", "=", ids);
		query.appendWhere(sc, new int[] { idx });

		ClassAttribute ca = new ClassAttribute(ApprovalLine.class, ApprovalLine.SORT);
		OrderBy orderBy = new OrderBy(ca, false);
		query.appendOrderBy(orderBy, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find(query);
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			ApprovalLine line = (ApprovalLine) obj[0];
			appLines.add(line);
		}

		return appLines;
	}

	public Map<String, Object> app(Map<String, Object> params) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<ApprovalColumns> list = new ArrayList<ApprovalColumns>();
		String creatorOid = (String) params.get("creatorOid");
		String name = (String) params.get("name");
		String startCreatedDate = (String) params.get("startCreatedDate");
		String endCreatedDate = (String) params.get("endCreatedDate");
		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(ApprovalLine.class, true);
		int idx_m = query.appendClassList(ApprovalMaster.class, true);

		WTUser sessionUser = (WTUser) SessionHelper.manager.getPrincipal();

		SearchCondition sc = null;
		ClassAttribute ca = null;

		sc = new SearchCondition(ApprovalLine.class, "masterReference.key.id", ApprovalMaster.class,
				"thePersistInfo.theObjectIdentifier.id");
		query.appendWhere(sc, new int[] { idx, idx_m });

		if (StringUtils.isNotNull(name)) {
			if (query.getConditionCount() > 0)
				query.appendAnd();

			ca = new ClassAttribute(ApprovalMaster.class, ApprovalMaster.NAME);
			ColumnExpression ce = ConstantExpression.newExpression("%" + name.toUpperCase() + "%");
			SQLFunction function = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca);
			sc = new SearchCondition(function, SearchCondition.LIKE, ce);
			query.appendWhere(sc, new int[] { idx_m });
		}

		if (!CommonUtils.isAdmin()) {
			if (query.getConditionCount() > 0)
				query.appendAnd();
			long ids = sessionUser.getPersistInfo().getObjectIdentifier().getId();
			sc = new SearchCondition(ApprovalLine.class, "ownership.owner.key.id", "=", ids);
			query.appendWhere(sc, new int[] { idx });
		}

		if (query.getConditionCount() > 0) {
			query.appendAnd();
		}

		// 경유?
		query.appendOpenParen();
		sc = new SearchCondition(ApprovalLine.class, ApprovalLine.STATE, "=", LINE_VIA_STAND);
		query.appendWhere(sc, new int[] { idx });
		query.appendOr();

		sc = new SearchCondition(ApprovalLine.class, ApprovalLine.STATE, "=", LINE_APPROVING);
		query.appendWhere(sc, new int[] { idx });
		query.appendOr();

		sc = new SearchCondition(ApprovalLine.class, ApprovalLine.STATE, "=", LINE_EXAM);
		query.appendWhere(sc, new int[] { idx });
		query.appendCloseParen();

		query.appendAnd();

		query.appendOpenParen();
		sc = new SearchCondition(ApprovalLine.class, ApprovalLine.LINE_TYPE, "=", APP_LINE);
		query.appendWhere(sc, new int[] { idx });
		query.appendOr();

		sc = new SearchCondition(ApprovalLine.class, ApprovalLine.LINE_TYPE, "=", VIA_LINE);
		query.appendWhere(sc, new int[] { idx });
		query.appendOr();

		sc = new SearchCondition(ApprovalLine.class, ApprovalLine.LINE_TYPE, "=", EXAM_LINE);
		query.appendWhere(sc, new int[] { idx });
		query.appendCloseParen();

		if (StringUtils.isNotNull(creatorOid)) {
			if (query.getConditionCount() > 0)
				query.appendAnd();
			WTUser user = (WTUser) CommonUtils.persistable(creatorOid);
			long user_ids = user.getPersistInfo().getObjectIdentifier().getId();
			sc = new SearchCondition(ApprovalMaster.class, "ownership.owner.key.id", SearchCondition.EQUAL, user_ids);
			query.appendWhere(sc, new int[] { idx_m });

		}

		if (StringUtils.isNotNull(startCreatedDate)) {
			if (query.getConditionCount() > 0)
				query.appendAnd();
			Timestamp start = DateUtils.startTimestamp(startCreatedDate);
			sc = new SearchCondition(ApprovalLine.class, ApprovalLine.START_TIME, SearchCondition.GREATER_THAN_OR_EQUAL,
					start);
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(endCreatedDate)) {
			if (query.getConditionCount() > 0)
				query.appendAnd();
			Timestamp end = DateUtils.endTimestamp(endCreatedDate);
			sc = new SearchCondition(ApprovalLine.class, ApprovalLine.START_TIME, SearchCondition.LESS_THAN_OR_EQUAL,
					end);
			query.appendWhere(sc, new int[] { idx });
		}

		ca = new ClassAttribute(ApprovalLine.class, ApprovalLine.START_TIME);
		OrderBy orderBy = new OrderBy(ca, true);
		query.appendOrderBy(orderBy, new int[] { idx });

		PageUtils pager = new PageUtils(params, query);
		QueryResult result = pager.find();
		int total = pager.getTotal();
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			ApprovalColumns columns = new ApprovalColumns((ApprovalLine) obj[0]);
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

	public Map<String, Object> receive(Map<String, Object> params) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<ApprovalColumns> list = new ArrayList<ApprovalColumns>();
		String creatorOid = (String) params.get("creatorOid");
		String name = (String) params.get("name");
		String startCreatedDate = (String) params.get("startCreatedDate");
		String endCreatedDate = (String) params.get("endCreatedDate");
		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(ApprovalLine.class, true);
		int idx_m = query.appendClassList(ApprovalMaster.class, true);

		WTUser sessionUser = (WTUser) SessionHelper.manager.getPrincipal();

		SearchCondition sc = null;
		ClassAttribute ca = null;

		sc = new SearchCondition(ApprovalLine.class, "masterReference.key.id", ApprovalMaster.class,
				"thePersistInfo.theObjectIdentifier.id");
		query.appendWhere(sc, new int[] { idx, idx_m });

		if (StringUtils.isNotNull(name)) {
			if (query.getConditionCount() > 0)
				query.appendAnd();

			ca = new ClassAttribute(ApprovalMaster.class, ApprovalMaster.NAME);
			ColumnExpression ce = ConstantExpression.newExpression("%" + name.toUpperCase() + "%");
			SQLFunction function = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca);
			sc = new SearchCondition(function, SearchCondition.LIKE, ce);
			query.appendWhere(sc, new int[] { idx_m });
		}

		if (!CommonUtils.isAdmin()) {
			if (query.getConditionCount() > 0)
				query.appendAnd();
			long ids = sessionUser.getPersistInfo().getObjectIdentifier().getId();
			sc = new SearchCondition(ApprovalLine.class, "ownership.owner.key.id", "=", ids);
			query.appendWhere(sc, new int[] { idx });
		}

		if (query.getConditionCount() > 0) {
			query.appendAnd();
		}

		// 경유?
		sc = new SearchCondition(ApprovalLine.class, ApprovalLine.STATE, "=", LINE_RECEIVE_STAND);
		query.appendWhere(sc, new int[] { idx });
		query.appendAnd();

		sc = new SearchCondition(ApprovalLine.class, ApprovalLine.LINE_TYPE, "=", RECEIVE_LINE);
		query.appendWhere(sc, new int[] { idx });

		if (StringUtils.isNotNull(creatorOid)) {
			if (query.getConditionCount() > 0)
				query.appendAnd();
			WTUser user = (WTUser) CommonUtils.persistable(creatorOid);
			long user_ids = user.getPersistInfo().getObjectIdentifier().getId();
			sc = new SearchCondition(ApprovalMaster.class, "ownership.owner.key.id", SearchCondition.EQUAL, user_ids);
			query.appendWhere(sc, new int[] { idx_m });

		}

		if (StringUtils.isNotNull(startCreatedDate)) {
			if (query.getConditionCount() > 0)
				query.appendAnd();
			Timestamp start = DateUtils.startTimestamp(startCreatedDate);
			sc = new SearchCondition(ApprovalLine.class, ApprovalLine.START_TIME, SearchCondition.GREATER_THAN_OR_EQUAL,
					start);
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(endCreatedDate)) {
			if (query.getConditionCount() > 0)
				query.appendAnd();
			Timestamp end = DateUtils.endTimestamp(endCreatedDate);
			sc = new SearchCondition(ApprovalLine.class, ApprovalLine.START_TIME, SearchCondition.LESS_THAN_OR_EQUAL,
					end);
			query.appendWhere(sc, new int[] { idx });
		}

		ca = new ClassAttribute(ApprovalLine.class, ApprovalLine.START_TIME);
		OrderBy orderBy = new OrderBy(ca, true);
		query.appendOrderBy(orderBy, new int[] { idx });

		PageUtils pager = new PageUtils(params, query);
		QueryResult result = pager.find();
		int total = pager.getTotal();
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			ApprovalColumns columns = new ApprovalColumns((ApprovalLine) obj[0]);
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

	public Map<String, Object> complete(Map<String, Object> params) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<ApprovalColumns> list = new ArrayList<ApprovalColumns>();

		String name = (String) params.get("name");
		String startCreatedDate = (String) params.get("startCreatedDate");
		String endCreatedDate = (String) params.get("endCreatedDate");
		String startCompleteDate = (String) params.get("startCompleteDate");
		String endCompleteDate = (String) params.get("endCompleteDate");
		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(ApprovalMaster.class, true);

		WTUser sessionUser = (WTUser) SessionHelper.manager.getPrincipal();

		SearchCondition sc = null;
		ClassAttribute ca = null;

		if (StringUtils.isNotNull(name)) {
			if (query.getConditionCount() > 0)
				query.appendAnd();

			ca = new ClassAttribute(ApprovalMaster.class, ApprovalMaster.NAME);
			ColumnExpression ce = ConstantExpression.newExpression("%" + name.toUpperCase() + "%");
			SQLFunction function = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca);
			sc = new SearchCondition(function, SearchCondition.LIKE, ce);
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(startCreatedDate)) {
			if (query.getConditionCount() > 0)
				query.appendAnd();
			Timestamp start = DateUtils.startTimestamp(startCreatedDate);
			sc = new SearchCondition(ApprovalMaster.class, ApprovalMaster.START_TIME,
					SearchCondition.GREATER_THAN_OR_EQUAL, start);
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(endCreatedDate)) {
			if (query.getConditionCount() > 0)
				query.appendAnd();
			Timestamp end = DateUtils.endTimestamp(endCreatedDate);
			sc = new SearchCondition(ApprovalMaster.class, ApprovalMaster.START_TIME,
					SearchCondition.LESS_THAN_OR_EQUAL, end);
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(startCompleteDate)) {
			if (query.getConditionCount() > 0)
				query.appendAnd();
			Timestamp start = DateUtils.startTimestamp(startCompleteDate);
			sc = new SearchCondition(ApprovalMaster.class, ApprovalMaster.COMPLETE_TIME,
					SearchCondition.GREATER_THAN_OR_EQUAL, start);
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(endCompleteDate)) {
			if (query.getConditionCount() > 0)
				query.appendAnd();
			Timestamp end = DateUtils.endTimestamp(endCompleteDate);
			sc = new SearchCondition(ApprovalMaster.class, ApprovalMaster.COMPLETE_TIME,
					SearchCondition.LESS_THAN_OR_EQUAL, end);
			query.appendWhere(sc, new int[] { idx });
		}

		sc = new SearchCondition(ApprovalMaster.class, "ownership.owner.key.id", "=",
				sessionUser.getPersistInfo().getObjectIdentifier().getId());
		query.appendWhere(sc, new int[] { idx });
		query.appendAnd();

		sc = new SearchCondition(ApprovalMaster.class, ApprovalMaster.STATE, "=", LINE_APPROVAL_COMPLETE);
		query.appendWhere(sc, new int[] { idx });

		ca = new ClassAttribute(ApprovalMaster.class, ApprovalLine.START_TIME);
		OrderBy orderBy = new OrderBy(ca, true);
		query.appendOrderBy(orderBy, new int[] { idx });

//		QueryResult result = PersistenceHelper.manager.find(query);
		PageUtils pager = new PageUtils(params, query);
		QueryResult result = pager.find();
		int total = pager.getTotal();
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			ApprovalColumns columns = new ApprovalColumns((ApprovalMaster) obj[0]);
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

	public Map<String, Object> rtn(Map<String, Object> params) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<ApprovalColumns> list = new ArrayList<ApprovalColumns>();
		String name = (String) params.get("name");
		String startCreatedDate = (String) params.get("startCreatedDate");
		String endCreatedDate = (String) params.get("endCreatedDate");
		String startCompleteDate = (String) params.get("startCompleteDate");
		String endCompleteDate = (String) params.get("endCompleteDate");
		QuerySpec query = new QuerySpec();
		int idx_m = query.appendClassList(ApprovalMaster.class, true);

		WTUser sessionUser = (WTUser) SessionHelper.manager.getPrincipal();

		SearchCondition sc = null;

		if (StringUtils.isNotNull(name)) {
			if (query.getConditionCount() > 0)
				query.appendAnd();

			ClassAttribute ca = new ClassAttribute(ApprovalMaster.class, ApprovalMaster.NAME);
			ColumnExpression ce = ConstantExpression.newExpression("%" + name.toUpperCase() + "%");
			SQLFunction function = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca);
			sc = new SearchCondition(function, SearchCondition.LIKE, ce);
			query.appendWhere(sc, new int[] { idx_m });
		}

		if (!CommonUtils.isAdmin()) {
			if (query.getConditionCount() > 0)
				query.appendAnd();
			long ids = sessionUser.getPersistInfo().getObjectIdentifier().getId();
			sc = new SearchCondition(ApprovalMaster.class, "ownership.owner.key.id", "=", ids);
			query.appendWhere(sc, new int[] { idx_m });
		}

		if (query.getConditionCount() > 0) {
			query.appendAnd();
		}

		sc = new SearchCondition(ApprovalMaster.class, ApprovalMaster.STATE, "=", LINE_RETURN_COMPLETE);
		query.appendWhere(sc, new int[] { idx_m });

		if (StringUtils.isNotNull(startCreatedDate)) {
			if (query.getConditionCount() > 0)
				query.appendAnd();
			Timestamp start = DateUtils.startTimestamp(startCreatedDate);
			sc = new SearchCondition(ApprovalMaster.class, ApprovalMaster.COMPLETE_TIME,
					SearchCondition.GREATER_THAN_OR_EQUAL, start);
			query.appendWhere(sc, new int[] { idx_m });
		}

		if (StringUtils.isNotNull(endCreatedDate)) {
			if (query.getConditionCount() > 0)
				query.appendAnd();
			Timestamp end = DateUtils.endTimestamp(endCreatedDate);
			sc = new SearchCondition(ApprovalMaster.class, ApprovalMaster.COMPLETE_TIME,
					SearchCondition.LESS_THAN_OR_EQUAL, end);
			query.appendWhere(sc, new int[] { idx_m });
		}

		if (StringUtils.isNotNull(startCompleteDate)) {
			if (query.getConditionCount() > 0)
				query.appendAnd();
			Timestamp start = DateUtils.startTimestamp(startCompleteDate);
			sc = new SearchCondition(ApprovalMaster.class, ApprovalMaster.COMPLETE_TIME,
					SearchCondition.GREATER_THAN_OR_EQUAL, start);
			query.appendWhere(sc, new int[] { idx_m });
		}

		if (StringUtils.isNotNull(endCompleteDate)) {
			if (query.getConditionCount() > 0)
				query.appendAnd();
			Timestamp end = DateUtils.endTimestamp(endCompleteDate);
			sc = new SearchCondition(ApprovalMaster.class, ApprovalMaster.COMPLETE_TIME,
					SearchCondition.LESS_THAN_OR_EQUAL, end);
			query.appendWhere(sc, new int[] { idx_m });
		}

		ClassAttribute ca = new ClassAttribute(ApprovalMaster.class, ApprovalMaster.START_TIME);
		OrderBy orderBy = new OrderBy(ca, true);
		query.appendOrderBy(orderBy, new int[] { idx_m });
		PageUtils pager = new PageUtils(params, query);
		QueryResult result = pager.find();
		int total = pager.getTotal();
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			ApprovalColumns columns = new ApprovalColumns((ApprovalMaster) obj[0]);
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

	public boolean isLastLine(ApprovalMaster master) throws Exception {
		boolean isLastLine = true;

		ArrayList<ApprovalLine> list = getExamAndViaLines(master);
		for (ApprovalLine line : list) {
			if (!line.getState().equals(ApprovalHelper.LINE_EXAM_COMPLETE)
					&& !line.getState().equals(ApprovalHelper.LINE_VIA_COMPLETE)) {
				isLastLine = false;
				break;
			}
		}
		return isLastLine;
	}

	public ArrayList history(Persistable per) throws Exception {
		ArrayList list = new ArrayList();

		if (per instanceof RevisionControlled) {
			RevisionControlled rc = (RevisionControlled) per;
			Mastered mastered = rc.getMaster();

			QuerySpec query = new QuerySpec();
			int idx = query.appendClassList(HistoryMasterLink.class, true);
			SearchCondition sc = new SearchCondition(HistoryMasterLink.class, "roleBObjectRef.key.id", "=",
					mastered.getPersistInfo().getObjectIdentifier().getId());
			query.appendWhere(sc, new int[] { idx });

			ClassAttribute ca = new ClassAttribute(HistoryMasterLink.class, HistoryMasterLink.CREATE_TIMESTAMP);
			OrderBy by = new OrderBy(ca, false);
			query.appendOrderBy(by, new int[] { idx });
			QueryResult result = PersistenceHelper.manager.find(query);
			while (result.hasMoreElements()) {
				Object[] obj = (Object[]) result.nextElement();
				HistoryMasterLink link = (HistoryMasterLink) obj[0];
				list.add(link);
			}
		} else {
			QuerySpec query = new QuerySpec();
			int idx = query.appendClassList(ApprovalHistoryLink.class, true);
			SearchCondition sc = new SearchCondition(ApprovalHistoryLink.class, "roleBObjectRef.key.id", "=",
					per.getPersistInfo().getObjectIdentifier().getId());
			query.appendWhere(sc, new int[] { idx });

			ClassAttribute ca = new ClassAttribute(ApprovalHistoryLink.class, ApprovalHistoryLink.CREATE_TIMESTAMP);
			OrderBy by = new OrderBy(ca, false);
			query.appendOrderBy(by, new int[] { idx });
			QueryResult result = PersistenceHelper.manager.find(query);
			while (result.hasMoreElements()) {
				Object[] obj = (Object[]) result.nextElement();
				ApprovalHistoryLink link = (ApprovalHistoryLink) obj[0];
				list.add(link);
			}
		}
		return list;
	}

	public boolean processLine(ApprovalLine line) throws Exception {
		int sort = line.getSort();
		ApprovalMaster m = line.getMaster();
		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(ApprovalLine.class, true);
		SearchCondition sc = new SearchCondition(ApprovalLine.class, ApprovalLine.SORT, "=", sort);
		query.appendWhere(sc, new int[] { idx });
		query.appendAnd();
		sc = new SearchCondition(ApprovalLine.class, "masterReference.key.id", "=",
				m.getPersistInfo().getObjectIdentifier().getId());
		query.appendWhere(sc, new int[] { idx });
		query.appendAnd();
		sc = new SearchCondition(ApprovalLine.class, ApprovalLine.STATE, "=", LINE_APPROVING);
		query.appendWhere(sc, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find(query);
		return result.size() > 0;
	}

	public ArrayList<ApprovalLine> getReceiveLine(ApprovalMaster master) throws Exception {
		ArrayList<ApprovalLine> list = new ArrayList<>();
		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(ApprovalLine.class, true);
		SearchCondition sc = new SearchCondition(ApprovalLine.class, ApprovalLine.LINE_TYPE, "=", RECEIVE_LINE);
		query.appendWhere(sc, new int[] { idx });
		query.appendAnd();
		sc = new SearchCondition(ApprovalLine.class, ApprovalLine.STATE, "=", LINE_STAND);
		query.appendWhere(sc, new int[] { idx });
		query.appendAnd();
		sc = new SearchCondition(ApprovalLine.class, "masterReference.key.id", "=",
				master.getPersistInfo().getObjectIdentifier().getId());
		query.appendWhere(sc, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find(query);
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			ApprovalLine line = (ApprovalLine) obj[0];
			list.add(line);
		}
		return list;
	}

	public ApprovalLine getAppLine(ApprovalMaster master) throws Exception {
		ApprovalLine appLine = null;
		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(ApprovalLine.class, true);
		SearchCondition sc = new SearchCondition(ApprovalLine.class, ApprovalLine.LINE_TYPE, "=", APP_LINE);
		query.appendWhere(sc, new int[] { idx });
//		query.appendAnd();
//		sc = new SearchCondition(ApprovalLine.class, ApprovalLine.STATE, "=", LINE_STAND);
//		query.appendWhere(sc, new int[] { idx });
		query.appendAnd();
		sc = new SearchCondition(ApprovalLine.class, "masterReference.key.id", "=",
				master.getPersistInfo().getObjectIdentifier().getId());
		query.appendWhere(sc, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find(query);
		if (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			appLine = (ApprovalLine) obj[0];
		}
		return appLine;
	}

	public String getReceiveUser(WTObject obj) throws Exception {
		QueryResult result = PersistenceHelper.manager.navigate(obj, "receive", ReceiveObjectLink.class);
		ReceiveObjectLink link = null;
		String str = "";
		while (result.hasMoreElements()) {
			WTUser user = (WTUser) result.nextElement();
			str += user.getFullName() + ",";
		}
		return str;
	}

	public boolean isLastViaLine(ApprovalMaster master) throws Exception {
		boolean isLastViaLine = true;

		ArrayList<ApprovalLine> list = getViaLines(master);
		for (ApprovalLine line : list) {
			if (!line.getState().equals(ApprovalHelper.LINE_VIA_COMPLETE)) {
				isLastViaLine = false;
				break;
			}
		}
		return isLastViaLine;
	}

	public ApprovalLine getStartLine(ApprovalMaster master) throws Exception {
		ApprovalLine startLine = null;
		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(ApprovalLine.class, true);
		SearchCondition sc = null;

		sc = new SearchCondition(ApprovalLine.class, ApprovalLine.LINE_TYPE, "=", ApprovalHelper.SUBMIT_LINE);
		query.appendWhere(sc, new int[] { idx });

		query.appendAnd();
		long ids = master.getPersistInfo().getObjectIdentifier().getId();
		sc = new SearchCondition(ApprovalLine.class, "masterReference.key.id", "=", ids);
		query.appendWhere(sc, new int[] { idx });

		ClassAttribute ca = new ClassAttribute(ApprovalLine.class, ApprovalLine.SORT);
		OrderBy orderBy = new OrderBy(ca, false);
		query.appendOrderBy(orderBy, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find(query);
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			startLine = (ApprovalLine) obj[0];
		}
		return startLine;
	}

	public ArrayList<ApprovalLine> getHistory(Persistable per) throws Exception {
		ArrayList<ApprovalLine> list = new ArrayList<>();

		if (per instanceof WTDocument || per instanceof WTPart || per instanceof EPMDocument) {
			RevisionControlled rc = (RevisionControlled) per;
			Mastered mastered = rc.getMaster();
//			int idx = query.appendClassList(HistoryMasterLink.class, true);
//			int idx_p = query.appendClassList(Persistable.class, false);
//			SearchCondition sc = new SearchCondition(HistoryMasterLink.class, "roleBObjectRef.key.id", Mastered.class,
//					"thePersistInfo.theObjectIdentifier.id");
//			query.appendWhere(sc, new int[] { idx, idx_p });
//			query.appendAnd();
//
//			sc = new SearchCondition(HistoryMasterLink.class, "roleBObjectRef.key.id", "=",
//					mastered.getPersistInfo().getObjectIdentifier().getId());
//			query.appendWhere(sc, new int[] { idx });
//
//			ClassAttribute ca = new ClassAttribute(HistoryMasterLink.class, HistoryMasterLink.CREATE_TIMESTAMP);
//			OrderBy by = new OrderBy(ca, false);
//			query.appendOrderBy(by, new int[] { idx });
//
//			QueryResult result = PersistenceHelper.manager.find(query);
			QueryResult result = PersistenceHelper.manager.navigate(mastered, "line", HistoryMasterLink.class);
			while (result.hasMoreElements()) {
				ApprovalLine line = (ApprovalLine) result.nextElement();
				list.add(line);
			}
		} else {

//			int idx = query.appendClassList(ApprovalHistoryLink.class, true);
//			int idx_p = query.appendClassList(Persistable.class, false);
//			SearchCondition sc = new SearchCondition(ApprovalHistoryLink.class, "roleBObjectRef.key.id",
//					Persistable.class, "thePersistInfo.theObjectIdentifier.id");
//			query.appendWhere(sc, new int[] { idx, idx_p });
//			query.appendAnd();
//
//			sc = new SearchCondition(ApprovalHistoryLink.class, "roleBObjectRef.key.id", "=",
//					per.getPersistInfo().getObjectIdentifier().getId());
//			query.appendWhere(sc, new int[] { idx });
//
//			ClassAttribute ca = new ClassAttribute(ApprovalHistoryLink.class, ApprovalHistoryLink.CREATE_TIMESTAMP);
//			OrderBy by = new OrderBy(ca, false);
//			query.appendOrderBy(by, new int[] { idx });

//			QueryResult result = PersistenceHelper.manager.find(query);

			QueryResult result = PersistenceHelper.manager.navigate(per, "line", ApprovalHistoryLink.class);
			while (result.hasMoreElements()) {
				ApprovalLine line = (ApprovalLine) result.nextElement();
				list.add(line);
			}
		}
		return list;
	}

	public String getObjType(Persistable per) throws Exception {
		if (per instanceof WTDocument) {
			return "문서";
		} else if (per instanceof ECO) {
			return "ECO";
		} else if (per instanceof ECR) {
			return "ECR";
		} else if (per instanceof ECN) {
			return "ECN";
		} else if (per instanceof Dist) {
			return "배포";
		}
		return "";
	}

	public ArrayList<LatestApprovalLine> getLatestLine() throws Exception {
		ArrayList<LatestApprovalLine> list = new ArrayList<LatestApprovalLine>();
		WTUser user = (WTUser) SessionHelper.manager.getPrincipal();

		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(LatestApprovalLine.class, true);
		SearchCondition sc = new SearchCondition(LatestApprovalLine.class, "roleAObjectRef.key.id", "=",
				user.getPersistInfo().getObjectIdentifier().getId());
		query.appendWhere(sc, new int[] { idx });

		ClassAttribute ca = new ClassAttribute(LatestApprovalLine.class, LatestApprovalLine.CREATE_TIMESTAMP);
		OrderBy by = new OrderBy(ca, true);
		query.appendOrderBy(by, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find(query);
		int i = 0;
		// 스케줄로 다 지운다..
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			LatestApprovalLine line = (LatestApprovalLine) obj[0];
			if (i > 1) {
				continue;
			}
			list.add(line);
			i++;
		}
		return list;
	}

	public int getAppCount() throws Exception {
		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(ApprovalLine.class, true);
		int idx_m = query.appendClassList(ApprovalMaster.class, true);

		WTUser sessionUser = (WTUser) SessionHelper.manager.getPrincipal();

		SearchCondition sc = null;

		sc = new SearchCondition(ApprovalLine.class, "masterReference.key.id", ApprovalMaster.class,
				"thePersistInfo.theObjectIdentifier.id");
		query.appendWhere(sc, new int[] { idx, idx_m });

		if (!CommonUtils.isAdmin()) {
			if (query.getConditionCount() > 0)
				query.appendAnd();
			long ids = sessionUser.getPersistInfo().getObjectIdentifier().getId();
			sc = new SearchCondition(ApprovalLine.class, "ownership.owner.key.id", "=", ids);
			query.appendWhere(sc, new int[] { idx });
		}

		if (query.getConditionCount() > 0) {
			query.appendAnd();
		}

		// 경유?
		query.appendOpenParen();
		sc = new SearchCondition(ApprovalLine.class, ApprovalLine.STATE, "=", LINE_VIA_STAND);
		query.appendWhere(sc, new int[] { idx });
		query.appendOr();

		sc = new SearchCondition(ApprovalLine.class, ApprovalLine.STATE, "=", LINE_APPROVING);
		query.appendWhere(sc, new int[] { idx });
		query.appendOr();

		sc = new SearchCondition(ApprovalLine.class, ApprovalLine.STATE, "=", LINE_EXAM);
		query.appendWhere(sc, new int[] { idx });
		query.appendCloseParen();

		query.appendAnd();

		query.appendOpenParen();
		sc = new SearchCondition(ApprovalLine.class, ApprovalLine.LINE_TYPE, "=", APP_LINE);
		query.appendWhere(sc, new int[] { idx });
		query.appendOr();

		sc = new SearchCondition(ApprovalLine.class, ApprovalLine.LINE_TYPE, "=", VIA_LINE);
		query.appendWhere(sc, new int[] { idx });
		query.appendOr();

		sc = new SearchCondition(ApprovalLine.class, ApprovalLine.LINE_TYPE, "=", EXAM_LINE);
		query.appendWhere(sc, new int[] { idx });
		query.appendCloseParen();

		QueryResult result = PersistenceHelper.manager.find(query);
		return result.size();
	}

	public int getReceiveCount() throws Exception {
		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(ApprovalLine.class, true);
		int idx_m = query.appendClassList(ApprovalMaster.class, true);

		WTUser sessionUser = (WTUser) SessionHelper.manager.getPrincipal();

		SearchCondition sc = null;

		sc = new SearchCondition(ApprovalLine.class, "masterReference.key.id", ApprovalMaster.class,
				"thePersistInfo.theObjectIdentifier.id");
		query.appendWhere(sc, new int[] { idx, idx_m });

		if (!CommonUtils.isAdmin()) {
			if (query.getConditionCount() > 0)
				query.appendAnd();
			long ids = sessionUser.getPersistInfo().getObjectIdentifier().getId();
			sc = new SearchCondition(ApprovalLine.class, "ownership.owner.key.id", "=", ids);
			query.appendWhere(sc, new int[] { idx });
		}

		if (query.getConditionCount() > 0) {
			query.appendAnd();
		}

		// 경유?
		sc = new SearchCondition(ApprovalLine.class, ApprovalLine.STATE, "=", LINE_RECEIVE_STAND);
		query.appendWhere(sc, new int[] { idx });
		query.appendAnd();

		sc = new SearchCondition(ApprovalLine.class, ApprovalLine.LINE_TYPE, "=", RECEIVE_LINE);
		query.appendWhere(sc, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find(query);
		return result.size();
	}

	public int getCompleteCount() throws Exception {
		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(ApprovalMaster.class, true);

		WTUser sessionUser = (WTUser) SessionHelper.manager.getPrincipal();

		SearchCondition sc = null;
		ClassAttribute ca = null;

		sc = new SearchCondition(ApprovalMaster.class, "ownership.owner.key.id", "=",
				sessionUser.getPersistInfo().getObjectIdentifier().getId());
		query.appendWhere(sc, new int[] { idx });
		query.appendAnd();

		sc = new SearchCondition(ApprovalMaster.class, ApprovalMaster.STATE, "=", LINE_APPROVAL_COMPLETE);
		query.appendWhere(sc, new int[] { idx });
		QueryResult result = PersistenceHelper.manager.find(query);
		return result.size();
	}

	public int getRtnCount() throws Exception {
		QuerySpec query = new QuerySpec();
		int idx_m = query.appendClassList(ApprovalMaster.class, true);

		WTUser sessionUser = (WTUser) SessionHelper.manager.getPrincipal();

		SearchCondition sc = null;

		if (!CommonUtils.isAdmin()) {
			if (query.getConditionCount() > 0)
				query.appendAnd();
			long ids = sessionUser.getPersistInfo().getObjectIdentifier().getId();
			sc = new SearchCondition(ApprovalMaster.class, "ownership.owner.key.id", "=", ids);
			query.appendWhere(sc, new int[] { idx_m });
		}

		if (query.getConditionCount() > 0) {
			query.appendAnd();
		}

		sc = new SearchCondition(ApprovalMaster.class, ApprovalMaster.STATE, "=", LINE_RETURN_COMPLETE);
		query.appendWhere(sc, new int[] { idx_m });

		QueryResult result = PersistenceHelper.manager.find(query);
		return result.size();
	}
}