<%@page import="java.io.BufferedReader"%>
<%@page import="java.util.Enumeration"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="platform.attr.service.AttrHelper"%>
<%@page import="platform.attr.entity.AttrDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String cadkey = (String) request.getParameter("cadkey");
System.out.println("cadKey=" + cadkey);

Enumeration en = request.getParameterNames();
while (en.hasMoreElements()) {
	String key = (String) en.nextElement();
	System.out.println("key=" + key);
}
Map<String, Object> params = new HashMap<>();
params.put("cadKey", cadkey);
List<AttrDTO> list = AttrHelper.manager.list(params);
JSONArray json = JSONArray.fromObject(list);
%>
<%=json%>
