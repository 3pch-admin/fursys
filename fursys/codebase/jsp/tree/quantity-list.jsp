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
			<span>BOM 소요량</span>
			>
			<span>표준소요량마스터</span>
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
				dataField : "commonCode",
				headerText : "표준유형코드",
				dataType : "string",
				width : 150,
				filter : {
					showIcon : true
				}
			}, {
				dataField : "sort",
				headerText : "항번",
				dataType : "string",
				width : 80,
				filter : {
					showIcon : true
				}
			}, {
				dataField : "commonName",
				headerText : "표준유형명",
				dataType : "string",
				width : 300,
				style : "left indent10",
				filter : {
					showIcon : true
				}
			}, {
				dataField : "materialInfo",
				headerText : "재질정보",
				dataType : "string",
				width : 140,
				filter : {
					showIcon : true
				}
			}, {
				dataField : "processInfo",
				headerText : "가공정보",
				dataType : "string",
				width : 140,
				filter : {
					showIcon : true
				}
			}, {
				dataField : "treatment",
				headerText : "표면처리",
				dataType : "string",
				width : 140,
				filter : {
					showIcon : true
				}
			}, {
				dataField : "bomLevel",
				headerText : "BOM 레벨",
				dataType : "string",
				width : 80,
				filter : {
					showIcon : true
				}
			// 			}, {
			// 				dataField : "requiredProcess",
			// 				headerText : "소요공정",
			// 				dataType : "string",
			// 				width : 140,
			// 				filter : {
			// 					showIcon : true
			// 				}
			}, {
				dataField : "materialCode",
				headerText : "자재코드",
				dataType : "string",
				width : 170,
				filter : {
					showIcon : true
				}
			}, {
				dataField : "color",
				headerText : "색상",
				dataType : "string",
				width : 100,
				filter : {
					showIcon : true
				}
			}, {
				dataField : "materialName",
				headerText : "자재명",
				dataType : "string",
				width : 300,
				style : "left indent10",
				filter : {
					showIcon : true
				}
			}, {
				dataField : "bom_unit",
				headerText : "단위",
				dataType : "string",
				width : 80,
				filter : {
					showIcon : true
				}
			}, {
				dataField : "x1",
				headerText : "변수(X1)",
				dataType : "string",
				width : 200,
				filter : {
					showIcon : true
				}
			}, {
				dataField : "x2",
				headerText : "변수(X2)",
				dataType : "string",
				width : 200,
				filter : {
					showIcon : true
				}
			}, {
				dataField : "processMargin",
				headerText : "가공마진(M)",
				dataType : "string",
				width : 80,
				style : "right",
				filter : {
					showIcon : true
				}
			}, {
				dataField : "processValue",
				headerText : "가공값(A)",
				dataType : "string",
				width : 150,
				style : "left indent10",
				filter : {
					showIcon : true
				}
			}, {
				dataField : "loss",
				headerText : "LOSS 율(B)",
				dataType : "string",
				width : 80,
				style : "right",
				filter : {
					showIcon : true
				}
			}, {
				dataField : "unitProcessAmount",
				headerText : "단위가공량(C)",
				dataType : "string",
				width : 150,
				style : "right",
				filter : {
					showIcon : true
				}
			}, {
				dataField : "unitConversion",
				headerText : "단위환산(D)",
				dataType : "string",
				width : 150,
				style : "right",
				filter : {
					showIcon : true
				}
			}, {
				dataField : "conversionFactor",
				headerText : "환산계수(E)",
				dataType : "string",
				width : 150,
				style : "right",
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
				// 				fillColumnSizeMode : true,
				// 				rowCheckToRadio : true,
				showRowCheckColumn : true,
				enableFilter : true,
				// 				showInlineFilter : true,
				filterLayerWidth : 280,
				filterLayerHeight : 340,
				editable : true,
				rowNumHeaderText : "번호"
			};
			myGridID = AUIGrid.create("#grid_wrap", columnLayout, auiGridProps);
			function load() {
				var params = _data($("#form"));
				var url = _url("/quantity/list");
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
					var url = _url("/quantity/create");
					_call(url, params, function(data) {
						load();
					}, "POST");
				});

				$("#excelBtn").click(function() {
					_excel(myGridID, "BOM - 표준소요량", []);
				})
				$(window).resize(function() {
					AUIGrid.resize("#grid_wrap");
				})
			})
		</script>
	</form>
</body>
</html>