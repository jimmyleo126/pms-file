<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../common/script.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>订单查看页面</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" id="style" type="text/css"
		href="<%=request.getContextPath()%>/css/ipms/css/order/order_check.css" />
	<link rel="stylesheet" id="style" type="text/css"
		href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css" />	
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css"/>
	<link href="//cdn.bootcss.com/font-awesome/4.6.3/css/font-awesome.min.css"  rel="stylesheet">
	<script>var base_path = "<%=request.getContextPath()%>";</script>
  </head>
  
  <body>
  <div class="check_wrap_margin check_color">
  <input type="text" id="roomid" value="<%=request.getParameter("roomId")%>" hidden="true" />
  <input type="text" id="customerid" value="<%=request.getParameter("customerid")%>" hidden="true" />
			<div class="top_check">
				<div class="left_check fl">
					<span class="top_icon"><i class="imooc im_top" onclick="window.parent.JDialog.close();">&#xea0f;</i></span>
					<span class="cn">订单号<span class="on"><%=request.getParameter("orderId")%></span></span><span class="mystatus"></span>
				</div>
				<div class="right_check fr">
					<ul>
						<li class="tab_one tab_select order_first" onclick="changeorder()"><i class="fa fa-file-text"></i><span>订单</span></li>
						<li class="tab_two" onclick="changeorderbill()"><i class="fa fa-th-list"></i><span>账单</span></li>
						<!-- <li class="tab_three" onclick="changeplan()"><i class="fa fa-folder-open-o"></i><span>房租计划</span></li> -->
						<li class="tab_four" onclick="changepro()"><i class="fa fa-bell"></i><span class="hot" style="display:none;"></span><span>提示</span></li>
						<li class="tab_five" onclick="changelog()"><i class="fa fa-columns"></i><span>日志</span></li>
						<li class="tab_six" onclick="changefinal()"><i class="fa fa-tasks"></i><span>财务计划</span></li>
					</ul>
				</div>
			</div>
			<div class="content_color" id="ifa">

			</div>
		</div>
		<script src="<%=request.getContextPath()%>/script/ipms/js/order_check/order_check.js" type="text/javascript" charset="utf-8"></script>
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script type="text/javascript">
			var path = "<%=request.getContextPath() %>";
			var orderId = '<%=request.getParameter("orderId")%>';
		</script>
  </body>
</html>
