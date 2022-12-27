<%@page import="platform.util.CommonUtils"%>
<%@ page import="platform.code.entity.BaseCode"%>
<%@ page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
ArrayList<BaseCode> company = (ArrayList<BaseCode>) request.getAttribute("company");
ArrayList<BaseCode> brand = (ArrayList<BaseCode>) request.getAttribute("brand");
ArrayList<BaseCode> notiTypes = (ArrayList<BaseCode>) request.getAttribute("notiTypes");
ArrayList<BaseCode> applyTimes = (ArrayList<BaseCode>) request.getAttribute("applyTimes");
ArrayList<BaseCode> lots = (ArrayList<BaseCode>) request.getAttribute("lots");
ArrayList<BaseCode> ecoTypes = (ArrayList<BaseCode>) request.getAttribute("ecoTypes");
ArrayList<BaseCode> devTypes = (ArrayList<BaseCode>) request.getAttribute("devTypes");
String ecoType = (String) request.getAttribute("ecoType");
String code = CommonUtils.getSessionBrand();
String ccode = CommonUtils.getSessionCompany();
%>
<!-- hidden value -->
<div class="header-title">
	<img src="/Windchill/jsp/images/home.png" class="home">
	<span>HOME</span>
	>
	<span>설계변경</span>
	>
	<span><%=ecoType%>
		ECO 등록
	</span>
</div>

<div id="tabs"></div>
<!-- <br class="ebr"> -->
<table class="button-table">
	<tr>
		<td class="right">
			<button type="button" id="createBtn">등록</button>
			<button type="button" id="closeBtn">닫기</button>
		</td>
	</tr>
</table>
<table class="create-table">
	<colgroup>
		<col width="120">
		<col width="800">
		<col width="120">
		<col width="800">
	</colgroup>
	<tr>
		<th>관련 ECR</th>
		<td colspan="3">
			<!-- hidden value -->
			<input type="text" name="ecr" class="AXInput w70p" readonly="readonly">
		</td>
	</tr>
	<tr>
		<th>ECO 번호</th>
		<td>
			<input type="text" name="number" class="AXInput w70p" readonly="readonly" placeholder="자동생성">
		</td>
		<th>
			ECO 명
			<font color="red">
				<b>*</b>
			</font>
		</th>
		<td>
			<input type="text" name="name" class="AXInput w70p" autofocus="autofocus">
		</td>
	</tr>
	<tr>
		<th>
			통보유형
			<font color="red">
				<b>*</b>
			</font>
		</th>
		<td>
			<select id="notiType" name="notiType" class="AXSelect w200px">
				<option value="">선택</option>
				<%
				for (BaseCode c : notiTypes) {
				%>
				<option value="<%=c.getCode()%>"><%=c.getName()%></option>
				<%
				}
				%>
			</select>
		</td>
		<th>
			적용시점
			<font color="red">
				<b>*</b>
			</font>
		</th>
		<td>
			<select id="applyTime" name="applyTime" class="AXSelect w200px">
				<option value="">선택</option>
				<%
				for (BaseCode b : applyTimes) {
				%>
				<option value="<%=b.getCode()%>"><%=b.getName()%></option>
				<%
				}
				%>
			</select>
		</td>
	</tr>
	<tr>
