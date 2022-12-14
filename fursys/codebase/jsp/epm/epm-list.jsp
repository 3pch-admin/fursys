<%@page import="platform.util.CommonUtils"%>
<%@page import="java.util.ArrayList"%>
<%@page import="platform.code.entity.BaseCode"%>
<%@page import="wt.part.QuantityUnit"%>
<%@page import="platform.part.service.PartHelper"%>
<%@page import="platform.doc.service.DocumentHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
ArrayList<BaseCode> brand = (ArrayList<BaseCode>) request.getAttribute("brand");
ArrayList<BaseCode> company = (ArrayList<BaseCode>) request.getAttribute("company");
ArrayList<BaseCode> cat_l = (ArrayList<BaseCode>) request.getAttribute("cat_l");
ArrayList<BaseCode> cat_m = (ArrayList<BaseCode>) request.getAttribute("cat_m");
QuantityUnit[] units = (QuantityUnit[]) request.getAttribute("units");
String code = CommonUtils.getSessionBrand();
String ccode = CommonUtils.getSessionCompany();
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
<body onload="load();" id="container">
	<form id="form">
		<input type="hidden" name="sessionid" id="sessionid">
		<input type="hidden" name="tpage" id="tpage">
		<input type="hidden" name="location" id="location">
		<div class="header-title">
			<img src="/Windchill/jsp/images/home.png" class="home">
			<span>HOME</span>
			>
			<span>도면 조회 및 배포</span>
			>
			<span>검색 도면 조회</span>
		</div>
		<table id="wrap-table">
			<colgroup>
				<!-- 				<col width="200"> -->
				<!-- 				<col width="10"> -->
				<col width="*">
			</colgroup>
			<tr>
				<!-- 				<td valign="top" class="tree-border"> -->
				<%-- 					<jsp:include page="/jsp/common/folder-tree.jsp"> --%>
				<%-- 						<jsp:param value="<%=PartHelper.ROOT%>" name="location" /> --%>
				<%-- 					</jsp:include></td> --%>
				<!-- 				<td>&nbsp;</td> -->
				<td>
					<table class="search-table top-color">
						<colgroup>
							<col width="150">
							<col width="*">
							<col width="150">
							<col width="*">
							<col width="150">
							<col width="*">
						</colgroup>
						<!-- 						<tr> -->
						<!-- 							<th>부품분류</th> -->
						<!-- 							<td colspan="5"> -->
						<%-- 								<input type="text" class="AXInput w80p" name="location" id="location" value="<%=PartHelper.ROOT%>" readonly="readonly" id="location"> --%>
						<!-- 							</td> -->
						<!-- 						</tr> -->
						<tr>
							<th>품목명칭(PART_NAME)</th>
							<td>
								<input type="text" name="name" class="AXInput w70p">
							</td>
							<th>부품번호(ERP_CODE)</th>
							<td>
								<input type="text" name="erpCode" class="AXInput w70p">
							</td>
							<th>CAD 파일명</th>
							<td>
								<input type="text" name="number" class="AXInput w70p">
							</td>
						</tr>
						<tr>
							<th>CAD 유형</th>
							<td>
								<select name="docType" id="docType" class="AXSelect w200px">
									<option value="">선택</option>
									<option value="CADDRAWING">드로잉(2D)</option>
									<option value="CADASSEMBLY">어셈블리(3D)</option>
									<option value="CADCOMPONENT">단품(3D)</option>
								</select>
							</td>
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
							<td>
								<label>
									<input type="radio" name="partType" value="ALL" checked="checked">
									<span>전체</span>
								</label>
								&nbsp;
								<label>
									<input type="radio" name="partType" value="SET">
									<span>세트</span>
								</label>
								&nbsp;
								<label>
									<input type="radio" name="partType" value="ITEM">
									<span>단품</span>
								</label>
								&nbsp;
								<label>
									<input type="radio" name="partType" value="MAT">
									<span>자재</span>
								</label>
							</td>
						</tr>
						<tr>
							<th>상태</th>
							<td>
								<select name="state" id="state" class="AXSelect w200px">
									<option value="">선택</option>
									<option value="INWORK">작업 중</option>
									<option value="UNDERAPPROVAL">승인 중</option>
									<option value="RELEASED">릴리즈됨</option>
									<option value="RETURN">반려됨</option>
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
							<th>작성일자</th>
							<td>
								<input type="text" class="AXInput w100px" name="startCreatedDate" id="startCreatedDate" maxlength="8">
								~
								<input type="text" class="AXInput w100px" name="endCreatedDate" id="endCreatedDate" data-start="startCreatedDate" maxlength="8">
								<i class="axi axi-close2 axicon clearBetween" data-target="CreatedDate" ></i>
							</td>
						</tr>
					</table>

					<table class="button-table">
						<tr>
							<!-- 							<td class="left"> -->
							<!-- 								<button type="button" id="createBtn">등록</button> -->
							<!-- 							</td> -->
							<td class="right">
								<button type="button" id="excelBtn">엑셀</button>
								<button type="button" id="searchBtn">조회</button>
							</td>
						</tr>
					</table>

					<div id="grid_wrap" style="height: 645px;"></div>
					<div id="grid_paging" class="aui-grid-paging-panel my-grid-paging-panel"></div>
					<script type="text/javascript">
						var myGridID;
						var totalRowCount;
						var rowCount = 30; // 1페이지에서 보여줄 행 수
						var pageButtonCount = 10; // 페이지 네비게이션에서 보여줄 페이지의 수
						var currentPage = 1; // 현재 페이지
						var totalPage;
						var columnLayout = [ {
							dataField : "no",
							headerText : "번호",
							dataType : "string",
							width : 40
						}, {
							headerText : "",
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
							dataField : "",
							headerText : "",
							dataType : "string",
							width : 40,
							renderer : {
								type : "IconRenderer",
								iconWidth : 30,
								iconHeight : 22,
								iconFunction : function(rowIndex, columnIndex, value, item) {
									return item.t;
								},
								onClick : function(event) {
									var item = event.item;

									if (item.t.indexOf("no-view.png") <= -1) {
										_openCreoView(item.oid);
									}
								}
							}
						}, {
							dataField : "erpCode",
							headerText : "ERP CODE",
							dataType : "string",
							width : 250,
							style : "left indent10"
						}, {
							dataField : "name",
							headerText : "품목명칭(PART_NAME)",
							dataType : "string",
							width : 350,
							style : "left indent10"
						}, {
							dataField : "number",
							headerText : "CAD 파일명",
							dataType : "string",
							style : "left indent10"
						}, {
							dataField : "version",
							headerText : "버전",
							dataType : "string",
							width : 60
						}, {
							dataField : "creator",
							headerText : "작성자",
							dataType : "string",
							width : 80
						}, {
							dataField : "createdDate",
							headerText : "작성일자",
							dataType : "date",
							formatString : "yyyy/mm/dd",
							width : 100
						}, {
							dataField : "state",
							headerText : "상태",
							dataType : "string",
							width : 130
						}, {
							dataField : "brandNm",
							headerText : "브랜드",
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
							showRowNumColumn : false,
							enableFilter : true,
							useContextMenu : true,
						};
						myGridID = AUIGrid.create("#grid_wrap", columnLayout, auiGridProps);
						createPagingNavigator(1);

						// 페이징 네비게이터를 동적 생성합니다.
						function createPagingNavigator(goPage) {
							var retStr = "";
							var prevPage = parseInt((goPage - 1) / pageButtonCount) * pageButtonCount;
							var nextPage = ((parseInt((goPage - 1) / pageButtonCount)) * pageButtonCount) + pageButtonCount + 1;
							prevPage = Math.max(0, prevPage);
							nextPage = Math.min(nextPage, totalPage);
							// 처음
							retStr += "<a href='javascript:moveToPage(1)'><span class='aui-grid-paging-number aui-grid-paging-first'>first</span></a>";

							// 이전
							retStr += "<a href='javascript:moveToPage(" + Math.max(1, prevPage) + ")'><span class='aui-grid-paging-number aui-grid-paging-prev'>prev</span></a>";

							for (var i = (prevPage + 1), len = (pageButtonCount + prevPage); i <= len; i++) {
								if (goPage == i) {
									retStr += "<span class='aui-grid-paging-number aui-grid-paging-number-selected'>" + i + "</span>";
								} else {
									retStr += "<a href='javascript:moveToPage(" + i + ")'><span class='aui-grid-paging-number'>";
									retStr += i;
									retStr += "</span></a>";
								}

								if (i >= totalPage) {
									break;
								}

							}

							// 다음
							retStr += "<a href='javascript:moveToPage(" + nextPage + ")'><span class='aui-grid-paging-number aui-grid-paging-next'>next</span></a>";

							// 마지막
							retStr += "<a href='javascript:moveToPage(" + totalPage + ")'><span class='aui-grid-paging-number aui-grid-paging-last'>last</span></a>";

							document.getElementById("grid_paging").innerHTML = retStr;
						}

						function load() {
							var params = _data($("#form"));
							var url = _url("/epm/list");
							AUIGrid.showAjaxLoader(myGridID);
							_call(url, params, function(data) {
								totalRowCount = data.total;
								totalPage = Math.ceil(totalRowCount / data.pageSize);
								$("input[name=sessionid").val(data.sessionid);
								createPagingNavigator(data.curPage);
								AUIGrid.removeAjaxLoader(myGridID);
								AUIGrid.setGridData(myGridID, data.list);
							}, "POST");
						}

						function moveToPage(goPage) {
							createPagingNavigator(goPage);
							currentPage = goPage;
							$("input[name=tpage").val(goPage);
							load();
						}

						AUIGrid.bind(myGridID, "cellClick", function(event) {
							if (event.dataField == "name" || event.dataField == "number") {
								var rowItem = event.item;
								var url = _url("/part/view", rowItem.oid);
								_popup(url, 1400, 500, "n");
							} else if (event.dataField == "thumb") {
								_openCreoView(event.item.eoid);
							}
						});

						// jquery 
						$(function() {

							$("#createBtn").click(function() {
								var url = _url("/part/create");
								_popup(url, 1400, 400, "n");
							})

							$("#searchBtn").click(function() {
								currentPage = 1;
								$("input[name=tpage").val(1);
								$("input[name=sessionid").val(0);
								load();
							})
							$("#excelBtn").click(function() {
								_excel(myGridID, "부품", []);
							})
							// 작성자, 수정자 선택바인드
							_folder("location", "/Default/부품");
							_selector("state");
							_selector("docType");
							_selector("brand");
							_between("endCreatedDate")
							_check("latest");
							_clearBetween("clearBetween");							
							AUIGrid.resize("#grid_wrap");
							_check("partType");
						}).keypress(function(e) {
							if (e.keyCode == 13) {
								currentPage = 1;
								$("input[name=tpage").val(1);
								$("input[name=sessionid").val(0);
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
	</form>
</body>
</html>