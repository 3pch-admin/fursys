<%@page import="net.sf.json.JSONArray"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
ArrayList<Map<String, Object>> list = (ArrayList<Map<String, Object>>) request.getAttribute("list");
JSONArray darr = JSONArray.fromObject(list);
String oid = (String) request.getAttribute("oid");
%>
<style type="text/css">
.compare {
	background-color: #e4edf5;
}
</style>
<div class="header-title">
	<img src="/Windchill/jsp/images/home.png" class="home">
	<span>HOME</span>
	>
	<span>BOM관리</span>
	>
	<span>EBOM 검증</span>
</div>

<div id="grid_wrap" style="height: 700px;"></div>

<table class="button-table">
	<tr>
		<td class="right">
			<button type="button" id="closeBtn">닫기</button>
			<button type="button" id="confirmBtn">저장</button>
		</td>
	</tr>
</table>

<script type="text/javascript">
	var myGridID;
	var columnLayout = [ {
		dataField : "number",
		headerText : "CAD 파일명",
		dataType : "string",
		style : "left indent10",
		editable : false,
	}, {
		dataField : "partType",
		headerText : "부품유형",
		dataType : "string",
		width : 100,
		filter : {
			showIcon : true
		},
		editable : false,
	}, {
		dataField : "itemName",
		headerText : "제품코드(ITEM_NAME)",
		dataType : "string",
		editable : false,
		width : 250
	}, {
		dataField : "partName",
		headerText : "부품명(PART_NAME)",
		dataType : "string",
		editable : false,
		width : 250
	}, {
		dataField : "partNo",
		headerText : "부품번호(PART_NO)",
		dataType : "string",
		editable : false,
		width : 250
	}, {
		dataField : "cqty",
		headerText : "CBOM 수량",
		dataType : "string",
		postfix : "개",
		editable : false,
		width : 100,
	}, {
		dataField : "eqty",
		headerText : "EBOM 수량",
		dataType : "string",
// 		postfix : "개",
		editable : false,
		width : 100,
	     renderer : {
	            type : "TemplateRenderer",
	     },		
		labelFunction : function(rowIndex, columnIndex, value, headerText, item, dataField, cItem) {
			   var str = value + "개";
			   if(item.cqty != item.eqty) {
				   str = "<font color=red><b>" + value + "개</b></font>";
			   }
			   return str;
			}
	}, {
		dataField : "compare",
		headerText : "수량차이",
		dataType : "string",
		postfix : "개",
		editable : false,
		width : 100
	}, {
		dataField : "oid",
		headerText : "oid",
		dataType : "string",
		visible : false
	}, ];

	var footerLayout = [ {
		dataField : "partNo",
		positionField : "partNo",
		labelText : "총 수량",
	}, {
		dataField : "camount",
		positionField : "camount",
		postfix : "개",
		operation : "SUM",
	}, {
		dataField : "eamount",
		positionField : "eamount",
		postfix : "개",
		operation : "SUM",
	}, {
		dataField : "compare",
		positionField : "compare",
		postfix : "개",
		operation : "SUM",
	} ];

	var auiProps = {
		// 		rowIdField : "oid",
		headerHeight : 30,
		rowHeight : 30,
		showRowNumColumn : true,
		showFooter : true,
		enableFilter : true,
		rowNumHeaderText : "번호",
		rowStyleFunction : function(rowIndex, item) {
			if (item.eqty != item.cqty) {
				return "compare";
			}
			return "";
		}
	};
	myGridID = AUIGrid.create("#grid_wrap", columnLayout, auiProps);
	AUIGrid.setFooter(myGridID, footerLayout);
	AUIGrid.setFooter(myGridID, footerLayout);
	AUIGrid.setGridData(myGridID,<%=darr%>);
	$(window).resize(function() {
		AUIGrid.resize("#grid_wrap");
	})
	
	requestData("/Windchill/jsp/ebom/mockup/ebom-verify.json");
	
	
	function requestData(url) {

		// ajax 요청 전 그리드에 로더 표시
		AUIGrid.showAjaxLoader(myGridID);

		// ajax (XMLHttpRequest) 로 그리드 데이터 요청
		ajax({
			url : url,
			onSuccess : function(data) {

				//console.log(data);

				// 그리드에 데이터 세팅
				// data 는 JSON 을 파싱한 Array-Object 입니다.
				AUIGrid.setGridData(myGridID, data);

				// 로더 제거
				AUIGrid.removeAjaxLoader(myGridID);
			},
			onError : function(status, e) {
				alert("데이터 요청에 실패하였습니다.\r\n status : " + status + "\r\nWAS 를 IIS 로 사용하는 경우 json 확장자가 web.config 의 handler 에 등록되었는지 확인하십시오.");
				// 로더 제거
				AUIGrid.removeAjaxLoader(myGridID);
			}
		});
	};
	
	$(function() {
		$("#closeBtn").click(function() {
			self.close();
		})
		
		$("#confirmBtn").click(function() {
			
			var compare = 0;
			if(compare == 0){
				if(confirm("CAD와 EBOM의 수량이 일치합니다. \n저장하시겠습니까?")) {
					var url = _url("/ebom/confirm", "<%=oid%>");
					var params = new Object();
					_call(url, params, function(data) {
						opener.closeAndLoad();
						self.close();
					}, "GET");
				}
			} else {
				if(confirm("CAD와 EBOM의 수량차이가 있습니다.\n저장하시겠습니까?")) {
					var url = _url("/ebom/confirm", "<%=oid%>");
					var params = new Object();
					_call(url, params, function(data) {
						opener.closeAndLoad();
						self.close();
					}, "GET");
				}
			}
		})
	})
</script>