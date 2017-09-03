//确定按钮添加维修申请记录
function addRepairRecord(){
	var contrartid = $("#contrartid").val();
	var roomid = $("#roomid").val();
	var person = $("#applyname").val();
	var mobile = $("#applymobile").val();
	var equipment = $("#equipment_select").val();
	var emergent = $("#emegent_select").val();
	var repairtime = $("#repairTime").val();
	var remark = $("#repairRemark").val();
	alert(remark);
	$.ajax({
        url: path + "/addRepairApplicationRecord.do",
		 type: "post",
		 data : {"roomId":roomid,"person":person,"mobile":mobile,"equipment":equipment,"emergent":emergent,"repairtime":repairtime,"contrartid":contrartid,"remark":remark },
		 success: function(json) {
			 showMsg(json.message);
			 setTimeout(" window.parent.JDialog.close();",1500);//关闭子窗口	
			// setTimeout("$(window.parent.document.getElementById('repairIframe')).attr('src','repairData.do');",2000);
			 $(window.parent.document.getElementById("repairIframe")).attr("src","repairData.do");//刷新iframe页面
		 },
		 error: function(json) {
			 showMsg(json.message);
		 }
	});
}
//查询维修申请详情高级查询
function queryRepairDetail(){
	var startdate = $("#startdate").val();
	var enddate = $("#enddate").val();
	var roomid = $("#roomid").val();
	var equipment = $("#equipment").val();
	var checkstatus = $("#checkstatus").val();
	var mobile = $("#mobile").val();
	if(equipment == "0" ){
		equipment="";
	}
	if(checkstatus == "3"){
		checkstatus="";
	}
	$("#repairIframe").attr("src","queryRepairDetail.do?startdate="+startdate+"&enddate="+enddate+"&roomid="+roomid+"&equipment="+equipment+"&checkstatus="+checkstatus+"&mobile="+mobile);
}
function showMsg(msg, fn) {
	if (!fn) {
		TipInfo.Msg.alert(msg);
	} else {
		TipInfo.Msg.confirm(msg, fn);
	}
}
/*function CheckNull(e){
	if(e.val().length == 0){
		showMsg("请输入申请人和手机号");
		return true;
	}else {
		return false;
	}
}*/
//新增维修设备房间编号显示的和隐藏div
function selectRoomIdDetail(){
	$("#roomtype_class").css("display","block");
}
document.documentElement.onclick=function(e){
	e= window.event ? window.event : e;
	var e_tar=e.srcElement ? e.srcElement : e.target;
	if(e_tar.id=="roomid"){
		return;
	}else{
		/*$("#equipment_class").css("display", "none");*/
		$("#roomtype_class").css("display", "none");
	}
};
 //单击显示极简户型D1所有房间
 function showSimpleRoomD1(e){
	 $.ajax({
         url: path + "/showSimpleRoomD1.do",
		 type: "post",
		 data : {},
		 success: function(json) {
			 loadSimpleRoomD1(json);
			 $("#roomtype").val($(e).text());
		 },
		 error: function() {}
	}); 
 }
 ;function loadSimpleRoomD1(json){
	
		var tablere="";
		$.each(json,function(index){
			tablere +=
			"<span id='roomid'><input type='radio' id='roomid' name='roomid' value='1'/>"+json[index]["ROOMID"]+"</span>";
			});
		if(json.length == 0){
	 		$("#room_date").html("");
	 	}else{
	 		$("#room_date").html(tablere);			 	
	 	}
		}
//显示极简户型D2所有房间
 function showSimpleRoomD2(e){
	 $.ajax({
         url: path + "/showSimpleRoomD2.do",
		 type: "post",
		 data : {},
		 success: function(json) {
			 loadSimpleRoomD2(json);	
			 $("#roomtype").val($(e).text());
		 },
		 error: function() {}
	}); 
 }
 ;function loadSimpleRoomD2(json){
		var tablere="";
		$.each(json,function(index){
			tablere +=
			"<span id='roomid'><input type='radio' id='roomid' name='roomid' value='1'/>"+json[index]["ROOMID"]+"</span>";
			});
		if(json.length == 0){
	 		$("#room_date").html("");
	 	}else{
	 		$("#room_date").html(tablere);			 	
	 	}
		}
//显示雅居户型D3所有房间
 function showAgileRoom(e){
	 $.ajax({
         url: path + "/showAgileRoom.do",
		 type: "post",
		 data : {},
		 success: function(json) {
			 loadAgileRoom(json);
			 $("#roomtype").val($(e).text());
		 },
		 error: function() {}
	}); 
 }
 ;function loadAgileRoom(json){
		var tablere="";
		$.each(json,function(index){
			tablere +=
			"<span id='roomid'><input type='radio' id='roomid' name='roomid' value='1'/>"+json[index]["ROOMID"]+"</span>";
			});
		if(json.length == 0){
	 		$("#room_date").html("");
	 	}else{
	 		$("#room_date").html(tablere);
	 	}
		}
//显示雅居户型D4所有房间
 function showLuxuryRoom(e){
	 $.ajax({
         url: path + "/showLuxuryRoom.do",
		 type: "post",
		 data : {},
		 success: function(json) {
			 loadLuxuryRoom(json);
			 $("#roomtype").val($(e).text());
		 },
		 error: function() {}
	}); 
 }
 ;function loadLuxuryRoom(json){
		var tablere="";
		$.each(json,function(index){
			tablere +=
			"<span id='roomid'><input type='radio' id='roomid' name='roomid' value='1'/>"+json[index]["ROOMID"]+"</span>";
			});
		if(json.length == 0){
	 		$("#room_date").html("");
	 	}else{
	 		$("#room_date").html(tablere);			 	
	 	}
		}
