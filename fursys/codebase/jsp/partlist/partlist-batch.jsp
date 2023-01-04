<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="header-title">
	<img src="/Windchill/jsp/images/home.png" class="home">
	<span>HOME</span>
	>
	<span>BOM관리</span>
	>
	<span>PART LIST 일괄 등록</span>
</div>
<input type="hidden" name="check" value="">
<table class="search-table top-color">
	<colgroup>
		<col width="120">
		<col width="400">
		<col width="120">
		<col width="400">
		<col width="120">
		<col width="400">
	</colgroup>
	<tr>
		<th>품목코드(ERP CODE)</th>
		<td>CCC0054PN</td>
		<th>기준색상</th>
		<td>BK</td>
		<th>PLM 임시코드</th>
		<td>SET-202212-0002</td>
	</tr>
</table>
<table class="button-table">
	<tr>
		<td class="right">
			<button type="button" id="excelBtn">엑셀출력</button>
			<button type="button" id="searchBtn">ERP조회/적용</button>
<!-- 			<button type="button" id="searchBtn">ERP 조회</button> -->
		</td>
	</tr>
</table>
<div id="grid_wrap" style="height: 420px;"></div>
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
	}, {
		dataField : "number",
		headerText : "PLM 임시코드",
		dataType : "string",
		width : 250,
		editable : false
	}, {
		dataField : "partName",
		headerText : "품목명",
		dataType : "string",
		width : 250,
		editable : false
	}, {
		dataField : "state",
		headerText : "상태",
		dataType : "string",
		width : 200,
		editable : false
	}, {
		headerText : "기준색상",
		dataType : "string",
		width : 80,
		children : [ {
			dataField : "color",
			headerText : "BK",
			width : 80,
			dataType : "string",
			editable : true
		} ]
	// 	}, {
	// 		headerText : "파생색상",
	// 		children : [ {
	// 			dataField : "",
	// 			headerText : "WW",
	// 			width : 80
	// 		} ]
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
		// 		fillColumnSizeMode : true,
		// 		rowCheckToRadio : true,
		showRowCheckColumn : true,
		showRowNumColumn : false,
		enableCellMerge : true,
		cellMergePolicy : "withNull",
		selectionMode : "multipleCells",
	};
	myGridID = AUIGrid.create("#grid_wrap", columnLayout, auiGridProps);
	function load() {
		requestData("/Windchill/jsp/partlist/mockup/partlist-seprate.json");
	}

	var idx = 0;
	function addColumn(items) {
		// 새로운 칼럼들 작성
		for (var i = 0; i < items.length; i++) {
			var item = items[i].item;
			var columnArray = [ {
				dataField : "manager",
				headerText : "접수자",
				children : [ {
					dataField : "color_" + idx,
					headerText : item.code,
					width : 80,
					dataType : "string",
					editable : true
				} ]
			} ]
			AUIGrid.addColumn(myGridID, columnArray, "last");
			idx++;
		}
	};

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
			// 엑스트라 체크박스 
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
	
	function set(item) {
		var applyColor = item.applyColor;
		var items = [ {
			color : applyColor,
			set : true
		}, {
			color : applyColor,
			set : true
		}, {
			color : applyColor,
			set : true
		} ];
		var indexs = [ 3, 6, 7 ]
		AUIGrid.updateRows(myGridID, items, indexs);
		AUIGrid.update(myGridID);
	}
	
	$(function() {
		$("#closeBtn").click(function() {
			self.close();
		})
		
	$("#searchBtn").click(function() {
			var columnArray = [ {
				dataField : "manager",
				headerText : "접수자",
				children : [ {
					dataField : "color_" + idx,
					headerText : "CR",
					width : 80,
					dataType : "string",
					editable : true
				} ]
			}, {
				dataField : "manager",
				headerText : "접수자",
				children : [ {
					dataField : "color_" + idx,
					headerText : "WW",
					width : 80,
					dataType : "string",
					editable : true
				} ]
			}, {
				dataField : "manager",
				headerText : "접수자",
				children : [ {
					dataField : "color_" + idx,
					headerText : "BL",
					width : 80,
					dataType : "string",
					editable : true
				} ]
			} ]
			AUIGrid.addColumn(myGridID, columnArray, "last");
			idx++;
			$("input[name=check]").val("check");
		})

		$("#seprateBtn").click(function() {
			var check = $("input[name=check]").val();
			if (check == "") {
				alert("ERP 조회를 먼저 해주세요.");
				return false;
			}
			var items = AUIGrid.getCheckedRowItems(myGridID);
			if (items.length == 0) {
				alert("색상 선택할 품목을 선택하세요.");
				return false;
			}
			var url = _url("/baseCode/popup?codeType=COLOR&fun=color&box=2");
			_popup(url, 1200, 720, "n");
		})

		$("#derivedBtn").click(function() {
			var check = $("input[name=check]").val();
			if (check == "") {
				alert("ERP 조회를 먼저 해주세요.");
				return false;
			}
			var url = _url("/baseCode/popup?codeType=COLOR&fun=addColumn");
			_popup(url, 1200, 720, "n");
		})

		$("#colorBtn").click(function() {
			var check = $("input[name=check]").val();
			if (check == "") {
				alert("ERP 조회를 먼저 해주세요.");
				return false;
			}
			var url = _url("/partlist/set?fun=set");
			_popup(url, 1200, 720, "n");
		})
	})
</script>
<table class="button-table">
	<tr>
		<td class="right">
			<button type="button" id="derivedBtn">파생 색상 지정</button>
			<button type="button" id="">컬러셋 지정</button>
<!-- 			<button type="button" id="colorBtn">컬러셋 지정</button> -->
			<button type="button" id="seprateBtn">개별 색상 지정</button>
			<button type="button" id="saveBtn">저장</button>
			<button type="button" id="closeBtn">닫기</button>
		</td>
	</tr>
</table>