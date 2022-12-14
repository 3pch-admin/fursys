<%@page import="platform.util.CommonUtils"%>
<%@page import="wt.session.SessionHelper"%>
<%@page import="wt.org.WTUser"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
WTUser user = (WTUser) SessionHelper.manager.getPrincipal();
boolean isAdmin = CommonUtils.isAdmin();
int appCount = (int) request.getAttribute("appCount");
int receiveCount = (int) request.getAttribute("receiveCount");
int completeCount = (int) request.getAttribute("completeCount");
int rtnCount = (int) request.getAttribute("rtnCount");
%>
<!-- SIDEBAR LEFT -->
<div class="sidebar">
	<!-- Scrollable -->
	<div class="height100p custom-scroll">

		<!-- SIDEBAR PROFILE -->
		<div class="sidebar-profile">

			<!-- Profile Avatar -->
			<a href="#" class="dropdown-toggle" data-toggle="dropdown">
				<div class="avatar avatar-lg">
					<img src="/Windchill/jsp/images/avatar-louis-hawkins.jpg" alt="Louis Hawkins">
				</div>
			</a>
			<!-- /Profile Avatar -->

			<!-- User Information -->
			<div class="user-info">
				<div class="name"><%=user.getFullName()%></div>
				<div class="post"><%=user.getEMail() != null ? user.getEMail() : ""%></div>
			</div>
			<!-- /User Information -->

			<!-- Profile dropdown menu -->
			<ul class="dropdown-menu dropdown-menu-dark sidebar-dropdown-profile">

				<li>
					<a href="#">
						<i class="icon icon-inline fa fa-address-card-o"></i>
						Profile
					</a>
				</li>
				<li>
					<a href="#" class="has-badge">
						<i class="icon icon-inline fa fa-envelope-o"></i>
						Inbox
						<span class="badge badge-notification badge-danger">3</span>
					</a>
				</li>
				<li>
					<a href="#">
						<i class="icon icon-inline fa fa-tasks"></i>
						Tasks
					</a>
				</li>
				<li>
					<a href="#" class="has-badge">
						<i class="icon icon-inline fa fa-calendar"></i>
						Calendar
						<span class="badge badge-notification badge-primary">5</span>
					</a>
				</li>

				<li class="divider"></li>

				<li>
					<a href="#">
						<i class="icon icon-inline fa fa-gears"></i>
						Settings
					</a>
				</li>
				<li>
					<a href="#">
						<i class="icon icon-inline fa fa-lock"></i>
						Lock Screen
					</a>
				</li>
				<li>
					<a href="javascript:_logout();">
						<i class="icon icon-inline fa fa-sign-out"></i>
						로그아웃
					</a>
				</li>
			</ul>
			<!-- /Profile dropdown menu -->

		</div>
		<!-- /SIDEBAR PROFILE -->

		<!-- SIDEBAR NAVIGATION -->
		<ul class="nav-sidebar">

			<li class="sub active">
				<a href="#" class="sub-toggle">
					<i class="icon icon-inline icon-inline fa fa-home"></i>
					<span class="title">나의할일</span>
				</a>
				<ul class="sub-menu collapse" data-menu-title="나의할일">
					<li>
						<a href="javascript:_page('/app/list?type=A')">
							<i class="icon icon-inline fa fa-circle-thin"></i>
							<span class="title">완료함</span>
							<span class="badge badge-danger badge-notification" style="position: relative; bottom: 2px;"><%=completeCount%></span>
						</a>
					</li>
					<li>
						<a href="javascript:_page('/app/list?type=B')">
							<i class="icon icon-inline fa fa-circle-thin"></i>
							<span class="title">진행함</span>
						</a>
					</li>
					<li>
						<a href="javascript:_page('/app/list?type=C')">
							<i class="icon icon-inline fa fa-circle-thin"></i>
							<span class="title"> 결재함</span>
							<span class="badge badge-danger badge-notification" style="position: relative; bottom: 2px;"><%=appCount%></span>
						</a>
					</li>
					<li>
						<a href="javascript:_page('/app/list?type=D')">
							<i class="icon icon-inline fa fa-circle-thin"></i>
							<span class="title">수신함</span>
							<span class="badge badge-danger badge-notification" style="position: relative; bottom: 2px;"><%=receiveCount%></span>
						</a>
					</li>
					<li>
						<a href="javascript:_page('/app/list?type=E')">
							<i class="icon icon-inline fa fa-circle-thin"></i>
							<span class="title">반려함</span>
							<span class="badge badge-danger badge-notification" style="position: relative; bottom: 2px;"><%=rtnCount%></span>
						</a>
					</li>
				</ul>
			</li>

			<li class="sub">
				<a href="#" class="sub-toggle">
					<i class="icon icon-inline fa fa-commenting"></i>
					<span class="title">프로젝트 관리</span>
				</a>
				<ul class="sub-menu" data-menu-title="프로젝트 관리">
					<li>
						<a href="javascript:_page('/project/list')">
							<i class="icon icon-inline fa fa-circle-thin"></i>
							<span class="title">프로젝트 조회</span>
						</a>
					</li>
					<li>
						<a href="javascript:_page('/project/my')">
							<i class="icon icon-inline fa fa-circle-thin"></i>
							<span class="title">나의 프로젝트</span>
						</a>
					</li>
					<li>
						<a href="javascript:_page('/task/my')">
							<i class="icon icon-inline fa fa-circle-thin"></i>
							<span class="title">나의 태스크</span>
						</a>
					</li>
					<li>
						<a href="javascript:_page('/issue/list')">
							<i class="icon icon-inline fa fa-circle-thin"></i>
							<span class="title">나의 이슈</span>
						</a>
					</li>
				</ul>
			</li>

			<li class="sub">
				<a href="#" class="sub-toggle">
					<i class="icon icon-inline fa fa-user"></i>
					<span class="title">템플릿 관리</span>
				</a>
				<ul class="sub-menu collapse" data-menu-title="템플릿 관리">
					<li>
						<a href="javascript:_page('/template/list')">
							<i class="icon icon-inline fa fa-circle-thin"></i>
							<span class="title">템플릿 조회</span>
						</a>
					</li>
				</ul>
			</li>

			<!-- 			<li class="sub"> -->
			<!-- 				<a href="#" class="sub-toggle"> -->
			<!-- 					<i class="icon icon-inline fa fa-diamond"></i> -->
			<!-- 					<span class="title">도면 관리</span> -->
			<!-- 				</a> -->
			<!-- 				<ul class="sub-menu collapse" data-menu-title="도면 관리"> -->
			<!-- 					<li> -->
			<!-- 						<a href="javascript:_page('/epm/list')"> -->
			<!-- 							<i class="icon icon-inline fa fa-circle-thin"></i> -->
			<!-- 							<span class="title">도면 조회</span> -->
			<!-- 						</a> -->
			<!-- 					</li> -->
			<!-- 				</ul> -->
			<!-- 			</li> -->

			<li class="sub">
				<a href="#" class="sub-toggle">
					<i class="icon icon-inline fa fa-quora"></i>
					<span class="title">부품 관리</span>
				</a>
				<ul class="sub-menu collapse" data-menu-title="부품 관리">
					<!-- 					<li> -->
					<!-- 						<a href="javascript:_page('/part/single')"> -->
					<!-- 							<i class="icon icon-inline fa fa-circle-thin"></i> -->
					<!-- 							<span class="title">부품(단품) 등록</span> -->
					<!-- 						</a> -->
					<!-- 					</li> -->
					<!-- 					<li> -->
					<!-- 						<a href="javascript:_page('/part/material')"> -->
					<!-- 							<i class="icon icon-inline fa fa-circle-thin"></i> -->
					<!-- 							<span class="title">부품(자재) 등록</span> -->
					<!-- 						</a> -->
					<!-- 					</li> -->
					<li>
						<a href="javascript:_page('/part/list')">
							<i class="icon icon-inline fa fa-circle-thin"></i>
							<span class="title">부품 조회</span>
						</a>
					</li>
				</ul>
			</li>

			<li class="sub">
				<a href="#" class="sub-toggle">
					<i class="icon icon-inline fa fa-fonticons"></i>
					<span class="title">BOM 관리</span>
				</a>
				<ul class="sub-menu collapse" data-menu-title="BOM 관리">
					<li>
						<a href="javascript:_page('/ebomMaster/list');">
							<i class="icon icon-inline fa fa-circle-thin"></i>
							<span class="title">EBOM 조회</span>
						</a>
					</li>
					<li>
						<a href="javascript:_page('/ebomMaster/plist');">
							<i class="icon icon-inline fa fa-circle-thin"></i>
							<span class="title">PARTLIST 조회</span>
						</a>
					</li>
					<li>
						<a href="javascript:_page('/mbom/list');">
							<i class="icon icon-inline fa fa-circle-thin"></i>
							<span class="title">MBOM 조회</span>
						</a>
					</li>
				</ul>
			</li>

			<li class="sub">
				<a href="#" class="sub-toggle">
					<i class="icon icon-inline fa fa-exchange"></i>
					<span class="title">설계변경</span>
				</a>
				<ul class="sub-menu collapse" data-menu-title="설계변경">
					<li>
						<a href="javascript:_page('/ecr/list');">
							<i class="icon icon-inline fa fa-circle-thin"></i>
							<span class="title">ECR 조회</span>
						</a>
					</li>
					<li>
						<a href="javascript:_page('/eco/design');">
							<i class="icon icon-inline fa fa-circle-thin"></i>
							<span class="title">설계 ECO 조회</span>
						</a>
					</li>
					<li>
						<a href="javascript:_page('/eco/prod');">
							<i class="icon icon-inline fa fa-circle-thin"></i>
							<span class="title">생산 ECO 조회</span>
						</a>
					</li>
					<li>
						<a href="javascript:_page('/ecn/list');">
							<i class="icon icon-inline fa fa-circle-thin"></i>
							<span class="title">ECN 조회</span>
						</a>
					</li>
				</ul>
			</li>

			<li class="sub">
				<a href="#" class="sub-toggle">
					<i class="icon icon-inline fa fa-diamond"></i>
					<span class="title">도면 조회 및 배포</span>
				</a>
