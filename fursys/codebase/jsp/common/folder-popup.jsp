<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String location = (String) request.getAttribute("location");
String target = (String) request.getAttribute("target");
String context = (String) request.getAttribute("context");
String callBack = (String) request.getParameter("callBack");
%>
<div class="header-title">
	<img src="/Windchill/jsp/images/home.png" class="home">
	<span>HOME</span>
	>
	<span>폴더 관리</span>
	>
	<span>폴더 정보</span>
</div>
<script type="text/javascript">
$(function() {
		var location = encodeURIComponent("<%=location%>");
		var context = encodeURIComponent("<%=context%>");
		$("#popup-folder-tree").fancytree({
			collapse : function() {
			},
			expand : function() {
			},
			click : function(e, data) {
				$(opener.document).find("#location").val(data.node.data.location);
<%if (callBack != null) {%>
	opener.<%=callBack%>(data.node.data.location);
<%}%>
	self.close();
			},
			source : $.ajax({
				url : "/Windchill/platform/util/tree?location=" + location + "&context=" + context,
				type : "GET",
				dataType : "JSON"
			})
		})

		$("#closeBtn").click(function() {
			self.close();
		})
	})
</script>
<div id="popup-folder-tree"></div>

<table class="button-table">
	<tr>
		<td class="right">
			<button type="button" id="closeBtn">닫기</button>
		</td>
	</tr>
</table>