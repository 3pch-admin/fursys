<%@page import="platform.doc.entity.DocumentColumns"%>
<%@page import="java.util.List"%>
<%@page import="platform.part.service.PartHelper"%>
<%@page import="wt.part.WTPart"%>
<%@page import="platform.util.IBAUtils"%>
<%@page import="wt.vc.VersionControlHelper"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="platform.util.CommonUtils"%>
<%@page import="wt.doc.WTDocument"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String oid = (String) request.getParameter("oid");
WTPart part = (WTPart) CommonUtils.persistable(oid);

List<DocumentColumns> docList = PartHelper.manager.getWTDocuments(part);

%>
<table class="view-table doc-view close">
	<colgroup>
		<col width="250">
		<col width="*">
		<col width="120">
		<col width="120">
		<col width="120">
	</colgroup>
	<tr>
		<th>문서번호</th>
		<th>문서명</th>
		<th>버전</th>
		<th>수정자</th>
		<th>수정일자</th>
	</tr>
	<%
	for(DocumentColumns docData : docList){
	%>
	<tr>
		<td class="left indent10 first"><%=docData.getNumber()%></td>
		<td class="left indent10"><%=docData.getName()%></td>
		<td class="center"><%=docData.getVersion()%></td>
		<td class="center"><%=docData.getModifier()%></td>
		<td class="center"><%=docData.getModifiedDate()%></td>
	</tr>

	<%
	}
	%>

</table>