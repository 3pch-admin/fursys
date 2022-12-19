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
String s = "wt.doc.WTDocument:189202";
//테스트11 병합용1
WTDocument d = (WTDocument) CommonUtils.persistable(s);

File f = new File("C:" + File.separator + "AUIGrid_style1.css");
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
%>