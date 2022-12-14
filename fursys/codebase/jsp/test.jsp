<%@page import="wt.content.ContentRoleType"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.io.FileOutputStream"%>
<%@page import="java.io.File"%>
<%@page import="wt.content.ContentServerHelper"%>
<%@page import="wt.content.ApplicationData"%>
<%@page import="wt.content.ContentItem"%>
<%@page import="wt.content.ContentHelper"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="wt.epm.EPMDocument"%>
<%@page import="java.util.ArrayList"%>
<%@page import="wt.part.WTPart"%>
<%@page import="platform.util.CommonUtils"%>
<%@page import="platform.part.service.PartHelper"%>
<%@page import="wt.part.WTPartMaster"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String oid = "wt.part.WTPartMaster:1373901";
WTPartMaster m = (WTPartMaster) CommonUtils.persistable(oid);
WTPart p = PartHelper.manager.getLatest(m);

ArrayList<WTPart> list = PartHelper.manager.getter(p);
System.out.println("list=" + list.size());
for (WTPart pp : list) {

	EPMDocument e = PartHelper.manager.getEPMDocument(pp);
	QueryResult qr = ContentHelper.service.getContentsByRole(e, ContentRoleType.PRIMARY);
	if (qr.hasMoreElements()) {
		ContentItem item = (ContentItem) qr.nextElement();
		if (item instanceof ApplicationData) {
	ApplicationData data = (ApplicationData) item;
	byte[] buffer = new byte[10240];
	InputStream is = ContentServerHelper.service.findLocalContentStream(data);
	String name = new String(e.getCADName().getBytes("EUC-KR"), "8859_1");
	File file = new File("C:" + File.separator + "sample" + File.separator + name);
	FileOutputStream fos = new FileOutputStream(file);
	int j = 0;
	while ((j = is.read(buffer, 0, 10240)) > 0) {
		fos.write(buffer, 0, j);
	}
	fos.close();
	is.close();
		}
	}
}
%>