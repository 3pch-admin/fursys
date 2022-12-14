<%@page import="platform.util.IBAUtils"%>
<%@page import="platform.ebom.entity.EBOM"%>
<%@page import="platform.part.service.PartHelper"%>
<%@page import="wt.part.WTPart"%>
<%@page import="platform.util.CommonUtils"%>
<%-- <%@page import="platform.ebom.entity.EBOMHeader"%> --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String oid = (String) request.getAttribute("oid");
EBOM header = (EBOM) CommonUtils.persistable(oid);
WTPart headerPart = header.getPart();
// EBOMHeader header = (EBOMHeader) CommonUtils.persistable(oid);
// WTPart headerPart = header.getPart();
String color = header.getColor();
%>
<div class="header-title">
	<img src="/Windchill/jsp/images/home.png" class="home">
	<span>HOME</span>
	>
	<span>BOM관리</span>
	>
	<span>EBOM & PARTLIST 정보</span>
</div>

<table class="search-table top-color">
	<colgroup>
		<col width="150">
		<col width="580">
		<!-- 		<col width="180"> -->
		<!-- 		<col width="530"> -->
		<col width="150">
		<col width="580">
	</colgroup>
	<tr>
		<th>부품명칭(단품, 세트)</th>
		<td>
			<input type="hidden" name="oid" class="AXInput w70p" readonly="readonly" value="<%=oid%>">
			<%=headerPart.getName()%>
			/
			<%=headerPart.getNumber()%>
			/
			<%=headerPart.getVersionIdentifier().getSeries().getValue()%>.<%=headerPart.getIterationIdentifier().getSeries().getValue()%></td>
		<th>유형</th>
		<td><%=PartHelper.manager.partTypeToDisplay(headerPart)%></td>
	</tr>
	<tr>
		<th>조회</th>
		<td>
			<input type="text" name="search" class="AXInput w70p">
		</td>
		<!-- 		<th>색상(CAD 매개변수)</th> -->
		<%-- 		<td><%=IBAUtils.getStringValue(headerPart, "COLOR")%></td> --%>
		<th>파생색상</th>
		<td><%=header.getColor()%>
		</td>
	</tr>
</table>

<table class="button-table">
	<tr>
		<td class="left">
			<select name="level" id="level" class="AXSelect w100px">
				<option value="">선택</option>
				<option value="0">전체축소</option>
				<option value="1">1레벨</option>
				<option value="2">2레벨</option>
				<option value="3">3레벨</option>
				<option value="4">4레벨</option>
				<option value="5">5레벨</option>
				<option value="6">6레벨</option>
				<option value="7">전체확장</option>
			</select>
		</td>
		<td class="right">
			<button type="button" id="closeBtn">닫기</button>
		</td>
	</tr>
</table>


<table class="create-table tree" id="tree">
	<colgroup>
		<col width="40">
		<col width="400">
		<col width="180">
		<col width="90">
		<col width="130">
		<col width="280">
		<col width="80">
		<col width="60">
		<col width="70">
		<col width="130">
		<col width="100">
		<col width="110">
		<!-- 		<col width="110"> -->
		<!-- 		<col width="80"> -->
		<col width="130">
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
			<th>수량</th>
			<!-- 			<th>기본색상</th> -->
			<th>적용색상</th>
			<!-- 			<th>색상변경</th> -->
			<!-- 			<th>COLOR SET선택</th> -->
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
			<!-- 			<td class="center"></td> -->
			<td class="center"></td>
			<!-- 			<td class="center"></td> -->
			<td class="center"></td>
		</tr>
	</tbody>
</table>

