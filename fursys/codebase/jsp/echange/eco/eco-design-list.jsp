<%@page import="platform.util.CommonUtils"%>
<%@page import="java.util.ArrayList"%>
<%@page import="platform.code.entity.BaseCode"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
ArrayList<BaseCode> ecoTypes = (ArrayList<BaseCode>) request.getAttribute("ecoTypes");
ArrayList<BaseCode> devTypes = (ArrayList<BaseCode>) request.getAttribute("devTypes");
ArrayList<BaseCode> applyTimes = (ArrayList<BaseCode>) request.getAttribute("applyTimes");
ArrayList<BaseCode> notiTypes = (ArrayList<BaseCode>) request.getAttribute("notiTypes");
ArrayList<BaseCode> lots = (ArrayList<BaseCode>) request.getAttribute("lots");
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
			<span>설계변경</span>
			>
			<span>설계 ECO 조회</span>
		</div>
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
				<th>ECO 번호</th>
				<td>
					<input type="text" name="number" class="AXInput w70p">
				</td>
				<th>ECO 명</th>
				<td>
					<input type="text" name="name" class="AXInput w70p" autofocus="autofocus">
				</td>
				<th>관련부품</th>
				<td>
					<input size="40" type="text" name="part" id="part" readonly="readonly" class="AXInput w70p">
					<i class="axi axi-close2 axicon"></i>
				</td>
			</tr>
			<tr>

				<th>상태</th>
				<td>
					<select name="state" class="AXSelect w100px" id="state">
						<option value="">선택</option>
						<option value="작업 중">작업 중</option>
						<option value="승인중">승인중</option>
						<option value="결재완료">결재완료</option>
						<option value="반려됨">반려됨</option>
					</select>
				</td>
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
			<tr>
<!-- 				<th>ECO 유형</th> -->
<!-- 				<td> -->
<!-- 					<select name="ecoType" class="AXSelect w200px" id="ecoType" > -->
<!-- 						<option value="">선택</option> -->
						<%
						for (BaseCode ecoType : ecoTypes) {
						%>
<%-- 						<option value="<%=ecoType.getCode()%>"><%=ecoType.getName()%></option> --%>
						<%
						}
						%>
<!-- 					</select> -->
<!-- 				</td> -->
				<th>개발구분</th>
				<td>
					<select name="devType" class="AXSelect w200px" id="devType">
						<option value="">선택</option>
						<%
						for (BaseCode devType : devTypes) {
						%>
						<option value="<%=devType.getCode()%>"><%=devType.getName()%></option>
						<%
						}
						%>
					</select>
				</td>
				<th>통보유형</th>
				<td>
					<select name="notiType" class="AXSelect w200px" id="notiType">
						<option value="">선택</option>
						<%
						for (BaseCode notiType : notiTypes) {
						%>
						<option value="<%=notiType.getCode()%>"><%=notiType.getName()%></option>
						<%
						}
						%>
					</select>
				</td>
				<th>예상 적용일</th>
				<td>
					<input type="text" class="AXInput w100px" name="expectationTime" id="expectationTime">
					<i class="axi axi-close2 axicon"></i>
				</td>				
			</tr>
			<tr>
				<th>제품LOT관리</th>
				<td>
					<select name="lot" class="AXSelect w200px" id="lot">
						<option value="">선택</option>
						<%
						for (BaseCode lot : lots) {
						%>
						<option value="<%=lot.getCode()%>"><%=lot.getName()%></option>
						<%
						}
						%>
					</select>
				</td>
				<th>적용시점</th>
				<td colspan="3">
					<select name="applyTime" class="AXSelect w200px" id="applyTime">
						<option value="">선택</option>
						<%
						for (BaseCode applyTime : applyTimes) {
						%>
						<option value="<%=applyTime.getCode()%>"><%=applyTime.getName()%></option>
						<%
						}
						%>
					</select>
				</td>
			</tr>
		</table>

		<table class="button-table">
			<tr>
				<td class="right">
					<button type="button" id="searchBtn">조회</button>
					<button type="button" id="">조회 초기화</button>
				</td>
			</tr>
			<tr>
				<td class="right">
					<button type="button" class="create" data-l="설계">설계 ECO 등록</button>
					<button type="button" id="modifyBtn">수정</button>
					<button type="button" id="approvalBtn">결재</button>
				</td>
			</tr>
		</table>

		<div id="grid_wrap" style="height: 600px;"></div>
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
				headerText : "ECO 번호",
				dataType : "string",
				width : 200
			}, {
				dataField : "name",
				headerText : "ECO 명",
				dataType : "string",
				style : "left indent10"
// 			}, {
// 				dataField : "ecoTypeNm",
// 				headerText : "ECO유형",
// 				dataType : "string",
// 				width : 80
			}, {
				dataField : "devTypeNm",
				headerText : "개발유형",
				dataType : "string",
				width : 80
			}, {
				dataField : "notiTypeNm",
				headerText : "통보유형",
				dataType : "string",
				width : 80
			}, {
				dataField : "lotNm",
				headerText : "제품 LOT 관리",
				dataType : "string",
				width : 170
			}, {
				dataField : "applyTimeNm",
				headerText : "적용시점",
				dataType : "string",
				width : 80
// 			}, {
// 				dataField : "expectationTime",
// 				headerText : "예상적용일",
// 				dataType : "date",
// 				width : 100,
// 				formatString : "yyyy/mm/dd"
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
				width : 80
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
				var url = _url("/eco/list");
				AUIGrid.showAjaxLoader(myGridID);
				params.ecoType = "D";
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
				var url = _url("/eco/view", rowItem.oid);
				_popup(url, "", "", "f");
			});

			//jquery
			$(function() {

				$("#deleteBtn").click(function() {
					var items = AUIGrid.getCheckedRowItems(myGridID);
					if (items.length == 0) {
						alert("삭제 할 ECO을 선택하세요.");
						return false;
					}
					<%
						if(!CommonUtils.isAdmin()) {
					%>
					var state = items[0].item.state;
					if (state != "작업중") {
						alert("작업중 상태의 ECO만 삭제가 가능합니다.");
						return false;
					}
					<%
						}
					%>

					if (!confirm("삭제 하시겠습니까?")) {
						return false;
					}
					var oid = items[0].item.oid;
					var url = _url("/eco/delete", oid);
					_call(url, null, function(data) {
						load();
					}, "GET");
				})

				$(".create").click(function() {
					var l = $(this).data("l");
					var url = _url("/eco/create?ecoType=" + l);
					_popup(url, "", "", "f");
				})

				$("#searchBtn").click(function() {
					currentPage = 1;
					$("input[name=tpage").val(1);
					$("input[name=sessionid").val(0);
					load();
				})

				$("#approvalBtn").click(function() {
					var items = AUIGrid.getCheckedRowItems(myGridID);
					if (items.length == 0) {
						alert("결재 할 ECO를 선택하세요.");
						return false;
					}
					var oid = items[0].item.oid;
					var url = _url("/app/register", oid);
					// 								_popup(url, "", "", "f");
					_popup(url, 1300, 600, "n");
				})

				_selector("state");
				_selector("ecoType");
				_selector("lot");
				_selector("devType");
				_selector("notiType");
				_selector("applyTime");
				_user("creator");
				_between("endCreatedDate");
				_date("expectationTime");
				$("#ecoType").bindSelectDisabled(true);
				$("#ecoType").bindSelectSetValue("D");
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
	</form>
</body>
</html>