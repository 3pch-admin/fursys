<%@page import="net.sf.json.JSONArray"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
ArrayList<Map<String, Object>> list = (ArrayList<Map<String, Object>>) request.getAttribute("list");
JSONArray darr = JSONArray.fromObject(list);
%>
<style type="text/css">
.compare {
	background-color: #e3ed8b;
}
</style>
<div class="header-title">
	<img src="/Windchill/jsp/images/home.png" class="home">
	<span>HOME</span>
	>
	<span>BOM관리</span>
	>
	<span>EBOM 검증</span>
</div>

<div id="grid_wrap" style="height: 700px;"></div>

<table class="button-table">
	<tr>
		<td class="right">
			<button type="button" id="confirmBtn">확인</button>
			<button type="button" id="closeBtn">닫기</button>
		</td>
	</tr>
</table>

<script type="text/javascript">
	var myGridID;
	var columnLayout = [ {
		dataField : "cadName",
		headerText : "CAD 파일명",
		dataType : "string",
		style : "left indent10",
		editable : false,
	}, {
		dataField : "partType",
		headerText : "부품유형",
		dataType : "string",
		width : 100,
		filter : {
			showIcon : true
		},
		editable : false,
	}, {
		dataField : "itemName",
		headerText : "제품코드(ITEM_NAME)",
		dataType : "string",
		editable : false,
		width : 250
	}, {
		dataField : "partName",
		headerText : "부품명(PART_NAME)",
		dataType : "string",
		editable : false,
		width : 250
	}, {
		dataField : "partNo",
		headerText : "부품번호(PART_NO)",
		dataType : "string",
		editable : false,
		width : 250
	}, {
		dataField : "camount",
		headerText : "CBOM 수량",
		dataType : "string",
		postFix : "개",
		editable : false,
		width : 100
	}, {
		dataField : "eamount",
		headerText : "EBOM 수량",
		dataType : "string",
		postFix : "개",
		editable : false,
		width : 100
	}, {
		dataField : "compare",
		headerText : "수량",
		dataType : "string",
		postFix : "개",
		editable : false,
		width : 100
	}, {
		dataField : "oid",
		headerText : "oid",
		dataType : "string",
		visible : false
	}, ];

	var footerLayout = [ {
		dataField : "partNo",
		positionField : "partNo",
		labelText : "총 수량",
	}, {
		dataField : "camount",
		positionField : "camount",
		postfix : "개",
		operation : "SUM",
	}, {
		dataField : "eamount",
		positionField : "eamount",
		postfix : "개",
		operation : "SUM",
	}, {
		dataField : "compare",
		positionField : "compare",
		postfix : "개",
		operation : "SUM",
	} ];

	var auiProps = {
		// 		rowIdField : "oid",
		headerHeight : 30,
		rowHeight : 30,
		showRowNumColumn : true,
		showFooter : true,
		enableFilter : true,
		rowNumHeaderText : "번호",
		rowStyleFunction : function(rowIndex, item) {
			if (item.eamount != item.camount) {
				return "compare";
			}
			return "";
		}
	};

	myGridID = AUIGrid.create("#grid_wrap", columnLayout, auiProps);
	AUIGrid.setFooter(myGridID, footerLayout);
	AUIGrid.setFooter(myGridID, footerLayout);
	AUIGrid.setGridData(myGridID,
<%=darr%>
	);
	$(window).resize(function() {
		AUIGrid.resize("#grid_wrap");
	})
</script>