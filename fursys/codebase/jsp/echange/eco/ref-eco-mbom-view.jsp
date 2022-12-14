<%@page import="platform.mbom.entity.MBOMHeaderECOLink"%>
<%@page import="platform.mbom.entity.MBOMHeader"%>
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
ECO eco = (ECO) CommonUtils.persistable(oid);
MBOMHeader header = null;
WTPart headerPart = null;
QueryResult result = PersistenceHelper.manager.navigate(eco, "header", MBOMHeaderECOLink.class);
if (result.hasMoreElements()) {
	header = (MBOMHeader) result.nextElement();
	headerPart = header.getPart();
}

if (header != null) {
%>
<table class="create-table mbom close" id="_mtree">
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
			var n, treeRight = $.ui.fancytree.getTree("#_mtree");
			var opts = {};
			var filterFunc = treeRight.filterNodes;
			n = filterFunc.call(treeRight, match, opts);
		});
	})
	var oid = "<%=header.getPersistInfo().getObjectIdentifier().getStringValue()%>";
	<%if (header != null) {%>
	var color = "<%=header.getColor()%>";
	loadMTree("<%=header.getPersistInfo().getObjectIdentifier().getStringValue()%>", color);
<%}%>
	function loadMTree(oid, color) {
		var tree = $.ui.fancytree.getTree("#_mtree");
		if (tree !== null) {
			$("#mcolor").unbindSelect();
			tree.clear();
			mreload(tree, oid, color);
		} else {
			$("#_mtree").fancytree({
				extensions : [ "table", "filter", "gridnav" ],
				quicksearch : true,
				source : {
					url : "/Windchill/platform/mbom/loadMTree?oid=" + oid + "&color=" + color
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

					$tdList.eq(0).html("<img src='" + node.data.thumb + "'>");
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
					$("input[name=c1]").checks();
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
	}

	function mreload(tree, oid, color) {
		var url = "/Windchill/platform/mbom/reloadMTree?oid=" + oid + "&color=" + color;
		var params = new Object();
		_call(url, params, function(data) {
			tree.reload(data.data);
			_selector("mcolor");
		}, "GET");
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

<table class="create-table mbom-search close">
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
				<b>관련된 MBOM이 없습니다.</b>
			</font>
		</td>
	</tr>
</table>
<%
}
%>