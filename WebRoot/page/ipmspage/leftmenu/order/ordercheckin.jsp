 <%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../../common/taglib.jsp"%>
<%@ include file="../../../common/script.jsp"%>
<% request.setAttribute("basePath", request.getContextPath());
   request.setAttribute("roomtype", request.getAttribute("roomtype"));
   request.setAttribute("theme", request.getAttribute("theme"));  
%>

<!DOCTYPE HTML>
<html>
  <head>
    <title>预订页面</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" id="style" href="<%=request.getContextPath()%>/css/ipms/css/order/order_details.css" />
	<link rel="stylesheet" id="style" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css"/>
	<link rel="stylesheet" id="style" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/common/jquery-dialog.css"/>
	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/need/laydate.css" />
	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/skins/molv/laydate.css"/>
	<link href="//cdn.bootcss.com/font-awesome/4.6.3/css/font-awesome.min.css"  rel="stylesheet">
	<script> var base_path = "<%=request.getContextPath()%>";</script>
	<style>
	.imooc_order{
		top:-10px;
		left:96%;
	}
	.submitbotton{
		font-size: 14px;
		font-family: "微软雅黑";
		height: 34px;
	    line-height: 31px;
	    text-align: center;
	    width: 80px;
	    margin-left: 4px;
	    border: 1px solid #CCCCCC;
	    background: #4A5064;
	    border: 1px solid #CCCCCC;
	    color: #fff;
	    cursor: pointer;
	}
	.sonepa{
		padding:2% 0 0  0;
	}
	.infohidden{
		display:none;
	}
	.info_write {
		
	    margin-top: 7px;
	    background: #FFFFFF;
	    overflow-x: scroll;
	    overflow-y: hidden;
	    
    }
    .subcontainer{
	    overflow-x: scroll;
	    overflow-y: hidden;
    }
    .info_write ul {
	    border: 1px solid;
	    width: auto;
	    display: table-row;
	}
	.info_write ul li{
		
	}
	
	 .xx_wrap {
        width: 90%;
        overflow-x: auto;
    }
    .nav {
        padding: 0;
        margin: 0;
        list-style: none;
    }
    .xx_wrap ul {
        display: flex;
    }
    .xx_wrap li {
        padding: 5px 20px;
        margin: 0 5px;
        border: 1px solid #DADADA;
    }
    .tabli{
    width:10%;
    display: inline-table;
    
    }
    .showuser{
    display:block;
    }
    .hiddenuser{
    display:none;
    }
    .detail_one_order .label_name_order {
    /* float: left; */
    /* width: 106px; */
    text-align: right;
    font-weight: normal;
    font-size: 0.85rem;
}
.order_pzh{
    margin-left: 19.6%;
    margin-top: 1%;
    display:none;
}
	</style>
	
  </head>
  <body>
  <div class="">
  <input id="roomprice" name="roomprice" type="hidden"/>
  <input id="memberId" name="memberId" type="text"/>
  <span class="close_span">
  	<i class="imooc imooc_order" style="color:#3B3E40;" onclick="window.parent.JDialog.close();">&#xe902;</i>
  </span>
	  <form action="" method="post">
		  <section class="detail_one sonepa">
			  <label class="label_title">1.查会员信息</label>
			  <input id="memberselect" name="memberselect" type="text" value="" class="check" onKeyUp="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
			  <input class="button_margin submitbotton" onclick="selectmember()" value="查询"/>
					<!--<input class="button_margin look_order" onclick="selectmember()" value= ""/>-->
		  </section>
		  <section class="detail_one">
			  <label class="label_title">2.预订单信息</label>
			  <ul class="clearfix">
				  <li><label class="label_name">场景</label><!--
				      <select id="theme" name="theme" class="check">   
                          <option value="1">酒店 </option>
                           <option value="2">公寓</option>    
                       </select>
                        --><select id="theme" name="theme" class="check" onchange="rtChange()">  
                                <c:forEach var="th" items="${theme}">  
                                <option  value="${th.content}"> ${th.paramname} </option>  
                                </c:forEach>  
                       </select>  
                   </li>
				   <li>
				       <label class="label_name">房间类型</label>
				       <select id="roomtype" name="roomtype" class="check" onchange="rtChange()">
				                <option id="choosert" value="">请选择房间类型</option>  
                                <c:forEach var="rt" items="${roomtype}">  
                                <option id="selectrt" value="${rt.roomtype}"> ${rt.roomname} </option>  
                                </c:forEach>  
                       </select>
                   </li> 
				   <li><label class="label_name" id="arrive">抵店日期</label> <input id="arrivedate" name="arrivedate" type="text" value="" class="check date"></li>
				   <li><label class="label_name" id="leave">离店日期</label> <input id="leavedate" name="leavedate" type="text" value="" class="check date"></li>				
				   <li><label class="label_name">数量</label><span onclick="subNum()">-</span><input id="acount" name="acount" type="text" value="1" class="check"  onKeyUp="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"><span onclick="addNum()">+</span></li>
				   <li><label class="label_name">房号</label><input id="roomid" name="roomid" type="text" class="check"></li>
				   <li><label class="label_name">房价码</label><input id ="rpid" name="rpid" type="text" value=""  class="check" disabled></li>
				   <li><label class="label_name">房价</label><input id="price" name="price" type="text" value="" class="check"disabled></li>
				   <li><label class="label_name">预订人</label><input id="userorder" name="userorder" type="text" value="" class="check" disabled></li>
				   <li><label class="label_name">预定人手机</label> <input id="ordemobile" name="ordemobile" type="text" value="" class="check" disabled></li>
				   <!--<li><label class="label_name">担保</label> <input name="" type="text" value="" class="check" disabled></li>-->
				   <li><label class="label_name">保留时效</label> <input id="guarantee" name="guarantee" type="text" value="" class="check" disabled></li>
				   <li><label class="label_name">接待备注</label> <textarea id="receptionremark" name="receptionremark" type="text" value="" class="check"></textarea></li>
				   <li><label class="label_name">收银备注</label> <textarea id="cashremark" name="cashremark" type="text" value="" class="check"></textarea></li>
				   <li><label class="label_name">客房备注</label> <textarea id="roomremark" name="roomremark" type="text" value="" class="check"></textarea></li>
			    </ul>
		   </section>
		  
		   <section class="detail_one">
		   <label class="label_title ">3.入住人信息</label>
		    <div id="tab" class="xx_wrap">
			    <ul  id="tabtitlenew" class="nav">
			        <li id="addnewtab" class="tab_select tabli">新增</li>
			        <li class="tabli" onclick="changeTab('0')">入住人</li>
			       
			    </ul>
			</div>
		    <div id="tabCon" >
		        <div id="tabCon_0" class="showuser style="display:block">
		        <ul id="tabcontent" class="clearfix">
				   <li><label class="label_name">姓名0</label><input id= "usercheckin" name="usercheckin" type="text" value="" class="check"></li>
				   <li><label class="label_name">手机号0</label> <input id= "usermobile" name="usermobile" type="text" value="" class="check"></li>
				</ul>
				</div>
		      
		    </div>
		 </div>
		   </section>
		   <section class="detail_one_order" style="background: #FFFFFF;">
			   <label class="label_title">4.预付款</label>
			   <div id="paymethod">
				   <input id="xj" name="" type="checkbox"/><label class="label_name_order">现金</label><input id="cash" name="cash" type="text" value="" class="check"/>
				   <input id="yhk" name="" type="checkbox"/><label class="label_name_order">银行卡</label> <input id="card" name="card" type="text" value="" class="check"/>
			   </div>
			    <div id="order_pzh" class="order_pzh">
				   <label class="label_name_order">凭证号</label> <input name="" type="text" value="" class="check">
			   </div>
				 <div id="exscore" style="float:left;"> <input id="jf" name="" type="checkbox"/><label class="label_name_order">积分</label><input id="score" name="score" type="text" value="" class="check"></div>
				 <div id="excash" style="display:none;float:left;"><label class="label_name_order">金额</label><input id="scorecash" name="score" type="text" value="" class="check"></div>
		  </section>
		  <section class="detail_four" >
			   <div class="fr" role="button">
			     <ul class="clearfix">
					<li><span class="button_margin" onclick="orderbegin()">预订</span></li>
			     </ul>
			   </div>
		  </section>
       </div>
		<script src="<%=request.getContextPath()%>/script/ipms/js/laydate.dev.js"></script>
		<script>
			laydate({
				elem: '#arrivedate'
			})
			laydate({
				elem: '#leavedate'
			})
		 $(document).ready(function(){
		  $("#paymethod").children('input[type=checkbox]').click(function(){
                if($(this).is(':checked')){ 
                    $(this).attr('checked',true).siblings().attr('checked',false);
                     if($(this).attr('id')=="yhk"){
                       $("#order_pzh").show();
                        $("#card").attr("disabled", false);
                        $("#cash").attr("disabled", true);
                   }else if($(this).attr('id')=="xj"){
                        $("#card").attr("disabled", true);
                        $("#cash").attr("disabled", false);
                         $("#order_pzh").hide();
                   }
                }else{
                    $(this).attr('checked',false).siblings().attr('checked',false);
                     if($(this).attr('id')=="yhk"){
                      $("#order_pzh").hide();
                       $("#card").attr("disabled", false);
                       $("#cash").attr("disabled", false);
                   }else if($(this).attr('id')=="xj"){
                       $("#order_pzh").hide();
                       $("#card").attr("disabled", false);
                       $("#cash").attr("disabled", false);
                   }
                }
            })
            
             $("#exscore").children('input[type=checkbox]').click(function(){
                if($(this).is(':checked')){ 
                  $("#excash").show();
                
                }else{
                $("#score").val("");
                 $("#scorecash").val("");
                $("#excash").hide();
                
                }
             
              })
            
            $("#score").bind("click",function(){
			  var MemberId = $("#memberId").val();
			  var sjscore = $("#score").val();
			  if(MemberId){
		      JDialog.open("", base_path + "/scoreExchange.do?MemberId=" + MemberId + "&sjscore="+sjscore, 600, 410);
		      }else{
		      showMsg("请先输入预订人（会员）手机号！");
		      }
			})
         
			  //$(".date").datetimepicker({ datetimeFormat: "yy/mm/dd" });
		      //$("#ui-datepicker-div").css('font-size','0.9em');
		      //$(".date").datepicker({ dateFormat: "yy/mm/dd " });
				$("#arrivedate").css('font-size','0.9em');
				$("#leavedate").css('font-size','0.9em');
		      $("#theme").change(function(){
		       if($("#theme").val() == 2){
		          $("#roomtype").attr("disabled", true);
			          var label_a=document.getElementById("arrive"); 
					  label_a.innerText="开始日期"; 
					  $("#arrive").html("开始日期");
					  var label_r=document.getElementById("leave"); 
					  label_r.innerText="结束日期"; 
					  $("#leave").html("结束日期"); 
		          }else if($("#theme").val() == 1){
			          $("#roomtype").removeAttr("disabled");
			          var label=document.getElementById("arrive"); 
					  label.innerText="抵店日期"; 
					  $("#arrive").html("抵店日期"); 
					  var label_r=document.getElementById("leave"); 
					  label_r.innerText="离店日期"; 
					  $("#leave").html("离店日期"); 
		          }  
		      })
		      
		      
		      
				});
				
		 
				
		function showMsg(msg, fn) {
				if (!fn) {
					TipInfo.Msg.alert(msg);
				} else {
					TipInfo.Msg.confirm(msg, fn);
				}
			}
			
			
			
			function subNum(){
			    var c = parseInt($("#acount").val());
			    if(c>=2){
				document.getElementById("acount").value=c-1;
				}else{
				  showMsg("数量不可为小于1的数字！");
				  }
				}
				
				
				
			function addNum(){
			     var c = parseInt($("#acount").val());
				document.getElementById("acount").value=c+1;
				}
			
			$(".info_write ul li").on("click",function(){
				$(this).addClass("active");
				$(this).siblings().removeClass("active");
			});
			$(".detail_four .look_order").on("click",function(){
				window.location.href="<%=request.getContextPath()%>/page/ipmspage/order/order_check.jsp";
				
			});
			
			
			
			function selectmember(){
			  $("#rpid").val("");
			  $("#price").val("");
			  $("#roomid").val("");
			  $("#roomtype ").val("");  
			   var Mnumber = $("#memberselect").val();
			   if(Mnumber){
			   $.ajax( {
				url : base_path + "/memberSelect.do",
				type : "post",
				dataType : "json",
				data : {
					'Mnumber' : Mnumber
				},
				success : function(json) {
					if (1 == json.result) {
					    if(json.message){
					        showMsg(json.message);
					       }else{
					         JDialog.open("", base_path + "/judgeUser.do?Mnumber="+Mnumber,300,150);
					       }
						
					} else {
						showMsg("没有该会员的信息，请输入正确的会员卡号或手机号！");
						window.setTimeout("location.reload(true)", 1800);
					}
				},
				error : function() {
					showMsg("操作失败，请重新操作！");
					window.setTimeout("location.reload(true)", 1800);
				}
			    });
			   }
			}
			
			
			$("#roomid").bind("click",function(){
			  var theme = $("#theme").val();
			  var roomtype = $("#roomtype").val();
			  var roomacount = $("#acount").val();
			  if(roomtype){
		      JDialog.open("", base_path + "/roomSelect.do?roomtype=" + roomtype+"&theme=" + theme+"&roomacount="+roomacount, 800, 410);
		      }else{
		      showMsg("请先选择房间类型！");
		      }
			})
			    
			
			function rtChange(){
			    $("#roomid").val(""); 	
				var t = $("#roomtype").val();
				var s =  $("#roomprice").val();
				var first = s.replace(/=/g,":'");
				var second = first.replace(/,/g,"',");
				var third = second.replace(/}/g,"'}");
				var fouth = third.replace(/}',/g,"},");
				var array = eval(fouth);
				for(var i = 0, l = array.length; i < l; i++){ 
				var rpName = array[i].ROOM_TYPE
				if(rpName==t){
				var rpId = array[i].RP_NAME;
				var price = array[i].ROOM_PRICE;
				$("#rpid").val(rpId);
				$("#price").val(price);
			    }
			  }
			}
			 var InputCount=0;
			
			$("#addnewtab").click(function(){
			   
			    InputCount++;
			    var tabCon = "tabCon_"+InputCount;
			    var rzr = "rzr_"+InputCount;
			    var span = "span_"+InputCount;
			    var tabtitle =  "tabtabtitle_"+InputCount;
			    var user = "user_"+InputCount;
			    var mobile = "mobile_"+InputCount;
			    
		         //$("#tabtitle").append('<input type="text" name="saletype' + InputCount + '"   class="auditreset inpute"/>');
		         //$("#tabtitle").append(' <li class="tab_select">入住人</li>');
		          $("#tabtitlenew").append(' <li class="tabli" id='+tabtitle+' onclick="changeTab('+InputCount+')"><span id='+rzr+'>入住人'+InputCount+'</span></li><span id='+span+' style="margin-left: -13px;">x</span>');
		          $("#tabCon").append('<div id='+tabCon+' style="display:none" class="hiddenuser"> <ul id="tabcontent" class="clearfix"> <li><label class="label_name">姓名'+InputCount+'</label><input id= '+user+' name="usercheckin" type="text" value="" class="check"></li> <li><label class="label_name">手机号'+InputCount+'</label> <input id= '+mobile+' name="usermobile" type="text" value="" class="check"></li></ul></div>');
			
			});
			
			function changeTab(tabCon_num){
			var usercount = InputCount
            for(i=0;i<=usercount;i++) {
            //$("#tabCon_"+i).hidden();
             document.getElementById("tabCon_"+i).style.display="none";
               //document.getElementById("tabCon_"+i).style.display='none'; //将所有的层都隐藏
            }
             $("#tabCon_"+tabCon_num).show();
             
              //$("#span_"+tabCon_num).show();
              
              
               //$("#span_"+tabCon_num).toggle(function(){
                   // $(this).css("backgroundColor","#f1c2d5");
                    //var roomid = $(this).find("span").html();
                    //rooms.push(roomid);
                //},function(){
                   // $(this).css("backgroundColor","#d6f6fd");
                   // var roomid = $(this).find("span").html();
                   // rooms.remove(roomid);
                //});
              
              
              $("#span_"+tabCon_num).click(function(e){
              $("#tabCon_"+tabCon_num).hide();
                 document.getElementById("span_"+tabCon_num).style.display="none";
                 //$("#tabCon_"+tabCon_num).hide();
                  $("#tabtabtitle_"+tabCon_num).hide();

              })
           // document.getElementById("tabCon_"+tabCon_num).attr("style","display:block;");//显示当前层
        }
        
        
        //function removediv(){
  

           //alert($(this).parent().attr(id))
			  // $(this).parent().remove();
			//$('.tabli').click(function(e){
                  ////  alert($(this).attr('id'));
                //});
				//}		
				
				
				//$(".tabli").click(function(e){
			//var id = $(e.target).attr('id');
				//alert(id);
		//})

			//var list=eval("("+s+")");
			//for(int i=0;i<list.length;i++){
			//name=list[i].RP_NAME;
			////alert(name)
			//}
			
			//var mycars=new Array(s )
			//var json = {};
				//for (var i = 0; i < mycars.length; i++) {
				//json[i]=mycars[i];
				//alert(json[i])
				//}
			    //var r = eval(s);
			    ///var r = eval(s.replace(/=/g,":"));
			    
			    //document.write{'<room_type>'+r[0].room_type+'</room_type>'}
			   //alert(r[0].room_type)
			   //alert(r[1].room_type)
			    //for(var r in s){
			      //for(var i=0; i<r.length; i++ ){
			//alert(r);
                 //alert(r[i])
              //var v = s[i];
              //if(t.equal(s[v].get("room_type"))){
              ////alert("124")
              //}
              
               //for(var i=0; i<t.length; i++ ){
                 //if(w[i].value == t){
                 //alert(w[i].room_type)
                 //}
			    //}
			    
			    
			    function orderbegin(){
			    var ordertheme = $("#theme").val();
			    var orderroomtype =  $("#roomtype").val();
			    var orderarrivedate = $("#arrivedate").val();
			    var orderleavedate = $("#leavedate").val();
			    var orderacount= $("#acount").val();
			    var orderrpid= $("#rpid").val();
			    var orderprice= $("#price").val();
			    var orderuser= $("#userorder").val();
			    var ordermobile= $("#ordemobile").val();
			    var orderguarantee= $("#guarantee").val();
			    var receptionremark= $("#receptionremark").val();
			    var cashremark= $("#cashremark").val();
			    var roomremark= $("#roomremark").val();
			    }
	</script>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
  </body>
</html>
