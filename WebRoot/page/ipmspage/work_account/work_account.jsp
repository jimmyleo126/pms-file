<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ include file="../../common/script.jsp"%>
<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>工作帐</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlistfont.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/work_account/work_account.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
	
  </head>
  
  <body>
   <div class="work_margin">
    <div class="header_roomlist">
		<ul>
			<li class="header_roomlist_li active" onclick="queryworkbillbystatus(1)">
				<span>未结工作帐</span>
			</li>
			<li class="header_roomlist_li" onclick="queryworkbillbystatus(2)">
				<span>已结工作帐</span>
			</li>
			<li class="header_roomlist_li" onclick="queryworkbillbytime()">
				<span>今日创建工作帐</span>
			</li>
			<li class="header_roomlist_li" onclick="queryworkbillbytimestatus(2)">
				<span>今日已结工作帐</span>
			</li>
			<li class="header_roomlist_li" onclick="queryworkbillbyuser()">
				<span>我创建的工作帐</span>
			</li>
			<li class="header_roomlist_li" onclick="queryworkbillbyuserstatus(2)">
				<span>我结的工作帐</span>
			</li>
		</ul>
		<div class="fr">
			<span class="highsearch">高级查询</span>
			<span class="createaccount">创建工作帐</span>
		</div>
		<div class="high_header fadeIndown">
			<h3>高级查询</h3>
			<i class="imooc" style="color:#fff;">&#xe900;</i>
			<form id="workbill_search" action="" method="post">
				<div class="margintop">
					<ul class="clearfix">
						<input name="会员编号" type="hidden" class="" />
						<li><label class="label_name">账单号</label><input name="workbillid" type="text" value="" class="text_search"></li>
						<li><label class="label_name">名称</label><input name="name" type="text" value="" class="text_search"></li>
						<li>
							<input name="cancel" type="button" value="确定" class="button_margin confirm" onclick="querymultiworkbill()">
							<!-- <input name="cancel" type="submit" value="取消" class="button_margin cancel">  -->
						</li>
					</ul>
					<input type="hidden" id="status" name="status">
					<input type="hidden" id="currentdate" name="currentdate">
					<input type="hidden" id="recorduser" name="recorduser">
				</div>
			</form>
		</div>
	</div>
	<section style="width: 100%;" class="box-content-section fl">
		<section class="box_content_widget fl">
			<div class="content" style="padding:10px;height:929px;">
				<iframe name="logFrame" id="logFrame" frameborder="0" width="100%" height="100%" src="<%=request.getContextPath()%>/showworkaccountdetail.do"></iframe>
				<!-- <table id="myTable" class="myTable" border="0" width="100">
					<thead>
						<tr>
							<th class="header">账单号</th>
							<th class="header">名称</th>
							<th class="header">状态</th>
							<th class="header">创建时间</th>
							<th class="header">结账者</th>
							<th class="header">结账时间</th>
							<th class="header">消费</th>
							<th class="header">结算</th>
							<th class="header">说明</th>
						</tr>
					</thead>
					<tbody id="workbill_date"> 
						
					</tbody>
				</table> -->
			</div>
		</section>
	</section>
	</div>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/ipms/js/workbillroom/util.js"></script>
	<script type="text/javascript">
		var path = "<%=request.getContextPath()%>";
		$(function (){
			queryworkbillbystatus(1);
		});
		
		function queryworkbillbystatus(status){
			$("#status").val(status);
			$("#currentdate").val("");
			$("#recorduser").val("");
			/*$.ajax({
		         url: path + "/loadworkbill.do",
				 type: "post",
				 data : {status : status},
				 success: function(json) {
				 	loadcheckdata(json);
				 },
				 error: function(json) {}
			});*/
			$("#logFrame").attr("src","<%=request.getContextPath()%>/showworkaccountdetail.do?" + $("#workbill_search").serialize() );
		}
		
		function queryworkbillbytime(){
			var arr = dealLocalDate(new Date()).split(" ")[0];
			$("#status").val("");
			$("#currentdate").val(arr[0]);
			$("#recorduser").val("");
			/*$.ajax({
		         url: path + "/loadworkbill.do",
				 type: "post",
				 data : {currentdate : arr[0]},
				 success: function(json) {
				 	loadcheckdata(json);
				 },
				 error: function(json) {}
			});
			*/
			$("#logFrame").attr("src","<%=request.getContextPath()%>/showworkaccountdetail.do?" + $("#workbill_search").serialize() );
		}
		
		$(".imooc").on("click", function() {
			$(".high_header").addClass("fadeOutUp").fadeOut(100);
		})
		
		function queryworkbillbytimestatus(status){
			var arr = dealLocalDate(new Date()).split(" ")[0];
			$("#status").val(status);
			$("#currentdate").val(arr[0]);
			$("#recorduser").val("");
			/*$.ajax({
		         url: path + "/loadworkbill.do",
				 type: "post",
				 data : {
				 	currentdate : arr[0],
				 	status : status
				 },
				 success: function(json) {
				 	loadcheckdata(json);
				 },
				 error: function(json) {}
			});*/
			$("#logFrame").attr("src","<%=request.getContextPath()%>/showworkaccountdetail.do?" + $("#workbill_search").serialize() );
		}
		
		$(".highsearch").on("click", function() {
			$(".high_header").css("display", "block");
			$(".high_header").removeClass("fadeOutUp");
		})
		$(".header_roomlist_li").on("click",function(){
			$(this).addClass("active");
			$(this).siblings().removeClass("active");
		})
		
		function queryworkbillbyuser(){
			$("#status").val("");
			$("#currentdate").val("");
			$("#recorduser").val("self");
			/*$.ajax({
		         url: path + "/loadworkbill.do",
				 type: "post",
				 data : {
				 	recorduser : "self"
				 },
				 success: function(json) {
				 	loadcheckdata(json);
				 },
				 error: function(json) {}
			});*/
			$("#logFrame").attr("src","<%=request.getContextPath()%>/showworkaccountdetail.do?" + $("#workbill_search").serialize() );
		}
		
		function queryworkbillbyuserstatus(status){
			$("#status").val(status);
			$("#currentdate").val("");
			$("#recorduser").val("self");
			/*$.ajax({
		         url: path + "/loadworkbill.do",
				 type: "post",
				 data : {
				 	recorduser : "self",
				 	status : status
				 },
				 success: function(json) {
				 	loadcheckdata(json);
				 },
				 error: function(json) {}
			});*/
			$("#logFrame").attr("src","<%=request.getContextPath()%>/showworkaccountdetail.do?" + $("#workbill_search").serialize() );
		}
		
		function querymultiworkbill(){
			
			/*$.ajax({
		         url: path + "/loadworkbill.do",
				 type: "post",
				 data : $("#workbill_search").serialize(),
				 success: function(json) {
				 	loadcheckdata(json);
				 },
				 error: function(json) {}
			});*/
			$("#logFrame").attr("src","<%=request.getContextPath()%>/showworkaccountdetail.do?" + $("#workbill_search").serialize() );
		}
		
		function loadcheckdata(json){
			 var tabledata;
			 
		 	$.each(json, function(index){
		 		tabledata = tabledata + "<tr class='odd' onclick='aa()' ondblclick='bb(this)'>" +
		 		"<td>" + json[index]["WORKBILLID"] + "</td>" +
		 		"<td>" + json[index]["NAME"] + "</td>" +
		 		"<td>" + json[index]["DECODESTATUS"] + "</td>" +
		 		"<td>" + dealDate(json[index]["RECORDTIME"]) + "</td>" +
		 		"<td>" + converttosomething(json[index]["FINALUSERNAME"], null, "") + "</td>" +
		 		"<td>" + dealDate(json[index]["FINALTIME"]) + "</td>" +
		 		"<td>" + converttosomething(json[index]["COST"], null, 0) + "</td>" +
		 		"<td>" + converttosomething(json[index]["PAY"], null, 0) + "</td>" +
		 		"<td>" + json[index]["DESCRIBE"] + "</td>" + 
		 		"</tr>";
		 	});
		 	if(json.length == 0){
		 		$("#workbill_date").html("");
		 	}else{
		 		$("#workbill_date").html(tabledata);			 	
		 	}
		}
	
		$(".confirm").on("click", function() {
			$(".high_header").addClass("fadeOutUp").fadeOut(100);
			//$(".high_header").css("display", "none");
		})
		var timer = null;
		function aa() {
			clearTimeout(timer);
			if (event.detail == 2)
				return;
			timer = setTimeout(function() {
				console.log('单击');
			}, 300);
		}
		function bb(e) {
			clearTimeout(timer);
			var workbillid = $(e.children[0]).html();
			window.parent.parent.JDialog.openWithNoTitle("", path + "/page/ipmspage/work_account/work_account_check.jsp?workbillid=" + workbillid,1180,738);
			//window.location.href="<%=request.getContextPath()%>/page/ipmspage/work_account/work_account_check.jsp";
		}
		$(".createaccount").on("click",function(){
			window.parent.parent.JDialog.open("创建工作账", path + "/page/ipmspage/work_account/creatework_account.jsp" ,820,238);
		});
	</script>
  </body>
</html>
