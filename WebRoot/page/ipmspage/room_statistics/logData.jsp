<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../common/script.jsp"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@ page import="com.ideassoft.core.page.Pagination"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	JSONObject pagination = JSONObject.fromObject(request.getAttribute("pagination"));	
%>

<!DOCTYPE HTML>
<html>
  <head>
    
    <title>日志</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="css/log.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/pagenation.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/customer.css"/>
<!--    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/pagination.css" />-->
	<style>
		.content{
		    height: 519px;
		}
	</style>
	
  </head>
  
  <body>
  	<form>
   	     <div class="log_width">
  			<input id="checkid" name="checkid" type="hidden" value="${checkid}">
			<input id="branchid" name="type" type="hidden" value="${type}">
			<div class="log_one">
				<section class="box_content_widget fl">
					<div class="content log_table">
						<table id="myTable" class="myTable" border="0">
							<thead>
								<tr>
									<th class="header">操作时间</th>		
									<th class="header">业务编码</th>
									<th class="header">业务类型</th>
									<th class="header">操作员</th>
									<th class="header" style="width: 35%;">内容</th>
								</tr>
							</thead>
							<tbody id="log_data">
							<c:forEach items="${result}" var="result">
									<tr>
										<td>${result["RECORDTIME"] }</td>
										<td>${result["CHECKID"] }</td>
										<td>${result["TYPE"] }</td>
										<td>${result["RECORDUSER"] }</td>
										<td title="${result['CONTENT']}">${result['CONTENT'] }</td>
									</tr>
								</c:forEach>
							</tbody>
							
						</table>
					</div>
					<div id="pager"></div>
				</section>
			</div>
		</div>
		
	</form>		
		
		<script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/workbillroom/util.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
		<script type="text/javascript">
		var path="<%=request.getContextPath() %>";
<%--		$(function(){--%>
<%--			--%>
<%--			$.ajax({--%>
<%--		         url: path + "/logData.do",--%>
<%--				 type: "post",--%>
<%--				 data : {},--%>
<%--				 success: function(json) {--%>
<%--					 loadLogData(json);--%>
<%--				 	--%>
<%--				 },--%>
<%--				 error: function(json) {}--%>
<%--			});--%>
<%--		});--%>
<%--		--%>
<%--		--%>
<%--		--%>
<%--		--%>
<%--  function loadLogData(json){--%>
<%--	  console.log(json)--%>
<%--	  var tabledata;--%>
<%--	 	$.each(json, function(index){--%>
<%--	 		tabledata = tabledata + "<tr class='odd' '>" +--%>
<%--	 		"<td>" + json[index]["CHECKID"] + "</td>" +--%>
<%--	 		"<td>" + json[index]["A"] + "</td>" +--%>
<%--	 		"<td>" + json[index]["RECORDUSER"] + "</td>" +--%>
<%--	 		"<td>" + dealDate(json[index]["RECORDTIME"]) + "</td>" +--%>
<%--	 		"<td title='" + json[index]["CONTENT"] + "' >" + json[index]["CONTENT"] + "</td>" +--%>
<%--	 		"</tr>";--%>
<%--	 	});--%>
<%----%>
<%--	 	if(json.length == 0){--%>
<%--	 		$("#log_data").html("");--%>
<%--	 	}else{--%>
<%--	 		$("#log_data").html(tabledata);			 	--%>
<%--	 	}--%>
<%--  }--%>
        Pager.renderPager(<%=pagination%>);


		</script>
  </body>
  
  
  
  
</html>
