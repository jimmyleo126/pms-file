$(document).ready(function() {
	$.ajax({
		url:base_path + "/queryRoomList.do",
		type:"post",
		dataType: "json",
		success:function(json) {
			if(json) {
				if (json.state == 1) {
					$(".hotel").show();
					$(".apartment").hide();
					$(".room_list").html(" ");
					for (i=0;i<json.roomlist.length;i++) {
						if (json.roomlist[i].STATUS == '0') {
							$("<li><span class='roomId'>" + json.roomlist[i].ROOMID + "</span><span>" + json.roomlist[i].ROOMTYPE + "</span><span>" 
									+json.roomlist[i].CHECKUSER + "</span><span style='display:none'>" + json.roomlist[i].STATUS + "</span>").appendTo(".room_list");
						} else if (json.roomlist[i].STATUS == '1') {
							$("<li class='order'><span class='roomId'>" + json.roomlist[i].ROOMID + "</span><span>" + json.roomlist[i].ROOMTYPE + "</span><span>" 
									+json.roomlist[i].CHECKUSER + "</span><span style='display:none'>" + json.roomlist[i].STATUS + "</span>").appendTo(".room_list");
						} else if (json.roomlist[i].STATUS == '2') {
							$("<li class='live'><span class='roomId'>" + json.roomlist[i].ROOMID + "</span><span style='display:none'>" + json.roomlist[i].CHEID +"</span><span>" + json.roomlist[i].ROOMTYPE + "</span><span>" 
									+json.roomlist[i].CHECKUSER + "</span><span style='display:none'>" + json.roomlist[i].STATUS + "</span>").appendTo(".room_list");
						} else if (json.roomlist[i].STATUS == 'T') {
							$("<li class='stopsell'><span class='roomId'>" + json.roomlist[i].ROOMID + "</span><span>" + json.roomlist[i].ROOMTYPE + "</span><span>" 
									+json.roomlist[i].CHECKUSER + "</span><span style='display:none'>" + json.roomlist[i].STATUS + "</span>").appendTo(".room_list");
						} else if (json.roomlist[i].STATUS == 'Z') {
							$("<li class='dirty'><span class='roomId'>" + json.roomlist[i].ROOMID + "</span><span>" + json.roomlist[i].ROOMTYPE + "</span><span>" 
									+json.roomlist[i].CHECKUSER + "</span><span style='display:none'>" + json.roomlist[i].STATUS + "</span>").appendTo(".room_list");
						} else if (json.roomlist[i].STATUS == 'W') {
							$("<li class='maintance'><span class='roomId'>" + json.roomlist[i].ROOMID + "</span><span>" + json.roomlist[i].ROOMTYPE + "</span><span>" 
									+json.roomlist[i].CHECKUSER + "</span><span style='display:none'>" + json.roomlist[i].STATUS + "</span>").appendTo(".room_list");
						} else if (json.roomlist[i].STATUS == 'O') {
							$("<li class='other'><span class='roomId'>" + json.roomlist[i].ROOMID + "</span><span>" + json.roomlist[i].ROOMTYPE + "</span><span>" 
								+json.roomlist[i].CHECKUSER + "</span><span style='display:none'>" + json.roomlist[i].STATUS + "</span>").appendTo(".room_list");
						}
					}
					$(".totalcount").text(json.totalcount[0].TOTAL);
					$(".livecount").html(json.totallive[0].LIVE);
					
					$("li").mousedown(function(e) {
						$(".setclean").val($(this).find(".roomId").text());
						$(document).bind("contextmenu",
						    function(){
						        return false;
						    }
						);
						if (e.which == 3) {
							  var mousePos = mousePosition(e);  
						        var  xOffset = 20;  
						        var  yOffset = 25; 
						        if ($(this).children("span:last-child").html() == '0' ) {
						        	$(".dirtyroom").hide();
							        $(".cleanroom").show();
							        $(".stoproom").hide();
							        $(".vzroom").hide();
							        $("#cleanroomid").val($(this).children("span:first-child").html());
							        $(".cleanroom").css("display","block").css("position","absolute")
							        		.css("top",(mousePos.y - yOffset) + "px").css("left",(mousePos.x + xOffset) + "px");  
						        } else if ($(this).children("span:last-child").html() == '2') {
						        	$(".cleanroom").hide();    
							        $(".dirtyroom").show();
							        $(".vzroom").hide();
							        $(".stoproom").hide();
							       // alert($(this).children("span:first-child").html());
							        $("#dirtyroomid").val($(this).children("span:first-child").html());
							        $("#thischeckid").val($(this).children("span:nth-child(2)").html());
							        $(".dirtyroom").css("display","block").css("position","absolute")
							        		.css("top",(mousePos.y - yOffset) + "px").css("left",(mousePos.x + xOffset) + "px");  
						        } else if ($(this).children("span:last-child").html() == 'T') {
									$(".stoproom").show();
									$(".cleanroom").hide(); 
									$(".dirtyroom").hide();
									$(".vzroom").hide();
									$("#cleanroomid").val($(this).children("span:first-child").html());
							        $(".stoproom").css("display","block").css("position","absolute")
					        		.css("top",(mousePos.y - yOffset) + "px").css("left",(mousePos.x + xOffset) + "px");  
						       } else if ($(this).children("span:last-child").html() == 'Z') {
									$(".vzroom").show();
									$(".stoproom").hide();
									$(".cleanroom").hide(); 
									$(".dirtyroom").hide();
									$("#cleanroomid").val($(this).children("span:first-child").html());
							        $(".vzroom").css("display","block").css("position","absolute")
					        		.css("top",(mousePos.y - yOffset) + "px").css("left",(mousePos.x + xOffset) + "px"); 
						       }
						}
					})
					
					$("li").dblclick(function(e) {
						if ($(this).children("span:last-child").html() == '0') {
							JDialog.openWithNoTitle("", base_path + "/orderNew.do",940,704);
						} else if ($(this).children("span:last-child").html() == '2') {
							var checknumber = $(this).children("span:nth-child(2)").html()
							var which = 'check';
							window.JDialog.openWithNoTitle("",base_path +'/page/ipmspage/room_statistics/turntoroomlistcheck.jsp?checkid=' + checknumber + '&type=' + which, 1179, 733);
						} else if ($(this).children("span:last-child").html() == 'W') {
							showMsg("此房为维修房!");
						} else if ($(this).children("span:last-child").html() == 'T') {
							showMsg("此房为停售房!");
						} else if ($(this).children("span:last-child").html() == 'Z') {
							showMsg("此房为脏房!");
						}
					}); 
				} else if (json.state == 2) {
					$(".hotel").hide();
					$(".apartment").show();
					$(".room_list").html(" ");
					for (i=0;i<json.apartment.length;i++) {
						if (json.apartment[i].STATUS == '0') {
							$("<li><span class='roomId'>" + json.apartment[i].RMID + "</span><span style='display:none'>" + json.apartment[i].STATUS + "</span><span>" + json.apartment[i].TYPE + "</span>").appendTo(".room_list");
						} else if (json.apartment[i].STATUS == '1') {
							$("<li class='order'><span class='roomId'>" + json.apartment[i].RMID + "</span><span style='display:none'>" + json.apartment[i].STATUS + "</span><span>" + "预定" + "</span>").appendTo(".room_list");
						} else if (json.apartment[i].STATUS == '2') {
							$("<li class='live'><span class='roomId'>" + json.apartment[i].RMID + "</span><span style='display:none'>" + json.apartment[i].STATUS + "</span><span>" + json.apartment[i].TYPE + "</span>").appendTo(".room_list");
						} else if (json.apartment[i].STATUS == 'M') {
							$("<li class='stopsell'><span class='roomId'>" + json.apartment[i].RMID + "</span><span style='display:none'>" + json.apartment[i].STATUS + "</span><span>" + "样板房" + "</span>").appendTo(".room_list");
						} else if (json.apartment[i].STATUS == 'E') {
							$("<li class='dirty'><span class='roomId'>" + json.apartment[i].RMID + "</span><span style='display:none'>" + json.apartment[i].STATUS + "</span><span>" + "员工宿舍" + "</span>").appendTo(".room_list");
						} else if (json.apartment[i].STATUS == 'W') {
							$("<li class='maintance'><span class='roomId'>" + json.apartment[i].RMID + "</span><span style='display:none'>" + json.apartment[i].STATUS + "</span><span>" + "维修房" + "</span>").appendTo(".room_list");
						} else if (json.apartment[i].STATUS == 'O') {
							$("<li class='other'><span class='roomId'>" + json.apartment[i].RMID + "</span><span style='display:none'>" + json.apartment[i].STATUS + "</span><span>" + "其他" + "</span>").appendTo(".room_list");
						}
					}
				}
			} 
		}
	});	
	getRoomFloor();
	getRoomType();
})


				

