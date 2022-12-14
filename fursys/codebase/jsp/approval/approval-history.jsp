<%@page import="org.springframework.security.oauth2.provider.approval.Approval"%>
<%@page import="java.lang.reflect.Array"%>
<%@page import="platform.util.StringUtils"%>
<%@page import="platform.approval.entity.ApprovalLine"%>
<%@page import="java.util.ArrayList"%>
<%@page import="platform.approval.entity.ApprovalMaster"%>
<%@page import="platform.approval.service.ApprovalHelper"%>
<%@page import="platform.util.CommonUtils"%>
<%@page import="wt.fc.Persistable"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String oid = (String) request.getParameter("oid");
Persistable per = (Persistable) CommonUtils.persistable(oid);
ApprovalMaster master = ApprovalHelper.manager.getMaster(per);
ArrayList<ApprovalLine> lines = ApprovalHelper.manager.getHistory(per);
%>
<table class="view-table app-view close">
	<colgroup>
		<col width="60">
		<col width="80">
		<col width="*">
		<col width="500">
		<col width="100">
		<col width="100">
		<col width="120">
		<col width="120">
		<col width="120">
		<col width="120">
	</colgroup>

	<tr>
		<th>구분</th>
		<th>역할</th>
		<th>결재제목</th>
		<th>결재의견</th>
		<th>담당자</th>
		<th>기안자</th>
		<th>상태</th>
		<th>수신일</th>
		<th>완료일</th>
		<th>의견만기일</th>
	</tr>
	<%
	for (ApprovalLine line : lines) {
	%>
	<tr>
		<td class="center first">
			<%
			String lineType = line.getLineType();
			if ("결재".equals(lineType)) {
			%>
			<b>
				<font color="red"><%=line.getLineType()%></font>
			</b>
			<%
			} else if ("검토".equals(lineType)) {
			%>
			<b>
				<font color="blue"><%=line.getLineType()%></font>
			</b>
			<%
			} else if ("수신".equals(lineType)) {
			%>
			<b>
				<font color="green"><%=line.getLineType()%></font>
			</b>
			<%
			} else {
			%>
			<b><%=line.getLineType()%></b>
			<%
			}
			%>
		</td>
		<td class="center"><%=line.getRole()%></td>
		<td class="left indent10"><%=line.getMaster().getName()%></td>
		<td class="left indent10"><%=StringUtils.convertToEmpty(line.getDescription())%></td>
		<td class="center"><%=line.getOwnership().getOwner().getFullName()%></td>
		<td class="center"><%=line.getMaster().getOwnership().getOwner().getFullName()%></td>
		<td class="center"><%=line.getState()%></td>
		<td class="center"><%=line.getCreateTimestamp().toString().substring(0, 10)%></td>
		<td class="center"><%=line.getCompleteTime() != null ? line.getCompleteTime().toString().substring(0, 10) : ""%></td>
		<td class="center"><%=line.getLimit() != null ? line.getLimit().toString().substring(0, 10) : ""%></td>
	</tr>
	<%
	}
	if (lines.size() == 0) {
	%>
	<tr>
		<td colspan="10" class="center first">
			<font color="red">
				<b>결재 이력이 없습니다.</b>
			</font>
		</td>
	</tr>
	<%
	}
	%>
</table>