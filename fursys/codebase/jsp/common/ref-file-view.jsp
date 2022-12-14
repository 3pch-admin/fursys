<%@page import="java.util.ArrayList"%>
<%@page import="platform.util.CommonUtils"%>
<%@page import="wt.content.ContentHolder"%>
<%@page import="platform.util.ContentUtils"%>
<%@page import="wt.content.ContentHelper"%>
<%@page import="wt.content.ApplicationData"%>
<%@page import="wt.fc.QueryResult"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String oid = (String) request.getParameter("oid");
ContentHolder holder = (ContentHolder) CommonUtils.persistable(oid);
%>
<table class="view-table file-view close">
	<colgroup>
		<col width="40">
		<col width="120">
		<col width="*">
		<col width="120">
	</colgroup>
	<tr>
		<th>&nbsp;</th>
		<th>타입</th>
		<th>파일명</th>
		<th>파일크기</th>
	</tr>
	<%
	String[] primary = ContentUtils.getPrimary(holder);
	if (primary[0] != null) {
	%>
	<tr>
		<td class="center first"><%=primary[6]%></td>
		<td class="center">
			<font color="red">
				<b><%=primary[8]%></b>
			</font>
		</td>
		<td class="left indent10"><%=primary[2]%></td>
		<td class="center"><%=primary[3]%></td>
	</tr>
	<%
	}
	ArrayList<String[]> secondary = ContentUtils.getSecondary(holder);
	for (String[] ss : secondary) {
	%>
	<tr>
		<td class="center first"><%=ss[6]%></td>
		<td class="center">
			<font color="blue">
				<b><%=ss[8]%></b>
			</font>
		</td>
		<td class="left indent10"><%=ss[2]%></td>
		<td class="center"><%=ss[3]%></td>
	</tr>
	<%
	}
	if (primary[0] == null && secondary.size() == 0) {
	%>
	<tr>
		<td class="center first" colspan="4">
			<font color="red">
				<b>등록된 첨부파일이 없습니다.</b>
			</font>
		</td>
	</tr>
	<%
	}
	%>
</table>