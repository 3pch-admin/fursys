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
String partTypeCd = (String) request.getAttribute("partTypeCd");
String rowId = (String) request.getParameter("rowId");
String poid = (String)request.getParameter("poid");
String callBack = (String)request.getParameter("callBack");
%>
<!-- hidden value -->
<input type="hidden" name="node" value="<%=poid %>">
<div class="header-title">
	<img src="/Windchill/jsp/images/home.png" class="home">
	<span>HOME</span>
	>
	<span>부품관리</span>
	>
	<span>신규부품 등록</span>
</div>

<table class="create-table part-info">
	<colgroup>
		<col width="200">
		<col width="700">
		<col width="200">
		<col width="700">
	</colgroup>
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
			<%
			if ("SET".equals(partTypeCd)) {
			%>
			<label>
				<input name="partType" type="radio" value="ITEM" checked="checked">
				단품
			</label>
			<label>
				<input name="partType" type="radio" value="WIP" disabled="disabled">
				재공 &nbsp;&nbsp;
			</label>
			<label>
				<input name="partType" type="radio" value="MAT" disabled="disabled">
				자재 &nbsp;&nbsp;
			</label>
			<%
			} else if ("ITEM".equals(partTypeCd)) {
			%>
			<label>
				<input name="partType" type="radio" value="ITEM" disabled="disabled">
				단품
			</label>
			<label>
				<input name="partType" type="radio" value="WIP" >
				재공 &nbsp;&nbsp;
			</label>
			<label>
				<input name="partType" type="radio" value="MAT" >
				자재 &nbsp;&nbsp;
			</label>
			<%
			} else if ("MAT".equals(partTypeCd)) {
			%>
			<label>
				<input name="partType" type="radio" value="ITEM" disabled="disabled">
				단품
			</label>
			<label>
				<input name="partType" type="radio" value="WIP" >
				재공 &nbsp;&nbsp;
			</label>
			<label>
				<input name="partType" type="radio" value="MAT" >
				자재 &nbsp;&nbsp;
			</label>
			<%
			} else if ("WIP".equals(partTypeCd)) {
			%>
			<label>
				<input name="partType" type="radio" value="ITEM" disabled="disabled">
				단품
			</label>
			<label>
				<input name="partType" type="radio" value="WIP" >
				재공 &nbsp;&nbsp;
			</label>
			<label>
				<input name="partType" type="radio" value="MAT" >
				자재 &nbsp;&nbsp;
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
				<option value="<%=c.getName()%>"><%=c.getName()%></option>
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
				<option value="<%=b.getName()%>"><%=b.getName()%></option>
				<%
				}
				%>
			</select>
		</td>
	</tr>
<!-- 	<tr> -->
<!-- 		<th>파생부품</th> -->
<!-- 		<td colspan="3"> -->
<!-- 			<input type="text" class="AXInput w60p" readonly="readonly" name="refNumber"> -->
<!-- 			<input type="hidden" readonly="readonly" name="ref"> -->
<!-- 		</td> -->
<!-- 	</tr> -->
<!-- 	<tr> -->
<!-- 		<th>PLM 임시코드</th> -->
<!-- 		<td colspan="3"> -->
<!-- 			<input type="text" class="AXInput w60p" readonly="readonly" name="refNumber"> -->
<!-- 		</td> -->
<!-- 	</tr> -->
<%if("SET" != partTypeCd) {%>
	<tr class="material">
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
	<%
		}
	%>
<!-- 	<tr class="items"> -->
<!-- 		<th>주문품여부</th> -->
<!-- 		<td> -->
<!-- 			<select name="purchase_yn" id="purchase_yn" class="AXSelect w200px"> -->
<!-- 				<option value="">선택</option> -->
<!-- 				<option value="Y">주문품(Y)</option> -->
<!-- 				<option value="N">주문품 아님(N)</option> -->
<!-- 			</select> -->
<!-- 		</td> -->
<!-- 		<th>사용여부</th> -->
<!-- 		<td> -->
<!-- 			<select name="use_type_code" id="use_type_code" class="AXSelect w200px"> -->
<!-- 				<option value="">선택</option> -->
<!-- 				<option value="Y">사용(Y)</option> -->
<!-- 				<option value="N">미사용(N)</option> -->
<!-- 			</select> -->
<!-- 		</td> -->
<!-- 	</tr> -->
</table>

<jsp:include page="/jsp/common/ref-doc-attach.jsp"></jsp:include>

<table class="button-table">
	<tr>
		<td class="right">
			<button type="button" class="saveBtn" data-apply="s">적용</button>
			<button type="button" class="saveBtn" data-apply="n">등록</button>
			<button type="button" id="closeBtn">닫기</button>
		</td>
	</tr>
</table>

<script type="text/javascript">
	$(function() {
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
			} else if ($(this).val() == "WIP") {
				$(".material").show();
				$(".items").hide();
				_selector("standard_code");
			}
		})
		
		$("#closeBtn").click(function() {
			self.close();
		})

		$(".saveBtn").click(function() {
			
			
			if("<%=partTypeCd%>" == "ITEM") {
				if($("input[name=partType]:checked").val() == "ITEM") {
					alert("단품 아래에 단품 추가는 불가능합니다.");
					return false;
				}
			}
			
			if("<%=partTypeCd%>" == "SET") {
				if ($("input[name=partType]:checked").val() == "MAT" 
					||	$("input[name=partType]:checked").val() == "WIP") {
					alert("세트 아래에 자재는 추가가 불가능합니다.");
					return false;
				}
			}

			if (!confirm("등록 하시겠습니까?")) {
				return false;
			}
			var apply = $(this).data("apply");
			var params = _data($("#form"));
			var url = _url("/part/attach");
			_call(url, params, function(data) {
				if (apply == "s") {
					opener.<%=callBack%>(data.node, "<%=rowId%>");
				} else {
					opener.<%=callBack%>(data.node, "<%=rowId%>");
					self.close();
				}
			}, "POST");
		})

		// 		_check("partType");
		$("input[name=partType]").checks();
		_selector("company");
		_selector("brand");
		_selector("cat_l");
		_selector("cat_m");
		_selector("unit");
		_folder("location", "/Default/부품");
		_selector("standard_code");
	})
</script>