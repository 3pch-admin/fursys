<%@page import="platform.doc.service.DocumentHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
		<div class="header-title">
			<img src="/Windchill/jsp/images/home.png" class="home">
			<span>HOME</span>
			>
			<span>라온케이 템플릿</span>
			>
			<span>라온케이 템플릿 조회</span>
		</div>
		<table id="wrap-table">
			<!-- 			<colgroup> -->
			<!-- 				<col width="200"> -->
			<!-- 				<col width="10"> -->
			<!-- 				<col width="*"> -->
			<!-- 			</colgroup> -->
			<tr>
<!-- 				<td valign="top" class="tree-border"> -->
<%-- 					<jsp:include page="/jsp/common/folder-tree.jsp"> --%>
<%-- 						<jsp:param value="<%=DocumentHelper.ROOT%>" name="location" /> --%>
<%-- 					</jsp:include> --%>
<!-- 				</td> -->
<!-- 				<td>&nbsp;</td> -->
				<td>
					<table class="search-table top-color">
						<colgroup>
							<col width="130">
							<col width="*">
							<col width="130">
							<col width="*">
						</colgroup>
						<tr>
							<th>라온케이 템플릿 명</th>
							<td>
								<input type="text" class="AXInput w70p" name="name">
							</td>
							<th>사용여부</th>
							<td>
								<select name="enable" id="enable" class="AXSelect w100px">
									<option value="">선택</option>
									<option value="true">사용</option>
									<option value="false">사용안함</option>
								</select>
							</td>
						</tr>
						<tr>
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
					</table>

					<table class="button-table">
						<tr>
							<td class="left">
								<button type="button" id="createBtn">등록</button>
								<button type="button" id="deleteBtn">삭제</button>
							</td>
							<td class="right">
								<button type="button" id="searchBtn">조회</button>
							</td>
						</tr>
					</table>

					<div id="grid_wrap" style="height: 705px;"></div>
					<script type="text/javascript">
						var myGridID;
						//head : [ "번호", "라온케이 템플릿 명", "라온케이 타입", "설명", "사용여부", "작성일자", "작성자" ]
						var columnLayout = [ {
							dataField : "name",
							headerText : "라온케이 템플릿 명",
							dataType : "string",
							style : "left indent10"
						}, {
							dataField : "enable",
							headerText : "사용여부",
							dataType : "string",
							width : 80
						}, {
							dataField : "createdDate",
							headerText : "작성일자",
							dataType : "date",
							formatString : "yyyy/mm/dd",
							width : 120
						}, {
							dataField : "creator",
							headerText : "작성자",
							dataType : "string",
							width : 120
						}, ];
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
							var url = _url("/raonk/list");
							AUIGrid.showAjaxLoader(myGridID);
							_call(url, params, function(data) {
								AUIGrid.removeAjaxLoader(myGridID);
								AUIGrid.setGridData(myGridID, data.list);
							}, "POST");
						}

						AUIGrid.bind(myGridID, "cellClick", function(event) {
							var rowItem = event.item;
							var url = _url("/raonk/view", rowItem.oid);
							_popup(url, "", "", "f");
						});

						// jquery 
						$(function() {
							$("#deleteBtn").click(function() {
								var items = AUIGrid.getCheckedRowItems(myGridID);
								if (items.length == 0) {
									alert("삭제 할 라온케이 템플릿을 선택하세요.");
									return false;
								}

								if (!confirm("삭제 하시겠습니까?")) {
									return false;
								}
								var oid = items[0].item.oid;
								var url = _url("/raonk/delete", oid);
								_call(url, null, function(data) {
									load();
								}, "GET");
							})

							$("#createBtn").click(function() {
								var url = _url("/raonk/create");
								_popup(url, "", "", "f");
							})

							$("#searchBtn").click(function() {
								load();
							})
							// 작성자, 수정자 선택바인드
							_between("endCreatedDate")
							_between("endModifiedDate")
							_user("creator");
							_selector("enable");
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
	</form>
</body>
</html>