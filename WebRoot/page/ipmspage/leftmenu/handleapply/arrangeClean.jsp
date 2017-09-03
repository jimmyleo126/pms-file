<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ include file="../../../common/script.jsp"%>
<%@ include file="../../../common/css.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
  <head>
    <title>安排保洁人员</title>
   <link rel="stylesheet"   href="<%=request.getContextPath()%>/css/ipms/css/roomlist/customer.css" />
	<link rel="stylesheet" id="style" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/roomlist_check.css"/>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css"/>
	<link href="//cdn.bootcss.com/font-awesome/4.6.3/css/font-awesome.min.css"  rel="stylesheet">
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/ipms/css/pagenation.css" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/ipms/pagination.css" />
  </head>
  <body>
  <div class="get_color">
  <form id="arrangeclean" class="arrangeclean">
  <input type="hidden" id="amount" name="amount" value="<%=request.getParameter("amount")%>"/>
        <section class="detail_one">
            <ul class="clearfix">
				<li><label class="label_name">保洁编号：</label><input id="recordId" name="recordId" type="text" value="<%=request.getParameter("cleanApplication")%>" class="text_search" readonly="readonly"></li>
				<li><label class="label_name">保洁日期：</label> <input id="cleanTime" name="cleanTime" type="text" value="<%=request.getParameter("getCleanTime")%>" class="text_search" readonly="readonly"></li>
				<li><label class="label_name">门店编号：</label> <input id="branchId" name="branchId" type="text" value="<%=request.getParameter("branchId")%>" class="text_search" readonly="readonly"></li>
				<li><label class="label_name">房间号：</label> <input id="roomId" name="roomId" type="text" value="<%=request.getParameter("roomid")%>" class="text_search" readonly="readonly"></li>
                <li><label class="label_name">时间段	：</label><input id="timeArea" name="timeArea" type="text"  value="<%=request.getParameter("getTimeArea")%>" class="text_search" readonly="readonly"></li>
                <li><label class="label_name">保洁人：</label>
						      	<select name="cleanPerson" class="text_search" id="cleanPerson">
						      		<!-- <option value="0">0</option>
						      		<option value="1">1</option> -->
						      	</select>
				</li>
				<li><label class="label_name">备注：</label> <input id="remark" name="remark" type="text" value="" class="text_edit" ></li>
				<li>
<!--		       <input type="button" id="submitbill" value="取消" onclick="window.parent.JDialog.close()" class="button_margin cancel"/>-->
		       <input type="button" id="submitbill" value="确认" onclick="updatecleanrecord()" class="button_margin confirm"/>
     			</li>	
            </ul>
        </section>
    </form>
    </div>
  
    
    <script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
    <script src="<%=request.getContextPath()%>/script/ipms/js/leftmenu/clean/arrangeClean.js"></script>
    <script src="<%=request.getContextPath()%>/script/common/datepickerCN.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/workbillroom/util.js"></script>
    <script>
    var base_path ="<%=request.getContextPath()%>";
    $(function(){
       getallstaff()
    

    });
    
    function getallstaff(){
  		$.ajax({
		         url: base_path + "/selectallstaff.do",
				 type: "post",
				 data : {},
				 success: function(json) {
				 	console.log(json)
				 	loadstaff(json);
				 },
				 error: function(json) {}
			});
  		
  		function loadstaff(json){
  		var tabledata = "<option value=''>保洁人</option>";
  		$.each(json,function(index){
  		tabledata = tabledata + "<option value='" +json[index]["STAFFID"] + "'>" +json[index]["STAFFNAME"] + "</option>"
  		
  		
  		});
		$("#cleanPerson").html(tabledata);			 	
	
  		}
  		}
    
    
    
    
    function updatecleanrecord(){

			if($("#roomId").val()==""){
			showMsg("请输入房号!")
			
			return false;
			}
			if($("#cleanPerson").val()== ""){
			showMsg("请输入保洁人员!")
			return false;
			}
			var amount = $("#amount").val();
			var getCleanTime = $("#cleanTime").val();
			var getTimeArea =$("#timeArea").val();
			$.ajax({
		         url: base_path + "/updatecleanrecord.do",
				 type: "post",
				 data : $("#arrangeclean").serialize(),
				 success: function(json) {
				                amount--;
								 $.ajax({
										   url: base_path + "/resetamount.do",
										   dataType: "json",
										    type: "post",	
										   data: {
										            'time' : getCleanTime,
										            'timearea' : getTimeArea,
										            'amount' : amount
										            },
										     success: function(json) {    
										       console.log(json.message);
										       },    
										     error:function(json){    
										          showMsg("重新修改房间数时修改失败!"); 
										            window.setTimeout("window.parent.location.reload(true)",1800);
										      }      
										 });

				 window.parent.showMsg(json.message);
		         window.setTimeout("window.parent.location.reload(true)",1800);
				 },	
				 error: function(json) {
				 window.parent.showMsg("设置失败!");
				 window.setTimeout("window.parent.location.reload(true)",1800);
				 }
			});
  		}

    </script>
  </body>
</html>
