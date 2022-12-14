<%@page import="platform.mbom.entity.MBOM"%>
<%@page import="platform.util.IBAUtils"%>
<%@page import="platform.part.service.PartHelper"%>
<%@page import="wt.part.WTPart"%>
<%@page import="platform.util.CommonUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String oid = (String) request.getAttribute("oid");
MBOM header = (MBOM) CommonUtils.persistable(oid);
WTPart headerPart = header.getPart();
%>
<div class="header-title">
	<img src="/Windchill/jsp/images/home.png" class="home"> <span>HOME</span> > <span>BOM관리</span> > <span>MBOM 정보</span>
</div>
<table class="search-table top-color">
	<colgroup>
		<col width="180">
		<col width="530">
<!-- 		<col width="180"> -->
<!-- 		<col width="530"> -->
		<col width="180">
		<col width="530">
	</colgroup>
	<tr>
		<th>부품명칭(단품, 세트)</th>
		<td><input type="hidden" name="oid" class="AXInput w70p" readonly="readonly" value="<%=oid%>"> <%=headerPart.getName()%> / <%=headerPart.getNumber()%> / <%=headerPart.getVersionIdentifier().getSeries().getValue()%>.<%=headerPart.getIterationIdentifier().getSeries().getValue()%>
		</td>
		<th>유형</th>
		<td><%=PartHelper.manager.partTypeToDisplay(headerPart)%></td>
	</tr>
	<tr>
		<th>조회</th>
		<td><input type="text" name="search" class="AXInput w70p"></td>
<!-- 		<th>색상(CAD 매개변수)</th> -->
<%-- 		<td><%=IBAUtils.getStringValue(headerPart, "COLOR")%></td> --%>
		<th>파생색상</th>
		<td><%=header.getColor()%></td>
	</tr>
</table>

<table class="button-table">
	<tr>
		<td class="left"><select name="level" id="level" class="AXSelect w100px">
				<option value="">선택</option>
				<option value="0">전체축소</option>
				<option value="1">1레벨</option>
				<option value="2">2레벨</option>
				<option value="3">3레벨</option>
				<option value="4">4레벨</option>
				<option value="5">5레벨</option>
				<option value="6">6레벨</option>
				<option value="7">전체확장</option>
		</select></td>
		<td class="right">
			<button type="button" id="modifyBtn">수정</button>
			<button type="button" id="closeBtn">닫기</button>
		</td>
	</tr>
</table>


<table class="create-table" id="tree">
	<colgroup>
		<col width="40">
		<col width="80">
		<col width="60">
		<col width="*">
		<col width="250">
		<col width="100">
		<col width="80">
		<col width="200">
		<col width="250">
		<col width="100">
		<col width="100">
		<col width="100">
		<col width="100">
	</colgroup>
	<thead>
		<tr>
			<th>&nbsp;</th>
			<th>부품유형</th>
			<th>접수자</th>
			<th>품목코드(ERP CODE)</th>
			<th>품목명(PART_NAME)</th>
			<th>수량</th>
			<th>단위</th>
			<th>재질(MATERIAL)</th>
			<th>PART_NO</th>
			<th>버전</th>
			<th>적용색상</th>
			<th>규격가로(W)</th>
			<th>규격세로(D)</th>
			<th>사용높이(H)</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td class="center"></td>
			<td class="center"></td>
			<td class="center"></td>
			<td></td>
			<td class="center"></td>
			<td class="right pad10"></td>
			<td class="center"></td>
			<td class="left indent10"></td>
			<td class="center"></td>
			<td class="center"></td>
			<td class="center"></td>
			<td class="center"></td>
			<td class="center"></td>
			<td class="center"></td>
		</tr>
	</tbody>
