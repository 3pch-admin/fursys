<%@page import="platform.part.service.PartHelper"%>
<%@page import="platform.util.CommonUtils"%>
<%@page import="wt.part.WTPart"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%

%>
<div class="header-title">
	<img src="/Windchill/jsp/images/home.png" class="home">
	<span>HOME</span>
	>
	<span>BOM관리</span>
	>
	<span>MBOM 파생</span>
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
		<th>부품명칭(단품)</th>
		<td>
			CD_H37_SIDE_R_ASSY_2.ASM
		</td>
		<th>유형</th>
		<td>
<!-- 			<input type="text" name="partType" class="AXInput w30p" readonly="readonly"> -->
			단품
		</td>
	</tr>
</table>

<table class="button-table">
	<tr>
		<td class="right">
			<button type="button" id="saveBtn">등록</button>
			<button type="button" id="closeBtn">닫기</button>
		</td>
	</tr>
</table>


<table class="create-table none">
	<colgroup>
		<col width="50%">
		<col width="30">
		<col width="50%">
	</colgroup>
	<tr>
		<td class="center none" valign="top">
			<div class="trees">
				<jsp:include page="/jsp/mbom/mbom-derived-left.jsp"></jsp:include>
			</div>
		</td>
		<td class="none" valign="top">&nbsp;</td>
		<td class="center none" valign="top">
			<div class="trees">
				<jsp:include page="/jsp/mbom/mbom-derived-right.jsp"></jsp:include>
			</div>
		</td>
	</tr>
</table>
<div id="loading_layer_open">
	<img src="/Windchill/jsp/images/loading.gif">
</div>
<script type="text/javascript">
	var nodes = new Array();
	var remove = new Array();
	var oid = $("input[name=oid]").val();
	var poid = $("input[name=poid]").val();
	$(function() {
		$("input[name=search]").on("keyup", function(e) {
			var _n, treeLeft = $.ui.fancytree.getTree("#treeLeft");
			var _opts = {};
			var filterFunc = treeLeft.filterNodes;
			var match = $(this).val();
			_n = filterFunc.call(treeLeft, match, _opts);

			var n, treeRight = $.ui.fancytree.getTree("#treeRight");
			var opts = {};
			var filterFunc = treeRight.filterNodes;
			n = filterFunc.call(treeRight, match, opts);
		});

		$("input[name=number]").click(function() {
			var url = "/Windchill/platform/part/popup?box=1";
			_popup(url, "", "", "f");
		})

		$("#closeBtn").click(function() {
			self.close();
		})

		$("#saveBtn").click(function() {
			if (!confirm("등록 하시겠습니까?")) {
				return false;
			}
		});
	})

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
		// 		$("#input[name=number]").add("input[name=name]").off();
		$("input[name=oid]").val(info[0].oid);
		$("input[name=number]").val(info[0].number);
		// 		$("input[name=name]").val(data[0].name);
		$("input[name=partType]").val(info[0].partType);
		$("input[name=color]").val(info[0].color);
		$("input[name=search]").attr("readonly", false);
		loadLeftTree(info[0].oid);
		loadRightTree(info[0].oid);
	}

	loadLeftTree(poid);
	loadRightTree(oid);
</script>