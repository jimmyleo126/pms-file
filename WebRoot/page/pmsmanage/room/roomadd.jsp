<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/taglib.jsp"%>
<%@ include file="../../common/css.jsp"%>
<%@ include file="../../common/script.jsp"%>
<% request.setAttribute("basePath", request.getContextPath()); 
   request.setAttribute("rpbranchid", request.getAttribute("rpbranchid"));
   request.setAttribute("rptheme", request.getAttribute("rptheme"));
   request.setAttribute("rproomtype", request.getAttribute("rproomtype"));
   request.setAttribute("rpstatus", request.getAttribute("rpstatus"));
   request.setAttribute("branchId", request.getAttribute("branchId"));
   request.setAttribute("theme", request.getAttribute("theme"));
  %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>添加记录</title>
	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="cache-control" content="no-cache"/>
	<meta http-equiv="expires" content="0"/>    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
	<meta http-equiv="description" content="This is my page"/>
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/datepicker.css"/>	
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<style>
	.ctdform{
	width: 500px;
	height: 354px;
	overflow-x: hidden;
	overflow-y: auto;
	position: relative;
	margin:0;padding:0;
	}
	.ptable{
	height:61px;
	font-size:14px;
	width: 100%;
	margin-bottom: 0;
	}
	.ctdtitle{
	vertical-align:middle;
	border:0 none;
	padding:2px;
	white-space:nowrap;
	text-align:center;
	}
	.notnull{
	 color:red;
	}
	.ptdinput{
	 width: 402px;
	 height: 36px;
	 border-bottom-right-radius:6px;
	 border-bottom-left-radius:6px;
	 border-top-right-radius:6px;
	 border-top-left-radius:6px;
	 border: 1px solid #dddddd;
	 background-color: white;
	}
	.pinputtd{
	padding: 2px;
	border: 0 none;
	vertical-align: top;
	}
	.ctdhr{
	border: 1px solid #dddddd;
	background: #f2f5f7 url(images/ui-bg_highlight-hard_100_f2f5f7_1x100.png) 50% top repeat-x;
	color: #362b36;
	margin:5px 1px 1px 1px;
	}
	.pdepartsubmit{
	float:left;
	width: 100px;
    height: 30px;
   background-color: #52B2E3;
    border-radius: 3px;
    text-align: center;
    padding: 3px;
    margin: 3px;
    line-height: 30px;
    font-size: 15px;
    font-family: Microsoft YaHei;
    color: #fff;
    font-weight: normal;
    cursor: pointer;
	}
	.pdepartsubmit:hover,.pdepartcancle:hover{
		font-weight:bold;
	}
	.pdepartcancle{
	float:left;
	width: 100px;
    height: 30px;
    background-color: #f60;
    border-radius: 3px;
    text-align: center;
    padding: 3px;
    margin: 3px;
    line-height: 30px;
    font-size: 15px;
    font-family: Microsoft YaHei;
    color: #fff;
    font-weight: normal;
    cursor: pointer;
	}
	
	.contractbutton{
	margin-top:100px;
	float: right;
    width: 251px;
    height: 50px;
    text-align: center;
    border-radius: 5px;
    margin-top:8px;
	}
	.ptime{
	 width: 402px;
	 height: 36px;
	 border-bottom-right-radius:6px;
	 border-bottom-left-radius:6px;
	 border-top-right-radius:6px;
	 border-top-left-radius:6px;
	 border: 1px solid #dddddd;
	 background-color: white;
	}
	</style>
	<script> 
	var base_path = '<%=request.getContextPath()%>';
	var projectid = '${ projectid }';
	</script>
  </head>  
  <body>
     <input id="oldtheme" name="themeid" type="hidden" value="${theme}"/>
	 <input id="oldbranchid" name="branchid" type="hidden" value="${branchId}"/>
     <form  name="myFrom" id="myForm" class="ctdform">
		 <table class="ptable" border="0" cellspacing="0" cellpadding="0">
		    <tbody>
		    <col width="13%;">
		     <col width="80%;">
		     <col width="2%;">
		     <col width="2%;">
		     <tr>
				   <td style="height:5px;"></td>
				   <td></td>
				   <td class="ctdtitle"></td>
				   <td class="ctdtitle"></td>
				</tr>
				<tr id="trprojectid">
				   <td class="ctdtitle notnull" align="center;">场景</td>
				   <td align="left" class="pinputtd">
					   <select id="rptheme" name="rptheme" class="ptdinput" onchange="thChange()">
								   <option value="">
									       请选择场景
								     </option>
							      <c:forEach var="rt" items="${rptheme}">
								    <option value="${rt.content}">
									   ${rt.paramname}
								     </option>
							      </c:forEach>
						         </select>
				   </td>
				   <td class="ctdtitle"> <input type="text" id="projectname" class="ptdinput" style="display:none;"/></td>
				   <td class="ctdtitle"></td>
				</tr>
				<tr>
				   <td class="ctdtitle notnull" align="center;">门店</td>
				   <td align="left" class="pinputtd">
				     <select id="branchid1" name="" class="ptdinput">
								  <option value="">
									       请选择门店
								     </option>
							      <c:forEach var="b" items="${rpbranchid}">
								    <c:set var="flagb" value="${b.branchtype}"></c:set>
		                            <c:if  test="${(flagb==1)}">
		                            <option value="<c:out value='${b.branchid}' />"/>
		                            <c:out value="${b.branchname}"/>
		                             </c:if>
                                    </option>
							      </c:forEach>
					  </select>
					  <select id="branchid2" name="" class="ptdinput" style="display:none">
								  <option value="">
									       请选择门店
								     </option>
							      <c:forEach var="b" items="${rpbranchid}">
								    <c:set var="flagb" value="${b.branchtype}"></c:set>
		                            <c:if  test="${(flagb==2)}">
		                            <option value="<c:out value='${b.branchid}' />"/>
		                            <c:out value="${b.branchname}"/>
		                             </c:if>
                                    </option>
							      </c:forEach>
					  </select>
					  <select id="branchid3" name="" class="ptdinput" style="display:none">
								  <option value="">
									       请选择门店
								     </option>
							      <c:forEach var="b" items="${rpbranchid}">
								    <c:set var="flagb" value="${b.branchtype}"></c:set>
		                            <c:if  test="${(flagb==3)}">
		                            <option value="<c:out value='${b.branchid}' />"/>
		                            <c:out value="${b.branchname}"/>
		                             </c:if>
                                    </option>
							      </c:forEach>
					  </select>
					  <!--<select id="rbbranchid" name="rbbranchid" class="ptdinput">
								   <option value="">
									     请选择门店
								     </option>
							      <c:forEach var="rb" items="${rpbranchid}">
								    <option value="${rb.branchid}">
									   ${rb.branchname}
								     </option>
							      </c:forEach>
					   </select>
				   --></td>
				   <td class="ctdtitle"> <input type="text" id="departid" class="ptdinput" style="display:none;"/></td>
				   <td class="ctdtitle"></td>
				</tr>
				<tr>
				   <td class="ctdtitle notnull" align="center;">房型</td>
				   <td align="left" class="pinputtd">
					  <select id="theme1" name="" class="ptdinput">
								  <option value="">
									       请选择房型
								     </option>
							      <c:forEach var="a" items="${rproomtype}">
								    <c:set var="flag" value="${a.theme}"></c:set>
		                            <c:if  test="${(flag==1)}">
		                            <option value="<c:out value='${a.roomtype}' />"/>
		                            <c:out value="${a.roomname}"/>
		                             </c:if>
                                    </option>
							      </c:forEach>
						         </select>
						         <select id="theme2" name="" class="ptdinput" style="display:none">
								  <option value="">
									       请选择房型
								     </option>
							      <c:forEach var="a" items="${rproomtype}">
								    <c:set var="flag" value="${a.theme}"></c:set>
		                            <c:if  test="${(flag==2)}">
		                            <option value="<c:out value='${a.roomtype}' />"/>
		                            <c:out value="${a.roomname}"/>
		                             </c:if>
                                    </option>
							      </c:forEach>
						         </select>
						         <select id="theme3" name="" class="ptdinput" style="display:none">
								  <option value="">
									       请选择房型
								     </option>
							      <c:forEach var="a" items="${rproomtype}">
								    <c:set var="flag" value="${a.theme}"></c:set>
		                            <c:if  test="${(flag==3)}">
		                            <option value="<c:out value='${a.roomtype}' />"/>
		                            <c:out value="${a.roomname}"/>
		                             </c:if>
                                    </option>
							      </c:forEach>
						         </select>
				   </td>
				   <td class="ctdtitle"> <input type="text" id="departid" class="ptdinput" style="display:none;"/></td>
				   <td class="ctdtitle"></td>
				</tr>
				<tr>
				   <td class="ctdtitle notnull" align="center">楼层</td>
				   <td align="left" class="pinputtd">
					   <input type="text" id="rpfloor" class="ptdinput"  onKeyUp="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" maxlength="2"/>
					   </select>
				   </td>
				   <td class="ctdtitle"></td>
				   <td class="ctdtitle"></td>
				</tr>
				<tr>
				   <td class="ctdtitle notnull" align="center;">房号</td>
				   <td align="left" class="pinputtd">
					   <input type="text" id="rproomid" class="ptdinput" onKeyUp="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" maxlength="6"/>
				   </td>
				   <td class="ctdtitle"> <input type="text" id="departid" class="ptdinput" style="display:none;" /></td>
				   <td class="ctdtitle"></td>
				</tr>
				<tr>
				   <td class="ctdtitle" align="center;">面积</td>
				   <td align="left" class="pinputtd">
					   <input type="text" id="rparea" class="ptdinput" onKeyUp="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" maxlength="3"/>
				   </td>
				   <td class="ctdtitle"> <input type="text" id="partypersonid" class="ptdinput" style="display:none;"/></td>
				   <td class="ctdtitle"></td>
				</tr>
			    <tr>
				   <td class="ctdtitle notnull" align="center;">状态</td>
				   <td align="left" class="pinputtd">
						         <select id="rpstatus1" name="" class="ptdinput">
						          <option value="0">
									    空
								     </option>
							      <c:forEach var="a" items="${rpstatus}">
								    <c:set var="flag" value="${a.orderno}"></c:set>
		                            <c:if  test="${(flag==null)||(flag==1)}">
		                            <option value="<c:out value='${a.content}' />"/>
		                            <c:out value="${a.paramname}"/>
		                             </c:if>
                                    </option>
							      </c:forEach>
						         </select>
						         <select id="rpstatus2" name="" class="ptdinput" style="display:none">
						         <option value="0">
									        空
								     </option>
							       <c:forEach var="a" items="${rpstatus}">
								    <c:set var="flag" value="${a.orderno}"></c:set>
		                            <c:if  test="${(flag==null)||(flag==2)}">
		                            <option value="<c:out value='${a.content}' />"/>
		                            <c:out value="${a.paramname}"/>
		                             </c:if>
                                    </option>
							      </c:forEach>
						         </select>
						         <select id="rpstatus3" name="" class="ptdinput" style="display:none">
						         <option value="0">
									          空
								     </option>
							      <c:forEach var="a" items="${rpstatus}">
								    <c:set var="flag" value="${a.orderno}"></c:set>
		                            <c:if  test="${(flag==null)||(flag==3)}">
		                            <option value="<c:out value='${a.content}' />"/>
		                            <c:out value="${a.paramname}"/>
		                             </c:if>
                                    </option>
							      </c:forEach>
						         </select>
				   </td>
				   <td class="ctdtitle"> <input type="text" id="departid" class="ptime" style="display:none;"/></td>
				   <td class="ctdtitle"></td>
				</tr>
				<tr>
				   <td class="ctdtitle" align="center;">备注</td>
				   <td align="left" class="pinputtd">
					   <input type="text" id="rpremark" class="ptdinput" />
				   </td>
				   <td class="ctdtitle"> <input type="text" id="departid" class="ptdinput" style="display:none;"/></td>
				   <td class="ctdtitle"></td>
				</tr>									
			 </tbody>
		 </table>			
	 </form>
	  <hr class="ctdhr"/>
	 <div class="contractbutton">
	 <div class="pdepartsubmit" onclick="roomadd()">提交</div>
	 <div class="pdepartcancle" onclick="window.parent.JDialog.close()">取消</div>
	 </div>
	   <script src="<%=request.getContextPath()%>/script/common/ui.datepicker.js"></script>
	   <script src="<%=request.getContextPath()%>/script/common/datepickerCN.js"></script>
	      <script>
	       $(document).ready(function(){
	        var themeid = $("#oldtheme").val();
	        var rptheme = themeid;
	             if(rptheme=="1"){
	                        $("#theme1").show();
	                        $("#theme2").hide();
	                        $("#theme2").val("");
	                        $("#theme3").hide();
	                        $("#theme3").val("");
	                        $("#branchid1").show();
	                        $("#branchid2").hide();
	                        $("#branchid2").val("");
	                        $("#branchid3").hide();
	                        $("#branchid3").val("");
	                        $("#rpstatus1").show();
	                        $("#rpstatus2").hide();
	                        $("#rpstatus2").val("0");
	                        $("#rpstatus3").hide();
	                        $("#rpstatus3").val("0");
	                      
                        }else if(rptheme=="2"){
                        	$("#theme1").hide();
                        	$("#theme1").val("");
	                        $("#theme2").show();
	                        $("#theme3").hide();
	                        $("#theme3").val("");
	                        $("#branchid1").hide();
                        	$("#branchid1").val("");
	                        $("#branchid2").show();
	                        $("#branchid3").hide();
	                        $("#branchid3").val("");
	                        $("#rpstatus1").hide();
                        	$("#rpstatus1").val("0");
	                        $("#rpstatus2").show();
	                        $("#rpstatus3").hide();
	                        $("#rpstatus3").val("0");
                        }else if(rptheme=="3"){
                        	$("#theme1").hide();
                        	$("#theme1").val("");
	                        $("#theme2").hide();
	                        $("#theme2").val("");
	                        $("#theme3").show();
	                        $("#branchid1").hide();
                        	$("#branchid1").val("");
	                        $("#branchid2").hide();
	                        $("#branchid2").val("");
	                        $("#branchid3").show();
	                        $("#rpstatus1").hide();
                        	$("#rpstatus1").val("0");
	                        $("#rpstatus2").hide();
	                        $("#rpstatus2").val("0");
	                        $("#rpstatus3").show();
                        }
           
	        var branchid = $("#oldbranchid").val();
	        $("#rptheme option[value='"+themeid+"']").attr("selected",true);
	        var newbranch = "branchid"+themeid;
            $("#"+newbranch+" option[value='"+branchid+"']").attr("selected",true);
	       
	    });
	      function showMsg(msg, fn) {
				if (!fn) {
					TipInfo.Msg.alert(msg);
				} else {
					TipInfo.Msg.confirm(msg, fn);
				}
			}
	  		   function thChange(){
	             var rptheme = $("#rptheme").val();
	             if(rptheme=="1"){
	                        $("#theme1").show();
	                        $("#theme2").hide();
	                        $("#theme2").val("");
	                        $("#theme3").hide();
	                        $("#theme3").val("");
	                        $("#branchid1").show();
	                        $("#branchid2").hide();
	                        $("#branchid2").val("");
	                        $("#branchid3").hide();
	                        $("#branchid3").val("");
	                        $("#rpstatus1").show();
	                        $("#rpstatus2").hide();
	                        $("#rpstatus2").val("0");
	                        $("#rpstatus3").hide();
	                        $("#rpstatus3").val("0");
	                      
                        }else if(rptheme=="2"){
                        	$("#theme1").hide();
                        	$("#theme1").val("");
	                        $("#theme2").show();
	                        $("#theme3").hide();
	                        $("#theme3").val("");
	                        $("#branchid1").hide();
                        	$("#branchid1").val("");
	                        $("#branchid2").show();
	                        $("#branchid3").hide();
	                        $("#branchid3").val("");
	                        $("#rpstatus1").hide();
                        	$("#rpstatus1").val("0");
	                        $("#rpstatus2").show();
	                        $("#rpstatus3").hide();
	                        $("#rpstatus3").val("0");
                        }else if(rptheme=="3"){
                        	$("#theme1").hide();
                        	$("#theme1").val("");
	                        $("#theme2").hide();
	                        $("#theme2").val("");
	                        $("#theme3").show();
	                        $("#branchid1").hide();
                        	$("#branchid1").val("");
	                        $("#branchid2").hide();
	                        $("#branchid2").val("");
	                        $("#branchid3").show();
	                        $("#rpstatus1").hide();
                        	$("#rpstatus1").val("0");
	                        $("#rpstatus2").hide();
	                        $("#rpstatus2").val("0");
	                        $("#rpstatus3").show();
                        }
			}
			
			function roomadd(){
			var theme = $("#rptheme").val();
			var branchid;
			var rpstatus;
			var theme1 = $("#theme1").val();
			var theme2 = $("#theme2").val();
			var theme3 = $("#theme3").val();
			var branchid1 = $("#branchid1").val();
			var branchid2 = $("#branchid2").val();
			var branchid3 = $("#branchid3").val();
			var rpstatus1 = $("#rpstatus1").val();
			var rpstatus2 = $("#rpstatus2").val();
			var rpstatus3 = $("#rpstatus3").val();
			var rproomtype;
			   if(theme1){
			      rproomtype = theme1;
			   }else if(theme2){
			      rproomtype = theme2;
			   }else if(theme3){
			      rproomtype = theme3;
			   }
			   
			   
			   if(rpstatus1){
			      rpstatus = rpstatus1;
			   }else if(rpstatus2){
			      rpstatus = rpstatus2;
			   }else if(rpstatus3){
			      rpstatus = rpstatus3;
			   }
			   
			    if(branchid1){
			      branchid = branchid1;
			   }else if(branchid2){
			      branchid = branchid2;
			   }else if(branchid3){
			      branchid = branchid3;
			   }
		   var floor = $("#rpfloor").val();
		   var roomid = $("#rproomid").val();
		   var area = $("#rparea").val();
		   var status = rpstatus;
		   var remark = $("#rpremark").val();
		   if(!theme){
		   showMsg("场景不能为空");
		   
			}else if(!branchid){
			 showMsg("门店不能为空");
			}else if(!rproomtype){
			 showMsg("房型不能为空");
			}else if(!floor){
			 showMsg("楼层不能为空");
			}else if(!roomid){
			 showMsg("房号不能为空");
			}else if(!status){
			 showMsg("状态不能为空");
			}else if(!/^\d+$/.test(floor)){
			  showMsg("楼层必须为数字，请重新输入");
			}else if(!/^\d+$/.test(roomid)){
			  showMsg("房号必须为数字，请重新输入");
			} else{
			
		   if (area) {
			if (!/^\d+$/.test(area)) {
				showMsg("面积必须为数字，请重新输入");
				$("#opostcode").val("");
				return;
			  }
			}
			
			$.ajax( {
				url : base_path + "/adddataRoom.do",
				type : "post",
				dataType : "json",
				data : {
					'theme' : theme,
					'branchid' : branchid,
					'rproomtype' : rproomtype,
					'floor' : floor,
					'roomid' : roomid,
					'area' : area,
					'status' : status,
					'remark' : remark
				},
				success : function(json) {
					if (1 == json.result) {
					    if(json.message){
					      showMsg(json.message);
					    }else{
					    showMsg("新增成功！");
					    window.setTimeout("window.parent.location.reload(true)", 2000);
						window.setTimeout("window.parent.JDialog.close();",2000);
					    }
		
					} else {
						showMsg("新增失败！");
						window.setTimeout("window.parent.location.reload(true)", 2000);
						window.setTimeout("window.parent.JDialog.close();",2000);
					}
				},
				error : function() {
					showMsg("操作失败，请重新操作！");
					window.setTimeout("window.parent.location.reload(true)", 2000);
				    window.setTimeout("window.parent.JDialog.close();",2000);
				}
			});
			}
		  }
        
	  </script>
	
  </body>
</html>