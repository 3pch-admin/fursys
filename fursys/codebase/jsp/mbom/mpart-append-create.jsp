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
%>
<!-- hidden value -->
<input type="hidden" name="content" id="content">
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
		<col width="800">
		<col width="200">
		<col width="800">
	</colgroup>
	<tr>
		<th>부품분류</th>
		<td colspan="3">
			<input type="text" class="AXInput w70p" readonly="readonly" value="<%=PartHelper.ROOT%>" name="location" id="location">
		</td>
	</tr>
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
				<input name="partType" type="radio" value="MAT" checked="checked">
				자재 &nbsp;&nbsp;
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
		<th>규격 가로(W)</th>
		<td>
			<input type="text" class="AXInput w60p" name="part_width">
			(mm)
		</td>
		<th>규격 세로(D)</th>
		<td>
			<input type="text" class="AXInput w60p" name="part_depth">
			(mm)
		</td>
	</tr>
	<tr>
		<th>규격 높이(H)</th>
		<td colspan="3">
			<input type="text" class="AXInput w60p" name="part_height">
			(mm)
		</td>
	</tr>
	<tr>
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
			
			
			if (!confirm("등록 하시겠습니까?")) {
				return false;
			}
			var apply = $(this).data("apply");
			var params = _data($("#form"));
			var url = _url("/part/attach");
			_call(url, params, function(data) {
				if (apply == "s") {
					opener.append(data.node);
				} else {
					opener.append(data.node);
					self.close();
				}
			}, "POST");
		})

		$("input[name=partType]").checks();
		// 		_check("partType");
		_selector("company");
		_selector("brand");
		_selector("unit");
		_folder("location", "/Default/부품");
	})
</script>