package platform.project.issue.service;

import java.util.ArrayList;
import java.util.Map;

import platform.project.issue.entity.Issue;
import platform.project.issue.entity.Solution;
import platform.project.task.entity.Task;
import platform.util.CommonUtils;
import platform.util.ContentUtils;
import wt.fc.PersistenceHelper;
import wt.org.WTPrincipal;
import wt.org.WTUser;
import wt.ownership.Ownership;
import wt.pom.Transaction;
import wt.services.StandardManager;
import wt.session.SessionHelper;
import wt.util.WTException;

public class StandardIssueService extends StandardManager implements IssueService {

	public static StandardIssueService newStandardIssueService() throws WTException {
		StandardIssueService instance = new StandardIssueService();
		instance.initialize();
		return instance;
	}

	@Override
	public Issue create(Map<String, Object> params) throws Exception {
		Issue issue = null;
		String oid = (String) params.get("oid");
		String name = (String) params.get("name");
		String issueType = (String) params.get("issueType");
		String description = (String) params.get("description");
		String manager = (String) params.get("managerOid");
		ArrayList<String> secondary = (ArrayList<String>) params.get("secondary");
		Transaction trs = new Transaction();
		try {
			trs.start();

			WTPrincipal prin = SessionHelper.manager.getPrincipal();
			Task task = (Task) CommonUtils.persistable(oid);
			WTUser m = (WTUser) CommonUtils.persistable(manager);
			issue = Issue.newIssue();
			issue.setName(name);
			issue.setDescription(description);
			issue.setTask(task);
			issue.setManager(m);
			issue.setProject(task.getProject());
			issue.setOwnership(Ownership.newOwnership(prin));
			issue.setIssueType(issueType);
			issue.setState("작업중");
			PersistenceHelper.manager.save(issue);

			trs.commit();
			trs = null;
		} catch (Exception e) {
			e.printStackTrace();
			trs.rollback();
			throw e;
		} finally {
			if (trs != null)
				trs.rollback();
		}
		return issue;
	}

	@Override
	public Issue delete(String oid) throws Exception {
		Issue issue = null;
		Transaction trs = new Transaction();
		try {
			trs.start();

			issue = (Issue) CommonUtils.persistable(oid);
			if (issue.getSolution() != null) {
				Solution solution = issue.getSolution();
				PersistenceHelper.manager.delete(solution);
			}

			issue = (Issue) PersistenceHelper.manager.delete(issue);

			trs.commit();
			trs = null;
		} catch (Exception e) {
			e.printStackTrace();
			trs.rollback();
			throw e;
		} finally {
			if (trs != null)
				trs.rollback();
		}
		return issue;
	}

	@Override
	public Issue modify(Map<String, Object> params) throws Exception {
		Issue issue = null;
		Transaction trs = new Transaction();
		try {
			trs.start();

			trs.commit();
			trs = null;
		} catch (Exception e) {
			e.printStackTrace();
			trs.rollback();
			throw e;
		} finally {
			if (trs != null)
				trs.rollback();
		}
		return issue;
	}

	@Override
	public void delete(Map<String, Object> params) throws Exception {
		ArrayList<String> list = (ArrayList<String>) params.get("list");
		Transaction trs = new Transaction();
		try {
			trs.start();

			for (String oid : list) {
				Issue issue = (Issue) CommonUtils.persistable(oid);
				if (issue.getSolution() != null) {
					Solution solution = issue.getSolution();
					PersistenceHelper.manager.delete(solution);
				}

				issue = (Issue) PersistenceHelper.manager.delete(issue);
			}
			trs.commit();
			trs = null;
		} catch (Exception e) {
			e.printStackTrace();
			trs.rollback();
			throw e;
		} finally {
			if (trs != null)
				trs.rollback();
		}
	}
}
