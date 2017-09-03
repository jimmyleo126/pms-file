<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@ page import="com.ideassoft.core.page.Pagination"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../../../common/script.jsp"%>
<%@ include file="../../../common/css.jsp"%>
<%
	JSONObject pagination = JSONObject.fromObject(request.getAttribute("pagination"));
	request.setAttribute("categoryCondition", request.getAttribute("categoryCondition")); 		
%>

<!DOCTYPE HTML>
<html>
  <head>
    <title>保洁</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-ui.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css"/>
    <link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/reset.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/shopsell/shopsell.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/pagenation.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/pagination.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style.css" />
    <link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/need/laydate.css"/>
	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/skins/molv/laydate.css"/>
    <link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
	<style>
 
	</style>
  </head>
  <body style="overflow:hidden;">
    <div class="check_wrap_margin load_margin">
		<div class="shop_content  margin_gdspage clean_record">
			<h2>保洁申请</h2>
			<div class="shop_search" style="height:625px;">
				<ul>
			 	  <li>
					<label class="label_name">开始日期</label>
					<input type="text" id="cleanstarttime" name="cleanstarttime" class="date_text text_search" value="" onblur="cc()" />
					<label class="label_name">截止日期</label>
					<input type="text" id="cleanendtime" name="cleanendtime" class="date_text text_search" value=""  />
					<select id="timeArea" name="timeArea" class="search_select">
					        <option  value="">时段</option> 
					        <option  value="0">上午</option> 
                            <option  value="1">下午</option>  
					</select>
					<select id="status" name="status" class="search_select">
					        <option  value="">处理状态</option> 
					        <option  value="1">未处理</option> 
                            <option  value="2">已处理</option>  
					</select>
					<input type="button" name="" id="" class="gdsbutton" value="查询" onclick="search()"/>
				 <input type="button" name="" id="" class="gdsbutton" value="新增申请" onclick="addnewapply()"/>
<!--                 <input type="button" name="" id="" class="setbutton manysale" value="设置可预约数量" onclick="setAmount()"/>-->
				   </li>
				</ul>
				<div class="div_iframe" style="height: 554px;">
					<iframe name="applicationIframe" id="applicationIframe" frameborder="0" width="100%" height="100%" src="querycleanapply.do"></iframe>
				</div>
			</div>
		</div>
	</div>
	    <script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
		<script src="<%=request.getContextPath()%>/script/ipms/js/index.js" type="text/javascript" charset="utf-8"></script>
<!--	    <script src="<%=request.getContextPath()%>/script/ipms/js/room_urban/jquery-labelauty.js"></script>-->
	    <script src="<%=request.getContextPath()%>/script/ipms/js/laydate.dev.js" charset="utf-8"></script>
	    <script src="<%=request.getContextPath()%>/script/ipms/js/leftmenu/clean/applydata.js"></script>
	    <script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script>
		 var base_path ="<%=request.getContextPath()%>";
		 laydate({
	        	elem: '#cleanstarttime'
	        	
    		});
    		laydate({
	        	elem: '#cleanendtime'
	        	
    		});
    		
	function cc(){
	//关闭控件
    Dates.close = function(){
    Dates.reshow();
    Dates.shde(Dates.query('#'+ as[0]), 1);
    Dates.elem = null;
       };
	}
		function search(){
			var startdate = $("#cleanstarttime").val();
			var enddate = $("#cleanendtime").val();
			var timeArea = $("#timeArea").val();
			var status = $("#status").val();
			$("#applicationIframe").attr("src",base_path+"/querycleanapplycondition.do?startdate="+startdate+"&enddate="+enddate+"&timeArea="+timeArea+"&status="+status);
		}
		
		function addnewapply(){
		JDialog.open("添加保洁申请",base_path +"/addnewapply.do",950,500);
		
		}
		
		</script>
  </body>
</html>
