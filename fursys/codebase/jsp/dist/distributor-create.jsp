<%@page import="platform.raonk.entity.Raonk"%>
<%@page import="platform.util.CommonUtils"%>
<%@page import="platform.code.entity.BaseCode"%>
<%@page import="java.util.ArrayList"%>
<%@page import="platform.doc.service.DocumentHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
ArrayList<BaseCode> factory = (ArrayList<BaseCode>) request.getAttribute("factory");
%>
<!-- hidden value -->
<input type="hidden" name="content" id="content">
<div class="header-title">
	<img src="/Windchill/jsp/images/home.png" class="home">
	<span>HOME</span>
	>
	<span>배포 관리</span>
	>
	<span>배포처 등록</span>
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
				사내
			</label>
			&nbsp;
			<label>
				<input type="radio" name="type" value="OUT">
				사외
			</label>
		</td>
	</tr>
	<tr class="close name">
		<th>사업장</th>
		<td>
			<select name="factory" id="factory" class="AXSelect w200px">
				<option value="">선택</option>
				<%
				for (BaseCode c : factory) {
				%>
				<option value="<%=c.getCode()%>"><%=c.getName()%></option>
				<%
				}
				%>
			</select>
		</td>
	</tr>
	<!-- 사외 -->
	<!-- 	<tr class="close factory"> -->
	<!-- 		<th>업체코드</th> -->
	<!-- 		<td> -->
	<!-- 			<input type="text" readonly="readonly" class="AXInput w40p" placeholder="자동생성"> -->
	<!-- 		</td> -->
	<!-- 	</tr> -->
	<tr class="close factory">
		<th>업체명</th>
		<td>
			<input type="text" class="AXInput w40p" name="names">
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
			<button type="button" id="saveBtn">등록</button>
			<button type="button" id="closeBtn">닫기</button>
		</td>
	</tr>
</table>

<script type="text/javascript">
	$(function() {
		_selector("enable");
		_selector("factory");
		$("#closeBtn").click(function() {
			self.close();
		})

		$("input[name=userId]").add("input[name=username]").click(function() {
			var url = "/Windchill/platform/user/popup?target=&callBack=distributor";
			_popup(url, 1200, 650, "n");
		})

		$("input:radio[name=type]").click(function() {
			if ($(this).val() == "IN") {
				$(".name").show();
				$(".factory").hide();
				_selector("enable");
				_selector("factory");
			} else if ($(this).val() == "OUT") {
				$(".name").hide();
				$(".factory").show();
				_selector("enable");
				_selector("factory");
			}
		})

		$("#saveBtn").click(function() {
			$names = $("input[name=names]");
			$factory = $("select[name=factory]");
			$types = $("input[name='type']:checked");

			if ($types.val() == undefined) {
				alert("배포처 구분을 선택하세요.");
				$types.focus();
				return false;
			}

			if ($types.val() == "OUT") {
				if ($names.val() == "") {
					alert("업체명을 입력하세요");
					$names.focus();
					return false;
				}
			}

			if ($types.val() == "IN") {
				if ($factory.val() == "") {
					alert("사업장을 선택하세요.");
					$factory.focus();
					return false;
				}
			}

			if (!confirm("등록 하시겠습니까?")) {
				return false;
			}

			var params = _data($("#form"));
			var url = _url("/distributor/create");
			console.log(params);
			_call(url, params, function(data) {
				opener.load();
				self.close();
			}, "POST");
		})

		$("input").checks();
	})

	function distributor(userId, userName, email) {
		$("input[name=userId]").val(userId);
		$("input[name=username]").val(userName);
		$("input[name=umail]").val(email);
	}
</script>