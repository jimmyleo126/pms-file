<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/script.jsp"%>
<!DOCTYPE HTML>
<html>
  <head>
  
    <title>退房</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/roomlist_check.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
	<link href="//cdn.bootcss.com/font-awesome/4.6.3/css/font-awesome.min.css"  rel="stylesheet">
  </head>
  <style>
  	#jd_iframe{
  		width:700px !important;
  	}
  	.newdatail_six ul li:nth-child(7){
  		position:relative !important;
  		width: auto;
    	top: auto;
    	z-index: 0;
  	}
  	#projectid{
  	    margin-left: 5px;
  	}
  	#remark{
  		line-height: 4;
  	}
  	.line{
  		height:1px;
  		width:100%;
  		border:1px solid #ccc;
  	}
  </style>
  <body>
   <!--入账开始 -->
		<section class="detail_six newdatail_six">
			<div class="high_header">
				<!--  <i class="imooc detail_imooc" style="color:#3B3E40;"></i>	-->
					<div class="margintop">
						<form id="bill_date">
							<ul class="clearfix">
						      <li><label class="label_name">房单号：</label><input name="checkId" type="text"  id="checkId" class="text_edit" readonly></li>
						      <li><label class="label_name">日租：</label><input id="roomprice" name="roomprice" type="text" class="text_number" readonly></li>
						      <li><label class="label_name">账面金额：</label><input id="accountfee" name="accountfee" type="text" class="text_number" readonly></li>
						      <li><label class="label_name">平账金额：</label><input id="totalfee" name="totalfee" type="text" class="text_number" readonly></li>
						      <hr style="height:1px;border:0px;background-color:#D5D5D5;color:#D5D5D5;width:90%;"/>
						      <li style="position:relative;">
							      <label class="label_name">支付方式：</label>
									<select name="projectid" id="projectid" class="text_search">
										<option value="">--请选支付方式--</option>
										<option value="2001">现金支出</option>
										<option value="2002">现金收取</option>
										<option value="2003">银行卡</option>
									</select>
						      <!-- 
							      <input id="project" name="project" type="text" value="" class="text_edit margin_text" onclick="onbill()">
							      <div id="ontime" class="ontime fadeIn"></div> -->
						      </li>
						      <li><label class="label_name">金额：</label><input id="amount" name="amount" type="number" min=0 value="" class="text_number" ></li>
						      <li><label class="label_name">备注：</label> <textarea id="remark" name="remark" onchange="autosaveRemark()" ></textarea></li>
						      <li onclick="checkout()"><span role="button" class="button confirm" >确定</span></li>
			       		   </ul>
			       		   <!-- <input type="hidden" id="projectid" name="projectid"> -->
			       		   <input type="hidden" id="subprice" name="subprice">
		       		   </form>
	    		 </div>
			</div>
		</section>
		<script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/workbillroom/util.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script type="text/javascript">
			var path = "<%=request.getContextPath() %>";
			var checkid = '<%=request.getParameter("checkid")%>';
			console.log(checkid)
			$(function(){
				//$($(".clearfix li")[5]).css("display", "block");
				//$($(".clearfix li")[5]).removeClass("fadeOutUp");
				// $($(".clearfix li")[5]).addClass("fadeOutUp").fadeOut(1000);
				loadCheckData();
				loadpayandcost();
				$("#checkId").val(checkid);
			});
			
			
			function loadCheckData(){
				$.ajax({
			         url: path + "/loadCheckData.do",
					 type: "post",
					 data : {checkid : checkid},
					 success: function(json) {
					 	console.log(json)
					 	$("#roomprice").val(json[0]["ROOMPRICE"]);
					 	$("#remark").val(json[0]["REMARK"]);
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
						// $("#TTV").html(json["TTV"].toFixed(2));
						// $("#cost").html(json["cost"].toFixed(2));
						// $("#pay").html(json["pay"].toFixed(2));
						 $("#accountfee").val((json["pay"] - json["cost"]).toFixed(2));
						 $("#totalfee").val((json["pay"] - json["cost"] - json["subprice"]).toFixed(2));
						 $("#amount").val(  Math.abs((json["pay"] - json["cost"] - json["subprice"])).toFixed(2)  );
						 $("#subprice").val(json["subprice"]);
						 if(json["pay"] == (json["cost"] + json["subprice"])){
						 	$("#amount,#project,#reamrk").attr("disabled", "disabled");
						// $($(".clearfix li")[4]).addClass("fadeOutUp").fadeOut(1000);
						 	//$($(".clearfix li")[4]).css("display", "block");
						 	//$($(".clearfix li")[5]).css("display", "none");
							//$($(".clearfix li")[5]).css("display", "block");
						 }
						 if(json["pay"] - json["cost"] - json["subprice"] > 0){
						 	$("#projectid").val("2001");
						 }else {
						 	$("#projectid").val("2002");
						 }
					 	
					 },
					 error: function(json) {}
				});
			}
			
			$(".margin_text").on("click",function(){
				$(".ontime").css("display","block");
			})
			
			function autosaveRemark(){
				var remark = $("#remark").val();
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
			
			function checkout(){
				$.ajax({
			         url: path + "/checkoutbill.do",
					 type: "post",
					 data : $("#bill_date").serialize(),
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
			
			function onbill(){
				document.getElementById("ontime").innerHTML='<iframe src="' + path +'/page/ipmspage/common_addbill/commbill.jsp" width="100%" height="100%" frameborder=no  scrolling="no"></iframe>'
			}
		</script>
  </body>
</html>
