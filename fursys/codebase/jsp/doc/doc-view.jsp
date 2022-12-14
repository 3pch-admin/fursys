<%@page import="platform.doc.entity.DocumentDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
DocumentDTO dto = (DocumentDTO) request.getAttribute("dto");
%>
<div class="header-title">
	<img src="/Windchill/jsp/images/home.png" class="home">
	<span>HOME</span>
	>
	<span>기술문서 관리</span>
	>
	<span>기술문서 정보</span>
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
<!-- 	<tr> -->
<!-- 		<th>저장위치</th> -->
<!-- 		<td colspan="3"> -->
<%-- 			<input type="hidden" name="oid" value="<%=dto.getOid()%>"> --%>
<%-- 			<%=dto.getLocation()%></td> --%>
<!-- 	</tr> -->
	<tr>
		<th>문서번호</th>
		<td><%=dto.getNumber()%></td>
		<th>문서명</th>
		<td><%=dto.getName()%></td>
	</tr>
	<tr>
		<th>상태</th>
		<td><%=dto.getState()%></td>
		<th>버전</th>
		<td><%=dto.getVersion()%></td>
	</tr>
	<tr>
		<th>회사</th>
		<td><%=dto.getCompanyNm()%></td>
		<th>브랜드</th>
		<td><%=dto.getBrandNm()%></td>
	</tr>
	<tr>
		<th>내용</th>
		<td colspan="3">
			<div class="raonkEditor none">
				<script type="text/javascript">
					var kEditorParam = {
						Id : "editor",
						Width : "100%",
						Height : "650px",
						Runtimes : 'html5', // agent, html5
						Mode : 'view'
					};
					new RAONKEditor(kEditorParam);
				</script>
			</div>
		</td>
	</tr>
	<tr>
		<th>작성자</th>
		<td><%=dto.getCreator()%></td>
		<th>작성일자</th>
		<td><%=dto.getCreatedDate()%></td>
	</tr>
	<tr>
		<th>수정자</th>
		<td><%=dto.getModifier()%></td>
		<th>수정일자</th>
		<td><%=dto.getModifiedDate()%></td>
	</tr>
</table>

<!-- 첨부 파일 -->
<jsp:include page="/jsp/common/ref-file-view.jsp">
	<jsp:param value="<%=dto.getOid()%>" name="oid" />
</jsp:include>

<!-- 버전 이력 -->
<jsp:include page="/jsp/doc/doc-history.jsp">
	<jsp:param value="<%=dto.getOid()%>" name="oid" />
</jsp:include>

<!-- 관련 부품 -->
<jsp:include page="/jsp/doc/ref-doc-part-view.jsp"></jsp:include>

<!-- 결재 이력 -->
<jsp:include page="/jsp/approval/approval-history.jsp">
	<jsp:param value="<%=dto.getOid()%>" name="oid" />
</jsp:include>

<table class="button-table">
	<tr>
		<td class="right">
			<%
				if(dto.isModify()) {
			%>
			<button type="button" id="modifyBtn">수정</button>
			<%
				}
			%>
			<%
				if(dto.isRevise()) {
			%>
			<button type="button" id="reviseBtn">개정</button>
			<%
				}
			%>
			<%
				if(dto.isDelete()) {
			%>
			<button type="button" id="deleteBtn">삭제</button>
			<%
				}
			%>
			<button type="button" id="closeBtn">닫기</button>
		</td>
	</tr>
</table>
<script type="text/javascript">
	$(function() {

		$("#reviseBtn").click(function() {

			if (!confirm("개정 하시겠습니까?")) {
				return false;
			}

			var params = _data($("#form"));
			var url = _url("/doc/revise");
			_call(url, params, function(data) {
				opener.load();
				self.close();
			}, "POST");
		})

		$("#deleteBtn").click(function() {

			if (!confirm("삭제 하시겠습니까?")) {
				return false;
			}

			var params = _data($("#form"));
			var url = _url("/doc/delete");
			_call(url, params, function(data) {
				opener.load();
				self.close();
			}, "POST");
		})
		
		$(window).resize(function() {
			AUIGrid.resize("#part_grid_wrap");
		})

		$("#modifyBtn").click(function() {
// 			var oid = $("input[name=oid]").val();
			var url = _url("/doc/modify", "<%=dto.getOid()%>");
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
				optionText : "첨부파일",
			}, {
				optionValue : "3",
				optionText : "버전이력",
			}, {
				optionValue : "4",
				optionText : "관련부품",
			}, {
				optionValue : "5",
				optionText : "결재이력"
			} ],
			onchange : function(selectedObject, value) {
				if (value == "1") {
					$(".info-view").show();
					$(".file-view").hide();
					$(".history-view").hide();
					$("#part_grid_wrap").hide();
					$(".app-view").hide();
				} else if (value == "2") {
					$(".info-view").hide();
					$(".file-view").show();
					$(".history-view").hide();
					$("#part_grid_wrap").hide();
					$(".app-view").hide();
				} else if (value == "3") {
					$(".info-view").hide();
					$(".file-view").hide();
					$(".history-view").show();
					$("#part_grid_wrap").hide();
					$(".app-view").hide();
				} else if (value == "4") {
					$(".info-view").hide();
					$(".file-view").hide();
					$(".history-view").hide();
					$("#part_grid_wrap").show();
					AUIGrid.setGridData("#part_grid_wrap", <%=dto.getPartJson()%>);
					AUIGrid.resize("#part_grid_wrap");
					$(".app-view").hide();
				} else if (value == "5") {
					$(".info-view").hide();
					$(".file-view").hide();
					$(".history-view").hide();
					$("#part_grid_wrap").hide();
					$(".app-view").show();
				}
			},
		});
		RAONKEDITOR.SetHtmlContents(decodeURIComponent(escape(window.atob("<%=dto.getContent()%>"))), "editor");
	})
</script>