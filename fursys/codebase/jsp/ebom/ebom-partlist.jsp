<%@page import="platform.ebom.entity.EBOMMaster"%>
<%@page import="platform.ebom.entity.EBOM"%>
<%@page import="platform.util.IBAUtils"%>
<%@page import="platform.part.service.PartHelper"%>
<%@page import="wt.part.WTPart"%>
<%@page import="platform.util.CommonUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String oid = (String) request.getAttribute("oid");
EBOMMaster header = (EBOMMaster) CommonUtils.persistable(oid);
WTPart headerPart = PartHelper.manager.getLatest(header.getPart());
%>
<div class="header-title">
	<img src="/Windchill/jsp/images/home.png" class="home">
	<span>HOME</span>
	>
	<span>BOM관리</span>
	>
	<span>PARTLIST 수정</span>
</div>

<div id="tabs"></div>
<br>

<table class="search-table top-color">
	<colgroup>
		<col width="170">
		<col width="650">
		<col width="150">
		<col width="580">
		<col width="150">
		<col width="580">
	</colgroup>
	<tr>
		<th>부품명칭(단품, 세트)</th>
		<td>
			<input type="hidden" name="partlist">
			<input type="hidden" name="edited" value="false">
			<input type="hidden" name="oid" class="AXInput w70p" readonly="readonly" value="<%=oid%>">
			<%=headerPart.getName()%>
			/
			<%=headerPart.getNumber()%>
			/
			<%=headerPart.getVersionIdentifier().getSeries().getValue()%>.<%=headerPart.getIterationIdentifier().getSeries().getValue()%>
		</td>
		<th>재료비/가공비</th>
		<td>
			<span id="dcost"><%=header.getDcost() != null ? header.getDcost() : 0%>원</span>
			/
			<span id="pcost"><%=header.getPcost() != null ? header.getPcost() : 0%>원</span>
		</td>
		<th>유형</th>
		<td><%=PartHelper.manager.partTypeToDisplay(headerPart)%></td>
	</tr>
	<tr>
		<th>조회</th>
		<td colspan="3">
			<input type="text" name="search" class="AXInput w70p">
		</td>
<!-- 		<th>색상(CAD 매개변수)</th> -->
<%-- 		<td><%=IBAUtils.getStringValue(headerPart, "COLOR")%></td> --%>
		<th>파생색상</th>
		<td>
			<select name="color" id="color" class="AXSelect w200px">
				<option value="">선택</option>
				<%
				String[] s = header.getDerivedColor().split(",");
				for (String color : s) {
				%>
				<option value="<%=color%>"><%=color%></option>
				<%
				}
				%>
			</select>
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
			<span id="edited"></span>
		</td>
		<td class="right">
			<button type="button" id="erpToMoney">추정원가 조회</button>
			<button type="button" id="nonBomBtn">원/부자재 생성</button>
			<button type="button" id="applyBtn">색상변경</button>
			<!-- 			<button type="button" class="saveBtn" data-type="t">ㄲ</button> -->
			<button type="button" class="saveBtn" data-type="m">저장</button>
			<button type="button" id="closeBtn">닫기</button>
		</td>
	</tr>
</table>


<table class="create-table tree" id="tree">
	<colgroup>
		<col width="40">
		<col width="80">
		<col width="*">
		<col width="250">
		<col width="100">
		<col width="100">
		<col width="200">
		<col width="250">
		<col width="100">
	</colgroup>
	<thead>
		<tr>
			<th>&nbsp;</th>
			<th>부품유형</th>
			<th>품목코드(ERP CODE)</th>
			<th>품목명(PART_NAME)</th>
			<th>수량</th>
			<th>단위</th>
			<th>재질(MATERIAL)</th>
			<th>PART_NO</th>
			<th>버전</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td class="center"></td>
			<td class="center"></td>
			<td></td>
			<td class="left indent10"></td>
			<td class="right pad10"></td>
			<td class="center"></td>
			<td class="left indent10"></td>
			<td class="center"></td>
			<td class="center"></td>
		</tr>
	</tbody>
</table>

<table class="create-table partlist close" id="_tree">
	<colgroup>
		<col width="40">
		<col width="80">
		<col width="100">
		<col width="*">
		<col width="250">
		<col width="100">
		<col width="100">
		<col width="200">
		<col width="250">
		<col width="100">
		<col width="100">
		<col width="110">
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
			<th>색상변경</th>
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
		</tr>
	</tbody>
</table>