function queryalllist() {
	var roomtype = $("#dropse_roomtype").val();
	var roomfloor = $("#dropse_floor").val();
	
	$.ajax({
		url:base_path + "/queryRoomList.do",
		type:"post",
		dataType: "json",
		data: { roomtype:roomtype, roomfloor:roomfloor },
		success:function(json) {
			if(json) {
				if (json.state == 1) {
					$(".hotel").show();
					$(".apartment").hide();
					$(".room_list").html(" ");
					for (i=0;i<json.roomlist.length;i++) {
						if (json.roomlist[i].STATUS == '0') {
							$("<li><span class='roomId'>" + json.roomlist[i].ROOMID + "</span><span>" + json.roomlist[i].ROOMTYPE + "</span><span>" 
									+json.roomlist[i].CHECKUSER + "</span><span style='display:none'>" + json.roomlist[i].STATUS + "</span>").appendTo(".room_list");
						} else if (json.roomlist[i].STATUS == '1') {
							$("<li class='order'><span class='roomId'>" + json.roomlist[i].ROOMID + "</span><span>" + json.roomlist[i].ROOMTYPE + "</span><span>" 
									+json.roomlist[i].CHECKUSER + "</span><span style='display:none'>" + json.roomlist[i].STATUS + "</span>").appendTo(".room_list");
						} else if (json.roomlist[i].STATUS == '2') {
							$("<li class='live'><span class='roomId'>" + json.roomlist[i].ROOMID + "</span><span style='display:none'>" + json.roomlist[i].CHEID +"</span><span>" + json.roomlist[i].ROOMTYPE + "</span><span>" 
									+json.roomlist[i].CHECKUSER + "</span><span style='display:none'>" + json.roomlist[i].STATUS + "</span>").appendTo(".room_list");
						} else if (json.roomlist[i].STATUS == 'T') {
							$("<li class='stopsell'><span class='roomId'>" + json.roomlist[i].ROOMID + "</span><span>" + json.roomlist[i].ROOMTYPE + "</span><span>" 
									+json.roomlist[i].CHECKUSER + "</span><span style='display:none'>" + json.roomlist[i].STATUS + "</span>").appendTo(".room_list");
						} else if (json.roomlist[i].STATUS == 'Z') {
							$("<li class='dirty'><span class='roomId'>" + json.roomlist[i].ROOMID + "</span><span>" + json.roomlist[i].ROOMTYPE + "</span><span>" 
									+json.roomlist[i].CHECKUSER + "</span><span style='display:none'>" + json.roomlist[i].STATUS + "</span>").appendTo(".room_list");
						} else if (json.roomlist[i].STATUS == 'W') {
							$("<li class='maintance'><span class='roomId'>" + json.roomlist[i].ROOMID + "</span><span>" + json.roomlist[i].ROOMTYPE + "</span><span>" 
									+json.roomlist[i].CHECKUSER + "</span><span style='display:none'>" + json.roomlist[i].STATUS + "</span>").appendTo(".room_list");
						} else if (json.roomlist[i].STATUS == 'O') {
							$("<li class='other'><span class='roomId'>" + json.roomlist[i].ROOMID + "</span><span>" + json.roomlist[i].ROOMTYPE + "</span><span>" 
								+json.roomlist[i].CHECKUSER + "</span><span style='display:none'>" + json.roomlist[i].STATUS + "</span>").appendTo(".room_list");
						}
					}
					$("li").mousedown(function(e) {
						$(".setclean").val($(this).find(".roomId").text());
						$(document).bind("contextmenu",
						    function(){
						        return false;
						    }
						);
						if (e.which == 3) {
							  var mousePos = mousePosition(e);  
						        var  xOffset = 20;  
						        var  yOffset = 25; 
						        if ($(this).children("span:last-child").html() == '0' ) {
						        	$(".dirtyroom").hide();
							        $(".cleanroom").show();
							        $(".stoproom").hide();
							        $(".vzroom").hide();
							        $("#cleanroomid").val($(this).children("span:first-child").html());
							        $(".cleanroom").css("display","block").css("position","absolute")
							        		.css("top",(mousePos.y - yOffset) + "px").css("left",(mousePos.x + xOffset) + "px");  
						        } else if ($(this).children("span:last-child").html() == '2') {
						        	$(".cleanroom").hide();    
							        $(".dirtyroom").show();
							        $(".vzroom").hide();
							        $(".stoproom").hide();
							       // alert($(this).children("span:first-child").html());
							        $("#dirtyroomid").val($(this).children("span:first-child").html());
							        $("#thischeckid").val($(this).children("span:nth-child(2)").html());
							        $(".dirtyroom").css("display","block").css("position","absolute")
							        		.css("top",(mousePos.y - yOffset) + "px").css("left",(mousePos.x + xOffset) + "px");  
						        } else if ($(this).children("span:last-child").html() == 'T') {
									$(".stoproom").show();
									$(".cleanroom").hide(); 
									$(".dirtyroom").hide();
									$(".vzroom").hide();
									$("#cleanroomid").val($(this).children("span:first-child").html());
							        $(".stoproom").css("display","block").css("position","absolute")
					        		.css("top",(mousePos.y - yOffset) + "px").css("left",(mousePos.x + xOffset) + "px");  
						       } else if ($(this).children("span:last-child").html() == 'Z') {
									$(".vzroom").show();
									$(".stoproom").hide();
									$(".cleanroom").hide(); 
									$(".dirtyroom").hide();
									$("#cleanroomid").val($(this).children("span:first-child").html());
							        $(".vzroom").css("display","block").css("position","absolute")
					        		.css("top",(mousePos.y - yOffset) + "px").css("left",(mousePos.x + xOffset) + "px"); 
						       }
						}
					})
					
					$("li").dblclick(function(e) {
						if ($(this).children("span:last-child").html() == '0') {
							JDialog.openWithNoTitle("", base_path + "/orderNew.do",940,704);
						} else if ($(this).children("span:last-child").html() == '2') {
							var checknumber = $(this).children("span:nth-child(2)").html()
							var which = 'check';
							window.JDialog.openWithNoTitle("",base_path +'/page/ipmspage/room_statistics/turntoroomlistcheck.jsp?checkid=' + checknumber + '&type=' + which, 1179, 733);
						} else if ($(this).children("span:last-child").html() == 'W') {
							showMsg("此房为维修房!");
						} else if ($(this).children("span:last-child").html() == 'T') {
							showMsg("此房为停售房!");
						} else if ($(this).children("span:last-child").html() == 'Z') {
							showMsg("此房为脏房!");
						}
					}); 
				} else if (json.state == 2) {
					$(".hotel").hide();
					$(".apartment").show();
					$(".room_list").html(" ");
					for (i=0;i<json.apartment.length;i++) {
						if (json.apartment[i].STATUS == '0') {
							$("<li><span class='roomId'>" + json.apartment[i].RMID + "</span><span style='display:none'>" + json.apartment[i].STATUS + "</span><span>" + json.apartment[i].TYPE + "</span>").appendTo(".room_list");
						} else if (json.apartment[i].STATUS == '1') {
							$("<li class='order'><span class='roomId'>" + json.apartment[i].RMID + "</span><span style='display:none'>" + json.apartment[i].STATUS + "</span><span>" + "预定" + "</span>").appendTo(".room_list");
						} else if (json.apartment[i].STATUS == '2') {
							$("<li class='live'><span class='roomId'>" + json.apartment[i].RMID + "</span><span style='display:none'>" + json.apartment[i].STATUS + "</span><span>" + json.apartment[i].TYPE + "</span>").appendTo(".room_list");
						} else if (json.apartment[i].STATUS == 'M') {
							$("<li class='stopsell'><span class='roomId'>" + json.apartment[i].RMID + "</span><span style='display:none'>" + json.apartment[i].STATUS + "</span><span>" + "样板房" + "</span>").appendTo(".room_list");
						} else if (json.apartment[i].STATUS == 'E') {
							$("<li class='dirty'><span class='roomId'>" + json.apartment[i].RMID + "</span><span style='display:none'>" + json.apartment[i].STATUS + "</span><span>" + "员工宿舍" + "</span>").appendTo(".room_list");
						} else if (json.apartment[i].STATUS == 'W') {
							$("<li class='maintance'><span class='roomId'>" + json.apartment[i].RMID + "</span><span style='display:none'>" + json.apartment[i].STATUS + "</span><span>" + "维修房" + "</span>").appendTo(".room_list");
						} else if (json.apartment[i].STATUS == 'O') {
							$("<li class='other'><span class='roomId'>" + json.apartment[i].RMID + "</span><span style='display:none'>" + json.apartment[i].STATUS + "</span><span>" + "其他" + "</span>").appendTo(".room_list");
						}
					}
				}
			} 
		}
	});		
}


