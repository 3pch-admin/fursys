package platform.project.issue.service;

import java.util.Map;

import platform.project.issue.entity.Issue;
import wt.method.RemoteInterface;

@RemoteInterface
public interface IssueService {

	public Issue create(Map<String, Object> params) throws Exception;

	public Issue delete(String oid) throws Exception;

	public Issue modify(Map<String, Object> params) throws Exception;

	public void delete(Map<String, Object> params) throws Exception;

}
