package com.ideassoft.pms.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hsqldb.lib.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ideassoft.bean.Bill;
import com.ideassoft.bean.Check;
import com.ideassoft.bean.Member;
import com.ideassoft.bean.OperateLog;
import com.ideassoft.bean.Order;
import com.ideassoft.bean.Room;
import com.ideassoft.bean.RoomType;
import com.ideassoft.bean.wrapper.LoginGetShift;
import com.ideassoft.bean.wrapper.MultiQueryCheck;
import com.ideassoft.bean.wrapper.MultiQueryOrder;
import com.ideassoft.core.ajax.AjaxResult;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.jdbc.SqlBuilder;
import com.ideassoft.core.page.Pagination;
import com.ideassoft.pms.service.OrderService;
import com.ideassoft.pms.service.RoomService;
import com.ideassoft.util.ChineseCharacterUtil;
import com.ideassoft.util.DateUtil;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.MD5Util;
import com.ideassoft.util.RequestUtil;

@Transactional
@Controller
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	@Autowired
	private RoomService roomService;
	
	@RequestMapping("/orderInfoAll.do")
	public ModelAndView orderInfoAll(HttpServletRequest request,HttpServletResponse reponse, String movement) throws Exception {
		ModelAndView mv = new ModelAndView();
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		Pagination pagination = SqlBuilder.buildPagination(request);
		String guarantee = "";
		String limited = "";
		String status = "";
		List<?> orderinfo = new ArrayList<Object>();
		
		if (movement == null) {
			movement = "";
		}
		if (movement.equals("3")) {
			guarantee = "2";
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
			limited = sdf.format(date);
			orderinfo = orderService.findBySQLWithPagination("queryorderlimited", new String []{branchId, guarantee, status, limited}, pagination, true);
		} else {
			if (movement.equals("1")) {
				guarantee = "2";
			} if (movement.equals("2")) {
				guarantee = "2";
				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
				limited = sdf.format(date);
			} if (movement.equals("4")) {
				status = "2";
			}
			orderinfo = orderService.findBySQLWithPagination("queryorderinfo", new String []{branchId, guarantee, status, limited}, pagination, true);
		}
		
		mv.addObject("pagination", pagination);
		mv.addObject("orderinfo", orderinfo);
		mv.setViewName("page/ipmspage/order/orderinfo");
        return mv;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/loadOrderData.do")
	public void loadOrderData(HttpServletRequest request, HttpServletResponse response, String orderId) {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		String sql = "loadOrderData";
		List<Map<String, Object>> list = orderService.findBySQL(sql,new String[]{orderId, orderId, branchId}, true);
		String advancecard = list.get(0).get("advancecard").toString();
		String advancecash = list.get(0).get("advancecash").toString();
		double a = Double.valueOf(advancecard) + Double.valueOf(advancecash);
		list.get(0).put("ADVANCE", a);
		JSONUtil.responseJSON(response, list);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/checkIn.do")
		public void checkIn(HttpServletRequest request, HttpServletResponse response, String checkinUser, String orderId, String roomId) throws UnknownHostException{
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);	
		String branchId = loginUser.getStaff().getBranchId();
		Order orderdata = (Order) orderService.findOneByProperties(Order.class, "orderId", orderId, "branchId", branchId);
		List<Check> checkdata = orderService.findByProperties(Check.class, "checkId", orderId, "branchId", branchId);
		Room roomdata = (Room) orderService.findOneByProperties(Room.class, "roomKey.roomId", roomId, "roomKey.branchId", branchId);
		
		if (checkdata.size() > 0) {
			JSONUtil.responseJSON(response, new AjaxResult(1, "已经办理过入住了!"));
		} else {
			Double a = 0.00;
			Date date = new Date();
			Bill bill = new Bill();
			LoginGetShift shift = (LoginGetShift) request.getSession(true).getAttribute("loginGetShift");
			String billId = DateUtil.currentDate("yyMMdd") + branchId + orderService.getSequence("SEQ_NEW_BILL");
			bill.setBillId(billId);
			bill.setBranchId(branchId);
			bill.setCheckId(orderId);
			bill.setProjectId("2004");
			bill.setProjectName("预存");
			bill.setDescribe("预存");
			bill.setCost(a);
			if (orderdata.getAdvanceCash() != null) {
				bill.setPayment("1");
			} else if (orderdata.getAdvanceCard() != null) {
				bill.setPayment("2");
			} else {
				bill.setPayment("0");
			}
			if (orderdata.getAdvanceCash() == null) {
				orderdata.setAdvanceCash(a);
			}
			if (orderdata.getAdvanceCard() == null) {
				orderdata.setAdvanceCard(a);
			}
			bill.setPay(orderdata.getAdvanceCash() + orderdata.getAdvanceCard());
			bill.setStatus("1");
			bill.setRecordTime(date);
			bill.setRecordUser(loginUser.getStaff().getStaffId());
			bill.setShift(shift.getShiftId());
			bill.setCashBox(shift.getCashbox());
			
			Check check = new Check();
			check.setCheckId(orderId);
			check.setBranchId(branchId);
			check.setCheckinTime(date);
			check.setRoomPrice(orderdata.getRoomPrice());
			check.setRpId(orderdata.getRpId());
			check.setCheckoutTime(orderdata.getLeaveTime());
			check.setCheckUser(checkinUser);
			check.setRoomId(roomId);
			check.setRoomType(roomdata.getRoomType());
			check.setDeposit(a);
			check.setTtv(a);
			check.setCost(a);
			check.setPay(orderdata.getAdvanceCash() + orderdata.getAdvanceCard());
			check.setAccountFee(a);
			check.setTotalFee(a);
			check.setStatus("1");
			check.setRecordTime(date);
			check.setRecordUser(loginUser.getStaff().getStaffId());
			check.setRemark(orderdata.getRemark());
			orderdata.setStatus("3");
			orderdata.setUserCheckin(checkinUser);
			
			OperateLog operlog = new OperateLog();
			operlog.setBranchId(branchId);
			operlog.setLogId(DateUtil.currentDate("yyMMdd") + branchId + orderService.getSequence("SEQ_OPERATELOG_ID"));
			String operid = InetAddress.getLocalHost().toString();//IP地址
			operid = (String) operid.subSequence(operid.indexOf("/")+1, operid.length());
			operlog.setOperIp(operid);
			operlog.setOperType("order");
			operlog.setOperModule("入住操作");
			operlog.setContent("订单号" + orderId + "已经入住");
			operlog.setRecordUser(loginUser.getStaff().getStaffId());
			operlog.setRecordTime(date);
			
			roomdata.getRoomKey().setStatus("2");
			try{
				orderService.save(check);
				orderService.update(orderdata);
				orderService.save(operlog);
				roomService.upateroomstatus(branchId, roomId, "2");
				orderService.update(roomdata);
				orderService.save(bill);
			}catch(Exception e){
				e.printStackTrace();
			}
			JSONUtil.responseJSON(response, new AjaxResult(0, "入住成功!"));
		}
	}
	
	@RequestMapping("/selectcleanroom.do")
	public void selectcleanroom(HttpServletRequest request, HttpServletResponse response) {
 		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		List<?> rt = orderService.findByProperties(RoomType.class, "theme", "1");
		List<?> list = orderService.findBySQL("queryroomempty", new String[]{branchId}, true);
		Map<String, List<Object>> listinfo = new HashMap<String, List<Object>>();
		
		for (int j = 0; j<rt.size(); j++) {
			List<Object> listdata = new ArrayList<Object>();
			for (int i=0; i<list.size(); i++) {
				if (((RoomType) rt.get(j)).getRoomType().toString().equals(((Map<?, ?>)list.get(i)).get("ROOM_TYPE"))) {
					listdata.add(((Map<?, ?>)list.get(i)).get("ROOMID"));
				}
			}
			listinfo.put(((RoomType) rt.get(j)).getRoomName().toString(),listdata);
		}	
		JSONUtil.responseJSON(response, listinfo);
	}
	
	@RequestMapping("/queryOrderData.do")
	public ModelAndView queryOrderData(HttpServletRequest request, HttpServletResponse response, MultiQueryOrder order) throws Exception {
		ModelAndView mv = new ModelAndView();
		Pagination pagination = SqlBuilder.buildPagination(request);
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		List<?> orderinfo = orderService.queryOrderData(branchId, order, pagination);
		mv.addObject("orderinfo", orderinfo);
		mv.addObject("pagination", pagination);
		mv.setViewName("/page/ipmspage/order/orderinfo");
		return mv;
	}
	
	//取消订单
	@RequestMapping("/cancleorder.do")
	public void cancleorder(HttpServletRequest request, HttpServletResponse response, String orderId) throws UnknownHostException {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);	
		String branchId = loginUser.getStaff().getBranchId();
		Order orderdata = (Order) orderService.findOneByProperties(Order.class, "orderId", orderId);
		orderdata.setStatus("0");
		
		OperateLog operlog = new OperateLog();
		operlog.setBranchId(branchId);
		operlog.setLogId(DateUtil.currentDate("yyMMdd") + branchId + orderService.getSequence("SEQ_OPERATELOG_ID"));
		String operid = InetAddress.getLocalHost().toString();//IP地址
		operid = (String) operid.subSequence(operid.indexOf("/")+1, operid.length());
		operlog.setOperIp(operid);
		operlog.setOperType("order");
		operlog.setOperModule("取消订单");
		operlog.setContent("订单号" + orderId + "被取消");
		operlog.setRecordUser(loginUser.getStaff().getStaffId());
		operlog.setRecordTime(new Date());
		
		try{
			orderService.save(orderdata);
			orderService.save(operlog);
		}catch(Exception e){
			e.printStackTrace();
		}
		JSONUtil.responseJSON(response, new AjaxResult(0, "取消成功!"));
	}
	
	//根据选择的房型展示价格
	@RequestMapping("/getRoomPrice.do")
	public void getRoomPrice(HttpServletRequest request, HttpServletResponse response, String orderId, String roomtype) {
 		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		Order orderdata = (Order) orderService.findOneByProperties(Order.class, "orderId", orderId);
		Member memberrank = (Member) orderService.findOneByProperties(Member.class, "memberId", orderdata.getOrderUser());
		List<?> roomprice = orderService.findBySQL("queryroompirce", new String[]{branchId, roomtype, orderdata.getTheme(),
				memberrank.getMemberRank()}, true);
		JSONUtil.responseJSON(response, roomprice);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/editorderinfo.do")
	public void editorderinfo(HttpServletRequest request, HttpServletResponse response, Order order) throws UnknownHostException {
 		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
 		String branchId = loginUser.getStaff().getBranchId();
 		
		Order orderdata = (Order) orderService.findOneByProperties(Order.class, "orderId", order.getOrderId());
		orderdata.setRoomType(order.getRoomType());
		orderdata.setRoomPrice(order.getRoomPrice());
		orderdata.setGuarantee(order.getGuarantee());
		orderdata.setRemark(order.getRemark());
		orderdata.setLimited(order.getLimited());
		
		OperateLog operlog = new OperateLog();
		operlog.setBranchId(branchId);
		operlog.setLogId(DateUtil.currentDate("yyMMdd") + branchId + orderService.getSequence("SEQ_OPERATELOG_ID"));
		String operid = InetAddress.getLocalHost().toString();//IP地址
		operid = (String) operid.subSequence(operid.indexOf("/")+1, operid.length());
		operlog.setOperIp(operid);
		operlog.setOperType("order");
		operlog.setOperModule("修改订单");
		operlog.setContent("订单号" + orderdata.getOrderId() + "被修改");
		operlog.setRecordUser(loginUser.getStaff().getStaffId());
		operlog.setRecordTime(new Date());
		
		try{
			orderService.update(orderdata);
			orderService.save(operlog);
		}catch(Exception e){
			e.printStackTrace();
		}
		JSONUtil.responseJSON(response, new AjaxResult(0, "修改成功!"));
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/changeorderstatus.do")
	public void changeorderstatus(HttpServletRequest request, HttpServletResponse response, String orderId) throws UnknownHostException {
 		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
 		String branchId = loginUser.getStaff().getBranchId();
		Order orderdata = (Order) orderService.findOneByProperties(Order.class, "orderId", orderId);
		orderdata.setStatus("2");
	
		OperateLog operlog = new OperateLog();
		operlog.setBranchId(branchId);
		operlog.setLogId(DateUtil.currentDate("yyMMdd") + branchId + orderService.getSequence("SEQ_OPERATELOG_ID"));
		String operid = InetAddress.getLocalHost().toString();//IP地址
		operid = (String) operid.subSequence(operid.indexOf("/")+1, operid.length());
		operlog.setOperIp(operid);
		operlog.setOperType("order");
		operlog.setOperModule("修改订单");
		operlog.setContent("订单号" + orderdata.getOrderId() + "未到");
		operlog.setRecordUser(loginUser.getStaff().getStaffId());
		operlog.setRecordTime(new Date());
		
		try{
			orderService.save(orderdata);;
			orderService.save(operlog);
		}catch(Exception e){
			e.printStackTrace();  
		}
		JSONUtil.responseJSON(response, new AjaxResult(0, "设为未到成功!"));
	}
}

