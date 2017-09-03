<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/script.jsp"%>
<%@ include file="../../common/taglib.jsp"%>
<%@ include file="../../common/css.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>会员会级</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/memberUpGrade.css"/>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/reset.css"/>
	<script src="<%=request.getContextPath()%>/script/pageFunctions.js"></script>
	<script>
      var base_path = "<%=request.getContextPath()%>";
	</script>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
   	<div>
   		<div>
 			<table class="table_account">
   			 <!-- <tr>
	    		<td class="membercard_td">
	    			<input id="1" class="membercard" type="button" value="注册会员" onclick="payUpGradeMemberLevel(this)"/>
	    		</td>
	    	</tr> -->
			<c:if test="${memberrank == '0'}">
				<div style="text-align: center;color: red;padding: 10%;">
					公司会员不可以升级!
				</div>
			</c:if>
			<c:if test="${memberrank == '5'}">
				<div style="text-align: center;color: red;padding: 10%;">
					该会员已到当前最高级别!
				</div>
			</c:if>
			<c:if test="${memberrank == '1'}">
				<div style="text-align: center;color: red;padding: 10%;">
					请注册会员!
				</div>
			</c:if>
			<c:if test="${memberrank >= '2' && memberrank < '3'}">
				<tr>
					<td class="membercard_td">
						<input id="3" class="membercard" type="button" value="银卡会员" onclick="payUpGradeMemberLevel(this)"/>
					</td>
				</tr>
			</c:if>
			<c:if test="${memberrank >= '2' && memberrank < '4'}">
				<tr>
					<td class="membercard_td">
						<input id="4" class="membercard" type="button" value="金卡会员" onclick="payUpGradeMemberLevel(this)"/>
					</td>
				</tr>
			</c:if>
			<c:if test="${memberrank >= '2' && memberrank < '5'}">
				<tr>
					<td class="membercard_td">
						<input id="5" class="membercard" type="button" value="铂金会员" onclick="payUpGradeMemberLevel(this)"/>
					</td>
				</tr>
			</c:if>
			<c:if test="${memberblack == '6'}">
				<tr>
					<td class="membercard_td">
						<input id="6" class="membercard" type="button" value="黑钻会员" onclick="payUpGradeMemberLevel(this)"/>
					</td>
				</tr>
			</c:if>
			</table>
			<input id="accountid" type="hidden" value="${accountid }" />
			<input id="memberid" type="hidden" value="${memberid }" />
			</div>
   	</div>
	<!-- <div>
		<input class="cancel" type="button" value="取消" onclick="window.parent.location.reload(true);"/>
	</div> -->
  </body>
</html>
