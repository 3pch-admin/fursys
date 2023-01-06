<%@page import="platform.project.task.entity.Task"%>
<%@page import="platform.util.CommonUtils"%>
<%@page import="platform.code.entity.BaseCode"%>
<%@page import="java.util.ArrayList"%>
<%@page import="platform.doc.service.DocumentHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String oid = (String) request.getAttribute("oid");
Task t = (Task) CommonUtils.persistable(oid);
String m = "";
if (t.getProject() != null) {
	m = "프로젝트";
}

if (t.getTemplate() != null) {
	m = "템플릿";
}
%>
<!-- hidden value -->
<div class="header-title">
	<img src="/Windchill/jsp/images/home.png" class="home">
	<span>HOME</span>
	>
	<span><%=m%>
		관리
	</span>
	>
	<span>산출물 정의</span>
</div>

<table class="create-table">
	<colgroup>
		<col width="130">
		<col width="*">
	</colgroup>
	<tr>
		<th>위치<span class="imp">*</span></th>
		<td>
			<input type="text" class="AXInput w60p" name="location" id="location" readonly="readonly">
		</td>
	</tr>
	<tr>
		<th>
			산출물 명
			<span class="imp">*</span>
		</th>
		<td>
			<input type="text" class="AXInput w60p" name="name" autofocus="autofocus">
		</td>
	</tr>
	<tr>
		<th>설명</th>
		<td>
			<textarea rows="12" cols="" name="description" id="description" class="AXTextarea"></textarea>
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
			
			var location = $("input[name=location]");
			var name = $("input[name=name]");
			
			if(location.val() == "") {
				alert("위치를 선택하세요.");
				var url = "/Windchill/platform/util/folder?location=/Default/문서&target=location&context=DOCUMENT";
				_popup(url, 600, 500, "n");
				return false;
			}
			
			if(name.val() == "") {
				alert("산출물 명을 입력하세요.");
				name.focus();
				return false;
			}
			
			
			if (!confirm("등록 하시겠습니까?")) {
				return false;
			}

			var params = _data($("#form"));
			var url = _url("/output/create");
			params.oid = "<%=oid%>";
			_call(url, params, function(data) {
				opener.location.reload();
				self.close();
			}, "POST");
		})
		
		$("input[name=name]").keydown(function(e){
			if(e.keyCode ==13 ){
				$("#saveBtn").click();
			}
		});
		
		$("input").checks();
		_folder("location", "/Default/문서", "DOCUMENT");
	})
</script>