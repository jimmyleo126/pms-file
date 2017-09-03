<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../common/newscript.jsp"%>
<!DOCTYPE HTML>
<html>
	<head>
		<title>客单</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/customer.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css" />
		<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
		<link href="//cdn.bootcss.com/font-awesome/4.6.3/css/font-awesome.min.css"  rel="stylesheet">
	</head>

	<body>
		<div class="ifa" id="f">
			<div class="check_one">
				<form action="" method="post">
					<ul class="clearfix">
						<li>
							<label class="label_name">
								房型
							</label>
							<input id="roomtype" type="text" value="" class="check specolor"
								disabled="disabled">
						</li>
						<li>
							<label class="label_name">
								房号
							</label>
							<input id="roomid" type="text" value="" class="check"
								disabled="disabled">
						</li>
						<li>
							<label class="label_name">
								抵点时间
							</label>
							<input id="checkintime" type="text" value="" class="check"
								disabled="disabled">
						</li>
						<li>
							<label class="label_name">
								离店时间
							</label>
							<input id="checkouttime" type="text" value="" class="check"
								disabled="disabled">
						</li>
						<li>
							<label class="label_name">
								房价
							</label>
							<input id="roomprice" type="text" value="" class="check"
								disabled="disabled">
						</li>
						<li>
							<label class="label_name">
								来源
							</label>
							<input id="source" type="text" value="" class="check"
								disabled="disabled">
						</li>
						<li>
							<label class="label_name">
								活动
							</label>
							<input id="" type="text" value="" class="check"
								disabled="disabled">
						</li>
						<li>
							<label class="label_name">
								担保
							</label>
							<input id="guarantee" type="text" value="" class="check"
								disabled="disabled">
						</li>
						<li>
							<label class="label_name">
								保留时效
							</label>
							<input id="limited" type="text" value="" class="check"
								disabled="disabled">
						</li>
						<li>
							<label class="label_name">
								状态
							</label>
							<input id="statusname" type="text" value="" class="check"
								disabled="disabled">
						</li>
						<li>
							<label class="label_name">
								预订人
							</label>
							<input id="orderusername" type="text" value="" class="check specolor"
								disabled="disabled">
						</li>
						<li>
							<label class="label_name">
								预定手机
							</label>
							<input id="mphone" type="text" value="" class="check"
								disabled="disabled">
						</li>
						<li>
							<label class="label_name">
								预定人会员ID
							</label>
							<input id="orderuser" type="text" value="" class="check"
								disabled="disabled">
						</li>
						<li>
							<label class="label_name">
								会员类型
							</label>
							<input id="rankname" type="text" value="" class="check"
								disabled="disabled">
						</li>
						<li>
							<label class="label_name">
								备注
							</label> 
							<input id="checkremark"  type="text" onchange="autosaveRemark()" class="check remark_text">
						</li>
					</ul>
				</form>
			</div>
			<div class="check_two">
					<div class="reset_price">
						<label for="" class="label_name">
							会员查询
						</label>
						<input type="text" name="" id="searchmembercondotion"  class="check reset_input" />
						<button class="search_one" onclick="searchmember()">
							查找
						</button>
						<button class="register_one" onclick="registermember()">
							注册会员
						</button>
						<span style="margin-right: 53px;" class="button_margin" onclick="showupdateMember()">修改客人</span>
						<span class="button_margin" onclick="deleteCustomer()">减少客人</span>
						<span class="button_margin" onclick="addCustomer()">增加客人</span>
						<span class="button_margin" onclick="sethostCustomer()">设置主客人</span>
					</div>
					<!--客人信息展示ul-->
					<ul class="check_ul clearfix" id="guest">
					</ul>
					<ul class="customer_info clearfix ">
						<li>
							<label class="label_name">
								姓名
							</label>
							<input id="username" type="text" value="" class="check"
								disabled="disabled">
						</li>
						<li>
							<label class="label_name">
								性别
							</label>
							<input id="gender" type="text" value="" class="check"
								disabled="disabled">
						</li>
						<!--
						 <li>
							<label class="label_name">
								抵店日期
							</label>
							<input name="离店日期" type="text" value="" class="check"
								disabled="disabled">
						</li>
						<li>
							<label class="label_name">
								离店日期
							</label>
							<input name="离店日期" type="text" value="" class="check"
								disabled="disabled">
						</li>
						-->
						<li>
							<label class="label_name">
								手机号码
							</label>
							<input id="mobile" type="text" value="" class="check"
								disabled="disabled">
						</li>
						<!--  
						<li>
							<label class="label_name">
								证件类型
							</label>
							<input name="预订人" type="text" value="" class="check"
								disabled="disabled">
						</li>
						-->
						<li>
							<label class="label_name">
								会员编码
							</label>
							<input id="memberid" type="text" value="" class="check"
								disabled="disabled">
						</li>
						<li>
							<label class="label_name">
								证件号码
							</label>
							<input id="idcard" type="text" value="" class="check  card_type"
								disabled="disabled">
						</li>
						<!--  
						<li>
							<label class="label_name">
								客单编码
							</label>
							<input name="预定人会员类型" type="text" value="" class="check"
								disabled="disabled">
						</li>
						-->
						<li>
							<label class="label_name">
								会员类型
							</label>
							<select class="check" disabled="disabled">
								<option value="0" id="memberrank">
									注册会员
								</option>
								<option value="1"></option>
							</select>
							<span role="button" class="button_margin reset_m" onclick="resetvip()">重置会员</span>
						</li>
						</br>
						<li class="address">
							<label class="label_name">
								住址
							</label>
							<textarea id="address" disabled="disabled"></textarea>
						</li>
						<li class="record">
							<label class="label_name">
								备注
							</label>
							<textarea  id="customerreamrk" disabled="disabled" class="te_height"></textarea>
						</li>
					</ul>
			<div class="check_three">
				<span class="conncroom fl">关联房</span>
				<span class="add_conroom fl" role="button" onclick="showroommap()">
					增加
				</span>
				<span class="add_conroom cancel fl" role="button" onclick="cancelroommap()">
					取消
				</span>
				<ul  class="fl">
					<li><i class="fa fa-star"></i><p id="hostroomid"></p><p>主关联房</p></li>
				</ul>
				<div class="captain">
					<ul class="main_ul clearfix fl" id="roommapping_data">
					</ul>
				</div>
			</div>
			<div class="check_four">
				<span class="button_margin button_color" onclick="updateCustomer()">确定</span>
				<span class="button_margin ">打印</span>
				<span class="button_margin" onclick="getAvailableRoom()">换房</span>
				<span class="button_margin" onclick="showgetContinue()">续住</span>
				<span class="button_margin" onclick="switchOrder()">转单</span>
			</div>
		</div>
		<script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/workbillroom/util.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script >
			var path = "<%=request.getContextPath() %>";
			var checkid = '<%=request.getParameter("checkid")%>';
			$(function(){
				laodCheckData();
				loadroommapping();
				$("#searchmembercondotion").focus();
			});
			
			function laodCheckData(){
				$.ajax({
			         url: path + "/loadCheckData.do",
					 type: "post",
					 data : {checkid : checkid},
					 success: function(json) {
					 	console.log(json)
					 	$("#roomtype").val(json[0]["ROOMNAME"]);
					 	$("#roomid,#hostroomid").val(json[0]["ROOMID"]);
					 	$("#hostroomid").html(json[0]["ROOMID"]);
					 	$("#checkintime").val(dealDate(json[0]["CHECKINTIME"]));
					 	$("#checkouttime").val(dealDate(json[0]["CHECKOUTTIME"]));
					 	$("#roomprice").val(json[0]["ROOMPRICE"]);
					 	$("#source").val(json[0]["DECODESOURCE"]);
					 	$("#orderuser").val(json[0]["ORDERUSER"]);
					 	$("#orderusername").val(json[0]["ORDERUSERNAME"]);
					 	$("#mphone").val(json[0]["MPHONE"]);
					 	$("#statusname").val(json[0]["STATUSNAME"]);
					 	$("#rankname").val(json[0]["RANKNAME"]);
					 	$("#guarantee").val(json[0]["DECODEGUARANTEE"]);
					 	$("#limited").val(json[0]["LIMITED"]);
					 	$("#orderid").val(json[0]["CHECKID"]);
					 	$("#checkremark").val(json[0]["REMARK"]);
					 	loadCheckuser(json[0]["CHECKUSER"],json[0]["CHECKUSERNAME"]);
					 },
					 error: function(json) {}
				});
			}
			
			function autosaveRemark(){
				var remark = $("#checkremark").val();
				if(remark){
					$.ajax({
				         url: path + "/autosaveRemark.do",
						 type: "post",
						 data : {
						 	checkid : checkid,
						 	remark : remark
						 },
						 success: function(json) {
						 	if(json.result == 0){
						 		showMsg(json.message);
						 	}
						 },
						 error: function(json) {}
					});
				}
			}
			
			function loadCheckuser(cumstomer,cumstomername){
				var msg = "";
				if(!cumstomername){
					msg = "入住人中有非会员!";
					showMsg(msg);
					return false;
				}
				var guest = cumstomer.split(",");
				var guestname = cumstomername.split(",");
				var childnode = "";
				$.each(guest, function(index){
					if(index == 0){
						childnode = childnode + "<li class='check_name active'><label style='display: none'>"+ guest[index] +"</label><span><i class='fa fa-star'></i><span>"+ guestname[index] +"</span></span></li>";
						loadCustomer(guest[index]);
					}else {
						childnode = childnode + "<li class='check_name'><label style='display: none'>"+ guest[index] +"</label><span>"+ guestname[index] +"</span></li>";
					}
				});
				$("#guest").html(childnode);
				bindcustomerclick();
			}
			
			function loadCustomer(memberid){
				$.ajax({
			         url: path + "/loadGuestData.do",
					 type: "post",
					 data : {memberid : memberid},
					 success: function(json) {
					 	$("#username").val(json["member"]["memberName"]);
					 	$("#gender").val((json["member"]["gendor"]==0?'女':'男'));
					 	$("#mobile").val(json["member"]["mobile"]);
					 	$("#idcard").val(json["member"]["idcard"]);
					 	$("#memberid").val(json["member"]["memberId"]);
					 	$("#memberrank").html(json["rankName"]);
					 	$("#address").val(json["member"]["address"]);
					 	$("#customerreamrk").val(json["member"]["reamrk"]);
					 },
					 error: function(json) {}
				});
			}
			
			
			
			
			
			/*增加客人*/

			function addCustomer(){
			
				if(isCheckout(checkid)){
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
			
			function bindcustomerclick(){
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
			
			function resetvip(){
				var orderuser = $("#orderuser").val();
				$.ajax({
			         url: path + "/resetvip.do",
					 type: "post",
					 data : {
					 	orderuser : orderuser,
					 	checkid : checkid
					 },
					 success: function(json) {
					 		//console.log($(window.document))
					 	if(json.result == 1){
					 		showMsg(json.message);
					 		laodCheckData();
					 	}else if(json.result == 0){
					 		showMsg(json.message);
					 	}
					 },
					 error: function(json) {}
				});
			}
			
			function searchmember(){
			
				if(isCheckout(checkid)){
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
			
			function resetCustomer(){
				$(".customer_info input").val("");
				$("#memberrank").html("");
				$("#address").val("");
			}
			
			function deleteCustomer(){
				if(isCheckout(checkid)){
					return false;
				}
				
				var oUl =$(".check_ul");
				var datamem = oUl.find("li");
				var a = oUl.find("li:not(:first-child)");
				var b = oUl.find(".active:not(:first-child)");
				var len = a.length;
				var datatlen = datamem.length;
				if(datatlen ==1 ){
					
				}else {
					b.prev().addClass("active");
					b.remove();
					//a[len-1].remove();
					//$(datamem[datatlen-2]).addClass("active");
					$(datamem[datatlen-2]).siblings().removeClass("active");
					loadCustomer($(datamem[datatlen-2]).find("label:hidden").html())
				}
			}
			
			function sethostCustomer(){
				/*$.each($(".check_ul li"),function(index){
					var li = $(".check_ul li")[index];
					cosnole.log($(li).find("i"))
					console.log($(li).find("i"))
				});*/
				
				if(isCheckout(checkid)){
					return false;
				}
				
				var msg="";
				var hostid = $($(".check_ul").find("i")[0]["parentNode"]["parentNode"]).find("label:hidden").html();
				var hostname = $($(".check_ul").find("i")[0]["nextSibling"]).html();
				var guestid = $(".check_ul").find(".active").find("label:hidden").html();
				var guestname = $(".check_ul").find(".active").find("span").html();
				if(hostid == guestid){
					msg = "当前已是主客人!"
					showMsg(msg);
				}else if(guestid == ''){
					msg = "无法设置!";
					showMsg(msg);
				}else {
					var tempid,tempname;
					tempid = hostid;
					tempname = hostname;
					$($(".check_ul").find("i")[0]["parentNode"]["parentNode"]).find("label:hidden").html(guestid);
					$($(".check_ul").find("i")[0]["nextSibling"]).html(guestname);
					$(".check_ul").find(".active").find("label:hidden").html(tempid);
					$(".check_ul").find(".active").find("span").html(tempname);
					loadCustomer(hostid);
				}
			}
			
			function switchOrder(branchid, orderid){
				//if($("#checkouttime"))
				
				if(isCheckout(checkid)){
					return false;
				}
				
				var arr1 = $("#checkouttime").val().split(" ");
				var arr2 = dealLocalDate(new Date()).split(" ");
				if(arr1[0] == arr2[0]){
					showtransferorder();
				}else {
					showMsg("无法转单!");
				}
			}
			
			function loadroommapping(){
				$.ajax({
			         url: path + "/loadroommapping.do",
					 type: "post",
					 data : {checkid : checkid},
					 success: function(json) {
					 	loadrooommapingdata(json);
					 },
					 error: function(json) {}
				});
			}
			
			function cancelroommap(){
				//console.log($("#roommapping_data .active").find("p")[0])
				
				if(isCheckout(checkid)){
					return false;
				}
				
				var delroomid = $($("#roommapping_data .active").find("p").get(0)).html();
				$.ajax({
			         url: path + "/cancelroommapping.do",
					 type: "post",
					 data : {
					 	checkid : checkid,
					 	delroomid : delroomid
					 },
					 success: function(json) {
					 	loadrooommapingdata(json.data);
					 	if(json.result == 0){
					 		showMsg(json.message);
					 	}
					 },
					 error: function(json) {}
				});
			}
			
			function updateCustomer(){
			
				if(isCheckout(checkid)){
					return false;
				}
				var strguest = "";
		 		$.each($(".check_ul li"),function(index){
		 			if($($(".check_ul li")[index]["childNodes"][0]).html() != ''){
		 				strguest = strguest + $($(".check_ul li")[index]["childNodes"][0]).html() + ",";
		 			}
		 		});
		 		strguest = strguest.substring(0,strguest.length-1);
				$.ajax({
			         url: path + "/updateCustomer.do",
					 type: "post",
					 data : {
					 	checkid : checkid,
					 	strguest : strguest
					 },
					 success: function(json) {
					 	if(json.result == 1){
					 		showMsg(json.message);
					 	}else if(json.result == 0){
					 		showMsg(json.message);
					 	}
					 },
					 error: function(json) {}
				});
			}
			
			function loadrooommapingdata(json){
				var data = '';
			 	$.each(json, function(index){
			 		data = data + "<li><p>" + json[index]["ROOMID"] + "</p>"+
			 				"<p>" + json[index]["MEMBERNAME"] + "</p></li>";
			 	});
			 	if(json.length == 0){
			 		$("#roommapping_data").html("");
			 	}else{
			 		$("#roommapping_data").html(data);			 	
			 	}
				$("#roommapping_data li").on("click",function() {
					$(this).addClass("active");
					$(this).siblings().removeClass("active");
				});
			}
			
			function showupdateMember(){
			
				if(isCheckout(checkid)){
					return false;
				}
				
				var guestid = $(".check_ul").find(".active").find("label:hidden").html();
				if(guestid)
					window.parent.JDialog.open("会员修改", path + "/showupdateMember.do?memberId=" + guestid , 600,420);
			}
			/*会员注册*/
			function registermember(){
			
				if(isCheckout(checkid)){
					return false;
				}
				
				window.parent.JDialog.open("会员注册", path + "/page/ipmspage/room_statistics/mebregister.jsp" , 500,220);
			}
			function getAvailableRoom(){
			
				if(isCheckout(checkid)){
					return false;
				}
				
				window.parent.JDialog.open("换房", path + "/showgetAvailableRoom.do?checkid=" + checkid , 1000, 650);
			}
			
			function showgetContinue(){
			
				if(isCheckout(checkid)){
					return false;
				}
				
				window.parent.JDialog.open("续住", path + "/showgetcontinue.do?checkid=" + checkid , 700, 400);
			}
			
			function showtransferorder(){
			
				if(isCheckout(checkid)){
					return false;
				}
				
				window.parent.JDialog.open("转单", path + "/showtransferorder.do?checkid=" + checkid , 1000, 650);
			}
			
			function showroommap(){
			
				if(isCheckout(checkid)){
					return false;
				}
				
				window.parent.JDialog.open("关联房", path + "/showroommap.do?checkid=" + checkid , 800, 514);
			}
		</script>
	</body>
</html>
