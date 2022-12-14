<%@page import="platform.util.CommonUtils"%>
<%@page import="wt.doc.WTDocument"%>
<%@page import="platform.doc.entity.DocumentDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String oid = (String) request.getParameter("per");
WTDocument doc = (WTDocument) CommonUtils.persistable(oid);
DocumentDTO dto = new DocumentDTO(doc);
%>
<table class="view-table obj-view">
	<colgroup>
		<col width="100">
		<col width="800">
		<col width="100">
		<col width="800">
	</colgroup>
	<tr>
		<th>저장위치</th>
		<td colspan="3">
			<%=dto.getLocation()%></td>
	</tr>
	<tr>
		<th>문서번호</th>
		<td><%=dto.getNumber()%></td>
		<th>문서명</th>
		<td><%=dto.getName()%></td>
	</tr>
	<tr>
		<th>상태</th>
		<td><%=dto.getState()%></td>
		<th>버전</th>
		<td><%=dto.getVersion()%></td>
	</tr>
	<tr>
		<th>회사</th>
		<td><%=dto.getCompanyNm()%></td>
		<th>브랜드</th>
		<td><%=dto.getBrandNm()%></td>
	</tr>
	<tr>
		<th>내용</th>
		<td colspan="3">
			<div class="raonkEditor">
				<script type="text/javascript">
					var kEditorParam = {
						Id : "editor",
						Width : "100%",
						Height : "350px",
						Runtimes : 'html5', // agent, html5
						Mode : 'view'
					};
					new RAONKEditor(kEditorParam);
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
	<tr>
		<th>수정자</th>
		<td><%=dto.getModifier()%></td>
		<th>수정일자</th>
		<td><%=dto.getModifiedDate()%></td>
	</tr>
</table>
<!-- 첨부 파일 -->
<jsp:include page="/jsp/common/ref-file-view.jsp">
	<jsp:param value="<%=dto.getOid()%>" name="oid" />
</jsp:include>

<!-- 버전 이력 -->
<jsp:include page="/jsp/doc/doc-history.jsp">
	<jsp:param value="<%=dto.getOid()%>" name="oid" />
</jsp:include>

<!-- 관련 부품 -->
<jsp:include page="/jsp/doc/ref-doc-part-view.jsp"></jsp:include>

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
			}, {
				optionValue : "3",
				optionText : "버전이력",
			}, {
				optionValue : "4",
				optionText : "관련부품",
			} ],
			onchange : function(selectedObject, value) {
				if (value == "0") {
					$(".info-view").show();
					$(".app-view").show();
					$(".tabNav").show();
					$(".br1").show();
					$(".obj-view").hide();
					$(".file-view").hide();
					$(".history-view").hide();
					$(".part-view").hide();
					$("#part_grid_wrap").hide();
				} else if (value == "1") {
					$(".info-view").hide();
					$(".app-view").hide();
					$(".tabNav").hide();
					$(".br1").hide();
					$(".obj-view").show();
					$(".file-view").hide();
					$(".history-view").hide();
					$(".part-view").hide();
					$("#part_grid_wrap").hide();
				} else if (value == "2") {
					$(".info-view").hide();
					$(".app-view").hide();
					$(".tabNav").hide();
					$(".br1").hide();
					$(".obj-view").hide();
					$(".file-view").show();
					$(".history-view").hide();
					$(".part-view").hide();
					$("#part_grid_wrap").hide();
				} else if (value == "3") {
					$(".info-view").hide();
					$(".app-view").hide();
					$(".tabNav").hide();
					$(".br1").hide();
					$(".obj-view").hide();
					$(".file-view").hide();
					$(".history-view").show();
					$(".part-view").hide();
					$("#part_grid_wrap").hide();
				} else if (value == "4") {
					$(".info-view").hide();
					$(".app-view").hide();
					$(".tabNav").hide();
					$(".br1").hide();
					$(".obj-view").hide();
					$(".file-view").hide();
					$(".history-view").hide();
					$("#part_grid_wrap").show();
					AUIGrid.resize("#part_grid_wrap");
				}
			},
		});
		
		$(window).resize(function() {
			AUIGrid.resize("#part_grid_wrap");
		})
		RAONKEDITOR.SetHtmlContents(decodeURIComponent(escape(window.atob("<%=dto.getContent()%>"))), "editor");
	})
</script>