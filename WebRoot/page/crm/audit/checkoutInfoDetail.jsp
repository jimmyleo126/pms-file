<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<% request.setAttribute("basePath", request.getContextPath()); %>
<%
request.setAttribute("checkdata", request.getAttribute("checkdata"));
request.setAttribute("roomtype", request.getAttribute("roomtype"));
request.setAttribute("roomname", request.getAttribute("roomname"));
request.setAttribute("operid", request.getAttribute("operid"));
request.setAttribute("pagetype", request.getAttribute("pagetype"));
request.setAttribute("audittype", request.getAttribute("audittype"));
%>

<%@ include file="../../common/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>详细信息</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css"
			href="<%=request.getContextPath()%>/css/common/dialog.css" />
		<link href="<%=request.getContextPath()%>/css/common/tipInfo.css"
			rel="stylesheet" type="text/css" media="all" />
		<link href="<%=request.getContextPath()%>/css/reset.css"
			rel="stylesheet" type="text/css" media="all" />
		<link
			href="<%=request.getContextPath()%>/css/ipms/auditInfoDetail.css"
			rel="stylesheet" type="text/css" media="all" />
		<style>
</style>
	</head>
	<body>
		<div style="height: 99%; overflow: auto;">
			<input id="pagetype" type="hidden" class="" value="${pagetype}"
				readonly />
			<input id="audittype" type="hidden" class="" value="${audittype}"
				readonly />
			<form action="" name="myFrom" id="myForm">
				<div class="appinfo_check">
					<c:forEach items="${checkdata}" var="cd">
						<table class="table">
							<col width="33%" />
							<col width="33%" />
							<col width="33%" />
							<tr>
								<td>
									<label>
										申请单号:
									</label>
									<input id="operid" type="text" class="table_input"
										value="${operid}" readonly />
								</td>
								<td>
									<label>
										申请人员:
									</label>
									<input id="applystaff" type="hidden" class="table_input"
										value="${cd.MEMBERID}" readonly />
									<input id="applystaffname" type="text" class="table_input"
										value="${cd.MEMBERNAME}" readonly />
								</td>
								<td>
									<label>
										申请日期:
									</label>
									<input id="recordTime" type="text" class="table_input"
										value="${cd.RECORDTIME}" readonly />
								</td>

							</tr>
							<tr>
								<td>
									<label>
										所在门店:
									</label>
									<input id="branchid" type="hidden" class="table_input"
										value="${cd.BRANCHID}" readonly />
									<input id="recordTime" type="text" class="table_input"
										value="${cd.BRANCHNAME}" readonly />
								</td>
								<td>
									<label>
										退房房型:
									</label>
									<input id="roomtype" type="hidden" class="table_input"
										value="${roomtype}" readonly />
									<input id="roomname" type="text" class="table_input"
										value="${roomname}" readonly />
								</td>
								<td>
									<label>
										退房房号:
									</label>
									<input id=roomid type="text" class="table_input"
										value="${cd.ROOMID}" readonly />
								</td>
							</tr>
							<tr>
								<td>
									<label>
										订单来源:
									</label>
									<input id="source" type="hidden" class="table_input"
										value="${cd.SOURCE}" readonly />
									<input id="sourcename" type="text" class="table_input"
										value="${cd.PARAMNAME}" readonly />
								</td>
								<td>
									<label>

									</label>
								</td>
								<td>
									<label>

									</label>
								</td>
							</tr>
						</table>
					</c:forEach>
				</div>
				<div id="tableInfoTitle" class="address">
					<label>
						审核意见:
					</label>
					<textarea id="auditMessage" class="auditMessage"></textarea>
					<table class="SubmitTable" border="0" cellspacing="0"
						cellpadding="0">
						<tbody>
							<tr>
								<td>
									<div class="div_button button" role="button"
										onclick="auditSubmitOK();">
										<a id="sData">通过</a>&nbsp;
									</div>
									<div class="div_button button" role="button"
										onclick="auditSubmitFail();">
										<a id="sData">驳回</a>&nbsp;
									</div>
									<div class="div_button button" role="button"
										onclick="window.parent.JDialog.close();">
										<a id="sData">关闭</a>
									</div>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				<div id="tableInfoTitleLog" class="tableInfoTitleLog">
					<table class="SubmitTable" border="0" cellspacing="0"
						cellpadding="0">
						<tbody>
							<tr>
								<td align="center">
									<div class="div_button button"
										onclick="window.parent.JDialog.close();">
										<a id="sData">关闭</a>
									</div>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</form>
		</div>
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/jquery.js"></script>
		<script
			src="<%=request.getContextPath()%>/script/crm/audit/auditInfoDetail.js"></script>
		<script>

	 	var base_path = '<%=request.getContextPath()%>';
	 	$(document).ready(function() {
			var pagetype = $("#pagetype").val();
			if(pagetype=="auditlog"){
				$("#tableInfoTitle").hide();
			}else if(pagetype=="audit"){
				$("#tableInfoTitleLog").hide();
			}
});
	 </script>
	</body>
</html>
