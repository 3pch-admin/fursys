<%@page import="platform.code.entity.BaseCode"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
ArrayList<BaseCode> ecoTypes = (ArrayList<BaseCode>) request.getAttribute("ecoTypes");
ArrayList<BaseCode> devTypes = (ArrayList<BaseCode>) request.getAttribute("devTypes");
ArrayList<BaseCode> applyTimes = (ArrayList<BaseCode>) request.getAttribute("applyTimes");
ArrayList<BaseCode> notiTypes = (ArrayList<BaseCode>) request.getAttribute("notiTypes");
ArrayList<BaseCode> lots = (ArrayList<BaseCode>) request.getAttribute("lots");
%>
<div class="header-title">
	<img src="/Windchill/jsp/images/home.png" class="home">
	<span>HOME</span>
	>
	<span>설계변경</span>
	>
	<span>ECO 조회</span>
</div>
<table class="search-table top-color">
	<colgroup>
		<col width="100">
		<col width="*">
		<col width="100">
		<col width="*">
		<col width="100">
		<col width="*">
	</colgroup>
	<tr>
		<th>ECO 번호</th>
		<td>
			<input type="text" name="number" class="AXInput w70p">
		</td>
		<th>ECO 명</th>
		<td>
			<input type="text" name="name" class="AXInput w70p" autofocus="autofocus">
		</td>
		<th>관련부품</th>
		<td>
			<input size="40" type="text" name="part" id="part" readonly="readonly" class="AXInput w70p">
			<i class="axi axi-close2 axicon"></i>
		</td>
	</tr>
	<tr>

		<th>상태</th>
		<td>
			<select name="state" class="AXSelect w100px" id="state">
				<option value="">선택</option>
				<option value="작업 중">작업 중</option>
				<option value="승인중">승인중</option>
				<option value="결재완료" selected="selected">결재완료</option>
				<option value="반려됨">반려됨</option>
			</select>
		</td>
		<th>작성자</th>
		<td>
			<input type="text" class="AXInput w200px" name="creator" id="creator">
			<i class="axi axi-close2 axicon"></i>
		</td>
		<th>작성일자</th>
		<td>
			<input type="text" class="AXInput w100px" name="startCreatedDate" id="startCreatedDate" maxlength="8">
			~
			<input type="text" class="AXInput w100px" name="endCreatedDate" id="endCreatedDate" data-start="startCreatedDate" maxlength="8">
			<i class="axi axi-close2 axicon"></i>
		</td>
	</tr>
	<tr>
		<th>ECO 유형</th>
		<td>
			<select name="ecoType" class="AXSelect w200px" id="ecoType">
				<option value="">선택</option>
				<%
				for (BaseCode ecoType : ecoTypes) {
				%>
				<option value="<%=ecoType.getCode()%>" <%if (ecoType.getName().equals("생산")) {%> selected="selected" <%}%>><%=ecoType.getName()%></option>
				<%
				}
				%>
			</select>
		</td>
		<th>개발구분</th>
		<td>
			<select name="devType" class="AXSelect w200px" id="devType">
				<option value="">선택</option>
				<%
				for (BaseCode devType : devTypes) {
				%>
				<option value="<%=devType.getCode()%>"><%=devType.getName()%></option>
				<%
				}
				%>
			</select>
		</td>
		<th>통보유형</th>
		<td>
			<select name="notiType" class="AXSelect w200px" id="notiType">
				<option value="">선택</option>
				<%
				for (BaseCode notiType : notiTypes) {
				%>
				<option value="<%=notiType.getCode()%>"><%=notiType.getName()%></option>
				<%
				}
				%>
			</select>
		</td>
	</tr>
	<tr>
		<th>제품LOT관리</th>
		<td>
			<select name="lot" class="AXSelect w200px" id="lot">
				<option value="">선택</option>
				<%
				for (BaseCode lot : lots) {
				%>
				<option value="<%=lot.getCode()%>"><%=lot.getName()%></option>
				<%
				}
				%>
			</select>
		</td>
		<th>적용시점</th>
		<td>
			<select name="applyTime" class="AXSelect w200px" id="applyTime">
				<option value="">선택</option>
				<%
				for (BaseCode applyTime : applyTimes) {
				%>
				<option value="<%=applyTime.getCode()%>"><%=applyTime.getName()%></option>
				<%
				}
				%>
			</select>
		</td>
		<th>예상 적용일</th>
		<td>
			<input type="text" class="AXInput w100px" name="expectationTime" id="expectationTime">
			<i class="axi axi-close2 axicon"></i>
		</td>
	</tr>
