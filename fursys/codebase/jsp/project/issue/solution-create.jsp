<%@page import="platform.util.CommonUtils"%>
<%@page import="platform.code.entity.BaseCode"%>
<%@page import="java.util.ArrayList"%>
<%@page import="platform.doc.service.DocumentHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String oid = (String) request.getAttribute("oid");
%>
<!-- hidden value -->
<div class="header-title">
	<img src="/Windchill/jsp/images/home.png" class="home">
	<span>HOME</span>
	>
	<span>프로젝트 관리</span>
	>
	<span>해결방안 등록</span>
</div>

<table class="create-table">
	<colgroup>
		<col width="130">
		<col width="*">
	</colgroup>
	<tr>
		<th>제목</th>
		<td>
			<input type="text" class="AXInput w60p" name="name">
		</td>
	</tr>
	<tr>
		<th>해결내용</th>
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

			if (!confirm("등록 하시겠습니까?")) {
				return false;
			}

			var params = _data($("#form"));
			var url = _url("/solution/create");
			params.oid = "<%=oid%>";
			_call(url, params, function(data) {
				opener.load();
				self.close();
			}, "POST");
		})
	})
</script>