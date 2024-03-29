<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.ideassoft.core.bean.pageConfig.PageConfig"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@ page import="com.ideassoft.core.page.Pagination"%>
<%@ include file="../common/taglib.jsp"%>
<!DOCTYPE html>
<%
	PageConfig pageConfig = (PageConfig) request.getAttribute("pageConfig");
	JSONObject json = JSONObject.fromObject(pageConfig);
	json.remove("sql");
	json.remove("dataFormats");
	Object params = request.getAttribute("initParams");
	JSONObject initParams = JSONObject.fromObject(params);
	String showType = pageConfig.getShowType();
%>
<html style="width:100%; height:100%;">
	<head>
		<title>report</title>
		<meta http-equiv="pragma" content="no-cache">
	    <meta http-equiv="cache-control" content="no-cache">
	    <meta http-equiv="expires" content="0">
		<%@ include file="../common/css.jsp"%>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/ui.jqgrid.css"/>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
		</head>
	<body style="width:100%; height:100%;margin:0;padding:0;">
		<form id="reportform" class="form">
			<%@ include file="pageParams.jsp"%>
			<div class="reportform_data">
				<!--<%@ include file="pageCharts.jsp"%>-->
				<div class="reportform_right">
					<div class="function_wrapper">
						<%@ include file="pageFunctions.jsp"%>
						<%@ include file="pageConditions.jsp"%>
					</div>
					<div id="pageData">
						<table id="dataGrid"></table>
						<div id="pager"></div>
						<div id="progressbar" class="c_loading"></div>
						<div id="loadDialog"></div>
						<div class="chart_outter">
							<div id="chart_inner" class="chart_inner"></div>
						</div>
					</div>
				</div>
			</div>
		</form>
		<!-- 隐藏条件 -->
		<input type="hidden" name="modelId" id="modelId" value='${modelId}'/>
		<input type="hidden" name="pageId" id="pageId" value='${pageConfig.pageId}'/>
		
		<%@ include file="../common/script.jsp"%>
		<script>
			var base_path = '<%=request.getContextPath()%>';
			var page_config = JSON.parse('<%=json%>');
			var initParams = JSON.parse('<%=initParams%>');
			var showType = '<%=showType%>';
		</script>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script src="<%=request.getContextPath()%>/script/fusioncharts/fusioncharts.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/datepickerCN.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/jquery.jqGrid.src.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/jquery.zList.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/jquery.fmatter.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/grid.locale-cn.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/ajaxFileUpload.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
		<script src="<%=request.getContextPath()%>/script/pageConditions.js"></script>
		<script src="<%=request.getContextPath()%>/script/pageFunctions.js"></script>
		<script src="<%=request.getContextPath()%>/script/page.js"></script>
	</body>
</html>