</table>
<table class="button-table">
	<tr>
		<td class="right">
			<button type="button" id="addBtn">추가</button>
			<button type="button" id="searchBtn">조회</button>
			<button type="button" id="closeBtn">닫기</button>
		</td>
	</tr>
</table>

<div id="grid_wrap" style="height: 730px;"></div>
<script type="text/javascript">
	var myGridID;
	var columnLayout = [ {
		dataField : "number",
		headerText : "ECO 번호",
		dataType : "string",
		width : 200
	}, {
		dataField : "name",
		headerText : "ECO 명",
		dataType : "string",
		style : "left indent10"
	}, {
		dataField : "ecoTypeNm",
		headerText : "ECO유형",
		dataType : "string",
		width : 80
	}, {
		dataField : "devTypeNm",
		headerText : "개발유형",
		dataType : "string",
		width : 80
	}, {
		dataField : "notiTypeNm",
		headerText : "통보유형",
		dataType : "string",
		width : 80
	}, {
		dataField : "lotNm",
		headerText : "제품 LOT 관리",
		dataType : "string",
		width : 150
	}, {
		dataField : "applyTimeNm",
		headerText : "적용시점",
		dataType : "string",
		width : 100
	}, {
		dataField : "expectationTime",
		headerText : "예상적용일",
		dataType : "date",
		width : 100,
		formatString : "yyyy/mm/dd"
	}, {
		dataField : "creator",
		headerText : "작성자",
		dataType : "string",
		width : 100
	}, {
		dataField : "createdDate",
		headerText : "작성일자",
		dataType : "date",
		formatString : "yyyy/mm/dd",
		width : 100
	}, {
		dataField : "state",
		headerText : "상태",
		dataType : "string",
		width : 80
	}, {
		dataField : "oid",
		headerText : "oid",
		dataType : "string",
		visible : false
	}, ];
	var auiGridProps = {
		rowIdField : "oid",
		headerHeight : 30,
		rowHeight : 30,
		fillColumnSizeMode : true,
		rowCheckToRadio : true,
		showRowCheckColumn : true,
		rowNumHeaderText : "번호",
		usePaging : true
	};
	myGridID = AUIGrid.create("#grid_wrap", columnLayout, auiGridProps);

	function load() {
		var params = _data($("#form"));
		var url = _url("/eco/list");
		console.log(params);
		AUIGrid.showAjaxLoader(myGridID);
		_call(url, params, function(data) {
			AUIGrid.removeAjaxLoader(myGridID);
			AUIGrid.setGridData(myGridID, data.list);
		}, "POST");
	}

	AUIGrid.bind(myGridID, "cellClick", function(event) {
		var rowItem = event.item;
		AUIGrid.setCheckedRowsByIds(myGridID, rowItem.oid);
	});

	//jquery
	$(function() {
		load();

		$("#searchBtn").click(function() {
			load();
		})
		$("#closeBtn").click(function() {
			self.close();
		})
		$("#addBtn").click(function() {
			var items = AUIGrid.getCheckedRowItems(myGridID);
			if (items.length == 0) {
				alert("추가 할 ECO를 선택하세요.");
				return false;
			}
			var state = items[0].item.state;
			if (state != "결재완료") {
				alert("결재완료 상태의 ECO만 추가 가능합니다.");
				return false;
			}
			var list = _array(items);
			var params = new Object();
			params.list = list;
			var oid = items[0].item.oid;
			var url = _url("/eco/info");
			_call(url, params, function(data) {
				opener.eco(data.info, oid);
				self.close();
			}, "POST");
		})
		_selector("state");
		_selector("ecoType");
		// 		$("#state").bindSelectDisabled(true);
		// 		$("#ecoType").bindSelectDisabled(true);
		_selector("lot");
		_selector("devType");
		_selector("notiType");
		_selector("applyTime");
		_user("creator");
		_between("endCreatedDate");
		_date("expectationTime");
	}).keypress(function(e) {
		if (e.keyCode == 13) {
			load();
		}
	})

	$(window).resize(function() {
		AUIGrid.resize("#grid_wrap");
	})
</script>