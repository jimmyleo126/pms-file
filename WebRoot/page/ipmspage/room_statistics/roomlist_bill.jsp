<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../common/script.jsp"%>
<!DOCTYPE HTML>
<html>
  <head>
    
    <title>房单</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/order/bill.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/order/orderfont.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css" />
  </head>
  
  <body>
    <div class="bill_width">
			<div class="bill_one">
				<section class="box_content_widget fl">
					<div class="content">
						<table id="myTable" class="myTable" border="0">
							<thead>
								<tr>
									<th class="header"><input type="checkbox" onchange="updateallcheckbox(this)"/><span style="margin-left: 5px;">全选</span></th>
									<th class="header">交易号</th>
									<th class="header">项目</th>
									<th class="header">项目名称</th>
									<th class="header">消费</th>
									<th class="header">结算</th>
									<th class="header">描述</th>
									<th class="header">操作者</th>
									<th class="header">营业日</th>
									<th class="header">凭证号</th>
									<th class="header">备注</th>
								</tr>
							</thead>
							<tbody id="roomlist_bill_date">
							</tbody>
						</table>
					</div>
				</section>
			</div>
			<div class="bill_two">
				<span>关联房</span>
				<ul class="clearfix fl">
					<li class="owner">
						<p id="roomid"></p>
						<p>主客房</p>
						<p style="display: none" id="checkid" ></p>
					</li>
				</ul>
				<ul class="clearfix fl" id="roommapping_data">
				</ul>
			</div>
			<div class="bill_three">
				<!-- <span class="">所选消费<span class="">0.00</span></span>
				<span class="">订单号<span class="">2063993</span></span> -->
				<span class="">TTV<span class="money" id="TTV">0.00</span></span>
				<span class="">消费<span class="money" id="cost">0.00</span></span>
				<span class="">结算<span class="money" id="pay">0.00</span></span>
				<span class="">帐面余额<span class="money" id="accountfee">1.00</span></span>
				<span class="">平账余额<span class="money" id="totalfee">1.00</span></span>
			</div>
			<!--功能按钮区-->
			<div class="bill_four">
				<!-- <span class="button_margin" onclick="goods()">商品售卖</span>
				<span class="button_margin">会员升级</span> -->
				<span class="button_margin" onclick="resetprice()">房费调整</span>
				<span class="button_margin" onclick="loadallbill()">所有明细</span>
			</div>
			<div class="foot_submit">
				<span class="button_margin" onclick="showaddbill()">入账</span>
				<span class="button_margin" onclick="consumption()">冲减</span>
				<span class="button_margin"  onclick="transferbill()">转账</span>
				<span class="button_margin" onclick="autotransferbill()">自动转账</span>
				<span class="button_margin">发票</span>
				<span class="button_margin">打印</span>
				<span class="button_margin" onclick="checkout()">结账</span>
			</div>
			<!--入账-->
			<!--<div class="cd-popup3">
				<div class="enter_account">
					<h2>入账</h2>
					<i class="imooc" style="color:#3B3E40;">&#xe900;</i>
					<form action="" method="post">
						<div class="margintop">
							<ul class="clearfix">
								<input name="房单编号" type="hidden" class="" />
								<li><label class="label_name">房单号</label><input name="会员编号" type="text" value="" id="id" class="add_info"></li>
								<li><label class="label_name">项目</label><input name="会员编号" type="text" value="" id="id" class="add_info"></li>
								<li><label class="label_name">金额</label><input name="会员编号" type="text" value="" id="id" class="add_info"></li>
								<li><label class="label_name">凭证号</label><input name="会员编号" type="text" value="" id="id" class="add_info"></li>
								<li><label class="label_name">备注</label><textarea rows="10" cols="71"></textarea></li>
								<li><input type="submit" name="" id="" value="确定" class="button_margin button_margin_submit"/></li>
							</ul>
						</div>
					</form>
				</div>
			</div>-->
		 </div>
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/workbillroom/util.js"></script>
		<script type="text/javascript">
		var path = "<%=request.getContextPath() %>";
		var checkid = '<%=request.getParameter("checkid")%>';
			$(function(){
				loadbill();
				loadpayandcost();
				loadCheckData();
				loadroommapping();
				$(".bill_two ul:first li").addClass("active");
				//$(".bill_two ul[id==''] li").addClass("active");
			});
			
			function resetprice(){
				if(isCheckout(checkid)){
					return false;
				}
				
				window.parent.JDialog.open("房费调整", path + "/page/ipmspage/room_statistics/resetroomprice.jsp?checkid=" + checkid , 420,180);
			}
			
			function updateallcheckbox(e){
				var checkbox = document.getElementById("roomlist_bill_date");
				checkedbytruns(checkbox);
			}
			
			function refershbill(){
				loadbill();
				loadpayandcost();
			}
			
			function loadCheckData(){
				$.ajax({
			         url: path + "/loadCheckData.do",
					 type: "post",
					 data : {checkid : checkid},
					 success: function(json) {
					 	$("#roomid").html(json[0]["ROOMID"]);
					 	$("#checkid").html(checkid);
					 },
					 error: function(json) {}
				});
			}
			
			function loadpayandcost(){
				$.ajax({
			         url: path + "/loadpayandcost.do",
					 type: "post",
					 data : {checkid : checkid},
					 success: function(json) {
					 	console.log(json)
						 $("#TTV").html(json["TTV"].toFixed(2));
						 $("#cost").html(json["cost"].toFixed(2));
						 $("#pay").html(json["pay"].toFixed(2));
						 $("#accountfee").html((json["pay"] - json["cost"]).toFixed(2));
						 $("#totalfee").html((json["pay"] - json["cost"] - json["subprice"]).toFixed(2));
					 	
					 },
					 error: function(json) {}
				});
			}
			
			function loadroommapping(){
				console.log(checkid)
				$.ajax({
			         url: path + "/loadroommapping.do",
					 type: "post",
					 data : {checkid : checkid},
					 success: function(json) {
					     loadrooommapingdata(json);
						 $(".owner").on("click",function(){
							 $(this).addClass("active");
							 $(".bill_two .owner").not(this).removeClass("active");
							 checkid = $(this).find("p:hidden").html();
							 refershbill();
						});
					 },
					 error: function(json) {}
				});
			}
			
			function loadrooommapingdata(json){
				console.log(json)
				var data = "";
			 	$.each(json, function(index){
			 		data = data + "<li class='owner'><p>" + json[index]["ROOMID"] + "</p>" + 
			 				"<p>查看</p>" +
			 				"<p style='display: none;'>" + json[index]["CHECKID"] + "</p></li>"
			 	});
			 	if(json.length == 0){
			 		$("#roommapping_data").html("");
			 	}else{
			 		$("#roommapping_data").html(data);		 	
			 	}
			}
			
			function loadbill(){
				$.ajax({
			         url: path + "/loadRoomBillData.do",
					 type: "post",
					 data : {
					 	checkid : checkid,
					 	status : '1'
					 },
					 success: function(json) {
						 loadroombilldata(json.data);
					 },
					 error: function(json) {}
				});
			}
			
			function loadallbill(){
				$.ajax({
			         url: path + "/loadRoomBillData.do",
					 type: "post",
					 data : {
					 	checkid : checkid,
					 	status : ''
					 },
					 success: function(json) {
					 	if(json.result == 0){
					 		showMsg(json.message);
					 	}
						 loadroombilldata(json.data);
					 },
					 error: function(json) {}
				});
			}
			
			function showaddbill(){
			
				if(isCheckout(checkid)){
					return false;
				}
				
				window.parent.JDialog.open("入账", path + "/showgetAddBill.do?checkid=" + checkid , 700, 320);
			}
			
			function transferbill(){
			
				if(isCheckout(checkid)){
					return false;
				}
				
				$.ajax({
			         url: path + "/checkroommappingnull.do",
					 type: "post",
					 data : {checkid : checkid},
					 success: function(json) {
					 	if(json.result == 1){
							window.parent.JDialog.open("转账", path + "/showTransferBill.do?checkid=" + checkid , 700, 400);
					 	}else if(json.result == 0){
					 		showMsg(json.message);
					 	}
					 },
					 error: function(json) {}
				});
			}
			
			function checkroommappingnull(){
				
			}
			
			function autotransferbill(){
			
				if(isCheckout(checkid)){
					return false;
				}
				
				$.ajax({
			         url: path + "/autoautotransferbill.do",
					 type: "post",
					 data : {checkid : checkid},
					 success: function(json) {
					 console.log(json)
						 console.log(json.message);
						 showMsg(json.message);
					 	
					 },
					 error: function(json) {}
				});
			}
			
			function loadroombilldata(json){
				var tabledata;
			 	$.each(json, function(index){
			 		tabledata = tabledata + "<tr class='odd' " + (json[index]["STATUS"] == 1?"":"style='color: #999'") + " onclick='aa(this)'>" +
			 		"<td><input type='checkbox'></td>" +
			 		"<td>" + json[index]["BILLID"] + "</td>" +
			 		"<td>" + json[index]["PROJECTID"] + "</td>" +
			 		"<td>" + json[index]["PROJECTNAME"] + "</td>" +
			 		"<td>" + converttosomething(json[index]["COST"].toFixed(2), null, '') + "</td>" +
			 		"<td>" + converttosomething(json[index]["PAY"].toFixed(2), null, '') +"</td>" +
			 		"<td>" + json[index]["DESCRIBE"] + "</td>" +
			 		"<td>" + json[index]["STAFFNAME"] + "</td>" +
			 		"<td>" + dealDate(json[index]["RECORDTIME"]) + "</td>" +
			 		"<td>" + json[index]["PAYMENT"] + "</td>"+
			 		"<td>" + converttosomething(json[index]["REMARK"], null, '')  + "</td>" +
			 		"</tr>";
			 	});
			 	if(json.length == 0){
			 		$("#roomlist_bill_date").html("");
			 	}else{
			 		$("#roomlist_bill_date").html(tabledata);			 	
			 	}
			}
			function consumption(){
			
				if(isCheckout(checkid)){
					return false;
				}
				
				$("input[type='checkbox']").is(':checked');
				var arr = $("input[type='checkbox']");
				var strbillid = "";
				$.each(arr, function(index){
					if(arr[index]["checked"]){
						strbillid = strbillid + $(arr[index]["parentNode"]["parentNode"]["childNodes"][1]).html() + ",";
					}
				});
				strbillid = strbillid.substring(0,strbillid.length-1);
				if(strbillid == ''){
					showMsg("账单未勾选!");
					return false;
				}
				JDialog.open("", path + "/page/ipmspage/room_statistics/consumption.jsp?strbillid=" + strbillid,680,360);
			}
			
			function checkout(){
			
				if(isCheckout(checkid)){
					return false;
				}
				
				$.ajax({
			         url: path + "/checkroompayoff.do",
					 type: "post",
					 data : {checkid : checkid},
					 success: function(json) {
					 console.log(json)
					 	if(json.result == 1){
							JDialog.open("退房", path + "/page/ipmspage/room_statistics/checkout.jsp?checkid=" + checkid, 700, 620);
						}else {
							showMsg(json.message);
						}
					 	
					 },
					 error: function(json) {}
				});
			}

			function aa(e) {
				if( $(e["style"]).length == 0) checkedbytruns(e);
				//$(e).find(":checkbox").attr("checked", !$(e).find(":checkbox").is(":checked"));
			}
			
			function goods(){
			
				if(isCheckout(checkid)){
					return false;
				}
				
				window.parent.parent.JDialog.open("", path + "/goods.do",1280,738);
			}
		</script>
  </body>
</html>
