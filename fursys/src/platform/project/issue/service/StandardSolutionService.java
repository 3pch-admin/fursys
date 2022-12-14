package platform.project.issue.service;

import java.util.Map;

import platform.project.issue.entity.Issue;
import platform.project.issue.entity.Solution;
import platform.util.CommonUtils;
import wt.fc.PersistenceHelper;
import wt.pom.Transaction;
import wt.services.StandardManager;
import wt.util.WTException;

public class StandardSolutionService extends StandardManager implements SolutionService {

	public static StandardSolutionService newStandardSolutionService() throws WTException {
		StandardSolutionService instance = new StandardSolutionService();
		instance.initialize();
		return instance;
	}

	@Override
	public Solution create(Map<String, Object> params) throws Exception {
		Solution solution = null;
		String oid = (String) params.get("oid");
		String name = (String) params.get("name");
		String description = (String) params.get("description");
		Transaction trs = new Transaction();
		try {
			trs.start();

			solution = Solution.newSolution();
			solution.setName(name);
			solution.setDescription(description);
			PersistenceHelper.manager.save(solution);

			Issue issue = (Issue) CommonUtils.persistable(oid);
			issue.setState("해결완료");
			issue.setSolution(solution);
			PersistenceHelper.manager.modify(issue);

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
		return solution;
	}

	@Override
	public Solution delete(String oid) throws Exception {
		Solution solution = null;
		Transaction trs = new Transaction();
		try {
			trs.start();

			solution = (Solution) CommonUtils.persistable(oid);
			PersistenceHelper.manager.delete(solution);

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
		return solution;
	}
}
