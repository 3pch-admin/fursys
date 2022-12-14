<%@page import="platform.util.StringUtils"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="platform.util.DateUtils"%>
<%@page import="platform.project.task.entity.Task"%>
<%@page import="java.util.Map"%>
<%@page import="platform.util.CommonUtils"%>
<%@page import="platform.code.entity.BaseCode"%>
<%@page import="java.util.ArrayList"%>
<%@page import="platform.doc.service.DocumentHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
DecimalFormat df = new DecimalFormat("#,###");
Task task = (Task) request.getAttribute("task");
String oid = (String) request.getAttribute("oid");
boolean isLast = (boolean) request.getAttribute("isLast");
%>
<!-- hidden value -->
<input type="hidden" name="oid" value="<%=oid%>">
<div class="header-title">
	<img src="/Windchill/jsp/images/home.png" class="home">
	<span>HOME</span>
	>
	<span>프로젝트 관리</span>
	>
	<span>태스트 수정</span>
</div>

<table class="create-table">
	<colgroup>
		<col width="130">
		<col width="500">
		<col width="130">
		<col width="500">
	</colgroup>
	<tr>
		<th>
			태스크 명
			<span class="imp">*</span>
		</th>
		<td colspan="3">
			<input type="text" class="AXInput w60p" name="name" autofocus="autofocus" value="<%=task.getName()%>">
		</td>
	</tr>
	<tr>
		<th>
			계획시작일
			<span class="imp">*</span>
		</th>
		<td>
			<input type="text" class="AXInput w100px" name="planStartDate" value="<%=task.getPlanStartDate().toString().substring(0, 10)%>" id="planStartDate" <%if(!isLast) { %> readonly="readonly" <%} %>>
		</td>
		<th>
			계획종료일
			<span class="imp">*</span>
		</th>
		<td>
			<input type="text" class="AXInput w100px" name="planEndDate" value="<%=task.getPlanEndDate().toString().substring(0, 10)%>" id="planEndDate" onchange="compare();" onblur="compare();" <%if(!isLast) { %> readonly="readonly" <%} %>>
		</td>
	</tr>
	<tr>
		<th>기간</th>
		<td>
			<input type="text" class="AXInput w100px" value="<%=DateUtils.getDuration(task.getPlanStartDate(), task.getPlanEndDate())%>" name="duration" onchange="calculation();" onKeyup="this.value=this.value.replace(/[^-0-9]/g,'');" <%if(!isLast) { %> readonly="readonly" <%} %>>
			일
		</td>
		<th>노무비</th>
		<td>
			<input type="text" class="AXInput w200px" name="labor" onblur="comma(this);" onKeyup="this.value=this.value.replace(/[^-0-9]/g,'');" value="<%=df.format(task.getLabor())%>" <%if(!isLast) { %> readonly="readonly" <%} %>>
		</td>
	</tr>
	<tr>
	<tr>
		<th>재료비</th>
		<td>
			<input type="text" class="AXInput w200px" name="material" onblur="comma(this);" onKeyup="this.value=this.value.replace(/[^-0-9]/g,'');" value="<%=df.format(task.getMaterial())%>" <%if(!isLast) { %> readonly="readonly" <%} %>>
		</td>
		<th>기타</th>
		<td>
			<input type="text" class="AXInput w200px" name="etc" onblur="comma(this);" onKeyup="this.value=this.value.replace(/[^-0-9]/g,'');" value="<%=df.format(task.getEtc())%>" <%if(!isLast) { %> readonly="readonly" <%} %>>
		</td>
	</tr>
	<tr>
		<th>내용</th>
		<td colspan="3">
			<textarea rows="8" cols="" name="description" id="description" class="AXTextarea"><%=StringUtils.convertToStr(task.getDescription(), "") %></textarea>
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
			var name = $("input[name=name]");
			if (name.val() == "") {
				alert("태스크 명을 입력하세요.");
				name.focus();
				return false;
			}

			var planStartDate = $("input[name=planStartDate]");
			if (planStartDate.val() == "") {
				alert("계획시작일을 입력하세요.");
				return false;
			}

			if (!confirm("수정 하시겠습니까?")) {
				return false;
			}

			var url = _url("/task/modify");
			var params = _data($("#form"));
			_call(url, params, function(data) {
				opener.grid();
				self.close();
			}, "POST");
		})

		_date("planStartDate");
		_date("planEndDate");

	})

	function comma(obj) {
		var value = obj.value;
		obj.value = value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
	}

	function compare() {
		var start = $("input[name=planStartDate]").val();
		var end = $("input[name=planEndDate]").val();
		var sDate = new Date(start);
		var eDate = new Date(end);

		var diff = sDate.getTime() - eDate.getTime();
		var dateDiff = Math.ceil((eDate.getTime() - sDate.getTime()) / (1000 * 3600 * 24));

		if (dateDiff < 0) {
			alert("계획종료일이 계획시작일 보다 앞의 날이 될수 없습니다.");
			$("input[name=planEndDate]").val(start);
			return false;
		}
		$("input[name=duration]").val(dateDiff);
	}

	function calculation() {
		var start = $("input[name=planStartDate]").val();
		var du = $("input[name=duration]").val();
		var rs = new Date(start);
		var plan = new Date(rs.setDate(rs.getDate() + 1));
		console.log(plan);
		$("input[name=planEndDate]").val(plan.getFullYear() + "-" + (plan.getMonth() - 1) + "-" + plan.getDate());
	}
</script>