<%@page import="platform.echange.ecn.entity.ECN"%>
<%@page import="platform.part.service.PartHelper"%>
<%@page import="platform.util.ThumbnailUtils"%>
<%@page import="platform.echange.eco.service.ECOHelper"%>
<%@page import="platform.echange.eco.entity.ECOWTPartLink"%>
<%@page import="platform.echange.eco.entity.ECO"%>
<%@page import="wt.part.WTPart"%>
<%@page import="platform.doc.service.DocumentHelper"%>
<%@page import="platform.doc.entity.WTDocumentWTPartLink"%>
<%@page import="java.util.ArrayList"%>
<%@page import="platform.util.IBAUtils"%>
<%@page import="wt.vc.VersionControlHelper"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="platform.util.CommonUtils"%>
<%@page import="wt.doc.WTDocument"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String oid = (String) request.getParameter("oid");
ECN ecn = (ECN) CommonUtils.persistable(oid);
ECO eco = ecn.getEco();
ArrayList<ECOWTPartLink> list = ECOHelper.manager.getPartLinks(eco);
%>
<table class="create-table part-view close">
	<colgroup>
		<col width="40">
		<col width="300">
		<col width="*">
		<col width="90">
		<col width="80">
		<col width="120">
		<col width="100">
		<col width="100">
		<col width="120">
		<col width="120">
	</colgroup>
	<tr>
		<th>&nbsp;</th>
		<th>부품번호</th>
		<th>부품명</th>
		<th>부품유형</th>
		<th>버전</th>
		<th>상태</th>
		<th>작성자</th>
		<th>작성일자</th>
		<th>회사</th>
		<th>브랜드</th>
	</tr>
	<%
	for (ECOWTPartLink link : list) {
		WTPart d = (WTPart) link.getPart();
	%>
	<tr>
		<td class="center first">
			<img src="<%=ThumbnailUtils.thumbnails(d)[1]%>">
		</td>
		<td class="left indent10"><%=d.getNumber()%></td>
		<td class="left indent10"><%=d.getName()%></td>
		<td class="center"><%=PartHelper.manager.partTypeToDisplay(d)%></td>
		<td class="center"><%=d.getVersionIdentifier().getSeries().getValue() + "." + d.getIterationIdentifier().getSeries().getValue()%></td>
		<td class="center"><%=d.getLifeCycleState().getDisplay()%></td>
		<td class="center"><%=d.getModifierName()%></td>
		<td class="center"><%=d.getModifyTimestamp().toString().substring(0, 10)%></td>
		<td class="center"><%=IBAUtils.getStringValue(d, "COMPANY_CODE")%></td>
		<td class="center"><%=IBAUtils.getStringValue(d, "BRAND_CODE")%></td>
	</tr>
	<%
	}
	if (list.size() == 0) {
	%>
	<tr>
		<td class="center first" colspan="10">
			<font color="red">
				<b>관련부품이 없습니다.</b>
			</font>
		</td>
	</tr>
	<%
	}
	%>
</table>