<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../../common/taglib.jsp"%>
<%@ include file="../../../common/css.jsp"%>
<%@ include file="../../../common/script.jsp"%>
<%
//String path = request.getContextPath();
//String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setAttribute("basePath", request.getContextPath());
request.setAttribute("cashcountin", request.getAttribute("cashcountin"));
request.setAttribute("cashcountout", request.getAttribute("cashcountout"));
request.setAttribute("cardcount", request.getAttribute("cardcount"));
request.setAttribute("cards", request.getAttribute("cards")); 
request.setAttribute("boxname", request.getAttribute("boxname"));
request.setAttribute("boxcount", request.getAttribute("boxcount")); 
request.setAttribute("shift", request.getAttribute("shift"));
request.setAttribute("shifterid", request.getAttribute("shifterid")); 
request.setAttribute("shiftername", request.getAttribute("shiftername"));
request.setAttribute("branchid", request.getAttribute("branchid")); 
request.setAttribute("branchname", request.getAttribute("branchname")); 
request.setAttribute("shiftname", request.getAttribute("shiftname"));
request.setAttribute("lastshiftvalue", request.getAttribute("lastshiftvalue")); 
request.setAttribute("submitperson", request.getAttribute("submitperson"));   
%>
<!DOCTYPE HTML>
<html>
  <head>
    <title>备用金登记</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/cash/cashregister.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/leftmenu/leftmenu.css"/>
    <link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/datetimepicker.css" media="all" />
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
	<script>
      var base_path = "<%=request.getContextPath()%>";
	</script>
  </head>
  <body>
    <div class="check_wrap_margin check_color">
			<h2>备用金</h2>
			<span class="close_span close_cash">
				<i class="imooc imooc_cash" style="color:#3B3E40;" onclick=window.parent.JDialog.close();>&#xe900;</i>
			</span>
			<div class="top_check">
				<div class="left_check fl">
					<ul>
						<li class="tab_one"><span>备用金交接</span></li>
						<div class="fr left_div">
							<span id="nowdate" class="">营业日 :</span>
							<span>|</span>
							<span class="name">${branchname}</span>
							<span>|</span>
							<span class="banci">班次:<span class="dateselect">${shiftname}</span></span>
							<!--<span class="banci">班次:<span class="dateselect"><%=request.getParameter("shiftname")%></span></span>
					--></div>
					</ul>
				</div>
			</div>
			<!--内容展示部分-->
			<div class="content_color">
			<input id="shift" name="shift" type="hidden" value="${shift}"/>
			<input id="shifterid" name="shifterid" type="hidden" value="${shifterid}"/>
			<input id="boxname" name="boxname" type="hidden" value="${boxname}"/>
			<input id="branchid" name="branchid" type="hidden" value="${branchid}"/>
			<input id="boxcount" name="boxcount" type="hidden" value="${boxcount}"/>
			<input id="cashcountout" name="cashcountout" type="hidden" value="${cashcountout}"/>
				<div class="ifa" id="f">
					<div class="check_one fl">
						<form action="" method="post">
							<div class="cabinet_cash fl">
								<ul>
									<li><label class="label_name">备用金柜</label>
									<select id="cashbox" name="" class="check" disabled="true">
										<option value="">请选择</option>
										<option value="A">A</option>
										<option value="B">B</option>
									</select></li>
								</ul>
							</div>
							<ul class="clearfix">
								<li><label class="label_name">交接人</label> <input id="nowshifter" name="" type="text" value="${shiftername}" class="check" disabled="true"></li>
								<li><label class="label_name">确认人</label> 
								                                             <select id="submitstaff" name="submitstaff" class="check">
								   <option value="">
									       请选择确认人
								     </option>
							      <c:forEach var="sp" items="${submitperson}">
								    <option value="${sp.staffid}">
									   ${sp.staffname}
								     </option>
							      </c:forEach>
						         </select>
								</li>
								<li><label class="label_name">现金收入</label> <input id="cashin" name="" type="text" value="${cashcountin}" class="check" onkeyup="num(this)" maxlength="9"></li>
								<li><label class="label_name">现金支出</label> <input id="cashout" name="" type="text" value="${cashcountout}" class="check" onkeyup="num(this)" maxlength="9"></li>
								<li><label class="label_name">金柜本金</label> <input id="cashboxhave" name="" type="text" value="${boxcount}" class="check" disabled="disabled"></li>
								<li><label class="label_name">备用金交接金额</label> <input id="boxnow" name="" type="text" value="" class="check" onkeyup="num(this)" maxlength="9"></li>
								<li><label class="label_name">投缴金额</label> <input id="givecash" name="" type="text" value="" class="check" onkeyup="num(this)" maxlength="9"></li>
								<li><label class="label_name">补上班次备用金额</label> <input id="lastshiftvalue" name="" type="text" value="${lastshiftvalue}" class="check" onkeyup="num(this)" maxlength="9"></li>
								<li><label class="label_name">本班需补备用金额</label> <input id="fixcash" name="" type="text" value="" class="check" onkeyup="num(this)" maxlength="9"></li>
								<li><label class="label_name">银行卡收入</label> <input id="cardin" name="" type="text" value="${cardcount}" class="check" onkeyup="num(this)" maxlength="9"></li>
								<li><label class="label_name">银行卡笔数</label> <input id="cards" name="" type="text" value="${cards}" class="check" onKeyUp="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" maxlength="2"></li>
								<li><label class="label_name">结账日期	</label><input id="payday" name="" type="text" value="" class="check" disabled="disabled"></li>
							</ul>
							<div class="card">
								<label class="label_name">押金收据号码</label><textarea id="depositno" name="" rows="2" cols="80" onKeyUp="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" maxlength="28"></textarea>
								<br/>
								<label class="label_name">发票号码</label><textarea id="invoiceno" name="" rows="2" cols="80"  onKeyUp="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" maxlength="28"></textarea>
								<br/>
								<label class="label_name">备注</label><textarea id="remark" name="" rows="2" cols="80" maxlength="100"></textarea>
							</div>
							  <div class="editdata_mss">
                            <input type="button" class="button_margin submitbotton_membersearch" onclick="cashshift()" value="交接">
                            </div>
						</form>
					</div>
				</div>
				<div id="a" style="display:none;">
				 <iframe name="goodsIframe" id="goodsIframe" frameborder="0" width="100%" height="100%" src=""></iframe>
				</div>
			</div>
		</div>
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script type="text/javascript">
		   Date.prototype.Format = function (fmt) { //author: meizz 
		   var o = {
				"M+": this.getMonth() + 1, //月份 
				"d+": this.getDate(), //日 
				"h+": this.getHours(), //小时 
				"m+": this.getMinutes(), //分 
				"s+": this.getSeconds(), //秒 
				"q+": Math.floor((this.getMonth() + 3) / 3), //季度 
				"S": this.getMilliseconds() //毫秒 
			};
			if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
			for (var k in o)
			if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
			return fmt;
			}
		  $(document).ready(function(){
		    var now = new Date();
            var time = now.Format("yyyy/MM/dd");
            var b =document.getElementById ("nowdate");
            b.innerHTML = "营业日 :"+time;
		    var boxname = $("#boxname").val();
	        $("#cashbox option[value='"+boxname+"']").attr("selected",true);
	        $("#payday").val(time);
	        var cashcountout = ($("#cashcountout").val()).replace('-', '');
	        $("#cashout").val(cashcountout);
	        var boxcount = $("#boxcount").val();
	        var cashin = $("#cashin").val();
	        var cashout = $("#cashout").val();
	        var lastshiftvalue = $("#lastshiftvalue").val();
	        var givepay = "0"
	        if((parseFloat(cashin) - parseFloat(cashout)- parseFloat(lastshiftvalue))>0){
	           givepay = (parseFloat(cashin) - parseFloat(cashout)- parseFloat(lastshiftvalue)).toFixed(2);
	        }
	        $("#givecash").val(givepay);
	         var boxhave = boxcount;
	        if(givepay<0){
	           boxhave =  (parseFloat(boxcount)+parseFloat(cashin) - parseFloat(cashout)- parseFloat(lastshiftvalue)).toFixed(2);	
	        }
	           
	        $("#boxnow").val(boxhave);
	         var needcash = "0";
	        if((parseFloat(boxcount) - parseFloat(boxhave))>0){
	         needcash = (parseFloat(boxcount) - parseFloat(boxhave)).toFixed(2);
	        }
	        $("#fixcash").val(needcash);
		  });
		 
		  function showMsg(msg, fn) {
				if (!fn) {
				TipInfo.Msg.alert(msg);
				   } else {
				TipInfo.Msg.confirm(msg, fn);
				  }
		   }
		  
		  function num(obj) {
				 obj.value = obj.value.replace(/[^\d.]/g, ""); // 清除"数字"和"."以外的字符
				 obj.value = obj.value.replace(/^\./g, ""); // 验证第一个字符是数字
				 obj.value = obj.value.replace(/\.{2,}/g, "."); // 只保留第一个, 清除多余的
				 obj.value = obj.value.replace(".", "$#$").replace(/\./g, "").replace("$#$",
							".");
				 obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3'); // 只能输入两个小数
				}
				
			/*备用金修改··*/
			//function changetwo() {
		        //$("#goodsIframe").attr("src","cashUpdate.do");
				//document.getElementById("a").innerHTML = '<iframe src="cashupdate.jsp" width="100%" height="100%" frameborder=no  scrolling="no"></iframe>'
			//}
			
			//$(".left_check ul li").on("click", function() {
				////$(this).addClass("tab_select");
				//$(this).siblings().removeClass("tab_select");
				//$(".ifa").css("display", "block");
				//$("#a").css("display", "none");
			//});
			
			//$(".tab_two").on("click", function() {
				//$("#a").css("display", "block");
				//$(".ifa").css("display", "none");
			//});
			
			
			function cashshift(){
			  var boxnow =  $("#boxnow").val();
			  var submitstaff = $("#submitstaff").val();
			  var branchid = $("#branchid").val();
			  var shift = $("#shift").val();
			  var boxname = $("#boxname").val();
			  var shifterid = $("#shifterid").val();
			  var cashin = $("#cashin").val();
			  var cashout = $("#cashout").val();
			  var givecash = $("#givecash").val();
			  var lastshiftvalue = $("#lastshiftvalue").val();
			  var fixcash = $("#fixcash").val();
			  var cardin = $("#cardin").val();
			  var cards = $("#cards").val();
			  var payday = $("#payday").val();
			  var depositno = $("#depositno").val();
			  var invoiceno = $("#invoiceno").val();
			  var remark = $("#remark").val();
			  if(!submitstaff){
			     showMsg("确认人不能为空")
			  }else if(!/^\d+|\d+\.\d{1,2}$/gi.test(boxnow)){
			     showMsg("备用金应为数字或保留俩位小数，为空请输入零！");
			     $("#boxnow").val("0");
			  }else if (!/^\d+|\d+\.\d{1,2}$/gi.test(cashin)) {
			     showMsg("现金收入应为数字或保留俩位小数，为空请输入零！");
			     $("#cashin").val("0");
		      }else if (!/^\d+|\d+\.\d{1,2}$/gi.test(cashout)) {
			     showMsg("现金支出应为数字或保留俩位小数，为空请输入零！");
			     $("#cashout").val("0");
		      }else if (!/^\d+|\d+\.\d{1,2}$/gi.test(givecash)) {
			     showMsg("投缴金额应为数字或保留俩位小数，为空请输入零！");
			     $("#givecash").val("0");
		      }else if (!/^\d+|\d+\.\d{1,2}$/gi.test(lastshiftvalue)) {
			     showMsg("补上班次备用金额应为数字或保留俩位小数，为空请输入零！");
			     $("#lastshiftvalue").val("0");
		      }else if (!/^\d+|\d+\.\d{1,2}$/gi.test(fixcash)) {
			     showMsg("本次需补备用金额应为数字或保留俩位小数，为空请输入零！");
			     $("#fixcash").val("0");
		      }else if (!/^\d+|\d+\.\d{1,2}$/gi.test(cardin)) {
			     showMsg("银行卡收入应为数字或保留俩位小数，为空请输入零！");
			     $("#cardin").val("0");
		      }else if (!/^\d+$/.test(cards)) {
				showMsg("银行卡笔数必须为数字，为空请输入零！");
				$("#cards").val("0");
			  } else{
			  
			     if (depositno) {
					if (!/^\d+$/.test(depositno)) {
						showMsg("押金收据编号必须为数字");
						$("#depositno").val("");
						return;
					} else {
						 depositno = $("#depositno").val();
					}
			    } 
			    if (invoiceno) {
					if (!/^\d+$/.test(invoiceno)) {
						showMsg("发票编号必须为数字");
						$("#invoiceno").val("");
						return;
					} else {
						 invoiceno = $("#invoiceno").val();
					}
			    }
				   $.ajax( {
					url : base_path + "/pettyCash.do",
					type : "post",
					data : {
						'branchid':branchid,
						'shift':shift,
						'boxname':boxname,
						'shifterid':shifterid,
						'submitstaff':submitstaff,
						'cashin':cashin,
						'cashout':cashout,
						'givecash':givecash,
						'boxnow':boxnow,
						'lastshiftvalue':lastshiftvalue,
						'fixcash':fixcash,
						'cardin':cardin,
						'cards':cards,
						'payday':payday,
						'depositno':depositno,
						'invoiceno':invoiceno,
						'remark':remark	
					},
					success : function(json) {
						if (json.result == 1) {
							showMsg("交接成功!");
							window.setTimeout("window.location.href = '<%=request.getContextPath()%>/page/ipmspage/login/login.jsp';",
									1800);
						} else {
							showMsg("交接失败!");
							window.setTimeout("location.reload(true)", 1800);
							window.setTimeout("window.parent.JDialog.close();",
									1800);
						}
					},
					error : function(json) {
						showMsg("操作失败");
						window.setTimeout("location.reload(true)", 1800);
						window.setTimeout("window.parent.JDialog.close();", 1800);
					}
				  });
			  
			  } 

			}
		</script>
  </body>
</html>
