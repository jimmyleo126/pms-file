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
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/shopsell/shopsell.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/pagenation.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/pagination.css" />
	
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/roomlist.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css"/>
	<style>
  
/*设置可预约数量按钮*/
 input.setbutton{
	font-size: 12px;
    font-family: "微软雅黑";
    height: 30px !important;
    line-height: 25px !important;
    text-align: center;
    width: 96px !important;
    margin-right: 24px;
    border: none;
    background-color: #red !important;
    color: #fff;
    cursor: pointer;
}
   </style>
  </head>
  <body id="aaa">
  	<div class="one_sale">
  	   <form id="pagerForm" name="pagerForm" action="querycleanapplycondition.do" target="_self">
	    <div class="sale_div handleapply_div">
	    	<table id="myTable" class="myTable" border="0" width="100">
				<thead id="log">
					<tr>
					   <th class="header" style="width:14%;display: none">保洁记录编号</th>
						<th class="header" style="width:10%;">门店名称</th>
						<th class="header">保洁日期</th>
<!--						<th class="header">保洁类型</th>-->
						<th class="header">房间号</th>						
						<th class="header">保洁人</th>						
						<th class="header">时段</th>
						<th class="header">录入人</th>
						<th class="header">备注</th>
						<th class="header">状态</th>
						<th class="header">操作</th>
					</tr>
				</thead>
				<tbody id="info">
					<c:forEach items="${recorddataCondition}" var="recorddataCondition">
						<tr>
							<td id="cleanApplyId" style="display: none">${recorddataCondition["RECORD_ID"]}</td>
							<td>${recorddataCondition["BRANCHNAME"]}</td>
							<td>${recorddataCondition["CLEANTIME"]}</td>
							<td>${recorddataCondition["ROOM_ID"]}</td>
							<td>${recorddataCondition["CLEAN_PERSON"]}</td>
							<td>${recorddataCondition["DTIMEAREA"]}</td>
							<td>${recorddataCondition["RECORD_USER"]}</td>
							<td>${recorddataCondition["REMARK"]}</td>
							<td>${recorddataCondition["DSTATUS"]}</td>
							
							<c:if test="${recorddataCondition.status == '0'}">
								<td><input name="dealrecord" type="button" class="gdsbutton" value="处理" "/></td>
							</c:if>
							<c:if test="${recorddataCondition.status == '1'}">
								<td>已处理</td>
							</c:if>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
<!--		<div id="manysalecontent"></div>-->
<!--		<div id="pager"></div>-->
<!--		<div>-->
<!--			<input type="hidden" id="time" name="time" value="${time}"/>-->
<!--			<input type="hidden" id="timeArea" name="timeArea" value="${timeArea}"/>-->
			
<!--		</div>-->
	</form>
<!--	<input type="hidden" id="branchId" name="branchId" value="${branchId}"/>-->
	</div>
	<%@ include file="../../../common/script.jsp"%>
	
	<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
	<script type="text/javascript">var base_path = "<%=request.getContextPath()%>";</script>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/ipms/js/leftmenu/clean/dealrecord.js"></script>
	<script>
	
		//Pager.renderPager(<%=pagination%>);
	</script>
  </body>
</html>
