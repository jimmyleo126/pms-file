<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/taglib.jsp"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@ page import="com.ideassoft.core.page.Pagination"%>
<%
	JSONObject pagination = JSONObject.fromObject(request.getAttribute("pagination"));
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css"
			href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=request.getContextPath()%>/css/ipms/css/reportform/report_forms.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=request.getContextPath()%>/css/ipms/pagination.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/pagination.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/pagination.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-ui.css"/>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css"/>
		<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
		<title>在住客人明细</title>
	</head>
	<body>
		<div class="member_margin">
			<form action="#" method="post" id = "myForm">
			  <section class="box-content-section padding  fl">
				<section class="box_content_widget fl">
				  <div class="content">
					<table id="myTable" class="myTable" border="0" width="100">
						<thead>
							<tr>
								<!--<th class="header">
									会员编号
								</th>
								--><th class="header">
									会员名称
								</th>
								<!--<th class="header">
									照片
								</th>
								<th class="header">
									会员登录名
								</th>
								--><th class="header">
									会员等级
								</th>
								<th class="header" style="width: 14%;">
									身份证
								</th>
								<th class="header">
									性别
								</th>
								<th class="header"  style="width: 11%;">
									生日
								</th>
								<th class="header">
									手机号
								</th>
								<!--<th class="header">
									邮件
								</th>
								--><th class="header">
									地址
								</th>
								<!--<th class="header">
									邮编
								</th>
								--><th class="header">
									注册来源
								</th>
								<th class="header">
									有效截至
								</th>
								<th class="header">
									注册时间
								</th>
								<!--<th class="header">
									记录时间
								</th>
								<th class="header">
									状态
								</th>
								--><th class="header" style="width: 13%;"> 
									备注
								</th>
							</tr>
						</thead>
						<tbody id="tbodyInfo">
							<c:forEach items="${roomGuestDetailLists}" var="list">
								<tr class="odd" id="${list['memberId']}">
									<!--<td>
										${list["memberId"] }
									</td>
									--><td>
										${list["memberName"] }
									</td>
									<!--<td>
										${list["photos"] }
									</td>
									<td>
										${list["loginName"] }
									</td>
									--><td>
										${list["memberRank"] }
									</td>
									<td>
										${list["idCard"] }
									</td>
									<td>
										${list["gendor"] }
									</td>
									<td>
										${list["birthDay"] }
									</td>
									<td>
										${list["moblie"] }
									</td>
									<!--<td>
										${list["email"] }
									</td>
									--><td>
										${list["address"] }
									</td>
									<!--<td>
										${list["postCode"] }
									</td>
									--><td>
										${list["oldsource"] }
									</td>
									<td>
										${list["validTime"] }
									</td>
									<td>
										${fn:substringBefore(list["registerTime"], '.') }
									</td>
									<!--<td>
										${fn:substringBefore(list["recordTime"], '.') }
									</td>
									<td>
										${list["status"] }
									</td>
									--><td>
										${list["remark"] }
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				 </div>
			   <div id="pager"></div>
			 </section>
			</section>	
		   </form>
		</div>
		<%@ include file="../../common/script.jsp"%>
		<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script src="<%=request.getContextPath()%>/script/ipms/js/reportforms/roomingGuests.js" type="text/javascript" charset="utf-8"></script>
		<script>
			var base_path = '<%=request.getContextPath()%>';
			Pager.renderPager(<%=pagination%>);
	 	</script>
	</body>
</html>