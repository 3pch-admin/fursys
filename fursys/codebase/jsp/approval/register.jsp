<%@page import="platform.mbom.entity.MBOM"%>
<%@page import="platform.mbom.entity.MBOMECOLink"%>
<%@page import="wt.fc.PersistenceHelper"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="wt.fc.Persistable"%>
<%@page import="platform.echange.eco.entity.ECO"%>
<%@page import="platform.util.StringUtils"%>
<%@page import="platform.util.CommonUtils"%>
<%@page import="platform.approval.service.ApprovalHelper"%>
<%@page import="platform.approval.entity.LatestApprovalLine"%>
<%@page import="java.util.ArrayList"%>
<%@page import="wt.org.WTUser"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String oid = (String) request.getAttribute("oid");
Persistable per = (Persistable) CommonUtils.persistable(oid);
ECO eco = null;
ArrayList<WTUser> list = new ArrayList<>();
if (per instanceof ECO) {
	eco = (ECO) per;
	QueryResult result = PersistenceHelper.manager.navigate(eco, "mbom", MBOMECOLink.class);
	while (result.hasMoreElements()) {
		MBOM mbom = (MBOM) result.nextElement();
		list.add(mbom.getManager());
	}
}

WTUser sessionUser = (WTUser) request.getAttribute("sessionUser");
String moid = sessionUser.getPersistInfo().getObjectIdentifier().getStringValue();
String name = sessionUser.getFullName();
ArrayList<LatestApprovalLine> lines = ApprovalHelper.manager.getLatestLine();
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
		<col width="500">
		<col width="50">
		<col width="500">
		<col width="50">
		<col width="500">
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
<!-- 			<table> -->
<!-- 				<tr> -->
<!-- 					<td> -->
<!-- 						<label> -->
<!-- 							<input type="checkbox" id="me" name="me"> -->
<!-- 							내가 승인자입니다. -->
<!-- 						</label> -->
<!-- 					</td> -->
<!-- 				</tr> -->
<!-- 			</table> -->
		<td valign="top">&nbsp;</td>
		<td valign="top">
			<table>
				<tr>
					<td class="appMark">
						<font color="white">
							<b>2</b>
						</font>
					<td class="indent10">
						<b>경유자 지정</b>
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
				</colgroup>
				<tr>
					<th colspan="4">이름</th>
					<th>&nbsp;</th>
				</tr>
				<tbody id="viaLine">
					<tr class="viaTr0">
						<td colspan="4" class="left indent10 h40">
							<span class="viaSpan0"> </span>
						</td>
						<td class="center h40">
							<!-- 							<img src="/Windchill/jsp/images/delete.png" class="size30 hide delVia0" onclick="javascript:removeVia(viaTr, 6);"> -->
							<img src="/Windchill/jsp/images/group_add.gif" class="size30 imgVia0" onclick="javascript:addUser(6, 'b', 'via6', 'setVia');">
						</td>
					</tr>
				</tbody>
			</table>
			<table>
				<colgroup>
					<col width="110">
					<col width="*">
				</colgroup>
				<tr>
					<td>
						<h2>경유 옵션</h2>
					</td>
				</tr>
				<tr>
					<td class="right">경유완료기한일 :</td>
					<td class="left">
						&nbsp;
						<input type="text" class="AXInput w100px" id="appLimit" name="appLimit">
						일까지
					</td>
				</tr>
				<tr>
					<td class="right">경유시점 :</td>
					<td class="left">
						&nbsp;
						<select name="conn" id="conn" class="AXSelect w100px">
							<option value="">선택</option>
							<!-- 							<option value="5">승인</option> -->
							<option value="3">검토4</option>
							<option value="2">검토3</option>
							<option value="1">검토2</option>
							<option value="0">검토1</option>
							<option value="-1">작성</option>
						</select>
					</td>
				</tr>
			</table>
		</td>
		<td valign="top">&nbsp;</td>
		<td valign="top">
			<table>
				<tr>
					<td class="appMark">
						<font color="white">
							<b>3</b>
						</font>
					<td class="indent10">
						<b>수신자 지정</b>
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
				</colgroup>
				<tr>
					<th colspan="4">이름</th>
					<th>&nbsp;</th>
				</tr>
				<tbody id="recLine">
					<tr class="recTr0">
						<td colspan="4" class="left indent10 h40">
							<span class="recSpan0"> </span>
						</td>
						<td class="center h40">
							<!-- 							<img src="/Windchill/jsp/images/delete.png" class="size30 hide delRec0" onclick="javascript:removeRec;"> -->
							<img src="/Windchill/jsp/images/group_add.gif" class="size30 imgRec0" onclick="javascript:addUser(20, 'c', 'receive20', 'setReceive');">
						</td>
					</tr>
				</tbody>
			</table>
			<br>
			<table>
				<tr>
					<td class="appMark">
						<font color="white">
							<b>4</b>
						</font>
					<td class="indent10">
						<b>열람자 지정</b>
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
				</colgroup>
				<tr>
					<th colspan="4">이름</th>
					<th>&nbsp;</th>
				</tr>
				<tbody id="openLine">
					<tr class="openTr0">
						<td colspan="4" class="left indent10 h40">
							<span class="openSpan0"> </span>
						</td>
						<td class="center h40">
							<!-- 							<img src="/Windchill/jsp/images/delete.png" class="size30 hide del50" onclick="javascript:removeLine(20);"> -->
							<img src="/Windchill/jsp/images/group_add.gif" class="size30 imgOpen" onclick="javascript:addUser(20, 'd', 'receive20', 'setOpen');">
						</td>
					</tr>
				</tbody>
			</table>
		</td>
	</tr>
