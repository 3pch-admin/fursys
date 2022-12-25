<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="header-title">
	<img src="/Windchill/jsp/images/home.png" class="home">
	<span>HOME</span>
	>
	<span>BOM관리</span>
	>
	<span>PART LIST 가공비</span>
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
		<th>품목코드(ERP CODE)</th>
		<td>CCC0054PN</td>
		<th>PLM 임시코드</th>
		<td>SET-202212-0002</td>
		<th>단품색상</th>
		<td>WW</td>
		<th>가공비</th>
		<td>24,120원</td>
	</tr>
</table>
<table class="button-table">
	<tr>
		<td class="right">
			<button type="button" id="excelBtn">엑셀출력</button>
			<button type="button" id="closeBtn">닫기</button>
		</td>
	</tr>
</table>
<div id="grid_wrap" style="height: 460px;"></div>
<script type="text/javascript">
	var myGridID;
	var columnLayout = [ {
		dataField : "partType",
		headerText : "부품유형",
		dataType : "string",
		width : 90,
		editable : false
	}, {
		dataField : "erpCode",
		headerText : "품목코드(ERP CODE)",
		dataType : "string",
		width : 150,
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
		dataField : "",
		headerText : "썸네일",
		dataType : "string",
		width : 70,
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
		dataField : "amount",
		headerText : "부품수량",
		dataType : "string",
		width : 80,
		postfix : "개",
		editable : false
	}, {
		headerText : "규격",
		children : [ {
			dataField : "",
			headerText : "W",
			width : 70
		}, {
			dataField : "",
			headerText : "H",
			width : 70
		}, {
			dataField : "",
			headerText : "D",
			width : 70
		} ]
	}, {
		dataField : "qty",
		headerText : "소요량",
		dataType : "string",
		width : 80,
		postfix : "개",
	}, {
		dataField : "unit",
		headerText : "단위",
		dataType : "string",
		width : 80,
	}, {
		dataField : "price",
		headerText : "단가 (ERP)",
		dataType : "string",
		width : 100,
	}, {
		dataField : "estimate",
		headerText : "예상단가",
		dataType : "string",
		width : 120,
		editable : true
	}, {
		dataField : "price",
		headerText : "금액",
		dataType : "string",
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
		fixedColumnCount : 5,
		// 		fillColumnSizeMode : true,
		// 		rowCheckToRadio : true,
		showRowCheckColumn : false,
		showRowNumColumn : false,
		enableCellMerge : true,
		cellMergePolicy : "withNull",
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
				headerText : "파생색상",
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
	})
</script>