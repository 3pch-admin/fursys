<%@page import="platform.part.service.PartHelper"%>
<%@page import="platform.ebom.entity.EBOM"%>
<%@page import="platform.util.IBAUtils"%>
<%@page import="platform.util.CommonUtils"%>
<%@page import="wt.part.WTPart"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String oid = (String) request.getAttribute("oid");
EBOM ebom = (EBOM) request.getAttribute("ebom");
WTPart part = (WTPart) request.getAttribute("part");
%>
<style type="text/css">
.library {
	background-color: #fefbc0;
}

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

.move {
	background: #9FC93C;
	font-weight: bold;
	color: #22741C;
}
</style>
<div class="header-title">
	<img src="/Windchill/jsp/images/home.png" class="home">
	<span>HOME</span>
	>
	<span>BOM관리</span>
	>
	<span>EBOM 정보</span>
</div>
<table class="search-table top-color">
	<colgroup>
		<col width="150">
		<col width="580">
		<col width="150">
		<col width="580">
	</colgroup>
	<tr>
		<th>부품명칭</th>
		<td>
			<input type="hidden" name="oid" class="AXInput w70p" readonly="readonly" value="<%=part.getPersistInfo().getObjectIdentifier().getStringValue()%>">
			<input type="hidden" name="eoid" class="AXInput w70p" readonly="readonly" value="<%=oid%>">
			<input type="text" name="number" class="AXInput w70p" readonly="readonly" value="<%=part.getNumber()%>">
		</td>
		<th>유형</th>
		<td>
			<input type="text" name="partType" class="AXInput w30p" readonly="readonly" value="<%=PartHelper.manager.partTypeToDisplay(part)%>">
		</td>
	</tr>
</table>

<table class="button-table">
	<tr>
		<td class="left">
<!-- 			<img src="/Windchill/jsp/images/undo.gif" style="height: 22px; width: 22px;"> -->
		</td>
		<td class="right">
			<button type="button">자재재공코드생성</button>
			<button type="button">임시저장</button>
			<button type="button">저장</button>
			<button type="button" id="closeBtn">닫기</button>
		</td>
	</tr>
</table>

