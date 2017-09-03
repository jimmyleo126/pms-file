$(".info_write ul li").on("click",function(){
				$(this).addClass("active");
				$(this).siblings().removeClass("active");
			});
			$(".detail_four .look_order").on("click",function(){
				window.location.href="<%=request.getContextPath()%>/page/ipmspage/order/order_check.jsp";
				
			});
			
			function showMsg(msg, fn) {
				if (!fn) {
					TipInfo.Msg.alert(msg);
				} else {
					TipInfo.Msg.confirm(msg, fn);
				}
			}
			function submitjudge(){
				window.parent.$("#memberId").val($("#memberid").val());
				window.parent.$("#memberrank").val($("#memberrank").val());
				window.parent.$("#userorder").val($("#membername").val());
				window.parent.$("#ordemobile").val($("#membermobile").val());
				window.parent.$("#usercheckin").val($("#membername").val());
				window.parent.$("#usermobile").val($("#membermobile").val());
				window.parent.$("#roomprice").val($("#rp").val());
				var timeid = "#"+$("#rpid").val();
				window.parent.$("#limited").val($(timeid).val());
				window.parent.JDialog.close();
			}
			
			function canceljudge(){
			//window.parent.$("#memberId").val($("#memberid").val());
			////window.parent.$("#userorder").val($("#membername").val());
			//window.parent.$("#ordemobile").val($("#membermobile").val());
			//window.parent.$("#roomprice").val($("#rp").val());
			//var timeid = "#"+$("#rpid").val();
			//window.parent.$("#limited").val($(timeid).val());
			window.parent.JDialog.close();
			}