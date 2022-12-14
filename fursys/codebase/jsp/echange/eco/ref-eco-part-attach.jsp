<%@page import="net.sf.json.JSONArray"%>
<%@page import="platform.user.service.UserHelper"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String ecoType = (String) request.getParameter("ecoType");
// ArrayList<Map<String, Object>> list = UserHelper.manager.gridToUser();
// JSONArray arr = JSONArray.fromObject(list);
%>
<table class="button-table part-button close">
	<tr>
		<td class="left">
			<input type="hidden" name="header">
			<button type="button" id="selectBom">세트/단품 추가</button>
			<!-- 			<button type="button" id="selectPartBtn">자재 추가</button> -->
			<button type="button" id="deletePartBtn">삭제</button>
		</td>
	</tr>
</table>
<div id="part_grid_wrap" style="height: 780px;" class="close"></div>
<script type="text/javascript">
// 	var keyValueList =
<%-- <%=arr%> --%>
// 	;
	var partGridID;
	var columnLayout = [ {
		dataField : "s",
		headerText : "S",
		dataType : "string",
		width : 40,
		editable : false
	}, {
		dataField : "thumb",
		headerText : "3D",
		dataType : "string",
		width : 40,
		editable : false,
		renderer : {
			type : "IconRenderer",
			iconWidth : 30,
			iconHeight : 22,
			iconFunction : function(rowIndex, columnIndex, value, item) {
				return item._3d;
			}
		}
	}, {
		// 							dataField : "_2d",
		headerText : "2D",
		dataType : "string",
		width : 40,
		editable : false,
		renderer : {
			type : "IconRenderer",
			iconWidth : 16,
			iconHeight : 16,
			iconFunction : function(rowIndex, columnIndex, value, item) {
				return item._2;
			}
		}
	}, 
// 	{
// 		dataField : "manager",
// 		headerText : "담당자",
// 		dataType : "string",
// 		width : 120,
// 		editable : true,
// 		labelFunction : function(rowIndex, columnIndex, value, headerText, item) {

// 			var retStr = "";
// 			for (var i = 0, len = keyValueList.length; i < len; i++) {
// 				if (keyValueList[i]["oid"] == value) {
// 					retStr = keyValueList[i]["name"];
// 					break;
// 				}
// 			}
// 			return retStr == "" ? value : retStr;
// 		},
// 		editRenderer : {
// 			type : "ComboBoxRenderer",
// 			autoCompleteMode : true, // 자동완성 모드 설정
// 			autoEasyMode : true,
// 			matchFromFirst : false, // 처음부터 매치가 아닌 단순 포함되는 자동완성
// 			list : keyValueList, //key-value Object 로 구성된 리스트
// 			keyField : "oid", // key 에 해당되는 필드명
// 			valueField : "name", // value 에 해당되는 필드명
// 			// 에디팅 유효성 검사
// 			validator : function(oldValue, newValue, item) {
// 				var isValid = false;
// 				for (var i = 0, len = keyValueList.length; i < len; i++) { // keyValueList 있는 값만..
// 					if (keyValueList[i]["name"] == newValue) {
// 						isValid = true;
// 						break;
// 					}
// 				}
// 				// 리턴값은 Object 이며 validate 의 값이 true 라면 패스, false 라면 message 를 띄움
// 				return {
// 					"validate" : isValid,
// 					"message" : "리스트에 있는 값만 선택(입력) 가능합니다."
// 				};
// 			}
// 		}
// 	}
	 {
		dataField : "partType",
		headerText : "부품유형",
		dataType : "string",
		width : 80,
		editable : false,
	}, {
		dataField : "number",
		headerText : "부품번호",
		dataType : "string",
		width : 250,
		style : "left indent10",
		editable : false
	}, {
		dataField : "name",
		headerText : "부품명칭",
		dataType : "string",
		width : 350,
		style : "left indent10",
		editable : false
	}, {
		dataField : "version",
		headerText : "버전",
		dataType : "string",
		width : 60,
		editable : false
	}, {
		dataField : "ref",
		headerText : "CAD연계",
		dataType : "string",
		width : 80,
		editable : false
	}, {
		dataField : "derived",
		headerText : "파생여부",
		dataType : "string",
		width : 60,
		editable : false
	}, {
		dataField : "creator",
		headerText : "작성자",
		dataType : "string",
		width : 80,
		editable : false
	}, {
		dataField : "createdDate",
		headerText : "작성일자",
		dataType : "date",
		formatString : "yyyy/mm/dd",
		width : 100,
		editable : false
	}, {
		dataField : "modifier",
		headerText : "수정자",
		dataType : "string",
		width : 80,
		editable : false
	}, {
		dataField : "modifiedDate",
		headerText : "수정일자",
		dataType : "date",
		formatString : "yyyy/mm/dd",
		width : 100,
		editable : false
	}, {
		dataField : "state",
		headerText : "상태",
		dataType : "string",
		width : 130,
		editable : false
	}, {
		dataField : "erpCode",
		headerText : "ERP CODE",
		dataType : "string",
		width : 120,
		editable : false
	}, {
		dataField : "companyNm",
		headerText : "회사",
		dataType : "string",
		width : 100,
		editable : false
	}, {
		dataField : "brandNm",
		headerText : "브랜드",
		dataType : "string",
		width : 100,
		editable : false
	}, {
		dataField : "unit",
		headerText : "단위",
		dataType : "string",
		width : 100,
		editable : false
	}, {
		dataField : "order",
		headerText : "주문품여부",
		dataType : "string",
		width : 100,
		editable : false
	}, {
		dataField : "use",
		headerText : "사용여부",
		dataType : "string",
		width : 100,
		editable : false
	}, {
		dataField : "w",
		headerText : "규격가로(W)",
		dataType : "string",
		width : 100,
		editable : false
	}, {
		dataField : "d",
		headerText : "규격세로(D)",
		dataType : "string",
		width : 100,
		editable : false
	}, {
		dataField : "h",
		headerText : "사용높이(H)",
		dataType : "string",
		width : 100,
		editable : false
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
		showRowCheckColumn : true,
		showRowNumColumn : false,
		editable : true,
		rowStyleFunction : function(rowIndex, item) {
			if (item.partType == "단품") {
				return "item";
			} else if (item.partType == "세트") {
				return "set";
			}
			return "";
		}
	};
	partGridID = AUIGrid.create("#part_grid_wrap", columnLayout, auiGridProps);
	AUIGrid.resize("#part_grid_wrap");

	$(function() {

		$("#deletePartBtn").click(function() {
			var items = AUIGrid.getCheckedRowItems(partGridID);
			if (items.length == 0) {
				alert("삭제 할 행을 선택하세요.");
			}
			for (var i = 0; i < items.length; i++) {
				AUIGrid.removeRow(partGridID, items[i].rowIndex);
			}
		})

		AUIGrid.bind("#part_grid_wrap", "cellEditBegin", function(event) {
			var item = event.item;
			var partType = item.partType;
			if (partType == "단품") {
				return true;
			}
			return false;
		});

		$("#selectBom").click(function() {
			var url;
<%if ("설계".equals(ecoType)) {%>
	url = "/Windchill/platform/ebom/popup?box=1&opt=a&callBack=design";
<%} else {%>
url = "/Windchill/platform/eco/prodPart?box=1&opt=a&callBack=prod";
<%}%>
	_popup(url, "", "", "f");
		})
	})

	function design(list, oid) {
		AUIGrid.clearGridData(partGridID);
		$("input[name=header]").val(oid);
		AUIGrid.setGridData(partGridID, list);
	}
</script>
