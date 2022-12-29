<%@page import="platform.part.service.PartHelper"%>
<%@page import="platform.ebom.entity.EBOM"%>
<%@page import="platform.util.IBAUtils"%>
<%@page import="platform.util.CommonUtils"%>
<%@page import="wt.part.WTPart"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%

%>
<style type="text/css">
.library {
	background-color: #fefbc0;
}
/* 엑스트라 체크박스 사용자 선택 못하는 표시 스타일 */
.disable-check-style {
	color: #d3825c;
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
	<span>PART LIST 등록</span>
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
		<td>DT_H37_800_TOTAL.ASM</td>
		<th>유형</th>
		<td>세트</td>
	</tr>
</table>

<table class="button-table">
	<tr>
		<td class="left">
			<!-- 			<img src="/Windchill/jsp/images/undo.gif" style="height: 22px; width: 22px;"> -->
		</td>
		<td class="right">
			<button type="button" id="colorBtn">단품 색상</button>
			<button type="button" id="seprateBtn">개별 등록</button>
			<button type="button" id="batchBtn">일괄 등록</button>
			<button type="button" id="confirmBtn">원부자재추가</button>
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
		dataField : "",
		headerText : "접수자",
		dataType : "string",
		width : 100,
		editable : false,
		renderer : { // HTML 템플릿 렌더러 사용
			type : "TemplateRenderer"
		},
		tooltip : {
			show : false
		},
		// dataField 로 정의된 필드 값이 HTML 이라면 labelFunction 으로 처리할 필요 없음.
		labelFunction : function(rowIndex, columnIndex, value, headerText, item, dataField, cItem) { // HTML 템플릿 작성
			// 			if (!value) return "";
			if (item.partType != "단품") {
				return "";
			}
			var width = (cItem.width - 12); // 좌우 여백 생각하여 12 빼줌.
			var template = '<input onclick="manager();" type="text" readonly="readonly" class="AXInput" value="' + item.manager + '" size="10"';
			// 			template += ' onkeydown="if(event.keyCode == 9) event.preventDefault();"' //탭 키를 누르면 브라우저에서 자동으로 다음 input 을 찾는데 이를 방지.
			template += '>';
			return template; // HTML 템플릿 반환..그대도 innerHTML 속성값으로 처리됨
		}
	}, {
		dataField : "erpCode",
		headerText : "품목코드(ERPCODE)",
		dataType : "string",
		style : "left indent10",
		editable : false,
		width : 350,
	}, {
		dataField : "number",
		headerText : "PLM 임시코드",
		dataType : "string",
		width : 240,
		editable : false,
	}, {
		dataField : "partName",
		headerText : "품목명(PART_NAME)",
		dataType : "string",
		editable : false,
		width : 250
	}, {
		dataField : "amount",
		headerText : "수량",
		dataType : "string",
		editable : false,
		postfix : "개",
		width : 100
	}, {
		dataField : "unit",
		headerText : "단위",
		dataType : "string",
		editable : false,
		width : 100
	}, {
		dataField : "version",
		headerText : "버전",
		dataType : "string",
		editable : false,
		width : 80
	}, {
		dataField : "color",
		headerText : "적용색상",
		dataType : "string",
		editable : true,
		postfix : "개",
		width : 80,
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
		rowCheckToRadio : true,
		showRowCheckColumn : true,
		showRowNumColumn : false,
		displayTreeOpen : true,
		treeColumnIndex : 3,
		enableDrag : true,
		enableDragByCellDrag : true,
		enableDrop : true,
		selectionMode : "multipleRows",
		enableUndoRedo : true,
		softRemoveRowMode : false,
		// 		fixedColumnCount : 2,
		treeLevelIndent : 20,
		editable : false,
		editableOnFixedCell : false,
		enableSorting : false,
		useContextMenu : true,
		showTooltip : true,
		fillColumnSizeMode : true,
		independentAllCheckBox : true,
		rowStyleFunction : function(rowIndex, item) {
			if (item.library) {
				return "library";
			}
			return "";
		},
		rowCheckVisibleFunction : function(rowIndex, isChecked, item) {
			if (item.partType != "단품") { // 이름이 Anna 인 경우 사용자 체크 못하게 함.
				return false;
			}
			return true;
		},

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

	// 	function loadTree() {
	// 		var params = new Object();
	// 		var oid = $("input[name=eoid]").val();
	// 		AUIGrid.showAjaxLoader(myGridID);
	// 		var url = "/Windchill/platform/ebom/loadTree?oid=" + oid;
	// 		_call(url, params, function(data) {
	// 			AUIGrid.removeAjaxLoader(myGridID);
	// 			AUIGrid.setGridData(myGridID, data.list);
	// 		}, "GET");
	// 	}

	function loadTree() {
		requestData("/Windchill/jsp/partlist/mockup/partlist-create.json");
	}

	function requestData(url) {

		// ajax 요청 전 그리드에 로더 표시
		AUIGrid.showAjaxLoader(myGridID);

		// ajax (XMLHttpRequest) 로 그리드 데이터 요청
		ajax({
			url : url,
			onSuccess : function(data) {

				//console.log(data);

				// 그리드에 데이터 세팅
				// data 는 JSON 을 파싱한 Array-Object 입니다.
				AUIGrid.setGridData(myGridID, data);

				// 로더 제거
				AUIGrid.removeAjaxLoader(myGridID);
			},
			onError : function(status, e) {
				alert("데이터 요청에 실패하였습니다.\r\n status : " + status + "\r\nWAS 를 IIS 로 사용하는 경우 json 확장자가 web.config 의 handler 에 등록되었는지 확인하십시오.");
				// 로더 제거
				AUIGrid.removeAjaxLoader(myGridID);
			}
		});
	};
	AUIGrid.bind(myGridID, "cellClick", function(event) {
		var rowItem = event.item;
		AUIGrid.setCheckedRowsByIds(myGridID, rowItem.oid);
	});

	function manager() {
		var url = "/Windchill/platform/user/popup?target=1";
		_popup(url, 1400, 650, "n");
	}

	$(window).resize(function() {
		AUIGrid.resize("#grid_wrap");
	})

	$(function() {
		loadTree();

		$("#closeBtn").click(function() {
			self.close();
		})

		$("#colorBtn").click(function() {
			var url = _url("/partlist/color");
			_popup(url, 1300, 500, "n");
		})

		$("#seprateBtn").click(function() {
			var items = AUIGrid.getCheckedRowItems(myGridID);
			if (items.length == 0) {
				alert("개별등록할 단품을 선택하세요.");
				return false;
			}
			var url = _url("/partlist/seprate");
			_popup(url, 1300, 500, "n");
		})
		$("#batchBtn").click(function() {
			var url = _url("/partlist/batch");
			_popup(url, 1300, 500, "n");
		})
		$("#confirmBtn").click(function() {
			var url = _url("/partlist/confirm");
			_popup(url, 1600, 600, "n");
		})
	})
</script>