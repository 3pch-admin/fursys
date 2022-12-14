package platform.user.service;

import java.util.HashMap;
import java.util.List;

import platform.user.entity.UserColumns;
import wt.method.RemoteInterface;
import wt.org.WTUser;

@RemoteInterface
public interface UserService {

	public void postSave(WTUser wtUser) throws Exception;

	public void postModify(WTUser wtUser) throws Exception;

	public void loadFromUserExcel(String path) throws Exception;

	public void modify(HashMap<String, List<UserColumns>> paramsMap) throws Exception;

}
