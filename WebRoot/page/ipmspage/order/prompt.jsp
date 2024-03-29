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
    
    <title>订单-提示</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/chummageplan.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/pagination.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/pagenation.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css">
  </head>
  
  <body>
  <form action = "loadOrderPromptData.do" id = "myform" name = "myform" method = "post" >
    <div class="prompt_width">
    		<div>
    			<input id="checkid" name="checkid" type="hidden" value="${checkid}">
    		</div>
    		<div class="prompt_header">
    			<span id="add" class="button">增加提示信息</span>
    		</div>
			<div class="prompt_one">
				<section class="box_content_widget fl">
					<div class="content" style="height:519px;">
						<table id="myTable" class="myTable" border="0" >
							<thead>
								<tr>
									<th class="header">日志号</th>
									<th class="header">提交者</th>
									<th class="header">提交时间</th>
									<th class="header">内容</th>
									<th class="header">阅读者</th>
									<th class="header">阅读时间</th>
									<th class="header">状态</th>
									<th class="header">操作</th>
								</tr>
							</thead>
							<tbody id="orderprompt_date" >
								<c:forEach items="${result}" var="result">
									<tr ondblclick="dbclick(this)">
										<td>${result["LOGID"] }</td>
										<td>${result["RECORDUSER"] }</td>
										<td>${result["RECORDTIME"] }</td>
										<td>${result["CONTENT"] }</td>
										<td>${result["READER"] }</td>
										<td>${result["READTIME"] }</td>
										<td>${result["STATUS"] }</td>
										<td onclick="cancelPrompt(this)"><span class="pro_cancel">取消</span></td>
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
		<script type="text/javascript" src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script>
			var path="<%=request.getContextPath()%>";	

			<%--$(function(){
				$.ajax({
			         url: path + "/loadOrderPromptData.do",
					 type: "post",
					 data : { },
					 success: function(${result}) {
						 console.log(${result})
						 loadOrderPromptData(${result});				 	
					 },
					 error: function(${result}) {}
				});
				});
			function loadOrderPromptData(json){
				
				var tablePrompt;
				$.each(json,function(index){
					tablePrompt = tablePrompt+"<tr class='odd'>"+
					"<td>"+json[index]['LOGID']+"</td>"+
					"<td>"+json[index]['RECORDUSER']+"</td>"+
					"<td>"+dealDate(json[index]['RECORDTIME'])+"</td>"+
					"<td>"+json[index]['CONTENT']+"</td>"+
					"<td>"+json[index]['READER']+"</td>"+
					"<td>"+dealDate(json[index]['READTIME'])+"</td>"+
					"</tr>";
					});
				if(json.length == 0){
			 		$("#orderprompt_date").html("");
			 	}else{
			 		$("#orderprompt_date").html(tablePrompt);			 	
			 	}
				}--%>
				var checkid ="<%=request.getParameter("checkid")%>";
				$("#add").bind("click",function(){
				
					if(isCheckout(checkid)){
						return false;
					}
					
					//window.location.href="addPromptRecord.jsp?checkid="+checkid;
					window.parent.JDialog.open("添加提示信息",path+"/showPromptRecord.do?checkid="+checkid,450,250);
					});
				function dbclick(e){
				
					if(isCheckout(checkid)){
						return false;
					}
					
					var logid = $(e).children().eq(0).html();						
					window.parent.JDialog.open("提示信息日志详情",path+"/showPromptRecordDetail.do?logid="+logid+"&checkid="+checkid,450,250);
					}
				function cancelPrompt(e){
				
					if(isCheckout(checkid)){
						return false;
					}
				
					var logid = $(e).parent().children().eq(0).html();
					//alert($(e).html());
					 $.ajax({
				         url: path + "/cancelPromptRecord.do",
						 type: "post",
						 data : {"logid" : logid },
						 success: function(json) {
							showMsg(json.message);
							setTimeout("location.reload(true)",2000);	
						 },
						 error: function() {}
					});
					}
				Pager.renderPager(<%=pagination%>);
		</script>
  </body>
</html>
