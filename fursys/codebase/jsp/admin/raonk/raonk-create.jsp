<%@page import="platform.code.entity.BaseCode"%>
<%@page import="java.util.ArrayList"%>
<%@page import="platform.doc.service.DocumentHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="header-title">
	<img src="/Windchill/jsp/images/home.png" class="home">
	<span>HOME</span>
	>
	<span>라온케이 템플릿</span>
	>
	<span>라온케이 템플릿 등록</span>
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
			<input type="text" class="AXInput w60p" name="name">
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
			<button type="button" id="saveBtn">등록</button>
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
			if (!confirm("등록 하시겠습니까?")) {
				return false;
			}

			RAONKEDITOR.GetHtmlContents({
				type : 'htmlexwithdoctype',
				callback : function(paramObj) {
					$("#contents").val(btoa(unescape(encodeURIComponent(paramObj.strData))));
				}
			}, '_editor');

			var params = _data($("#form"));
			var url = _url("/raonk/create");
			_call(url, params, function(data) {
				opener.load();
				self.close();
			}, "POST");
		})
// 		_folder("location", "/Default/Document");
	})
</script>