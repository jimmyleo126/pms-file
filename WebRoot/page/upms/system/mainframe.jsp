<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../common/taglib.jsp"%>
<!DOCTYPE html>
<html>
	<head>
	  	<title>权限管理系统</title>
      	<link href="<%=request.getContextPath()%>/images/shortcut.png" rel="shortcut icon" />
	  	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-ui.css"/>
	  	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/ui.jqgrid.css"/>
	  	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style.css"/>
	  	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/mainframe.css"/>
		<link href="//cdn.bootcss.com/font-awesome/4.6.3/css/font-awesome.min.css"  rel="stylesheet">
		<script>var basePath="<%=request.getContextPath()%>"</script>
   </head>
   <body oncontextmenu="return false;" ondragstart="return false;" onselectstart="return false;">
		<div class="title main_one">
			<div class="sys_icon">权限管理系统</div>
			<div class="model_list">
				<ul id="modelList" class="ul"></ul>
			</div>
		</div>
		<!--右侧用户登录展示 -->
		<div class="user active">
              <div class="user_photo">
                  <img src="images/admin.png" alt="img" width="67%">
              </div>
              <h4 class="user_name ellipsis">${sessionScope.LOGIN_USER.staff.staffName}</h4>
              <i class="fa fa-angle-down"></i>
              <div class="user_panel">
                  <ul class="user_opt">
                      <li>
                          <a href="javascript:;">
                              <i class="fa fa-user"></i>
                              <span class="opt_name">用户信息</span>
                          </a>
                      </li>
                       <li>
                          <a href="javascript:;">
                              <i class="fa fa-desktop"></i>
                              <span class="opt_name">主界面</span>
                          </a>
                      </li>
                       <li class="pf-logout" >
                          <a href="#" id="loginout" onclick="loginOut();">
                              <i class="fa fa-stop-circle-o"></i>
                              <span class="opt_name">退出</span>
                          </a>
                      </li>
                  </ul>
              </div>
        </div>
        <!--右侧消息提示-->
      	<div class="notice">
            <a class="dropdown-toggle button" data-toggle="dropdown" href="#">
                <i class="fa fa-bell"></i>
                <span class="label label-transparent-black">1</span>
            </a>
            	<ul class="dropdown-menu wide nopadding bordered animated fadeInDown">
                  <li><h1>你有 <strong>3</strong>个新消息</h1></li>
                  <li>
                    <a href="#">
                      <span class="label label-green"><i class="fa fa-user"></i></span>
                   	  新的用户注册
                      <span class="small">18 mins</span>
                    </a>
                  </li>

                  <li>
                    <a href="#">
                      <span class="label label-red"><i class="fa fa-power-off"></i></span>
                      	服务启动
                      <span class="small">27 mins</span>
                    </a>
                  </li>

                  <li>
                    <a href="#">
                      <span class="label label-orange"><i class="fa fa-plus"></i></span>
                      	新的订单
                      <span class="small">36 mins</span>
                    </a>	
                  </li>

                  <li>
                    <a href="#">
                      <span class="label label-cyan"><i class="fa fa-power-off"></i></span>
                    	 服务重启
                      <span class="small">45 mins</span>
                    </a>
                  </li>

                  <li>
                    <a href="#">
                      <span class="label label-amethyst"><i class="fa fa-power-off"></i></span>
                      	你好
                      <span class="small">50 mins</span>
                    </a>
                  </li>

                   <li>
                   	 <a href="#">查看所有通告 <i class="fa fa-angle-right"></i></a>
                   </li>
                </ul>
        </div>
        <!--右侧消息提示结束 -->
		<div class="main">
			<div class="bgstyle">
				<form id="reportform" class="reportform">
					<div class="main_title">
						<div class="main_left_title">
							<div class="title_icon"><span class="title_text">功能列表</span></div>
						</div>
						<div id="tabs" class="main_right_title">
							<jsp:include page="../../system/tabheader.jsp"></jsp:include>
						</div>
					</div>
					<div class="main_info">
						<div class="main_left">
							<div id="backZone" align="left" class="main_left_tree">
								<ul id="menuList" class="ul"></ul>
							</div>
						</div>
						<div class="main_right" >
							<div id="dataZone" align="left" class="main_right_grid">
								<iframe name="contentFrame_first" id="contentFrame_first" src="<%=request.getContextPath()%>/page/system/welcome.jsp"
								 frameborder="0" width="100%" height="100%" style="display:block;"></iframe>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
		<!-- <div class="footer"><%@include file="../../system/footer.jsp" %></div> -->
		<input type="hidden" name="modelId" id="modelId" value='${modelConfig.modelId}'/>
		<script type="text/javascript">
			var base_path = "<%=request.getContextPath()%>";
		</script>
		<jsp:include page="../../common/script.jsp"></jsp:include>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script src="<%=request.getContextPath()%>/script/mainframe.js"></script>
		<script src="<%=request.getContextPath()%>/script/mainTabs.js"></script>
		<script>
		</script>
		<script type="text/javascript">
			$(".main").css("height", $("body").outerHeight() - 80);
			$(".main_info").css("height", $("form").outerHeight() - 40);
			
			$(document).ready(function() {
				currentFrame = "#contentFrame_first";
				
				var models = $("#modelList"), width;
				<c:forEach items="${models}" var="model" varStatus="loopStatus">
					<c:if test="${ model.value.show }">
						$("<li id='model_${model.key}' name='${model.value.modelName}' class='" 
							+ ('${model.key}' == '${modelConfig.modelId}' ? "model_selected" : "model_unselected")
							+ "' onclick=listFunctions('${model.key}');><div id='model_${model.key}_inner' class='model_inner'>"
							+ "${model.value.modelName}</div></li>").appendTo(models);
						$("#model_${model.key}_inner").css("background", "url(./images/model/${model.value.icon}.png) no-repeat");
						$("#model_${model.key}_inner").css("backgroundPosition", "top center");
						$("#model_${model.key}_inner").css("background-position","25px 4px");
						$("#model_${model.key}").hover(function(){
							$(this).addClass("selectmodelstyle");
						},function(){
							$(this).removeClass("selectmodelstyle");
						});
						width = "${model.value.modelName}".length * 15;
						width = width > 100 ? width : 100;
						$("#model_${model.key}").css("width", width + "px");
					</c:if>
				</c:forEach>
				listFunctions('${modelConfig.modelId}');
			});
			
			function loginOut(){
				$("#loginout").attr("href",base_path+"/loadMainFrame.do");
			}
		</script>
	</body>
</html>

