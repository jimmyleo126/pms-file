var mobReg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/; //手机校验
var idcardReg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/; //身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X 
var emailReg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;


function isWorkbillCheckout(workbillid){
	flag = false;
	msg = "";
	if( !workbillid || workbillid == ""){
		msg = "无此房单!"
		showMsg(msg);
		return false;
	}
	$.ajax({
        url: path + "/isWorkbillCheckout.do",
		 type: "post",
		 data : {workbillid : workbillid},
		 async:false,
		 success: function(json) {
			 if(json.result == 1){
				 showMsg(json.message);
				flag = true;
			 }
		 },
		 error: function(json) {}
	});
	return flag;
}

function isCheckout(checkid){
	flag = false;
	msg = "";
	if( !checkid || checkid == ""){
		msg = "无此房单!"
		showMsg(msg);
		return false;
	}
	$.ajax({
        url: path + "/isCheckout.do",
		 type: "post",
		 data : {checkid : checkid},
		 async:false,
		 success: function(json) {
			 if(json.result == 1){
				 showMsg(json.message);
				flag = true;
			 }
		 },
		 error: function(json) {}
	});
	return flag;
}

function isMobile(mobile){
	if(mobReg.test(mobile)){
		return true;
	}else{
		var msg = "不是有效的手机号!";
		showMsg(msg);
		return false;
	}
}

function isIdcard(idcard){
	if(idcardReg.test(idcard)){
		return true;
	}else{
		if(idcard){
			var msg = "身份证不合法!";
			showMsg(msg);
		}
		return false;
	}
}

function isEmail(email){
	if(emailReg.test(email)){
		return true;
	}else{
		if(email){
			var msg = "邮箱不合法!";
			showMsg(msg);		
		}
		return false;
	}
}

function dealDate(myT){
	if(myT){
		var result;
		var year = myT.year + 1900;
		var month = myT.month + 1;
		var date = myT.date;
		var hour = myT.hours;
		var minute = myT.minutes;
		if (month >= 1 && month <= 9) {
			month = "0" + month;
		}
		if (date >= 1 && date <= 9) {
			date = "0" + date;
		}
		if (hour >= 0 && hour <= 9) {
			hour = "0" + hour;
		}
		if (minute >= 0 && minute <= 9) {
			minute = "0" + minute;
		}
		result = year + "/" +month + "/" + date + " " + hour + ":" + minute;
		return(result)
	}else{
		return '';
	}
}

function dealLocalDate(myT){
	var result;
	var year = myT.getFullYear();
	var month = myT.getMonth() + 1;
	var strDate = myT.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = year + "/" + month + "/" + strDate
    + " " + myT.getHours() + ":" + myT.getMinutes();
    return currentdate;
}



function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
            + " " + date.getHours() + seperator2 + date.getMinutes()
            + seperator2 + date.getSeconds();
    return currentdate;
}

function converttosomething(oldsome, reason, newsome){
	return oldsome == reason ? newsome : oldsome;
}

function showMsg(msg, fn) {
	if (!fn) {
		TipInfo.Msg.alert(msg);
	} else {
		TipInfo.Msg.confirm(msg, fn);
	}
}

function checkedbytruns(e){
	var checkbox = $(e).find(':checkbox');
    checkbox.prop('checked', !checkbox.prop('checked'));
}

function isNull(e, title){
	if(e.val().length == 0){
		var msg = "不能为空!";
		if(title) msg = title + msg;
		showMsg(msg);
		e.focus();
		return true;
	}else {
		return false;
	}
}

