 <%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../../common/taglib.jsp"%>
<%@ include file="../../../common/css.jsp"%>
<%@ include file="../../../common/script.jsp"%>
<% request.setAttribute("basePath", request.getContextPath());
%>

<!DOCTYPE HTML>
<html>
  <head>
    <title>新增保洁申请</title> 
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/order/order_details.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
    <link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/need/laydate.css"/>
	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/skins/molv/laydate.css"/>
    <link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/leftmenu/leftmenu.css"/>
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
<!--	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/leftmenu/left_order.css"/>-->
	<script>
      var base_path = "<%=request.getContextPath()%>";
	</script>
	<style>
	
.class_main{
    position: absolute;
    top: 7%;
    left: 18%;
    height: 338px;
    width: 500px;
    background: palegoldenrod;
    border: 1px solid #0077b3;
    z-index: 999;
}
.class_main ul li{
	float: left;
    
    
}
.input_ms{
position: relative;

}


	</style>
  </head>
  <body>
  <div class="member_margin ">
	  <form action="submitnewcleanapplication.do" method="post" id="addnewapplyform">
	      <section class="detail_one">
	          <input type="hidden" id="roomtype" name="roomtype" value="${branchId}"/>
	           <input type="hidden" id="memberId" name="memberId" value="${memberId}"/>
			  <table class="table_ms">
			  <tr>
			    <td align="right"><span>房间号</span></td>
			    <td colspan="5"><input id="roomid" name="roomid" class="input_ms" type="text" onclick="checkroomid()"/></td>
			    <div id="allroomid" class="class_main" style="display:none"></div>
			  </tr>
			  <tr>
			    <td align="right"><span>合同编号</span></td>
			    <td ><input id="contrartid" name="contrartid" class="input_ms" type="text" readonly="readonly"/></td>
<!--			   <td align="right"><span>门店编号</span></td>-->
<!--			  <td><input id="branchid" name="branchid" class="input_ms" type="text" readonly="readonly"/></td>-->
			    <td align="right"><span>预约人</span></td>
			    <td><input id="reservedperson" name="reservedperson" class="input_ms" type="text" readonly="readonly"/></td>
			  </tr>
			  <tr>
			    <td align="right"><span>手机号</span></td>
			    <td><input id="mobile" name="mobile" class="input_ms" type="text" readonly="readonly"/></td>
			    <td align="right"><span>保洁日期</span></td>
			    <td><input id="cleantime" name="cleantime" class="date_text text_search" type="text" /></td>
			  </tr>
			  <tr>
			    <td align="right"><span>时间段</span></td>
			    <td>
			      <select id="timeArea" name="timeArea" class="text_search">
					        <option  value="0">上午</option> 
                            <option  value="1">下午</option>  
				  </select>
				</td>
				<td align="right"><span>保洁类型</span></td>
				<td>
			      <select id="cleanstyle" name="cleanstyle" class="text_search">
					        <option  value="0">免费</option> 
                            <option  value="1">收费</option>  
                            <option  value="2">赠送</option> 
                            <option  value="3">其他</option>  
				  </select>
				</td>
			  </tr>
			  <tr>
			    <td align="right"><span>备注</span></td>
			    <td colspan="5"><textarea id="remark" name="remark" class="textarea_ms" type="text"/></textarea></td>
			  </tr>
		      </table>
		   </section>   			    
          <div class="editdata_ms">
<!--             <input type="button"   class="button_margin submitbotton_membersearch two_button" onclick="window.parent.JDialog.close();" value="取消" />-->
             <input type="button" class="button_margin submitbotton_membersearch two_button" onclick="submitform()" value="确定">
          </div>
          </form>
       </div>
	
	<script src="<%=request.getContextPath()%>/script/ipms/js/laydate.dev.js" charset="utf-8"></script>
<!--	<script src="<%=request.getContextPath()%>/script/common/jquery-ui.min.js"></script>-->
<!--	<script src="<%=request.getContextPath()%>/script/common/jquery.dialog.js"></script>-->
<!--	<script src="<%=request.getContextPath()%>/script/common/jquery.jqGrid.src.js"></script>-->
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
<!--	<script src="<%=request.getContextPath()%>/script/ipms/js/leftmenu/membersearch.js"></script>-->
<script >
	 var base_path ="<%=request.getContextPath()%>";
		laydate({
	    	elem: '#cleantime'
		});
		
		function checkroomid(){
		//alert(123)
		document.getElementById("allroomid").innerHTML='<iframe src="' + base_path +'/page/ipmspage/leftmenu/handleapply/checkroom.jsp" width="100%" height="100%" frameborder=no  scrolling="no"></iframe>'
		
		
		}
		function submitform(){
		if($("#cleantime").val() == null || $("#cleantime").val() == ""){
		showMsg("日期不能为空！");
		return false;
		}
		
		var inputdate = new Date(Date.parse($("#cleantime").val()));
	    var today = new Date();
	    var year = today.getFullYear(); 
	    var month = today.getMonth() + 1;
	    var day = today.getDate();
	    var new_today = new Date(year,month -1,day);
	   
	   //日期校验
	    if(inputdate < new_today){
		  showMsg("日期不能小于当天!");
		   return false;
	  }
	  if(inputdate > getLastDay(year,08)){
	      showMsg("日期不能超过当月最大天数!");
		   return false;
	  }
	  
	  
	  
		$.ajax({
		         url: base_path + "/submitnewcleanapplication.do",
				 type: "post",
				 data: $("#addnewapplyform").serialize(),
				 success: function(json) {
				    showMsg(json.message);
		      		window.setTimeout("window.parent.location.reload(true)",1800);
				 },
				 error: function(json) {}
		
		});
		
		
		}
	//获取当月最大日期	
		function getLastDay(year,month) { 
		    var new_year = year;    //取当前的年份          
		    var new_month = ++month;//取下一个月的第一天，方便计算（最后一天不固定）       
		    if(month>11) {         
		     new_month -=12;        //月份减          
		     new_year++;            //年份增          
		    }         
		    var new_date = new Date(new_year,new_month-1,1); 
		    // var new_date = new Date(new_year,0,1); 
		       //alert(new_date)        //取当年当月中的第一天          
		    return (new Date(new_date.getTime()-1000*60*60*24));//获取当月最后一天日期          
		} 
		function showMsg(msg, fn) {
			if (!fn) {
				TipInfo.Msg.alert(msg);
			} else {
				TipInfo.Msg.confirm(msg, fn);
			}
		}
		
		$("#roomid").on("click",function(){
			$("#allroomid").css("display","block");
		})
		
document.documentElement.onclick=function(e){
	e= window.event ? window.event : e;
	var e_tar=e.srcElement ? e.srcElement : e.target;
	if(e_tar.id=="roomid"){
		return;
	}else{
		/*$("#equipment_class").css("display", "none");*/
		$("#allroomid").css("display", "none");
	}
};
	</script>
  </body>
</html>
