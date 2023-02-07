<%@page import="platform.util.CommonUtils"%>
<%@page import="wt.part.QuantityUnit"%>
<%@page import="platform.part.service.PartHelper"%>
<%@page import="platform.code.entity.BaseCode"%>
<%@page import="java.util.ArrayList"%>
<%@page import="platform.doc.service.DocumentHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
ArrayList<BaseCode> company = (ArrayList<BaseCode>) request.getAttribute("company");
ArrayList<BaseCode> brand = (ArrayList<BaseCode>) request.getAttribute("brand");

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
<body id="container">
	<form id="form">
		<!-- hidden value -->
		<input type="hidden" name="content" id="content">
		<div class="header-title">
			<img src="/Windchill/jsp/images/home.png" class="home">
			<span>HOME</span>
			>
			<span>부품 관리</span>
			>
			<span>부품 등록</span>
		</div>

		<div id="tabs"></div>
		<br>
		<table class="create-table part-info">
			<colgroup>
				<col width="150">
				<col width="600">
				<col width="150">
				<col width="600">
			</colgroup>
<!-- 			<tr> -->
<!-- 				<th>부품분류</th> -->
<!-- 				<td colspan="3"> -->
<%-- 					<input type="text" class="AXInput w70p" readonly="readonly" value="<%=PartHelper.ROOT%>" name="location" id="location"> --%>
<!-- 				</td> -->
<!-- 			</tr> -->
			<tr>
				<th>부품명</th>
				<td>
					<input type="text" class="AXInput w60p" name="part_name">
				</td>
				<th>부품명(영문)</th>
				<td>
					<input type="text" class="AXInput w60p" name="part_name_en">
				</td>
			</tr>
			<tr>
				<th>부품번호 / 버전</th>
				<td>
					<input type="text" readonly="readonly" class="AXInput w60p" placeholder="자동생성">
				</td>
				<th>부품유형</th>
				<td>
					<label>
						<input name="partType" type="radio" value="ITEM" checked="checked">
						<span>단품</span>
					</label>
					&nbsp;
					<label>
						<input name="partType" type="radio" value="WIP">
						<span>재공</span>
					</label>
					&nbsp;
					<label>
						<input name="partType" type="radio" value="MAT">
						<span>자재</span>
					</label>
				</td>
			</tr>
			<tr>
				<th>카테고리_대</th>
				<td>
					<select name="cat_l" id = "cat_l" class="AXSelect w200px">
						<option value="">선택</option>
						<%
						for(BaseCode c : cat_l) {
						%>
							<option value="<%=c.getCode()%>" ><%=c.getName()%></option>
						<%
						}
						%>
					</select>
				</td>
				<th>카테고리_중</th>
				<td>
				<select name="cat_m" id = "cat_m" class="AXSelect w200px">
						<option value="">선택</option>
						<%
						for(BaseCode d : cat_m) {
						%>
							<option value="<%=d.getCode()%>" ><%=d.getName()%></option>
						<%
						}
						%>
					</select>
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
				<th>ERP CODE</th>
				<td>
					<input type="text" class="AXInput w60p" name="erpCode" readonly="readonly">
				</td>
			</tr>
			<tr>
				<th>회사</th>
				<td>
					<select name="company" id="company" class="AXSelect w200px">
						<option value="">선택</option>
						<%
						for (BaseCode c : company) {
						%>
						<option value="<%=c.getCode()%>" <%if (ccode.equals(c.getCode())) {%> selected="selected" <%}%>><%=c.getName()%></option>
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
						<option value="<%=b.getCode()%>" <%if (code.equals(b.getCode())) {%> selected="selected" <%}%>><%=b.getName()%></option>
						<%
						}
						%>
					</select>
				</td>
			</tr>
			<tr>
				<th>파생부품</th>
				<td colspan="3">
					<input type="text" class="AXInput w60p" readonly="readonly" name="refNumber">
					<input type="hidden" readonly="readonly" name="ref">
				</td>
			</tr>
			<!-- // 11 15  -->
