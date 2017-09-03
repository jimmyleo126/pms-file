<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<% request.setAttribute("basePath", request.getContextPath()); %>
<%
request.setAttribute("pagetype", request.getAttribute("pagetype"));
request.setAttribute("audittype", request.getAttribute("audittype"));
request.setAttribute("operid", request.getAttribute("operid"));
request.setAttribute("staff", request.getAttribute("staff"));
request.setAttribute("staffname", request.getAttribute("staffname"));
request.setAttribute("applytime", request.getAttribute("applytime"));
request.setAttribute("applybranch", request.getAttribute("applybranch"));
request.setAttribute("branchname", request.getAttribute("branchname"));
request.setAttribute("branchid", request.getAttribute("branchid"));
request.setAttribute("branchnameid", request.getAttribute("branchnameid"));
request.setAttribute("theme", request.getAttribute("theme"));
request.setAttribute("themename", request.getAttribute("themename"));
request.setAttribute("roomype", request.getAttribute("roomype"));
request.setAttribute("roontypename", request.getAttribute("roontypename"));
request.setAttribute("rpauditdata", request.getAttribute("rpauditdata"));
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
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/dialog.css" />
		<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
		<link href="<%=request.getContextPath()%>/css/reset.css" rel="stylesheet" type="text/css" media="all" />
		<link href="<%=request.getContextPath()%>/css/pmsmanage/rpaudit.css" rel="stylesheet" type="text/css" media="all" />
		<style>
		
		</style>
	</head>

	<body>
	   <input id="pagetype" type="hidden" class=""
				value="${pagetype}" readonly />
			<input id="audittype" type="hidden" class=""
				value="${audittype}" readonly />
		<div style="height: 99%; overflow: auto;">
			<form action="" name="myFrom" id="myForm">
				<div class="appinfo_c">
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
									value="${staff}" readonly />
								<input id="staffname" type="text" class="table_input"
									value="${staffname}" readonly />
							</td>
							<td>
								<label>
									申请日期:
								</label>
								<input id="applytime" type="text" class="table_input"
									value="${applytime}" readonly />
							</td>
							<td style="display:none">
								<label>
									归属门店:
								</label>
								<input id="branchid" type="hidden" class="table_input"
									value="${applybranch}" readonly />
								<input id="branchname" type="text" class="table_input"
									value="${branchname}" readonly />
							</td>
					   </tr>
						<tr>
							<td>
								<label>
									申请门店:
								</label>
								<input id="applybranch" type="text" class="table_input"
									value="${branchnameid}" readonly />
							</td>
							<td>
								<label>
									申请场景:
								</label>
								<input id="theme" type="text" class="table_input"
									value="${themename}" readonly />
							</td>
							<td>
								<label>
									申请房型:
								</label>
								<input id="roomype" type="text" class="table_input"
									value="${roontypename}" readonly />
							</td>
						</tr>
						<c:forEach items="${rpauditdata}" var="gl">
					
							<tr>
								<td>
									<label class="purchaseTitle">房 价 码 :</label>
									<input id="materialName" type="text" class="table_input"
										value="${gl.RP_ID}" readonly />
								</td>
								<td>
									<label class="purchaseTitle">房价名称:</label>
									<input id="goodsName" type="text" class="table_input"
										value="${gl.RP_NAME}" readonly />
								</td>
								<td>
									<label class="purchaseTitle">申请房价:</label>
									<input id="goodsAmount" type="text" class="table_input"
										value="${gl.ROOM_PRICE}"  readonly/>
								</td>
							</tr>
					</c:forEach>
					</table>
				</div>
				<div id="tableInfoTitle" class="address">
					<label>审核意见:</label>
					<textarea id="auditMessage" class="auditMessage"></textarea>
					<table class="SubmitTable" border="0" cellspacing="0" cellpadding="0">
						<tbody>
							<tr>
								<td >
									<div class="div_button button" role="button"
										onclick="auditSubmitOK();">
										<a id="sData">通过</a>&nbsp;
									</div>
									<div class="div_button button" role="button"
										onclick="auditSubmitback();">
										<a id="sData">打回</a>&nbsp;
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
