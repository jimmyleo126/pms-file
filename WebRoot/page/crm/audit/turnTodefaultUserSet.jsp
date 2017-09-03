<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/taglib.jsp"%>
<%@ include file="../../common/css.jsp"%>
<%@ include file="../../common/script.jsp"%>
<% request.setAttribute("basePath", request.getContextPath()); 
   request.setAttribute("auditid", request.getAttribute("auditid"));
   request.setAttribute("auditname", request.getAttribute("auditname"));
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'defaultaudit.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">				
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
	<script>
      var base_path = "<%=request.getContextPath()%>";
	</script>
  </head>
  <body> 
  	<form id="passform">
			<div class="fieldWrapper" align="center">
				<div class="button-group">
					<div class="button_div">
					   <input type="button" class="button-label button" onclick="auditUserSure()" value="确定" />
      				   <input type="button" class="button-label button" onclick="defaultReset()" value="重置" />
					</div>
				</div>
				<table class="top-table">
					<tbody>
						<tr>
							<td class="kv-label">
								默认审核人：
							</td>
							<td class="kv-content" style="padding: 10px;">
								<input type="hidden" class="text-tip" id="defaultAuditId" name="defaultAuditId"  value="${auditid}"/>
								<input type="text" id="defaultAudit" name="defaultAudit" class="auditreset"  value="${auditname}"/>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</form>
 </body>
	<script src="<%=request.getContextPath()%>/script/common/jquery.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/jquery-ui.min.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/jquery.dialog.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/jquery.jqGrid.src.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/crm/audit/defaultaudit.js"></script>
</html>
