function loadPageByBusinessIdNew(){
	
	$(".date").datetimepicker({ dateFormat: "yy/mm/dd" })
	$("#datetimepicker_div").css('font-size','0.9em');

	$("#usingPerson").bind("click",function(){
	 	JDialog.open("适用对象", base_path + "/selectMultiUsingPerson.do",450,350);
	});
	
	$("#usingType").bind("click",function(){
	 	JDialog.open("适用类型 ", base_path + "/selectMultiUsingType.do",450,350);
	});
	
	
	
	if($("#businessId").val() == "1"){
		var innerDetail = "<table id='campaign1' name='campaign1'>"+
		"<tr><td class='state' colspan='2' style='text-align: right;width: 12%;'><label>推荐会员</label></td>"+
		"<td colspan='2'  style='width: 20%;'><input id='goodsService' name='goodsService' value='' class='text_edit'/></td>"+
		"<td class='state' colspan='2'><label>个</label></td></tr>"+
		"<tr><td class='state' colspan='2' style='text-align: right;width: 12%;'><label>送</label></td>"+
		"<td colspan='2'><input id='couponPrice' name='couponPrice' value=''  class='text_edit' type='text' readonly/></td>"+
		"<td class='state' colspan='2'><label>元优惠券</label></td></tr>"+
		"<tr><td class='state' colspan='2' style='text-align: right;width: 12%;'><label>组合名称</label></td>"+
		"<td colspan='2'><input id='couponGroupId' name='couponGroupId' value='' type='hidden' class='text_edit'/><input id='couponName' name='couponName' value='' class='text_edit'/></td>"+
		"<td class='state' colspan='2'><input id='showRule' name='showRule' type='text' style='width:300px' readonly/></td></tr>" +
		"</table>";
		$("#campaignDetail").html(innerDetail);
		
		$("#couponPrice").bind("click",function(){
		 	JDialog.open("优惠券组合", base_path + "/selectcouponGroup.do",450,350);
		});
	}else if($("#businessId").val() == "2"){
		var innerDetail = "<table id='campaign1' name='campaign1'><tr><td class='state' colspan='2'><label>开卡"+
		"</label></td>"+
		"<td class='state'><label>即送</label></td><td colspan='2'><input id='couponPrice' name='couponPrice' value='' /><input id='couponGroupId' name='couponGroupId' type='hidden' value='' />"+
		"</td><td class='state'><label>元优惠券</label></td></tr><tr><td class='state'>"+
		"<label>面额</label></td><td class='state'><label>10元</label></td><td class='state'>"+
		"20元</td><td class='state'><label>30元</label></td><td class='state'><label>"+
		"50元</label></td><td class='state'><label>100元</label></td></tr><tr><td class='state'>"+
		"<label>张数</label></td><td><label><input id='tenCoupon' name='tenCoupon' value='' readonly/>"+
		"</label></td><td><input id='twentyCoupon' name='twentyCoupon' value='' readonly/>"+
		"</td><td><label><input id='thirtyCoupon' name='thirtyCoupon' value='' readonly/>"+
		"</label></td><td><label><input id='fiftyCoupon' name='fiftyCoupon' value='' readonly/>"+
		"</label></td><td><label><input id='hundredCoupon' name='hundredCoupon' value='' readonly/>"+
		"</label></td</tr></table>";
		$("#campaignDetail").html(innerDetail);
		$("#couponPrice").bind("click",function(){
		 	JDialog.open("优惠券组合", base_path + "/selectcouponGroupAnother.do",450,350);
		});
	}else if($("#businessId").val() == "3"){
		var innerDetail = "<table id='campaign1' name='campaign1'><tr><td class='state'><label>充值"+
		"</label></td><td><input id='operMoney' name='operMoney' value='' /></td>"+
		"<td class='state'><label>元送</label></td><td colspan='2'><input id='couponPrice' name='couponPrice' value='' /><input id='couponGroupId' name='couponGroupId' type='hidden' value='' />"+
		"</td><td class='state'><label>元优惠券</label></td></tr><tr><td class='state'>"+
		"<label>面额</label></td><td class='state'><label>10元</label></td><td class='state'>"+
		"20元</td><td class='state'><label>30元</label></td><td class='state'><label>"+
		"50元</label></td><td class='state'><label>100元</label></td></tr><tr><td class='state'>"+
		"<label>张数</label></td><td><label><input id='tenCoupon' name='tenCoupon' value='' readonly/>"+
		"</label></td><td><input id='twentyCoupon' name='twentyCoupon' value='' readonly/>"+
		"</td><td><label><input id='thirtyCoupon' name='thirtyCoupon' value='' readonly/>"+
		"</label></td><td><label><input id='fiftyCoupon' name='fiftyCoupon' value='' readonly/>"+
		"</label></td><td><label><input id='hundredCoupon' name='hundredCoupon' value='' readonly/>"+
		"</label></td></tr></table>";
		$("#campaignDetail").html(innerDetail);
		$("#couponPrice").bind("click",function(){
		 	JDialog.open("优惠券组合", base_path + "/selectcouponGroupAnother.do",450,350);
		});
	}else if($("#businessId").val() == "4"){
		var innerDetail = "<table id='campaign1' name='campaign1'><tr><td class='state'><label>注册首晚入住</label></td><td class='state'><input id='discountPrice' name='discountPrice' value='' /></td><td class='state'><label>元</label></td></tr></table>";
		$("#campaignDetail").html(innerDetail);
	}else if($("#businessId").val() == "5"){
		var innerDetail = "<table id='campaign1' name='campaign1'>"+
 		"<tr id='oldLoad'><td class='state'><label>连续预约预订</label></td><td><input id='liveDay' name='liveDay' value='' /></td><td class='state'><label>天</label></td></tr>"+
		"</table>";
		$("#campaignDetail").html(innerDetail);
		
		$("#liveDay").change(function(){
			$("#oldLoad").siblings().remove();
			for(var i=0;i<$("#liveDay").val();i++){
				$("#campaign1").append("<tr><td class='state'><label>第"+(i+1)+"天&nbsp;&nbsp;&nbsp;&nbsp; 折扣</label></td><td><input id='dayCount"+(i+1)+"' name='dayCount"+(i+1)+"' value='' /></td><td class='state'><label>%</label></td></tr>");
			}
		});
	
	}else if($("#businessId").val() == "6"){
		var innerDetail ="<table id='campaign1' name='campaign1'><tr><td class='state'><label>归属门店：</label></td><td><input id='branchId' name='branchId' value='' /><input id='branchId_CUSTOM_VALUE' name='branchId_CUSTOM_VALUE' type='hidden' value='' /></td><td class='state'><label>特惠房型：</label></td>"+
		 "<td><input id='roomType' name='roomType' value='' /><input id='roomType_CUSTOM_VALUE' name='roomType_CUSTOM_VALUE' type='hidden' value='' /></td><td class='state'>"+
		 "<label>特惠价格：</label></td><td><input id='discountPrice' name='discountPrice' value='' /></td><td><label>元</label></td><tr>"+
		 "<td class='state' style='width:21%;'><label>特惠房间：</label></td><td><input id='experienceCount' name='experienceCount' value='' /></td><td><label>间</label></td>"
			"</tr></table>";
		$("#campaignDetail").html(innerDetail);
		
		$("#branchId").bind("click",function(){
		 	JDialog.open("归属门店", base_path + "/selectBranchId.do",450,350);
		});
		$("#roomType").bind("click",function(){
		 	JDialog.open("特惠房型", base_path + "/selectRoomType.do",450,350);
		});
	}else if($("#businessId").val() == "7"){
		var innerDetail = "<table id='campaign1' name='campaign1'>"+
		"<tr><td class='state' colspan='2' width='14%'><label>使用积分</label></td>"+
		"<td colspan='2'><input id='operScore' name='operScore' value='' /><label  style='margin-left:12px;font-size:12px;color:#333;'>分</label></td>"+
		"<td class='state' colspan='2'></td></tr>"+
		"<tr><td class='state' colspan='2'><label>送</label></td>"+
		"<td colspan='2'><input id='couponPrice' name='couponPrice' value='' type='text' readonly/><label style='margin-left:12px;font-size:12px;color:#333;'>元优惠券</label></td>"+
		"<td class='state' colspan='2'></td></tr>"+
		"<tr><td class='state' colspan='2'><label>组合名称</label></td>"+
		"<td colspan='2'><input id='couponGroupId' name='couponGroupId' type='hidden' value='' /><input id='couponName' name='couponName' value='' /></td>"+
		"<td class='state' colspan='2'><input id='showRule' name='showRule' type='text' style='width:444px;border:none;' readonly/></td></tr>" +
		"</table>";
		$("#campaignDetail").html(innerDetail);
		
		$("#couponPrice").bind("click",function(){
		 	JDialog.open("优惠券组合", base_path + "/selectcouponGroup.do",450,350);
		});
	}else{
		$("#campaignDetail").html("");
	}

}

