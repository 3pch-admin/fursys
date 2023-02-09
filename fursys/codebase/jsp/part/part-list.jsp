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
			<span>부품 관리</span>
			>
			<span>부품 조회</span>
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
							<col width="100">
							<col width="*">
							<col width="100">
							<col width="*">
							<col width="100">
							<col width="*">
						</colgroup>
						<!-- 						<tr> -->
						<!-- 							<th>부품분류</th> -->
						<!-- 							<td colspan="5"> -->
						<%-- 								<input type="text" class="AXInput w80p" name="location" id="location" value="<%=PartHelper.ROOT%>" readonly="readonly" id="location"> --%>
						<!-- 							</td> -->
						<!-- 						</tr> -->
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
									최신버전
								</label>
								&nbsp;
								<label>
									<input type="radio" name="latest" value="false">
									모든버전
								</label>
							</td>
							<th>부품유형</th>
							<td colspan="3">
								<label>
									<input type="radio" name="partType" value="ALL" checked="checked">
									전체
								</label>
								&nbsp;
								<label>
									<input type="radio" name="partType" value="SET">
									세트
								</label>
								&nbsp;
								<label>
									<input type="radio" name="partType" value="ITEM">
									단품
								</label>
								&nbsp;
								<label>
									<input type="radio" name="partType" value="WIP">
									재공
								</label>
								&nbsp;
								<label>
									<input type="radio" name="partType" value="MAT">
									자재
								</label>
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
									<option value="<%=c.getCode()%>"><%=c.getName()%></option>
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
									<option value="<%=c.getCode()%>"><%=c.getName()%></option>
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
									<option value="UNDERAPPROVAL">승인 중</option>
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
									자재속성
								</label>
								&nbsp;
								<label>
									<input type="radio" name="attr" value="false">
									설계속성(공통)
								</label>
								&nbsp;
								<label>
									<input type="radio" name="attr" value="hide">
									숨김
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
					<br class="br">

					<table class="search-table top-color" id=attrTable>
						<colgroup>
							<col width="100">
							<col width="*">
							<col width="100">
						</colgroup>
						<tr>
							<th>규격구분</th>
							<td>
								<select id="standard_code" class="AXSelect w200px" id="standard_code">
									<option value="">선택</option>
								</select>
							</td>
							<th>가단가</th>
							<td>
								<input type="text" name="dummy_unit_price" id="dummy_unit_price" class="AXInput w60p">
								(원)
							</td>
						</tr>
					</table>

					<table class="search-table top-color" id="attrTable2">
						<colgroup>
							<col width="100">
							<col width="*">
							<col width="100">
							<col width="*">
							<col width="100">
							<col width="*">
						</colgroup>
						<tr>
							<th>재질정보</th>
							<td>
								<input type="text" name="material" id="material" class="AXInput w60p">
							</td>
							<th>가공정보</th>
							<td>
								<input type="text" name="process" id="process" class="AXInput w60p">
							</td>
							<th>표면처리</th>
							<td>
								<input type="text" name="finish" id="finish" class="AXInput w60p">
							</td>
						</tr>
						<tr>
							<th>무늬결방향</th>
							<td>
								<input type="text" name="dtWoodGrain" id="dtWoodGrain" class="AXInput w60p">
							</td>
							<th>조립철물여부</th>
							<td>
								<input type="text" name="packType" id="packType" class="AXInput w60p">
							</td>
							<th>G코드생성여부</th>
							<td>
								<input type="text" name="imCamGcode" id="imCamGcode" class="AXInput w60p">
							</td>
						</tr>
					</table>

					<table class="button-table">
						<tr>
							<td class="left">
							</td>
							<td class="right">
								<button type="button" id="createBtn">등록</button>
								<button type="button" id="excelBtn">엑셀</button>
								<button type="button" id="searchBtn">조회</button>
							</td>
						</tr>
					</table>

					<div id="grid_wrap" style="height: 565px;"></div>
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
							dataField : "thumb",
							headerText : "3D",
							dataType : "string",
							width : 40,
							renderer : {
								type : "IconRenderer",
								iconWidth : 30,
								iconHeight : 22,
								iconFunction : function(rowIndex, columnIndex, value, item) {
									return item._3d;
								},
								onClick : function(event) {
									var item = event.item;

									if (item._3d.indexOf("no-view.png") <= -1) {
										_openCreoView(item.eoid);
									}
								}
							}
						}, {
							// 							dataField : "_2d",
							headerText : "2D",
							dataType : "string",
							width : 40,
							renderer : {
								type : "IconRenderer",
								iconWidth : 30,
								iconHeight : 22,
								iconFunction : function(rowIndex, columnIndex, value, item) {
									return item._2d;
								},
								onClick : function(event) {
									var item = event.item;
									if (item._2d.indexOf("no-view.png") <= -1) {
										_openCreoView(item.eoid2d);
									}
								}
							}
						}, {
							dataField : "partType",
							headerText : "부품유형",
							dataType : "string",
							width : 80
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
							width : 60
						}, {
							dataField : "ref",
							headerText : "CAD연계",
							dataType : "string",
							width : 80
						}, {
							dataField : "derived",
							headerText : "파생여부",
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
							dataField : "modifier",
							headerText : "수정자",
							dataType : "string",
							width : 80
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
							width : 130
						}, {
							dataField : "erpCode",
							headerText : "ERP CODE",
							dataType : "string",
							width : 120
						}, {
							dataField : "companyNm",
							headerText : "회사",
							dataType : "string",
							width : 100
						}, {
							dataField : "brandNm",
							headerText : "브랜드",
							dataType : "string",
							width : 100
						}, {
							dataField : "unit",
							headerText : "단위",
							dataType : "string",
							width : 100
						// 						}, {
						// 11 15 리뷰
						// 							dataField : "order",
						// 							headerText : "주문품여부",
						// 							dataType : "string",
						// 							width : 100
						// 						}, {
						// 							dataField : "use",
						// 							headerText : "사용여부",
						// 							dataType : "string",
						// 							width : 100
						// 						}, {
						// 							dataField : "w",
						// 							headerText : "규격가로(W)",
						// 							dataType : "string",
						// 							width : 100
						// 						}, {
						// 							dataField : "d",
						// 							headerText : "규격세로(D)",
						// 							dataType : "string",
						// 							width : 100
						// 						}, {
						// 							dataField : "h",
						// 							headerText : "사용높이(H)",
						// 							dataType : "string",
						// 							width : 100
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
							var url = _url("/part/list");
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
								_popup(url, 1300, 600, "n");
							} else if (event.dataField == "thumb") {
								_openCreoView(event.item.eoid);
							}
						});

						// jquery 
						$(function() {

							$("#createBtn").click(function() {
								var url = _url("/part/create");
								_popup(url, 1200, 600, "n");
							})

							$(".br").hide();
							$("#attrTable").hide();
							$("#attrTable2").hide();
							$("input[name=attr]").click(function() {
								if ($(this).val() == "true") {
									$("#attrTable").show();
									$("#attrTable2").hide();
									_selector("purchase_yn");
									_selector("standard_code");
									$(".br").show();
									AUIGrid.resize("#grid_wrap");
								} else if ($(this).val() == "false") {
									$("#attrTable").hide();
									$("#attrTable2").show();
									$(".br").show();
									AUIGrid.resize("#grid_wrap");
								} else if ($(this).val() == "hide") {
									$("#attrTable").hide();
									$("#attrTable2").hide();
									$(".br").hide();
									AUIGrid.resize("#grid_wrap");
								}
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
							_selector("cat_l");
							_selector("cat_m");
							_selector("company");
							_selector("brand");
							_selector("unit");
							_between("endCreatedDate")
							_between("endModifiedDate")
							$("input[name=latest]").checks();
							$("input[name=partType]").checks();
							$("input[name=attr]").checks();
							AUIGrid.resize("#grid_wrap");
							$("input[name=name]").focus();
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