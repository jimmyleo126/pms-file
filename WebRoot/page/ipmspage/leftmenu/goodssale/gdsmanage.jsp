<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@ page import="com.ideassoft.core.page.Pagination"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	JSONObject pagination = JSONObject.fromObject(request.getAttribute("pagination"));
	request.setAttribute("categoryCondition", request.getAttribute("categoryCondition")); 		
%>

<!DOCTYPE HTML>
<html>
  <head>
    <title>商品售卖</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/reset.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/shopsell/shopsell.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/pagination.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css"/>
	<link href="css/font-awesome.min.css" rel="stylesheet" type="text/css">

  </head>
  <body style="background: transparent;overflow:hidden;">
    <div class="check_wrap_margin check_color">
		<div class="shop_content">
			<h2>小商品管理</h2>
			<div class="shop_search" style="height:547px;">
				<form action="" method="post" style="height:100%;">
					<input type="text" name="text" id="goodsid" class="shop_name" value="" placeholder="编码" />
					<input type="text" name="text" id="goodsname" class="shop_name" value="" placeholder="名称" />
					<select id="categoryid" name="" class="search_select">
					        <option  value="">所有分类</option> 
						 <c:forEach var="th" items="${categoryCondition}">  
	                              <option  value="${th.categoryid}"> ${th.categoryname} </option>  
	                              </c:forEach>  
					</select>
					<select id="updowm" name="" class="search_select">
	                       <option  value="1"> 在售 </option>
	                       <option  value="2"> 下架</option>   
					</select>
					<input type="button" name="" id="" class="gdsbutton" value="查询" onclick="gdsearch()"/>
					<iframe name="goodsIframe" id="goodsIframe" frameborder="0" width="100%" height="100%" src="goodsManage.do"></iframe></div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../../common/script.jsp"%>
    <script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
	<script src="<%=request.getContextPath()%>/script/ipms/js/index.js" type="text/javascript" charset="utf-8"></script>
	<script>
	function gdsearch(){	 
	 var goodsid = $("#goodsid").val();
	 var goodsname = $("#goodsname").val();
	 var categoryid = $("#categoryid").val();
	 var status = $("#updowm").val();
	$("#goodsIframe").attr("src","gdmanageCondition.do?goodsid="+goodsid+"&goodsname="+goodsname+"&categoryid="+categoryid+"&status="+status);
	}
	
	</script>
  </body>
</html>
