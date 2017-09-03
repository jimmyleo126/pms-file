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
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/leftmenu/left_order.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/pagenation.css" />
   <script>
      var base_path = "<%=request.getContextPath()%>";
	</script>
  </head>
  <body>
  <div class="condition_div">
     <form id="pagerForm" name="pagerForm" action="" target="_self">
	    <div class="div_part_one">
	    	<table id="myTable" class="myTable" border="0" width="100">
				<thead id="log">
					<tr>
						<th class="header">订单号</th>
						<th class="header">房型</th>
						<th class="header">房价</th>
						<th class="header">预定时间</th>
						<th class="header">担保类型</th>
						<th class="header">保留时间</th>
						<th class="header">预订人</th>
						<th class="header">入住日期</th>
						<th class="header">退房日期</th>
						<th class="header">入住人</th>
						<th class="header">预定渠道</th>
						<!--<th class="header">客房备注</th>
						<th class="header">接待备注</th>
						--><th class="header">备注</th>
					</tr>
				</thead>
				<tbody id="info">
					<c:forEach items="${orderCondition}" var="orderCondition">
						<tr>
							<td>${orderCondition["ORDERID"]}</td>
							<td>${orderCondition["ROOMTYPE"]}</td>
							<td>${orderCondition["ROOMPRICE"]}</td>
							<td>${orderCondition["ORDERTIME"]}</td>
							<td>${orderCondition["GUARANTEE"]}</td>
							<td>${orderCondition["LIMITED"] }</td>
							<td>${orderCondition["ORDERUSER"] }</td>
							<td>${orderCondition["ARRIVALTIME"] }</td>
							<td>${orderCondition["LEAVETIME"] }</td>
							<td>${orderCondition["USERCHECKIN"] }</td>
							<td>${orderCondition["SOURCE"] }</td>
							<!--<td>${orderCondition["ROOMREMARK"] }</td>
							<td>${orderCondition["RECEPTIONREMARK"] }</td>
							--><td>${orderCondition["REMARK"] }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div id="pager"></div>
		<div>
			<input type="hidden" id="source" name="source" value=""/>
			<input type="hidden" id="status" name="status" value=""/>
		</div>
	</form>
   </div>
	<%@ include file="../../../common/script.jsp"%>
	<script src="<%=request.getContextPath()%>/script/ipms/js/integral/integral.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
	<script>
		Pager.renderPager(<%=pagination%>);
	</script>
  </body>
</html>
