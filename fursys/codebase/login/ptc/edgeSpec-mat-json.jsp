<%@page import="net.sf.json.JSONObject"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="platform.tree.service.EdgeSpecHelper"%>
<%@page import="platform.tree.entity.EMaterialDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
Map<String, Object> params = new HashMap<>();
List<EMaterialDTO> list = EdgeSpecHelper.manager.ematerial(params);
JSONArray json = JSONArray.fromObject(list);
%>
<%=json%>
