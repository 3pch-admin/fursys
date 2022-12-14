<%@page import="net.sf.json.JSONArray"%>
<%@page import="platform.epm.entity.EpmColumns"%>
<%@page import="platform.part.entity.PartColumns" %>
<%@page import="java.util.ArrayList"%>
<%@page import="platform.code.entity.BaseCode"%>
<%@page import="wt.part.QuantityUnit"%>
<%@page import="platform.part.service.PartHelper"%>
<%@page import="platform.doc.service.DocumentHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
// ArrayList<EpmColumns> list = (ArrayList<EpmColumns>) request.getAttribute("list");
ArrayList<PartColumns> list = (ArrayList<PartColumns>) request.getAttribute("list");
JSONArray arr = JSONArray.fromObject(list);
%>
<div class="header-title">
	<input type="hidden" name="sessionid" id="sessionid">
	<input type="hidden" name="tpage" id="tpage">
	<input type="hidden" name="location" id="location">
	<img src="/Windchill/jsp/images/home.png" class="home">
	<span>HOME</span>
	>
	<span>배포 관리</span>
	>
	<span>배포 도면 목록</span>
</div>

<div id="grid_wrap" style="height: 500px;"></div>
<table class="button-table">
	<tr>
		<td class="right">
			<button type="button" id="closeBtn">닫기</button>
		</td>
	</tr>
</table>
<script type="text/javascript">
	var myGridID;
	var columnLayout = [ {
		headerText : "P",
		dataType : "string",
		width : 40,
		renderer : {
			type : "IconRenderer",
			iconWidth : 16,
			iconHeight : 16,
			iconFunction : function(rowIndex, columnIndex, value, item) {
				return item.p;
			}
		}
	}, {
		// 							dataField : "_3d",
		headerText : "",
		dataType : "string",
		width : 40,
		renderer : {
			type : "IconRenderer",
			iconWidth : 30,
			iconHeight : 22,
			iconFunction : function(rowIndex, columnIndex, value, item) {
				return item.t;
			},
		// 			onClick : function(event) {
		// 				var item = event.item;
		// 				if (item._3d.indexOf("no-view.png") <= -1) {
		// 					_openCreoView(item.eoid);
		// 				}
		// 			}
		}
	}, {
		dataField : "number",
		headerText : "CAD 파일명",
		dataType : "string",
		width : 250,
		style : "left indent10"
	}, {
		dataField : "name",
		headerText : "도면명칭",
		dataType : "string",
		width : 350,
		style : "left indent10"
	}, {
		dataField : "erpCode",
		headerText : "ERP CODE",
		dataType : "string",
		width : 120
	}, {
		dataField : "version",
		headerText : "버전",
		dataType : "string",
		width : 60
	}, {
		dataField : "creator",
		headerText : "작성자",
		dataType : "string",
		width : 80
	}, {
		dataField : "createdDate",
		headerText : "작성일자",
		dataType : "date",
		formatString : "yyyy/mm/dd",
		width : 100
	}, {
		dataField : "modifier",
		headerText : "수정자",
		dataType : "string",
		width : 80
	}, {
		dataField : "modifiedDate",
		headerText : "수정일자",
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
		// 		showRowCheckColumn : true,
		showRowNumColumn : false,
	// 		enableFilter : true,
	// 		useContextMenu : true,
	};
	myGridID = AUIGrid.create("#grid_wrap", columnLayout, auiGridProps);
	AUIGrid.setGridData("#grid_wrap", <%=arr%>);
	// jquery 
	$(function() {
		$("#closeBtn").click(function() {
			self.close();
		})
	})

	$(window).resize(function() {
		AUIGrid.resize("#grid_wrap");
	})
</script>