<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../../common/script.jsp"%>
<%@ include file="../../../common/css.jsp"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@ page import="com.ideassoft.core.page.Pagination"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	JSONObject pagination = JSONObject.fromObject(request.getAttribute("pagination"));	
%>
<!DOCTYPE HTML>
<html>
  <head>
  	<title>公寓预约</title>
    <link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/pagination.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/order/order.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/leftmenu/left_order.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/datetimepicker.css" media="all" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style.css" />
    <script>
      var base_path = "<%=request.getContextPath()%>";
	</script>
  </head>
  <body >
	<div class="box_popup_member box_popup_member_margin leftorder_margin">
		<span class="close_span"><i class="imooc imooc_order" style="color:#3B3E40;" onclick="window.parent.JDialog.close();">&#xe900;</i></span>
		<h3>公寓预约</h3>
		<form id="pagerForm" name="pagerForm" action="queryApartmentReserved.do" method="post" target="reservedFrame">
			<div class="content_member">
				<div class="search_style fl">
					<ul class="search_member clearfix fl">
						<li><label class="label_name">预约人:</label><input id="memberName" name="memberName" type="text" class="search_text"></li>
						<li><label class="label_name">手机号:</label><input  id="mobile" name="mobile" type="text" class="search_text"></li>
						<li><label class="label_name">预约时间:</label><input id="reservedTime" name="reservedTime" type="text" class="search_text check date"></li>
						<li><button type="button" class="btn_style btn_search button_margin" onclick="submit()">查询</button></li>
					</ul>
				</div>
				<div class="content_member_show" style="width: 100%; height:97%;">
					<iframe name="reservedFrame" id="reservedFrame" frameborder="0" width="100%" height="100%" src="queryApartmentReserved.do"></iframe>
				</div>
			</div>
		</form>
	</div>
	<%@ include file="../../../common/script.jsp"%>
	<script src="<%=request.getContextPath()%>/script/ipms/js/integral/integral.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/datepickerCN.js"></script>
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
			var memberName = $("#memberName").val();
			var mobile = $("#mobile").val();
			var reservedTime = $("#reservedTime").val();
		 	$("#reservedFrame").attr("src","queryApartmentReserved.do?memberName="+memberName+"&mobile="+mobile+"&reservedTime="+reservedTime);
		}
		</script>
  </body>
</html>
