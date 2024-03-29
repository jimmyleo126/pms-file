<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../common/css.jsp"%>
<%@ include file="../../common/script.jsp"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@ page import="com.ideassoft.core.page.Pagination"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
JSONObject pagination = JSONObject.fromObject(request.getAttribute("pagination"));
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>订单</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" id="style" type="text/css"
		href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css"
		href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
	<link rel="stylesheet" id="style" type="text/css"
			href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css" />	
	<link rel="stylesheet" id="style" type="text/css"
		href="<%=request.getContextPath()%>/css/ipms/css/orderfont.css" />
	<link rel="stylesheet" id="style" type="text/css"
		href="<%=request.getContextPath()%>/css/ipms/css/order/order.css" />
	<link rel="stylesheet" type="text/css" 
		href="<%=request.getContextPath()%>/css/ipms/pagination.css" />	
	<link rel="stylesheet" type="text/css" 
		href="<%=request.getContextPath()%>/css/ipms/css/pagenation.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css" />		
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
	<link rel="stylesheet" id="style" type="text/css" 
		href="<%=request.getContextPath()%>/css/common/datetimepicker.css" media="all" />

  </head>
  
  <body>
  	<div class="order_margin">
  	    <div class="header_order">
			<ul>
				<li class="header_order_li active" index="0"><p>今日预抵</p><p>(所有)</p></li>
				<li class="header_order_li" index="1"><p>今日预抵</p><p>(担保)</p></li>
				<li class="header_order_li" index="2"><p>今日预抵</p><p>(未过保留期)</p></li>
				<li class="header_order_li" index="3"><p>今日预抵</p><p>(已过保留期)</p></li>
				<li class="header_order_li" index="4"><p>今日预抵</p><p>	异常订单</p></li>
			</ul>
			<div class="fr">
				<span class="highsearch">高级查询</span>
			</div>
			<div class="high_header fadeInDown">
				<h3>高级查询</h3>
				<i class="imooc imooc_order" style="color:#fff;">&#xe900;</i>
				<form action="" method="post" id="myForm" name="myForm">
						<div class="margintop">
							<ul class="clearfix">
						      <li><label class="label_name" >订单号</label><input name="orderId" type="text" id="orderId" class="text_search"></li>
						      <li><label class="label_name">房型</label>
						      	<select name="roomType" class="text_search" id="roomType">
						      		<!-- <option value="0">0</option>
						      		<option value="1">1</option> -->
						      	</select>
						      	</li>
						      <li><label class="label_name">来源</label>	
						      	<select name="source" id="source" class="text_search">
									<option value="">--请选择来源--</option>
									<option value="1">app</option>
									<option value="2">网站</option>
									<option value="3">分店</option>
									<option value="4">wap</option>
									<option value="5">合作渠道</option>
									<option value="6">其他</option>
									<option value="7">微信</option>
								</select>
							</li>
						      <li><label class="label_name">销售员</label> <input name="saleStaff" type="text" id="saleStaff" value="" class="text_search"></li>
						      <li><label class="label_name">入住人</label><input name="userCheckin" type="text" id="userCheckin" value="" class="text_search"></li>
						      <li>
						      	<label class="label_name">担保类型</label>
								<select name="guarantee" id="guarantee" class="text_search">
									<option value="">--请选择担保类型--</option>
									<option value="1">无担保</option>
									<option value="2">有担保</option>
								</select>
							  </li>
						      <li><label class="label_name">预订人</label><input name="orderUser" id="orderUser" type="text" value="" class="text_search"></li>
						      <li><label class="label_name">预定手机</label> <input name="mphone" id="mphone" type="text" value="" class="text_search"></li>
						      <li><label class="label_name">状态</label>								
						      	<select name="status" id="status" class="text_search">
									<option value="">--请选择状态类型--</option>
									<option value="0">取消</option>
									<option value="1">新订</option>
									<option value="2">未到</option>
									<option value="3">在住/转单</option>
									<option value="4">离店</option>
									<option value="5">删除</option>
								</select>
							 </li>
			       		  </ul>
			       		  <ul class="clearfix">
			       		  	  <li><label class="label_name">预定日期</label> <input id="orderTime" name="orderTime" type="text" value="" class="check date text_search">
			       		  	  </li>
						      <li><label class="label_name">至</label><input name="ordtimes" id="ordtimes" type="text" value="" class="check date text_search">
						      </li>
			       		  </ul>
			       		  <ul class="clearfix">
			       		  	  <li><label class="label_name">抵店日期</label>	<input id="arrivalTime" name="arrivalTime" type="text" value=""
								class="check date text_search">
			       		  	  </li>
						      <li><label class="label_name">至</label><input name="arrtimes" id="arrtimes" type="text" value="" class="check date text_search">
						      </li>
			       		  </ul>
			       		   <ul class="clearfix">
			       		  	  <li><label class="label_name">离店日期</label> <input name="leaveTime" id="leaveTime" type="text" value="" class="check date text_search">
			       		  	  </li>
						      <li><label class="label_name">至</label><input name="leavetimes" id="leavetimes" type="text" value="" class="check date text_search">
						      </li>
			       		  </ul>
			       		  <ul class="clearfix">
							 <li><input type="button" value="确认" class="button_margin confirm" onclick="querydata()"></li>
			       		  </ul>
		    		</div>
				</form>
			</div>
		</div>
	<section class="box-content-section fl" style="width:100%;">
		<section class="box_content_widget fl">
			<div id="integral" class="content" style="height:902px;padding:10px;">
				<iframe name="orderFrame" id="orderFrame" frameborder="0" width="100%" height="100%" src="orderInfoAll.do"></iframe>
			</div>
		</section>
	</section>
	</div>
	<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>     
	<script src="<%=request.getContextPath()%>/script/common/jquery.jqGrid.src.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/datepickerCN.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>	
	<script src="<%=request.getContextPath()%>/script/ipms/js/order/orderinfo.js"></script>
	<script type="text/javascript">
		Pager.renderPager(<%=pagination%>);
		var path = "<%=request.getContextPath()%>";
	</script>
  </body>
</html>
