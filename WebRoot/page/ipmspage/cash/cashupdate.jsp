<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>备用金修改</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="css/cashupdate.css" />
	<link rel="stylesheet" type="text/css" href="css/reset.css" />
  </head>
  
  <body>
    <div class="cash_wrapper">
			<div class="cash_one">
				<form action="" method="post">
					<div class="cash_header">
						<label class="label_name">开始营业日期</label>
						<input type="text" value="" name="" id="" class="cash_search"/>
						<label class="label_name">结束营业日期</label>
						<input type="text" value="" name="" id=""  class="cash_search"/>
						<input type="submit" name="" id="" value="查询" class="button_margin_submit"/>
					</div>
				</form>	
			</div>
			<div class="cash_A">
				<h3>
				 备用金柜A
				</h3>
				<div class="content_member_show">
					<section class="box_content_widget fl">
						<div class="content">
							<table id="myTable" class="myTable" border="0">
								<thead>
									<tr>
										<th class="header">营业日</th>
										<th class="header">交接时间</th>
										<th class="header">班次</th>
										<th class="header">现金收入①</th>
										<th class="header">现金支出②</th>
										<th class="header">补上班次备用金额③</th>
										<th class="header">投缴金额④</th>
										<th class="header">交接金额</th>
										<th class="header">会员积分</th>
										<th class="header">备注</th>
									</tr>
								</thead>
								<tbody>
									<tr class="odd">
											<td>01/3/2013</td>
											<td>0</td>
											<td>0</td>
											<td>John Doe</td>
											<td>0</td>
											<td>0</td>
											<td>John Doe</td>
											<td>0</td>
											<td>0</td>
											<td>John Doe</td>
										</tr>
										<tr>
											<td>01/3/2013</td>
											<td>4</td>
											<td>0</td>
											<td>John Doe</td>
											<td>0</td>
											<td>0</td>
											<td>John Doe</td>
											<td>0</td>
											<td>0</td>
											<td>0</td>
										</tr>
								</tbody>
							</table>
						</div>
					</section>
				</div>
			</div>
			<div class="cash_B">
				<h3>
				 备用金柜B
				</h3>
				<div class="content_member_show">
					<section class="box_content_widget fl">
						<div class="content">
							<table id="myTable" class="myTable" border="0">
								<thead>
									<tr>
										<th class="header">营业日</th>
										<th class="header">交接时间</th>
										<th class="header">班次</th>
										<th class="header">现金收入①</th>
										<th class="header">现金支出②</th>
										<th class="header">补上班次备用金额③</th>
										<th class="header">投缴金额④</th>
										<th class="header">交接金额</th>
										<th class="header">会员积分</th>
										<th class="header">备注</th>
									</tr>
								</thead>
								<tbody>
								<tr class="odd">
											<td>01/3/2013</td>
											<td>0</td>
											<td>0</td>
											<td>John Doe</td>
											<td>0</td>
											<td>0</td>
											<td>John Doe</td>
											<td>0</td>
											<td>0</td>
											<td>John Doe</td>
										</tr>
										<tr>
											<td>01/3/2013</td>
											<td>4</td>
											<td>0</td>
											<td>John Doe</td>
											<td>0</td>
											<td>0</td>
											<td>John Doe</td>
											<td>0</td>
											<td>0</td>
											<td>0</td>
										</tr>
								</tbody>
							</table>
						</div>
					</section>
				</div>
			</div>
		</div>
  </body>
</html>
