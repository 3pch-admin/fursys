<%@page import="platform.util.StringUtils"%>
<%@page import="platform.code.entity.BaseCode"%>
<%@page import="platform.project.task.service.TaskHelper"%>
<%@page import="platform.project.task.entity.TaskRoleLink"%>
<%@page import="platform.project.task.entity.Task"%>
<%@page import="java.util.ArrayList"%>
<%@page import="platform.project.template.entity.TemplateDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
TemplateDTO dto = (TemplateDTO) request.getAttribute("dto");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<%@include file="/jsp/common/iframe.jsp"%>
<%@include file="/jsp/common/script.jsp"%>
<script type="text/javascript">
$(function() {
	$(".task-view").click(function() {
		var oid = $(this).data("oid");
		document.location.href = "/Windchill/platform/template/templateTaskView?oid="+oid;
	}).mouseover(function() {
		$(this).css({
			"cursor": "pointer",
			"text-decoration" : "underline",
			"color" : "blue"
		})
	}).mouseout(function() {
		$(this).css({
			"text-decoration" : "none",
			"color" : "black"
		})
	})
	
	$("#editTemplateBtn").click(function() {
		var url = _url("/template/modify", "<%=dto.getOid()%>");
		_popup(url, 1300, 500, "n");
	})
	
	$("#deleteTemplateBtn").click(function() {
		if(!confirm("템플릿을 삭제 하시겠습니까?")) {
			return false;
		}
		var url = _url("/template/delete", "<%=dto.getOid()%>");
		var params = new Object();
		call(url, params, function(data) {
			parent.opener.load();
			parent.close();
		}, "GET");
	})
})

function grid() {
		parent.load("<%=dto.getOid()%>");
		document.location.reload();
	}
</script>
</head>
<body style="margin: 0 !important;">
	<div>
		<img src="/Windchill/jsp/images/title_bullet.gif">
		<span class="header">템플릿 정보
		<img src="/Windchill/jsp/images/edit.gif" style="cursor: pointer; position: relative; top: 2px;" id="editTemplateBtn">
		<img src="/Windchill/jsp/images/delete.png" style="cursor: pointer; position: relative; top: 2px;" id="deleteTemplateBtn">
		</span>
	</div>
	<table class="view-table">
		<colgroup>
			<col width="130">
			<col width="400">
			<col width="130">
			<col width="400">
		</colgroup>
		<tr>
			<th>템플릿명</th>
			<td><%=dto.getName()%></td>
			<th>사용여부</th>
			<td><%=dto.isEnable() == true ? "사용" : "사용안함"%></td>
		</tr>
		<tr>
			<th>기간</th>
			<td><%=dto.getDuration()%>일
			</td>
			<th>회사</th>
			<td><%=dto.getCompanyNm()%></td>
		</tr>
		<tr>
			<th>설명</th>
			<td colspan="3">
				<textarea rows="15" cols="" class="AXTextarea none" readonly="readonly"><%=dto.getDescription()%></textarea>
			</td>
		</tr>
		<tr>
			<th>작성일자</th>
			<td><%=dto.getCreatedDate().toString().substring(0, 10)%></td>
			<th>작성자</th>
			<td><%=dto.getCreator()%></td>
		</tr>
	</table>
	<br>
	<div>
		<img src="/Windchill/jsp/images/title_bullet.gif">
		<span class="header">하위 태스크 정보</span>
	</div>
	<%
	ArrayList<Task> list = dto.getChildrens();
	%>
	<table class="view-table">
		<colgroup>
			<col width="200">
			<col width="*">
			<col width="80">
			<col width="200">
			<col width="150">
		</colgroup>
		<tr>
			<th>태스크명</th>
			<th>설명</th>
			<th>기간</th>
			<th>선행태스크</th>
			<th>역할</th>
		</tr>
		<%
		for (Task t : list) {
			String toid = t.getPersistInfo().getObjectIdentifier().getStringValue();
			String roles = "";
			String s = "";
			ArrayList<TaskRoleLink> links = TaskHelper.manager.getTaskRoleLink(t.getTemplate(), t);
			ArrayList<Task> pres = TaskHelper.manager.getPreTasks(t);
			for (TaskRoleLink link : links) {
				BaseCode c = link.getRole();
				roles += c.getName() + ",";
			}
			
			for(Task pre : pres) {
				s += pre.getName();
			}
			
		%>
		<tr>
			<td class="first task-view" data-oid="<%=toid%>"><%=t.getName()%></td>
			<td class="ellipsis300" title="<%=StringUtils.convertToStr(t.getDescription(), "")%>"><%=StringUtils.convertToStr(t.getDescription(), "")%></td>
			<td class="center"><%=t.getDuration()%>일</td>
			<td><%=s %></td>
			<td class="ellipsis150" title="<%=roles%>"><%=roles%></td>
		</tr>
		<%
		}
		if(list.size() == 0) {
		%>
		<tr>
			<td class="first center" colspan="5">
				<font color="red"><b>하위 태스크가 없습니다.</b></font>
			</td>
		</tr>
		<%
		}
		%>
	</table>
</body>
</html>