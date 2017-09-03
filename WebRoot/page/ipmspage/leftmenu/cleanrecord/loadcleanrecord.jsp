<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@ page import="com.ideassoft.core.page.Pagination"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../../../common/script.jsp"%>
<%
	JSONObject pagination = JSONObject.fromObject(request.getAttribute("pagination"));
	request.setAttribute("categoryCondition", request.getAttribute("categoryCondition")); 		
%>

<!DOCTYPE HTML>
<html>
  <head>
    <title>保洁记录</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/css/pagenation.css" />
	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/pagination.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css"/>
    <link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/reset.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/shopsell/shopsell.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
    <link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/need/laydate.css"/>
	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/skins/molv/laydate.css"/>
	
	
	<style>
	</style>
  </head>
  <body style="overflow:hidden;">
    <div class="check_wrap_margin  load_margin">
		<div class="shop_content  margin_gdspage clean_record">
			<h2>保洁记录</h2>
			<div class="shop_search" style="height:720px;">
				<ul class="clearfix">
				  <li>
					<label class="label_name">保洁日期</label>
					<input type="text" id="cleantime1" name="cleantime1" class="date_text text_search" value=""  />
					<select id="timeArea" name="timeArea" class="search_select">
				        <option  value="0">未完成</option> 
                        <option  value="1">已完成</option>  
					</select>
					<input type="button" name="" id="" class="gdsbutton" value="查询" onclick="searchrecord()"/>
				   </li>
				</ul>
				<div class="div_iframe" style="height:585px;">
					<iframe name="recordIframe" id="recordIframe" frameborder="0" width="100%" height="100%" src="querycleanrecord.do"></iframe>
				</div>
			</div>
		</div>
	</div>
	    <script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
		<script src="<%=request.getContextPath()%>/script/ipms/js/index.js" type="text/javascript" charset="utf-8"></script>
        <script src="<%=request.getContextPath()%>/script/ipms/js/laydate.dev.js" charset="utf-8"></script>		
        <script>
		 var path ="<%=request.getContextPath()%>";
		 laydate({
	        	elem: '#cleantime1'
    		});
    		
    		
		function searchrecord(){	 
		 var date = $("#cleantime1").val();
		 //alert(date)
		 var status = $("#timeArea").val();
		// alert()
		$("#recordIframe").attr("src","querycleanrecordcondition.do?time="+date+"&status="+status);
		}
		</script>
  </body>
</html>
