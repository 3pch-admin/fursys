<%@page import="platform.util.DateUtils"%>
<%@page import="platform.util.StringUtils"%>
<%@page import="platform.util.CommonUtils"%>
<%@page import="platform.project.task.service.TaskHelper"%>
<%@page import="platform.project.task.entity.Task"%>
<%@page import="java.util.ArrayList"%>
<%@page import="platform.project.entity.Project"%>
<%@page import="platform.project.template.entity.Template"%>
<%@page import="wt.fc.Persistable"%>
<%@page import="wt.fc.ReferenceFactory"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String oid = (String) request.getAttribute("oid");
Task task = (Task) CommonUtils.persistable(oid);
String m = "";
ArrayList<Task> list = new ArrayList<Task>();
Template template = task.getTemplate();
Project project = task.getProject();
int sort = task.getSort();
if (StringUtils.isNotNull(template)) {
	m = "템플릿";
	list = TaskHelper.manager.getAllTemplateTask(template);
}

if (StringUtils.isNotNull(project)) {
	m = "프로젝트";
	list = TaskHelper.manager.getAllProjectTask(project);
}
%>
<div class="header-title">
	<img src="/Windchill/jsp/images/home.png" class="home">
	<span>HOME</span>
	>
	<span><%=m%>
		관리
	</span>
	>
	<span>선행태스크 추가</span>
</div>
<table class="button-table">
	<tr>
		<td class="right">
			<button type="button" class="addBtn">추가</button>
			<button type="button" class="closeBtn">닫기</button>
		</td>
	</tr>
</table>
<table class="view-table">
	<colgroup>
		<col width="40">
		<col width="200">
		<col width="*">
		<col width="80">
	</colgroup>
	<tr>
		<th>&nbsp;</th>
		<th>태스크명</th>
		<th>설명</th>
		<th>기간</th>
	</tr>
	<%
	for (Task t : list) {
		int compSort = t.getSort();
		boolean isBox = true;

		if (sort < compSort) {
			isBox = false;
		}

		if (t.equals(task)) {
			isBox = false;
		}
		
		if(t.getDepth() != task.getDepth()) {
			isBox = false;
		}

		String toid = t.getPersistInfo().getObjectIdentifier().getStringValue();
	%>
	<tr>
		<td class="first center">
			<%
			if (isBox) {
			%>
			<input type="radio" name="oid" value="<%=toid%>">
			<%
			}
			%>
		</td>
		<td><%=t.getName()%></td>
		<td class="ellipsis350" title="<%=StringUtils.convertToEmpty(t.getDescription())%>"><%=StringUtils.convertToEmpty(t.getDescription())%></td>
		<td class="center"><%=t.getDuration()%>일</td>
		</td>
	</tr>
	<%
	}
	%>
</table>
<table class="button-table">
	<tr>
		<td class="right">
			<button type="button" class="addBtn">추가</button>
			<button type="button" class="closeBtn">닫기</button>
		</td>
	</tr>
</table>
<script type="text/javascript">
	$(function() {
		$(".closeBtn").click(function() {
			self.close();
		})

		$(".addBtn").click(function() {
			var url = "/Windchill/platform/task/pre";
			var arr = new Array();
			var box = $("input[name=oid]");
			$.each(box, function(idx) {
				if(box.eq(idx).prop("checked")) {
					arr.push(box.eq(idx).val());
				}
			})
			console.log(arr);
			var params = new Object();
			params.arr = arr;
			params.oid = "<%=oid%>";
			_call(url, params, function(data) {
				opener.grid();
				self.close();
			}, "POST");
		})

		$("input").checks();
	})
</script>