
jQuery(document).ready(function() {
	
});

function transportData() {
	jQuery("#progressbar").css("display", "block");
	jQuery("#progressbar").progressbar({ value : 0 });
//	jQuery("#progressbar").progressbar({ value : false });
	
	load_flag = true;
	setProcessValue();
	
	//TODO transport data call servlet
	jQuery("#progressbar").progressbar({
		complete : function(event, ui) {
			showMsg("load complete!");
			jQuery("#progressbar").css("display", "none");
			load_flag = false;
		}
	});
}

function setProcessValue() {
	var cnt = jQuery("#progressbar").progressbar("option", "value") + 1;
	jQuery("#progressbar").progressbar("option", "value", cnt);
	if (load_flag && cnt <= 100) setTimeout("setProcessValue()", 100);
}

function showMsg(msg, fn) {
	if (!fn) {
		TipInfo.Msg.alert(msg);
	} else {
		TipInfo.Msg.confirm(msg, fn);
	}
}

function delDepart() {
	var departIds = concatColumns("DEPARTID");

	$.ajax({
		url: base_path + "/departDelete.do",
		type: "post",
		dataType: "json",
		data: { departIds: departIds },
		success: function(json) {
			var result = json.result;
			if (result == 1) {
				showMsg("操作成功");
				refreshGrid();
			}
		},
		error: function(json) {
			showMsg("操作失败！");
		}
	});
}

function delMember() {
	var memberid = concatColumns("MEMBERID");
	$.ajax({
		url: base_path + "/delMember.do",
		type: "post",
		dataType: "json",
		data: { memberid: memberid },
		success: function(json) {
			var result = json.code;
			if (result == 1) {
				showMsg("操作成功");
				refreshGrid();
			}
		},
		error: function(json) {
			showMsg("操作失败！");
		}
	});
}

function delContrart() {
	var contractId = concatColumns("CONTRARTID");
	$.ajax({
		url: base_path + "/delContrart.do",
		type: "post",
		dataType: "json",
		data: { contractId: contractId },
		success: function(json) {
			var result = json.code;
			if (result == 1) {
				showMsg("操作成功");
				refreshGrid();
			}
		},
		error: function(json) {
			showMsg("操作失败！");
		}
	});
}

function delUserCascade() {
	var userIds = concatColumns("USERID");

	$.ajax({
		url: base_path + "/userDelete.do",
		type: "post",
		dataType: "json",
		data: { userIds: userIds },
		success: function(json) {
			var result = json.result;
			if (result == 1) {
				showMsg("操作成功");
				refreshGrid();
			} else {
				var msg = json.message;
				showMsg("用户[" + msg.substring(0, msg.length - 1) + "]在线，无法删除！");
			}
		},
		error: function(json) {
			showMsg("操作失败！");
		}
	});
}

function roleRightEdit(rowid) {
	var roleId = "";
	if (rowid) {
		var rowData = $(grid_name).jqGrid("getRowData", rowid);
		roleId = rowData["ROLEID"];
	}
	JDialog.open("角色管理", base_path + "/roleRightEdit.do?roleId=" + roleId, 920, 580, "doSubmit");
}

function roleRightDelete() {
	var roleIds = concatColumns("ROLEID");
	
	$.ajax({
		url: base_path + "/roleRightDelete.do",
		type: "post",
		dataType: "json",
		data: { roleIds: roleIds },
		success: function(json) {
			showMsg("操作成功！");
			refreshGrid();
		},
		error: function(json) {
			showMsg("操作失败！");
		}
	});
}

function addAdjustButton(v) {
	return "<a class='b_button_cell'><span class='b_minus' onclick='adjustNum(this, -1)'>-</span></a>"
		 + "<span name='val'>" + v + "</span>" 
		 + "<a class='b_button_cell'><span class='b_plus' onclick='adjustNum(this, 1)'>+</span></a>";
}

function adjustNum(obj, num) {
	var val = $(obj).parent().parent().find("span[name=val]");
	val.html(parseInt(val.html()) + num);
}