function queryarrival() {
	$.ajax({
		url:base_path + "/queryArrival.do",
		type:"post",
		dataType:"json",
		success:function(json) {
			if(json) {
				$(".room_list").html(" ");
				for (i=0;i<json.length;i++) {
					if (json[i].STATUS == '0') {
						$("<li><span>"+ json[i].ROOMID + "</span><span>" + json[i].ROOMTYPE + "</span><span>" 
								+json[i].CHECKUSER + "</span>").appendTo(".room_list");
					} else if (json[i].STATUS == '1') {
						$("<li class='order'><span>" + json.ROOMID + "</span><span>" + json[i].ROOMTYPE + "</span><span>" 
								+json[i].CHECKUSER + "</span>").appendTo(".room_list");
					} else if (json[i].STATUS == '2') {
						$("<li class='live'><span>" + json[i].ROOMID + "</span><span>" + json[i].ROOMTYPE + "</span><span>" 
								+json[i].CHECKUSER + "</span>").appendTo(".room_list");
					} else if (json[i].STATUS == 'T') {
						$("<li class='stopsell'><span>" + json[i].ROOMID + "</span><span>" + json[i].ROOMTYPE + "</span><span>" 
								+json[i].CHECKUSER + "</span>").appendTo(".room_list");
					} else if (json[i].STATUS == 'Z') {
						$("<li class='dirty'><span>" + json[i].ROOMID + "</span><span>" + json[i].ROOMTYPE + "</span><span>" 
								+json[i].CHECKUSER + "</span>").appendTo(".room_list");
					} else if (json[i].STATUS == 'W') {
						$("<li class='maintance'><span>" + json[i].ROOMID + "</span><span>" + json[i].ROOMTYPE + "</span><span>" 
								+json[i].CHECKUSER + "</span>").appendTo(".room_list");
					} else if (json[i].STATUS == 'O') {
						$("<li class='other'><span>" + json[i].ROOMID + "</span><span>" + json[i].ROOMTYPE + "</span><span>" 
							+json[i].CHECKUSER + "</span>").appendTo(".room_list");
					}
				}
			}
		}
	})
}

