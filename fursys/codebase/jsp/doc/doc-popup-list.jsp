<%@page import="platform.code.entity.BaseCode"%>
<%@page import="java.util.ArrayList"%>
<%@page import="platform.doc.service.DocumentHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
ArrayList<BaseCode> brand = (ArrayList<BaseCode>) request.getAttribute("brand");
ArrayList<BaseCode> company = (ArrayList<BaseCode>) request.getAttribute("company");
%>
<input type="hidden" name="sessionid" id="sessionid">
<input type="hidden" name="tpage" id="tpage">
<div class="header-title">
	<img src="/Windchill/jsp/images/home.png" class="home">
	<span>HOME</span>
	>
	<span>기술문서 관리</span>
	>
	<span>기술문서 조회</span>
</div>
<table id="wrap-table">
	<colgroup>
		<col width="200">
		<col width="10">
		<col width="*">
	</colgroup>
	<tr>
		<td valign="top" class="tree-border">
			<jsp:include page="/jsp/common/folder-tree.jsp">
				<jsp:param value="<%=DocumentHelper.ROOT%>" name="location" />
				<jsp:param value="DOCUMENT" name="context" />
			</jsp:include>
		</td>
		<td>&nbsp;</td>
		<td>
			<table class="search-table top-color">
				<colgroup>
					<col width="100">
					<col width="*">
					<col width="100">
					<col width="*">
					<col width="100">
					<col width="*">
				</colgroup>
				<tr>
					<th>문서분류</th>
					<td colspan="3">
						<input type="text" class="AXInput w80p" name="location" id="location" value="<%=DocumentHelper.ROOT%>" readonly="readonly">
					</td>
				</tr>
				<tr>
					<th>문서제목</th>
					<td>
						<input type="text" class="AXInput w70p" name="name">
					</td>
					<th>문서번호</th>
					<td>
						<input type="text" class="AXInput w70p" name="number">
					</td>
					<th>상태</th>
					<td>
						<select name="state" class="AXSelect w100px" id="state">
							<option value="">선택</option>
							<option value="INWORK">작업 중</option>
							<option value="UNDER">승인 중</option>
							<option value="RELEASED">릴리즈됨</option>
							<option value="RETURN">반려됨</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>관련부품</th>
					<td>
						<input type="text" class="AXInput w70p" name="part" id="part">
						<i class="axi axi-close2 axicon"></i>
					</td>
					<th>관련프로젝트</th>
					<td>
						<input type="text" class="AXInput w70p" name="project" id="project">
						<i class="axi axi-close2 axicon"></i>
					</td>
					<th>버전</th>
					<td>
						<label>
							<input type="radio" name="latest" value="true" checked="checked">
							<span>최신버전</span>
						</label>
						&nbsp;
						<label>
							<input type="radio" name="latest" value="false">
							<span>모든버전</span>
						</label>
					</td>
				</tr>
				<tr>
					<th>작성자</th>
					<td>
						<input type="text" class="AXInput w200px" name="creator" id="creator">
						<i class="axi axi-close2 axicon clearUser" data-target="creator"></i>
					</td>
					<th>작성일자</th>
					<td>
						<input type="text" class="AXInput w100px" name="startCreatedDate" id="startCreatedDate" maxlength="8">
						~
						<input type="text" class="AXInput w100px" name="endCreatedDate" id="endCreatedDate" data-start="startCreatedDate" maxlength="8">
						<i class="axi axi-close2 axicon clearBetween" data-target="CreatedDate"></i>
					</td>
					<th>회사</th>
					<td>
						<select name="company" id="company" class="AXSelect w200px">
							<option value="">선택</option>
							<%
							for (BaseCode c : company) {
							%>
							<option value="<%=c.getCode()%>"><%=c.getName()%></option>
							<%
							}
							%>
						</select>
					</td>
				</tr>
				<tr>
					<th>수정자</th>
					<td>
						<input type="text" class="AXInput w200px" name="modifier" id="modifier">
						<i class="axi axi-close2 axicon clearUser" data-target="modifier"></i>
					</td>
					<th>수정일자</th>
					<td>
						<input type="text" class="AXInput w100px" name="startModifiedDate" id="startModifiedDate" maxlength="8">
						~
						<input type="text" class="AXInput w100px" name="endModifiedDate" id="endModifiedDate" data-start="startModifiedDate" maxlength="8">
						<i class="axi axi-close2 axicon clearBetween" data-target="ModifiedDate"></i>
					</td>
					<th>브랜드</th>
					<td>
						<select name="brand" id="brand" class="AXSelect w200px">
							<option value="">선택</option>
							<%
							for (BaseCode b : brand) {
							%>
							<option value="<%=b.getCode()%>"><%=b.getName()%></option>
							<%
							}
							%>
						</select>
					</td>
				</tr>
			</table>

			<table class="button-table">
				<tr>
					<td class="right">
						<button type="button" id="addBtn">추가</button>
						<button type="button" id="searchBtn">조회</button>
						<button type="button" id="closeBtn">닫기</button>
					</td>
				</tr>
			</table>

			<div id="grid_wrap" style="height: 695px;"></div>
			<div id="grid_paging" class="aui-grid-paging-panel my-grid-paging-panel"></div>
			<script type="text/javascript">
				var myGridID;
				var totalRowCount;
				var rowCount = 30; // 1페이지에서 보여줄 행 수
				var pageButtonCount = 10; // 페이지 네비게이션에서 보여줄 페이지의 수
				var currentPage = 1; // 현재 페이지
				var totalPage;
				var columnLayout = [ {
					dataField : "no",
					headerText : "번호",
					dataType : "string",
					width : 40
				}, {
					dataField : "number",
					headerText : "문서번호",
					dataType : "string",
					width : 120
				}, {
					dataField : "name",
					headerText : "문서명",
					dataType : "string",
					style : "left indent10"
				}, {
					dataField : "version",
					headerText : "버전",
					dataType : "string",
					width : 80
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
					dataField : "modifier",
					headerText : "수정자",
					dataType : "string",
					width : 100
				}, {
					dataField : "modifiedDate",
					headerText : "수정일자",
					dataType : "date",
					formatString : "yyyy/mm/dd",
					width : 100
				}, {
					dataField : "companyNm",
					headerText : "회사",
					dataType : "string",
					width : 130
				}, {
					dataField : "brandNm",
					headerText : "브랜드",
					dataType : "string",
					width : 130
				}, {
					dataField : "state",
					headerText : "상태",
					dataType : "string",
					width : 100
				}, {
					// 							dataField : "primary",
					headerText : "첨부파일",
					dataType : "string",
					width : 80,
					renderer : {
						type : "IconRenderer",
						iconWidth : 16,
						iconHeight : 16,
						iconFunction : function(rowIndex, columnIndex, value, item) {
							return item.primary;
						},
						onClick : function(event) {
							var url = event.item.url;
							alert("다운로드 만들어내기..");
						}
					}
				}, {
					dataField : "oid",
					headerText : "oid",
					dataType : "string",
					visible : false
				}, ];
				var auiGridProps = {
					rowIdField : "oid",
					headerHeight : 30,
					rowHeight : 30,
					fillColumnSizeMode : true,
					showRowCheckColumn : true,
					rowNumHeaderText : "번호",
					showRowNumColumn : false
				};
				myGridID = AUIGrid.create("#grid_wrap", columnLayout, auiGridProps);
				createPagingNavigator(1);
				// 페이징 네비게이터를 동적 생성합니다.
				function createPagingNavigator(goPage) {
					var retStr = "";
					var prevPage = parseInt((goPage - 1) / pageButtonCount) * pageButtonCount;
					var nextPage = ((parseInt((goPage - 1) / pageButtonCount)) * pageButtonCount) + pageButtonCount + 1;
					prevPage = Math.max(0, prevPage);
					nextPage = Math.min(nextPage, totalPage);
					// 처음
					retStr += "<a href='javascript:moveToPage(1)'><span class='aui-grid-paging-number aui-grid-paging-first'>first</span></a>";

					// 이전
					retStr += "<a href='javascript:moveToPage(" + Math.max(1, prevPage) + ")'><span class='aui-grid-paging-number aui-grid-paging-prev'>prev</span></a>";

					for (var i = (prevPage + 1), len = (pageButtonCount + prevPage); i <= len; i++) {
						if (goPage == i) {
							retStr += "<span class='aui-grid-paging-number aui-grid-paging-number-selected'>" + i + "</span>";
						} else {
							retStr += "<a href='javascript:moveToPage(" + i + ")'><span class='aui-grid-paging-number'>";
							retStr += i;
							retStr += "</span></a>";
						}

						if (i >= totalPage) {
							break;
						}

					}

					// 다음
					retStr += "<a href='javascript:moveToPage(" + nextPage + ")'><span class='aui-grid-paging-number aui-grid-paging-next'>next</span></a>";

					// 마지막
					retStr += "<a href='javascript:moveToPage(" + totalPage + ")'><span class='aui-grid-paging-number aui-grid-paging-last'>last</span></a>";

					document.getElementById("grid_paging").innerHTML = retStr;
				}
				function load() {
					var params = _data($("#form"));
					var url = _url("/doc/list");
					AUIGrid.showAjaxLoader(myGridID);
					_call(url, params, function(data) {
						totalRowCount = data.total;
						totalPage = Math.ceil(totalRowCount / data.pageSize);
						$("input[name=sessionid").val(data.sessionid);
						createPagingNavigator(data.curPage);
						AUIGrid.removeAjaxLoader(myGridID);
						AUIGrid.setGridData(myGridID, data.list);
					}, "POST");
				}

				$(function() {
					load();

					$("#searchBtn").click(function() {
						currentPage = 1;
						$("input[name=tpage").val(1);
						$("input[name=sessionid").val(0);
						load();
					})
					$("#closeBtn").click(function() {
						self.close();
					})
					$("#addBtn").click(function() {
						var items = AUIGrid.getCheckedRowItems(myGridID);
						if (items.length == 0) {
							alert("추가 할 문서를 선택하세요.");
							return false;
						}
						var list = _array(items);
						var params = new Object();
						params.list = list;
						var url = _url("/doc/info");
						_call(url, params, function(data) {
							opener.doc(data.info);
							self.close();
						}, "POST");
					})

					AUIGrid.bind(myGridID, "cellClick", function(event) {
						var item = event.item, rowIdField, rowId;
						rowIdField = AUIGrid.getProp(event.pid, "rowIdField"); // rowIdField 얻기
						rowId = item[rowIdField];

						// 이미 체크 선택되었는지 검사
						if (AUIGrid.isCheckedRowById(event.pid, rowId)) {
							// 엑스트라 체크박스 체크해제 추가
							AUIGrid.addUncheckedRowsByIds(event.pid, rowId);
						} else {
							// 엑스트라 체크박스 체크 추가
							AUIGrid.addCheckedRowsByIds(event.pid, rowId);
						}
					});

					// 작성자, 수정자 선택바인드
					_folder("location", "/Default/문서");
					_user("creator");
					_user("modifier");
					_finder("part", "/util/part");
					_finder("project", "/util/project");
					_selector("state");
					_selector("brand");
					_selector("company");
					_between("endCreatedDate");
					_between("endModifiedDate");
					_check("latest");
				}).keypress(function(e) {
					if (e.keyCode == 13) {
						currentPage = 1;
						$("input[name=tpage").val(1);
						$("input[name=sessionid").val(0);
						load();
					}
				})

				$(window).resize(function() {
					AUIGrid.resize("#grid_wrap");
				})
			</script>
		</td>
	</tr>
</table>