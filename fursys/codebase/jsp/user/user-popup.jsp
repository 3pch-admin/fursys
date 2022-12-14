<%@page import="platform.doc.service.DocumentHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String target = (String) request.getAttribute("target");
String callBack = (String) request.getParameter("callBack");
%>
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
		<td valign="top">
			<jsp:include page="/jsp/user/dept-tree.jsp" />
		</td>
		<td>&nbsp;</td>
		<td valign="top">
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

			<div id="grid_wrap" style="height: 600px;"></div>
			<script type="text/javascript">
				var myGridID;
				var columnLayout = [ {
					dataField : "userId",
					headerText : "아이디",
					dataType : "string",
					width : 120
				}, {
					dataField : "userName",
					headerText : "이름",
					dataType : "string",
					width : 120
				}, {
					dataField : "deptName",
					headerText : "부서명",
					dataType : "string",
					width : 150
				}, {
					dataField : "duty",
					headerText : "직급",
					dataType : "string",
					width : 120
				}, {
					dataField : "email",
					headerText : "이메일",
					dataType : "string",
					style : "left indent10"
				}, {
					dataField : "oid",
					headerText : "oid",
					dataType : "string",
					visible : false
				},{
					dataField : "woid",
					headerText : "woid",
					dataType : "string",
					visible : false
				},  ];
				var auiGridProps = {
					rowIdField : "oid",
					headerHeight : 30,
					rowHeight : 30,
					fillColumnSizeMode : true,
					rowCheckToRadio : true,
					showRowCheckColumn : true,
					rowNumHeaderText : "번호"
				};
				myGridID = AUIGrid.create("#grid_wrap", columnLayout, auiGridProps);

				function load() {
					var params = _data($("#form"));
					var url = _url("/user/list");
					AUIGrid.showAjaxLoader(myGridID);
					_call(url, params, function(data) {
						AUIGrid.removeAjaxLoader(myGridID);
						AUIGrid.setGridData(myGridID, data.list);
					}, "POST");
				}

				AUIGrid.bind(myGridID, "cellClick", function(event) {
					var rowItem = event.item;
					AUIGrid.setCheckedRowsByIds(myGridID, rowItem.oid);
				});
				

				$(function() {
					load();
					
					$("#closeBtn").click(function() {
						self.close();
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
						<%if ("manager".equals(callBack)) {%>
						opener.manager(userName, woid, "<%=target%>");
<%-- 						$(opener.document).find(".managerName<%=target%>").before("<input type='hidden' name='manager' value='" + woid + "'>"); --%>
<%-- 						$(opener.document).find(".managerName<%=target%>").val(userName); --%>
						self.close();
						<%} else {%>
						var target = "<%=target%>";
						var targetID = target + "Oid";
						$(opener.document).find("#" + targetID).remove();
						$(opener.document).find("#" + target).before("<input type=\"hidden\" name=\"" + targetID + "\" id=\"" + targetID + "\"> ");
						$(opener.document).find("#" + targetID).val(oid);
						$(opener.document).find("#" + target).val(userName);
						self.close();
			<%}%>
				})

					$("#searchBtn").click(function() {
						load();
					})

				}).keypress(function(e) {
					if (e.keyCode == 13) {
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