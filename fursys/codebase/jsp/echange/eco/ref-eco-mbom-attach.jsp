<%@page import="net.sf.json.JSONArray"%>
<%@page import="platform.user.service.UserHelper"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<table class="button-table part-button close">
	<tr>
		<td class="left">
			<!-- 			<input type="hidden" name="header"> -->
			<button type="button" id="selectBom">세트/단품 추가</button> <!-- 			<button type="button" id="selectPartBtn">자재 추가</button> -->
			<button type="button" id="deletePartBtn">삭제</button>
		</td>
</table>
<div id="part_grid_wrap" style="height: 830px;" class="close"></div>
<script type="text/javascript">
	var partGridID;
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
		dataField : "thumb",
		headerText : "T",
		dataType : "string",
		width : 40,
		renderer : {
			type : "IconRenderer",
			iconWidth : 30,
			iconHeight : 22,
			iconFunction : function(rowIndex, columnIndex, value, item) {
				return item.t;
			}
		}
	}, {
		dataField : "number",
		headerText : "부품번호",
		dataType : "string",
		width : 300,
		style : "left indent10"
	}, {
		dataField : "manager",
		headerText : "접수자",
		dataType : "string",
		width : 100
	}, {
		dataField : "applyColor",
		headerText : "파생색상",
		dataType : "string",
		width : 100
	}, {
		dataField : "partType",
		headerText : "부품유형",
		dataType : "string",
		width : 90
	}, {
		dataField : "name",
		headerText : "부품명칭",
		dataType : "string",
		style : "left indent10"
	}, {
		dataField : "version",
		headerText : "버전",
		dataType : "string",
		width : 80
	}, {
		dataField : "ecoNumber",
		headerText : "ECO 번호",
		dataType : "string",
		width : 150
	}, {
		dataField : "erpCode",
		headerText : "ERP CODE",
		dataType : "string",
		width : 150
	}, {
		dataField : "state",
		headerText : "BOM 상태",
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
		$("#selectBom").click(function() {
			var url = "/Windchill/platform/mbom/popup?box=1&callBack=part";
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