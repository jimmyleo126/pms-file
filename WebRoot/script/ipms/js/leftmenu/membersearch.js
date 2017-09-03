

function showMsg(msg, fn) {
				if (!fn) {
					TipInfo.Msg.alert(msg);
				} else {
					TipInfo.Msg.confirm(msg, fn);
				}
			}

function msidsearch(){
	var msoidcard = $("#msoidcard").val();
	var mphone ="";
	var mcard = "";
	if((msoidcard.length)>11){
		mcard = msoidcard;
	}else if((msoidcard.length)=11){
		mphone = msoidcard;
	}
	if(msoidcard){
	var re =  /^[0-9a-zA-Z]*$/g ;
		if(re.test(msoidcard)){			
			 $.ajax( {
					url : base_path + "/memberJudge.do",
					type : "post",
					dataType : "json",
					data : {
						'mcard' : mcard,
						'mphone' : mphone,
					},
					success : function(json) {
						if (1 == json.result) {
						    if(json.message){
						        showMsg(json.message);
						       }else{
						    	   $("#msIframe").attr("src","msCondition.do?mphone="+mphone+"&mcard="+mcard);
						       }
							
						} else {
							showMsg("没有该会员的信息，请输入正确的会员卡号或手机号！");
							window.setTimeout("location.reload(true)", 1800);
						}
					},
					error : function() {
						showMsg("操作失败，请重新操作！");
						window.setTimeout("location.reload(true)", 1800);
					}
				    });
					
		}else{
			showMsg("请先输入正确的手机号或身份证号格式（数字或字母）！")
		}
	}else{
	 showMsg("请先输入手机号或身份证号！")	
	}
}


function editmemberdata(){
	
	var memberid = $("#msmemberid").val();
	var mobile = $("#msmobile").val();
	var email = $("#msemail").val();
	var address = $("#msadress").val();
	var remark = $("#msremark").val();
	jQuery.ajax( {
		url : "editmemberData.do",
		dataType : "json",
		type : "post",
		data : {
			"memberid" : memberid,
			"mobile" : mobile,
			"email" :email,
			"address" : address,
			"remark" : remark
		},
		success : function(json) {
			if (1 == json.result) {
				showMsg("修改成功！");
				window.parent.parent.JDialog.close();
			} else {
				showMsg("修改失败");
				window.parent.parent.JDialog.close();
			}
		}
	});
}