function closeDialog(){
	window.parent.JDialog.close();
}
//储值卡充值
function cardRecharge(rowid){
	var rowData = getRowDataById(rowid);
	var accountid=rowData["ACCOUNTID"];
	var memberid=rowData["MEMBERID"];
	var membername=rowData["MEMBER_NAME"];
	JDialog.open("储值卡充值", base_path + "/turnToCardRecharge.do?accountid="+accountid+"&memberid="+memberid+"&membername="+membername,920,550);
}
//查看合同文档
function querycontract(rowid){
	var rowData = getRowDataById(rowid);
	var contract=rowData["CONTRART"];
	JDialog.open("合同查看", base_path + "/queryContract.do?contract="+contract,1000,600);
}
//房租续约
function rentRenewal(rowid){
	var rowData = getRowDataById(rowid);
	var contractId=rowData["CONTRARTID"];
	JDialog.open("房租续约", base_path + "/rentRenewal.do?contractId="+contractId,600,500);
}
//会员卡升级
function memberUpGrade(rowid){
	var rowData = getRowDataById(rowid);
	var accountid=rowData["ACCOUNTID"];
	var memberid=rowData["MEMBERID"];
	JDialog.open("会员升级", base_path + "/memberUpGrade.do?accountid="+accountid+"&memberid="+memberid,1000,600);
}
//会员卡升级充值
function payUpGradeMemberLevel(member){
	var memberrank = member.id;
	var memberrankname = member.value;
	var accountid = $("#accountid").val();
	var memberid = $("#memberid").val();
	JDialog.open("会员", base_path + "/payUpGradeMemberLevel.do?memberrank="+memberrank+"&accountid="+accountid+"&memberid="+memberid+"&memberrankname="+memberrankname,500,300);
}

//新增模板 
function addSmsTemplatePage(){
	JDialog.open("模板新增", base_path + "/turnToSmsTemplateAddPage.do", 800, 420);	
}

//模板修改编辑
function alterSmsTemplate(rowid){
	var rowData = getRowDataById(rowid);
	var templateId = rowData["TEMPLATEID"];
	JDialog.open("模板修改", base_path + "/turnToalterSmsTemplatePage.do?templateId="+templateId,800,420)
}

//审核信息页面
function auditInfoDetail(rowid){
	var rowData = getRowDataById(rowid);
	var audittype = rowData["TABLEAUDITTYPE"];
	var operid = rowData["OPERID"];
	var pagetype = "audit" ;
	if(audittype=="采购申请"){
		JDialog.open("审核信息", base_path + "/auditInfoDetail.do?operid="+operid+"&pagetype="+pagetype+"&audittype="+audittype,780,510);
	}else if(audittype=="房租价申请"){
		JDialog.open("审核信息", base_path + "/rpapplyInfoDetail.do?operid="+operid+"&pagetype="+pagetype+"&audittype="+audittype,780,600);
	}else if(audittype=="退房申请"){
		JDialog.open("审核信息", base_path + "/checkoutInfoDetail.do?operid="+operid+"&pagetype="+pagetype+"&audittype="+audittype,780,300);
	}else if(audittype=="维修申请"){
		JDialog.open("审核信息", base_path + "/repairapplyInfoDetail.do?operid="+operid+"&pagetype="+pagetype+"&audittype="+audittype,980,300);
	}
	
}

//审核日志信息页面
function audiLogInfoDetail(rowid){
	var rowData = getRowDataById(rowid);
	var audittype = rowData["OPERTYPE"];
	var operid = rowData["OPERID"];
	var pagetype = "auditlog" ;
	if(audittype=="采购申请"){
		JDialog.open("审核信息", base_path + "/auditInfoDetail.do?operid="+operid+"&pagetype="+pagetype+"&audittype="+audittype,780,510);
	}else if(audittype=="房租价申请"){
		JDialog.open("审核信息", base_path + "/rpapplyInfoDetail.do?operid="+operid+"&pagetype="+pagetype+"&audittype="+audittype,780,600);
	}else if(audittype=="退房申请"){
		JDialog.open("审核信息", base_path + "/checkoutInfoDetail.do?operid="+operid+"&pagetype="+pagetype+"&audittype="+audittype,780,300);
	}else if(audittype=="维修申请"){
		JDialog.open("审核信息", base_path + "/repairapplyInfoDetail.do?operid="+operid+"&pagetype="+pagetype+"&audittype="+audittype,980,300);
	}
}

//短信发送的详情（某一条数据是否发送成功，短信内容是什么）
function turnToSmsShowDetail(rowid){
	var rowData = getRowDataById(rowid);
	var templateId = rowData["TEMPLATEID"];
	JDialog.open("发送详情", base_path + "/turnToSmsShowDetailPage.do?templateId="+templateId,1000,520);
}

