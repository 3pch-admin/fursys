<%@page import="java.text.DecimalFormat"%>
<%@page import="platform.util.DateUtils"%>
<%@page import="wt.session.SessionHelper"%>
<%@page import="platform.util.ContentUtils"%>
<%@page import="wt.org.WTUser"%>
<%@page import="platform.code.service.BaseCodeHelper"%>
<%@page import="platform.project.issue.entity.Issue"%>
<%@page import="platform.project.issue.service.IssueHelper"%>
<%@page import="platform.project.task.entity.PreTaskPostTaskLink"%>
<%@page import="wt.doc.WTDocument"%>
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
DecimalFormat df = new DecimalFormat("#,###");
TaskDTO dto = (TaskDTO) request.getAttribute("dto");
boolean isPM = (boolean) request.getAttribute("isPM");
String poid = dto.getProject().getPersistInfo().getObjectIdentifier().getStringValue();
WTUser sessionUser = (WTUser)SessionHelper.manager.getPrincipal();
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
			
			if(!confirm("정의된 산출물을 삭제 하시겠습니까?")) {
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

			var url = _url("/output/delete");
			call(url, params, function(data) {
				document.location.reload();
			}, "POST");
		})

		$("input").checks();
		
		$("#saveRoleBtn").click(function() {
			if(!confirm("지정한 담당자들을 저장 하시겠습니까?")) {
				return false;
			}
			
			var arr = new Array();
			$loids = $("input[name*=loid]");
			$.each($loids, function(idx) {
				var value = $loids.eq(idx).val();
				var name = $loids.eq(idx).attr("name"); // loid0, loid1..
				name = name.replaceAll("loid", "roleUser") + "Oid";
				$k = $("input[name=" + name + "]").val();
				arr.push(value + "&" + $k);
			})
			
			var params = new Object();
			params.arr = arr;
			console.log(params);
			var url = _url("/task/role");
			call(url, params, function(data) {
				document.location.reload();
				grid();
			}, "POST");
		})
		

		$("#addPreTaskBtn").click(function() {
			var url = _url("/task/addPreTask", "<%=dto.getOid()%>");
			_popup(url, "800", "700", "n");
		})

		$("#deletePreTaskBtn").click(function() {
			var o = $("input[name=pre]");
			if (o.length == 0) {
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

			var arr = new Array();
			$.each(o, function(idx) {
				if (o.eq(idx).prop("checked")) {
					arr.push(o.eq(idx).val());
				}
			})

			if (!confirm("선행태스크 연결을 삭제 하시겠습니까?")) {
				return false;
			}

			var params = new Object();
			params.list = arr;
			var url = _url("/task/disconnect");
			call(url, params, function(data) {
				document.location.reload();
			}, "POST");
		})

		$(".directBtn").click(function() {
			var oid = $(this).data("oid");
			var loc = $(this).data("loc");
			var url = "/Windchill/platform/output/direct?oid=" + oid + "&location=" + loc;
			_popup(url, "", "", "f");
			return false;
		})

		$(".connectBtn").click(function() {
			var oid = $(this).data("oid");
			var loc = $(this).data("loc");
			var url = "/Windchill/platform/output/connect?oid=" + oid + "&location=" + loc;
			_popup(url, "", "", "f");
			return false;
		})

		$(".disconnectBtn").click(function() {
			if (!confirm("산출물을 삭제 하시겠습니까?\n연결된 문서와의 연결만 끊어지며 기존문서는 보존이 되어집니다.")) {
				return false;
			}
			var oid = $(this).data("oid");
			var url = _url("/output/disconnect", oid);
			call(url, null, function(data) {
				document.location.reload();
			}, "GET");
			return false;
		})
		
		
		$("#deleteIssueBtn").click(function() {
			var o = $("input[name=ioid]");
			if (o.length == 0) {
				alert("등록된 이슈가 없습니다.");
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
				alert("삭제할 이슈를 선택하세요.");
				return false;
			}

			var arr = new Array();
			$.each(o, function(idx) {
				if (o.eq(idx).prop("checked")) {
					arr.push(o.eq(idx).val());
				}
			})

			if (!confirm("이슈를 삭제 하시겠습니까?")) {
				return false;
			}

			var params = new Object();
			params.list = arr;
			var url = _url("/issue/delete");
			call(url, params, function(data) {
				document.location.reload();
			}, "POST");
		})
		
		$("#addIssueBtn").click(function() {
			var oid = $(this).data("oid");
			var url = "/Windchill/platform/issue/create?oid=<%=dto.getOid()%>";
			_popup(url, 1200, 460, "n");
			return false;
		})
		
		$("#progressBtn").click(function() {
			
			var value = $("input[name=progress]").val();
			
			if(value == 100) {
				if(!confirm("진행율을 100으로 저장 할시 태스크가 완료됩니다.\n진행 하시겠습니까?")) {
					return false;
				}
			} else {
				if(!confirm("진행율을 저장 하시겠습니까?")) {
					return false;
				}
			}
			

			var params = new Object();
			params.progress = value;
			params.oid = "<%=dto.getOid()%>";
			var url = _url("/task/progress");
			call(url, params, function(data) {
				document.location.reload();
				grid();
			}, "POST");
		})
		
		$("#startTask").click(function() {
			if(!confirm("태스크를 시작 하시겠습니까?")) {
				return false;
			}
			var params = new Object();
			params.oid = "<%=dto.getOid()%>";
			var url = _url("/task/start");
			call(url, params, function(data) {
				document.location.reload();
				grid();
			}, "POST");
		})
		
		$("#completeTask").click(function() {
			
			var progress = $("input[name=progress]").val();
			if(progress != "100") {
				if(!confirm("태스크 완료시 진행율은 자동으로 100%처리 되어집니다.\n현재 진행율:" + progress + "%, 태스크를 완료 하시겠습니까?")) {
					return false;
				}
			} else { 
				if(!confirm("태스크를 완료 하시겠습니까?")) {
					return false;
				}
			}
			
			var params = new Object();
			params.oid = "<%=dto.getOid()%>";
			var url = _url("/task/complete");
			call(url, params, function(data) {
				document.location.reload();
				grid();
			}, "POST");
		})
		
		$("#stopTask").click(function() {
			if(!confirm("태스크를 중단 하시겠습니까?")) {
				return false;
			}
			var params = new Object();
			params.oid = "<%=dto.getOid()%>";
			var url = _url("/task/stop");
			call(url, params, function(data) {
				document.location.reload();
				grid();
			}, "POST");
		})
		
		$(".issue-view").click(function() {
			var oid = $(this).data("oid");
			var url = _url("/issue/view", oid);
			_popup(url, 1200, 500, "n");
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
		
		$(".doc-view").click(function() {
			var oid = $(this).data("oid");
			var url = _url("/doc/view", oid);
			_popup(url, "", "", "f");
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
		
		$(".task-view").click(function() {
			var oid = $(this).data("oid");
			document.location.href = "/Windchill/platform/project/projectTaskView?oid="+oid;
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
		
		$(".clearRole").click(function() {
			var preFix = $(this).data("pre");
			$("input[name=roleUser" + preFix + "]").val("");
			$("input[name=roleUser" + preFix + "Oid]").val(" ");
		})
		
		$("#editTaskBtn").click(function() {
			var url = _url("/task/modify", "<%=dto.getOid()%>");
			_popup(url, 1100, 400, "n");
		})
	})

	function grid() {
		parent.load("<%=poid%>");
		document.location.reload();
	}
</script>
</head>
<body class="m0">
	<div>
		<img src="/Windchill/jsp/images/title_bullet.gif">
		<span class="header">태스크 정보
		<%
			if(dto.isReady()) {
		%>
		<img src="/Windchill/jsp/images/edit.gif" style="cursor: pointer; position: relative; top: 2px;" id="editTaskBtn">
		<%
			}
		%>
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
			<th>태스크명</th>
			<td><%=dto.getName()%></td>
			<th>기간</th>
			<td><%=dto.getDuration()%>일
			</td>
		</tr>
		<tr>
			<th>노무비</th>
			<td><%=df.format(dto.getLabor())%>원</td>
			<th>재료비</th>
			<td><%=df.format(dto.getMaterial())%>원</td>
		</tr>	
		<tr>
			<th>기타</th>
			<td colspan="3"><%=df.format(dto.getEtc())%>원</td>
		</tr>				
		<tr>
			<th>설명</th>
			<td colspan="3">
				<textarea rows="5" cols="" class="AXTextarea none" readonly="readonly"><%=dto.getDescription()%></textarea>
			</td>
		</tr>
<!-- 		<tr> -->
<!-- 			<th>작성일자</th> -->
<%-- 			<td><%=dto.getCreatedDate().toString().substring(0, 10)%></td> --%>
<!-- 			<th>작성자</th> -->
<%-- 			<td><%=dto.getCreator()%></td> --%>
<!-- 		</tr> -->
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
			<th>진행율</th>
			<td>
				<%
				//.. 권한들 조절 해야
				if (dto.isStart() && dto.isLast()) {
				%>
				<input type="text" class="AXInput" id="progress" name="progress" value="<%=dto.getProgress()%>" maxlength="3" size="2" onKeyup="this.value=this.value.replace(/[^-0-9]/g,'');">
				<img src="/Windchill/jsp/images/save.gif" style="cursor: pointer; position: relative; top: 5px;" id="progressBtn">
				<%
				} else {
				%>
				<%=dto.getProgress()%>%
				<%
				}
				%>
			</td>
			<th>상태</th>
			<td><%=dto.getState()%>
				<%
				if (dto.isReady() && dto.isLast()) {
				%>
				<img src="/Windchill/jsp/images/planStart.gif" style="position: relative; top: 3px; cursor: pointer;" id="startTask">
				<%
				} else if (dto.isStart()) {
				%>
				<img src="/Windchill/jsp/images/planComplete.gif" style="position: relative; top: 3px; cursor: pointer;" id="completeTask">
				<%
				} else {
				%>

				<%
				}
				%>
			</td>
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
			<th>진행상태</th>
			<td colspan="3">
				<progress value="<%=dto.getProgress()%>" max="100"></progress>
			</td>
		</tr>
	</table>

	<br>
	<div>
		<img src="/Windchill/jsp/images/title_bullet.gif">
		<span class="header">담당자
		<%
			if(dto.isReady()) {
		%>
		<img src="/Windchill/jsp/images/save.gif" style="cursor: pointer; position: relative; top: 2px;" id="saveRoleBtn">
		<%
			}
		%>
		</span>
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
		int preFix = 0;
		ArrayList<TaskRoleLink> roleLinks = dto.getRoleLinks();
		for (TaskRoleLink link : roleLinks) {
			String loid = link.getPersistInfo().getObjectIdentifier().getStringValue();
			BaseCode role = link.getRole();
			WTUser user = link.getUser();
		%>
		<tr>
			<td class="first center"><%=role.getName()%></td>
			<td>
				<%
				if (dto.isReady()) {
				%>
				<input type="hidden" name="loid<%=preFix %>" value="<%=loid %>">
				<input type="hidden" name="roleUser<%=preFix %>Oid" id="roleUser<%=preFix %>Oid" value="<%=user != null ? user.getPersistInfo().getObjectIdentifier().getStringValue() : " "%>">
				<input type="text" value="<%=user != null ? user.getFullName() : ""%>" class="AXInput w200px" name="roleUser<%=preFix %>" id="roleUser<%=preFix %>">
				<i class="axi axi-close2 axicon clearRole" data-pre="<%=preFix%>"></i>
				<script type="text/javascript">
				$(function() {
					_user("roleUser<%=preFix%>");
				})
				</script>
				<%
				preFix++;
				} else {
				%>
				<%=user != null ? user.getFullName() : ""%>
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
				<font color="red">
					<b>지정된 담당자가 없습니다.</b>
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
		<span class="header">산출물</span>
	</div>
	<%
	if (dto.isReady()) {
	%>
	<table class="button-table mt0">
		<tr>
			<td class="left">
				<button type="button" id="addOutputBtn">추가</button>
				<button type="button" id="deleteOutputBtn">삭제</button>
			</td>
		</tr>
	</table>
	<%
	}
	%>
	<table class="view-table">
		<colgroup>
			<col width="40">
			<col width="180">
<!-- 			<col width="*"> -->
			<col width="200">
			<col width="80">
			<col width="80">
			<col width="100">
			<col width="80">
			<%
				if(dto.isReady()) {
			%>
			<col width="200">
			<%
				}
			%>
		</colgroup>
		<tr>
			<th>&nbsp;</th>
			<th>산출물 명</th>
<!-- 			<th>위치</th> -->
			<th>문서 명</th>
			<th>버전</th>
			<th>상태</th>
			<th>등록자</th>
			<th>첨부파일</th>
			<%
				if(dto.isReady()) {
			%>
			<th></th>
			<%
				}
			%>
		</tr>
		<%
		ArrayList<Output> outputs = dto.getOutputs();
		for (Output output : outputs) {
			String ooid = output.getPersistInfo().getObjectIdentifier().getStringValue();
			WTDocument doc = output.getDocument();
			String[] primary = null;
			if(doc != null) {
				primary = ContentUtils.getPrimary(doc);
			}
		%>
		<tr>
			<td class="first center">
				<%
				if (dto.isReady()) {
				%>
				<input type="checkbox" value="<%=ooid%>" id="ooid" name="ooid">
				<%
				} else {
				%>
				&nbsp;
				<%
				}
				%>
			</td>
			<td class="output-view" data-oid="<%=ooid%>"><%=output.getName()%></td>
<%-- 			<td class="ellipsis100"><%=output.getLocation()%></td> --%>
			<%
				if(doc != null) {
					String dd = doc.getPersistInfo().getObjectIdentifier().getStringValue();
			%>
			<td class="doc-view" data-oid="<%=dd%>"><%=doc.getName() %></td>
			<td class="center"><%=doc.getVersionIdentifier().getSeries().getValue() %>.<%=doc.getIterationIdentifier().getSeries().getValue() %></td>
			<td class="center"><%=doc.getLifeCycleState().getDisplay() %></td>
			<td class="center"><%=doc.getCreatorFullName() %></td>
			<td class="center"><%=primary[6] %></td>
			<%
				} else {
			%>
			<td colspan="5" class="center">
				<font color="red"><b>산출물 문서가 등록 되지 않았습니다.</b></font>
			</td>
			<%
				}
			%>
			<%
				if(dto.isReady()) {
			%>
			<td class="center">
				<%
				if (doc == null) {
				%>
				<button class="directBtn" data-oid="<%=ooid%>" data-loc="<%=output.getLocation()%>">직접등록</button>
				<button class="connectBtn" data-oid="<%=ooid%>" data-loc="<%=output.getLocation()%>">링크등록</button>
				<%
				} else {
				%>
				<button class="disconnectBtn" data-oid="<%=ooid%>">삭제</button>
				<%
				}
				%>
			</td>
			<%
				}
			%>
		</tr>
		<%
		}
		if (outputs.size() == 0) {
		%>
		<tr>
			<td class="first center" colspan="9">
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
	<%
	if (dto.isReady()) {
	%>
	<table class="button-table mt0">
		<tr>
			<td class="left">
				<button type="button" id="addPreTaskBtn">추가</button>
				<button type="button" id="deletePreTaskBtn">삭제</button>
			</td>
		</tr>
	</table>
	<%
	}
	%>
	<table class="view-table">
		<colgroup>
			<col width="40">
			<col width="*">
			<col width="80">
			<col width="80">
			<col width="100">
			<col width="100">
			<col width="100">
			<col width="100">
			<col width="80">
		</colgroup>
		<tr>
			<th>&nbsp;</th>
			<th>태스크명</th>
			<th>상태</th>
			<th>기간</th>
			<th>계획시작일</th>
			<th>계획종료일</th>
			<th>실제시작일</th>
			<th>실제종료일</th>
			<th>진행율</th>
		</tr>
		<%
		ArrayList<PreTaskPostTaskLink> pres = dto.getPres();
		for (PreTaskPostTaskLink link : pres) {
			Task pre = link.getPreTask();
			String loid = link.getPersistInfo().getObjectIdentifier().getStringValue();
			
		%>
		<tr>
			<td class="first center">
				<%
				if (dto.isReady()) {
				%>
				<input type="checkbox" value="<%=loid%>" name="pre">
				<%
				} else {
				%>
				&nbsp;
				<%
				}
				%>
			</td>
			<td class="task-view" data-oid="<%=pre.getPersistInfo().getObjectIdentifier().getStringValue()%>"><%=pre.getName()%></td>
			<td class="center"><%=pre.getState()%></td>
			<td class="center"><%=DateUtils.getDuration(pre.getPlanStartDate(), pre.getPlanEndDate())%>일</td>
			<td class="center"><%=pre.getPlanStartDate().toString().substring(0, 10)%></td>
			<td class="center"><%=pre.getPlanEndDate().toString().substring(0, 10)%></td>
			<td class="center"><%=pre.getStartDate() != null ? pre.getStartDate().toString().substring(0, 10) : ""%></td>
			<td class="center"><%=pre.getEndDate() != null ? pre.getEndDate().toString().substring(0, 10) : ""%></td>
			<td class="center"><%=pre.getProgress()%>%
			</td>
		</tr>
		<%
		}
		if (pres.size() == 0) {
		%>
		<tr>
			<td class="first center" colspan="9">
				<font color="red">
					<b>등록된 선행 태스크가 없습니다.</b>
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
		<span class="header">이슈</span>
	</div>
	<%
	if (dto.isReady()) {
	%>
	<table class="button-table mt0">
		<tr>
			<td class="left">
				<button type="button" id="addIssueBtn">추가</button>
				<button type="button" id="deleteIssueBtn">삭제</button>
			</td>
		</tr>
	</table>
	<%
	}
	%>
	<table class="view-table">
		<colgroup>
			<col width="40">
			<col width="*">
			<col width="80">
			<col width="80">
			<col width="100">
			<col width="100">
			<col width="100">
			<col width="120">
		</colgroup>
		<tr>
			<th>&nbsp;</th>
			<th>이슈 명</th>
			<th>이슈 타입</th>
			<th>상태</th>
			<th>담당자</th>
			<th>요청자</th>
			<th>요청일</th>
			<th>&nbsp;</th>
		</tr>
		<%
		ArrayList<Issue> issues = dto.getIssues();
		for (Issue issue : issues) {
			String ioid = issue.getPersistInfo().getObjectIdentifier().getStringValue();
		%>
		<tr>
			<td class="first center">
				<%
					if(dto.isReady()) {
				%>
				<input type="checkbox" value="<%=ioid%>" name="ioid">
				<%
					}
				%>
			</td>
			<td class="issue-view" data-oid="<%=ioid%>"><%=issue.getName()%></td>
			<td class="center"><%=BaseCodeHelper.manager.getNameByCodeTypeAndCode("ISSUETYPE", issue.getIssueType())%></td>
			<td class="center"><%=issue.getState()%></td>
			<td class="center"><%=issue.getManager() != null ? issue.getManager().getFullName() : ""%></td>
			<td class="center"><%=issue.getOwnership().getOwner().getFullName()%></td>
			<td class="center"><%=issue.getCreateTimestamp().toString().substring(0, 10)%></td>
			<td class="center">
				<%
					if(sessionUser.equals(issue.getManager()) && dto.isStart()) {
				%>
				<button class="solutionBtn" data-oid="<%=ioid%>">해결방안등록</button>
				<%
					}
				%>
			</td>
		</tr>
		<%
		}
		if (issues.size() == 0) {
		%>
		<tr>
			<td class="first center" colspan="8">
				<font color="red">
					<b>등록된 이슈가 없습니다.</b>
				</font>
			</td>
		</tr>
		<%
		}
		%>
	</table>
</body>
</html>