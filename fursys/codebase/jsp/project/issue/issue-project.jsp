<%@page import="net.sf.json.JSONArray"%>
<%@page import="wt.part.WTPart"%>
<%@page import="platform.doc.service.DocumentHelper"%>
<%@page import="platform.doc.entity.WTDocumentWTPartLink"%>
<%@page import="java.util.ArrayList"%>
<%@page import="platform.util.IBAUtils"%>
<%@page import="wt.vc.VersionControlHelper"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="platform.util.CommonUtils"%>
<%@page import="wt.doc.WTDocument"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="issue_grid_wrap" style="height: 840px;" class="close"></div>
<script type="text/javascript">
	var issueGridID;
	var columnLayout = [ {
		dataField : "taskName",
		headerText : "태스크 명",
		dataType : "string",
		width : 250,
		cellMerge : true
	}, {
		dataField : "name",
		headerText : "이슈 명",
		dataType : "string",
		width : 350,
		style : "left indent10"
	}, {
		dataField : "description",
		headerText : "내용",
		dataType : "string",
		// 		width : 250,
		style : "left indent10"
	}, {
		dataField : "state",
		headerText : "상태",
		dataType : "string",
		width : 100,
	}, {
		dataField : "manager",
		headerText : "담당자",
		dataType : "string",
		width : 100
	}, {
		dataField : "creator",
		headerText : "요청자",
		dataType : "string",
		width : 100
	}, {
		dataField : "createdDate",
		headerText : "요청일자",
		dataType : "date",
		formatString : "yyyy/mm/dd",
		width : 100
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
		rowNumHeaderText : "번호",
		enableCellMerge : true,
		cellMergePolicy : "withNull",
	};
	issueGridID = AUIGrid.create("#issue_grid_wrap", columnLayout, auiGridProps);
	
	AUIGrid.bind(issueGridID, "cellClick", function(event) {
		var rowItem = event.item;
		var dataField = event.dataField;

		if ("taskName" == dataField) {
			var toid = rowItem.toid;
			var oid = rowItem.poid;
			document.location.href = "/Windchill/platform/project/view?oid=" + oid + "&toid=" + toid;
		} else if ("name" == dataField) {
			var oid = rowItem.oid;
			var url = _url("/issue/view", rowItem.oid);
			_popup(url, 1200, 500, "n");
		}
	});
</script>