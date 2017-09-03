<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../../common/script.jsp"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@ page import="com.ideassoft.core.page.Pagination"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
	JSONObject pagination = JSONObject.fromObject(request.getAttribute("pagination"));	
%>
<!DOCTYPE HTML>
<html>
  <head>
    <link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlistfont.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/pagination.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/pagenation.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/roomlist.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/leftmenu/left_order.css"/>
  </head>
  <body>
   <div class="condition_div">
  	   <form id="pagerForm" name="pagerForm" action="" target="_self">
	    <div class="div_part_one">
	    	<table id="myTable" class="myTable" border="0" width="100">
				<thead id="log">
					<tr>
						<th class="header" style="display:none">申请编号</th>
						<th class="header" style="display:none">合同编号</th>
						<th class="header" style="display:none">日志编号</th>
						<th class="header">房号</th>
						<th class="header">申请人</th>
						<th class="header">手机号码</th>
						<th class="header">申请日期</th>
						<th class="header">申请状态</th>
						<th class="header">维修状态</th>
						<th class="header">备注</th>
						<th class="header">操作</th>
					</tr>
				</thead>
				<tbody id="info">
					
					<c:forEach items="${result}" var="re">
						<tr ondblclick="showDetail(this)">
							<td style="display:none">${re["REPAIRAPPLYID"]}</td>
							<td style="display:none">${re["CONTRACTID"]}</td>
							<td style="display:none">${re["LOGID"]}</td>
							<td>${re["ROOMID"]}</td>
							<td>${re["RESERVEDPERSON"]}</td>
							<td>${re["MOBILE"]}</td>
							<td><fmt:formatDate type="time" value="${re['APPLICATIONDATE']}" pattern="yyyy/MM/dd" />
							</td>
							<td>${re["STATUS"]}</td>
							<td>${re["LOGSTATUS"]}</td>
							<td>${re["REMARK"]}</td>
							<td>
								<input id="cancel" type="button" class="" value="撤销" onclick="cancelApplication(this)"/>
								<input id="Done" type="button" class="" value="修復" onclick="doneApplication(this)"/>
							</td>					
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div id="pager"></div>
	</form>
  </div>
	
	<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
	<script src="<%=request.getContextPath()%>/script/ipms/js/leftmenu/goodssale.js"></script>
	<script src="<%=request.getContextPath()%>/script/ipms/js/leftmenu/repair.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script>
		Pager.renderPager(<%=pagination%>);
		var base_path = "<%=request.getContextPath()%>";
	    var path = "<%=request.getContextPath()%>";
	</script>
  </body>
</html>
