<%@page import="wt.org.WTUser"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String oid = (String) request.getAttribute("oid");
String line = (String) request.getAttribute("line");
WTUser sessionUser = (WTUser) request.getAttribute("sessionUser");
String moid = sessionUser.getPersistInfo().getObjectIdentifier().getStringValue();
String name = sessionUser.getFullName();
%>
<div class="header-title">
	<img src="/Windchill/jsp/images/home.png" class="home">
	<span>HOME</span>
	>
	<span>결재</span>
	>
	<span>결재선 지정</span>
</div>

<table>
	<colgroup>
		<col width="*">
	</colgroup>
	<tr>
		<td valign="top">
			<table>
				<tr>
					<td class="appMark">
						<font color="white">
							<b>1</b>
						</font>
					<td class="indent10">
						<b>작성부서 결재선</b>
					</td>
				</tr>
			</table>
			<table class="app-reg-table">
				<colgroup>
					<col width="100">
					<col width="100">
					<col width="100">
					<col width="100">
					<col width="100">
					<col width="100">
				</colgroup>
				<tr>
					<th rowspan="2">작성</th>
					<th colspan="4">검토</th>
					<th rowspan="2">승인</th>
				</tr>
				<tr>
					<th class="leftNone">1</th>
					<th>2</th>
					<th>3</th>
					<th>4</th>
				</tr>
				<tr>
					<td class="center h40"><%=sessionUser.getFullName()%></td>
					<td class="center h40 td1">
						<span class="span1"></span>
						<img src="/Windchill/jsp/images/group_add.gif" class="size30 img1" onclick="javascript:addUser(1, 'a', 'exam1', 'setUser');">
					</td>
					<td class="center h40 td2">
						<span class="span2"></span>
						<img src="/Windchill/jsp/images/group_add.gif" class="size30 img2" onclick="javascript:addUser(2, 'a', 'exam2', 'setUser');">
					</td>
					<td class="center h40 td3">
						<span class="span3"></span>
						<img src="/Windchill/jsp/images/group_add.gif" class="size30 img3" onclick="javascript:addUser(3, 'a', 'exam3', 'setUser');">
					</td>
					<td class="center h40 td4">
						<span class="span4"></span>
						<img src="/Windchill/jsp/images/group_add.gif" class="size30 img4" onclick="javascript:addUser(4, 'a', 'exam4', 'setUser');">
					</td>
					<td class="center h40 td5">
						<span class="span5"></span>
						<img src="/Windchill/jsp/images/group_add.gif" class="size30 img5" onclick="javascript:addUser(5, 'a', 'appUser', 'setUser');">
					</td>
				</tr>
				<tr>
					<td class="center h40">&nbsp;</td>
					<td class="center h40 deltd1">
						<img src="/Windchill/jsp/images/delete.png" class="size30 hide  del1" onclick="javascript:resetLine(1);">
					</td>
					<td class="center h40 deltd2">
						<img src="/Windchill/jsp/images/delete.png" class="size30 hide del2" onclick="javascript:resetLine(2);">
					</td>
					<td class="center h40 deltd3">
						<img src="/Windchill/jsp/images/delete.png" class="size30 hide del3" onclick="javascript:resetLine(3);">
					</td>
					<td class="center h40 deltd4">
						<img src="/Windchill/jsp/images/delete.png" class="size30 hide del4" onclick="javascript:resetLine(4);">
					</td>
					<td class="center h40 deltd5">
						<img src="/Windchill/jsp/images/delete.png" class="size30 hide del5" onclick="javascript:resetLine(5);">
					</td>
				</tr>
			</table>
			<br>
			<table>
				<tr>
					<td>
						<label>
							<input type="checkbox" id="me" name="me">
							내가 승인자입니다.
						</label>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<table class="button-table">
	<tr>
		<td class="right">
			<button type="button" id="saveBtn">결재</button>
			<button type="button" id="closeBtn">닫기</button>
		</td>
	</tr>
</table>
<script type="text/javascript">
	$(function() {
		
		$("#saveBtn").click(function() {
			$exam = $("input[name*=exam]");
			var exams = new Array();
			for(var i=0; i<$exam.length; i++) {
				exams.push($exam.val());
			}
			
			if($("input[name=appUser]").length == 0) {
				alert("승인자를 선택하세요.");
				return false;
			}
			
			var params = new Object();
			params.appUser = $("input[name=appUser]").val();
			params.oid = "<%=oid%>";
			params.line = "<%=line%>";
			params.exam = exams;
			if(!confirm("결재를 기안 하시겠습니까?")) {
				return false;
			}
			
			var url = "/Windchill/platform/app/receiveSave";
			_call(url, params, function(data) {
				self.close();
				opener.load();
			}, "POST");
		})

		$("#closeBtn").click(function() {
			self.close();
		})

		$("#me").checks();

		$("#me").click(function() {
			var str = "<%=name%>";
			var oid = "<%=moid%>";
			if ($("#me").prop("checked")) {
				$(".img1").hide();
				$(".img2").hide();
				$(".img3").hide();
				$(".img4").hide();
				$(".img5").hide();
				$(".td5").html("<input type='hidden' value='" + oid + "' name='appUser'>" + str);
			} else {
				// 처리..
				$(".img1").show();
				$(".img2").show();
				$(".img3").show();
				$(".img4").show();
				$(".td5").html("<img src='/Windchill/jsp/images/group_add.gif' class='size30 img5' onclick=\"javascript:addUser(5, 'a');\">");
			}
		})
	})

	function resetLine(index) {
		$(".del" + index).hide();
		$(".img" + index).removeClass("hide");
		$(".span" + index).html("");
	}

	function setUser(index, str, oid, name) {
		if (index != 20 && index != 5) {
			$(".img" + index).addClass("hide");
			$(".del" + index).show();
			$(".span" + index).html("<input type='hidden' value='" + oid + "' name='" + name + "'>" + str);
		} else {
			$(".img" + index).addClass("hide");
			$(".del" + index).show();
			$(".span" + index).html("<input type='hidden' value='" + oid + "' name='" + name + "'>" + str);
		}
	}

	function addUser(index, type, name, callBack) {
		var url = "/Windchill/platform/app/addUser?index=" + index + "&type=" + type + "&name=" + name + "&callBack=" + callBack;
		_popup(url, 700, 400, "n");
	}
</script>