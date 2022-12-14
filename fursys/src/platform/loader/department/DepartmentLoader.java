package platform.loader.department;

import platform.department.service.DepartmentHelper;
import platform.user.service.UserHelper;
import wt.method.RemoteMethodServer;

public class DepartmentLoader {

	public static void main(String[] args) throws Exception {

		if (args.length != 3) {
			System.out.println("args[0] = username");
			System.out.println("args[1] = password");
			System.out.println("args[2] = excel path");
			System.exit(0);
		}

		String path = args[2];
		String user = args[0];
		String pw = args[1];

		RemoteMethodServer.getDefault().setUserName(user);
		RemoteMethodServer.getDefault().setPassword(pw);

		load(path);

		System.exit(0);
	}

	private static void load(String path) throws Exception {
		DepartmentHelper.service.loadFromDepartmentExcel(path);
		DepartmentHelper.service.setParent(path);
	}
}
