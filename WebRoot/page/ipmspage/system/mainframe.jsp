<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../common/taglib.jsp"%>
<%@ include file="../../common/css.jsp"%>
<%@ include file="../../common/script.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>主界面</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta name="viewport" content="width=device-width,initial-scale=1" />
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">	
	<link rel="Shortcut Icon" href="<%=request.getContextPath()%>/css/ipms/img/backgroundimg/deer.png" type="image/x-icon">
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />	
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/system/mainframe.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/loginfile/box_popup.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css" />
	<link rel="stylesheet" id="style"  href="<%=request.getContextPath()%>/css/fontawesome/css/font-awesome.min.css" />

	<link rel="stylesheet" type="text/css"
			href="<%=request.getContextPath()%>/css/common/jquery-dialog.css" />
	<script type="text/javascript">
	var base_path = "<%=request.getContextPath()%>";
	var img_url="<%=request.getContextPath()%>/css/ipms/img/png-logo/";
	</script>		
 </head>
  <body class="push">
    	<div id="box_mainframe" class="box_mainframe">
    	<!-- 左侧导航 -->
    		<nav class="box_left_sliderbar box_left_vertical fl" id="left_menu">
    			<div class="header_title">
    				<span class="logo_icon">logo</span>
    				<span class="title_word" onclick="sdf(this)">微石青禾系统</span>
    			</div>
    			<!--  <div class="sidebar_fold">
    				<span class="fa fa-bars"></span>
    			</div>-->
				<ul class="box_left_sliderbar_ul" id="accordion">
					<c:if test="${ branch.branchType == '1' || loginBranch.branchId == '100001'}">
						<li class="section"> 
							<div class="link"><i class="fa fa-desktop"></i>常用<i class="fa fa-chevron-down"></i></div>
							<ul class="submenu">
								<li><span class="f1">F1</span><a onclick="orderSerach()">预定入住</a></li>
								<li><span class="f1 f2">F2</span><a onclick="orderNew()">预定</a></li>
								<li class="last"><span class="f1 f3">F3</span><a onclick = "goods()">商品售卖</a></li>
							</ul>	
						</li>
						<li class="section">
							<div class="link"><i class="fa fa-users"></i>会员<i class="fa fa-chevron-down"></i></div>
							<ul class="submenu">
								<li class="last"><span class="f1 f4">F4</span><a onclick="memberSearch()">查询会员</a></li>
							</ul>
							<ul class="submenu">
								<li class="last"><span class="f1 f4">F5</span><a onclick="memberAddLevel()">购卡升级</a></li>
							</ul>
						</li>
						<li class="section">
							<div class="link"><i class="fa fa-bell"></i>日结异常<i class="fa fa-chevron-down"></i></div>
							<ul class="submenu">
								<li class="last"><span class="f1 f4">F6</span><a onclick="exceptionSearch()">日结异常</a></li>
							</ul>
						</li>
						<li class="section">
						<div class="link"><i class="fa fa-tasks"></i>其他<i class="fa fa-chevron-down"></i></div>
							<ul class="light_cash submenu">
								<li class="register"><span class="f1 f5">F7</span><a onclick="pettyCash()">备用金交接</a></li>
							</ul>
						</li>
					</c:if>	
					<c:if test="${ branch.branchType == '2' || loginBranch.branchId == '100001'}">
						<li class="section">
						<div class="link"><i class="fa fa-tasks"></i>公寓管理<i class="fa fa-chevron-down"></i></div>
							<ul class="light_cash submenu">
								<li class="register"><span class=" f5"></span><a onclick="apartmentCheckOut()">处理退房申请</a></li>
								<li class="register"><span class=" f5"></span><a onclick="apartmentRent()">房租到期提醒</a></li>
								<li class="register"><span class=" f5"></span><a onclick="apartmentReserved()">处理公寓预约</a></li>
								<li class="register"><span class=" f5"></span><a onclick="repair()">公寓维修申请</a></li>
								<li class="register"><span class=" f5"></span><a onclick="handleApply()">处理保洁申请</a></li>
							</ul>
						</li>
				    </c:if>
					<li class="section">
					<div class="link"><i class="fa fa-tasks"></i>登录信息<i class="fa fa-chevron-down"></i></div>
						<ul class="light_cash submenu">
							<li class="register"><span class="f1 f5">F10</span><a href="<%=request.getContextPath()%>/loadMainFrame.do">退出</a></li>
						</ul>
					</li>
				</ul>
			</nav>
			<div class="picBox" onclick="switchSysBar()" id="switchPoint">
				<span class="fa fa-chevron-left"></span>
			</div> 
			<!--头部导航 -->
			<div class="wrap_right">
				<div class="header-toggle fl">
					<ul class="header_toggle_ul fl">
						<li class="header_toggle_line active" index="0" id="contentFrame_first"  onclick="changeUrl(this)">
							<img src="<%=request.getContextPath()%>/css/ipms/img/png-logo/room_status.png" alt="房态图">
							<a class="header-toggle-div"><span>房态图</span></a>
						</li>
						<c:if test="${ branch.branchType == '1' || loginBranch.branchId == '100001'}">
							<li class="header_toggle_line " index="1" id="menu_901" onclick="changeUrlone(this)"><img src="<%=request.getContextPath()%>/css/ipms/img/png-logo/count.png" alt="房态统计"/><a class="header-toggle-div"><span>房态统计<span></a></li>
							<li class="header_toggle_line" index="2" id="menu_902" onclick="changeUrltwo(this)"><img src="<%=request.getContextPath()%>/css/ipms/img/png-logo/city.png"  alt="同城房态"/><a class="header-toggle-div"><span>同城房态<span></a></li>
							<li class="header_toggle_line" index="3" id="menu_903" onclick="changeUrlthree(this)"><img src="<%=request.getContextPath()%>/css/ipms/img/png-logo/order.png" alt="订单"/><a class="header-toggle-div"><span>订单<span></a></li>
							<li class="header_toggle_line" index="4" id="menu_904" onclick="changeUrlfour(this)"><img src="<%=request.getContextPath()%>/css/ipms/img/png-logo/icon_room.png" alt="房单" /><a class="header-toggle-div"><span>房单<span></a></li>
							<li class="header_toggle_line" index="5" id="menu_905" onclick="changeUrlfive(this)"><img src="<%=request.getContextPath()%>/css/ipms/img/png-logo/workaccout.png" alt="工作帐"/><a class="header-toggle-div"><span>工作帐<span></a></li>
							<li class="header_toggle_line" index="6" id="menu_905" onclick="changeUrlsix(this)"><img src="<%=request.getContextPath()%>/css/ipms/img/png-logo/stop_sell.png" alt="停售房"/><a class="header-toggle-div"><span>停售房<span></a></li>
							<li class="header_toggle_line" index="7" id="menu_907" onclick="changeIntegral(this)"><img src="<%=request.getContextPath()%>/css/ipms/img/png-logo/log.png" alt="日志"/><a class="header-toggle-div"><span>日志</span><span></a></li>
							<li class="header_toggle_line" index="8" id="menu_906" onclick="reportForms(this)"><img src="<%=request.getContextPath()%>/css/ipms/img/png-logo/report.png" alt="报表"/><a class="header-toggle-div"><span>报表</span><span></a></li>
						</c:if>
					</ul>	
				
				</div>
				<div id="st-scroll">
				</div>
				<div class="mark">
				
				</div>
			</div>
		</div>
		<div class="footer"><%@include file="../system/footer.jsp" %></div>
		<div id="next" style="display:none;">
		</div>
		<!--遮罩层-->
		<script src="<%=request.getContextPath()%>/script/ipms/js/classie.js" type="text/javascript"></script>
		<script src="<%=request.getContextPath()%>/script/ipms/js/index.js"></script>
		<script  src="<%=request.getContextPath()%>/script/ipms/js/mainframe/mainframe.js"></script>
		<script type="text/javascript">
			
		</script>
		
  </body>
</html>
