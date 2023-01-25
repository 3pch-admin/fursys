<%@page import="platform.dist.entity.DistPartDistributorUserLink"%>
<%@page import="platform.dist.entity.DistPartLink"%>
<%@page import="platform.dist.entity.DistPartColumns"%>
<%@page import="platform.dist.service.DistHelper"%>
<%@page import="platform.dist.service.DistributorHelper"%>
<%@page import="platform.dist.entity.Dist"%>
<%@page import="platform.ebom.entity.EBOM"%>
<%@page import="platform.part.service.PartHelper"%>
<%@page import="wt.fc.PersistenceHelper"%>
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
Dist di = (Dist) CommonUtils.persistable(oid);

ArrayList<DistPartLink> dd = DistHelper.manager.getPartLinks(di);
%>
<form id="form">
	<input type="hidden" name="oid" id="oid" value="<%=oid%>" >
	<table class="view-table dist-part-view close">
		<div class="view-table dist-part-view close" id="ref_dist_part_grid_wrap" style="height: 370px; padding-top: 5px;"></div>
	</table>
	
	<script type="text/javascript">
		var myGridID;
		var columnLayout = [ {
			// 		dataField : "s",
			headerText : "",
			dataType : "string",
			width : 40,
			renderer : {
				type : "IconRenderer",
				iconWidth : 16,
				iconHeight : 16,
				iconFunction : function(rowIndex, columnIndex, value, item) {
					return item.s;
				}
			},
			editable : false
		}, {
			dataField : "thumb",
			headerText : "",
			dataType : "string",
			width : 40,
			renderer : {
				type : "IconRenderer",
				iconWidth : 30,
				iconHeight : 22,
				iconFunction : function(rowIndex, columnIndex, value, item) {
					return item.t;
				}
			},
			editable : false,
		}, {
			dataField : "number",
			headerText : "부품번호",
			dataType : "string",
			style : "left indent10",
			width : 150,
			editable : false,
			//cellMerge : true
		}, {
			dataField : "name",
			headerText : "부품명칭",
			dataType : "string",
			width : 350,
			style : "left indent10",
			editable : false,
			//cellMerge : true
		}, {
			dataField : "version",
			headerText : "버전",
			dataType : "string",
			width : 60,
			editable : false,
	 		//cellMerge : true
		}, {
			dataField : "pdf",
			headerText : "PDF",
			dataType : "string",
			width : 60,
			editable : true,
			renderer : {
				type : "CheckBoxEditRenderer",
				showLabel : false,
				editable : true, // 체크박스 편집 활성화 여부(기본값 : false)
				checkValue : "true", // true, false 인 경우가 기본
				unCheckValue : "false",
			// 			disabledFunction : function(rowIndex, columnIndex, value, isChecked, item, dataField) {
			// 				// 행 아이템의 name 이 Anna 라면 체크박스 비활성화(disabled) 처리
			// 				if (item.docType == "CADDRAWING") {
			// 					return false;
			// 				}
			// 				return true;
			// 			}
			}
		}, {
			dataField : "step",
			headerText : "STEP",
			dataType : "string",
			width : 60,
			renderer : {
				type : "CheckBoxEditRenderer",
				showLabel : false,
				editable : true, // 체크박스 편집 활성화 여부(기본값 : false)
				checkValue : "true", // true, false 인 경우가 기본
				unCheckValue : "false",
			// 			disabledFunction : function(rowIndex, columnIndex, value, isChecked, item, dataField) {
			// 				// 행 아이템의 name 이 Anna 라면 체크박스 비활성화(disabled) 처리
			// 				if (item.docType != "CADDRAWING") {
			// 					return false;
			// 				}
			// 				return true;
			// 			}
			}
		}, {
			dataField : "dwg",
			headerText : "DWG",
			dataType : "string",
			width : 60,
			renderer : {
				type : "CheckBoxEditRenderer",
				showLabel : false,
				editable : true, // 체크박스 편집 활성화 여부(기본값 : false)
				checkValue : "true", // true, false 인 경우가 기본
				unCheckValue : "false",
			// 			disabledFunction : function(rowIndex, columnIndex, value, isChecked, item, dataField) {
			// 				// 행 아이템의 name 이 Anna 라면 체크박스 비활성화(disabled) 처리
			// 				if (item.docType == "CADDRAWING") {
			// 					return false;
			// 				}
			// 				return true;
			// 			}
			}
		}, {
			dataField : "type",
			headerText : "배포처구분",
			dataType : "string",
			width : 80,
			editable : false,
		}, {
			dataField : "distributor",
			headerText : "배포처",
			dataType : "string",
			width : 200,
			editable : false,
		}, {
			dataField : "user",
			headerText : "배포처 수신자",
			dataType : "string",
			width : 250
		}, {
			dataField : "uoid",
			headerText : "uoid",
			dataType : "string",
			visible : false
		}, {
			dataField : "oid",
			headerText : "oid",
			dataType : "string",
			visible : false
		}, ];
		var auiGridProps = {
			rowIdField : "rowId",
			headerHeight : 30,
			rowHeight : 30,
			showRowNumColumn : true,
			showRowCheckColumn : false,
			rowCheckToRadio : false,
			rowNumHeaderText : "번호",
			softRemoveRowMode : false,
			showAutoNoDataMessage : false,
			enableCellMerge : true,
			editable : true,
			cellMergePolicy : "withNull",
		};
		myGridID = AUIGrid.create("#ref_dist_part_grid_wrap", columnLayout, auiGridProps);
	
		function load() {
			var params = _data($("#form"));
			var url = _url("/dist/partList");
			AUIGrid.showAjaxLoader(myGridID);
			_call(url, params, function(data) {
				AUIGrid.removeAjaxLoader(myGridID);
				AUIGrid.setGridData(myGridID, data.list);
				console.log(data.list);
			}, "POST");
			
			
			
		}
		
	
		$(function() {
			load();
	
			$(window).resize(function() {
				AUIGrid.resize("#ref_dist_part_grid_wrap");
			})
	
		})
	
		function info(list) {
			for (var i = 0; i < list.length; i++) {
				var value = list[i];
				var email = value.email;
				var type = value.type;
				var name = value.name;
				var uoid = value.uoid;
				var userName = value.userName;
	
				var selectedItems = AUIGrid.getSelectedItems(myGridID);
				if (selectedItems.length <= 0)
					return;
	
				// distributor, user 사용자, type
				
				if (i == 0) {
					var selItem = selectedItems[0].item;
					selItem.user = userName;
					selItem.distributor = name;
					selItem.uoid = uoid;
					selItem.type = type;
					AUIGrid.updateRowsById(myGridID, selItem, selItem.rowId);
				} else {
					var selItem = selectedItems[0].item;
					var item = new Object();
					// 선택행 아래..
					item  = selItem;
					item.user = userName;
					item.distributor = name;
					item.uoid = uoid;
					item.type = type;
					AUIGrid.addRow(myGridID, item, "selectionDown");
				}
			}
		}
	
	</script>
</form>