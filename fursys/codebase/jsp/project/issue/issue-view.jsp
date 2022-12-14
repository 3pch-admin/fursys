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
IssueDTO dto = (IssueDTO) request.getAttribute("dto");
Solution solution = dto.getSolution();
WTUser sessionUser = (WTUser) SessionHelper.manager.getPrincipal();
boolean isManager = sessionUser.getName().equals(dto.getIssue().getManager().getName());
%>
<!-- hidden value -->
<div id="tabs"></div>
<br>
<div class="header-title">
	<img src="/Windchill/jsp/images/home.png" class="home">
	<span>HOME</span>
	>
	<span>프로젝트 관리</span>
	>
	<span>이슈 정보</span>
</div>

<table class="create-table info-view">
	<colgroup>
		<col width="130">
		<col width="*">
		<col width="130">
		<col width="*">
	</colgroup>
	<tr>
		<th>이슈 명</th>
		<td>
			<%=dto.getDescription()%>
		</td>
		<th>상태</th>
		<td><%=dto.getState()%></td>
	</tr>
	<tr>
		<th>담당자</th>
		<td><%=dto.getManager()%></td>
		<th>이슈타입</th>
		<td><%=dto.getIssueTypeNm()%>
	</tr>
	<tr>
		<th>작성자</th>
		<td><%=dto.getCreator()%></td>
		<th>작성일</th>
		<td><%=dto.getCreatedDate()%></td>
	</tr>
	<tr>
		<th>내용</th>
		<td colspan="3">
			<textarea rows="12" cols="" name="description" id="description" class="AXTextarea none" readonly="readonly"><%=dto.getDescription()%></textarea>
		</td>
	</tr>
</table>


<!-- 해결 -->
<jsp:include page="/jsp/project/issue/issue-solution.jsp">
	<jsp:param value="<%=dto.getOid() %>" name="oid"/>
</jsp:include>

<table class="button-table">
	<tr>
		<td class="right">
			<%
				if(CommonUtils.isAdmin() && !dto.isComplete()) {
			%>
			<button type="button" class="issueDeleteBtn">삭제</button>
			<%
				if(dto.getSolution() != null) {
			%>
			<button type="button" class="solutionDeleteBtn close">삭제</button>
			<%
				}
				}
			%>
			<%
				if(!dto.isComplete()) {
			%>
			<button type="button" id="modifyBtn">수정</button>
			<%
				}
			%>
			<button type="button" id="closeBtn">닫기</button>
		</td>
	</tr>
</table>

<script type="text/javascript">
	$(function() {
		
		$(".issueDeleteBtn").click(function() {
			if(!confirm("이슈를 삭제 하시겠습니까?")) {
				return false;
			}
			var url = _url("/issue/delete", "<%=dto.getOid()%>");
			var params = new Object();
			_call(url, params, function(data) {
				opener.load();
				self.close();
			}, "GET");
		})
		
		$(".solutionDeleteBtn").click(function() {
			if(!confirm("해결내용을 삭제 하시겠습니까?")) {
				return false;
			}
			var url = _url("/solution/delete", "<%=dto.getSolution() != null ? dto.getSolution().getPersistInfo().getObjectIdentifier().getStringValue() : ""%>");
			var params = new Object();
			_call(url, params, function(data) {
				document.location.reload();
			}, "GET");
		})
		
		$("#closeBtn").click(function() {
			self.close();
		})
		
		$("#modifyBtn").click(function() {
			document.location.href = "/Windchill/platform/issue/modify?oid=<%=dto.getOid()%>";
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
				optionText : "해결내용",
			} ],
			onchange : function(selectedObject, value) {
				if (value == "1") {
					$(".info-view").show();
					$(".solution-view").hide();
					$(".issueDeleteBtn").show();
					$(".solutionDeleteBtn").hide();
				} else if (value == "2") {
					$(".info-view").hide();
					$(".solution-view").show();
					$(".issueDeleteBtn").hide();
					$(".solutionDeleteBtn").show();
				}
			},
		});
	})
</script>