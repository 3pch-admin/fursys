<%@page import="platform.doc.service.DocumentHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String callBack = request.getParameter("callBack");
String key = request.getParameter("key");
%>
<div class="header-title">
	<img src="/Windchill/jsp/images/home.png" class="home">
	<span>HOME</span>
	>
	<span>ERP</span>
	>
	<span>재고수량 조회</span>
</div>
<table id="wrap-table">
	<tr>
		<td>
			<table class="search-table">
				<colgroup>
					<col width="100">
					<col width="*">
					<col width="100">
					<col width="*">
				</colgroup>
				<tr>
					<th>자재코드</th>
					<td>
						<input type="text" class="AXInput w70p" name="matcd">
					</td>
					<th>색상코드</th>
					<td>
						<input type="text" class="AXInput w70p" name="matcol">
					</td>
				</tr>
				<tr>
					<th>사업장</th>
					<td>
					<select class="AXSelect w200px" name="storecd" id="storecd">
					<option value="">선택</option>
					</select>
					</td>
					<th>조회월</th>
					<td>
						<select class="AXSelect w200px" name="yymm" id="yymm">
							<option value="">선택</option>
							<option value="01">1월</option>
							<option value="02">2월</option>
							<option value="03">3월</option>
							<option value="04">4월</option>
							<option value="05">5월</option>
							<option value="06">6월</option>
							<option value="07">7월</option>
							<option value="08">8월</option>
							<option value="09">9월</option>
							<option value="10">10월</option>
							<option value="11">11월</option>
							<option value="12">12월</option>
						</select>
					</td>
				</tr>
			</table>

			<table class="button-table">
				<tr>
					<td class="right">
						<button type="button" id="addBtn">추가</button>
						<button type="button" id="searchBtn">조회</button>
						<button type="button" id="closeBtn">닫기</button>
					</td>
				</tr>
			</table>

			<div id="grid_wrap" style="height: 430px;"></div>
			<script type="text/javascript">
				var myGridID;			
				var columnLayout = [ {
					dataField : "storecd",
					headerText : "사업장",
					dataType : "string",
					width : 100
				}, {
					dataField : "matcd",
					headerText : "자재코드",
					dataType : "string",
					style : "left indent10"
				}, {
					dataField : "matcol",
					headerText : "색상",
					dataType : "string",
					width : 80
				}, {
					dataField : "nowqty",
					headerText : "수량",
					dataType : "string",
					width : 250
				}, {
					dataField : "unit",
					headerText : "UNIT",
					dataType : "string",
					width : 100
				}, {
					dataField : "yymm",
					headerText : "년월",
					dataType : "string",
					width : 100
				}];
				var auiGridProps = {
					// 					rowIdField : "oid",
					headerHeight : 30,
					rowHeight : 30,
					fillColumnSizeMode : true,
					rowCheckToRadio : true,
					showRowCheckColumn : true,
					rowNumHeaderText : "번호",
				// 					usePaging : true
				};
				myGridID = AUIGrid.create("#grid_wrap", columnLayout, auiGridProps);

				function load() {
					var params = _data($("#form"));
					var url = _url("/erp/getStock");
					AUIGrid.showAjaxLoader(myGridID);
					_call(url, params, function(data) {
						AUIGrid.removeAjaxLoader(myGridID);
						AUIGrid.setGridData(myGridID, data.list);
					}, "POST");
				}

				$(function() {
					load();

					$("#searchBtn").click(function() {
						load();
					})
					$("#closeBtn").click(function() {
						self.close();
					})
					$("#addBtn").click(function() {
						var items = AUIGrid.getCheckedRowItems(myGridID);
						if (items.length == 0) {
							alert("추가 할 세트 및 단품 코드를 선택하세요.");
							return false;
						}
						var itm_cd = items[0].item.itm_cd;
						opener.<%=callBack%>(itm_cd, "<%=key%>");
						self.close();
					})
				}).keypress(function(e) {
					if (e.keyCode == 13) {
						load();
					}
				})

				_selector("yymm");
				_selector("storecd");
				
				$(window).resize(function() {
					AUIGrid.resize("#grid_wrap");
				})
			</script>
		</td>
	</tr>
</table>