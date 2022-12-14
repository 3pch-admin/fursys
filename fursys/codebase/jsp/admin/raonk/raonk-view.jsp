<%@page import="platform.raonk.entity.RaonkDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
RaonkDTO dto = (RaonkDTO) request.getAttribute("dto");
%>
<div class="header-title">
	<img src="/Windchill/jsp/images/home.png" class="home">
	<span>HOME</span>
	>
	<span>라온케이 템플릿</span>
	>
	<span>라온케이 템플릿 정보</span>
</div>

<div id="tabs"></div>
<br>
<table class="view-table info-view">
	<colgroup>
		<col width="130">
		<col width="600">
		<col width="130">
		<col width="600">
	</colgroup>
<!-- 	<tr> -->
<!-- 		<th>저장위치</th> -->
<!-- 		<td colspan="3"> -->
<%-- 			<input type="hidden" name="oid" value="<%=dto.getOid()%>"> --%>
<%-- 			<%=dto.getLocation()%></td> --%>
<!-- 	</tr> -->
	<tr>
		<th>라온케이 템플릿 명</th>
		<td colspan ="3"><%=dto.getName()%></td>
	</tr>
	<tr>
		<th>내용</th>
		<td colspan="3">
			<div class="raonkEditor">
				<script type="text/javascript">
					var kEditorParam = {
						Id : "editor",
						Width : "100%",
						Height : "780px",
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
</table>

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
		$("#deleteBtn").click(function() {

			if (!confirm("삭제 하시겠습니까?")) {
				return false;
			}

			var params = _data($("#form"));
			var url = _url("/raonk/delete");
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
			var url = _url("/raonk/modify", "<%=dto.getOid()%>");
			document.location.href = url;
		})

		$("#closeBtn").click(function() {
			self.close();
		})
	});
		RAONKEDITOR.SetHtmlContents(decodeURIComponent(escape(window.atob("<%=dto.getContents()%>"))), "editor");
</script>