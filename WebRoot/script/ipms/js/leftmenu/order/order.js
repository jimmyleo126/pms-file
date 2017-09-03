//var allpricr="";

$(document).ready(
		function() {
			/* 初始化下拉表单 */
			$(".mySelect").select( {
				width : "150px"
			});
			var themetype = $("#themetype").val();
			$("#theme").val(themetype);
			$("#paymethod").children('input[type=checkbox]').click(
					function() {
						if ($(this).is(':checked')) {
							$(this).attr('checked', true).siblings().attr(
									'checked', false);
							if ($(this).attr('id') == "yhk") {
								$("#order_pzh").show();
								$("#card").attr("disabled", false);
								$("#cash").val("");
								$("#cash").attr("disabled", true);
							} else if ($(this).attr('id') == "xj") {
								$("#card").val("");
								$("#order_pzh").val("");
								$("#card").attr("disabled", true);
								$("#cash").attr("disabled", false);
								$("#order_pzh").hide();
							}
						} else {
							$(this).attr('checked', false).siblings().attr(
									'checked', false);
							$("#cash").val("");
							$("#card").val("");
							$("#order_pzh").val("");
							$("#order_pzh").hide();
							$("#card").attr("disabled", true);
							$("#cash").attr("disabled", true);
						}
					})

			$("#exscore").children('input[type=checkbox]').click(function() {
				if ($(this).is(':checked')) {
					$("#excash").show();
					$("#scorecash").val("");
					$("#excash").hide();
					$("#score").attr("disabled", false);
				} else {
					$("#score").val("");
					$("#scorecash").val("");
					$("#excash").hide();
					$("#score").attr("disabled", true);
				}
			})

			$("#score").bind(
					"click",
					function() {
						var MemberId = $("#memberId").val();
						var sjscore = $("#score").val();
						if (MemberId) {
							JDialog.open("", base_path
									+ "/scoreExchange.do?MemberId=" + MemberId
									+ "&sjscore=" + sjscore, 600, 200);
						} else {
							showMsg("预订人（会员）信息不能为空！");
						}
					})

			// $(".date").datetimepicker({ datetimeFormat: "yy/mm/dd" });
			// $("#ui-datepicker-div").css('font-size','0.9em');
			// $(".date").datepicker({ dateFormat: "yy/mm/dd " });
			var date = new Date();
			date.setMonth(date.getMonth() + 3);
			/*$(".date").datepicker( {
				minDate : 0,
				maxDate : date
			});
			$(".ldate").datepicker( {
				minDate : 0
			});*/
			$("#arrivedate").css('font-size', '0.9em');
			$("#leavedate").css('font-size', '0.9em');

			$("#theme").change(function() {
				if ($("#theme").val() == 2) {
					$("#roomtype").attr("disabled", true);
					var label_a = document.getElementById("arrive");
					label_a.innerText = "开始日期";
					$("#arrive").html("开始日期");
					var label_r = document.getElementById("leave");
					label_r.innerText = "结束日期";
					$("#leave").html("结束日期");
				} else if ($("#theme").val() == 1) {
					$("#roomtype").removeAttr("disabled");
					var label = document.getElementById("arrive");
					label.innerText = "抵店日期";
					$("#arrive").html("抵店日期");
					var label_r = document.getElementById("leave");
					label_r.innerText = "离店日期";
					$("#leave").html("离店日期");
				}
			})
		});

function DateAdd(interval, number, date) {
    switch (interval) {
    case "y ": {
        date.setFullYear(date.getFullYear() + number);
        return date;
        break;
    }
    case "q ": {
        date.setMonth(date.getMonth() + number * 3);
        return date;
        break;
    }
    case "m ": {
        date.setMonth(date.getMonth() + number);
        return date;
        break;
    }
    case "w ": {
        date.setDate(date.getDate() + number * 7);
        return date;
        break;
    }
    case "d ": {
        date.setDate(date.getDate() + number);
        return date;
        break;
    }
    case "h ": {
        date.setHours(date.getHours() + number);
        return date;
        break;
    }
    case "m ": {
        date.setMinutes(date.getMinutes() + number);
        return date;
        break;
    }
    case "s ": {
        date.setSeconds(date.getSeconds() + number);
        return date;
        break;
    }
    default: {
        date.setDate(d.getDate() + number);
        return date;
        break;
    }
    }
}

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
var now = new Date();
var newDate = DateAdd("m ", 3, now);
var time1 = newDate.Format("yyyy/MM/dd");
//var now = new Date();
//加五天.
//var newDate = DateAdd("d ", 5, now);
//alert(newDate.toLocaleDateString())
//加两个月.
//newDate = DateAdd("m ", 2, now);
//alert(newDate.toLocaleDateString())
//加一年
//newDate = DateAdd("y ", 1, now);
//alert(newDate.toLocaleDateString())
	laydate({
		elem: '#arrivedate',
		min:laydate.now(0) ,
		max:time1
	})
	laydate({
		elem: '#leavedate',
		min:laydate.now(0) ,
	})
	
	$(document).ready(function(){
		$("#memberselect").focus();
	})