<!-- 		<th> -->
<!-- 			예상적용일 -->
<!-- 			<font color="red"> -->
<!-- 				<b>*</b> -->
<!-- 			</font> -->
<!-- 		</th> -->
<!-- 		<td> -->
<!-- 			<input type="text" class="AXInput w100px" name="expectationTime" id="expectationTime"> -->
<!-- 		</td> -->
		<th>
			제품LOT관리
			<font color="red">
				<b>*</b>
			</font>
		</th>
		<td colspan="3">
			<select id="lot" name="lot" class="AXSelect w200px">
				<option value="">선택</option>
				<%
				for (BaseCode b : lots) {
				%>
				<option value="<%=b.getCode()%>" ><%=b.getName()%></option>
				<%
				}
				%>
			</select>
		</td>
	</tr>
	<tr>
		<th>ECO유형</th>
		<td>
			<select id="ecoType" name="ecoType" disabled="disabled" class="AXSelect w200px">
				<option value="">선택</option>
				<%
				for (BaseCode c : ecoTypes) {
				%>
				<option value="<%=c.getCode()%>" <%if (ecoType.equals(c.getName())) {%> selected="selected" <%}%>><%=c.getName()%></option>
				<%
				}
				%>
			</select>
		</td>
		<th>
			개발구분
			<font color="red">
				<b>*</b>
			</font>
		</th>
		<td>
			<select id="devType" name="devType" class="AXSelect w200px">
				<option value="">선택</option>
				<%
				for (BaseCode b : devTypes) {
				%>
				<option value="<%=b.getCode()%>"><%=b.getName()%></option>
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
			<select id="company" name="company" class="AXSelect w200px">
				<option value="">선택</option>
				<%
				for (BaseCode c : company) {
				%>
				<option value="<%=c.getCode()%>" <%if (ccode.equals(c.getCode())) {%> selected="selected" <%}%>><%=c.getName()%></option>
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
			<select id="brand" name="brand" class="AXSelect w200px">
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
		<th>변경사유</th>
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
		<th>변경내용</th>
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
				upload.init("", "s");
			</script>
		</td>
	</tr>

	<!-- 관련부품 -->
	<%
	if ("설계".equals(ecoType)) {
	%>
	<jsp:include page="/jsp/echange/eco/ref-eco-ebom-attach.jsp"></jsp:include>
	<%
	} else {
	%>
	<jsp:include page="/jsp/echange/eco/ref-eco-mbom-attach.jsp"></jsp:include>
	<%
	}
	%>
	<!-- 관련문서 -->
	<jsp:include page="/jsp/common/ref-doc-attach.jsp"></jsp:include>
	<!-- 결재선 -->
	<%-- 	<jsp:include page="/jsp/approval/register-app.jsp"></jsp:include> --%>
</table>


<script type="text/javascript">
	$(function() {

		$(window).resize(function() {
			AUIGrid.resize("#part_grid_wrap");
			AUIGrid.resize("#doc_grid_wrap");
		})

		$("#closeBtn").click(function() {
			self.close();
		})

		$("#createBtn").click(function() {
			$name = $("input[name=name]");
			$notiType = $("select[name=notiType]");
// 			$expectationTime = $("input[name=expectationTime]");
			$applyTime = $("select[name=applyTime]");
			$lot = $("select[name=lot]");
			$devType = $("select[name=devType]");
			$company = $("select[name=company]");
			$brand = $("select[name=brand]");

			if ($name.val() == "") {
				alert("ECO 명을 입력하세요.");
				$name.focus();
				return false;
			}
			if ($notiType.val() == "") {
				alert("통보유형을 선택하세요.");
				$notiType.focus();
				return false;
			}
			if ($applyTime.val() == "") {
				alert("적용시점을 선택하세요.");
				$applyTime.focus();
				return false;
			}
// 			if ($expectationTime.val() == "") {
// 				alert("예상적용일을 선택하세요.");
// 				$expectationTime.focus();
// 				return false;
// 			}
			if ($lot.val() == "") {
				alert("제품LOT관리를 선택하세요.");
				$lot.focus();
				return false;
			}
			if ($devType.val() == "") {
				alert("개발구분을 선택하세요.");
				$devType.focus();
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
			var url = _url("/eco/create");
			var ebomMasterList = AUIGrid.getGridData(partGridID);
			var docList = AUIGrid.getGridData(docGridID);
			params.ebomMasterList = ebomMasterList;
			params.docList = docList;
			params.ecoType = $("select[name=ecoType] option:selected").val();
			console.log(params);
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
					$(".part-button").hide();
					//문서
					$("#doc_grid_wrap").hide();
					$(".doc-button").hide();
					// 결재선
					_date("expectationTime");
					_selector("ecoType");
					_selector("lot");
					_selector("devType");
					_selector("applyTime");
					_selector("company");
					_selector("notiType");
					_selector("company");
					_selector("brand");
					$("#ecoType").bindSelectDisabled(true);
				} else if (value == "2") {
					// 기본
					$(".create-table").hide();
					$(".ebr").hide();
					$(".button-table").hide();
					// 부품
					$("#part_grid_wrap").show();
					AUIGrid.resize("#part_grid_wrap");
					$(".part-button").show();
					// 					AUIGrid.resize("#part_grid_wrap");
					_selector("color");
					//문서
					$("#doc_grid_wrap").hide();
					$(".doc-button").hide();
					// 결재선
				} else if (value == "3") {
					// 기본
					$(".create-table").hide();
					$(".ebr").hide();
					$(".button-table").hide();
					// 부품
					$("#part_grid_wrap").hide();
					$(".part-button").hide();
					//문서
					$("#doc_grid_wrap").show();
					$(".doc-button").show();
					AUIGrid.resize("#doc_grid_wrap");
				}
			},
		});
		_date("expectationTime");
		_selector("ecoType");
		_selector("lot");
		_selector("devType");
		_selector("applyTime");
		_selector("company");
		_selector("notiType");
		_selector("company");
		_selector("brand");
		$("#ecoType").bindSelectDisabled(true);
// 		$("#brand").bindSelectDisabled(true);
	})
</script>