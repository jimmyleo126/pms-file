<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<% request.setAttribute("basePath", request.getContextPath()); %>
<%
request.setAttribute("pagetype", request.getAttribute("pagetype"));
request.setAttribute("audittype", request.getAttribute("audittype"));
request.setAttribute("operid", request.getAttribute("operid"));
request.setAttribute("applystaff", request.getAttribute("applystaff"));
request.setAttribute("applystaffname", request.getAttribute("applystaffname"));
request.setAttribute("branchid", request.getAttribute("branchid"));
request.setAttribute("branchname", request.getAttribute("branchname"));
request.setAttribute("applytime", request.getAttribute("applytime"));
request.setAttribute("purchasetype", request.getAttribute("purchasetype"));
request.setAttribute("purchaseamount", request.getAttribute("purchaseamount"));
request.setAttribute("applyreason", request.getAttribute("applyreason"));
request.setAttribute("purinfo", request.getAttribute("purinfo"));
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
		<link href="<%=request.getContextPath()%>/css/ipms/auditInfoDetail.css" rel="stylesheet" type="text/css" media="all" />
		<style>
		
		</style>
	</head>

	<body>
		<div style="height: 99%; overflow: auto;">
			<input id="pagetype" type="hidden" class=""
				value="${pagetype}" readonly />
			<input id="audittype" type="hidden" class=""
				value="${audittype}" readonly />
			<form action="" name="myFrom" id="myForm">
				<div class="appinfo_c">
					<table class="table">
						<col width="25%" />
						<col width="25%" />
						<col width="25%" />
						<col width="25%" />
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
									value="${applystaff}" readonly />
								<input id="applystaffname" type="text" class="table_input"
									value="${applystaffname}" readonly />
							</td>
							<td>
								<label>
									申请日期:
								</label>
								<input id="recordTime" type="text" class="table_input"
									value="${applytime}" readonly />
							</td>
							<td>
								<label>
									归属门店:
								</label>
								<input id="branchid" type="hidden" class="table_input"
									value="${branchid}" readonly />
								<input id="recordTime" type="text" class="table_input"
									value="${branchname}" readonly />
							</td>
						</tr>
						<tr>
							<td>
								<label>
									采购类别:
								</label>
								<input id="recordTime" type="text" class="table_input"
									value="${purchasetype}" readonly />
							</td>
							<td>
								<label>
									采购金额:
								</label>
								<input id="recordTime" type="text" class="table_input"
									value="${purchaseamount}" readonly />
							</td>
							<td>
								<label>
									采购理由:
								</label>
								<input id="recordTime" type="text" class="table_input"
									value="${applyreason}" readonly />
							</td>
						</tr>
					</table>
				</div>
				<div class="appinfo_scroll">
					<c:forEach items="${purinfo}" var="gl">
						<table class="table">
							<col width="25%" />
							<col width="25%" />
							<col width="25%" />
							<col width="25%" />
							<tr>
								<td>
									<label class="purchaseTitle">商品名称:</label>
									<input id="materialName" type="text" class="table_input"
										value="${gl.GOODSNAME}" readonly />
								</td>
								<td>
									<label class="purchaseTitle">采购价格:</label>
									<input id="goodsName" type="text" class="table_input"
										value="${gl.PURCHASE_PRICE}" readonly />
								</td>
								<td>
									<label class="purchaseTitle">采购数量:</label>
									<input id="goodsAmount" type="text" class="table_input"
										value="${gl.AMOUNT}"  />
								</td>
								<td>
									<label class="purchaseTitle">备注信息:</label>
									<textarea id="goodsAmount"class="table_input"  readonly>${gl.REMARK}</textarea>
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
	 </script>
	</body>
</html>
