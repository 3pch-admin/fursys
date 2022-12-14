<%@page import="platform.echange.ecr.entity.ECRDTO"%>
<%@page import="platform.code.entity.BaseCode"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
ArrayList<BaseCode> company = (ArrayList<BaseCode>) request.getAttribute("company");
ArrayList<BaseCode> brand = (ArrayList<BaseCode>) request.getAttribute("brand");
ArrayList<BaseCode> reqType = (ArrayList<BaseCode>) request.getAttribute("reqType");
ECRDTO dto = (ECRDTO) request.getAttribute("dto");
%>
<!-- hidden value -->
<input type="hidden" name="oid" value="<%=dto.getOid()%>">
<div class="header-title">
	<img src="/Windchill/jsp/images/home.png" class="home">
	<span>HOME</span>
	>
	<span>설계변경</span>
	>
	<span>ECR 수정</span>
</div>
<div id="tabs"></div>
<br class="ebr">
<table class="create-table">
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
			<input type="text" name="number" size="70" class="AXInput w60p" readonly="readonly" value="<%=dto.getNumber()%>">
		</td>
		<th>
			ECR 명
			<font color="red">
				<b>*</b>
			</font>
		</th>
		<td>
			<input type="text" name="name" class="AXInput w60p" autofocus="autofocus" value="<%=dto.getName()%>">
		</td>
	</tr>
	<tr>
		<th>
			요청유형
			<font color="red">
				<b>*</b>
			</font>
		</th>
		<td>
			<select id="reqType" class="AXSelect w100px" name="reqType">
				<option value="">선택</option>
				<%
				for (BaseCode c : reqType) {
				%>
				<option value="<%=c.getName()%>" <%if (c.getName().equals(dto.getReqType())) {%> selected="selected" <%}%>><%=c.getName()%></option>
				<%
				}
				%>
			</select>
		</td>
		<th>
			처리기한
			<font color="red">
				<b>*</b>
			</font>
		</th>
		<td>
			<input type="text" name="limit" id="limit" class="AXInput w100px" value="<%=dto.getLimit()%>">
			<i class="axi axi-close2 axicon"></i>
		</td>
	</tr>
	<tr>
		<th>
			회사
			<font color="red">
				<b>*</b>
			</font>
		</th>
		<td>
			<select id="company" class="AXSelect w100px" name="company">
				<option value="">선택</option>
				<%
				for (BaseCode c : company) {
				%>
				<option value="<%=c.getName()%>" <%if (c.getName().equals(dto.getCompany())) {%> selected="selected" <%}%>><%=c.getName()%></option>
				<%
				}
				%>
			</select>
		</td>
		<th>
			브랜드
			<font color="red">
				<b>*</b>
			</font>
		</th>
		<td>
			<select id="brand" class="AXSelect w100px" name="brand">
				<option value="">선택</option>
				<%
				for (BaseCode b : brand) {
				%>
				<option value="<%=b.getName()%>" <%if (b.getName().equals(dto.getBrand())) {%> selected="selected" <%}%>><%=b.getName()%></option>
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
				<input type="hidden" id="reason" name="reason" value="<%=dto.getReason()%>">
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
				<input type="hidden" id="description" name="description" value="<%=dto.getDescription()%>">
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

	<!-- 관련부품-->
	<jsp:include page="/jsp/common/ref-part-attach.jsp"></jsp:include>
	<!-- 관련문서-->
	<jsp:include page="/jsp/common/ref-doc-attach.jsp"></jsp:include>
	<!-- 	<!-- 결재선 -->
	<%-- 	<jsp:include page="/jsp/approval/register-app.jsp"> --%>
	<%-- 		<jsp:param value="<%=dto.getOid()%>" name="oid" /> --%>
	<%-- 		<jsp:param value="update" name="cmd" /></jsp:include> --%>
</table>

<table class="button-table">
	<tr>
		<td class="right">
			<button type="button" id="modifyBtn">저장</button>
			<button type="button" id="closeBtn">닫기</button>
		</td>
	</tr>
</table>

<script type="text/javascript">
	$(function() {
		$("#backBtn").click(function() {
			history.go(-1);
		})

		$("#closeBtn").click(function() {
			self.close();
		})

		$("#modifyBtn").click(function() {
			$name = $("input[name=name]");
			$reqType = $("select[name=reqType]");
			$company = $("select[name=company]");
			$brand = $("select[name=brand]");
			$limit = $("input[name=limit]");

			if ($name.val() == "") {
				alert("ECR명을 입력하세요.");
				$name.focus();
				return false;
			}
			if ($reqType.val() == "") {
				alert("요청유형을 선택하세요.");
				$reqType.focus();
				return false;
			}
			if ($limit.val() == "") {
				alert("처리기한을 선택하세요.");
				$limit.focus();
				return false;
			}
			if ($company.val() == "") {
				alert("회사를 선택하세요");
				$company.focus();
				return false;
			}
			if ($brand.val() == "") {
				alert("브랜드를 선택하세요.");
				$brand.focus();
				return false;
			}

			if (!confirm("수정 하시겠습니까?")) {
				return false;
			}

			RAONKEDITOR.GetHtmlContents({
				type : 'htmlexwithdoctype',
				callback : function(paramObj) {
					$("#reason").val(btoa(unescape(encodeURIComponent(paramObj.strData))));
				}
			}, 'editor');

			RAONKEDITOR.GetHtmlContents({
				type : 'htmlexwithdoctype',
				callback : function(paramObj) {
					$("#description").val(btoa(unescape(encodeURIComponent(paramObj.strData))));
				}
			}, 'editor');

			var params = _data($("#form"));
			var url = _url("/ecr/modify");
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
				optionText : "관련부품",
			}, {
				optionValue : "3",
				optionText : "관련문서",
			} ],
			onchange : function(selectedObject, value) {
				if (value == "1") {
					// 기본
					$(".create-table").show();
					$(".ebr").show();
					// 부품
					$("#part_grid_wrap").hide();
					AUIGrid.resize("#part_grid_wrap");
					$(".part-button").hide();
					//문서
					$("#doc_grid_wrap").hide();
					AUIGrid.resize("#doc_grid_wrap");
					$(".doc-button").hide();
				} else if (value == "2") {
					// 기본
					$(".create-table").hide();
					$(".ebr").hide();
					// 부품
					$("#part_grid_wrap").show();
					AUIGrid.resize("#part_grid_wrap");
					$(".part-button").show();
					//문서
					$("#doc_grid_wrap").hide();
					AUIGrid.resize("#doc_grid_wrap");
					$(".doc-button").hide();
					// 결재선
				} else if (value == "3") {
					// 기본
					$(".create-table").hide();
					// 부품
					$("#part_grid_wrap").hide();
					AUIGrid.resize("#part_grid_wrap");
					$(".part-button").hide();
					//문서
					$(".ebr").hide();
					$("#doc_grid_wrap").show();
					AUIGrid.resize("#doc_grid_wrap");
					$(".doc-button").show();
					// 결재선
				}
			},
		});
		AUIGrid.setGridData("#doc_grid_wrap",
<%=dto.getDocJson()%>
	);
		AUIGrid.setGridData("#part_grid_wrap",
<%=dto.getPartJson()%>
	);
		_date("limit");
		_selector("reqType");
		_selector("company");
		_selector("brand");
	})

	function RAONKEDITOR_CreationComplete(editorId) {
		$reason = $("#reason");
		$description = $("#description");
		RAONKEDITOR.SetHtmlContents(decodeURIComponent(escape(window.atob($reason.val()))), editorId);
		RAONKEDITOR.SetHtmlContents(decodeURIComponent(escape(window.atob($description.val()))), editorId);
	}
</script>