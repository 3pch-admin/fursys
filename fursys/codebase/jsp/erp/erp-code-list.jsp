<%@page import="platform.util.CommonUtils"%>
<%@page import="platform.doc.service.DocumentHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String callBack = request.getParameter("callBack");
String key = request.getParameter("key");
String code = CommonUtils.getSessionBrand();
%>
<div class="header-title">
	<img src="/Windchill/jsp/images/home.png" class="home">
	<span>HOME</span>
	>
	<span>ERP</span>
	>
	<span>제품코드 조회</span>
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
					<th>제품코드</th>
					<td>
						<input type="text" class="AXInput w70p" name="itm_cd">
					</td>
					<th>제품명</th>
					<td>
						<input type="text" class="AXInput w70p" name="itm_nm">
					</td>
				</tr>
				<tr>
					<th>제품구분</th>
					<td>
						<select class="AXSelect w200px" name="itm_type" id="itm_type">
							<option value="">선택</option>
							<option value="SET">세트</option>
							<option value="ITM">단품</option>
						</select>
					</td>
					<th>색상코드</th>
					<td>
						<input type="text" class="AXInput w70p" name="col_cd" id="col_cd">
					</td>
				</tr>
				<tr>
					<th>표준품 구분</th>
					<td>
						<select class="AXSelect w200px" name="com_stdsec" id="com_stdsec">
							<option value="">선택</option>
							<option value="관리주문품">관리주문품</option>
							<option value="비관리주문품">비관리주문품</option>
							<option value="표준품">표준품</option>
						</select>
					</td>
					<th>브랜드</th>
					<td>
						<select class="AXSelect w200px" name="com_brand" id="com_brand">
							<option value="">선택</option>
							<option value="T60F01" <%if("T60F01".equals(code)) { %> selected="selected"  <%} %> >퍼시스</option>
							<option value="T60I01" <%if("T60I01".equals(code)) { %> selected="selected"  <%} %> >일룸</option>
							<option value="T60I02" <%if("T60I02".equals(code)) { %> selected="selected"  <%} %> >데스커</option>
							<option value="T60I03" <%if("T60I03".equals(code)) { %> selected="selected"  <%} %> >알로소</option>
							<option value="T60P01" <%if("T60P01".equals(code)) { %> selected="selected"  <%} %> >시디즈</option>
							<option value="T60P02" <%if("T60P02".equals(code)) { %> selected="selected"  <%} %> >슬로우</option>
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

			<div id="grid_wrap" style="height: 380px;"></div>
			<script type="text/javascript">
				var myGridID;
				var columnLayout = [ {
					dataField : "itm_type",
					headerText : "제품구분",
					dataType : "string",
					width : 100
				}, {
					dataField : "itm_cd",
					headerText : "제품코드",
					dataType : "string",
					style : "left indent10"
				}, {
					dataField : "col_cd",
					headerText : "색상코드",
					dataType : "string",
					width : 80
				}, {
					dataField : "itm_nm",
					headerText : "제품명",
					dataType : "string",
					width : 250
				}, {
					dataField : "com_brand_nm",
					headerText : "브랜드",
					dataType : "string",
					width : 100
				}, {
					dataField : "com_stdsec",
					headerText : "표준품 구분",
					dataType : "string",
					width : 130
				}, {
					dataField : "use_yn",
					headerText : "사용유무",
					dataType : "string",
					width : 130
				}, ];
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
					var url = _url("/erp/getErpCode");
					AUIGrid.showAjaxLoader(myGridID);
					params.com_brand = "<%=code%>";
					_call(url, params, function(data) {
						AUIGrid.removeAjaxLoader(myGridID);
						AUIGrid.setGridData(myGridID, data.list);
					}, "POST");
				}
				
				function loadTree() {
					requestData("/Windchill/jsp/ebom/mockup/erp-code-list.json");
				}

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
				
				AUIGrid.bind(myGridID, "cellClick", function(event){
					var rowItem = event.item;
					console.log(rowItem);
					
					AUIGrid.setCheckedRowsByIds(myGridID, rowitem._$uid);
				})
				
				$(function() {
// 					load();
					loadTree();

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

					_selector("com_stdsec");
					_selector("com_brand");
					_selector("itm_type");
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