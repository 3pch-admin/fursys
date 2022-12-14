<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<table class="button-table part-button close">
	<tr>
		<td class="left">
			<button type="button" id="selectPartBtn">추가</button>
			<button type="button" id="deletePartBtn">삭제</button>
		</td>
	</tr>
</table>
<div id="part_grid_wrap" style="height: 830px;" class="close"></div>
<script type="text/javascript">
	var partGridID;
	var columnLayout = [ {
		headerText : "",
		dataType : "string",
		width : 40,
		renderer : {
			type : "IconRenderer",
			iconWidth : 16,
			iconHeight : 16,
			iconFunction : function(rowIndex, columnIndex, value, item) {
				return item.p;
			}
		}
	}, {
		dataField : "thumb",
		headerText : "3D",
		dataType : "string",
		width : 40,
		renderer : {
			type : "IconRenderer",
			iconWidth : 30,
			iconHeight : 22,
			iconFunction : function(rowIndex, columnIndex, value, item) {
				return item._3d;
			},
			onClick : function(event) {
				var item = event.item;

				if (item._3d.indexOf("no-view.png") <= -1) {
					_openCreoView(item.eoid);
				}
			}
		}
	}, {
		// 							dataField : "_2d",
		headerText : "2D",
		dataType : "string",
		width : 40,
		renderer : {
			type : "IconRenderer",
			iconWidth : 30,
			iconHeight : 22,
			iconFunction : function(rowIndex, columnIndex, value, item) {
				return item._2d;
			},
			onClick : function(event) {
				var item = event.item;
				if (item._2d.indexOf("no-view.png") <= -1) {
					_openCreoView(item.eoid2d);
				}
			}
		}
	}, {
		dataField : "partType",
		headerText : "부품유형",
		dataType : "string",
		width : 80
	}, {
		dataField : "number",
		headerText : "부품번호",
		dataType : "string",
		width : 250,
		style : "left indent10"
	}, {
		dataField : "name",
		headerText : "부품명칭",
		dataType : "string",
		width : 350,
		style : "left indent10"
	}, {
		dataField : "version",
		headerText : "버전",
		dataType : "string",
		width : 60
	}, {
		dataField : "ref",
		headerText : "CAD연계",
		dataType : "string",
		width : 80
	}, {
		dataField : "derived",
		headerText : "파생여부",
		dataType : "string",
		width : 60
	}, {
		dataField : "creator",
		headerText : "작성자",
		dataType : "string",
		width : 80
	}, {
		dataField : "createdDate",
		headerText : "작성일자",
		dataType : "date",
		formatString : "yyyy/mm/dd",
		width : 100
	}, {
		dataField : "modifier",
		headerText : "수정자",
		dataType : "string",
		width : 80
	}, {
		dataField : "modifiedDate",
		headerText : "수정일자",
		dataType : "date",
		formatString : "yyyy/mm/dd",
		width : 100
	}, {
		dataField : "state",
		headerText : "상태",
		dataType : "string",
		width : 130
	}, {
		dataField : "erpCode",
		headerText : "ERP CODE",
		dataType : "string",
		width : 120
	}, {
		dataField : "companyNm",
		headerText : "회사",
		dataType : "string",
		width : 100
	}, {
		dataField : "brandNm",
		headerText : "브랜드",
		dataType : "string",
		width : 100
	}, {
		dataField : "unit",
		headerText : "단위",
		dataType : "string",
		width : 100
	}, {
		dataField : "oid",
		headerText : "oid",
		dataType : "string",
		visible : false
	}, ];
	var auiGridProps = {
		rowIdField : "rowId",
		headerHeight : 30,
		rowHeight : 30,
		showRowNumColumn : true,
		showRowCheckColumn : true,
		rowNumHeaderText : "번호",
		softRemoveRowMode : false,
		showAutoNoDataMessage : false
	};

	partGridID = AUIGrid.create("#part_grid_wrap", columnLayout, auiGridProps);
	$(function() {
		$("#selectPartBtn").click(function() {
			var url = "/Windchill/platform/part/popup?box=2";
			_popup(url, "", "", "f");
		})

		$("#deletePartBtn").click(function() {
			var items = AUIGrid.getCheckedRowItems(partGridID);
			if (items.length == 0) {
				alert("삭제 할 행을 선택하세요.");
			}
			AUIGrid.removeCheckedRows(partGridID);
		})
	})
	function part(list) {
		AUIGrid.addRow(partGridID, list);
	}
</script>