function queryleavelive(status){
	var roomtype = $("#dropse_roomtype").val();
	var roomfloor = $("#dropse_floor").val();
	
	$.ajax({
		url:base_path + "/queryLeavelive.do",
		type:"post",
		dataType:"json",
		data: { status: status, roomtype:roomtype, roomfloor:roomfloor },
		success:function(json) {
			if(json.length > 0) {
				$(".room_list").html(" ");
				for (i=0;i<json.length;i++) {
					$("<li class='live'><span>" + json[i].ROOMID + "</span><span style='display:none'>" + json[i].CHEID +"</span><span>" + json[i].ROOMTYPE + "</span><span>" 
							+json[i].CHECKUSER + "</span>").appendTo(".room_list");
				}
			
			
				$("li").mousedown(function(e) {
					$(document).bind("contextmenu",
					    function(){
					        return false;
					    }
					);
					
					if (e.which == 3) {
						  var mousePos = mousePosition(e);  
					        var  xOffset = 20;  
					        var  yOffset = 25; 
				        	$(".cleanroom").hide();
					        $(".dirtyroom").show();
					        $("#dirtyroomid").val($(this).children("span:first-child").html());
					        $("#thischeckid").val($(this).children("span:nth-child(2)").html());
					        $(".dirtyroom").css("display","block").css("position","absolute")
					        		.css("top",(mousePos.y - yOffset) + "px").css("left",(mousePos.x + xOffset) + "px"); 
					}
				})
				
				$("li").dblclick(function(e) {
						var checknumber = $(this).children("span:nth-child(2)").html()
						var which = 'check';
						window.JDialog.openWithNoTitle("",base_path +'/page/ipmspage/room_statistics/turntoroomlistcheck.jsp?checkid=' + checknumber + '&type=' + which, 1179, 733);
				});
			} 
		}
	})
}

