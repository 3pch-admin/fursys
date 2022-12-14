<%@ page import="platform.echange.eco.entity.ECODTO" %>
<%@ page import= "platform.code.entity.BaseCode" %>
<%@ page import= "java.util.ArrayList" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
ECODTO dto = (ECODTO) request.getAttribute("dto");
ArrayList<BaseCode> company = (ArrayList<BaseCode>) request.getAttribute("company");
ArrayList<BaseCode> brand = (ArrayList<BaseCode>) request.getAttribute("brand");
ArrayList<BaseCode> notiType = (ArrayList<BaseCode>) request.getAttribute("notiTypes");
ArrayList<BaseCode> applyTime = (ArrayList<BaseCode>) request.getAttribute("applyTimes");
ArrayList<BaseCode> lots = (ArrayList<BaseCode>) request.getAttribute("lots");
ArrayList<BaseCode> ecoTypes = (ArrayList<BaseCode>) request.getAttribute("ecoTypes");
ArrayList<BaseCode> devTypes = (ArrayList<BaseCode>) request.getAttribute("devType");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!-- hidden value -->
	<input type="hidden" name="oid" value="<%=dto.getOid()%>">
	<div class="header-title">
		<img src="/Windchill/jsp/images/home.png" class="home">
		<span>HOME</span>
		>
		<span>설계변경</span>
		>
		<span>ECO 수정</span>
	</div>
	<table class="createTable">
		<colgroup>
			<col width="120">
			<col width="800">
			<col width="120">
			<col width="800">
		</colgroup>
		<tr>
			<th>관련 ECO</th>
			<td colspan="3">
				<!-- hidden value -->
				<input type="text" name="ecr" size="70" class="textInput" readonly="readonly">
				<img src="/Windchill/jsp/images/search.gif" class="search20" id="fsearch">
				<img src="/Windchill/jsp/images/delete.png" class="delete20" data-t="location">
			</td>
		</tr>
		<tr>
			<th>ECO 번호</th>
			<td>
				<input type="hidden" name="oid" value="<%=dto.oid%>">
				<input type="text" name="" size="70" class="textInput" readonly="readonly" value="<%=dto.number%>">
			</td>
			<th>
				ECO 명
				<span class="imp">*</span>
			</th>
			<td>
				<input type="text" name="name" size="70" class="textInput" autofocus="autofocus" value="<%=dto.name%>">
			</td>
		</tr>
		<tr>
			<th>
				통보유형
				<span class="imp">*</span>
			</th>
			<td>
				<select id="notiType" name="notiType">
					<option value="">선택</option>
					<%
					for (BaseCode c : notiType) {
					%>
					<option value="<%=c.getName()%>" <%if (c.getName().equals(dto.notiTypes)) {%> selected="selected" <%}%>><%=c.getName()%></option>
					<%
					}
					%>
				</select>
			</td>
			<th>
				적용시점
				<span class="imp">*</span>
			</th>
			<td>
				<select id="applyTime" name="applyTime">
					<option value="">선택</option>
					<%
					for (BaseCode b : applyTime) {
					%>
					<option value="<%=b.getName()%>" <%if (b.getName().equals(dto.applyTime)) {%> selected="selected" <%}%>><%=b.getName()%></option>
					<%
					}
					%>
				</select>
			</td>
		</tr>
		<tr>
			<th>
				예상적용일
				<span class="imp">*</span>
			</th>
			<td>
				<input type="text" class="textInput" readonly="readonly" name="expectationTime" id="expectationTime" value="<%=dto.expectationTime%>">
			</td>
			<th>
				제품LOT관리
				<span class="imp">*</span>
			</th>
			<td>
				<select id="lot" name="lot">
					<option value="">선택</option>
					<%
					for (BaseCode b : lots) {
					%>
					<option value="<%=b.getName()%>" <%if (b.getName().equals(dto.lot)) {%> selected="selected" <%}%>><%=b.getName()%></option>
					<%
					}
					%>
				</select>
			</td>
		</tr>
		<tr>
			<th>
				ECO유형
				<span class="imp">*</span>
			</th>
			<td>
				<select id="ecoType" name="ecoType" disabled="disabled">
					<option value="">선택</option>
					<%
					for (BaseCode c : ecoTypes) {
					%>
					<option value="<%=c.getName()%>" <%if (c.getName().equals(dto.ecoType)) {%> selected="selected" <%}%>><%=c.getName()%></option>
					<%
					}
					%>
				</select>
			</td>
			<th>
				개발구분
				<span class="imp">*</span>
			</th>
			<td>
				<select id="devType" name="devType">
					<option value="">선택</option>
					<%
					for (BaseCode b : devTypes) {
					%>
					<option value="<%=b.getName()%>" <%if (b.getName().equals(dto.devType)) {%> selected="selected" <%}%>><%=b.getName()%></option>
					<%
					}
					%>
				</select>
			</td>
		</tr>
		<tr>
			<th>
				회사
				<span class="imp">*</span>
			</th>
			<td>
				<select id="company" name="company">
					<option value="">선택</option>
					<%
					for (BaseCode c : company) {
					%>
					<option value="<%=c.getName()%>" <%if (c.getName().equals(dto.company)) {%> selected="selected" <%}%>><%=c.getName()%></option>
					<%
					}
					%>
				</select>
			</td>
			<th>
				브랜드
				<span class="imp">*</span>
			</th>
			<td>
				<select id="brand" name="brand">
					<option value="">선택</option>
					<%
					for (BaseCode b : brand) {
					%>
					<option value="<%=b.getName()%>" <%if (b.getName().equals(dto.brand)) {%> selected="selected" <%}%>><%=b.getName()%></option>
					<%
					}
					%>
				</select>
			</td>
		</tr>
		<tr>
			<th>요청사유</th>
			<td colspan="3">
				<div class="raonkEditor">
					<input type="hidden" id="reason" name="reason" value="<%=dto.reason%>">
					<script type="text/javascript">
									var kEditorParam = {
										Id : "reason",
										Width : "100%",
										Height : "250px",
										FocusInitObjId : "name",
										Runtimes : 'html5' // agent, html5
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
					<input type="hidden" id="description" name="description" value="<%=dto.description%>">
					<script type="text/javascript">
									var kEditorParam = {
										Id : "description",
										Width : "100%",
										Height : "250px",
										FocusInitObjId : "name",
										Runtimes : 'html5' // agent, html5
									};
									new RAONKEditor(kEditorParam);
								</script>
				</div>
			</td>
		</tr>
		<tr>
			<th>첨부파일</th>
			<td colspan="3">
				<div class="AXUpload5" id="secondary_layer"></div>
				<div class="AXUpload5QueueBox_list" id="uploadQueueBox"></div>
				<script type="text/javascript">
								upload.init("<%=dto.getOid()%>", "s");
				</script>
			</td>
		</tr>

	<!-- 관련부품 -->
	<jsp:include page="/jsp/common/ref-part-attach.jsp">
		<jsp:param value="<%=dto.getOid()%>" name="oid" />
		<jsp:param value="update" name="cmd" /></jsp:include>
	<!-- 관련문서 -->
	<jsp:include page="/jsp/common/ref-doc-attach.jsp">
		<jsp:param value="<%=dto.getOid()%>" name="oid" />
		<jsp:param value="update" name="cmd" /></jsp:include>
	<!-- 결재선 -->
	<jsp:include page="/jsp/approval/register-app.jsp">
		<jsp:param value="<%=dto.getOid()%>" name="oid" />
		<jsp:param value="update" name="cmd" /></jsp:include>
</table>

	<table class="button-table">
	<tr>
		<td class="right">
			<input type="button" id="modify" value="수정">
			<input type="button" id="close" value="닫기">
		</td>
	</tr>
</table>



</body>
</html>