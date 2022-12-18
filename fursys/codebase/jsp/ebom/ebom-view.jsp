<%@page import="platform.ebom.entity.EBOM"%>
<%@page import="platform.util.IBAUtils"%>
<%@page import="platform.util.CommonUtils"%>
<%@page import="wt.part.WTPart"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String oid = (String) request.getAttribute("oid");
EBOM ebom = (EBOM) request.getAttribute("ebom");
WTPart part = (WTPart) request.getAttribute("part");
%>
<style type="text/css">
.library {
	background-color: #fefbc0;
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

.aui-grid-tree-branch-icon {
	display: inline-block;
	width: 18px;
	height: 16px;
	background: url(/Windchill/jsp/images/part.gif) 50% 50% no-repeat;
	background-size: 16px;
	vertical-align: bottom;
	margin: 0 2px 0 0;
}

.aui-grid-tree-leaf-icon {
	display: inline-block;
	width: 16px;
	height: 16px;
	background: url(/Windchill/jsp/images/part.gif) 50% 50% no-repeat;
	vertical-align: bottom;
	margin: 0 2px 0 4px;
}

.aui-grid-tree-branch-open-icon {
	display: inline-block;
	width: 18px;
	height: 16px;
	background: url(/Windchill/jsp/images/part.gif) 50% 50% no-repeat;
	background-size: 16px;
	vertical-align: bottom;
	margin: 0 2px 0 0;
}

.move {
	background: #9FC93C;
	font-weight: bold;
	color: #22741C;
}
</style>
<div class="header-title">
	<img src="/Windchill/jsp/images/home.png" class="home">
	<span>HOME</span>
	>
	<span>BOM관리</span>
	>
	<span>EBOM 등록</span>
</div>
<table class="search-table top-color">
	<colgroup>
		<col width="150">
		<col width="580">
		<col width="150">
		<col width="580">
	</colgroup>
	<tr>
		<th>부품명칭(단품, 세트)</th>
		<td>
			<input type="hidden" name="oid" class="AXInput w70p" readonly="readonly" value="<%=part.getPersistInfo().getObjectIdentifier().getStringValue()%>">
			<input type="hidden" name="eoid" class="AXInput w70p" readonly="readonly" value="<%=oid%>">
			<input type="text" name="number" class="AXInput w70p" readonly="readonly" value="<%=part.getNumber()%>">
		</td>
		<th>유형</th>
		<td>
			<input type="text" name="partType" class="AXInput w30p" readonly="readonly" value="<%=IBAUtils.getStringValue(part, "PART_TYPE")%>">
		</td>
	</tr>
</table>

<table class="button-table">
	<tr>
		<td class="right">
			<button type="button" id="verifyBtn">검증</button>
			<button type="button" id="modifyBtn">수정</button>
			<button type="button" id="closeBtn">닫기</button>
		</td>
	</tr>
</table>

<div id="grid_right" style="height: 840px;"></div>
<script type="text/javascript">
	
</script>