</table>
<br>
<br>
<table>
	<tr>
		<td>
			<table>
				<tr>
					<td class="appMark">
						<font color="white">
							<b>5</b>
						</font>
					<td class="indent10">
						<b>최근 결재선</b>
					</td>
				</tr>
			</table>
			<table class="app-reg-table">
				<colgroup>
					<col width="130">
					<col width="130">
					<col width="130">
					<col width="130">
					<col width="130">
					<col width="130">
					<col width="100">
				</colgroup>
				<tr>
					<th>작성</th>
					<th>검토1</th>
					<th>검토2</th>
					<th>검토3</th>
					<th>검토4</th>
					<th>승인</th>
					<th>&nbsp;</th>
				</tr>
				<%
				int idx = 0;
				for (LatestApprovalLine line : lines) {
					WTUser appUser = (WTUser) CommonUtils.persistable(line.getIndex5());
					WTUser index1 = null;
					String oid1 = null;
					WTUser index2 = null;
					String oid2 = null;
					WTUser index3 = null;
					String oid3 = null;
					WTUser index4 = null;
					String oid4 = null;
					if (StringUtils.isNotNull(line.getIndex1())) {
						index1 = (WTUser) CommonUtils.persistable(line.getIndex1());
						oid1 = index1.getFullName() + "&" + index1.getPersistInfo().getObjectIdentifier().getStringValue();
					}

					if (StringUtils.isNotNull(line.getIndex2())) {
						index2 = (WTUser) CommonUtils.persistable(line.getIndex2());
						oid2 = index2.getFullName() + "&" + index1.getPersistInfo().getObjectIdentifier().getStringValue();
					}

					if (StringUtils.isNotNull(line.getIndex3())) {
						index3 = (WTUser) CommonUtils.persistable(line.getIndex3());
						oid3 = index3.getFullName() + "&" + index1.getPersistInfo().getObjectIdentifier().getStringValue();
					}

					if (StringUtils.isNotNull(line.getIndex4())) {
						index4 = (WTUser) CommonUtils.persistable(line.getIndex4());
						oid4 = index4.getFullName() + "&" + index1.getPersistInfo().getObjectIdentifier().getStringValue();
					}
				%>
				<tr>
					<td class="center"><%=sessionUser.getFullName()%></td>
					<td class="center">
						<input type="hidden" name="index1_<%=idx%>" value="<%=index1 != null ? oid1 : ""%>">
						<%=index1 != null ? index1.getFullName() : ""%>
					</td>
					<td class="center">
						<input type="hidden" name="index2_<%=idx%>" value="<%=index2 != null ? oid2 : ""%>"><%=index2 != null ? index2.getFullName() : ""%></td>
					<td class="center">
						<input type="hidden" name="index3_<%=idx%>" value="<%=index3 != null ? oid3 : ""%>"><%=index3 != null ? index3.getFullName() : ""%></td>
					<td class="center">
						<input type="hidden" name="index4_<%=idx%>" value="<%=index4 != null ? oid4 : ""%>"><%=index4 != null ? index4.getFullName() : ""%></td>
					<td class="center">
						<input type="hidden" name="app_<%=idx%>" value="<%=appUser.getFullName()%>&<%=appUser.getPersistInfo().getObjectIdentifier().getStringValue()%>"><%=appUser.getFullName()%></td>
					<td class="center">
						<button type="button" class="selectBtn" data-idx="<%=idx%>">선택</button>
					</td>
				</tr>
				<%
				idx++;
				}
				%>
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
		
		$(".selectBtn").click(function() {
			resetLine(1);
			resetLine(2);
			resetLine(3);
			resetLine(4);
			resetLine(5);
			var idx = $(this).data("idx");
			var index1 = $("input[name=index1_" + idx + "]").val();
			var index2 = $("input[name=index2_" + idx + "]").val();
			var index3 = $("input[name=index3_" + idx + "]").val();
			var index4 = $("input[name=index4_" + idx + "]").val();
			var app = $("input[name=app_" + idx + "]").val();
			$(".img1").addClass("hide");
			if(index1 != "") {
				$(".del1").removeClass("hide");
				$(".span1").html("<input type='hidden' value='" + index1.split("&")[1] + "' name='exam1'>" + index1.split("&")[0]);
			}
			$(".img2").addClass("hide");			
			if(index2 != "") {
				$(".del2").removeClass("hide");
				$(".span2").html("<input type='hidden' value='" + index2.split("&")[1] + "' name='exam2'>" + index2.split("&")[0]);
			}

			$(".img3").addClass("hide");
			if(index3 != "") {
				$(".del3").removeClass("hide");
				$(".span3").html("<input type='hidden' value='" + index3.split("&")[1] + "' name='exam3'>" + index3.split("&")[0]);
			}
			$(".img4").addClass("hide");		
			if(index4 != "") {
				$(".del4").removeClass("hide");
				$(".span4").html("<input type='hidden' value='" + index4.split("&")[1] + "' name='exam4'>" + index4.split("&")[0]);
			}

			$(".img5").addClass("hide");
			if(app != "") {
				$(".del5").removeClass("hide");
				$(".td5").html("<input type='hidden' value='" + app.split("&")[1] + "' name='appUser'>" + app.split("&")[0]);
			}
			_selector("conn");
			_date("appLimit");
		})
		
		$("#saveBtn").click(function() {
			
			if($("input[name=appUser]").length == 0) {
				alert("승인자를 선택하세요.");
				return false;
			}
			
			$exam = $("input[name*=exam]");
			var exams = new Array();
			for(var i=0; i<$exam.length; i++) {
				exams.push($exam.val());
			}
			
			$via = $("input[name*=via]");
			var vias = new Array();
			for(var i=0; i<$via.length; i++) {
				vias.push($via.val());
			}
			
			$receive = $("input[name*=receive]");
			var receives = new Array();
			for(var i=0; i<$receive.length; i++) {
				receives.push($receive.val());
			}

			$open = $("input[name*=open]");
			var opens = new Array();
			for(var i=0; i<$open.length; i++) {
				opens.push($open.val());
			}
			
			if(vias.length > 0) {
				if($("input[name=appLimit]").val() == "") {
					alert("경유완료기한일 입력하세요.");
					return false;
				}
				
				if($("select[name=conn]").val() == "") {
					alert("경유 시점을 선택하세요.");
					return false;
				}
			}
			
			var params = new Object();
			params.exam = exams;
			params.via = vias;
			params.receive = receives;
			params.open = opens;
			params.appUser = $("input[name=appUser]").val();
			params.appLimit = $("input[name=appLimit]").val();
			params.conn = $("select[name=conn]").val();
			params.oid = "<%=oid%>";
// 			console.log(params);
			
			if(!confirm("결재를 기안 하시겠습니까?")) {
				return false;
			}
			
			var url = "/Windchill/platform/app/save";
			_call(url, params, function(data) {
				self.close();
				opener.load();
			}, "POST");
		})

		$("#closeBtn").click(function() {
			self.close();
		})

		_selector("conn");
		_date("appLimit");
		$("#me").checks();

		$("#me").click(function() {
			var str = "<%=name%>";
			var oid = "<%=moid%>";
			if ($("#me").prop("checked")) {
				$(".img1").addClass("hide");
				$(".img2").addClass("hide");
				$(".img3").addClass("hide");
				$(".img4").addClass("hide");
				$(".img5").addClass("hide");
				$(".td5").html("<input type='hidden' value='" + oid + "' name='appUser'>" + str);
			} else {
				// 처리..
				$(".img1").removeClass("hide");
				$(".img2").removeClass("hide")
				$(".img3").removeClass("hide")
				$(".img4").removeClass("hide")
				$(".td5").html("<img src='/Windchill/jsp/images/group_add.gif' class='size30 img5' onclick=\"javascript:addUser(5, 'a', 'appUser', 'setUser');\">");
			}
			_selector("conn");
			_date("appLimit");
		})
	})

	function resetLine(index) {
		$(".del" + index).hide();
		$(".img" + index).removeClass("hide");
		$(".span" + index).html("");
	}

	function setVia(items) {
		var len = $("img[class*=imgVia]").length;
		var arr = $("input[name*=via]");
		for (var i = 0; i < items.length; i++) {
			var item = items[i].item;
			var oid = item.woid;
			var bool = true;
			$.each(arr, function(idx) {
				var value = arr.eq(idx).val();
				if (value == oid) {
					bool = false;
					return true;
				}
			})
			if (bool) {
				var name = item.userName;
				$(".imgVia" + len).addClass("hide");
				$(".delVia" + len).show();
				var html = "";
				$body = $("#viaLine");
				html += "<tr class='viaTr" + len + "'>";
				html += "<td colspan='4' class='left indent10 h40'><span class='viaSpan" + len + "'></span></td>";
				html += "<td class='center h40'><img src='/Windchill/jsp/images/delete.png' class='size30 delVia" + len + "' onclick=\"javascript:removeVia('viaTr', " + len + ");\">";
				html += "<img src='/Windchill/jsp/images/group_add.gif' class='size30 hide imgVia" + len + "' onclick=\"javascript:addUser(" + len + ", 'b', 'via" + len + "', 'setVia');\"></td>";
				html += "</tr>";
				$body.prepend(html);
				$(".viaSpan" + len).html("<input type='hidden' value='" + oid + "' name='via" + len + "'>" + name);
				len++;
			}
		}
		_selector("conn");
		_date("appLimit");
	}

	function setReceive(items) {
		var len = $("img[class*=imgRec]").length;
		var arr = $("input[name*=receive]");
		for (var i = 0; i < items.length; i++) {
			var item = items[i].item;
			var oid = item.woid;
			var bool = true;
			$.each(arr, function(idx) {
				var value = arr.eq(idx).val();
				if (value == oid) {
					bool = false;
					return true;
				}
			})
			if (bool) {
				var name = item.userName;
				$(".imgRec" + len).addClass("hide");
				$(".delRec" + len).show();
				var html = "";
				$body = $("#recLine");
				html += "<tr class='recTr" + len + "'>";
				html += "<td colspan='4' class='left indent10 h40'><span class='recSpan" + len + "'></span></td>";
				html += "<td class='center h40'><img src='/Windchill/jsp/images/delete.png' class='size30 delRec" + len + "' onclick=\"javascript:removeRec('recTr', " + len + ");\">";
				html += "<img src='/Windchill/jsp/images/group_add.gif' class='size30 hide imgRec" + len + "' onclick=\"javascript:addUser(" + len + ", 'c', 'rec" + len + "', 'setReceive');\"></td>";
				html += "</tr>";
				$body.prepend(html);
				$(".recSpan" + len).html("<input type='hidden' value='" + oid + "' name='rec" + len + "'>" + name);
				len++;
			}
		}
		_selector("conn");
		_date("appLimit");
	}

	function setOpen(items) {
		var len = $("img[class*=imgOpen]").length;
		var arr = $("input[name*=open]");
		for (var i = 0; i < items.length; i++) {
			var item = items[i].item;
			var oid = item.woid;
			var bool = true;
			$.each(arr, function(idx) {
				var value = arr.eq(idx).val();
				if (value == oid) {
					bool = false;
					return true;
				}
			})
			if (bool) {
				var name = item.userName;
				$(".imgOpen" + len).addClass("hide");
				$(".delOpen" + len).show();
				var html = "";
				$body = $("#openLine");
				html += "<tr class='openTr" + len + "'>";
				html += "<td colspan='4' class='left indent10 h40'><span class='openSpan" + len + "'></span></td>";
				html += "<td class='center h40'><img src='/Windchill/jsp/images/delete.png' class='size30 delOpen" + len + "' onclick=\"javascript:removeOpen('openTr', " + len + ");\">";
				html += "<img src='/Windchill/jsp/images/group_add.gif' class='size30 hide imgOpen" + len + "' onclick=\"javascript:addUser(" + len + ", 'c', 'rec" + len + "', 'setOpen');\"></td>";
				html += "</tr>";
				$body.prepend(html);
				$(".openSpan" + len).html("<input type='hidden' value='" + oid + "' name='open" + len + "'>" + name);
				len++;
			}
		}
		_selector("conn");
		_date("appLimit");
	}

	function removeOpen(name, index) {
		$("." + name + index).remove();
		_selector("conn");
		_date("appLimit");
		var len = $("img[class*=imgOpen]").length;
		$(".imgOpen" + len).addClass("hide");
		$(".delOpen" + len).show();
	}

	function removeRec(name, index) {
		$("." + name + index).remove();
		_selector("conn");
		_date("appLimit");
		var len = $("img[class*=imgRec]").length;
		$(".imgRec" + len).addClass("hide");
		$(".delRec" + len).show();
	}

	function removeVia(name, index) {
		$("." + name + index).remove();
		_selector("conn");
		_date("appLimit");
		var len = $("img[class*=imgVia]").length;
		$(".imgVia" + len).addClass("hide");
		$(".delVia" + len).show();
	}

	function removeLine(name, index) {
		$("." + name + index).remove();
		_selector("conn");
		_date("appLimit");
	}

	function setUser(index, str, oid, name) {
		if (index != 20 && index != 5) {
			$(".img" + index).addClass("hide");
			$(".del" + index).show();
			$(".span" + index).html("<input type='hidden' value='" + oid + "' name='" + name + "'>" + str);
			// 			$(".td" + index).html("<input type='hidden' value='" + oid + "' name='" + name + "'>" + str);
		} else {
			$(".img" + index).addClass("hide");
			$(".del" + index).show();
			$(".td" + index).html("<input type='hidden' value='" + oid + "' name='" + name + "'>" + str);
			// 			$(".td" + index).html("<input type='hidden' value='" + oid + "' name='" + name + "'>" + str);
		}
	}

	function addUser(index, type, name, callBack) {
		var url = "/Windchill/platform/app/addUser?index=" + index + "&type=" + type + "&name=" + name + "&callBack=" + callBack;
		_popup(url, 1200, 650, "n");
	}
</script>