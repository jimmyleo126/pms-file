$(function(){
	bindClickEvent();
})

function bindClickEvent() {
	var movement = [ "0", "1", "2", "3", "4"];
	$(".header_order_li").on("click", function() {
		$("#orderFrame").attr("src",  "orderInfoAll.do?movement=" + movement[$(this).attr("index")]);
	})
}

$(".header_order_li").on("click",function(){
	$(this).addClass("active");
	$(this).siblings().removeClass("active");
});



$(function(){
	$(".date").datepicker({ dateFormat: "yy/mm/dd " });
	$("#starttime").css('font-size','0.9em');
	$("#endtime").css('font-size','0.9em');
	var now = new Date();    
    var year = now.getFullYear();       //年   
    var month = now.getMonth() + 1;     //月   
    var day = now.getDate();
    var time = year+"/"+month+"/"+day;
    $("#orderTime").val(time);
    $("#ordtimes").val(time);
    $("#arrivalTime").val(time);
    $("#arrtimes").val(time);
    $("#leaveTime").val(time);
    $("#leavetimes").val(time);
    
	loadsearchroomtype();
});


function loadsearchroomtype(){
	$.ajax({
         url: path + "/loadsearchroomtype.do",
		 type: "post",
		 data : {},
		 success: function(json) {
		 	var data = "<option value=''>房间类型</option>";
		 	$.each(json,function(index){
		 		data = data + "<option value='" +json[index]["ROOMNAME"] + "'>" +json[index]["ROOMNAME"] + "</option>"
		 	});
		 	$("#roomType").html(data);
			/**$(".mySelect").select({
				width: "175px"
			});*/
		 },
		 error: function(json) {}
	});
}

$(".highsearch").on("click",function(){
	$(".high_header").css("display","block");
	var now = new Date();    
    var year = now.getFullYear();       //年   
    var month = now.getMonth() + 1;     //月   
    var day = now.getDate();
    var time = year+"/"+month+"/"+day;
    $("#orderTime").val(time);
    $("#ordtimes").val(time);
    $("#arrivalTime").val(time);
    $("#arrtimes").val(time);
    $("#leaveTime").val(time);
    $("#leavetimes").val(time);
    $("#orderId").val("");
    $("#saleStaff").val("");
    $("#userCheckin").val("");
    $("#guarantee").val("");
    $("#orderUser").val("");
    $("#source option:first").prop("selected","selected");
    $("#mphone").val("");
    $("#roomType option:first").prop("selected","selected");
    $("#status option:first").prop("selected","selected");
})
$(".imooc").on("click",function(){
	$(".high_header").fadeOut(500);
});

function close(){
	$(".high_header").css("display","none");
}

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
	window.location.href="<%=request.getContextPath()%>/page/ipmspage/order/order_details.jsp";
}
$(".header_order_li").on("click",function(){
	$(this).addClass("active");
	$(this).siblings().removeClass("active");
});

function querydata(){
	$("#orderFrame").attr("src","queryOrderData.do?" + $("#myForm").serialize() );
	$(".high_header").css("display","none");
	$(".header_order_li").removeClass("active");
}	
