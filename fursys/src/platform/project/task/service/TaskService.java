package platform.project.task.service;

import java.util.Map;

import platform.project.entity.Project;
import platform.project.task.entity.Task;
import platform.project.template.entity.Template;
import wt.method.RemoteInterface;

@RemoteInterface
public interface TaskService {

	public void pre(Map<String, Object> params) throws Exception;

	public void disconnect(Map<String, Object> params) throws Exception;

	public Task complete(Map<String, Object> params) throws Exception;

	public Task stop(Map<String, Object> params) throws Exception;

	public Task start(Map<String, Object> params) throws Exception;

	public void _calculation(Task task) throws Exception;

	public void progress(Map<String, Object> params) throws Exception;

	public void role(Map<String, Object> params) throws Exception;

	public void dependency(Project project) throws Exception;

	public Task modify(Map<String, Object> params) throws Exception;
}
