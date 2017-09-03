<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/script.jsp"%>
<!DOCTYPE HTML>
<html>
  <head>
  
    <title>新增提示记录</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/roomlist_check.css"/>
	<link href="//cdn.bootcss.com/font-awesome/4.6.3/css/font-awesome.min.css"  rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
	
  </head>
  
  <body>
		<section class="detail_six add_pro promptdetail">
			<div class="high_header">
					<div class="margintop">
						<form id="bill_date">
							<ul class="clearfix">
						      <li><label class="label_name">房单号：</label><input id="prompt_checkid" name="checkId" style="width: 20.6em;" value="${result['LOGID']}" class="text_number" readonly></li>
						      <li><label class="label_name">内容：</label><textarea id="prompt_content" name="content" readonly>${result["CONTENT"]}</textarea></li>
			       		   </ul>
		       		   </form>
	    		 </div>
			</div>
		</section>
			<script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/workbillroom/util.js"></script>
			<script type="text/javascript" src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
			<script type="text/javascript">
			var path = "<%=request.getContextPath()%>";
			$(function (){
				$(window.parent.document).find(".tab_four").click();
			});
			</script>
  </body>
</html>
