package platform.department.service;

import platform.department.entity.Department;
import wt.method.RemoteInterface;
import wt.util.WTException;

@RemoteInterface
public interface DepartmentService {

	public Department make() throws WTException;

	public void loadFromDepartmentExcel(String path) throws Exception;

	public void setParent(String path) throws Exception;

}
