<%@page import="platform.util.StringUtils"%>
<%@page import="platform.util.CommonUtils"%>
<%@page import="platform.project.entity.Project"%>
<%@page import="platform.project.service.ProjectHelper"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>
<%@page import="platform.project.template.entity.TemplateDTO"%>
<%@page import="platform.doc.entity.DocumentDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String oid = (String) request.getAttribute("oid");
String toid = (String)request.getAttribute("toid");
Project project = (Project)request.getAttribute("project");
ArrayList<Map<String, Object>> list = (ArrayList<Map<String, Object>>) request.getAttribute("list");
JSONArray arr = JSONArray.fromObject(list);
JSONArray outputJson = (JSONArray) request.getAttribute("outputJson");
JSONArray issueJson = (JSONArray) request.getAttribute("issueJson");
JSONArray ganttJson = (JSONArray) request.getAttribute("ganttJson");

boolean isComplete = (boolean)request.getAttribute("isComplete");
boolean isReady = (boolean)request.getAttribute("isReady");
boolean isProgress = (boolean)request.getAttribute("isProgress");

%>
<style type="text/css">
.aui-grid-tree-leaf-icon {
	display: inline-block;
	width: 16px;
	height: 16px;
	background: url(/Windchill/jsp/asset/AUIGrid/images/flat_circle.png) no-repeat;
	background-size: 16px;
	vertical-align: bottom;
	margin: 0 2px 0 4px;
}

.aui-grid-tree-minus-icon {
	display: inline-block;
	width: 16px;
	height: 16px;
	border: none;
	background: url(/Windchill/jsp/asset/AUIGrid/images/arrow-downright.png) 50% 50% no-repeat;
	background-size: 16px;
	vertical-align: bottom;
	margin: 0 2px 0 0;
}

.aui-grid-tree-plus-icon {
	display: inline-block;
	width: 16px;
	height: 16px;
	border: none;
	background: url(/Windchill/jsp/asset/AUIGrid/images/arrow-right.png) 50% 50% no-repeat;
	background-size: 16px;
	vertical-align: bottom;
	margin: 0 2px 0 0;
}
</style>
<div id="tabs"></div>
<br>
<div class="header-title">
	<img src="/Windchill/jsp/images/home.png" class="home">
	<span>HOME</span>
	>
	<span>???????????? ??????</span>
	>
	<span>???????????? ??????</span>
</div>

<div id="projectView">
	<%
		if(isReady) {
	%>
	<table class="button-table">
		<tr>
			<td class="left">
				<button type="button" id="saveBtn">??????</button>
				<button type="button" id="addBtn">??????</button>
				<button type="button" id="deleteRow">??????</button>
				<button type="button" id="upRowBtn">??????</button>
				<button type="button" id="downRowBtn">?????????</button>
				<button type="button" id="indentRowBtn">???????????????</button>
				<button type="button" id="outdentRowBtn">???????????????</button>
				<%
					if(isReady) {
				%>
				<button type="button" id="startProjectBtn">??????</button>
				<%
					}
				%>
			</td>
		</tr>
	</table>
	<%
		}
	%>
	<table class="">
		<colgroup>
			<col width="50%">
			<col width="30">
			<col width="49%">
		</colgroup>
		<tr>
			<td class="center none" valign="top">
				<div id="grid_wrap"  <%if(isReady) {%>  style="height: 805px;" <%} else { %> style="height: 845px;" <%} %>></div>
			</td>
			<td class="none" valign="top">&nbsp;</td>
			<td class="center none" valign="top">
				<%
					if(!StringUtils.isNotNull(toid)) {
				%>
				<iframe style="height: 820px;" id="right" src="/Windchill/platform/project/projectView?oid=<%=oid%>"></iframe>
				<%
					} else {
				%>
				<iframe style="height: 820px;" id="right" src="/Windchill/platform/project/projectTaskView?oid=<%=toid%>"></iframe>
				<%
					}
				%>
			</td>
		</tr>
	</table>
</div>

<jsp:include page="/jsp/project/output/output-project.jsp"></jsp:include>

<jsp:include page="/jsp/project/issue/issue-project.jsp"></jsp:include>

<jsp:include page="/jsp/project/project-gantt.jsp"></jsp:include>