<script type="text/javascript">
	var nodes = new Array();
	$(function() {
		var oid = "<%=oid%>";
		// 컬러 셋 설정
		$("#_tree").on("click", "input[name=managerName]", function(e) {
			var node = $.ui.fancytree.getNode(e);
			var target = node.key;
			var url = "/Windchill/platform/user/popup?target=" + target + "&callBack=manager";
			_popup(url, 1200, 650, "n");
		});

		$("#erpToMoney").click(function() {
			var tree = $.ui.fancytree.getTree("#_tree");
			if (tree == null) {
				alert("추정원가 조회를 위한 PART LIST를 먼저 선택하세요.");
				return false;
			}
			if (!confirm("추정원가를 조회 하시겠습니까?")) {
				return false;
			}
			var oid = $("input[name=partlist]").val();
			var color = $("#color").val();
// 			var url = "/Windchill/platform/erp/getPCost";
// 			var params = new Object();
// 			params.oid = oid;
// 			params.color = color;
// 			_call(url, params, function(data) {
// 				var dcost = data.dcost;
// 				$("span#dcost").text(dcost + "원");
// 			}, "POST");
			
			
			var _url = "http://192.8.231.236:8081/dtIf/pcost";
			var _params = new Object();
			_params.code = "std_side_l_assy_3.asm";
			_params.API_TOKEN = "WMJMC4Bxm4TrK4YdNXmzMhb4fXyRWg3Yn6nQgZ5Cg5k2cuXM7G";
			_call(_url, _params, function(data) {
				console.log(data);
			}, "POST");
			
		})

		$("#nonBomBtn").click(function() {
			var tree = $.ui.fancytree.getTree("#_tree");
			if (tree == null) {
				alert("원/부자재 생성을 위한 PART LIST를 먼저 선택하세요.");
				return false;
			}
			if (!confirm("원/부자재를 생성 하시겠습니까?")) {
				return false;
			}
			var url = _url("/erp/structure");
			var params = new Object();
			var tree = $.ui.fancytree.getTree("#_tree");
			var rootNode = tree.getRootNode();
			var array = rootNode.getChildren();
			var list = [];
			var color = $("select[name=color] option:selected").val();
			toJsonArray(array, list);
			var json = btoa(unescape(encodeURIComponent(JSON.stringify(list))));
			params.json = json;
			params.color = color;
			_call(url, params, function(data) {
				loadPTree(oid, color);
			}, "POST");
		})

		$(".saveBtn").click(function() {
			var type = $(this).data("type");
			var tree = $.ui.fancytree.getTree("#_tree");
			if (tree == null) {
				alert("저장할 PART LIST를 먼저 선택하세요.");
				return false;
			}

			var rootNode = tree.getRootNode();
			var array = rootNode.getChildren();
			var list = [];
			toJsonArray(array, list);
			<%
				if(!CommonUtils.isAdmin()) {
			%>
			if (type == "m") {
				for (var i = 0; i < list.length; i++) {
					var applyColor = list[i].applyColor;
					if (applyColor == undefined) {
						alert("적용색상값이 입력되 않은 부품이 있습니다. \n부품번호 = " + list[i].number);
						return false;
					}
					if(list[i].partType == "단품" && list[i].managerName == "") {
						alert("담당자가 지정되지 않은 부품이 있습니다. \n부품번호 = " + list[i].number);
						return false;
					}
				}
			}
			<%
				}
			%>
			var color = $("select[name=color] option:selected").val();
			var json = btoa(unescape(encodeURIComponent(JSON.stringify(list))));
			var url = "/Windchill/platform/ebomMaster/color";
			var params = new Object();
			params.json = json;
			params.type = type;
			params.oid = oid;
			if (!confirm("저장 하시겠습니까?")) {
				return false;
			}
			_call(url, params, function(data) {
				clear();
				loadPTree(oid, color);
			}, "POST");
		})

		$(document).on("click", ".helper-checks-checkbox-c1", function(e) {
			var node = $.ui.fancytree.getNode(e);
	
			if (node.data.library) {
				alert("라이브러리는 색상변경이 불가능 합니다.");
				return false;
			}

			// 			if (!node.data.isColor) {
			// 				alert("COLOR 변경 설정이 안되어 있습니다.");
			// 				return false;
			// 			}

			// 			if (node.data.isColorSet) {
			// 				alert("COLOR SET 설정이 되어진 부품은 색상변경이 불가능합니다.");
			// 				return false;
			// 			}

			var list = [];
			list.push(node);
			getter(node.children, list);
			for(var i=0; i<list.length; i++) {
				var n = list[i];
				var key = n.key;
				if ($(this).hasClass("sed")) {
					$(".c1" + n.key).next().addClass("sed");
					$(n.tr).css("background-color", "#dff4ff");
					nodes.push(n);
				} else {
					$(n.tr).css("background-color", "white");
					$(".c1" + n.key).next().removeClass("sed");
					var filterArr = nodes.filter(function(data) {
						return data.key !== n.key;
					});
					nodes = filterArr;
				}
			}
		})

		$("#applyBtn").click(function() {
			var tree = $.ui.fancytree.getTree("#_tree");
			if (tree == null) {
				alert("PART LIST를 먼저 선택하세요.");
				return false;
			}

			if (nodes.length == 0) {
				alert("색상변경 할 대상을 선택하세요.");
				return false;
			}

			var url = "/Windchill/platform/baseCode/popup?box=2&codeType=COLOR";
			_popup(url, 1200, 700, "n");
		})

		$("#level").change(function() {
			var level = $(this).val();
			var tree = $.ui.fancytree.getTree("#tree");
			var _tree = $.ui.fancytree.getTree("#_tree");
			var root = tree.getRootNode();
			var _root = null;
			if (_tree != null) {
				_root = _tree.getRootNode();
			}
			if (level == "0") {
				tree.expandAll(false);
				if (_tree != null) {
					_tree.expandAll(false);
				}
			} else if (level == "1") {
				expands(root, 1);
				if (_tree != null) {
					expands(_root, 1);
				}
			} else if (level == "2") {
				expands(root, 2);
				if (_tree != null) {
					expands(_root, 2);
				}
			} else if (level == "3") {
				expands(root, 3);
				if (_tree != null) {
					expands(_root, 3);
				}
			} else if (level == "4") {
				expands(root, 4);
				if (_tree != null) {
					expands(_root, 4);
				}
			} else if (level == "5") {
				expands(root, 5);
				if (_tree != null) {
					expands(_root, 5);
				}
			} else if (level == "6") {
				expands(root, 6);
				if (_tree != null) {
					expands(_root, 6);
				}
			} else if (level == "7") {
				tree.expandAll(true);
				if (_tree != null) {
					_tree.expandAll(true);
				}
			}
		})

		$("#closeBtn").click(function() {
			self.close();
		})

		$("#color").change(function() {
			var color = $(this).val();
			var edited = $("input[name=edited").val();
			if(color == "") {
				return false;
			}
			
			if(edited == "true") {
				alert("변경사항이 있습니다. 수정 내용을 먼저 저장해주세요.");
				return false;
			}
			
			$("#nonBomBtn").prop("disabled", false);
			loadPTree(oid, color);
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
				url : "/Windchill/platform/ebomMaster/loadETree?oid=" + oid
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
				nodeColumnIdx : 2,
			},
			tooltip : function(event, data) {
				return data.node.title;
			},
			renderColumns : function(event, data) {
				var node = data.node, $tdList = $(node.tr).find(">td");
				var isLib = node.data.library;
				var key = node.key;
				var partTypeCd = node.data.partTypeCd;
				if (isLib) {
					$(node.tr).css("background-color", "#fdedc4");
				} else {
					if (partTypeCd == "ITEM") {
						$(node.tr).css("background-color", "#d1fcd5");
					} else if (partTypeCd == "SET") {
						$(node.tr).css("background-color", "#ffe6e6");
					}
				}
				var colors = "red";
				if (node.data.partType == "단품") {
					colors = "red";
				} else if (node.data.partType == "세트") {
					colors = "blue";
				} else if (node.data.partType == "자재") {
					colors = "#009300";
				}
	
				$tdList.eq(0).html("<img src='" + node.data.thumb + "' onclick=_openCreoView('" + node.data.eoid + "');>");
				$tdList.eq(1).html("<b><font color='" + colors + "'>" + node.data.partType + "</font></b>");
				$tdList.eq(3).text(node.data.name);
				$tdList.eq(4).text(node.data.amount);
				$tdList.eq(5).text(node.data.unit);
				$tdList.eq(6).text(node.data.material);
				$tdList.eq(7).text(node.data.partNo);
				$tdList.eq(8).text(node.data.version);
			},
			init : function() {
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
		$("#tabs").bindTab({
			theme : "AXTabs",
			value : "1",
			overflow : "scroll", /* "visible" */
			options : [ {
				optionValue : "1",
				optionText : "EBOM",
			}, {
				optionValue : "2",
				optionText : "PART LIST",
			} ],
			onchange : function(selectedObject, value) {
				if (value == "1") {
					$(".tree").show();
					$(".partlist").hide();
					$("#color").unbindSelect();
					_selector("color");
					$("#color").bindSelectDisabled(true);
				} else if (value == "2") {
					$(".tree").hide();
					$(".partlist").show();
					$("#color").unbindSelect();
					_selector("color");
				}
			}
		})
	})

	function loadPTree(oid, color) {
		$("input[name=partlist]").val(oid);
		var tree = $.ui.fancytree.getTree("#_tree");
		if (tree !== null) {
			$("#color").unbindSelect();
			tree.clear();
			reload(tree, oid, color);
		} else {
			$("#_tree").fancytree({
				extensions : [ "table", "filter", "gridnav" ],
				quicksearch : true,
				source : {
					url : "/Windchill/platform/ebomMaster/loadPTree?oid=" + oid + "&color=" + color
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
					if (partType == "단품") {
						$tdList.eq(2).html("<input type='hidden' name='manager' value='" + node.data.manager + "'><input type='text' value='" + node.data.managerName + "' name='managerName' class='AXInput w80p managerName" + node.key + "' readonly='readonly'>");
					} else {
						$tdList.eq(2).html("");
					}
					$tdList.eq(4).text(node.data.name);
					$tdList.eq(5).text(node.data.amount);
					$tdList.eq(6).text(node.data.unit);
					$tdList.eq(7).text(node.data.material);
					$tdList.eq(8).text(node.data.partNo);
					$tdList.eq(9).text(node.data.version);
					$tdList.eq(10).text(node.data.applyColor);
					if(node.data.isRoot) {
						$tdList.eq(11).text("");
					} else {
						$tdList.eq(11).html("<input type='checkbox' class='isBox c1" + node.key + "' name='c1' value='true'>");
					}
				},
				init : function() {
					$("input[name=c1]").checks();
					mask.close();
					$("#loading_layer").hide();
					_selector("color");
					$("#color").bindSelectDisabled(false);
				},
				preInit : function() {
					mask.open();
					$("#loading_layer").show();
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

	function reload(tree, oid, color) {
		var url = "/Windchill/platform/ebomMaster/reloadPTree?oid=" + oid + "&color=" + color;
		var params = new Object();
		_call(url, params, function(data) {
			tree.reload(data.data);
		}, "GET");
	}

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
		_selector("color");
	}

	function manager(userName, oid, key) {
		var tree = $.ui.fancytree.getTree("#_tree");
		var node = tree.getNodeByKey(key);
		node.setSelected(false); // 전환
		node.data.manager = oid;
		node.data.managerName = userName;
		node.renderTitle();
		$(".manager" + key).val(userName);
		$(".c1" + key).removeClass("isBox");
		_setBoxs("input[name=c1]");
		edited();
	}

	function color(items) {
		var tree = $.ui.fancytree.getTree("#_tree");
		var color = items[0].item.code;

		nodes.forEach(function(node) {
			var key = node.key;
			node.setSelected(false); // 전환
			node.data.applyColor = color;
// 			$(node.tr).css("background-color", "#dcfdfe");
			$(node.tr).css("background-color", "white");
			node.renderTitle();
			$(".c1" + key).removeClass("isBox");
			// 			$(".s1" + key).removeClass("isBox");
		})
		_setBoxs("input[name=c1]");
		nodes = [];
		edited();
	}
	
	function clear() {
		$("input[name=edited]").val("false");
		$("#edited").html("");
	}
	
	function edited() {
		$("input[name=edited]").val("true");
		$("#edited").html("<font color='red'><b>* 변경사항이 있습니다. 저장을 하지 않을시 수정 내용은 저장되지 않습니다.</b></font>");
	}

	
	function getter(nodes, list) {
		for (var i = 0; nodes != null && i < nodes.length; i++) {
			list.push(nodes[i]);
			if (nodes[i].children !== undefined) {
				getter(nodes[i].children, list);
			}
		}
	}
	
	function toJsonArray(array, list) {
		for (var i = 0; array != null && i < array.length; i++) {
			list.push(array[i].data);
			if (array[i].children !== undefined) {
				array[i].data.parent = array[i].parent.data.oid;
				toJsonArray(array[i].children, list);
			}
		}
	}
</script>
