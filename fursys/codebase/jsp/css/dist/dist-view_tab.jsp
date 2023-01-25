<%@page import="platform.util.CommonUtils"%>
<%@page import="wt.fc.PersistenceHelper"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="platform.code.service.BaseCodeHelper"%>
<%@page import="platform.dist.entity.DistDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
DistDTO dto = (DistDTO) request.getAttribute("dto");
// ECO eco = (ECO)CommonUtils.persistable(dto.getOid());
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
		<td><%=dto.getNumber()%></td>
		<th>다운로드 기간</th>
		<td><%=dto.getDuration()%>&nbsp;주</td>
	</tr>
	<tr>
		<th>작성자</th>
		<td><%=dto.getCreator()%></td>
		<th>-</th>
		<td>-</td>
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

<!-- 배포처 -->
<jsp:include page="/jsp/dist/ref-dist-distributor-view.jsp">
	<jsp:param value="<%=dto.getOid()%>" name="oid" />
</jsp:include>

<!-- 결재 이력 -->
<jsp:include page="/jsp/approval/approval-history.jsp">
	<jsp:param value="<%=dto.getOid()%>" name="oid" />
</jsp:include>

<!-- 배포 정보  -->
<jsp:include page="/jsp/dist/ref-dist-part-view.jsp">
	<jsp:param value="<%=dto.getOid()%>" name="oid" />
</jsp:include>

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

		$("#modifyBtn").click(function() {
			var url = _url("/dist/modify", "<%=dto.getOid()%>");
			document.location.href = url;
		})

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
				optionText : "배포처",
			}, 
			{
				optionValue : "3",
				optionText : "배포도면",
			}, {
				optionValue : "4",
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
					$(".dist-part-view").hide();
					$(".ref_dist_distributor_grid_wrap").hide();
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
					$(".dist-part-view").hide();
					$(".ref_dist_distributor_grid_wrap").show();
					AUIGrid.resize("#ref_dist_distributor_grid_wrap");
				} else if (value == "3") {
					$(".info-view").hide();
					$(".file-view").hide();
					$("#part_grid_wrap").show();
					AUIGrid.resize("#part_grid_wrap");
					$(".ebom").hide();
					$(".ebom-search").hide();
					$(".ebr").hide();
					$(".mbom").hide();
					$(".mbom-search").hide();
					$("#doc_grid_wrap").hide();
					$(".app-view").hide();
					$(".ref_dist_distributor_grid_wrap").hide();
					$(".dist-part-view").show();
					AUIGrid.resize("#ref_dist_part_grid_wrap");
				} else if (value == "4") {
					$(".info-view").hide();
					$(".file-view").hide();
					$("#part_grid_wrap").hide();
					$(".ebom").show();
					$(".ebr").show();
					_selector("ecolor");
					$(".mbom").hide();
					$(".mbom-search").hide();
					$("#doc_grid_wrap").hide();
					$(".app-view").hide();
					$(".dist-part-view").hide();
					$(".ref_dist_distributor_grid_wrap").hide();
				} 
			},
		});
		
		
		$(window).resize(function() {
			AUIGrid.resize("#doc_grid_wrap");
			AUIGrid.resize("#part_grid_wrap");
			AUIGrid.resize("#ref_dist_part_grid_wrap");
			AUIGrid.resize("#ref_dist_distributor_grid_wrap");
			
		})
		RAONKEDITOR.SetHtmlContents(decodeURIComponent(escape(window.atob("<%=dto.getDescription()%>"))), "description");
	})
</script>