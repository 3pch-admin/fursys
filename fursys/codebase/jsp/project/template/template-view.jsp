<%@page import="platform.util.CommonUtils"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>
<%@page import="platform.project.template.entity.TemplateDTO"%>
<%@page import="platform.doc.entity.DocumentDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String oid = (String) request.getAttribute("oid");
ArrayList<Map<String, Object>> list = (ArrayList<Map<String, Object>>) request.getAttribute("list");
JSONArray arr = JSONArray.fromObject(list);
boolean isAdmin = CommonUtils.isAdmin();
%>
<style type="text/css">
.aui-grid-tree-leaf-icon {
	display: inline-block;
	width: 16px;
	height: 16px;
	background: url(/Windchill/jsp/asset/AUIGrid/images/flat_circle.png) no-repeat;
	background-size: 16px;
	vertical-align: bottom;
	margin: 0 2px 0 4px;
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
</style>
<div class="header-title">
	<img src="/Windchill/jsp/images/home.png" class="home">
	<span>HOME</span>
	>
	<span>템플릿 관리</span>
	>
	<span>템플릿 정보</span>
</div>

<table class="button-table">
	<tr>
		<td class="left">
			<button type="button" id="saveBtn">저장</button>
			<button type="button" id="addBtn">추가</button>
			<button type="button" id="deleteRow">삭제</button>
			<button type="button" id="upRowBtn">위로</button>
			<button type="button" id="downRowBtn">아래로</button>
			<button type="button" id="indentRowBtn">수준올리기</button>
			<button type="button" id="outdentRowBtn">수준내리기</button>
			<button type="button" id="resetBtn">일정초기화</button>
		</td>
	</tr>
</table>
<table class="">
	<colgroup>
		<col width="50%">
		<col width="30">
		<col width="49%">
	</colgroup>
	<tr>
		<td class="center none" valign="top">
			<div id="grid_wrap" style="height: 845px;"></div>
		</td>
		<td class="none" valign="top">&nbsp;</td>
		<td class="center none" valign="top">
			<iframe id="right" style="height: 820px;" src="/Windchill/platform/template/templateView?oid=<%=oid%>"></iframe>
		</td>
	</tr>
</table>


<table class="button-table">
	<tr>
		<td class="right">
			<!-- 			<button type="button" id="deleteBtn">삭제</button> -->
			<button type="button" id="closeBtn">닫기</button>
		</td>
	</tr>
</table>
<script type="text/javascript">
	var roles =
<%=arr%>
	;
	var columnLayout = [ {
		dataField : "name",
		headerText : "태스크 명",
		dataType : "string",
		style : "left indent10",
		width : 280,
		editable : true,
		editRenderer : {
			type : "InputEditRenderer",
			validator : function(oldValue, newValue, item) {
				var isValid = false;
				if (newValue && newValue.length > 0) {
					isValid = true;
				}
				// 리턴값은 Object 이며 validate 의 값이 true 라면 패스, false 라면 message 를 띄움
				return {
					"validate" : isValid,
					"message" : "태스크 명을 입력하세요."
				};
			}
		},
		headerTooltip: {
			show: true,
			tooltipHtml: "템플릿의 WBS 구조를 먼저 저장 후 기간을 수정하세요."
		},		
	}, {
		dataField : "duration",
		headerText : "기간",
		dataType : "numeric",
		width : 80,
		postfix : "일",
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
					"message" : "기간을 입력하세요."
				};
			}
		},
		headerTooltip: {
			show: true,
			tooltipHtml: "최하위 레벨의 태스크 기간만 수정 가능하며, 전체 기간은 자동 계산 되어집니다."
		},	
	}, 
	<%
		if(isAdmin) {
	%>
	{
		dataField : "planStartDate",
		headerText : "계획시작일",
		dataType : "string",
		width : 80,
		editable : false
	}, {
		dataField : "planEndDate",
		headerText : "계획종료일",
		dataType : "string",
		width : 80,
		editable : false
	}, 
	<%
		}	
	%>
	{
		dataField : "description",
		headerText : "설명",
		dataType : "string",
		width : 350,
		style : "left indent10"
	}, {
		dataField : "preTask",
		headerText : "선행태스크",
		dataType : "string",
		width : 200,
		editable : false
	}, {
		dataField : "roles",
		headerText : "역할",
		dataType : "string",
		width : 200,
		renderer : {
			type : "IconRenderer",
			iconWidth : 16, // icon 사이즈, 지정하지 않으면 rowHeight에 맞게 기본값 적용됨
			iconHeight : 16,
			iconPosition : "aisleRight",
			iconTableRef : { // icon 값 참조할 테이블 레퍼런스
				"default" : "/Windchill/jsp/asset/AUIGrid/images/list-icon.png" // default
			},
			onClick : function(event) {
				// 아이콘을 클릭하면 수정으로 진입함.
				AUIGrid.openInputer(event.pid);
			}
		},
		editRenderer : {
			type : "DropDownListRenderer",
			showEditorBtn : false,
			showEditorBtnOver : false, // 마우스 오버 시 에디터버턴 보이기
			multipleMode : true, // 다중 선택 모드(기본값 : false)
			showCheckAll : true, // 다중 선택 모드에서 전체 체크 선택/해제 표시(기본값:false);
			delimiter : ",", // 다중 선택시 구분자 (기본값 : 컴마(,))
			list : roles,
			keyField : "code", // key 에 해당되는 필드명
			valueField : "name" // value 에 해당되는 필드명			
		},
		labelFunction : function(rowIndex, columnIndex, value, headerText, item) {
			var retStr = "";
			if (value != undefined) {
				var valueArr = value.split(","); // 구분자 기본값은 ", " 임.
				var tempValueArr = [];

				for (var i = 0, len = roles.length; i < len; i++) {
					if (valueArr.indexOf(roles[i]["code"]) >= 0) {
						tempValueArr.push(roles[i]["name"]);
					}
				}
				return tempValueArr.sort().join(","); // 정렬시켜서 보여주기.
			}
		},
	}, {
		dataField : "oid",
		headerText : "oid",
		dataType : "string",
		visible : false
	}, ];

	var auiLeftProps = {
		rowIdField : "oid",
		headerHeight : 30,
		rowHeight : 30,
		showRowNumColumn : false,
		displayTreeOpen : true,
		treeColumnIndex : 0,
		enableDrag : true,
		enableDragByCellDrag : true,
		enableDrop : true,
		enableUndoRedo : true,
		editable : true,
		softRemoveRowMode : false,
		selectionMode : "multipleCells",
// 		useContextMenu : true,
		fixedColumnCount : 1,
		editableOnFixedCell : true
	};

	myGridID = AUIGrid.create("#grid_wrap", columnLayout, auiLeftProps);
	var oid = "<%=oid%>";
	function load(oid) {
		var params = new Object();
		AUIGrid.showAjaxLoader(myGridID);
		var url = _url("/template/load", oid);
		_call(url, params, function(data) {
			AUIGrid.removeAjaxLoader(myGridID);
			AUIGrid.setGridData(myGridID, data.list);
		}, "GET");
	}

	load(oid);

	AUIGrid.bind(myGridID, "addRowFinish", function(event) {
		var items = event.items;
		for (var i = 0; i < items.length; i++) {
			var item = items[i];
			item.duration = 1;
			item.isNew = true;
		}
	});

	AUIGrid.bind(myGridID, "cellClick", function(event) {
		var oid = event.item.oid;
		var panel = document.getElementById("right");
		var dataField = event.dataField;
		if (dataField == "name") {
			if (oid.indexOf("Template") > -1) {
				panel.src = "/Windchill/platform/template/templateView?oid=" + oid;
			} else if (oid.indexOf("Task") > -1) {
				panel.src = "/Windchill/platform/template/templateTaskView?oid=" + oid;
			}
		}
	});

	AUIGrid.bind(myGridID, "cellEditBegin", function(event) {
		var item = event.item;
		var oid = item.oid;
		var dataField = event.dataField;
		if (oid.indexOf("Template") > -1) {
			if(dataField != "name" && dataField != "description") {
				return false;
			}
		}
		
		if(dataField == "duration") {
			if(item.isNew) {
				alert("저장 후 일정을 수정하세요.");
				return false;
			}
			
			if(item.children.length != 0) {
				return false;
			}
		}
		
		return true;
	});

	$(function() {

		$("#saveBtn").click(function() {
			var d = AUIGrid.getGridData(myGridID);
			for (var i = 0; i < d.length; i++) {
				var item = d[i];
				var toid = item.oid;
				if(toid.indexOf("Template") > -1) {
					continue;
				}
				
				var name = item.name;
				if (name == undefined || name == "") {
					alert("태스크 명이 입력안된 태스크가 존재합니다.");
					return false;
				}
				
				var depth = item._$depth;
				if(depth == 1) {
					alert(item.name + " 태스크가 템플릿의 레벨과 일치합니다.\n태스크는 템플릿과 같은 레벨에 존재 할 수 없습니다.");
					return false;
				}
				

			}
			if (!confirm("저장 하시겠습니까?")) {
				return false;
			}

			var _deleted = AUIGrid.getRemovedItems(myGridID);
// 			console.log(JSON.stringify(AUIGrid.getTreeGridData(myGridID)));
			// 			var json = btoa(unescape(encodeURIComponent(JSON.stringify(list))));
			var json = btoa(unescape(encodeURIComponent(JSON.stringify(AUIGrid.getTreeGridData(myGridID)))));
			var url = "/Windchill/platform/template/saveTree";
			var params = new Object();
			params.json = json;
			params._deleted = _deleted;
			_call(url, params, function(data) {
				load(oid);
			}, "POST");
		})

		$("#outdentRowBtn").click(function() {
			AUIGrid.outdentTreeDepth(myGridID);
		})

		$("#indentRowBtn").click(function() {
			AUIGrid.indentTreeDepth(myGridID);
		})

		$("#upRowBtn").click(function() {
			var selectedItems = AUIGrid.getSelectedItems(myGridID);
			if (selectedItems.length <= 0)
				return;
			AUIGrid.moveRowsToUp(myGridID);

		})

		$("#downRowBtn").click(function() {
			var selectedItems = AUIGrid.getSelectedItems(myGridID);
			if (selectedItems.length <= 0)
				return;
			AUIGrid.moveRowsToDown(myGridID);
		})

		$("#addBtn").click(function() {
			var selectedItems = AUIGrid.getSelectedItems(myGridID);
			if (selectedItems.length <= 0)
				return;

			var selItem = selectedItems[0].item;
			var parentRowId = null;
			var item = new Object();
			item.duration = 1;
			item.isNew = true;
			// 선택행 아래..
			AUIGrid.addTreeRow(myGridID, item, parentRowId, "selectionDown");
		})

		$("#deleteRow").click(function() {
			var selectedItems = AUIGrid.getSelectedItems(myGridID);
			if (selectedItems.length <= 0)
				return;
			var oid = selectedItems[0].item.oid;
			if (oid.indexOf("Template") > -1) {
				alert("템플릿은 삭제가 불가능합니다.");
				return false;
			}
			AUIGrid.removeRow(myGridID, "selectedIndex");
		})

		$("#closeBtn").click(function() {
			self.close();
		})

		$("#resetBtn").click(function() {
			if(!confirm("WBS 구조 및 일정이 모두 초기화 되어집니다.\n진행하시겠습니까?")) {
				return false;
			}
			
			var url = _url("/template/reset");
			var params = new Object();
			params.oid = "<%=oid%>";
			_call(url, params, function(data) {
				document.location.reload();
			}, "POSt");
		})
		
		
	})

	$(window).resize(function() {
		AUIGrid.resize("#grid_wrap");
	})
</script>