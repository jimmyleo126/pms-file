package com.ideassoft.pms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.jdbc.SqlBuilder;
import com.ideassoft.core.page.Pagination;
import com.ideassoft.crm.service.ManageService;
import com.ideassoft.pms.service.ReportFormService;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.RequestUtil;

@Transactional
@Controller
public class reportFormController {
	@Autowired
	private ReportFormService reportFormService;
	@Autowired
	ManageService manageService;
	
	/*//在住客人明细表
	@RequestMapping("/queryRoomingGuest.do")
	public ModelAndView queryRoomingGuest(HttpServletRequest request, HttpServletResponse response, String roomType, String roomType_CUSTOM_VALUE, String roomId, String checkUser) throws Exception {
		ModelAndView mv = new ModelAndView();
		Pagination pagination = SqlBuilder.buildPagination(request);
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		// 查找在住客人明细
		List<?> roomingGuestsList = this.reportFormService.queryRoomingGuestByCon(pagination, branchId, roomType_CUSTOM_VALUE, roomId, checkUser );
		mv.addObject("roomingGuestsList", roomingGuestsList);
		mv.addObject("pagination", pagination);
		// 查找所有的门店传递到页面上去
		List<?> branchList = this.manageService.queryAllOfBranch();
		mv.addObject("branchList", branchList);
		mv.addObject("roomType", roomType);
		mv.addObject("roomType_CUSTOM_VALUE", roomType_CUSTOM_VALUE);
		mv.addObject("roomId", roomId);
		mv.addObject("checkUser", checkUser);
		mv.setViewName("page/ipmspage/reportform/roomingGuests");
		return mv;
	}*/
	
	@RequestMapping("/queryRoomingGuest.do")
	public ModelAndView queryRoomingGuest(HttpServletRequest request, HttpServletResponse response, String roomType, String roomType_CUSTOM_VALUE, String roomId, String checkUser) throws Exception {
		ModelAndView mv = new ModelAndView();
		// 查找所有的门店传递到页面上去
		List<?> branchList = this.manageService.queryAllOfBranch();
		mv.addObject("branchList", branchList);
		List<?> roomTypeList = this.manageService.queryAllOfRoomType();
		mv.addObject("roomTypeList", roomTypeList);
		//查找房型
		mv.addObject("roomType", roomType);
		mv.addObject("roomType_CUSTOM_VALUE", roomType_CUSTOM_VALUE);
		mv.addObject("roomId", roomId);
		mv.addObject("checkUser", checkUser);
		mv.setViewName("page/ipmspage/reportform/roomingGuests");
		return mv;
	}
	
	@RequestMapping("/queryRoomingGuestData.do")
	public ModelAndView queryRoomingGuestData(HttpServletRequest request, HttpServletResponse response, String roomType, String roomType_CUSTOM_VALUE, String roomId, String checkUser) throws Exception {
		ModelAndView mv = new ModelAndView();
		Pagination pagination = SqlBuilder.buildPagination(request);
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		// 查找在住客人明细
		List<?> roomingGuestsList = this.reportFormService.queryRoomingGuestByCon(pagination, branchId, roomType, roomId, checkUser );
		mv.addObject("roomingGuestsList", roomingGuestsList);
		mv.addObject("pagination", pagination);
		mv.addObject("roomType", roomType);
		mv.addObject("roomType_CUSTOM_VALUE", roomType_CUSTOM_VALUE);
		mv.addObject("roomId", roomId);
		mv.addObject("checkUser", checkUser);
		mv.setViewName("page/ipmspage/reportform/roomingGuestsData");
		return mv;
	}
	
	
	
	//在住客人的明细根据查询条件来返回结果
/*	@RequestMapping("/queryRoomingByCon.do")
	public void queryRoomingGuestByCondition(HttpServletRequest request, HttpServletResponse response, String roomType, String roomId, String checkUser)throws Exception{
		Pagination pagination = SqlBuilder.buildPagination(request);
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		List<?> roomingGuestsList = this.reportFormService.queryRoomingGuestByCon(pagination, branchId, roomType, roomId, checkUser );
		Map<String, Object> rm = new HashMap<String, Object>();
		rm.put("result", roomingGuestsList);
		rm.put("pagination", pagination);
		JSONUtil.responseJSON(response, rm);
	}
	*/
	
