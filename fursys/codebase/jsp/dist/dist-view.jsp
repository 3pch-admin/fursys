<%@page import="platform.util.CommonUtils"%>
<%@page import="wt.fc.PersistenceHelper"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="platform.code.service.BaseCodeHelper"%>
<%@page import="platform.dist.entity.DistDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
DistDTO dto = (DistDTO) request.getAttribute("dto");
// ECO eco = (ECO)CommonUtils.persistable(dto.getOid());
// QueryResult result = PersistenceHelper.manager.navigate(eco, "header", MBOMHeaderECOLink.class);
// out.println("size="+result.size());
%>
<div class="header-title">
	<img src="/Windchill/jsp/images/home.png" class="home">
	<span>HOME</span>
	>
	<span>도면 조회 및 배포</span>
	>
	<span>도면 배포 정보</span>
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
		<th>배포 번호</th>
		<td colspan="3"><%=dto.getNumber()%></td>
	</tr>
	<tr>
		<th>발신자</th>
		<td><%=dto.getCreator()%></td>
		<th>수신자</th>
		<td></td>
		</tr>
		<tr>
		<th>내용</th>
		<td colspan="3">
			<div class="raonkEditor">
				<script type="text/javascript">
					var kEditorParam = {
						Id : "description",
						Width : "100%",
						Height : "200px",
						Runtimes : 'html5', // agent, html5
						Mode : 'view'
					};
					new RAONKEditor(kEditorParam);
				</script>
			</div>
		</td>
	</tr>
</table>

<!-- 첨부 파일 -->
<jsp:include page="/jsp/common/ref-file-view.jsp">
	<jsp:param value="<%=dto.getOid()%>" name="oid" />
</jsp:include>

<!-- 설계변경정보 -->
<jsp:include page="">
<jsp:param value="<%=dto.getOid() %>" name="oid"/>
</jsp:include>

<!-- Part 정보 -->
<jsp:include page="">
<jsp:param value="<%=dto.getOid() %>" name="oid"/>
</jsp:include>

<!-- 다운로드 이력 -->
<jsp:include page="">
<jsp:param value="<%=dto.getOid() %>" name="oid"/>
</jsp:include>

<!-- 관련 문서 --> 
<%-- <jsp:include page="/jsp/echange/eco/ref-eco-doc-view.jsp"> --%>
<%-- 	<jsp:param value="<%=dto.getOid()%>" name="oid" /> --%>
<%-- </jsp:include> --%>

<!-- 관련 부품 -->
<%-- <jsp:include page="/jsp/echange/eco/ref-eco-part-view.jsp"></jsp:include> --%>

 <!-- 관련 EBOM -->
<%-- <jsp:include page="/jsp/echange/eco/ref-eco-ebom-view.jsp"> --%>
<%-- 	<jsp:param value="<%=dto.getOid()%>" name="oid" /> --%>
<%-- </jsp:include> --%>


<!-- 관련 MBOM -->
<%-- <jsp:include page="/jsp/echange/eco/ref-eco-mbom-view.jsp"> --%>
<%-- 	<jsp:param value="<%=dto.getOid()%>" name="oid" /> --%>
<%-- </jsp:include> --%>


<!-- 결재 이력 -->
<%-- <jsp:include page="/jsp/approval/approval-history.jsp"> --%>
<%-- 	<jsp:param value="<%=dto.getOid()%>" name="oid" /> --%>
<%-- </jsp:include> --%>

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
				optionText : "첨부파일",
			}, 
// 			{
// 				optionValue : "3",
// 				optionText : "관련부품",
// 			},
			{
				optionValue : "6",
				optionText : "관련문서",
			}, {
				optionValue : "7",
				optionText : "결재이력"
			} ],
			onchange : function(selectedObject, value) {
				if (value == "1") {
					$(".info-view").show();
					$(".file-view").hide();
					$("#part_grid_wrap").hide();
					$(".ebom").hide();
					$(".ebom-search").hide();
					$(".ebr").hide();
					$(".mbom").hide();
					$(".mbom-search").hide();
					$("#doc_grid_wrap").hide();
					$(".app-view").hide();
				} else if (value == "2") {
					$(".info-view").hide();
					$(".file-view").show();
					$("#part_grid_wrap").hide();
					$(".ebom").hide();
					$(".ebom-search").hide();
					$(".ebr").hide();
					$(".mbom").hide();
					$(".mbom-search").hide();
					$("#doc_grid_wrap").hide();
					$(".app-view").hide();
				} else if (value == "3") {
					$(".info-view").hide();
					$(".file-view").hide();
					$("#part_grid_wrap").show();
<%-- 					AUIGrid.setGridData("#part_grid_wrap", <%=dto.getPartJson()%>); --%>
					AUIGrid.resize("#part_grid_wrap");
					$(".ebom").hide();
					$(".ebom-search").hide();
					$(".ebr").hide();
					$(".mbom").hide();
					$(".mbom-search").hide();
					$("#doc_grid_wrap").hide();
					$(".app-view").hide();
				} else if (value == "4") {
					$(".info-view").hide();
					$(".file-view").hide();
					$("#part_grid_wrap").hide();
					$(".ebom").show();
					$(".ebom-search").show();
					$(".ebr").show();
					_selector("ecolor");
					$(".mbom").hide();
					$(".mbom-search").hide();
					$("#doc_grid_wrap").hide();
					$(".app-view").hide();
				} else if (value == "5") {
					$(".info-view").hide();
					$(".file-view").hide();
					$("#part_grid_wrap").hide();
					$(".ebom").hide();
					$(".ebom-search").hide();
					$(".ebr").hide();
					$(".mbom").show();
					$(".mbom-search").show();
					$("#doc_grid_wrap").hide();
					$(".app-view").hide();
				} else if (value == "6") {
					$(".info-view").hide();
					$(".file-view").hide();
					$("#part_grid_wrap").hide();
					$(".ebom").hide();
					$(".ebom-search").hide();
					$(".ebr").hide();
					$(".mbom").hide();
					$(".mbom-search").hide();
					$("#doc_grid_wrap").show();
<%-- 					AUIGrid.setGridData("#doc_grid_wrap", <%=dto.getDocJson()%>); --%>
					AUIGrid.resize("#doc_grid_wrap");
					$(".app-view").hide();
				} else if (value == "7") {
					$(".info-view").hide();
					$(".file-view").hide();
					$("#part_grid_wrap").hide();
					$(".ebom").hide();
					$(".ebom-search").hide();
					$(".ebr").hide();
					$(".mbom").hide();
					$(".mbom-search").hide();
					$("#doc_grid_wrap").hide();
					$(".app-view").show();
				}
			},
		});
		
		
		$(window).resize(function() {
			AUIGrid.resize("#doc_grid_wrap");
			AUIGrid.resize("#part_grid_wrap");
		})
		RAONKEDITOR.SetHtmlContents(decodeURIComponent(escape(window.atob("<%=dto.getDescription()%>"))), "description");
	})
</script>