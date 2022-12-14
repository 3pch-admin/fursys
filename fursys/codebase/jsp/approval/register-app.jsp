<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<table class="button-table register-button close">
	<tr>
		<td class="left">
			<button type="button" id="addLine">추가</button>
		</td>
	</tr>
</table>
<table class="wrap-table register-app close">
	<colgroup>
		<col width="50%">
		<col width="80">
		<col width="50%">
	</colgroup>
	<tr>
		<td valign="top">
			<table class="opener-app-table" id="app_table">
				<colgroup>
					<col width="80">
					<col width="*">
					<col width="100">
					<col width="100">
					<col width="130">
					<col width="300">
				</colgroup>
				<thead>
					<tr>
						<td colspan="5" class="none">
							<div class="tabNav">■ 결재 및 경유</div>
						</td>
						<td class="right none">
							<div>
								<span class="limitText">의견만기일 :</span>
								<input type="text" name="appLimit" class="AXInput w100px" id="appLimit" readonly="readonly">
							</div>
						</td>
					</tr>
					<tr>
						<th>결재형식</th>
						<th>이름</th>
						<th>아이디</th>
						<th>직급</th>
						<th>부서</th>
						<th>이메일</th>
					</tr>
				</thead>
				<tbody id="appBody">
				</tbody>
			</table>
		</td>
		<td>&nbsp;</td>
		<td valign="top">
			<table class="opener-app-table" id="ref_table">
				<colgroup>
					<col width="*">
					<col width="100">
					<col width="100">
					<col width="100">
					<col width="250">
				</colgroup>
				<thead>
					<tr>
						<td colspan="6" class="none">
							<div class="tabNav">■ 참조</div>
						</td>
					</tr>
					<tr>
						<th>이름</th>
						<th>아이디</th>
						<th>직급</th>
						<th>부서</th>
						<th>이메일</th>
					</tr>
				</thead>
				<tbody id="refBody">
				</tbody>
			</table>
		</td>
	</tr>
</table>
<script type="text/javascript">
	$(function() {
		$("#addLine").click(function() {
			var url = "/Windchill/platform/app/popup";
			_popup(url, 1600, 850, "n");
		})
	})
</script>