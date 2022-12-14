<%@page import="platform.echange.eco.entity.ECO"%>
<%@page import="platform.echange.eco.entity.ECODTO"%>
<%@page import="platform.util.ContentUtils"%>
<%@page import="java.util.ArrayList"%>
<%@page import="platform.echange.ecn.entity.ECNDTO"%>
<%@page import="platform.echange.ecn.entity.ECN"%>
<%@page import="platform.util.CommonUtils"%>
<%@page import="wt.doc.WTDocument"%>
<%@page import="platform.doc.entity.DocumentDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String oid = (String) request.getParameter("per");
ECN ecn = (ECN) CommonUtils.persistable(oid);
ECNDTO dto = new ECNDTO(ecn);
%>
<table class="view-table obj-view">
	<colgroup>
		<col width="100">
		<col width="800">
		<col width="100">
		<col width="800">
	</colgroup>
	<tr>
		<th>ECN 번호</th>
		<td><%=dto.getNumber()%></td>
		<th>ECN 명</th>
		<td><%=dto.getName()%></td>
	</tr>
	<tr>
		<th>통보유형</th>
		<td><%=dto.getNotiTypeNm()%></td>
		<th>적용시점</th>
		<td><%=dto.getApplyTimeNm()%></td>
	</tr>
	<tr>
		<th>적용예정일</th>
		<td><%=dto.getApplicationDate().toString().substring(0, 10)%></td>
		<th>사업장</th>
		<td><%=dto.getPlantNm()%></td>
	</tr>
	<tr>
		<th>회사</th>
		<td><%=dto.getCompanyNm()%></td>
		<th>브랜드</th>
		<td><%=dto.getBrandNm()%></td>
	</tr>
	<tr>
		<th>작성자</th>
		<td><%=dto.getCreator()%></td>
		<th>작성일자</th>
		<td><%=dto.getCreatedDate()%></td>
	</tr>
</table>

<!-- 첨부 파일 -->
<jsp:include page="/jsp/common/ref-file-view.jsp">
	<jsp:param value="<%=dto.getOid()%>" name="oid" />
</jsp:include>

<!-- 관련 ECO -->
<jsp:include page="/jsp/echange/ecn/ref-ecn-eco-view.jsp">
	<jsp:param value="<%=dto.getOid()%>" name="oid" />
</jsp:include>


<script type="text/javascript">
	$(function() {
		$("#tabs").bindTab({
			theme : "AXTabs",
			value : "2",
			overflow : "scroll", /* "visible" */
			options : [ {
				optionValue : "1",
				optionText : "기본정보",
			}, {
				optionValue : "2",
				optionText : "결재객체",
			}, {
				optionValue : "3",
				optionText : "첨부파일",
			}, {
				optionValue : "4",
				optionText : "관련ECO",
			} ],
			onchange : function(selectedObject, value) {
				if (value == "1") {
					$(".info-view").show();
					$(".obj-view").hide();
					$(".app-view").show();
					$(".tabNav").show();
					$(".br1").show();
					$(".file-view").hide();
					$(".eco-view").hide();
				} else if (value == "2") {
					$(".info-view").hide();
					$(".obj-view").show();
					$(".app-view").hide();
					$(".tabNav").hide();
					$(".br1").hide();
					$(".file-view").hide();
					$(".eco-view").hide();
				} else if (value == "3") {
					$(".info-view").hide();
					$(".obj-view").hide();
					$(".app-view").hide();
					$(".tabNav").hide();
					$(".br1").hide();
					$(".file-view").show();
					$(".eco-view").hide();
				} else if (value == "4") {
					$(".info-view").hide();
					$(".obj-view").hide();
					$(".app-view").hide();
					$(".tabNav").hide();
					$(".br1").hide();
					$(".file-view").hide();
					$(".eco-view").show();
				}
			},
		});
	})
</script>