//积分调整
function scoreAdjustment(rowid){
	var rowData = getRowDataById(rowid);
	var accountid=rowData["ACCOUNTID"];
	JDialog.open("积分调整", base_path + "/turnToScoreAdjustment.do?accountid="+accountid,780,410);
}

//新增营销活动
function addCampaignsPage(){
	JDialog.open("活动新增", base_path + "/turnToCampaignsAddPage.do", 870, 500);	
}

//活动修改编辑
function alterCampaign(rowid){
	var rowData = getRowDataById(rowid);
	var campaignId = rowData["DATAID"];
	JDialog.open("活动修改", base_path + "/turnAlterCampaignPage.do?campaignId="+campaignId,870,500)
}


//客户来源分析详细信息查看
function showDetailInformation(rowid){
	var rowData = getRowDataById(rowid);
	var time = rowData["ORDERTIME"];
	JDialog.open("客户来源详情", base_path + "/showDetailInformation.do?time=" + time ,987,410);
	
}
//会员层次统计详情
function showMemberDetailInfo(rowid){
	var rowData = getRowDataById(rowid);
	var time = rowData["REGISTERTIME"];
	
	JDialog.open("会员详情", base_path + "/showMemberDetailInfo.do?time=" + time ,1230,410);
}
//会员消费详情
function  showConsumeDetailInfo(rowid){
	var rowData = getRowDataById(rowid);
	var memberID = rowData["MEMBERID"];
	JDialog.open("消费详情", base_path + "/showConsumeDetailInfo.do?memberID=" + memberID,1500,410);
}
//房间销售明细订单详情
function OrderStream(rowid){
	var rowData = getRowDataById(rowid);
	var roomtype = rowData["ROOMTYPE"];
	var startdate = rowData["STARTDATE"];
	var enddate = rowData["ENDDATE"];
	console.log(rowData);
	JDialog.open("详情查询", base_path + "/orderRoomDetailShow.do?roomtype="+roomtype+"&startdate="+startdate+"&enddate="+enddate,800,380)
}
//房间销售汇总房单详情
function RoomSummary(rowid){
	var rowData = getRowDataById(rowid);
	var roomtype = rowData["ROOMTYPE"];
	var startdate = rowData["STARTDATE"];
	var enddate = rowData["ENDDATE"];
	JDialog.open("详情查询", base_path + "/checkDetailShow.do?roomtype="+roomtype+"&startdate="+startdate+"&enddate="+enddate,1000,380)
}

//房间管理自定义逻辑删除
function delRoom(){	
	var theme = concatColumns("THEME");
	var branchid = concatColumns("BRANCHID");
	var roomid = concatColumns("ROOMID");
	var roomtype = concatColumns("ROOMTYPE");
	var roomstatus = concatColumns("STATUS");
	$.ajax({
		url: base_path + "/delRoom.do",
		type: "post",
		dataType: "json",
		data: { theme: theme,
		        branchid: branchid,
		        roomid: roomid,
		        roomtype: roomtype,
		        roomstatus: roomstatus
		},
		success: function(json) {
			var result = json.result;
			if (result == 1) {
				showMsg("删除成功");
				 window.setTimeout("location.reload(true)", 1800);
			} else {
				showMsg("删除失败");
				 window.setTimeout("location.reload(true)", 1800);
			}
		},
		error: function(json) {
			showMsg("操作失败！");
			 window.setTimeout("location.reload(true)", 1800);
		}
	});
}


//房价申请页面
function rpApply(rowid){
	var rowData = getRowDataById(rowid);
	var themename = rowData["THEME"];
	var branchname = rowData["BRANCHID"];
	var rpkindname = rowData["RPKIND"];
	var roomtypename = rowData["ROOMTYPE"];
	var status = rowData["STATUS"];
	JDialog.open("房价管理", base_path + "/rpBasicApply.do?themename="+themename+"&branchname="+branchname+"&rpkindname="+rpkindname+"&roomtypename="+roomtypename+"&status="+status,880,610);		
}


