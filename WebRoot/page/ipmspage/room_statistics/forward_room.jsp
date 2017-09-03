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
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/pagination.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/roomlist.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/pagenation.css" />
  </head>
  <body>
  	<form id="pagerForm" name="pagerForm" action="CurrentRoom.do" target="_self">
	    <div>
	    	<table id="myTable" class="myTable" border="0" width="100">
				<thead id="date">
					<tr>
						<th class="header">房间类型</th>
						<c:forEach items="${listdate}" var="listdate">
							<th class="header">${listdate}</th>
						</c:forEach>
					</tr>
				</thead>	
				<tbody id="info">
					<c:forEach items="${listroomtype}" var="roomtype" >
						<c:forEach items="${newlistdata}" var="data" >
							<c:if test="${data.key == roomtype}">
								<tr>
									<td>${data.key}</td>
									<c:forEach items="${listdate}" var="listdate">
										<c:forEach items="${data.value}" var="datalist">
											<c:if test="${datalist.key == listdate}">
												<td>${datalist.value}</td>
											</c:if>
										</c:forEach>
									</c:forEach>
								</tr>
							</c:if>
						</c:forEach>	
					</c:forEach>
				</tbody>
			
			</table>
		</div>
		<div id="pager"></div>
	</form>
	<%@ include file="../../common/script.jsp"%>
	<script src="<%=request.getContextPath()%>/script/ipms/js/integral/integral.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
	<script>
		Pager.renderPager(<%=pagination%>);
	</script>
  </body>
</html>