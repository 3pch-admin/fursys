<%@page import="platform.util.IBAUtils"%>
<%@page import="platform.util.CommonUtils"%>
<%@page import="platform.util.StringUtils"%>
<%@page import="wt.part.WTPart"%>
<%@page import="platform.part.entity.PartDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
PartDTO dto = (PartDTO) request.getAttribute("dto");
%>
<div class="header-title">
	<img src="/Windchill/jsp/images/home.png" class="home">
	<span>HOME</span>
	>
	<span>부품 관리</span>
	>
	<span>부품 정보</span>
</div>

<div id="tabs"></div>
<br>

<table class="view-table info-view">
	<colgroup>
		<col width="150">
		<col width="500">
		<col width="150">
		<col width="500">
		<col width="*">
	</colgroup>
	<tr>
		<th>부품분류</th>
		<td colspan="4"><%=dto.getLocation()%>
		</td>
		<td rowspan="8" class="center">
			<jsp:include page="/jsp/part/include_thumb.jsp">
				<jsp:param value="<%=dto.getOid()%>" name="oid" />
				<jsp:param value="<%=dto.getEoid()%>" name="eoid" />
			</jsp:include>
		</td>
	</tr>
	<tr>
		<th>부품번호 / 버전</th>
		<td><%=dto.getNumber()%>
			/
			<%=dto.getVersion()%>
		</td>
		<th>부품유형</th>
		<td><%=dto.getPartTypeNm()%>
		</td>
	</tr>
	<tr>
		<th>부품명칭</th>
		<td>
			<%=dto.getPart_name()%>
		</td>
		<th>부품명칭(영문)</th>
		<td><%=dto.getPart_name_en()%></td>
	</tr>
	<tr>
		<th>단위</th>
		<td><%=dto.getUnit()%></td>
		<th>ERP CODE</th>
		<td><%=dto.getErpCode()%></td>
	</tr>
	<tr>
		<th>회사</th>
		<td><%=dto.getCompanyNm()%></td>
		<th>브랜드</th>
		<td><%=dto.getBrandNm()%></td>
	</tr>
	<tr>
		<th>상태</th>
		<td><%=dto.getState()%></td>
		<th>파생부품</th>
		<td>
			<%
			WTPart refPart = null;
			if (StringUtils.isNotNull(dto.getRef())) {
				refPart = (WTPart) CommonUtils.persistable(dto.getRef());
			%>
			<%=IBAUtils.getStringValue(refPart, "PART_NAME")%>
			<%
			} else {
			%>
			&nbsp;
			<%
			}
			%>
		</td>
	</tr>
	<tr>
		<th>작성자</th>
		<td><%=dto.getCreator()%></td>
		<th>작성일자</th>
		<td><%=dto.getCreatedDate()%></td>
	</tr>
	<tr>
		<th>수정자</th>
		<td><%=dto.getModifier()%></td>
		<th>수정일자</th>
		<td><%=dto.getModifiedDate()%></td>
	</tr>
	<!-- 자재 -->
	<tr>
		<th>규격 가로(W)</th>
		<td><%=dto.getPart_width()%></td>
		<th>규격 세로(D)</th>
		<td colspan="3"><%=dto.getPart_depth()%></td>
	</tr>
	<tr>
		<th>규격 높이(H)</th>
		<td colspan="5"><%=dto.getPart_height()%></td>
	</tr>
	<%
	if (dto.getPartTypeNm().equals("자재")) {
	%>
	<tr>
		<th>규격</th>
		<td><%=dto.getStandard_code()%></td>
		<th>가단가</th>
		<td colspan="3"><%=dto.getDummy_unit_price()%>원
		</td>
	</tr>
	<%
	} else {
	%>
	<tr>
		<th>주문품여부</th>
		<td><%=dto.getPurchase_yn()%></td>
		<th>사용여부</th>
		<td colspan=""><%=dto.getUse_type_code()%></td>
	</tr>
	<%
	}
	%>
	<!-- 단품, 세트  -->
</table>

<!-- 속성 파일 -->
<jsp:include page="/jsp/part/part-attr.jsp">
	<jsp:param value="<%=dto.getOid()%>" name="oid" />
</jsp:include>

<!-- 버전 이력 -->
<jsp:include page="/jsp/part/part-history.jsp">
	<jsp:param value="<%=dto.getOid()%>" name="oid" />
</jsp:include>

<!-- EBOM -->
<%-- <jsp:include page="/jsp/part/ref-part-ebom-view.jsp"> --%>
<%-- 	<jsp:param value="<%=dto.getOid()%>" name="oid" /> --%>
<%-- </jsp:include> --%>

<!-- PARTLIST -->
<%-- <jsp:include page="/jsp/part/ref-part-partlist-view.jsp"> --%>
<%-- 	<jsp:param value="<%=dto.getOid()%>" name="oid" /> --%>
<%-- </jsp:include> --%>