function showMsg(msg, fn) {
	if (!fn) {
		TipInfo.Msg.alert(msg);
	} else {
		TipInfo.Msg.confirm(msg, fn);
	}
}

function addmember() {
	JDialog.open("会员注册", base_path + "/ordermemberAdd.do", 850, 410);
}

function subNum() {
	var c = parseInt($("#acount").val());
	// var orderarrivedate = $("#arrivedate").val();
	// var orderleavedate = $("#leavedate").val();
	if (c >= 2) {

		document.getElementById("acount").value = c - 1;
		var nown = document.getElementById("acount").value;
		// var acprice = $("#price").val()

		// var aDate, oDate1, oDate2, iDays;
		// aDate = orderarrivedate.split("/");
		// oDate1 = new Date(aDate[1] + '/' + aDate[2] + '/' + aDate[0]);
		// //转换为12/13/2008格式
		// aDate = orderleavedate.split("/");
		// oDate2 = new Date(aDate[1] + '/' + aDate[2] + '/' + aDate[0]);
		// var i=(oDate2 - oDate1) / 1000 / 60 / 60 /24;

		// iDays = i; //把相差的毫秒数转换为天数
		// allpricr = parseInt(nown*acprice*i);
		// $("#allpricr").val(allpricr);

	} else {
		showMsg("数量不可为小于1的数字！");
	}
}

function addNum() {
	// var orderarrivedate = $("#arrivedate").val();
	// var orderleavedate = $("#leavedate").val();
	// if((!orderarrivedate)&&(!orderleavedate)){
	// showMsg("日期不能为空")
	// }else{
	var c = parseInt($("#acount").val());
	document.getElementById("acount").value = c + 1;
	// var nown= document.getElementById("acount").value;
	// var acprice = $("#price").val();

	// var aDate, oDate1, oDate2, iDays;
	// /aDate = orderarrivedate.split("/");
	// oDate1 = new Date(aDate[1] + '/' + aDate[2] + '/' + aDate[0]);
	// //转换为12/13/2008格式
	// aDate = orderleavedate.split("/");
	// oDate2 = new Date(aDate[1] + '/' + aDate[2] + '/' + aDate[0]);
	// var i=(oDate2 - oDate1) / 1000 / 60 / 60 /24;
	// if(i>0){}
	// /else{i+=1;}
	// iDays = i; //把相差的毫秒数转换为天数
	// allpricr = parseInt(nown*acprice*i);
	// $("#allpricr").val(allpricr);
	// }
}

$(".info_write ul li").on("click", function() {
	$(this).addClass("active");
	$(this).siblings().removeClass("active");
});
$(".detail_four .look_order")
		.on(
				"click",
				function() {
					window.location.href = "<%=request.getContextPath()%>/page/ipmspage/order/order_check.jsp";

				});

function selectmember() {
	$("#rpid").val("");
	$("#price").val("");
	$("#roomid").val("");
	$("#roomtype ").val("");
	var Mnumber = $("#memberselect").val();
	if (Mnumber) {
		$.ajax( {
			url : base_path + "/memberSelect.do",
			type : "post",
			dataType : "json",
			data : {
				'Mnumber' : Mnumber
			},
			success : function(json) {
				if (1 == json.result) {
					if (json.message) {
						showMsg(json.message);
						window.setTimeout("location.reload(true)", 6000);
					} else {
						JDialog.open("", base_path + "/judgeUser.do?Mnumber="
								+ Mnumber, 300, 150);
					}

				} else {
					showMsg("没有该会员的信息，请输入正确的会员卡号或手机号！");
					window.setTimeout("location.reload(true)", 3000);
				}
			},
			error : function() {
				showMsg("操作失败，请重新操作！");
				window.setTimeout("location.reload(true)", 1800);
			}
		});
	} else {
		showMsg("请先输入会员手机号进行查询！");
		window.setTimeout("location.reload(true)", 1800);
	}
}

