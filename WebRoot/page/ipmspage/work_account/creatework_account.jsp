<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../common/script.jsp"%>
<!DOCTYPE HTML>
<html>
  <head>
    
    <title>新增停售房</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/stopsell/stophouse_info.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/need/laydate.css"/>
	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/skins/molv/laydate.css"/>
	<link href="//cdn.bootcss.com/font-awesome/4.6.3/css/font-awesome.min.css"  rel="stylesheet">
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<style type="text/css">
		.clearfix li:nth-child(7){
		    float: right;
   			margin: 37px 63px;
		}
	</style>
  </head>
  
  <body>
   	   <div class="house_info">
			<form action="" method="post" id="work_bill_creat">
				<div class="">
					<ul class="clearfix">
						<!-- <li><label class="label_name">单号</label><input style="background-color: #f0f0f0" id="workbillId" name="workbillId" type="text" readonly class="adroom"></li> -->
						<li><label class="label_name">名称</label> <input id="name" name="name" type="text" value="" class="adroom" ></li>
						<li><label class="label_name">说明</label> <input id="describe" name="describe" type="text" value="" class="adroom"></li>
						<li><label class="label_name">创建日期</label> <input style="background-color: #f0f0f0" id="recordTime"  type="text" readonly class="adroom"></li>
						<li><label class="label_name">创建者</label> <input style="background-color: #f0f0f0" id="recordUser" name="recordUser" type="text" readonly class="adroom"></li>
						<li><label class="label_name">结账日期	</label><input id="finalTime"  type="text"  class="adroom" disabled></li>
						<li><label class="label_name">结账者</label> <input id="finalUser" name="finalUser" type="text" value="" class="adroom" disabled></li>
						<li><span><input  type="button" id="submitbill" value="确定" class="button_margin button_confirm" onclick="submitbill1()"/></span></li>
					</ul>
				</div>
			</form>
		</div>
		<script src="<%=request.getContextPath()%>/script/ipms/js/laydate.dev.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/workbillroom/util.js"></script>
		<script type="text/javascript">
			var path = "<%=request.getContextPath()%>";
			var workbillid = "<%=request.getParameter("workbillid")%>";
			$(function (){
				getcreatworkbillinfo();
			});
			
			function getcreatworkbillinfo(){
				$.ajax({
			         url: path + "/getcreatworkbillinfo.do",
					 type: "post",
					 data : {},
					 success: function(json) {
					 	console.log(json)
					 	$(".rno").html(json.workbill.workbillId);
					 	//$("#workbillId").val(json.workbill.workbillId);
					 	$("#recordTime").val(json.recordTime);
					 	$("#recordUser").val(json.staffName);
					 	$("#recordTimetemp").val(new Date());
					 },
					 error: function(json) {}
				});
			}
			
			function submitbill1(){
				console.log($("#work_bill_creat").serialize())
				console.log()
				//window.parent.JDialog.close();
				//window.showMsg("sdfsdf");
				$.ajax({
			         url: path + "/creatworkbill.do",
					 type: "post",
					 data : $("#work_bill_creat").serialize(),
					 success: function(json) {
					 	if(json.result == 1){
					 		//showMsg(json.message);
					 		$(window.parent.document).find("#menu_905").click();
					 		window.parent.JDialog.close();
					 	}else if(json.result == 0){
					 		showMsg(json.message);
					 	}
					 },
					 error: function(json) {}
				});
				}
		</script>	
  </body>
</html>
