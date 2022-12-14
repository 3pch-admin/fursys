<%@page import="wt.session.SessionHelper"%>
<%@page import="wt.org.WTUser"%>
<%@page import="platform.project.issue.entity.Solution"%>
<%@page import="platform.util.CommonUtils"%>
<%@page import="platform.project.issue.entity.Issue"%>
<%@page import="platform.project.issue.entity.IssueDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String oid = (String) request.getParameter("oid");
Issue issue = (Issue) CommonUtils.persistable(oid);
IssueDTO dto = new IssueDTO(issue);
Solution solution = dto.getSolution();
%>
<table class="create-table solution-view close">
	<colgroup>
		<col width="130">
		<col width="*">
	</colgroup>
	<tr>
		<th>제목</th>
		<td><%=solution != null ? solution.getName()  : "<font color='red'><b>해결방안이 등록되지 않았습니다.</b></font>"%></td>
	</tr>
	<tr>
		<th>해결내용</th>
		<td>
			<textarea rows="14" cols="" name="description" id="description" class="AXTextarea none" readonly="readonly"><%=solution != null ? solution.getDescription() : ""%>
			</textarea>
		</td>
	</tr>
	<tr>
		<th>처리날짜</th>
		<td><%=solution != null ? solution.getCreateTimestamp().toString().substring(0, 10) : ""%></td>
	</tr>
</table>