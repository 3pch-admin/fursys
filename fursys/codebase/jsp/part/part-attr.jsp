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
WTPart part = (WTPart) CommonUtils.persistable(oid);
%>
<table class="view-table attr-view close">
	<colgroup>
		<col width="200">
		<col width="300">
		<col width="200">
		<col width="300">
	</colgroup>
	<tr>
	<tr>
		<th>색상 (COLOR)</th>
		<td><%=IBAUtils.getAttrValues(part, "s", "COLOR")%></td>
		<th>중량 (PRO_MP_MASS)</th>
		<td><%=IBAUtils.getAttrValues(part, "f", "PRO_MP_MASS")%></td>
	</tr>
	<tr>
		<th>비중 (SPECIFIC_GRAVITY)</th>
		<td><%=IBAUtils.getAttrValues(part, "f", "SPECIFIC_GRAVITY")%></td>
		<th>척도 (SCALE)</th>
		<td><%=IBAUtils.getAttrValues(part, "s", "SCALE")%></td>
	</tr>
	<tr>
		<th>부품명 (PART_NAME)</th>
		<td><%=IBAUtils.getAttrValues(part, "s", "PART_NAME")%></td>
		<th>제품명 (ITEM_NAME)</th>
		<td><%=IBAUtils.getAttrValues(part, "s", "ITEM_NAME")%></td>
	</tr>
	<tr>
		<th>건명 (PROJECT_NAME)</th>
		<td><%=IBAUtils.getAttrValues(part, "s", "PROJECT_NAME")%></td>
		<th>작성자 (DRAWN_BY)</th>
		<td><%=IBAUtils.getAttrValues(part, "s", "DRAWN_BY")%></td>
	</tr>
	<tr>
		<th>작성일 (DRAWN_DATE)</th>
		<td><%=IBAUtils.getAttrValues(part, "s", "DRAWN_DATE")%></td>
		<th>승인자 (APPROVED_BY)</th>
		<td><%=IBAUtils.getAttrValues(part, "s", "APPROVED_BY")%></td>
	</tr>
	<tr>
		<th>승인일 (APPROVED_DATE)</th>
		<td><%=IBAUtils.getAttrValues(part, "s", "APPROVED_DATE")%></td>
		<th>부품번호 (PART_NO)</th>
		<td><%=IBAUtils.getAttrValues(part, "s", "PART_NO")%></td>
	</tr>
	<tr>
		<th>도면번호 (DRAWING_NO)</th>
		<td><%=IBAUtils.getAttrValues(part, "s", "DRAWING_NO")%></td>
		<th>BOM 포함여부 (BOM)</th>
		<td><%=IBAUtils.getAttrValues(part, "b", "BOM")%></td>
	</tr>
	<tr>
		<th>리비전 (PTC_WM_REVISION)</th>
		<td><%=IBAUtils.getAttrValues(part, "s", "PTC_WM_REVISION")%></td>
		<th>규격 (DESCRIPTION)</th>
		<td><%=IBAUtils.getAttrValues(part, "s", "DESCRIPTION")%></td>
	</tr>
	<tr>
		<th>부품유형 (PART_TYPE)</th>
		<td><%=IBAUtils.getAttrValues(part, "s", "PART_TYPE")%></td>
		<th>G코드 생성여부 (IM_CAM_GCODE)</th>
		<td><%=IBAUtils.getAttrValues(part, "b", "IM_CAM_GCODE")%></td>
	</tr>
	<tr>
		<th>재질정보 (MATERIAL)</th>
		<td><%=IBAUtils.getAttrValues(part, "s", "MATERIAL")%></td>
		<th>가공정보 (PROCESS)</th>
		<td><%=IBAUtils.getAttrValues(part, "s", "PROCESS")%></td>
	</tr>
	<tr>
		<th>표면처리 (FINISH)</th>
		<td><%=IBAUtils.getAttrValues(part, "s", "FINISH")%></td>
		<th>재질정보 코드 (MATERIAL_CODE)</th>
		<td><%=IBAUtils.getAttrValues(part, "s", "MATERIAL_CODE")%></td>
	</tr>
	<tr>
		<th>가공정보 코드 (PROCESS_CODE)</th>
		<td><%=IBAUtils.getAttrValues(part, "s", "PROCESS_CODE")%></td>
		<th>표면처리 코드 (FINISH_COD)</th>
		<td><%=IBAUtils.getAttrValues(part, "s", "FINISH_COD")%></td>
	</tr>
	<tr>
		<th>무늬결 방향 (DT_WOODGRAIN)</th>
		<td><%=IBAUtils.getAttrValues(part, "s", "DT_WOODGRAIN")%></td>
		<th>BOM 소요량 (BOM_REQUIREMENT)</th>
		<td><%=IBAUtils.getAttrValues(part, "s", "BOM_REQUIREMENT")%></td>
	</tr>
	<tr>
		<th>BOM 단위 (BOM_UNIT)</th>
		<td><%=IBAUtils.getAttrValues(part, "s", "BOM_UNIT")%></td>
		<th>조립철물 여부 (PACK_TYPE)</th>
		<td><%=IBAUtils.getAttrValues(part, "s", "PACK_TYPE")%></td>
	</tr>
</table>