<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/script.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>房租续约</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/rentrenewal/rentrenewal.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css"/>
  </head>
  
  <body>
  	<div class="div_from">
  		<div class="margintop">
  			<input type="hidden" id="contractId" name="contractId" value="${contractId }"/>
			<ul>
				<li class="high_header_li"><label class="label_name">会员:</label><input name="memberName" type="text" id="memberName" readonly="readonly" class="text_search" value="${memberName }"></li>
		      	<li class="high_header_li"><label class="label_name">房间:</label><input name="roomId" type="text" id="roomId" readonly="readonly" class="text_search" value="${roomId }"></li>
     		 	<li class="high_header_li"><label class="label_name">租赁方式:</label><input name="typeofpaymentName" type="text" id="typeofpaymentName" readonly="readonly" class="text_search" value="${typeofpaymentName }"></li>
		      	<li class="high_header_li"><label class="label_name">结束时间:</label><input name="endTime" type="text" id="endTime" readonly="readonly" class="text_search" value="${endTime }"></li>
      			<li class="high_header_li">
	      			<label class="label_name">续费方式:</label>
			      	<select name="rent_typeofpayment" id="rent_typeofpayment" class="text_search">
						<option value="1">月付</option>
						<option value="3">季付</option>
						<option value="6">半年付</option>
						<option value="12">年付</option>
					</select>
	      		</li>
	      	</ul>
  		</div>
	</div>
	<div class="div_margintop">
		<div class="div_button">
  			<input class="cancel" type="button" value="提交" onclick="submit_rent()"/>  
	    </div>
		<div class="div_button">
			<input class="cancel" type="button" value="取消" onclick="window.parent.JDialog.close()"/>
		</div>
	</div>
  	
  </body>
  <script type="text/javascript">
  	$(function(){
  		var typeofpayment = '${typeofpayment}';
  		if(typeofpayment == 1){
  			typeofpayment = 0;
  		}else if(typeofpayment == 3){
  			typeofpayment = 1;
  		}else if(typeofpayment == 6){
  			typeofpayment = 2;
  		}else if(typeofpayment == 12){
  			typeofpayment = 3;
  		}
  		document.getElementById("rent_typeofpayment")[typeofpayment].selected=true;
  	});
  </script>
  <script type="text/javascript">
  	function submit_rent(){
  		var contractId = $("#contractId").val();
  		var rent_typeofpayment = $("#rent_typeofpayment").val();
  		$.ajax({
  			url: "changeApartmentRent.do",
  			dataType: "json",
  			type: "post",
  			data:{
  				'contractId' : contractId,
  				'rent_typeofpayment' : rent_typeofpayment
  			},
  			success: function(json) {
		        window.parent.showMsg("修改成功!");
		        window.setTimeout("window.parent.location.reload(true)",800);
  				   
  			},
  			error:function(json){
				window.parent.showMsg("修改失败!");
			    window.setTimeout("window.parent.location.reload(true)",1000);
  			}
  		});
  	}
  </script>
</html>