function queryroom(status) {
	var roomtype = $("#dropse_roomtype").val();
	var roomfloor = $("#dropse_floor").val();
	
	$.ajax({
		url:base_path + "/queryRoomByStatus.do",
		type:"post",
		dataType:"json",
		data: { status: status, roomtype:roomtype, roomfloor:roomfloor },
		success:function(json) {
			if(json.length > 0) {
				$(".room_list").html(" ");
				for (i=0;i<json.length;i++) {
					if (json[i].STATUS == '0') {
						$("<li><span>" + json[i].ROOMID + "</span><span>" + json[i].ROOMTYPE+ "</span><span style='display:none'>" + json[i].STATUS+ "</span>").appendTo(".room_list");
					} else if (json[i].STATUS == 'Z') {
						$("<li class='dirty'><span>" + json[i].ROOMID + "</span><span>" + json[i].ROOMTYPE + "</span><span style='display:none'>" + json[i].STATUS + "</span>").appendTo(".room_list");
					} else if (json[i].STATUS == 'T') {
						$("<li class='stopsell'><span>" + json[i].ROOMID + "</span><span>" + json[i].ROOMTYPE + "</span><span style='display:none'>" + json[i].STATUS+ "</span>").appendTo(".room_list");
					}
				}
				$("li").mousedown(function(e) {
					$(document).bind("contextmenu",
					    function(){
					        return false;
					    }
					);
					
					if (e.which == 3) {
						  var mousePos = mousePosition(e);  
					        var  xOffset = 20;  
					        var  yOffset = 25; 
				        	$(".cleanroom").show();
					        $(".dirtyroom").hide();
					        $("#cleanroomid").val($(this).children("span:first-child").html());
					        $(".cleanroom").css("display","block").css("position","absolute")
					        		.css("top",(mousePos.y - yOffset) + "px").css("left",(mousePos.x + xOffset) + "px");  
					}
				})
				
				$("li").dblclick(function(e) {
					if ($(this).children("span:last-child").html() == '0') {
						JDialog.openWithNoTitle("", base_path + "/orderNew.do",940,704);
					} else if ($(this).children("span:last-child").html() == 'Z') {
						showMsg("此房为脏房!");
					} else if ($(this).children("span:last-child").html() == 'T') {
						showMsg("此房为停售房!");
					}
				});
			}
		}
	})
}

