<%@page import="platform.dist.entity.DistributorUserColumns"%>
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

ArrayList<DistributorUserColumns> dd = DistHelper.manager.getDistributorUserColumnLinks(di);

out.println("<br>"+dd.size());

for(DistributorUserColumns asas:dd){
	out.println("<br>"+asas.getUserName());
}

%>
<form id="form">
	<input type="hidden" name="oid" id="oid" value="<%=oid%>" >
	<table class="view-table dist-distributor-view close">
		<div class="view-table dist-distributor-view close" id="ref_dist_distributor_grid_wrap" style="height: 370px; padding-top: 5px;"></div>
	</table>
	
	<script type="text/javascript">
		var dist_GridID;
		var dist_columnLayout = [ {
			dataField : "no",
			headerText : "번호",
			dataType : "string",
			width : 40
		}, {
			dataField : "name",
			headerText : "배포처",
			dataType : "string",
			style : "left indent10"
		}, {
			dataField : "type",
			headerText : "배포처 구분",
			dataType : "string",
			width : 100
		}, {
			dataField : "userId",
			headerText : "사용자 아이디",
			dataType : "string",
			width : 120
		}, {
			dataField : "userName",
			headerText : "사용자명",
			dataType : "string",
			width : 120
		}, {
			dataField : "email",
			headerText : "이메일",
			dataType : "string",
			width : 200
		}, {
			dataField : "enable",
			headerText : "사용여부",
			dataType : "string",
			width : 120
		}, {
			dataField : "creator",
			headerText : "작성자",
			dataType : "string",
			width : 100
		}, {
			dataField : "createdDate",
			headerText : "작성일자",
			dataType : "date",
			formatString : "yyyy/mm/dd",
			width : 100
		}, {
			dataField : "oid",
			headerText : "oid",
			dataType : "string",
			visible : false
		}, ];
		var dist_auiGridProps = {
			rowIdField : "oid",
			headerHeight : 30,
			rowHeight : 30,
			fillColumnSizeMode : true,
			// 						rowCheckToRadio : false,
			showRowCheckColumn : true,
			showRowNumColumn : false
		};
		dist_GridID = AUIGrid.create("#ref_dist_distributor_grid_wrap", dist_columnLayout, dist_auiGridProps);
	
		function load() {
			var params = _data($("#form"));
			var url = _url("/dist/distributortList");
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
				AUIGrid.resize("#ref_dist_distributor_grid_wrap");
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