//添加房间id确定按钮单击事件
 function chooseRoom(){
	 	var equipment = $("equipment_select").val();
	 	var emergent = $("emegent_select").val();
	 	var repairTime = $("repairTime").val();
 		var array = $("input:radio");
 		var type = $("#roomtype").val();
 		var roomtype = type.substring(type.indexOf("(")+1,type.indexOf(")"));//拿到房型D1和合同表数据匹配
 		var val = "";
 		$.each(array,function(index){
 	 		if(array[index]["checked"]){
 		 		val = val +$(array[index]["parentNode"]).text()+",";
 		 		}
 	 		});
 		newstr = val.substring(0,val.length-1);
 		if(newstr){
 			$('#roomid').val(newstr);
 			$("#roomtype_class").css("display", "none");
 		}else{
 			showMsg("请您选择至少一个房间");
 		}
 		$.ajax({//自动获取
 			url: path + "/getApplicationDate.do",
 			type: "post",
 			data : {"roomid" : newstr,"roomtype" : roomtype },
 			success: function(json) {
 				console.log(json.contrartid)
 				$("#contrartid").val(json.contrartid);
 				$("#applyname").val(json.membername);
 				$("#applymobile").val(json.mobile);
 			},
 			error: function() {}
 		});
 		}
 
 //双击事件
 function showDetail(e){
	var applyId = $($(e).find("td").get(0)).html();
	var repairLogstatus = $($(e).find("td").get(8)).html();
	var logId = $($(e).find("td").get(2)).html();
	window.parent.JDialog.open("维修申请记录详情",base_path+"/dblRepairDetail.do?applyId="+applyId+"&repairLogstatus="+repairLogstatus+"&logId="+logId,1000,300);
 }
 //更改维修申请表审核状态
 function changeStatus(e){
	var applyId = $($(e).parent().parent().find("td").get(0)).html();
	//console.log($($(e).parent().parent().find("td").get(0)).html());
	 $.ajax({
         url: path + "/changeStatus.do",
		 type: "post",
		 data : {"applyId" : applyId },
		 success: function(json) {
			 	//debugger;
			 	//alert(json.message);
				 window.parent.showMsg(json.message);
				 // $("#change").css("background-color","red");
				setTimeout("location.reload(true)",1000);	
		 },
		 error: function() {}
	});
 }
 //更改状态为取消,取消申请
 function cancelApplication(e){
	var applyId = $($(e).parent().parent().find("td").get(0)).html();
	 $.ajax({
         url: path + "/cancel.do",
		 type: "post",
		 data : {"applyId" : applyId },
		 success: function(json) {
			 	//debugger;
			 	//alert(json.message);
				 window.parent.showMsg(json.message);
				 // $("#change").css("background-color","red");
				setTimeout("location.reload(true)",1000);	
		 },
		 error: function() {}
	});
 }
 //处理已修復申請記錄,打开页面
 function doneApplication(e){
	 var applyId = $($(e).parent().parent().find("td").get(0)).html();
	// var status = $($(e).parent().parent().find("td").get(7)).html();
	 var applystatus = $($(e).parent().parent().find("td").get(7)).html();
	 //alert(status);
	 if(applystatus == "已审核" ){
		 $.ajax({
			 url: path + "/checkDone.do",
			 type: "post",
			 data : {"applyId" : applyId},
			 success: function(json) {
				 if(json.result == 1){
					 window.parent.showMsg(json.message);
					 
				 }else{
					 window.parent.JDialog.open("添加修复信息", base_path+"/doneApplication.do?applyId="+applyId, 700,430);
				 }
			 },
			 error: function() {}
		 });
	 }else{
		 window.parent.showMsg("该记录未审核或已取消!");
	 }
 }
 //添加维修日志修复确定按钮单击事件
 function addRepairLog(){
	 var problemtab = $("#problemtab_select").val();
	 var problemdesc = $("#problemdesc").val();
	 var repairPerson = $("#repairPerson").val();
	 var timearea = $("#timearea_select").val();
	 var status = $("#status_select").val();
	 var repairLogRemark = $("#repairLogRemark").val();
	 var applyId = $("#applyId").val();
	 var repairTime = $("#repairDate").val();
	 $.ajax({
		 url: path + "/addRepairLogRecord.do",
		 type: "post",
		 data : {"applyId" : applyId,
		 "problemtab" : problemtab,
		 "problemdesc" : problemdesc,
		 "repairPerson" : repairPerson,
		 "timearea" : timearea,
		 "status" : status,
		 "repairLogRemark" : repairLogRemark,
		 "repairTime" :repairTime
	 },
	 success: function(json) {
		// console.log(json.message)
		 showMsg(json.message);
		 setTimeout(" window.parent.JDialog.close();",1500);//关闭子窗口	
		 $(window.parent.document.getElementById("repairIframe")).attr("src","repairData.do");//刷新iframe页面
	 },
	 error: function() {}
	 }); 
 }
 
	 
 
 
 
 
 
 
 
 
 
 
 
 