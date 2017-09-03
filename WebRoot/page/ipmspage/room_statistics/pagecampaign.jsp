<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@ page import="com.ideassoft.core.page.Pagination"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	JSONObject pagination = JSONObject.fromObject(request.getAttribute("pagination"));	
%>
<!DOCTYPE HTML>
<html>
  <head>
    <link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlistfont.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/roomlist.css" />
  </head>
  <body>
  	<form id="pagerForm" name="pagerForm" action="CurrentRoom.do" target="_self">
	    <div>
	    	<table id="myTable" class="myTable" border="0" width="100">
				<thead id="date">
					<tr>
						<c:forEach items="${list}" var="name">
							<th class="header">${name["CAMNAME"]}</th>
						</c:forEach>
					</tr>
				</thead>
				<tbody id="info">
					<tr>
						<c:forEach items="${list}" var="count">
							<td>${count["COUNTNUM"]}/${count["CAMP"]}</td>
						</c:forEach>
					</tr>
				</tbody>
			</table>
		</div>
		<div id="pager"></div>
	</form>
	<%@ include file="../../common/script.jsp"%>
	<script src="<%=request.getContextPath()%>/script/ipms/js/integral/integral.js"></script>
  </body>
</html>