package platform.util;

public class StringUtils {

	private StringUtils() {

	}

	public static boolean isNotNull(Object var) {
		if (var == null) {
			return false;
		}
		return true;
	}

	public static boolean isNotNull(String var) {
		if (var == null || var.equals("")) {
			return false;
		}
		return true;
	}

	public static String convertToEmpty(String param) {
		if (!isNotNull(param)) {
			return "";
		}
		return param.trim();
	}

	public static String convertToStr(String param, String str) {
		if (isNotNull(param)) {
			return param.trim();
		}
		return str.trim();
	}
}
