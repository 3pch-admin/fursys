<%@page import="org.json.JSONArray"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="platform.code.entity.BaseCodeType"%>
<%@page import="platform.doc.service.DocumentHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
BaseCodeType[] types = (BaseCodeType[]) request.getAttribute("types");
JSONArray codeTypes = (JSONArray) request.getAttribute("codeTypes");
// out.println(codeTypes);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<%@include file="/jsp/common/iframe.jsp"%>
<%@include file="/jsp/common/script.jsp"%>
</head>
<!-- <body> -->
<body onload="load();">
	<form id="form">
		<div class="header-title">
			<img src="/Windchill/jsp/images/home.png" class="home">
			<span>HOME</span>
			>
			<span>관리자</span>
			>
			<span>기준코드 조회</span>
		</div>
		<table id="wrap-table">
			<tr>
				<td>
					<table class="search-table top-color">
						<colgroup>
							<col width="100">
							<col width="*">
							<col width="100">
							<col width="*">
						</colgroup>
						<tr>
							<th>코드타입</th>
							<td>
								<select name="codeType" id="codeType" class="AXSelect w200px">
									<option value="">선택</option>
									<%
									for (BaseCodeType t : types) {
									%>
									<option value="<%=t.toString()%>"><%=t.getDisplay()%></option>
									<%
									}
									%>
								</select>
							</td>
							<th>사용여부</th>
							<td>
								<select name="enable" id="enable" class="AXSelect w200px">
									<option value="">선택</option>
									<option value="true">사용</option>
									<option value="false">사용안함</option>
								</select>
							</td>
						</tr>
						<tr>
							<th>코드</th>
							<td>
								<input type="text" name="code" class="AXInput w60p">
							</td>
							<th>코드 명</th>
							<td>
								<input type="text" name="name" class="AXInput w60p">
							</td>
						</tr>
					</table>

					<table class="button-table">
						<tr>
							<td class="left">
								<button type="button" id="addRowBtn">추가</button>
								<button type="button" id="deleteRowBtn">삭제</button>
								<button type="button" id="saveBtn">저장</button>
							</td>
							<td class="right">
								<button type="button" id="searchBtn">조회</button>
							</td>
						</tr>
					</table>

					<div id="grid_wrap" style="height: 710px;"></div>
					<script type="text/javascript">
						var keyValueList = <%=codeTypes%>;	
						var myGridID;
						//head : [ "번호", "문서번호", "문서명", "버전", "작성자", "작성일자", "수정자", "수정일자", "회사", "브랜드", "상태", "주 첨부파일" ]
						var columnLayout = [ 
// 							{
// 							dataField : "sort",
// 							headerText : "정렬",
// 							dataType : "string",
// 							width : 40,
// 							editable : false
// 						},
						{
							dataField : "code",
							headerText : "코드",
							dataType : "string",
							width : 120
						}, {
							dataField : "name",
							headerText : "코드 명",
							dataType : "string",
							width : 150
						}, {
							dataField : "codeType",
							headerText : "코드타입",
							dataType : "string",
							width : 120,
							editRenderer : { // 편집 모드 진입 시 드랍다운리스트 출력하고자 할 때
								type : "DropDownListRenderer",
								list : keyValueList,
								keyField : "label", // key 에 해당되는 필드명
								valueField : "value", // value 에 해당되는 필드명
								labelFunction : function(  rowIndex, columnIndex, value, headerText, item ) {  // key-value 에서 엑셀 내보내기 할 때 value 로 내보내기 위한 정의
									var retStr = ""; // key 값에 맞는 value 를 찾아 반환함.
									for(var i=0,len=keyValueList.length; i<len; i++) {
										if(keyValueList[i]["label"] == value) {
											retStr = keyValueList[i]["value"];
											break;
										}
									}
									return retStr == "" ? value : retStr;
								}
							}
						}, {
							dataField : "parentCode",
							headerText : "상위코드",
							dataType : "string",
							width : 120,
						}, {
							dataField : "parentName",
							headerText : "상위코드 명",
							dataType : "string",
							width : 150
						}, {
							dataField : "parentCodeType",
							headerText : "상위코드 타입",
							dataType : "string",
							width : 120
						}, {
							dataField : "enable",
							headerText : "사용여부",
							dataType : "string",
							width : 100,
							labelFunction : function(rowIndex, columnIndex, value, headerText, item, dataField, cItem) {
								if (value == true) {
									return "사용";
								} else {
									return "사용불가";
								}
								return "";
							}
						}, {
							dataField : "description",
							headerText : "설명",
							dataType : "string",
							style : "left indent10"
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
							fillColumnSizeMode : true,
							editable : true,
							rowNumHeaderText : "번호"
						};
						myGridID = AUIGrid.create("#grid_wrap", columnLayout, auiGridProps);

						function load() {
							var params = _data($("#form"));
							var url = _url("/baseCode/list");
							AUIGrid.showAjaxLoader(myGridID);
							_call(url, params, function(data) {
								AUIGrid.removeAjaxLoader(myGridID);
								AUIGrid.setGridData(myGridID, data.list);
							}, "POST");
						}

						// jquery 
						$(function() {
							$("#searchBtn").click(function() {
								load();
							})

							$("#addRowBtn").click(function() {
								var item = new Object();
								AUIGrid.addRow(myGridID, item, "first"); // 최하단에 1행 추가
							})

							$("#deleteRowBtn").click(function() {
								var items = AUIGrid.getCheckedRowItems(myGridID);
								if (items.length == 0) {
									alert("삭제 할 행을 선택하세요.");
								}
								for (var i = 0; i < items.length; i++) {
									AUIGrid.removeRow(myGridID, items[i].rowIndex);
								}
							})

							$("#saveBtn").click(function() {
								var removeRows = AUIGrid.getRemovedItems(myGridID);
								var addRows = AUIGrid.getAddedRowItems(myGridID);
								var editRows = AUIGrid.getEditedRowItems(myGridID);
								if (removeRows.length == 0 && addRows.length == 0 && editRows.length == 0) {
									return false;
								}

								if (!confirm("저장 하시겠습니까?")) {
									return false;
								}
								var params = new Object();
								params.addRows = addRows;
								params.editRows = editRows;
								params.removeRows = removeRows;
								var url = _url("/baseCode/save");
								_call(url, params, function(data) {
									load();
								}, "POST");
							});
							// 작성자, 수정자 선택바인드
						}).keypress(function(e) {
							if (e.keyCode == 13) {
								load();
							}
						})

						_selector("enable");
						_selector("codeType");
						$(window).resize(function() {
							AUIGrid.resize("#grid_wrap");
						})
					</script>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>