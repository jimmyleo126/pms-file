


function showMsg(msg, fn) {
	if (!fn) {
	TipInfo.Msg.alert(msg);
	   } else {
	TipInfo.Msg.confirm(msg, fn);
	  }
	}



$("input[name='gdsale']").bind("click",function() {
	    var saletype = "singlesale";
         var gdsid = $(this).parent().parent().children("td").eq(1).html();
         var gdsname = $(this).parent().parent().children("td").eq(2).html();
         var gdsprice = $(this).parent().parent().children("td").eq(4).html();
         JDialog.open("商品售卖", base_path + "/gdsPage.do?gdsid="+gdsid+"&gdsname="+gdsname+"&gdsprice="+gdsprice+"&saletype="+saletype, 750, 420);
    })
    
    
    
    
     var salecontent = new Array(); 
		$("input[name='gdscheckbox']").bind("click",function(){
			 var gdsid = $(this).parent().parent().children("td").eq(1).html();
	         //var gdsname = $(this).parent().parent().children("td").eq(2).html();
	         //var gdsprice = $(this).parent().parent().children("td").eq(4).html(); 
	         //var list = {"gdsid": gdsid};
		     if($(this).is(':checked')){  
                  content.push(gdsid);
                }else{
                   for (var i = 0;i < content.length; i++) { 
                   var s = content[i];
			       if (s == gdsid) {   
			       content.splice(i,1);
			       i--; 
			      } 
			    }
              }
		   });
		function gdsmanyearch(){
			//alert("被选中的td的总长度："+content.length)
			if(!content.length){
				showMsg("请至少选择一个商品！")
			}else{
				
		 var saletype = "multiplesale";
		var newarr = new Array();
		var arrstr = '[';
		$.each(content, function(index){
			arrstr = arrstr + JSON.stringify(content[index]) + ',';
		})
		var newstr = arrstr.substring(0, arrstr.length-1);
		newstr = newstr + ']';
		   JDialog.open("商品售卖", base_path + "/gdsmulPage.do?newstr="+newstr+"&saletype="+saletype, 750, 420);
		}
	}