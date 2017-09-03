<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setAttribute("basePath", request.getContextPath());
	request.setAttribute("orderId", request.getAttribute("orderId"));
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>订单详情</title>
<%@ include file="../../common/css.jsp"%>
<%@ include file="../../common/script.jsp"%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/ui.jqgrid.css"/>
<script src="<%=request.getContextPath()%>/script/common/jquery.jqGrid.src.js"></script>
<script src="<%=request.getContextPath()%>/script/common/grid.locale-cn.js"></script>
<body>
	<table id="orderRoomDetailShow"></table>
   	<div id="pager"></div>
   	<div class="dialogButton" style="margin:8px auto" onclick="closed()">关 闭</div>
	<script>
		var base_Path = "${basePath}";
		var orderId = "${orderId}";
		jQuery(document).ready(function() {
			jQuery("#orderRoomDetailShow").jqGrid({
				url: "roomorder.do?orderId="+orderId,
				datatype: "json",
			   	colNames: setGridCols(),
			   	colModel: setGridModels(),   
			   	width: 800,
			   	height: 270,
			   	rowNum: 20,
			   	rowList: [20,50,100],
			   	pager: "#pager",
			   	forceFit: true,
			   	cellsubmit: "clientArray",
			   	sortname: "id",
			    viewrecords: true,
			    sortorder: "desc",
			    altRows: true,
				altclass: "ui-state-focus"
			});
		});
		
	function setGridCols() {
		return ['订单号','来源','销售员','预订人','入住人','支付方式','房间状态','下单日期','更新人'];
	}
	
	function setGridModels() {
		return [{name: 'ORDER_ID', index: 'ORDER_ID', width:60, align:"center" },
		    {name: 'SOURCE', index: 'SOURCE', width:60, align:"center" },
		    {name: 'SALE_STAFF', index: 'SALE_STAFF', width:60, align:"center" },
		    {name: 'ORDER_USER', index:'ORDER_USER' ,hidden:true,width:60, align:"center" },
		    {name: 'USER_CHECKIN', index:'USER_CHECKIN' ,width:60, hidden:true, align:"center" },
		    {name: 'PAYMENT_TYPE', index:'PAYMENT_TYPE' ,hidden:true,width:80, align:"center" },
		    {name: 'STATUS', index:'STATUS' ,width:60, align:"center" },
		    {name: 'ORDER_TIME', index:'ORDER_TIME' ,width:60, align:"center" },
		    {name: 'RECORD_USER', index:'RECORD_USER' ,width:60, align:"center" }]
		    
	}
	
	function closed(){
		window.parent.JDialog.close();
	}
	</script>
</body>
</html>
