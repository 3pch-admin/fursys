<%@page import="java.util.ArrayList"%>
<%@page import="platform.code.entity.BaseCode"%>
<%@page import="wt.part.QuantityUnit"%>
<%@page import="platform.part.service.PartHelper"%>
<%@page import="platform.doc.service.DocumentHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String opt = (String) request.getParameter("opt"); // a 세트/단품, b 자재
String box = (String) request.getParameter("box");
String callBack = (String) request.getParameter("callBack");
ArrayList<BaseCode> brand = (ArrayList<BaseCode>) request.getAttribute("brand");
ArrayList<BaseCode> company = (ArrayList<BaseCode>) request.getAttribute("company");
ArrayList<BaseCode> cat_l = (ArrayList<BaseCode>) request.getAttribute("cat_l");
ArrayList<BaseCode> cat_m = (ArrayList<BaseCode>) request.getAttribute("cat_m");
QuantityUnit[] units = (QuantityUnit[]) request.getAttribute("units");
%>
<div class="header-title">
	<img src="/Windchill/jsp/images/home.png" class="home">
	<span>HOME</span>
	>
	<span>부품 관리</span>
	>
	<span>부품 조회</span>
</div>
<table id="wrap-table">
	<colgroup>
		<col width="200">
		<col width="10">
		<col width="*">
	</colgroup>
	<tr>
		<td valign="top"><jsp:include page="/jsp/common/folder-tree.jsp">
				<jsp:param value="<%=PartHelper.ROOT%>" name="location" />
			</jsp:include></td>
		<td>&nbsp;</td>
		<td>
			<table class="search-table">
				<colgroup>
					<col width="100">
					<col width="*">
					<col width="100">
					<col width="*">
					<col width="100">
					<col width="*">
				</colgroup>
				<tr>
					<th>부품분류</th>
					<td colspan="5">
						<input type="text" class="AXInput w80p" name="location" id="location" value="<%=PartHelper.ROOT%>" readonly="readonly">
					</td>
				</tr>
				<tr>
					<th>부품명칭</th>
					<td>
						<input type="text" name="name" class="AXInput w60p">
					</td>
					<th>부품번호</th>
					<td>
						<input type="text" name="number" class="AXInput w60p">
					</td>
					<th>ERP 코드</th>
					<td>
						<input type="text" name="erpCode" class="AXInput w60p">
					</td>
				</tr>
				<tr>
					<th>버전</th>
					<td>
						<label>
							<input type="radio" name="latest" value="true" checked="checked">
							<span>최신버전</span>
						</label>
						&nbsp;
						<label>
							<input type="radio" name="latest" value="false">
							<span>모든버전</span>
						</label>
					</td>
					<th>부품유형</th>
					<td colspan="3">
						<!-- 						<label> -->
						<!-- 							<input type="radio" name="partType" value="ALL" checked="checked"> -->
						<!-- 							<span>전체</span> -->
						<!-- 						</label> -->
						<%
						if ("a".equals(opt)) {
						%>
						<label>
							<input type="radio" name="partType" value="SET" checked="checked">
							<span>세트</span>
						</label>
						<label>
							<input type="radio" name="partType" value="ITEM">
							<span>단품</span>
						</label>
						<%
						} else {
						%>
						<label>
							<input type="radio" name="partType" value="MAT" checked="checked">
							<span>자재</span>
						</label>
						<%
						}
						%>
					</td>
				</tr>
				<tr>
					<th>단위</th>
					<td>
						<select name="unit" id="unit" class="AXSelect w200px">
							<option value="">선택</option>
							<%
							for (QuantityUnit unit : units) {
							%>
							<option value="<%=unit.toString()%>"><%=unit.getDisplay()%> (<%=unit.toString()%>)
							</option>
							<%
							}
							%>
						</select>
					</td>
					<th>카테고리 대</th>
					<td>
						<select name="cat_l" id="cat_l" class="AXSelect w200px">
							<option value="">선택</option>
							<%
							for (BaseCode c : cat_l) {
							%>
							<option value="<%=c.getName()%>"><%=c.getName()%></option>
							<%
							}
							%>
						</select>
					</td>
					<th>카테고리 중</th>
					<td>
						<select name="cat_m" id="cat_m" class="AXSelect w200px">
							<option value="">선택</option>
							<%
							for (BaseCode c : cat_m) {
							%>
							<option value="<%=c.getName()%>"><%=c.getName()%></option>
							<%
							}
							%>
						</select>
					</td>
				</tr>
				<tr>
					<th>상태</th>
					<td>
						<select name="state" id="state" class="AXSelect w200px">
							<option value="">선택</option>
							<option value="INWORK">작업 중</option>
							<option value="APPROVAL">승인 중</option>
							<option value="RELEASED">릴리즈됨</option>
							<option value="RETURN">반려됨</option>
						</select>
					</td>
					<th>회사</th>
					<td>
						<select name="company" id="company" class="AXSelect w200px">
							<option value="">선택</option>
							<%
							for (BaseCode c : company) {
							%>
							<option value="<%=c.getCode()%>"><%=c.getName()%></option>
							<%
							}
							%>
						</select>
					</td>
					<th>브랜드</th>
					<td>
						<select name="brand" id="brand" class="AXSelect w200px">
							<option value="">선택</option>
							<%
							for (BaseCode b : brand) {
							%>
							<option value="<%=b.getCode()%>"><%=b.getName()%></option>
							<%
							}
							%>
						</select>
					</td>
				</tr>
				<tr>
					<th>조회조건 확장</th>
					<td>
						<label>
							<input type="radio" name="attr" value="true">
							<span>자재속성</span>
						</label>
						<label>
							<input type="radio" name="attr" value="false">
							<span>설계속성(공통)</span>
						</label>
					</td>
					<th>작성일자</th>
					<td>
						<input type="text" class="AXInput w100px" name="startCreatedDate" id="startCreatedDate" maxlength="8">
						~
						<input type="text" class="AXInput w100px" name="endCreatedDate" id="endCreatedDate" data-start="startCreatedDate" maxlength="8">
						<i class="axi axi-close2 axicon"></i>
					</td>
					<th>수정일자</th>
					<td>
						<input type="text" class="AXInput w100px" name="startModifiedDate" id="startModifiedDate" maxlength="8">
						~
						<input type="text" class="AXInput w100px" name="endModifiedDate" id="endModifiedDate" data-start="startModifiedDate" maxlength="8">
						<i class="axi axi-close2 axicon"></i>
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

			<div id="grid_wrap" style="height: 610px;"></div>
			<script type="text/javascript">
				var myGridID;

				var columnLayout = [ {
					// 							dataField : "p",
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
					dataField : "s",
					headerText : "S",
					dataType : "string",
					width : 40
				}, {
					// 							dataField : "_3d",
					headerText : "3D",
					dataType : "string",
					width : 40,
					renderer : {
						type : "IconRenderer",
						iconWidth : 30,
						iconHeight : 22,
						iconFunction : function(rowIndex, columnIndex, value, item) {
							return item._3d;
						}
					}
				}, {
					// 							dataField : "_2d",
					headerText : "2D",
					dataType : "string",
					width : 40,
					renderer : {
						type : "IconRenderer",
						iconWidth : 16,
						iconHeight : 16,
						iconFunction : function(rowIndex, columnIndex, value, item) {
							return item._2;
						}
					}
				}, {
					dataField : "partType",
					headerText : "부품유형",
					dataType : "string",
					width : 100
				}, {
					dataField : "number",
					headerText : "부품번호",
					dataType : "string",
					width : 250,
					style : "left indent10"
				}, {
					dataField : "name",
					headerText : "부품명칭",
					dataType : "string",
					width : 350,
					style : "left indent10"
				}, {
					dataField : "version",
					headerText : "버전",
					dataType : "string",
					width : 80
				}, {
					dataField : "ref",
					headerText : "CAD연계",
					dataType : "string",
					width : 80
				}, {
					dataField : "derived",
					headerText : "파생여부",
					dataType : "string",
					width : 80
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
					dataField : "modifier",
					headerText : "수정자",
					dataType : "string",
					width : 100
				}, {
					dataField : "modifiedDate",
					headerText : "수정일자",
					dataType : "date",
					formatString : "yyyy/mm/dd",
					width : 100
				}, {
					dataField : "state",
					headerText : "상태",
					dataType : "string",
					width : 150
				}, {
					dataField : "erpCode",
					headerText : "ERP CODE",
					dataType : "string",
					width : 100
				}, {
					dataField : "company",
					headerText : "회사",
					dataType : "string",
					width : 100
				}, {
					dataField : "brand",
					headerText : "브랜드",
					dataType : "string",
					width : 100
				}, {
					dataField : "unit",
					headerText : "단위",
					dataType : "string",
					width : 100
				}, {
					dataField : "order",
					headerText : "주문품여부",
					dataType : "string",
					width : 100
				}, {
					dataField : "use",
					headerText : "사용여부",
					dataType : "string",
					width : 100
				}, {
					dataField : "w",
					headerText : "규격가로(W)",
					dataType : "string",
					width : 100
				}, {
					dataField : "d",
					headerText : "규격세로(D)",
					dataType : "string",
					width : 100
				}, {
					dataField : "h",
					headerText : "사용높이(H)",
					dataType : "string",
					width : 100
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
					<%if (box.equals("1")) {%>
					rowCheckToRadio : true,
					<%}%>
					showRowCheckColumn : true,
					rowNumHeaderText : "번호",
					usePaging : true
				};
				myGridID = AUIGrid.create("#grid_wrap", columnLayout, auiGridProps);

				function load() {
					var params = _data($("#form"));
					var url = _url("/part/list");
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
					$("#closeBtn").click(function() {
						self.close();
					})
					
							<%if ("1".equals(box)) {%>
				AUIGrid.bind(myGridID, "cellClick", function(event) {
					var rowItem = event.item;
					AUIGrid.setCheckedRowsByIds(myGridID, rowItem.oid);
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
				
					$("#addBtn").click(function() {
						var items = AUIGrid.getCheckedRowItems(myGridID);
						if (items.length == 0) {
							alert("추가 할 부품을 선택하세요.");
							return false;
						}
						var oid = items[0].item.oid;
						var params = new Object();
						params.oid = oid;
						var url = _url("/eco/attach");
						_call(url, params, function(data) {
							opener.<%=callBack%>(data.info);
							self.close();
						}, "POST");
					})
					// 작성자, 수정자 선택바인드
					_selector("state");
					_selector("cat_l");
					_selector("cat_m");
					_selector("company");
					_selector("brand");
					_selector("unit");
					_between("endCreatedDate")
					_between("endModifiedDate")
					_check("latest");
					_check("partType");
					_check("latest");
					_check("attr");
					load();
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