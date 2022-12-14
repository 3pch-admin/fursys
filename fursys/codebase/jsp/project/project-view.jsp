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
	<span>프로젝트 관리</span>
	>
	<span>프로젝트 정보</span>
</div>

<div id="projectView">
	<%
		if(isReady) {
	%>
	<table class="button-table">
		<tr>
			<td class="left">
				<button type="button" id="saveBtn">저장</button>
				<button type="button" id="addBtn">추가</button>
				<button type="button" id="deleteRow">삭제</button>
				<button type="button" id="upRowBtn">위로</button>
				<button type="button" id="downRowBtn">아래로</button>
				<button type="button" id="indentRowBtn">수준올리기</button>
				<button type="button" id="outdentRowBtn">수준내리기</button>
				<%
					if(isReady) {
				%>
				<button type="button" id="startProjectBtn">시작</button>
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
			<!-- 			<button type="button" id="deleteBtn">삭제</button> -->
			<button type="button" id="closeBtn">닫기</button>
		</td>
	</tr>
</table>
<script type="text/javascript">
	var roles =<%=arr%>;
	var columnLayout = [ {
		dataField : "name",
		headerText : "태스크 명",
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
		headerText : "진행율",
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
		headerText : "상태",
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
			defaultFormat: "yyyy-mm-dd", // 달력 선택 시 데이터에 적용되는 날짜 형식
			showEditorBtnOver: true, // 마우스 오버 시 에디터버턴 출력 여부
			showExtraDays: false, //  지난 달, 다음 달 여분의 날짜(days) 출력 안함
			showTodayBtn: true,
			showUncheckDateBtn: true,
			uncheckDateValue: ""
		},	
	}, {
		dataField : "planStartDate",
		headerText : "계획시작일",
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
			defaultFormat: "yyyy-mm-dd", // 달력 선택 시 데이터에 적용되는 날짜 형식
			showEditorBtnOver: true, // 마우스 오버 시 에디터버턴 출력 여부
			showExtraDays: false, //  지난 달, 다음 달 여분의 날짜(days) 출력 안함
			showTodayBtn: true,
			showUncheckDateBtn: true,
			uncheckDateValue: ""
		},		
	}, {
		dataField : "planEndDate",
		headerText : "계획종료일",
		dataType : "string",
		width : 100,
		editable : false
	}, {
		dataField : "startDate",
		headerText : "실제시작일",
		dataType : "string",
		width : 100,
		editable : false
	}, {
		dataField : "endDate",
		headerText : "실제종료일",
		dataType : "string",
		width : 100,
		editable : false
	}, {
		dataField : "duration",
		headerText : "기간",
		dataType : "string",
		width : 80,
		postfix : "일",
	}, {
		dataField : "preTask",
		headerText : "선행태스크",
		dataType : "string",
		width : 200,
		editable : false
	}, {
		dataField : "roles",
		headerText : "역할",
		dataType : "string",
		width : 200,
		<%
			if(isReady && (ProjectHelper.manager.isPM(project) || CommonUtils.isAdmin())) {
		%>
		renderer : {
			type : "IconRenderer",
			iconWidth : 16, // icon 사이즈, 지정하지 않으면 rowHeight에 맞게 기본값 적용됨
			iconHeight : 16,
			iconPosition : "aisleRight",
			iconTableRef : { // icon 값 참조할 테이블 레퍼런스
				"default" : "/Windchill/jsp/asset/AUIGrid/images/list-icon.png" // default
			},
			onClick : function(event) {
				// 아이콘을 클릭하면 수정으로 진입함.
				AUIGrid.openInputer(event.pid);
			}
		},
		editRenderer : {
			type : "DropDownListRenderer",
			showEditorBtn : false,
			showEditorBtnOver : false, // 마우스 오버 시 에디터버턴 보이기
			multipleMode : true, // 다중 선택 모드(기본값 : false)
			showCheckAll : true, // 다중 선택 모드에서 전체 체크 선택/해제 표시(기본값:false);
			delimiter : ",", // 다중 선택시 구분자 (기본값 : 컴마(,))
			list : roles,
			keyField : "code", // key 에 해당되는 필드명
			valueField : "name" // value 에 해당되는 필드명			
		},
		<%
			}
		%>
		labelFunction : function(rowIndex, columnIndex, value, headerText, item) {
			var retStr = "";
			if (value != undefined) {
				var valueArr = value.split(","); // 구분자 기본값은 ", " 임.
				var tempValueArr = [];

				for (var i = 0, len = roles.length; i < len; i++) {
					if (valueArr.indexOf(roles[i]["code"]) >= 0) {
						tempValueArr.push(roles[i]["name"]);
					}
				}
				return tempValueArr.sort().join(","); // 정렬시켜서 보여주기.
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
		if (item.state == "대기중") {
			imgSrc = "/Windchill/jsp/images/task_ready.gif";
		} else if (item.state == "진행중") {
			imgSrc = "/Windchill/jsp/images/task_progress.gif";
		} else if (item.state == "완료됨") {
			imgSrc = "/Windchill/jsp/images/task_complete.gif";
		} else if (item.state == "중지됨") {
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
			if(!confirm("프로젝트를 시작 하시겠습니까?")) {
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
					alert("태스크 명이 입력안된 태스크가 존재합니다.");
					return false;
				}
			}
			if (!confirm("저장 하시겠습니까?")) {
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
				alert("템플릿은 삭제가 불가능합니다.");
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
				optionText : "기본정보",
			}, {
				optionValue : "2",
				optionText : "산출물",
			}, {
				optionValue : "3",
				optionText : "이슈",
			}, {
				optionValue : "4",
				optionText : "간트차트",
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
				name : "name", // 작업 이름 필드
		        start : "planStartDate", // 작업 시작 날짜 필드
		        end : "planEndDate", // 작업 종료 날짜 필드
		        resource : "role", // 자원
		        progress : "progress", // 완료율
		        predecessor : "preTask", // 선행관계
		        startActual : "startDate",
		        endActual : "endDate",
// 		        isMilestone : "isMilestone" // 마일스톤 여부
		};
		
		// 사용자가 작성한 JSON 데이터를 AUIGantt 데이터로 변환
		var data = AUIGantt.utils.converter(json, fieldMap);
		// 변환된 데이터 삽입
		AUIGantt.setGanttData("#gantt_wrap", data);
	}
</script>