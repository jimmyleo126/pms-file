<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@ page import="com.ideassoft.core.page.Pagination"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	JSONObject pagination = JSONObject.fromObject(request.getAttribute("pagination"));
%>
<!DOCTYPE HTML>
<html>
  <head>
    <link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlistfont.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/pagination.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/roomlist.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/shopsell/shopsell.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/pagenation.css" />
	<style><!--
   .imooc_order{
		top:-10px;
		left:96%;
	}
	body{
		background:transparent !important;
		overflow: hidden;
	}
	.ostable{
	height: 10%;
    border: none;
    margin-top: 1%;
    margin-bottom: 1%;
	}
	.osinput{
	width:90%;
	padding: 7px 5px;
	}
	.ostr{
	border-bottom: 1px solid #e9e9e9;
	}
	.ostbcontent{
	height:70%;
	overflow-y: scroll;
	}
	.ostdh{
		height:45px;
	}
   .singlesalediv{
	    position:absolute;
	    width:400px;
	    height:300px;
	    left:50%;
	    top:200px;
	    display:block;
	    z-index:99;
   }
	#mask{ 
	    z-index: 999;
	     left:0;
	    top:0;
	    position: absolute;
	    background-color: #000000;
	    filter: alpha(opacity = 25);
	    -moz-opacity: 0.25;
	    opacity: 0.25;
	    }
   </style>
  </head>
  <body id="aaa">
  	    <form id="pagerForm" name="pagerForm" action="" target="_self">
	    <div class="sale_div" style="height:547px;">
	    	<table id="myTable" class="myTable" border="0" width="100">
				<thead id="log">
					<tr>
					    <th class="header"><input name="chooseAll" id="chooseAll" type="checkbox"/>选择</th>
						<th class="header">商品编号</th>
						<th class="header">商品名称</th>
						<th class="header">商品分类</th>
						<th class="header">商品售价</th>
						<th class="header">兑换积分</th>
						<th class="header">商品规格</th>
						<th class="header">商品单位</th>
						<th class="header">备注</th>
						<th class="header">操作</th>
					</tr>
				</thead>
				<tbody id="info">
					<c:forEach items="${goodsSale}" var="goodsSale">
						<tr>
							<td><input name="gdscheckbox" type="checkbox" /></td>
							<td>${goodsSale["GOODSID"]}</td>
							<td>${goodsSale["GOODSNAME"]}</td>
							<td>${goodsSale["CATEGORYNAME"]}</td>
							<td>${goodsSale["PRICE"]}</td>
							<td>${goodsSale["INTEGRAL"]}</td>
							<td>${goodsSale["SPECIFICATIONS"] }</td>
							<td>${goodsSale["UNIT"] }</td>
							<td>${goodsSale["REMARK"] }</td>
							<td><input name="gdsdown" type="button" class="gdsbutton" value="下架" onclick="goodsdown()"/></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div id="manysalecontent"></div>
		<div id="pager"></div>
		<div>
			<input type="hidden" id="source" name="source" value=""/>
			<input type="hidden" id="status" name="status" value=""/>
		</div>
	</form>
	<%@ include file="../../../common/script.jsp"%>
	<script src="<%=request.getContextPath()%>/script/ipms/js/integral/integral.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
	<script type="text/javascript">var base_path = "<%=request.getContextPath()%>";</script>
	<script src="<%=request.getContextPath()%>/script/ipms/js/leftmenu/goodsmanage.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
  </body>
</html>
