<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../common/script.jsp"%>
<%@ include file="../../common/css.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>房态统计</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" id="style" href="<%=request.getContextPath()%>/css/ipms/jquery-easyui/themes/icon.css" />
	<link rel="stylesheet" id="style" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />	
	<link rel="stylesheet" id="style" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />	
	<link rel="stylesheet" id="style" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/room_statistics.css" />	
	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/common/jquery-dialog.css" />
	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/css/pagenation.css" />
	<link rel="stylesheet" id="style" type="text/css" 
		href="<%=request.getContextPath()%>/css/common/datetimepicker.css" media="all" />

  </head>
  <body>
   <div class="room_margin">
  	<div class="header_search">
  		<form action="" method="">
	  		<span>当前房态</span>
	  		<label>营业日期</label>
	  		<input id="mdate" type="text" name="mdate" class="date_txt" />
  			<span class="forward">远期房态</span>
  		</form>
  	</div>
		<section class="box-content-section fl" style="width:100%;">
			<section class="box_content_widget fl">
				<div id="integral" class="content" style="height:600px;padding:10px;">
					<iframe name="currentFrame" id="currentFrame" frameborder="0" width="100%" height="100%" ></iframe>
				</div>
			</section>
		</section>
			<!--  市场活动配额-->
		<section class="box-content-section fl" style="width:100%;height:30px;">
			<section class="box_content_widget fl" style="width:100%;height:30px;">
				<div class="label_name" style="width:auto;">市场活动配额:</div>	
			</section>
		</section>	

		<section class="box-content-section fl" style="width:100%;">
			<section class="box_content_widget fl">
				<div id="integral" class="content" style="height:600px;padding:10px;">
					<iframe name="activityFrame" id="activityFrame" frameborder="0" width="100%" height="100%" ></iframe>
				</div>
			</section>
		</section>	
		<!-- <div class="quota" style="margin-top:604px;">
			
			<table>
				<thead id="activity">
					<tr>
						<th class="header">房间类型</th>
						<th class="header">总房数</th>
						<th class="header">可售房数</th>
						<th class="header">停售房数</th>
						<th class="header">过夜房数</th>
						<th class="header">有效预定</th>
						<th class="header">所有预定</th>
					</tr>
				</thead>
				<tbody id="info">
					<c:forEach items="${list}" var="list">
						<tr>
							<td>${list["ROOMNAME"] }</td>
							<td>${list["ROOMID"] }</td>
							<td>${list["SELL"] }</td>
							<td>${list["STOP"] }</td>
							<td>${list["NIGHT"] }</td>
							<td>${list["VAILD"] }</td>
							<td>${list["INVAILD"] }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div> -->
	</div>
	<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/jquery.jqGrid.src.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/datepickerCN.js"></script>
		<script type="text/javascript">	
			var base_path = "<%=request.getContextPath()%>";
			
			$(".forward").on("click",function(){
				JDialog.openWithNoTitle("",base_path+"/page/ipmspage/room_statistics/room_forward.jsp",1179,738);
			});
			$(document).ready(function(){
				$(".date_txt").datepicker({ dateFormat: "yy/mm/dd ",startDate:new Date() });
				$("#mdate").css('font-size','0.9em');
				var now = new Date();    
			    var year = now.getFullYear();       //年   
			    var month = now.getMonth() + 1;     //月   
			    var day = now.getDate();
			    var time = year+"/"+month+"/"+day;
			    $("#mdate").val(time);
				$("#currentFrame").attr("src","CurrentRoom.do?madate="+time);
				$("#activityFrame").attr("src","QueryCampaign.do");
				$("#mdate").change(function(){
					$("#currentFrame").attr("src","CurrentRoom.do?madate="+$("#mdate").val());
				});
			})
		</script>
	</body>
</html>
