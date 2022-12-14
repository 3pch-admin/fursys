<%@page import="platform.util.CommonUtils"%>
<%@ page import="platform.code.entity.BaseCode"%>
<%@ page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
ArrayList<BaseCode> company = (ArrayList<BaseCode>) request.getAttribute("company");
ArrayList<BaseCode> brand = (ArrayList<BaseCode>) request.getAttribute("brand");
ArrayList<BaseCode> notiTypes = (ArrayList<BaseCode>) request.getAttribute("notiTypes");
ArrayList<BaseCode> applyTimes = (ArrayList<BaseCode>) request.getAttribute("applyTimes");
ArrayList<BaseCode> plant = (ArrayList<BaseCode>) request.getAttribute("plant");
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
	<span>ECN 등록</span>
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
		<th>ECN 번호</th>
		<td>
			<input type="text" name="number" class="AXInput w70p" readonly="readonly" placeholder="자동생성">
		</td>
		<th>
			ECN 명
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
		<th>
			적용예정일
			<font color="red">
				<b>*</b>
			</font>
		</th>
		<td>
			<input type="text" class="AXInput w100px" name="applicationDate" id="applicationDate">
		</td>
		<th>
			사업장
			<font color="red">
				<b>*</b>
			</font>
		</th>
		<td>
			<select id="plant" name="plant" class="AXSelect w200px">
				<option value="">선택</option>
				<%
				for (BaseCode c : plant) {
				%>
				<option value="<%=c.getCode()%>"><%=c.getName()%></option>
				<%
				}
				%>
			</select>
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
		<th>첨부파일</th>
		<td colspan="3">
			<div class="AXUpload5" id="secondary_layer"></div>
			<div class="AXUpload5QueueBox_list" id="uploadQueueBox"></div>
			<script type="text/javascript">
				upload.init("", "s");
			</script>
		</td>
	</tr>
	<!-- 	<tr> -->
	<!-- 		<th> -->
	<!-- 			관련ECO -->
	<!-- 			<font color="red"> -->
	<!-- 				<b>*</b> -->
	<!-- 			</font> -->
	<!-- 		</th> -->
	<!-- 		<td colspan="3" class="inner"> -->
	<%-- 			<jsp:include page="/jsp/echange/ecn/ref-ecn-eco-attach.jsp"></jsp:include> --%>
	<!-- 		</td> -->
	<!-- 	</tr> -->

	<!-- 관련문서 -->
	<jsp:include page="/jsp/echange/ecn/ref-ecn-eco-attach.jsp"></jsp:include>

	<!-- 관련문서 -->
	<jsp:include page="/jsp/common/ref-doc-attach.jsp"></jsp:include>
	<!-- 결재선 -->
	<%-- 	<jsp:include page="/jsp/approval/register-app.jsp"></jsp:include> --%>
</table>

<table class="button-table">
	<tr>
		<td class="right">
			<button type="button" id="createBtn">등록</button>
			<button type="button" id="closeBtn">닫기</button>
		</td>
	</tr>
</table>
<script type="text/javascript">
	$(function() {

		$("#closeBtn").click(function() {
			self.close();
		})

		$("#createBtn").click(function() {
			$name = $("input[name=name]");
			$company = $("select[name=company]");
			$brand = $("select[name=brand]");
			$plant = $("select[name=plant]");
			$applicationDate = $("input[name=applicationDate]");
			$notiType = $("select[name=notiType]");
			$applyTime = $("select[name=applyTime]");
			$refEco = $("input[name=eoid]");

			if ($name.val() == "") {
				alert("ECN 명을 입력하세요.");
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
			if ($applicationDate.val() == "") {
				alert("실제적용일을 선택하세요.");
				$expectationTime.focus();
				return false;
			}
			if ($plant.val() == "") {
				alert("사업장을 선택하세요.");
				$plant.focus();
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
			if ($refEco.val() == undefined) {
				alert("관련 ECO를 선택하세요.");
				$("#selectEcoBtn").click();
				return false;
			}
			if (!confirm("등록 하시겠습니까?")) {
				return false;
			}

			var params = _data($("#form"));
			var url = _url("/ecn/create");
			var docList = AUIGrid.getGridData(docGridID);
			params.docList = docList;
			_call(url, params, function(data) {
				opener.load();
				self.close();
			}, "POST");
		})

		$(window).resize(function() {
			AUIGrid.resize("#eco_grid_wrap");
			AUIGrid.resize("#doc_grid_wrap");
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
				optionText : "관련 ECO",
			}, {
				optionValue : "3",
				optionText : "관련문서",
			} ],
			onchange : function(selectedObject, value) {
				if (value == "1") {
					// 기본
					$(".create-table").show();
					$(".ebr").show();
					//문서
					$("#eco_grid_wrap").hide();
					$(".eco-button").hide();
					// 결재선
					$("#doc_grid_wrap").hide();
					$(".doc-button").hide();
					_date("applicationDate");
					_selector("applyTime");
					_selector("company");
					_selector("notiType");
					_selector("company");
					_selector("brand");
					_selector("plant");

				} else if (value == "2") {
					// 기본
					$(".create-table").hide();
					$("#eco_grid_wrap").show();
					$(".eco-button").show();
					$(".ebr").hide();
					//문서
					$("#doc_grid_wrap").hide();
					AUIGrid.resize("#eco_grid_wrap");
					$(".doc-button").hide();
				} else if (value == "3") {
					// 기본
					$(".create-table").hide();
					$(".ebr").hide();
					$("#eco_grid_wrap").hide();
					$(".eco-button").hide();
					//문서
					$("#doc_grid_wrap").show();
					AUIGrid.resize("#doc_grid_wrap");
					$(".doc-button").show();
				}
			},
		});
		_date("applicationDate");
		_selector("applyTime");
		_selector("company");
		_selector("notiType");
		_selector("company");
		_selector("brand");
		_selector("plant");
	})
</script>