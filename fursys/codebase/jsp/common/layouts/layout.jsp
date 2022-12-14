<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><tiles:insertAttribute name="title" ignore="true" /></title>
<%@include file="/jsp/common/css.jsp"%>
<%@include file="/jsp/common/script.jsp"%>
<script type="text/javascript">
var mask = new ax5.ui.mask();
function open() {
	mask.open();
}

function close() {
	mask.close();
}
</script>
</head>
<body class="sidebar-style expanded">
	<!-- header -->
	<tiles:insertAttribute name="header" />

	<!-- main -->
	<main class="main-container">
		<tiles:insertAttribute name="menu" />
		<div class="content container-fluid">
			<iframe src="/Windchill/jsp/index.jsp" id="body"></iframe>
		</div>
	</main>
	<!-- footer -->
	<tiles:insertAttribute name="footer" />

</body>
</html>