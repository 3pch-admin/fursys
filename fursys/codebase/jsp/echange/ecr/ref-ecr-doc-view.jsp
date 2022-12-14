<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="doc_grid_wrap" style="height: 840px;" class="close"></div>
<script type="text/javascript">
	var docGridID;
	var columnLayout = [ {
		dataField : "number",
		headerText : "문서번호",
		dataType : "string",
		width : 150
	}, {
		dataField : "name",
		headerText : "문서명",
		dataType : "string",
		style : "left indent10"
	}, {
		dataField : "version",
		headerText : "버전",
		dataType : "string",
		width : 80
	}, {
		dataField : "receive",
		headerText : "수신자",
		dataType : "string",
		width : 100
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
		dataField : "modifier",
		headerText : "수정자",
		dataType : "string",
		width : 100
	}, {
		dataField : "modifiedDate",
		headerText : "수정일자",
		dataType : "date",
		formatString : "yyyy/mm/dd",
		width : 100
	}, {
		dataField : "companyNm",
		headerText : "회사",
		dataType : "string",
		width : 130
	}, {
		dataField : "brandNm",
		headerText : "브랜드",
		dataType : "string",
		width : 130
	}, {
		dataField : "state",
		headerText : "상태",
		dataType : "string",
		width : 100
	}, {
		// 							dataField : "primary",
		headerText : "첨부파일",
		dataType : "string",
		width : 80,
		renderer : {
			type : "IconRenderer",
			iconWidth : 16,
			iconHeight : 16,
			iconFunction : function(rowIndex, columnIndex, value, item) {
				return item.primary;
			},
			onClick : function(event) {
				var url = event.item.url;
			}
		}
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
		rowNumHeaderText : "번호"
	};
	docGridID = AUIGrid.create("#doc_grid_wrap", columnLayout, auiGridProps);
</script>