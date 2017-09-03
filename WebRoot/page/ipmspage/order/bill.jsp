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
    
    <title>账单</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/order/bill.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/order/orderfont.css"/>
  </head>
  
  <body>
    <div class="bill_width">
			<div class="bill_one">
				<section class="box_content_widget fl">
					<div class="content">	
						<table id="myTable" class="myTable" border="0">
							<thead>
								<tr>
									<th class="header">交易哈</th>
									<th class="header">会员卡号</th>
									<th class="header">会员登录名</th>
									<th class="header">会员姓名</th>
									<th class="header">会员身份证</th>
									<th class="header">会员邮箱</th>
									<th class="header">联系电话</th>
									<th class="header">会员等级</th>
									<th class="header">会员积分</th>
									<th class="header">备注</th>
								</tr>
							</thead>
							<tbody>

							</tbody>
						</table>
					</div>
				</section>
			</div>
			<div class="bill_two">
				<span>关联房</span>
				<ul class="clearfix fl">
					<li class="owner">
						<p id=""></p>
						<p>主客房</p>
						<p style="display: none" id="" ></p>
					</li>
				</ul>
			</div>
			<div class="bill_three">
				<span class="">所选消费<span class="money">0.00</span></span>
				<span class="">订单号<span class="money">2063993</span></span>
				<span class="">TTV<span class="money">0</span></span>
				<span class="">消费<span class="money">0.00</span></span>
				<span class="">结算<span class="money">0.00</span></span>
				<span class="">帐面余额<span class="money">1.00</span></span>
				<span class="">平面余额<span class="money">1.00</span></span>
			</div>
			<div class="bill_four"><span class="button_margin">现金</span><span class="button_margin">银行卡</span></div>
			<div class="foot_submit">
				<span class="button_margin">入账</span>
				<span class="button_margin">冲减</span>
				<span class="button_margin">发票</span>
				<span class="button_margin">打印</span>
				<span class="button_margin">取消订单</span>
			</div>
		</div>
  </body>
</html>
