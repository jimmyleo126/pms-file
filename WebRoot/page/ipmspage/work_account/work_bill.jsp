<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../common/script.jsp"%>
<!DOCTYPE HTML>
<html>
  <head>
    
    <title>工作帐</title>
    
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
									<th class="header">选择</th>
									<th class="header" style="width:10%;">交易号</th>
									<th class="header">项目</th>
									<th class="header">项目名称</th>
									<th class="header">消费</th>
									<th class="header">结算</th>
									<th class="header">时间</th>
									<th class="header">操作者</th>
									<th class="header">凭证号</th>
									<th class="header" style="width:14%;">备注</th>
								</tr>
							</thead>
							<tbody id="workbill_data">
							</tbody>
						</table>
					</div>
				</section>
			</div>
			<div class="bill_three">
				<span class="">消费<span id="cost">0.00</span></span>
				<span class="">结算<span id="pay">0.00</span></span>
				<span class="">帐面余额<span id="accountfee">1.00</span></span>
			</div>
			<div class="bill_four">
				<span class="button_margin" onclick="loadallworkbill()">所有明细</span>
			</div>
			<div class="foot_submit">
				<span class="button_margin" onclick="showaddbill()">入账</span>
				<span class="button_margin" onclick="consumption()">冲减</span>
				<span class="button_margin">发票</span>
				<span class="button_margin">打印</span>
				<span class="button_margin" onclick="checkout()">结账</span>
			</div>
		</div>
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script src="<%=request.getContextPath()%>/script/ipms/js/workbillroom/util.js"></script>
		<script type="text/javascript">
		var path = "<%=request.getContextPath() %>";
		var workbillid = '<%=request.getParameter("workbillid")%>';
			$(function(){
				console.log(workbillid)
				$.ajax({
			         url: path + "/loadWorkBillData.do",
					 type: "post",
					 data : {
					 	workbillid : workbillid,
					 	status : "1"
					 },
					 success: function(json) {
						 loadworkbilldata(json);
					 },
					 error: function(json) {}
				});
				
				$.ajax({
			         url: path + "/loadpayandcostworkbill.do",
					 type: "post",
					 data : {workbillid : workbillid},
					 success: function(json) {
						 $("#cost").html(json["cost"]);
						 $("#pay").html(json["pay"]);
						 $("#accountfee").html((json["pay"] - json["cost"]).toFixed(2));
					 	
					 },
					 error: function(json) {}
				});
				
			});
			
			function showaddbill(){
				window.parent.JDialog.open("入账", path + "/showgetAddWorkBill.do?workbillid=" + workbillid , 700, 400);
			}
			
			function loadallworkbill(){
				$.ajax({
			         url: path + "/loadWorkBillData.do",
					 type: "post",
					 data : {
					 	workbillid : workbillid,
					 	status : ''
					 },
					 success: function(json) {
					 	if(json.result == 0){
					 		showMsg(json.message);
					 	}
						 loadworkbilldata(json);
					 },
					 error: function(json) {}
				});
			}
			
			function loadworkbilldata(json){
			console.log(json)
				 var tabledata;
			 	$.each(json, function(index){
			 		tabledata = tabledata + "<tr class='odd' " + (json[index]["STATUS"] == 1?"":"style='color: #999'") + " onclick='aa(this)'>" +
			 		"<td><input type='checkbox'></td>" +
			 		"<td>" + json[index]["DETAILID"] + "</td>" +
			 		"<td>" + json[index]["PROJECTID"] + "</td>" +
			 		"<td>" + json[index]["PROJECTNAME"] + "</td>" +
			 		"<td>" + converttosomething(json[index]["COST"], null, '') + "</td>" +
			 		"<td>" + converttosomething(json[index]["PAY"], null, '') +"</td>" +
			 		"<td>" + dealDate(json[index]["RECORDTIME"]) + "</td>" +
			 		"<td>" + json[index]["STAFFNAME"] + "</td>" +
			 		"<td>" + converttosomething(json[index]["VOUCHER"], null, '') + "</td>"+
			 		"<td>" + converttosomething(json[index]["REMARK"], null, '')  + "</td>" +
			 		"</tr>";
			 	});
			 	if(json.length == 0){
			 		$("#workbill_data").html("");
			 	}else{
			 		$("#workbill_data").html(tabledata);			 	
			 	}
			}
			
			function consumption(){
				$("input[type='checkbox']").is(':checked');
				var arr = $("input[type='checkbox']");
				var strdetailid = "";
				$.each(arr, function(index){
					if(arr[index]["checked"]){
						strdetailid = strdetailid + $(arr[index]["parentNode"]["parentNode"]["childNodes"][1]).html() + ",";
					}
				});
				strdetailid = strdetailid.substring(0,strdetailid.length-1);
				if(strdetailid == ''){
					showMsg("账单未勾选!");
					return false;
				}
				JDialog.open("", path + "/page/ipmspage/work_account/consumptionworkbill.jsp?strdetailid=" + strdetailid,680,360);
			}
			
			function checkout(){
				$.ajax({
			         url: path + "/checkoutworkbill.do",
					 type: "post",
					 data : {workbillid : workbillid},
					 success: function(json) {
						 console.log(json.message);
						 if(json.result == 1){
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
				window.parent.JDialog.open("", path + "/goods.do",1280,738);
			}
		</script>
  </body>
</html>
