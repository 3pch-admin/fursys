<%@page import="platform.echange.eco.entity.ECO"%>
<%@page import="platform.echange.eco.entity.ECODTO"%>
<%@page import="platform.echange.ecn.entity.ECN"%>
<%@page import="platform.util.CommonUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String oid = (String) request.getParameter("oid");
ECN ecn = (ECN) CommonUtils.persistable(oid);
%>
<table class="view-table eco-view close">
	<colgroup>
		<col width="150">
		<col width="*">
		<col width="120">
		<col width="100">
		<col width="100">
		<col width="200">
		<col width="140">
<!-- 		<col width="100"> -->
		<col width="100">
		<col width="100">
		<col width="80">
	</colgroup>
	<tr>
		<th>ECO번호</th>
		<th>ECO명</th>
		<th>ECO유형</th>
		<th>개발유형</th>
		<th>통보유형</th>
		<th>제품LOT관리</th>
		<th>적용시점</th>
<!-- 		<th>예상적용일</th> -->
		<th>작성자</th>
		<th>작성일시</th>
		<th>상태</th>
	</tr>
	<%
	ECO eco = ecn.getEco();
	ECODTO data = new ECODTO(eco);
	%>
	<tr>
		<td class="center first"><%=data.getNumber()%></td>
		<td class="left indent10"><%=data.getName()%></td>
		<td class="center"><%=data.getEcoTypeNm()%></td>
		<td class="center"><%=data.getDevTypeNm()%></td>
		<td class="center"><%=data.getNotiTypeNm()%></td>
		<td class="center"><%=data.getLotNm()%></td>
		<td class="center"><%=data.getApplyTimeNm()%></td>
<%-- 		<td class="center"><%=data.getExpectationTime()%></td> --%>
		<td class="center"><%=data.getCreator()%></td>
		<td class="center"><%=data.getCreatedDate()%></td>
		<td class="center"><%=data.getState()%></td>
	</tr>
</table>