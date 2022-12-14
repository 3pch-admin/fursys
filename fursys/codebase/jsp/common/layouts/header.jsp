<%@page import="platform.message.entity.Message"%>
<%@page import="java.util.ArrayList"%>
<%@page import="wt.session.SessionHelper"%>
<%@page import="wt.org.WTUser"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
WTUser sessionUser = (WTUser) SessionHelper.manager.getPrincipal();
ArrayList<Message> messages = (ArrayList<Message>) request.getAttribute("messages");
%>
<!-- NAVIGATION: Top Menu -->
<nav class="navbar navbar-fixed-top top-menu">
	<div class="container-fluid">

		<!-- Navigation header -->
		<div class="navbar-header">

			<!-- Collapse button -->
			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
				<span class="sr-only">Toggle navigation</span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<!-- /Collapse button -->

			<!-- Brand -->
			<a href="/Windchill/platform/main" class="navbar-brand navbar-brand-cover">
				<div class="logo">FURSYS PLM</div>
				<div class="navbar-brand-small">
					<img src="/Windchill/jsp/images/logo-small.png" alt="CasperoBoard">
				</div>
			</a>
			<!-- /Brand -->

		</div>
		<!-- /Navigation header -->

		<!-- Top Menu (Not Collapsed) -->
		<div class="navbar-top">
			<ul class="nav navbar-nav">
				<li>
					<!-- 					<a href="#" class="sidebar-collapse"> -->
					<!-- 						<i class="icon icon-inline fa fa-toggle-left muted"></i> -->
					<!-- 					</a> -->
				</li>
			</ul>
		</div>
		<!-- /Top Menu (Not Collapsed) -->

		<!-- Top menu -->
		<div id="navbar" class="navbar-collapse collapse">

			<!-- Right menu -->
			<ul class="nav navbar-nav navbar-right">

				<!-- Buttons -->
				<li>

					<!-- Search Form -->
					<form class="navbar-search-form">
						<div class="form-group">
							<input type="text" class="form-control" placeholder="Type search here...">
						</div>
					</form>
					<!-- /Search Form -->

					<a href="#" class="navbar-search-toggle hidden-xs">
						<i class="icon icon-inline fa fa-search"></i>
						<span class="hidden-sm hidden-md hidden-lg">Search</span>
					</a>
				</li>
				<li class="dropdown">

					<a href="#" class="dropdown-toggle no-caret nav-notification" data-toggle="dropdown">
						<i class="icon icon-inline fa fa-envelope-open-o"></i>
						<span class="hidden-sm hidden-md hidden-lg">Notifications</span>
						<span class="badge badge-danger badge-notification"><%=messages.size() %></span>
					</a>

					<ul class="dropdown-menu dropdown-menu-right navbar-notifications-dropdown">

						<li class="title">새 메세지</li>
						<%
						for (Message message : messages) {
						%>
						<li>
							<a href="#" class="notification">
								<div class="avatar avatar-lg image">
									<img src="/Windchill/jsp/images/avatar-lori-harrison.jpg" alt="Lori Harrison">
								</div>
								<div class="user-name"><%=message.getUser().getFullName() %></div>
								<p class="text"><%=message.getDescription() %></p>
								<div class="date"><%=message.getCreateTimestamp().toString().substring(0, 16) %></div>
							</a>
						</li>
						<%
						}
						%>
						<li>
							<a href="#" class="btn btn-default btn-block btn-no-border">모든 메세지 보기</a>
						</li>

					</ul>

				</li>

				<li>
					<a href="#" class="sidebar-toggle" data-sidebar=".sidebar-users">
						<i class="icon icon-inline fa fa-comments-o"></i>
						<span class="hidden-sm hidden-md hidden-lg">Users</span>
					</a>
				</li>
				<!-- /Buttons -->

				<!-- Profile -->
				<li class="dropdown">

					<!-- Profile avatar -->
					<a href="#" class="dropdown-toggle" data-toggle="dropdown">
						<div class="profile-avatar circle">
							<img src="/Windchill/jsp/images/avatar-louis-hawkins.jpg" alt="Louis Hawkins">
						</div>
						<span class="user-name"><%=sessionUser.getFullName() %></span>
					</a>
					<!-- /Profile avatar -->

					<!-- Profile dropdown menu -->
					<ul class="dropdown-menu dropdown-menu-right">

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
							<a href="#">
								<i class="icon icon-inline fa fa-sign-out"></i>
								Sign out
							</a>
						</li>
					</ul>
					<!-- /Profile dropdown menu -->

				</li>
				<!-- /Profile -->

			</ul>
			<!-- /Right menu -->

		</div>
		<!-- /Top menu -->

	</div>
