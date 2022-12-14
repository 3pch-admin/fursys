<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String location = (String) request.getParameter("location");
String context = (String) request.getParameter("context");

out.println("location=="+location);
out.println("context=="+context);
%>
<script type="text/javascript">
$(function() {
		var location = encodeURIComponent("<%=location%>");
		var context = encodeURIComponent("<%=context%>");
		$("#folder-tree").fancytree({
			collapse : function() {
			},
			expand : function() {
			},
			click : function(e, data) {
				$("#location").val(data.node.data.location);
				currentPage = 1;
				$("input[name=tpage").val(1);
				$("input[name=sessionid").val(0);
				load();
			},
			
			contextMenu: {
			      menu: {
			        "add": { "name": "Add", "icon": "add" },
			        "edit": { "name": "Edit", "icon": "edit" },
			        "delete": { "name": "Delete", "icon": "delete" },
			      },
			      actions: function(node, action, options) {
			        
			        if( "add" == action){
						var poid = node.key;
						var url = "/Windchill/platform/util/createFolder?location=" + location + "&context=" + context ; 
						var params = new Object();
						params.poid = poid;
						params.context = "<%=context%>";
						
						_call(url, params, function(data) {
							alert("###########111=="+data.result);
							
						}, "POST");
						
			        }else if( "edit" == action){
			        	
					}else if( "delete" == action){
			        	
			        }
			        
			        
			      }
			},
			
			edit : {
				triggerStart: ["f2"],
				close : function(e, data) {
					alert("edit");
					if(data.save && !data.isNew) {
						var node = data.node;
						var orgTitle = data.orgTitle;
						if (node.type == "root") {
							dialogs.alert({
								theme : "alert",
								title : "경고",
								msg : "최상위 폴더명은 수정이 불가능합니다.",
							}, function() {
								if (this.key == "ok") {
									$(".context-menu-list").hide();
									node.title = orgTitle;
									node.render(true);
								}
							})
							return false;
						}
						
						var oid = data.node.data.id;
						var poid = data.node.parent.data.id;
						var url = "/Windchill/plm/common/renameFolderAction";
						var params = new Object();
						params.oid = oid;
						params.poid = poid;
						params.text = data.node.title;
						$(document).ajaxCallServer(url, params, function(data) {
							if(data.result == "FAIL") {
								if(data.reload) {
									document.location.href = data.url;									
								} else {
									dialogs.alert({
										theme : "alert",
										title : "경고",
										msg : data.msg
									}, function() {
										if(this.key == "ok") {
											node.title = "";
										}
									})
								}
							} else if(data.result == "SUCCESS") {
// 								newNode.data.id = data.foid;
								mask.close();
							}
						}, false);
					}

					if(data.save && data.isNew) {
						var newNode = data.node;
						var poid = data.node.parent.data.id;
						var url = "/Windchill/plm/common/createFolderAction";
						var params = new Object();
						params.poid = poid;
						params.text = data.node.title;
						params.context = "<%=context%>";
						$(document).ajaxCallServer(url, params, function(data) {
							if(data.result == "FAIL") {
								if(data.reload) {
									document.location.href = data.url;									
								} else {
									dialogs.alert({
										theme : "alert",
										title : "경고",
										msg : data.msg
									}, function() {
										if(this.key == "ok") {
											newNode.remove();
										}
									})
								}
							} else if(data.result == "SUCCESS") {
								newNode.data.id = data.foid;
								mask.close();
							}
						}, false);
					}
				}
			},
			extensions : [ "edit", "contextMenu" ],
			source : $.ajax({
				url : "/Windchill/platform/util/tree?location=" + location + "&context=" + context,
				type : "GET",
				dataType : "JSON"
			})
			
		})
		var h = $("#container_td").height();
		//$("ul.fancytree-container").css("height", h - 9);
		
		$.fn.createFolderSlib = function(node) {

			if (node == null) {
				return;
			}

			if (node.type == "root") {
				dialogs.alert({
					theme : "alert",
					title : "경고",
					msg : "최상위 폴더랑 같은 레벨에 생성 할 수 없습니다.",
					width : 400
				}, function() {
					if (this.key == "ok") {
						$(".context-menu-list").hide();
					}
				})
				return false;
			}

			node.editCreateNode("after", {
				title : "새 폴더",
				folder : true
			})
		}

		// 하위 레벨
		$.fn.createFolderAfter = function(node) {
			node.editCreateNode("child", {
				title : "새 폴더",
				folder : true
			})
		}
	})
</script>
<div id="folder-tree"></div>