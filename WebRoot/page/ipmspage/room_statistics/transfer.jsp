<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../common/script.jsp"%>
<!DOCTYPE HTML>
<html>
  <head>
    <title>转账</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/customer.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
	
	<script>var base_path = "<%=request.getContextPath()%>";</script>
  </head>
  
  <body>
   		<!--转账-->
			<section class="transfer">
				<div class="high_header">
					<h4>
						请选择目标账户(双击):
					</h4>
					<div class="margintop">
						<label>转账金额:</label><input type="number" class="text_edit" value="" id="amount" min="0"/>
						<ul class="clearfix" id="ul_data">
						</ul>
					</div>
				</div>
			</section>
		<!--转账结束-->
		 <script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/workbillroom/util.js"></script>
		<!--<script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/customer/customer.js"></script> -->
		<script>
		document.addEventListener('touchstart',function(e){
			document.getElementById('focus').focus();
		});
			var path = "<%=request.getContextPath()%>";
			var checkid = "<%=request.getParameter("checkid")%>";
			$(function(){
				$.ajax({
			         url: path + "/loadCustomerData.do",
					 type: "post",
					 data : {checkid : checkid, checkusername : ''},
					 success: function(json) {
						 loadCustomerData(json);
					 },
					 error: function(json) {}
				});
			});
			function loadCustomerData(json){
				 var tabledata = '';
			 	$.each(json, function(index){
			 		tabledata = tabledata + "<li ondblclick='transferbill(this)'>" +
			 		"<p>" + json[index]["ROOMID"] + "</p>" +
			 		"<p>" + json[index]["FIRSTCHECKUSERNAME"] + "</p>" +
			 		"<p style='display: none'>" + json[index]["CHECKID"] + "</p>" +
			 		"</li>";
			 	});
			 	if(json.length == 0){
			 		$("#ul_data").html("");
			 	}else{
			 		$("#ul_data").html(tabledata);			 	
			 	}
			}
			function transferbill(e){
				var targetcheckid = $($(e)[0].children[2]).html();
				if(isNull($("#amount"))){
					return false;
				}
				var amount = $("#amount").val();
				$.ajax({
			         url: path + "/transferbill.do",
					 type: "post",
					 data : {
					 	checkid : checkid,
					 	targetcheckid : targetcheckid,
					 	amount : amount
					 	},
					 success: function(json) {
					 	 showMsg(json.message);
					 	 setTimeout("refreshpage()", 2000);
					 },
					 error: function(json) {}
				});
			}
			function refreshpage(){
				 $(window.parent.document).find(".tab_two").click();
				 window.parent.JDialog.close();
			}       
		</script>
  </body>
</html>
