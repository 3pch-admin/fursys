<%@page import="net.sf.json.JSONObject"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="platform.tree.service.TreeHelper"%>
<%@page import="platform.tree.entity.SurfaceDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
Map<String, Object> params = new HashMap<>();
List<SurfaceDTO> list = TreeHelper.manager.surface(params);
JSONArray json = JSONArray.fromObject(list);
%>
<%=json%>
