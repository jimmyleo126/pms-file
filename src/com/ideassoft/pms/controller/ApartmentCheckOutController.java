package com.ideassoft.pms.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ideassoft.bean.CheckOut;
import com.ideassoft.bean.Contrart;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.page.Pagination;
import com.ideassoft.crm.service.CommonService;
import com.ideassoft.pms.service.ApartmentCheckOutService;
import com.ideassoft.pms.service.RoomService;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.ReflectUtil;
import com.ideassoft.util.RequestUtil;

@Transactional
@Controller
public class ApartmentCheckOutController {
	
	@Autowired
	private ApartmentCheckOutService apartmentCheckOutService;
	
	@Autowired
	private RoomService roomService;
	
	@Autowired
	CommonService commonService;
	
	@RequestMapping("/apartmentCheckOut.do")
	public ModelAndView apartmentCheckOut(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/ipmspage/leftmenu/apartmentcheckout/apartmentcheckout");
		return mv;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/queryApartmentCheckOut.do")
	public ModelAndView queryApartmentOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		Pagination pagination = (Pagination) ReflectUtil.setBeanFromRequest(request, Pagination.class);
		if(pagination != null && pagination.getPageNum() == null){
			pagination.setPageNum(1);
			pagination.setRows(17);
		}
		String orderuser = request.getParameter("orderuser");
		String ordertime = request.getParameter("ordertime");
		String roomId = request.getParameter("roomId");
		String dispose = request.getParameter("dispose");
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		List<CheckOut> list = apartmentCheckOutService.findBySQLWithPagination("apartmentCheckOut", new String []{branchId, orderuser, ordertime, roomId, dispose}, pagination, true);
		mv.addObject("list", list);
		mv.addObject("orderuser", orderuser);
		mv.addObject("ordertime", ordertime);
		mv.addObject("roomId", roomId);
		mv.addObject("pagination", pagination);
		mv.setViewName("page/ipmspage/leftmenu/apartmentcheckout/apartmentcheckoutinfo");
		return mv;
	}
	
	@RequestMapping("/checkApartmentCheckOut.do")
	public void checkApartmentReserved(HttpServletRequest request, HttpServletResponse response, String Id) throws Exception {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String[] Ids = Id.split(",");
		String contractId = Ids[0];
		String checkOutId = Ids[1];
		CheckOut checkOut = (CheckOut) apartmentCheckOutService.findOneByProperties(CheckOut.class, "checkOutId", checkOutId);
		checkOut.setDispose("1");
		checkOut.setRecordUser(loginUser.getStaff().getStaffId());
		Contrart contrart = (Contrart) apartmentCheckOutService.findOneByProperties(Contrart.class, "contrartId", contractId);
		contrart.setStatus("2");
//		Room room = (Room) apartmentCheckOutService.findOneByProperties(Room.class, "roomKey.branchId", checkOut.getBranchId(), "roomKey.roomId", checkOut.getRoomId());
//		room.getRoomKey().setStatus("0");
		apartmentCheckOutService.update(checkOut);
		apartmentCheckOutService.update(contrart);
		roomService.upateroomstatus(checkOut.getBranchId(), checkOut.getRoomId(), "0");
//		apartmentCheckOutService.update(room);
		
		commonService.UpDatePath("CheckOut");
		
        String result = "{\"code\":\"1\"}";
        JSONUtil.responseJSON(response, result);
	}
	
}
