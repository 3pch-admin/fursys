<%@page import="platform.util.ThumbnailUtils"%>
<%@page import="platform.dist.service.DistHelper"%>
<%@page import="platform.dist.vo.DistFileVO"%>
<%@page import="wt.content.ApplicationData"%>
<%@page import="wt.content.ContentRoleType"%>
<%@page import="wt.content.ContentHelper"%>
<%@page import="com.ptc.wvs.server.util.PublishUtils"%>
<%@page import="wt.representation.Representation"%>
<%@page import="wt.epm.EPMDocument"%>
<%@page import="wt.part.WTPart"%>
<%@page import="platform.dist.entity.DistPartColumns"%>
<%@page import="platform.dist.entity.DistributorUserColumns"%>
<%@page import="platform.util.CommonUtils"%>
<%@page import="wt.fc.PersistenceHelper"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="platform.code.service.BaseCodeHelper"%>
<%@page import="platform.dist.entity.DistDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
DistDTO dto = (DistDTO) request.getAttribute("dto");

%>
<input type="hidden" name="oid" id="oid" value="<%=dto.getOid() %>">
<div class="header-title">
	<img src="/Windchill/jsp/images/home.png" class="home">
	<span>HOME</span>
	>
	<span>도면 조회 및 배포</span>
	>
	<span>도면 배포 정보</span>
</div>

<div id="tabs"></div>
<br>
<table class="view-table info-view">
	<colgroup>
		<col width="100">
		<col width="*">
		<col width="100">
		<col width="*">
	</colgroup>
	<tr>
		<th>배포 번호</th>
		<td><%=dto.getNumber()%></td>
		<th>다운로드 기간</th>
		<td><%=dto.getDuration()%>&nbsp;주</td>
	</tr>
	<tr>
		<th>배포명</th>
		<td><%=dto.getName() %></td>
		<th>작성자</th>
		<td><%=dto.getCreator()%></td>
		
	</tr>
	
<!-- 	<tr> -->
<!-- 		<th>description</th> -->
<%-- 		<td><%=dto.getDescription() %></td> --%>
		
<!-- 	</tr> -->
		<tr>
		<th>내용</th>
		<td colspan="3">
			<div class="raonkEditor">
				<script type="text/javascript">
					var kEditorParam = {
						Id : "description",
						Width : "100%",
						Height : "200px",
						Runtimes : 'html5', // agent, html5
						Mode : 'view'
					};
					new RAONKEditor(kEditorParam);
				</script>
			</div>
		</td>
	</tr>
	
	<tr>
		<th>배포처</th>
		<td colspan="3"    >
			<table class="view-table info-view">
				<tr>
					<th>배포처</th>
					<th>사용자 아이디(이메일)</th>
					<th>사용자명</th>
				</tr>
				<%
				for(DistributorUserColumns diUser : dto.getDistributorUserList()){
				%>
				<tr>
					<td><%=diUser.getName() %></td>
					<td><%=diUser.getUserId() %></td>
					<td><%=diUser.getUserName() %></td>
				</tr>
				<%
				}
				%>
			</table>
		</td>
	</tr>
	<tr>
		<th>배포 도면</th>
		<td colspan="3">
			<table class="view-table info-view">
				<tr>
<!-- 					<th>-</th> -->
<th>3D</th>
<th>2D</th>
					<th>부품번호</th>
					<th>부품명칭</th>
					<th>버전</th>
					<th>PDF[링크][도면]</th>
					<th>DWG[링크][도면]</th>
					<th>STEP[링크][도면]</th>
