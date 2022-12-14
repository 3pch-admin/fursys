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
			<span>엣지사양선택</span>
			>
			<span>모양/두께 </span>
		</div>
		<table class="button-table">
			<tr>
				<td class="left">
					<button type="button" id="addRowBtn">추가</button>
					<button type="button" id="deleteRowBtn">삭제</button>
					<button type="button" id="saveBtn">저장</button>
				</td>
				<td class="right">
					<input type="file" id="importBtn" name="files" accept=".xlsx" style="display: none">
					<label class="importBtn" for="importBtn">가져오기</label>
					<button type="button" id="excelBtn">엑셀</button>
					<!-- <button type="button" id="importBtn">가져오기</button> -->
				</td>
			</tr>
		</table>
		<div id="grid_wrap" style="height: 790px;"></div>
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
				dataField : "shape",
				headerText : "형태/두께",
				dataType : "string",
				width : 500,
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
				// 				rowCheckToRadio : true,
				showRowCheckColumn : true,
				enableFilter : true,
				// 				showInlineFilter : true,
				editable : true,
				filterLayerWidth : 280,
				filterLayerHeight : 340,
				rowNumHeaderText : "번호"
			};
			myGridID = AUIGrid.create("#grid_wrap", columnLayout, auiGridProps);
			function load() {
				var params = _data($("#form"));
				params.type = "eshape";
				var url = _url("/edgespec/list");
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
					var url = _url("/edgespec/eshape");
					_call(url, params, function(data) {
						load();
					}, "POST");
				});

				$("#excelBtn").click(function() {
					_excel(myGridID, "엣지사양 - 모양/두께", []);
				})
				$("#importBtn").on('change', function(evt) {
					var data = null;
					var file = evt.target.files[0];
					if (typeof file == "undefined") {
						alert("파일 선택 시 오류 발생!!");
						return;
					}
					var reader = new FileReader();

					reader.onload = function(e) {
						var data = e.target.result;
						var workbook;
						var jsonObj = process_wb(workbook);
						createAUIGrid(jsonObj[Object.keys(jsonObj)[0]]);
					};
					reader.readAsArrayBuffer(file);
				});
				function process_wb(wb) {
					var output = "";
					output = JSON.stringify(to_json(wb));
					output = output.replace(/<!\[CDATA\[(.*?)\]\]>/g, '$1');
					return JSON.parse(output);
				}

				// 엑셀 시트를 파싱하여 반환
				function to_json(workbook) {
					var result = {};
					workbook.SheetNames.forEach(function(sheetName) {
						// JSON 으로 파싱
						//var roa = XLSX.utils.sheet_to_row_object_array(workbook.Sheets[sheetName]);

						// CSV 로 파싱
						var roa = XLSX.utils.sheet_to_csv(workbook.Sheets[sheetName]);

						if (roa.length > 0) {
							result[sheetName] = roa;
						}
					});
					return result;
				}
				function createAUIGrid(csvStr) {
					if (AUIGrid.isCreated(myGridID)) {
						AUIGrid.destroy(myGridID);
						myGridID = null;
					}
					var jsonData = parseCsv(csvStr);
					var columnLayout = [];
					var gridProps = {
						selectionMode : "multipleCells"
					};

					// 현재 엑셀 파일의 0번째 행을 기준으로 컬럼을 작성함.
					// 만약 상단에 문서 제목과 같이 있는 경우
					// 조정 필요.
					var firstRow = jsonData[0];

					if (typeof firstRow == "undefined") {
						alert("AUIGrid 로 변환할 수 없는 엑셀 파일입니다.");
						return;
					}

					$.each(firstRow, function(n, v) {
						columnLayout.push({
							dataField : n,
							headerText : "Col" + (1 + n),
							width : 100
						});
					});
					// 그리드에 CSV 데이터 삽입
					AUIGrid.setCsvGridData(myGridID, csvStr, false);
				}
				;
				$(window).resize(function() {
					AUIGrid.resize("#grid_wrap");
				})
			})
		</script>
	</form>
</body>
</html>