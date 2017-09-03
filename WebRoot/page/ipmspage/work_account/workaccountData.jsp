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
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/pagenation.css" />
  </head>
  <body>
  	<form>
	    <div style="height:824px;">
	    	<table id="myTable" class="myTable" border="0" width="100">
				<thead id="log">
					<tr>
						<th class="header">账单号</th>
						<th class="header">名称</th>
						<th class="header">状态</th>
						<th class="header">创建时间</th>
						<th class="header">结账者</th>
						<th class="header">结账时间</th>
						<th class="header">消费</th>
						<th class="header">结算</th>
						<th class="header">说明</th>
					</tr>
				</thead>
				<tbody id="info">
					<c:forEach items="${list}" var="list">
						<tr ondblclick='bb(this)'>
							<td>${list["WORKBILLID"] }</td>
							<td>${list["NAME"] }</td>
							<td>${list["DECODESTATUS"] }</td>
							<td>${list["RECORDTIME"] }</td>
							<td>${list["FINALUSERNAME"] }</td>
							<td>${list["FINALTIME"] }</td>
							<td>${list["COST"] }</td>
							<td>${list["PAY"] }</td>
							<td>${list["DESCRIBE"] }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div id="pager"></div>
		<input type="hidden" id="workbillid" name="workbillid" value="${multiqueryworkbill.workbillid}">
		<input type="hidden" id="name" name="name" value="${multiqueryworkbill.name}">
		<input type="hidden" id="status" name="status" value="${multiqueryworkbill.status}">
		<input type="hidden" id="currentdate" name="currentdate" value="${multiqueryworkbill.currentdate}">
		<input type="hidden" id="recorduser" name="recorduser" value="${multiqueryworkbill.recorduser}">
		<input type="hidden" id="finaluser" name="finaluser" value="${multiqueryworkbill.finaluser}">
		<input type="hidden" id="recorddatebegin" name="recorddatebegin" value="${multiqueryworkbill.recorddatebegin}">
		<input type="hidden" id="recorddateend" name="recorddateend" value="${multiqueryworkbill.recorddateend}">
	</form>
	<%@ include file="../../common/script.jsp"%>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/workbillroom/util.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
	<script>
		var path = "<%=request.getContextPath()%>";
		Pager.renderPager(<%=pagination%>);
		function bb(e) {
			var workbillid = $(e.children[0]).html();
			window.parent.parent.JDialog.openWithNoTitle("", path + "/page/ipmspage/work_account/work_account_check.jsp?workbillid=" + workbillid,1180,738);
			//window.location.href="<%=request.getContextPath()%>/page/ipmspage/work_account/work_account_check.jsp";
		}
		
	</script>
  </body>
</html>