$("#dropse_roomtype").change(function(){
	var roomType = $("#dropse_roomtype").val();
	var roomfloor = $("#dropse_floor").val();
	var label = $(".roomstatus").find(".active").find("span").html();
	
	$.ajax({
		url:base_path + "/queryByRoomType.do",
		type:"post",
		dataType:"json",
		data: { roomType: roomType, roomfloor:roomfloor, label:label },
		success:function(json) {
			if(json.length > 0) {
				$(".room_list").html(" ");
				for (i=0;i<json.length;i++) {
					if (json[i].STATUS == '0') {
						$("<li><span>"+ json[i].ROOMID + "</span><span style='display:none'>" + json[i].CHEID +"</span><span>" + json[i].ROOMTYPE + "</span>" +
								"<span style='display:none;'>" + json[i].STATUS + "</span>").appendTo(".room_list");
					} else if (json[i].STATUS == '1') {
						$("<li class='order'><span>" + json[i].ROOMID + "</span><span style='display:none'>" + json[i].CHEID +"</span><span>" + json[i].ROOMTYPE + "</span><span>" 
								+json[i].CHECKUSER + "</span><span style='display:none;'>" + json[i].STATUS + "</span>").appendTo(".room_list");
					} else if (json[i].STATUS == '2') {
						$("<li class='live'><span>" + json[i].ROOMID + "</span><span style='display:none'>" + json[i].CHEID +"</span><span>" + json[i].ROOMTYPE + "</span><span>" 
								+json[i].CHECKUSER + "</span><span style='display:none;'>" + json[i].STATUS + "</span>").appendTo(".room_list");
					} else if (json[i].STATUS == 'T') {
						$("<li class='stopsell'><span>" + json[i].ROOMID + "</span><span style='display:none'>" + json[i].CHEID +"</span><span>" + json[i].ROOMTYPE + "</span>" +
								"<span style='display:none;'>" + json[i].STATUS + "</span>").appendTo(".room_list");
					} else if (json[i].STATUS == 'Z') {
						$("<li class='dirty'><span>" + json[i].ROOMID + "</span><span style='display:none'>" + json[i].CHEID +"</span><span>" + json[i].ROOMTYPE + "</span>" +
								"<span style='display:none;'>" + json[i].STATUS + "</span>").appendTo(".room_list");
					} else if (json[i].STATUS == 'W') {
						$("<li class='maintance'><span>" + json[i].ROOMID + "</span><span style='display:none'>" + json[i].CHEID +"</span><span>" + json[i].ROOMTYPE + "</span>" +
								"<span style='display:none;'>" + json[i].STATUS + "</span>").appendTo(".room_list");
					} else if (json[i].STATUS == 'O') {
						$("<li class='other'><span>" + json[i].ROOMID + "</span><span style='display:none'>" + json[i].CHEID +"</span><span>" + json[i].ROOMTYPE + "</span><span style='display:none;'>" + json[i].STATUS + "</span>").appendTo(".room_list");
					}
				}
			
			
				$("li").mousedown(function(e) {
					$(document).bind("contextmenu",
					    function(){
					        return false;
					    }
					);
					
					if (e.which == 3) {
						  var mousePos = mousePosition(e);  
					        var  xOffset = 20;  
					        var  yOffset = 25; 
					        if ($(this).children("span:last-child").html() == '0' || 
					        		$(this).children("span:last-child").html() == 'Z') {
					        	$(".dirtyroom").hide();
						        $(".cleanroom").show();
						        $("#cleanroomid").val($(this).children("span:first-child").html());
						        $(".cleanroom").css("display","block").css("position","absolute")
						        		.css("top",(mousePos.y - yOffset) + "px").css("left",(mousePos.x + xOffset) + "px");  
					        } else if ($(this).children("span:last-child").html() == '2') {
					        	$(".cleanroom").hide();
						        $(".dirtyroom").show();
						       // alert($(this).children("span:first-child").html());
						        $("#dirtyroomid").val($(this).children("span:first-child").html());
						        $("#thischeckid").val($(this).children("span:nth-child(2)").html());
						        $(".dirtyroom").css("display","block").css("position","absolute")
						        		.css("top",(mousePos.y - yOffset) + "px").css("left",(mousePos.x + xOffset) + "px");  
					        } else {
					        	$(".stoproom").show();
								$(".cleanroom").hide(); 
								$(".dirtyroom").hide();
								$("#cleanroomid").val($(this).children("span:first-child").html());
						        $(".stoproom").css("display","block").css("position","absolute")
				        		.css("top",(mousePos.y - yOffset) + "px").css("left",(mousePos.x + xOffset) + "px");  
					       }
					}
				})
				
				$("li").dblclick(function(e) {
					if ($(this).children("span:last-child").html() == '0') {
						JDialog.openWithNoTitle("", base_path + "/orderNew.do",940,704);
					} else if ($(this).children("span:last-child").html() == '2') {
						var checknumber = $(this).children("span:nth-child(2)").html()
						var which = 'check';
						window.JDialog.openWithNoTitle("",base_path +'/page/ipmspage/room_statistics/turntoroomlistcheck.jsp?checkid=' + checknumber + '&type=' + which, 1179, 733);
					} else if ($(this).children("span:last-child").html() == 'W') {
						showMsg("此房为维修房!");
					} else if ($(this).children("span:last-child").html() == 'T') {
						showMsg("此房为停售房!");
					} else if ($(this).children("span:last-child").html() == 'Z') {
						showMsg("此房为脏房!");
					}
				});
			} 
		}
	})
})

