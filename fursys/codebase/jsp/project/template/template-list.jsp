<%@page import="platform.util.CommonUtils"%>
<%@page import="platform.code.entity.BaseCode"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
ArrayList<BaseCode> company = (ArrayList<BaseCode>) request.getAttribute("company");
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
			<span>템플릿 관리</span>
			>
			<span>템플릿 조회</span>
		</div>
		<table class="search-table top-color">
			<colgroup>
				<col width="130">
				<col width="*">
				<col width="130">
				<col width="*">
			</colgroup>
			<tr>
				<th>템플릿 명</th>
				<td>
					<input type="text" name="name" id="name" class="AXInput w70p">
				</td>
				<!-- 				<th>템플릿 번호</th> -->
				<!-- 				<td> -->
				<!-- 					<input type="text" class="AXInput w200px" name="number"> -->
				<!-- 				</td> -->
				<!-- 			</tr> -->
				<!-- 			<tr> -->
				<th>회사</th>
				<td>
					<select name="company" id="company" class="AXSelect w200px">
						<option value="">선택</option>
						<%
						for (BaseCode c : company) {
						%>
						<option value="<%=c.getCode()%>"><%=c.getName()%></option>
						<%
						}
						%>
					</select>
				</td>
				<th>사용여부</th>
				<td>
					<select id="enable" name="enable" class="AXSelect w100px">
						<option value="">선택</option>
						<option value="">예</option>
						<option value="">아니오</option>
					</select>
				</td>
			</tr>
		</table>

		<table class="button-table">
			<tr>
				<td class="left">
					<button type="button" id="createBtn">등록</button>
					<%
					if (isAdmin) {
					%>
					<button type="button" id="deleteBtn">삭제</button>
					<%
					}
					%>
				</td>
				<td class="right">
					<button type="button" id="excelBtn">엑셀</button>
					<button type="button" id="searchBtn">조회</button>
					<button type="button" id="">초기화</button>
				</td>
			</tr>
		</table>

		<div id="grid_wrap" style="height: 725px;"></div>
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
			// 			}, {
			// 				dataField : "number",
			// 				headerText : "템플릿 번호",
			// 				dataType : "string",
			// 				width : 200
			}, {
				dataField : "name",
				headerText : "템플릿 명",
				dataType : "string",
				style : "left indent10"
			}, {
				dataField : "duration",
				headerText : "기간",
				dataType : "string",
				width : 80
			}, {
				dataField : "companyNm",
				headerText : "회사",
				dataType : "string",
				width : 130
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
				var url = _url("/template/list");
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
				var url = _url("/template/view", rowItem.oid);
				_popup(url, "", "", "f");
			});

			//jquery
			$(function() {

				$("#createBtn").click(function() {
					var url = _url("/template/create");
					_popup(url, 1300, 600, "n");
				})

				$("#deleteBtn").click(function() {
					var items = AUIGrid.getCheckedRowItems(myGridID);
					if (items.length == 0) {
						alert("삭제 할 템플릿을 선택하세요.");
						return false;
					}

					if (!confirm("삭제 하시겠습니까?")) {
						return false;
					}
					var oid = items[0].item.oid;
					var url = _url("/template/delete", oid);
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

			}).keypress(function(e) {
				if (e.keyCode == 13) {
					currentPage = 1;
					$("input[name=tpage").val(1);
					$("input[name=sessionid").val(0);
					load();
				}
			})
			_selector("enable");
			_selector("company");
			$("input[name=name]").focus();
			$(window).resize(function() {
				AUIGrid.resize("#grid_wrap");
			})
		</script>
	</form>
</body>
</html>