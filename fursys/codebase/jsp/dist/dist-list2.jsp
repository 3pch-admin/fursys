<%@page import="platform.util.CommonUtils"%>
<%@page import="platform.code.entity.BaseCode"%>
<%@page import="java.util.ArrayList"%>
<%@page import="platform.doc.service.DocumentHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>	
qweqweqwrqwqr	wqwrqw 123123
<head>12
<meta charset="UTF-8">12312312
<title></title>
<%@include file="/jsp/common/iframe.jsp"%>
<%@include file="/jsp/common/script.jsp"%>
</head>
<!-- <body> -->
<body onload="load();" id="container">
	<form id="form">
		<input type="hidden" name="sessionid" id="sessionid">
		<input type="hidden" name="tpage" id="tpage">
		<input type="hidden" name="location" id="location">
		<div class="header-title">
			<img src="/Windchill/jsp/images/home.png" class="home">
			<span>HOME</span>
			>
			<span>배포 관리</span>
			>
			<span>도면 배포</span>
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
							<col width="100">
							<col width="*">
						</colgroup>
						<tr>
							<th>배포제목</th>
							<td>
								<input type="text" class="AXInput w70p" name="dist_name">
								<input type="hidden" name="material_type" value="ITEM">
							</td>
							<th>CAD 파일명</th>
							<td>
								<input type="text" class="AXInput w70p" name="fileName">
							</td>
							<th>상태</th>
							<td>
								<select name="state" class="AXSelect w100px" id="state">
									<option value="">선택</option>
									<option value="작업중">작업중</option>
									<option value="승인중">승인중</option>
									<option value="릴리즈됨">릴리즈됨</option>
									<option value="반려됨">반려됨</option>
								</select>
							</td>
						</tr>
						<tr>
							<th>작성일자</th>
							<td>
								<input type="text" class="AXInput w100px" name="startCreatedDate" id="startCreatedDate" maxlength="8">
								~
								<input type="text" class="AXInput w100px" name="endCreatedDate" id="endCreatedDate" data-start="startCreatedDate" maxlength="8">
								<i class="axi axi-close2 axicon clearBetween" data-target="CreatedDate"></i>
							</td>
							<th>배포처</th>
							<td>
								<input type="text" class="AXInput w200px" name="creator" id="creator" readonly="readonly">
								<i class="axi axi-close2 axicon clearUser" data-target="creator"></i>
							</td>
							<th>배포처구분MergeTest</th>
							<td>
								<select name="type" id="type" class="AXSelect w100px">
									<option value="">선택</option>
									<option value="IN">사내</option>
									<option value="OUT">사외</option>
								</select>
							</td>
						</tr>
					</table>

					<table class="button-table">
						<tr>
							<td class="left">
								<button type="button" id="createBtn">등록</button>
								<button type="button" id="modifyBtn">수정</button>
								<button type="button" id="deleteBtn">삭제</button>
								<button type="button" id="approvalBtn">결재</button>
							</td>
							<td class="right">
								<button type="button" id="excelBtn">엑셀</button>
								<button type="button" id="searchBtn">조회</button>
							</td>
						</tr>
					</table>

					<div id="grid_wrap" style="height: 530px;"></div>
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
							dataField : "name",
							headerText : "배포명",
							dataType : "string",
							style : "left indent10",
// 							width : 300,
							cellMerge : true,
						}, {
							dataField : "state",
							headerText : "상태",
							dataType : "string",
							width : 80,
							//cellMerge : true,
							mergeRef : "number",
							mergePolicy : "restrict"
						}, {
							dataField : "duration",
							headerText : "다운로드 기간",
							dataType : "string",
							width : 150,
						}, {
							dataField : "creator",
							headerText : "작성자",
							dataType : "string",
							width : 100,
							//cellMerge : true,
							mergeRef : "number",
							mergePolicy : "restrict"
						}, {
							dataField : "createdDate",
							headerText : "작성일자",
							dataType : "string",
							width : 100,
							//cellMerge : true,
							mergeRef : "number",
							mergePolicy : "restrict"
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
							rowCheckToRadio : true,
							showRowCheckColumn : true,
							showRowNumColumn : false,
							enableCellMerge : false,
							cellMergePolicy : "withNull",
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
							var url = _url("/dist/list");
							AUIGrid.showAjaxLoader(myGridID);
							_call(url, params, function(data) {
								console.log(data.list);
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
							if (event.dataField == "name" || event.dataField == "fileName") {
								var rowItem = event.item;
								var url = _url("/dist/view", rowItem.oid);
								//_popup(url, "", "", "f");
								_popup(url, 1400, 960, "n");
							}
						});

						$(function() {

							$("#createBtn").click(function() {
								var url = "/Windchill/platform/dist/create";
								_popup(url, "1024", "600", "n");
							})

							$("#modifyBtn").click(function() {
								var items = AUIGrid.getCheckedRowItems(myGridID);
								if (items.length == 0) {
									alert("수정 할 배포를 선택하세요.");
									return false;
								}
								var oid = items[0].item.oid;
								var url = _url("/dist/modify", oid);
								_popup(url, "", "", "f");
							})

							$("#excelBtn").click(function() {
								_excel(myGridID, "도면 배포", []);
							})

							$("#approvalBtn").click(function() {
								var items = AUIGrid.getCheckedRowItems(myGridID);
								if (items.length == 0) {
									alert("결재 할 배포를 선택하세요.");
									return false;
								}
								var oid = items[0].item.doid;
								var url = _url("/app/register", oid);
								_popup(url, 1300, 600, "n");
							})

							$("#deleteBtn").click(function() {
								var items = AUIGrid.getCheckedRowItems(myGridID);
								if (items.length == 0) {
									alert("삭제 할 배포를 선택하세요.");
									return false;
								}

								var state = items[0].item.state;
					<%if (!CommonUtils.isAdmin()) {%>
						if (state != "작업중") {
									alert("작업중 상태의 배포만 삭제 할 수 있습니다.");
									return false;
								}
					<%}%>
						if (!confirm("삭제 하시겠습니까?")) {
									return false;
								}
								var oid = items[0].item.oid;
								var url = _url("/dist/delete", oid);
								_call(url, null, function(data) {
									currentPage = 1;
									$("input[name=tpage").val(1);
									$("input[name=sessionid").val(0);
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
							_between("endCreatedDate");
							_check("latest");
							_user("creator");
							_selector("type")
							_clearUser("clearUser");
							_clearBetween("clearBetween");
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