$("#dropse_floor").change(function(){
	var roomType = $("#dropse_roomtype").val();
	var roomfloor = $("#dropse_floor").val();
	var label = $(".roomstatus").find(".active").find("span").html();
	
	if (label != "空净房" || label != "脏房" || label != "停售" || label != "所有") {
		label = $(".roomstatus").find(".active").find("span").text();
	}
	$.ajax({
		url:base_path + "/queryByRoomFloor.do",
		type:"post",
		dataType:"json",
		data: { roomFloor: roomfloor, roomtype:roomType, label:label },
		success:function(json) {
			if(json.roomlist.length) {
				$(".room_list").html(" ");
				for (i=0;i<json.roomlist.length;i++) {
					if (json.roomlist[i].STATUS == '0') {
						$("<li><span>" + json.roomlist[i].ROOMID + "</span><span>" + json.roomlist[i].ROOMTYPE + "</span>" +
								"<span style='display:none;'>" + json.roomlist[i].STATUS + "</span>").appendTo(".room_list");
					} else if (json.roomlist[i].STATUS == '1') {
						$("<li class='order'><span>" + json.roomlist[i].ROOMID + "</span><span>" + json.roomlist[i].ROOMTYPE + "</span>" +
								"<span style='display:none;'>" + json.roomlist[i].STATUS + "</span>").appendTo(".room_list");
					} else if (json.roomlist[i].STATUS == '2') {
						$("<li class='live'><span>" + json.roomlist[i].ROOMID + "</span><span>" + json.roomlist[i].ROOMTYPE + "</span><span>" 
								+json.roomlist[i].CHECKUSER + "</span><span style='display:none;'>" + json.roomlist[i].STATUS + "</span>").appendTo(".room_list");
					} else if (json.roomlist[i].STATUS == 'T') {
						$("<li class='stopsell'><span>" + json.roomlist[i].ROOMID + "</span><span>" + json.roomlist[i].ROOMTYPE + "</span>" +
								"<span style='display:none;'>" + json.roomlist[i].STATUS + "</span>").appendTo(".room_list");
					} else if (json.roomlist[i].STATUS == 'Z') {
						$("<li class='dirty'><span>" + json.roomlist[i].ROOMID + "</span><span>" + json.roomlist[i].ROOMTYPE + "</span>" +
								"<span style='display:none;'>" + json.roomlist[i].STATUS + "</span>").appendTo(".room_list");
					} else if (json.roomlist[i].STATUS == 'W') {
						$("<li class='maintance'><span>" + json.roomlist[i].ROOMID + "</span><span>" + json.roomlist[i].ROOMTYPE + "</span>" +
								"<span style='display:none;'>" + json.roomlist[i].STATUS + "</span>").appendTo(".room_list");
					} else if (json.roomlist[i].STATUS == 'O') {
						$("<li class='other'><span>" + json.roomlist[i].ROOMID + "</span><span>" + json.roomlist[i].ROOMTYPE + "</span>" +
								"<span style='display:none;'>" + json.roomlist[i].STATUS + "</span>").appendTo(".room_list");
					}
				}
				$(".nine").text(json.roomlist.length);
			
				$("li").mousedown(function(e) {
					$(document).bind("contextmenu",
					    function(){
					        return false;
					    }
					);
					
					if (e.which == 3) {
						  var mousePos = mousePosition(e);  
					        var  xOffset = 20;  
					        var  yOffset = 25; 
					        if ($(this).children("span:last-child").html() == '0' || 
					        		$(this).children("span:last-child").html() == 'Z') {
					        	$(".dirtyroom").hide();
						        $(".cleanroom").show();
						        $("#cleanroomid").val($(this).children("span:first-child").html());
						        $(".cleanroom").css("display","block").css("position","absolute")
						        		.css("top",(mousePos.y - yOffset) + "px").css("left",(mousePos.x + xOffset) + "px");  
					        } else if ($(this).children("span:last-child").html() == '2') {
					        	$(".cleanroom").hide();
						        $(".dirtyroom").show();
						        $("#dirtyroomid").val($(this).children("span:first-child").html());
						        $("#thischeckid").val($(this).children("span:nth-child(2)").html());
						        $(".dirtyroom").css("display","block").css("position","absolute")
						        		.css("top",(mousePos.y - yOffset) + "px").css("left",(mousePos.x + xOffset) + "px");  
					        } else {
					        	$(".stoproom").show();
								$(".cleanroom").hide(); 
								$(".dirtyroom").hide();
								$("#cleanroomid").val($(this).children("span:first-child").html());
						        $(".stoproom").css("display","block").css("position","absolute")
				        		.css("top",(mousePos.y - yOffset) + "px").css("left",(mousePos.x + xOffset) + "px");  
					       }
					}
				})
				
				$("li").dblclick(function(e) {
					if ($(this).children("span:last-child").html() == '0') {
						JDialog.openWithNoTitle("", base_path + "/orderNew.do",940,704);
					} else if ($(this).children("span:last-child").html() == '2') {
						var checknumber = $(this).children("span:nth-child(2)").html()
						var which = 'check';
						window.JDialog.openWithNoTitle("",base_path +'/page/ipmspage/room_statistics/turntoroomlistcheck.jsp?checkid=' + checknumber + '&type=' + which, 1179, 733);
					} else if ($(this).children("span:last-child").html() == 'W') {
						showMsg("此房为维修房!");
					} else if ($(this).children("span:last-child").html() == 'T') {
						showMsg("此房为停售房!");
					} else if ($(this).children("span:last-child").html() == 'Z') {
						showMsg("此房为脏房!");
					}
				});
			} 	
		}
	})
})


