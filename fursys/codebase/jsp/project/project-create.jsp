<%@page import="java.util.Map"%>
<%@page import="platform.util.CommonUtils"%>
<%@page import="platform.code.entity.BaseCode"%>
<%@page import="java.util.ArrayList"%>
<%@page import="platform.doc.service.DocumentHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
// ArrayList<BaseCode> company = (ArrayList<BaseCode>) request.getAttribute("company");
ArrayList<BaseCode> pjtType = (ArrayList<BaseCode>) request.getAttribute("pjtType");
ArrayList<BaseCode> customer = (ArrayList<BaseCode>) request.getAttribute("customer");
ArrayList<Map<String, Object>> list = (ArrayList<Map<String, Object>>) request.getAttribute("list");
%>
<!-- hidden value -->
<div class="header-title">
	<img src="/Windchill/jsp/images/home.png" class="home">
	<span>HOME</span>
	>
	<span>프로젝트 관리</span>
	>
	<span>프로젝트 등록</span>
</div>

<table class="create-table">
	<colgroup>
		<col width="130">
		<col width="*">
	</colgroup>
	<tr>
		<th>
			프로젝트 명
			<span class="imp">*</span>
		</th>
		<td>
			<input type="text" class="AXInput w60p" name="name" autofocus="autofocus">
		</td>
	</tr>
	<tr>
		<th>
			프로젝트 유형
			<span class="imp">*</span>
		</th>
		<td>
			<select name="projectType" id="projectType" class="AXSelect w200px">
				<option value="">선택</option>
				<%
				for (BaseCode c : pjtType) {
				%>
				<option value="<%=c.getCode()%>"><%=c.getName()%></option>
				<%
				}
				%>
			</select>
		</td>
	</tr>
	<tr>
		<th>
			고객
			<span class="imp">*</span>
		</th>
		<td>
			<select name="customer" id="customer" class="AXSelect w200px">
				<option value="">선택</option>
				<%
				for (BaseCode c : customer) {
				%>
				<option value="<%=c.getCode()%>"><%=c.getName()%></option>
				<%
				}
				%>
			</select>
		</td>
	</tr>
	<tr>
		<th>
			PM
			<span class="imp">*</span>
		</th>
		<td>
			<input type="text" class="AXInput w200px" name="pm" id="pm">
		</td>
	</tr>
	<tr>
		<th>
			계획시작일
			<span class="imp">*</span>
		</th>
		<td>
			<input type="text" class="AXInput w100px" name="planStartDate" id="planStartDate">
		</td>
	</tr>
	<tr>
		<th>
			템플릿
		</th>
		<td>
			<select name="templateOid" id="templateOid" class="AXSelect w30p">
				<option value="">선택</option>
				<%
				for (Map<String, Object> map : list) {
				%>
				<option value="<%=map.get("value")%>"><%=map.get("name")%></option>
				<%
				}
				%>
			</select>
		</td>
	</tr>
	<tr>
		<th>
			예산(원)
			<span class="imp">*</span>
		</th>
		<td>
			<input type="text" maxlength="15" class="AXInput w200px" name="budget" onblur="comma(this);" onKeyup="this.value=this.value.replace(/[^-0-9]/g,'');">
		</td>
	</tr>
	<tr>
		<th>내용</th>
		<td>
			<textarea rows="11" cols="" name="description" id="description" class="AXTextarea"></textarea>
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

			var name = $("input[name=name]");
			if (name.val() == "") {
				alert("프로젝트 명을 입력하세요.");
				name.focus();
				return false;
			}

			var projectType = $("select[name=projectType]");
			if (projectType.val() == "") {
				alert("프로젝트 유형을 선택하세요.");
				return false;
			}

			var customer = $("select[name=customer]");
			if (customer.val() == "") {
				alert("고객을 선택하세요.");
				return false;
			}

			var pm = $("input[name=pm]");
			if (pm.val() == "") {
				alert("PM을 선택하세요.");
				return false;
			}

			var planStartDate = $("input[name=planStartDate]");
			if (planStartDate.val() == "") {
				alert("계획 시작일을 선택하세요.");
				return false;
			}

// 			var templateOid = $("select[name=templateOid]");
// 			if (templateOid.val() == "") {
// 				alert("템플릿을 선택하세요.");
// 				return false;
// 			}

			var budget = $("input[name=budget]");
			if (budget.val() == "") {
				alert("예산을 입력하세요.");
				budget.focus();
				return false;
			}

			if (!confirm("등록 하시겠습니까?")) {
				return false;
			}

			var params = _data($("#form"));
			var url = _url("/project/create");
			_call(url, params, function(data) {
				opener.load();
				self.close();
			}, "POST");
		})
		_selector("templateOid");
		_user("pm");
		_date("planStartDate");
		_selector("customer");
		_selector("projectType");
	})

	function comma(obj) {
		var value = obj.value;
		obj.value = value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
	}
</script>