<%@page import="java.text.DecimalFormat"%>
<%@page import="wt.org.WTUser"%>
<%@page import="platform.project.entity.ProjectRoleLink"%>
<%@page import="platform.util.StringUtils"%>
<%@page import="platform.project.entity.ProjectDTO"%>
<%@page import="platform.code.entity.BaseCode"%>
<%@page import="platform.project.task.service.TaskHelper"%>
<%@page import="platform.project.task.entity.TaskRoleLink"%>
<%@page import="platform.project.task.entity.Task"%>
<%@page import="java.util.ArrayList"%>
<%@page import="platform.project.template.entity.TemplateDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
ProjectDTO dto = (ProjectDTO) request.getAttribute("dto");
DecimalFormat df = new DecimalFormat("#,###");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<%@include file="/jsp/common/iframe.jsp"%>
<%@include file="/jsp/common/script.jsp"%>
</head>
<body class="m0">
	<div>
		<img src="/Windchill/jsp/images/title_bullet.gif">
		<span class="header">프로젝트 정보</span>
	</div>
	<table class="view-table">
		<colgroup>
			<col width="130">
			<col width="400">
			<col width="130">
			<col width="400">
		</colgroup>
		<tr>
			<th>프로젝트 명</th>
			<td><%=dto.getName()%></td>
			<th>PM</th>
			<td><%=dto.getPm()%></td>
		</tr>
		<tr>
			<th>기간</th>
			<td><%=dto.getDuration()%>일
			</td>
			<th>고객</th>
			<td><%=dto.getCustomerNm()%></td>
		</tr>
		<tr>
			<th>예산(원)</th>
			<td><%=df.format(dto.getBudget()) %>원</td>
			<th>비용(TOTAL)</th>
			<td><%=df.format(dto.getTotal()) %>원</td>
		</tr>
		<tr>
			<th>재료비</th>
			<td><%=df.format(dto.getMaterial()) %>원</td>
			<th>노무비</th>
			<td><%=df.format(dto.getLabor()) %>원</td>
		</tr>
		<tr>
			<th>설명</th>
			<td colspan="3">
				<textarea rows="8" cols="" class="AXTextarea none" readonly="readonly"><%=StringUtils.convertToStr(dto.getDescription(), "")%></textarea>
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
		<span class="header">일정 정보</span>
	</div>
	<table class="view-table">
		<colgroup>
			<col width="130">
			<col width="400">
			<col width="130">
			<col width="400">
		</colgroup>
		<tr>
			<th>기간</th>
			<td><%=dto.getDuration()%>일
			</td>
			<th>상태</th>
			<td><%=dto.getState()%></td>
		</tr>
		<tr>
			<th>계획시작일</th>
			<td><%=dto.getPlanStartDate()%></td>
			<th>계획종료일</th>
			<td><%=dto.getPlanEndDate()%></td>
		</tr>
		<tr>
			<th>실제시작일</th>
			<td><%=dto.getStartDate()%></td>
			<th>실제종료일</th>
			<td><%=dto.getEndDate()%></td>
		</tr>
		<tr>
			<th>진행율</th>
			<td colspan="3">
				<progress value="<%=dto.getProgress()%>" max="100"></progress>
			</td>
		</tr>
	</table>

	<br>
	<div>
		<img src="/Windchill/jsp/images/title_bullet.gif">
		<span class="header">담당자</span>
	</div>
	<table class="view-table">
		<colgroup>
			<col width="150">
			<col width="*">
		</colgroup>
		<tr>
			<th>역할</th>
			<th>담당자</th>
		</tr>
		<%
		ArrayList<ProjectRoleLink> roleLinks = dto.getRoleLinks();
		for (ProjectRoleLink link : roleLinks) {
			BaseCode role = link.getRole();
			ArrayList<WTUser> ll = TaskHelper.manager.getProjectUser(dto.getProject(), role);
		%>
		<tr>
			<td class="first center"><%=role.getName() %></td>
			<td>
			<%
				String s = "";
				for(int i=0; i<ll.size(); i++) {
					WTUser user = (WTUser)ll.get(i);
					if(i == ll.size() - 1) {
						s += user.getFullName();
					} else {
						s += user.getFullName() + ",";						
					}
			%>
			<%=s %>
			<%
				}
			%>
			</td>
		</tr>
		<%
		}
		if (roleLinks.size() == 0) {
		%>
		<tr>
			<td class="first center" colspan="2">
				<font color="red"><b>지정된 담당자가 없습니다.</b></font>
			</td>
		</tr>
		<%
		}
		%>
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
			<col width="*">
			<col width="80">
			<col width="100">
			<col width="100">
			<col width="100">
			<col width="100">
			<col width="80">
		</colgroup>
		<tr>
			<th>태스크명</th>
			<th>기간</th>
			<th>계획시작일</th>
			<th>계획종료일</th>
			<th>실제시작일</th>
			<th>실제종료일</th>
			<th>진행율</th>
		</tr>
		<%
		for (Task t : list) {
			String roles = "";
			ArrayList<TaskRoleLink> links = TaskHelper.manager.getTaskRoleLink(t.getProject(), t);
			for (TaskRoleLink link : links) {
				BaseCode c = link.getRole();
				roles += c.getName() + ",";
			}
		%>
		<tr>
			<td class="first"><%=t.getName()%></td>
			<td class="center"><%=t.getDuration()%>일</td>
			<td class="center"><%=t.getPlanStartDate().toString().substring(0, 10)%></td>
			<td class="center"><%=t.getPlanEndDate().toString().substring(0, 10)%></td>
			<td class="center"><%=t.getStartDate() != null ? t.getStartDate().toString().substring(0, 10) : ""%></td>
			<td class="center"><%=t.getEndDate() != null ? t.getEndDate().toString().substring(0, 10) : ""%></td>
			<td class="center"><%=t.getProgress()%>%
			</td>
		</tr>
		<%
		}
		if (list.size() == 0) {
		%>
		<tr>
			<td class="first center" colspan="7">
				<font color="red">
					<b>하위 태스크가 없습니다.</b>
				</font>
			</td>
		</tr>
		<%
		}
		%>
	</table>
</body>
</html>