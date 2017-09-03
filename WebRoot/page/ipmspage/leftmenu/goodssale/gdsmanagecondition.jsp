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
	<style>
	body{
		background:transparent !important;
		overflow: hidden;
	}
   .imooc_order{
		top:-10px;
		left:96%;
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
   
   </style>
  </head>
  <body>
  	   <form id="pagerForm" name="pagerForm" action="" target="_self">
	    <div class="sale_div" style="height:547px;">
	    	<table id="myTable" class="myTable" border="0" width="100">
				<thead id="log">
					<tr>
					    <th class="header">选择</th>
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
					<c:forEach items="${gdsmanageCondition}" var="gdsmanageCondition">
						<tr>
						    <td><input name="gdscheckbox" type="checkbox"/></td>
							<td>${gdsmanageCondition["GOODSID"]}</td>
							<td>${gdsmanageCondition["GOODSNAME"]}</td>
							<td>${gdsmanageCondition["CATEGORYNAME"]}</td>
							<td>${gdsmanageCondition["PRICE"]}</td>
							<td>${gdsmanageCondition["INTEGRAL"]}</td>
							<td>${gdsmanageCondition["SPECIFICATIONS"] }</td>
							<td>${gdsmanageCondition["UNIT"] }</td>
							<td>${gdsmanageCondition["REMARK"] }</td>
							<td name="gmstatus" style="display:none;">${gdsmanageCondition["STATUS"] }</td>
							<td id="" name="gmsup"><input name="gdsdown" type="button" class="gdsbutton" onclick="goodsdown()" value="下架"/></td>
							<td id="" name="gmsdown" style="dispaly:none;"><input name="gdsup" type="button" class="gdsbutton" onclick="goodsup()" value="上架"/></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
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
	<script>
		Pager.renderPager(<%=pagination%>);
		$(document).ready(function(){
		var s = $("td[name='gmstatus']").html();
		if(s=="1"){
		 $("td[name='gmsdown']").hide();
		 $("td[name='gmsup']").show();
		}else{
		 $("td[name='gmsdown']").show();
		 $("td[name='gmsup']").hide();
		}
		
		})
		
	</script>
  </body>
</html>
