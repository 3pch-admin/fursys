<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="header-title">
	<img src="/Windchill/jsp/images/home.png" class="home">
	<span>HOME</span>
	>
	<span>설계변경</span>
	>
	<span>DTMG 전송 등록</span>
</div>
<table class="search-table top-color">
	<colgroup>
		<col width="120">
		<col width="300">
		<col width="120">
		<col width="300">
		<col width="120">
		<col width="300">
		<col width="120">
		<col width="300">
	</colgroup>
	<tr>
		<th>DTMG 번호</th>
		<td>DTMG-1226-001</td>
		<th>DTMG 명</th>
		<td>
			<input type="text" class="AXInput">
		</td>
		<th>CAD Total Ass’y</th>
		<td>DT-H-37-500-T</td>
		<th>ERP CODE</th>
		<td>CCC10432</td>
	</tr>
</table>
<table class="button-table">
	<tr>
		<td class="right">
			<button type="button" id="addBtn">단품(품목)추가</button>
			<button type="button" id="deleteBtn">삭제</button>
			<button type="button" id="closeBtn">닫기</button>
		</td>
	</tr>
</table>
<div id="grid_wrap" style="height: 460px;"></div>
<script type="text/javascript">
	var myGridID;
	var columnLayout = [ {
		dataField : "",
		headerText : "T",
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
		}
	}, {
		dataField : "number",
		headerText : "부품번호",
		dataType : "string",
		width : 150,
		editable : false
	}, {
		dataField : "manager",
		headerText : "접수자",
		dataType : "string",
		width : 100,
		editable : false
	}, {
		dataField : "color",
		headerText : "파생색상",
		dataType : "string",
		width : 100,
		editable : false
	}, {
		dataField : "partType",
		headerText : "부품유형",
		dataType : "string",
		width : 100,
		editable : false
	}, {
		dataField : "partName",
		headerText : "부품명칭",
		dataType : "string",
		width : 150,
		editable : false
	}, {
		dataField : "version",
		headerText : "버전",
		dataType : "string",
		width : 80,
		editable : false
	}, {
		dataField : "ecoNumber",
		headerText : "ECO 번호",
		dataType : "string",
		width : 120,
		editable : false
	}, {
		dataField : "erpCode",
		headerText : "ERP CODE",
		dataType : "string",
		width : 120,
		editable : false
	}, {
		dataField : "state",
		headerText : "BOM 상태",
		dataType : "string",
		width : 120,
		editable : false
	}, {
		dataField : "creator",
		headerText : "작성자",
		dataType : "string",
		width : 120,
		editable : false
	}, {
		dataField : "createdDate",
		headerText : "작성일자",
		dataType : "date",
		formatString : "yyyy/mm/dd",
		width : 120,
		editable : false
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
		editable : false,
		showRowCheckColumn : true,
		showRowNumColumn : true,
		enableCellMerge : true,
		rowNumHeaderText : "번호",
		cellMergePolicy : "withNull",
		softRemoveRowMode : false
	};
	myGridID = AUIGrid.create("#grid_wrap", columnLayout, auiGridProps);
	function load() {
		requestData("/Windchill/jsp/dtmg/mockup/dtmg-create.json");
	}

	
	function requestData(url) {

		// ajax 요청 전 그리드에 로더 표시
		AUIGrid.showAjaxLoader(myGridID);

		// ajax (XMLHttpRequest) 로 그리드 데이터 요청
		ajax({
			url : url,
			onSuccess : function(data) {

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

	function color(items) {
		var item = items[0].item;
		var color = item.code;
		var arr = AUIGrid.getCheckedRowItems(myGridID);
		for (var i = 0; i < arr.length; i++) {
			arr[i]["color_" + idx] = color;
			console.log(arr[i]);
			AUIGrid.updateRow(myGridID, arr[i], arr[i].rowIndex);
		}
	}

	AUIGrid.bind(myGridID, "cellClick", function(event) {
		var item = event.item, rowIdField, rowId;
		rowIdField = AUIGrid.getProp(event.pid, "rowIdField"); // rowIdField 얻기
		rowId = item[rowIdField];

		// 이미 체크 선택되었는지 검사
		if (AUIGrid.isCheckedRowById(event.pid, rowId)) {
			// 엑스트라 체크박스 체크해제 추가
			AUIGrid.addUncheckedRowsByIds(event.pid, rowId);
		} else {
			// 엑스트라 체크박스 체크 추가
			AUIGrid.addCheckedRowsByIds(event.pid, rowId);
		}
	});
	
	function part(items) {
		console.log(items);
		load();
	}

	$(function() {

		$("#addBtn").click(function() {
			var url = "/Windchill/platform/part/popup?box=1";
			_popup(url, "", "", "f");
		})

		$("#closeBtn").click(function() {
			self.close();
		})

		$("#deleteBtn").click(function() {
			var items = AUIGrid.getCheckedRowItems(myGridID);
			for (var i = items.length - 1; i >= 0; i--) {
				console.log(items[i]);
				AUIGrid.removeRow(myGridID, items[i].rowIndex);
			}
		})
	})
</script>