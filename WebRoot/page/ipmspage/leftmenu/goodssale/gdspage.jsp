
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../../common/taglib.jsp"%>
<%@ include file="../../../common/css.jsp"%>
<%@ include file="../../../common/script.jsp"%>
<%
	request.setAttribute("basePath", request.getContextPath());
	request.setAttribute("gdsid", request.getAttribute("gdsid"));
	request.setAttribute("gdsname", request.getAttribute("gdsname"));
	request.setAttribute("gdsprice", request.getAttribute("gdsprice"));
	request.setAttribute("gdsproject", request
			.getAttribute("gdsproject"));
	request.setAttribute("gdsprojectpay", request
			.getAttribute("gdsprojectpay"));
	request.setAttribute("workbill", request.getAttribute("workbill"));
%>

<!DOCTYPE HTML>
<html class="whitebg">
	<head>
		<title>售卖页面</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" id="style" type="text/css"
			href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
		<link rel="stylesheet" id="style" type="text/css"
			href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css" />
		<link rel="stylesheet" id="style" type="text/css"
			href="<%=request.getContextPath()%>/css/ipms/css/shopsell/shopsell.css" />
		<link rel="stylesheet" id="style" type="text/css"
			href="<%=request.getContextPath()%>/css/common/tipInfo.css" />
		<link rel="stylesheet" id="style" type="text/css"
			href="<%=request.getContextPath()%>/css/ipms/css/leftmenu/leftmenu.css" />
		<link href="<%=request.getContextPath()%>/css/common/tipInfo.css"
			rel="stylesheet" type="text/css" media="all" />
		<script>
      		var base_path = "<%=request.getContextPath()%>";
		</script>
	</head>
	<body style="background: transparent;">
		<input id="gdsid" type="hidden" value="${gdsid}" />
		<input id="gdspriceprice" type="hidden" value="${gdsprice}" />
		<div id="singlesale" class="">
			<div class="sale_div">
				<div class="multiplesale_div">
					<table id="myTable" class="myTable tabledata_gdspage" border="0"
						width="100">
						<thead id="log">
							<tr>
								<th class="header">
									商品名称
								</th>
								<th class="header">
									售价
								</th>
								<th class="header">
									小计
								</th>
								<th class="header">
									数量
								</th>
							</tr>
						</thead>
						<tbody id="info">
							<tr>
								<td align="center">
									${gdsname}
								</td>
								<td align="center" id="gdsprice">
									${gdsprice}
								</td>
								<td align="center" id="gdsacount">
									${gdsprice}
								</td>
								<td align="center">
									<span onclick="subNum()" class="jian count">-</span>
									<input id="acount" name="acount" onblur="lacount()" type="text"
										value="1" class="acount_gdspage"
										onKeyUp=this.value = this.value.replace(/\D/g, '');onafterpaste="this.value=this.value.replace(/\D/g,'')">
									<span onclick="addNum()" class="jia count">+</span>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="totaltable">
					<label>
						总计数量：
					</label>
					<input class="totalinput" id="totalnumber" value="1" disable="true" />
					<label>
						总计金额：
					</label>
					<input class="totalinput" id="totalprice" value="${gdsprice}"
						disable="true" />
				</div>
				<div class="paymethod_gdspage">
					<div id="paycheckbox" class="footertwo_gdspage">
						<span>付款方式：</span>
						<input id="roompay" type="checkbox" class="check">
						<span class="spanmar_gdspage">挂房账</span>
						<input id="cashpay" type="checkbox" class="check">
						<span class="spanmar_gdspage">现金</span>
						<input id="cardpay" type="checkbox" class="check">
						<span>银行卡</span>
					</div>
				</div>
				<div id="" class="footerthree_gdspage">
					<input id="cashpayvalue" type="text"
						class="check payinput_gdspage_cash">
					<input id="cardpayvalue" type="text"
						class="check payinput_gdspage_card" placeholder="凭证号"
						onKeyUp=this.value = this.value.replace(/\D/g, ''); onafterpaste="this.value=this.value.replace(/\D/g,'')">
				</div>
				<div id="payroom" class="footerone_gdspage">
				
					<input id="gdsroomid" name="roomid" type="text" class="check roomselect_gdspage" disabled="true" placeholder="房号"/>
					<input id="gdscheckid" name="gdscheckid" type="hidden" class="check roomselect_gdspage"/>
					<input id="" name="" class="reset_gdspage" type="button" value="清空" class="check roomselect_gdspage" onclick="cleargdsroom();"/>
				</div>
				<div id="costproject" class="footerzero_gdspage">
					<span>消费项目：</span>
					<select id="gdsproject" name="" class="check projectselect_gdspage">
						<c:forEach var="th" items="${gdsproject}">
							<option value="${th.content}">
								${th.paramname}
							</option>
						</c:forEach>
					</select>
				</div>
				<div id="payproject">
					<div class="footerzero_gdspage">
						<span>结算项目：</span>
						<select id="gdsprojectpay" name=""
							class="check projectselect_gdspage">
							<c:forEach var="th" items="${gdsprojectpay}">
								<option value="${th.content}">
									${th.paramname}
								</option>
							</c:forEach>
						</select>
					</div>
					<div class="footerzero_gdspage">
						<span>工作账：</span>
						<select id="gdsworkbill" name="" class="check projectselect_gdspage">
							<c:forEach var="th" items="${workbill}">
								<option value="${th.workbillid}">
									${th.name}
								</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="sbotton_gdspage">
					<input id="" name="" type="button"
						class="submitbotton_gdspage clean enter" style="float: right;"
						value="入账" onclick="gdspaysubmit();"/>
					<input id="" name="" type="button"
						class="submitbotton_gdspage clean" style="float: right;"
						value="重置" onclick="location.reload(true);"/>
				</div>
			</div>
		</div>
			<script>
			$(document).ready(
			function() {
				$("#paycheckbox").children('input[type=checkbox]').click(
						function() {
							if ($(this).is(':checked')) {
								$(this).attr('checked', true).siblings().attr(
										'checked', false);
								if ($(this).attr('id') == "roompay") {
									$("#gdsroomid").attr("disabled", false);
									$("#payroom").show();
									$("#cashpayvalue").val("");
									$("#cashpayvalue").hide();
									$("#cardpayvalue").val("");
									$("#cardpayvalue").hide();
									$("#costproject").show();
									$("#payproject").hide();
								} else if ($(this).attr('id') == "cashpay") {
								    $("#payroom").hide();
									$("#gdsroomid").val("");
									$("#gdsroomid").attr("disabled", true);
									$("#cashpayvalue").show();
									$("#cashpayvalue").val($("#totalprice").val());
									$("#cardpayvalue").val("");
									$("#cardpayvalue").hide();
									$("#costproject").hide();
									$("#payproject").show();
								} else if ($(this).attr('id') == "cardpay") {
								    $("#payroom").hide();
									$("#gdsroomid").val("");
									$("#gdsroomid").attr("disabled", true);
									$("#cardpayvalue").show();
									$("#cashpayvalue").val("");
									$("#cashpayvalue").hide();
									$("#costproject").hide();
									$("#payproject").show();
								}
							} else {
								$(this).attr('checked', false).siblings().attr(
										'checked', false);
								$("#gdsroomid").val("");
								$("#gdsroomid").attr("disabled", true);
								$("#cashpayvalue").val("");
								$("#cashpayvalue").hide();
								$("#cardpayvalue").val("");
								$("#cardpayvalue").hide();
								$("#costproject").hide();
								$("#payproject").hide();
							}
						})

			})

	$("#gdsroomid").bind("click", function() {
		JDialog.open("挂房账", base_path + "/gdsroomSelect.do", 600, 300);
	});

	function subNum() {
		var c = parseInt($("#acount").val());
		if (c >= 2) {
			document.getElementById("acount").value = c - 1;
			var nown = document.getElementById("acount").value;
			var gdsprice = $("#gdsprice").html()
			var allpricr = parseInt(nown * gdsprice);
			$("#gdsacount").html(allpricr);
			$("#totalprice").val(allpricr);
			$("#totalnumber").val(nown);

		} else {
			showMsg("数量不可为小于1的数字！");
		}
	}

	function addNum() {

		var c = parseInt($("#acount").val());
		document.getElementById("acount").value = c + 1;
		var nown = document.getElementById("acount").value;
		var gdsprice = $("#gdsprice").html();
		var allpricr = parseInt(nown * gdsprice);
		$("#gdsacount").html(allpricr);
		$("#totalprice").val(allpricr);
		$("#totalnumber").val(nown);
	}

	function lacount() {
		var nown = document.getElementById("acount").value;
		var re = /^[0-9]+$/;
		if (re.test(nown)) {
			var gdsprice = $("#gdsprice").html();
			var allpricr = parseInt(nown * gdsprice);
			$("#gdsacount").html(allpricr);
			$("#totalprice").val(allpricr);
			$("#totalnumber").val(nown);
		} else {
			showMsg("数量必须为正整数，请重新输入");
			window.setTimeout("location.reload(true)", 1500);
		}
	}

	document.onkeydown = function(event) {
		var e = event || window.event || arguments.callee.caller.arguments[0];
		if (e && e.keyCode == 13) { // enter 键
			var nown = document.getElementById("acount").value;
			var re = /^[0-9]+$/;
			if (re.test(nown)) {
				var gdsprice = $("#gdsprice").html();
				var allpricr = parseInt(nown * gdsprice);
				$("#gdsacount").html(allpricr);
				$("#totalprice").val(allpricr);
				$("#totalnumber").val(nown);
			} else {
				showMsg("数量必须为正整数，请重新输入");
				window.setTimeout("location.reload(true)", 1500);
			}
		}
	};

	function cleargdsroom() {
		$("#gdsroomid").val("");

	}

	function gdspaysubmit() {
		var gdscheckid = $("#gdscheckid").val();
		var gdsproject = $("#gdsproject").val();
		var gdsprojectname = $("#gdsproject").find("option:selected").text();
		var totalprice = $("#totalprice").val();

		var gdsroomid = $("#gdsroomid").val();
		var gdsid = $("#gdsid").val();
		var totalnumber = $("#totalnumber").val();
		var gdsprice = $("#gdspriceprice").val();
		var cashpayvalue = $("#cashpayvalue").val();
		var gdsprojectpay = $("#gdsprojectpay").val();
		var gdsprojectpayname = $("#gdsprojectpay").find("option:selected")
				.text();
		var gdsworkbill = $("#gdsworkbill").val();
		var gdsworkbillname = $("#gdsworkbill").find("option:selected").text();
		var cardpayvalue = $("#cardpayvalue").val();
		if ($("#roompay").is(':checked')) {
			if (gdsroomid) {
				$.ajax( {
					url : base_path + "/gdsRoompay.do",
					type : "post",
					dataType : "json",
					data : {
						'gdscheckid' : gdscheckid,
						'gdsproject' : gdsproject,
						'gdsprojectname' : gdsprojectname,
						'totalprice' : totalprice,
						'gdsroomid' : gdsroomid,
						'gdsid' : gdsid,
						'totalnumber' : totalnumber,
						'gdsprice' : gdsprice
					},
					success : function(json) {
						if (1 == json.result) {
							showMsg("挂账成功！");
							window.setTimeout("location.reload(true)", 1800);
							window.setTimeout("window.parent.JDialog.close();",
									1800);

						} else {
							showMsg("挂账失败！");
							window.setTimeout("location.reload(true)", 1800);
							window.setTimeout("window.parent.JDialog.close();",
									1800);
						}
					},
					error : function() {
						showMsg("操作失败，请重新操作！");
						window.setTimeout("location.reload(true)", 1800);
						window.setTimeout("window.parent.JDialog.close();",
								1800);
					}
				});
			} else {
				showMsg("请先选择挂账房间");
			}

		} else if ($("#cashpay").is(':checked')) {
			if (cashpayvalue) {
				if (cashpayvalue == totalprice) {

					$
							.ajax( {
								url : base_path + "/gdsRoompaycash.do",
								type : "post",
								dataType : "json",
								data : {
									'gdscheckid' : gdscheckid,
									'gdsprojectpay' : gdsprojectpay,
									'gdsprojectpayname' : gdsprojectpayname,
									'gdsworkbill' : gdsworkbill,
									'gdsworkbillname' : gdsworkbillname,
									'totalprice' : totalprice,
									'cashpayvalue' : cashpayvalue,
									'gdsid' : gdsid,
									'totalnumber' : totalnumber,
									'gdsprice' : gdsprice
								},
								success : function(json) {
									if (1 == json.result) {
										showMsg("售卖成功！");
										window.setTimeout(
												"location.reload(true)", 1800);
										window
												.setTimeout(
														"window.parent.JDialog.close();",
														1800);

									} else {
										showMsg("售卖失败！");
										window.setTimeout(
												"location.reload(true)", 1800);
										window
												.setTimeout(
														"window.parent.JDialog.close();",
														1800);
									}
								},
								error : function() {
									showMsg("操作失败，请重新操作！");
									window.setTimeout("location.reload(true)",
											1800);
									window.setTimeout(
											"window.parent.JDialog.close();",
											1800);
								}
							});

				} else {
					showMsg("需支付金额与输入金额不等，请重新输入");

				}

			} else {
				showMsg("请输入支付金额");
			}
		} else if ($("#cardpay").is(':checked')) {
			if (cardpayvalue) {
				if (cardpayvalue.length > 6) {
					showMsg("凭证号长度不能超过6位");
				} else {
					$
							.ajax( {
								url : base_path + "/gdsRoompaycard.do",
								type : "post",
								dataType : "json",
								data : {
									'gdscheckid' : gdscheckid,
									'gdsprojectpay' : gdsprojectpay,
									'gdsprojectpayname' : gdsprojectpayname,
									'gdsworkbill' : gdsworkbill,
									'gdsworkbillname' : gdsworkbillname,
									'totalprice' : totalprice,
									'cardpayvalue' : cardpayvalue,
									'gdsid' : gdsid,
									'totalnumber' : totalnumber,
									'gdsprice' : gdsprice
								},
								success : function(json) {
									if (1 == json.result) {
										showMsg("售卖成功！");
										window.setTimeout(
												"location.reload(true)", 1800);
										window
												.setTimeout(
														"window.parent.JDialog.close();",
														1800);

									} else {
										showMsg("售卖失败！");
										window.setTimeout(
												"location.reload(true)", 1800);
										window
												.setTimeout(
														"window.parent.JDialog.close();",
														1800);
									}
								},
								error : function() {
									showMsg("操作失败，请重新操作！");
									window.setTimeout("location.reload(true)",
											1800);
									window.setTimeout(
											"window.parent.JDialog.close();",
											1800);
								}
							});
				}
			} else {
				showMsg("请先输入凭证号");
			}

		} else {
			showMsg("请先选择付款方式");
		}

	}
</script>
			<script
				src="<%=request.getContextPath()%>/script/common/jquery-ui.min.js"></script>
			<script
				src="<%=request.getContextPath()%>/script/common/jquery.dialog.js"></script>
			<script
				src="<%=request.getContextPath()%>/script/common/jquery.jqGrid.src.js"></script>
			<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
			<script
				src="<%=request.getContextPath()%>/script/ipms/js/leftmenu/goodssale.js"></script>
	</body>
</html>
