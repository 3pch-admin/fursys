<%@page import="net.sf.json.JSONArray"%>
<%@page import="platform.user.service.UserHelper"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<table class="search-table top-color close">
	<colgroup>
		<col width="150">
		<col width="600">
		<col width="150">
		<col width="600">
	</colgroup>
	<tr>
		<th>CAD Total Ass'y</th>
		<td>DT-H37-5-800-T</td>
		<th>ERP CODE</th>
		<td>CCC0054PN</td>
	</tr>
</table>
<table class="button-table part-button close">
	<tr>
		<td class="right">
			<!-- 			<input type="hidden" name="header"> -->
			<button type="button" id="selectBom">단품(품목) 추가</button>
			<!-- 			<button type="button" id="selectPartBtn">자재 추가</button> -->
			<button type="button" id="deletePartBtn">삭제</button>
		</td>
</table>
<div id="part_grid_wrap" style="height: 830px;" class="close"></div>
<style type="text/css">
.aui-grid-tree-minus-icon {
	display: inline-block;
	width: 16px;
	height: 16px;
	border: none;
	background: url(/Windchill/jsp/asset/AUIGrid/images/arrow-downright.png) 50% 50% no-repeat;
	background-size: 16px;
	vertical-align: bottom;
	margin: 0 2px 0 0;
}

.aui-grid-tree-plus-icon {
	display: inline-block;
	width: 16px;
	height: 16px;
	border: none;
	background: url(/Windchill/jsp/asset/AUIGrid/images/arrow-right.png) 50% 50% no-repeat;
	background-size: 16px;
	vertical-align: bottom;
	margin: 0 2px 0 0;
}

.aui-grid-tree-branch-icon {
	display: inline-block;
	width: 18px;
	height: 16px;
	background: url(/Windchill/jsp/images/part.gif) 50% 50% no-repeat;
	background-size: 16px;
	vertical-align: bottom;
	margin: 0 2px 0 0;
}

.aui-grid-tree-leaf-icon {
    display: inline-block;
    width: 16px;
    height: 16px;
    background: url(/Windchill/jsp/images/part.gif) 50% 50% no-repeat;
    vertical-align: bottom;
    margin: 0 2px 0 4px;
}

.aui-grid-tree-branch-open-icon {
	display: inline-block;
	width: 18px;
	height: 16px;
	background: url(/Windchill/jsp/images/part.gif) 50% 50% no-repeat;
	background-size: 16px;
	vertical-align: bottom;
	margin: 0 2px 0 0;
}
</style>
<script type="text/javascript">
	var partGridID;
	var columnLayout = [ {
		// 		dataField : "thumb",
		headerText : "T",
		dataType : "string",
		width : 40,
		renderer : {
			type : "IconRenderer",
			iconWidth : 30,
			iconHeight : 22,
			iconFunction : function(rowIndex, columnIndex, value, item) {
				return item.thumb;
			}
		}
	}, {
		dataField : "number",
		headerText : "부품번호",
		dataType : "string",
		width : 300,
		style : "left indent10"
	}, {
		dataField : "managerName",
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
	}, {
		dataField : "manager",
		headerText : "manager",
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
		showAutoNoDataMessage : false,
		displayTreeOpen : true,
		treeColumnIndex : 1,
		rowCheckDependingTree : true,
		rowCheckDisabledFunction : function(rowIndex, isChecked, item) {
			if (item.pId != 0) {
				return false; // false 반환하면 disabled 처리됨
			}
			return true;
		}
	};

	partGridID = AUIGrid.create("#part_grid_wrap", columnLayout, auiGridProps);
	$(function() {
		$("#selectBom").click(function() {
			var url = "/Windchill/platform/ebomMaster/popup?box=1&callBack=part";
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
		console.log(list);
		// 		var grid = AUIGrid.getGridData(partGridID);
		AUIGrid.addTreeRow(partGridID, list);
	}
</script>