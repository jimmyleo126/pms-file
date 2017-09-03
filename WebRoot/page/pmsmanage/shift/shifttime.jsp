<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/taglib.jsp"%>
<%@ include file="../../common/css.jsp"%>
<%@ include file="../../common/script.jsp"%>
<% request.setAttribute("basePath", request.getContextPath()); 
   request.setAttribute("shiftcontent", request.getAttribute("shiftcontent"));
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
	<link href="<%=request.getContextPath()%>/css/pmsmanage/common/datetimepicker.min.css" rel="stylesheet" type="text/css" media="all" />
	<link href="<%=request.getContextPath()%>/css/pmsmanage/common/zui.min.css" rel="stylesheet" type="text/css" media="all" />
	<script>
      var base_path = "<%=request.getContextPath()%>";
	</script>
  </head>
  <body> 
  	<form id="passform">
			<div class="fieldWrapper" align="center">
				<table class="top-table">
					<tbody>
					  	<c:forEach  items="${shiftcontent}" var="sc" varStatus="index">
						<tr>
							<td class="kv-label">
								<label>${sc.paramname}</label>
							</td>
							<td class="kv-content" style="padding: 10px;">
								<input id="shiftbegin" class="Wdate" name="" onfocus="WdatePicker({dateFmt:'HH:mm:ss'})"  value="${sc.paramdesc}" />
								&nbsp;&nbsp;至&nbsp;&nbsp;
								<input id="shiftend" class="Wdate"  name="" onfocus="WdatePicker({dateFmt:'HH:mm:ss'})"  value="${sc.content}" />
							</td>
						</tr>
						</c:forEach >
					</tbody>
				</table>
				<div class="button-group">
					<div class="button_div">
					   <input type="button" class="button-label button" onclick="" value="确定" />
      				   <input type="button" class="button-label button" onclick="" value="重置" />
					</div>
				</div>
			</div>
		</form>
 </body>

	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/pmsmanage/common/datetimepicker.min.js"></script>
	<script src="<%=request.getContextPath()%>/script/pmsmanage/common/zui.min.js"></script>
	<script src="<%=request.getContextPath()%>/script/pmsmanage/common/WdatePicker.js"></script>
	<script src="<%=request.getContextPath()%>/script/crm/audit/defaultaudit.js"></script>
	<script>
	$(".form-time").datetimepicker({
    language:  "zh-CN",
    weekStart: 1,
    todayBtn:  1,
    autoclose: 1,
    todayHighlight: 1,
    startView: 1,
    minView: 0,
    maxView: 1,
    forceParse: 0,
    format: 'hh:ii'
});
	</script>
	
</html>
