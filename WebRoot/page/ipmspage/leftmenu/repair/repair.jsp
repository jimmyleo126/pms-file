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
  	<title>维修申请查询</title>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/pagenation.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/leftmenu/left_order.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css">
	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/need/laydate.css"/>
	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/skins/molv/laydate.css"/>
    <script>
      var base_path = "<%=request.getContextPath()%>";
	</script>
	
  </head>
  <body >
		<div class="box_popup_member box_popup_member_margin leftorder_margin repair_margin">
		<span class="close_span"><i class="imooc imooc_order" style="color:#3B3E40;" onclick="window.parent.JDialog.close();">&#xe900;</i></span>
		<h3  >查找维修记录</h3>
		<form action="" method="post">
			<div class="content_member">
				<div class="search_style search_repair fl">
					<ul class=" search_member clearfix f1">
						<li>
							<label class="label_name">申请日期:</label>
							<input id="startdate" name="querystartdate" type="text" class="search_text" value="开始日期">
							<input id="enddate" name="queryenddate" type="text" class="search_text" value="结束日期">
						</li>
						<li><label class="label_name">房号:</label><input id="roomid" name="roomid" type="text" class="search_text" ></li>
						<li><label class="label_name">维修物品:</label>
							<select id="equipment" class="search_text">
							<option value="0">--请选择--</option>
							<option value="1">家具</option>
							<option value="2">电器</option>
							<option value="3">厨具</option>
							<option value="4">装饰</option>
							<option value="5">卫浴</option>
							<option value="6">其他</option>
						</select>
						</li>
						<%--<input id="equipment" name="queryequipment" type="text" class="search_text" >--%>
						<li><label class="label_name">状态:</label>
							<select id="checkstatus" class="search_text">
								<option value="3">--请选择--</option>
								<option value="1">未审核</option>
								<option value="0">已取消</option>
								<option value="2">已审核</option>
							</select>
						</li>
						<%--<input id="checkstatus" name="querystatus" type="text" class="search_text" >--%>
						<li><label class="label_name" style="width:40px">手机:</label><input id="mobile" name="querymobile" type="text" class="search_text" ></li>
						<li><input type="button" class="btn_style btn_search button_margin query" onclick="queryRepairDetail()" value="查询"/></li>
						<li><input type="text" class="btn_style btn_search button_margin add" onclick="addRepairDetail()" value="新增申请"/></li>
					</ul>
				</div>
				<div class="content_member_show" style="width: 100%; height:97%;">
					<iframe id="repairIframe" name="repairIframe" class="" frameborder="0" width="100%" height="100%" src="repairData.do "></iframe>
				</div>
			</div>
		</form>
	</div>
	
	<%@ include file="../../../common/script.jsp"%>
	<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/ipms/js/leftmenu/repair.js"></script>
	<%--<script  src="<%=request.getContextPath()%>/script/ipms/js/jquery-1.8.2.min.js"></script>
	<script src="<%=request.getContextPath()%>/css/ipms/jquery-easyui/jquery.min.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/script/ipms/js/classie.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/css/ipms/jquery-easyui/jquery.easyui.min.js" type="text/javascript"></script>
	--%>
	<script src="<%=request.getContextPath()%>/script/ipms/js/laydate.dev.js" charset="utf-8"></script>
	
	<script>
		Pager.renderPager(<%=pagination%>);
	</script>
	<script>
	laydate({
    	elem: '#enddate'
	});
	laydate({
    	elem: '#startdate'
	});
	//添加维修申请表记录
	function addRepairDetail(){
		JDialog.open("添加维修记录",base_path +"/addRepairApplication.do",750,430);
	}
	
	</script>
  </body>
</html>
