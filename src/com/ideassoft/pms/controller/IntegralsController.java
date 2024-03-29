package com.ideassoft.pms.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ideassoft.bean.LoginLog;
import com.ideassoft.bean.Shift;
import com.ideassoft.bean.SaleLog;
import com.ideassoft.bean.OperateLog;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.jdbc.SqlBuilder;
import com.ideassoft.core.page.Pagination;
import com.ideassoft.pms.service.IntegralsService;
import com.ideassoft.util.RequestUtil;



@Transactional
@Controller
public class IntegralsController {
	
	@Autowired
	private IntegralsService integralsService;
	
	/**
	 * 登录日志
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("LoginLog.do")
	public ModelAndView Loginlog(HttpServletRequest request, HttpServletResponse response) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String logname = request.getParameter("logname");
		String source = request.getParameter("source");
		String status = request.getParameter("status");
		String starttime = request.getParameter("starttime") == null ? sdf.format(new Date()) : request.getParameter("starttime");
		String endtime = request.getParameter("endtime") == null ? sdf.format(new Date()) : request.getParameter("endtime");
		ModelAndView mv = new ModelAndView();
		Pagination pagination = SqlBuilder.buildPagination(request);
		@SuppressWarnings("unchecked")
		List<LoginLog> list = integralsService.findBySQLWithPagination("loginlog", new String []{logname, source, status, starttime, endtime}, pagination, true);
		mv.addObject("list", list);
		mv.addObject("pagination", pagination);
		mv.addObject("logname", logname);
		mv.addObject("source", source);
		mv.addObject("status", status);
		mv.addObject("starttime", starttime);
		mv.addObject("endtime", endtime);
        mv.setViewName("page/ipmspage/integral/loginlog");
        return mv;
	}
	
	/**
	 * 交接班日志
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("shiftLog.do")
	public ModelAndView shiftLog(HttpServletRequest request, HttpServletResponse response) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String lastshift = request.getParameter("lastshift");
		String currshift = request.getParameter("currshift");
		String lastuser = request.getParameter("lastuser");
		String curruser = request.getParameter("curruser");
		String starttime = request.getParameter("starttime") == null ? sdf.format(new Date()) : request.getParameter("starttime");
		String endtime = request.getParameter("endtime") == null ? sdf.format(new Date()) : request.getParameter("endtime");
		ModelAndView mv = new ModelAndView();
		Pagination pagination = SqlBuilder.buildPagination(request);
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId =  loginUser.getStaff().getBranchId();
		@SuppressWarnings("unchecked")
		List<Shift> list = integralsService.findBySQLWithPagination("shiftlog", new String []{branchId, lastshift, currshift, lastuser, curruser, starttime, endtime}, pagination, true);
		mv.addObject("list", list);
		mv.addObject("pagination", pagination);
		mv.addObject("lastshift", lastshift);
		mv.addObject("currshift", currshift);
		mv.addObject("lastuser", lastuser);
		mv.addObject("curruser", curruser);
		mv.addObject("starttime", starttime);
		mv.addObject("endtime", endtime);
        mv.setViewName("page/ipmspage/integral/shiftlog");
        return mv;
	}
	
	/**
	 * 售卖日志
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("saleLog.do")
	public ModelAndView SaleLog(HttpServletRequest request, HttpServletResponse response) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String debts = request.getParameter("debts");
		String goodsname = request.getParameter("goodsname");
		String paytype = request.getParameter("paytype");
		String starttime = request.getParameter("starttime") == null ? sdf.format(new Date()) : request.getParameter("starttime");
		String endtime = request.getParameter("endtime") == null ? sdf.format(new Date()) : request.getParameter("endtime");
		ModelAndView mv = new ModelAndView();
		Pagination pagination = SqlBuilder.buildPagination(request);
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId =  loginUser.getStaff().getBranchId();
		@SuppressWarnings("unchecked")
		List<SaleLog> list = integralsService.findBySQLWithPagination("salelog", new String []{branchId, debts, goodsname, paytype, starttime, endtime}, pagination, true);
		mv.addObject("list", list);
		mv.addObject("pagination", pagination);
		mv.addObject("debts", debts);
		mv.addObject("goodsname", goodsname);
		mv.addObject("paytype", paytype);
		mv.addObject("starttime", starttime);
		mv.addObject("endtime", endtime);
        mv.setViewName("page/ipmspage/integral/salelog");
        return mv;
	}
	
	/**
	 * 操作日志
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("operateLog.do")
	public ModelAndView OperateLog(HttpServletRequest request, HttpServletResponse response) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String opertype = request.getParameter("opertype");
		String recorduser = request.getParameter("recorduser");
		String starttime = request.getParameter("starttime") == null ? sdf.format(new Date()) : request.getParameter("starttime");
		String endtime = request.getParameter("endtime") == null ? sdf.format(new Date()) : request.getParameter("endtime");
		ModelAndView mv = new ModelAndView();
		Pagination pagination = SqlBuilder.buildPagination(request);
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId =  loginUser.getStaff().getBranchId();
		@SuppressWarnings("unchecked")
		List<OperateLog> list = integralsService.findBySQLWithPagination("operateLog", new String []{branchId, opertype, recorduser, starttime, endtime}, pagination, true);
		mv.addObject("list", list);
		mv.addObject("pagination", pagination);
		mv.addObject("opertype", opertype);
		mv.addObject("recorduser", recorduser);
		mv.addObject("starttime", starttime);
		mv.addObject("endtime", endtime);
        mv.setViewName("page/ipmspage/integral/operatelog");
        return mv;
	}
	
	@RequestMapping("roomstatusLog.do")
	public ModelAndView RoomstatusLog(HttpServletRequest request, HttpServletResponse response) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String recorduser = request.getParameter("recorduser");
		String starttime = request.getParameter("starttime") == null ? sdf.format(new Date()) : request.getParameter("starttime");
		String endtime = request.getParameter("endtime") == null ? sdf.format(new Date()) : request.getParameter("endtime");
		ModelAndView mv = new ModelAndView();
		Pagination pagination = SqlBuilder.buildPagination(request);
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId =  loginUser.getStaff().getBranchId();
		@SuppressWarnings("unchecked")
		List<OperateLog> list = integralsService.findBySQLWithPagination("roomstatus", new String []{branchId, recorduser, starttime, endtime}, pagination, true);
		mv.addObject("list", list);
		mv.addObject("pagination", pagination);
		mv.addObject("recorduser", recorduser);
		mv.addObject("starttime", starttime);
		mv.addObject("endtime", endtime);
        mv.setViewName("page/ipmspage/integral/roomstatuslog");
        return mv;
	}
}
