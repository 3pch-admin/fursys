<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
	$(function() {
		$("#folder-tree").fancytree({
			collapse : function() {
			},
			expand : function() {
			},
			click : function(e, data) {
				currentPage = 1;
				$("input[name=tpage").val(1);
				$("input[name=sessionid").val(0);
				$("#deptOid").val(data.node.data.oid);
				load();
			},
			source : $.ajax({
				url : "/Windchill/platform/dept/tree",
				type : "GET",
				dataType : "JSON"
			})
		})
	})
</script>
<div id="folder-tree"></div>