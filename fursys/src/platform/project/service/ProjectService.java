package platform.project.service;

import java.util.ArrayList;
import java.util.Map;

import platform.project.entity.Project;
import platform.project.task.entity.Task;
import platform.project.task.entity.TaskTreeNode;
import platform.project.template.entity.Template;
import wt.method.RemoteInterface;

@RemoteInterface
public interface ProjectService {

	public Project create(Map<String, Object> params) throws Exception;

	public void copys(Project project, Template template) throws Exception;

	public void calculation(Project project) throws Exception;

	public void saveTree(Map<String, Object> params) throws Exception;

	public void saveTree(Project project, Task parentTask, ArrayList<TaskTreeNode> childrens) throws Exception;

	public void start(String oid) throws Exception;

	public void complete(String oid) throws Exception;

}
