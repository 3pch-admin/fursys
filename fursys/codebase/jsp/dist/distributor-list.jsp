<%@page import="platform.util.CommonUtils"%>
<%@page import="platform.code.entity.BaseCode"%>
<%@page import="java.util.ArrayList"%>
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
			<span>배포처 관리</span>
		</div>
		<table id="wrap-table">
			<td>
				<table class="search-table top-color">
					<colgroup>
						<col width="150">
						<col width="650">
						<col width="150">
						<col width="*">
					</colgroup>
					<tr>
						<th>배포처</th>
						<td colspan="3">
							<input type="text" class="AXInput w40p" name="name">
						</td>
					</tr>
					<tr>
						<th>배포처 구분</th>
						<td>
							<select name="type" id="type" class="AXSelect w100px">
								<option value="">선택</option>
								<option value="OUT">사외</option>
								<option value="IN">사내</option>
							</select>
						</td>
						<th>사용여부</th>
						<td>
							<select name="enable" class="AXSelect w100px" id="enable">
								<option value="">전체</option>
								<option value="true" selected="selected">사용</option>
								<option value="false">사용안함</option>
							</select>
						</td>
					</tr>
					<tr>
						<th>작성자</th>
						<td>
							<input type="text" class="AXInput w30p" name="creator" id="creator">
						</td>
						<th>작성일자</th>
						<td colspan="3">
							<input type="text" class="AXInput w100px" name="startCreatedDate" id="startCreatedDate" maxlength="8">
							~
							<input type="text" class="AXInput w100px" name="endCreatedDate" id="endCreatedDate" data-start="startCreatedDate" maxlength="8">
							<i class="axi axi-close2 axicon clearBetween" data-target="CreatedDate"></i>
						</td>
					</tr>
				</table>
				<a style="display: none;" id="download"></a>
				<table class="button-table">
					<tr>
						<td class="left">
							<%
							if (isAdmin) {
							%>
							<button type="button" id="createBtn">등록</button>
							<!-- 							<button type="button" id="modifyBtn">수정</button> -->
							<button type="button" id="deleteBtn">사용여부 변경</button>
							<%
							}
							%>
						</td>
						<td class="right">
							<%
							if (isAdmin) {
							%>
							<button type="button" id="excelBtn">엑셀</button>
							<%
							}
							%>
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
						dataField : "type",
						headerText : "배포처 구분",
						dataType : "string",
						width : 100
					}, {
						dataField : "name",
						headerText : "배포처",
						dataType : "string",
						style : "left indent10"
					}, {
						dataField : "enable",
						headerText : "사용여부",
						dataType : "string",
						width : 120
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
						width : 150
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
						var url = _url("/distributor/list");
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
						if (event.dataField == "name" || event.dataField == "number") {
							var rowItem = event.item;
							var url = _url("/distributor/view", rowItem.oid);
							_popup(url, 700, 300, "n");
						}
					});

					$(function() {

						$("#createBtn").click(function() {
							var url = "/Windchill/platform/distributor/create";
							_popup(url, 800, 430, "n");
						})

						$("#modifyBtn").click(function() {
							var items = AUIGrid.getCheckedRowItems(myGridID);
							if (items.length == 0) {
								alert("수정 할 배포처를 선택하세요.");
								return false;
							}
							var oid = items[0].item.oid;
							var url = _url("/distributor/modify", oid);
							_popup(url, "", "", "f");
						})

						$("#excelBtn").click(function() {
							_excel(myGridID, "배포처", []);
						})

						$("#deleteBtn").click(function() {
							var items = AUIGrid.getCheckedRowItems(myGridID);
							if (items.length == 0) {
								alert("배포처를 선택하세요.");
								return false;
							}
							if (!confirm("사용여부 변경 하시겠습니까?")) {
								return false;
							}
							var oid = items[0].item.oid;
							var url = _url("/distributor/delete", oid);
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

						_clearBetween("clearBetween")
						_between("endCreatedDate");
						_selector("type");
						_selector("enable");
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
		</table>

	</form>
</body>
</html>