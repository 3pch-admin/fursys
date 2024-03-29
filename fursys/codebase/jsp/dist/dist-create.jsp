<%@page import="java.util.Map"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="platform.raonk.entity.Raonk"%>
<%@page import="platform.util.CommonUtils"%>
<%@page import="platform.code.entity.BaseCode"%>
<%@page import="java.util.ArrayList"%>
<%@page import="platform.doc.service.DocumentHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
// ArrayList<Map<String, Object>> list = (ArrayList<Map<String, Object>>) request.getAttribute("list");
// JSONArray arr = JSONArray.fromObject(list);
%>
<!-- hidden value -->
<input type="hidden" name="description" id="description">
<div class="header-title">
	<img src="/Windchill/jsp/images/home.png" class="home">
	<span>HOME</span>
	>
	<span>배포 관리</span>
	>
	<span>배포 등록</span>
</div>

<table class="create-table" style="margin-bottom: 5px;">
	<colgroup>
		<col width="130">
		<col width="*">
		<col width="130">
		<col width="*">
	</colgroup>
	<tr>
		<th>배포명<font color="red"><b>*</b></font></th>
		<td>
			<input type="text" class="AXInput w60p" name="distName" id="distName">
		</td>
		<th>다운로드 기간<font color="red"><b>*</b></font>
		</th>
		<td>
			<select id="duration" name="duration" class="AXSelect w100px">
				<option value="">선택</option>
				<option value="1">1주</option>
				<option value="2" selected="selected">2주</option>
				<option value="3">3주</option>
				<option value="4">4주</option>
				<option value="5">5주</option>
				<option value="6">6주</option>
				<option value="7">7주</option>
				<option value="8">8주</option>
			</select>
		</td>
	</tr>
	<tr>
		<th>내용</th>
		<td colspan="3">
			<div class="raonkEditor">
				<script type="text/javascript">
					var kEditorParam = {
						Id : "editor",
						Width : "100%",
						Height : "200px",
						FocusInitObjId : "name",
						Runtimes : 'html5' // agent, html5
					};
					new RAONKEditor(kEditorParam);
				</script>
			</div>
		</td>
	</tr>
	<!-- 
	<tr>
		<th>첨부파일</th>
		<td colspan="3">
			<div class="AXUpload5" id="secondary_layer"></div>
			<div class="AXUpload5QueueBox_list" id="uploadQueueBox"></div>
			<script type="text/javascript">
				upload.init("", "s");
			</script>
		</td>
	</tr>
	 -->
	<tr style="height: 300px;">
		<th>배포처</th>
		<td colspan="3"    >
			<button type="button" id="dist_addBtn">사용자로 추가</button>
			<button type="button" id="dist_addBtn2">배포처로 추가</button>
			<button type="button" id="dist_deleteBtn">삭제</button>
			<div id="dist_grid_wrap" style="height: 250px; padding-top: 5px;"></div>
		</td>
	</tr>
	<tr style="height: 420px;">
		<th>배포 도면</th>
		<td colspan="3">
			<!-- <button type="button" id="addBtn">추가</button> -->
			<button type="button" id="ecn_addBtn">ECN 추가</button>
			<button type="button" id="part_addBtn">세트 추가</button>
			<button type="button" id="part_addBtn2">단품 추가</button>
			<button type="button" id="part_addBtn3">자재 추가</button>
			<button type="button" id="deleteBttn">삭제</button>
			<div id="grid_wrap" style="height: 370px; padding-top: 5px;"></div>
		</td>
	</tr>
	
</table>



<table class="button-table">
	<tr>
		<td class="right">
			<button type="button" id="saveBtn">등록</button>
			<button type="button" id="closeBtn">닫기</button>
		</td>
	</tr>
</table>

