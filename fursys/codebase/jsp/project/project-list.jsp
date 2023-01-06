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
		<input type="hidden" name="sessionid" id="sessionid"> <input type="hidden" name="tpage" id="tpage">
		<div class="header-title">
			<img src="/Windchill/jsp/images/home.png" class="home"> <span>HOME</span> > <span>프로젝트 관리</span> > <span>프로젝트 조회</span>
		</div>
		<table class="search-table top-color">
			<colgroup>
				<col width="130">
				<col width="*">
				<col width="130">
				<col width="*">
				<col width="130">
				<col width="*">
			</colgroup>
			<tr>
<!-- 				<th>프로젝트 번호</th> -->
<!-- 				<td><input type="text" class="AXInput w80p" name="number"></td> -->
				<th>프로젝트 명</th>
				<td colspan="3"><input type="text" name="name" id="name" class="AXInput w40p"></td>
				<th>진행상태</th>
				<td><select name="state" id="state" class="AXSelect w200px">
				<option value="">선택</select></td>
			</tr>
			<tr>
				<th>PM</th>
				<td>
				<input type="text" class="AXInput w200px" name="manager" id="manager" readonly="readonly"> 
				<i class="axi axi-close2 axicon clearUser" data-target="manager"></i>
				</td>
				<th>회사</th>
				<td><select name="company" id="company" class="AXSelect w100px">
						<option value="">선택</option>
						<%
						for (BaseCode c : company) {
						%>
						<option value="<%=c.getCode()%>"><%=c.getName()%></option>
						<%
						}
						%>
				</select></td>
				<th>부서</th>
				<td><select id="department" name="department" class="AXSelect w200px">
				<option value="">선택</option>
				</select></td>
			</tr>
			<tr>
				<th>계획시작일자</th>
				<td><input type="text" class="AXInput w100px" name="startPlanDate" id="startPlanDate" maxlength="8"> ~ 
				<input type="text" class="AXInput w100px" name="endPlanDate" id="endPlanDate" data-start="startPlanDate" maxlength="8"> 
				<i	class="axi axi-close2 axicon clearBetween" data-target="PlanDate"></i></td>
				<th>계획종료일자</th>
				<td colspan="3"><input type="text" class="AXInput w100px" name="startPlanEndDate" id="startPlanEndDate" maxlength="8"> ~ 
				<input type="text" class="AXInput w100px" name="endPlanEndDate" id="endPlanEndDate" data-start="startPlanEndDate" maxlength="8"> 
				<i	class="axi axi-close2 axicon clearBetween" data-target="PlanEndDate"></i></td>
			</tr>
		</table>

		<table class="button-table">
			<tr>
				<td class="left">
					<button type="button" id="createBtn">등록</button> <%
 if (isAdmin) {
 %>
					<button type="button" id="deleteBtn">삭제</button> <%
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

		<div id="grid_wrap" style="height: 490px;"></div>
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
				headerText : "프로젝트 명",
				dataType : "string",
				style : "left indent10"
			}, {
				dataField : "duration",
				headerText : "기간",
				dataType : "string",
				width : 80,
				postfix : "일"
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
				dataField : "projectTypeNm",
				headerText : "프로젝트 유형",
				dataType : "string",
				width : 100
			}, {
				dataField : "customerNm",
				headerText : "고객사",
				dataType : "string",
				width : 80
			}, {
				dataField : "pm",
				headerText : "PM",
				dataType : "string",
				width : 80
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
				dataField : "state",
				headerText : "상태",
				dataType : "string",
				width : 80
			}, {
				dataField : "budget",
				headerText : "예산",
				dataType : "numeric",
				formatString : "#,###",
				width : 130,
				postfix : "원"
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
				var url = _url("/project/list");
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

				$("#createBtn").click(function() {
					var url = _url("/project/create");
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
					var url = _url("/project/delete", oid);
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
			_user("manager");
			_selector("state");
			_selector("company");
			_selector("department");
			_between("endPlanDate");
			_between("endPlanEndDate");
			_clearUser("manager");
			_clearBetween("clearBetween");
			$("input[name=name]").focus();
			$(window).resize(function() {
				AUIGrid.resize("#grid_wrap");
			})
		</script>
	</form>
</body>
</html>