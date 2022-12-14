<%@page import="platform.util.CommonUtils"%>
<%@page import="platform.code.entity.BaseCode"%>
<%@page import="java.util.ArrayList"%>
<%@ page import="platform.echange.ecr.service.ECRHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
ArrayList<BaseCode> reqType = (ArrayList<BaseCode>) request.getAttribute("reqType");
ArrayList<BaseCode> brand = (ArrayList<BaseCode>) request.getAttribute("brand");
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
			<span>설계변경</span>
			>
			<span>ECR조회</span>
		</div>
		<table class="search-table top-color">
			<colgroup>
				<col width="100">
				<col width="*">
				<col width="100">
				<col width="*">
			</colgroup>
			<tr>
				<th>ECR 번호</th>
				<td>
					<input type="text" class="AXInput w70p" name="number">
				</td>
				<th>ECR 명</th>
				<td>
					<input type="text" class="AXInput w70p" name="name">
				</td>
				<th>관련부품</th>
				<td>
					<input type="text" class="AXInput w70p" name="part" id="part" readonly="readonly">
					<i class="axi axi-close2 axicon"></i>
				</td>
			</tr>
			<tr>
				<th>상태</th>
				<td>
					<select name="state" class="AXSelect w200px" id="state">
						<option value="">선택</option>
						<option value="작업 중">작업 중</option>
						<option value="승인 중">승인 중</option>
						<option value="릴리즈됨">릴리즈됨</option>
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
			<tr>
				<th>회사</th>
				<td>
					<select name="company" id="company" class="AXSelect w200px">
						<option value="">선택</option>
						<%
						for (BaseCode b : company) {
						%>
						<option value="<%=b.getCode()%>"><%=b.getName()%></option>
						<%
						}
						%>
					</select>
				</td>
				<th>브랜드</th>
				<td>
					<select name="brand" id="brand" class="AXSelect w200px">
						<option value="">선택</option>
						<%
						for (BaseCode b : brand) {
						%>
						<option value="<%=b.getCode()%>"><%=b.getName()%></option>
						<%
						}
						%>
					</select>
				</td>
				<th>요청유형</th>
				<td>
					<select name="reqType" class="AXSelect w100px" id="reqType">
						<option value="">선택</option>
						<%
						for (BaseCode c : reqType) {
						%>
						<option value="<%=c.getCode()%>"><%=c.getName()%></option>
						<%
						}
						%>
					</select>
				</td>
			</tr>
		</table>
	</form>

	<table class="button-table">
		<tr>
			<td class="left">
				<button type="button" id="registBtn">등록</button>
				<button type="button" id="modifyBtn">수정</button>
				<%
					if(isAdmin) {
				%>
				<button type="button" id="deleteBtn">삭제</button>
				<%
					}
				%>
				<button type="button" id="approvalBtn">결재</button>
			</td>
			<td class="right">
				<button type="button" id="excelBtn">엑셀</button>
				<button type="button" id="searchBtn">조회</button>
				<button type="button" id="">초기화</button>
			</td>
		</tr>
	</table>

	<div id="grid_wrap" style="height: 645px;"></div>
	<div id="grid_paging" class="aui-grid-paging-panel my-grid-paging-panel"></div>
	<script type="text/javascript">
		var myGridID;
		var totalRowCount;
		var rowCount = 30; // 1페이지에서 보여줄 행 수
		var pageButtonCount = 10; // 페이지 네비게이션에서 보여줄 페이지의 수
		var currentPage = 1; // 현재 페이지
		var totalPage;
		var myGridID;
		var columnLayout = [ {
			dataField : "no",
			headerText : "번호",
			dataType : "string",
			width : 40
		}, {
			dataField : "number",
			headerText : "ECR 번호",
			dataType : "string",
			width : 150
		}, {
			dataField : "name",
			headerText : "ECR 명",
			dataType : "string",
			style : "left indent10",
		}, {
			dataField : "reqType",
			headerText : "요청유형",
			dataType : "string",
			width : 80
		}, {
			dataField : "limit",
			headerText : "처리기한",
			formatString : "yyyy/mm/dd",
			dataType : "date",
			width : 80
		}, {
			dataField : "state",
			headerText : "상태",
			dataType : "string",
			width : 80
		}, {
			dataField : "company",
			headerText : "회사",
			dataType : "string",
			width : 130
		}, {
			dataField : "brand",
			headerText : "브랜드",
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
			dataField : "creator",
			headerText : "수정자",
			dataType : "string",
			width : 100
		}, {
			dataField : "createdDate",
			headerText : "수정일자",
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
		// 			rowNumHeaderText : "번호",
		// 			usePaging : true
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
			var url = _url("/ecr/list");
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
			var url = _url("/ecr/view", rowItem.oid);
			_popup(url, "", "", "f");
		});

		//jquery
		$(function() {

			$("#approvalBtn").click(function() {
				var items = AUIGrid.getCheckedRowItems(myGridID);
				if (items.length == 0) {
					alert("결재 할 ECR을 선택하세요.");
					return false;
				}
				<%
					if(!CommonUtils.isAdmin()) {	
				%>
				var state = items[0].item.state;
				if(state != "작업 중") {
					alert("작업 중 상태의 ECR만 결재가 가능합니다.");
					return false;
				}
				<%
					}
				%>
				var oid = items[0].item.oid;
				var url = _url("/app/register", oid);
				_popup(url, 1300, 600, "n");
			})

			$("#registBtn").click(function() {
				var url = _url("/ecr/create");
				// 				_popup(url, "1000", "600", "n");
				_popup(url, "", "", "f");
			})

			$("#modifyBtn").click(function() {
				var items = AUIGrid.getCheckedRowItems(myGridID);
				if (items.length == 0) {
					alert("수정 할 ECR을 선택하세요.");
					return false;
				}
				var oid = items[0].item.oid;
				var url = _url("/ecr/modify", oid);
				_popup(url, "", "", "f");
			})

			$("#excelBtn").click(function() {
				_excel(myGridID, "문서", []);
			})

			$("#deleteBtn").click(function() {
				var items = AUIGrid.getCheckedRowItems(myGridID);
				if (items.length == 0) {
					alert("삭제 할 문서를 선택하세요.");
					return false;
				}

				if (!confirm("삭제 하시겠습니까?")) {
					return false;
				}
				var oid = items[0].item.oid;
				var url = _url("/ecr/delete", oid);
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
			_user("creator");
			_finder("part", "/util/part");
			_selector("state");
			_between("endCreatedDate");
			_selector("reqType");
			_selector("brand");
			_selector("company");
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
</body>
</html>