	//查询每张单子的具体入住人
	@RequestMapping("/queryRoomGuestDetail.do")
	public ModelAndView queryRoomGuestDetail(HttpServletRequest request, HttpServletResponse response, String checkUser) throws Exception {
		ModelAndView mv = new ModelAndView();
		List<?> roomGuestDetailLists = this.reportFormService.queryRoomGuestDetail(checkUser );
		mv.addObject("roomGuestDetailLists", roomGuestDetailLists);
		mv.setViewName("page/ipmspage/reportform/roomGuestDetails");
		return mv;
	}
	
	//换房明细表
	@RequestMapping("/changeRoomTable.do")
	public ModelAndView changeRoomTable(HttpServletRequest request, HttpServletResponse response, String checkId, String status, String checkUser, String startTime, String endTime, String recordUser) throws Exception {
		ModelAndView mv = new ModelAndView();
		// 查找所有的门店传递到页面上去
		List<?> branchList = this.manageService.queryAllOfBranch();
		mv.addObject("branchList", branchList);
		mv.addObject("checkId", checkId);
		mv.addObject("status", status);
		mv.addObject("checkUser", checkUser);
		mv.addObject("startTime", startTime);
		mv.addObject("endTime", endTime);
		mv.addObject("recordUser", recordUser);
		mv.setViewName("page/ipmspage/reportform/changRoomTable");
		return mv;
	}
	
	//换房明细表
	@RequestMapping("/changeRoomTableData.do")
	public ModelAndView changeRoomTableData(HttpServletRequest request, HttpServletResponse response, String checkId, String status, String checkUser, String startTime, String endTime, String recordUser) throws Exception {
		ModelAndView mv = new ModelAndView();
		Pagination pagination = SqlBuilder.buildPagination(request);
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		// 查找换房明细
		List<?> changeRoomList = this.reportFormService.queryChangeRoomByCon(pagination, branchId, checkId, status, checkUser ,startTime, endTime, recordUser);
		mv.addObject("changeRoomList", changeRoomList);
		mv.addObject("pagination", pagination);
		// 查找所有的门店传递到页面上去
		List<?> branchList = this.manageService.queryAllOfBranch();
		mv.addObject("branchList", branchList);
		mv.addObject("checkId", checkId);
		mv.addObject("status", status);
		mv.addObject("checkUser", checkUser);
		mv.addObject("startTime", startTime);
		mv.addObject("endTime", endTime);
		mv.addObject("recordUser", recordUser);
		mv.setViewName("page/ipmspage/reportform/changRoomTableData");
		return mv;
	}
	
/*	//换房明细，根据查询条件
	@RequestMapping("/changeRoomTableByCon.do")
	public void changeRoomTableByCon(HttpServletRequest request, HttpServletResponse response, String checkId, String status, String checkUser, String startTime, String endTime)throws Exception{
		Pagination pagination = SqlBuilder.buildPagination(request);
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		List<?> changeRoomList = this.reportFormService.queryChangeRoomByCon(pagination, branchId, checkId, status, checkUser ,startTime, endTime);
		Map<String, Object> rm = new HashMap<String, Object>();
		rm.put("result", changeRoomList);
		rm.put("pagination", pagination);
		JSONUtil.responseJSON(response, rm);
	}*/
	
	//入账明细表
	@RequestMapping("/accountRecordDetail.do")
	public ModelAndView accountRecordDetail(HttpServletRequest request, HttpServletResponse response,String startTime, String endTime, String checkId,String recordType, String accountStatus,String payMent,String recordUser,String projectType) throws Exception {
		ModelAndView mv = new ModelAndView();
		//查找账单状态list和支付方式list
		List<?> billStatusList = this.reportFormService.queryBillStatusList();
		List<?> payMentList = this.reportFormService.querypayMentList();
		mv.setViewName("page/ipmspage/reportform/accountRecordDetail");
		mv.addObject("payMentList", payMentList);
		mv.addObject("billStatusList", billStatusList);
		mv.addObject("startTime", startTime);
		mv.addObject("endTime", endTime);
		mv.addObject("checkId", checkId);
		mv.addObject("recordType", recordType);
		mv.addObject("accountStatus", accountStatus);
		mv.addObject("payMent", payMent);
		mv.addObject("recordUser", recordUser);
		mv.addObject("projectType", projectType);
		return mv;
	}
	
