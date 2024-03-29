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
  	<title>添加修复信息</title>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/pagination.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/leftmenu/left_order.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css">
	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/need/laydate.css"/>
	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/skins/molv/laydate.css"/>
	</head>
  <body>
	<div class="high_header add_repair  fadeIndown">
		<form id="workbill_search" action="" method="post">
			<input id="applyId" value="${applyId}" type="hidden"/>
			<div class="margintop add_repair">
				<ul class="clearfix">
					<li><label class="label_name">问题标签：</label>
						<select id="problemtab_select" class="text_add search_text">
							<option value="">--请选择--</option>
							<option value="1">家电</option>
							<option value="2">网络</option>
							<option value="3">装饰</option>
						</select>
					</li>
					<li><label class="label_name">问题描述：</label><input id="problemdesc" name="problemdesc" type="text" class="search_text" value=""/></li>
					<li><label class="label_name">维修人员：</label><input id="repairPerson" name="applyname" type="text" class="search_text" value="" /></li>
					<li><label class="label_name">修复日期：</label><input id="repairDate" name="date" type="text" class="search_text" value="" /></li>
					<li><label class="label_name">维修时段：</label>
						<select id="timearea_select" class="text_add search_text">
							<option value="">--请选择--</option>
							<option value="0">9.30~11.30AM</option>
							<option value="1">13:00~15:00PM</option>
						</select>
					</li>
					<li><label class="label_name">是否修复:</label> 
						<select id="status_select" class="text_add search_text">
							<option value="0">--请选择--</option>
							<option value="2">是</option>
							<option value="1">否</option>
						</select>
					</li>	
					<li ><label class="label_name">备注：</label><textarea id="repairLogRemark" name="remark" class="date_text search_text"></textarea></li>
					<li>
						<input name="confirm" type="button" value="确定" class="button_margin confirm comfirm_log" onclick="addRepairLog()">
					</li>
				</ul>
			</div>
		</form>
	</div>
	<%@ include file="../../../common/script.jsp"%>
	<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
	<script  src="<%=request.getContextPath()%>/script/ipms/js/jquery-1.8.2.min.js"></script>
	<script src="<%=request.getContextPath()%>/script/ipms/js/classie.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/css/ipms/jquery-easyui/jquery.min.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/css/ipms/jquery-easyui/jquery.easyui.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/ipms/js/laydate.dev.js" charset="utf-8"></script>
	<script src="<%=request.getContextPath()%>/script/ipms/js/leftmenu/repair.js"></script>
	<script>
		Pager.renderPager(<%=pagination%>);
		var path="<%=request.getContextPath()%>";
		
		laydate({
	        	elem: '#repairDate'
    	});
	</script>
  </body>
</html>
