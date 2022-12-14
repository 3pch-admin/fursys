<%@page import="platform.approval.service.ApprovalHelper"%>
<%@page import="platform.approval.entity.ApprovalLine"%>
<%@page import="java.util.ArrayList"%>
<%@page import="platform.util.StringUtils"%>
<%@page import="platform.approval.entity.ApprovalDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
ApprovalDTO dto = (ApprovalDTO) request.getAttribute("dto");
String className = dto.getPer().getClass().getName();
String jsp = "/jsp/approval/" + className.substring(className.lastIndexOf(".") + 1) + ".jsp";
boolean isApp = true;
if (dto.getLineType().equals("참조")) {
	isApp = false;
}
%>
<div class="header-title">
	<img src="/Windchill/jsp/images/home.png" class="home">
	<span>HOME</span>
	>
	<span>작업공간</span>
	>
	<span>결재 정보</span>
</div>

<div id="tabs"></div>
<br>
<table class="view-table info-view close">
	<colgroup>
		<col width="100">
		<col width="800">
		<col width="100">
		<col width="800">
	<tr>
		<th>결재 제목</th>
		<td colspan="3">
			<input type="hidden" name="oid" value="<%=dto.getOid()%>">
			<%=dto.getName()%></td>
	</tr>
	<tr>
		<th>담당자</th>
		<td><%=dto.getCreator()%></td>
		<th>수신일</th>
		<td><%=dto.getStartTime()%></td>
	</tr>
	<tr>
		<th>구분</th>
		<td><%=dto.getLineType()%></td>
		<th>역할</th>
		<td><%=dto.getRole()%></td>
	</tr>
	<tr>
		<th>기안자</th>
		<td><%=dto.getSubmiter()%></td>
		<th>상태</th>
		<td><%=dto.getState()%></td>
	</tr>
	<tr>
		<th>결재의견</th>
		<td colspan="3">
			<textarea rows="10" cols="" name="description" class="AXTextarea"><%=dto.getDescription()%></textarea>
		</td>
	</tr>
</table>
<br class="br1 close">
<div class="tabNav close">■ 결재 및 경유</div>
<table class="view-table app-view close">
	<colgroup>
		<col width="60">
		<col width="80">
		<col width="*">
		<col width="400">
		<col width="80">
		<col width="80">
		<col width="100">
		<col width="100">
		<col width="100">
		<col width="100">
	</colgroup>
	<tr>
		<th>구분</th>
		<th>역할</th>
		<th>결재제목</th>
		<th>결재의견</th>
		<th>담당자</th>
		<th>기안자</th>
		<th>상태</th>
		<th>수신일</th>
		<th>완료일</th>
		<th>의견만기일</th>
	</tr>

	<%
	ArrayList<ApprovalLine> lines = ApprovalHelper.manager.getHistory(dto.getPer());
	for (ApprovalLine line : lines) {
	%>
	<tr>
		<td class="center first">
			<%
			String lineType = line.getLineType();
			if ("결재".equals(lineType)) {
			%>
			<b>
				<font color="red"><%=line.getLineType()%></font>
			</b>
			<%
			} else if ("검토".equals(lineType)) {
			%>
			<b>
				<font color="blue"><%=line.getLineType()%></font>
			</b>
			<%
			} else if ("수신".equals(lineType)) {
			%>
			<b>
				<font color="green"><%=line.getLineType()%></font>
			</b>
			<%
			} else {
			%>
			<b><%=line.getLineType()%></b>
			<%
			}
			%>
		</td>
		<td class="center"><%=line.getRole()%></td>
		<td class="left indent10"><%=line.getMaster().getName()%></td>
		<td class="left indent10"><%=StringUtils.convertToEmpty(line.getDescription())%></td>
		<td class="center"><%=line.getOwnership().getOwner().getFullName()%></td>
		<td class="center"><%=line.getMaster().getOwnership().getOwner().getFullName()%></td>
		<td class="center"><%=line.getState()%></td>
		<td class="center"><%=line.getCreateTimestamp().toString().substring(0, 10)%></td>
		<td class="center"><%=line.getCompleteTime() != null ? line.getCompleteTime().toString().substring(0, 10) : ""%></td>
		<td class="center"><%=line.getLimit() != null ? line.getLimit().toString().substring(0, 10) : ""%></td>
	</tr>
	<%
	}
	if (lines.size() == 0) {
	%>
	<tr>
		<td colspan="10" class="center">
			<font color="red">
				<b>결재이력이 없습니다.</b>
			</font>
	</tr>
	<%
	}
	%>
</table>

<!-- 결재 객체 -->
<jsp:include page="<%=jsp%>">
	<jsp:param value="<%=dto.getPer().getPersistInfo().getObjectIdentifier().getStringValue()%>" name="per" />
</jsp:include>
<table class="button-table">
	<tr>
		<td class="right">
			<%
			if (isApp) {
			%>
			<button type="button" id="approvalBtn">승인</button>
			<button type="button" id="returnBtn">반려</button>
			<%
			} else {
			%>
			<button type="button" id="confirmBtn">확인</button>
			<%
			}
			%>
			<button type="button" id="closeBtn">닫기</button>
		</td>
	</tr>
</table>

<script type="text/javascript">
	$(function() {
		$("#confirmBtn").click(function() {

			if (!confirm("확인 하시겠습니까?")) {
				return false;
			}

			var params = _data($("#form"));
			var url = _url("/app/confirm");
			_call(url, params, function(data) {
				opener.load();
				self.close();
			}, "POST");
		})

		$("#approvalBtn").click(function() {
// 			$description = $("textarea[name=description]");
			
// 			if($description.val() == "") {
// 				alert("결재의견을 입력하세요.");
// 				$description.focus();
// 				return false;
// 			}
			
			if (!confirm("승인 하시겠습니까?")) {
				return false;
			}

			var params = _data($("#form"));
			var url = _url("/app/approval");
			_call(url, params, function(data) {
				opener.load();
				self.close();
			}, "POST");
		})

		$("#returnBtn").click(function() {

			if (!confirm("반려 하시겠습니까?")) {
				return false;
			}

			var params = _data($("#form"));
			var url = _url("/app/rtn");
			_call(url, params, function(data) {
				opener.load();
				self.close();
			}, "POST");
		})

		$("#closeBtn").click(function() {
			self.close();
		})
	})
</script>