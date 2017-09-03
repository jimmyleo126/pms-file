<%@ page language="java" import="java.util.*" pageEncoding="utf-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="../../common/taglib.jsp"%>
<%@ include file="../../common/script.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	
%>
<!DOCTYPE HTML>
<html>
	<head>
		<base href="<%=basePath%>">
		<title>首页</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
				<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/index/htmleaf-demo.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/footer.css" />
		<link href="http://cdn.bootcss.com/font-awesome/4.6.3/css/font-awesome.min.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/index/index.css" />
		<script>var base_path = "<%=request.getContextPath()%>";</script>
	</head>
	<body>
		<div class="htmleaf-container">
			<div class="demo_title">
				<span class="name">微石青禾|logo</span>
				<span id="show" class="show"></span>
			</div>
			<div class="img_desktop">
				<img src="<%=request.getContextPath()%>/css/ipms/img/backgroundimg/ff.png" width="1000px" height="500px" />
			</div>
			<div class="demo">
				<div class="container">
					<div class="row">
						<div class="col-md-12">
							<div class="navbar">
							 <span class="navbar_close" onclick="turnToLoginPage();">退出</span>
								<ul class="menu fa">
									<li onclick="turnToPms();">
										<a id ="pms" class="fa fa-h-square"></a>
									</li>
									<c:forEach items="${ subSystemKind }" var="subSystem">
									<li  onclick="turnToSysTem(this)">
										<a class="fa"  id ="${subSystem.key}"></a>
										<span class="subSystem_two">${ subSystem.value }</span>
									</li>
									<script type="text/javascript">
										var subsystem = "#${subSystem.key}";
										var systemStyle = "fa-${subSystem.key}";
										$(subsystem).addClass(systemStyle);
									</script>
									</c:forEach>
									<li>
									</li>
								</ul>
							</div>
						</div>
					</div>
				</div>

			</div>
		</div>
		<div class="footer"><%@include file="../system/footer.jsp"%></div>
		<script>
			var subSystemKind =${subSystemKind};
			$("	.navbar_close").on("mousedown",function(){
				$(".navbar_close").addClass("active");
			});
			$("	.navbar_close").on("mouseout",function(){
				$(".navbar_close").removeClass("active");
			});
			
			function showMsg(msg, fn) {
				if (!fn) {
					TipInfo.Msg.alert(msg);
				} else {
					TipInfo.Msg.confirm(msg, fn);
				}
			}
			
			$(function() {
				$(".navbar .menu").css({
					'transform':'scale(1)',
					'z-index':'1',
					'transition':'transform 0.4s 0.08s, z-index 0s 0.5s'
					});
				$('.menu li a').hover(function() {
					$(this).addClass('on');
				}, function() {
					$(this).removeClass('on');
				});	
		    })
			window.onload = function() {
				showTime();
			}
			function checkTime(i) {
				return i < 10 ? '0' + i : '' + i;
			}
			
			function showTime() {
				var now = new Date();
				var year = now.getFullYear();
				var month = now.getMonth() + 1;
				var day = now.getDate();
				var h = now.getHours();
				var m = now.getMinutes();
				var s = now.getSeconds();
				m = checkTime(m)
				s = checkTime(s)
		
				var weekday = new Array(7)
				weekday[0] = "星期日"
				weekday[1] = "星期一"
				weekday[2] = "星期二"
				weekday[3] = "星期三"
				weekday[4] = "星期四"
				weekday[5] = "星期五"
				weekday[6] = "星期六"
		
				document.getElementById("show").innerHTML = "" + year + "年" + month
						+ "月" + day + "日 " + weekday[now.getDay()] + h + ":" + m + ":"
						+ s;
				t = setTimeout('showTime()', 500)
			}
			
			function turnToSysTem(e){
				var obj = $(e).find("a").attr('id');
				var href = "loadMainFrame.do?subSystem="+obj;
				$("#" + obj).attr('href',href);
				
			}
			
			function turnToPms(){
			    $.ajax( {
			       url : base_path + "/judgeCash.do",
			       type : "post",
			       async:false,
				   success : function(json) {
					if (1 == json.result) {
						if (json.message) {
							showMsg(json.message);
							window.setTimeout("location.reload(true)", 6000);
						} else {
						    $("#pms").attr('href','loadMainFrame.do?subSystem=pms');
						}
			
					  } else {
						showMsg("操作失败");
						window.setTimeout("location.reload(true)", 3000);
					}
					},
					error : function(json) {
						showMsg("操作失败");
						window.setTimeout("location.reload(true)", 1800);
						window.setTimeout("window.parent.JDialog.close();", 1800);
					}
				});
				
			}
			
			function turnToLoginPage(){
				window.location.href = "<%=request.getContextPath()%>/page/ipmspage/login/login.jsp";
			}
	function back(){
		window.top.location.href = base_path + "/logout.do";
	}
		</script>
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	</body>
</html>
