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

import com.ideassoft.bean.Member;
import com.ideassoft.bean.Reserved;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.notifier.service.impl.NotifyService;
import com.ideassoft.core.page.Pagination;
import com.ideassoft.crm.service.CommonService;
import com.ideassoft.pms.service.ApartmentReservedService;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.ReflectUtil;
import com.ideassoft.util.RequestUtil;

@Transactional
@Controller
public class ApartmentReservedController {
	
	@Autowired
	private ApartmentReservedService apartmentReservedService;
	
	@Autowired
	private NotifyService notifyService;
	
	@Autowired
	CommonService commonService;
	
	@RequestMapping("/apartmentReserved.do")
	public ModelAndView apartmentReserved(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/ipmspage/leftmenu/apartmentreserved/apartmentreserved");
		return mv;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/queryApartmentReserved.do")
	public ModelAndView queryApartmentOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		Pagination pagination = (Pagination) ReflectUtil.setBeanFromRequest(request, Pagination.class);
		if(pagination != null && pagination.getPageNum() == null){
			pagination.setPageNum(1);
			pagination.setRows(17);
		}
		String memberName = request.getParameter("memberName");
		String mobile = request.getParameter("mobile");
		String reservedTime = request.getParameter("reservedTime");
		List<Reserved> list = apartmentReservedService.findBySQLWithPagination("reservedApartment", new String []{branchId, memberName, mobile, reservedTime}, pagination, true);
		mv.addObject("list", list);
		mv.addObject("orderuser", memberName);
		mv.addObject("mobile", mobile);
		mv.addObject("ordertime", reservedTime);
		mv.addObject("pagination", pagination);
		mv.setViewName("page/ipmspage/leftmenu/apartmentreserved/apartmentreservedinfo");
		return mv;
	}
	
	@RequestMapping("/checkApartmentReserved.do")
	public void checkApartmentReserved(HttpServletRequest request, HttpServletResponse response, String reservedId, String status) throws Exception {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Reserved reserved = (Reserved) apartmentReservedService.findOneByProperties(Reserved.class, "reservedId", reservedId);
		Member member = (Member) apartmentReservedService.findById(Member.class, reserved.getReservedPerson());
		reserved.setStatus(status);
		reserved.setRecordUser(loginUser.getStaff().getStaffId());
		apartmentReservedService.update(reserved);
		
		commonService.UpDatePath("Reserved");
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("会员", member.getMemberName());
		map.put("预约", "成功");
		//notifyService.happenSendSms(null,null,null,"123123",map,member.getMobile(),member.getMemberRank());
        String result = "{\"code\":\"1\"}";
        JSONUtil.responseJSON(response, result);
	}
	
}
