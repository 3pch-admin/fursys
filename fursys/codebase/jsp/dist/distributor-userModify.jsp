<%@page import="platform.raonk.entity.Raonk"%>
<%@page import="platform.util.CommonUtils"%>
<%@page import="platform.code.entity.BaseCode"%>
<%@page import="java.util.ArrayList"%>
<%@page import="platform.dist.entity.DistributorDTO" %>
<%@page import="platform.doc.service.DocumentHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
ArrayList<BaseCode> plant = (ArrayList<BaseCode>) request.getAttribute("plant");
DistributorDTO dto = (DistributorDTO) request.getAttribute("dto");
%>
<!-- hidden value -->
<input type="hidden" name="content" id="content">
<input type="hidden" name="oid"" value="<%=dto.getOid() %>">
<div class="header-title">
	<img src="/Windchill/jsp/images/home.png" class="home">
	<span>HOME</span>
	>
	<span>배포 관리</span>
	>
	<span>배포처 수정</span>
</div>

<table class="create-table">
	<colgroup>
		<col width="150">
		<col width="*">
	</colgroup>
	<tr>
		<th>배포처 구분</th>
		<td>
			<label>
				<input type="radio" name="type" value="IN">
				<span>사내</span>
			</label>
			&nbsp;
			<label>
				<input type="radio" name="type" value="OUT">
				<span>사외</span>
			</label>
		</td>
	</tr>
	
	<tr class="close name">
		<th>사업장</th>
		<td>
			<select name="plant" id="plant" class="AXSelect w200px">
				<option value="">선택</option>
				<%
				for (BaseCode c : plant) {
				%>
				<option value="<%=c.getName()%>" <%if (c.getName().equals(dto.getName())) {%> selected="selected" <%}%>><%=c.getName()%></option>
				<%
				}
				%>
			</select>
		</td>
	</tr>
	<tr class="close name">
		<th>사용자 아이디</th>
		<td>
			<input type="text" class="AXInput w30p" name="userId" readonly="readonly" value="<%=dto.getUserId() %>">
			<input type="hidden" class="AXInput w30p" name="umail" readonly="readonly">
		</td>
	</tr>
	<tr class="close name">
		<th>사용자명</th>
		<td>
			<input type="text" class="AXInput w30p" name="username" readonly="readonly" value="<%=dto.getUserName() %>">
		</td>
	</tr>
	<!-- 사외 -->
	<tr class="close plant">
		<th>업체명</th>
		<td>
			<input type="text" class="AXInput w60p" name="names" value="<%=dto.getName()%>">
		</td>
	</tr>
	<tr class="close plant">
		<th>사용자 아이디(이메일)</th>
		<td>
			<input type="text" class="AXInput w60p" name="email" value="<%=dto.getEmail()%>">
		</td>
	</tr>
	<tr class="close plant">
		<th>사용자명</th>
		<td>
			<input type="text" class="AXInput w60p" name="userName" value="<%=dto.getUserName()%>">
		</td>
	</tr>
	
	<tr>
		<th>설명</th>
		<td>
			<textarea rows="8" cols="" class="AXTextarea" name="description"><%=dto.getDescription()%></textarea>
		</td>
	</tr>
	<tr>
		<th>사용여부</th>
		<td>
			<select name="enable" id="enable" class="AXSelect w100px">
				<option value="">선택</option>
				<option value="true" selected="selected">사용</option>
				<option value="false">사용안함</option>
			</select>
		</td>
	</tr>
</table>

<table class="button-table">
	<tr>
		<td class="right">
			<button type="button" id="saveBtn">저장</button>
			<button type="button" id="closeBtn">닫기</button>
		</td>
	</tr>
</table>

<script type="text/javascript">
	$(function() {
		_check("type");
		_selector("enable");
		_selector("plant");
		$("#closeBtn").click(function() {
			self.close();
		})
		
		$("input[name=userId]").add("input[name=username]").click(function() {
			var url = "/Windchill/platform/user/popup?target=&callBack=distributor";
			_popup(url, 1200, 650, "n");
		})

		$("input[name=type]").click(function() {
			if ($(this).val() == "IN") {
				$(".name").show();
				$(".plant").hide();
				_selector("enable");
				_selector("plant");
			} else if ($(this).val() == "OUT") {
				$(".name").hide();
				$(".plant").show();
				_selector("enable");
				_selector("plant");
			}
		})

		$("#saveBtn").click(function() {

			if (!confirm("수정 하시겠습니까?")) {
				return false;
			}

			var params = _data($("#form"));
			var url = _url("/distributor/modify");
			console.log(params);
			_call(url, params, function(data) {
				opener.load();
				self.close();
			}, "POST");
		})
	})
	
	function distributor(userId, userName, email) {
		$("input[name=userId]").val(userId);
		$("input[name=username]").val(userName);
		$("input[name=umail]").val(email);
	}
</script>