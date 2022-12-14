<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
		<div class="header-title">
			<img src="/Windchill/jsp/images/home.png" class="home">
			<span>HOME</span>
			>
			<span>목재 관리</span>
			>
			<span>목재 표면재</span>
		</div>
		<table class="button-table">
			<tr>
				<td class="left">
					<button type="button" id="addRowBtn">추가</button>
					<button type="button" id="deleteRowBtn">삭제</button>
					<button type="button" id="saveBtn">저장</button>
				</td>
				<td class="right">
					<button type="button" id="excelBtn">엑셀</button>
				</td>
			</tr>
		</table>
		<div id="grid_wrap" style="height: 800px;"></div>
		<script type="text/javascript">
			var myGridID;
			var columnLayout = [ {
				dataField : "code",
				headerText : "CODE",
				dataType : "string",
				width : 150,
				filter : {
					showIcon : true
				}
			}, {
				dataField : "rank",
				headerText : "RANK",
				dataType : "string",
				width : 80,
				filter : {
					showIcon : true
				}
			}, {
				dataField : "material",
				headerText : "재질",
				dataType : "string",
				style : "left indent10",
				filter : {
					showIcon : true
				}
			}, {
				dataField : "creator",
				headerText : "등록자",
				dataType : "string",
				width : 150,
				filter : {
					showIcon : true
				},
				editable : false
			}, {
				dataField : "createdDate",
				headerText : "등록일",
				dataType : "string",
				width : 150,
				filter : {
					showIcon : true
				},
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
				fillColumnSizeMode : true,
				//	 				rowCheckToRadio : true,
				showRowCheckColumn : true,
				enableFilter : true,
				// 					showInlineFilter : true,
				editable : true,
				filterLayerWidth : 280,
				filterLayerHeight : 340,
				rowNumHeaderText : "번호"
			};
			myGridID = AUIGrid.create("#grid_wrap", columnLayout, auiGridProps);
			function load() {
				var params = _data($("#form"));
				params.type = "surface";
				var url = _url("/tree/list");
				AUIGrid.showAjaxLoader(myGridID);
				_call(url, params, function(data) {
					AUIGrid.removeAjaxLoader(myGridID);
					AUIGrid.setGridData(myGridID, data.list);
				}, "POST");
			}
			$(function() {
				$("#addRowBtn").click(function() {
					var item = new Object();
					// 					item.createdDate = new Date();
					AUIGrid.addRow(myGridID, item, "last"); // 최하단에 1행 추가
				})

				$("#deleteRowBtn").click(function() {
					var items = AUIGrid.getCheckedRowItems(myGridID);
					if (items.length == 0) {
						alert("삭제 할 행을 선택하세요.");
					}
					for (var i = 0; i < items.length; i++) {
						AUIGrid.removeRow(myGridID, items[i].rowIndex);
					}
				})

				$("#saveBtn").click(function() {
					var removeRows = AUIGrid.getRemovedItems(myGridID);
					var addRows = AUIGrid.getAddedRowItems(myGridID);
					var editRows = AUIGrid.getEditedRowItems(myGridID);
					if (removeRows.length == 0 && addRows.length == 0 && editRows.length == 0) {
						return false;
					}

					if (!confirm("저장 하시겠습니까?")) {
						return false;
					}
					var params = new Object();
					params.addRows = addRows;
					params.editRows = editRows;
					params.removeRows = removeRows;
					var url = _url("/tree/surface");
					_call(url, params, function(data) {
						load();
					}, "POST");
				});

				$("#excelBtn").click(function() {
					_excel(myGridID, "목재 - 표면재", []);
				})
				$(window).resize(function() {
					AUIGrid.resize("#grid_wrap");
				})
			})
		</script>
	</form>
</body>
</html>