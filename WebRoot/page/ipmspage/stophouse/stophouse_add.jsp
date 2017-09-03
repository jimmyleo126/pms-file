<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../common/script.jsp"%>
<!DOCTYPE HTML>
<html>
  <head>
    
    <title>新增停售房</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/stopsell/stophouse_info.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/need/laydate.css"/>
	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/skins/molv/laydate.css"/>
	<link href="//cdn.bootcss.com/font-awesome/4.6.3/css/font-awesome.min.css"  rel="stylesheet">
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
  </head>
  
  <body>
   	   <div class="house_info">
			<form action="" method="post" id="haltplan_submit">
				<div class="">
					<ul class="clearfix">
						<li><label class="label_name">房间号码：</label><input name="roomId" type="text" value="" id="roomId" class="adroom"></li>
						<li><label class="label_name">停售类型：</label>
							<select name="haltType"class="adroom">
								<option value="1">停售</option>
								<option value="2">维修</option>
							</select>
						</li>
						<li><label class="label_name">停售原因：</label>
							<select name="haltReason" class="adroom">
								<option value="1">马桶损坏</option>
								<option value="2">天花漏水</option>
								<option value="3">地板变形</option>
								<option value="4">设施损坏</option>
								<option value="5">其他原因</option>
							</select>
						</li>
						<li><label class="label_name">开始日期：</label><input name="startTime" type="text" value="" id="startTime" class="adroom">
						<i class="fa fa-calendar-check-o"></i></li>
						<li><label class="label_name">预计结束日期：</label><input name="endTime" type="text" value="" id="endTime" class="adroom">
						<i class="fa fa-calendar-check-o"></i></li>
						<li>
							<label class="label_name">备注：</label>
							<textarea name="remark" rows="10" cols="90"></textarea>
						</li>
						<li>
							<input id="submithaltpan" type="button" value="确定" class="button_comfirm button_margin" onclick="submithaltplan()">
						</li>
					</ul>
				</div>
			</form>
		</div>
		<script src="<%=request.getContextPath()%>/script/ipms/js/laydate.dev.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/workbillroom/util.js"></script>
		<script type="text/javascript">
			var path = "<%=request.getContextPath()%>";
			laydate({
				elem: '#startTime'
			})
			laydate({
				elem: '#endTime'
			})
			function submithaltplan(){
				console.log($("#haltplan_submit").serialize())
				$.ajax({
			         url: path + "/submithaltpan.do",
					 type: "post",
					 data : $("#haltplan_submit").serialize(),
					 success: function(json) {
					 	if(json.result == 0){
						 	showMsg(json.message);
					 	}else if(json.result == 1){
					 		showMsg(json.message);
					 		setTimeout("refresh()", 2000);
					 	}
					 },
					 error: function(json) {}
				});
			}
			
			function refresh(){
				$(window.parent.document.forms[0]).submit();
			}
		</script>
  </body>
</html>
