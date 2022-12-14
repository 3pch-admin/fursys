<%@page import="platform.raonk.entity.Raonk"%>
<%@page import="platform.util.CommonUtils"%>
<%@page import="platform.code.entity.BaseCode"%>
<%@page import="java.util.ArrayList"%>
<%@page import="platform.dist.entity.DistributorDTO"%>
<%@page import="platform.doc.service.DocumentHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
// ArrayList<BaseCode> plant = (ArrayList<BaseCode>) request.getAttribute("plant");
DistributorDTO dto = (DistributorDTO) request.getAttribute("dto");
// String oid = (String) request.getAttribute("oid");
// DistributorDTO dto = (DistributorDTO) CommonUtils.persistable(oid);
%>
<!-- hidden value -->
<input type="hidden" name="content" id="content">
<input type="hidden" name="oid" value="<%=dto.getOid()%>">
<div class="header-title">
	<img src="/Windchill/jsp/images/home.png" class="home">
	<span>HOME</span>
	>
	<span>배포 관리</span>
	>
	<span>배포처 정보</span>
</div>

<table class="create-table">
	<colgroup>
		<col width="150">
		<col width="*">
	</colgroup>
	<tr>
		<th>배포처 구분</th>
		<td>
			<%=dto.getType().equals("OUT") ? "사외" : "사내"%>
		</td>
	</tr>
	<tr>
		<th>업체 코드</th>
		<td>
			<%=dto.getNumber()%>
		</td>
	</tr>
	<tr>
		<th>사업장/업체</th>
		<td>
			<%=dto.getName()%>
		</td>
	</tr>
	<tr>
		<th>사용여부</th>
		<td><%=dto.getEnable() == true ? "사용" : "사용안함"%></td>
	</tr>
</table>

<table class="button-table">
	<tr>
		<td class="right">
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