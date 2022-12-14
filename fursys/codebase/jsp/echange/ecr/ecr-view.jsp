<%@ page import="platform.echange.ecr.entity.ECRDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
ECRDTO dto = (ECRDTO) request.getAttribute("dto");
%>
<div class="header-title">
	<img src="/Windchill/jsp/images/home.png" class="home">
	<span>HOME</span>
	>
	<span>설계변경</span>
	>
	<span>ECR 정보</span>
</div>

<div id="tabs"></div>
<br class="ebr">
<table class="view-table info-view">
	<colgroup>
		<col width="120">
		<col width="800">
		<col width="120">
		<col width="800">
	</colgroup>
	<tr>
		<th>ECR 번호</th>
		<td>
			<input type="hidden" name="oid" value="<%=dto.getOid()%>">
			<%=dto.getNumber()%>
		</td>
		<th>ECR 명</th>
		<td><%=dto.getName()%></td>
	</tr>
	<tr>
		<th>요청 유형</th>
		<td><%=dto.getReqType()%></td>
		<th>처리기한</th>
		<td><%=dto.getLimit()%></td>
	</tr>
	<tr>
		<th>회사</th>
		<td><%=dto.getCompany()%></td>
		<th>브랜드</th>
		<td><%=dto.getBrand()%></td>
	</tr>
	<tr>
		<th>요청사유</th>
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
		<th>요청내용</th>
		<td colspan="3">
			<div class="raonkEditor">
				<script type="text/javascript">
						var kEditorParam = {
							Id : "description",
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
		<th>작성자</th>
		<td><%=dto.getCreatorFullName()%></td>
		<th>작성일자</th>
		<td><%=dto.getCreateTimestamp()%></td>
	</tr>
</table>

<jsp:include page="/jsp/common/ref-file-view.jsp">
	<jsp:param value="<%=dto.getOid()%>" name="oid" />
</jsp:include>
<!-- 관련 문서 -->
<jsp:include page="/jsp/echange/ecr/ref-ecr-part-view.jsp"></jsp:include>

<!-- 관련 문서 -->
<jsp:include page="/jsp/echange/ecr/ref-ecr-doc-view.jsp"></jsp:include>

<table class="button-table">
	<tr>
		<td class="right">
			<button type="button" id="modifyBtn">수정</button>
			<button type="button" id="deleteBtn">삭제</button>
			<button type="button" id="closeBtn">닫기</button>
		</td>
	</tr>
</table>
<script type="text/javascript">
		$(function() {

			$("#reviseBtn").click(function() {

				if (!confirm("개정 하시겠습니까?")) {
					return false;
				}

				var params = _data($("#form"));
				var url = _url("/doc/revise");
				_call(url, params, function(data) {
					opener.load();
					self.close();
				}, "POST");
			})

			$("#deleteBtn").click(function() {

				if (!confirm("삭제 하시겠습니까?")) {
					return false;
				}

				var params = _data($("#form"));
				var url = _url("/ecr/delete");
				_call(url, params, function(data) {
					opener.load();
					self.close();
				}, "POST");
			})

			$("#modifyBtn").click(function() {
				var oid = $("input[name=oid]").val();
				var url = _url("/ecr/modify", oid);
				document.location.href = url;
			})

			$(window).resize(function() {
				AUIGrid.resize("#doc_grid_wrap");
				AUIGrid.resize("#part_grid_wrap");
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
					optionText : "첨부파일",
				}, {
					optionValue : "3",
					optionText : "관련부품",
				}, {
					optionValue : "4",
					optionText : "관련문서",
				} ],
				onchange : function(selectedObject, value) {
					if (value == "1") {
						//기본정보
						$(".info-view").show();
// 						$(".ebr").show();
						//첨부파일
						$(".file-view").hide();
						//부품
						$("#part_grid_wrap").hide();
						$(".part-button").hide();
						//문서
						$("#doc_grid_wrap").hide();
						$(".doc-button").hide();
					} else if (value == "2") {
						$(".info-view").hide();
// 						$(".ebr").hide();
						//첨부파일
						$(".file-view").show();
						//부품
						$("#part_grid_wrap").hide();
						$(".part-button").hide();
						//문서
						$("#doc_grid_wrap").hide();
						$(".doc-button").hide();
					} else if (value == "3") {
						$(".info-view").hide();
// 						$(".ebr").hide();
						//첨부파일
						$(".file-view").hide();
						//부품
						$("#part_grid_wrap").show();
						$(".part-button").show();
						AUIGrid.setGridData("#part_grid_wrap", <%=dto.getPartJson()%>);
						AUIGrid.resize("#part_grid_wrap");
						//문서
						$("#doc_grid_wrap").hide();
						$(".doc-button").hide();
					} else if (value == "4") {
						$(".info-view").hide();
// 						$(".ebr").hide();
						//첨부파일
						$(".file-view").hide();
						//부품
						$("#part_grid_wrap").hide();
						$(".part-button").hide();
						//문서
						$("#doc_grid_wrap").show();
						$(".doc-button").show();
						AUIGrid.setGridData("#doc_grid_wrap", <%=dto.getDocJson()%>);
						AUIGrid.resize("#doc_grid_wrap");
					}
				},
			});
			RAONKEDITOR.SetHtmlContents(decodeURIComponent(escape(window.atob("<%=dto.getReason()%>"))), "reason");
			RAONKEDITOR.SetHtmlContents(decodeURIComponent(escape(window.atob("<%=dto.getDescription()%>"))), "description");
	})
</script>