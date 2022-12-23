<%@page import="platform.util.CommonUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	boolean isAdmin = CommonUtils.isAdmin();
%>
<table id="treeRight" class="view-table">
	<thead>
		<tr>
			<th style="width: 40px; min-width: 40px;">&nbsp;</th>
			<th style="width: 300px; min-width: 300px;">부품번호</th>
			<th>ITEM_NAME</th>
			<th style="width: 50px; min-width: 50px;">부품 <br> 유형
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
			<!-- 			<td class="center"></td> -->
			<td class="center"></td>
			<td class="center"></td>
		</tr>
	</tbody>
</table>
<script type="text/javascript">
	function loadRightTree(oid) {
		$("#treeRight").fancytree({
			extensions : [ "table", "dnd5", "filter", "gridnav", "multi", "contextMenu" ],
			quicksearch : true,
			debugLevel : 0,
			source : {
				url : "/Windchill/jsp/mbom/mockup/mbom-derived-right.json"
// 				url : "/Windchill/platform/ebomMaster/loadETree?oid=" + oid
			// 				url : "/Windchill/platform/ebom/load?oid=wt.part.WTPart:196170"
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
			tooltip : function(event, data) {
				return data.node.title;
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
			gridnav : {
				autofocusInput : false,
				handleCursorKeys : true
			},
			dnd5 : {
				multiSource : true,
				scroll : true, // Enable auto-scrolling while dragging
				scrollSensitivity : 20, // Active top/bottom margin in pixel
				scrollSpeed : 5,
				dragStart : function(node, data) {
					data.effectAllowed = "all";
					data.dropEffect = data.dropEffectSuggested; // "link";
					return true;
				},

				dragEnter : function(node, data) {
					<%
						if(isAdmin) {
					%>
					return true;
					<%
						}
					%>
					var partTypeCd = data.node.data.partTypeCd;
					if (partTypeCd == "SET") {
						if (data.otherNode.data.partTypeCd == "MAT" || data.otherNode.data.partTypeCd == "SET") {
							return false;
						}
					}
					if (partTypeCd == "ITEM") {
						if (data.otherNode.data.partTypeCd == "ITEM" || data.otherNode.data.partTypeCd == "SET") {
							return false;
						}
					}

					if (partTypeCd == "MAT") {
						if (data.otherNode.data.partTypeCd == "ITEM" || data.otherNode.data.partTypeCd == "SET") {
							return false;
						}
					}

					return true;
				},
				dragOver : function(node, data) {
					return true;
				},
				dragDrop : function(node, data) {
					var newNode;
					var transfer = data.dataTransfer;
					var sourceNodes = data.otherNodeList;
					var mode = data.dropEffect;
					var copyMode = data.dropEffect !== "move";
					var sameTree = (data.otherNode.tree === data.tree);
					var hitMode = data.hitMode;
					if (hitMode == "before") {
						// 세트와 동일한 레벨로는 모든게 불가능..
						var partTypeCd = data.node.data.partTypeCd;
						if (node.data.level == 0) {
							return false;
						} else {
							if (partTypeCd == "ITEM") {
								if (data.otherNode.data.partTypeCd == "MAT") {
									return false;
								}
							}
						}

					}

					if (hitMode == "after") {
						// 세트와 동일한 레벨로는 모든게 불가능..
						var partTypeCd = data.node.data.partTypeCd;
						if (node.data.level == 0) {
							return false;
						} else {
							if (partTypeCd == "ITEM") {
								if (data.otherNode.data.partTypeCd == "MAT") {
									return false;
								}
							}
						}
					}

					if (data.hitMode === "after") {
						sourceNodes.reverse();
					}

					if (copyMode) {
						newNode = data.otherNode.copyTo(node, data.hitMode);
					}

					if (!sameTree) {
						$(data.otherNode.tr).css("background-color", "#feedf0");
						data.otherNode.data.move = true;
						move(data.otherNode.children);
					} else {
						if (data.otherNode) {
							if (data.hitMode == "over") {
								var sameNode = data.otherNode.data.number === data.node.data.number;
								if (!sameNode) {
									$.each(sourceNodes, function(i, o) {
										o.moveTo(node, data.hitMode);
									});
								} else {
									// 같은 것일 경우 수량 업데이트 기존 노드 삭제??
									$.each(sourceNodes, function(i, o) {
										var t = node.data.amount + o.data.amount;
										node.data.amount = t;
										node.renderTitle();
										o.remove();
									});
								}
							} else {
								$.each(sourceNodes, function(i, o) {
									o.moveTo(node, data.hitMode);
								});
							}
						}
					}
					// 					calculation(data.otherNode, data.otherNode.data.amount);
					node.setExpanded();
				},
			},
			contextMenu : {
				menu : {
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
						},
					},
					"sep2" : "---------",
					"fold3" : {
						"name" : "추가",
						"items" : {
							"top" : {
								"name" : "최상위부품추가"
							},
							"append" : {
								"name" : "신규부품추가"
							},
							"exist" : {
								"name" : "기존부품추가"
							},
						},
					},
				},
				actions : function(node, action, options) {
					var tree = $.ui.fancytree.getTree("#treeRight");
					var node = tree.getActiveNode();
					var rootNode = tree.getRootNode();
					// 					alert(node.data.partTypeCd);
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
					} else if (action == "root") {
						node.moveTo(tree.getFirstChild(), "firstChild");
					} else if (action == "append") {
						var url = "/Windchill/platform/part/append?partTypeCd=" + node.data.partTypeCd;
						_popup(url, 1200, 450, "n");
					} else if (action == "top") {
						var url = "/Windchill/platform/part/top?partTypeCd=" + node.data.partTypeCd;
						_popup(url, 1200, 450, "n");
					} else if (action == "exist") {
						var url = "/Windchill/platform/part/exist?box=1&partTypeCd=" + node.data.partTypeCd;
						_popup(url, "", "", "f");
					}
				}
			}
		}).on("nodeCommand", function(event, data) {
			var refNode, moveMode;
			var tree = $.ui.fancytree.getTree(this);
			var node = tree.getActiveNode();
			var rootNode = tree.getRootNode();
			var key = node.key;
			var rkey = rootNode.key;
			switch (data.cmd) {
			case "indent":
			case "moveDown":
			case "moveUp":
			case "outdent":
				tree.applyCommand(data.cmd, node);
				break;
			case "remove":
				if (!confirm("삭제 하시겠습니까?")) {
					return false;
				}
				remove.push(node.data.oid);
				tree.applyCommand(data.cmd, node);
				break
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
			case "ctrl+up":
				cmd = "moveUp";
				break;
			case "ctrl+down":
				cmd = "moveDown";
				break;
			case "ctrl+right":
				cmd = "indent";
				break;
			case "ctrl+left":
				cmd = "outdent";
				break;
			case "del":
				cmd = "remove";
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

	function move(array) {
		for (var i = 0; array != null && i < array.length; i++) {
			$(array[i].tr).css("background-color", "#feedf0");
			array[i].data.move = true;
			move(array[i].children);
		}
	}

	function isDescendantOf(target, node) {
		if (target.children == null) {
			return true;
		}

		var nodes = target.children;
		for (var i = 0; i < nodes.length; i++) {
			if (nodes[i].data.number == node.number) {
				return false;
			}
		}
		return true;
	}

	function append(newNode) {
		var tree = $.ui.fancytree.getTree("#treeRight");
		var node = tree.getActiveNode();
		if (!isDescendantOf(node, newNode)) {
			alert("중복된 부품을 추가 할 수 없습니다.");
			return false;
		}

		if (node.isRootNode()) {
			newNode.level = 0;
			var refNode = tree.getFirstChild();
			refNode.addChildren(newNode);
			newNode.moveTo(tree.getFirstChild(), "firstChild");
		} else {
			newNode.parent = node.data.oid;
			newNode.level = node.data.level + 1;
			node.addChildren(newNode);
		}
	}


	$(function() {
		$("#treeRight").tableHeadFixer();
	})
	// 	loadRightTree();
</script>