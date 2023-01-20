<%@page import="platform.util.CommonUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	boolean isAdmin = CommonUtils.isAdmin();
%>
	<div class="header-title close lumpDelete">
			<img src="/Windchill/jsp/images/home.png" class="home">
			<span>HOME</span>
			>
			<span>BOM 관리</span>
			>
			<span>MBOM 조회</span>
			>
			<span>일괄 삭제</span>
		</div>
		<div class="close" id="lumpDelete" >
					<table class="lump-table top-color">
						<colgroup>
							<col width="90">
							<col width="200">
							<col width="90">
							<col width="250">
							<col width="500">
						</colgroup>
						<tr>
							<th>제품코드</th>
							<td>
								<input type="text" name="number" class="AXInput w80p" placeholder="mBOM 단품코드 선택 창">
							</td>
							<th>색상코드</th>
							<td colspan="2">
								<input type="text" name="ecoNumber" class="AXInput w10p" placeholder="색상코드">
							로 시작하는 모든 제품에서
							</td>
						</tr>
						
						<tr>
							<th>자재코드</th>
							<td>
								<input type="text" class="AXInput w60p" name="" id="" placeholder="자재코드">
							</td>
							<th>색상코드</th>
							<td>
								<input type="text" class="AXInput w30p" name="" placeholder="색상코드">
								를 삭제합니다
							</td>
							<td class="right">
							<button type="button">일괄삭제</button>
							&nbsp;
							</td>
						</tr>
			</table>
		</div>
					
<script type="text/javascript">
</script>