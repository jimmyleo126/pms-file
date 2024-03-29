<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/script.jsp"%>
<%@ include file="../../common/css.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>兑换规则</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet"media="all" />
	<link href="<%=request.getContextPath()%>/css/reset.css" rel="stylesheet"  media="all" />
	<link href="<%=request.getContextPath()%>/css/ipms/exchangeScore.css" rel="stylesheet"media="all" />
  </head>
  
  <body>
    <div class="exchange_box">
    		<table id="myTable" border="0">
				<thead>
					<tr>
						<th class="header">积分</th>
						<th class="header">金额</th>
					</tr>
				</thead>
				<tbody>
			    	<tr>
			    		<td>
			    			<input id="score" class="score"  type="text" value="${score }" />
			    		</td>
			    		<td>
			    			<input id="money" class="money" type="text" value="${money }"/>
			    		</td>
			    	</tr>
		    	</tbody>
	    </table>
    </div>
    <div class="bottom_div">
    	<div class="button_div">
	    	<input class="button submit" type="button" value="确定" onclick="updateReserve()"/>  
	    </div>
		<div class="button_div">
			<input class="button cancel" type="button" value="清空" onclick="del()"/>
		</div>
    </div>
  </body>
  <script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
  <script src="<%=request.getContextPath()%>/script/crm/integral/exchangeScore.js"></script>
</html>
