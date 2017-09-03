<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ include file="../../common/script.jsp"%>
<!DOCTYPE HTML>
<html>
	<head>
		<base href="<%=basePath%>">
		<title>房态</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" id="style" type="text/css"  href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
		<link rel="stylesheet" id="style" type="text/css"  href="<%=request.getContextPath()%>/css/ipms/css/roomlist/roomstate.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css" />	
		<link rel="stylesheet" id="style"  href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
		<link rel="stylesheet" id="style"  href="<%=request.getContextPath()%>/css/iconfont/iconfont.css"/>
	</head> 

	<body>
		<div class="hotel">
			<div class="main_wrapper">
				<div class="main_content">
					<ul class="room_list">
					</ul>
				</div>
				<div class="footer_content">
					<form action="" method="">
						<div class="countroom fl">
							<span>总房数<span class="totalcount"></span>
							</span>
							<span>在住房数<span class="livecount"></span>
							</span>
						</div>
						<ul class="roomstatus">
							<li class="footer_li active" onclick="queryalllist();">
								<span>所有</span>
							</li>
							<li class="footer_li" onclick="queryarrival();">
								<span><img src="./css/ipms/img/backgroundimg/arriving.png"/>预抵</span>
							</li>
							<li class="footer_li" onclick="queryleavelive(1);">
								<span><img src="./css/ipms/img/backgroundimg/leaving.png"/>预离</span>
							</li>
							<li class="footer_li" onclick="queryleavelive(2);">
								<span><img src="./css/ipms/img/backgroundimg/holding.png"/>在住</span>
							</li>
							<li class="footer_li" onclick="queryroom('empty');">
								<span>空净房</span>
							</li>
							<li class="footer_li" onclick="queryroom('dirty');">
								<span>脏房</span>
							</li>
							<li class="footer_li" onclick="queryroom('stop');">
								<span>停售</span>
							</li>
							<li class="dropse_li">
								<select id="dropse_roomtype"  class="mySelect">
									<!-- <option value="">
										所有房型
									</option>
									<option value="SD">
										大床房
									</option>
									<option value="BR">
										商务房
									</option>
									<option value="DS">
										豪华套房
									</option>
									<option value="SR">
										标准间
									</option>
									<option value="BSR">
										商务标准间
									</option>
									<option value="DSR">
										豪华标间
									</option>
									<option value="SKR">
										特色大床房
									</option>
									<option value="SSR">
										套房
									</option>
									<option value="XR">
										X•房
									</option> -->
								</select>
							</li>
							<li class="dropse_li">
								<select id="dropse_floor">
									<!-- <option value="">
										所有楼层
									</option>
									<option value="1">
										1
									</option>
									<option value="2">
										2
									</option>
									<option value="3">
										3
									</option>
									<option value="4">
										4
									</option>
									<option value="5">
										5
									</option>
									<option value="6">
										6
									</option> -->
								</select>
							</li>
							<li class="dropse_li" style="margin-left:100px;">
								<input type="text" name="roomId" id="roomId" value="" placeholder="房号" />
							</li>
							<li class="" >
								<span>打印机</span>
							</li>
						</ul>
					</form>
				</div>
				<div class="setmargin cleanroom" style="display:none">
					 <ul>
						<li onclick="setroom('Z');" class="setclean"><i class="iconfont icon-zangfang"></i><span class="spans">设置脏房<span/></li>
						<!-- <li onclick="setroom('T');" class="setclean"><i class="iconfont icon-tingzhi"></i><span class="spans">设置停售房<span/></li> -->
						<li onclick="refresh();"><i class="iconfont icon-shuaxin"></i><span class="spans">刷新<span/></li>
						<input type="text" id="cleanroomid" style="display:none;"/>
					</ul> 
				</div>
				<div class="setmargin dirtyroom" style="display:none">
					<ul>
						<!-- <li onclick="setroom('Z');" class="setclean"><i class="iconfont icon-zangfang"></i><span class="spans">设置脏房<span/></li> -->	
						<li onclick="turnto('check',this);" class="setclean"><i class="iconfont icon-tuanfangdingdan"></i><span class="spans">转至房单<span/></li>
						<li onclick="turnto('bill', this);" class="setclean"><i class="iconfont icon-zhangdan"></i><span class="spans">转至账单<span/></li>
						<li onclick="turnto('plan', this);" class="setclean"><i class="iconfont icon-jihua"></i><span class="spans">转至房租计划<span/></li>
						<li onclick="turnto('hint', this);" class="setclean"><i class="iconfont icon-tishi"></i><span class="spans">转至提示<span/></li>
						<li onclick="refresh();"><i class="iconfont icon-shuaxin"></i><span class="spans">刷新<span/></li>
						<input type="text" id="dirtyroomid" style="display:none;"/>
						<input type="text" id="thischeckid" style="display:none;"/>
					</ul>
				</div>
				<!-- <div class="setmargin stoproom" style="display:none">
					<ul>
						<li onclick="setroom('0');" class="setclean"><i class="iconfont icon-qingjie"></i><span class="spans">设置清洁房<span/></li>
						<li onclick="refresh();"><i class="iconfont icon-shuaxin"></i><span class="spans">刷新<span/></li>
						<input type="text" id="stoproomid" style="display:none;"/>
					</ul>
				</div> -->
				<div class="setmargin vzroom" style="display:none">
					<ul>
						<li onclick="setroom('0');" class="setclean"><i class="iconfont icon-qingjie"></i><span class="spans">设置清洁房<span/></li>
						<!-- <li onclick="setroom('T');" class="setclean"><i class="iconfont icon-tingzhi"></i><span class="spans">设置停售房<span/></li> -->
						<li onclick="refresh();"><i class="iconfont icon-shuaxin"></i><span class="spans">刷新<span/></li>
						<input type="text" id="vzroom" style="display:none;"/>
					</ul>
				</div>
			</div>
		</div>
	
		<div class="apartment">
			<div class="main_wrapper">
				<div class="main_content">
					<ul class="room_list">
					</ul>
				</div>
			</div>
		</div>
	<script src="<%=request.getContextPath()%>/script/ipms/js/room.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>	
	<script src="<%=request.getContextPath()%>/script/ipms/js/customer/customer.js"></script>
	<script>
		var base_path = "<%=request.getContextPath()%>";
		
		$(function(){
			$(".footer_content ul .footer_li").on("click",function(){
				$(this).addClass("active");
				$(this).siblings().removeClass("active");
			});
		})
	</script>
	</body>
</html>
