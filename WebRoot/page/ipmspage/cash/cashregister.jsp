<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>备用金登记</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="css/cashregister.css"/>
	<link rel="stylesheet" type="text/css" href="css/reset.css"/>

  </head>
  
  <body>
    <div class="check_wrap_margin check_color">
			<h2>备用金</h2>
			<div class="top_check">
				<div class="left_check fl">
					<ul>
						<li class="tab_one"><span>备用金交接</span></li>
						<li class="tab_two tab_select" onclick="changetwo()"><span>备用金修改</span></li>
					</ul>
				</div>
				<div class="right_check fr">
					<span class="">营业日 <span class="ondate">2017-06-09</span></span>
					<span>|</span>
					<span class="name">长沙店张店长</span>
					<span>|</span>
					<span class="banci">班次<span class="dateselect">早班</span></span>
				</div>
			</div>
			<!--内容展示部分-->
			<div class="content_color">
				<div class="ifa" id="f">
					<div class="check_one fl">
						<form action="" method="post">
							<div class="cabinet_cash fl">
								<ul>
									<li><label class="label_name">备用金柜</label>
									<select name="" class="check">
										<option value="">请选择</option>
										<option value="">A</option>
										<option value="">B</option>
									</select></li>
								</ul>
							</div>
							<ul class="clearfix">
								<li><label class="label_name">交接人</label> <input name="交接人" type="text" value="" class="check" ></li>
								<li><label class="label_name">确认人</label> <input name="确认人" type="text" value="" class="check"></li>
								<li><label class="label_name">现金收入</label> <input name="现金收入" type="text" value="" class="check" disabled="disabled"></li>
								<li><label class="label_name">现金支出</label> <input name="现金支出" type="text" value="" class="check" disabled="disabled"></li>
								<li><label class="label_name">投缴金额</label> <input name="投缴金额" type="text" value="" class="check" disabled="disabled"></li>
								<li><label class="label_name">备用金交接金额</label> <input name="投缴金额" type="text" value="" class="check" disabled="disabled"></li>
								<li><label class="label_name">补上班次备用金额</label> <input name="投缴金额" type="text" value="" class="check" disabled="disabled"></li>
								<li><label class="label_name">本班需补备用金额</label> <input name="投缴金额" type="text" value="" class="check" disabled="disabled"></li>
								<li><label class="label_name">银行卡余额</label> <input name="投缴金额" type="text" value="" class="check" disabled="disabled"></li>
								<li><label class="label_name">银行卡张数</label> <input name="投缴金额" type="text" value="" class="check" disabled="disabled"></li>
								<li><label class="label_name">结账日期	</label><input name="预订人" type="text" value="" class="check" disabled="disabled"></li>
							</ul>
							<div class="card">
								<label class="label_name">押金收据号码</label><textarea name="" rows="2" cols="80"></textarea>
								<br/>
								<label class="label_name">发票号码</label><textarea name="" rows="2" cols="80"></textarea>
								<br/>
								<label class="label_name">备注</label><textarea name="" rows="2" cols="80"></textarea>
							</div>
						</form>
					</div>
				</div>
				<div id="a" style="display:none;height:626px;"></div>
			</div>
		</div>
		<script type="text/javascript">
			/*备用金修改··*/
			function changetwo() {
				document.getElementById("a").innerHTML = '<iframe src="cashupdate.jsp" width="100%" height="100%" frameborder=no  scrolling="no"></iframe>'
			}
			$(".left_check ul li").on("click", function() {
				$(this).addClass("tab_select");
				$(this).siblings().removeClass("tab_select");
			});
			$(".tab_one").on("click", function() {
				$(".ifa").css("display", "block");
				$("#a").css("display", "none");
			});
			$(".tab_two").on("click", function() {
				$("#a").css("display", "block");
				$(".ifa").css("display", "none");
			});
		</script>
  </body>
</html>
