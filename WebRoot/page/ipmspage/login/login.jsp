<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../common/taglib.jsp"%>
<%@ include file="../../common/script.jsp"%>
<%
	request.setAttribute("basePath", request.getContextPath());
 %>
<!DOCTYPE HTML>
<html>
	<head>
		<title>登录页</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<meta name="author" content="Matthew Wagerfield"/>
		<meta name="keywords" content="flat,surface,shader,Float32Array"/>
		<meta name="description" content="Simple, lightweight Flat Surface Shader for rendering lit triangles."/>
		<meta property="og:description" content="Simple, lightweight Flat Surface Shader for rendering lit triangles."/>
		<meta property="og:site_name" content="Flat Surface Shader"/>
		<meta property="og:title" content="Flat Surface Shader"/>
		<meta property="og:type" content="website"/>
		<link rel="stylesheet" id="style" type="text/css"
			href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
		<link rel="stylesheet" id="style" type="text/css"
			href="<%=request.getContextPath()%>/css/ipms/css/loginfile/login.css" />
		<link rel="stylesheet" id="style" type="text/css"
			href="<%=request.getContextPath()%>/css/ipms/css/loginfile/styles.css" />
		<link href="<%=request.getContextPath()%>/css/common/tipInfo.css"
			rel="stylesheet" type="text/css" media="all" />
		<link rel="stylesheet" id="style" type="text/css"
			href="<%=request.getContextPath()%>/css/system/footer.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/jquery-labelauty.css"/>
		<script src="<%=request.getContextPath()%>/script/ipms/js/room_urban/jquery-labelauty.js"></script>
		<script>
          var base_path = "<%=request.getContextPath()%>";
	    </script>
	</head>
	<body>
		<div id="container" class="container">
			<div id="output" class="container"></div>
			<div id="vignette" class="overlay vignette"></div>
			<div id="noise" class="overlay noise"></div>
			<div id="ui" class="wrapper">
				<div id="home-banner">
					<!--login start-->
					<div class="loginReg-viewbox login-margin login-weight" id="box">
						<!--班次展示
						<h2 class="showclasses">
							<span>当前班次:<span class="red">早班</span>
							</span>
						</h2>-->
						<div class="fl">
							<div id="shift" class="loginReg-order">
								<ul class="clearfix">
									<li class="cloginReg-order-li"><input type="radio" name="radio" value="1" data-labelauty="早班" ></li>
									<li class="cloginReg-order-li"><input type="radio" name="radio" value="2" data-labelauty="中班"></li>
									<li class="cloginReg-order-li"><input type="radio" name="radio" value="3" data-labelauty="晚班"></li>
								</ul>
							</div>
							<div id="cashbox" class="loginReg-cash">
								<ul class="clearfix">
									<li class="cloginReg-order-li">
										<input type="radio" name="radio1" value="A" checked="" data-labelauty="备用金A">
									</li>
									<li class="cloginReg-order-li">
										<input type="radio" name="radio1" value="B" data-labelauty="备用金B">
									</li>
								</ul>
							</div>
						</div>
						<div class="loginReg-panel clearfix">
							<div class="design-img">
								<span>微石青禾|logo</span>
							</div>
							<input type="hidden" id="" name="CodeToken">
							<div class="formView">
								<form role="form" class="form-horizontal" id="loginform" onsubmit="beforeSubmit();" method="post" >
									<input type="hidden" id="" name="id" value="1">
									<div class="form-group">
										<label class="inputTit">
											用户名
										</label>
										<input id="loginName" name="loginName" type="text"
											class="form-control inputInf" placeholder="请输入用户名" />
										<!--  <span id="login_error" class="error fadeInDown"></span>-->
									</div>
									<div class="form-group">
										<label class="inputTit">
											密&nbsp;&nbsp; 码
										</label>
										<input id="password" name="password" type="password"
											maxlength="20" class="form-control inputInf"
											placeholder="请输入密码" />
									</div>
									<div class="form-group">
										<div class="errMsg" id="p_message1" style="display: none;"></div>
										<button type="button" class="btn-loginReg" onclick="loginCheck();">
											登&nbsp;&nbsp;&nbsp;&nbsp;录
										</button>
									</div>
								</form>
							</div>
						</div>
						<!--  login-over-->
					</div>
					
				</div>
			</div>
		</div>
		<div id="controls" class="controls" style="opacity: 0;"></div>
		<div class="footer"><%@include file="../system/footer.jsp" %></div>
		<!--<script src="<%=request.getContextPath()%>/script/ipms/js/jquery-1.8.2.min.js" type="text/javascript" charset="utf-8"></script>
		-->
		<!--<script>!function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0];if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src="//platform.twitter.com/widgets.js";fjs.parentNode.insertBefore(js,fjs);}}(document,"script","twitter-wjs");</script>
		-->
		<script>
		$(document).ready(function() {
			
		});
			var basePath = "${ basePath }";
			//一、获取整个页面（浏览器的可视区）的宽度、高度。
			
			//二、然后获取自身DIV盒子的宽度、高度
			//三、用浏览器可视区的宽度、高度，减去DIV自身的宽度，高度，然后除以2。就可以获取到DIV距离页面的top值，left值。在赋值给DIV相应的top值，left值。
			//四、随着我们的浏览器窗口的改变，让DIV盒子也能随着页面的改变而居中。（利用onresize事件）
			
		    window.onload = function(){
	  		$(function(){
				$(':input').labelauty();
			});
	  		  function box(){
			    var oBox = document.getElementById('box');
			    var L1 = oBox.offsetWidth;
			    var H1 = oBox.offsetHeight;
			    var Left = (document.documentElement.clientWidth-L1)/2;
			    var top = (document.documentElement.clientHeight-H1)/2;
			    oBox.style.left = Left+'px';
			    oBox.style.top = top+'px';
			    }
			    box();
			    window.onresize = function(){
			        box();
			    } 
			}
			
		</script>
		<script src="<%=request.getContextPath()%>/script/ipms/js/login/prefixfree.min.js"></script>
		<script src="<%=request.getContextPath()%>/script/ipms/js/login/dat.gui.min.js"></script>
		<script src="<%=request.getContextPath()%>/script/ipms/js/login/fss.min.js"></script>
		<script src="<%=request.getContextPath()%>/script/ipms/js/login/example.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script src="<%=request.getContextPath()%>/script/login.js"></script>
	</body>
</html>
