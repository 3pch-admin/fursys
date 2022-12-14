<%@page import="platform.project.output.entity.OutputDTO"%>
<%@page import="wt.session.SessionHelper"%>
<%@page import="wt.org.WTUser"%>
<%@page import="platform.project.issue.entity.Solution"%>
<%@page import="platform.project.issue.entity.IssueDTO"%>
<%@page import="platform.util.CommonUtils"%>
<%@page import="platform.code.entity.BaseCode"%>
<%@page import="java.util.ArrayList"%>
<%@page import="platform.doc.service.DocumentHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
OutputDTO dto = (OutputDTO) request.getAttribute("dto");
%>
<!-- hidden value -->
<input type="hidden" name="oid" value="<%=dto.getOid()%>">
<div class="header-title">
	<img src="/Windchill/jsp/images/home.png" class="home">
	<span>HOME</span>
	>
	<span>프로젝트 관리</span>
	>
	<span>산출물 정보</span>
</div>

<table class="create-table info-view">
	<colgroup>
		<col width="130">
		<col width="*">
	</colgroup>
	<tr>
		<th>위치</th>
		<td>
			<%=dto.getLocation()%>
		</td>
	</tr>
	<tr>
		<th>산출물 명</th>
		<td>
			<%=dto.getName()%>
		</td>
	</tr>
	<tr>
		<th>내용</th>
		<td colspan="3">
			<textarea rows="12" cols="" name="description" id="description" class="AXTextarea none" readonly="readonly"><%=dto.getDescription()%></textarea>
		</td>
	</tr>
</table>


<table class="button-table">
	<tr>
		<td class="right">
			<button type="button" id="deleteBtn">삭제</button>
			<button type="button" id="modifyBtn">수정</button>
			<button type="button" id="closeBtn">닫기</button>
		</td>
	</tr>
</table>

<script type="text/javascript">
	$(function() {

		$("#deleteBtn").click(function() {
			if(!confirm("삭제 하시겠습니까?")) {
				return false;
			}
			var params = new Object();
			var arr = new Array();
			arr.push($("input[name=oid]").val());
			params.list = arr;
			var url = _url("/output/delete");
			_call(url, params, function(data) {
				opener.grid();
				self.close();
			}, "POST");
		})
		
		$("#closeBtn").click(function() {
			self.close();
		})
		
		$("#modifyBtn").click(function() {
			document.location.href = "/Windchill/platform/output/modify?oid=<%=dto.getOid()%>";
		})
	})
</script>