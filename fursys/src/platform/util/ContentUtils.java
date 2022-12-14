package platform.util;

import java.util.ArrayList;

import wt.content.ApplicationData;
import wt.content.ContentHelper;
import wt.content.ContentHolder;
import wt.content.ContentRoleType;
import wt.content.ContentServerHelper;
import wt.fc.IconDelegate;
import wt.fc.IconDelegateFactory;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.fc.WTObject;
import wt.session.SessionHelper;
import wt.util.FileUtil;
import wt.util.IconSelector;

public class ContentUtils {

	private ContentUtils() {

	}

	public static ArrayList<String[]> getSecondary(String oid) throws Exception {
		return getSecondary((ContentHolder) CommonUtils.persistable(oid));
	}

	public static ArrayList<String[]> getSecondary(ContentHolder holder) throws Exception {
		ArrayList<String[]> secondarys = new ArrayList<String[]>();
		QueryResult result = ContentHelper.service.getContentsByRole(holder, ContentRoleType.SECONDARY);
		while (result.hasMoreElements()) {
			ApplicationData data = (ApplicationData) result.nextElement();
			String[] secondary = new String[9];
			secondary[0] = data.getPersistInfo().getObjectIdentifier().getStringValue();
			secondary[1] = holder.getPersistInfo().getObjectIdentifier().getStringValue();
			secondary[2] = data.getFileName();
			secondary[3] = data.getFileSizeKB() + " KB";
			secondary[4] = icon(data.getFileName());
			secondary[5] = ContentHelper.getDownloadURL(holder, data, false, data.getFileName()).toString();
			secondary[6] = "<a href='" + secondary[5] + "'><img src='" + icon(data.getFileName()) + "'></a>";
			secondary[7] = data.getUploadedFromPath();
			secondary[8] = "첨부파일";
			secondarys.add(secondary);
		}
		return secondarys;
	}

	public static String[] getPrimary(String oid) throws Exception {
		return getPrimary((ContentHolder) CommonUtils.persistable(oid));
	}

	public static String[] getPrimary(ContentHolder holder) throws Exception {
		String[] primary = new String[9];
		QueryResult result = ContentHelper.service.getContentsByRole(holder, ContentRoleType.PRIMARY);
		if (result.hasMoreElements()) {
			ApplicationData data = (ApplicationData) result.nextElement();

			primary[0] = data.getPersistInfo().getObjectIdentifier().getStringValue();
			primary[1] = holder.getPersistInfo().getObjectIdentifier().getStringValue();
			primary[2] = data.getFileName();
			primary[3] = data.getFileSizeKB() + " KB";
			primary[4] = icon(data.getFileName());
			primary[5] = ContentHelper.getDownloadURL(holder, data, false, data.getFileName()).toString();
			primary[6] = "<a href='" + primary[5] + "'><img src='" + icon(data.getFileName()) + "'></a>";
			primary[7] = data.getUploadedFromPath();
			primary[8] = "주 첨부파일";
		}
		return primary;
	}

	public static String icon(String fileName) {
		String ext = FileUtil.getExtension(fileName);
		StringBuffer icon = new StringBuffer();
//		icon.append("<img src=");
		if (ext.equalsIgnoreCase("pdf")) {
			icon.append("/Windchill/jsp/images/fileicon/file_pdf.gif");
		} else if (ext.equalsIgnoreCase("html")) {
			icon.append("/Windchill/jsp/images/fileicon/file_html.gif");
		} else if (ext.equalsIgnoreCase("zip") || ext.equalsIgnoreCase("jar") || ext.equalsIgnoreCase("war")) {
			icon.append("/Windchill/jsp/images/fileicon/file_zip.gif");
		} else if (ext.equalsIgnoreCase("xls") || ext.equalsIgnoreCase("xlsx")) {
			icon.append("/Windchill/jsp/images/fileicon/file_excel.gif");
		} else if (ext.equalsIgnoreCase("ppt") || ext.equalsIgnoreCase("pptx")) {
			icon.append("/Windchill/jsp/images/fileicon/file_ppoint.gif");
		} else if (ext.equalsIgnoreCase("gif")) {
			icon.append("/Windchill/jsp/images/fileicon/file_gif.gif");
		} else if (ext.equalsIgnoreCase("bmp")) {
			icon.append("/Windchill/jsp/images/fileicon/file_bmp.gif");
		} else if (ext.equalsIgnoreCase("jpg") || ext.equalsIgnoreCase("jpeg")) {
			icon.append("/Windchill/jsp/images/fileicon/file_jpg.gif");
		} else if (ext.equalsIgnoreCase("png")) {
			icon.append("/Windchill/jsp/images/fileicon/file_png.gif");
		} else {
			icon.append("/Windchill/jsp/images/fileicon/file_generic.gif");
		}
//		icon.append(">");
		return icon.toString();
	}

