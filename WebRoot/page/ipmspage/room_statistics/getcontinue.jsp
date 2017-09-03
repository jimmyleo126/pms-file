<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/script.jsp"%>
<%@ include file="../../common/taglib.jsp"%>
<!DOCTYPE HTML>
<html>
  <head>
    
    <title>续住</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/customer.css" />
	<link rel="stylesheet" id="style" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/roomlist_check.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/newtipInfo.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css"/>
	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/need/laydate.css"/>
	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/skins/molv/laydate.css"/>
	<link href="//cdn.bootcss.com/font-awesome/4.6.3/css/font-awesome.min.css"  rel="stylesheet">	
  </head>
  
  <body>
   <div class="get_color">
		<form id="getcontinue_bill" class="getcontinue_bill">
			<section class="detail_one">
				<ul class="clearfix">
					<li><label class="label_name">当前离店：</label><input id="cuurent_checkouttime" type="text" name="cuurent_checkouttime" value = "${ checkoutTime }" class="text_edit" readonly/></li>
					<li>
						<label class="label_name">续住日期：</label> <input id="getcontinuetime" name="getcontinuetime" type="text" style="width: 70%" value="" class="text_edit wdate" onblur="plust()" readonly>
						<span style="border: 1px solid #DDDDDD;padding: 5px;" onclick="plustime('A')">中午</span><span style="border: 1px solid #DDDDDD;padding: 5px;" onclick="plustime('P')">下午</span>
					</li>
					<li>
						<label class="label_name">项目：</label><input id="project" name="project" type="text" value="" class="text_edit margin_text" onclick="oncontinue()" onchange="hideontime()">
						<div id="ontime" class="ontime oncontinue fadeIn"></div>
					</li>
					<li><label class="label_name">金额：</label> <input id="amount" name="amount" type="number" value="" class="text_edit" ></li>
					<li><label class="label_name">凭证号：</label> <input name="预定人会员类型" type="text" value="" class="text_edit" ></li>
					<li>
						<input type="button" id="submitbill" value="取消" onclick="closeWin()" class="button_margin cancel"/>
						<input type="button" id="submitbill" value="确认" onclick="submitgetcontinuebill()" class="button_margin confirm"/>
					</li>
				</ul>
			</section>
		 	<input type="hidden" name="checkId" value="${ checkid }">
		</form>
	</div>
	<script src="<%=request.getContextPath()%>/script/ipms/js/laydate.dev.js" charset="utf-8"></script>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
  	<script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/workbillroom/util.js"></script>
  	<script type="text/javascript">
	  	laydate({
	    	elem: '#getcontinuetime'
		});
  		var path = '<%=request.getContextPath()%>';
  		$(function(){
  			console.log(${Check.checkoutTime})
  			//$(".wdate").datetimepicker({ datetimeFormat: "yy/mm/dd HH:mm:ss" })
  		});
  		function closeWin() {
		  window.parent.JDialog.close();
		}
		function plust(){
			console.log($("#getcontinuetime").val())
		}
		
		function plustime(e){
			debugger
			var checkoutval = $("#getcontinuetime").val();
			if(!checkoutval){
				checkoutval = dealLocalDate(new Date()).split(" ")[0];
			}
			if(e == 'A'){
				$("#getcontinuetime").val(checkoutval.split(" ")[0] + " 12:00");
			}else if(e == 'P'){
				$("#getcontinuetime").val(checkoutval.split(" ")[0] + " 18:00");
			}
		}

  		function submitgetcontinuebill(){
			if(isNull($("#getcontinuetime"))){
				return false;
			}
			/*if(isNull($("#project"))){
				return false;
			}
			if(isNull($("#amount"))){
				return false;
			}*/
			$.ajax({
		         url: path + "/submitgetcontinuebill.do",
				 type: "post",
				 data : $("#getcontinue_bill").serialize(),
				 success: function(json) {
						 showMsg(json.message);
						 setTimeout("refreshpage()", 2000);
				 },	
				 error: function(json) {}
			});
  		}
  		$(".margin_text").on("click",function(){
			$(".ontime").css("display","block");
		})		
		
  		/*续住-->项目*/
  		function oncontinue(){
			document.getElementById("ontime").innerHTML='<iframe src="' + path +'/page/ipmspage/common_addbill/commbill.jsp" width="100%" height="100%" frameborder=no  scrolling="no"></iframe>'
		}
		
		function hideontime(){
			$("#ontime").css("display","none");
		}
		
		function refreshpage(){
			 $(window.parent.document).find(".tab_one").click();
			 window.parent.JDialog.close();
		}
		
  	</script>
  </body>
</html>
