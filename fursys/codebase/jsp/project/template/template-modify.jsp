<%@page import="platform.util.StringUtils"%>
<%@page import="platform.project.template.entity.Template"%>
<%@page import="platform.util.CommonUtils"%>
<%@page import="platform.code.entity.BaseCode"%>
<%@page import="java.util.ArrayList"%>
<%@page import="platform.doc.service.DocumentHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
ArrayList<BaseCode> company = (ArrayList<BaseCode>) request.getAttribute("company");
Template template = (Template) request.getAttribute("template");
%>
<!-- hidden value -->
<input type="hidden" name="oid" value="<%=template.getPersistInfo().getObjectIdentifier().getStringValue() %>">
<div class="header-title">
	<img src="/Windchill/jsp/images/home.png" class="home">
	<span>HOME</span>
	>
	<span>템플릿 관리</span>
	>
	<span>템플릿 수정</span>
</div>

<table class="create-table">
	<colgroup>
		<col width="130">
		<col width="*">
	</colgroup>
	<tr>
		<th>사용여부</th>
		<td colspan="3">
			<input type="checkbox" name="enable" value="true" checked="checked">
		</td>
	</tr>
	<tr>
		<th>
			템플릿 명
			<span class="imp">*</span>
		</th>
		<td colspan="3">
			<input type="text" class="AXInput w60p" name="name" autofocus="autofocus" value="<%=template.getName()%>">
		</td>
	</tr>
	<tr>
		<th>
			회사
			<span class="imp">*</span>
		</th>
		<td colspan="3">
			<select name="company" id="company" class="AXSelect w200px">
				<option value="">선택</option>
				<%
				for (BaseCode c : company) {
				%>
				<option value="<%=c.getCode()%>" <%if (c.getCode().equals(template.getCompany())) {%> selected="selected" <%}%>><%=c.getName()%></option>
				<%
				}
				%>
			</select>
		</td>
	</tr>
	<tr>
		<th>설명</th>
		<td colspan="3">
			<textarea rows="15" cols="" name="description" id="description" class="AXTextarea"><%=StringUtils.convertToStr(template.getDescription(), "") %></textarea>
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
				alert("템플릿 명을 입력하세요.");
				name.focus();
				return false;
			}

			var company = $("select[name=company]");
			if (company.val() == "") {
				alert("회사를 선택하세요.");
				return false;
			}

			if (!confirm("등록 하시겠습니까?")) {
				return false;
			}

			var params = _data($("#form"));
			var url = _url("/template/modify");
			_call(url, params, function(data) {
				opener.grid();
				self.close();
			}, "POST");
		})
		_selector("company");
		$("input").checks();
	})
</script>