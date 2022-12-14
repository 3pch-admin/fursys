package platform.project.issue.service;

import wt.services.ServiceFactory;

public class SolutionHelper {

	public static final SolutionService service = ServiceFactory.getService(SolutionService.class);
	public static final SolutionHelper manager = new SolutionHelper();
}
