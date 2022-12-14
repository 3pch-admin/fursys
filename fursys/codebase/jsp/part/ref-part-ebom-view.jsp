<%@page import="platform.ebom.service.EBOMHelper"%>
<%@page import="platform.ebom.entity.EBOM"%>
<%@page import="platform.part.service.PartHelper"%>
<%@page import="platform.ebom.entity.EBOMHeaderECOLink"%>
<%@page import="wt.fc.PersistenceHelper"%>
<%@page import="platform.ebom.entity.EBOMHeader"%>
<%@page import="platform.echange.eco.service.ECOHelper"%>
<%@page import="platform.echange.eco.entity.ECOWTPartLink"%>
<%@page import="platform.echange.eco.entity.ECO"%>
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
<%
String oid = (String) request.getParameter("oid");
WTPart part = (WTPart) CommonUtils.persistable(oid);
String number = part.getNumber();
EBOM ebom = EBOMHelper.manager.getEBOM(part);
if (ebom != null) {
	EBOMHeader header = ebom.getHeader();
	WTPart headerPart = header.getPart();
%>
<table class="search-table ebom-search close">
	<colgroup>
		<col width="180">
		<col width="530">
		<col width="180">
		<col width="530">
		<col width="180">
		<col width="530">
	</colgroup>
	<tr>
		<th>부품명칭(단품, 세트)</th>
		<td colspan="3">
			<%=headerPart.getName()%>
			/
			<%=headerPart.getNumber()%>
			/
			<%=headerPart.getVersionIdentifier().getSeries().getValue()%>.<%=headerPart.getIterationIdentifier().getSeries().getValue()%>
		</td>
		<th>유형</th>
		<td><%=PartHelper.manager.partTypeToDisplay(headerPart)%></td>
	</tr>
	<tr>
		<th>조회</th>
		<td>
			<input type="text" name="search" class="AXInput w70p">
		</td>
		<th>색상(CAD 매개변수)</th>
		<td><%=IBAUtils.getStringValue(headerPart, "COLOR")%></td>
		<th>파생색상</th>
		<td><%=header.getColorSet()%></td>
	</tr>
</table>
<br class="ebr close">
<table class="create-table ebom close" id="_tree">
	<colgroup>
		<col width="40">
		<col width="400">
		<col width="180">
		<col width="80">
		<col width="150">
		<col width="320">
		<col width="60">
		<col width="60">
		<col width="70">
		<col width="130">
		<col width="90">
		<col width="120">
		<col width="120">
		<col width="120">
	</colgroup>
	<thead>
		<tr>
			<th>&nbsp;</th>
			<th>부품번호</th>
			<th>ITEM_NAME</th>
			<th>부품유형</th>
			<th>PART_NO</th>
			<th>부품명</th>
			<th>단위</th>
			<th>버전</th>
			<th>CAD유무</th>
			<th>상태</th>
			<th>소요량</th>
			<th>기본색상</th>
			<th>적용색상</th>
			<th>ERP CODE</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td class="center"></td>
			<td></td>
			<td class="center"></td>
			<td class="center"></td>
			<td class="center"></td>
			<td class="left indent10"></td>
			<td class="center"></td>
			<td class="center"></td>
			<td class="center"></td>
			<td class="center"></td>
			<td class="right pad10"></td>
			<td class="center"></td>
			<td class="center"></td>
			<td class="center"></td>
		</tr>
	</tbody>
</table>

<script type="text/javascript">

	$(function() {
		$("input[name=search]").on("keyup", function(e) {
			var match = $(this).val();
			var n, treeRight = $.ui.fancytree.getTree("#_tree");
			var opts = {};
			var filterFunc = treeRight.filterNodes;
			n = filterFunc.call(treeRight, match, opts);
		});
	})
	var oid = "<%=header.getPersistInfo().getObjectIdentifier().getStringValue()%>";
	var color = "WW";
	<%if (header != null) {%>
	loadETree("<%=header.getPersistInfo().getObjectIdentifier().getStringValue()%>", color);
<%}%>
	function loadETree(oid, color) {
		$("#_tree").fancytree({
			extensions : [ "table", "filter", "gridnav" ],
			quicksearch : true,
			source : {
				url : "/Windchill/platform/ebom/loadPTree?oid=" + oid + "&color=" + color
			},
			filter : {
				autoApply : true, // Re-apply last filter if lazy data is loaded
				autoExpand : true, // Expand all branches that contain matches while filtered
				counter : true, // Show a badge with number of matching child nodes near parent icons
				fuzzy : false, // Match single characters in order, e.g. 'fb' will match 'FooBar'
				hideExpandedCounter : true, // Hide counter badge if parent is expanded
				hideExpanders : false, // Hide expanders if all child nodes are hidden by filter
				highlight : true, // Highlight matches by wrapping inside <mark> tags
				leavesOnly : false, // Match end nodes only
				nodata : false, // Display a 'no data' status node if result is empty
				mode : "dimm" // Grayout unmatched nodes (pass "hide" to remove unmatched node instead)
			},
			table : {
				indentation : 10,
				nodeColumnIdx : 1,
			},
			renderColumns : function(event, data) {
				var node = data.node, $tdList = $(node.tr).find(">td");
				var isLib = node.data.library;
				var key = node.key;
				if (isLib) {
					$(node.tr).css("background-color", "#fdedc4");
				}

				if("<%=number%>" == node.data.number) {
					$(node.tr).css("background-color", "#adfcc5");
				}

				$tdList.eq(0).html(node.data.thumb);
				$tdList.eq(2).text(node.data.itemName);
				var colors = "red";
				if (node.data.partType == "단품") {
					colors = "red";
				} else if (node.data.partType == "세트") {
					colors = "blue";
				} else if (node.data.partType == "자재") {
					colors = "#009300";
				}
				$tdList.eq(3).html("<b><font color='" + colors + "'>" + node.data.partType + "</font></b>");
				$tdList.eq(4).text(node.data.partNo);
				$tdList.eq(5).text(node.data.name);
				$tdList.eq(6).text(node.data.unit);
				$tdList.eq(7).text(node.data.version);
				$tdList.eq(8).text(node.data.ref);
				$tdList.eq(9).text(node.data.state);
				$tdList.eq(10).text(node.data.amount);
				$tdList.eq(11).text(node.data.color);
				$tdList.eq(12).text(node.data.applyColor);
				$tdList.eq(14).text(node.data.erpCode);
			},
			init : function() {
			},
			tooltip : function(event, data) {
				return data.node.title;
			},
		}).on("nodeCommand", function(event, data) {
			var refNode, moveMode;
			var tree = $.ui.fancytree.getTree(this);
			var node = tree.getActiveNode();
			var rootNode = tree.getRootNode();
			var key = node.key;
			var rkey = rootNode.key;
			switch (data.cmd) {
			case "l1expands":
				expands(rootNode, 1);
				break;
			case "l2expands":
				expands(rootNode, 2);
				break;
			case "l3expands":
				expands(rootNode, 3);
				break;
			case "l4expands":
				expands(rootNode, 4);
				break;
			case "l5expands":
				expands(rootNode, 5);
				break;
			case "l6expands":
				expands(rootNode, 6);
				break;
			default:
				return;
			}
		}).on("keydown", function(e) {
			var cmd = null;
			switch ($.ui.fancytree.eventToString(e)) {
			case "1":
				cmd = "l1expands";
				break;
			case "2":
				cmd = "l2expands";
				break;
			case "3":
				cmd = "l3expands";
				break;
			case "4":
				cmd = "l4expands";
				break;
			case "5":
				cmd = "l5expands";
				break;
			case "6":
				cmd = "l6expands";
				break;
			}
			if (cmd) {
				$(this).trigger("nodeCommand", {
					cmd : cmd
				});
				return false;
			}
		});
	}

	function expands(rootNode, level) {
		rootNode.visit(function(subNode) {
			subNode.setExpanded(false);

		});
		rootNode.visit(function(subNode) {
			if (subNode.getLevel() <= level) {
				subNode.setExpanded(true);
			}
		});
	}
</script>
<%
} else {
%>
<table class="create-table ebom-search close">
	<colgroup>
		<col width="40">
		<col width="400">
		<col width="180">
		<col width="80">
		<col width="150">
		<col width="320">
		<col width="60">
		<col width="60">
		<col width="70">
		<col width="130">
		<col width="90">
		<col width="120">
		<col width="120">
		<col width="120">
	</colgroup>
	<thead>
		<tr>
			<th>&nbsp;</th>
			<th>부품번호</th>
			<th>ITEM_NAME</th>
			<th>부품유형</th>
			<th>PART_NO</th>
			<th>부품명</th>
			<th>단위</th>
			<th>버전</th>
			<th>CAD유무</th>
			<th>상태</th>
			<th>소요량</th>
			<th>기본색상</th>
			<th>적용색상</th>
			<th>ERP CODE</th>
		</tr>
	</thead>
	<tr>
		<td colspan="15" class="center first">
			<font color="red">
				<b>관련된 EBOM이 없습니다.</b>
			</font>
		</td>
	</tr>
</table>
<%
}
%>