function campaignPageReset(){
	window.location.reload(true);
};

function campaignClose(){
	window.parent.JDialog.close();
};

function alterCampaignInfo(){
	 //调用checkForm
	 var arr=new Array(
	     new Array('businessId','业务类型不可为空!'),
	     new Array('campaignName','活动名称不可为空!'),
	     new Array('campaignType','活动分类不可为空!'),
	     new Array('usingPerson','适用对象不可为空!'),
	     new Array('usingRange','使用范围不可为空!'), 
	     new Array('usingType','适用类型不可为空!'), 
	     new Array('startTime','开始时间不可为空!'), 
	     new Array('endTime','结束时间不可为空!'), 
	     new Array('priority','优先级不可为空!'), 
	     new Array('campaignDesc','活动描述不可为空!')
	 );
	 
	//调用checkFormInputLength
	 var arrlength=new Array(
	     new Array('campaignName','15','活动名称不可超过15字!'),
	     new Array('usingPerson_CUSTOM_VALUE','12','适用对象不可超过6种!'),
	     new Array('usingType_CUSTOM_VALUE','6','适用类型不可超过3种!'),
	     new Array('campaignDesc','60','活动描述不可超过60字!')
	 );
	
	
	 if(!checkFormIsNull(arr) ){
	     return false;
	 }else if(!checkFormInputLength(arrlength)){
		  return false;
	 }else{
		 if($("#businessId").val()=="1"){
			 var arrCompaignNull = new Array(new Array('goodsService','推荐会员个数不可为空!'),
					                     new Array('couponGroupId','优惠券不可为空!'));
			 var arrCompaignLength = new Array(new Array('goodsService','2','推荐会员的个数不可超过2位数!'),
                    							new Array('couponGroupId','8','优惠券组合只能选一个!'));
			//校验数字
			 var regNum = /^\+?[1-9][0-9]*$/;
             var memberCount = $('#goodsService').val();
             if (!regNum.test(memberCount)){
            	 window.parent.showMsg("输入会员个数格式不正确！");
            	 $("#goodsService").val("");
            	 return false;
             }
			 if(!checkFormIsNull(arrCompaignNull) ){
			     return false;
			 }else if(!checkFormInputLength(arrCompaignLength)){
				  return false;
			 }
		 }else if($("#businessId").val()=="2"){
			 var arrCompaignNull = new Array(new Array('couponGroupId','优惠券不可为空!'));
			 var arrCompaignLength = new Array(new Array('couponGroupId','8','优惠券组合只能选一个!'));
			if(!checkFormIsNull(arrCompaignNull) ){
				return false;
			}else if(!checkFormInputLength(arrCompaignLength)){
				return false;
			}
		 }else if($("#businessId").val()=="3"){
			 var arrCompaignNull = new Array(new Array('operMoney','充值金额不可为空!'),
                    						 new Array('couponGroupId','优惠券不可为空!'));
			 var arrCompaignLength = new Array(new Array('couponGroupId','8','优惠券组合只能选一个!'));
			 //金额校验
			 var reg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
            var operMoney = $("#operMoney").val();
            if (!reg.test(operMoney)){
           	 window.parent.showMsg("操作金额输入格式不正确！");
           	 $("#operMoney").val("");
           	 return false;
            }
                
			if(!checkFormIsNull(arrCompaignNull) ){
				return false;
			}else if(!checkFormInputLength(arrCompaignLength)){
				return false;
			}
		 }else if($("#businessId").val()=="4"){
			 var arrCompaignNull = new Array(new Array('discountPrice','金额不可为空!'));
			 var reg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
            var discountPrice = $('#discountPrice').val();
            if (!reg.test(discountPrice)){
            	window.parent.showMsg("输入金额格式不正确！");
	           	$("#discountPrice").val("");
	           	$("#discountPrice").focus();
	           	return false;
            }
			 if(!checkFormIsNull(arrCompaignNull) ){
				return false;
			 }
		 }else if($("#businessId").val()=="5"){
			 var arrCompaignNull = new Array(new Array('liveDay','天数不可为空!'));
			 var reg = /^\+?[1-9][0-9]*$/;
            var liveDay = $('#liveDay').val();
            if (!reg.test(liveDay)){
           	 window.parent.showMsg("输入天数格式不正确！");
           	 $("#liveDay").val("");
             $("#liveDay").focus();
           	 return false;
            }
             var datas = $("input[id^=dayCount]");
             for(var k=0;k<datas.length;k++){
           	  var content = $(datas[k]).val();
           	  if (content == null || content ==""){
           		 window.parent.showMsg("折扣不可为空！");
           		 $(datas[k]).val("");
           		 $(datas[k]).focus();
           	  }
           	  if (!reg.test(content)){
                	 window.parent.showMsg("输入折扣的格式不正确！");
                	 $(datas[k]).val("");
                	 $(datas[k]).focus();
                	 return false;
                 } 
             }
			 if(!checkFormIsNull(arrCompaignNull) ){
				return false;
			 }
		 }else if($("#businessId").val()=="6"){
			 var arrCompaignNull = new Array(new Array('roomType_CUSTOM_VALUE','房型不可为空!'),
					 							new Array('experienceCount','房间数不可为空!'),
					 			   new Array('discountPrice','特惠价格不可为空!'));
			 var arrCompaignLength = new Array(new Array('roomType_CUSTOM_VALUE','4','房型只能选一个!'));
			 var reg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
            var discountPrice = $('#discountPrice').val();
            if (!reg.test(discountPrice)){
           	 window.parent.showMsg("输入金额格式不正确！");
           	 setTimeOut(window.parent.location.reload(),1000);
           	 $("#discountPrice").val("");
           	 return false;
            }
            //校验非零正整数
            var regNum = /^\+?[1-9][0-9]*$/;
            var roomCount = $('#experienceCount').val();
            if (!regNum.test(roomCount)){
           	 window.parent.showMsg("输入房间数量格式不正确！");
           	 $("#experienceCount").val("");
           	 return false;
            }
            if(!checkFormIsNull(arrCompaignNull) ){
				return false;
			}else if(!checkFormInputLength(arrCompaignLength)){
				return false;
			}
		 }
		 
		 //在开始时间，结束时间保存到后台时将表单里的startTime,endTime清空，将相关内容在js里生成想要的string模式，再到后台进行转换
		// alert("开始时间"+$("#startTimeTemp").val()+"结束shiajina"+$("#endTimeTemp").val())
		 	var realStartTime = transStartAndEndTime($("#startTimeTemp").val()); 
		 	var realEndTime = transStartAndEndTime($("#endTimeTemp").val());
		 	//alert("sssss"+realStartTime+"ffff"+realEndTime)
		 	$("#businessId").removeAttr("disabled");
		 //ajax提交数据
			$.ajax({
				url:base_path + '/campaignsAlter.do?realStartTime='+realStartTime+'&realEndTime='+realEndTime,
				type:"post",
				data : $("#basicInfo").serialize(),
				success : function(json){
					if(json.result == 1){
						window.parent.showMsg("修改成功！");
						window.parent.refreshGrid();
						window.parent.JDialog.close();
					}else{
						window.parent.showMsg("操作失败！");
						window.setTimeout("window.parent.location.reload(true)", 1000);
					}
				},
				error:function(json){
					window.parent.showMsg("操作异常！"+json);
					window.setTimeout("window.parent.location.reload(true)", 1000);
				},
			})		
   }
}

