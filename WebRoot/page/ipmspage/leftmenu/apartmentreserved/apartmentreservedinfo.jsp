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
    <link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlistfont.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/pagenation.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/pagination.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/roomlist.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/leftmenu/left_order.css"/>
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
  </head>
  <body>
   <div class="condition_div">
  	   <form id="pagerForm" name="pagerForm" action="" target="_self">
	    <div class="div_part_one">
	    	<table id="myTable" class="myTable" border="0" width="100">
				<thead id="log">
					<tr>
						<th class="header">门店名称</th>
						<th class="header">房间类型</th>
						<th class="header">预约人</th>
						<th class="header">手机</th>
						<th class="header">看房时间</th>
						<th class="header">预约来源</th>
						<th class="header">审核</th>
						<th class="header">操作人</th>
						<th class="header">备注</th>
					</tr>
				</thead>
				<tbody id="info">
					<c:forEach items="${list}" var="list">
						<tr>
							<td>${list["BRANCH_NAME"]}</td>
							<td>${list["ROOM_NAME"]}</td>
							<td>${list["MEMBER_NAME"]}</td>
							<td>${list["MOBILE"]}</td>
							<td>${list["RESERVED_DATE"] }</td>
							<td>${list["PARAM_NAME"] }</td>
							<c:if test="${list.status == '1'}">
								<td>
									<input type="button" id="${list['RESERVED_ID']}" value="同意" onclick="change(this.id,'2')"/>
									<input type="button" id="${list['RESERVED_ID']}" value="取消" onclick="change(this.id,'0')"/>
								</td>
							</c:if>
							<td>${list["STAFF_NAME"] }</td>
							<c:if test="${list.status != '1'}">
								<td>${list["STATUS_NAME"] }</td>
							</c:if>
							<td>${list["REMARK"] }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div id="pager"></div>
		<div>
			<input type="hidden" id="memberName" name="memberName" value="${memberName }"/>
			<input type="hidden" id="mobile" name="mobile" value="${mobile }"/>
			<input type="hidden" id="reservedTime" name="reservedTime" value="${reservedTime }"/>
		</div>
	</form>
  </div>
	<%@ include file="../../../common/script.jsp"%>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
	<script>
		Pager.renderPager(<%=pagination%>);
	</script>
	<script type="text/javascript">
		function showMsg(msg, fn) {
			if (!fn) {
				TipInfo.Msg.alert(msg);
			} else {
				TipInfo.Msg.confirm(msg, fn);
			}
		}
		function change(reservedId,status){
			$.ajax({
				url: "checkApartmentReserved.do",
				dataType: "json",
				type: "post",
				data:{
					'reservedId' : reservedId,
					'status' : status
				},
				success: function(json) {
				        showMsg("同意预约!");
				        window.setTimeout("window.parent.location.reload(true)",800);
					   
				},
				error:function(json){
						showMsg("预约失败!");
					    window.setTimeout("window.parent.location.reload(true)",1000);
				}
			});
		};
	</script>
  </body>
</html>
