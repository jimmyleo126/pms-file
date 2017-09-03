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
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/pagination.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlistfont.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/roomlist.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/showdialog/alert.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>	
	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/css/pagenation.css" >

  </head>
  <body>
  	<form id="pagerForm" name="pagerForm" action="operateLog.do" target="_self">
	    <div>
	    	<table id="myTable" class="myTable" border="0" width="100">
				<thead id="log">
					<tr>
						<th class="header" style="width:120px;">订单号</th>
						<th class="header">房型</th>
						<th class="header">房价</th>
						<th class="header">担保</th>
						<th class="header">时效</th>
						<th class="header">预订人</th>
						<th class="header">预订人手机</th>
						<th class="header">预订渠道</th>
						<th class="header">预订时间</th>
						<th class="header">抵店时间</th>
						<th class="header">预离时间</th>
						<th class="header">状态</th>
						<th class="header">接待备注</th>
						<th class="header">客房备注</th>
						<th class="header">备注</th>
						<th class="header">操作</th>
					</tr>
				</thead>
				<tbody id="info">
					<c:forEach items="${orderinfo}" var="ordinfo" varStatus="status">
						<tr ondblclick="checkorderinfo(this)">
							<td>${ordinfo["ORDERID"] }</td>
							<td>${ordinfo["ROOMNAME"] }</td>
							<td>${ordinfo["ROOMPRICE"] }</td>
							<td>${ordinfo["GUARANTEE"] }</td>
							<td>${ordinfo["LIMITED"] }</td>
							<td>${ordinfo["ORDERUSER"] }</td>
							<td>${ordinfo["PHONE"] }</td>
							<td>${ordinfo["SOURCE"] }</td>
							<td>${ordinfo["ORDERTIME"] }</td>
							<td>${ordinfo["ARRIVALTIME"] }</td>
							<td>${ordinfo["LEAVETIME"] }</td>
							<td>${ordinfo["STATUS"] }</td>
							<td>${ordinfo["ROOMREMARK"] }</td>
							<td>${ordinfo["RECEPTIONREMARK"] }</td>
							<td>${ordinfo["REMARK"] }</td>
							<td onclick="changestatus(this);"><!-- <span class="cancel_span cancel_btn"></span>--><span class="undone"></span></td> 
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div id="pager"></div>
	</form>
	<%@ include file="../../common/script.jsp"%>
	<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>	
	<script src="<%=request.getContextPath()%>/script/ipms/js/showdialog/alert.js"></script>
	<script>
		Pager.renderPager(<%=pagination%>);
	</script>
	<script>
		var path = "<%=request.getContextPath()%>";
		var M = {

		}
		/*弹出层*/
		$(document).delegate(".cancel_btn",'click',function(){
			if(M.dialog9){
				return M.dialog9.show();
			}
			M.dialog9 = jqueryAlert({
				'style'   : 'pc',
				'title'   : '提示',
				'content' :  '确定要取消当前客人吗？',
				'modal'   : true,
				'contentTextAlign' : 'left',
				'animateType': 'scale',
				'bodyScroll' : 'true',
				'buttons' : {
					'取消' : function(){
						M.dialog9.close();
					},
					'确定' : function(){
						//删除当前客人数据
					}
				}
			})
		})
		function checkorderinfo(e) {
			var orderid = $(e.children[0]).html();
			window.parent.parent.JDialog.openWithNoTitle("", path + "/page/ipmspage/order/order_details.jsp?orderid=" + orderid,1179,733);
			//window.location.href=path + "/page/ipmspage/room_statistics/roomlist_check.jsp?checkid=" + checkid;
		}
		$("table.myTable tbody tr").mouseover(function(){
			$(this).find(".cancel_span").css({'background-image':'url('+ path +'/css/ipms/img/backgroundimg/cancel2.png)'});
			$(this).find(".undone").css({'background-image':'url('+ path +'/css/ipms/img/backgroundimg/undone2.png)'});
			$(".cancel_span").css("background-repeat","no-repeat");
			$(".cancel_span").css("background-position","center");
			$(".undone").css("background-repeat","no-repeat");
			$(".undone").css("background-position","center");
		})
		$("table.myTable tbody tr").mouseout(function(){
			$(this).find(".cancel_span").css({'background-image':'url('+ path +'/css/ipms/img/backgroundimg/cancel.png)'});
			$(this).find(".undone").css({'background-image':'url('+ path +'/css/ipms/img/backgroundimg/undone.png)'});
		})
		$(".cancel_span").on("click",function(){
			$(this).css({'background-image':'url('+ path +'/css/ipms/img/backgroundimg/cancel3.png)'});
			
		})
		
		$(".undone").on("click",function(){
			$(this).css({'background-image':'url('+ path +'/css/ipms/img/backgroundimg/undone3.png)'});
		})
		
		function changestatus(e) {
			var orderid = $($(e).parent().find("td:first")).text();
			$.ajax({
			   url: path + "/changeorderstatus.do",
				 type: "post",
				 dataType: "json",
				 data : {orderId : orderid},
				 success: function(json) {
					 if (json) {
						showMsg(json.message);
					}
				 },
				 error: function(json) {
					 showMsg("操作失败！");
				 }
			});
		}
		
		function showMsg(msg, fn) {
			if (!fn) {
				TipInfo.Msg.alert(msg);
			} else {
				TipInfo.Msg.confirm(msg, fn);
			}
		}
	</script>
  </body>
</
<html>