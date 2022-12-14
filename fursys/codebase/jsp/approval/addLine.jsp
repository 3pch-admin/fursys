<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- hidden value -->
<div class="header-title">
	<img src="/Windchill/jsp/images/home.png" class="home">
	<span>HOME</span>
	>
	<span>작업공간</span>
	>
	<span>결재선 지정</span>
</div>

<table id="wrap-table">
	<colgroup>
		<col width="350">
		<col width="20">
		<col width="150">
		<col width="20">
		<col width="*">
	</colgroup>
	<tr>
		<td valign="top">
			<table class="w100p">
				<tr>
					<td id="org" title="조직도">조직도</td>
					<td id="user" title="사용지" class="onView">사용자</td>
					<td id="line" title="개인결재선">개인결재선</td>
				</tr>
			</table>
			<table id="orgTable">
				<!-- 				<tr> -->
				<!-- 					<td class="h5px"></td> -->
				<!-- 				</tr> -->
				<tr>
					<td colspan="3" style="border-right: 1px solid #b8b8b8; border-left: 1px solid #b8b8b8; border-bottom: 1px solid #b8b8b8;">
						<div id="dept" style="height: 330px;"></div>
					</td>
				</tr>
				<tr>
					<td class="h5px"></td>
				</tr>
				<tr>
					<td>
						<select multiple="multiple" name="deptUserList" id="deptUserList"></select>
					</td>
				</tr>
			</table>
			<table id="userTable">
				<tr>
					<td class="h5px"></td>
				</tr>
				<tr>
					<td class="right">
						<input type="text" name="key" class="AXInput w200px" autofocus="autofocus">
						<button type="button" id="searchBtn" class="rpost2">조회</button>
					</td>
				</tr>
				<tr>
					<td class="h5px"></td>
				</tr>
				<tr>
					<td>
						<select multiple="multiple" name="userList" id="userList"></select>
					</td>
				</tr>
			</table>
			<table id="lineTable">
				<tr>
					<td class="h5px"></td>
				</tr>
				<tr>
					<td class="right">
						<input type="text" name="name" class="AXInput w20px">
						<button type="button" id="save" class="rpost2">저장</button>
						<button type="button" id="delete" class="rpost2">삭제</button>
					</td>
				</tr>
				<tr>
					<td class="h5px"></td>
				</tr>
				<tr>
					<td>
						<select multiple="multiple" name="lineList" id="lineList"></select>
					</td>
				</tr>
			</table>
		</td>
		<td>&nbsp;</td>
		<td>
			<table class="typeTable">
				<tr>
					<td class="center types">결재타입</td>
				</tr>
				<tr>
					<td class="center p4">
						<label>
							<input type="radio" name="type" value="app" checked="checked">
							결재
						</label>
					</td>
				</tr>
				<tr>
					<td class="center p4">
						<label>
							<input type="radio" name="type" value="via">
							경유
						</label>
					</td>
				</tr>
				<tr>
					<td class="center p4">
						<label>
							<input type="radio" name="type" value="ref">
							참조
						</label>
					</td>
				</tr>
				<tr>
					<td class="center p4">
						<button type="button" id="selectedDel" onclick="javascript:deleteLine();">선택삭제</button>
					</td>
				</tr>
				<tr>
					<td class="center p4">
						<button type="button" id="resetAll" onclick="javascript:lineReset(true);">전체삭제</button>
					</td>
				</tr>
			</table>
		</td>
		<td>&nbsp;</td>
		<td valign="top">
			<table class="app_layer_table">
				<tr>
					<td>
						<div class="tabNav">■ 결재 및 공유</div>
					</td>
					<td class="right">
						<div>
							<span class="limitText">의견만기일 :</span>
							<input type="text" name="limit" class="AXInput w100px">
						</div>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<div class="line_layer_view">
							<table class="app-table" id="app_table">
								<colgroup>
									<col width="40">
									<col width="80">
									<col width="*">
									<col width="100">
									<col width="100">
									<col width="130">
									<col width="300">
								</colgroup>
								<thead>
									<tr>
										<th>
											<input type="checkbox" name="allApp" id="allApp">
										</th>
										<th>결재형식</th>
										<th>이름</th>
										<th>아이디</th>
										<th>직급</th>
										<th>부서</th>
										<th>이메일</th>
									</tr>
								</thead>
								<tbody id="appBody">
								</tbody>
							</table>
						</div>
					</td>
				</tr>
			</table>


			<table class="ref_layer_table">
				<tr>
					<td>
						<div class="tabNav">■ 참조</div>
					</td>
				</tr>
				<tr>
					<td>
						<div class="ref_layer_view">
							<table class="app-table" id="ref_table">
								<colgroup>
									<col width="60">
									<col width="*">
									<col width="100">
									<col width="100">
									<col width="100">
									<col width="250">
								</colgroup>
								<thead>
									<tr>
										<th>
											<input type="checkbox" name="allRef" id="allRef">
										</th>
										<th>이름</th>
										<th>아이디</th>
										<th>직급</th>
										<th>부서</th>
										<th>이메일</th>
									</tr>
								</thead>
								<tbody id="refBody">
								</tbody>
							</table>
						</div>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<table class="button-table">
	<tr>
		<td class="right">
			<button type="button" id="applyBtn">적용</button>
			<button type="button" id="closeBtn">닫기</button>
		</td>
	</tr>
