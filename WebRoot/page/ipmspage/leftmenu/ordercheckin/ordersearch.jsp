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
  	<title>订单查询</title>
    <link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/pagination.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/order/order.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/leftmenu/left_order.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style.css" />
    <script>
      var base_path = "<%=request.getContextPath()%>";
	</script>
  </head>
  <body >
	<div class="box_popup_member box_popup_member_margin leftorder_margin">
		<span class="close_span"><i class="imooc imooc_order" style="color:#3B3E40;" onclick="window.parent.JDialog.close();">&#xe900;</i></span>
		<h3>查找订单</h3>
		<form action="" method="post">
			<div class="content_member">
				<div class="search_style fl">
					<ul class="search_member clearfix fl">
						<li><label class="label_name">订单号:</label><input id="orderid" name="orderid" type="text" class="search_text"></li>
						<li><label class="label_name">预订人:</label><input id="orderuser" name="orderuser" type="text" class="search_text"></li>
						<li><label class="label_name">入住人:</label><input id="usercheckin" name="usercheckin" type="text" class="search_text"></li>
						<li><label class="label_name">会员手机:</label><input  id="mobile" name="mobile" type="text" class="search_text"></li>
						<li><label class="label_name">会员卡号:</label><input id="memberid" name="memberid" type="text" class="search_text"></li>
						<li><input type="button" class="btn_style btn_search button_margin" onclick="osearchdata()" value="查询"/></li>
					</ul>
				</div>
				<div class="content_member_show" style="width: 100%; height:97%;">
					<iframe name="" id="orderIframe" frameborder="0" width="100%" height="100%" src="orderData.do"></iframe>
				</div>
			</div>
		</form>
	</div>
	<%@ include file="../../../common/script.jsp"%>
	<script src="<%=request.getContextPath()%>/script/ipms/js/integral/integral.js"></script>
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
		
		function osearchdata(){
		var orderid = $("#orderid").val();
		var orderuser = $("#orderuser").val();
		var usercheckin = $("#usercheckin").val();
		var mobile = $("#mobile").val();
		var memberid = $("#memberid").val();
		if((!orderid)&&(!orderuser)&&(!usercheckin)&&(!mobile)&&(!memberid)){
		  $("#orderIframe").attr("src","orderData.do");
		}else{	
		  $("#orderIframe").attr("src","orderCondition.do?orderid="+orderid+"&orderuser="+orderuser+"&usercheckin="+usercheckin+"&mobile="+mobile+"&memberid="+memberid);
		  }
		}
		</script>
  </body>
</html>
