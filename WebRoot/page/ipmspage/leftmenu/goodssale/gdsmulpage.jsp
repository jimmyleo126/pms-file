 <%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../../common/taglib.jsp"%>
<%@ include file="../../../common/css.jsp"%>
<%@ include file="../../../common/script.jsp"%>
<% request.setAttribute("basePath", request.getContextPath());
   request.setAttribute("saletype", request.getAttribute("saletype"));
   request.setAttribute("listgoods", request.getAttribute("listgoods"));
   request.setAttribute("gdsproject", request.getAttribute("gdsproject"));
   request.setAttribute("gdsprojectpay", request.getAttribute("gdsprojectpay"));
   request.setAttribute("workbill", request.getAttribute("workbill"));   
%>

<!DOCTYPE HTML>
<html class="whitebg">
  <head>
    <title>售卖页面</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/leftmenu/leftmenu.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/shopsell/shopsell.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
	<script>
      var base_path = "<%=request.getContextPath()%>";
	</script>
	<style>
	.imooc_order{
		top:-10px;
		left:96%;
	}
	.yfk{
	    color: red;
	    border: none;
	    font-size: 15px;
	    margin-left: 5px;
	    width: 35px;
	}
	.yfkspan{
		margin-left: 10px;
	}
	.acount{
		width:50px;
		text-align:center;
	}
	.totaltable{
		border:none;
		margin-top: 4%;
	}
	.totalinput{
		color:red;	
		border:none;
	}
	.multiplesale{
		margin: 10px 10px;
	}
	.multiplesale table thead tr{
		background: #f1f1f1;
	    font-size: 14px;
	    color: #333;
	}
	.multiplesale table thead tr th{
		padding:4px;
	}
	