<script type="text/javascript">

	var dist_GridID;
	
	var dist_columnLayout = [ {
		dataField : "name",
		headerText : "배포처",
		dataType : "string",
		style : "left indent10"
	}, {
		dataField : "type",
		headerText : "배포처 구분",
		dataType : "string",
		width : 100
// 	}, {
// 		dataField : "userId",
// 		headerText : "사용자 아이디",
// 		dataType : "string",
// 		width : 120
	}, {
		dataField : "userName",
		headerText : "사용자명",
		dataType : "string",
		width : 120
	}, {
		dataField : "email",
		headerText : "이메일",
		dataType : "string",
		width : 200
	}, {
		dataField : "distributorUser_oid",
		headerText : "distributorUser_oid",
		dataType : "string",
		visible : false
	}, ];
	var dist_auiGridProps = {
			rowIdField : "distributorUser_oid",
			headerHeight : 30,
			rowHeight : 30,
			fillColumnSizeMode : true,
			// 						rowCheckToRadio : false,
			showRowCheckColumn : true,
			showRowNumColumn : false
	};
	
	
	dist_GridID = AUIGrid.create("#dist_grid_wrap", dist_columnLayout, dist_auiGridProps);

	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	var myGridID;
	
	var columnLayout = [ {
		// 		dataField : "s",
		headerText : "",
		dataType : "string",
		width : 40,
		renderer : {
			type : "IconRenderer",
			iconWidth : 16,
			iconHeight : 16,
			iconFunction : function(rowIndex, columnIndex, value, item) {
				return item.s;
			}
		},
		editable : false
	}, {
		dataField : "thumb",
		headerText : "",
		dataType : "string",
		width : 40,
		renderer : {
			type : "IconRenderer",
			iconWidth : 30,
			iconHeight : 22,
			iconFunction : function(rowIndex, columnIndex, value, item) {
				return item.t;
			}
		},
		editable : false,
	}, {
		dataField : "number",
		headerText : "부품번호",
		dataType : "string",
		style : "left indent10",
		editable : false,
		cellMerge : true
	}, {
		dataField : "name",
		headerText : "부품명칭",
		dataType : "string",
		width : 350,
		style : "left indent10",
		editable : false,
		cellMerge : true
	}, {
		dataField : "version",
		headerText : "버전",
		dataType : "string",
		width : 60,
		editable : false,
	// 		cellMerge : true,
	// 		mergeRef : "number",
	}, {
		dataField : "pdf",
		headerText : "PDF",
		dataType : "string",
		width : 60,
		editable : true,
		renderer : {
			type : "CheckBoxEditRenderer",
			showLabel : false,
			editable : true, // 체크박스 편집 활성화 여부(기본값 : false)
			checkValue : "true", // true, false 인 경우가 기본
			unCheckValue : "false",
		// 			disabledFunction : function(rowIndex, columnIndex, value, isChecked, item, dataField) {
		// 				// 행 아이템의 name 이 Anna 라면 체크박스 비활성화(disabled) 처리
		// 				if (item.docType == "CADDRAWING") {
		// 					return false;
		// 				}
		// 				return true;
		// 			}
		}
	}, {
		dataField : "step",
		headerText : "STEP",
		dataType : "string",
		width : 60,
		renderer : {
			type : "CheckBoxEditRenderer",
			showLabel : false,
			editable : true, // 체크박스 편집 활성화 여부(기본값 : false)
			checkValue : "true", // true, false 인 경우가 기본
			unCheckValue : "false",
		// 			disabledFunction : function(rowIndex, columnIndex, value, isChecked, item, dataField) {
		// 				// 행 아이템의 name 이 Anna 라면 체크박스 비활성화(disabled) 처리
		// 				if (item.docType != "CADDRAWING") {
		// 					return false;
		// 				}
		// 				return true;
		// 			}
		}
	}, {
		dataField : "dwg",
		headerText : "DWG",
		dataType : "string",
		width : 60,
		renderer : {
			type : "CheckBoxEditRenderer",
			showLabel : false,
			editable : true, // 체크박스 편집 활성화 여부(기본값 : false)
			checkValue : "true", // true, false 인 경우가 기본
			unCheckValue : "false",
		// 			disabledFunction : function(rowIndex, columnIndex, value, isChecked, item, dataField) {
		// 				// 행 아이템의 name 이 Anna 라면 체크박스 비활성화(disabled) 처리
		// 				if (item.docType == "CADDRAWING") {
		// 					return false;
		// 				}
		// 				return true;
		// 			}
		}
	}, {
		dataField : "uoid",
		headerText : "uoid",
		dataType : "string",
		visible : false
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
		rowCheckToRadio : true,
		rowNumHeaderText : "번호",
		softRemoveRowMode : false,
		showAutoNoDataMessage : false,
		enableCellMerge : true,
		editable : true,
		cellMergePolicy : "withNull",
	};
	
	
	myGridID = AUIGrid.create("#grid_wrap", columnLayout, auiGridProps);

	AUIGrid.bind(myGridID, "cellEditEnd", function(event) {
		var dataField = event.dataField;
		var value = event.value;
		var item = event.item;

		if (dataField == "distributor") {
			if (value == "") {
				return false;
			}
			var params = new Object();
			params.oid = value;
			var url = _url("/distributor/info");
			_call(url, params, function(data) {
				var userName = data.userName;
				var type = data.type;
				var email = data.email;
				item.type = type;
				item.user = userName + " / " + email;
				AUIGrid.updateRow(myGridID, item, event.rowIndex);
			}, "POST");
		}
	});

	AUIGrid.bind(myGridID, "cellClick", function(event) {
		if (event.dataField == "user") {
			var rowItem = event.item;
			var url = _url("/distributor/popupUser", rowItem.oid);
			_popup(url, 900, 600, "n");
		}

		if (event.dataField == "number" || event.dataField == "name") {
			var rowItem = event.item;
			var url = _url("/dist/detail", rowItem.oid);
			_popup(url, 1200, 600, "n");
		}

		var rowItem = event.item;
		var rowIdFeild, rowId;
		rowIdField = AUIGrid.getProp(event.pid, "rowIdField");
		rowId = rowItem[rowIdField];
		if (AUIGrid.isCheckedRowById(event.pid, rowId)) {
			AUIGrid.addUncheckedRowsByIds(event.pid, rowId);
		} else {
			AUIGrid.addCheckedRowsByIds(event.pid, rowId);
		}
	})

	$(function() {

		$("#closeBtn").click(function() {
			self.close();
		})
		
		$("#dist_addBtn").click(function() {
			var url = _url("/distributor/popupUser?box=2");
			_popup(url, 1000, 500, "n");
		})
		
		$("#dist_deleteBtn").click(function() {
			var items = AUIGrid.getCheckedRowItems(dist_GridID);
			if (items.length > 1) {
				alert("배포처를 선택하세요.");
				return false;
			}
			AUIGrid.removeRowByRowId(dist_GridID, items[0].item.rowId);
		})

		$("#part_addBtn").click(function() {
			var url = _url("/dist/popup?box=2&cmd=1");
			_popup(url, 1000, 500, "n");
		})
		
		$("#part_addBtn2").click(function() {
			var url = _url("/dist/popup?box=2&cmd=2");
			_popup(url, 1000, 500, "n");
		})
		
		$("#part_addBtn3").click(function() {
			var url = _url("/dist/popup?box=2&cmd=3");
			_popup(url, 1000, 500, "n");
		})

		$("#deleteBttn").click(function() {
			var items = AUIGrid.getCheckedRowItems(myGridID);
			if (items.length > 1) {
				alert("배포도면을 선택 선택하세요.");
				return false;
			}
			AUIGrid.removeRowByRowId(myGridID, items[0].item.rowId);
		})
		
		$("#saveBtn").click(function() {
			if (!confirm("등록 하시겠습니까?")) {
				return false;
			}

			RAONKEDITOR.GetHtmlContents({
				type : 'htmlexwithdoctype',
				callback : function(paramObj) {
					$("#description").val(btoa(unescape(encodeURIComponent(paramObj.strData))));
				}
			}, 'editor');

			var params = _data($("#form"));
			var url = _url("/dist/create");
			var partList = AUIGrid.getGridData(myGridID);
			var distributorList = AUIGrid.getGridData(dist_GridID);
			params.partList = partList;
			console.log(distributorList);
			params.distributorList = distributorList;
			console.log(params);
			_call(url, params, function(data) {
				opener.load();
				self.close();
			}, "POST");
		})

		$(window).resize(function() {
			AUIGrid.resize("#grid_wrap");
			AUIGrid.resize("#dist_grid_wrap");
		})

		$("#deleteBtn").click(function() {
			var items = AUIGrid.getCheckedRowItems(myGridID);
			if (items.length == 0) {
				alert("삭제 할 도면을 선택하세요.");
			}
			AUIGrid.removeCheckedRows(myGridID);
		})

// 		$("#daddBtn").click(function() {
// 			var items = AUIGrid.getCheckedRowItems(myGridID);
// 			if (items.length > 1) {
// 				alert("하나의 행을 선택하세요.");
// 				return false;
// 			}
// 			items[0].item.type = "";
// 			items[0].item.user = "";
// 			AUIGrid.addRow(myGridID, items[0].item, items[0].rowIndex + 1);
// 		})

		_selector("duration");
	})

	function info(list) {
		AUIGrid.addRow(dist_GridID, list);
	}
	
	function dist(list) {
		AUIGrid.addRow(dist_GridID, list);
	}

	function part(list) {
		AUIGrid.addRow(myGridID, list);
	}
</script>