<!-- 			<tr> -->
<!-- 				<th>규격 가로(W)</th> -->
<!-- 				<td> -->
<!-- 					<input type="text" class="AXInput w60p" name="part_width"> -->
<!-- 					(mm) -->
<!-- 				</td> -->
<!-- 				<th>규격 세로(D)</th> -->
<!-- 				<td> -->
<!-- 					<input type="text" class="AXInput w60p" name="part_depth"> -->
<!-- 					(mm) -->
<!-- 				</td> -->
<!-- 			</tr> -->
<!-- 			<tr> -->
<!-- 				<th>규격 높이(H)</th> -->
<!-- 				<td colspan="3"> -->
<!-- 					<input type="text" class="AXInput w60p" name="part_height"> -->
<!-- 					(mm) -->
<!-- 				</td> -->
<!-- 			</tr> -->
			<tr class="material">
				<th>규격</th>
				<td>
					<select name="standard_code" class="AXSelect w200px" id="standard_code">
						<option value="">선택</option>
						<option value="M58001">규격</option>
						<option value="M58002">비규격</option>
					</select>
				</td>
				<th>가단가</th>
				<td>
					<input type="text" class="AXInput w70px" name="dummy_unit_price">
					원
				</td>
			</tr>
<!-- 			<tr class="items"> -->
<!-- 				<th>주문품여부</th> -->
<!-- 				<td> -->
<!-- 					<select name="purchase_yn" id="purchase_yn" class="AXSelect w200px"> -->
<!-- 						<option value="">선택</option> -->
<!-- 						<option value="Y">주문품(Y)</option> -->
<!-- 						<option value="N">주문품 아님(N)</option> -->
<!-- 					</select> -->
<!-- 				</td> -->
<!-- 				<th>사용여부</th> -->
<!-- 				<td> -->
<!-- 					<select name="use_type_code" id="use_type_code" class="AXSelect w200px"> -->
<!-- 						<option value="">선택</option> -->
<!-- 						<option value="Y">사용(Y)</option> -->
<!-- 						<option value="N">미사용(N)</option> -->
<!-- 					</select> -->
<!-- 				</td> -->
<!-- 			</tr> -->
		</table>

		<jsp:include page="/jsp/part/ref-doc-attach.jsp"></jsp:include>

		<table class="button-table">
			<tr>
				<td class="right">
					<button type="button" id="saveBtn">등록</button>
					<button type="button" id="closeBtn">닫기</button>
				</td>
			</tr>
		</table>
	</form>

	<script type="text/javascript">
		$(function() {

			$(".items").show();
			$(".material").hide();
			$("input[name=partType]").click(function() {
				if ($(this).val() == "ITEM") {
					$(".items").show();
					$(".material").hide();
					_selector("purchase_yn");
					_selector("use_type_code");
				} else if ($(this).val() == "MAT") {
					$(".material").show();
					$(".items").hide();
					_selector("standard_code");
				}
			})
			$("#closeBtn").click(function() {
				self.close();
			})

			$("#saveBtn").click(function() {
				if (!confirm("등록 하시겠습니까?")) {
					return false;
				}

				var params = _data($("#form"));
				var url = _url("/part/create");
				_call(url, params, function(data) {
					opener.load();
					self.close();
				}, "POST");
			})

			$("#tabs").bindTab({
				theme : "AXTabs",
				value : "1",
				overflow : "scroll", /* "visible" */
				options : [ {
					optionValue : "1",
					optionText : "기본정보",
				}, {
					optionValue : "2",
					optionText : "관련문서",
				} ],
				onchange : function(selectedObject, value) {
					if (value == "1") {
						$("br").show();
						$(".part-info").show();
						$(".doc-button").hide();
						$("#doc_grid_wrap").hide();
// 						AUIGrid.resize("#doc_grid_wrap");
					} else if (value == "2") {
						$("br").hide();
						$(".part-info").hide();
						$(".doc-button").show();
						$("#doc_grid_wrap").show();
						AUIGrid.resize("#doc_grid_wrap");
					}
				},
			});
// 			AUIGrid.resize("#doc_grid_wrap");
			$(window).resize(function() {
				AUIGrid.resize("#doc_grid_wrap"); 
			})
			
// 			_check("partType");
			_selector("standard_code");
			_selector("cat_l");
			_selector("cat_m");
			_selector("company");
			_selector("brand");
			_selector("unit");
			_selector("purchase_yn");
			_selector("use_type_code");
			_folder("location", "/Default/부품");
		})
	</script>
</body>
</html>