$("#roomid").bind(
		"click",
		function() {
			var theme = $("#theme").val();
			var roomtype = $("#roomtype").val();
			var roomacount = $("#acount").val();
			if (roomtype) {
				JDialog.open("", base_path + "/roomSelect.do?roomtype="
						+ roomtype + "&theme=" + theme + "&roomacount="
						+ roomacount, 800, 410);
			} else {
				showMsg("请先选择房间类型！");
			}
		})

function num(obj) {
	obj.value = obj.value.replace(/[^\d.]/g, ""); // 清除"数字"和"."以外的字符
	obj.value = obj.value.replace(/^\./g, ""); // 验证第一个字符是数字
	obj.value = obj.value.replace(/\.{2,}/g, "."); // 只保留第一个, 清除多余的
	obj.value = obj.value.replace(".", "$#$").replace(/\./g, "").replace("$#$",
			".");
	obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3'); // 只能输入两个小数
}

function rtChange() {
	// var orderarrivedate = $("#arrivedate").val();
	// var orderleavedate = $("#leavedate").val();
	// var c = parseInt($("#acount").val());
	// if((!orderarrivedate)&&(!orderleavedate)){
	// showMsg("日期不能为空")
	// }
	// else{
	// var aDate, oDate1, oDate2, iDays;
	// aDate = orderarrivedate.split("/");
	// oDate1 = new Date(aDate[1] + '/' + aDate[2] + '/' + aDate[0]);
	// //转换为12/13/2008格式
	// aDate = orderleavedate.split("/");
	// oDate2 = new Date(aDate[1] + '/' + aDate[2] + '/' + aDate[0]);
	// var i=(oDate2 - oDate1) / 1000 / 60 / 60 /24;
	// /if(i>0){}
	// else{i+=1;}
	// iDays = i; //把相差的毫秒数转换为天
	if(!$("#memberselect").val()){
		showMsg("请先查询出会员信息！");
		$("#roomtype").val("");
	}else{
		$("#roomid").val("");
		var t = $("#roomtype").val();
		var s = $("#roomprice").val();
		var first = s.replace(/=/g, ":'");
		var second = first.replace(/,/g, "',");
		var third = second.replace(/}/g, "'}");
		var fouth = third.replace(/}',/g, "},");
		var array = eval(fouth);
		for ( var i = 0, l = array.length; i < l; i++) {
			var rpName = array[i].ROOM_TYPE
			if (t == rpName) {
				var rpId = array[i].RP_NAME;
				var rpIdid = array[i].RP_ID;
				var price = array[i].ROOM_PRICE;
				$("#rpid").val(rpId);
				$("#rpidid").val(rpIdid);
				$("#price").val(price);
				// $("#allpricr").val(price*iDays*c);
			} else {
			}
			// }
		}
	}
}

var InputCount = 0;

$("#addnewtab")
		.click(function() {

			InputCount++;
			var tabCon = "tabCon_" + InputCount;
			var rzr = "rzr_" + InputCount;
			var span = "span_" + InputCount;
			var tabtitle = "tabtabtitle_" + InputCount;
			var user = "user_" + InputCount;
			var mobile = "mobile_" + InputCount;

			// $("#tabtitle").append('<input type="text" name="saletype' +
			// InputCount + '" class="auditreset inpute"/>');
				// $("#tabtitle").append(' <li class="tab_select">入住人</li>');
				$("#tabtitlenew").append(
						' <li class="tabli" id=' + tabtitle
								+ ' onclick="changeTab(' + InputCount
								+ ')"><span id=' + rzr + '>入住人' + InputCount
								+ '</span></li><span id=' + span
								+ ' style="margin-left: -13px;">x</span>');
				$("#tabCon")
						.append(
								'<div id='
										+ tabCon
										+ ' style="display:none" class="hiddenuser"> <ul id="tabcontent" class="clearfix"> <li><label class="label_name">姓名'
										+ InputCount
										+ '</label><input id= '
										+ user
										+ ' name="usercheckin" type="text" value="" class="check"></li> <li><label class="label_name">手机号'
										+ InputCount
										+ '</label> <input id= '
										+ mobile
										+ ' name="usermobile" type="text" value="" class="check"></li></ul></div>');

			});

