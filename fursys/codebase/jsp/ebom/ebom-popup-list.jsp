<%@page import="platform.doc.service.DocumentHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String callBack = (String) request.getParameter("callBack");
String box = (String) request.getParameter("box");
String purpose = (String) request.getParameter("purpose");
%>
<div class="header-title">
	<img src="/Windchill/jsp/images/home.png" class="home">
	<span>HOME</span>
	>
	<span>BOM 관리</span>
	>
	<span>EBOM 조회</span>
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
					<th>부품번호</th>
					<td>
						<input type="text" name="name" class="AXInput w60p">
					</td>
					<th>ECO번호</th>
					<td>
						<input type="text" name="number" class="AXInput w60p">
					</td>
				</tr>
				<tr>
					<th>상태</th>
					<td>
						<select name="state" id="state" class="AXSelect w200px">
							<option value="">선택</option>
							<option value="EBOM 작성중">EBOM 작성중</option>
							<option value="PART LIST 작성중">PART LIST 작성중</option>
							<option value="EBOM 승인중">EBOM 승인중</option>
							<option value="EBOM 승인됨">EBOM 승인됨</option>
						</select>
					</td>
					<th>ERP CODE</th>
					<td>
						<input type="text" name="erpCode" class="AXInput w60p">
					</td>
				</tr>
				<tr>
					<th>작성자</th>
					<td>
						<input type="text" class="AXInput w200px" name="creator" id="creator">
						<i class="axi axi-close2 axicon"></i>
					</td>
					<th>작성일자</th>
					<td>
						<input type="text" class="AXInput w100px" name="startCreatedDate" id="startCreatedDate" maxlength="8">
						~
						<input type="text" class="AXInput w100px" name="endCreatedDate" id="endCreatedDate" data-start="startCreatedDate" maxlength="8">
						<i class="axi axi-close2 axicon"></i>
					</td>
				</tr>
			</table>
			<table class="button-table">
				<tr>
					<td class="right">
					<%if(!purpose.equals("reference")) {%>
						<button type="button" id="addBtn">추가</button>
					<%
					}
					%>
						<button type="button" id="searchBtn">조회</button>
						<button type="button" id="closeBtn">닫기</button>
					</td>
				</tr>
			</table>

			<div id="grid_wrap" style="height: 775px;"></div>
			<script type="text/javascript">
				var myGridID;
				var columnLayout = [ {
					headerText : "P",
					dataType : "string",
					width : 40,
					renderer : {
						type : "IconRenderer",
						iconWidth : 16,
						iconHeight : 16,
						iconFunction : function(rowIndex, columnIndex, value, item) {
							return item.p;
						}
					}
				}, {
					dataField : "thumb",
					headerText : "T",
					dataType : "string",
					width : 40,
					renderer : {
						type : "IconRenderer",
						iconWidth : 30,
						iconHeight : 22,
						iconFunction : function(rowIndex, columnIndex, value, item) {
							return item.t;
						}
					}
				}, {
					dataField : "number",
					// 							headerText : "부품번호",
					headerText : "CREO 파일명",
					dataType : "string",
					// 							width : 250,
					style : "left indent10"
				}, {
					dataField : "partType",
					headerText : "부품유형",
					dataType : "string",
					width : 90
				}, {
					dataField : "erpCode",
					headerText : "세트/단품코드",
					dataType : "string",
					style : "center",
					width : 200
// 				}, {
// 					dataField : "name",
// 					headerText : "부품명칭",
// 					dataType : "string",
// 					style : "left indent10"
				}, {
					dataField : "version",
					headerText : "버전",
					dataType : "string",
					width : 80
// 				}, {
// 					dataField : "ecoNumber",
// 					headerText : "ECO 번호",
// 					dataType : "string",
// 					width : 150
// 				}, {
// 					dataField : "erpCode",
// 					headerText : "ERP CODE",
// 					dataType : "string",
// 					width : 150
				}, {
					dataField : "state",
					headerText : "BOM 상태",
					dataType : "string",
					width : 130
// 				}, {
// 					dataField : "org",
// 					headerText : "조직",
// 					dataType : "string",
// 					width : 100
				}, {
					dataField : "creator",
					headerText : "작성자",
					dataType : "string",
					width : 100
				}, {
					dataField : "createdDate",
					headerText : "작성일자",
					dataType : "date",
					formatString : "yyyy/mm/dd",
					width : 100
				}, {
					dataField : "oid",
					headerText : "oid",
					dataType : "string",
					visible : false
				}, {
					dataField : "poid",
					headerText : "poid",
					dataType : "string",
					visible : false
				}, ];
				var auiGridProps = {
					rowIdField : "oid",
					headerHeight : 30,
					rowHeight : 30,
					<%if (box.equals("1")) {%>
					rowCheckToRadio : true,
					<%}%>
					fillColumnSizeMode : true,
// 					rowCheckToRadio : true,
					showRowCheckColumn : true,
					rowNumHeaderText : "번호",
// 					usePaging : true
				};
				myGridID = AUIGrid.create("#grid_wrap", columnLayout, auiGridProps);
				

				function load() {
					var params = _data($("#form"));
					var url = _url("/ebom/list");
					AUIGrid.showAjaxLoader(myGridID);
					_call(url, params, function(data) {
						AUIGrid.removeAjaxLoader(myGridID);
						AUIGrid.setGridData(myGridID, data.list);
					}, "POST");
				}

				$(function() {
					load();
					$("#closeBtn").click(function() {
						self.close();
					})

					$("#addBtn").click(function() {
						var items = AUIGrid.getCheckedRowItems(myGridID);
						if (items.length == 0) {
							alert("추가 할 단품/세트를 선택하세요.");
							return false;
						}
						// 						var state = items[0].item.state;
						// 						if (state != "PART LIST 작성중") {
						// 							alert("PART LIST 작성중인 EBOM만 추가 할 수 있습니다.");
						// 							return false;
						// 						}
						
						for(var i=0; i<items.length; i++) {
							var params = new Object();
							params.poid = items[i].item.poid;
							params.oid = items[i].item.oid;
							var url = _url("/ebom/info");
							_call(url, params, function(data) {
								opener.<%=callBack%>(data.list);
								self.close();
							}, "POST");
						}
					})

					$("#searchBtn").click(function() {
						load();
					})

				<%if ("1".equals(box)) {%>
				AUIGrid.bind(myGridID, "cellClick", function(event) {
						var dataField = event.dataField;
						var rowItem = event.item;
						var state = rowItem.state;
						if (dataField == "number") {
							if (state == "EBOM 임시저장") {
								var url = _url("/ebom/view", rowItem.oid);
								_popup(url, "", "", "f");
							} else if(state == "EBOM 작성중(검증완료)") {
								var url = _url("/ebom/modify", rowItem.oid);
								_popup(url, "", "", "f");
							}
						} else if (event.dataField == "thumb") {
							_openCreoView(event.item.toid);
						}
					});
				<%} else {%>
				AUIGrid.bind(myGridID, "cellClick", function(event) {
					var item = event.item, rowIdField, rowId;
					rowIdField = AUIGrid.getProp(event.pid, "rowIdField"); // rowIdField 얻기
					rowId = item[rowIdField];
					
					// 이미 체크 선택되었는지 검사
					if(AUIGrid.isCheckedRowById(event.pid, rowId)) {
						// 엑스트라 체크박스 체크해제 추가
						AUIGrid.addUncheckedRowsByIds(event.pid, rowId);
					} else {
						// 엑스트라 체크박스 체크 추가
						AUIGrid.addCheckedRowsByIds(event.pid, rowId);
					}
				});	
				<%}%>
				
					// 작성자, 수정자 선택바인드
					_selector("state");
					_user("creator");
					_between("endCreatedDate");
				}).keypress(function(e) {
					if (e.keyCode == 13) {
						load();
					}
				})

				$(window).resize(function() {
					AUIGrid.resize("#grid_wrap");
				})
			</script>
		</td>
	</tr>
</table>