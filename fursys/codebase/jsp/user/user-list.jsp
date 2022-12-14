<%@page import="platform.util.CommonUtils"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>
<%@page import="platform.doc.service.DocumentHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
ArrayList<Map<String, Object>> list = (ArrayList<Map<String, Object>>) request.getAttribute("list");
JSONArray arr = JSONArray.fromObject(list);
ArrayList<Map<String, Object>> dlist = (ArrayList<Map<String, Object>>) request.getAttribute("dlist");
JSONArray darr = JSONArray.fromObject(dlist);
boolean isAdmin = CommonUtils.isAdmin();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<%@include file="/jsp/common/iframe.jsp"%>
<%@include file="/jsp/common/script.jsp"%>
</head>
<!-- <body> -->
<body onload="load();" id="container">
	<form id="form">
		<input type="hidden" name="sessionid" id="sessionid">
		<input type="hidden" name="tpage" id="tpage">
		<div class="header-title">
			<img src="/Windchill/jsp/images/home.png" class="home">
			<span>HOME</span>
			>
			<span>관리자</span>
			>
			<span>사용자 관리</span>
		</div>
		<table id="wrap-table">
			<colgroup>
				<col width="200">
				<col width="10">
				<col width="*">
			</colgroup>
			<tr>
				<td valign="top" class="tree-border">
					<jsp:include page="/jsp/user/dept-tree.jsp" />
				</td>
				<td>&nbsp;</td>
				<td valign="top" class="top-color">
					<table class="search-table">
						<colgroup>
							<col width="100">
							<col width="*">
							<col width="100">
							<col width="*">
						</colgroup>
						<tr>
							<th>이름</th>
							<td>
								<input type="hidden" name="deptOid" id="deptOid">
								<input type="text" class="AXInput w70p" name="name">
							</td>
							<th>아이디</th>
							<td>
								<input type="text" class="AXInput w70p" name="username">
							</td>
						</tr>
					</table>

					<table class="button-table">
						<tr>
							<td class="right">
								<button type="button" id="modifyBtn">수정</button>
								<button type="button" id="searchBtn">조회</button>
							</td>
						</tr>
					</table>

					<div id="grid_wrap" style="height: 720px;"></div>
					<div id="grid_paging" class="aui-grid-paging-panel my-grid-paging-panel"></div>
					<script type="text/javascript">
						var userList = <%=arr%>;
						var deptList = <%=darr%>;
						var myGridID;
						var totalRowCount;
						var rowCount = 30; // 1페이지에서 보여줄 행 수
						var pageButtonCount = 10; // 페이지 네비게이션에서 보여줄 페이지의 수
						var currentPage = 1; // 현재 페이지
						var totalPage;
						var columnLayout = [ {
							dataField : "no",
							headerText : "번호",
							dataType : "string",
							width : 40,
							editable : false
						}, {
							dataField : "userName",
							headerText : "이름",
							dataType : "string",
							width : 150,
							editable : false
						}, {
							dataField : "userId",
							headerText : "아이디",
							dataType : "string",
							width : 150,
							editable : false
						}, {
							dataField : "department",
							headerText : "부서명",
							dataType : "string",
							width : 200,
							editable : true,
							labelFunction : function(rowIndex, columnIndex, value, headerText, item) {
								var retStr = "";
								for (var i = 0, len = deptList.length; i < len; i++) {
									if (deptList[i]["code"] == value) {
										retStr = deptList[i]["name"];
										break;
									}
								}
								return retStr == "" ? value : retStr;
							},
							editRenderer : {
								type : "ComboBoxRenderer",
								autoCompleteMode : true, // 자동완성 모드 설정
								autoEasyMode : true,
								matchFromFirst : false, // 처음부터 매치가 아닌 단순 포함되는 자동완성
								list : deptList, //key-value Object 로 구성된 리스트
								keyField : "code", // key 에 해당되는 필드명
								valueField : "name", // value 에 해당되는 필드명
								// 에디팅 유효성 검사
								validator : function(oldValue, newValue, item) {
									var isValid = false;
									for (var i = 0, len = deptList.length; i < len; i++) { // keyValueList 있는 값만..
										if (deptList[i]["name"] == newValue) {
											isValid = true;
											break;
										}
									}
									// 리턴값은 Object 이며 validate 의 값이 true 라면 패스, false 라면 message 를 띄움
									return {
										"validate" : isValid,
										"message" : "리스트에 있는 값만 선택(입력) 가능합니다."
									};
								}
							}
						}, 
// 						{
// 							dataField : "duty",
// 							headerText : "직급",
// 							dataType : "string",
// 							width : 150,
// 							editable : false
// 						},
						{
							dataField : "brand",
							headerText : "브랜드",
							dataType : "string",
							width : 150,
							editable : true,
							labelFunction : function(rowIndex, columnIndex, value, headerText, item) {
								var retStr = "";
								for (var i = 0, len = userList.length; i < len; i++) {
									if (userList[i]["code"] == value) {
										retStr = userList[i]["name"];
										break;
									}
								}
								return retStr == "" ? value : retStr;
							},
							editRenderer : {
								type : "ComboBoxRenderer",
								autoCompleteMode : true, // 자동완성 모드 설정
								autoEasyMode : true,
								matchFromFirst : false, // 처음부터 매치가 아닌 단순 포함되는 자동완성
								list : userList, //key-value Object 로 구성된 리스트
								keyField : "code", // key 에 해당되는 필드명
								valueField : "name", // value 에 해당되는 필드명
								// 에디팅 유효성 검사
								validator : function(oldValue, newValue, item) {
									var isValid = false;
									for (var i = 0, len = userList.length; i < len; i++) { // keyValueList 있는 값만..
										if (userList[i]["name"] == newValue) {
											isValid = true;
											break;
										}
									}
									// 리턴값은 Object 이며 validate 의 값이 true 라면 패스, false 라면 message 를 띄움
									return {
										"validate" : isValid,
										"message" : "리스트에 있는 값만 선택(입력) 가능합니다."
									};
								}
							}
						}, {
							dataField : "email",
							headerText : "이메일",
							dataType : "string",
							style : "left indent10",
							editable : false
						}, {
							dataField : "oid",
							headerText : "oid",
							dataType : "string",
							visible : false
						}, ];
						var auiGridProps = {
							rowIdField : "oid",
							headerHeight : 30,
							rowHeight : 30,
							editable : true,
							fillColumnSizeMode : true,
							showRowNumColumn : false
						};
						myGridID = AUIGrid.create("#grid_wrap", columnLayout, auiGridProps);
						createPagingNavigator(1);

						// 페이징 네비게이터를 동적 생성합니다.
						function createPagingNavigator(goPage) {
							var retStr = "";
							var prevPage = parseInt((goPage - 1) / pageButtonCount) * pageButtonCount;
							var nextPage = ((parseInt((goPage - 1) / pageButtonCount)) * pageButtonCount) + pageButtonCount + 1;
							prevPage = Math.max(0, prevPage);
							nextPage = Math.min(nextPage, totalPage);
							// 처음
							retStr += "<a href='javascript:moveToPage(1)'><span class='aui-grid-paging-number aui-grid-paging-first'>first</span></a>";

							// 이전
							retStr += "<a href='javascript:moveToPage(" + Math.max(1, prevPage) + ")'><span class='aui-grid-paging-number aui-grid-paging-prev'>prev</span></a>";

							for (var i = (prevPage + 1), len = (pageButtonCount + prevPage); i <= len; i++) {
								if (goPage == i) {
									retStr += "<span class='aui-grid-paging-number aui-grid-paging-number-selected'>" + i + "</span>";
								} else {
									retStr += "<a href='javascript:moveToPage(" + i + ")'><span class='aui-grid-paging-number'>";
									retStr += i;
									retStr += "</span></a>";
								}

								if (i >= totalPage) {
									break;
								}

							}

							// 다음
							retStr += "<a href='javascript:moveToPage(" + nextPage + ")'><span class='aui-grid-paging-number aui-grid-paging-next'>next</span></a>";

							// 마지막
							retStr += "<a href='javascript:moveToPage(" + totalPage + ")'><span class='aui-grid-paging-number aui-grid-paging-last'>last</span></a>";

							document.getElementById("grid_paging").innerHTML = retStr;
						}

						function load() {
							var params = _data($("#form"));
							var url = _url("/user/list");
							AUIGrid.showAjaxLoader(myGridID);
							_call(url, params, function(data) {
								totalRowCount = data.total;
								totalPage = Math.ceil(totalRowCount / data.pageSize);
								$("input[name=sessionid").val(data.sessionid);
								createPagingNavigator(data.curPage);
								AUIGrid.removeAjaxLoader(myGridID);
								AUIGrid.setGridData(myGridID, data.list);
							}, "POST");
						}
						function moveToPage(goPage) {
							createPagingNavigator(goPage);
							currentPage = goPage;
							$("input[name=tpage").val(goPage);
							load();
						}

						$(function() {
							
							
							$("input[name=name]").focus();

							$("#searchBtn").click(function() {
								currentPage = 1;
								$("input[name=tpage").val(1);
								$("input[name=sessionid").val(0);
								load();
							})

							$("#modifyBtn").click(function() {
								// 								var removeRows = AUIGrid.getRemovedItems(myGridID);
								// 								var addRows = AUIGrid.getAddedRowItems(myGridID);
								var editRows = AUIGrid.getEditedRowItems(myGridID);
								// 								if (removeRows.length == 0 && addRows.length == 0 && editRows.length == 0) {
								// 									return false;
								// 								}

								if (editRows.length == 0) {
									return false;
								}

								if (!confirm("수정 하시겠습니까?")) {
									return false;
								}
								var params = new Object();
								// 								params.addRows = addRows;
								params.editRows = editRows;
								// 								params.removeRows = removeRows;
								console.log(editRows);
								var url = _url("/user/modify");
								_call(url, params, function(data) {
									load();
								}, "POST");
							});

						}).keypress(function(e) {
							if (e.keyCode == 13) {
								currentPage = 1;
								$("input[name=tpage").val(1);
								$("input[name=sessionid").val(0);
								load();
							}
						})

						$(window).resize(function() {
							AUIGrid.resize("#grid_wrap");
						})
					</script>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>