<table class="button-table">
	<tr>
		<td class="right">
			<!-- 			<button type="button" id="deleteBtn">??????</button> -->
			<button type="button" id="closeBtn">??????</button>
		</td>
	</tr>
</table>
<script type="text/javascript">
	var roles =<%=arr%>;
	var columnLayout = [ {
		dataField : "name",
		headerText : "????????? ???",
		dataType : "string",
		style : "left indent10",
		width : 280,
		<%
		if(isReady) {
		%>
		editable : true,
		<%
			}
		%>
	}, {
		dataField : "progress",
		headerText : "?????????",
		dataType : "string",
		width : 80,
		editable : false,
		renderer: {
			type: "BarRenderer",
			min: 0,
			max: 100,
// 			offset: 30
		},
		postfix : "%"
	}, {
		dataField : "state",
		headerText : "??????",
		dataType : "string",
		width : 80,
		<%
			if(isReady) {
		%>
		editable : true,
		<%
			}
		%>
		editRenderer : {
			type: "CalendarRenderer",
			defaultFormat: "yyyy-mm-dd", // ?????? ?????? ??? ???????????? ???????????? ?????? ??????
			showEditorBtnOver: true, // ????????? ?????? ??? ??????????????? ?????? ??????
			showExtraDays: false, //  ?????? ???, ?????? ??? ????????? ??????(days) ?????? ??????
			showTodayBtn: true,
			showUncheckDateBtn: true,
			uncheckDateValue: ""
		},	
	}, {
		dataField : "planStartDate",
		headerText : "???????????????",
		dataType : "date",
		width : 100,
		<%
			if(isReady) {
		%>
		editable : true,
		<%
			}
		%>
		editRenderer : {
			type: "CalendarRenderer",
			defaultFormat: "yyyy-mm-dd", // ?????? ?????? ??? ???????????? ???????????? ?????? ??????
			showEditorBtnOver: true, // ????????? ?????? ??? ??????????????? ?????? ??????
			showExtraDays: false, //  ?????? ???, ?????? ??? ????????? ??????(days) ?????? ??????
			showTodayBtn: true,
			showUncheckDateBtn: true,
			uncheckDateValue: ""
		},		
	}, {
		dataField : "planEndDate",
		headerText : "???????????????",
		dataType : "string",
		width : 100,
		editable : false
	}, {
		dataField : "startDate",
		headerText : "???????????????",
		dataType : "string",
		width : 100,
		editable : false
	}, {
		dataField : "endDate",
		headerText : "???????????????",
		dataType : "string",
		width : 100,
		editable : false
	}, {
		dataField : "duration",
		headerText : "??????",
		dataType : "string",
		width : 80,
		postfix : "???",
	}, {
		dataField : "preTask",
		headerText : "???????????????",
		dataType : "string",
		width : 200,
		editable : false
	}, {
		dataField : "roles",
		headerText : "??????",
		dataType : "string",
		width : 200,
		<%
			if(isReady && (ProjectHelper.manager.isPM(project) || CommonUtils.isAdmin())) {
		%>
		renderer : {
			type : "IconRenderer",
			iconWidth : 16, // icon ?????????, ???????????? ????????? rowHeight??? ?????? ????????? ?????????
			iconHeight : 16,
			iconPosition : "aisleRight",
			iconTableRef : { // icon ??? ????????? ????????? ????????????
				"default" : "/Windchill/jsp/asset/AUIGrid/images/list-icon.png" // default
			},
			onClick : function(event) {
				// ???????????? ???????????? ???????????? ?????????.
				AUIGrid.openInputer(event.pid);
			}
		},
		editRenderer : {
			type : "DropDownListRenderer",
			showEditorBtn : false,
			showEditorBtnOver : false, // ????????? ?????? ??? ??????????????? ?????????
			multipleMode : true, // ?????? ?????? ??????(????????? : false)
			showCheckAll : true, // ?????? ?????? ???????????? ?????? ?????? ??????/?????? ??????(?????????:false);
			delimiter : ",", // ?????? ????????? ????????? (????????? : ??????(,))
			list : roles,
			keyField : "code", // key ??? ???????????? ?????????
			valueField : "name" // value ??? ???????????? ?????????			
		},
		<%
			}
		%>
		labelFunction : function(rowIndex, columnIndex, value, headerText, item) {
			var retStr = "";
			if (value != undefined) {
				var valueArr = value.split(","); // ????????? ???????????? ", " ???.
				var tempValueArr = [];

				for (var i = 0, len = roles.length; i < len; i++) {
					if (valueArr.indexOf(roles[i]["code"]) >= 0) {
						tempValueArr.push(roles[i]["name"]);
					}
				}
				return tempValueArr.sort().join(","); // ??????????????? ????????????.
			}
		},
	}, {
		dataField : "oid",
		headerText : "oid",
		dataType : "string",
		visible : false
	}, ];

	var auiLeftProps = {
		rowIdField : "oid",
		headerHeight : 30,
		rowHeight : 30,
		showRowNumColumn : false,
		displayTreeOpen : true,
		treeColumnIndex : 0,
		<%
			if(isReady) {
		%>
		enableDrag : true,
		enableDragByCellDrag : true,
		enableDrop : true,
		enableUndoRedo : true,
		<%
			}
		%>
		editable : false,
		softRemoveRowMode : false,
		selectionMode : "multipleCells",
// 		useContextMenu : true,
		fixedColumnCount : 1,
		treeLevelIndent : 25,
		enableSorting : false
	};

	auiLeftProps.treeIconFunction = function(rowIndex, isBranch, isOpen, depth, item) {
		var imgSrc = null;
		if (item.state == "?????????") {
			imgSrc = "/Windchill/jsp/images/task_ready.gif";
		} else if (item.state == "?????????") {
			imgSrc = "/Windchill/jsp/images/task_progress.gif";
		} else if (item.state == "?????????") {
			imgSrc = "/Windchill/jsp/images/task_complete.gif";
		} else if (item.state == "?????????") {
			imgSrc = "/Windchill/jsp/images/task_stop.gif";
		} 
		return imgSrc;
	};

	myGridID = AUIGrid.create("#grid_wrap", columnLayout, auiLeftProps);
	var oid = "<%=oid%>";
	function load(oid) {
		var params = new Object();
		AUIGrid.showAjaxLoader(myGridID);
		var url = _url("/project/load", oid);
		_call(url, params, function(data) {
			AUIGrid.removeAjaxLoader(myGridID);
			AUIGrid.setGridData(myGridID, data.list);
		}, "GET");
	}

	load(oid);

	AUIGrid.bind(myGridID, "cellEditBegin", function(event) {
		var item = event.item;
		var oid = item.oid;
		return true;
	});

	AUIGrid.bind(myGridID, "addRowFinish", function(event) {
		var items = event.items;
		for (var i = 0; i < items.length; i++) {
			var item = items[i];
			item.duration = 1;
			item.isNew = true;
		}
	});

	AUIGrid.bind(myGridID, "cellClick", function(event) {
		var oid = event.item.oid;
		var panel = document.getElementById("right");
		var dataField = event.dataField;
		if (dataField == "name") {
			if (oid.indexOf("Project") > -1) {
				panel.src = "/Windchill/platform/project/projectView?oid=" + oid;
			} else if (oid.indexOf("Task") > -1) {
				panel.src = "/Windchill/platform/project/projectTaskView?oid=" + oid;
			}
		}
	});

	$(function() {

		$("#startProjectBtn").click(function() {
			if(!confirm("??????????????? ?????? ???????????????????")) {
				return false;
			}
			
			var url = _url("/project/start", "<%=oid%>");
			_call(url, null, function(data) {
				document.location.reload();
			}, "GET");
		})
		
		$("#saveBtn").click(function() {
			var d = AUIGrid.getGridData(myGridID);
			for (var i = 0; i < d.length; i++) {
				var item = d[i];
				var name = item.name;
				if (name == undefined) {
					alert("????????? ?????? ???????????? ???????????? ???????????????.");
					return false;
				}
			}
			if (!confirm("?????? ???????????????????")) {
				return false;
			}

			var _deleted = AUIGrid.getRemovedItems(myGridID);
			console.log(JSON.stringify(AUIGrid.getTreeGridData(myGridID)));
			// 			var json = btoa(unescape(encodeURIComponent(JSON.stringify(list))));
			var json = btoa(unescape(encodeURIComponent(JSON.stringify(AUIGrid.getTreeGridData(myGridID)))));
			var url = "/Windchill/platform/project/saveTree";
			var params = new Object();
			params.json = json;
			params._deleted = _deleted;
			_call(url, params, function(data) {
				load(oid);
			}, "POST");
		})

		$("#outdentRowBtn").click(function() {
			AUIGrid.outdentTreeDepth(myGridID);
		})

		$("#indentRowBtn").click(function() {
			AUIGrid.indentTreeDepth(myGridID);
		})

		$("#upRowBtn").click(function() {
			var selectedItems = AUIGrid.getSelectedItems(myGridID);
			if (selectedItems.length <= 0)
				return;
			AUIGrid.moveRowsToUp(myGridID);

		})

		$("#downRowBtn").click(function() {
			var selectedItems = AUIGrid.getSelectedItems(myGridID);
			if (selectedItems.length <= 0)
				return;
			AUIGrid.moveRowsToDown(myGridID);
		})

		$("#addBtn").click(function() {
			var selectedItems = AUIGrid.getSelectedItems(myGridID);
			if (selectedItems.length <= 0)
				return;

			var selItem = selectedItems[0].item;
			var parentRowId = null;
			var item = new Object();
			// 			item.duration = 1;
			item.isNew = true;
			AUIGrid.addTreeRow(myGridID, item, parentRowId, "last");
		})

		$("#deleteRow").click(function() {
			var selectedItems = AUIGrid.getSelectedItems(myGridID);
			if (selectedItems.length <= 0)
				return;
			var oid = selectedItems[0].item.oid;
			if (oid.indexOf("Template") > -1) {
				alert("???????????? ????????? ??????????????????.");
				return false;
			}
			AUIGrid.removeRow(myGridID, "selectedIndex");
		})

		$("#closeBtn").click(function() {
			self.close();
		})

		$("#tabs").bindTab({
			theme : "AXTabs",
			value : "1",
			overflow : "scroll", /* "visible" */
			options : [ {
				optionValue : "1",
				optionText : "????????????",
			}, {
				optionValue : "2",
				optionText : "?????????",
			}, {
				optionValue : "3",
				optionText : "??????",
			}, {
				optionValue : "4",
				optionText : "????????????",
			} ],
			onchange : function(selectedObject, value) {
				if (value == "1") {
					$("#projectView").show();
					AUIGrid.resize("#grid_wrap");
					$("#output_grid_wrap").hide();
					$("#issue_grid_wrap").hide();
					$("#gantt_wrap").hide();
				} else if (value == "2") {
					$("#projectView").hide();
					$("#output_grid_wrap").show();
					AUIGrid.setGridData("#output_grid_wrap", <%=outputJson%>);
					AUIGrid.resize("#output_grid_wrap");
					$("#issue_grid_wrap").hide();
					$("#gantt_wrap").hide();
				} else if (value == "3") {
					$("#projectView").hide();
					$("#output_grid_wrap").hide();
					$("#issue_grid_wrap").show();
					AUIGrid.resize("#issue_grid_wrap");
					AUIGrid.setGridData("#issue_grid_wrap", <%=issueJson%>);
					$("#gantt_wrap").hide();
				} else if (value == "4") {
					$("#projectView").hide();
					$("#output_grid_wrap").hide();
					$("#issue_grid_wrap").hide();
					$("#gantt_wrap").show();
					AUIGantt.resize("#gantt_wrap");
<%-- 					AUIGantt.setGanttData("#gantt_wrap", <%=ganttJson%>); --%>
					convert(<%=ganttJson%>);
				}
			},
		});
	})

	$(window).resize(function() {
		AUIGrid.resize("#grid_wrap");
		AUIGrid.resize("#output_grid_wrap");
		AUIGrid.resize("#issue_grid_wrap");
		AUIGantt.resize("#gantt_wrap");
	})
	
	function convert(json) {
		var fieldMap = {
				name : "name", // ?????? ?????? ??????
		        start : "planStartDate", // ?????? ?????? ?????? ??????
		        end : "planEndDate", // ?????? ?????? ?????? ??????
		        resource : "role", // ??????
		        progress : "progress", // ?????????
		        predecessor : "preTask", // ????????????
		        startActual : "startDate",
		        endActual : "endDate",
// 		        isMilestone : "isMilestone" // ???????????? ??????
		};
		
		// ???????????? ????????? JSON ???????????? AUIGantt ???????????? ??????
		var data = AUIGantt.utils.converter(json, fieldMap);
		// ????????? ????????? ??????
		AUIGantt.setGanttData("#gantt_wrap", data);
	}
</script>