</nav>
<!-- /NAVIGATION: Top Menu -->

<!-- FIXED COLLAPSED SIDEBAR: Users -->
<div class="sidebar-fixed sidebar-users">

	<!-- PANEL: Users -->
	<div class="panel panel-users">

		<!-- Panel heading -->
		<div class="panel-heading">

			<!-- Panel Title -->
			<div class="panel-title">Users List</div>
			<!-- /Panel Title -->

			<!-- Panel Controls -->
			<div class="panel-controls">
				<ul class="panel-buttons">
					<li>
						<a href="#" class="btn-panel-control icon fa fa-search"></a>
					</li>
					<li>
						<a href="#" class="btn-panel-control icon fa fa-plus"></a>
					</li>
					<li class="dropdown">
						<a href="#" class="btn-panel-control icon fa fa-fw fa-circle-thin dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true"></a>
						<ul class="dropdown-menu dropdown-menu-right">
							<li>
								<a href="#">
									<i class="icon fa fa-user-plus"></i>
									Create User
								</a>
							</li>
							<li>
								<a href="#">
									<i class="icon fa fa-user"></i>
									HR-Panel
								</a>
							</li>
						</ul>
					</li>
				</ul>
			</div>
			<!-- /Panel Controls -->

		</div>
		<!-- /Panel Heading -->

		<!-- Panel Body -->
		<div class="panel-body users">
			<div class="custom-scroll" style="height: 100%">

				<!-- TABLE: Users -->
				<table class="table table-responsive users-table">

					<!-- ROW: User -->
					<tr class="user">
						<td class="user-avatar">
							<!-- User Avatar -->
							<div class="avatar image">
								<img src="/Windchill/jsp/images/avatar-benjamin-jacobs.jpg" alt="Benjamin Jacobs">
							</div>
							<!-- /User Avatar -->
						</td>
						<td>
							<!-- User Info -->
							<a href="#" class="name">Benjamin Jacobs</a>
							<div class="post">Director</div>
							<!-- /User Info -->
						</td>
						<td>
							<!-- User Buttons -->
							<ul class="inline-icons">
								<li>
									<a href="#" class="icon-theme icon-theme-xs icon-brand-facebook fa fa-facebook"></a>
								</li>
								<li>
									<a href="#" class="icon-theme icon-theme-xs icon-brand-twitter fa fa-twitter"></a>
								</li>
								<li>
									<a href="#" class="icon-theme icon-theme-xs icon-brand-googleplus fa fa-google-plus"></a>
								</li>
							</ul>
							<!-- /User Buttons -->
						</td>
					</tr>
					<!-- /ROW: User -->

					<!-- ROW: User -->
					<tr class="user">
						<td class="user-avatar">
							<!-- User Avatar -->
							<div class="avatar image">
								<img src="/Windchill/jsp/images/avatar-deborah-young.jpg" alt="Deborah Young">
							</div>
							<!-- /User Avatar -->
						</td>
						<td>
							<!-- User Info -->
							<a href="#" class="name">Deborah Young</a>
							<div class="post">Animation Designer</div>
							<!-- /User Info -->
						</td>
						<td>
							<!-- User Buttons -->
							<ul class="inline-icons">
								<li>
									<a href="#" class="icon-theme icon-theme-xs icon-brand-facebook fa fa-facebook"></a>
								</li>
								<li>
									<a href="#" class="icon-theme icon-theme-xs icon-brand-twitter fa fa-twitter"></a>
								</li>
								<li>
									<a href="#" class="icon-theme icon-theme-xs icon-brand-googleplus fa fa-google-plus"></a>
								</li>
							</ul>
							<!-- /User Buttons -->
						</td>
					</tr>
					<!-- /ROW: User -->

					<!-- ROW: User -->
					<tr class="user">
						<td class="user-avatar">
							<!-- User Avatar -->
							<div class="avatar image">
								<img src="/Windchill/jsp/images/avatar-louis-hawkins.jpg" alt="Louis Hawkins">
							</div>
							<!-- /User Avatar -->
						</td>
						<td>
							<!-- User Info -->
							<a href="#" class="name">Louis Hawkins</a>
							<div class="post">Marketing Director</div>
							<!-- /User Info -->
						</td>
						<td>
							<!-- User Buttons -->
							<ul class="inline-icons">
								<li>
									<a href="#" class="icon-theme icon-theme-xs icon-brand-facebook fa fa-facebook"></a>
								</li>
								<li>
									<a href="#" class="icon-theme icon-theme-xs icon-brand-twitter fa fa-twitter"></a>
								</li>
								<li>
									<a href="#" class="icon-theme icon-theme-xs icon-brand-googleplus fa fa-google-plus"></a>
								</li>
							</ul>
							<!-- /User Buttons -->
						</td>
					</tr>
					<!-- /ROW: User -->

					<!-- ROW: User -->
					<tr class="user">
						<td class="user-avatar">
							<!-- User Avatar -->
							<div class="avatar image">
								<img src="/Windchill/jsp/images/avatar-ashley-warren.jpg" alt="Ashley Warren">
							</div>
							<!-- /User Avatar -->
						</td>
						<td>
							<!-- User Info -->
							<a href="#" class="name">Ashley Warren</a>
							<div class="post">Account Manager</div>
							<!-- /User Info -->
						</td>
						<td>
							<!-- User Buttons -->
							<ul class="inline-icons">
								<li>
									<a href="#" class="icon-theme icon-theme-xs icon-brand-facebook fa fa-facebook"></a>
								</li>
								<li>
									<a href="#" class="icon-theme icon-theme-xs icon-brand-twitter fa fa-twitter"></a>
								</li>
								<li>
									<a href="#" class="icon-theme icon-theme-xs icon-brand-googleplus fa fa-google-plus"></a>
								</li>
							</ul>
							<!-- /User Buttons -->
						</td>
					</tr>
					<!-- /ROW: User -->

					<!-- ROW: User -->
					<tr class="user">
						<td class="user-avatar">
							<!-- User Avatar -->
							<div class="avatar image">
								<img src="/Windchill/jsp/images/avatar-christopher-tucker.jpg" alt="Christopher Tucker">
							</div>
							<!-- /User Avatar -->
						</td>
						<td>
							<!-- User Info -->
							<a href="#" class="name">Christopher Tucker</a>
							<div class="post">Director</div>
							<!-- /User Info -->
						</td>
						<td>
							<!-- User Buttons -->
							<ul class="inline-icons">
								<li>
									<a href="#" class="icon-theme icon-theme-xs icon-brand-facebook fa fa-facebook"></a>
								</li>
								<li>
									<a href="#" class="icon-theme icon-theme-xs icon-brand-twitter fa fa-twitter"></a>
								</li>
								<li>
									<a href="#" class="icon-theme icon-theme-xs icon-brand-googleplus fa fa-google-plus"></a>
								</li>
							</ul>
							<!-- /User Buttons -->
						</td>
					</tr>
					<!-- /ROW: User -->

					<!-- ROW: User -->
					<tr class="user">
						<td class="user-avatar">
							<!-- User Avatar -->
							<div class="avatar image">
								<img src="/Windchill/jsp/images/avatar-lori-harrison.jpg" alt="Lori Harrison">
							</div>
							<!-- /User Avatar -->
						</td>
						<td>
							<!-- User Info -->
							<a href="#" class="name">Lori Harrison</a>
							<div class="post">Animation Designer</div>
							<!-- /User Info -->
						</td>
						<td>
							<!-- User Buttons -->
							<ul class="inline-icons">
								<li>
									<a href="#" class="icon-theme icon-theme-xs icon-brand-facebook fa fa-facebook"></a>
								</li>
								<li>
									<a href="#" class="icon-theme icon-theme-xs icon-brand-twitter fa fa-twitter"></a>
								</li>
								<li>
									<a href="#" class="icon-theme icon-theme-xs icon-brand-googleplus fa fa-google-plus"></a>
								</li>
							</ul>
							<!-- /User Buttons -->
						</td>
					</tr>
					<!-- /ROW: User -->

					<!-- ROW: User -->
					<tr class="user">
						<td class="user-avatar">
							<!-- User Avatar -->
							<div class="avatar image">
								<img src="/Windchill/jsp/images/avatar-phillip-cole.jpg" alt="Phillip Cole">
							</div>
							<!-- /User Avatar -->
						</td>
						<td>
							<!-- User Info -->
							<a href="#" class="name">Phillip Cole</a>
							<div class="post">Marketing Director</div>
							<!-- /User Info -->
						</td>
						<td>
							<!-- User Buttons -->
							<ul class="inline-icons">
								<li>
									<a href="#" class="icon-theme icon-theme-xs icon-brand-facebook fa fa-facebook"></a>
								</li>
								<li>
									<a href="#" class="icon-theme icon-theme-xs icon-brand-twitter fa fa-twitter"></a>
								</li>
								<li>
									<a href="#" class="icon-theme icon-theme-xs icon-brand-googleplus fa fa-google-plus"></a>
								</li>
							</ul>
							<!-- /User Buttons -->
						</td>
					</tr>
					<!-- /ROW: User -->

					<!-- ROW: User -->
					<tr class="user">
						<td class="user-avatar">
							<!-- User Avatar -->
							<div class="avatar image">
								<img src="/Windchill/jsp/images/avatar-ann-james.jpg" alt="Ann James">
							</div>
							<!-- /User Avatar -->
						</td>
						<td>
							<!-- User Info -->
							<a href="#" class="name">Ann James</a>
							<div class="post">Account Manager</div>
							<!-- /User Info -->
						</td>
						<td>
							<!-- User Buttons -->
							<ul class="inline-icons">
								<li>
									<a href="#" class="icon-theme icon-theme-xs icon-brand-facebook fa fa-facebook"></a>
								</li>
								<li>
									<a href="#" class="icon-theme icon-theme-xs icon-brand-twitter fa fa-twitter"></a>
								</li>
								<li>
									<a href="#" class="icon-theme icon-theme-xs icon-brand-googleplus fa fa-google-plus"></a>
								</li>
							</ul>
							<!-- /User Buttons -->
						</td>
					</tr>
					<!-- /ROW: User -->

					<!-- ROW: User -->
					<tr class="user">
						<td class="user-avatar">
							<!-- User Avatar -->
							<div class="avatar image">
								<img src="/Windchill/jsp/images/avatar-tyler-brewer.jpg" alt="Tyler Brewer">
							</div>
							<!-- /User Avatar -->
						</td>
						<td>
							<!-- User Info -->
							<a href="#" class="name">Tyler Brewer</a>
							<div class="post">Account Manager</div>
							<!-- /User Info -->
						</td>
						<td>
							<!-- User Buttons -->
							<ul class="inline-icons">
								<li>
									<a href="#" class="icon-theme icon-theme-xs icon-brand-facebook fa fa-facebook"></a>
								</li>
								<li>
									<a href="#" class="icon-theme icon-theme-xs icon-brand-twitter fa fa-twitter"></a>
								</li>
								<li>
									<a href="#" class="icon-theme icon-theme-xs icon-brand-googleplus fa fa-google-plus"></a>
								</li>
							</ul>
							<!-- /User Buttons -->
						</td>
					</tr>
					<!-- /ROW: User -->

					<!-- ROW: User -->
					<tr class="user">
						<td class="user-avatar">
							<!-- User Avatar -->
							<div class="avatar image">
								<img src="/Windchill/jsp/images/avatar-eric-martinez.jpg" alt="Eric Martinez">
							</div>
							<!-- /User Avatar -->
						</td>
						<td>
							<!-- User Info -->
							<a href="#" class="name">Eric Martinez</a>
							<div class="post">Account Manager</div>
							<!-- /User Info -->
						</td>
						<td>
							<!-- User Buttons -->
							<ul class="inline-icons">
								<li>
									<a href="#" class="icon-theme icon-theme-xs icon-brand-facebook fa fa-facebook"></a>
								</li>
								<li>
									<a href="#" class="icon-theme icon-theme-xs icon-brand-twitter fa fa-twitter"></a>
								</li>
								<li>
									<a href="#" class="icon-theme icon-theme-xs icon-brand-googleplus fa fa-google-plus"></a>
								</li>
							</ul>
							<!-- /User Buttons -->
						</td>
					</tr>
					<!-- /ROW: User -->

					<!-- ROW: User -->
					<tr class="user">
						<td class="user-avatar">
							<!-- User Avatar -->
							<div class="avatar image">
								<img src="/Windchill/jsp/images/avatar-karen-garcia.jpg" alt="Karen Garcia">
							</div>
							<!-- /User Avatar -->
						</td>
						<td>
							<!-- User Info -->
							<a href="#" class="name">Karen Garcia</a>
							<div class="post">Animation Designer</div>
							<!-- /User Info -->
						</td>
						<td>
							<!-- User Buttons -->
							<ul class="inline-icons">
								<li>
									<a href="#" class="icon-theme icon-theme-xs icon-brand-facebook fa fa-facebook"></a>
								</li>
								<li>
									<a href="#" class="icon-theme icon-theme-xs icon-brand-twitter fa fa-twitter"></a>
								</li>
								<li>
									<a href="#" class="icon-theme icon-theme-xs icon-brand-googleplus fa fa-google-plus"></a>
								</li>
							</ul>
							<!-- /User Buttons -->
						</td>
					</tr>
					<!-- /ROW: User -->

					<!-- ROW: User -->
					<tr class="user">
						<td class="user-avatar">
							<!-- User Avatar -->
							<div class="avatar image">
								<img src="/Windchill/jsp/images/avatar-susan-estrada.jpg" alt="Susan Estrada">
							</div>
							<!-- /User Avatar -->
						</td>
						<td>
							<!-- User Info -->
							<a href="#" class="name">Susan Estrada</a>
							<div class="post">Animation Designer</div>
							<!-- /User Info -->
						</td>
						<td>
							<!-- User Buttons -->
							<ul class="inline-icons">
								<li>
									<a href="#" class="icon-theme icon-theme-xs icon-brand-facebook fa fa-facebook"></a>
								</li>
								<li>
									<a href="#" class="icon-theme icon-theme-xs icon-brand-twitter fa fa-twitter"></a>
								</li>
								<li>
									<a href="#" class="icon-theme icon-theme-xs icon-brand-googleplus fa fa-google-plus"></a>
								</li>
							</ul>
							<!-- /User Buttons -->
						</td>
					</tr>
					<!-- /ROW: User -->

					<!-- ROW: User -->
					<tr class="user">
						<td class="user-avatar">
							<!-- User Avatar -->
							<div class="avatar image">
								<img src="/Windchill/jsp/images/avatar-joshua-ward.jpg" alt="Joshua Ward">
							</div>
							<!-- /User Avatar -->
						</td>
						<td>
							<!-- User Info -->
							<a href="#" class="name">Joshua Ward</a>
							<div class="post">Animation Designer</div>
							<!-- /User Info -->
						</td>
						<td>
							<!-- User Buttons -->
							<ul class="inline-icons">
								<li>
									<a href="#" class="icon-theme icon-theme-xs icon-brand-facebook fa fa-facebook"></a>
								</li>
								<li>
									<a href="#" class="icon-theme icon-theme-xs icon-brand-twitter fa fa-twitter"></a>
								</li>
								<li>
									<a href="#" class="icon-theme icon-theme-xs icon-brand-googleplus fa fa-google-plus"></a>
								</li>
							</ul>
							<!-- /User Buttons -->
						</td>
					</tr>
					<!-- /ROW: User -->

					<!-- ROW: User -->
					<tr class="user">
						<td class="user-avatar">
							<!-- User Avatar -->
							<div class="avatar image">
								<img src="/Windchill/jsp/images/avatar-louis-mendoza.jpg" alt="Louis Mendoza">
							</div>
							<!-- /User Avatar -->
						</td>
						<td>
							<!-- User Info -->
							<a href="#" class="name">Louis Mendoza</a>
							<div class="post">Account Manager</div>
							<!-- /User Info -->
						</td>
						<td>
							<!-- User Buttons -->
							<ul class="inline-icons">
								<li>
									<a href="#" class="icon-theme icon-theme-xs icon-brand-facebook fa fa-facebook"></a>
								</li>
								<li>
									<a href="#" class="icon-theme icon-theme-xs icon-brand-twitter fa fa-twitter"></a>
								</li>
								<li>
									<a href="#" class="icon-theme icon-theme-xs icon-brand-googleplus fa fa-google-plus"></a>
								</li>
							</ul>
							<!-- /User Buttons -->
						</td>
					</tr>
					<!-- /ROW: User -->

				</table>
				<!-- /TABLE: Users -->

			</div>
		</div>
		<!-- /Panel Body -->

	</div>
	<!-- PANEL: /Users -->

</div>
<!-- /FIXED COLLAPSED SIDEBAR: Users -->
