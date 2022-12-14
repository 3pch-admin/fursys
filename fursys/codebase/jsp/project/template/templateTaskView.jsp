<%@page import="platform.project.task.entity.PreTaskPostTaskLink"%>
<%@page import="platform.util.StringUtils"%>
<%@page import="platform.project.task.entity.Task"%>
<%@page import="platform.project.output.entity.Output"%>
<%@page import="platform.code.entity.BaseCode"%>
<%@page import="platform.project.task.service.TaskHelper"%>
<%@page import="platform.project.task.entity.TaskRoleLink"%>
<%@page import="java.util.ArrayList"%>
<%@page import="platform.project.task.entity.TaskDTO"%>
<%@page import="platform.project.template.entity.TemplateDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
TaskDTO dto = (TaskDTO) request.getAttribute("dto");
String toid = dto.getTemplate().getPersistInfo().getObjectIdentifier().getStringValue();
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
		$("#addOutputBtn").click(function() {
			var url = _url("/output/create", "<%=dto.getOid()%>");
			_popup(url, "1200", "420", "n");
		})

		$("#deleteOutputBtn").click(function() {
			var o = $("input[name=ooid]");
			if(o.length == 0) {
				alert("정의된 산출물이 없습니다.");
				return false;
			}
			var bool = false;
			$.each(o, function(idx) {
				if (o.eq(idx).prop("checked")) {
					bool = true;
					return true;
				}
			})

			if (!bool) {
				alert("삭제할 산출물 정의를 선택하세요.");
				return false;
			}
			
			var arr = new Array();
			$.each(o, function(idx) {
				if (o.eq(idx).prop("checked")) {
					arr.push(o.eq(idx).val());
				}
			})
			
			var params = new Object();
			params.list = arr;
			console.log(params);
			if(!confirm("정의된 산출물을 삭제 하시겠습니까?")) {
				return false;
			}
			var url = _url("/output/delete");
			call(url, params, function(data) {
				document.location.reload();
			}, "POST");
		})
		
		$("input").checks();

		$("#addPreTaskBtn").click(function() {
			var url = _url("/task/addPreTask", "<%=dto.getOid()%>");
			_popup(url, "800", "700", "n");
		})
		
		$("#deletePreTaskBtn").click(function() {
			var o = $("input[name=pre]");
			if(o.length == 0) {
				alert("정의된 선행태스크가 없습니다.");
				return false;
			}
			var bool = false;
			$.each(o, function(idx) {
				if (o.eq(idx).prop("checked")) {
					bool = true;
					return true;
				}
			})

			if (!bool) {
				alert("삭제할 선행태스크를 선택하세요.");
				return false;
			}
			
			if(!confirm("선행태스크 연결을 삭제 하시겠습니까?")) {
				return false;
			}
			
			
			var arr = new Array();
			$.each(o, function(idx) {
				if (o.eq(idx).prop("checked")) {
					arr.push(o.eq(idx).val());
				}
			})
			
			var params = new Object();
			params.list = arr;
			var url = _url("/task/disconnect");
			call(url, params, function(data) {
				document.location.reload();
				grid();
			}, "POST");
		})
		
				
		$(".output-view").click(function() {
			var oid = $(this).data("oid");
			var url = _url("/output/view", oid);
			_popup(url, 1200, 400, "n")
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
	})
	
	function grid() {
		parent.load("<%=toid%>");
		document.location.reload();
	}
</script>
</head>
<body style="margin: 0 !important;">
	<div>
		<img src="/Windchill/jsp/images/title_bullet.gif">
		<span class="header">태스크 정보</span>
	</div>
	<table class="view-table">
		<colgroup>
			<col width="130">
			<col width="400">
			<col width="130">
			<col width="400">
		</colgroup>
		<tr>
			<th>태스크명</th>
			<td><%=dto.getName()%></td>
			<th>기간</th>
			<td><%=dto.getDuration()%>일
			</td>
		</tr>
		<tr>
			<th>역할</th>
			<td colspan="3">
				<%
				String roles = "";
				ArrayList<TaskRoleLink> links = TaskHelper.manager.getTaskRoleLink(dto.getTask().getTemplate(), dto.getTask());
				for (TaskRoleLink link : links) {
					BaseCode c = link.getRole();
					roles += c.getName() + ",";
				}
				%>
				<%=roles%>
			</td>
		</tr>
		<tr>
			<th>설명</th>
			<td colspan="3">
				<textarea rows="15" cols="" class="AXTextarea" readonly="readonly"><%=dto.getDescription()%></textarea>
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
		<span class="header">산출물 정의</span>
	</div>
	<table class="button-table" style="margin-top: 0px;">
		<tr>
			<td class="left">
				<button type="button" id="addOutputBtn">추가</button>
				<button type="button" id="deleteOutputBtn">삭제</button>
			</td>
		</tr>
	</table>
	<table class="view-table">
		<colgroup>
			<col width="40">
			<col width="300">
			<col width="*">
			<col width="200">
		</colgroup>
		<tr>
			<th>&nbsp;</th>
			<th>이름</th>
			<th>위치</th>
			<th>설명</th>
		</tr>
		<%
		ArrayList<Output> outputs = dto.getOutputs();
		for (Output output : outputs) {
			String ooid = output.getPersistInfo().getObjectIdentifier().getStringValue();
		%>
		<tr>
			<td class="first">
				<input type="checkbox" value="<%=ooid%>" id="ooid" name="ooid">
			</td>
			<td class="output-view" data-oid="<%=ooid%>"><%=output.getName()%></td>
			<td><%=output.getLocation()%></td>
			<td class="ellipsis250"><%=StringUtils.convertToStr(output.getDescription(), "")%></td>
		</tr>
		<%
		}
		if (outputs.size() == 0) {
		%>
		<tr>
			<td class="first center" colspan="4">
				<font color="red">
					<b>정의된 산출물이 없습니다.</b>
				</font>
			</td>
		</tr>
		<%
		}
		%>
	</table>

	<br>
	<div>
		<img src="/Windchill/jsp/images/title_bullet.gif">
		<span class="header">선행태스크</span>
	</div>
	<table class="button-table" style="margin-top: 0px;">
		<tr>
			<td class="left">
				<button type="button" id="addPreTaskBtn">추가</button>
				<button type="button" id="deletePreTaskBtn">삭제</button>
			</td>
		</tr>
	</table>
	<table class="view-table">
		<colgroup>
			<col width="40">
			<col width="200">
			<col width="*">
			<col width="100">
		</colgroup>
		<tr>
			<th>&nbsp;</th>
			<th>태스크명</th>
			<th>설명</th>
			<th>기간</th>
		</tr>
		<%
		ArrayList<PreTaskPostTaskLink> pres = dto.getPres();
		for (PreTaskPostTaskLink link : pres) {
			Task pre = link.getPreTask();
			String loid = link.getPersistInfo().getObjectIdentifier().getStringValue();
		%>
		<tr>
			<td class="first center">
				<input type="checkbox" value="<%=loid%>" name="pre">
			</td>
			<td><%=pre.getName()%></td>
			<td><%=StringUtils.convertToEmpty(pre.getDescription())%></td>
			<td class="center"><%=pre.getDuration()%>일
			</td>
		</tr>
		<%
		}
		if (pres.size() == 0) {
		%>
		<tr>
			<td class="first center" colspan="4">
				<font color="red">
					<b>등록된 선행 태스크가 없습니다.</b>
				</font>
			</td>
		</tr>
		<%
		}
		%>
	</table>
</body>
</html>