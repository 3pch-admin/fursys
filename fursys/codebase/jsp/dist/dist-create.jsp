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
		<th>
			배포명
			<font color="red">
				<b>*</b>
			</font>
		</th>
		<td>
			<input type="text" class="AXInput w60p" name="name">
		</td>
		<th>
			다운로드 기간
			<font color="red">
				<b>*</b>
			</font>
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
						Height : "250px",
						FocusInitObjId : "name",
						Runtimes : 'html5' // agent, html5
					};
					new RAONKEditor(kEditorParam);
				</script>
			</div>
		</td>
	</tr>
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
</table>

<button type="button" id="addBtn">추가</button>
<!-- <button type="button" id="daddBtn">배포처 추가</button> -->
<button type="button" id="deleteBttn">삭제</button>
<div id="grid_wrap" style="height: 370px; padding-top: 5px;"></div>

<table class="button-table">
	<tr>
		<td class="right">
			<button type="button" id="saveBtn">등록</button>
			<button type="button" id="closeBtn">닫기</button>
		</td>
	</tr>
</table>

<script type="text/javascript">
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
		dataField : "type",
		headerText : "배포처구분",
		dataType : "string",
		width : 120,
		editable : false,
	}, {
		dataField : "distributor",
		headerText : "배포처",
		dataType : "string",
		width : 200,
		editable : false,
	// 		labelFunction : function(rowIndex, columnIndex, value, headerText, item) {
	// 			var retStr = "";
	// 			for (var i = 0, len = list.length; i < len; i++) {
	// 				if (list[i]["oid"] == value) {
	// 					retStr = list[i]["name"];
	// 					break;
	// 				}
	// 			}
	// 			return retStr == "" ? value : retStr;
	// 		},
	// 		editRenderer : {
	// 			type : "ComboBoxRenderer",
	// 			autoCompleteMode : true, // 자동완성 모드 설정
	// 			autoEasyMode : true,
	// 			matchFromFirst : false, // 처음부터 매치가 아닌 단순 포함되는 자동완성
	// 			list : list, //key-value Object 로 구성된 리스트
	// 			keyField : "oid", // key 에 해당되는 필드명
	// 			valueField : "name", // value 에 해당되는 필드명
	// 			// 에디팅 유효성 검사
	// 			validator : function(oldValue, newValue, item) {
	// 				var isValid = false;
	// 				for (var i = 0, len = list.length; i < len; i++) { // keyValueList 있는 값만..
	// 					if (list[i]["name"] == newValue) {
	// 						isValid = true;
	// 						break;
	// 					}
	// 				}
	// 				// 리턴값은 Object 이며 validate 의 값이 true 라면 패스, false 라면 message 를 띄움
	// 				return {
	// 					"validate" : isValid,
	// 					"message" : "리스트에 있는 값만 선택(입력) 가능합니다."
	// 				};
	// 			}
	// 		}
	}, {
		dataField : "user",
		headerText : "배포처 수신자",
		dataType : "string",
		width : 250
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

		$("#addBtn").click(function() {
			var url = _url("/dist/popup?box=2");
			_popup(url, "", "", "f");
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
			params.partList = partList;
			console.log(params);
			_call(url, params, function(data) {
				opener.load();
				self.close();
			}, "POST");
		})

		$(window).resize(function() {
			AUIGrid.resize("#grid_wrap");
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
		for (var i = 0; i < list.length; i++) {
			var value = list[i];
			var email = value.email;
			var type = value.type;
			var name = value.name;
			var uoid = value.uoid;
			var userName = value.userName;

			var selectedItems = AUIGrid.getSelectedItems(myGridID);
			if (selectedItems.length <= 0)
				return;

			// distributor, user 사용자, type
			
			if (i == 0) {
				var selItem = selectedItems[0].item;
				selItem.user = userName;
				selItem.distributor = name;
				selItem.uoid = uoid;
				selItem.type = type;
				AUIGrid.updateRowsById(myGridID, selItem, selItem.rowId);
			} else {
				var selItem = selectedItems[0].item;
				var item = new Object();
				// 선택행 아래..
				item  = selItem;
				item.user = userName;
				item.distributor = name;
				item.uoid = uoid;
				item.type = type;
				AUIGrid.addRow(myGridID, item, "selectionDown");
			}
		}
	}

	function part(list) {
		AUIGrid.addRow(myGridID, list);
	}
</script>