<!-- MBOM -->
<%-- <jsp:include page="/jsp/part/ref-part-mbom-view.jsp"> --%>
<%-- 	<jsp:param value="<%=dto.getOid()%>" name="oid" /> --%>
<%-- </jsp:include> --%>

<!-- WTDOC -->
<jsp:include page="/jsp/part/ref-part-doc-view.jsp">
	<jsp:param value="<%=dto.getOid()%>" name="oid" />
</jsp:include>

<table class="button-table">
	<tr>
		<td class="right">
			<button type="button" id="createDocBtn">기타문서등록</button>
			<button type="button" id="modifyBtn">수정</button>
			<button type="button" id="reviseBtn">개정</button>
			<!-- 			<button type="button" id="deleteBtn">삭제</button> -->
			<button type="button" id="closeBtn">닫기</button>
		</td>
	</tr>
</table>
<script type="text/javascript">
	$(function() {

		$("#createDocBtn").click(function() {
			var oid = $(this).data("oid");
			var url = _url("/Windchill/platform/doc/createPartToDoc", oid);
			_popup(url, 800, 800, "n");
		})
		
		$("#thumb").click(function() {
			var oid = $(this).data("oid");
			_openCreoView(oid);
		})

		$("#modifyBtn").click(function() {
			var oid = $("input[name=oid]").val();
			var url = _url("/part/modify", oid);
			document.location.href = url;
		})

		$("#closeBtn").click(function() {
			self.close();
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
				optionText : "도면속성정보",
			}, {
				optionValue : "3",
				optionText : "버전이력",
			}, {
				optionValue : "4",
				optionText : "EBOM",
			}, {
				optionValue : "5",
				optionText : "PART LIST"
			}, {
				optionValue : "6",
				optionText : "MBOM"
			}, {
				optionValue : "7",
				optionText : "문서"
			} ],
			onchange : function(selectedObject, value) {
				if (value == "1") {
					$(".info-view").show();
					$(".attr-view").hide();
					$(".history-view").hide();
					$(".ebom").hide();
					$(".ebom-search").hide();
					$(".ebr").hide();
					$(".pbom").hide();
					$(".pbom-search").hide();
					$(".pbr").hide();
					$(".mbom").hide();
					$(".mbom-search").hide();
					$(".doc-search").hide();
					$(".mbr").hide();
					$(".doc-view").hide();
				} else if (value == "2") {
					$(".info-view").hide();
					$(".attr-view").show();
					$(".history-view").hide();
					$(".ebom").hide();
					$(".ebom-search").hide();
					$(".ebr").hide();
					$(".pbom").hide();
					$(".pbom-search").hide();
					$(".pbr").hide();
					$(".mbom").hide();
					$(".mbom-search").hide();
					$(".mbr").hide();
					$(".doc-view").hide();
				} else if (value == "3") {
					$(".info-view").hide();
					$(".attr-view").hide();
					$(".history-view").show();
					$(".ebom").hide();
					$(".ebom-search").hide();
					$(".ebr").hide();
					$(".pbom").hide();
					$(".pbom-search").hide();
					$(".pbr").hide();
					$(".mbom").hide();
					$(".mbom-search").hide();
					$(".mbr").hide();
					$(".doc-view").hide();
				} else if (value == "4") {
					$(".info-view").hide();
					$(".attr-view").hide();
					$(".history-view").hide();
					$(".ebom").show();
					$(".ebom-search").show();
					$(".ebr").show();
					$(".pbom").hide();
					$(".pbom-search").hide();
					$(".pbr").hide();
					$(".mbom").hide();
					$(".mbom-search").hide();
					$(".mbr").hide();
					$(".doc-view").hide();
				} else if (value == "5") {
					$(".info-view").hide();
					$(".attr-view").hide();
					$(".history-view").hide();
					$(".ebom").hide();
					$(".ebom-search").hide();
					$(".ebr").hide();
					_selector("pcolor");
					$(".pbom").show();
					$(".pbom-search").show();
					$(".pbr").show();
					$(".mbom").hide();
					$(".mbom-search").hide();
					$(".mbr").hide();
					$(".doc-view").hide();
				} else if (value == "6") {
					$(".info-view").hide();
					$(".attr-view").hide();
					$(".history-view").hide();
					$(".ebom").hide();
					$(".ebom-search").hide();
					$(".ebr").hide();
					$(".pbom").hide();
					$(".pbom-search").hide();
					$(".pbr").hide();
					$(".mbom").show();
					$(".mbom-search").show();
					$(".mbr").show();
					_selector("mcolor");
					$(".doc-view").hide();
				}else if (value == "7") {
					$(".info-view").hide();
					$(".attr-view").hide();
					$(".history-view").hide();
					$(".ebom").hide();
					$(".ebom-search").hide();
					$(".ebr").hide();
					$(".pbom").hide();
					$(".pbom-search").hide();
					$(".pbr").hide();
					$(".mbom").hide();
					$(".mbom-search").hide();
					$(".mbr").hide();
					$(".doc-view").show();
				}
			},
		});
	})
</script>