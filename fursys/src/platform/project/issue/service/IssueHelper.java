package platform.project.issue.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import platform.project.entity.Project;
import platform.project.issue.entity.Issue;
import platform.project.issue.entity.IssueColumns;
import platform.project.issue.entity.IssueProjectLink;
import platform.project.task.entity.Task;
import platform.util.CommonUtils;
import platform.util.PageUtils;
import platform.util.StringUtils;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.org.WTUser;
import wt.query.ClassAttribute;
import wt.query.ColumnExpression;
import wt.query.ConstantExpression;
import wt.query.OrderBy;
import wt.query.QuerySpec;
import wt.query.SQLFunction;
import wt.query.SearchCondition;
import wt.services.ServiceFactory;
import wt.session.SessionHelper;
import wt.util.WTAttributeNameIfc;

public class IssueHelper {

	public static final IssueService service = ServiceFactory.getService(IssueService.class);
	public static final IssueHelper manager = new IssueHelper();

	public ArrayList<Issue> getIssues(Task task) throws Exception {
		ArrayList<Issue> list = new ArrayList<>();
		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(Issue.class, true);

		SearchCondition sc = null;

		sc = new SearchCondition(Issue.class, "taskReference.key.id", "=",
				task.getPersistInfo().getObjectIdentifier().getId());
		query.appendWhere(sc, new int[] { idx });
//		query.appendAnd();

		if (task.getProject() != null) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}

			sc = new SearchCondition(Issue.class, "projectReference.key.id", "=",
					task.getProject().getPersistInfo().getObjectIdentifier().getId());
			query.appendWhere(sc, new int[] { idx });
		}

		ClassAttribute ca = new ClassAttribute(Issue.class, Issue.CREATE_TIMESTAMP);
		OrderBy by = new OrderBy(ca, true);
		query.appendOrderBy(by, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find(query);
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			Issue issue = (Issue) obj[0];
			list.add(issue);
		}
		return list;
	}

	public Map<String, Object> list(Map<String, Object> params) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<IssueColumns> list = new ArrayList<>();

		String name = (String) params.get("name");
		String issueType = (String) params.get("issueTypes");
		String roles = (String) params.get("roles");
		String state = (String) params.get("state");

		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(Issue.class, true);

		SearchCondition sc = null;
		ClassAttribute ca = null;

		WTUser user = (WTUser) SessionHelper.manager.getPrincipal();

		if (!CommonUtils.isAdmin()) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(Issue.class, "managerReference.key.id", "=",
					user.getPersistInfo().getObjectIdentifier().getId());
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(name)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			ca = new ClassAttribute(Issue.class, Issue.NAME);
			ColumnExpression ce = ConstantExpression.newExpression("%" + name.toUpperCase() + "%");
			SQLFunction function = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca);
			sc = new SearchCondition(function, SearchCondition.LIKE, ce);
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(issueType)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(Issue.class, Issue.ISSUE_TYPE, "=", issueType);
			query.appendWhere(sc, new int[] { idx });
		}

//		if(StringUtils.isNotNull(roles)) {
//			if(query.getConditionCount() > 0) {
//				query.appendAnd();
//			}
//			int idx_link = query.appendClassList(Issue.class, false);
//			sc = new SearchCondition(Issue.class,  )
//			query.appendWhere(sc, new int[] { idx, idx_link });
//			query.appendAnd();
//		}

		if (StringUtils.isNotNull(state)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(Issue.class, Issue.STATE, "=", state);
			query.appendWhere(sc, new int[] { idx });
		}

		ca = new ClassAttribute(Issue.class, WTAttributeNameIfc.CREATE_STAMP_NAME);
		OrderBy orderBy = new OrderBy(ca, true);
		query.appendOrderBy(orderBy, new int[] { idx });

//		QueryResult result = PersistenceHelper.manager.find(query);
		PageUtils pager = new PageUtils(params, query);
		QueryResult result = pager.find();
		int total = pager.getTotal();
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			IssueColumns columns = new IssueColumns((Issue) obj[0]);
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

	public ArrayList<IssueColumns> getIssues(Project project) throws Exception {
		ArrayList<IssueColumns> list = new ArrayList<>();
		QueryResult result = PersistenceHelper.manager.navigate(project, "issue", IssueProjectLink.class);
		while (result.hasMoreElements()) {
			Issue issue = (Issue) result.nextElement();
			list.add(new IssueColumns(issue));
		}
		return list;
	}
}