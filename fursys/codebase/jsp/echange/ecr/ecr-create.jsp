<%@page import="platform.util.CommonUtils"%>
<%@page import="platform.code.entity.BaseCode"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
ArrayList<BaseCode> company = (ArrayList<BaseCode>) request.getAttribute("company");
ArrayList<BaseCode> brand = (ArrayList<BaseCode>) request.getAttribute("brand");
ArrayList<BaseCode> reqType = (ArrayList<BaseCode>) request.getAttribute("reqType");
String code = CommonUtils.getSessionBrand();
String ccode = CommonUtils.getSessionCompany();
%>
<!-- hidden value -->
<input type="hidden" name="content" id="content">
<div class="header-title">
	<img src="/Windchill/jsp/images/home.png" class="home">
	<span>HOME</span>
	>
	<span>설계변경</span>
	>
	<span>ECR 등록</span>
</div>

<div id="tabs"></div>
<br class="ebr">

<table class="create-table">
	<colgroup>
		<col width="100">
		<col width="*">
		<col width="100">
		<col width="*">
	</colgroup>
	<tr>
		<th>ECR 번호</th>
		<td>
			<input type="text" name="" size="70" class="AXInput w70p" readonly="readonly" placeholder="자동생성">
		</td>
		<th>
			ECR 명
			<font color="red">
				<b>*</b>
			</font>
		</th>
		<td>
			<input type="text" name="name" size="70" class="AXInput w70p" autofocus="autofocus">
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
				<option value="<%=c.getName()%>"><%=c.getName()%></option>
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
			<input type="text" name="limit" id="limit" class="AXInput w100px" maxlength="8">
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
				<option value="<%=c.getName()%>" <%if (ccode.equals(c.getCode())) {%> selected="selected" <%}%>><%=c.getName()%></option>
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
				<option value="<%=b.getCode()%>" <%if (code.equals(b.getCode())) {%> selected="selected" <%}%>><%=b.getName()%></option>
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
				<input type="hidden" id="reason" name="reason">
				<script type="text/javascript">
					var kEditorParam = {
						Id : "reason",
						Width : "100%",
						Height : "350px",
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
				<input type="hidden" id="description" name="description">
				<script type="text/javascript">
					var kEditorParam = {
						Id : "description",
						Width : "100%",
						Height : "350px",
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
				upload.init(null, "s");
			</script>
		</td>
	</tr>

	<!-- 관련부품 -->
	<jsp:include page="/jsp/common/ref-part-attach.jsp"></jsp:include>
	<!-- 관련문서 -->
	<jsp:include page="/jsp/common/ref-doc-attach.jsp"></jsp:include>
	<!-- 결재선 -->
	<%-- 	<jsp:include page="/jsp/approval/register-app.jsp"></jsp:include> --%>
</table>

<table class="button-table">
	<tr>
		<td class="right">
			<button type="button" id="saveBtn">등록</button>
			<button type="button" id="closeBtn">닫기</button>
		</td>
	</tr>
</table>

<script type="text/javascript">
	$(function() {

		$("#closeBtn").click(function() {
			self.close();
		})

		$("#saveBtn").click(function() {
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

			if (!confirm("등록 하시겠습니까?")) {
				return false;
			}

			RAONKEDITOR.GetHtmlContents({
				type : 'htmlexwithdoctype',
				callback : function(paramObj) {
					$("#reason").val(btoa(unescape(encodeURIComponent(paramObj.strData))));
				}
			}, 'reason');
			
			RAONKEDITOR.GetHtmlContents({
				type : 'htmlexwithdoctype',
				callback : function(paramObj) {
					$("#description").val(btoa(unescape(encodeURIComponent(paramObj.strData))));
				}
			}, 'description');

			var params = _data($("#form"));
			var partList = AUIGrid.getGridData(partGridID);
			var docList = AUIGrid.getGridData(docGridID);
			params.partList = partList;
			params.docList = docList;
			params.brand = "<%=code%>";
			var url = _url("/ecr/create");
			_call(url, params, function(data) {
				opener.load();
				self.close();
			}, "POST");
		})

		$(window).resize(function() {
			AUIGrid.resize("#doc_grid_wrap");
			AUIGrid.resize("#part_grid_wrap");
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
					$(".part-button").hide();
					//문서
					$("#doc_grid_wrap").hide();
					$(".doc-button").hide();
					_selector("company");
					_selector("brand");
					_selector("reqType");
					_date("limit");
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
					$(".doc-button").hide();
				} else if (value == "3") {
					// 기본
					$(".create-table").hide();
					$(".ebr").hide();
					// 부품
					$("#part_grid_wrap").hide();
					$(".part-button").hide();
					//문서
					$("#doc_grid_wrap").show();
					AUIGrid.resize("#doc_grid_wrap");
					$(".doc-button").show();
				}
			},
		});
		_selector("company");
		_selector("brand");
		_selector("reqType");
		_date("limit");
// 		$("#brand").bindSelectDisabled(true);
	})
</script>