$(document).ready(function() {
	laydate({
		elem:'#obirthday'
	})
	$("#obirthday").css('font-size', '0.9em');
})

function showMsg(msg, fn) {
	if (!fn) {
		TipInfo.Msg.alert(msg);
	} else {
		TipInfo.Msg.confirm(msg, fn);
	}
}
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
	json += "]";

	return json;
}

function IdentityCodeValid(code) {
	var city = {
		11 : "北京",
		12 : "天津",
		13 : "河北",
		14 : "山西",
		15 : "内蒙古",
		21 : "辽宁",
		22 : "吉林",
		23 : "黑龙江 ",
		31 : "上海",
		32 : "江苏",
		33 : "浙江",
		34 : "安徽",
		35 : "福建",
		36 : "江西",
		37 : "山东",
		41 : "河南",
		42 : "湖北 ",
		43 : "湖南",
		44 : "广东",
		45 : "广西",
		46 : "海南",
		50 : "重庆",
		51 : "四川",
		52 : "贵州",
		53 : "云南",
		54 : "西藏 ",
		61 : "陕西",
		62 : "甘肃",
		63 : "青海",
		64 : "宁夏",
		65 : "新疆",
		71 : "台湾",
		81 : "香港",
		82 : "澳门",
		91 : "国外 "
	};
	var tip = "";
	var pass = true;

	if (!code
			|| !/^\d{6}(18|19|20)?\d{2}(0[1-9]|1[12])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/i
					.test(code)) {
		tip = "身份证号格式错误";
		pass = false;
	}

	else if (!city[code.substr(0, 2)]) {
		tip = "身份证地址编码错误";
		pass = false;
	} else {
		// 18位身份证需要验证最后一位校验位
		if (code.length == 18) {
			code = code.split('');
			// ∑(ai×Wi)(mod 11)
			// 加权因子
			var factor = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 ];
			// 校验位
			var parity = [ 1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2 ];
			var sum = 0;
			var ai = 0;
			var wi = 0;
			for ( var i = 0; i < 17; i++) {
				ai = code[i];
				wi = factor[i];
				sum += ai * wi;
			}
			var last = parity[sum % 11];
			if (parity[sum % 11] != code[17]) {
				tip = "身份证校验位(最后一位)错误";
				pass = false;
			}
		}
	}
	if (!pass)
		showMsg(tip);
	return pass;
}

function ordernewmember() {
	var omembername = $("#omembername").val();
	var ologinname = $("#ologinname").val();
	var omobile = $("#omobile").val();
	var oidcard = $("#oidcard").val();
	var oemail = $("#oemail").val();
	var opostcode = $("#opostcode").val();
	if (!omembername) {
		showMsg("姓名不能为空")
	} else if (!ologinname) {
		showMsg("登录名不能为空")
	} else if (!omobile) {
		showMsg("手机号不能为空")
	} else {
		if ((oidcard == "")) {
			var oidcard = $("#oidcard").val();
		} else if (oidcard) {
			if (IdentityCodeValid(oidcard) == false) {
				showMsg("身份证输入不合法");
				$("#oidcard").val("");
				return;
			} else {
				var oidcard = $("#oidcard").val();
			}

		}
		if (omobile) {
			var phonejudge = /^1[34578]\d{9}$/
			if (phonejudge.test(omobile) == false) {
				showMsg("手机号输入不合法");
				$("#omobile").val("");
				return;
			} else {
				var omobile = $("#omobile").val();
			}
		}
		if (oemail == "") {
			var oemail = $("#oemail").val();
		} else if (oemail) {
			var reg = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
			ismail = reg.test(oemail);
			if (!ismail) {
				showMsg("邮箱输入不合法");
				$("#oemail").val("");
				return;
			} else {
				var oemail = $("#oemail").val();
			}
		}
		if (opostcode == "") {
			var opostcode = $("#opostcode").val();
		} else if (opostcode) {
			if (!/^\d+$/.test(opostcode)) {
				showMsg("邮编必须为数字，请重新输入");
				$("#opostcode").val("");
				return;
			}else if((opostcode.length)!=6){
				showMsg("邮编必须为6位数字，请重新输入");
				$("#opostcode").val("");
				return;
			}else {
				var opostcode = $("#opostcode").val();
			}
		}
		submitormadd();

	}

	/*
	 * var omembername = $("#omembername").val(); var ologinname =
	 * $("#ologinname").val(); var omobile = $("#omobile").val(); var oidcard =
	 * $("#oidcard").val(); var oemail = $("#oemail").val(); var a,b,c;
	 * if(oidcard==""||IdentityCodeValid(oidcard)== true){ a = "1"; return true;
	 * }else{ showMsg("身份证输入不合法"); return false; }
	 * if(oemail==""||/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/.test(oemail)==
	 * true){ b = "1"; return true; }else{ showMsg("邮箱输入不合法"); return false; }
	 * if(/^1[34578]\d{9}$/.test(omobile)== true){ c = "1"; return true; }else{
	 * showMsg("手机号输入不合法"); return false; } if(!omembername){ showMsg("姓名不能为空")
	 * }else if(!ologinname){ showMsg("登录名不能为空") }else if(!omobile){
	 * showMsg("手机号不能为空") }else if((a=="1")&&(b=="1")&&(c=="1")){
	 * submitormadd(); }
	 */

}

function submitormadd() {

	$.ajax( {
				url : base_path + "/formOmadd.do",
				type : "post",
				data : {
					ordermaddjson : parseToJson($("#omemberadd").serialize(),
							"oremark")
				},
				success : function(json) {
					if (json.result == 1) {
						showMsg("注册成功!");
						window.setTimeout("location.reload(true)", 1800);
						window.setTimeout("window.parent.JDialog.close();",
								1800);
					} else if(json.result == 0){
						showMsg("当前会员已存在!");
						window.setTimeout("location.reload(true)", 1800);
						window.setTimeout("window.parent.JDialog.close();",
								1800);
					} else {
						showMsg("注册失败!");
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