//房价初始化
function rpInitialize(){
	$.ajax({
		url: base_path + "/rpInitialize.do",
		type: "post",
		dataType: "json",
		success: function(json) {
			if (json.result == 1) {
				if(json.message){
			     showMsg(json.message);
				}else{
					showMsg("初始化成功");
					window.setTimeout("location.reload(true)", 1800);
				}
				
			} else {
				 showMsg("初始化失败");
				 window.setTimeout("location.reload(true)", 1800);
			}
		},
		error: function(json) {
			showMsg("操作失败！");
			 window.setTimeout("location.reload(true)", 1800);
		}
	});
	
}



//房租价调整页面
function rpAdjust(rowid){
	showMsg("开发测试中，暂不能点击！");
}


//商品自定义逻辑删除
function delGoods(){	
	var goodsid = concatColumns("GOODSID");
	$.ajax({
		url: base_path + "/delGoods.do",
		type: "post",
		dataType: "json",
		data: { goodsid: goodsid
		},
		success: function(json) {
			var result = json.result;
			if (result == 1) {
				showMsg("删除成功");
				 window.setTimeout("location.reload(true)", 1800);
			} else {
				showMsg("删除失败");
				 window.setTimeout("location.reload(true)", 1800);
			}
		},
		error: function(json) {
			showMsg("操作失败！");
			 window.setTimeout("location.reload(true)", 1800);
		}
	});
}

//商品分类自定义逻辑删除
function delCategory(){	
	var categoryid = concatColumns("CATEGORYID");
	$.ajax({
		url: base_path + "/delCategory.do",
		type: "post",
		dataType: "json",
		data: { categoryid: categoryid
		},
		success: function(json) {
			var result = json.result;
			if (result == 1) {
				showMsg("删除成功");
				 window.setTimeout("location.reload(true)", 1800);
			} else {
				showMsg("删除失败");
				 window.setTimeout("location.reload(true)", 1800);
			}
		},
		error: function(json) {
			showMsg("操作失败！");
			 window.setTimeout("location.reload(true)", 1800);
		}
	});
}

//房间管理自定义新增编辑
function editrpRow(rowid){
	var rowData = getRowDataById(rowid);
	var theme = rowData["THEME"];
	var branch = rowData["BRANCHID"];
	var roomtype = rowData["ROOMTYPE"];
	var floor = rowData["FLOOR"];
	var roomid = rowData["ROOMID"];
	var area = rowData["AREA"];
	var status = rowData["STATUS"];
	var remark = rowData["REMARK"];
	if((!branch)&&(!roomid)){
		JDialog.open("添加记录", base_path + "/roomAdd.do" ,500,420);
	}else{
		
		JDialog.open("编辑记录", base_path + "/roomEdit.do?theme="+theme+"&branch="+branch+"&roomtype="+roomtype+"&floor="+floor+"&roomid="+roomid+"&area="+area+"&status="+status+"&remark="+remark ,500,420);
	}	
}

//房租价自定义逻辑删除
function delRoomPrice(){	
	var theme = concatColumns("THEME");
	var branchid = concatColumns("BRANCHID");
	var roomtype = concatColumns("ROOMTYPE");
	var rpid = concatColumns("RPID");
	$.ajax({
		url: base_path + "/delRoomPrice.do",
		type: "post",
		dataType: "json",
		data: { theme: theme,
		        branchid: branchid,
		        roomtype: roomtype,
		        rpid: rpid
		},
		success: function(json) {
			var result = json.result;
			if (result == 1) {
				showMsg("删除成功");
				 window.setTimeout("location.reload(true)", 1800);
			} else {
				showMsg("删除失败");
				 window.setTimeout("location.reload(true)", 1800);
			}
		},
		error: function(json) {
			showMsg("操作失败！");
			 window.setTimeout("location.reload(true)", 1800);
		}
	});
}

//金柜自定义逻辑删除
function delCash(){	
	var branchId = concatColumns("BRANCHID");
	var cashBox = concatColumns("CASHBOX");
	$.ajax({
		url: base_path + "/delCash.do",
		type: "post",
		dataType: "json",
		data: { branchId: branchId,
		        cashBox: cashBox
		},
		success: function(json) {
			var result = json.result;
			if (result == 1) {
				showMsg("删除成功");
				 window.setTimeout("location.reload(true)", 1800);
			} else {
				showMsg("删除失败");
				 window.setTimeout("location.reload(true)", 1800);
			}
		},
		error: function(json) {
			showMsg("操作失败！");
			 window.setTimeout("location.reload(true)", 1800);
		}
	});
}







