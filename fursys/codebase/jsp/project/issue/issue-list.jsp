<%@page import="platform.util.CommonUtils"%>
<%@page import="platform.code.entity.BaseCode"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
ArrayList<BaseCode> issueTypes = (ArrayList<BaseCode>) request.getAttribute("issueTypes");
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
			<span>나의 이슈</span>
		</div>
		<table class="search-table top-color">
			<colgroup>
				<col width="130">
				<col width="700">
				<col width="130">
				<col width="700">
			</colgroup>
			<tr>
				<th>이슈 명</th>
				<td colspan="3">
					<input type="text" name="name" id="name" class="AXInput w40p">
				</td>
			</tr>

			<tr>
				<th>이슈구분</th>
				<td>
					<select name="issueTypes" id="issueTypes" class="AXSelect w200px">
						<option value="">선택</option>
						<%
						for (BaseCode b : issueTypes) {
						%>
						<option value="<%=b.getCode()%>"><%=b.getName()%></option>
						<%
						}
						%>
					</select>
				</td>
				<th>진행상태</th>
				<td>
					<select id="state" name="state" class="AXSelect w100px">
						<option value="">선택</option>
						<option value="작업중">작업중</option>
						<option value="해결완료">해결완료</option>
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
				dataField : "name",
				headerText : "이슈 명",
				dataType : "string",
				width : 350,
				style : "left indent10"
			}, {
				dataField : "projectName",
				headerText : "프로젝트 명",
				dataType : "string",
				cellMerge : true
			}, {
				dataField : "taskName",
				headerText : "태스크 명",
				dataType : "string",
				width : 250,
				cellMerge : true
			}, {
				dataField : "issueTypeNm",
				headerText : "이슈타입",
				dataType : "string",
				width : 120,
			}, {
				dataField : "state",
				headerText : "상태",
				dataType : "string",
				width : 100,
			}, {
				dataField : "manager",
				headerText : "담당자",
				dataType : "string",
				width : 130
			}, {
				dataField : "",
				headerText : "해결방안",
				dataType : "string",
				width : 140,
				renderer : {
					type : "ButtonRenderer",
					labelText : "등록",
					onClick : function(event) {
						var oid = event.item.oid;
						var url = _url("/solution/create", oid);
						_popup(url, 1200, 400, "n");
					},
					visibleFunction : function(rowIndex, columnIndex, value, item, dataField) {
						if (item.state == "해결완료" || item.tstage != "진행중") {
							return false;
						}
						return true;
					}
				}
			}, {
				dataField : "creator",
				headerText : "요청자",
				dataType : "string",
				width : 130
			}, {
				dataField : "createdDate",
				headerText : "요청일자",
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
				var url = _url("/issue/list");
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
				var dataField = event.dataField;
				if (dataField == "projectName") {
					// 프로젝트				
					var url = _url("/project/view", rowItem.poid);
					_popup(url, "", "", "f");
				} else if (dataField == "taskName") {
					// 태스트				
					var url = _url("/project/view", rowItem.toid);
					_popup(url, "", "", "f");
				} else if (dataField == "name") {
					// 이슈				
					var url = _url("/issue/view", rowItem.oid);
					_popup(url, 1200, 500, "n");
				}
			});

			//jquery
			$(function() {

				// 				$("#excelBtn").click(function() {
				// 					_excel(issueGridID, "나의 이슈", []);
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
			_selector("issueTypes");
			_selector("state");
			_selector("roles");
			$("input[name=name]").focus();
			$(window).resize(function() {
				AUIGrid.resize("#grid_wrap");
			})
		</script>
	</form>
</body>
</html>