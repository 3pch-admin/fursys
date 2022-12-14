<%@page import="platform.raonk.entity.Raonk"%>
<%@page import="platform.util.StringUtils"%>
<%@page import="platform.doc.entity.DocumentDTO"%>
<%@page import="platform.code.entity.BaseCode"%>
<%@page import="java.util.ArrayList"%>
<%@page import="platform.doc.service.DocumentHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
ArrayList<BaseCode> company = (ArrayList<BaseCode>) request.getAttribute("company");
ArrayList<BaseCode> brand = (ArrayList<BaseCode>) request.getAttribute("brand");
ArrayList<Raonk> raonk = (ArrayList<Raonk>) request.getAttribute("raonk");
DocumentDTO dto = (DocumentDTO) request.getAttribute("dto");
%>
<!-- hidden value -->
<input type="hidden" name="content" id="content" value="<%=dto.getContent()%>">
<input type="hidden" name="oid" value="<%=dto.getOid()%>">
<div class="header-title">
	<img src="/Windchill/jsp/images/home.png" class="home">
	<span>HOME</span>
	>
	<span>기술문서 관리</span>
	>
	<span>기술문서 수정</span>
</div>

<div id="tabs"></div>
<br class="br1">
<table class="create-table">
	<colgroup>
		<col width="130">
		<col width="*">
		<col width="130">
		<col width="*">
	</colgroup>
	<tr>
		<th>
			문서분류
			<font color="red">
				<b>*</b>
			</font>
		</th>
		<td colspan="3">
			<input type="text" class="AXInput w40p" readonly="readonly" value="<%=dto.getLocation()%>" name="location" id="location">
		</td>
	</tr>
	<tr>
		<!-- 		<th>문서번호</th> -->
<!-- 		<td> -->
<%-- 			<input type="text" class="AXInput w60p" name="number" readonly="readonly" value="<%=dto.getNumber()%>"> --%>
<!-- 		</td> -->
		<th>
			문서명
			<font color="red">
				<b>*</b>
			</font>
		</th>
		<td>
			<input type="text" class="AXInput w60p" name="name" value="<%=dto.getName()%>">
		</td>
		<th>라온케이템플릿</th>
		<td>
			<select name="raonk" id="raonk" class="AXSelect w200px">
				<option value="">선택</option>
				<%
				for (Raonk r : raonk) {
				%>
				<option value="<%=r.getPersistInfo().getObjectIdentifier().getStringValue()%>"><%=r.getName()%></option>
				<%
				}
				%>
			</select>
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
			<select name="company" id="company" class="AXSelect w200px">
				<option value="">선택</option>
				<%
				for (BaseCode c : company) {
				%>
				<option value="<%=c.getCode()%>" <%if (c.getCode().equals(dto.getCompany())) {%> selected="selected" <%}%>><%=c.getName()%></option>
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
			<select name="brand" id="brand" class="AXSelect w200px">
				<option value="">선택</option>
				<%
				for (BaseCode b : brand) {
				%>
				<option value="<%=b.getCode()%>" <%if (b.getCode().equals(dto.getBrand())) {%> selected="selected" <%}%>><%=b.getName()%></option>
				<%
				}
				%>
			</select>
		</td>
	</tr>
	<tr>
		<th>설명</th>
		<td colspan="3">
			<div class="raonkEditor">
				<script type="text/javascript">
					var kEditorParam = {
						Id : "editor",
						Width : "100%",
						Height : "500px",
						FocusInitObjId : "name",
						Runtimes : 'html5' // agent, html5
					};
					new RAONKEditor(kEditorParam);
				</script>
			</div>
		</td>
	</tr>
	<tr>
		<th>
			주 첨부파일
			<font color="red">
				<b>*</b>
			</font>
		</th>
		<td colspan="3">
			<div class="AXUpload5" id="primary_layer"></div>
			<script type="text/javascript">
				upload.init("<%=dto.getOid()%>", "p");
			</script>
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
	<jsp:include page="/jsp/common/ref-part-attach.jsp"></jsp:include>
	<!-- 결재선 -->
	<%-- 	<jsp:include page="/jsp/approval/register-app.jsp"> --%>
	<%-- 	</jsp:include> --%>
	</table>

<table class="button-table">
	<tr>
		<td class="right">
			<button type="button" id="modifyBtn">수정</button>
			<button type="button" id="closeBtn">닫기</button>
		</td>
	</tr>
</table>

<script type="text/javascript">
	$(function() {
		AUIGrid.setGridData("#part_grid_wrap",<%=dto.getPartJson()%>);
		$("#closeBtn").click(function() {
			self.close();
		})

		$("#modifyBtn").click(function() {
			$name = $("input[name=name]");
			$location = $("input[name=location]");
			$brand = $("select[name=brand]");
			$company = $("select[name=company]");
			$primary = $("input[name=primary]");

			if ($name.val() == "") {
				alert("문서 명을 입력하세요.");
				$name.focus();
				return false;
			}
			if ($company.val() == "") {
				alert("회사를 선택하세요.");
				$company.focus();
				return false;
			}
			if ($brand.val() == "") {
				alert("브랜드를 선택하세요.");
				$brand.focus();
				return false;
			}
			if ($primary.val() == undefined) {
				alert("주 첨부파일을 선택하세요.");
				$("#primary_layer_AX_selector").click();
				return false;
			}

			if (!confirm("수정 하시겠습니까?")) {
				return false;
			}

			RAONKEDITOR.GetHtmlContents({
				type : 'htmlexwithdoctype',
				callback : function(paramObj) {
					$("#content").val(btoa(unescape(encodeURIComponent(paramObj.strData))));
				}
			}, 'editor');
			var partList = AUIGrid.getGridData(partGridID);
			var params = _data($("#form"));
			params.partList = partList;
			var url = _url("/doc/modify");
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
			// 			}, {
			// 				optionValue : "3",
			// 				optionText : "결재선"
			} ],
			onchange : function(selectedObject, value) {
				if (value == "1") {
					_selector("company");
					_selector("brand");
					_selector("raonk");
					// 기본
					$(".create-table").show();
					$("#part_grid_wrap").hide();
					$(".part-button").hide();
					$(".br1").show();
				} else if (value == "2") {
					// 기본
					$(".create-table").hide();
					// 부품
					$("#part_grid_wrap").show();
					$(".part-button").show();
					$(".br1").hide();
					AUIGrid.resize("#part_grid_wrap");
				}
			},
		});

		$(window).resize(function() {
			AUIGrid.resize("#part_grid_wrap");
		})

		// 		_folder("location", "/Default/문서");
		$("#location").click(function() {
			var url = "/Windchill/platform/util/folder?location=/Default/Document&target=&context=&callBack=raonk";
			_popup(url, 600, 500, "n");
		})
		_selector("company");
		_selector("brand");
		_selector("raonk");
		$("#brand").bindSelectDisabled(true);
		$("#raonk").change(function() {
			var oid = $(this).val();
			if (oid == "") {
				return false;
			}
			if (!confirm("작성된 내용이 삭제 후 라온케이 템플릿이 입력 되어집니다.\n계속 하시겠습니까?")) {
				return false;
			}

			var url = _url("/raonk/content", oid);
			_call(url, null, function(data) {
				RAONKEDITOR.SetHtmlContents(decodeURIComponent(escape(window.atob(data.content))), "editor");
			}, "GET");
		})
	})

	function RAONKEDITOR_CreationComplete(editorId) {
		$value = $("#content");
		RAONKEDITOR.SetHtmlContents(decodeURIComponent(escape(window.atob($value.val()))), editorId);
	}
</script>
