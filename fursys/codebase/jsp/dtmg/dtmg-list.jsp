<%@page import="platform.util.CommonUtils"%>
<%@page import="platform.doc.service.DocumentHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
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
<body id="container">
	<form id="form">
		<input type="hidden" name="sessionid" id="sessionid">
		<input type="hidden" name="tpage" id="tpage">
		<div class="header-title">
			<img src="/Windchill/jsp/images/home.png" class="home">
			<span>HOME</span>
			>
			<span>설계변경</span>
			>
			<span>DTMG 전송 조회</span>
		</div>
		<table id="wrap-table">
			<tr>
				<td>
					<table class="search-table top-color">
						<colgroup>
							<col width="100">
							<col width="*">
							<col width="100">
							<col width="*">
						</colgroup>
						<tr>
							<th>부품번호</th>
							<td>
								<input type="text" name="number" class="AXInput w60p">
							</td>
							<th>ECO번호</th>
							<td>
								<input type="text" name="ecoNumber" class="AXInput w60p">
							</td>
						</tr>
						<tr>
							<th>상태</th>
							<td>
								<select name="state" id="state" class="AXSelect w200px">
									<option value="">선택</option>
									<option value="EBOM 작성중">EBOM 작성중</option>
									<option value="PART LIST 작성중">PART LIST 작성중</option>
									<option value="EBOM 승인중">EBOM 승인중</option>
									<option value="EBOM 승인됨">EBOM 승인됨</option>
								</select>
							</td>
							<th>ERP CODE</th>
							<td>
								<input type="text" name="erpCode" class="AXInput w60p">
							</td>
						</tr>
						<tr>
							<th>작성자</th>
							<td>
								<input type="text" class="AXInput w200px" name="creator" id="creator">
								<i class="axi axi-close2 axicon"></i>
							</td>
							<th>작성일자</th>
							<td>
								<input type="text" class="AXInput w100px" name="startCreatedDate" id="startCreatedDate" maxlength="8">
								~
								<input type="text" class="AXInput w100px" name="endCreatedDate" id="endCreatedDate" data-start="startCreatedDate" maxlength="8">
								<i class="axi axi-close2 axicon"></i>
							</td>
						</tr>
					</table>

					<table class="button-table">
						<tr>
							<td class="right">
								<button type="button" id="createBtn">DTMG 전송 등록</button>
								<button type="button" id="derivedBtn">삭제</button>
								<button type="button" id="searchBtn">조회</button>
							</td>
						</tr>
					</table>

					<div id="grid_wrap" style="height: 640px;"></div>
					<div id="grid_paging" class="aui-grid-paging-panel my-grid-paging-panel"></div>
					<script type="text/javascript">
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
							width : 40
						}, {
							dataField : "number",
							headerText : "DTMG 번호",
							dataType : "string",
							width : 150
						}, {
							dataField : "name",
							headerText : "DTMG 명",
							dataType : "string",
							width : 150
						}, {
							dataField : "erpCode",
							headerText : "ERP CODE",
							dataType : "string",
							width : 150
						}, {
							dataField : "asm",
							headerText : "CAD Total Ass’y",
							dataType : "string",
							width : 150
						}, {
							dataField : "creator",
							headerText : "작성자",
							dataType : "string",
							width : 100
						}, {
							dataField : "createdDate",
							headerText : "작성일자",
							dataType : "date",
							formatString : "yyyy/mm/dd",
							width : 100
						}, {
							dataField : "state",
							headerText : "상태",
							dataType : "string",
							width : 120
						}, {
							dataField : "oid",
							headerText : "oid",
							dataType : "string",
							visible : false
						}, ];
						var auiGridProps = {
							// 							rowIdField : "oid",
							headerHeight : 30,
							rowHeight : 30,
							fillColumnSizeMode : true,
							rowCheckToRadio : true,
							showRowCheckColumn : true,
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
							requestData("/Windchill/jsp/dtmg/mockup/dtmg-list.json");
						}

						function requestData(url) {

							// ajax 요청 전 그리드에 로더 표시
							AUIGrid.showAjaxLoader(myGridID);

							// ajax (XMLHttpRequest) 로 그리드 데이터 요청
							ajax({
								url : url,
								onSuccess : function(data) {

									//console.log(data);

									// 그리드에 데이터 세팅
									// data 는 JSON 을 파싱한 Array-Object 입니다.
									AUIGrid.setGridData(myGridID, data);

									// 로더 제거
									AUIGrid.removeAjaxLoader(myGridID);
								},
								onError : function(status, e) {
									alert("데이터 요청에 실패하였습니다.\r\n status : " + status + "\r\nWAS 를 IIS 로 사용하는 경우 json 확장자가 web.config 의 handler 에 등록되었는지 확인하십시오.");
									// 로더 제거
									AUIGrid.removeAjaxLoader(myGridID);
								}
							});
						};

						// 						function load() {
						// 							var params = _data($("#form"));
						// 							var url = _url("/ebom/list");
						// 							AUIGrid.showAjaxLoader(myGridID);
						// 							_call(url, params, function(data) {
						// 								totalRowCount = data.total;
						// 								totalPage = Math.ceil(totalRowCount / data.pageSize);
						// 								$("input[name=sessionid").val(data.sessionid);
						// 								createPagingNavigator(data.curPage);
						// 								AUIGrid.removeAjaxLoader(myGridID);
						// 								AUIGrid.setGridData(myGridID, data.list);
						// 							}, "POST");
						// 						}

						function moveToPage(goPage) {
							createPagingNavigator(goPage);
							currentPage = goPage;
							$("input[name=tpage").val(goPage);
							load();
						}
						AUIGrid.bind(myGridID, "cellClick", function(event) {
							var dataField = event.dataField;
							var rowItem = event.item;
							var state = rowItem.state;
							if (dataField == "number") {
								if (state == "EBOM 임시저장") {
									var url = _url("/ebom/modify", rowItem.oid);
									_popup(url, "", "", "f");
								} else if (state == "EBOM 작성중(검증완료)") {
									var url = _url("/ebom/view", rowItem.oid);
									_popup(url, "", "", "f");
								}
							} else if (event.dataField == "thumb") {
								_openCreoView(event.item.toid);
							}
							AUIGrid.setCheckedRowsByIds(myGridID, rowItem._$uid);
						});

						$(function() {

							$("input[name=number]").focus();

							$("#createBtn").click(function() {
								var url = "/Windchill/platform/dtmg/create";
								_popup(url, 1400, 600, "n");
							})

							$("#deleteBtn").click(function() {
								var items = AUIGrid.getCheckedRowItems(myGridID);
								if (items.length == 0) {
									alert("삭제 할 PART LIST를 선택하세요.");
									return false;
								}

								if (!confirm("삭제 하시겠습니까?")) {
									return false;
								}

								var oid = items[0].item.oid;
								var url = _url("/ebom/delete", oid);
								_call(url, null, function(data) {
									load();
								}, "GET");
							})

							$("#searchBtn").click(function() {
								currentPage = 1;
								$("input[name=tpage").val(1);
								$("input[name=sessionid").val(0);
								load();
							})
							// 작성자, 수정자 선택바인드
							_selector("state");
							_user("creator");
							_between("endCreatedDate");
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