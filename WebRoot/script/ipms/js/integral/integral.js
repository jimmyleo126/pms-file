$(function(){
	bindTabClick();
})

function bindTabClick() {
	var movement = [ "LoginLog", "shiftLog", "saleLog", "operateLog", "roomstatusLog"];
	$(".header_roomlist_li").on("click", function() {
		$("#logFrame").attr("src", movement[$(this).attr("index")] + ".do");
		change($(this).attr("index"));
		document.getElementById("selectAge")[$(this).attr("index")].selected=true;
	})
}

$(".header_roomlist_li").on("click",function(){
	$(this).addClass("active");
	$(this).siblings().removeClass("active");
});
$(".date").datepicker({ dateFormat: "yy/mm/dd " });
$("#starttime").css('font-size','0.9em');
$("#endtime").css('font-size','0.9em');
$(function(){
	var now = new Date();    
    var year = now.getFullYear();       //年   
    var month = now.getMonth() + 1;     //月   
    var day = now.getDate();
    var time = year+"/"+month+"/"+day;
    $("#starttime").val(time);
    $("#endtime").val(time);
});

function showMsg(msg, fn) {
	if (!fn) {
		TipInfo.Msg.alert(msg);
	} else {
		TipInfo.Msg.confirm(msg, fn);
	}
}
function submitFrom(){
	var start = $("#starttime").val();
	var end = $("#endtime").val();
	var starttime = new Date(start);
	var endtime = new Date(end);
	if(Date.parse(starttime) > Date.parse(endtime)){
		window.showMsg("开始时间不能大于结束时间");
		return false;
	}
	$("#condition").submit();
}

//高级查询    
$(".highsearch").on("click",function(){
	$(".high_header").css("display","block");
})
$(".imooc").on("click",function(){
	$(".high_header").fadeOut(500);
});
var timer = null;
function aa() {
	clearTimeout(timer);
	if (event.detail == 2)
		return;
	timer = setTimeout(function() {
		console.log('单击');
	}, 300);
}
function bb() {
	clearTimeout(timer);
	window.location.href="order_details.jsp";
}
function change(select){
	$("#loginfo").empty();
	//var select = $("#selectAge").val();
	var html = "";
	if(select == 0){
		$("#condition").attr("action","LoginLog.do");
		html += "<li><label class='label_name'>登录人</label><input name='logname' id='logname' type='text' value='' class='text_search'></li>";
		html += "<li><label class='label_name' class='text_search'>登录端</label>" +
				"<select name='source' id='source' class='text_search'>" +
				"<option value=''>--请选择--</option>" +	
				"<option value='1'>app</option>" +
				"<option value='2'>网站</option>" +
			    "<option value='3'>分店</option>" +
			    "<option value='4'>wap</option>" +
			    "</select></li>";
		html += "<li><label class='label_name' class='text_search'>登录状态</label>" +
				"<select name='status' id='status' class='text_search'>" +
				"<option value=''>--请选择--</option>" +	
				"<option value='1'>成功</option>" +
				"<option value='0'>失败</option>" +
			    "</select></li>";
	}else if(select == 1){
		$("#condition").attr("action","shiftLog.do");
		html += "<li><label class='label_name'>上个班次</label><input name='lastshift' id='lastshift' type='text' value='' class='text_search'></li>";
		html += "<li><label class='label_name'>当前班次</label><input name='currshift' id='currshift' type='text' value='' class='text_search'></li>";
		html += "<li><label class='label_name'>交接人</label><input name='lastuser' id='lastuser' type='text' value='' class='text_search'></li>";
		html += "<li><label class='label_name'>当班人</label><input name='curruser' id='curruser' type='text' value='' class='text_search'></li>";
	}else if(select == 2){
		$("#condition").attr("action","saleLog.do");
		html += "<li><label class='label_name'>挂账类型</label>" +
				"<select name='debts' id='debts' class='text_search'>" +
				"<option value=''>--请选择--</option>" +	
				"<option value='1'>工作帐</option>" +
				"<option value='2'>房账</option>" +
			    "</select></li>";
		html += "<li><label class='label_name'>商品名称</label><input name='goodsname' id='goodsname' type='text' value='' class='text_search'></li>";
		html += "<li><label class='label_name'>付款</label>" +
				"<select name='paytype' id='paytype' class='text_search'>" +
				"<option value=''>--请选择--</option>" +	
				"<option value='1'>挂房账</option>" +
				"<option value='2'>现金</option>" +
			    "<option value='3'>银行卡</option>" +
			    "<option value='4'>微信</option>" +
			    "<option value='5'>支付宝</option>" +
			    "</select></li>";
	}else if(select == 3){
		$("#condition").attr("action","operateLog.do");
		html += "<li><label class='label_name'>操作类型</label>" +
				"<select name='opertype' id='opertype' class='text_search'>" +
				"<option value=''>--请选择--</option>" +	
				"<option value='1'>保存</option>" +
				"<option value='2'>删除</option>" +
			    "<option value='3'>修改</option>" +
			    "<option value='4'>充值</option>" +
				"<option value='5'>入住操作</option>" +
			    "<option value='7'>保洁处理</option>" +
			    "</select></li>";
				"</li>";
		html += "<li><label class='label_name'>操作人</label><input name='recorduser' id='recorduser' type='text' value='' class='text_search'></li>";
	}else if(select == 4){
		$("#condition").attr("action","roomstatusLog.do");
		html += "<li><label class='label_name'>操作人</label><input name='recorduser' id='recorduser' type='text' value='' class='text_search'></li>";
	}
	$("#loginfo").html(html);
}
function resetform(){
	$(':input','#condition')  
	 .not(':button, :submit, :reset, :hidden')  
	 .val('')  
	 .removeAttr('checked')  
	 .removeAttr('selected');  
}