	//入账明细表
	@RequestMapping("/accountRecordDetailData.do")
	public ModelAndView accountRecordDetailData(HttpServletRequest request, HttpServletResponse response,String startTime, String endTime, String checkId,String recordType, String accountStatus,String payMent,String recordUser ,String projectType) throws Exception {
		ModelAndView mv = new ModelAndView();
		Pagination pagination = SqlBuilder.buildPagination(request);
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		// 查找入账明细
		List<?> accountRecordList = this.reportFormService.accountRecordDetailByCon(pagination, startTime, endTime, branchId, checkId, recordType, accountStatus, payMent, recordUser, projectType );
		mv.addObject("accountRecordList", accountRecordList);
		mv.addObject("pagination", pagination);
		/*// 查找所有的门店传递到页面上去
		List<?> branchList = this.manageService.queryAllOfBranch();
		mv.addObject("branchList", branchList);*/
		//查找入账sum
		List<?> accountRecordSum = this.reportFormService.accountRecordDetailSumByCon( startTime, endTime, branchId, checkId, recordType, accountStatus, payMent, recordUser, projectType );
		mv.addObject("accountRecordSum", accountRecordSum);
		mv.addObject("startTime", startTime);
		mv.addObject("endTime", endTime);
		mv.addObject("checkId", checkId);
		mv.addObject("recordType", recordType);
		mv.addObject("accountStatus", accountStatus);
		mv.addObject("payMent", payMent);
		mv.addObject("recordUser", recordUser);
		mv.addObject("projectType", projectType);
		mv.setViewName("page/ipmspage/reportform/accountRecordDetailData");
		return mv;
	}
	
