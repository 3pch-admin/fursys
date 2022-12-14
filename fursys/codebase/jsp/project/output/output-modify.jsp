<%@page import="platform.util.StringUtils"%>
<%@page import="platform.project.output.entity.Output"%>
<%@page import="platform.project.task.entity.Task"%>
<%@page import="platform.util.CommonUtils"%>
<%@page import="platform.code.entity.BaseCode"%>
<%@page import="java.util.ArrayList"%>
<%@page import="platform.doc.service.DocumentHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String oid = (String) request.getAttribute("oid");
Output output = (Output) request.getAttribute("output");
String m = "";
if (output.getProject() != null) {
	m = "프로젝트";
}

if (output.getTemplate() != null) {
	m = "템플릿";
}
%>
<!-- hidden value -->
<input type="hidden" name="oid" value="<%=oid %>">
<div class="header-title">
	<img src="/Windchill/jsp/images/home.png" class="home">
	<span>HOME</span>
	>
	<span><%=m%>
		관리
	</span>
	>
	<span>산출물 수정</span>
</div>

<table class="create-table">
	<colgroup>
		<col width="130">
		<col width="*">
	</colgroup>
	<tr>
		<th>위치<span class="imp">*</span></th>
		<td>
			<input type="text" class="AXInput w60p" name="location" id="location" readonly="readonly" value="<%=output.getLocation() %>">
		</td>
	</tr>
	<tr>
		<th>
			산출물 명
			<span class="imp">*</span>
		</th>
		<td>
			<input type="text" class="AXInput w60p" name="name" value="<%=output.getName()%>">
		</td>
	</tr>
	<tr>
		<th>설명</th>
		<td>
			<textarea rows="12" cols="" name="description" id="description" class="AXTextarea"><%=StringUtils.convertToStr(output.getDescription(), "") %></textarea>
		</td>
	</tr>
</table>

<table class="button-table">
	<tr>
		<td class="right">
			<button type="button" id="modifyBtn">수정</button>
			<button type="button" id="closeBtn">닫기</button>
		</td>
	</tr>
</table>

<script type="text/javascript">
	$(function() {

		$("#closeBtn").click(function() {
			self.close();
		})

		$("#modifyBtn").click(function() {
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
			
			
			if (!confirm("수정 하시겠습니까?")) {
				return false;
			}

			var params = _data($("#form"));
			var url = _url("/output/modify");
			_call(url, params, function(data) {
				opener.grid();
				self.close();
			}, "POST");
		})
		$("input").checks();
		_folder("location", "/Default/문서", "DOCUMENT");
	})
</script>