<%@page import="platform.util.ThumbnailUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String oid = (String) request.getParameter("oid");
String eoid = (String) request.getParameter("eoid");
String[] thumb = ThumbnailUtils.thumbnails(oid);
%>
<img src="<%=thumb[0]%>" style="vertical-align: middle; cursor: pointer;" width="350px" id="thumb" data-oid="<%=eoid%>">