</table>
<script type="text/javascript">
	$(function() {
		var oid = "<%=oid%>";

		loadMTree(oid);

		$("#closeBtn").click(function() {
			self.close();
		})

		$("#modifyBtn").click(function() {
			if (!confirm("수정 하시겠습니까?")) {
				return false;
			}

			var tree = $.ui.fancytree.getTree("#tree");
			var rootNode = tree.getRootNode();
			var array = rootNode.getChildren();
			var list = [];
			toJsonArray(array, list);
			var json = btoa(unescape(encodeURIComponent(JSON.stringify(list))));
			var url = "/Windchill/platform/mbom/modify";
			var color = "<%=header.getColor() %>";
			var params = new Object();
			params.json = json;
			params.oid = $("input[name=oid]").val();
			params.color = color;
			_call(url, params, function(data) {
				var tree = $.ui.fancytree.getTree("#tree");
				$("#color").unbindSelect();
				tree.clear();
				reload(tree, oid, color);
			}, "POST");
		})

		$("input[name=search]").on("keyup", function(e) {
			var _n, treeLeft = $.ui.fancytree.getTree("#tree");
			var _opts = {};
			var filterFunc = treeLeft.filterNodes;
			var match = $(this).val();
			_n = filterFunc.call(treeLeft, match, _opts);
		});

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

	})

	function getter(array, list) {
		for (var i = 0; array != null && i < array.length; i++) {
			list.push(array[i].data.poid);
			if (array[i].children !== undefined) {
				getter(array[i].children, list);
			}
		}
	}

	function toJsonArray(array, list) {
		for (var i = 0; array != null && i < array.length; i++) {
			array[i].data.key = array[i].key;
			list.push(array[i].data);
			if (array[i].children !== undefined) {
				array[i].data.parent = array[i].parent.data.poid;
				toJsonArray(array[i].children, list);
			}
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
		var tree = $.ui.fancytree.getTree("#tree");
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

	function loadMTree(oid) {
		var tree = $.ui.fancytree.getTree("#tree");
		$("#tree").fancytree({
			extensions : [ "table", "dnd5", "filter", "gridnav", "multi", "contextMenu" ],
			quicksearch : true,
			source : {
				url : "/Windchill/platform/mbom/loadMTree?oid=" + oid
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
				nodeColumnIdx : 3,
			},
			tooltip : function(event, data) {
				return data.node.title;
			},
			dnd5 : {
				multiSource : true,

				dragStart : function(node, data) {
					data.effectAllowed = "all";
					data.dropEffect = data.dropEffectSuggested; // "link";
					return true;
				},

				dragEnter : function(node, data) {
					return true;
				},
				dragOver : function(node, data) {
					data.dropEffect = data.dropEffectSuggested;
					return true;
				},
				dragDrop : function(node, data) {
					var newNode;
					var transfer = data.dataTransfer;
					var sourceNodes = data.otherNodeList;
					var mode = data.dropEffect;
					var copyMode = data.dropEffect !== "move";
					var sameTree = (data.otherNode.tree === data.tree);

					if (data.hitMode === "after") {
						sourceNodes.reverse();
					}

					if (copyMode) {
						newNode = data.otherNode.copyTo(node, data.hitMode);
					}

					if (!sameTree) {
						// 						$(data.otherNode.tr).css("background-color", "#feedf0");
						// 						data.otherNode.data.move = true;
						// 						move(data.otherNode.children);
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
					node.setExpanded();
				},
			},
			renderColumns : function(event, data) {
				var node = data.node, $tdList = $(node.tr).find(">td");
				var isLib = node.data.library;
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

				$tdList.eq(0).html("<img src='" + node.data.thumb + "' onclick=_openCreoView('" + node.data.eoid + "');>");
				var colors = "red";
				if (node.data.partType == "단품") {
					colors = "red";
				} else if (node.data.partType == "세트") {
					colors = "blue";
				} else if (node.data.partType == "자재") {
					colors = "#009300";
				}
				$tdList.eq(1).html("<b><font color='" + colors + "'>" + node.data.partType + "</font></b>");
				$tdList.eq(2).text(node.data.managerName);
				$tdList.eq(4).text(node.data.name);
				$tdList.eq(5).text(node.data.amount);
				$tdList.eq(6).text(node.data.unit);
				$tdList.eq(7).text(node.data.material);
				$tdList.eq(8).text(node.data.partNo);
				$tdList.eq(9).text(node.data.version);
				$tdList.eq(10).text(node.data.applyColor);
				$tdList.eq(11).text(node.data.width);
				$tdList.eq(12).text(node.data.height);
				$tdList.eq(13).text(node.data.depth);
			},
			init : function() {
				_selector("level");
				mask.close();
				$("#loading_layer").hide();
			},
			preInit : function() {
				mask.open();
				$("#loading_layer").show();
			},
			contextMenu : {
				menu : {
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
					"sep3" : "---------",
					"fold2" : {
						"name" : "구조변경",
						"items" : {
							"root" : {
								"name" : "최상위"
							},
							"moveUp" : {
								"name" : "위로"
							},
							"moveDown" : {
								"name" : "아래로"
							},
							"outdent" : {
								"name" : "왼쪽"
							},
							"indent" : {
								"name" : "오른쪽"
							},
						},
					},
				},
				actions : function(node, action, options) {
					var tree = $.ui.fancytree.getTree("#tree");
					var node = tree.getActiveNode();
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
					} else if (action == "moveUp" || action == "moveDown" || action == "indent" || action == "outdent") {
						tree.applyCommand(action, node);
					} else if (action == "root") {
						node.moveTo(tree.getFirstChild(), "firstChild");
					} else if (action == "append") {
						var url = "/Windchill/platform/mbom/append?partTypeCd=" + node.data.partTypeCd;
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
	
	function reload(tree, oid, color) {
		var url = "/Windchill/platform/mbom/reloadMTree?oid=" + oid + "&color=" + color;
		var params = new Object();
		_call(url, params, function(data) {
			tree.reload(data.data);
		}, "GET");
	}
</script>
