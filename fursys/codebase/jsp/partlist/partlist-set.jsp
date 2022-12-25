<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="header-title">
	<img src="/Windchill/jsp/images/home.png" class="home">
	<span>HOME</span>
	>
	<span>BOM관리</span>
	>
	<span>PART LIST 컬러셋</span>
</div>
<table class="search-table top-color">
	<colgroup>
		<col width="140">
		<col width="300">
		<col width="140">
		<col width="300">
		<col width="140">
		<col width="300">
		<col width="140">
		<col width="300">
	</colgroup>
	<tr>
		<th>유형</th>
		<td>Fitting</td>
		<th>ITEM_NAME</th>
		<td>체결부품</td>
		<th>PART_NAME</th>
		<td>Hafele Rafix 20</td>
		<th>부품적용색상</th>
		<td>BK</td>
	</tr>
</table>
<table class="button-table">
	<tr>
		<td class="right">
			<button type="button" id="applyBtn">적용</button>
			<button type="button" id="closeBtn">닫기</button>
		</td>
	</tr>
</table>
<div id="grid_wrap" style="height: 320px;"></div>
<script type="text/javascript">
	var colorList = [ "WW", "BK", "007B", "009", "009W", "021A" ];
	var myGridID;
	var columnLayout = [ {
		dataField : "type",
		headerText : "유형",
		dataType : "string",
		width : 90,
		editable : false
	}, {
		dataField : "itemName",
		headerText : "ITEM_NAME",
		dataType : "string",
		style : "center",
		width : 150,
		editable : false
	}, {
		dataField : "partName",
		headerText : "PART_NAME",
		dataType : "string",
		width : 250,
		editable : false
	}, {
		dataField : "number",
		headerText : "파일명",
		dataType : "string",
		width : 250,
		editable : false
	}, {
		dataField : "color",
		headerText : "CAD에 입력된 색상",
		dataType : "string",
		width : 150,
		editable : false
	}, {
		dataField : "erpCode",
		headerText : "ERP자재코드",
		dataType : "string",
		width : 200,
		editable : true,
	}, {
		dataField : "applyColor",
		headerText : "부품적용색상",
		dataType : "string",
		width : 120,
		editable : true,
	}, {
		dataField : "parentColor",
		headerText : "상위색상",
		dataType : "string",
		width : 100,
		editable : true,
	}, {
		dataField : "oid",
		headerText : "oid",
		dataType : "string",
		visible : false
	}, ];
	var auiGridProps = {
		// 		rowIdField : "oid",
		headerHeight : 30,
		rowHeight : 30,
		editable : false,
		fillColumnSizeMode : true,
		rowCheckToRadio : true,
		showRowCheckColumn : true,
		showRowNumColumn : false,
		independentAllCheckBox : true,
		selectionMode : "multipleCells",
		rowCheckDisabledFunction : function(rowIndex, isChecked, item) {
			if (item.erp) { // 이름이 Anna 인 경우 사용자 체크 못하게 함.
				return false;
			}
			return true;
		},
	};
	myGridID = AUIGrid.create("#grid_wrap", columnLayout, auiGridProps);
	function load() {
		requestData("/Windchill/jsp/partlist/mockup/partlist-set.json");
	}

	load();
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
		AUIGrid.setCheckedRowsByIds(myGridID, rowItem._$uid);
	});

	$(function() {

		$("#applyBtn").click(function() {
			var items = AUIGrid.getCheckedRowItems(myGridID);
			if (items.length == 0) {
				alert("적용할 컬러셋을 선택하세요.");
				return false;
			}
			opener.set(items[0].item);
			self.close();
		})

		$("#closeBtn").click(function() {
			self.close();
		})
	})
</script>