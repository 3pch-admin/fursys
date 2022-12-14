<%@page import="platform.util.CommonUtils"%>
<%@page import="platform.code.entity.BaseCode"%>
<%@page import="java.util.ArrayList"%>
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
<body onload="load();" id="container">
	<form id="form">
		<input type="hidden" name="sessionid" id="sessionid">
		<input type="hidden" name="tpage" id="tpage">
		<div class="header-title">
			<img src="/Windchill/jsp/images/home.png" class="home">
			<span>HOME</span>
			>
			<span>프로젝트 관리</span>
			>
			<span>나의 태스크</span>
		</div>
		<table class="search-table top-color">
			<colgroup>
				<col width="140">
				<col width="500">
				<col width="140">
				<col width="500">
				<col width="140">
				<col width="500">
			</colgroup>
			<tr>
				<th>프로젝트 명</th>
				<td>
					<input type="text" name="pjtName" id="pjtName" class="AXInput w70p">
				</td>
				<th>태스크 명</th>
				<td>
					<input type="text" name="taskName" id="taskName" class="AXInput w70p">
				</td>
				<th>프로젝트 상태</th>
				<td>
					<select name="pjtState" id="pjtState" class="AXSelect w100px">
						<option value="">선택</option>
						<option value="대기중">대기중</option>
						<option value="진행중">진행중</option>
						<option value="완료됨">완료됨</option>
						<option value="중지됨">중지됨</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>계획 시작일자</th>
				<td>
					<input type="text" class="AXInput w100px" name="startProjectDate" id="startProjectDate" maxlength="8">
					~
					<input type="text" class="AXInput w100px" name="endProjectDate" id="endProjectDate" data-start="startProjectDate" maxlength="8">
					<i class="axi axi-close2 axicon clearBetween" data-target="ProjectDate"></i>
				</td>
				<th>계획 종료일자</th>
				<td>
					<input type="text" class="AXInput w100px" name="startFinishDate" id="startFinishDate" maxlength="8">
					~
					<input type="text" class="AXInput w100px" name="endFinishDate" id="endFinishDate" data-start="startFinishDate" maxlength="8">
					<i class="axi axi-close2 axicon clearBetween" data-target="FinishDate"></i>
				</td>
				<th>태스크 상태</th>
				<td>
					<select id="taskState" name="taskState" class="AXSelect w100px">
						<option value="">선택</option>
						<option value="대기중">대기중</option>
						<option value="진행중">진행중</option>
						<option value="완료됨">완료됨</option>
						<option value="중지됨">중지됨</option>
					</select>
				</td>
			</tr>
		</table>

		<table class="button-table">
			<tr>
				<td class="right">
					<!-- 					<button type="button" id="excelBtn">엑셀</button> -->
					<button type="button" id="searchBtn">조회</button>
					<button type="button" id="">초기화</button>
				</td>
			</tr>
		</table>

		<div id="grid_wrap" style="height: 670px;"></div>
		<div id="grid_paging" class="aui-grid-paging-panel my-grid-paging-panel"></div>
		<script type="text/javascript">
			var myGridID;
			var totalRowCount;
			var rowCount = 30; // 1페이지에서 보여줄 행 수
			var pageButtonCount = 10; // 페이지 네비게이션에서 보여줄 페이지의 수
			var currentPage = 1; // 현재 페이지
			var totalPage;
			var columnLayout = [ {
				dataField : "projectName",
				headerText : "프로젝트 명",
				dataType : "string",
				cellMerge : true
			}, {
				dataField : "taskName",
				headerText : "태스크 명",
				dataType : "string",
				width : 350,
				style : "left indent10"
			}, {
				dataField : "role",
				headerText : "역할",
				dataType : "string",
				width : 150,
			}, {
				dataField : "progress",
				headerText : "진행율",
				dataType : "string",
				width : 100,
				editable : false,
				renderer : {
					type : "BarRenderer",
					min : 0,
					max : 100,
				},
				postfix : "%"
			}, {
				dataField : "state",
				headerText : "태스크 상태",
				dataType : "string",
				width : 100,
			},
			<%if (isAdmin) {%>
			{
				dataField : "user",
				headerText : "담당자",
				dataType : "string",
				width : 100,
			}, 
			<%}%>
			{
				dataField : "duration",
				headerText : "공수",
				dataType : "string",
				width : 80,
				postfix : "일"
			}, {
				dataField : "planStartDate",
				headerText : "계획시작일",
				dataType : "date",
				formatString : "yyyy/mm/dd",
				width : 100
			}, {
				dataField : "planEndDate",
				headerText : "계획종료일",
				dataType : "date",
				formatString : "yyyy/mm/dd",
				width : 100
			}, {
				dataField : "startDate",
				headerText : "실제시작일",
				dataType : "date",
				formatString : "yyyy/mm/dd",
				width : 100
			}, {
				dataField : "endDate",
				headerText : "실제종료일",
				dataType : "date",
				formatString : "yyyy/mm/dd",
				width : 100
			}, {
				dataField : "poid",
				headerText : "oid",
				dataType : "string",
				visible : false
			}, {
				dataField : "oid",
				headerText : "oid",
				dataType : "string",
				visible : false
			}, ];
			var auiGridProps = {
				// 				rowIdField : "oid",
				headerHeight : 30,
				rowHeight : 30,
				rowNumHeaderText : "번호",
				enableCellMerge : true,
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
				var url = _url("/task/my");
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
				var url = _url("/project/view", rowItem.oid);
				_popup(url, "", "", "f");
			});

			//jquery
			$(function() {

				// 				$("#excelBtn").click(function() {
				// 					_excel(issueGridID, "나의 태스크", []);
				// 				})

				$("#searchBtn").click(function() {
					currentPage = 1;
					$("input[name=tpage").val(1);
					$("input[name=sessionid").val(0);
					load();
				})

			}).keypress(function(e) {
				if (e.keyCode == 13) {
					currentPage = 1;
					$("input[name=tpage").val(1);
					$("input[name=sessionid").val(0);
					load();
				}
			})
			_selector("pjtState");
			_selector("taskState");
			_between("endProjectDate");
			_between("endFinishDate");
			_clearBetween("clearBetween");
			$("input[name=name]").focus();
			$(window).resize(function() {
				AUIGrid.resize("#grid_wrap");
			})
		</script>
	</form>
</body>
</html>