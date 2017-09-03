 <%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../../common/taglib.jsp"%>
<%@ include file="../../../common/css.jsp"%>
<%@ include file="../../../common/script.jsp"%>
<% request.setAttribute("basePath", request.getContextPath());
   request.setAttribute("mscData", request.getAttribute("mscData")); 
%>

<!DOCTYPE HTML>
<html>
  <head>
    <title>会员查询</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/order/order_details.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
    <link rel="stylesheet"  id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/datetimepicker.css" media="all" />
    <link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/leftmenu/leftmenu.css"/>
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
	<script>
      var base_path = "<%=request.getContextPath()%>";
	</script>
  </head>
  <body>
  <div class="member_margin">
	      <section class="detail_one">
	       <c:forEach items="${mscData}" var="mscData">
			  <table class="table_ms">
			  <col width="10%"/>
			  <col width="23%"/>
			  <col width="10%"/>
			  <col width="23%"/>
			  <col width="10%"/>
			  <col width="23%"/>
			  <tr>
			  <td align="right"><span>会员卡号</span></td>
			  <td colspan="5"><input id="msmemberid" name="" class="input_ms" type="text" value="${mscData.memberid}" disabled="true"/></td>
			  </tr>
			  <tr>
			  <td align="right"><span>会员等级</span></td>
			  <td colspan="5"><input id="" name="" class="input_ms" type="text" value="${mscData.rankname}" disabled="true"/></td>
			  </tr>
			  <tr>
			  <td align="right"><span>姓名</span></td>
			  <td><input id="" name="" class="input_ms" type="text" value="${mscData.membername}" disabled="true"/></td>
			  <td align="right"><span>性别</span></td>
			  <td><input id="" name="" class="input_ms" type="text" value="${mscData.gendor}" disabled="true"/></td>
			  <td align="right"><span>出生日期</span></td>
			  <td><input id="" name="" class="input_ms" type="text" value="${mscData.birthday}" disabled="true"/></td>
			  </tr>
			  <tr>
			  <td align="right"><span>身份证</span></td>
			  <td><input id="" name="" class="input_ms" type="text" value="${mscData.idcard}" disabled="true"/></td>
			  <td align="right"><span>手机号</span></td>
			  <td><input id="msmobile" name="" class="input_ms" value="${mscData.mobile}" type="text"/></td>
			  <td align="right"><span>Email</span></td>
			  <td><input id="msemail" name="" class="input_ms" value="${mscData.email}" type="text"/></td>
			  </tr>
			  <tr>
			  <td align="right"><span>销售来源</span></td>
			  <td><input id="" name="" class="input_ms" type="text" value="${mscData.source}" disabled="true"/></td>
			  <td align="right"><span>生效时间</span></td>
			  <td><input id="" name="" class="input_ms" type="text" value="${mscData.validtime}" disabled="true"/></td>
			  <td align="right"><span>失效时间</span></td>
			  <td><input id="" name="" class="input_ms" type="text" value="${mscData.invalidtime}" disabled="true"/></td>
			  </tr>
			  
			  <tr>
			  <td align="right"><span>地址</span></td>
			  <td colspan="5"><textarea id="msadress" name="" class="textarea_ms" type="text"/>${mscData.address}</textarea></td>
			  </tr>
			  <tr>
			  <td align="right"><span>备注</span></td>
			  <td colspan="5"><textarea id="msremark" name="" class="textarea_ms" type="text"/>${mscData.remark}</textarea></td>
			  </tr>
			  </table>
			  </c:forEach>
		   </section>   			    
          <div class="editdata_ms">
             <input type="button" class="button_margin submitbotton_membersearch two_button" onclick="editmemberdata()" value="修改资料">
          </div>
       </div>
	<script></script>
	<script src="<%=request.getContextPath()%>/script/common/jquery-ui.min.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/jquery.dialog.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/jquery.jqGrid.src.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/ipms/js/leftmenu/membersearch.js"></script>
  </body>
</html>
