<%@page import="wt.part.QuantityUnit"%>
<%@page import="platform.part.service.PartHelper"%>
<%@page import="platform.code.entity.BaseCode"%>
<%@page import="java.util.ArrayList"%>
<%@page import="platform.doc.service.DocumentHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
ArrayList<BaseCode> company = (ArrayList<BaseCode>) request.getAttribute("company");
ArrayList<BaseCode> brand = (ArrayList<BaseCode>) request.getAttribute("brand");
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
	<span>최상위 부품 등록</span>
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
			<input type="text" class="AXInput w60p" name="part_name" autofocus="autofocus">
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
				단품
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
	<tr>
		<th>파생부품</th>
		<td colspan="3">
			<input type="text" class="AXInput w60p" readonly="readonly" name="refNumber">
			<input type="hidden" readonly="readonly" name="ref">
		</td>
	</tr>
	<tr>
		<th>PLM 임시코드</th>
		<td colspan="3">
			<input type="text" class="AXInput w60p" readonly="readonly" name="refNumber">
		</td>
	</tr>
	<tr>
		<th>주문품여부</th>
		<td>
			<select name="purchase_yn" id="purchase_yn" class="AXSelect w200px">
				<option value="">선택</option>
				<option value="Y">주문품(Y)</option>
				<option value="N">주문품 아님(N)</option>
			</select>
		</td>
		<th>사용여부</th>
		<td>
			<select name="use_type_code" id="use_type_code" class="AXSelect w200px">
				<option value="">선택</option>
				<option value="Y">사용(Y)</option>
				<option value="N">미사용(N)</option>
			</select>
		</td>
	</tr>
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
				if ($("input[name=partType]:checked").val() == "MAT") {
					alert("세트 아래에 자재는 추가가 불가능합니다.");
					return false;
				}
			}

			if (!confirm("등록 하시겠습니까?")) {
				return false;
			}
			var apply = $(this).data("apply");
			var params = _data($("#form"));
			console.log(params);
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

		$("input[name=erpCode]").click(function() {
			var url = _url("/erp/getErpCode?callBack=setErpCode");
			_popup(url, 1200, 600, "n");
		})

		$("input[name=partType]").checks();
		_selector("company");
		_selector("brand");
		_selector("unit");
		_selector("purchase_yn");
		_selector("use_type_code");
		_selector("standard_code");
		_folder("location", "/Default/부품");
	})

	function setErpCode(code) {
		$("input[name=erpCode]").val(code);
	}
</script>