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
<div id="output_grid_wrap" style="height: 840px;" class="close"></div>
<script type="text/javascript">
	var outputGridID;
	var columnLayout = [ {
		dataField : "taskName",
		headerText : "태스크 명",
		dataType : "string",
		width : 250,
		cellMerge : true
	}, {
		dataField : "name",
		headerText : "산출물 명",
		dataType : "string",
		// 		width : 250,
		style : "left indent10"
	}, {
		dataField : "docName",
		headerText : "문서명",
		dataType : "string",
		width : 350,
		style : "left indent10"
	}, {
		dataField : "location",
		headerText : "위치",
		dataType : "string",
		width : 350,
		style : "left indent10"
	}, {
		dataField : "version",
		headerText : "버전",
		dataType : "string",
		width : 80
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
		headerText : "상테",
		dataType : "string",
		width : 100
	}, {
		dataField : "state",
		headerText : "첨부파일",
		dataType : "string",
		width : 130
	}, {
		dataField : "toid",
		headerText : "toid",
		dataType : "string",
		visible : false
	}, {
		dataField : "doid",
		headerText : "doid",
		dataType : "string",
		visible : false
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
	outputGridID = AUIGrid.create("#output_grid_wrap", columnLayout, auiGridProps);

	AUIGrid.bind(outputGridID, "cellClick", function(event) {
		var rowItem = event.item;
		var dataField = event.dataField;

		if ("taskName" == dataField) {
			var toid = rowItem.toid;
			var oid = rowItem.poid;
			document.location.href = "/Windchill/platform/project/view?oid=" + oid + "&toid=" + toid;
		} else if ("name" == dataField) {
			var oid = rowItme.oid;
			var url = _url("/output/view", oid);
			_popup(url, 1200, 400, "n")
		} else if ("docName" == dataField) {
			var oid = rowItem.doid;
			if (oid == "") {
				return false;
			}
			var url = _url("/doc/view", oid);
			_popup(url, "", "", "f");
		}
	});
</script>