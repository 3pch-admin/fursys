<%@page import="platform.util.IBAUtils"%>
<%@page import="platform.util.CommonUtils"%>
<%@ page import="wt.part.WTPart" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String oid = (String) request.getAttribute("oid");
WTPart part = (WTPart) request.getAttribute("part");
%>
<style type="text/css">
.library {
	background-color: #fefbc0;
}

.released {
	background-color: #dbdbfd;
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
	<span>EBOM 등록</span>
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
			<input type="hidden" name="oid" class="AXInput w70p" readonly="readonly">
			<input type="text" name="number" class="AXInput w70p" readonly="readonly" placeholder="클릭하여 편집할 부품을 선택하세요.">
		</td>
		<th>유형</th>
		<td>
			<input type="text" name="partType" class="AXInput w30p" readonly="readonly">
		</td>
	</tr>
</table>

<table class="button-table">
	<tr>
		<td class="right">
			<button type="button" id="createBtn">등록</button>
			<button type="button" id="verifyBtn">수량검증</button>
			<button type="button" id="closeBtn">닫기</button>
		</td>
	</tr>
</table>


<table class="create-table none">
	<colgroup>
		<col width="49%">
		<col width="30">
		<col width="49%">
	</colgroup>
	<tr>
		<td class="center none" valign="top">
			<div id="grid_left" style="height: 840px;"></div>
		</td>
		<td class="none" valign="top">&nbsp;</td>
		<td class="center none" valign="top">
			<div id="grid_right" style="height: 840px;"></div>
		</td>
	</tr>
</table>
<script type="text/javascript">
	var leftGridID;
	var rightGridID;
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
		dataField : "number",
		headerText : "CREO 파일명",
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
	// 		headerTooltip : {
	// 			show : true,
	// 			tooltipHtml : "레벨 내리기 단축키 : Shift + Alt + -><br>레벨 올리기 단축키 : Shift + Alt + <-<br>행 삭제 단축키 : Ctrl + Delete"
	// 		},
	}, {
		dataField : "itemName",
		headerText : "ITEM_NAME",
		dataType : "string",
		width : 150,
		editable : false,
	}, {
		dataField : "partNo",
		headerText : "PART_NO",
		dataType : "string",
		editable : false,
		width : 150
	}, {
		dataField : "partName",
		headerText : "부품명",
		dataType : "string",
		editable : false,
		width : 200
	}, {
		dataField : "state",
		headerText : "상태",
		dataType : "string",
		editable : false,
		width : 120
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
		width : 80,
		editRenderer : {
			type : "InputEditRenderer",
			onlyNumeric : true, // Input 에서 숫자만 가능케 설정
			validator : function(oldValue, newValue, item) {
				var isValid = false;
				if (newValue && newValue.length > 0) {
					isValid = true;
				}
				// 리턴값은 Object 이며 validate 의 값이 true 라면 패스, false 라면 message 를 띄움
				return {
					"validate" : isValid,
					"message" : "수량을 입력하세요."
				};
			}
		},
	}, {
		dataField : "oid",
		headerText : "oid",
		dataType : "string",
		visible : false
	}, {
		dataField : "uid",
		headerText : "uid",
		dataType : "string",
		visible : false
	}, ];

	// 	var footerLayout = [ {
	// 		labelText : "총 수량",
	// 		positionField : "state",
	// 		dataField : "state",
	// 	}, {
	// 		dataField : "amount",
	// 		positionField : "amount",
	// 		postfix : "개",
	// 		operation : "SUM",
	// 		formatString : "###0"
	// 	} ];

	var auiLeftProps = {
		// 		rowIdField : "uid",
		headerHeight : 30,
		rowHeight : 30,
		showRowNumColumn : false,
		displayTreeOpen : true,
		treeColumnIndex : 2,
		// 		showFooter : true,
		enableDrag : true,
		enableDragByCellDrag : true,
		enableDrop : false,
		enableUndoRedo : true,
		dropToOthers : true,
		fixedColumnCount : 3,
		treeLevelIndent : 20,
		enableSorting : false,
		showStateColumn : true,
		softRemoveRowMode : true,
		showTooltip : true,
		rowStyleFunction : function(rowIndex, item) {
			if (item.library) {
				return "library";
			} else if(item.state == "릴리즈됨") {
				return "released";
			}
			return "";
		}
	};

	var auiRightProps = {
		// 		rowIdField : "uid",
		headerHeight : 30,
		rowHeight : 30,
		showStateColumn : true,
		showRowNumColumn : false,
		displayTreeOpen : true,
		treeColumnIndex : 2,
		// 		showFooter : true,
		enableDrag : true,
		enableDragByCellDrag : true,
		enableDrop : true,
		selectionMode : "multipleRows",
		enableUndoRedo : true,
		dropToOthers : true,
		softRemoveRowMode : false,
		fixedColumnCount : 3,
		treeLevelIndent : 20,
		editable : true,
		editableOnFixedCell : true,
		enableSorting : false,
		useContextMenu : true,
		showTooltip : true,
		rowStyleFunction : function(rowIndex, item) {
			if (item.library) {
				return "library";
			}
			return "";
		}
	};

	leftGridID = AUIGrid.create("#grid_left", columnLayout, auiLeftProps);
	rightGridID = AUIGrid.create("#grid_right", columnLayout, auiRightProps);
	// 	AUIGrid.setFooter(leftGridID, footerLayout);
	// 	AUIGrid.setFooter(rightGridID, footerLayout);

	// 위
	AUIGrid.bind(rightGridID, "indent", function(event) {
		// 		if (event.items.length == 0) {
		// 			return false;
		// 		}
		// 		var item = event.items[0];
		// 		var cCd = item.partTypeCd; // 이동할 타입
		// 		var parent = AUIGrid.getItemByRowId(rightGridID, item._$parent);
		// 		if (parent != undefined) {
		// 			var pCd = parent.partTypeCd; // 부모 타입

		// 			// 단품일 경우
		// 			if (cCd == "ITEM") {
		// 				// 자재 아래로 이동 불가
		// 				if (pCd == "MAT") {
		// 					alert("단품은 자재 아래로 이동이 불가능 합니다.");
		// // 					AUIGrid.undo(rightGridID);
		// 					return false;
		// 				}

		// 				if (pCd == "ITEM") {
		// 					alert("단품은 단품 아래로 이동이 불가능 합니다.");
		// // 					AUIGrid.undo(rightGridID);
		// 					return false;
		// 				}
		// 			}
		// 		}
	});

	// 아래
	AUIGrid.bind(rightGridID, "outdent", function(event) {
		// 		var item = event.items[0];
		// 		var cCd = item.partTypeCd; // 이동할 타입
		// 		console.log(event.type + " 이벤트\r\n" + "\r\n적용된 행 개수 : " + event.items.length);
		// 		console.log(item);
		// 		if (event.items.length == 0) {
		// 			return false;
		// 		}

		// 		if (item._$depth == 1) {
		// 			alert("최하위 레벨로 이동은 불가능 합니다.");
		// // 			AUIGrid.undo(rightGridID);
		// 			return false;
		// 		}
		// 		console.log("A");
		// 		var parent = AUIGrid.getItemByRowId(rightGridID, item._$parent);
		// 		if (parent != undefined) {
		// 			var pCd = parent.partTypeCd; // 부모 타입

		// 			// 		// 자재일경우..
		// 			if (cCd == "MAT") {
		// 				// 세트 아래로 옴기기 불가능
		// 				if (pCd == "SET") {
		// // 					alert("자재는 세트 아래에 존재가 불가능 합니다.");
		// // 					AUIGrid.undo(rightGridID);
		// 					return;

		// 				}
		// 				console.log("C");
		// 			}
		// 			// 단품일 경우
		// 			if (cCd == "ITEM") {
		// 				// 자재 아래로 이동 불가
		// 				if (pCd == "MAT") {
		// 					alert("단품은 자재 아래로 이동이 불가능 합니다.");
		// // 					AUIGrid.undo(rightGridID);
		// 					return false;
		// 				}

		// 				if (pCd == "ITEM") {
		// 					alert("단품은 단품 아래로 이동이 불가능 합니다.");
		// // 					AUIGrid.undo(rightGridID);
		// 					return false;
		// 				}
		// 			}
		// 		}
	});

	// drag prevent
	AUIGrid.bind(leftGridID, "dragBegin", function(event) {
		var rowIndex = event.rowIndex;
		if (rowIndex == 0) {
			alert("최상위는 편집 불가능합니다.");
			return false;
		}
		return true;
	})

	AUIGrid.bind(rightGridID, "cellEditBegin", function(event) {
		var rowIndex = event.rowIndex;
		if (rowIndex == 0) {
			return false;
		}
		return true;
	});

	// 	AUIGrid.bind(rightGridID, "cellEditEnd", function(event) {
	// 		alert("C");
	// 	});

	// 	AUIGrid.bind(rightGridID, "columnStateChange", function(event) {
	// 	      var str = "event type : " + event.type;
	// 	      str += ", dataField : " + event.dataField;
	// 	      str += ", prop : " + event.property;
	// 	      str += ", old : " + event.old + ", current : " + event.current;
	// 	      alert(str);
	// 	});

	AUIGrid.bind(rightGridID, "contextMenu", function(event) {
		AUIGrid.setSelectionByIndex(rightGridID, event.rowIndex, event.columnIndex);
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
			label : "재공 추가",
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
			var root = AUIGrid.getItemByRowIndex(rightGridID, 0);
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
		case 3:
			break;
		case 5:
			AUIGrid.outdentTreeDepth(rightGridID);
			break;
		case 6:
			AUIGrid.indentTreeDepth(rightGridID);
			console.log(item);
			var parentItem = AUIGrid.getParentItemByRowId(rightGridID, item.uid);
			console.log(parentItem);
			break;
		case 7:
			AUIGrid.moveRowsToUp(rightGridID);
			break;
		case 8:
			AUIGrid.moveRowsToDown(rightGridID);
			break;
		case 10:
			AUIGrid.undo(rightGridID);
			break;
		case 11:
			AUIGrid.redo(rightGridID);
			break;
		case 12:
			AUIGrid.removeRow(rightGridID, "selectedIndex");
			break;
		}
	};

	AUIGrid.bind(leftGridID, "dropEnd", function(event) {
		console.log(event);
		// 		AUIGrid.removeRow(leftGridID, event.fromRowIndex);
	})

	// article zone. not drag
	AUIGrid.bind(leftGridID, "dropEndBefore", function(event) {
		event.isMoveMode = false;
		var item = event.items[0];
		var number = item.number;
		if (number.indexOf("ARTICLE") > -1 || number.indexOf("ZONE") > -1) {
			alert("ARTICLE 및 ZONE 은 EBOM 편집의 대상이 아닙니다.");
			return false;
		}

		var validNumber = validate(item);
		if (!validNumber) {
			alert("ARTICLE 및 ZONE 이 포함 되어있습니다.");
			return false;
		}

		var pidToDrop = event.pidToDrop;
		console.log(item.uid);
		var notHave = AUIGrid.isUniqueValue(pidToDrop, "uid", item.uid); // 이미 존재하는지 검사
		if (!notHave) {
			if (confirm("지금 드랍되는 행은 이미 이전에 드랍된 행입니다. 또 드랍하시겠습니까?")) {
				return true;
			} else {
				return false; // 기본 행위 안함.
			}
		}
		return true;
	});

	// 	AUIGrid.bind(leftGridID, "dropEndBefore", function(event) {
	// 		if (event.items.length == 0)
	// 			return false;

	// 		// 이벤트의 isMoveMode 속성을 false 설정하면 행 복사를 합니다.
	// 		event.isMoveMode = false;

	// 		console.log(event);

	// 		// 드랍되는 그리드의 PID
	// 		var pidToDrop = event.pidToDrop;
	// 		var item = event.items[0]; // 드래깅 되고 있는 첫번째 행
	// 		var notHave = AUIGrid.isUniqueValue(pidToDrop, "id", item.rowId); // 이미 존재하는지 검사
	// 		if (!notHave) {
	// 			if (confirm("지금 드랍되는 행은 이미 이전에 드랍된 행입니다. 또 드랍하시겠습니까?")) {
	// 				return true;
	// 			} else {
	// 				return false; // 기본 행위 안함.
	// 			}
	// 		}
	// 		return true; // 만약 return false 를 하게 되면 드랍 행위를 하지 않습니다.(즉, 기본 행위를 안함)
	// 	});

	$(window).resize(function() {
		AUIGrid.resize("#grid_left");
		AUIGrid.resize("#grid_right");
	})

	function validate(item) {
		var children = item.children;
		for (var i = 0; i < children.length; i++) {
			var dd = children[i];
			if (dd.number.indexOf("ARTICLE") > -1 || dd.number.indexOf("ZONE") > -1) {
				return false;
			}
			validate(dd);
		}
		return true;
	}

	function _child(node, rowId) {
		AUIGrid.addTreeRow(rightGridID, node, rowId, "last");
	}

	function _top(node, rowId) {
		AUIGrid.addTreeRow(rightGridID, node, rowId, "first");
	}

	
	$(function() {

		$("#verifyBtn").click(function() {
			var url = _url("/ebom/verify", $("input[name=oid]").val());
			_popup(url, 1400, 800, "n");
		})

		$("input[name=number]").click(function() {
			var url = "/Windchill/platform/part/popup?box=1";
			_popup(url, "", "", "f");
		})

		$("#closeBtn").click(function() {
			self.close();
		})

		$("#createBtn").click(function() {

			var d = AUIGrid.getGridData(rightGridID);
			for (var i = 0; i < d.length; i++) {
				var item = d[i];
				var depth = item._$depth;
				console.log(item);
				// 				if(depth == 1) {
				// 					alert(item.name + " 태스크가 템플릿의 레벨과 일치합니다.\n태스크는 템플릿과 같은 레벨에 존재 할 수 없습니다.");
				// 					return false;
				// 				}
			}

			// 			if (!confirm("저장 후 검증 페이지로 이동 되어집니다.\n기존 저장된 EBOM이 있을 경우는 다시 저장을 하지 않습니다.\n진행 하시겠습니까?")) {
			if (!confirm("저장 후 검증 페이지로 이동 되어집니다.")) {
				return false;
			}

			// 			var json = btoa(unescape(encodeURIComponent(JSON.stringify(list))));
			var json = btoa(unescape(encodeURIComponent(JSON.stringify(AUIGrid.getTreeGridData(rightGridID)))));
			var url = "/Windchill/platform/ebom/create";
			var params = new Object();
			params.json = json;
			params.oid = $("input[name=oid]").val();
			_call(url, params, function(data) {
				var url = _url("/ebom/verify", data.oid);
				_popup(url, 1600, 800, "n");
			}, "POST");
		})
	})

	function part(info) {
		// 		$("#input[name=number]").add("input[name=name]").off();
		$("input[name=oid]").val(info[0].oid);
		$("input[name=number]").val(info[0].number);
		// 		$("input[name=name]").val(data[0].name);
		$("input[name=partType]").val(info[0].partType);
		$("input[name=color]").val(info[0].color);
		$("input[name=search]").attr("readonly", false);
		loadLeftTree(info[0].oid);
		loadRightTree(info[0].oid);
	}

	function loadLeftTree(oid) {
		var params = new Object();
		AUIGrid.showAjaxLoader(leftGridID);
		var url = _url("/ebom/left", oid);
		_call(url, params, function(data) {
			console.log(data.list);
			AUIGrid.removeAjaxLoader(leftGridID);
			AUIGrid.setGridData(leftGridID, data.list);
		}, "GET");
	}

	function loadRightTree(oid) {
		var params = new Object();
		AUIGrid.showAjaxLoader(rightGridID);
		params = JSON.stringify(params);
		var url = "/Windchill/platform/ebom/right?oid=" + oid;
		$.ajax({
			type : "GET",
			url : url,
			dataType : "JSON",
			crossDomain : true,
			data : params,
			async : true, //동기처리를 해야만 펑션리턴이 가능함
			contentType : "application/json; charset=UTF-8",
			success : function(data) {
				AUIGrid.removeAjaxLoader(rightGridID);
				AUIGrid.setGridData(rightGridID, data.list);
			},
		});
	}

	function closeAndLoad() {
		opener.load();
		self.close();
	}
</script>