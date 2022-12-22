<%@page import="platform.code.service.BaseCodeHelper"%>
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
String codeType = (String) request.getAttribute("codeType");
BaseCodeType c = BaseCodeType.toBaseCodeType(codeType);
String box = (String) request.getParameter("box");
String fun = request.getParameter("fun");
%>
<div class="header-title">
	<img src="/Windchill/jsp/images/home.png" class="home">
	<span>HOME</span>
	>
	<span>관리자</span>
	>
	<span><%=c.getDisplay() %>코드 조회</span>
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
							<option value="<%=t.toString()%>" <%if (codeType.equals(t.toString())) {%> selected="selected" <%}%>><%=t.getDisplay()%></option>
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
						<input type="text" name="code" class="AXInput w60p" autofocus="autofocus">
					</td>
					<th>코드 명</th>
					<td>
						<input type="text" name="name" class="AXInput w60p">
					</td>
				</tr>
			</table>

			<table class="button-table">
				<tr>
					<td class="right">
						<%
							if(!"2".equals(box)) {
						%>
						<button type="button" class="applyBtn" data-apply="s">적용</button>
						<%
							}
						%>
						<button type="button" class="applyBtn" data-apply="a">추가</button>
						<button type="button" id="searchBtn">조회</button>
						<button type="button" id="closeBtn">닫기</button>
					</td>
				</tr>
			</table>

			<div id="grid_wrap" style="height: 530px;"></div>
			<script type="text/javascript">
				var keyValueList =<%=codeTypes%>;
				var myGridID;
				//head : [ "번호", "문서번호", "문서명", "버전", "작성자", "작성일자", "수정자", "수정일자", "회사", "브랜드", "상태", "주 첨부파일" ]
				var columnLayout = [ 
// 				{
// 					dataField : "sort",
// 					headerText : "정렬",
// 					dataType : "string",
// 					width : 40,
// 					editable : false
// 				},
				{
					dataField : "code",
					headerText : "코드",
					dataType : "string",
					width : 120
				}, {
					dataField : "name",
					headerText : "코드 명",
					dataType : "string",
					width : 180
				}, {
					dataField : "codeType",
					headerText : "코드타입",
					dataType : "string",
					width : 120,
					filter : {
						showIcon : true
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
					filter : {
						showIcon : true
					},
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
					editable : false,
					rowIdField : "oid",
					headerHeight : 30,
					rowHeight : 30,
					<%
						if("2".equals(box)) {
					%>
					rowCheckToRadio : true,
					<%
						}
					%>
					showRowCheckColumn : true,
					fillColumnSizeMode : true,
					enableFilter : true,
					filterLayerWidth : 280,
					filterLayerHeight : 340,
					rowNumHeaderText : "번호"
				};
				myGridID = AUIGrid.create("#grid_wrap", columnLayout, auiGridProps);

				// 셀클릭 이벤트 바인딩
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
				function load() {
					var params = _data($("#form"));
					var url = _url("/baseCode/list");
					params.codeType = "<%=codeType%>";
					AUIGrid.showAjaxLoader(myGridID);
					_call(url, params, function(data) {
						AUIGrid.removeAjaxLoader(myGridID);
						AUIGrid.setGridData(myGridID, data.list);
					}, "POST");
				}
				load();

				// jquery 
				$(function() {

					$("#closeBtn").click(function() {
						self.close();
					})

					$(".applyBtn").click(function() {
						var apply = $(this).data("apply");
						var items = AUIGrid.getCheckedRowItems(myGridID);
// 						if (items.length == 0) {
// 							alert("추가 할 색상을 선택하세요.");
// 							return false;
// 						}

						opener.<%=fun%>(items);
						if (apply == "a") {
							self.close();
						}
					})

					$("#searchBtn").click(function() {
						load();
					})

					$("#codeType").bindSelectDisabled(true);
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