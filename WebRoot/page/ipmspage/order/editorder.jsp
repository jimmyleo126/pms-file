<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/script.jsp"%>
<!DOCTYPE HTML>
<html>
  <head>
    <title>修改订单</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" id="style" type="text/css"
		href="<%=request.getContextPath()%>/css/ipms/css/order/order_details.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css" />	
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>	
	<link rel="stylesheet" id="style" type="text/css"
		href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/select/jquery.mCustomScrollbar.min.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/select/select.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css"/>
	<script src="<%=request.getContextPath()%>/script/common/jquery-ui.min.js"></script>
		
	<script>var base_path = "<%=request.getContextPath()%>";</script>	
	<link href="//cdn.bootcss.com/font-awesome/4.6.3/css/font-awesome.min.css"  rel="stylesheet">
  </head>
  <style>
  	#jd_iframe{
  		width:700px !important;
  	}
  </style>
  <body>
  <form action ="" name="myForm" id="myForm">
		<section class="detail_six">
			<div class="high_header">
				<div class="margintop">
					<ul class="clearfix">
					  <input name="会员编号" type="hidden"  class=""/>
				      <li><label class="label_name">订单号：</label><input name="orderId" type="text" value="<%=request.getParameter("orderId")%>" id="orderId" class="text_edit" readonly="readonly"></li>
				      <li><label class="label_name">预订人：</label><input name="orderUser" type="text" value="" id="orderuser" class="text_edit" disabled></li>
				      <li><label class="label_name">预订人手机：</label> <input name="mphone" type="text" value="" id="mphone" class="text_edit" disabled></li>
				      <li>
					      <label class="label_name">房间类型：</label>
					      <select class="select_edit mySelect" id="roomType" name="roomType">
					      </select>
					      <input name="roomPrice" type="text" id="roomPrice" class="text_number" readonly="readonly"/>
				      </li>
				      <li>
				      		<label class="label_name">担保类型：</label>
				      		<select class="select_edit" name="guarantee" id="guarantee">
						      	<option value="1">无</option>
						    	<option value="2">有</option>
					        </select>
				      </li>
				      <li class="holdtime">
				      <label class="label_name">保留时效：</label>
						   <input class="select_edit" id="limited" name="limited"/>
				      </li>
				      <li class="li_mark"><label class="label_name">备注：</label> <textarea name="remark" id="remark" col="100" rols="10"></textarea></li>
				      <li><span role="button" class="button confirm" onclick="submitorder();">确定</span></li>
	       		   </ul>
	    		 </div>
			</div>					
		</section>
	</form>
<!-- 	<script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/selectjs/jquery.select.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/selectjs/jquery.mCustomScrollbar.concat.min.js"></script>  -->
	<script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/workbillroom/util.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script type="text/javascript">
	var path = "<%=request.getContextPath() %>";
	$(document).ready(function(){
		$.ajax({
		         url: path + "/loadsearchroomtype.do",
				 type: "post",
				 data : {},
				 success: function(json) {
				 	var data = "<option value=''>房间类型</option>";
				 	$.each(json,function(index){
				 		data = data + "<option value='" +json[index]["ROOMTYPE"] + "'>" +json[index]["ROOMNAME"] + "</option>"
				 	});
				 	$("#roomType").html(data);
					$(".mySelect").select({
						width: "175px"
					});
				 },
				 error: function(json) {}
		});
		$.ajax({
	         url: path + "/loadOrderData.do",
			 type: "post",
			 dataType: "json",
			 data : { orderId : $("#orderId").val() },
			 success: function(json) {
			 var value = $("#roomType").val(json[0]["RMTYPE"]);
			 var opts = document.getElementById("roomType");  
			 for(var i=0;i<opts.options.length;i++){  
		        if(value == opts.options[i].value){  
		          opts.options[i].selected = 'selected'; 
		          break;
	          }  
	        }   
		 	//$("#orderid").val(json["ROOMID"]);
		 	$("#arrivaltime").val(json[0]["ARRIVALTIME"]);
		 	$("#leavetime").val(json[0]["LEAVETIME"]);
		 	$("#orderuser").val(json[0]["ORDERUSER"]);
		 	$("#mphone").val(json[0]["MPHONE"]);
		 	$("#source").val(json[0]["SOURCE"]);
		 	$("#memberrank").val(json[0]["MEMBERRANK"]);
		 	$("#activity").val(json[0]["ACTIVITY"]);
		 	$("#guarantee").val(json[0]["GUARANTEE"]);
		 	var values = document.getElementById("guarantee");
		 	if (json[0]["GUARANTEE"] == '无担保') {
		 		values.options[0].selected = 'selected'; 
		 	} else {
		 		values.options[1].selected = 'selected'; 
		 	}
		 	$("#limited").val(json[0]["LIMITED"]);
		 	$("#roomPrice").val(json[0]["PRICE"]);
		 	//$("#orderid").val(json[0]["ORDERID"]);
		 	$("#remark").val(json[0]["REMARK"]);
		 	//loadCheckuser(json[0]["CHECKUSER"],json[0]["CHECKUSERNAME"]);
			 },
			 error: function(json) {}
			 
		});
	})
	
		function submitorder(){
			$.ajax({
		         url: path + "/editorderinfo.do",
				 type: "post",
				 data : $("#myForm").serialize(),
				 success: function(json) {
					 showMsg(json.message);
					 window.setTimeout("window.parent.location.reload(true)",1800);
				 },
				 error: function(json) {
				 	showMsg("操作失败！");
				 }
			});
		}
	/*	$(".margin_text").on("click",function(){
			$(".ontime").css("display","block");
		}) */
	//	function onbill(){
	//		document.getElementById("ontime").innerHTML='<iframe src="' + path +'/page/ipmspage/common_addbill/commbill.jsp" width="100%" height="100%" frameborder=no  scrolling="no"></iframe>'
	//	}
	
	
	//	$(function(){
	//		loadsearchroomtype();
	//	});
		
	//	function loadsearchroomtype(){
			
		//}
		$("#roomType").change(function(){
			console.log($("#roomType").val());
			$.ajax({
				url: path + "/getRoomPrice.do",
				type: "post",
				dataType: "json",
				data: { roomtype : $("#roomType").val(), orderId : $("#orderId").val() },
				success: function(json) {
					$("#roomPrice").val(json[0]["ROOM_PRICE"]);
				},
				error: function(json) {
					showMsg("操作失败！");
				}
			});
		})
		
	/**		function getprice(){
			$.ajax({
				url: path + "/getRoomPrice.do",
				type: "post",
				dataType: "json",
				data: { roomtype : $("#roomType").val(), orderId : $("#orderId").val() },
				success: function(json) {
					$("#roomPrice").val(json[0]["ROOM_PRICE"]);
				},
				error: function(json) {
					showMsg("操作失败！");
				}
			});
		}**/
	</script>
  </body>
</html>