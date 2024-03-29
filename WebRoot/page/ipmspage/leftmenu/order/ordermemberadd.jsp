 <%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../../common/taglib.jsp"%>
<%@ include file="../../../common/script.jsp"%>
<% request.setAttribute("basePath", request.getContextPath());
%>

<!DOCTYPE HTML>
<html class="whitebg">
  <head>
    <title>会员注册</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/order/order_details.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
    <link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/leftmenu/leftmenu.css"/>
    <link href="//cdn.bootcss.com/font-awesome/4.6.3/css/font-awesome.min.css"  rel="stylesheet"/>
    <link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/need/laydate.css" />
	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/skins/molv/laydate.css"/>
	<script>
      var base_path = "<%=request.getContextPath()%>";
	</script>
  </head>
  <body>
  <div class="register_margin"><!--
     <i class="imooc imooc_order_gdspage" style="color:#3B3E40;" onclick="window.parent.JDialog.close();">&#xe902;</i>-->
     <form action="" method="post" id="omemberadd" name="omemberadd">
	      <section class="register_one">
	        <ul class="clearfix">
			  <li><label class="label_name red">姓名</label><input id="omembername" name="omembername" class="input_ms" type="text" maxlength="10"/></li>
			  <li><label class="label_name red">登录名</label><input id="ologinname" name="ologinname" class="input_ms" type="text" maxlength="10"/></li>
			  <li><label class="label_name red">性别</label>
			  	<select id="ogendor" name="ogendor" class="select_omadd">
	                <option  value="0">女</option>  
                    <option  value="1">男 </option> 
                </select>	
              </li>
              <li><label class="label_name">身份证</label><input id="oidcard" name="oidcard" class="input_ms" type="text" onKeyUp="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" maxlength="18"/></li>
			  <li><label class="label_name red">手机号</label><input id="omobile" name="omobile" class="input_ms" type="text" onKeyUp="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" maxlength="11"/></li>
			  <li><label class="label_name">出生日期</label>
			 	<input id="obirthday" name="obirthday" class="input_ms date" type="text"/>
			 	<i class="fa fa-calendar-check-o"></i>
			  </li>
			  <li><label class="label_name">Email</label><input id="oemail" name="oemail" class="input_ms" type="text" maxlength="20"/></li>
		      <li><label class="label_name">邮编</label><input id="opostcode" name="opostcode" class="input_ms" type="text" onKeyUp="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" maxlength="6"/></li>
			  <li><label class="label_name">地址</label><textarea id="oaddress" name="oaddress" class="textarea_ms" type="text" maxlength="30"/></textarea></li>
			  <li><label class="label_name">备注</label><textarea id="oremark" name="oremark" class="textarea_ms" type="text" maxlength="100"/></textarea></li>
			  </ul>
		   </section> 
		   <span class="warnningspan_om">注：红色字体为必填项</span>  
	 </form> 	    
       <div class="editdata_ms">
          <input type="button" class="button_margin submitbotton_membersearch" onclick="ordernewmember()" value="注册会员">
       </div>
   </div>
   	<script>
   		
   	</script>
    <script src="<%=request.getContextPath()%>/script/ipms/js/laydate.dev.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/ipms/js/leftmenu/order/ordermemberadd.js"></script>
  </body>
</html>