function changeTab(tabCon_num) {
	var usercount = InputCount
	for (i = 0; i <= usercount; i++) {
		// $("#tabCon_"+i).hidden();
		document.getElementById("tabCon_" + i).style.display = "none";
		// document.getElementById("tabCon_"+i).style.display='none'; //将所有的层都隐藏
	}
	$("#tabCon_" + tabCon_num).show();

	// $("#span_"+tabCon_num).show();

	// $("#span_"+tabCon_num).toggle(function(){
	// $(this).css("backgroundColor","#f1c2d5");
	// var roomid = $(this).find("span").html();
	// rooms.push(roomid);
	// },function(){
	// $(this).css("backgroundColor","#d6f6fd");
	// var roomid = $(this).find("span").html();
	// rooms.remove(roomid);
	// });

	$("#span_" + tabCon_num).click(function(e) {
		$("#user_" + tabCon_num).val("");
		$("#mobile_" + tabCon_num).val("");
		$("#tabCon_" + tabCon_num).hide();
		document.getElementById("span_" + tabCon_num).style.display = "none";
		$("#tabtabtitle_" + tabCon_num).hide();

	})
	// document.getElementById("tabCon_"+tabCon_num).attr("style","display:block;");//显示当前层
}

// function removediv(){

// alert($(this).parent().attr(id))
// $(this).parent().remove();
// $('.tabli').click(function(e){
// // alert($(this).attr('id'));
// });
// }

// $(".tabli").click(function(e){
// var id = $(e.target).attr('id');
// alert(id);
// })

// var list=eval("("+s+")");
// for(int i=0;i<list.length;i++){
// name=list[i].RP_NAME;
// //alert(name)
// }

// var mycars=new Array(s )
// var json = {};
// for (var i = 0; i < mycars.length; i++) {
// json[i]=mycars[i];
// alert(json[i])
// }
// var r = eval(s);
// /var r = eval(s.replace(/=/g,":"));

// document.write{'<room_type>'+r[0].room_type+'</room_type>'}
// alert(r[0].room_type)
// alert(r[1].room_type)
// for(var r in s){
// for(var i=0; i<r.length; i++ ){
// alert(r);
// alert(r[i])
// var v = s[i];
// if(t.equal(s[v].get("room_type"))){
// //alert("124")
// }

// for(var i=0; i<t.length; i++ ){
// if(w[i].value == t){
// alert(w[i].room_type)
// }
// }

function parseToJson(msg, end) {
	var json = "[{";
	var msg2 = msg.split("&"); // 先以“&”符号进行分割，得到一个key=value形式的数组
	var t = false;
	for ( var i = 0; i < msg2.length; i++) {
		var msg3 = msg2[i].split("="); // 再以“=”进行分割，得到key，value形式的数组
		for ( var j = 0; j < msg3.length; j++) {
			json += "\"" + msg3[j] + "\"";
			if (j + 1 != msg3.length) {
				json += ":";
			}
			if (t) {
				json += "}";
				if (i + 1 != msg2.length) { // 表示是否到了当前行的最后一列
					json += ",{";
				}
				t = false;
			}
			if (msg3[j] == end) { // 这里的“canshu5”是你的表格的最后一列的input标签的name值，表示是否到了当前行的最后一个input
				t = true;
			}
		}
		if (!msg2[i].match(end)) { // 同上
			json += ";";
		}

	}
	json += "}]";

	return json;
}

// var users = new Array();
// var user0 = $("#usercheckin").val();
// users.push(user0);
// for(i=1;i<=InputCount;i++){
// var orname = $("#user_"+i).val();
// var ormobile = $("#mobile_"+i).val();
// if((!orname)&&(orname=="")){
// }else{
// users.push(orname);
// }
// }
// var len = users.length;

