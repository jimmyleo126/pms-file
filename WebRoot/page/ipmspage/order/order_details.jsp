<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../common/taglib.jsp"%>
<%@ include file="../../common/script.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>订单详细页面</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" id="style" type="text/css"
		href="<%=request.getContextPath()%>/css/ipms/css/order/order_details.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css" />	
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>	
	<link rel="stylesheet" id="style" type="text/css"
		href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css"/>
	<script src="<%=request.getContextPath()%>/script/common/jquery-ui.min.js"></script>	
	<script>var base_path = "<%=request.getContextPath()%>";</script>	
  </head>
  
  <body>
   	<div class="detail_wrap_margin detail_color">
			<div class="detail_title">
				<span class="top_icon"><i class="imooc im_top" onclick="window.parent.JDialog.close();">&#xea0f;</i></span>
				<span>订单<span class="order_span"><%=request.getParameter("orderid")%> ${ orderid }</span></span>
				<span class="mystatus"></span>
			</div>
			<div class="order_margin">
			<form action="" method="post">
				<section class="detail_one">
					<label class="label_title">1.订单信息</label>
					<ul class="clearfix">
						<li><label class="label_name">房号</label><input name="房号" onclick="roomselect(this)" placeholder="请选择房间号"  type="text" id="roomid" class="check room_select"></li>
						<li><label class="label_name">房间类型</label><input id="roomtype" name="房间类型" type="text" class="check"  id="roomtype" disabled/></li>
						<li><label class="label_name">抵店日期</label> <input id="arrivaltime" name="抵点日期" type="text" class="check" disabled></li>
						<li><label class="label_name">离店日期</label> <input id="leavetime" name="离店日期" type="text" class="check" disabled></li>
						<li><label class="label_name">预订人</label><input id="orderuser" name="预订人" type="text"  class="check" disabled></li>
						<li><label class="label_name">预定人手机</label> <input id="mphone" name="预定人手机" type="text" class="check" disabled></li>
						<li><label class="label_name">预定人会员类型</label> <input id="memberrank" name="预定人会员类型" type="text" class="check" disabled></li>
						<li><label class="label_name">市场活动</label> <input id="activity" name="市场活动" type="text" class="check" disabled></li>
						<li><label class="label_name">来源</label> <input id="source" name="来源" type="text" class="check" disabled></li>
						<li><label class="label_name">是否担保</label> <input id="guarantee" name="是否担保" type="text" class="check" disabled></li>
						<li><label class="label_name">保留时效</label> <input id="limited" name="保留时效" type="text"  class="check" disabled></li>
						<li><label class="label_name">订单号</label><input id="orderid" name="订单号" type="text" class="check" disabled></li>
						<li><label class="label_name">备注</label>
						<textarea  id="remark" name="remark" class="" rows="2" cols="124.5" readonly="readonly"></textarea>
						</li>
					</ul>
				</section>
				<section class="detail_two">
						<div class="two_left fl">
							<label class="label_title">2.客人信息</label>
							<input id="searchmembercondotion" type="text" value="" class="input_search">
							<span onclick="searchmember()" class="check_color button_margin">
							查找
							</span>
							<span class="register_one"  onclick="registermember()">
								注册会员
							</span>
						</div>

						<div class="fr">
							<ul class="clearfix">
								<li><span class="button_margin">读取证件</span></li>
								<li><span class="button_margin" onclick="sethostCustomer();">设置主客人</span></li>
								<li><span class="button_margin" onclick="addCustomer()">添加新客人</span></li>
								<li><span class="button_margin" onclick="deleteCustomer()">减少客人</span></li>
							</ul>
						</div>
						
						<div class="info_write fl">
							<ul class="check_ul clearfix" id="guest">
								<li class='check_name active'><label class="guestmemberid" style='display: none'></label><span>新客人</span></li>
							</ul>
							<ul class="customer_info clearfix">
								<li><label class="label_name">姓名</label><input name="username" type="text" id="username" class="check" readonly="readonly"></li>
								<li>
									<label class="label_name" for="">性别</label>
									<input id="gender" type="text" value="" class="info_write_select" disabled="disabled">
								</li>
								<li>
									<label class="label_name" for="" >会员类型</label>
									<select class="info_write_select" disabled='disabled'>
										<option value="1" id="memberRank"></option>
									</select>
								</li>
								<li><label class="label_name">会员编码</label><input name="会员编码" id="memberid" type="text" value="" class="check" disabled></li>
								<li><label class="label_name">出生日期</label> <input name="出生日期" id="birthday" type="text" value="" class="check" disabled></li>
								<li><label class="label_name">手机号码</label> <input name="手机号码" id="checkphone" type="text" value="" class="check"></li>
								<li>
									<label class="label_name" for="">证件类型</label>
									<select class="info_write_select" name="cars" disabled='disabled'>
										<option value="0">身份证/驾驶证</option>
										<option value="1">居住证</option>
									</select></li>
								<li><label class="label_name">证件号码</label> <input name="身份证号码" type="text" value="" id="idcard" class="check" disabled></li>
							</ul>
						</div>
				</section>
				<section class="detail_three">
					<div class="detail_three_div">
						<label class="label_title">3.预付项目</label>
						<span class="small" id="advance"></span>
					</div>
					<div class="table_div">
					<table border="1" class="myTable" style="display:none;">
						<thead>
							<tr>
								<th class="header">项目</th>
								<th class="header">名称</th>
								<th class="header">消费</th>
								<th class="header">结算</th>
							</tr>
							</thead>
						<tbody>	
							<tr class="cardcontent">
								<td>银行卡</td>
								<td>预付</td>
								<td>0.00</td>
								<td class="card"></td>
							</tr>
							<tr class="cashcontent">
								<td>现金</td>
								<td>预付</td>
								<td>0.00</td>
								<td class="cash"></td>
							</tr>
						</tbody>
					</table>
				</section>
				<section class="detail_four" >
					<div class="fr" role="button">
						<ul class="clearfix">
							<li onclick="checkin();"><span class="button_margin">入住</span></li>
							<li onclick="cancleorder();"><span class="button_margin">取消订单</span></li>
							<li onclick="showeditorder();"><span class="button_margin update_order">修改订单</span></li>
							<li onclick="checkOrder()"><span class="button_margin look_order">查看订单</span></li>
							
						</ul>
					</div>
				</section>
				<!--  房间选择-->
				<!-- 
				<section class="detail_five">
					<div class="high_header">
						<h2>
							房间选择
						</h2>
						<i class="imooc" style="color: #3B3E40;">&#xe902;</i>
						<div class="margintop">
							<span class="span_title">房型：<!-- <span class="span_name" id="roomtype"></span> --></span>
							<!-- <ul class="clearfix">
								<<li><span id="roomnumber">305</span></li> 
							</ul>-->
						</div>
					</div>
				</section>-->
				

			</div> 
		</div>
		<script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/workbillroom/util.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/jquery-ui.min.js"></script>
	    <script src="<%=request.getContextPath()%>/script/common/jquery.dialog.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script>
			var path = "<%=request.getContextPath() %>";
			var orderid = '<%=request.getParameter("orderid")%>';
			$(document).ready(function(){
				$.ajax({
			         url: path + "/loadOrderData.do",
					 type: "post",
					 dataType: "json",
					 data : {orderId : orderid},
					 success: function(json) {
						 console.log(json)
					 	$("#roomtype").val(json[0]["ROOMTYPE"]);
					 	//$("#orderid").val(json["ROOMID"]);
					 	$("#arrivaltime").val(json[0]["ARRIVALTIME"]);
					 	$("#leavetime").val(json[0]["LEAVETIME"]);
					 	$("#orderuser").val(json[0]["ORDERUSER"]);
					 	$("#mphone").val(json[0]["MPHONE"]);
					 	$("#source").val(json[0]["SOURCE"]);
					 	$("#birthday").val(json[0]["BIRTHDAY"]);
					 	$("#memberrank").val(json[0]["MEMBERRANK"]);
					 	$("#activity").val(json[0]["ACTIVITY"]);
					 	$("#guarantee").val(json[0]["GUARANTEE"]);
					 	$("#limited").val(json[0]["LIMITED"]);
					 	$("#orderid").val(json[0]["ORDERID"]);
					 	$("#advance").html("预付金额：" + json[0]["ADVANCE"]);
					 	$("#remark").val(json[0]["REMARK"]);
					 	$(".costone").val("0.00");
					 	$(".mystatus").text(json[0]["STATUS"]);
					 	if (json[0]["ADVANCECARD"] == 0 || json[0]["ADVANCECARD"] == '0') {
					 		$(".cardcontent").hide();
						 } else {
							$(".myTable").show();
							$(".card").text(json[0]["ADVANCECARD"]);	
						}
					 	if (json[0]["ADVANCECASH"] == 0) {
					 		$(".cashcontent").hide();
						 } else {
							$(".myTable").show();
					 		$(".cash").text(json[0]["ADVANCECASH"]);
						 }
					 	//loadCheckuser(json[0]["CHECKUSER"],json[0]["CHECKUSERNAME"]);
					 },
					 error: function(json) {}
					 
				});
			});
			
			function roomselect(){
				$(".detail_five").css("display","block");
				window.JDialog.open("选房", path + "/page/ipmspage/order/getRoom.jsp?orderId=" + <%=request.getParameter("orderid")%> , 1000, 650);
			}
			$(".imooc").on("click",function(){
				$(".detail_five").css("display","none");
			});
			$(".detail_imooc").on("click",function(){
				$(".detail_six").css("display","none");
			});
			$(".update_order").on("click",function(){
				$(".detail_six").css("display","block");
			})
			$(".info_write ul li").on("click", function() {
				$(this).addClass("active");
				$(this).siblings().removeClass("active");
			});
			$(".detail_four .look_order").on("click", function() {
				window.location.href = base_path + "order_check.jsp";
			});
			
			function checkin() {
				if(isCheckout(orderid)){
					return false;
				}
				if ($("#memberid").val() == null || $("#memberid").val() == "") {
					showMsg("请至少填写一位入住人！")
					return false;
				} else if ($("#roomid").val() == null || $("#roomid").val() == "") {
					showMsg("请选择房号！")
					return false;
				} else {
					var checkinuser = "";
					$.each($(".check_ul li"),function(i){
						if (i == 0) {
						 	checkinuser = $($(".check_ul li")[i]).find("label:hidden").html() ;
						} else {
							checkinuser = checkinuser + "," + $($(".check_ul li")[i]).find("label:hidden").html();
						}
					})
					$.ajax({
				         url: path + "/checkIn.do",
						 type: "post",
						 dataType: "json",
						 data : { checkinUser : checkinuser, 
						 			orderId: $("#orderid").val(), roomId: $("#roomid").val() },
						 success: function(json) {
						 	if (json.result == 0) { 
						 		showMsg("入住成功!");
						 		window.setTimeout("window.parent.JDialog.close()",1800);
						 	} else if (json.result == 1) {
						 		showMsg(json.message);
						 	}	
						 },
						 error: function(json) {
						 	showMsg("入住失败!");
						 }
					});
				}
			}
			
			function cancleorder() {	
				if(isCheckout(orderid)){
					return false;
				}
				$.ajax({
					url: path + "/cancleorder.do",
					type: "post",
					datatype: "json",
					data: { orderId: $("#orderid").val() },
					success: function (json) {
						if (json.result == 0) {
							showMsg(json.message);
						}
					},
					error: function(json) {
						showMsg("操作失败！");
					}
				})
			}
			
			function registermember(){
				if(isCheckout(orderid)){
					return false;
				}
				JDialog.open("会员注册", path + "/page/ipmspage/leftmenu/order/ordermemberadd.jsp" , 800, 410);
			}
			//订单查看
			function checkOrder(){
				var customerid;
				$(".check_name").each(function(i){
					if (i == 0) {
						customerid = $(this).find("label:hidden").text(); 
					} else {
						var custid = $(this).find("label:hidden").text(); 
						customerid = customerid + "," + custid;
					}
				})
				window.parent.JDialog.openWithNoTitle("", path + "/page/ipmspage/order/order_check.jsp?orderId=" + $("#orderid").val() + 
						"&roomId=" + $("#roomid").val() +"&customerid="+customerid, 1179, 733);
			}
			
			function searchmember(){
				if(isCheckout(orderid)){
					return false;
				}
				var datamember = $("#searchmembercondotion").val();
				$.ajax({
			         url: path + "/searchmember.do",
					 type: "post",
					 data : {datamember : datamember},
					 success: function(json) {
					 	if(json.result == 0){
					 		showMsg(json.message);
					 	}else if(json.result == 1){
					 		var flag = false;//判断是否有新客人
					 		$.each($(".check_ul li"),function(index){
					 			if($($(".check_ul li")[index]["childNodes"][0]).html() == ''){
					 				flag = true;
					 				$($(".check_ul li")[index]["childNodes"][0]).html(json.data[0]["MEMBERID"]);
					 				$($(".check_ul li")[index]["childNodes"][1]).html(json.data[0]["MEMBERNAME"]);
					 				loadCustomer(json.data[0]["MEMBERID"]);
					 			}else if($($(".check_ul li")[index]["childNodes"][0]).html() == json.data[0]["MEMBERID"]){
					 				flag = true;
					 				showMsg("客人重复!");
					 				return false;
					 			}
					 		});
					 		if(!flag){
					 			showMsg("请添加新客人!");
					 		}
					 	}
					 },
					 error: function(json) {}
				});
			}
			
			function addCustomer(){
				if(isCheckout(orderid)){
					return false;
				}
				var countnewguest = 0;
		 		$.each($(".check_ul li"),function(index){
		 			if($($(".check_ul li")[index]["childNodes"][0]).html() == ''){
		 			countnewguest = countnewguest + 1;
		 			}
		 		});
				if(countnewguest < 1){
					var childnode=$("<li class='check_name'><label style='display: none'></label><span>新客人</span></li>");
					$(".check_ul").append(function(n){
						return childnode;
					});
					$(childnode).addClass("active");
					$(childnode).siblings().removeClass("active");
					resetCustomer();
					bindcustomerclick();
				}
			}
			
			function sethostCustomer(){
				if(isCheckout(orderid)){
					return false;
				}
				var msg="";
				var hostid = $($(".check_ul").children().eq(0)).find("label:hidden").html();
				var hostname = $($(".check_ul").children().eq(0)).find("span").text();
				var guestid = $(".check_ul").find(".active").find("label:hidden").html();
				var guestname = $(".check_ul").find(".active").find("span").html();
				
				if(hostid == ''){
					msg = "请先至少添加一位客人信息!";
					showMsg(msg);
				} else if(hostid == guestid){
					msg = "当前已是主客人!"
					showMsg(msg);
				} else {
					var tempid,tempname;
					tempid = hostid;
					tempname = hostname;
					debugger;
					$($(".check_ul").children().eq(0)).find("label:hidden").html(guestid);
					$($(".check_ul").children().eq(0)).find("span").html(guestname);
					$(".check_ul").find(".active").find("label:hidden").html(tempid);
					$(".check_ul").find(".active").find("span").html(tempname);
					loadCustomer(hostid);
				}
			}
			
			function bindcustomerclick(){
				if(isCheckout(orderid)){
					return false;
				}
				$(".check_ul li").on("click",function() {
					$(this).addClass("active");
					$(this).siblings().removeClass("active");
					if( $(this["childNodes"][0]).html() == ''){
						resetCustomer();
					}else {
						loadCustomer($(this["childNodes"][0]).html());					
					}
				});
			}
			
			function deleteCustomer(){
				if(isCheckout(orderid)){
					return false;
				}
				var oUl =$(".check_ul");
				var a = oUl.find("li:not(:first-child)");
				var len = a.length;
				a[len-1].remove();
			}	
			
			function resetCustomer(){
				if(isCheckout(orderid)){
					return false;
				}
				$(".customer_info input").val("");
				$("#memberrank").html("");
				$("#address").val("");
			}
			
			function loadCustomer(memberid){
				$.ajax({
			         url: path + "/loadGuestData.do",
					 type: "post",
					 data : {memberid : memberid},
					 success: function(json) {
					 	$("#username").val(json["member"]["memberName"]);
					 	$("#gender").val((json["member"]["gendor"]==0?'女':'男'));
					 	$("#checkphone").val(json["member"]["mobile"]);
					 	$("#idcard").val(json["member"]["idcard"]);
					 	$("#memberid").val(json["member"]["memberId"]);
					 	$("#birthday").val(json["birthday"]);
					 	$("#memberRank").html(json["rankName"]);
					 	$("#address").val(json["member"]["address"]);
					 	$("#reamrk").text(json["member"]["reamrk"]);
					 },
					 error: function(json) {}
				});
			}
			
		function showeditorder(){
			if(isCheckout(orderid)){
					return false;
				}
			//window.parent.JDialog.openWithNoTitle("", path + "/page/ipmspage/order/order_check.jsp", 700, 400);/
			window.JDialog.open("修改订单", path + "/page/ipmspage/order/editorder.jsp?orderId=" + $("#orderid").val() , 700, 400);
		}	
	</script>
  </body>
</html>
