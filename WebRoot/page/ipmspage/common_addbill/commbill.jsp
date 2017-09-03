<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../common/script.jsp"%>
<!DOCTYPE HTML>
<html>
	<head>
		<title>公共入账页面</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
		<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/roomlist_check.css" />

	</head>

	<body>
	<div class="data-container">
		<div class="view-all" id="project_data">
		<!--  <p class="data-title">
				消费
			</p>
			<div class="data-list data-list-hot">
				<ul class="clearfix">
					<li>
						<a href="javascript:;" class="d-item">
							赔偿
						</a>
					</li>
					<li>
						<a href="javascript:;">保险赠送</a>
					</li>
					<li>
						<a href="javascript:;"">电话费</a>
					</li>
					<li>
						<a href="javascript:;">
						其他杂项
						</a>
					</li>
					<li>
						<a href="javascript:;">房费调整
						</a>
					</li>
					<li>
						<a href="javascript:;">
						餐厅
						</a>
					</li>
					<li>
						<a href="javascript:;">
							佣金
						</a>
					</li>
					<li>
						<a href="javascript:;">
							出租收入
						</a>
					</li>
				</ul>
			</div>
			<p class="data-title">
				结算
			</p>
			<div class="data-list data-list-hot">
				<ul class="clearfix">
					<li>
						<a href="javascript:;">
							现金支出
						</a>
					</li>
					<li>
						<a href="javascript:;" >
						 现金
						</a>
					</li>
					<li>
						<a href="javascript:;">银行卡
						</a>
					</li>
				</ul>
			</div>-->
		</div>
	</div>
	<script type="text/javascript">
		var path = "<%=request.getContextPath()%>";
  		$(function(){
  			getallproject();
  		});
  		
  		function getallproject(){
			$.ajax({
		         url: path + "/getproject.do",
				 type: "post",
				 data : {},
				 success: function(json) {
				 	console.log(json)
				 	loadroomtype(json);
				 	
				 },
				 error: function(json) {}
			});
  		}
  		
  		function loadroomtype(json){
				 var tabledata = "";
			 	$.each(json, function(indextype){
			 		tabledata = tabledata + "<p class='data-title'>" + indextype + 
			 								"</p><div class='data-list data-list-hot'><ul class='clearfix'>";
			 		$.each(json[indextype], function(index){
			 			tabledata = tabledata + "<li onclick='setvalue(this)'><a>" + json[indextype][index] + "</a><span style='display: none'>"+ index +"</span></li>";
			 		})
			 		
			 		tabledata = tabledata + "</ul></div>";
			 		/*tabledata = tabledata + "<tr class='odd' '>" +
			 		"<td>" + json[index]["BILLID"] + "</td>" +
			 		"<td>" + json[index]["PROJECTID"] + "</td>" +
			 		"<td>" + json[index]["PROJECTNAME"] + "</td>" +
			 		"<td>" + converttosomething(json[index]["COST"], null, '') + "</td>" +
			 		"<td>" + converttosomething(json[index]["PAY"], null, '') +"</td>" +
			 		"<td>" + json[index]["RECORDUSER"] + "</td>" +
			 		"<td>" + dealDate(json[index]["RECORDTIME"]) + "</td>" +
			 		"<td>" + json[index]["PAYMENT"] + "</td>"+
			 		"<td>" + json[index]["REMARK"] + "</td>" +
			 		"</tr>";*/
			 	});
			 	if(json.length == 0){
			 		$("#project_data").html("");
			 	}else{
			 		$("#project_data").html(tabledata);			 	
			 	}
  		}
  		function setvalue(e){
  			window.parent.$("#project").val($(e["childNodes"][0]).html());
  			window.parent.$("#projectid").val($(e["childNodes"][1]).html());
  			window.parent.$("#ontime").css("display","none");
  		}
	</script>
	</body>
</html>