	//入账汇总
	@RequestMapping("/accountInTotal.do")
	public ModelAndView accountInTotal(HttpServletRequest request, HttpServletResponse response,String startTime,String endTime) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/ipmspage/reportform/accountRecordTotal");
		mv.addObject("startTime", startTime);
		mv.addObject("endTime", endTime);
		return mv;
	}

	//入账汇总
	@RequestMapping("/accountInTotalData.do")
	public ModelAndView accountInTotalData(HttpServletRequest request, HttpServletResponse response,String startTime,String endTime) throws Exception {
		ModelAndView mv = new ModelAndView();
		Pagination pagination = SqlBuilder.buildPagination(request);
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		// 查找入账明细
		List<?> accountRecordList = this.reportFormService.accountRecordTotal(pagination, startTime.trim(), endTime.trim(), branchId.trim());
		accountRecordList.remove(accountRecordList.size() - 1);
		mv.addObject("accountRecordList", accountRecordList);
		mv.addObject("pagination", pagination);
		//查找入账sum
		List<?> accountRecordSum= this.reportFormService.accountRecordTotalSum(startTime, endTime, branchId);
		mv.addObject("accountRecordSum", accountRecordSum);
		mv.addObject("startTime", startTime);
		mv.addObject("endTime", endTime);
		mv.setViewName("page/ipmspage/reportform/accountRecordTotalData");
		return mv;
	}
	
	//冲减明细
	@RequestMapping("/cancelOutDetail.do")
	public ModelAndView cancelOutDetail(HttpServletRequest request, HttpServletResponse response,String startTime,String endTime,String recordUser) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/ipmspage/reportform/cancelOutDetail");
		mv.addObject("startTime",startTime);
		mv.addObject("endTime",endTime);
		mv.addObject("recordUser",recordUser);
		return mv;
	}
	
	//冲减明细
	@RequestMapping("/cancelOutDetailData.do")
	public ModelAndView cancelOutDetailData(HttpServletRequest request, HttpServletResponse response,String startTime,String endTime,String recordUser) throws Exception {
		ModelAndView mv = new ModelAndView();
		Pagination pagination = SqlBuilder.buildPagination(request);
		// 查找冲减明细
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		List<?> cancelOutDetailList = this.reportFormService.cancelOutDetail(pagination,startTime, endTime, recordUser, branchId);
		mv.addObject("cancelOutDetailList", cancelOutDetailList);
		/*List<?> cancelOutSum = this.reportFormService.cancelOutSum(pagination,startTime, endTime, recordUser, branchId);
		mv.addObject("cancelOutSum", cancelOutSum);*/
		mv.addObject("pagination", pagination);
		mv.addObject("startTime",startTime);
		mv.addObject("endTime",endTime);
		mv.addObject("recordUser",recordUser);
		mv.setViewName("page/ipmspage/reportform/cancelOutDetailData");
		return mv;
	}
	
	//商品售卖明细
	@RequestMapping("/goodsSaleDetail.do")
	public ModelAndView goodsSaleDetail(HttpServletRequest request, HttpServletResponse response,String startTime,String endTime,String recordUser,String goodsName,String goodsKind) throws Exception {
		ModelAndView mv = new ModelAndView();
		//查找商品类型加载到condition
		List<?> goodsType =  this.reportFormService.queryAllOfGoodsType();
		mv.addObject("goodsType", goodsType);
		mv.setViewName("page/ipmspage/reportform/goodsSaleDetail");
		mv.addObject("startTime",startTime);
		mv.addObject("endTime",endTime);
		mv.addObject("recordUser",recordUser);
		mv.addObject("goodsName",goodsName);
		mv.addObject("goodsKind",goodsKind);
		return mv;
	}

	//商品售卖明细
	@RequestMapping("/goodsSaleDetailData.do")
	public ModelAndView goodsSaleDetailData(HttpServletRequest request, HttpServletResponse response,String startTime,String endTime,String recordUser,String goodsName,String goodsKind) throws Exception {
		ModelAndView mv = new ModelAndView();
		Pagination pagination = SqlBuilder.buildPagination(request);
		// 查找商品售卖明细
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		List<?> goodsSaleDetailList = this.reportFormService.goodsSaleDetail(pagination,startTime, endTime, recordUser, branchId,goodsName,goodsKind);
		mv.addObject("goodsSaleDetailList", goodsSaleDetailList);
		List<?> goodsSaleSum = this.reportFormService.goodsSaleSum(pagination,startTime, endTime, recordUser, branchId,goodsName,goodsKind);
		mv.addObject("goodsSaleSum", goodsSaleSum);
		mv.addObject("pagination", pagination);
		mv.addObject("startTime",startTime);
		mv.addObject("endTime",endTime);
		mv.addObject("recordUser",recordUser);
		mv.addObject("goodsName",goodsName);
		mv.addObject("goodsKind",goodsKind);
		mv.setViewName("page/ipmspage/reportform/goodsSaleDetailData");
		return mv;
	}
	
	//ttv明细
	@RequestMapping("/ttvDetail.do")
	public ModelAndView ttvDetail(HttpServletRequest request, HttpServletResponse response,String startTime,String endTime,String recordUser,String status) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.addObject("startTime",startTime);
		mv.addObject("endTime",endTime);
		mv.addObject("recordUser",recordUser);
		mv.addObject("status",status);
		mv.setViewName("page/ipmspage/reportform/ttvDetail"); 
		return mv;
	}

	//ttv明细
	@RequestMapping("/ttvDetailData.do")
	public ModelAndView ttvDetailData(HttpServletRequest request, HttpServletResponse response,String startTime,String endTime,String recordUser,String status) throws Exception {
		ModelAndView mv = new ModelAndView();
		Pagination pagination = SqlBuilder.buildPagination(request);
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		List<?> ttvDetailList = this.reportFormService.ttvDetail(pagination,branchId, startTime, endTime, recordUser, status);
		mv.addObject("ttvDetailList", ttvDetailList);
		List<?> ttvDetailSum = this.reportFormService.ttvDetailSum(pagination,branchId, startTime, endTime, recordUser, status);
		mv.addObject("ttvDetailSum", ttvDetailSum);
		mv.addObject("pagination", pagination);
		mv.addObject("startTime",startTime);
		mv.addObject("endTime",endTime);
		mv.addObject("recordUser",recordUser);
		mv.addObject("status",status);
		mv.setViewName("page/ipmspage/reportform/ttvDetailData"); 
		return mv;
	}
	//水电表缴费单
	@RequestMapping("/WaterMeterPayment.do")
	public ModelAndView WaterMeterPayment(HttpServletRequest request, HttpServletResponse response,String startTime,String endTime,String recordUser,String status) throws Exception {
		ModelAndView mv = new ModelAndView();
		//Pagination pagination = SqlBuilder.buildPagination(request);
		// 查水电表缴费表
		//LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		//String branchId = loginUser.getStaff().getBranchId();
		//TODO
		//TODO
		//List<?> ttvDetailList = this.reportFormService.ttvDetail(pagination,branchId, startTime, endTime, recordUser, status);
		//mv.addObject("ttvDetailList", ttvDetailList);
		//mv.addObject("pagination", pagination);
		mv.setViewName("page/ipmspage/reportform/ttvDetail"); 
		return mv;
	}
}
