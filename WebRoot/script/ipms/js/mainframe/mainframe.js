
	$(function() {
		$("#contentFrame_first").click();
		/*点击*/
			$(".mark").on("click",function(){
				$(this).css("display","none");
			});	
			/*左侧侧边栏*/
			$(".box_left_sliderbar").on({
				mouseover:function(){
					$(".nav_span").css("display","block");
				},
				mouseout:function(){
					$(".nav_span").css("display","none");
				 }
			});
			/*主页面头部点击图片更换事件
			var _li=$(".header-toggle ul li");
			var img=$(".header-toggle ul li img");
			var image_url_origin = ["room_status.png","count.png","city.png","order.png", "icon_room.png", "workaccout.png", 
							"stop_sell.png","log.png","report.png", "statistic.png"];
			var image_url = ["room_status_two.png","count_two.png","city_two.png","order_two.png",
							"icon_room_two.png", "workaccout_two.png", "stop_sell_two.png","log_two.png",
							"report_two.png", "statistic_two.png"];
			var tempLi;
			/*$(".header-toggle ul li").on("click",function(e){
				$(this).addClass("active");
				$(this).siblings().removeClass("active");
				
				if(tempLi) {
					tempLi.find("img").attr("src", img_url + image_url_origin[tempLi.attr("index")])
				}
				$(this).find("img").attr("src", img_url + image_url[$(this).attr("index")]);
				tempLi = $(this);
			});*/
			
		});
		var _li=$(".header-toggle ul li");
		var img=$(".header-toggle ul li img");
		var image_url_origin = ["room_status.png","count.png","city.png","order.png", "icon_room.png", "workaccout.png", 
		                        "stop_sell.png","log.png","report.png", "statistic.png"];
		var image_url = ["room_status_two.png","count_two.png","city_two.png","order_two.png",
		                 "icon_room_two.png", "workaccout_two.png", "stop_sell_two.png","log_two.png",
		                 "report_two.png", "statistic_two.png"];
		var tempLi;
		function changeimage(e){
			$(e).addClass("active");
			$(e).siblings().removeClass("active");
			
			if(tempLi) {
				tempLi.find("img").attr("src", img_url + image_url_origin[tempLi.attr("index")])
			}
			$(e).find("img").attr("src", img_url + image_url[$(e).attr("index")]);
			tempLi = $(e);
		}
		/*侧边栏滑动隐藏*/
		function switchSysBar(){
			var switchPoint=$("#switchPoint");
			var left_menu=$("#left_menu");
			var icon=$(".picBox span");
			if(1 == window.status){
				window.status = 0;
				left_menu.hide();
				if(icon.attr('class')=="fa fa-chevron-left"){
					icon.removeClass("fa-chevron-left");
					icon.addClass("fa-chevron-right");
				}else{
					icon.addClass("fa-chevron-left");
					icon.removeClass("fa-chevron-right");
				}
				 left_menu.css({
					 "transition-duration": "0.3s",
					 "transition":"all 0.3s ease-in-out",
					 "transform":"translate3d(-100,0,0)",
					 "left":"-100px"
				});
				 $(".header-toggle").addClass("one");
				 $(".header_toggle_ul").addClass("two");
			}
			else{
				window.status = 1;
		        left_menu.show(500);
		        left_menu.css({
					 "transition-duration": "0.3s",
					 "transition":"all 0.3s ease-in-out",
					 "left":"0"	
				});
		        $(".header-toggle").removeClass("one");
		        $(".header_toggle_ul").removeClass("two");
			}
		}
		function changeUrl(e){
			console.log(e)
			changeimage(e);
			document.getElementById("st-scroll").innerHTML='<iframe src="' + base_path +'/page/ipmspage/room_status/room_status.jsp" width="100%" height="100%" frameborder=no  scrolling="no"></iframe>'
			//console.log('<iframe src="' + base_path +'/page/ipmspage/room_status/room_status.jsp" width="100%" height="100%" frameborder=no  scrolling="no"></iframe>')
		}
		function changeUrlone(e){
			changeimage(e);
			document.getElementById("st-scroll").innerHTML='<iframe src="' + base_path +'/page/ipmspage/room_statistics/room_statistics.jsp" width="100%" height="100%" frameborder=no  scrolling="no"></iframe>'
		}
		function changeUrltwo(e){
			changeimage(e);
			document.getElementById("st-scroll").innerHTML='<iframe src="' + base_path +'/queryRoom.do" width="100%" height="100%" frameborder=no  scrolling="no"></iframe>'
		}
		function changeUrlthree(e){
			changeimage(e);
			document.getElementById("st-scroll").innerHTML='<iframe src="' + base_path +'/page/ipmspage/order/order.jsp" width="100%" height="100%" frameborder=no  scrolling="no"></iframe>'
		}
		function changeUrlfour(e){
			changeimage(e);
			document.getElementById("st-scroll").innerHTML='<iframe src="' + base_path +'/page/ipmspage/room_statistics/roomlist.jsp" width="100%" height="100%" frameborder=no  scrolling="no"></iframe>'
		}
		function changeUrlfive(e){
			changeimage(e);
			document.getElementById("st-scroll").innerHTML='<iframe src="' + base_path +'/page/ipmspage/work_account/work_account.jsp" width="100%" height="100%" frameborder=no  scrolling="no"></iframe>'
		}	
		function changeUrlsix(e){
			changeimage(e);
			document.getElementById("st-scroll").innerHTML='<iframe src="' + base_path +'/page/ipmspage/stophouse/stopsellhouse.jsp" width="100%" height="100%" frameborder=no  scrolling="no"></iframe>'
		}
		function reportForms(e){
			changeimage(e);
			document.getElementById("st-scroll").innerHTML='<iframe src="' + base_path +'/page/ipmspage/reportform/reportForms.jsp" width="100%" height="100%" frameborder=no  scrolling="no"></iframe>'
		}
		function changeIntegral(e){
			changeimage(e);
			document.getElementById("st-scroll").innerHTML='<iframe src="' + base_path +'/page/ipmspage/integral/integral.jsp" width="100%" height="100%" frameborder=no  scrolling="no"></iframe>'
		}
		/*预定*/
		function orderNew(){
			JDialog.openWithNoTitle("", base_path + "/orderNew.do",940,704);
		}
			
	    function orderSerach(){
			 JDialog.openWithNoTitle("", base_path + "/orderSerach.do",1280,780);
		}
		/*日结异常*/
	    function exceptionSearch(){
			 JDialog.openWithNoTitle("", base_path + "/exceptionSerach.do",1280,779);
		}
	    
		function goods(){
			 JDialog.openWithNoTitle("", base_path + "/goods.do",1280,738);
		}
		
		function memberSearch(){
			 JDialog.openWithNoTitle("", base_path + "/memberSearch.do",1000,641);
		}
		/*备用金*/
		function pettyCash(){
			//JDialog.openWithNoTitle("", base_path + "/pettyCash.do",1088,776);
			$.ajax( {
				url : base_path + "/cashCount.do",
				type : "get",
				success : function(cashmap) {
				var cashcountin = cashmap.cashin;
				var cashcountout = cashmap.cashout;
				var cardcount = cashmap.card;
				var cards = cashmap.cards;
				var boxname = cashmap.boxname;
				var boxcount = cashmap.boxcount;
				var shift = cashmap.shift;
				var shifterid = cashmap.shifterid;
				var shiftername = cashmap.shiftername;
				var branchname = cashmap.branchname;
				var shiftname = cashmap.shiftname;
				var lastshiftvalue = cashmap.lastshiftvalue;
				    JDialog.openWithNoTitle("", base_path + "/cashShift.do?cashcountin="+cashcountin+"&cashcountout="+cashcountout+"&cardcount="+cardcount+"&cards="+cards+"&boxname="+boxname+"&boxcount="+boxcount+"&shift="+shift+"&shifterid="+shifterid+"&shiftername="+shiftername+"&branchname="+branchname+"&shiftname="+shiftname+"&lastshiftvalue="+lastshiftvalue,1088,776);
					//JDialog.openWithNoTitle("", base_path + "/page/ipmspage/leftmenu/pettycash/cashregister.jsp?cashcount="+cashcount+"&cardcount="+cardcount+"&boxname="+boxname+"&boxcount="+boxcount+"&shift="+shift+"&shifterid="+shifterid+"&shiftername="+shiftername+"&branchname="+branchname+"&shiftname="+shiftname+"&lastshiftvalue="+lastshiftvalue,1088,776);
				},
				error : function() {
				}
				
			});
			
		}
		/*申请处理*/
		function handleApply(){
			JDialog.openWithNoTitle("",base_path + "/handleApply.do",1280,738);
		}
		function apartmentReserved(){
			 JDialog.openWithNoTitle("", base_path + "/apartmentReserved.do",1280,780);
		}
		function apartmentCheckOut(){
			 JDialog.openWithNoTitle("", base_path + "/apartmentCheckOut.do",1280,780);
		}
		function apartmentRent(){
			 JDialog.openWithNoTitle("", base_path + "/apartmentRent.do",1280,780);
		}
		
		/*处理公寓维修*/
		function repair(){
			JDialog.openWithNoTitle("",base_path+"/repair.do",1280,704);
		}
		/*侧边快捷键*/
		document.onkeydown=function(event){
            var e = event || window.event || arguments.callee.caller.arguments[0];          
             if(e && e.keyCode==112){ 
            	 JDialog.openWithNoTitle("", base_path + "/orderSerach.do",1280,779);  
            }else if(e && e.keyCode==113){
            	JDialog.openWithNoTitle("", base_path + "/orderNew.do",940,704);
            }else if(e && e.keyCode==114){
            	 JDialog.openWithNoTitle("", base_path + "/goods.do",1280,738);
            }else if(e && e.keyCode==115){
            	 JDialog.openWithNoTitle("", base_path + "/memberSearch.do",1000,641);
            }else if(e && e.keyCode==116){
            	 JDialog.openWithNoTitle("", base_path + "/payUpGradeMemberLevelInPms.do",800,700);
            }else if(e && e.keyCode==117){
            	JDialog.openWithNoTitle("", base_path + "/exceptionSerach.do",1280,800);
            }else if(e && e.keyCode==118){
            	JDialog.openWithNoTitle("", base_path + "/pettyCash.do",1120,780);
            }else if(e && e.keyCode==121){
            	window.location.href = base_path +"/loadMainFrame.do";
            }
        }; 
		//会员购卡升级
        function memberAddLevel(){
        	JDialog.openWithNoTitle("", base_path + "/payUpGradeMemberLevelInPms.do",800,700);
        }
        
 
       	
		
		
		
		
		
		