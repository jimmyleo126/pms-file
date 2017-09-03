$(function() {
	query();

});
function sup(n) {
	return (n < 10) ? '0' + n : n;
}
query = function() {
	$.ajax( {
		url : basePath + "/shiftTime.do",
		type : "post",
		dataType : "text",
		success : function(data) {
			$(":radio[name='radio'][value='" + data + "']").prop("checked","checked");
		},
		error : function() {
			showMsg("默认班次失败，请手动选择班次！");
		}
	});
}

$(document).ready(function() {
	$("#loginName").focus();
	/*
	 * $("form input").blur(function(){ $("#login_error").hide(); });
	 */

	$(document).keydown(function(e) {
		var currKey = 0, e = e || event;
		currKey = e.keyCode || e.which || e.charCode;
		if (currKey == 13) {
			$("form").each(function() {
				loginCheck();
			});
		}
	});
});

function showMsg(msg, fn) {
	if (!fn) {
		TipInfo.Msg.alert(msg);
	} else {
		TipInfo.Msg.confirm(msg, fn);
	}
}

function beforeSubmit() {
	$("#password").val($.md5($("#password").val()));
}

function loginReset() {
	$("input").val("");
}

function showErrorMsg(errorDiv, msg) {
	if (errorDiv) {
		var X = "", Y = "";
		X = errorDiv.offset().left - 400;
		Y = errorDiv.offset().top + errorDiv.height() - 220;
		errorDiv.select();
		$("#login_error").html(msg).show();
	}
}

function dvonClick(id) {
	var value = $("#" + id).val();
	var dvalue = $("#" + id).attr("defaultValue");
	if (value == dvalue) {
		$("#" + id).val("");
		$("#" + id).css("color", "#000");
	}
}

function dvonBlur(id) {
	var value = $("#" + id).val();
	var dvalue = $("#" + id).attr("defaultValue");
	if (value == dvalue) {
		$("#" + id).val(dvalue);
		$("#" + id).css("color", "#999");
	}
}

function regist() {
	window.location.href = basePath + "/page/system/register.jsp";
}

function loginCheck() {

	var userInfo = $("#loginName").val();
	var password = $("#password").val();
	password = $.md5(password);
	var shiftid = null;
	var cashbox = null;
	var obj_shift = document.getElementsByName("radio");
	var obj_cashbox = document.getElementsByName("radio1");

	for ( var i = 0; i < obj_shift.length; i++) {
		if (obj_shift[i].checked) {
			shiftid = obj_shift[i].value;
		}
	}
	for ( var i = 0; i < obj_cashbox.length; i++) {
		if (obj_cashbox[i].checked) {
			cashbox = obj_cashbox[i].value;
		}
	}
	$.ajax( {
		url : basePath + "/checkUser.do",
		dataType : "json",
		type : "post",
		data : {
			userInfo : userInfo,
			psw : password,
			shiftid : shiftid,
			cashbox : cashbox
		},
		success : function(json) {
			if (json.error) {
				var info = json.error.replace(/^[\s]*|[\s]*$/g, '');
				if (info == "BLACK_IPNAMELIST") {
					showErrorMsg($("#loginName"), "该IP已处于限制状态!");
				} else if (info == "AWAIT_APPLY") {
					showErrorMsg($("#loginName"), "该IP正等待开通!");
				} else if (info == "ACCOUNT_NOT_EXIST") {
					showErrorMsg($("#loginName"), "用户名不存在!");
				} else if (info == "PASSWORD_INCORRECT") {
					showErrorMsg($("#password"), "密码错误!");
					$("#psw").val("");
				} else if (info == "ACCOUNT_DISABLED") {
					showErrorMsg($("#loginName"), "用户已被禁用!");
				}
			} else if (json.sucess) {
				var option = "height="
						+ screen.availHeight
						+ ",width="
						+ screen.availWidth
						+ ",top=0,left=0,"
						+ "toolbar=no,menubar=no,scrollbars=no,location=no,status=no";
				window.open(basePath + "/loadMainFrame.do", "", option);
				// try{
				// window.opener = null;
				// window.open(" ", "_self");
				// window.close();
				// }catch(e){
				// }
				var random = Math.random();
				$("#loginform").attr("action",
						basePath + "/loadMainFrame.do?bsd=" + random);
				$("#loginform").submit();
			}
		}
	});
		
}
