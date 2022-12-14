package platform.project.issue.service;

import java.util.Map;

import platform.project.issue.entity.Solution;
import wt.method.RemoteInterface;

@RemoteInterface
public interface SolutionService {

	public Solution create(Map<String, Object> params) throws Exception;

	public Solution delete(String oid) throws Exception;
}
