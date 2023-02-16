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
	<span>배포처 사용자 등록</span>
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
				<input type="radio" name="type" value="OUT" checked>
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
	<tr class="close name">
		<th>사용자 아이디</th>
		<td>
			<input type="text" class="AXInput w30p" name="userId" readonly="readonly">
			<input type="hidden" class="AXInput w30p" name="umail" readonly="readonly">
		</td>
	</tr>
	<tr class="close name">
		<th>사용자명</th>
		<td>
			<input type="text" class="AXInput w30p" name="username" readonly="readonly">
		</td>
	</tr>

	<!-- 사외 -->
	<tr class="factory">
		<th>배포처</th>
		<td>
			<input type="hidden" name="distributorOid" id="distributorOid">
			<input type="text" class="AXInput w70p" name="distributor" id="distributor">
			<i class="axi axi-close2 axicon deleteDistributor"></i>
		</td>
	</tr>
	<tr class="factory">
		<th>사용자 아이디(이메일)</th>
		<td>
			<input type="text" class="AXInput w60p" name="email">
		</td>
	</tr>
	<tr class="factory">
		<th>사용자명</th>
		<td>
			<input type="text" class="AXInput w60p" name="userName">
		</td>
	</tr>

	<tr>
		<th>설명</th>
		<td>
			<textarea rows="6" cols="" class="AXTextarea" name="description"></textarea>
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
		//_finder("distributor", "/distributor/popupList");
		_distributor("distributor");
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
				$(".factory").hide();
				_selector("enable");
				_selector("factory");
				_distributor("distributor");
			} else if ($(this).val() == "OUT") {
				$(".name").hide();
				$(".factory").show();
				_selector("enable");
				_selector("factory");
				_distributor("distributor");
			}
		})

		$("#saveBtn").click(function() {
			$type = $("input[name='type']:checked");
			$enable = $("input[name='enable']");
			
			$factory = $("select[name='factory']");
			$userId  = $("input[name=userId");
			//사내
			$distributor = $("input[name=distributor]");
			$email = $("input[name=email]");
			$userName = $("input[name=userName]");
			//사외
			
			if($type.val() == undefined){
				alert("배포처 구분을 선택하세요.");
				$type.focus();
				return false;
			}
			if($enable.val() == ""){
				alert("사용여부를 선택하세요.");
				$enable.focus();
				return false;
			}
			
			if($type.val() == "IN"){
				if($factory.val() == ""){
					alert("사업장을 선택하세요.");
					$factory.focus();
					return false;
				}
				if($userId.val()==""){
					alert("사용자 아이디를 선택하세요.");
					$userId.focus();
					return false;
				}
			} else {
				if($distributor.val()==""){
				alert("배포처를 입력하세요.");
				$distributor.focus();
				return false;
				}
				if($email.val()==""){
					alert("사용자 아이디를 입력하세요.");
					$email.focus();
					return false;
				}
				if($userName.val()==""){
					alert("사용자명을 입력하세요.");
					$userName.focus();
					return false;
				}
			}
			
			if (!confirm("등록 하시겠습니까?")) {
				return false;
			}

			var params = _data($("#form"));
			var url = _url("/distributor/userCreate");
			console.log(params);
			_call(url, params, function(data) {
				opener.load();
				self.close();
			}, "POST");
		})
		
	
	$("input[name=email]").blur(function() {
			var email_check = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
			var email = $(this).val();
			if (!email_check.test(email)) {
				alert("잘못된 형식의 이메일 주소입니다.");
				return false;
			}
		});

		$("input").checks();

		$(".deleteDistributor").click(function() {
			$("input[name=distributor]").val("");
		})
	})

	function distributor(userId, userName, email) {
		$("input[name=userId]").val(userId);
		$("input[name=username]").val(userName);
		$("input[name=umail]").val(email);
	}
	
	// distributor-popup-list 에서 배포처  
	function dist(name, oid ){
		$("input[name=distributor]").val(name);
		$("input[name=distributorOid]").val(oid);
		
	}
</script>