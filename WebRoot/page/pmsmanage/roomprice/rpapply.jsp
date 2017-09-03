<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../common/taglib.jsp"%>
<%@ include file="../../common/css.jsp"%>
<%@ include file="../../common/script.jsp"%>
<% request.setAttribute("basePath", request.getContextPath());
   request.setAttribute("rptheme", request.getAttribute("rptheme")); 
   request.setAttribute("rpbranchid", request.getAttribute("rpbranchid")); 
   request.setAttribute("rpkind", request.getAttribute("rpkind")); 
   request.setAttribute("rproomtype", request.getAttribute("rproomtype"));
   request.setAttribute("rppack", request.getAttribute("rppack"));
   request.setAttribute("rpstatus", request.getAttribute("rpstatus"));
   request.setAttribute("rpsetup", request.getAttribute("rpsetup"));
   request.setAttribute("themeid", request.getAttribute("themeid"));
   request.setAttribute("branchid", request.getAttribute("branchid"));
   request.setAttribute("rpking", request.getAttribute("rpking"));
   request.setAttribute("statusid", request.getAttribute("statusid")); 
%>

<!DOCTYPE HTML>
<html>
	<head>
		<title>房租价申请</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/dialog.css" />
		<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
		<link href="<%=request.getContextPath()%>/css/reset.css" rel="stylesheet" type="text/css" media="all" />
		<link href="<%=request.getContextPath()%>/css/pmsmanage/roomprice.css" rel="stylesheet" type="text/css" media="all" />
		<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/need/laydate.css"/>
		<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/skins/molv/laydate.css"/>
		<style>
		.appinfo_c {
		    height: 68%;
		    overflow: hidden;
		}
		</style>
	</head>
	<body>
	    <input id="oldtheme" name="oldtheme" type="hidden" value="${themeid}"/>
	    <input id="oldbranchid" name="oldbranchid" type="hidden" value="${branchid}"/>
	    <input id="oldrpking" name="oldrpking" type="hidden" value="${rpking}"/>
	    <input id="rproomtype" name="rproomtype" type="hidden" value="${rproomtype}"/>
	    <input id="rpsetup" name="rpsetup" type="hidden" value="${rpsetup}"/>
	    <input id="rpstatusid" name="rpstatusid" type="hidden" value="${statusid}"/>
		<div style="height: 99%; overflow: auto;">
				<div class="appinfo_c">
					<table class="table">
					    <col width="10%" />
						<col width="23%" />
						<col width="10%" />
						<col width="23%" />
						<col width="10%" />
						<col width="23%" />
						<tr>
							<td align="right">
								<label>场景模式:</label></td>
								<td><select id="rptheme" name="rptheme" class="check" onchange="thChange()" disabled>
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
							<td align="right">
								<label>归属门店:</label></td>
								 <td><select id="branchid1" name="" class="ptdinput" disabled>
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
								  <select id="branchid2" name="" class="ptdinput" style="display:none" disabled>
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
								  <select id="branchid3" name="" class="ptdinput" style="display:none" disabled>
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
							</td>
							<td align="right">
								<label >房价类型:</label></td>
								 <td><select id="rpkind1" name="" class="ptdinput" disabled>
								  <option value="">
									       请选择房价类型
								     </option>
							      <c:forEach var="r" items="${rpkind}">
								    <c:set var="flagr" value="${r.orderno}"></c:set>
		                            <c:if  test="${(flagr==1)}">
		                            <option value="<c:out value='${r.content}' />"/>
		                            <c:out value="${r.paramname}"/>
		                             </c:if>
                                    </option>
							      </c:forEach>
								  </select>
								  <select id="rpkind2" name="" class="ptdinput" style="display:none" disabled>
											  <option value="">
												       请选择房价类型
											     </option>
										       <c:forEach var="r" items="${rpkind}">
								    <c:set var="flagr" value="${r.orderno}"></c:set>
		                            <c:if  test="${(flagr==2)}">
		                            <option value="<c:out value='${r.content}' />"/>
		                            <c:out value="${r.paramname}"/>
		                             </c:if>
                                    </option>
							      </c:forEach>
								  </select>
								  <select id="rpkind3" name="" class="ptdinput" style="display:none" disabled>
											  <option value="">
												       请选择房价类型
											     </option>
								    <c:forEach var="r" items="${rpkind}">
								    <c:set var="flagr" value="${r.orderno}"></c:set>
		                            <c:if  test="${(flagr==3)}">
		                            <option value="<c:out value='${r.content}' />"/>
		                            <c:out value="${r.paramname}"/>
		                             </c:if>
                                    </option>
							      </c:forEach>
								  </select>
							</td>
							
						</tr>
						<tr>
						<td align="right"><label >开始日期:</label></td>
						<td><input id="begindate" name="begindate" type="text" /></td>
						<td align="right"><label >结束日期:</label></td>
						<td><input id="enddate" name="enddate" type="text"/></td>
						<td align="right"><label >生效周期:</label></td>
						<td><input id="validday" name="validday" type="text"/></td>
						</tr>
						<tr>
						<td align="right"><label >过滤日期:</label></td>
						<td><input id="filterday" name="filterday" type="text" placeholder="格式:20170830，多个日期请用逗号分隔"/></td>
						<td align="right"><label >房价状态:</label></td>
						<td><select id="rpstatus" name="rpstatus" class="check" disabled>
								   <option value="">
									       请选择状态
								     </option>
							      <c:forEach var="ra" items="${rpstatus}">
								    <option value="${ra.content}">
									   ${ra.paramname}
								     </option>
							      </c:forEach>
						         </select></td>
						<td ></td>
						<td></td>
						</tr>					
						</table>
					    <form action="" name="rpapplydetail" id="myForm">
						<table class="table">
					    <col width="10%" />
						<col width="23%" />
						<col width="10%" />
						<col width="23%" />
						<col width="10%" />
						<col width="23%" />
						     <c:forEach var="y" items="${rproomtype}">
						       <c:set var="flagy" value="${y.theme}"></c:set>
						         <c:if  test="${(flagy==1)}">
						         <tr>
						       <td align="right"> 
								<label>${y.roomname}:</label></td>
								 <td><input id="rpapplyroomtype" name="${y.roomtype}" type="text" value="${y.roomtype}"/></td>
								  <td align="right"> 
								<label >门市价:</label></td>
								<td> <input id="${y.roomtype}" name="${y.roomtype}_price" type="text" value="${y.roomprice}"/>
							</td>
							<td align="right">
								<label>包价:</label></td>
								<td><select id="packid"  name="${y.roomtype}_packid" class="check">
								    <option value="">
									       请选择包价
								     </option>
							      <c:forEach var="rpa" items="${rppack}">
								    <option value="${rpa.packid}">
									   ${rpa.packname}
								     </option>
							      </c:forEach>
						         </select>
							</td>
								</tr>
							 </c:if>
							</c:forEach>	
						</table>
						</form>
				</div>
				<div id="tableInfoTitle" class="address">
					<label>
						备注:
					</label>
					<textarea id="auditMessage" class="auditMessage"></textarea>
					<table class="SubmitTable" border="0" cellspacing="0" cellpadding="0">
						<tbody>
							<tr>
								<td >
									<div class="div_button button" role="button"
										onclick="auditSubmitOK();">
										<a id="sData">申请</a>&nbsp;
									</div>
										<div class="div_button button" role="button"
										onclick="window.parent.JDialog.close();">
										<a id="sData">取消</a>
									</div>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
		
		</div>
		<script src="<%=request.getContextPath()%>/script/ipms/js/laydate.dev.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/jquery.js"></script>
		<script
			src="<%=request.getContextPath()%>/script/crm/audit/auditInfoDetail.js"></script>
		<script>
	 	var base_path = '<%=request.getContextPath()%>';
	 </script>
	 <script>
	   $(document).ready(function(){
	        var themeid = $("#oldtheme").val();
	        var rptheme = themeid;
	             if(rptheme=="1"){
	                        $("#rpkind1").show();
	                        $("#rpkind2").hide();
	                        $("#rpkind2").val("");
	                        $("#rpkind3").hide();
	                        $("#rpkind3").val("");
	                        $("#branchid1").show();
	                        $("#branchid2").hide();
	                        $("#branchid2").val("");
	                        $("#branchid3").hide();
	                        $("#branchid3").val("");

                        }else if(rptheme=="2"){
                        	$("#rpkind1").hide();
                        	$("#rpkind1").val("");
	                        $("#rpkind2").show();
	                        $("#rpkind3").hide();
	                        $("#rpkind3").val("");
	                        $("#branchid1").hide();
                        	$("#branchid1").val("");
	                        $("#branchid2").show();
	                        $("#branchid3").hide();
	                        $("#branchid3").val("");
                        }else if(rptheme=="3"){
                        	$("#rpkind1").hide();
                        	$("#rpkind1").val("");
	                        $("#rpkind2").hide();
	                        $("#rpkind2").val("");
	                        $("#rpkind3").show();
	                        $("#branchid1").hide();
                        	$("#branchid1").val("");
	                        $("#branchid2").hide();
	                        $("#branchid2").val("");
	                        $("#branchid3").show();
                        }
           
	        var branchid = $("#oldbranchid").val();
	        var rpking = $("#oldrpking").val();
	        var statusid = $("#rpstatusid").val();
	        $("#rptheme option[value='"+themeid+"']").attr("selected",true);
	         $("#rpstatus option[value='"+statusid+"']").attr("selected",true);
	        var newbranch = "branchid"+themeid;
            $("#"+newbranch+" option[value='"+branchid+"']").attr("selected",true);
            var newrpkind = "rpkind"+themeid;
            $("#"+newrpkind+" option[value='"+rpking+"']").attr("selected",true);
	       
	    });	    
	    
	laydate({
		elem: '#begindate',
		min:laydate.now(1) 
	})
	
	laydate({
		elem: '#enddate',
		min:laydate.now(1) 
	})
	    
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
	                        $("#rpkind1").show();
	                        $("#rpkind2").hide();
	                        $("#rpkind2").val("");
	                        $("#rpkind3").hide();
	                        $("#rpkind3").val("");
	                        $("#branchid1").show();
	                        $("#branchid2").hide();
	                        $("#branchid2").val("");
	                        $("#branchid3").hide();
	                        $("#branchid3").val("");
	                      
                        }else if(rptheme=="2"){
                        	$("#rpkind1").hide();
                        	$("#rpkind1").val("");
	                        $("#rpkind2").show();
	                        $("#rpkind3").hide();
	                        $("#rpkind3").val("");
	                        $("#branchid1").hide();
                        	$("#branchid1").val("");
	                        $("#branchid2").show();
	                        $("#branchid3").hide();
	                        $("#branchid3").val("");
                        }else if(rptheme=="3"){
                        	$("#rpkind1").hide();
                        	$("#rpkind1").val("");
	                        $("#rpkind2").hide();
	                        $("#rpkind2").val("");
	                        $("#rpkind3").show();
	                        $("#branchid1").hide();
                        	$("#branchid1").val("");
	                        $("#branchid2").hide();
	                        $("#branchid2").val("");
	                        $("#branchid3").show();
                        }
			}
			
	function num(obj){
			obj.value = obj.value.replace(/[^\d.]/g,""); //清除"数字"和"."以外的字符
			obj.value = obj.value.replace(/^\./g,""); //验证第一个字符是数字
			obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个, 清除多余的
			obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
			obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3'); //只能输入两个小数
     }

    function rpcount(){
                var s =  $("#rpsetup").val();
				var first = s.replace(/=/g,":'");
				var second = first.replace(/,/g,"',");
				var third = second.replace(/}/g,"'}");
				var fouth = third.replace(/}',/g,"},");
				var array = eval(fouth);
				for(var i = 0, l = array.length; i < l; i++){ 
				var discount = array[i].DISCOUNT
				var paramname = array[i].PARAMNAME
				var beforediscount = $("#MSJ").val();
				 if ( !/^\d+|\d+\.\d{1,2}$/gi.test(beforediscount) ){
                      showMsg("请输入数字（小数点后俩位）")
                       window.setTimeout("location.reload(true)", 1500);
                    }else{
                      var afterdiscount = (beforediscount*discount)/100;
					  var disid = "#"+paramname;
					  $(disid).val(afterdiscount.toFixed(2));
                    }				
			  }
            }


    document.onkeydown=function(event){
            var e = event || window.event || arguments.callee.caller.arguments[0];          
             if(e && e.keyCode==13){ // enter 键
			 var s =  $("#rpsetup").val();
				var first = s.replace(/=/g,":'");
				var second = first.replace(/,/g,"',");
				var third = second.replace(/}/g,"'}");
				var fouth = third.replace(/}',/g,"},");
				var array = eval(fouth);
				for(var i = 0, l = array.length; i < l; i++){ 
				var discount = array[i].DISCOUNT
				var paramname = array[i].PARAMNAME
				var beforediscount = $("#MSJ").val();
				 if ( !/^\d+|\d+\.\d{1,2}$/gi.test(beforediscount) ){
                      showMsg("请输入数字（小数点后俩位）")
                       window.setTimeout("location.reload(true)", 1500);
                    }else{
                      var afterdiscount = (beforediscount*discount)/100;
					  var disid = "#"+paramname;
					  $(disid).val(afterdiscount.toFixed(2));
                    }
				
			  }
            }
        }; 
        
 function auditSubmitOK(){
 
   var rbbranchid = $("#rbbranchid").val();
   var rptheme = $("#rptheme").val();
   var theme1 = $("#theme1").val();
   var theme2 = $("#theme2").val();
   var theme3 = $("#theme3").val();
   var theme3 = $("#theme3").val();
   var packid = $("#packid").val();
   var rproomtype;
   if(theme1){
      rproomtype = theme1;
   }else if(theme2){
      rproomtype = theme2;
   }else if(theme3){
      rproomtype = theme3;
   }
   var operateid = rbbranchid+rptheme+rproomtype;
   if(!rbbranchid){
      showMsg("请先选择门店")
   }else if(!rptheme){
     showMsg("请先选择场景")
   }else if(!rproomtype){
     showMsg("请先选择房型")
   }else{
     $.ajax( {
				url : base_path + "/rpOperate.do",
				type : "post",
				dataType : "json",
				data : {
					'operateid' : operateid
				},
				success : function(json) {
					if (1 == json.result) {
					    if(json.message){
					        showMsg(json.message);
					       }else{
					          var s =  $("#rpsetup").val();
								var first = s.replace(/=/g,":'");
								var second = first.replace(/,/g,"',");
								var third = second.replace(/}/g,"'}");
								var fouth = third.replace(/}',/g,"},");
								var array = eval(fouth);
								for(var i = 0, l = array.length; i < l; i++){ 
								var discount = array[i].DISCOUNT
								var paramname = array[i].PARAMNAME
								var rpname = array[i].PARAMDESC
								var  memberrank = array[i].CONTENT
								var disid = "#"+paramname;
								var rtrp = $(disid).val();
								 $.ajax( {
									url : base_path + "/auditRp.do",
									type : "post",
									dataType : "json",
									data : {
										'rbbranchid' : rbbranchid,
										'rpid' : paramname,
										'rpname' : rpname,
										'rproomtype' : rproomtype,
										'roomprice' : rtrp,
										'packid' : packid,
										'discount' : discount,
										'memberrank' : memberrank,
										'rptheme' : rptheme
									},
									success : function(json) {
										if (1 == json.result) {
										    if(json.message){
										        showMsg(json.message);
										        window.setTimeout("location.reload(true)", 1800);
										        window.setTimeout("window.parent.JDialog.close();",1800);
										       }else{
										         showMsg("操作rp成功！");
										       }
											
										} else {
											showMsg("操作失败！");
											
										}
									},
									error : function() {
										showMsg("操作失败，请重新操作！");
										
									}
								    });
								
							  }
					       }
						
					} else {
						showMsg("记录日志失败！");
						window.setTimeout("location.reload(true)", 1800);
					}
				},
				error : function() {
					showMsg("操作失败，请重新操作！");
					window.setTimeout("location.reload(true)", 1800);
				}
			    });
   }
 
 }
	 </script>
	</body>
</html>
