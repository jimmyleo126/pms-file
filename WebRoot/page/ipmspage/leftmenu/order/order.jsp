<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../../common/taglib.jsp"%>
<%@ include file="../../../common/script.jsp"%>
<%
	request.setAttribute("basePath", request.getContextPath());
	request.setAttribute("roomtype", request.getAttribute("roomtype"));
	request.setAttribute("themetype", request.getAttribute("themetype"));
	request.setAttribute("theme", request.getAttribute("theme"));
%>

<!DOCTYPE HTML>
<html>
	<head>
		<title>预订页面</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/css/order/order_details.css" />
		<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
		<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css" />
		<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/common/tipInfo.css" />
		<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/common/jquery-dialog.css"/>
		<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/css/leftmenu/leftmenu.css" />
		<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/css/select/jquery.mCustomScrollbar.min.css" />
	 	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/css/select/select.css" />
	 	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/need/laydate.css"/>
		<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/skins/molv/laydate.css"/>
		<link href="//cdn.bootcss.com/font-awesome/4.6.3/css/font-awesome.min.css"  rel="stylesheet">
		<script>
		      var base_path = "<%=request.getContextPath()%>";
		</script>
	</head>
	<body>
		<div class="walk_margin">
			<input id="roomprice" name="roomprice" type="hidden" />
			<input id="memberId" name="memberId" type="hidden" />
			<input id="memberrank" name="memberRank" type="hidden" />
			<input id="themetype" name="themetype" type="hidden" value="${themetype}"/>
			<h3>预定</h3>
			<span class="close_span">
				<i class="imooc imooc_order" style="color:#3B3E40;" onclick=window.parent.JDialog.close();>&#xe900;</i>
			</span>
			<div class="walk_form">
				<form action="" method="post">
					<section class="detail_one sonepa">
					<label class="label_title">
						1.查会员信息
					</label>
					<input id="memberselect" name="memberselect" type="text" value=""
						class="check check_one" placeholder="手机号" onKeyUp=this.value = this.value.replace(/\D/g, ''); onafterpaste="this.value=this.value.replace(/\D/g,'')">
					<span role="button" class="button_margin submitbutton_order"onclick=selectmember();>查询</span>
					<span role="button" class="button_margin addmember_order orange" onclick=addmember();>注册会员</span>
					</section>
					<section class="detail_one">
					<label class="label_title">
						2.预订单信息
					</label>
					<ul class="clearfix">
						<li>
							<label class="label_name">
								场景
							</label>
							<select id="theme" name="theme" class="check"
								 disabled>
								<c:forEach var="th" items="${theme}">
									<option value="${th.content}">
										${th.paramname}
									</option>
								</c:forEach>
							</select>
						</li>
						<li>
							<label class="label_name" id="arrive">
								抵店日期
							</label>
							<input id="arrivedate" name="arrivedate" type="text" value=""
								class="check date" onblur="adtimejudge()">
						</li>
						<li>
							<label class="label_name" id="leave">
								离店日期
							</label>
							<input id="leavedate" name="leavedate" type="text" value=""
								class="check ldate">
						</li>
						<li>
							<label class="label_name">
								房间类型
							</label>
							<select id="roomtype" name="roomtype" class="check"
								onchange="rtChange()">
								<option id="choosert" value="">
									房间类型
								</option>
								<c:forEach var="rt" items="${roomtype}">
									<option name="${rt.roomtype}" id="${rt.roombed}"
										value="${rt.roomtype}">
										${rt.roomname}
									</option>
								</c:forEach>
							</select>
						</li>
						<li>
							<label class="label_name">
								数量
							</label>
							<span onclick=subNum(); class="count jian">-</span>
							<input id="acount" name="acount" type="text " value="1" class="check number" onKeyUp=this.value = this.value.replace(/\D/g, '') onafterpaste="this.value=this.value.replace(/\D/g,'')">
							<span onclick=addNum(); class="count jia">+</span>
						</li>
						<li>
							<label class="label_name">房价码</label>
							<input id="rpid" name="rpid" type="text" value="" class="check"
								disabled>
							<input id="rpidid" name="rpidid" type="hidden" value=""class="check" disabled>
						</li>
						<li>
							<label class="label_name">
								房价
							</label>
							<input id="price" name="price" type="text" value="" class="check"
								disabled>
						</li>
						<li>
							<label class="label_name">
								预订人
							</label>
							<input id="userorder" name="userorder" type="text" value=""
								class="check" disabled>
						</li>
						<li>
							<label class="label_name">
								预定人手机
							</label>
							<input id="ordemobile" name="ordemobile" type="text" value=""
								class="check" disabled>
						</li>
						<!--<li><label class="label_name">担保</label> <input name="" type="text" value="" class="check" disabled></li>-->
						<li>
							<label class="label_name">
								保留时效
							</label>
							<input id="limited" name="guarantee" type="text" value=""
								class="check" disabled>
						</li>
						<li>
							<label class="label_name">
								接待备注
							</label>
							<textarea id="receptionremark" name="receptionremark" type="text"
								value="" class="check"></textarea>
						</li>
						<li>
							<label class="label_name">
								客房备注
							</label>
							<textarea id="roomremark" name="roomremark" type="text" value=""
								class="check"></textarea>
						</li>
					</ul>
					</section>
					<!--
					   <section class="detail_one">
					   <label class="label_title ">3.入住人信息</label>
					    <div id="tab" class="xx_wrap">
						    <ul  id="tabtitlenew" class="nav">
						        <li id="addnewtab" class="tab_select tabli">新增</li>
						        <li class="tabli" onclick="changeTab('0')">入住人</li>
						       
						    </ul>
						</div>
					    <div id="tabCon" >
					        <div id="tabCon_0" class="showuser style="display:block">
					        <ul id="tabcontent" class="clearfix">
							   <li><label class="label_name">姓名0</label><input id= "usercheckin" name="usercheckin" type="text" value="" class="check"></li>
							   <li><label class="label_name">手机号0</label> <input id= "usermobile" name="usermobile" type="text" value="" class="check"></li>
							</ul>
							</div>
					      
					    </div>
					 </div>
			  	 	</section>
				  	 -->
					<section class="detail_one_order"
						style="background: #FFFFFF;height:142px;">
					<label class="label_title">
						3.预付款
						<!--<span class="yfkspan">（如预付，需支付的金额为</span>
						<input id="allpricr" class="yfk" name="" type="text" value="" />
						<span>元）</span>
					-->
					</label>
					<div id="paymethod" class="paymethod">
						<input id="xj" name="" type="checkbox" />
						<label class="label_name_order">
							现金
						</label>
						<input id="cash" name="cash" type="text" value="" class="check"
							disabled="true"  placeholder="预付金额" onkeyup="num(this)"/>
						<input id="yhk" name="" type="checkbox"/>
						<label class="label_name_order">
							银行卡
						</label>
						<input id="card" name="card" type="text" value="" class="check"
							disabled="true" placeholder="预付金额" onkeyup="num(this)"/>
					</div>
					<div id="order_pzh" class="order_pzh paymethod">
						<label class="label_name_order">
							凭证号
						</label>
						<input id="voucher" name="" type="text" value="" class="check" placeholder="凭证号" onKeyUp="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
					</div>
					<div id="exscore" class="paymethod">
						<input id="jf" name="" type="checkbox" />
						<label class="label_name_order">
							积分
						</label>
						<input id="score" name="score" type="text" value="" class="check"
							disabled="true">
					</div>
					<div id="excash" style="display: none; float: left;">
						<label class="label_name_order">
							金额
						</label>
						<input id="scorecash" name="score" type="text" value=""
							class="check">
					</div>
					</section>
					<section class="detail_four">
						<span class="button_margin" onclick=orderbegin();>预订</span>
					</section>
				</form>
			</div>
		</div>
		<script src="<%=request.getContextPath()%>/script/ipms/js/laydate.dev.js"></script>
	    <script src="<%=request.getContextPath()%>/script/ipms/js/selectjs/jquery.select.js"></script>
        <script src="<%=request.getContextPath()%>/script/ipms/js/selectjs/jquery.mCustomScrollbar.concat.min.js"></script> 
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script src="<%=request.getContextPath()%>/script/ipms/js/leftmenu/order/order.js"></script>
	</body>
</html>
