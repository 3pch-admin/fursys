<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<table class="button-table eco-button close">
	<tr>
		<td class="left none">
			<input type="hidden" name="eoid">
			<button type="button" id="selectEcoBtn">추가</button>
			<button type="button" id="deleteEcoBtn">삭제</button>
			<button type="button" id="stockBtn">재고수량 조회</button>
		</td>
	</tr>
</table>
<div id="eco_grid_wrap" style="height: 830px;" class="close"></div>
<script type="text/javascript">
	var ecoGridID;
	var columnLayout = [ {
		headerText : "P",
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
		dataField : "s",
		headerText : "S",
		dataType : "string",
		width : 40
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
			}
		}
	}, {
		// 							dataField : "_2d",
		headerText : "2D",
		dataType : "string",
		width : 40,
		renderer : {
			type : "IconRenderer",
			iconWidth : 16,
			iconHeight : 16,
			iconFunction : function(rowIndex, columnIndex, value, item) {
				return item._2;
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
		dataField : "order",
		headerText : "주문품여부",
		dataType : "string",
		width : 100
	}, {
		dataField : "use",
		headerText : "사용여부",
		dataType : "string",
		width : 100
	}, {
		dataField : "w",
		headerText : "규격가로(W)",
		dataType : "string",
		width : 100
	}, {
		dataField : "d",
		headerText : "규격세로(D)",
		dataType : "string",
		width : 100
	}, {
		dataField : "h",
		headerText : "사용높이(H)",
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
		// 		fillColumnSizeMode : true,
		// 		showRowCheckColumn : true,
		showRowNumColumn : false,
		softRemoveRowMode : false
	};
	ecoGridID = AUIGrid.create("#eco_grid_wrap", columnLayout, auiGridProps);

	$(function() {
		$("#selectEcoBtn").click(function() {
			var url = "/Windchill/platform/eco/popup";
			_popup(url, "", "", "f");
		})

		$("#deleteEcoBtn").click(function() {
			if (!confirm("모든 데이터를 삭제 합니다.")) {
				return false;
			}
			AUIGrid.clearGridData(ecoGridID);
		})

		$("#stockBtn").click(function() {
			var url = _url("/erp/getStock");
			_popup(url, 1200, 600, "n");
		})
	})

	function eco(list, oid) {
		$("input[name=eoid]").val(oid);
		AUIGrid.addRow(ecoGridID, list);
	}
</script>
