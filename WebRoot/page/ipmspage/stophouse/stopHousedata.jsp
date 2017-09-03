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
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/pagenation.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css" />
	<style type="text/css">
		.button_margin{
			float: none;
		    height: 31px;
		    line-height: 29px;
		}
		table.myTable td{
			padding: 1px;
		}
	</style>
  </head>
  <body>
  	<form>
	    <div style="height:824px;">
	    	<table id="myTable" class="myTable" border="0" width="100">
				<thead id="log">
					<tr>
						<th class="header">编号</th>
						<th class="header">房号</th>
						<th class="header">类型</th>
						<th class="header">状态</th>
						<th class="header">开始日期</th>
						<th class="header">结束日期</th>
						<th class="header">创建者</th>
						<th class="header">原因</th>
						<th class="header">备注</th>
						<th class="header">操作</th>
					</tr>
				</thead>
				<tbody id="info">
					<c:forEach items="${result}" var="list">
						<tr ondblclick="showstophaltplan(this)">
							<td>${list["LOGID"] }</td>
							<td>${list["ROOMID"] }</td>
							<td>${list["DECODEHALTTYPE"] }</td>
							<!--<td>${list["USER_ID"] }</td>-->
							<td>${list["DECODESTATUS"] }</td>
							<td>${list["STARTTIME"] }</td>
							<td>${list["ENDTIME"] }</td>
							<td>${list["STAFFNAME"] }</td>
							<td>${list["DECODEHALTREASON"] }</td>
							<td>${list["REMARK"] }</td>
							<c:choose>
								<c:when test="${list.STATUS == 1}">
									<td><input type="button" class="button_margin" value="取消" onclick="tipcancel(this)"/></td>
								</c:when>
								<c:otherwise>
									<td><input type="button" class="button_margin" style="background: #d0d0d8;" value="取消" /></td>
								</c:otherwise>
							</c:choose>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div id="pager"></div>
		<input type="hidden" id="logid" name="logid" value="${ multiqueryhalt.logid }">
		<input type="hidden" id="roomid" name="roomid" value="${ multiqueryhalt.roomid }">
		<input type="hidden" id="halttype" name="halttype" value="${ multiqueryhalt.halttype }">
		<input type="hidden" id="haltreason" name="haltreason" value="${ multiqueryhalt.haltreason }">
		<input type="hidden" id="starttime" name="starttime" value="${ multiqueryhalt.starttime }">
		<input type="hidden" id="endtime" name="endtime" value="${ multiqueryhalt.endtime }">
		<input type="hidden" id="status" name="status" value="${ multiqueryhalt.status }">
	</form>
	<%@ include file="../../common/script.jsp"%>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/workbillroom/util.js"></script>
	<script>
		var path = "<%=request.getContextPath() %>";
		
		Pager.renderPager(<%=pagination%>);
		console.log(${ multiqueryhalt.status })
		
		$(function (){
		});
		
		function showstophaltplan(e){
			var logid = $(e).find("td").first().text();
			window.JDialog.open("停售房编辑", path + "/page/ipmspage/stophouse/stophouse_edit.jsp?logid=" + logid , 812,433);
		}
		
		function tipcancel(e){
			var logid = $(e.parentNode.parentNode).find("td:first").text();
			showMsg("确定取消?", "cancelhaltplan('"+logid+"')");			
		}
		function cancelhaltplan(logid){
			$.ajax({
		         url: path + "/cancelhaltplan.do",
				 type: "post",
				 data : {logid : logid},
				 success: function(json) {
				 	if(json.result == 1){
				 		showMsg(json.message);
				 		setTimeout("$('form').submit();", 2000);
				 	}
				 },
				 error: function(json) {}
			});
		}
		
	</script>
  </body>
</html>
