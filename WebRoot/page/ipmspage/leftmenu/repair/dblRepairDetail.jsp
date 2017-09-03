<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<% request.setAttribute("basePath", request.getContextPath()); %>


<%--<%
request.setAttribute("repairdata", request.getAttribute("repairdata"));
request.setAttribute("operid", request.getAttribute("operid"));
request.setAttribute("pagetype", request.getAttribute("pagetype"));
request.setAttribute("audittype", request.getAttribute("audittype"));
%>--%>
<%@ include file="../../../common/taglib.jsp"%>
<!DOCTYPE HTML>
<html>
  <head>
  		<link rel="stylesheet" type="text/css"
			href="<%=request.getContextPath()%>/css/common/dialog.css" />
		<link href="<%=request.getContextPath()%>/css/common/tipInfo.css"
			rel="stylesheet" type="text/css" media="all" />
		<link href="<%=request.getContextPath()%>/css/reset.css"
			rel="stylesheet" type="text/css" media="all" />
		<link
			href="<%=request.getContextPath()%>/css/ipms/auditInfoDetail.css"
			rel="stylesheet" type="text/css" media="all" />
  </head>
  <body>
		<div style="height: 99%; overflow: auto;">
			<input id="pagetype" type="hidden" class="" value="${pagetype}"
				readonly />
			<input id="audittype" type="hidden" class="" value="${audittype}"
				readonly />
			<form action="" name="myFrom" id="myForm">
				<div class="appinfo_repair">
					<c:forEach items="${result}" var="re">
						<table class="table">
							<col width="25%" />
							<col width="25%" />
							<col width="25%" />
							<col width="25%" />
							<tr>
								<td>
									<label>
										维修房号:
									</label>
									<input id=roomid type="text" class="table_input dbl_repair_input"
										value="${re.ROOMID}" readonly />
								</td>
								<td>
									<label>
										申请人员:
									</label>
									<input id="applystaffname" type="text" class="apply_member_input table_input dbl_repair_input"
										value="${re['RESERVEDPERSON']}" readonly />
								</td>
								<td>
									<label>
										设备名称:
									</label>
									<input id="equipment" type="text" class="table_input dbl_repair_input"
										value="${re.EQUIPMENT}" readonly />
								</td>
								<td>
									<label>
										紧急程度:
									</label>
									<input id="emergent" class="table_input dbl_repair_input"
										value="${re.EMERGENT}" readonly">

									</input>
								</td>
							</tr>
							<tr>
								<td>
									<label>
										维修房型:
									</label>
									<%--<input id="roomtype" type="hidden" class="table_input"
										value="${rd.ROOMTYPE}" readonly />
									--%><input id="roomname" type="text" class="table_input dbl_repair_input"
										value="${re.ROOMTYPE}" readonly />
								</td>
								<td>
									<label>
										所在门店:
									</label>
									<input id="r" type="text" class="table_input dbl_repair_input"
										value="${re.BRANCHID}" readonly />
								</td>
								<td>
									<label>
										申请来源:
									</label>
									<input id="" type="text" class="apply_input table_input dbl_repair_input"
										value="${re['SOURCES']}" readonly />
								</td>
								<td>
									<label>
										维修状态:
									</label>
									<input id="equipment" type="text" class="table_input dbl_repair_input"
										value="${re.REPAIRLOGSTATUS}" readonly>
								</td>
							</tr>
							
							<tr>
								<td>
									<label>
										维修人员:
									</label>
									<input id="" type="text" class="apply_input table_input dbl_repair_input"
										value="${re['REPAIRPERSON']}" readonly />
								</td>
								<td>
									<label>
										维修时段:
									</label>
									<input id="" type="text" class="apply_input table_input dbl_repair_input"
										value="${re['REPAIRTIMEAREA']}" readonly />
								</td>
								<td>
									<label>
										申请日期:
									</label>
									<input id="" type="text" class="table_input dbl_repair_input"
										value="<fmt:formatDate type='time' value='${re["APPLICATIONDATE"]}' pattern="yyyy/MM/dd" />
										" readonly />
								</td>
								<td>
									<label>
										预修日期:
									</label>
									<input id=repairtime type="text" class="table_input dbl_repair_input"
										value="<fmt:formatDate type='time' value='${re["REPAIRTIME"]}' pattern="yyyy/MM/dd" />" readonly />
								</td>
							</tr>
							<tr>
								<td>
									<label>
										合同编号:
									</label>
									<input id="contractid" type="text" class=" contract_input table_input dbl_repair_input"
										value="${re.CONTRACTID}" readonly />
								</td>
								<td>
									<label>
										申请单号:
									</label>
									<input id="" type="text" class="apply_input table_input dbl_repair_input"
										value="${re['REPAIRAPPLYID']}" readonly />
								</td>
								<%--<td>
									<label>
										日志编号:
									</label>
									<input id="" type="text" class="apply_input table_input dbl_repair_input"
										value="${re['LOGID']}" readonly />
								</td>
								--%><td>
									<label>
										修复日期:
									</label>
									<input id="" type="text" class="table_input dbl_repair_input"
										value="<fmt:formatDate type='time' value='${re["ACREPAIRTIME"]}' pattern="yyyy/MM/dd" />
										" readonly />
								</td>
							</tr>
							<tr>
								<td colspan="4">
									<label>
										审核描述:
									</label>
									<textarea id="remark" type="text" class="table_textarea repair_dblaudit" readonly>${re.AUDITDESCRIPTION}</textarea>
								</td>
							</tr>
							<tr>
								<td colspan="4">
									<label>
										申请备注:
									</label>
									<textarea id="auditMessage" class="table_textarea repair_dblremark" readonly>${re.REMARK}</textarea>
								</td>
							</tr>	
						</table>
					</c:forEach>
				</div>
			</form>
		</div>
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/jquery.js"></script>
		<script
			src="<%=request.getContextPath()%>/script/crm/audit/auditInfoDetail.js"></script>
		<script>
	 
		
	 </script>
	</body>
</html>