<div id="grid_wrap" style="height: 855px;"></div>
<script type="text/javascript">
	var myGridID;
	var columnLayout = [ {
		dataField : "",
		headerText : "",
		dataType : "string",
		width : 40,
		editable : false,
		renderer : {
			type : "IconRenderer",
			iconWidth : 30,
			iconHeight : 22,
			iconFunction : function(rowIndex, columnIndex, value, item) {
				return item.thumb;
			},
			onClick : function(event) {
				var item = event.item;

				if (item._3d.indexOf("no-view.png") <= -1) {
					_openCreoView(item.eoid);
				}
			}
		}
	}, {
		dataField : "number",
		headerText : "부품번호",
		dataType : "string",
		style : "left indent10",
		editable : false,
		width : 350,
		styleFunction : function(rowIndex, columnIndex, value, headerText, item, dataField) {
			var style = "";
			if (item.number.indexOf("ARTICLE") > -1 || item.number.indexOf("ZONE") > -1) {
				style = "red";
			}
			return style;
		},
	}, {
		dataField : "itemName",
		headerText : "ITEM_NAME",
		dataType : "string",
		width : 200,
		editable : false,
	}, {
		dataField : "partType",
		headerText : "부품유형",
		dataType : "string",
		width : 80,
		editable : false,
		styleFunction : function(rowIndex, columnIndex, value, headerText, item, dataField) {
			var style = "";
			if (item.partType == "자재") {
				style = "red";
			} else if (item.partType == "단품") {
				style = "blue";
			} else {
				style = "green";
			}
			// 로직 처리
			return style;
		}
	}, {
		dataField : "partNo",
		headerText : "PART_NO",
		dataType : "string",
		editable : false,
		width : 200
	}, {
		dataField : "partName",
		headerText : "부품명",
		dataType : "string",
		editable : false,
		width : 200
	}, {
		dataField : "unit",
		headerText : "단위",
		dataType : "string",
		editable : false,
		width : 80
	}, {
		dataField : "version",
		headerText : "버전",
		dataType : "string",
		editable : false,
		width : 80
	}, {
		dataField : "amount",
		headerText : "수량",
		dataType : "string",
		editable : true,
		postfix : "개",
		width : 80,
	}, {
		dataField : "state",
		headerText : "상태",
		dataType : "string",
		editable : false,
		width : 120
	}, {
		dataField : "erpCode",
		headerText : "PLM 임시코드",
		dataType : "string",
		editable : false,
		width : 120
	}, {
		dataField : "erpCode",
		headerText : "ERP CODE",
		dataType : "string",
		editable : false,
		width : 120,
		renderer: { // HTML 템플릿 렌더러 사용
			type: "TemplateRenderer"
		},
		tooltip : {
			show : false
		},
		// dataField 로 정의된 필드 값이 HTML 이라면 labelFunction 으로 처리할 필요 없음.
		labelFunction: function (rowIndex, columnIndex, value, headerText, item, dataField, cItem) { // HTML 템플릿 작성
// 			if (!value) return "";
			var width = (cItem.width - 12); // 좌우 여백 생각하여 12 빼줌.
			var template = '<input type="text" class="AXInput" size="10"';
// 			template += ' onkeydown="if(event.keyCode == 9) event.preventDefault();"' //탭 키를 누르면 브라우저에서 자동으로 다음 input 을 찾는데 이를 방지.
			template += '>';
			return template; // HTML 템플릿 반환..그대도 innerHTML 속성값으로 처리됨
		}		
	}, {
		dataField : "oid",
		headerText : "oid",
		dataType : "string",
		visible : false
	}, ];
	var auiGridProps = {
			headerHeight : 30,
			rowHeight : 30,
			showStateColumn : true,
			showRowNumColumn : false,
			displayTreeOpen : true,
			treeColumnIndex : 1,
			enableDrag : true,
			enableDragByCellDrag : true,
			enableDrop : true,
			selectionMode : "multipleRows",
			enableUndoRedo : true,
			softRemoveRowMode : false,
			fixedColumnCount : 2,
			treeLevelIndent : 20,
			editable : false,
			editableOnFixedCell : false,
			enableSorting : false,
			useContextMenu : true,
			showTooltip : true,
			fillColumnSizeMode : true,
			rowStyleFunction : function(rowIndex, item) {
				if (item.library) {
					return "library";
				}
				return "";
			}
	};
	myGridID = AUIGrid.create("#grid_wrap", columnLayout, auiGridProps);

	AUIGrid.bind(myGridID, "contextMenu", function(event) {
		AUIGrid.setSelectionByIndex(myGridID, event.rowIndex, event.columnIndex);
		var menus = [ {
			label : "최상위 추가",
			callback : contextItemHandler
		}, {
			label : "하위 추가",
			callback : contextItemHandler
		}, {
			label : "기존부품 추가",
			callback : contextItemHandler
		}, {
			label : "_$line"
		}, {
			label : "레벨 올리기 (Shift + Alt + ←)",
			callback : contextItemHandler
		}, {
			label : "레벨 내리기 (Shift + Alt + →)",
			callback : contextItemHandler
		}, {
			label : "위로 (Shift + Alt + ↑)",
			callback : contextItemHandler
		}, {
			label : "아래로 (Shift + Alt + ↓)",
			callback : contextItemHandler
		}, {
			label : "_$line"
		}, {
			label : "작업취소 Undo (Ctrl + z)",
			callback : contextItemHandler
		}, {
			label : "작업취소 Redo (Ctrl + y)",
			callback : contextItemHandler
		}, {
			label : "삭제 (Ctrl + Delete)",
			callback : contextItemHandler
		}, ]
		if (event.dataField == "number") {
			var item = event.item;
			// 			if (item.state == "릴리즈됨") {
			// 				return false;
			// 			}
			return menus;
		}
		return false;
	});

	function contextItemHandler(event) {
		var item = event.item;
		var rowIndex = event.rowIndex;
		switch (event.contextIndex) {
		case 0:
			var root = AUIGrid.getItemByRowIndex(myGridID, 0);
			var url = "/Windchill/platform/part/top?partTypeCd=" + item.partTypeCd + "&rowId=" + root._$uid + "&poid=" + root.oid + "&callBack=_top";
			_popup(url, 1100, 380, "n");
			break;
		case 1:
			var url = "/Windchill/platform/part/append?partTypeCd=" + item.partTypeCd + "&rowId=" + item._$uid + "&poid=" + item.oid + "&callBack=_child";
			_popup(url, 1100, 380, "n");
			break;
		case 2:
			var url = "/Windchill/platform/part/exist?partTypeCd=" + item.partTypeCd + "&rowId=" + item._$uid + "&poid=" + item.oid + "&box=1&callBack=_child";
			_popup(url, "", "", "f");
			break;
		case 4:
			AUIGrid.outdentTreeDepth(myGridID);
			break;
		case 5:
			AUIGrid.indentTreeDepth(myGridID);
			console.log(item);
			var parentItem = AUIGrid.getParentItemByRowId(myGridID, item.uid);
			console.log(parentItem);
			break;
		case 6:
			AUIGrid.moveRowsToUp(myGridID);
			break;
		case 7:
			AUIGrid.moveRowsToDown(myGridID);
			break;
		case 9:
			AUIGrid.undo(myGridID);
			break;
		case 10:
			AUIGrid.redo(myGridID);
			break;
		case 11:
			AUIGrid.removeRow(myGridID, "selectedIndex");
			break;
		}
	};
	
	function loadTree() {
		var params = new Object();
		var oid = $("input[name=eoid]").val();
		AUIGrid.showAjaxLoader(myGridID);
		var url = "/Windchill/platform/ebom/loadTree?oid=" + oid;
		_call(url, params, function(data) {
			AUIGrid.removeAjaxLoader(myGridID);
			AUIGrid.setGridData(myGridID, data.list);
		}, "GET");
	}
	
	$(window).resize(function() {
		AUIGrid.resize("#grid_wrap");
	})
	
	$(function() {
		loadTree();
	})
</script>