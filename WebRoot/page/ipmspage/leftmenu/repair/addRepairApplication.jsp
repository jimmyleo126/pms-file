<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@ page import="com.ideassoft.core.page.Pagination"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
	String sysdate = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
%>
<!DOCTYPE HTML>
<html>
  <head>
  	<title>维修申请</title>
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
			<div class="margintop add_repair">
				<input type="hidden" id="roomtype" value=""/>
				<ul class="clearfix">
					<li><label class="label_name">房号：</label><input id="roomid" name="roomid" type="text" class="search_text" onclick="selectRoomIdDetail()"></li>
					<li><label class="label_name">合同编号：</label><input id="contrartid" name="contrartid" type="text" class="search_text" value=""  disabled/></li>
					<li><label class="label_name">申请人姓名：</label><input id="applyname" name="applyname" type="text" class="search_text" value="" disabled></li>
					<li><label class="label_name">手机号码：</label><input id="applymobile" name="mobile" type="text" class="search_text" disabled></li>
					<li><label class="label_name">维修物品：</label>
						<select id="equipment_select" class="text_add search_text">
							<option value="1">家具</option>
							<option value="2">电器</option>
							<option value="3">厨具</option>
							<option value="4">装饰</option>
							<option value="5">卫浴</option>
							<option value="6">其他</option>
						</select>
					</li>
					<li><label class="label_name">紧急维修：</label> 
						<select id="emegent_select" class="text_add search_text"><option value="1">是</option><option value="2">否</option></select>
					</li>
					<li><label class="label_name">申请维修日期：</label><input id="repairTime" name="repairTime" type="text" class="date_text search_text" value="<%=sysdate %>"/></li>		
					<li ><label class="label_name">申请备注：</label><textarea id="repairRemark" name="remark" class="date_text search_text"></textarea></li>
					<li>
						<input name="confirm" type="button" value="确定" class="button_margin confirm" onclick="addRepairRecord()">
					</li>
				</ul>
			</div>
		</form>
	</div>
	<div class="roomtype_class" id="roomtype_class" style="display:none;">
		<div class="class_main">
			<ul class="clearfix ">
				<li><span  id="roomid" onclick="showSimpleRoomD1(this)">极简户型(D1)</span></li>
				<li><span  id="roomid" onclick="showSimpleRoomD2(this)">极简户型(D2)</span></li>
				<li><span  id="roomid" onclick="showAgileRoom(this)">雅居户型(D3)</span></li>
				<li><span  id="roomid" onclick="showLuxuryRoom(this)">奢华户型(D4)</span></li>
			</ul>
			<div class="class_detail">
				<table>
					<tr id="room_date">
					</tr>
				</table> 
				<input type="button" id="roomid" class="button_margin confirm" value="确定" onclick="chooseRoom()"/>
			</div>
		</div>
	</div>
	<%@ include file="../../../common/script.jsp"%>
	<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
	<script src="<%=request.getContextPath()%>/script/ipms/js/leftmenu/repair.js"></script>
	<script  src="<%=request.getContextPath()%>/script/ipms/js/jquery-1.8.2.min.js"></script>
	<script src="<%=request.getContextPath()%>/script/ipms/js/classie.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/css/ipms/jquery-easyui/jquery.min.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/css/ipms/jquery-easyui/jquery.easyui.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/ipms/js/laydate.dev.js" charset="utf-8"></script>
	<script>
		var path="<%=request.getContextPath()%>";
		laydate({
	        	elem: '#repairTime'
    	});
		  $(document).ready(function(){
				$("#roomid").focus();//默认光标选中
			    })
	</script>
  </body>
</html>
