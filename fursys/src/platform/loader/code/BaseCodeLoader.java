package platform.loader.code;

import platform.code.service.BaseCodeHelper;
import wt.method.RemoteMethodServer;

public class BaseCodeLoader {

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
		BaseCodeHelper.service.initialize(path);
	}
}