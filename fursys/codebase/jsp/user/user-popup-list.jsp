<%@page import="platform.doc.service.DocumentHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String target = (String) request.getAttribute("target");
String callBack = (String) request.getParameter("callBack");
%>
<input type="hidden" name="sessionid" id="sessionid">
<input type="hidden" name="tpage" id="tpage">
<div class="header-title">
	<img src="/Windchill/jsp/images/home.png" class="home">
	<span>HOME</span>
	>
	<span>관리자</span>
	>
	<span>사용자 관리</span>
</div>
<table id="wrap-table">
	<colgroup>
		<col width="200">
		<col width="10">
		<col width="*">
	</colgroup>
	<tr>
		<td valign="top" class="tree-border">
			<jsp:include page="/jsp/user/dept-tree.jsp" />
		</td>
		<td>&nbsp;</td>
		<td valign="top" class="top-color">
			<table class="search-table">
				<colgroup>
					<col width="100">
					<col width="*">
					<col width="100">
					<col width="*">
				</colgroup>
				<tr>
					<th>이름</th>
					<td>
						<input type="hidden" name="deptOid" id="deptOid">
						<input type="text" class="AXInput w70p" name="name" autofocus="autofocus">
					</td>
					<th>아이디</th>
					<td>
						<input type="text" class="AXInput w70p" name="username">
					</td>
				</tr>
			</table>

			<table class="button-table">
				<tr>
					<td class="right">
						<button type="button" id="addBtn">추가</button>
						<button type="button" id="searchBtn">조회</button>
						<button type="button" id="closeBtn">닫기</button>
					</td>
				</tr>
			</table>

			<div id="grid_wrap" style="height: 480px;"></div>
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
					width : 40,
					editable : false
				}, {
					dataField : "userName",
					headerText : "이름",
					dataType : "string",
					width : 150,
					editable : false
				}, {
					dataField : "userId",
					headerText : "아이디",
					dataType : "string",
					width : 150,
					editable : false
				}, {
					dataField : "deptName",
					headerText : "부서명",
					dataType : "string",
					width : 200,
					editable : false
				}, {
					dataField : "brandNm",
					headerText : "브랜드",
					dataType : "string",
					width : 150,
					editable : false,
				}, {
					dataField : "email",
					headerText : "이메일",
					dataType : "string",
					style : "left indent10",
					editable : false
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
					editable : true,
					rowCheckToRadio : true,
					showRowCheckColumn : true,
					fillColumnSizeMode : true,
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
					var url = _url("/user/list");
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

				AUIGrid.bind(myGridID, "cellClick", function(event) {
					var rowItem = event.item;
					AUIGrid.setCheckedRowsByIds(myGridID, rowItem.oid);
				});
				
				AUIGrid.bind(myGridID, "cellDoubleClick", function(event) {
					var rowItem = event.item;
					var oid = rowItem.oid;
					var woid = rowItem.woid;
					var userName = rowItem.userName;
					var userId = rowItem.userId;
					var email = rowItem.email;
					<%if ("manager".equals(callBack)) {%>
					opener.manager(userName, woid, "<%=target%>");
					self.close();
					<%} else if ("distributor".equals(callBack)) {%>
					opener.distributor(userId, userName, email);
					self.close();
					<%} else {%>
					var target = "<%=target%>";
					var targetID = target + "Oid";
					$(opener.document).find("#" + targetID).remove();
					$(opener.document).find("#" + target).before("<input type=\"hidden\" name=\"" + targetID + "\" id=\"" + targetID + "\"> ");
					$(opener.document).find("#" + targetID).val(woid);
					$(opener.document).find("#" + target).val(userName);
					self.close();
					<%}%>
				});

				function moveToPage(goPage) {
					createPagingNavigator(goPage);
					currentPage = goPage;
					$("input[name=tpage").val(goPage);
					load();
				}

				
				$(function() {
					load();

					$("#closeBtn").click(function() {
						self.close();
					})

					$("#searchBtn").click(function() {
						currentPage = 1;
						$("input[name=tpage").val(1);
						$("input[name=sessionid").val(0);
						load();
					})
					
					$("#addBtn").click(function() {
						var items = AUIGrid.getCheckedRowItems(myGridID);
						if (items.length == 0) {
							alert("추가 할 사용자를 선택하세요.");
							return false;
						}
						var oid = items[0].item.oid;
						var woid = items[0].item.woid;
						var userName = items[0].item.userName;
						var userId = items[0].item.userId;
						var email = items[0].item.email;
						<%if ("manager".equals(callBack)) {%>
						opener.manager(userName, woid, "<%=target%>");
						self.close();
						<%} else if ("distributor".equals(callBack)) {%>
						opener.distributor(userId, userName, email);
						self.close();
						<%} else {%>
						var target = "<%=target%>";
						var targetID = target + "Oid";
						$(opener.document).find("#" + targetID).remove();
						$(opener.document).find("#" + target).before("<input type=\"hidden\" name=\"" + targetID + "\" id=\"" + targetID + "\"> ");
						$(opener.document).find("#" + targetID).val(woid);
						$(opener.document).find("#" + target).val(userName);
						self.close();
						<%}%>
				})

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