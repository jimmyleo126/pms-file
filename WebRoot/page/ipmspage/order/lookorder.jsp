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
    <title>订单</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/order/order_check.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css"/>
	<link href="//cdn.bootcss.com/font-awesome/4.6.3/css/font-awesome.min.css"  rel="stylesheet">
	<script>var base_path = "<%=request.getContextPath()%>";</script>
  </head>
  <body>
		<div class="content_color" id="ifa">
		<input id="orderId" type="text" hidden="true" value="<%=request.getParameter("orderId")%>"/>
			<div class="ifa">
				<div class="check_one fl">
					<form action="" method="post">
						<ul class="clearfix">
							<li><label class="label_name">房间类型</label><input name="房间类型" type="text" id="roomtype"  class="check" disabled="disabled"></li>
							<li><label class="label_name">抵店日期</label> <input name="抵店日期" type="text" id="arrivaltime" class="check" disabled="disabled"></li>
							<li><label class="label_name">离店日期</label> <input name="离店日期" type="text" id="leavetime"  class="check" disabled="disabled"></li>
							<li><label class="label_name">房号</label> <input name="房号" id="roomid" type="text" value="<%=request.getParameter("roomid")%>" class="check" disabled="disabled"></li>
							<li><label class="label_name">房价</label> <input name="房价" type="text" id="roomprice" value="" class="check" disabled="disabled"></li>
							<li><label class="label_name">活动</label> <input name="活动" id="activity"  type="text" value="" class="check" disabled="disabled"></li>
							<li><label class="label_name">担保</label> <input name="担保" id="guarantee" type="text" value="" class="check" disabled="disabled"></li>
							<li><label class="label_name">保留时效</label> <input name="保留时效" id="limited" type="text" value="" class="check" disabled="disabled"></li>
							<li><label class="label_name">状态</label> <input name="状态" id="status" type="text" value="" class="check" disabled="disabled"></li>
							<li><label class="label_name">预定人会员类型</label> <input name="预定人会员类型" id="memberrank" type="text" value="" class="check" disabled="disabled"></li>
							<li><label class="label_name">预订人</label><input name="预订人" id="orderuser"  type="text" value="" class="check" disabled="disabled"></li>
							<li><label class="label_name">预定人手机</label> <input name="预定人手机" id="mphone" type="text" value="" class="check" disabled="disabled"></li>
							<li><label class="label_name">预定人会员卡号</label> <input name="预定人会员卡号"  id="memberid" type="text" value="" class="check" disabled="disabled"></li>
							<li><label class="label_name">销售员</label><input name="销售员" id="staff" type="text" value="" class="check" disabled="disabled"></li>
							<li><label class="label_name">接待备注</label> <textarea name="备注"  id="receptionremark" class="check txt_area" rols="3" cols="100" disabled="disabled"></textarea></li>
							<li><label class="label_name">客房备注</label> <textarea name="备注"  id="roomremark" class="check txt_area" rols="3" cols="100" disabled="disabled"></textarea></li>
							<li><label class="label_name">备注</label> <textarea name="备注"  id="remark" class="check txt_area" rols="3" cols="100" disabled="disabled"></textarea></li>						
						</ul>
					</form>
				</div>
				<div class="check_two fl">
					<ul class="check_ul clearfix"  id="checklabel">
						<li class='check_name active' id="guestmemberid"><label></label><span>新客人</span></li>
					</ul>
					<ul class="clearfix" id="">
						<li><label class="label_name">姓名</label> <input name="姓名" type="text" id="memberName" class="check" disabled="disabled"></li>
						<li><label class="label_name">性别</label> <input name="性别" type="text" value="" id="gendor" class="check" disabled="disabled"></li>
						<li><label class="label_name">出生日期</label> <input name="出生日期" type="text" id="birthday" value="" class="check" disabled="disabled"></li>
						<li><label class="label_name">证件类型</label> <input name="证件类型" type="text"  value="" class="check" disabled="disabled"></li>
						<li><label class="label_name">证件号码</label> <input name="证件类型" type="text" id="idcard" value="" class="check" disabled="disabled"></li>
						<li><label class="label_name">备注</label> <textarea name="备注"  id="remark" class="check txt_area" rols="3"></textarea></li>
					</ul>	
					<span class="button_margin getIN" role="button" onClick="enterIN()">入住</span>
				</div>
			</div>
		</div>
	</div>
	<script src="<%=request.getContextPath()%>/script/ipms/js/order_check/order_check.js"  charset="utf-8"></script>
	<script src="<%=request.getContextPath()%>/script/common/jquery-ui.min.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/jquery.dialog.js"></script>
	<script>
		var path = "<%=request.getContextPath() %>";
		var orderId = '<%=request.getParameter("orderId")%>';
		var customerId = '<%=request.getParameter("customerid")%>';
		$(document).ready(function(){
			$.ajax({
		         url: path + "/loadOrderData.do",
				 type: "post",
				 dataType: "json",
				 data : {orderId : orderId},
				 success: function(json) {
				 	$("#roomtype").val(json[0]["ROOMTYPE"]);
				 	//$("#orderid").val(json["ROOMID"]);
				 	$("#arrivaltime").val(json[0]["ARRIVALTIME"]);
				 	$("#leavetime").val(json[0]["LEAVETIME"]);
				 	$("#orderuser").val(json[0]["ORDERUSER"]);
				 	$("#mphone").val(json[0]["MPHONE"]);
				 	$("#source").val(json[0]["SOURCE"]);
				 	$("#roomprice").val(json[0]["PRICE"]);
				 	$("#birthday").val(json[0]["BIRTHDAY"]);
				 	$("#memberrank").val(json[0]["MEMBERRANK"]);
				 	$("#activity").val(json[0]["ACTIVITY"]);
				 	$("#guarantee").val(json[0]["GUARANTEE"]);
				 	$("#limited").val(json[0]["LIMITED"]);
				 	$("#orderid").val(json[0]["ORDERID"]);
				 	$("#advance").html("预付金额：" + json[0]["ADVANCE"]);
				 	$("#receptionremark").val(json[0][""])
				 	$("#remark").val(json[0]["REMARK"]);
				 	$("#status").val(json[0]["STATUS"]);
				 	$(".mystatus").text(json[0]["STATUS"]);
				 	$("#staff").val(json[0]["RECORDUSER"]);
				 	$("#memberid").val(json[0]["MEMBERID"]);
				 	//loadCheckuser(json[0]["CHECKUSER"],json[0]["CHECKUSERNAME"]);
				 },
				 error: function(json) {}
				 
			});
			
			loadCustomer(customerId);
		});

		function loadCustomer(memberid){
			$.ajax({
		         url: path + "/queryGuest.do",
				 type: "post",
				 data : {memberid : memberid},
				 success: function(json) {
					 if (json) {
						 $("#guestmemberid").hide();
						 console.log(json);
						if (memberid.indexOf(",") > 0) {
							var memberids = memberid.split(",");
						 	for (i=0;i<memberids.length;i++) {
					 			$("<li class='check_name' onclick='queryCustomer(this)'><label style='display:none;'>" + json[memberids[i]]["memberId"] + "</label><span>" + 
							 			json[memberids[i]]["memberName"] + "</span></li>").appendTo("#checklabel");
					 			if (i == 0) {
					 				$("#memberName").val(json[memberids[i]]["memberName"]);
									$("#gender").val((json[memberids[i]]["gendor"]==0?'女':'男'));
									$("#birthday").val(json[memberids[i]]["birthday"]);
									$("#idcard").val(json[memberid]["idcard"]);
									$("#reamrk").text(json[memberids[i]]["reamrk"]);
							 	}
							 	
							}
						} else {
							$("<li class='check_name'><span>" + json[memberid]["memberName"] + "</span></li>").appendTo("#checklabel");
							$("#memberName").val(json[memberid]["memberName"]);
							$("#gendor").val((json[memberid]["gendor"]==0?'女':'男'));
							$("#birthday").val(json[memberid]["birthday"].split(" ")[0]);
							$("#idcard").val(json[memberid]["idcard"]);
							$("#reamrk").text(json[memberid]["reamrk"]);
						}	
					}
				},
				 error: function(json) {}
			});
		}

		function queryCustomer(e) {
			var memid = $($(e).find("label:hidden")).text();
			console.log(memid)
			$.ajax({
				url: path + "/queryGuest.do",
				type: "post",
				data : {memberid : memid},
				success: function(json) {
					if(json) {
						$("#memberName").val(json[memberid]["memberName"]);
						$("#gender").val((json[memberid]["gendor"]==0?'女':'男'));
						$("#birthday").val(json[memberid]["birthday"]);
						$("#idcard").val(json[memberid]["idcard"]);
						$("#reamrk").text(json[memberid]["reamrk"]);
					}
				},
				error: function(json) {}
			});
		}
	</script>	
    </body>
</html>