//批量验证表单非空
function checkFormIsNull(arr){
	 for(var i=0;i<arr.length;i++){
		 if($("#"+arr[i][0]).val()==''){
			 window.parent.showMsg(arr[i][1]);
			 $("#"+arr[i][0]).focus();
			 return false;
		 }
	 }
	 return true; 
};

//批量验证字段长度
function checkFormInputLength(arrlength){
	 for(var j=0; j<arrlength.length;j++){
		 if($("#"+arrlength[j][0]).val().length > Number(arrlength[j][1])){
			 window.parent.showMsg(arrlength[j][2]);
			 $("#"+arrlength[j][0]).focus();
			 return false;	 
		 }
	 }
	 return true;
};
//删除当前记录
function deleteCampaignInfo(){
	$("#businessId").removeAttr("disabled");
	$.ajax({
		url:base_path + '/campaignsDelete.do',
		type:"post",
		data : $("#basicInfo").serialize(),
		success : function(json){
			if(json.result == 1){
				window.parent.showMsg("删除成功！");
				window.parent.refreshGrid();
				window.parent.JDialog.close();
			}else{
				window.parent.showMsg("删除失败！");
				window.setTimeout("window.parent.location.reload(true)", 1000);
			}
		},
		error:function(json){
			window.parent.showMsg("操作异常！"+json);
			window.setTimeout("window.parent.location.reload(true)", 1000);
		},
	})
}

//取转换后的开始时间，结束时间
function transStartAndEndTime(obj){
	if(obj.indexOf("-") > 0){
		var tempsdate = obj.replace(/-/g,"/").substring(0,obj.replace(/-/g,"/").length-3); 
		return tempsdate;
	}else{
		return obj;
	}
	
}