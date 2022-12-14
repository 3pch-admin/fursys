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
QueryResult result = VersionControlHelper.service.allIterationsOf(part.getMaster());
%>
<table class="view-table history-view close">
	<colgroup>
		<col width="250">
		<col width="*">
		<col width="120">
		<col width="120">
		<col width="120">
		<col width="120">
		<col width="120">
	</colgroup>
	<tr>
		<th>부품번호</th>
		<th>부품명</th>
		<th>버전</th>
		<th>수정자</th>
		<th>수정일자</th>
		<th>회사</th>
		<th>브랜드</th>
	</tr>
	<%
	while (result.hasMoreElements()) {
		WTPart d = (WTPart) result.nextElement();
	%>
	<tr>
		<td class="left indent10 first"><%=d.getNumber()%></td>
		<td class="left indent10"><%=d.getName()%></td>
		<td class="center"><%=d.getVersionIdentifier().getSeries().getValue() + "." + d.getIterationIdentifier().getSeries().getValue()%></td>
		<td class="center"><%=d.getModifierName()%></td>
		<td class="center"><%=d.getModifyTimestamp().toString().substring(0, 10)%></td>
		<td class="center"><%=IBAUtils.getStringValue(d, "COMPANY_CODE")%></td>
		<td class="center"><%=IBAUtils.getStringValue(d, "BRAND_CODE")%></td>
	</tr>

	<%
	}
	%>

</table>