<table class="create-table partlist close" id="_tree">
	<colgroup>
		<col width="40">
		<col width="400">
		<col width="180">
		<col width="100">
		<col width="90">
		<col width="130">
		<col width="280">
		<col width="80">
		<col width="60">
		<col width="70">
		<col width="130">
		<col width="100">
		<!-- 		<col width="110"> -->
		<col width="110">
		<!-- 		<col width="80"> -->
		<col width="130">
	</colgroup>
	<thead>
		<tr>
			<th>&nbsp;</th>
			<th>부품번호</th>
			<th>ITEM_NAME</th>
			<th>접수자</th>
			<th>부품유형</th>
			<th>PART_NO</th>
			<th>부품명</th>
			<th>단위</th>
			<th>버전</th>
			<th>CAD유무</th>
			<th>상태</th>
			<th>수량</th>
			<!-- 			<th>기본색상</th> -->
			<th>적용색상</th>
			<!-- 			<th>색상변경</th> -->
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
			<td class="center"></td>
			<td class="left indent10"></td>
			<td class="center"></td>
			<td class="center"></td>
			<td class="center"></td>
			<td class="center"></td>
			<td class="right pad10"></td>
			<!-- 			<td class="center"></td> -->
			<td class="center"></td>
			<!-- 			<td class="center"></td> -->
			<td class="center"></td>
		</tr>
	</tbody>
</table>

<script type="text/javascript">
	var nodes = new Array();
	$(function() {
		var oid = "<%=oid%>";
		var color = "<%=color%>";
		$("#level").change(function() {
			var level = $(this).val();
			var tree = $.ui.fancytree.getTree("#tree");
			var root = tree.getRootNode();
			if (level == "0") {
				tree.expandAll(false);
			} else if (level == "1") {
				expands(root, 1);
			} else if (level == "2") {
				expands(root, 2);
			} else if (level == "3") {
				expands(root, 3);
			} else if (level == "4") {
				expands(root, 4);
			} else if (level == "5") {
				expands(root, 5);
			} else if (level == "6") {
				expands(root, 6);
			} else if (level == "7") {
				tree.expandAll(true);
			}
		})

		$("#closeBtn").click(function() {
			self.close();
		})

		$("input[name=search]").on("keyup", function(e) {
			var _n, treeLeft = $.ui.fancytree.getTree("#tree");
			var _opts = {};
			var filterFunc = treeLeft.filterNodes;
			var match = $(this).val();
			_n = filterFunc.call(treeLeft, match, _opts);

			var n, treeRight = $.ui.fancytree.getTree("#_tree");
			var opts = {};
			var filterFunc = treeRight.filterNodes;
			n = filterFunc.call(treeRight, match, opts);
		});

		$("#tree").fancytree({
			extensions : [ "table", "filter", "gridnav" ],
			quicksearch : true,
			source : {
				url : "/Windchill/platform/ebom/loadPLTree?oid=" + oid + "&color=" + color
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
			tooltip : function(event, data) {
				return data.node.title;
			},
			renderColumns : function(event, data) {
				var node = data.node, $tdList = $(node.tr).find(">td");
				var isLib = node.data.library;
				var key = node.key;
				if (isLib) {
					$(node.tr).css("background-color", "#fdedc4");
				}

				$tdList.eq(0).html("<img src='" + node.data.thumb + "' onclick=_openCreoView('" + node.data.eoid + "');>");
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
				// 				$tdList.eq(11).text(node.data.color);
				$tdList.eq(11).text(node.data.applyColor);
				// 				$tdList.eq(12).html("<input type='checkbox' name='c'" + (node.data.isColor == true ? "checked='checked'" : '') + "value='true' disabled='disabled'>");
				// 				$tdList.eq(13).html("<input type='checkbox' name='s'" + (node.data.isColorSet == true ? "checked='checked'" : '') + " value='true' disabled='disabled'>");
				$tdList.eq(12).text(node.data.erpCode);
			},
			init : function() {
				$("input[name=c]").checks();
				// 				$("input[name=s]").checks();
				mask.close();
				$("#loading_layer").hide();
				_selector("color");
				$("#color").bindSelectDisabled(true);
			},
			preInit : function() {
				mask.open();
				$("#loading_layer").show();
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

		_selector("level");
	})

	function expands(rootNode, level) {
		$("#color").unbindSelect();
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
