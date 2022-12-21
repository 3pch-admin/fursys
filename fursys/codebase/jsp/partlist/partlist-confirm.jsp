<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="header-title">
	<img src="/Windchill/jsp/images/home.png" class="home">
	<span>HOME</span>
	>
	<span>BOM관리</span>
	>
	<span>PART LIST 확정</span>
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
		<td>SET-202212-0002</td>
	</tr>
</table>
<table class="button-table">
	<tr>
		<td class="right">
			<button type="button" id="addBtn">원부자재 추가</button>
			<button type="button" id="searchBtn">추정원가 조회</button>
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
		headerText : "파생색상",
		children : [ {
			dataField : "color_1",
			headerText : "WW",
			width : 80
		} ]
	}, {
		headerText : "파생색상",
		children : [ {
			dataField : "color_2",
			headerText : "RED",
			width : 80
		} ]
	}, {
		headerText : "파생색상",
		children : [ {
			dataField : "color_3",
			headerText : "YELLOW",
			width : 80
		} ]
	}, {
		headerText : "파생색상",
		children : [ {
			dataField : "color_4",
			headerText : "BK",
			width : 80
		} ]
	}, {
		headerText : "파생색상",
		children : [ {
			dataField : "color_5",
			headerText : "CC",
			width : 80
		} ]
	}, {
		headerText : "파생색상",
		children : [ {
			dataField : "",
			headerText : "009A",
			width : 80
		} ]
	}, {
		headerText : "파생색상",
		children : [ {
			dataField : "",
			headerText : "007",
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
		// 		fillColumnSizeMode : true,
		// 		rowCheckToRadio : true,
		showRowCheckColumn : false,
		showRowNumColumn : false,
		enableCellMerge : true,
		cellMergePolicy : "withNull",
	};
	myGridID = AUIGrid.create("#grid_wrap", columnLayout, auiGridProps);
	function load() {
		requestData("/Windchill/jsp/partlist/mockup/partlist-confirm.json");
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

	$(function() {
		$("#closeBtn").click(function() {
			self.close();
		})

		$("#saveBtn").click(function() {
		})

		$("#searchBtn").click(function() {
			var url = _url("/partlist/estimate");
			_popup(url, 1500, 500, "n");
		})
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