</table>
<script type="text/javascript">
	$(function() {
		openerLine();
		$("input[name=limit]").bindDate({
			align : "left",
			valign : "top",
			buttonText : "확인",
			customPos : {
				top : 25,
				left : 25
			},
		});

		$("#closeBtn").click(function() {
			self.close();
		})

		$("#applyBtn").click(function() {

			if ($("input[name=appOid]").length == 0) {
				alert("최소 하나 이상의 결재라인이 추가 되어야합니다.");
				return false;
			}

			$openerAppBody = $("#appBody", opener.document);
			$appValue = $("input[name=appValue]");
			for (var i = $openerAppBody.length; i >= 0; i--) {
				$openerAppBody.eq(i).children().remove();
			}

			var cnt = 0;
			for (var i = 0; i < $appValue.length; i++) {
				var value = $appValue.eq(i).val().split("&"); // oid,woid,name,username,duty,deptName,email
				var type = value[7];
				$html = "<tr>";
				if (type == "app") {
					type = "결재";
					$html += "<td class='center'><font color='red'><b>" + type + "</b></font></td>";
				} else if (type == "via") {
					var limit = $("input[name=limit]");
					if (limit.val() == "") {
						alert("경유에 지정된 사용자가 있을 경우 의견만기일은 필수 선택입니다.");
						return false;
					}
					type = "경유";
					$html += "<td class='center'><font color='blue'><b>" + type + "</b></font></td>";
				}

				$html += "<td class='center'><input type='hidden' name='appValue' value='" + $appValue.eq(i).val() + "'><input type='hidden' name='appOid' value='" + value[0]+"&"+type + "'>" + value[2] + "</td>";
				$html += "<td class='center'>" + value[3] + "</td>";
				$html += "<td class='center'>" + value[4] + "</td>";
				$html += "<td class='center'>" + value[5] + "</td>";
				$html += "<td class='center'>" + value[6] + "</td>";
				$html += "</tr>";
				$openerAppBody.append($html);
			}

			$openerRefBody = $("#refBody", opener.document);
			$refValue = $("input[name=refValue]");
			for (var i = $openerRefBody.length; i >= 0; i--) {
				$openerRefBody.eq(i).children().remove();
			}

			for (var i = 0; i < $refValue.length; i++) {
				var value = $refValue.eq(i).val().split("&"); // oid,woid,name,username,duty,deptName,email
				$html = "<tr>";
				$html += "<td class='center'><input type='hidden' name='refValue' value='" + $refValue.eq(i).val() + "'><input type='hidden' name='refOid' value='" + value[0] + "'>" + value[2] + "</td>";
				$html += "<td class='center'>" + value[3] + "</td>";
				$html += "<td class='center'>" + value[4] + "</td>";
				$html += "<td class='center'>" + value[5] + "</td>";
				$html += "<td class='center'>" + value[6] + "</td>";
				$html += "</tr>";
				$openerRefBody.append($html);
			}

			var limit = $("input[name=limit]");
			$openerLimit = $("#appLimit", opener.document);
			$openerLimit.val(limit.val());
			self.close();
		})

		// 기본 사용자 로드..
		var value = $("input[name=key]").val();
		loadUserList(value);

		$("#orgTable").hide();
		$("#userTable").show();
		$("#lineTable").hide();

		$("#user").click(function() {
			$("#org").removeClass("onView");
			$("#user").addClass("onView");
			$("#line").removeClass("onView");

			$("#orgTable").hide();
			$("#userTable").show();
			$("#lineTable").hide();

			$("input[name=key]").focus();
			var value = $("input[name=key]").val();
			loadUserList(value);
		})

		$("#org").click(function() {
			$("#org").addClass("onView");
			$("#user").removeClass("onView");
			$("#line").removeClass("onView");

			$("#orgTable").show();
			$("#userTable").hide();
			$("#lineTable").hide();

		})

		$("#line").click(function() {
			$("#org").removeClass("onView");
			$("#user").removeClass("onView");
			$("#line").addClass("onView");

			$("#orgTable").hide();
			$("#userTable").hide();
			$("#lineTable").show();
			$("input[name=name]").focus();
			// 					loadUserLine();
		})

		// 		$("#app_table").tableHeadFixer();

		// 		$("#ref_table").tableHeadFixer();

		// 		$("#via_table").tableHeadFixer();

		$("input[type=checkbox]").checks();
		$("input[type=radio]").checks();
		$("#dept").fancytree({
			click : function(e, data) {
				var oid = data.node.data.oid;
				deptUser(oid);
			},
			focusOnSelect : false,
			source : $.ajax({
				url : "/Windchill/platform/dept/tree",
				type : "POST",
				dataType : "JSON"
			})
		})

		$(document).on("dblclick", "#lineList option", function() {
			var oid = $(this).val();
			if (!confirm("선택한 결재선을 적용합니다.\기존 설정된 결재선은 모두 삭제가 되어집니다.")) {
				return false;
			}
			lineReset(false);
			setUserLine();
		})

		$(document).on("dblclick", "#userList option", function() {
			var oid = $(this).val();
			var type = $("input[name='type']:checked").val();
			if (type === undefined) {
				alert("선택할 결재타입을 먼저 선택하세요.");
				return false;
			}
			callLine(oid, type, "GET");
		})

		$(document).on("dblclick", "#deptUserList option", function() {
			var oid = $(this).val();
			var type = $("input[name='type']:checked").val();
			if (type === undefined) {
				alert("선택할 결재타입을 먼저 선택하세요.");
				return false;
			}
			callLine(oid, type, "GET");
		})

		$("#allApp").click(function() {
			$check = $(document).find("input[name=appOid]");
			$value = $(this).prop("checked");
			$.each($check, function(idx) {
				if ($value) {
					$check.next().addClass("sed");
				} else {
					$check.next().removeClass("sed");
				}
				$check.eq(idx).prop("checked", $value);
			})
		})

		$("#allRef").click(function() {
			$check = $(document).find("input[name=refOid]");
			$value = $(this).prop("checked");
			$.each($check, function(idx) {
				if ($value) {
					$check.next().addClass("sed");
				} else {
					$check.next().removeClass("sed");
				}
				$check.eq(idx).prop("checked", $value);
			})
		})

		$("#search").click(function() {
			var value = $("input[name=key]").val();
			loadUserList(value);
		});

		$("#save").click(function() {
			$name = $("input[name=name]");
			if ($name.val() == "") {
				alert("개인결재선 이름을 입력하세요.");
				return false;
			}

			if (!confirm("저장 하시겠습니까?")) {
				return false;
			}

			var url = "/Windchill/fursys/app/saveUserLine";
			var params = new Object();
			params.name = $name.val();
			params = $(document).line(params);
			$(document)._call(url, params, function(data) {
				alert("개인 결재선이 저장 되었습니다.");
				loadUserLine();
			})
		})

		$.fn.line = function(params) {
			$app = $("input[name=appValue]");
			$ref = $("input[name=refValue]");

			var apps = new Array();
			$.each($app, function(idx) {
				$value = $app.eq(idx).val();
				apps.push($value);
			})
			params["apps"] = apps;

			var refs = new Array();
			$.each($ref, function(idx) {
				$value = $ref.eq(idx).val();
				refs.push($value);
			})
			params["refs"] = refs;
			return params;
		}

		$("#delete").click(function() {

			$options = $("#lineList option");
			var oid;
			$.each($options, function(idx) {
				if ($options.eq(idx).prop("selected") == true) {
					oid = $options.eq(idx).val();
					return false;
				}
			})

			if (oid === undefined) {
				alert("삭제할 개인 결재선을 선택하세요.");
				return false;
			}

			if (!confirm("선택한 개인 결재선을 삭제하시겠습니까?")) {
				return false;
			}

			var url = "/Windchill/fursys/app/deleteUserLine?oid=" + oid;
			var params = new Object();
			$(document)._call(url, params, function(data) {
				alert("개인 결재선이 삭제 되었습니다.");
				loadUserLine();
			})
		})
	}).keypress(function(e) {
		if (e.keyCode == 13) {
			var value = $("input[name=key]").val();
			loadUserList(value);
		}
	})

	function setUserLine() {
		$options = $("#lineList option");
		var cnt = 0;
		var appList;
		var refList;
		$.each($options, function(idx) {
			if ($options.eq(idx).prop("selected") == true) {
				cnt++;
				appList = $options.eq(idx).data("app");
				refList = $options.eq(idx).data("ref");
			}
		})

		$appBody = $("#appBody");
		$refBody = $("#refBody");

		$appLen = appList.split(",").length;
		$refLen = refList.split(",").length;

		$appHtml = "";
		for (var i = 0; i < $appLen; i++) {
			var value = appList.split("&");
			var oid = value[0];
			var name = value[2];
			var username = value[3];
			var duty = value[4];
			var deptName = value[5];
			var email = value[6];
			$appHtml += "<tr>";
			$appHtml += "<td class='center'><input type='hidden' name='appValue' value='" + appList + "'><input type='checkbox' name='appOid' value='" + oid + "'></td>";
			$appHtml += "<td class='center'>" + (i + 1) + "</td>";
			$appHtml += "<td class='center'>" + name + "</td>";
			$appHtml += "<td class='center'>" + username + "</td>";
			$appHtml += "<td class='center'>" + duty + "</td>";
			$appHtml += "<td class='center'>" + deptName + "</td>";
			$appHtml += "<td class='center'>" + email + "</td>";
			$appHtml += "</tr>";
		}
		$appBody.append($appHtml);
		_setBoxs("input[name=appOid]");

		$refHtml = "";
		for (var i = 0; i < $refLen; i++) {
			var value = refList.split("&");
			var oid = value[0];
			var name = value[2];
			var username = value[3];
			var duty = value[4];
			var deptName = value[5];
			var email = value[6];
			$refHtml += "<tr>";
			$refHtml += "<td class='center'><input type='hidden' name='refValue' value='" + refList + "'><input type='checkbox' name='refOid' value='" + oid + "'></td>";
			$refHtml += "<td class='center'>" + name + "</td>";
			$refHtml += "<td class='center'>" + username + "</td>";
			$refHtml += "<td class='center'>" + duty + "</td>";
			$refHtml += "<td class='center'>" + deptName + "</td>";
			$refHtml += "<td class='center'>" + email + "</td>";
			$refHtml += "</tr>";
		}
		$refBody.append($refHtml);
		_setBoxs("input[name=refOid]");
	}

	function loadUserLine() {
		var url = "/Windchill/fursys/app/getUserLine";
		var params = new Object();
		$(document)._call(url, params, function(data) {
			var list = data.list;
			$("#lineList option").remove();
			for (var i = 0; i < list.length; i++) {
				var oid = list[i].oid;
				var name = list[i].name;
				var appList = list[i].appList;
				var refList = list[i].refList;
				$("#lineList").append("<option data-app=\"" + appList + "\" data-ref=\"" + refList + "\" value=\""+oid+"\">" + name + "</option>");
			}
		})
	}

	function loadUserList(value) {
		var url = "/Windchill/platform/user/info";
		var params = new Object();
		params.key = value;
		_call(url, params, function(data) {
			var info = data.info;
			$("#userList option").remove();
			var len = info.length;
			for (var i = 0; i < len; i++) {
				$("#userList").append("<option value=\"" + info[i].oid + "\">" + info[i].userName + " [" + info[i].deptName + "]</option>");
			}
		}, "POST")
	}

	function callLine(oid, type, call) {
		var url = "/Windchill/platform/user/info?oid=" + oid;
		var params = new Object();
		_call(url, null, function(data) {
			var info = data.info;
			setLines(info, type);
		}, "GET");
	}

	function setLines(info, type) {
		if (type == "app" || type == "via") {
			setAppLine(info, type);
		} else if (type == "ref") {
			setRefLine(info);
		}
	}

	// 결재 라인 체크
	function checkLine(oid) {
		var bool = false;
		$app = $("input[name=appOid]");
		$.each($app, function(idx) {
			if (oid == $app.eq(idx).val()) {
				alert("이미 결재라인에 지정된 사용자 입니다.");
				bool = true;
				return false;
			}
		})

		$ref = $("input[name=refOid]");
		$.each($ref, function(idx) {
			if (oid == $ref.eq(idx).val()) {
				alert("이미 참조라인에 지정된 사용자 입니다.");
				bool = true;
				return false;
			}
		})
		return bool;
	}

	// 결재 라인 set
	function setAppLine(info, type) {
		$app = $("input[name=appOid]");

		var check = checkLine(info.oid);
		if (check) {
			return false;
		}

		var comp;
		$.each($app, function(idx) {
			if (info.oid == $app.eq(idx).val()) {
				comp = true;
				return true;
			}
		})

		if (comp) {
			return false;
		}

		$body = $("#appBody");
		$html = "";
		$html += "<tr>";
		$html += "<td class='center'><input type='hidden' name='appValue' value='" + (info.to + type) + "'><input type='checkbox' name='appOid' value='" + info.oid + "'></td>";

		if (type == "app") {
			$html += "<td class='center'><font color='red'><b>결재</b></font></td>";
		} else if (type == "via") {
			$html += "<td class='center'><font color='blue'><b>경유</b></font></td>";
		}

		$html += "<td class='center'>" + info.userName + "</td>";
		$html += "<td class='center'>" + info.userId + "</td>";
		$html += "<td class='center'>" + info.duty + "</td>";
		$html += "<td class='center'>" + info.deptName + "</td>";
		$html += "<td class='center'>" + info.email + "</td>";
		$html += "</tr>";
		$body.append($html);
		_setBoxs("input[name=appOid]");
	}

	// 참조 라인 set
	function setRefLine(info) {
		$ref = $("input[name=refOid]");

		var check = checkLine(info.oid);
		if (check) {
			return false;
		}

		var comp;
		$.each($ref, function(idx) {
			if (info.oid == $ref.eq(idx).val()) {
				comp = true;
				return true;
			}
		})

		if (comp) {
			return false;
		}

		$body = $("#refBody");
		$html = "";
		$html += "<tr>";
		$html += "<td class='center'><input type='hidden' name='refValue' value='" + info.to + "'><input type='checkbox' name='refOid' value='" + info.oid + "'></td>";
		$html += "<td class='center'>" + info.userName + "</td>";
		$html += "<td class='center'>" + info.userId + "</td>";
		$html += "<td class='center'>" + info.duty + "</td>";
		$html += "<td class='center'>" + info.deptName + "</td>";
		$html += "<td class='center'>" + info.email + "</td>";
		$html += "</tr>";
		$body.append($html);
		_setBoxs("input[name=refOid]");
	}
	function deptUser(oid) {
		var params = new Object();
		params.oid = oid;
		var url = "/Windchill/platform/dept/deptUser";
		_call(url, params, function(data) {
			var info = data.info;
			setDeptOptionList(info);
		}, "POST")
	}

	function setDeptOptionList(info) {
		$("#deptUserList option").remove();
		var len = info.length;
		for (var i = 0; i < len; i++) {
			$("#deptUserList").append("<option value=\"" + info[i].oid + "\">" + info[i].userName + " [" + info[i].deptName + "]</option>");
		}
	}

	function deleteLine() {
		$appOid = $("input[name=appOid]");
		$refOid = $("input[name=refOid]");

		for (var i = 0; i < $appOid.length; i++) {
			if ($appOid.eq(i).prop("checked")) {
				$appOid.eq(i).parent().parent().remove();
			}
		}

		for (var i = 0; i < $refOid.length; i++) {
			if ($refOid.eq(i).prop("checked")) {
				$refOid.eq(i).parent().parent().remove();
			}
		}

		$("input[name=appAll]").prop("checked", false);
		$("input[name=allApp]").next().removeClass("sed");
		$("input[name=allRef]").prop("checked", false);
		$("input[name=allRef]").next().removeClass("sed");
	}

	function lineReset(opt) {
		if (opt) {
			if (!confirm("모든 결재라인을 삭제 하시겠습니까?")) {
				return false;
			}
		}

		$appOid = $("input[name=appOid]");
		$refOid = $("input[name=refOid]");

		for (var i = 0; i < $appOid.length; i++) {
			$appOid.eq(i).parent().parent().remove();
		}

		for (var i = 0; i < $refOid.length; i++) {
			$refOid.eq(i).parent().parent().remove();
		}

		$("input[name=appAll]").prop("checked", false);
		$("input[name=allApp]").next().removeClass("sed");
		$("input[name=allRef]").prop("checked", false);
		$("input[name=allRef]").next().removeClass("sed");
	}

	function openerLine() {

		$limit = $(opener.document).find("input[name=appLimit]");
		$("input[name=limit]").val($limit.val());

		$appLen = $(opener.document).find("input[name=appValue]").length;
		$refLen = $(opener.document).find("input[name=refValue]").length;

		$appList = $(opener.document).find("input[name=appValue]");
		$refList = $(opener.document).find("input[name=refValue]");

		$appBody = $("#appBody");
		$appHtml = "";
		for (var i = 0; i < $appLen; i++) {
			var value = $appList.eq(i).val().split("&");
			console.log(value);
			var oid = value[0];
			var name = value[2];
			var username = value[3];
			var duty = value[4];
			var deptName = value[5];
			var email = value[6];
			$appHtml += "<tr>";
			$appHtml += "<td class='center'><input type='hidden' name='appValue' value='" + $appList.eq(i).val() + "'><input class='isBox' type='checkbox' name='appOid' value='" + oid + "'></td>";
			$appHtml += "<td class='center'>" + (i + 1) + "</td>";
			$appHtml += "<td class='center'>" + name + "</td>";
			$appHtml += "<td class='center'>" + username + "</td>";
			$appHtml += "<td class='center'>" + duty + "</td>";
			$appHtml += "<td class='center'>" + deptName + "</td>";
			$appHtml += "<td class='center'>" + email + "</td>";
			$appHtml += "</tr>";
		}
		$appBody.append($appHtml);

		$refBody = $("#refBody");
		$refHtml = "";
		for (var i = 0; i < $refLen; i++) {
			var value = $refList.eq(i).val().split("&");
			var oid = value[0];
			var name = value[2];
			var username = value[3];
			var duty = value[4];
			var deptName = value[5];
			var email = value[6];
			$refHtml += "<tr>";
			$refHtml += "<td class='center'><input type='hidden' name='refValue' value='" + $refList.eq(i).val() + "'><input class='isBox' type='checkbox' name='refOid' value='" + oid + "'></td>";
			$refHtml += "<td class='center'>" + name + "</td>";
			$refHtml += "<td class='center'>" + username + "</td>";
			$refHtml += "<td class='center'>" + duty + "</td>";
			$refHtml += "<td class='center'>" + deptName + "</td>";
			$refHtml += "<td class='center'>" + email + "</td>";
			$refHtml += "</tr>";
		}
		$refBody.append($refHtml);
	}
	function openerLine() {

		$limit = $(opener.document).find("input[name=appLimit]");
		$("input[name=limit]").val($limit.val());

		$appLen = $(opener.document).find("input[name=appValue]").length;
		$refLen = $(opener.document).find("input[name=refValue]").length;

		$appList = $(opener.document).find("input[name=appValue]");
		$refList = $(opener.document).find("input[name=refValue]");

		$appBody = $("#appBody");
		$appHtml = "";
		for (var i = 0; i < $appLen; i++) {
			var value = $appList.eq(i).val().split("&");
			var oid = value[0];
			var name = value[2];
			var username = value[3];
			var duty = value[4];
			var deptName = value[5];
			var email = value[6];
			var type = value[7];
			$appHtml += "<tr>";
			$appHtml += "<td class='center'><input type='hidden' name='appValue' value='" + $appList.eq(i).val() + "'><input class='isBox' type='checkbox' name='appOid' value='" + oid + "'></td>";
			if (type == "app") {
				$appHtml += "<td class='center'><font color='red'><b>결재</b></font></td>";
			} else if (type == "via") {
				$appHtml += "<td class='center'><font color='blue'><b>경유</b></font></td>";
			}
			$appHtml += "<td class='center'>" + name + "</td>";
			$appHtml += "<td class='center'>" + username + "</td>";
			$appHtml += "<td class='center'>" + duty + "</td>";
			$appHtml += "<td class='center'>" + deptName + "</td>";
			$appHtml += "<td class='center'>" + email + "</td>";
			$appHtml += "</tr>";
		}
		$appBody.append($appHtml);

		$refBody = $("#refBody");
		$refHtml = "";
		for (var i = 0; i < $refLen; i++) {
			var value = $refList.eq(i).val().split("&");
			var oid = value[0];
			var name = value[2];
			var username = value[3];
			var duty = value[4];
			var deptName = value[5];
			var email = value[6];
			$refHtml += "<tr>";
			$refHtml += "<td class='center'><input type='hidden' name='refValue' value='" + $refList.eq(i).val() + "'><input class='isBox' type='checkbox' name='refOid' value='" + oid + "'></td>";
			$refHtml += "<td class='center'>" + name + "</td>";
			$refHtml += "<td class='center'>" + username + "</td>";
			$refHtml += "<td class='center'>" + duty + "</td>";
			$refHtml += "<td class='center'>" + deptName + "</td>";
			$refHtml += "<td class='center'>" + email + "</td>";
			$refHtml += "</tr>";
		}
		$refBody.append($refHtml);

	}
</script>