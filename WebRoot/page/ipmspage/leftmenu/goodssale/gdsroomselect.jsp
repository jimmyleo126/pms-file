<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../../common/taglib.jsp"%>
<%@ include file="../../../common/css.jsp"%>
<%@ include file="../../../common/script.jsp"%>
<% request.setAttribute("basePath", request.getContextPath());
   request.setAttribute("gdsroomId", request.getAttribute("gdsroomId"));  
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>房号选择</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/order/order_details.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/roomstate.css" />	
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
	<script src="<%=request.getContextPath()%>/script/ipms/js/jquery-1.8.2.min.js" type="text/javascript" charset="utf-8"></script>	
	<script>
      var base_path = "<%=request.getContextPath()%>";
	</script>
	<style>
	.whitebg{
    background: #fff;
	}
	.divalign{
	text-align:center;
	}
  .footer_content	.all{
	background-color:#d6f6fd;
	}
	.gdsroomselectbotton{
	float: right;
    margin: 11px;
	font-size: 14px;
	font-family: "微软雅黑";
	height: 40px;
    line-height: 31px;
    text-align: center;
    width: 80px;
    border: 1px solid #CCCCCC;
    background: #4A5064;
    border: 1px solid #CCCCCC;
    color: #fff;
    cursor: pointer;
	}
	.gdsfooter_content{
	
	width: 70.1%;
    /* height: 60px; */
    /* background: #D9DEE4; */
    position: fixed;
    bottom: 45px;
    padding-left: 20px;
	}
	.span_gdsrs{
	margin-left: 2.4%;
	}
	.main_content ul li span.span_gdsrs_hide{
	display:none;
	}
	.active{
	color:red;
	}
	</style>
  </head>
  <body>
		<div class="main_wrapper">
		   <span class="span_gdsrs">请选择挂账房间：</span>
			<div class="main_content">
			<div id="myrt">
				<ul id="mylabel">
				 <c:forEach var="rs" items="${gdsroomId}" varStatus="aaa">
				    <li>
						<span class="roomid">${rs.roomid}</span>
						<span>${rs.roomtypename}</span>
						<span>${rs.checkusername}</span>
						<span class="span_gdsrs_hide">${rs.checkid}</span>   
						</li>  
				 </c:forEach>
				</ul>
				</div>
				
			</div>
			<div class="gdsfooter_content">
			
				<input type="button" class="gdsroomselectbotton" value="取消" onclick="window.parent.JDialog.close()"/>
				<input type="button" class="gdsroomselectbotton" value="确定" onclick="gdsrmselect()"/>
			</div>
		</div>
	<script src="script/ipms/js/jquery-1.8.2.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script>
		$(".footer_content ul .footer_li").on("click",function(){
			$(this).addClass("active");
			$(this).siblings().removeClass("active");
			
		});
		
		 //$(".main_wrapper ul li").on("click",function(){
 	     //$(this).addClass("active");
 	     //$(this).removeClass("active");
         // })
		
		function showMsg(msg, fn) {
				if (!fn) {
					TipInfo.Msg.alert(msg);
				} else {
					TipInfo.Msg.confirm(msg, fn);
				}
			}
		
		
		var roomid = "";
		var checkid= ""
		var lis = Array.prototype.slice.call(document.getElementById('myrt').getElementsByTagName('li'));
        var len = lis.length;
		 $(function(){		 
                $("#myrt ul li").click(function(){
                     var activeLen = document.querySelectorAll('.active').length;
			        if (this.classList.contains('active')) {
			            this.classList.remove('active')
			        } else {
			            if (!activeLen) {
			                this.classList.add('active');
			                 roomid = $(this).find("span:eq(0)").html();
                             checkid = $(this).find("span:eq(3)").html();
			            }else{
			                lis.forEach(function(item){
			                    item.classList.remove('active')
			                })
			                this.classList.add('active')
			                 roomid = $(this).find("span:eq(0)").html();
                            checkid = $(this).find("span:eq(3)").html();
			            }
                  ///roomid = $(this).find("span:eq(0)").html();
                  //checkid = $(this).find("span:eq(3)").html();
                }
   
            })
            
            })
  function gdsrmselect(){
            if(roomid==""){
              showMsg("请选择一个房间挂账！")
              }else{
                window.parent.$("#gdsroomid").val(roomid);
                window.parent.$("#gdscheckid").val(checkid);
                window.parent.JDialog.close();
              }
		}
	
	</script>
	</body>
</html>
