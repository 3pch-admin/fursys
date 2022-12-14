package platform.project.template.service;

import java.util.ArrayList;
import java.util.Map;

import platform.project.task.entity.Task;
import platform.project.task.entity.TaskTreeNode;
import platform.project.template.entity.Template;
import platform.project.template.entity.TemplateDTO;
import wt.method.RemoteInterface;

@RemoteInterface
public interface TemplateService {

	public Template create(TemplateDTO dto) throws Exception;

	public Template delete(String oid) throws Exception;

	public void saveTree(Map<String, Object> params) throws Exception;

	public void saveTree(Template template, Task parentTask, ArrayList<TaskTreeNode> childrens) throws Exception;

	public void calculation(Template template) throws Exception;

	public Template modify(TemplateDTO dto) throws Exception;

	public void reset(Map<String, Object> params) throws Exception;
}
