<%@page import="platform.util.CommonUtils"%>
<%@page import="platform.doc.service.DocumentHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	boolean isAdmin = CommonUtils.isAdmin();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<%@include file="/jsp/common/iframe.jsp"%>
<%@include file="/jsp/common/script.jsp"%>
</head>
<!-- <body> -->

<div id="tabs"></div>
<br>
		<div class="header-title lumpReplace">
			<img src="/Windchill/jsp/images/home.png" class="home">
			<span>HOME</span>
			>
			<span>BOM 관리</span>
			>
			<span>MBOM 조회</span>
			>
			<span>일괄 대체</span>
		</div>
					<table class="lump-table top-color lumpReplace">
						<colgroup>
							<col width="90">
							<col width="200">
							<col width="90">
							<col width="150">
							<col width="30">
							<col width="90">
							<col width="200">
							<col width="90">
							<col width="*">
							<col width="90">
						</colgroup>
						<tr>
							<th>제품코드</th>
							<td>
								<input type="text" name="number" class="AXInput w80p" placeholder="mBOM 단품코드 선택 창">
							</td>
							<th>색상코드</th>
							<td colspan="8">
								<input type="text" name="ecoNumber" class="AXInput w10p" placeholder="색상코드">
							로 시작하는 모든 제품에서
							</td>
						</tr>
						
						<tr>
							<th>자재코드</th>
							<td>
							<input type="text" name="" class="AXInput w60p" placeholder="자재코드">
							</td>
							<th>색상코드</th>
							<td>
								<input type="text" name="" class="AXInput w60p" placeholder="색상코드">
								 </td>
								 <td>
								▶
								 &nbsp;
								</td>
							<th>자재코드</th>
							<td>
								<input type="text" class="AXInput w60p" name="" id="" placeholder="자재코드">
							</td>
							<th>색상코드</th>
							<td>
								<input type="text" class="AXInput w40p" name="" placeholder="색상코드">
								로 대체합니다
							</td>
							<td>
							<button type="button">일괄대체</button>
							&nbsp;
							</td>
						</tr>
			</table>
		
		<!--일괄삭제-->
		<jsp:include page="/jsp/mbom/mbom-lumpDelete.jsp"></jsp:include>
		

<script type="text/javascript">
	$(function() {

		$("#tabs").bindTab({
			theme : "AXTabs",
			value : "1",
			overflow : "scroll", /* "visible" */
			options : [ {
				optionValue : "1",
				optionText : "일괄대체",
			}, {
				optionValue : "2",
				optionText : "일괄삭제",
			} ],
			onchange : function(selectObject, value) {
				if (value == "1") {
					//기본
					$(".lumpReplace").show();
					$(".lumpDelete").hide();
					$("#lumpDelete").hide();
					//일괄삭제
				} else if (value == "2") {
					$(".lumpReplace").hide();
					$(".lumpDelete").show();
					$("#lumpDelete").show();
				}
			}
		})
	})
</script>