<!-- 				<ul class="sub-menu collapse" data-menu-title="검색 도면 조회"> -->
<!-- 					<li> -->
<!-- 						<a href="javascript:_page('/epm/list')"> -->
<!-- 							<i class="icon icon-inline fa fa-circle-thin"></i> -->
<!-- 							<span class="title">검색 도면 조회</span> -->
<!-- 						</a> -->
<!-- 					</li> -->
<!-- 				</ul> -->
				<ul class="sub-menu collapse" data-menu-title="배포 관리">
					<li>
						<a href="javascript:_page('/distributor/list')">
							<i class="icon icon-inline fa fa-circle-thin"></i>
							<span class="title">배포처 관리</span>
						</a>
					</li>
				</ul>
				<ul class="sub-menu collapse" data-menu-title="배포 관리">
					<li>
						<a href="javascript:_page('/distributor/userList')">
							<i class="icon icon-inline fa fa-circle-thin"></i>
							<span class="title">배포처 사용자 관리</span>
						</a>
					</li>
				</ul>
				<ul class="sub-menu collapse" data-menu-title="배포 관리">
					<li>
						<a href="javascript:_page('/dist/list')">
							<i class="icon icon-inline fa fa-circle-thin"></i>
							<span class="title">도면 배포(단품)</span>
						</a>
					</li>
				</ul>
				<ul class="sub-menu collapse" data-menu-title="배포 관리">
					<li>
						<a href="javascript:_page('/dist/matList')">
							<i class="icon icon-inline fa fa-circle-thin"></i>
							<span class="title">도면 배포(자재)</span>
						</a>
					</li>
				</ul>
			</li>

			<li class="sub">
				<a href="#" class="sub-toggle">
					<i class="icon icon-inline fa fa-deafness"></i>
					<span class="title">기술문서 관리</span>
				</a>
				<ul class="sub-menu collapse" data-menu-title="기술문서 관리">
					<!-- 					<li> -->
					<!-- 						<a href="javascript:_page('/doc/create');"> -->
					<!-- 							<i class="icon icon-inline fa fa-circle-thin"></i> -->
					<!-- 							<span class="title">기술문서 등록</span> -->
					<!-- 						</a> -->
					<!-- 					</li> -->
					<%
					if (isAdmin) {
					%>
					<li>
						<a href="javascript:_page('/doc/list');">
							<i class="icon icon-inline fa fa-circle-thin"></i>
							<span class="title">기술문서 조회</span>
						</a>
					</li>
					<%
					}
					%>
					<li>
						<a href="javascript:_page('/doc/my');">
							<i class="icon icon-inline fa fa-circle-thin"></i>
							<span class="title">개인문서 조회</span>
						</a>
					</li>
					<li>
						<a href="javascript:_page('/doc/open');">
							<i class="icon icon-inline fa fa-circle-thin"></i>
							<span class="title">문서열람</span>
						</a>
					</li>
				</ul>
			</li>


			<!-- 목재 관리 -->
			<li class="sub">
				<a href="#" class="sub-toggle">
					<i class="icon icon-inline fa fa-glide"></i>
					<span class="title">목재 관리</span>
				</a>
				<ul class="sub-menu collapse" data-menu-title="목재 관리">
					<li>
						<a href="javascript:_page('/tree/combination');">
							<i class="icon icon-inline fa fa-circle-thin"></i>
							<span class="title">재질 정보</span>
						</a>
					</li>
					<li>
						<a href="javascript:_page('/tree/info');">
							<i class="icon icon-inline fa fa-circle-thin"></i>
							<span class="title">목재 소재</span>
						</a>
					</li>
					<li>
						<a href="javascript:_page('/tree/surface');">
							<i class="icon icon-inline fa fa-circle-thin"></i>
							<span class="title">목재 표면제</span>
						</a>
					</li>
					<li>
						<a href="javascript:_page('/tree/edge');">
							<i class="icon icon-inline fa fa-circle-thin"></i>
							<span class="title">엣지</span>
						</a>
					</li>
					<li>
						<a href="javascript:_page('/tree/treatment');">
							<i class="icon icon-inline fa fa-circle-thin"></i>
							<span class="title">표면 처리</span>
						</a>
					</li>
				</ul>
			</li>
			<!-- 목재외 관리 -->
			<li class="sub">
				<a href="#" class="sub-toggle">
					<i class="icon icon-inline fa fa-signing"></i>
					<span class="title">목재外 관리</span>
				</a>
				<ul class="sub-menu collapse" data-menu-title="목재外 관리">
					<li>
						<a href="javascript:_page('/exctree/info');">
							<i class="icon icon-inline fa fa-circle-thin"></i>
							<span class="title">재질 정보</span>
						</a>
					</li>
					<li>
						<a href="javascript:_page('/exctree/emanufacturing');">
							<i class="icon icon-inline fa fa-circle-thin"></i>
							<span class="title">가공 정보</span>
						</a>
					</li>
					<li>
						<a href="javascript:_page('/exctree/esurface');">
							<i class="icon icon-inline fa fa-circle-thin"></i>
							<span class="title">표면 처리</span>
						</a>
					</li>
				</ul>
			</li>

			<!-- BOM소요량 -->
			<li class="sub">
				<a href="#" class="sub-toggle">
					<i class="icon icon-inline fa fa-low-vision"></i>
					<span class="title">BOM소요량</span>
				</a>
				<ul class="sub-menu collapse" data-menu-title="BOM소요량">
					<li>
						<a href="javascript:_page('/quantity/list');">
							<i class="icon icon-inline fa fa-circle-thin"></i>
							<span class="title">표준소요량마스터</span>
						</a>
					</li>
				</ul>
			</li>

			<!--  엣지사양선택 -->
			<li class="sub">
				<a href="#" class="sub-toggle">
					<i class="icon icon-inline fa fa-snapchat"></i>
					<span class="title">엣지사양선택</span>
				</a>
				<ul class="sub-menu collapse" data-menu-title="엣지사양선택">
					<li>
						<a href="javascript:_page('/edgespec/ematerial');">
							<i class="icon icon-inline fa fa-circle-thin"></i>
							<span class="title">소재</span>
						</a>
					</li>
					<li>
						<a href="javascript:_page('/edgespec/eshape');">
							<i class="icon icon-inline fa fa-circle-thin"></i>
							<span class="title">모양/두께</span>
						</a>
					</li>
				</ul>
			</li>

			<!-- CAD속성 -->
			<li class="sub">
				<a href="#" class="sub-toggle">
					<i class="icon icon-inline fa fa-first-order"></i>
					<span class="title">CAD속성</span>
				</a>
				<ul class="sub-menu collapse" data-menu-title="CAD속성">
					<li>
						<a href="javascript:_page('/attr/list');">
							<i class="icon icon-inline fa fa-circle-thin"></i>
							<span class="title">원가 및 생산속성</span>
						</a>
					</li>
				</ul>
			</li>

			<!-- 관리자 -->
			<li class="sub">
				<a href="#" class="sub-toggle">
					<i class="icon icon-inline fa fa-envelope-open"></i>
					<span class="title">관리자</span>
				</a>
				<ul class="sub-menu collapse" data-menu-title="관리자">
					<li>
						<a href="javascript:_page('/baseCode/list');">
							<i class="icon icon-inline fa fa-circle-thin"></i>
							<span class="title">기준코드 관리</span>
						</a>
					</li>
					<li>
						<a href="javascript:_page('/user/list');">
							<i class="icon icon-inline fa fa-circle-thin"></i>
							<span class="title">사용자 관리</span>
						</a>
					</li>
					<li>
						<a href="javascript:_page('/raonk/list');">
							<i class="icon icon-inline fa fa-circle-thin"></i>
							<span class="title">라온케이 템플릿</span>
						</a>
					</li>
					<li>
						<a href="javascript:_page('/color/list');">
							<i class="icon icon-inline fa fa-circle-thin"></i>
							<span class="title">라이브러리 컬러셋</span>
						</a>
					</li>
					<li>
						<a href="javascript:_page('/erp/list');">
							<i class="icon icon-inline fa fa-circle-thin"></i>
							<span class="title">ERP 전송 이력</span>
						</a>
					</li>
				</ul>
			</li>
		</ul>
		<!-- /SIDEBAR NAVIGATION -->

	</div>
	<!-- /Scrollable -->

	<!-- Bottom Bar -->
	<div class="sidebar-fixed-bottom">

		<a href="#" class="btn btn-dark btn-block btn-show-group">
			<i class="icon icon-inline fa fa-angle-right"></i>
		</a>

		<div class="btn-group-wrapper">
			<div class="btn-group btn-group-justified" role="group">
				<a href="#" class="btn btn-dark" role="button">
					<i class="icon icon-inline fa fa-gears"></i>
				</a>
				<a href="xp-lockscreen.html" class="btn btn-dark" role="button">
					<i class="icon icon-inline fa fa-lock"></i>
				</a>
				<a href="javascript:_logout();" class="btn btn-dark" role="button">
					<i class="icon icon-inline fa fa-sign-out"></i>
				</a>
			</div>
		</div>

	</div>
	<!-- /Bottom Bar -->

</div>
<!-- /SIDEBAR LEFT -->
