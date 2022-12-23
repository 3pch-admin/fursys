<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
	<span>PART LIST 일괄 등록</span>
</div>
<table class="search-table top-color">
	<colgroup>
		<col width="150">
		<col width="580">
		<col width="150">
		<col width="580">
	</colgroup>
	<tr>
		<th>품목코드(ERP CODE)</th>
		<td>CCC0054PN</td>
		<th>PLM 임시코드</th>
		<td>ITEM-2212-0002</td>
	</tr>
</table>
<table class="button-table">
	<tr>
		<td class="right">
			<button type="button" id="searchBtn">조회</button>
		</td>
	</tr>
</table>
<div id="grid_wrap" style="height: 320px;"></div>
<script type="text/javascript">
	var colorList = [ "WW", "BK", "007B", "009", "009W", "021A" ];
	var myGridID;
	var columnLayout = [ {
		dataField : "partType",
		headerText : "부품유형",
		dataType : "string",
		width : 90,
		editable : false
	// 	}, {
	// 		dataField : "manager",
	// 		headerText : "단품 접수자",
	// 		dataType : "string",
	// 		style : "center",
	// 		width : 100,
	// 		editable : false
	}, {
		dataField : "erpCode",
		headerText : "품목코드(ERP CODE)",
		dataType : "string",
		width : 250,
		editable : false
// 	}, {
// 		dataField : "number",
// 		headerText : "PLM 임시코드",
// 		dataType : "string",
// 		width : 250,
// 		editable : false
	}, {
		dataField : "partName",
		headerText : "품목명",
		dataType : "string",
		width : 250,
		editable : false
	}, {
		dataField : "state",
		headerText : "BOM상태",
		dataType : "string",
		width : 200,
		editable : false
		}, {
			headerText : "홍길동",
			children : [ {
				dataField : "color",
				headerText : "AGNWW",
				width : 80
			} ]
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
		editable : true,
				fillColumnSizeMode : true,
				displayTreeOpen : true,
		// 		rowCheckToRadio : true,
		showRowCheckColumn : true,
		showRowNumColumn : false,
		enableCellMerge : true,
		cellMergePolicy : "withNull",
	};
	myGridID = AUIGrid.create("#grid_wrap", columnLayout, auiGridProps);
	
	function load() {
		requestData("/Windchill/jsp/mbom/mockup/mbom-modify.json");
	}


	load();
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

	AUIGrid.bind(myGridID, "cellEditBegin", function(event) {
		if (event.isClipboard) {
			return true;
		}
		return false;
	});

	$(function() {
		$("#closeBtn").click(function() {
			self.close();
		})

		$("#searchBtn").click(function() {
			var item = {
				color : "BK/WW/009"
			};
			AUIGrid.updateRow(myGridID, item, 1);
		})

		// 		$("#saveBtn").click(function() {
		// 			addColumn();
		// 		})
	})
</script>
<table class="button-table">
	<tr>
		<td class="right">
			<button type="button" id="saveBtn">저장</button>
			<button type="button" id="closeBtn">닫기</button>
		</td>
	</tr>
</table>