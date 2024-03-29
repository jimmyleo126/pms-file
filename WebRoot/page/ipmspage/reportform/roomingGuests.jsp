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
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/pagination.css" />
	    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/pagination.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-ui.css"/>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css"/>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reportform/report_forms.css" />
		<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/select/jquery.mCustomScrollbar.min.css" />
	 	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/select/select.css" />
		<title>在住客人明细报表</title>
	</head>
	<body style="overflow:hidden;">
		<div class="shop_search">
			<form action="queryRoomingGuest.do" method="post" id = "myForm">
				<div class="form_margin">
				<!--<select name="branchId" id="branchId" class="search_select">
					<option value="">
						归属门店
					</option>
					<c:forEach items="${branchList}" var="bl">
						<option value="${bl.branchId}">
							${bl.branchName}
						</option>
					</c:forEach>
					</select>-->
					<!--<input type="text" name="roomType" id="roomType" class="shop_name" value="${roomType}" placeholder="房间类型" />
					<input type="hidden" name="roomType_CUSTOM_VALUE" id="roomType_CUSTOM_VALUE" class="shop_name" value="${roomType_CUSTOM_VALUE}"  />
					-->
					<select name="roomType" id="roomType" class="search_select mySelect">
						<option value="">房间类型</option>
						<c:forEach items="${roomTypeList}" var="rl">
							<option value="${rl.roomType}">
								${rl.roomName}
							</option>
						</c:forEach>
					</select>
					<input type="text" name="roomId" id="roomId" class="shop_name rnumber" value="${roomId}" placeholder="房间号"/>
					<input type="text" name="checkUser" id="checkUser" class="shop_name" value="${checkUser}" placeholder="客人名称" />
					<button type="button" class="btn_style btn_search button_margin" onclick="search();">查询</button>
				</div>
				<section class="box-content-section fl">
				<section class="box_content_widget fl">
				<div class="content">
					<iframe name="frame" id="frame" class="myTable" frameborder="0" width="100%" height="100%" ></iframe>
				</div>
				<div id="pager"></div>
				</section>
				</section>
			</form>
		</div>
		<%@ include file="../../common/script.jsp"%>
		<script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/selectjs/jquery.select.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/selectjs/jquery.mCustomScrollbar.concat.min.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script src="<%=request.getContextPath()%>/script/ipms/js/reportforms/roomingGuests.js" charset="utf-8"></script>
		<script>
			var base_path = '<%=request.getContextPath()%>';
			Pager.renderPager(<%=pagination%>);
			var roomType ="${roomType}";
			$(document).ready(function(){
			$("#roomType").val(roomType);
				/*初始化下拉表单*/
				$(".mySelect").select({
				   width: "140px"
				});
				$("#frame").attr('src',"queryRoomingGuestData.do?roomType_CUSTOM_VALUE="+$("#roomType_CUSTOM_VALUE").val()+"&roomType="+$("#roomType").val()+"&roomId="+$("#roomId").val()+"&checkUser="+$("#checkUser").val());
			    $("#roomType").change(function(){
			    	if($("#roomType").val()== null || $("#roomType").val() ==""){
			    		$("#roomType_CUSTOM_VALUE").val("");
			    	}
			    })
		    }) 
	 	</script>
	</body>
</html>