	public static void updatePrimary(String value, ContentHolder holder) throws Exception {

		QueryResult result = ContentHelper.service.getContentsByRole(holder, ContentRoleType.PRIMARY);
		if (result.hasMoreElements()) {
			ApplicationData dd = (ApplicationData) result.nextElement();
			PersistenceHelper.manager.delete(dd);
		}

		String[] s = value.split("&");
		String name = s[0];
		String path = s[1];

		ApplicationData data = ApplicationData.newApplicationData(holder);
		data.setRole(ContentRoleType.PRIMARY);
		data.setCreatedBy(SessionHelper.manager.getPrincipalReference());
		data = (ApplicationData) ContentServerHelper.service.updateContent(holder, data, path);
		data.setFileName(name);
		PersistenceHelper.manager.modify(data);
	}

	public static void updateSecondary(ArrayList<String> list, ContentHolder holder) throws Exception {

		QueryResult result = ContentHelper.service.getContentsByRole(holder, ContentRoleType.SECONDARY);
		while (result.hasMoreElements()) {
			ApplicationData dd = (ApplicationData) result.nextElement();
			PersistenceHelper.manager.delete(dd);
		}

		for (int i = 0; list != null && i < list.size(); i++) {
			String value = (String) list.get(i);
			String[] s = value.split("&");
			String name = s[0];
			String path = s[1];
			ApplicationData data = ApplicationData.newApplicationData(holder);
			data.setRole(ContentRoleType.SECONDARY);
			data.setCreatedBy(SessionHelper.manager.getPrincipalReference());
			data.setFileName(name);
			data = (ApplicationData) ContentServerHelper.service.updateContent(holder, data, path);
		}
	}

	public static String getOpenIcon(String oid) throws Exception {
		ReferenceFactory rf = new ReferenceFactory();
		WTObject obj = (WTObject) rf.getReference(oid).getObject();
		return getOpenIcon(obj, "");
	}

	public static String getStandardIcon(String oid) throws Exception {
		ReferenceFactory rf = new ReferenceFactory();
		WTObject obj = (WTObject) rf.getReference(oid).getObject();
		return getStandardIcon(obj, "");
	}

	public static String getStandardIcon(WTObject obj) throws Exception {
		return getStandardIcon(obj, "");
	}

	public static String getOpenIcon(WTObject obj) throws Exception {
		return getOpenIcon(obj, "");
	}

	public static String getStandardIcon(WTObject obj, String tooltip) throws Exception {
		IconDelegateFactory factory = IconDelegateFactory.getInstance();
		IconDelegate delegate = factory.getIconDelegate(obj);
		IconSelector selector = delegate.getStandardIconSelector();
		if (!StringUtils.isNotNull(tooltip)) {
			tooltip = delegate.getToolTip(SessionHelper.getLocale());
		}
		/// Windchill/jsp/images/part.gif
		// icon.append("<img src=\"/Windchill/" + selector.getIconKey() + "\" title=\""
		/// + tooltip + "\">");
		return "/Windchill/" + selector.getIconKey();
	}

	public static String getOpenIcon(WTObject obj, String tooltip) throws Exception {
		// StringBuffer icon = new StringBuffer();
		IconDelegateFactory factory = IconDelegateFactory.getInstance();
		IconDelegate delegate = factory.getIconDelegate(obj);
		IconSelector selector = delegate.getOpenIconSelector();
		if (!StringUtils.isNotNull(tooltip)) {
			tooltip = delegate.getToolTip(SessionHelper.getLocale());
		}
		// icon.append("<img src=\"/Windchill/" + selector.getIconKey() + "\" title=\""
		// + tooltip + "\">");
		return "/Windchill/" + selector.getIconKey();
	}

}
