<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../common/script.jsp"%>
<!DOCTYPE HTML>
<html>
  <head>
    
    <title>创建工作帐</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/work_account/work_account_check.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css" />
	<link href="//cdn.bootcss.com/font-awesome/4.6.3/css/font-awesome.min.css"  rel="stylesheet">
  </head>
  
  <body>
   	   <div class="check_wrap_margin check_color newworkcount">
			<div class="top_check">
				<div class="left_check fl">
				<span class="top_icon"><i class="imooc im_top" onclick="window.parent.JDialog.close();">&#xea0f;</i></span>
				<span class="cn">单号 <span class="rno">916</span></span>
				</div>
				<div class="right_check fl">
					<ul>
						<li class="tab_one tab_select"><i class="fa fa-wpforms"></i><span>工作帐</span></li>
					</ul>
				</div>
			</div>
			<div class="content_color">
				<div class="ifa" id="f">
					<div class="check_one fl">
						<form id="work_bill_creat" action="" method="post">
							<ul class="clearfix">
								<li><label class="label_name">单号</label><input style="background-color: #f0f0f0" id="workbillId" name="workbillId" type="text" readonly class="check"></li>
								<li><label class="label_name">名称</label> <input id="name" name="name" type="text" value="" class="check" ></li>
								<li><label class="label_name">说明</label> <input id="describe" name="describe" type="text" value="" class="check"></li>
								<li><label class="label_name">创建日期</label> <input style="background-color: #f0f0f0" id="recordTime"  type="text" readonly class="check"></li>
								<li><label class="label_name">创建者</label> <input style="background-color: #f0f0f0" id="recordUser" name="recordUser" type="text" readonly class="check"></li>
								<li><label class="label_name">结账日期	</label><input id="finalTime"  type="text"  class="check" disabled></li>
								<li><label class="label_name">结账者</label> <input id="finalUser" name="finalUser" type="text" value="" class="check" disabled></li>
								<li><span><input style="margin-right: -106px;" type="button" id="submitbill" value="确定" class="button_margin confirm" onclick="submitbill1()"/></span></li>
							</ul>
							<input id="recordTimetemp" name="recordTime" type="hidden" >
						</form>
					</div>
				</div>
				<div id="a" style="display:none;height:626px;"></div>
				<div id="d" style="display: none;height:626px;"></div>

			</div>
		</div>
		<script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/workbillroom/util.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
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
					 	$("#workbillId").val(json.workbill.workbillId);
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
