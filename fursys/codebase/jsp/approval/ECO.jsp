<%@page import="platform.echange.eco.entity.ECO"%>
<%@page import="platform.echange.eco.entity.ECODTO"%>
<%@page import="platform.util.ContentUtils"%>
<%@page import="java.util.ArrayList"%>
<%@page import="platform.echange.ecn.entity.ECNDTO"%>
<%@page import="platform.echange.ecn.entity.ECN"%>
<%@page import="platform.util.CommonUtils"%>
<%@page import="wt.doc.WTDocument"%>
<%@page import="platform.doc.entity.DocumentDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String oid = (String) request.getParameter("per");
ECO eco = (ECO) CommonUtils.persistable(oid);
ECODTO dto = new ECODTO(eco);
%>
<table class="view-table obj-view">
	<colgroup>
		<col width="100">
		<col width="800">
		<col width="100">
		<col width="800">
	</colgroup>
	<tr>
		<th>ECO 번호</th>
		<td><%=dto.getNumber()%></td>
		<th>ECO 명</th>
		<td><%=dto.getName()%></td>
	</tr>
	<tr>
		<th>통보유형</th>
		<td><%=dto.getNotiTypeNm()%></td>
		<th>적용시점</th>
		<td><%=dto.getApplyTimeNm()%></td>
	</tr>
	<tr>
<!-- 		<th>예상적용일</th> -->
<%-- 		<td><%=dto.getExpectationTime().toString().substring(0, 10)%></td> --%>
		<th>제품LOT관리</th>
		<td colspan="3"><%=dto.getLotNm()%></td>
	</tr>
	<tr>
		<th>ECO유형</th>
		<td><%=dto.getEcoTypeNm()%></td>
		<th>개발구분</th>
		<td><%=dto.getDevTypeNm()%></td>
	</tr>
	<tr>
		<th>회사</th>
		<td><%=dto.getCompanyNm()%></td>
		<th>브랜드</th>
		<td><%=dto.getBrandNm()%></td>
	</tr>
	<tr>
		<th>변경사유</th>
		<td colspan="3">
			<div class="raonkEditor">
				<script type="text/javascript">
					var kEditorParam = {
						Id : "reason",
						Width : "100%",
						Height : "250px",
						Runtimes : 'html5', // agent, html5
						Mode : 'view'
					};
					new RAONKEditor(kEditorParam);
				</script>
			</div>
		</td>
	</tr>
	<tr>
		<th>변경내용</th>
		<td colspan="3">
			<div class="raonkEditor">
				<script type="text/javascript">
					function edior() {
						var kEditorParam = {
							Id : "description",
							Width : "100%",
							Height : "250px",
							Runtimes : 'html5', // agent, html5
							Mode : 'view'
						};
						new RAONKEditor(kEditorParam);
					}
				</script>
			</div>
		</td>
	</tr>
	<tr>
		<th>작성자</th>
		<td><%=dto.getCreator()%></td>
		<th>작성일자</th>
		<td><%=dto.getCreatedDate()%></td>
	</tr>
</table>

<!-- 첨부 파일 -->
<jsp:include page="/jsp/common/ref-file-view.jsp">
	<jsp:param value="<%=dto.getOid()%>" name="oid" />
</jsp:include>

<!-- 관련 문서 -->
<jsp:include page="/jsp/echange/eco/ref-eco-doc-view.jsp">
	<jsp:param value="<%=dto.getOid()%>" name="oid" />
</jsp:include>

<!-- 관련 부품 -->
<%-- <jsp:include page="/jsp/echange/eco/ref-eco-part-view.jsp"> --%>
<%-- 	<jsp:param value="<%=dto.getOid()%>" name="oid" /> --%>
<%-- </jsp:include> --%>

<!-- 관련 EBOM -->
<jsp:include page="/jsp/echange/eco/ref-eco-ebom-view.jsp">
	<jsp:param value="<%=dto.getOid()%>" name="oid" />
</jsp:include>


<!-- <!-- 관련 MBOM -->
<%-- <jsp:include page="/jsp/echange/eco/ref-eco-mbom-view.jsp"> --%>
<%-- 	<jsp:param value="<%=dto.getOid()%>" name="oid" /> --%>
<%-- </jsp:include> --%>