</style>
  </head>
  <body style="background:transparent !important;">
     <div id="multiplesale" class="multiplesale">
     	<div class="multiplesale_div">
	        <table id="myTable" class="myTable" border="0" width="100">
				<thead id="log">
					<tr>
						<th class="header">商品名称</th>
						<th class="header">售价</th>
						<th class="header">小计</th>
						<th class="header">数量</th>
					</tr>	
				</thead>
				<tbody id="info">
			        <c:forEach items="${listgoods}" var="list1" varStatus="aaa">
					<tr>
						<td align="center" id="${aaa.index}gdsid" style="display:none">${list1.goodsId}</td>
						<td align="center">${list1.goodsName}</td>
						<td align="center" name="gdsinprice" id="${aaa.index}gdsprice">${list1.price}</td>
						<td align="center" id="${aaa.index}gdsacount" name="gdsacount">${list1.price}</td>
						<td align="center" id="${aaa.index}">
						<span  id="${aaa.index}sub" onclick="sub(this)" class="count jian">-</span>
						<input id="${aaa.index}acount" name="acount" onblur="lacount()" type="text" value="1" class="acount"  onKeyUp="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
						<span  id="${aaa.index}sub" onclick="add(this)" class="count jia">+</span>
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<table class="totaltable">
            <col width="25%"/>
            <col width="25%"/>
            <col width="25%"/>
            <col width="25%"/>
            <tr> 
	            <td></td>
	            <td></td>
			    <td align="right"><span>总计数量：</span><input class="totalinput" id="totalnumber" value="0" disable="true"/></td>
			    <td align="right"><span>总计金额：</span><input class="totalinput" id="totalprice" value="0" disable="true"/></td>			
		   </tr>
           </table>
           <div class="paymethod_gdspage">
              <div id="paycheckbox" class="footertwo_gdspage"> 
              <span>付款方式：</span>
              <input id="roompay" type="checkbox" class="check"><span class="spanmar_gdspage">挂房账</span>
              <input id="cashpay" type="checkbox" class="check"><span class="spanmar_gdspage">现金</span>
              <input id="cardpay" type="checkbox" class="check"><span>银行卡</span>
              </div>
           </div>
        <div id="" class="footerthree_gdspage_mul"> 
             <input id="cashpayvalue" type="text" class="check payinput_gdspage_cash">
             <input id="cardpayvalue" type="text" class="check payinput_gdspage_card_mul" placeholder="凭证号" onKeyUp="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
        </div>
        <div id="payroom" class="footerone_gdspage">
                <input id="gdsroomid" name="roomid" type="text" class="check roomselect_gdspage" disabled="true" placeholder="房号">
                <input id="gdscheckid" name="gdscheckid" type="hidden" class="check roomselect_gdspage">
                <input id="" name=""class="reset_gdspage" type="button" value="清空" class="check roomselect_gdspage" onclick="cleargdsroom()">
        </div>
         <div id="costproject" class="footerzero_gdspage">
              <span>消费项目：</span>
              <select id="gdsproject" name="" class="check projectselect_gdspage">  
               <c:forEach var="th" items="${gdsproject}">  
                   <option  value="${th.content}"> ${th.paramname} </option>  
               </c:forEach>  
              </select> 
          </div>
          <div id="payproject">
               <div class="footerzero_gdspage">
                   <span>结算项目：</span>
                <select id="gdsprojectpay" name="" class="check projectselect_gdspage">  
                 <c:forEach var="th" items="${gdsprojectpay}">  
                   <option  value="${th.content}"> ${th.paramname} </option>  
                 </c:forEach>  
                </select> 
               </div>
               <div class="footerzero_gdspage">
                  <span>工作账：</span>
                  <select id="gdsworkbill" name="" class="check projectselect_gdspage">  
                 <c:forEach var="th" items="${workbill}">  
                   <option  value="${th.workbillid}"> ${th.name} </option>  
                 </c:forEach>  
                  </select> 
               </div>
           </div> 
           <div class="sbotton_gdspage">
             <input id="" name="" type="button" class="submitbotton_gdspage clean enter" style="float:right;" value="入账" onclick="gdspaysubmit()"/>
             <input id="" name="" type="button" class="submitbotton_gdspage clean" style="float:right;" value="重置" onclick="location.reload(true)"/>
           </div>
        </div>
		<script>
		$(document).ready(function(){
		 var alltext= document.getElementsByName("gdsacount"); 
				  var totalprice = 0;
					for(var i =0;i<alltext.length;i++)
					{
					          totalprice = parseInt(totalprice)+parseInt(alltext[i].innerHTML);
					     
					}
					 var allacounttext= document.getElementsByName("acount"); 
					var totalnumber = 0;
					for(var i =0;i<allacounttext.length;i++)
					{
					          totalnumber = parseInt(totalnumber)+parseInt(allacounttext[i].value);
					         
					     
					}
				  $("#totalprice").val(totalprice);
				  $("#totalnumber").val(totalnumber);
				  
				  
         $("#paycheckbox").children('input[type=checkbox]').click(function(){
                if($(this).is(':checked')){ 
                    $(this).attr('checked',true).siblings().attr('checked',false);
                     if($(this).attr('id')=="roompay"){
                        $("#payroom").show();
                        $("#gdsroomid").attr("disabled", false);
                        $("#cashpayvalue").val("");
                        $("#cashpayvalue").hide();
                        $("#cardpayvalue").val("");
                        $("#cardpayvalue").hide();
                        $("#costproject").show();
                        $("#payproject").hide();
                   }else if($(this).attr('id')=="cashpay"){
                        $("#payroom").hide();
                        $("#gdsroomid").val("");
                        $("#gdsroomid").attr("disabled", true);
                        $("#cashpayvalue").show();
                        $("#cashpayvalue").val(totalprice);
                        $("#cardpayvalue").val("");
                        $("#cardpayvalue").hide();
                        $("#costproject").hide();
                        $("#payproject").show();
                   }else if($(this).attr('id')=="cardpay"){
                        $("#payroom").hide();
                        $("#gdsroomid").val("");
                        $("#gdsroomid").attr("disabled", true);
                        $("#cardpayvalue").show();
                        $("#cashpayvalue").val("");
                        $("#cashpayvalue").hide();
                        $("#costproject").hide();
                        $("#payproject").show();
                   }
                }else{
                    $(this).attr('checked',false).siblings().attr('checked',false);
                       $("#gdsroomid").val("");
                       $("#gdsroomid").attr("disabled", true);
                       $("#cashpayvalue").val("");
                       $("#cashpayvalue").hide();
                       $("#cardpayvalue").val("");
                       $("#cardpayvalue").hide();
                       $("#costproject").hide();
                       $("#payproject").hide();
                }
            })
            
          })
           	//挂房账
            $("#gdsroomid").bind("click",function(){        
           		 JDialog.open("挂房账", base_path + "/gdsroomSelect.do", 600, 300);
            });
            
            
            function cleargdsroom() {
		        $("#gdsroomid").val("");

	        }
          
			function sub(data){
			       var spanid = data.id;
			       var acountid = spanid.replace('sub', 'acount');
			       var acountidid = "#"+acountid;
			       var gdspriceid = spanid.replace('sub', 'gdsprice');
			       var gdspriceidid = "#"+gdspriceid;
			       var gdsacountid = spanid.replace('sub', 'gdsacount');
			       var gdsacountidid = "#"+gdsacountid;
			       var c = $(acountidid).val();
			    if(c>=2){
				  c = c-1;
				  $(acountidid).val(c);
				  var gdsprice = $(gdspriceidid).html()
				  var allpricr = parseInt(c*gdsprice);
				  $(gdsacountidid).html(allpricr);
				  var alltext= document.getElementsByName("gdsacount"); 
				  var totalprice = 0;
					for(var i =0;i<alltext.length;i++)
					{
					          totalprice = parseInt(totalprice)+parseInt(alltext[i].innerHTML);
					     
					}
					 var allacounttext= document.getElementsByName("acount"); 
					var totalnumber = 0;
					for(var i =0;i<allacounttext.length;i++)
					 
					{
					          totalnumber = parseInt(totalnumber)+parseInt(allacounttext[i].value);
					         
					     
					}
				  $("#totalprice").val(totalprice);
				  $("#totalnumber").val(totalnumber);
				  
				}else{
				  showMsg("数量不可为小于1的数字！");
				  }
				}
				
			function add(data){
			       var spanid = data.id;
			       var acountid = spanid.replace('sub', 'acount');
			       var acountidid = "#"+acountid;
			       var gdspriceid = spanid.replace('sub', 'gdsprice');
			       var gdspriceidid = "#"+gdspriceid;
			       var gdsacountid = spanid.replace('sub', 'gdsacount');
			       var gdsacountidid = "#"+gdsacountid;
			       var c = $(acountidid).val();
                   c = parseInt(parseInt(c)+1);
				   $(acountidid).val(c);
				   var gdsprice = $(gdspriceidid).html()
				  var allpricr = parseInt(c*gdsprice);
				  $(gdsacountidid).html(allpricr);
				  var alltext= document.getElementsByName("gdsacount"); 
				  var totalprice = 0;
					for(var i =0;i<alltext.length;i++)
					{
					          totalprice = parseInt(totalprice)+parseInt(alltext[i].innerHTML);
					     
					}
					var allacounttext= document.getElementsByName("acount"); 
					var totalnumber = 0;
					for(var i =0;i<allacounttext.length;i++)
					{
					          totalnumber = parseInt(totalnumber)+parseInt(allacounttext[i].value);
					         
					     
					}
				  $("#totalprice").val(totalprice);
				  $("#totalnumber").val(totalnumber);
				 }
				 
			function lacount(){
			
			    var allacounttext= document.getElementsByName("acount"); 
			    var allpricetext= document.getElementsByName("gdsinprice"); 
			    var totalnumber = 0;
			    var totalprice = 0;
			    var re = /^[0-9]+$/ ;
		
			    for(var i =0;i<allacounttext.length;i++) 
					{    if(re.test(allacounttext[i].value)){
					     totalnumber = parseInt(totalnumber)+parseInt(allacounttext[i].value);
					     totalprice = parseInt(totalprice)+parseInt(allacounttext[i].value*allpricetext[i].innerHTML);		
					    } else{ 
					
					     showMsg("数量必须为正整数，请重新输入");
				          window.setTimeout("location.reload(true)", 1500);
					    }
					    			     
					}
					
				  $("#totalprice").val(totalprice);
				  $("#totalnumber").val(totalnumber);
				 
			    
			  
			}
			
			document.onkeydown=function(event){
            var e = event || window.event || arguments.callee.caller.arguments[0];          
             if(e && e.keyCode==13){ // enter 键
                  var allacounttext= document.getElementsByName("acount"); 
			    var allpricetext= document.getElementsByName("gdsinprice"); 
			    var totalnumber = 0;
			    var totalprice = 0;
			    var re = /^[0-9]+$/ ;
		
			    for(var i =0;i<allacounttext.length;i++) 
					{    if(re.test(allacounttext[i].value)){
					     totalnumber = parseInt(totalnumber)+parseInt(allacounttext[i].value);
					     totalprice = parseInt(totalprice)+parseInt(allacounttext[i].value*allpricetext[i].innerHTML);		
					    } else{ 
					
					     showMsg("数量必须为正整数，请重新输入");
				          window.setTimeout("location.reload(true)", 1500);
					    }
					    			     
					}
					
				  $("#totalprice").val(totalprice);
				  $("#totalnumber").val(totalnumber);
			    
            }
        }; 
        
        
        function gdspaysubmit(){
            var gdscheckid = $("#gdscheckid").val();
            var gdsproject = $("#gdsproject").val();
            var gdsprojectname = $("#gdsproject").find("option:selected").text();
            var gdsroomid = $("#gdsroomid").val();
             var cashpayvalue = $("#cashpayvalue").val();
            var gdsprojectpay = $("#gdsprojectpay").val();
            var gdsprojectpayname = $("#gdsprojectpay").find("option:selected").text();
            var gdsworkbill = $("#gdsworkbill").val();
            var gdsworkbillname = $("#gdsworkbill").find("option:selected").text();
            var cardpayvalue = $("#cardpayvalue").val();
            var alltext= document.getElementsByName("gdsacount"); 
					for(var i =0;i<alltext.length;i++)
					{
		               var gd = "#"+i+"gdsid";${aaa.index}acount
		               var goodsid = $(gd).text();
		               var gr = "#"+i+"gdsprice";
		               var goodsprice = $(gr).text();
		               var ga = "#"+i+"gdsacount";
		               var gdsacount = $(ga).text();
		                var ac = "#"+i+"acount";
		               var acount = $(ac).val();
		              if($("#roompay").is(':checked')){
           if(gdsroomid){
               $.ajax( {
					url : base_path + "/gdsRoompay.do",
					type : "post",
					dataType : "json",
					data : {
						'gdscheckid' : gdscheckid,
						'gdsproject' : gdsproject,
						'gdsprojectname' : gdsprojectname,
						'totalprice' : gdsacount,
						'gdsroomid' : gdsroomid,
						'gdsid' : goodsid,
						'totalnumber' : acount,
						'gdsprice' : goodsprice
					},
					success : function(json) {
						if (1 == json.result) {
						    showMsg("挂账成功！");
						    window.setTimeout("location.reload(true)", 1800);
						    window.setTimeout("window.parent.JDialog.close();", 1800);
							
						} else {
							showMsg("挂账失败！");
							window.setTimeout("location.reload(true)", 1800);
							 window.setTimeout("window.parent.JDialog.close();", 1800);
						}
					},
					error : function() {
						showMsg("操作失败，请重新操作！");
						window.setTimeout("location.reload(true)", 1800);
						 window.setTimeout("window.parent.JDialog.close();", 1800);
					}
				    });
           }else{
              showMsg("请先选择挂账房间");
           }
           
        }else if($("#cashpay").is(':checked')){
           if(cashpayvalue){
           var totalprice = $("#totalprice").val();
           if(cashpayvalue==totalprice){
           
            $.ajax( {
					url : base_path + "/gdsRoompaycash.do",
					type : "post",
					dataType : "json",
					data : {
						'gdscheckid' : gdscheckid,
						'gdsprojectpay' : gdsprojectpay,
						'gdsprojectpayname' : gdsprojectpayname,
						'gdsworkbill' : gdsworkbill,
						'gdsworkbillname' : gdsworkbillname,
						'totalprice' : gdsacount,
						'cashpayvalue' : cashpayvalue,
						'gdsid' : goodsid,
						'totalnumber' : acount,
						'gdsprice' : goodsprice
					},
					success : function(json) {
						if (1 == json.result) {
						    showMsg("售卖成功！");
						    window.setTimeout("location.reload(true)", 1800);
						     window.setTimeout("window.parent.JDialog.close();", 1800);
							
						} else {
							showMsg("售卖失败！");
							window.setTimeout("location.reload(true)", 1800);
							 window.setTimeout("window.parent.JDialog.close();", 1800);
						}
					},
					error : function() {
						showMsg("操作失败，请重新操作！");
						window.setTimeout("location.reload(true)", 1800);
						 window.setTimeout("window.parent.JDialog.close();", 1800);
					}
				    });
           
           }else{
             showMsg("需支付金额与输入金额不等，请重新输入");
           
           }
             
             
           }else{
              showMsg("请输入支付金额");
           }
        }else if($("#cardpay").is(':checked')){
             if(cardpayvalue){
                 if(cardpayvalue.length>6){
                    showMsg("凭证号长度不能超过6位");
                 }else{
                  $.ajax( {
					url : base_path + "/gdsRoompaycard.do",
					type : "post",
					dataType : "json",
					data : {
						'gdscheckid' : gdscheckid,
						'gdsprojectpay' : gdsprojectpay,
						'gdsprojectpayname' : gdsprojectpayname,
						'gdsworkbill' : gdsworkbill,
						'gdsworkbillname' : gdsworkbillname,
						'totalprice' : gdsacount,
						'cardpayvalue' : cardpayvalue,
						'gdsid' : goodsid,
						'totalnumber' : acount,
						'gdsprice' : goodsprice
					},
					success : function(json) {
						if (1 == json.result) {
						    showMsg("售卖成功！");
						    window.setTimeout("location.reload(true)", 1800);
						     window.setTimeout("window.parent.JDialog.close();", 1800);
							
						} else {
							showMsg("售卖失败！");
							window.setTimeout("location.reload(true)", 1800);
							 window.setTimeout("window.parent.JDialog.close();", 1800);
						}
					},
					error : function() {
						showMsg("操作失败，请重新操作！");
						window.setTimeout("location.reload(true)", 1800);
						 window.setTimeout("window.parent.JDialog.close();", 1800);
					}
				    });
				    }
             }else{
                showMsg("请先输入凭证号");
             }
           
           }else{
           showMsg("请先选择付款方式");
        }
       
					     
					} 
            

           
        
        
        }
        
        
	</script>
	<script src="<%=request.getContextPath()%>/script/common/jquery-ui.min.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/jquery.dialog.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/jquery.jqGrid.src.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/ipms/js/leftmenu/goodssale.js"></script>
  </body>
</html>
