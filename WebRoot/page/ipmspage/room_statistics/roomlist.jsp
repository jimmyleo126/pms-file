<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../common/script.jsp"%>
<%@ include file="../../common/css.jsp"%>
<!DOCTYPE HTML>
<html>
  <head>
    <title>房单</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlistfont.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/roomlist.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/select/jquery.mCustomScrollbar.min.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/select/select.css" />
	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/need/laydate.css"/>
	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/skins/molv/laydate.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/pagination.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/pagenation.css"/>
  </head>
  
  <body>
  	<div class="room_margin"> 
      <div class="header_roomlist">
			<ul>
				<li class="header_roomlist_li active" onclick="getRoomList()">
					<span>在住房单</span>
				</li>
				<li class="header_roomlist_li" onclick="getDueOut()">
					<span>预离房单</span>
				</li>
				<li class="header_roomlist_li" onclick="gettodayOut()">
					<span>今日已退房单</span>
				</li>
			</ul>
			<div class="fr">
				<span class="highsearch">高级查询</span>
			</div>
			<div class="high_header fadeIndown">
				<h3>高级查询</h3>
				<i class="imooc imooc_room" style="color:#FFF;">&#xe900;</i>
				<form id="room_search" action="" method="post">
					<div class="margintop">
						<ul class="clearfix">
							<li><label class="label_name">房单号</label><input name="checkid" type="text" value="" id="id" class="text_search"></li>
							<li><label class="label_name">房型</label>
								<select name="roomtype" id="roomtype" class="text_search mySelect"style="height:190px;">
									<option value="">--请选择房间类型--</option>
								</select>
							</li>
							<li><label class="label_name label_name_roomid">房号</label> <input name="roomid" type="text" value="" class="text_search"></li>
							<li><label class="label_name">入住时间</label>
								<input name="checkintime" id="checkintime" type="text" value="" class="text_search">
								<i class="fa fa-calendar-check-o"></i>
							</li>
							<li><label class="label_name">退房时间</label>
								<input name="checkouttime" id="checkouttime" type="text" value="" class="text_search">
								<i class="fa fa-calendar-check-o"></i>
							</li>
							<li><label class="label_name">入住人</label><input id="checkuser" name="checkuser" type="text" value="" class="text_search"></li>
							<li><label class="label_name">担保类型</label>
								<select name="guarantee" id="guarantee" class="text_search">
									<option value="">--请选择担保类型--</option>
									<option value="1">无担保</option>
									<option value="2">担保留房</option>
								</select>
							</li>
							<li><label class="label_name">预订人</label><input name="orderuser" id="orderUser" type="text" value="" class="text_search"></li>
							<li><label class="label_name">预定手机</label> <input id="mphone" name="mphone" type="text" value="" class="text_search"></li>
							<li><label class="label_name">状态</label>
								<select name="status" id="status" class="text_search">
									<option value="">--请选择状态类型--</option>
									<option value="1">在住</option>
									<option value="2">离店</option>
									<option value="3">已退未结</option>
								</select>
							</li>
						</ul>
						<ul class="clearfix">
							<li><label class="label_name">预定日期</label>
								<input name="ordertimebegin" id="ordertimebegin" type="text" readonly class="text_search date">
								<i class="fa fa-calendar-check-o"></i>
							</li>
							<li><label class="label_name">至</label><input name="ordertimeend" id="ordertimebegin_two" type="text" readonly class="text_search date">
							<i class="fa fa-calendar-check-o"></i></li>
						</ul>
						<ul class="clearfix">
							<li><label class="label_name">抵店日期</label> <input name="arrivaltimebegin" id="arrivaltimebegin" type="text" readonly class="text_search date">
							<i class="fa fa-calendar-check-o"></i></li>
							<li><label class="label_name">至</label><input name="arrivaltimeend" id="arrivaltimebegin_two" type="text" readonly class="text_search date">
							<i class="fa fa-calendar-check-o"></i></li>
						</ul>
						<ul class="clearfix">
							<li><label class="label_name">离店日期</label> <input name="leavetimebegin" type="text" id="leavetimebegin" readonly class="text_search date">
							<i class="fa fa-calendar-check-o"></i></li>
							<li><label class="label_name">至</label><input name="leavetimeend" id="leavetimebegin_two" type="text" readonly class="text_search date">
							<i class="fa fa-calendar-check-o"></i></li>
						</ul>
						<ul class="clearfix">
							<li><input type="button" style="margin-right: 12px;" value="重置" class="button_margin" onclick="resetsearch()">
							</li>
							<li><input type="button" value="确认" class="button_margin cancel confirm" onclick="getQueryData()"></li>
						</ul>
					</div>
				</form>
				
			</div>
		</div>
		<section class="box-content-section fl" style="width:100%;">
			<section class="box_content_widget fl">
				<div class="content" style="height:929px;padding:10px;">
				 	<iframe name="logFrame" id="logFrame" frameborder="0" width="100%" height="100%"></iframe>
				</div>
			</section>
		</section>
	 </div>
	 <script src="<%=request.getContextPath()%>/script/ipms/js/laydate.dev.js"></script>
	 <script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/selectjs/jquery.select.js"></script>
	 <script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/selectjs/jquery.mCustomScrollbar.concat.min.js"></script>
	 <script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/workbillroom/util.js"></script>
		<script type="text/javascript">
			var path = "<%=request.getContextPath()%>";
			$(function(){
				loadsearchroomtype();
				getRoomList();
				
			});
			/*初始化下拉表单*/
			
			laydate({
				elem: '#checkintime'
			})
			laydate({
				elem: '#checkouttime'
			})
			/*时间插件	*/
			laydate({
				elem: '#ordertimebegin'
			})
			laydate({
				elem: '#ordertimebegin_two'
			})
			laydate({
				elem: '#arrivaltimebegin'
			})
			laydate({
				elem: '#arrivaltimebegin_two'
			})
			laydate({
				elem: '#leavetimebegin'
			})
			laydate({
				elem: '#leavetimebegin_two'
			})
			function getRoomList(){
				/*$.ajax({
			         url: path + "/loadCheckData.do",
					 type: "post",
					 data : {status : "1"},
					 success: function(json) {
					 	loadcheckdata(json)
					 },
					 error: function(json) {}
				});*/
				$("#logFrame").attr("src","<%=request.getContextPath()%>/showroomlistcheckdata.do?status=1");
				//$("#logFrame").contents().find("#pagerForm").attr("action","<%=request.getContextPath()%>/showroomlistcheckdata.do?status=1");
			}
			
			function getDueOut(){
				var arr = dealLocalDate(new Date()).split(" ");
				/*$.ajax({
			         url: path + "/loadCheckData.do",
					 type: "post",
					 data : {	
					 	checkouttime : arr[0],
					 	status : "1"
					 	 },
					 success: function(json) {
					 	loadcheckdata(json)
					 },
					 error: function(json) {}
				});*/
				$("#logFrame").attr("src","<%=request.getContextPath()%>/showroomlistcheckdata.do?status=1&checkouttime=" + arr[0] );
				
			}
			
			function gettodayOut(){
				var todaydate = dealLocalDate(new Date()).split(" ");
				/*$.ajax({
			         url: path + "/loadCheckData.do",
					 type: "post",
					 data : {
					 	checkouttime : todaydate[0],
					 	status : "2"
					 	},
					 success: function(json) {
					 	loadcheckdata(json)
					 },
					 error: function(json) {}
				});*/
				$("#logFrame").attr("src","<%=request.getContextPath()%>/showroomlistcheckdata.do?status=2&checkouttime=" + todaydate[0] );
			}
			
			function getQueryData(){
				/*$.ajax({
			         url: path + "/loadCheckData.do",
					 type: "post",
					 data : $("#room_search").serialize(),
					 success: function(json) {
					 	loadcheckdata(json)
					 },
					 error: function(json) {}
				});*/
				$("#logFrame").attr("src","<%=request.getContextPath()%>/showroomlistcheckdata.do?" + $("#room_search").serialize() );
				
			}
			
			function loadcheckdata(json){
				 var tabledata;
			 	$.each(json, function(index){
			 		tabledata = tabledata + "<tr class='odd' onclick='aa()' ondblclick='bb(this)'>" +
			 		"<td>" + json[index]["CHECKID"] + "</td>" +
			 		"<td>" + json[index]["ROOMNAME"] + "</td>" +
			 		"<td>" + json[index]["ROOMID"] + "</td>" +
			 		"<td>" + dealDate(json[index]["CHECKINTIME"]) + "</td>" +
			 		"<td>" + dealDate(json[index]["CHECKOUTTIME"]) +"</td>" +
			 		"<td>" + json[index]["ORDERUSER"] + "</td>" +
			 		"<td>" + json[index]["MPHONE"] + "</td>" +
			 		"<td>" + json[index]["FIRSTCHECKUSERNAME"] + "</td>" +
			 		"<td>" + json[index]["FIRSTCHECKUSERPHONE"] + "</td>" +
			 		"<td>" + json[index]["SOURCE"] + "</td>" + 
			 		"<td>" + json[index]["FIRSTCHECKUSER"] + "</td>" + 
			 		"<td>" + json[index]["ROOMPRICE"] + "</td>" + 
			 		"<td>" + json[index]["STATUSNAME"] + "</td>" + 
			 		"</tr>";
			 	});
			 	if(json.length == 0){
			 		$("#check_data").html("");
			 	}else{
			 		$("#check_data").html(tabledata);			 	
			 	}
			}
			
			function loadsearchroomtype(){
				$.ajax({
			         url: path + "/loadsearchroomtype.do",
					 type: "post",
					 data : {},
					 success: function(json) {
					 	var data = "<option value=''>房间类型</option>";
					 	$.each(json,function(index){
					 		data = data + "<option value='" +json[index]["ROOMNAME"] + "'>" +json[index]["ROOMNAME"] + "</option>"
					 	});
					 	$("#roomtype").html(data);
						$(".mySelect").select({
							width: "175px"
						});
					 },
					 error: function(json) {}
				});
			}
		
			$(".highsearch").on("click", function() {
				$(".high_header").css("display", "block");
				$(".high_header").removeClass("fadeOutUp");
			})
			$(".imooc").on("click", function() {
				$(".high_header").addClass("fadeOutUp").fadeOut(100);
			})
			$(".cancel").on("click", function() {
				$(".high_header").fadeOut(100);
			})
			var timer = null;
			function aa() {
				clearTimeout(timer);
				if (event.detail == 2)
					return;
				timer = setTimeout(function() {
					console.log('单击');
				}, 300);
			}
			
			function resetsearch(){
				$("#room_search :text").val("");
				$("#room_search #status").find("option").eq(0).prop("selected",true);
				$("#room_search #guarantee").find("option").eq(0).prop("selected",true);
				$("#room_search #roomtype").find("option").eq(0).prop("selected",true);
			}
		/*	function bb(e) {
				clearTimeout(timer);
				var checkid = $(e.children[0]).html();
				window.parent.JDialog.openWithNoTitle("", path + "/page/ipmspage/room_statistics/roomlist_check.jsp?checkid=" + checkid ,1196 ,738);
				//window.location.href=path + "/page/ipmspage/room_statistics/roomlist_check.jsp?checkid=" + checkid;
			}
		*/
		$(".header_roomlist_li").on("click",function(){
			$(this).addClass("active");
			$(this).siblings().removeClass("active");
		});
		</script>
  </body>
</html>
