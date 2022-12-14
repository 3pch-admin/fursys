<%@page import="platform.ebom.entity.EBOM"%>
<%@page import="platform.util.IBAUtils"%>
<%@page import="platform.part.service.PartHelper"%>
<%@page import="wt.part.WTPart"%>
<%@page import="platform.util.CommonUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="header-title">
	<img src="/Windchill/jsp/images/home.png" class="home">
	<span>HOME</span>
	>
	<span>라이브러리 컬러셋</span>
	>
	<span>라이브러리 컬러셋 등록</span>
</div>
<table class="search-table top-color">
	<colgroup>
		<col width="150">
		<col width="580">
		<col width="150">
		<col width="580">
		<!-- 		<col width="180"> -->
		<!-- 		<col width="530"> -->
	</colgroup>
	<tr>
		<th>부품명칭(단품, 세트)</th>
		<td>
			<input type="hidden" name="oid" class="AXInput w70p" readonly="readonly">
			<input type="text" name="number" class="AXInput w70p" readonly="readonly" placeholder="클릭하여 편집할 부품을 선택하세요.">
		</td>
		<th>유형</th>
		<td>
			<input type="text" name="partType" class="AXInput w30p" readonly="readonly">
		</td>
	</tr>
	<tr>
		<th>조회</th>
		<td>
			<!-- 			<input type="text" name="search" class="AXInput w70p"> -->
			<input type="text" name="search" class="AXInput w70p" readonly="readonly">
		</td>
		<th>색상(CAD 매개변수)</th>
		<td>
			<input type="text" name="color" readonly="readonly" class="AXInput w40p">
		</td>
	</tr>
</table>

<table class="button-table">
	<tr>
		<td class="right">
			<button type="button" id="saveBtn">저장</button>
			<button type="button" id="applyBtn">색상변경</button>
			<button type="button" id="closeBtn">닫기</button>
		</td>
	</tr>
</table>


<table class="create-table" id="tree">
	<colgroup>
		<col width="40">
		<col width="*">
		<col width="250">
		<col width="90">
		<col width="130">
		<col width="350">
		<col width="100">
		<col width="100">
		<col width="100">
	</colgroup>
	<thead>
		<tr>
			<th>&nbsp;</th>
			<th>부품번호</th>
			<th>ITEM_NAME</th>
			<th>부품유형</th>
			<th>PART_NO</th>
			<th>부품명</th>
			<th>버전</th>
			<th>색상</th>
			<th></th>
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
		</tr>
	</tbody>
</table>
<script type="text/javascript">
	var nodes = new Array();
	$(function() {
		$("#closeBtn").click(function() {
			self.close();
		})

			$("#saveBtn").click(function() {
			var tree = $.ui.fancytree.getTree("#tree");
			if (tree == null) {
				return false;
			}
			var rootNode = tree.getRootNode();
			var array = rootNode.getChildren();
			var list = [];
			toJsonArray(array, list);
			var json = btoa(unescape(encodeURIComponent(JSON.stringify(list))));
			var url = "/Windchill/platform/color/create";
			var params = new Object();
			params.json = json;
			if (!confirm("저장 하시겠습니까?")) {
				return false;
			}
			_call(url, params, function(data) {
				opener.load();
				self.close();
			}, "POST");
		})
		
		$("#applyBtn").click(function() {
			var tree = $.ui.fancytree.getTree("#tree");
			if (tree == null) {
				alert("컬러셋 등록한 부품을 먼저 선택하세요.");
				return false;
			}

			if (nodes.length == 0) {
				alert("색상변경 할 대상을 선택하세요.");
				return false;
			}

			var url = "/Windchill/platform/baseCode/popup?box=2&codeType=COLOR";
			_popup(url, 1200, 600, "n");
		})

		$(document).on("click", ".helper-checks-checkbox-c1", function(e) {
			var node = $.ui.fancytree.getNode(e);

			if ($(this).hasClass("sed")) {
				$(node.tr).css("background-color", "#dff4ff");
				nodes.push(node);
			} else {
				$(node.tr).css("background-color", "white");
				var filterArr = nodes.filter(function(data) {
					return data.key !== node.key;
				});
				nodes = filterArr;
			}
		})

		$("input[name=number]").click(function() {
			var url = "/Windchill/platform/part/popup?box=1";
			_popup(url, "", "", "f");
		})

		$("input[name=search]").on("keyup", function(e) {
			var _n, treeLeft = $.ui.fancytree.getTree("#tree");
			var _opts = {};
			var filterFunc = treeLeft.filterNodes;
			var match = $(this).val();
			_n = filterFunc.call(treeLeft, match, _opts);
		});

	})

	function loadTree(oid) {
		$("#tree").fancytree({
			extensions : [ "table", "filter", "gridnav", "contextMenu" ],
			quicksearch : true,
			source : {
				url : "/Windchill/platform/ebom/loadTree?oid=" + oid
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
				var key = node.key;
				$tdList.eq(0).html("<img src='" + node.data.thumb + "' onclick=_openCreoView('" + node.data.eoid + "');>");
				$tdList.eq(2).text(node.data.itemName);
				$tdList.eq(3).text(node.data.partType);
				$tdList.eq(4).text(node.data.partNo);
				$tdList.eq(5).text(node.data.name);
				$tdList.eq(6).text(node.data.version);
				$tdList.eq(7).text(node.data.applyColor);
				$tdList.eq(8).html("<input type='checkbox' class='isBox c1" + node.key + "' name='c1' value='true'>");
			},
			init : function() {
				$("input[name=c1]").checks();
				mask.close();
				$("#loading_layer").hide();
			},
			preInit : function() {
				mask.open();
				$("#loading_layer").show();
			},
			tooltip : function(event, data) {
				return data.node.title;
			},
		})
	}

	function color(items) {
		var tree = $.ui.fancytree.getTree("#tree");
		var color = items[0].item.code;

		nodes.forEach(function(node) {
			var key = node.key;
			node.setSelected(false); // 전환
			node.data.applyColor = color;
			$(node.tr).css("background-color", "#dcfdfe");
			node.renderTitle();
			$(".c1" + key).removeClass("isBox");
		})
		_setBoxs("input[name=c1]");
		nodes = [];
	}

	function getter(array, list) {
		for (var i = 0; array != null && i < array.length; i++) {
			list.push(array[i].data.oid);
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
				array[i].data.parent = array[i].parent.data.oid;
				toJsonArray(array[i].children, list);
			}
		}
	}

	function part(info) {
		$("input[name=oid]").val(info[0].oid);
		$("input[name=number]").val(info[0].number);
		$("input[name=partType]").val(info[0].partType);
		$("input[name=color]").val(info[0].color);
		$("input[name=search]").attr("readonly", false);
		loadTree(info[0].oid);
	}
</script>
