<%@page import="platform.echange.eco.entity.ECODTO"%>
<%@page import="platform.echange.ecn.entity.ECN"%>
<%@page import="platform.util.CommonUtils"%>
<%@page import="platform.echange.eco.entity.ECO"%>
<%@page import="platform.util.ContentUtils"%>
<%@page import="java.util.ArrayList"%>
<%@page import="platform.echange.ecn.entity.ECNDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
ECNDTO dto = (ECNDTO) request.getAttribute("dto");
%>
<div class="header-title">
	<img src="/Windchill/jsp/images/home.png" class="home">
	<span>HOME</span>
	>
	<span>설계변경</span>
	>
	<span> ECN 정보 </span>
</div>

<div id="tabs"></div>
<br>
<table class="view-table info-view">
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
		<th>실제적용일</th>
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
	<tr>
		<th>
			관련
			<br>
			ECO
		</th>
		<td colspan="3" class="inner">
			<br>
			<table class="view-table">
				<colgroup>
					<col width="150">
					<col width="*">
					<col width="120">
					<col width="100">
					<col width="100">
					<col width="200">
					<col width="140">
					<col width="100">
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
<!-- 					<th>예상적용일</th> -->
					<th>작성자</th>
					<th>작성일시</th>
					<th>상태</th>
				</tr>
				<%
				ECN ecn = (ECN) CommonUtils.persistable(dto.getOid());
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
<%-- 					<td class="center"><%=data.getExpectationTime()%></td> --%>
					<td class="center"><%=data.getCreator()%></td>
					<td class="center"><%=data.getCreatedDate()%></td>
					<td class="center"><%=data.getState()%></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<th>첨부파일</th>
		<td colspan="3" class="inner">
			<br>
			<table class="view-table">
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
				ArrayList<String[]> secondary = ContentUtils.getSecondary(dto.getOid());
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
				if (secondary.size() == 0) {
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
		</td>
	</tr>
</table>

<!-- 관련 부품 -->
<jsp:include page="/jsp/echange/ecn/ref-ecn-part-view.jsp">
	<jsp:param value="<%=dto.getOid()%>" name="oid" />
</jsp:include>

<!-- 관련 MBOM -->
<jsp:include page="/jsp/echange/ecn/ref-ecn-mbom-view.jsp">
	<jsp:param value="<%=dto.getOid()%>" name="oid" />
</jsp:include>

<!-- 결재 이력 -->
<jsp:include page="/jsp/approval/approval-history.jsp">
	<jsp:param value="<%=dto.getOid()%>" name="oid" />
</jsp:include>

<!-- 결재 모든 이력 -->
<%-- <jsp:include page="/jsp/approval/approval-all-history.jsp"> --%>
<%-- 	<jsp:param value="<%=dto.getOid()%>" name="oid" /> --%>
<%-- </jsp:include> --%>

<table class="button-table">
	<tr>
		<td class="right">
			<button type="button" id="modifyBtn">수정</button>
			<button type="button" id="deleteBtn">삭제</button>
			<button type="button" id="closeBtn">닫기</button>
		</td>
	</tr>
</table>

<script type="text/javascript">
	$(function() {

		$("#closeBtn").click(function() {
			self.close();
		})

		$("#tabs").bindTab({
			theme : "AXTabs",
			value : "1",
			overflow : "scroll", /* "visible" */
			options : [ {
				optionValue : "1",
				optionText : "기본정보",
			}, {
				optionValue : "2",
				optionText : "관련부품",
			}, {
				optionValue : "3",
				optionText : "MBOM",
			}, {
				optionValue : "4",
				optionText : "결재이력"
			}, {
				optionValue : "5",
				optionText : "전체 결재이력"
			} ],
			onchange : function(selectedObject, value) {
				if (value == "1") {
					$(".info-view").show();
					$(".part-view").hide();
					$(".mbom").hide();
					$(".mbom-search").hide();
					$(".app-view").hide();
					$(".tabNav").hide();
					$(".all-app-view").hide();
				} else if (value == "2") {
					$(".info-view").hide();
					$(".part-view").show();
					$(".mbom").hide();
					$(".mbom-search").hide();
					$(".app-view").hide();
					$(".tabNav").hide();
					$(".all-app-view").hide();
				} else if (value == "3") {
					$(".info-view").hide();
					$(".part-view").hide();
					$(".mbom").show();
					$(".mbom-search").show();
					_selector("mcolor");
					$(".app-view").hide();
					$(".tabNav").hide();
					$(".all-app-view").hide();
				} else if (value == "4") {
					$(".info-view").hide();
					$(".part-view").hide();
					$(".mbom").hide();
					$(".mbom-search").hide();
					$(".app-view").show();
					$(".tabNav").show();
					$(".all-app-view").hide();
				} else if (value == "5") {
					$(".info-view").hide();
					$(".part-view").hide();
					$(".mbom").hide();
					$(".mbom-search").hide();
					$(".app-view").hide();
					$(".tabNav").hide();
					$(".all-app-view").show();
				}
			},
		});
	})
</script>