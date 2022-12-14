<%@page import="platform.code.entity.BaseCode"%>
<%@page import="java.util.ArrayList"%>
<%@page import="platform.doc.service.DocumentHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import = "platform.raonk.entity.RaonkDTO" %>
<%
	RaonkDTO dto = (RaonkDTO) request.getAttribute("dto");
%>
<div class="header-title">
	<img src="/Windchill/jsp/images/home.png" class="home">
	<span>HOME</span>
	>
	<span>라온케이 템플릿</span>
	>
	<span>라온케이 템플릿 수정</span>
</div>

<table class="create-table">
	<colgroup>
		<col width="130">
		<col width="*">
	</colgroup>
<!-- 	<tr> -->
<!-- 		<th>라온케이분류</th> -->
<!-- 		<td colspan="3"> -->
<%-- 			<input type="text" class="AXInput w70p" readonly="readonly" value="<%=DocumentHelper.ROOT%>" name="location" id="location"> --%>
<!-- 		</td> -->
<!-- 	</tr> -->
	<tr>
		<th>라온케이 템플릿 명</th>
		<td colspan="3">
			<input type="text" class="AXInput w60p" name="name" value="<%= dto.getName()%>">
		</td>
	</tr>
	<tr>
		<th>내용</th>
		<td colspan="3">
			<div class="raonkEditor">
				<input type="hidden" name="contents" id="contents">
				<script type="text/javascript">
					var kEditorParam = {
						Id : "_editor",
						Width : "100%",
						Height : "840px",
						FocusInitObjId : "name",
						Runtimes : 'html5' // agent, html5
					};
					new RAONKEditor(kEditorParam);
				</script>
			</div>
		</td>
	</tr>
</table>

<table class="button-table">
	<tr>
		<td class="right">
			<button type="button" id="saveBtn">저장</button>
			<button type="button" id="closeBtn">닫기</button>
		</td>
	</tr>
</table>

<script type="text/javascript">
	$(function() {

		$("#closeBtn").click(function() {
			self.close();
		})

		$("#saveBtn").click(function() {
			if (!confirm("수정 하시겠습니까?")) {
				return false;
			}

			RAONKEDITOR.GetHtmlContents({
				type : 'htmlexwithdoctype',
				callback : function(paramObj) {
					$("#contents").val(btoa(unescape(encodeURIComponent(paramObj.strData))));
				}
			}, '_editor');

			var params = _data($("#form"));
			var url = _url("/raonk/modify");
			_call(url, params, function(data) {
				opener.load();
				self.close();
			}, "POST");
		})
				RAONKEDITOR.SetHtmlContents(decodeURIComponent(escape(window.atob("<%=dto.getContents()%>"))), "_editor");

		
		function RAONKEDITOR_CreationComplete(editorId) {
			$value = $("#contents");
			RAONKEDITOR.SetHtmlContents(decodeURIComponent(escape(window.atob($value.val()))), editorId);
		}
// 		_folder("location", "/Default/Document");
	})
</script>