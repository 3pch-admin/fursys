<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="header-title">
	<img src="/Windchill/jsp/images/home.png" class="home">
	<span>HOME</span>
	>
	<span>BOM관리</span>
	>
	<span>PART LIST 단품 색상</span>
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
		<td>.SET-202212-0002</td>
	</tr>
</table>
<table class="button-table">
	<tr>
		<td class="right">
			<button type="button" id="searchBtn">ERP 조회</button>
			<button type="button" id="applyBtn">ERP 저용</button>
		</td>
	</tr>
</table>
<div id="grid_wrap" style="height: 320px;"></div>
<script type="text/javascript">
	var myGridID;
	var totalRowCount;
	var rowCount = 30; // 1페이지에서 보여줄 행 수
	var pageButtonCount = 10; // 페이지 네비게이션에서 보여줄 페이지의 수
	var currentPage = 1; // 현재 페이지
	var totalPage;
	var columnLayout = [ {
		dataField : "partType",
		headerText : "부품유형",
		dataType : "string",
		width : 90
	}, {
		dataField : "manager",
		headerText : "단품 접수자",
		dataType : "string",
		style : "center",
		width : 100
	}, {
		dataField : "erpCode",
		headerText : "품목코드(ERP CODE)",
		dataType : "string",
		width : 250
	}, {
		dataField : "number",
		headerText : "PLM 임시코드",
		dataType : "string",
		width : 250
	}, {
		dataField : "creator",
		headerText : "품목명",
		dataType : "string",
		width : 250
	}, {
		dataField : "createdDate",
		headerText : "적용색상",
		dataType : "string",
		width : 200
	}, {
		dataField : "oid",
		headerText : "oid",
		dataType : "string",
		visible : false
	}, ];
	var auiGridProps = {
		//		rowIdField : "oid",
		headerHeight : 30,
		rowHeight : 30,
		fillColumnSizeMode : true,
		rowCheckToRadio : true,
		showRowCheckColumn : true,
		showRowNumColumn : false
	};
	myGridID = AUIGrid.create("#grid_wrap", columnLayout, auiGridProps);

	$(function() {
		$("#closeBtn").click(function() {
			self.close();
		})
	})
</script>
<table class="button-table">
	<tr>
		<td class="right">
			<button type="button" id="searchBtn">개별 색상 지정</button>
			<button type="button" id="searchBtn">저장</button>
			<button type="button" id="closeBtn">닫기</button>
		</td>
	</tr>
</table>