<script type="text/javascript">
	$(function() {
		$("#tabs").bindTab({
			theme : "AXTabs",
			value : "1",
			overflow : "scroll", /* "visible" */
			options : [ {
				optionValue : "0",
				optionText : "기본정보",
			}, {
				optionValue : "1",
				optionText : "결재객체",
			}, {
				optionValue : "2",
				optionText : "첨부파일",
			}, 
// 			{
// 				optionValue : "3",
// 				optionText : "관련부품",
// 			},
			<%if ("설계".equals(dto.getEcoTypeNm())) {%>	
		{
			optionValue : "4",
			optionText : "PART LIST",
		}, 
		<%} else {%>
		{
			optionValue : "5",
			optionText : "MBOM",
		},
		<%}%> 
			{
				optionValue : "6",
				optionText : "관련문서",
			} ],
			onchange : function(selectedObject, value) {
				if (value == "0") {
					$(".info-view").show();
					$(".app-view").show();
					$(".tabNav").show();
					$(".br1").show();
					$(".obj-view").hide();
					$(".file-view").hide();
					$(".part-view").hide();
					$(".ebom").hide();
					$(".ebom-search").hide();
					$(".mbom").hide();
					$(".mbom-search").hide();
					$("#doc_grid_wrap").hide();
				} else if (value == "1") {
					$(".info-view").hide();
					$(".app-view").hide();
					$(".tabNav").hide();
					$(".br1").hide();
					$(".obj-view").show();
					$(".file-view").hide();
					$(".part-view").hide();
					$(".ebom").hide();
					$(".ebom-search").hide();
					$(".mbom").hide();
					$(".mbom-search").hide();
					$("#doc_grid_wrap").hide();
				} else if (value == "2") {
					$(".info-view").hide();
					$(".app-view").hide();
					$(".tabNav").hide();
					$(".br1").hide();
					$(".obj-view").hide();
					$(".file-view").show();
					$(".part-view").hide();
					$(".ebom").hide();
					$(".ebom-search").hide();
					$(".mbom").hide();
					$(".mbom-search").hide();
					$("#doc_grid_wrap").hide();
				} else if (value == "3") {
					$(".info-view").hide();
					$(".app-view").hide();
					$(".tabNav").hide();
					$(".br1").hide();
					$(".obj-view").hide();
					$(".file-view").hide();
					$(".part-view").show();
					$(".ebom").hide();
					$(".ebom-search").hide();
					$(".mbom").hide();
					$(".mbom-search").hide();
					$("#doc_grid_wrap").hide();
				} else if (value == "4") {
					$(".info-view").hide();
					$(".app-view").hide();
					$(".tabNav").hide();
					$(".br1").hide();
					$(".obj-view").hide();
					$(".file-view").hide();
					$(".part-view").hide();
					$(".ebom").show();
					$(".ebr").show();
					$(".ebom-search").show();
					_selector("ecolor");
					$(".mbom").hide();
					$(".mbom-search").hide();
					$("#doc_grid_wrap").hide();
				} else if (value == "5") {
					$(".info-view").hide();
					$(".app-view").hide();
					$(".tabNav").hide();
					$(".br1").hide();
					$(".obj-view").hide();
					$(".file-view").hide();
					$(".part-view").hide();
					$(".ebom").hide();
					$(".ebom-search").hide();
					$(".mbom").show();
					$(".mbom-search").show();
					$("#doc_grid_wrap").hide();
				} else if (value == "6") {
					$(".info-view").hide();
					$(".app-view").hide();
					$(".tabNav").hide();
					$(".br1").hide();
					$(".obj-view").hide();
					$(".file-view").hide();
					$(".part-view").hide();
					$(".ebom").hide();
					$(".ebom-search").hide();
					$(".mbom").hide();
					$(".mbom-search").hide();
					$("#doc_grid_wrap").show();
					AUIGrid.setGridData("#doc_grid_wrap", <%=dto.getDocJson()%>);
					AUIGrid.resize("#doc_grid_wrap");
				}
			}
		});
		RAONKEDITOR.SetHtmlContents(decodeURIComponent(escape(window.atob("<%=dto.getReason()%>"))), "reason");
		RAONKEDITOR.SetHtmlContents(decodeURIComponent(escape(window.atob("<%=dto.getDescription()%>"))), "description");
	})
</script>