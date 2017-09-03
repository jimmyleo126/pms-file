package com.ideassoft.crm.controller;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ideassoft.bean.Branch;
import com.ideassoft.bean.Campaign;
import com.ideassoft.bean.CampaignRule;
import com.ideassoft.bean.CouponGroup;
import com.ideassoft.bean.RoomType;
import com.ideassoft.core.ajax.AjaxResult;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.constants.CommonConstants;
import com.ideassoft.core.page.Pagination;
import com.ideassoft.crm.service.CampaignService;
import com.ideassoft.crm.service.ManageService;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.RequestUtil;

@Controller
public class CampaignController {
	@Autowired
	CampaignService campaignService;
	@Autowired
	ManageService manageService;
	
	@RequestMapping("/turnToCampaignsAddPage.do")
	public ModelAndView turnToCampaignsAddPage(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		// 查找所有的门店传递到页面上去
		List<?> branchList = this.manageService.queryAllOfBranch();
		mv.addObject("branchList", branchList);
		//查找营销活动业务
		List<?> businessList = this.campaignService.queryAllOfBusiness();
		mv.addObject("businessList", businessList);
		//查找优惠券组合表
		mv.setViewName("page/crm/campaign/addCampaigns");
		return mv;
	}
	
	//多选营销活动（酒店，公寓，民宿）
	@RequestMapping("/selectMultiUsingType.do")
	public ModelAndView selectMultiUsingType(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/dialog/customDialog");
		mv.addObject("dialogColumns","paramName,paramDesc");
		mv.addObject("dialogTarget", "SysParam");
		mv.addObject("dialogConditions", "paramType = 'EVENT_TYPE'");
		mv.addObject("dialogQuickAdd", null);
		mv.addObject("selectType","dialog");
		mv.addObject("colName", "usingType");
		return mv;
	}
	
	//多选营销活动使用对象
	@RequestMapping("/selectMultiUsingPerson.do")
	public ModelAndView selectMultiUsingPerson(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/dialog/customDialog");
		mv.addObject("dialogColumns","memberRank,rankName"); 
		mv.addObject("dialogTarget", "MemberRank");
		mv.addObject("dialogConditions", null);
		mv.addObject("dialogQuickAdd", null);
		mv.addObject("selectType","dialog");
		mv.addObject("colName", "usingPerson");
		return mv;
	}

	//优惠券选择页面
	@RequestMapping("/selectcouponGroup.do")
	public ModelAndView selectcouponGroup(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/crm/campaign/couponGroupSelect");
		return mv;
	}
	
	//优惠券页面的查询功能
	@RequestMapping("/queryCouponGroupDetail.do")
	public void queryCouponGroupDetail(HttpServletRequest request, HttpServletResponse response,String groupId) throws Exception {
		Pagination pagination;
		Integer pageNum = Integer.parseInt(request.getParameter("page"));
		Integer rows = Integer.parseInt(request.getParameter("rows"));
		pagination = new Pagination(pageNum, rows);
		List<?> result = this.campaignService.queryCouponGroupDetail(groupId,pagination);
		JSONUtil.responseJSON(response, JSONUtil.buildReportJSONByPagination(result, pagination));
	}
	
	//选择特惠房型
	@RequestMapping("/selectRoomType.do")
	public ModelAndView selectRoomType(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/dialog/customDialog");
		mv.addObject("dialogColumns","roomType,roomName");
		mv.addObject("dialogTarget", "RoomType");
		mv.addObject("dialogConditions", null);
		mv.addObject("dialogQuickAdd", null);
		mv.addObject("selectType","dialog-radio");
		mv.addObject("colName", "roomType");
		return mv;
	}
	//另一种营销活动的查询方式
	@RequestMapping("/selectcouponGroupAnother.do")
	public ModelAndView selectcouponGroupAnother(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/crm/campaign/couponGroupSelectAnother");
		return mv;
	}
	
