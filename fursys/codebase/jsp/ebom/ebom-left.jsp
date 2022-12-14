<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<table id="treeLeft" class="view-table">
	<thead>
		<tr>
			<th style="width: 40px; min-width: 40px;">&nbsp;</th>
			<th style="width: 300px; min-width: 300px;">부품번호</th>
			<th>ITEM_NAME</th>
			<th style="width: 50px; min-width: 50px;">
				부품
				<br>
				유형
			</th>
			<th style="width: 120px; min-width: 120px;">PART_NO</th>
			<th>부품명</th>
<!-- 			<th style="width: 70px; min-width: 70px;">상태</th> -->
			<th style="width: 40px; min-width: 40px;">버전</th>
			<th style="width: 50px; min-width: 50px;">수량</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td class="center first"></td>
			<td class="left"></td>
			<td class="center"></td>
			<td class="center"></td>
			<td class="center"></td>
			<td class="center"></td>
			<td class="center"></td>
<!-- 			<td class="center"></td> -->
			<td class="center"></td>
		</tr>
	</tbody>
</table>
<script type="text/javascript">
	function loadLeftTree(oid) {
		$("#treeLeft").fancytree({
			extensions : [ "table", "dnd5", "filter", "gridnav", "multi", "contextMenu" ],
			quicksearch : true,
			source : {
				url : "/Windchill/platform/ebomMaster/loadTree?oid=" + oid
			// 				url : "/Windchill/platform/ebom/loadTree?oid=wt.part.WTPart:196170"
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
				indentation : 8,
				nodeColumnIdx : 1,
			},
			renderColumns : function(event, data) {
				var node = data.node, $tdList = $(node.tr).find(">td");
				var isLib = node.data.library;
				var key = node.key;
				var partType = node.data.partType;
				if (isLib) {
					$(node.tr).css("background-color", "#fdedc4");
				} else {
					if (partType == "단품") {
						$(node.tr).css("background-color", "#d1fcd5");
					} else if (partType == "세트") {
						$(node.tr).css("background-color", "#ffe6e6");
					}
				}
// 				$tdList.eq(0).html(node.data.thumb);
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
// 				$tdList.eq(6).text(node.data.state);
				$tdList.eq(6).text(node.data.version);
				$tdList.eq(7).text(node.data.amount);
			},
			init : function() {
				mask.close();
				$("#loading_layer_open").hide();
			},
			preInit : function() {
				mask.open();
				$("#loading_layer_open").show();
			},
			gridnav : {
				autofocusInput : false,
				handleCursorKeys : true
			},
			tooltip : function(event, data) {
				return data.node.title;
			},
			dnd5 : {
				multiSource : true,

				dragStart : function(node, data) {
					// 어케할지...
					if (node.data.move) {
						alert("이미 구조편집한 데이터 입니다.");
						return false;
					}

					data.effectAllowed = "all";
					data.dropEffect = "copy";
					return true;
				},

				dragEnter : function(node, data) {
					var sameTree = (data.otherNode.tree === data.tree);
					if (sameTree) {
						return false;
					}
					return true;
				},

				dragOver : function(node, data) {
					data.dropEffect = data.dropEffectSuggested;
					return true;
				},
				dragDrop : function(node, data) {
					return true;
				},
			},

			contextMenu : {
				menu : {
					"article" : {
						"name" : "정리",
						"icon" : "edit"
					},
					"sep2" : "---------",
					"fold1" : {
						"name" : "확장",
						"items" : {
							"l1expands" : {
								"name" : "1레벨"
							},
							"l2expands" : {
								"name" : "2레벨",
							},
							"l3expands" : {
								"name" : "3레벨"
							},
							"l4expands" : {
								"name" : "4레벨"
							},
							"l5expands" : {
								"name" : "5레벨"
							},
							"expands" : {
								"name" : "전체확장"
							},
							"collapse" : {
								"name" : "전체축소"
							},
						}
					},
				},
				actions : function(node, action, options) {
					var tree = $.ui.fancytree.getTree("#treeLeft");
					var rootNode = tree.getRootNode();
					if (action == "expands") {
						tree.expandAll(true);
					} else if (action == "collapse") {
						tree.expandAll(false);
					} else if (action == "l1expands") {
						expands(rootNode, 1);
					} else if (action == "l2expands") {
						expands(rootNode, 2);
					} else if (action == "l3expands") {
						expands(rootNode, 3);
					} else if (action == "l4expands") {
						expands(rootNode, 4);
					} else if (action == "l5expands") {
						expands(rootNode, 5);
					} else if (action == "article") {
						var list = [];
						var array = rootNode.getChildren();
						article(array);
						list = clear(array, list);
						expands(rootNode, 1);
						remove(list);
						clear();
					}
				}
			}
		})
	}
	function article(array) {
		var tree = $.ui.fancytree.getTree("#treeLeft");
		for (var i = 0; array != null && i < array.length; i++) {
			if (array[i].children !== undefined) {
				var p = array[i].parent.title;
				var idx = p.indexOf("ARTICLE");
				if (idx > -1) {
					array[i].moveTo(tree.getFirstChild(), "firstChild");
				}
				if (array[i] != null) {
					article(array[i].children);
				}
			}
		}
	}

	function clear(array, list) {
		var tree = $.ui.fancytree.getTree("#treeLeft");
		for (var i = 0; array != null && i < array.length; i++) {
			list.push(array[i]);
			if (array[i].children != null) {
				clear(array[i].children, list);
			}
		}
		return list;
	}

	function remove(list) {
		for (var i = 0; i < list.length; i++) {
			var p = list[i].title;
			var idx = p.indexOf("ARTICLE");
			if (idx > -1) {
				// 				list[i].remove();
			}
		}
	}

	$(function() {
		$("#treeLeft").tableHeadFixer();
	})
	// 	loadLeftTree();
</script>
