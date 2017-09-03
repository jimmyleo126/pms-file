<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/script.jsp"%>
<%@ include file="../../common/css.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>储值卡充值</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/addCustom.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/reset.css"/>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
   	<table class="table_account">
   		<tr style="display: none">
   			<td>账户编号</td>
   			<td><input id="account_Id" type="text" value="${accountid }" readonly="true"/></td>
   		</tr>
   		<tr>
   			<td>会员编号</td>
   			<td><input id="member_Id" type="text" value="${memberid }" readonly="true"/></td>
   		</tr>
   		<tr>
   			<td>会员名称</td>
   			<td><input id="member_name" type="text" value="${membername }" readonly="true"/></td>
   		</tr>
   		<tr>
   			<td>选择活动</td>
   			<td>
   				<input id="total_recharge" name="total_recharge" value="" readonly="true" onpropertychange="aaa()"/>
				<input type="hidden" id="total_recharge_CUSTOM_VALUE" name="total_recharge_CUSTOM_VALUE" value=""/>
				<img class="div_img" src="<%=request.getContextPath()%>/images/x.png" onclick="empty(0)"/>
   			</td>
   		</tr>
   		<tr>
   			<td>充值金额</td>
   			<td><input id="discount_gift" type="text" value="" 
   			onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" 
   			onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"/>
   			<img class="div_img" src="<%=request.getContextPath()%>/images/x.png" onclick="empty(1)"/>
   			</td>
   		</tr>
   	</table>
   	<div class="div_desc" id="total_recharge_DESC"></div>
    <div class="div_button">
    	<input class="submit" type="button" value="提交" onclick="updateReserve()"/>  
    </div>
	<div class="div_button">
		<input class="cancel" type="button" value="取消" onclick="window.parent.JDialog.close()"/>
	</div>
  </body>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/crm/member/addCustom.js"></script>
</html>