	//各种营销活动,保存数据
	@RequestMapping("/campaignsAdd.do")
	public void campaignsAdd(HttpServletRequest request, HttpServletResponse response, Campaign campaign) throws Exception {
		boolean currExist = false;
		String businessId = request.getParameter("businessId");
		String usingPerson = request.getParameter("usingPerson_CUSTOM_VALUE");
		String usingType_CUSTOM_VALUE = request.getParameter("usingType_CUSTOM_VALUE");
		String dataId = this.manageService.getSequence("SEQ_SMSTEMPLATES_ID", null);
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String recordUser = loginUser.getStaff().getStaffId();
		campaign.setDataId(dataId);
		campaign.setRecordUser(recordUser);
		campaign.setUsingPerson(usingPerson);
		campaign.setUsingType(usingType_CUSTOM_VALUE);
		campaign.setStatus("1");
		//修改的过程中：时间加载到前台时会改变形式，再次保存时作修改(没有了时分秒)
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		String startTime = request.getParameter("realStartTime");
		Date startDate = sdf.parse(startTime.toString());
		String endTime = request.getParameter("realEndTime");
		Date endDate = sdf.parse(endTime.toString());
		campaign.setStartTime(startDate);
		campaign.setEndTime(endDate);
		if("6".equals(businessId)){
			String branchId = request.getParameter("branchId_CUSTOM_VALUE");
			campaign.setBranchId(branchId);
			String roomType = request.getParameter("roomType_CUSTOM_VALUE");
			campaign.setRoomType(roomType);
		}
		
		//如果是活动折上折，需向活动副表记录数据
		if("5".equals(businessId)){
			String liveDay = request.getParameter("liveDay");
			for(int j=0; j<Integer.parseInt(liveDay);j++){
				CampaignRule camRule = new CampaignRule();
				String camRuleDataId = this.manageService.getSequence("SEQ_CAMPAIGNRULE_ID", null);
				String discountTemp = request.getParameter("dayCount"+String.valueOf((j+1)));
				camRule.setCampaignId(dataId);
				camRule.setDataId(camRuleDataId);
				camRule.setLiveDay(liveDay);
				camRule.setCurrentDay(String.valueOf(j+1));
				camRule.setDayCount(discountTemp);
				camRule.setStatus("1");
				this.campaignService.save(camRule);	
			}
		}
		
		//save之前要先来数据库检重一遍
		//将时间进行处理，endTime 加1天
		Calendar cal = Calendar.getInstance();
		cal.setTime(endDate);
		cal.add(Calendar.DATE, 1);
		
		if(!"2".equals(businessId) && !"6".equals(businessId)){
			currExist = this.campaignService.IsCurrExist(businessId,startTime,sdf.format(cal.getTime()),null);
		}else if ("2".equals(businessId)){
			int count = 0;
			String[] usindPersonArray = campaign.getUsingPerson().split(",");
			for (int i = 0; i < usindPersonArray.length ; i++){
				currExist = this.campaignService.IsCurrExist(businessId,startTime,sdf.format(cal.getTime()),usindPersonArray[i]);
				if(currExist){
					count++;
				}	
			}
			
			if(count != 0){
				currExist = true;
			}
			
		}else{
			//营销活动6
			String branchId = campaign.getBranchId();
			String roomType = campaign.getRoomType();
			currExist = this.campaignService.IsDiscountRoomExist(branchId,startTime,sdf.format(cal.getTime()),roomType);
		}
		
		if(currExist){
			JSONUtil.responseJSON(response, new AjaxResult(Integer.parseInt("2"), null));
		}else{
			this.campaignService.save(campaign);	
			JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS, null));
		}
	}
	
	//选择归属门店
	@RequestMapping("/selectBranchId.do")
	public ModelAndView selectBranchId(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/crm/campaign/selectBranchId");
		return mv;
	}
	
	//选择归属门店的查询数据方法
	@RequestMapping("/querybranchIdAndType.do")
	public void querybranchIdAndType(HttpServletRequest request, HttpServletResponse response,String branchName, String branchType) throws Exception {
		Pagination pagination;
		Integer pageNum = Integer.parseInt(request.getParameter("page"));
		Integer rows = Integer.parseInt(request.getParameter("rows"));
		pagination = new Pagination(pageNum, rows);
		branchName = URLDecoder.decode(branchName, "UTF-8");
		List<?> result = this.campaignService.querybranchNameAndType(branchName, branchType, pagination);
		JSONUtil.responseJSON(response, JSONUtil.buildReportJSONByPagination(result, pagination));
	}	
	
	//营销活动修改跳页面
	@SuppressWarnings("unchecked")
	@RequestMapping("/turnAlterCampaignPage.do")
	public ModelAndView turnAlterCampaignPage(HttpServletRequest request, HttpServletResponse response)throws Exception{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/crm/campaign/campaignAlter");
		String campaignId = request.getParameter("campaignId");
		Campaign campaignInfo = (Campaign)this.campaignService.findOneByProperties(Campaign.class, "dataId", campaignId);
		/*//对开始时间和结束时间进行相应的处理
		Date startTime = campaignInfo.getStartTime();
		Date endTime = campaignInfo.getEndTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		String startTimeStr = sdf.format(startTime);
		String endTimeStr = sdf.format(endTime);
		campaignInfo.setStartTime(sdf.parse(startTimeStr));
		campaignInfo.setEndTime(sdf.parse(endTimeStr));*/
		mv.addObject("campaignInfo", campaignInfo);
		//部分数据在campaignInfo，没有翻译成中文，则另外查传递到页面去
		List<?> usingPersonAndType = this.campaignService.queryUsingPersonAndType(campaignId);
		JSONObject PersonAndType  = JSONUtil.fromObject(usingPersonAndType.get(0));
		mv.addObject("usingPersonAndType", PersonAndType);	
		// 查找所有的门店传递到页面上去加载到页面上
		List<?> branchList = this.manageService.queryAllOfBranch();
		mv.addObject("branchList", branchList);
		//查找营销活动业务
		List<?> businessList = this.campaignService.queryAllOfBusiness();
		mv.addObject("businessList", businessList);
		//如果是营销活动折上折就要求campaignrule表里查找规则加载到页面5
		List<?> campaignRuleList = this.campaignService.findByProperties(CampaignRule.class, "campaignId",campaignInfo.getDataId(),"status","1");			
		JSONArray campaignRules = JSONUtil.fromObject(campaignRuleList);
		mv.addObject("campaignRuleList", campaignRules);
		//营销活动是推荐有礼活动时，要查询优惠劵组合表
		List<?> couponList = this.campaignService.findByProperties(CouponGroup.class, "dataId",campaignInfo.getCouponGroupId());			
		JSONArray couponLists = JSONUtil.fromObject(couponList);
		mv.addObject("couponList", couponLists);
		//营销活动6查询其数据库里的归属部门，和房型
		List<?> cambranchList = this.campaignService.findByProperties(Branch.class, "branchId",campaignInfo.getBranchId());			
		JSONArray branchLists = JSONUtil.fromObject(cambranchList);
		mv.addObject("branchLists", branchLists);
		List<?> camroomList = this.campaignService.findByProperties(RoomType.class, "roomType",campaignInfo.getRoomType());			
		JSONArray camroomLists = JSONUtil.fromObject(camroomList);
		mv.addObject("camroomLists", camroomLists);
		return mv;	
	}
	
	//营销活动修改保存数据
	@RequestMapping("/campaignsAlter.do")
	public void campaignsAlter(HttpServletRequest request, HttpServletResponse response, Campaign campaign) throws Exception {
		String businessId = request.getParameter("businessId");
		String usingPerson = request.getParameter("usingPerson_CUSTOM_VALUE");
		String usingType = request.getParameter("usingType_CUSTOM_VALUE");
		String dataId = request.getParameter("dataId");
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String recordUser = loginUser.getStaff().getStaffId();
		//campaign.setDataId(dataId);
		campaign.setRecordUser(recordUser);
		campaign.setUsingPerson(usingPerson);
		campaign.setUsingType(usingType);
		//campaign.setStatus("1");
		campaign.setRecordTime(new Date());
		//修改的过程中：时间加载到前台时会改变形式，再次保存时作修改(没有了时分秒)
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		String startTime = request.getParameter("realStartTime");
		Date startDate = sdf.parse(startTime.toString());
		String endTime = request.getParameter("realEndTime");
		Date endDate = sdf.parse(endTime.toString());
		campaign.setStartTime(startDate);
		campaign.setEndTime(endDate);
		if("6".equals(businessId)){
			String branchId = request.getParameter("branchId_CUSTOM_VALUE");
			campaign.setBranchId(branchId);
			String roomType = request.getParameter("roomType_CUSTOM_VALUE");
			campaign.setRoomType(roomType);
		}
		this.campaignService.update(campaign);	
		if("5".equals(businessId)){
			//根据活动的campaignId找到辅表将相应的数据删除
			this.campaignService.deleteCampaignRules(dataId);
			//新增该活动的活动规则表
			String liveDay = request.getParameter("liveDay");
			for(int j=0; j<Integer.parseInt(liveDay);j++){
				CampaignRule camRule = new CampaignRule();
				String camRuleDataId = this.manageService.getSequence("SEQ_CAMPAIGNRULE_ID", null);
				String discountTemp = request.getParameter("dayCount"+String.valueOf((j+1)));
				camRule.setCampaignId(dataId);
				camRule.setDataId(camRuleDataId);
				camRule.setLiveDay(liveDay);
				camRule.setCurrentDay(String.valueOf(j+1));
				camRule.setDayCount(discountTemp);
				camRule.setStatus("1");
				this.campaignService.save(camRule);	
			}
		}
		JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS, null));
	}
  /*  //转换前台传递过来的字符串型的date,（在以对象传递过来时）
	@InitBinder
	public void init(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}*/

	//删除当前营销活动的记录（改为逻辑删除改状态为0）
	@RequestMapping("/campaignsDelete.do")
	public void campaignsDelete(HttpServletRequest request, HttpServletResponse response, Campaign campaign) throws Exception {
		//this.campaignService.delete(campaign);
		campaign.setStatus("0");
		this.campaignService.update(campaign);
		String businessId = request.getParameter("businessId");
		if(businessId.equals("5")){
			//根据活动的campaignId找到辅表将相应的数据删除
			String dataId = request.getParameter("dataId");
			//删除是逻辑删除
			this.campaignService.deleteCampaignRules(dataId);
		}
		JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS, null));
	}
	
	//每一个加载条件点击跳dialog的框体
	//多选营销活动使用对象
	@RequestMapping("/pageConditionOpenJDialog.do")
	public ModelAndView pageConditionOpenJDialog(HttpServletRequest request,HttpServletResponse response,String inputId, String javaBeanName, String colsName, String queryConditions) {
		ModelAndView mv = new ModelAndView();
		String[] arr = queryConditions.split("=");
		arr[1] = "\'" + arr[1] + "\'";
		queryConditions = arr[0] + "=" + arr[1];
		mv.setViewName("page/dialog/customDialog");
		mv.addObject("dialogColumns",colsName); 
		mv.addObject("dialogTarget", javaBeanName);
		mv.addObject("dialogConditions", queryConditions);
		mv.addObject("dialogQuickAdd", null);
		mv.addObject("selectType","dialog-radio");
		mv.addObject("colName", inputId);
		return mv;
	}
	
}
