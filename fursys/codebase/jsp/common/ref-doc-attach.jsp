<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<table class="button-table doc-button close">
	<tr>
		<td class="right">
			<button type="button" id="selectDocBtn">추가</button>
			<button type="button" id="deleteDocBtn">제거</button>
		</td>
	</tr>
</table>
<div id="doc_grid_wrap" style="height: 830px;" class="close"></div>
<script type="text/javascript">
	var docGridID;
	var columnLayout = [ {
		dataField : "number",
		headerText : "문서번호",
		dataType : "string",
		width : 120
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
		fillColumnSizeMode : true,
		showRowCheckColumn : true,
		showRowNumColumn : false,
		softRemoveRowMode : false,
		showAutoNoDataMessage : false
	};
	docGridID = AUIGrid.create("#doc_grid_wrap", columnLayout, auiGridProps);

	$(function() {
		$("#selectDocBtn").click(function() {
			var url = "/Windchill/platform/doc/popup";
			_popup(url, "", "", "f");
		})

		$("#deleteDocBtn").click(function() {
			var items = AUIGrid.getCheckedRowItems(docGridID);
			if (items.length == 0) {
				alert("삭제 할 행을 선택하세요.");
			}
			for (var i = 0; i < items.length; i++) {
				AUIGrid.removeRow(docGridID, items[i].rowIndex);
			}
		})
	})

	function doc(list) {
// 		AUIGrid.clearGridData(docGridID);
		AUIGrid.setGridData(docGridID, list);
	}
</script>
