<%@page import="platform.doc.service.DocumentHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
			<span>작업공간</span>
			>
			<span>검토함</span>
		</div>
		<table id="wrap-table">
			<tr>
				<td>
					<table class="search-table">
						<colgroup>
							<col width="100">
							<col width="*">
							<col width="100">
							<col width="*">
						</colgroup>
						<tr>
							<th>결재제목</th>
							<td colspan="3">
								<input type="text" class="AXInput w70p" name="name">
							</td>
						</tr>
						<tr>
							<th>기안자</th>
							<td>
								<input type="text" class="AXInput w200px" name="creator" id="creator">
								<i class="axi axi-close2 axicon"></i>
							</td>
							<th>기안일</th>
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
								<button type="button" id="searchBtn">조회</button>
							</td>
						</tr>
					</table>

					<div id="grid_wrap" style="height: 650px;"></div>
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
							dataField : "lineType",
							headerText : "결재타입",
							dataType : "string",
							width : 100
						}, {
							dataField : "name",
							headerText : "결재제목",
							dataType : "string",
							style : "left indent10"
						}, {
							dataField : "state",
							headerText : "진행상태",
							dataType : "string",
							width : 100
						}, {
							dataField : "submiter",
							headerText : "기안자",
							dataType : "string",
							width : 100
						}, {
							dataField : "startTime",
							headerText : "기안일",
							dataType : "date",
							formatString : "yyyy/mm/dd",
							width : 100
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
							fillColumnSizeMode : true,
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
							var params = _data($("#form"));
							params.type = "D";
							var url = _url("/app/list");
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

						AUIGrid.bind(myGridID, "cellClick", function(event) {
							var rowItem = event.item;
							var url = _url("/app/view", rowItem.oid);
							_popup(url, 1400, 700, "n");
						});

						// jquery 
						$(function() {

							$("#searchBtn").click(function() {
								currentPage = 1;
								$("input[name=tpage").val(1);
								$("input[name=sessionid").val(0);
								load();
							})
							// 작성자, 수정자 선택바인드
							_between("endCreatedDate")
							_user("creator");
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