package platform.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

import wt.util.WTProperties;

public class DateUtils {

	private static SimpleDateFormat start = null;
	private static SimpleDateFormat end = null;

	static {
		start = new SimpleDateFormat("yyyy-MM-dd");
		end = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");

		try {
			String timezone = WTProperties.getLocalProperties().getProperty("wt.method.timezone");
			TimeZone zone = null;
			if (StringUtils.isNotNull(timezone)) {
				zone = TimeZone.getTimeZone(timezone);
			}

			if (zone == null) {
//				zone = TimeZone.getTimeZone("Asia/Seoul");
				zone = TimeZone.getTimeZone("JST");
			}

			start.setTimeZone(zone);
			end.setTimeZone(zone);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private DateUtils() {

	}

	public static Timestamp today() throws Exception {
		Calendar ca = Calendar.getInstance();
		Timestamp today = new Timestamp(ca.getTime().getTime());
		return today;
	}

	public static Timestamp startTimestamp(String param) throws Exception {
		if (!StringUtils.isNotNull(param)) {
			return null;
		}
		Date date = start.parse(param);
		Timestamp start = new Timestamp(date.getTime());
		return start;
	}

	public static Timestamp endTimestamp(String param) throws Exception {
		if (!StringUtils.isNotNull(param)) {
			return null;
		}
		Date date = end.parse(param + " 23:59:59");
		Timestamp end = new Timestamp(date.getTime());
		return end;
	}

	public static int getDuration(Timestamp start, Timestamp end) {
		if (!StringUtils.isNotNull(start) || !StringUtils.isNotNull(end)) {
			return 1;
		}

		Date startDate = new Date(start.getTime());
		Date endDate = new Date(end.getTime());
		return getDuration(startDate, endDate);
	}

	public static int getDuration(Date start, Date end) {
		Date before = null;
		Date after = null;

		if (start == null || end == null) {
			return 0;
		}

		if (start.getTime() < end.getTime()) {
			before = start;
			after = end;
		} else {
			after = start;
			before = end;
		}
		long millis = 24 * 60 * 60 * 1000;
		int day = (int) ((after.getTime() - before.getTime()) / millis);
		return day;
	}

	public static String getTimeToString(java.util.Date timeDate, String sFormat) {
		TimeZone tz = TimeZone.getTimeZone("Asia/Seoul");
		String defaultZone = tz.getID(); // "Asia/Seoul"
		return getTimeToString(timeDate, sFormat, defaultZone);
	}

	public static String getTimeToString(java.util.Date timeDate, String sFormat, String tzString) {
		if (timeDate == null) {
			return "";
		}

		if (sFormat == null || sFormat.length() == 0) {
			sFormat = "yyyy-MM-dd HH:mm:ss";
		}

		TimeZone tz = null;
		if (tzString == null || tzString.length() == 0) {
			tz = TimeZone.getDefault();
		} else {
			tz = TimeZone.getTimeZone(tzString);
		}

		String retString = "";

		try {
			SimpleDateFormat sdf = new SimpleDateFormat(sFormat);
			SimpleTimeZone stz = new SimpleTimeZone(tz.getRawOffset(), tz.getID());
			sdf.setTimeZone(stz);
			retString = sdf.format(timeDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retString;
	}

}