<!-- 					<th>oid</th> -->
				</tr>
				<%
				for(DistPartColumns di_part : dto.getPartList()){
					System.out.println("#### getOid=="+di_part.getOid());
					String oid =di_part.getOid();
					String[] thumb2 = null;
					if( di_part.getOid() != null){
						WTPart part = (WTPart) CommonUtils.persistable(di_part.getOid());
						
						System.out.println("#### getEoid=="+di_part.getEoid());
						if( di_part.getEoid() != null && di_part.getEoid().length() > 0  ){
							EPMDocument epm = (EPMDocument) CommonUtils.persistable(di_part.getEoid());
							oid +="//3d="+di_part.getEoid();
							String docType = epm.getDocType().toString();
							oid +="//3d_type="+docType;
							if (epm != null) {
								Representation representation = PublishUtils.getRepresentation(epm);
								if(representation != null){
									QueryResult result = ContentHelper.service.getContentsByRole(representation, ContentRoleType.ADDITIONAL_FILES);
									while (result.hasMoreElements()) {
										ApplicationData data = (ApplicationData) result.nextElement();
										oid +="//stp="+data.getFileName();
										//out.println(data.getFileName());
									DistFileVO fileVo = DistHelper.manager.getDistFileVO(epm);
									oid +="//voStp="+fileVo.getStpFile().getFileName();
									out.println(fileVo.getStpFile().getFileName());
									}
								}
								
								
								if( di_part.getEoid2d() != null && di_part.getEoid2d().length() > 0  ){
									EPMDocument epm2d = (EPMDocument) CommonUtils.persistable(di_part.getEoid2d());
									if (epm2d != null) {
										thumb2 = ThumbnailUtils.thumbnails(epm2d);
										oid +="//2d="+di_part.getEoid2d();
										Representation representation2 = PublishUtils.getRepresentation(epm2d);
										if(representation2 != null){
											QueryResult result2 = ContentHelper.service.getContentsByRole(representation2, ContentRoleType.ADDITIONAL_FILES);
											while (result2.hasMoreElements()) {
												ApplicationData data = (ApplicationData) result2.nextElement();
												oid +="//2d2="+data.getFileName();
												//out.println(data.getFileName());
											}
										}
									}
								}
							}						
						}
					}
					String[] thumb = ThumbnailUtils.thumbnails(di_part.getOid());
				%>
				<tr>
					<td style="text-align: center">
						<img src="<%=thumb[1]%>"style= "cursor: pointer;"  id="thumb" data-oid="<%=di_part.getEoid()%>"></td>
					<td style="text-align: center">
						<%
						if (thumb2 != null) {
						%><img src="<%=thumb2[1]%>" style="cursor: pointer;" id="thumb2" data-oid="<%=di_part.getEoid2d()%>">
					</td>
						<%
						}
						%>
<%-- 					<td><%=di_part.getLinkOid() %></td> --%>
					<td><%=di_part.getNumber() %></td>
					<td><%=di_part.getName() %></td>
					<td><%=di_part.getVersion() %></td>
					<td>[<%=di_part.isLinkPdf()?"O":"X" %>][<%=di_part.isPdf()?"O":"X" %>]</td>
					<td>[<%=di_part.isLinkDwg()?"O":"X" %>][<%=di_part.isDwg()?"O":"X" %>]</td>
					<td>[<%=di_part.isLinkStep()?"O":"X" %>][<%=di_part.isStep()?"O":"X" %>]</td>
<%-- 					<td><%=oid %></td> --%>
				</tr>
				<%
					
				}
				%>
			</table>
		
		</td>
	</tr>
	
</table>


<!-- 결재 이력 -->
<jsp:include page="/jsp/approval/approval-history.jsp">
	<jsp:param value="<%=dto.getOid()%>" name="oid" />
</jsp:include>


<table class="button-table">
	<tr>
		<td class="right">
			<button type="button" id="sendEpBtn">ep_send</button>
			<button type="button" id="sendCPCBtn">cpc_send</button>
			<button type="button" id="modifyBtn">수정</button>
			<button type="button" id="deleteBtn">삭제</button>
			<button type="button" id="closeBtn">닫기</button>
		</td>
	</tr>
</table>

<script type="text/javascript">
	$(function() {
		
		$("#thumb").click(function() {
			var oid = $(this).data("oid");
			console.log(oid);
			_openCreoView(oid);
		})

		$("#sendEpBtn").click(function() {
			if (!confirm("ep 전송 하시겠습니까?")) {
				return false;
			}
	
			var params = _data($("#form"));
			var url = _url("/dist/sendEp2");
			console.log(params);
			_call(url, params, function(data) {
				opener.load();
				//self.close();
			}, "POST");
		})
		
		$("#sendCPCBtn").click(function() {
			if (!confirm("CPC 전송 하시겠습니까?")) {
				return false;
			}
	
			var params = _data($("#form"));
			var url = _url("/dist/sendCPC");
			console.log(params);
			_call(url, params, function(data) {
				opener.load();
				//self.close();
			}, "POST");
		})
		
		$("#modifyBtn").click(function() {
			var url = _url("/dist/modify", "<%=dto.getOid()%>");
			document.location.href = url;
		})

		$("#closeBtn").click(function() {
			self.close();
		})

		$("#tabs").bindTab({
			theme : "AXTabs",
			value : "1",
			overflow : "scroll", /* "visible" */
			options : [ {
				optionValue : "1",
				optionText : "기본정보",
			}, {
				optionValue : "4",
				optionText : "결재이력"
			} ],
			onchange : function(selectedObject, value) {
				if (value == "1") {
					$(".info-view").show();
					$(".file-view").hide();
					$("#part_grid_wrap").hide();
					$(".ebom").hide();
					$(".ebom-search").hide();
					$(".ebr").hide();
					$(".mbom").hide();
					$(".mbom-search").hide();
					$("#doc_grid_wrap").hide();
					$(".app-view").hide();
					$(".dist-part-view").hide();
					$(".ref_dist_distributor_grid_wrap").hide();
				} else if (value == "4") {
					$(".info-view").hide();
					$(".file-view").hide();
					$("#part_grid_wrap").hide();
					$(".ebom").show();
					$(".ebr").show();
					_selector("ecolor");
					$(".mbom").hide();
					$(".mbom-search").hide();
					$("#doc_grid_wrap").hide();
					$(".app-view").hide();
					$(".dist-part-view").hide();
					$(".ref_dist_distributor_grid_wrap").hide();
				} 
			},
		});
		
		
		$(window).resize(function() {
			
		})
		RAONKEDITOR.SetHtmlContents(decodeURIComponent(escape(window.atob("<%=dto.getDescription()%>"))), "description");
	})
</script>