function orderbegin() {
	var ordertheme = $("#theme").val();
	var orderroomtype = $("#roomtype").val();
	var orderarrivedate = $("#arrivedate").val();
	var orderleavedate = $("#leavedate").val();
	var orderacount = $("#acount").val();
	var orderrpid = $("#rpidid").val();
	var orderprice = $("#price").val();
	var orderuser = $("#userorder").val();
	var ordermobile = $("#ordemobile").val();
	var orderlimited = $("#limited").val();
	var receptionremark = $("#receptionremark").val();
	var roomremark = $("#roomremark").val();
	var t = $("#roomtype").val();
	var s = (document.getElementsByName(t)[0].id)
	var maxusers = parseInt(s * orderacount);
	var ordercash = $("#cash").val();
	var ordercard = $("#card").val();
	var ordervoucher = $("#voucher").val();
	var orderscore = $("#score").val();
	var ordergurantee = "1";
	if ((ordercash != null && ordercash != "")
			|| (ordercard != null && ordercard != "")) {
		ordergurantee = "2";
	}
	if ((!orderarrivedate) && (!orderleavedate)) {
		showMsg("日期不能为空！")
	} else if ((new Date(orderarrivedate.replace(/\-/g, "\/"))) > (new Date(
			orderleavedate.replace(/\-/g, "\/")))) {
		showMsg("起始时间不得晚于结束时间！")
	} else if ((!orderroomtype) && (orderroomtype == "")) {
		showMsg("请先选择房间类型！")
	} else if (!orderuser) {
		showMsg("预订人 不能为空！")
	} else if (!ordermobile) {
		showMsg("预订人手机号不能为空！")
	} else if (ordercash) {
		if (!/^\d+|\d+\.\d{1,2}$/gi.test(ordercash)) {
			showMsg("预付现金应为数字或保留俩位小数，请重新输入！");
			$("#cash").val("");
		} else {
			orderdata();
		}
	} else if (ordercard) {
		if (!/^\d+|\d+\.\d{1,2}$/gi.test(ordercard)) {
			showMsg("预付现金应为数字或保留俩位小数，请重新输入！");
			$("#card").val("");
		} else if (!ordervoucher) {
			showMsg("银行卡预付，必须输入凭证号！")
		} else if (!/^\d+$/.test(ordervoucher)) {
			showMsg("凭证号必须为数字，请重新输入！");
			$("#voucher").val("");
		} else if ((ordervoucher.length) != 6) {
			showMsg("凭证号位数应为6位，请重新输入！");
			$("#voucher").val("");
		} else {
			orderdata();
		}
	}/*
		 * else if(ordervoucher){ if((ordervoucher.length)!=6){
		 * showMsg("凭证号位数应为6位，请重新输入！"); $("#voucher").val(""); }else{
		 * orderdata(); } }
		 */else {
		orderdata();

	}

}

function orderdata() {
	var ordertheme = $("#theme").val();
	var orderroomtype = $("#roomtype").val();
	var orderarrivedate = $("#arrivedate").val();
	var orderleavedate = $("#leavedate").val();
	var orderacount = $("#acount").val();
	var orderrpid = $("#rpidid").val();
	var orderprice = $("#price").val();
	var orderuser = $("#userorder").val();
	var ordermobile = $("#ordemobile").val();
	var orderlimited = $("#limited").val();
	var receptionremark = $("#receptionremark").val();
	var roomremark = $("#roomremark").val();
	var t = $("#roomtype").val();
	var s = (document.getElementsByName(t)[0].id)
	var maxusers = parseInt(s * orderacount);
	var ordercash = $("#cash").val();
	var ordercard = $("#card").val();
	var ordervoucher = $("#voucher").val();
	var orderscore = $("#score").val();
	var ordergurantee = "1";
	if ((ordercash != null && ordercash != "")
			|| (ordercard != null && ordercard != "")) {
		ordergurantee = "2";
	}
	$.ajax( {
		url : base_path + "/orderRoom.do",
		type : "post",
		dataType : "json",
		data : {
			'ordertheme' : ordertheme,
			'orderroomtype' : orderroomtype,
			'orderarrivedate' : orderarrivedate,
			'orderleavedate' : orderleavedate,
			'orderacount' : orderacount,
			'orderrpid' : orderrpid,
			'orderprice' : orderprice,
			'orderuser' : orderuser,
			'ordermobile' : ordermobile,
			'orderlimited' : orderlimited,
			'receptionremark' : receptionremark,
			'roomremark' : roomremark,
			'ordercash' : ordercash,
			'ordercard' : ordercard,
			'ordervoucher' : ordervoucher,
			'orderscore' : orderscore,
			'ordergurantee' : ordergurantee
		// 'users' :susers,
		// 's':s
		},
		success : function(json) {
			if (1 == json.result) {
				if (json.message) {
					showMsg(json.message);
					window.setTimeout("window.location.reload(true)", 2500);
				} else {
					showMsg("预订成功!");
					window.setTimeout("window.parent.location.reload(true)",
							2500);
				}

			} else {
				showMsg("预订失败！");
				window.setTimeout("window.parent.location.reload(true)", 1800);
			}
		},
		error : function() {
			showMsg("操作失败，请重新操作！");
			window.setTimeout("window.parent.location.reload(true)", 1800);
		}
	});

}
