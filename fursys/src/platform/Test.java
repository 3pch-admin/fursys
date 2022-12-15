package platform;

import java.io.File;

import platform.util.CommonUtils;
import wt.content.ApplicationData;
import wt.content.ContentHelper;
import wt.content.ContentRoleType;
import wt.content.ContentServerHelper;
import wt.doc.WTDocument;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.session.SessionHelper;

public class Test {

	public static void main(String[] args) throws Exception {

		String s = "wt.doc.WTDocument:189202";

		WTDocument d = (WTDocument) CommonUtils.persistable(s);

		File f = new File("C:" + File.separator + "AUIGrid_style.css");
		QueryResult result = ContentHelper.service.getContentsByRole(d, ContentRoleType.PRIMARY);
		if (result.hasMoreElements()) {
			ApplicationData dd = (ApplicationData) result.nextElement();
			PersistenceHelper.manager.delete(dd);
		}

		ApplicationData data = ApplicationData.newApplicationData(d);
		data.setRole(ContentRoleType.PRIMARY);
		data.setCreatedBy(SessionHelper.manager.getPrincipalReference());
		data = (ApplicationData) ContentServerHelper.service.updateContent(d, data, f.getAbsolutePath());
		data.setFileName(f.getName());
		PersistenceHelper.manager.modify(data);
		System.out.println(s);

	}
}