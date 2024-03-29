<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/taglib.jsp"%>
<%@ include file="../../common/script.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="<%=request.getContextPath()%>/css/reset.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-ui.css"/>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css"/>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/datetimepicker.css" media="all" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css" media="all" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/campaigns.css" media="all" />
		<title>营销活动添加</title>
	</head>
	<body>
		
		<form id="basicInfo" name="basicInfo" class="basicInfo">
			<table>
				<tr>
					<td style="width:8%;">
						业务类型
					</td>
					<td>
						<select name="businessId" id="businessId" class="ui-select">
							<option value="">
								请选择
							</option>
							<c:forEach items="${businessList}" var="bl">
								<option value="${bl.content}">
									${bl.paramName}
								</option>
							</c:forEach>
						</select>
					</td>
					<td style="width:8%;">
						活动名称
					</td>
					<td>
						<input id="campaignName" name="campaignName" value="" />
					</td>
					<td style="width:8%;">
						活动分类
					</td>
					<td>
						<select id="campaignType" name="campaignType">
							<option value="">
								请选择
							</option>
							<option value="1">
								充值优惠
							</option>
							<option value="2">
								消费优惠
							</option>
							<option value="3">
								其他
							</option>
						</select>
					</td>
				</tr>
				<tr>
					<td style="width:8%;">
						适用对象
					</td>
					<td>
						<input id="usingPerson" name="usingPerson" value="" />
						<input type="hidden" id="usingPerson_CUSTOM_VALUE" name="usingPerson_CUSTOM_VALUE" value="" />
					</td>
					<td style="width:8%;">
						使用范围
					</td>
					<td>
						<select id="usingRange" name="usingRange">
							<option value="">
								请选择
							</option>
							<option value="0">
								所有
							</option>
							<option value="1">
								线上
							</option>
							<option value="2">
								线下
							</option>
						</select>
					</td>
					<td style="width:8%;">
						适用类型
					</td>
					<td >
						<input id="usingType" name="usingType" value="" />
						<input type="hidden" id="usingType_CUSTOM_VALUE" name="usingType_CUSTOM_VALUE" value="" />
					</td>
				</tr>
				<tr>
					<td style="width:8%;">
						开始时间
					</td>
					<td>
						<input id="startTimeTemp" name="startTimeTemp" class="date" value="" />
					</td>
					<td style="width:8%;">
						结束时间
					</td>
					<td>
						<input id="endTimeTemp" name="endTimeTemp" class="date" value="" />
					</td>
					<!--<td style="width:8%;">
						活动周期
					</td>
					<td >
						<input id="campaignCycle" name="campaignCycle" value="" />
					</td>
				-->
					<td style="width:8%;">
							优先级
						</td>
						<td>
							<select id="priority" name="priority">
								<option value="">
									请选择
								</option>	
								<option value="0">
									普通
								</option>
								<option value="1">
									中级
								</option>
								<option value="2">
									高级
								</option>
								<option value="3">
									超高级
								</option>
							</select>
						</td>
				</tr>
				<tr>
					<td style="width:12%;">
						活动描述
					</td>
					<td colspan="5">
						<textArea id="campaignDesc" name="campaignDesc" style="border: 1px solid #ddd;width: 103%;margin: 6px 0 0 7px;"></textArea>
					</td>
				</tr>

			</table>
			<div id="campaignDetail" name="campaignDetail">
			</div>
		</form>
		<div class="button_div fr" style="width:100%;margin-top: -13px;">
			<!--<div class="button div_button close" style="display:inline-block;float:right;margin: 8px;" onclick="campaignClose();">
				<span>关闭</span>
			</div>
			--><div class="button div_button save" style="display:inline-block;float:right;margin: 8px;" onclick="addCampaignInfo();">
				<span>保存</span>
			</div>
			<div class="button div_button reset" style="display:inline-block;float:right;margin: 8px;" onclick="campaignPageReset();">
				<span>重置</span>
			</div>
		</div>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script src="<%=request.getContextPath()%>/script/crm/campaign/campaignAdd.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/ui.datetimepicker.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/datepickerCN.js"></script>
		<script>
		var base_path ="<%=request.getContextPath()%>";			
	</script>
	</body>
</html>