<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../common/script.jsp"%>
<!DOCTYPE HTML>
<html class="whitebg">
  <head>
    <title>会员注册</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/mebregister.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
    <link rel="stylesheet"  id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/datetimepicker.css" media="all" />
    <link href="//cdn.bootcss.com/font-awesome/4.6.3/css/font-awesome.min.css"  rel="stylesheet">	
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
	<style type="text/css">
		.register_one{
			padding: 53px 24px;
		}
	</style>
  </head>
  <body>
  <div class="register_margin">
     <form action="" method="post" id="omemberadd" name="omemberadd">
	      <section class="register_one">
	        <ul class="clearfix">
			  <li><label class="label_name">房价</label><input id="amount" name="amount" value="0.00"  class="input_ms" type="number"/></li>
			  <li>
			   	<input type="button" class="button_margin ms_re" onclick="resetprice()" value="调整">
			  </li>
			  </ul>
		   </section> 
		 </form>
   </div>
	<script src="<%=request.getContextPath()%>/script/common/jquery-ui.min.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/jquery.dialog.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/jquery.jqGrid.src.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/datepickerCN.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/workbillroom/util.js"></script>
	<script type="text/javascript">
		var path = "<%=request.getContextPath() %>";
		var checkid = "<%=request.getParameter("checkid")%>";
		$(function (){
			loadCheckData();
		})
	
		function loadCheckData(){
			$.ajax({
		         url: path + "/loadCheckData.do",
				 type: "post",
				 data : {checkid : checkid},
				 success: function(json) {
				 	//$("#amount").val(json[0]["ROOMPRICE"]);
				 },
				 error: function(json) {}
			});
		}
		
		function resetprice(){
			var amount = $("#amount").val();
			$.ajax({
		         url: path + "/resetprice.do",
				 type: "post",
				 data : {
				 	checkid : checkid,
				 	amount : amount
				 },
				 success: function(json) {
				 	if(json.result == 1){
				 		showMsg(json.message);
				 		setTimeout("refresh()", 2000);
				 	}else {
				 		showMsg(json.message);
				 	}
				 },
				 error: function(json) {}
			});
		}
		
		function refresh(){
	 		window.parent.JDialog.close();
		}
		
		
		
	</script>
  </body>
</html>