if($("#roomId").focus){
	$("#roomId").keydown(function(e){
		 var key = e.which;
         if (key == 13) {
        	e.preventDefault ? e.preventDefault() : (e.returnValue = false);
        	
			$.ajax({
				url:base_path + "/queryByRoomId.do",
				type:"post",
				dataType:"json",
				data: { roomId: $("#roomId").val() },
				success:function(json) {
					if(json.length > 0) {
						$(".room_list").html(" ");
						for (i=0;i<json.length;i++) {
							if (json[i].STATUS == '0') {
								$("<li><span>"+ json[i].ROOMID + "</span><span>" + json[i].ROOMTYPE + "</span><span>" 
										+json[i].CHECKUSER + "</span>").appendTo(".room_list");
							} else if (json[i].STATUS == '1') {
								$("<li class='order'><span>" + json[i].ROOMID + "</span><span>" + json[i].ROOMTYPE + "</span><span>" 
										+json[i].CHECKUSER + "</span>").appendTo(".room_list");
							} else if (json[i].STATUS == '2') {
								$("<li class='live'><span>" + json[i].ROOMID + "</span><span>" + json[i].ROOMTYPE + "</span><span>" 
										+json[i].CHECKUSER + "</span>").appendTo(".room_list");
							} else if (json[i].STATUS == 'T') {
								$("<li class='stopsell'><span>" + json[i].ROOMID + "</span><span>" + json[i].ROOMTYPE + "</span><span>" 
										+json[i].CHECKUSER + "</span>").appendTo(".room_list");
							} else if (json[i].STATUS == 'Z') {
								$("<li class='dirty'><span>" + json[i].ROOMID + "</span><span>" + json[i].ROOMTYPE + "</span><span>" 
										+json[i].CHECKUSER + "</span>").appendTo(".room_list");
							} else if (json[i].STATUS == 'W') {
								$("<li class='maintance'><span>" + json[i].ROOMID + "</span><span>" + json[i].ROOMTYPE + "</span><span>" 
										+json[i].CHECKUSER + "</span>").appendTo(".room_list");
							} else if (json[i].STATUS == 'O') {
								$("<li class='other'><span>" + json[i].ROOMID + "</span><span>" + json[i].ROOMTYPE + "</span><span>" 
									+json[i].CHECKUSER + "</span>").appendTo(".room_list");
							}
						}
					} 
				}
			})
         }
	})
}


function mousePosition(ev){   
    ev = ev || window.event;   
    if(ev.pageX || ev.pageY){   
        return {x:ev.pageX, y:ev.pageY};   
    }   
    return {   
        x:ev.clientX + document.body.scrollLeft - document.body.clientLeft,   
        y:ev.clientY + document.body.scrollTop - document.body.clientTop   
    };   
}  


function setroom(roomstatus) {
	var roomId;
	if (roomstatus == '0' || roomstatus == 'T') {
		roomId = $("#cleanroomid").val();
	} else {
		roomId = $("#dirtyroomid").val();
	}
	
	$.ajax({
		url: base_path + "/setRoomStatus.do",
		type: "post",
		dataType: "json",
		data: { roomstatus: roomstatus, roomId : roomId},
		success: function(json) {
			if (json.result == 1) {
				showMsg(json.message);
				setTimeout("location.reload()",1800);
			} else if (json.result == 0) {
				showMsg(json.message);
			}
		},
		error: function(json) {
			showMsg("操作失败！");
		}
	});	
}

function turnto(which) {
	var roomId = $("#dirtyroomid").val();
	var checknumber = $("#thischeckid").val();
	if (which) {
		window.JDialog.openWithNoTitle("",base_path +'/page/ipmspage/room_statistics/turntoroomlistcheck.jsp?checkid=' + checknumber + '&type=' + which, 1179, 733);
	} 
}

function refresh(){
	location.reload();
}

$(".room_list").mousedown(function(e) {
	if (e.which == 1) {
		$(".vzroom").hide();
		$(".stoproom").hide();
		$(".cleanroom").hide(); 
		$(".dirtyroom").hide();
	}
});

function showMsg(msg, fn) {
	if (!fn) {
		TipInfo.Msg.alert(msg);
	} else {
		TipInfo.Msg.confirm(msg, fn);
	}
}

function getRoomType() {
	$.ajax({
        url: base_path + "/loadsearchroomtype.do",
		 type: "post",
		 data : {},
		 success: function(json) {
		 	var data = "<option value=''>房间类型</option>";
		 	$.each(json,function(index){
		 		data = data + "<option value='" +json[index]["ROOMTYPE"] + "'>" +json[index]["ROOMNAME"] + "</option>"
		 	});
		 	$("#dropse_roomtype").html(data);
			$(".mySelect").select({
				width: "175px"
			});
		 },
		 error: function(json) {}
	});
}

function getRoomFloor() {
	$.ajax({
        url: base_path + "/loadRoomFloor.do",
		 type: "post",
		 data : {},
		 success: function(json) {
		 	var data = "<option value=''>所有楼层</option>";
		 	$.each(json,function(index){
		 		data = data + "<option value='" +json[index]["FLOOR"] + "'>" +json[index]["FLOOR"] + "</option>"
		 	});
		 	$("#dropse_floor").html(data);
			$(".mySelect").select({
				width: "175px"
			});
		 },
		 error: function(json) {}
	});
}