package com.ideassoft.pms.controller;

import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.ideassoft.bean.Bill;
import com.ideassoft.bean.Branch;
import com.ideassoft.bean.ExceptionMessage;
import com.ideassoft.bean.Goods;
import com.ideassoft.bean.LoginLog;
import com.ideassoft.bean.Member;
import com.ideassoft.bean.MemberAccount;
import com.ideassoft.bean.Order;
import com.ideassoft.bean.PettyCash;
import com.ideassoft.bean.Recording;
import com.ideassoft.bean.RoomStatus;
import com.ideassoft.bean.SaleLog;
import com.ideassoft.bean.Shift;
import com.ideassoft.bean.Staff;
import com.ideassoft.bean.SysParam;
import com.ideassoft.bean.WorkBillDetail;
import com.ideassoft.bean.wrapper.LoginGetShift;
import com.ideassoft.core.ajax.AjaxResult;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.constants.SystemConstants;
import com.ideassoft.core.jdbc.SqlBuilder;
import com.ideassoft.core.page.Pagination;
import com.ideassoft.pms.service.LeftmenuService;
import com.ideassoft.pms.service.RoomService;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.MD5Util;
import com.ideassoft.util.RequestUtil;

@Transactional
@Controller
public class LeftmenuController {
	@Autowired
	private LeftmenuService LeftmenuService;
	
	@Autowired
	private RoomService roomService;

	@RequestMapping("/orderNew.do")
	public ModelAndView orderCheckIn(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginuser.getStaff().getBranchId();
		String themetype = null;
		if (branchid.startsWith("1")) {
			themetype = "1";
		} else if (branchid.startsWith("2")) {
			themetype = "1";
		}
		List<?> roomtype = LeftmenuService.getRoomtype();
		List<?> theme = LeftmenuService.getTheme();
		mv.setViewName("page/ipmspage/leftmenu/order/order");
		request.setAttribute("roomtype", roomtype);
		request.setAttribute("themetype", themetype);
		request.setAttribute("theme", theme);
		return mv;
	}

	@RequestMapping("/memberSelect.do")
	public void memberSelect(HttpServletRequest request, HttpServletResponse response, String Mnumber) throws Exception {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		List<?> memberdata = LeftmenuService.getMemberdata(Mnumber);
		/*String rpId = ((Map<?, ?>) memberdata.get(0)).get("RPID")==null ?"":((Map<?, ?>) memberdata.get(0)).get("RPID").toString();*/
		/*String rpId = ((Map<?, ?>) memberdata.get(0)).get("RPID").toString();*/
		String branchid = loginuser.getStaff().getBranchId();
		String a = null;
		if (null == memberdata || memberdata.size() == 0) {
			a = "未查询到相关会员信息或该会员已失效，请先注册会员！";
		}else if((((Map<?, ?>) memberdata.get(0)).get("RPID").toString()).equals("")){
			a = "房价参数未配置，请先前往后台管理系统至少进行一次房价申请！";
		}else {
			String rpId = ((Map<?, ?>) memberdata.get(0)).get("RPID").toString();
			List<?> roomprice = LeftmenuService.getRoomprice(rpId,branchid);
			if(null == roomprice || roomprice.size() == 0){
				a = "当前系统门店房价未配置或在审核中，请先前往后台管理系统进行配置！";
			}
		}
	
		JSONUtil.responseJSON(response, new AjaxResult(1, a));
	}

	@RequestMapping("/judgeUser.do")
	public ModelAndView judgeUser(HttpServletRequest request, HttpServletResponse response, String Mnumber)
			throws Exception {
		ModelAndView mv = new ModelAndView();
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		List<?> memberdata = LeftmenuService.getMemberdata(Mnumber);
		String rpId = ((Map<?, ?>) memberdata.get(0)).get("RPID").toString();
		String branchid = loginuser.getStaff().getBranchId();
		List<?> roomprice = LeftmenuService.getRoomprice(rpId,branchid);
		List<?> guarantee = LeftmenuService.getGuarantee();
		mv.setViewName("page/ipmspage/leftmenu/order/judgeusercheckin");// 二级页面
		request.setAttribute("memberdata", memberdata);
		request.setAttribute("roomprice", roomprice);
		request.setAttribute("guarantee", guarantee);
		request.setAttribute("rpId", rpId);
		return mv;
	}

	@RequestMapping("/roomSelect.do")
	public ModelAndView roomSelect(HttpServletRequest request, HttpServletResponse response, String theme,
			String roomtype, String roomacount) throws Exception {
		ModelAndView mv = new ModelAndView();
		List<?> roomId = LeftmenuService.getRoomid(theme, roomtype);
		List<?> roomTypename = LeftmenuService.getTypename(roomtype);
		String roomtypename = ((Map<?, ?>) roomTypename.get(0)).get("room_name").toString();
		mv.setViewName("page/ipmspage/leftmenu/order/roomselect");
		request.setAttribute("roomId", roomId);
		request.setAttribute("roomtypename", roomtypename);
		request.setAttribute("roomacount", roomacount);
		return mv;
	}

	@RequestMapping("/scoreExchange.do")
	public ModelAndView scoreExchange(HttpServletRequest request, HttpServletResponse response, String MemberId,
			String sjscore) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/ipmspage/leftmenu/order/scoreexchage");
		SysParam sysParamscore = ((SysParam) (LeftmenuService.findOneByProperties(SysParam.class, "paramType", "SCORE")));
		SysParam sysParammoney = ((SysParam) (LeftmenuService.findOneByProperties(SysParam.class, "paramType", "MONEY")));
		MemberAccount memberscore = ((MemberAccount) (LeftmenuService.findOneByProperties(MemberAccount.class,
				"memberId", MemberId)));
		String currintegration = memberscore.getCurrIntegration().toString();
		String score = sysParamscore.getContent().toString();
		String money = sysParammoney.getContent().toString();
		request.setAttribute("score", score);
		request.setAttribute("money", money);
		request.setAttribute("currintegration", currintegration);
		request.setAttribute("sjscore", sjscore);
		return mv;
	}

	@RequestMapping("/orderRoom.do")
	public void orderRoom(HttpServletRequest request, HttpServletResponse response, String ordertheme,
			String orderroomtype, String orderarrivedate, String orderleavedate, String orderacount, String orderrpid,
			String orderprice, String orderuser, String ordermobile, String orderlimited, String receptionremark,
			String roomremark, String ordercash, String ordercard, String ordervoucher, String orderscore,
			String ordergurantee) throws Exception {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		Member member = ((Member) (LeftmenuService.findOneByProperties(Member.class, "memberName", orderuser)));
		String memberid = member.getMemberId().toString();
		String branchid = loginuser.getStaff().getBranchId();
		Integer len = Integer.valueOf(orderacount);
		DateFormat dff = new SimpleDateFormat("yy/MM/dd");
		Date s = dff.parse((URLDecoder.decode(orderarrivedate, "UTF-8")));
		Date g = dff.parse((URLDecoder.decode(orderleavedate, "UTF-8")));
		Double price = Double.parseDouble(orderprice);
		Double cash;
		Double card;
		Integer score;
		String paymentType ="";
		if (ordercash != null && !ordercash.equals("")) {
			cash = Double.parseDouble(ordercash);
			paymentType ="0,";
		} else {
			cash = null;
		}

		if (ordercard != null && !ordercard.equals("")) {
			card = Double.parseDouble(ordercard);
			paymentType = paymentType + "1,";
		} else {
			card = null;
		}

		if (orderscore != null && !orderscore.equals("")) {
			score = Integer.valueOf(orderscore);
			paymentType = paymentType + "2,";
		} else {
			score = null;
		}
		SysParam sysParamscore = ((SysParam) (LeftmenuService.findOneByProperties(SysParam.class, "paramType", "ORDERSWITCH")));
		String orderswitch = sysParamscore.getContent().toString();
		List<?> roomIds = LeftmenuService.getRoomids(orderswitch,orderroomtype, orderarrivedate, orderleavedate);
		String a = null;
		/*if(roomIds.size()==0){
			a = "暂时没有合适的房间可供预订，请重新选择房型！";
		}else{*/
		ArrayList<String> roomIdsarray = new ArrayList<String>();
		for (int i = 0; i < roomIds.size(); i++) {
			String roomtypename = ((Map<?, ?>) roomIds.get(i)).get("room_id").toString();
			roomIdsarray.add(roomtypename);
		}
	
		for (int i = 0; i < len; i++) {
			String logId = this.LeftmenuService.getSequence("SEQ_NEW_ORDER", null);
			Date day = new Date();
			SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
			String t = df.format(day);
			String datId = t + branchid + logId;
			/*String rd = roomIdsarray.get(i);*/
			Order order = new Order();
			order.setOrderId(datId);
			order.setBranchId(branchid);
			order.setSource("3");
			order.setTheme(ordertheme);
			order.setSaleStaff(staff.getStaffId());
			/*order.setRoomId(rd);*/
			order.setRoomType(orderroomtype);
			order.setOrderUser(memberid);
			order.setMPhone(ordermobile);
			order.setRpId(orderrpid);
			order.setArrivalTime(s);
			order.setLeaveTime(g);
			order.setRoomPrice(price);
			order.setGuarantee(ordergurantee);
			order.setLimited(orderlimited);
			order.setReceptionRemark(receptionremark);
			order.setRoomRemark(roomremark);
			order.setAdvanceCash(cash);
			order.setAdvanceCard(card);
			order.setVoucher(ordervoucher);
			order.setAdvanceScore(score);
			order.setStatus("1");
			order.setRecordUser(staff.getStaffId());
			//支付类型的set值
			if(paymentType.length() > 0){
				order.setPaymentType(paymentType.substring(0,paymentType.length()-1));
			}else{
				order.setPaymentType(paymentType);
			}
			LeftmenuService.save(order);
			//预订更新房态
			RoomStatus roomStatus = ((RoomStatus) (LeftmenuService.findOneByProperties(RoomStatus.class, "branchId",
					branchid, "roomType", orderroomtype)));
			String oldsellnum = roomStatus.getSellnum().toString();
			int v = Integer.parseInt(oldsellnum) - 1;
			Integer newsellnum = Integer.valueOf(v);
			roomStatus.setSellnum(newsellnum);
			roomStatus.setRecordTime(new Date());
			roomStatus.setRecordUser(staff.getStaffId());
			LeftmenuService.save(roomStatus);
		  }
		/*}*/
		JSONUtil.responseJSON(response, new AjaxResult(1, a));
	}

	// List<?> rooms = LeftmenuService.getRooms();
	/*
	 * int rooms [] = new int[]{201,202,203}; List rs= Arrays.asList(users); int
	 * len = rs.size(); Map<String, List> a = new HashMap<String, List>(); List
	 * list= new ArrayList(); list.add(s); list.add(orderacount); a.put("list",
	 * list); Map<String, Integer> results = new HashMap<String, Integer>(); int
	 * n = len;
	 */
	/*
	 * for (String key : a.keySet()) { int fnum = Integer.valueOf(a.get(key));
	 * for (int i = 0; i < fnum; i++) { results.put(key + "_" + i, 1); n--; } }
	 * 
	 * 
	 * int i; int b = Integer.valueOf(orderacount).intValue(); for(i=0;i<b;i++){
	 * 
	 * 
	 * }
	 */

	@RequestMapping("/orderSerach.do")
	public ModelAndView orderSerach(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		/*
		 * Pagination pagination = SqlBuilder.buildPagination(request);
		 * 
		 * @SuppressWarnings("unchecked") List<LoginLog> orderlist =
		 * LeftmenuService.findBySQLWithPagination("ordersearchdata",
		 * pagination, true); List<?> orderData= LeftmenuService.getOrderdata();
		 */
		mv.setViewName("page/ipmspage/leftmenu/ordercheckin/ordersearch");
		/*
		 * mv.addObject("orderlist", orderlist); mv.addObject("pagination",
		 * pagination); request.setAttribute("orderData", orderData);
		 */
		return mv;
	}

	@RequestMapping("/orderData.do")
	public ModelAndView orderData(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		Pagination pagination = SqlBuilder.buildPagination(request);
		LoginUser loginuser = (LoginUser)request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginuser.getStaff().getBranchId();
		if(branchId.equals(SystemConstants.User.BRANCH_ID)){
			branchId = "%";
		}
		@SuppressWarnings("unchecked")
		List<LoginLog> orderlist = LeftmenuService.findBySQLWithPagination("ordersearchdata",new String[]{branchId}, pagination, true);
		//List<?> orderData = LeftmenuService.getOrderdata();
		mv.setViewName("page/ipmspage/leftmenu/ordercheckin/orderdata");
		mv.addObject("orderlist", orderlist);
		mv.addObject("pagination", pagination);
		//request.setAttribute("orderData", orderData);
		return mv;
	}

	@RequestMapping("/orderCondition.do")
	public ModelAndView orderCondition(HttpServletRequest request, String orderid, String orderuser,
			String usercheckin, String mobile, String memberid) throws Exception {
		ModelAndView mv = new ModelAndView();
		Pagination pagination = SqlBuilder.buildPagination(request);
		LoginUser loginuser = (LoginUser)request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginuser.getStaff().getBranchId();
		if(branchId.equals(SystemConstants.User.BRANCH_ID)){
			branchId = "%";
		}
		// @SuppressWarnings("unchecked")
		List<?> orderCondition = LeftmenuService.getOrdercondition(orderid, orderuser, usercheckin, mobile, memberid, branchId,
				pagination);
		mv.setViewName("page/ipmspage/leftmenu/ordercheckin/ordercondition");
		mv.addObject("orderCondition", orderCondition);
		mv.addObject("pagination", pagination);
		return mv;
	}

	@RequestMapping("/goods.do")
	public ModelAndView goods(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		LoginUser loginuser = (LoginUser)request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginuser.getStaff().getBranchId();
		List<?> categoryCondition = LeftmenuService.getCategorycondition(branchid);
		mv.setViewName("page/ipmspage/leftmenu/goodssale/goods");
		request.setAttribute("categoryCondition", categoryCondition);
		return mv;
	}

	@RequestMapping("/osSearch.do")
	public void osSearch(HttpServletRequest request, HttpServletResponse response, String orderid, String orderuser,
			String usercheckin, String mobile, String memberid) throws Exception {
		JSONUtil.responseJSON(response, new AjaxResult(1, null));
	}

	@RequestMapping("/goodsSale.do")
	public ModelAndView goodsSale(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		Pagination pagination = SqlBuilder.buildPagination(request);
		// @SuppressWarnings("unchecked")
		LoginUser loginuser = (LoginUser)request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginuser.getStaff().getBranchId();
		if(branchId.equals(SystemConstants.User.BRANCH_ID)){
			branchId = "%";
		}
		List<?> goodsSale = LeftmenuService.getGoodssale(pagination,branchId);
		mv.setViewName("page/ipmspage/leftmenu/goodssale/goodssale");
		mv.addObject("goodsSale", goodsSale);
		mv.addObject("pagination", pagination);
		return mv;
	}

	@RequestMapping("/gdsaleCondition.do")
	public ModelAndView gdsaleCondition(HttpServletRequest request, HttpServletResponse response, String goodsid,
			String goodsname, String categoryid) throws Exception {
		ModelAndView mv = new ModelAndView();
		Pagination pagination = SqlBuilder.buildPagination(request);
		LoginUser loginuser = (LoginUser)request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginuser.getStaff().getBranchId();
		if(branchId.equals(SystemConstants.User.BRANCH_ID)){
			branchId = "%";
		}
		List<?> gdsaleCondition = LeftmenuService.getGdsalecondition(goodsid, goodsname, categoryid,branchId, pagination);
		mv.setViewName("page/ipmspage/leftmenu/goodssale/gdsalecondition");
		mv.addObject("gdsaleCondition", gdsaleCondition);
		mv.addObject("pagination", pagination);
		return mv;
	}

	@RequestMapping("/gdsPage.do")
	public ModelAndView gdsPage(HttpServletRequest request, String gdsid, String gdsname, String gdsprice)
			throws Exception {
		ModelAndView mv = new ModelAndView();
		List<?> gdsproject = LeftmenuService.getGdsproject();
		List<?> gdsprojectpay = LeftmenuService.getGdsprojectpay();
		LoginUser loginuser = (LoginUser)request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginuser.getStaff().getBranchId();
		if(branchid.equals(SystemConstants.User.BRANCH_ID)){
			branchid = "%";
		}
		List<?> workbill = LeftmenuService.getWorkbill(branchid);
		mv.setViewName("page/ipmspage/leftmenu/goodssale/gdspage");
		request.setAttribute("gdsid", gdsid);
		request.setAttribute("gdsname", gdsname);
		request.setAttribute("gdsprice", gdsprice);
		request.setAttribute("gdsproject", gdsproject);
		request.setAttribute("gdsprojectpay", gdsprojectpay);
		request.setAttribute("workbill", workbill);
		return mv;
	}
	  
	@RequestMapping("/gdsmulPage.do")
	public ModelAndView gdsPage(HttpServletRequest request, String saletype, String newstr) throws Exception {
		ModelAndView mv = new ModelAndView();
		List<?> gdsproject = LeftmenuService.getGdsproject();
		List<?> gdsprojectpay = LeftmenuService.getGdsprojectpay();
		LoginUser loginuser = (LoginUser)request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginuser.getStaff().getBranchId();
		if(branchid.equals(SystemConstants.User.BRANCH_ID)){
			branchid = "%";
		}
		List<?> workbill = LeftmenuService.getWorkbill(branchid);
		mv.setViewName("page/ipmspage/leftmenu/goodssale/gdsmulpage");
		List<Goods> listgoods = new ArrayList<Goods>();
		JSONArray newstrr = new JSONArray(newstr);
		for (int i = 0; i < newstrr.length(); i++) {
			String s = newstrr.getString(i);
			Goods goods = (Goods) LeftmenuService.findOneByProperties(Goods.class, "goodsId", s);
			listgoods.add(goods);
		}
		request.setAttribute("listgoods", listgoods);
		request.setAttribute("saletype", saletype);
		request.setAttribute("gdsproject", gdsproject);
		request.setAttribute("gdsprojectpay", gdsprojectpay);
		request.setAttribute("workbill", workbill);
		// System.out.println();
		return mv;
	}

	@RequestMapping("/gdsroomSelect.do")
	public ModelAndView gdsroomSelect(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		LoginUser loginuser = (LoginUser)request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginuser.getStaff().getBranchId();
		if(branchId.equals(SystemConstants.User.BRANCH_ID)){
			branchId = "%";
		}
		List<?> gdsroomId = LeftmenuService.getGdsroomid(branchId);
		mv.setViewName("page/ipmspage/leftmenu/goodssale/gdsroomselect");
		mv.addObject("gdsroomId", gdsroomId);
		return mv;
	}

	@RequestMapping("/memberSearch.do")
	public ModelAndView memberSearch(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/ipmspage/leftmenu/membersearch/membersearch");
		return mv;
	}

	@RequestMapping("/memberSearchdata.do")
	public ModelAndView memberSearchdata(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/ipmspage/leftmenu/membersearch/memberpage");
		return mv;
	}

	@RequestMapping("/memberJudge.do")
	public void memberJudge(HttpServletRequest request, HttpServletResponse response, String mphone, String mcard)
			throws Exception {
		List<?> mscData = LeftmenuService.getMscdta(mphone, mcard);
		String a = null;
		if (null == mscData || mscData.size() == 0) {
			a = "未查询到相关会员信息！";
		}
		JSONUtil.responseJSON(response, new AjaxResult(1, a));
	}

	@RequestMapping("/msCondition.do")
	public ModelAndView msCondition(HttpServletRequest request, String mphone, String mcard) throws Exception {
		ModelAndView mv = new ModelAndView();
		List<?> mscData = LeftmenuService.getMscdta(mphone, mcard);
		mv.setViewName("page/ipmspage/leftmenu/membersearch/membercondition");
		request.setAttribute("mscData", mscData);
		return mv;
	}

	@RequestMapping("/editmemberData.do")
	public void editmemberData(HttpServletRequest request, HttpServletResponse response, String memberid,
			String mobile, String email, String address, String remark) throws Exception {
		Member member = ((Member) (LeftmenuService.findOneByProperties(Member.class, "memberId", memberid)));
		member.setMobile(mobile);
		member.setEmail(email);
		member.setAddress(address);
		member.setRemark(remark);
		LeftmenuService.save(member);
		JSONUtil.responseJSON(response, new AjaxResult(1, null));
	}

	@RequestMapping("/gdsRoompay.do")
	public void gdsRoompay(HttpServletRequest request, HttpServletResponse response, String gdscheckid,
			String gdsproject, String gdsprojectname, String totalprice, String gdsroomid, String gdsid,
			String totalnumber, String gdsprice) throws Exception {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String branchid = loginuser.getStaff().getBranchId();
		LoginGetShift loginGetShift = (LoginGetShift)request.getSession(true).getAttribute("loginGetShift"); 
		String shiftid = loginGetShift.getShiftId(); 
		String cashbox =loginGetShift.getCashbox();
		String logId = this.LeftmenuService.getSequence("SEQ_NEW_BILL", null);
		Date day = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
		String t = df.format(day);
		String datId = t + branchid + logId;
		Double cost = Double.parseDouble(totalprice);
		Bill bill = new Bill();
		bill.setBillId(datId);
		bill.setBranchId(branchid);
		bill.setCheckId(gdscheckid);
		bill.setProjectId(gdsproject);
		bill.setProjectName(gdsprojectname.trim());
		bill.setDescribe("商品");
		bill.setCost(cost);
		bill.setPay((double) 0);
		bill.setPayment("0");
		bill.setStatus("1");
		bill.setRecordUser(staff.getStaffId());
		bill.setShift(shiftid);
		bill.setCashBox(cashbox);
		LeftmenuService.save(bill);
		Goods goods = ((Goods) (LeftmenuService.findOneByProperties(Goods.class, "goodsId", gdsid)));
		// String goodsname = goods.getGoodsName();
		String categoryid = goods.getCategoryId();
		// GoodsCategory goodscategory = ((GoodsCategory)
		// (LeftmenuService.findOneByProperties(GoodsCategory.class,
		// "categoryId", categoryid)));
		// String categoryname = goodscategory.getCategoryName();
		String salelogId = this.LeftmenuService.getSequence("SEQ_NEW_SALELOG", null);
		String saledatId = t + branchid + salelogId;
		Integer amount = Integer.valueOf(totalnumber);
		Double price = Double.parseDouble(gdsprice);
		SaleLog salelog = new SaleLog();
		salelog.setLogId(saledatId);
		salelog.setBranchId(branchid);
		salelog.setDebts("2");
		salelog.setCheckId(gdscheckid);
		salelog.setRoomId(gdsroomid);
		salelog.setCategoryId(categoryid);
		salelog.setGoodsName(gdsid);
		salelog.setAmount(amount);
		salelog.setPrice(price);
		salelog.setPayType("1");
		salelog.setRecordUser(staff.getStaffId());
		LeftmenuService.save(salelog);
		JSONUtil.responseJSON(response, new AjaxResult(1, null));

	}

	@RequestMapping("/gdsRoompaycash.do")
	public void gdsRoompaycash(HttpServletRequest request, HttpServletResponse response, String gdscheckid,
			String gdsprojectpay, String gdsprojectpayname, String gdsworkbill, String gdsworkbillname,
			String totalprice, String cashpayvalue, String gdsid, String totalnumber, String gdsprice) throws Exception {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String branchid = loginuser.getStaff().getBranchId();
		LoginGetShift loginGetShift = (LoginGetShift)request.getSession(true).getAttribute("loginGetShift"); 
		String shiftid = loginGetShift.getShiftId(); 
		String cashbox =loginGetShift.getCashbox();
		String logId = this.LeftmenuService.getSequence("SEQ_NEW_WORKBILLDETAIL", null);
		Date day = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
		String t = df.format(day);
		String datId = t + branchid + logId;
		Double pay = Double.parseDouble(totalprice);
		WorkBillDetail workbilldetail = new WorkBillDetail();
		workbilldetail.setDetailId(datId);
		workbilldetail.setBranchId(branchid);
		workbilldetail.setProjectId(gdsprojectpay);
		workbilldetail.setProjectName(gdsprojectpayname.trim());
		workbilldetail.setDescribe("商品");
		workbilldetail.setPay(pay);
		workbilldetail.setCost((double) 0);
		workbilldetail.setPayment("1");
		workbilldetail.setStatus("1");
		workbilldetail.setRecordUser(staff.getStaffId());
		workbilldetail.setWorkbillId(gdsworkbill);
		workbilldetail.setShift(shiftid);
		workbilldetail.setCashBox(cashbox);
		LeftmenuService.save(workbilldetail);
		Goods goods = ((Goods) (LeftmenuService.findOneByProperties(Goods.class, "goodsId", gdsid)));
		String categoryid = goods.getCategoryId();
		String salelogId = this.LeftmenuService.getSequence("SEQ_NEW_SALELOG", null);
		String saledatId = t + branchid + salelogId;
		Integer amount = Integer.valueOf(totalnumber);
		Double price = Double.parseDouble(gdsprice);
		SaleLog salelog = new SaleLog();
		salelog.setLogId(saledatId);
		salelog.setBranchId(branchid);
		salelog.setDebts("1");
		salelog.setCategoryId(categoryid);
		salelog.setGoodsName(gdsid);
		salelog.setAmount(amount);
		salelog.setPrice(price);
		salelog.setPayType("2");
		salelog.setRecordUser(staff.getStaffId());
		LeftmenuService.save(salelog);
		String recordlogId = this.LeftmenuService.getSequence("SEQ_NEW_RECORDING", null);
		String recorddatId = t + branchid + recordlogId;
		Recording recording = new Recording();
		recording.setRecordId(recorddatId);
		recording.setBranchId(branchid);
		recording.setRecordType("2");
		recording.setProjectId(gdsprojectpay);
		recording.setFee(pay);
		recording.setRecordUser(staff.getStaffId());
		LeftmenuService.save(recording);
		JSONUtil.responseJSON(response, new AjaxResult(1, null));

	}

	@RequestMapping("/gdsRoompaycard.do")
	public void gdsRoompaycard(HttpServletRequest request, HttpServletResponse response, String gdscheckid,
			String gdsprojectpay, String gdsprojectpayname, String gdsworkbill, String gdsworkbillname,
			String totalprice, String cardpayvalue, String gdsid, String totalnumber, String gdsprice) throws Exception {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String branchid = loginuser.getStaff().getBranchId();
		LoginGetShift loginGetShift = (LoginGetShift)request.getSession(true).getAttribute("loginGetShift"); 
		String shiftid = loginGetShift.getShiftId(); 
		String cashbox =loginGetShift.getCashbox();
		String logId = this.LeftmenuService.getSequence("SEQ_NEW_WORKBILLDETAIL", null);
		Date day = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
		String t = df.format(day);
		String datId = t + branchid + logId;
		Double pay = Double.parseDouble(totalprice);
		WorkBillDetail workbilldetail = new WorkBillDetail();
		workbilldetail.setDetailId(datId);
		workbilldetail.setBranchId(branchid);
		workbilldetail.setProjectId(gdsprojectpay);
		workbilldetail.setProjectName(gdsprojectpayname.trim());
		workbilldetail.setDescribe("商品");
		workbilldetail.setPay(pay);
		workbilldetail.setCost((double) 0);
		workbilldetail.setPayment("2");
		workbilldetail.setStatus("1");
		workbilldetail.setRecordUser(staff.getStaffId());
		workbilldetail.setVoucher(cardpayvalue);
		workbilldetail.setWorkbillId(gdsworkbill);
		workbilldetail.setShift(shiftid);
		workbilldetail.setCashBox(cashbox);
		LeftmenuService.save(workbilldetail);
		Goods goods = ((Goods) (LeftmenuService.findOneByProperties(Goods.class, "goodsId", gdsid)));
		String categoryid = goods.getCategoryId();
		String salelogId = this.LeftmenuService.getSequence("SEQ_NEW_SALELOG", null);
		String saledatId = t + branchid + salelogId;
		Integer amount = Integer.valueOf(totalnumber);
		Double price = Double.parseDouble(gdsprice);
		SaleLog salelog = new SaleLog();
		salelog.setLogId(saledatId);
		salelog.setBranchId(branchid);
		salelog.setDebts("1");
		salelog.setCategoryId(categoryid);
		salelog.setGoodsName(gdsid);
		salelog.setAmount(amount);
		salelog.setPrice(price);
		salelog.setPayType("3");
		salelog.setRecordUser(staff.getStaffId());
		LeftmenuService.save(salelog);
		String recordlogId = this.LeftmenuService.getSequence("SEQ_NEW_RECORDING", null);
		String recorddatId = t + branchid + recordlogId;
		Recording recording = new Recording();
		recording.setRecordId(recorddatId);
		recording.setBranchId(branchid);
		recording.setRecordType("2");
		recording.setProjectId(gdsprojectpay);
		recording.setFee(pay);
		recording.setRecordUser(staff.getStaffId());
		LeftmenuService.save(recording);
		JSONUtil.responseJSON(response, new AjaxResult(1, null));

	}

	@RequestMapping("/gdmanageData.do")
	public ModelAndView gdmanageData(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		LoginUser loginuser = (LoginUser)request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginuser.getStaff().getBranchId();
		List<?> categoryCondition = LeftmenuService.getCategorycondition(branchid);
		mv.setViewName("page/ipmspage/leftmenu/goodssale/gdsmanage");
		request.setAttribute("categoryCondition", categoryCondition);
		return mv;
	}

	@RequestMapping("/goodsPage.do")
	public ModelAndView goodsPage(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		LoginUser loginuser = (LoginUser)request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginuser.getStaff().getBranchId();
		List<?> categoryCondition = LeftmenuService.getCategorycondition(branchid);
		mv.setViewName("page/ipmspage/leftmenu/goodssale/goodspage");
		request.setAttribute("categoryCondition", categoryCondition);
		return mv;
	}

	@RequestMapping("/goodsManage.do")
	public ModelAndView goodsManage(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		Pagination pagination = SqlBuilder.buildPagination(request);
		// @SuppressWarnings("unchecked")
		LoginUser loginuser = (LoginUser)request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginuser.getStaff().getBranchId();
		if(branchId.equals(SystemConstants.User.BRANCH_ID)){
			branchId = "%";
		}
		List<?> goodsSale = LeftmenuService.getGoodssale(pagination, branchId);
		mv.setViewName("page/ipmspage/leftmenu/goodssale/gdsmanagedata");
		mv.addObject("goodsSale", goodsSale);
		mv.addObject("pagination", pagination);
		return mv;
	}

	@RequestMapping("/gdsDown.do")
	public void gdsDown(HttpServletRequest request, HttpServletResponse response, String gdsid) throws Exception {
		Goods goods = ((Goods) (LeftmenuService.findOneByProperties(Goods.class, "goodsId", gdsid)));
		goods.setStatus("2");
		LeftmenuService.save(goods);
		JSONUtil.responseJSON(response, new AjaxResult(1, null));
	}

	@RequestMapping("/gdsUp.do")
	public void gdsUp(HttpServletRequest request, HttpServletResponse response, String gdsid) throws Exception {
		Goods goods = ((Goods) (LeftmenuService.findOneByProperties(Goods.class, "goodsId", gdsid)));
		goods.setStatus("1");
		LeftmenuService.save(goods);
		JSONUtil.responseJSON(response, new AjaxResult(1, null));
	}

	/*
	 * @RequestMapping("/goodsManage.do") public ModelAndView
	 * goodsManage(HttpServletRequest request) throws Exception{ ModelAndView mv
	 * = new ModelAndView(); List<?> categoryCondition =
	 * LeftmenuService.getCategorycondition();
	 * mv.setViewName("page/ipmspage/leftmenu/goodsmanage");
	 * request.setAttribute("categoryCondition", categoryCondition); return mv;
	 * }
	 */

	@RequestMapping("/gdmanageCondition.do")
	public ModelAndView gdmanageCondition(HttpServletRequest request, HttpServletResponse response, String goodsid,
			String goodsname, String categoryid, String status) throws Exception {
		ModelAndView mv = new ModelAndView();
		Pagination pagination = SqlBuilder.buildPagination(request);
		LoginUser loginuser = (LoginUser)request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginuser.getStaff().getBranchId();
		if(branchId.equals(SystemConstants.User.BRANCH_ID)){
			branchId = "%";
		};
		List<?> gdsmanageCondition = LeftmenuService.getGdmanagecondition(goodsid, goodsname, categoryid, status, branchId,
				pagination);
		mv.setViewName("page/ipmspage/leftmenu/goodssale/gdsmanagecondition");
		mv.addObject("gdsmanageCondition", gdsmanageCondition);
		mv.addObject("pagination", pagination);
		return mv;
	}

	@RequestMapping("/ordermemberAdd.do")
	public ModelAndView ordermemberAdd(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/ipmspage/leftmenu/order/ordermemberadd");
		return mv;
	}

	@RequestMapping("/formOmadd.do")
	public void formPurchase(HttpServletRequest request, HttpServletResponse response, String ordermaddjson)
			throws Exception {
		// LoginUser loginuser = (LoginUser)
		// request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		// Staff staff = loginuser.getStaff();
		// String branchid = loginuser.getStaff().getBranchId();
		JSONArray ordermaddarray = new JSONArray(ordermaddjson);
		for (int i = 0; i < ordermaddarray.length(); i++) {
			JSONObject jsonordermadd = (JSONObject) ordermaddarray.get(i);
			Member member = new Member();
			String memberid = this.LeftmenuService.getSequence("SEQ_MEMBER_ID", null);
			String membername = jsonordermadd.getString("omembername");
			String loginname = jsonordermadd.getString("ologinname");
			String pwd = MD5Util.getCryptogram("888888");
			String idcard = jsonordermadd.getString("oidcard");
			String gendor = jsonordermadd.getString("ogendor");
			String birthday = jsonordermadd.getString("obirthday");
			String mobile = jsonordermadd.getString("omobile");
			//检重@author jiangsm
			Member existmember = (Member) roomService.findOneByProperties(Member.class, "mobile",mobile,"status","1");
			if(existmember != null){
				JSONUtil.responseJSON(response, new AjaxResult(0, "手机号已注册!", null));
				return;
			}
			String email = jsonordermadd.getString("oemail");
			String address = jsonordermadd.getString("oaddress");
			String postcode = jsonordermadd.getString("opostcode");
			String remark = jsonordermadd.getString("oremark");
			DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
			Date birdate;
			if (((URLDecoder.decode(birthday, "UTF-8")) != null && !(URLDecoder.decode(birthday, "UTF-8")).equals(""))) {
				birdate = df.parse((URLDecoder.decode(birthday, "UTF-8")));
			} else {
				birdate = null;
			}
			Date day = new Date();
			SimpleDateFormat dfe = new SimpleDateFormat("yyyy/MM/dd");
			String t = dfe.format(day);
			Date D = df.parse(t);
			Calendar calendar = Calendar.getInstance();
			Date date = new Date(System.currentTimeMillis());
			calendar.setTime(date);
			calendar.add(Calendar.YEAR, +1);
			date = calendar.getTime();
			String s = dfe.format(date);
			Date Dl = df.parse(s);
			member.setMemberId(memberid);
			member.setMemberName(URLDecoder.decode(membername, "UTF-8"));
			member.setLoginName(URLDecoder.decode(loginname, "UTF-8"));
			member.setPassword(pwd);
			member.setMemberRank("1");
			member.setIdcard(idcard);
			member.setGendor(gendor);
			member.setBirthday(birdate);
			member.setMobile(mobile);
			member.setEmail(URLDecoder.decode(email, "UTF-8"));
			member.setAddress(URLDecoder.decode(address, "UTF-8"));
			member.setPostcode(postcode);
			member.setSource("1");
			member.setValidTime(D);
			member.setRegisterTime(D);
			member.setInvalidTime(Dl);
			member.setStatus("1");
			member.setRemark(URLDecoder.decode(remark, "UTF-8"));
			member.setRecordTime(new Date());
			LeftmenuService.save(member);
			String accountid = this.LeftmenuService.getSequence("SEQ_ACCOUNT_ID", null);
			MemberAccount memberaccount = new MemberAccount();
			memberaccount.setAccountId(accountid);
			memberaccount.setMemberId(memberid);
			memberaccount.setCurrBalance((double) 0);
			memberaccount.setCurrIntegration((long) 0);
			memberaccount.setTotalRecharge((double) 0);
			memberaccount.setDiscountGift((double) 0);
			memberaccount.setChargeGift((double) 0);
			memberaccount.setTotalConsume((double) 0);
			memberaccount.setTotalIntegration((long) 0);
			memberaccount.setIngegrationGift((long) 0);
			memberaccount.setTotalConsIntegration((long) 0);
			memberaccount.setTotalRoomnights(0);
			memberaccount.setCurrRoomnights(0);
			memberaccount.setTotalNoshow((short) 0);
			memberaccount.setCurrNoshow((short) 0);
			memberaccount.setThisYearIntegration((long) 0);
			memberaccount.setRecordTime(new Date());
			memberaccount.setStatus("1");
			LeftmenuService.save(memberaccount);
		}

		JSONUtil.responseJSON(response, new AjaxResult(1, null));
	}

	@RequestMapping("/exceptionSerach.do")
	public ModelAndView exceptionSerach(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		List<?> exceptionType = LeftmenuService.getExceptiontype();
		mv.setViewName("page/ipmspage/leftmenu/exception/exceptionpage");
		request.setAttribute("exceptionType", exceptionType);
		return mv;
	}

	@RequestMapping("/exceptionData.do")
	public ModelAndView exceptionData(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginuser.getStaff().getBranchId();
		Pagination pagination = SqlBuilder.buildPagination(request);
		List<?> exceptiondata = LeftmenuService.getExceptiondata(branchid, pagination);
		mv.setViewName("page/ipmspage/leftmenu/exception/exceptiondata");
		mv.addObject("exceptiondata", exceptiondata);
		mv.addObject("pagination", pagination);
		return mv;
	}

	@RequestMapping("/exceptionCondition.do")
	public ModelAndView exceptionCondition(HttpServletRequest request, HttpServletResponse response,
			String exbegintime, String exendtime, String exceptiontype, String exceptionstatus) throws Exception {
		ModelAndView mv = new ModelAndView();
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginuser.getStaff().getBranchId();
		Pagination pagination = SqlBuilder.buildPagination(request);
		List<?> exceptioncondition = LeftmenuService.getExceptioncondition(exbegintime, exendtime, exceptiontype,
				exceptionstatus, branchid, pagination);
		mv.setViewName("page/ipmspage/leftmenu/exception/exceptioncondition");
		mv.addObject("exceptioncondition", exceptioncondition);
		mv.addObject("pagination", pagination);
		return mv;
	}

	@RequestMapping("/exceptioncs.do")
	public void exceptioncs(HttpServletRequest request, HttpServletResponse response, String exceptionid)
			throws Exception {
		ExceptionMessage exceptionmessage = ((ExceptionMessage) (LeftmenuService.findOneByProperties(
				ExceptionMessage.class, "exceptionId", exceptionid)));
		exceptionmessage.setStatus("1");
		LeftmenuService.save(exceptionmessage);
		JSONUtil.responseJSON(response, new AjaxResult(1, null));
	}

	/*@RequestMapping("/pettyCash.do")
	public ModelAndView pettyCash(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		// List<?> exceptionType = LeftmenuService.getExceptiontype();
		mv.setViewName("page/ipmspage/leftmenu/pettycash/cashregister");
		// request.setAttribute("exceptionType", exceptionType);
		return mv;
	}*/
	
	
	@RequestMapping("/cashCount.do")
	public void cashCount(HttpServletRequest request, HttpServletResponse response) throws Exception {
  	     LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		 Staff staff = loginuser.getStaff();
		 String branchid = loginuser.getStaff().getBranchId();
		 String cashierstaff = staff.getStaffId();
		 LoginGetShift loginGetShift = (LoginGetShift)request.getSession(true).getAttribute("loginGetShift"); 
  		 String shiftid = loginGetShift.getShiftId(); 
		 String cashbox =loginGetShift.getCashbox();
		 List<?> cashiercashData = LeftmenuService.getCashiercashdata(branchid,cashierstaff,shiftid,cashbox);
		 List<?> cashiercardData = LeftmenuService.getCashiercarddata(branchid,cashierstaff,shiftid,cashbox);
		 String cashcountin =  null;
		 String cashcountout =  null;
		 String cardcount =  null; 
		 if (null == cashiercashData || cashiercashData.size() == 0) {
			 cashcountin =  "0.00";
			 cashcountout =  "0.00";
		 }else{
			 //cashcountin =  ((Map<?, ?>) cashiercashData.get(0)).get("CASHIN").toString();
			 cashcountin = ((Map<?, ?>) cashiercashData.get(0)).get("CASHIN")==null ?"0.00":((Map<?, ?>) cashiercashData.get(0)).get("CASHIN").toString();
			 cashcountout = ((Map<?, ?>) cashiercashData.get(0)).get("CASHOUT")==null ?"0.00":((Map<?, ?>) cashiercashData.get(0)).get("CASHOUT").toString();
			// cashcountout =  ((Map<?, ?>) cashiercashData.get(0)).get("CASHOUT").toString();
			 
		 }
		 if (null == cashiercardData || cashiercardData.size() == 0) {
			 cardcount =  "0.00"; 
		 }else{
			 cardcount =  ((Map<?, ?>) cashiercardData.get(0)).get("CARD").toString(); 
			 
		 }
		 String lastbranchid = branchid;
		 List<?> cashboxstatus = LeftmenuService.getCashboxstatus(lastbranchid,cashbox);
		/* CashBox cashboxdata = ((CashBox) (LeftmenuService.findOneByProperties(CashBox.class, "branchId", branchid,
					"cashBox", cashbox)));*/
		 String cashboxcount = ((Map<?, ?>) cashboxstatus.get(0)).get("CASH_COUNT").toString();
		 Staff cashierstaffclass = ((Staff) (LeftmenuService.findOneByProperties(Staff.class, "staffId", cashierstaff)));
		 String cashierstaffname = cashierstaffclass.getStaffName().toString();
		 Branch branch = ((Branch) (LeftmenuService.findOneByProperties(Branch.class, "branchId", branchid)));
		 String branchname = branch.getBranchName().toString();
		 SysParam sysparam = ((SysParam) (LeftmenuService.findOneByProperties(SysParam.class, "paramType", "SHIFT","content",shiftid)));
		/* SysParam sysparam = ((SysParam) (LeftmenuService.findOneByProperties(SysParam.class, "paramType", "SHIFTTIME","orderNo",shiftid)));
*/		 String shiftname = sysparam.getParamName().toString();
         List<?> lastshift = LeftmenuService.getLastshift(branchid);
         String lastshiftvalue = null;
         if (null == lastshift || lastshift.size() == 0) {
        	 lastshiftvalue = "0.00";
         }else{
        	 String lastbranchidlast = ((Map<?, ?>) lastshift.get(0)).get("BRANCH_ID").toString();
        	 String currshift = ((Map<?, ?>) lastshift.get(0)).get("SHIFT").toString();
        	 String curruser = ((Map<?, ?>) lastshift.get(0)).get("HAND_USER").toString();
        	 String recordtime = ((Map<?, ?>) lastshift.get(0)).get("RECORD_TIME").toString();
 			 List<?> lastshiftValue = LeftmenuService.getLastshiftvalue(lastbranchidlast,currshift,curruser,recordtime);
 			 lastshiftvalue = ((Map<?, ?>) lastshiftValue.get(0)).get("CURR_SHIFTVALUE").toString();
         }
         List<?> billcard = LeftmenuService.getBillcard(branchid,cashierstaff,shiftid,cashbox);
         List<?> workcard = LeftmenuService.getWorkcard(branchid,cashierstaff,shiftid,cashbox);
         String bcard = ((Map<?, ?>) billcard.get(0)).get("BCARD").toString();
         String wcard = ((Map<?, ?>) workcard.get(0)).get("WCARD").toString();
         int a = Integer.parseInt(bcard);
         int b = Integer.parseInt(wcard);
         String cards = String.valueOf(a+b);
		 Map<String,String> cashmap = new HashMap<String,String>();
		 cashmap.put("cashin", cashcountin);
		 cashmap.put("cashout", cashcountout);
		 cashmap.put("card", cardcount);
		 cashmap.put("cards", cards);
		 cashmap.put("boxname", cashbox);
		 cashmap.put("boxcount", cashboxcount); 
		 cashmap.put("shift", shiftid);
		 cashmap.put("shifterid", cashierstaff);
		 cashmap.put("shiftername", cashierstaffname);
		 cashmap.put("branchname", branchname);
		 cashmap.put("shiftname", shiftname);
		 cashmap.put("lastshiftvalue", lastshiftvalue);
		 JSONUtil.responseJSON(response,cashmap);
	}

	@RequestMapping("/cashShift.do")
	public ModelAndView cashShift(HttpServletRequest request, HttpServletResponse response,String cashcountin,String cashcountout,String cardcount,String cards,String boxname,
			String boxcount,String shift,String shifterid,String shiftername,String branchname,String shiftname,String lastshiftvalue) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/ipmspage/leftmenu/pettycash/cashregister");
		 LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		 String branchid = loginuser.getStaff().getBranchId();
		List<?> submitperson = LeftmenuService.getSubmitperson(branchid);
		request.setAttribute("cashcountin", cashcountin);
		request.setAttribute("cashcountout", cashcountout);
		request.setAttribute("cardcount", cardcount);
		request.setAttribute("cards", cards);
		request.setAttribute("boxname", boxname);
		request.setAttribute("boxcount", boxcount);
		request.setAttribute("shift", shift);
		request.setAttribute("shifterid", shifterid);
		request.setAttribute("shiftername", shiftername);
		request.setAttribute("branchid", branchid);
		request.setAttribute("branchname", branchname);
		request.setAttribute("shiftname", shiftname);
		request.setAttribute("lastshiftvalue", lastshiftvalue);
		request.setAttribute("submitperson", submitperson);
		return mv;
	}
	
	
	/*
	 * 左侧跳转维修页面
	 */
	@RequestMapping("/repair.do")
	public ModelAndView repair(HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/ipmspage/leftmenu/repair/repair");
		return mv;
	}

	
	
	@RequestMapping("/judgeCash.do")
	public void judgeCash(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String userInfo = staff.getStaffId();
		LoginGetShift loginGetShift = (LoginGetShift)request.getSession(true).getAttribute("loginGetShift"); 
		String shiftid = loginGetShift.getShiftId(); 
		String cashbox =loginGetShift.getCashbox();
		String a = null;
		String lastbranchid = null;
		String curruser = null;
		String currshift = shiftid;
		String cbstatus = "";
		if(userInfo.equalsIgnoreCase("1")){
			lastbranchid = "100001";
			curruser = "1";
		}else{
			Staff staffaother = ((Staff) (LeftmenuService.findOneByProperties(Staff.class, "staffId", userInfo)));
			lastbranchid = staffaother.getBranchId().toString();
			curruser = staffaother.getStaffId().toString();
		}
		List<?> cashboxstatus = LeftmenuService.getCashboxstatus(lastbranchid,cashbox);
		/*CashBox cashboxstatus = ((CashBox) (LeftmenuService.findOneByProperties(CashBox.class, "branchId", lastbranchid,"cashBox",cashbox)));*/
		if(cashboxstatus.size() > 0 ){
			cbstatus = ((Map<?, ?>) cashboxstatus.get(0)).get("CASH_STATUS").toString();
		}else if(cashboxstatus.size()==0 && lastbranchid.equals("100001")){
		}else{
			cbstatus = "-1";
		}
		if(cbstatus.equals("0")){
			Date day = new Date();
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			String recordtime = df.format(day);
			List<?> lastshiftValue = LeftmenuService.getLastshiftvalue(lastbranchid,currshift,curruser,recordtime);
			if (null == lastshiftValue || lastshiftValue.size() == 0) {
				String branchid = lastbranchid;
		       /* cashboxstatus.setCashStatus("1");*/
				List<?> lastshift = LeftmenuService.getLastshift(branchid);
				String shift = null;
				String hanguser = null;
				if (null == lastshift || lastshift.size() == 0) {
					 shift = "0";
					 hanguser = "0";
				}else{
					shift = ((Map<?, ?>) lastshift.get(0)).get("SHIFT").toString();
					hanguser = ((Map<?, ?>) lastshift.get(0)).get("HAND_USER").toString();
				}
				String logId = this.LeftmenuService.getSequence("SEQ_NEW_SHIFT", null);
				Date shiftday = new Date();
				SimpleDateFormat dfe = new SimpleDateFormat("yyMMdd");
				String t = dfe.format(shiftday);
				String logid = t + branchid + logId;
				Shift shiftdata = new Shift();
				shiftdata.setLogId(logid);
				shiftdata.setBranchId(lastbranchid);
				shiftdata.setCurrShift(shiftid);
				shiftdata.setCurrUser(curruser);
				shiftdata.setLastShift(shift);
				shiftdata.setLastUser(hanguser);
				shiftdata.setRecordTime(new Date());
				shiftdata.setRecordUser(curruser);
				LeftmenuService.save(shiftdata);
				
			}else{
				a = "今天当前您选择的班次您已经登录并交接过，请重新选择班次";	
			}
			
		}else if(cbstatus.equals("1")){
			a = "金柜"+cashbox+"正在被使用，请重新选择操作金柜";
		}else if(cbstatus.equals("-1")){
			a = "该门店还没有配置金柜";
		}
		JSONUtil.responseJSON(response, new AjaxResult(1, a));
	}
	
	
	@RequestMapping("/pettyCash.do")
	public void pettyCash(HttpServletRequest request, HttpServletResponse response, String branchid, String shift, String boxname
			, String shifterid, String submitstaff,String cashin, String cashout, String givecash, String boxnow,String lastshiftvalue, String fixcash
			, String cardin, String cards, String payday, String depositno, String invoiceno, String remark) throws Exception {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String recordUser = staff.getStaffId();
		String logId = this.LeftmenuService.getSequence("SEQ_NEW_PETTYCASH", null);
		Date day = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
		String t = df.format(day);
		String logid = t + branchid + logId;
		Double cashIn = Double.parseDouble(cashin);
		Double cashOut = Double.parseDouble(cashout);
		Double paymentvalue = Double.parseDouble(givecash);
		Double shiftvalue = Double.parseDouble(boxnow);
		Double lastshiftValue = Double.parseDouble(lastshiftvalue);
		Double currshiftvalue = Double.parseDouble(fixcash);
		Double cardIn = Double.parseDouble(cardin);
		PettyCash pettycash = new PettyCash();
		pettycash.setLogId(logid);
		pettycash.setBranchId(branchid);
		pettycash.setShift(shift);
		pettycash.setCashBox(boxname);
		pettycash.setHandUser(shifterid);
		pettycash.setConfirmUser(submitstaff);
		pettycash.setCashIn(cashIn);
		pettycash.setCashOut(cashOut);
		pettycash.setPaymentValue(paymentvalue);
		pettycash.setShiftValue(shiftvalue);
		pettycash.setLastShiftvalue(lastshiftValue);
		pettycash.setCurrShiftvalue(currshiftvalue);
		pettycash.setCardBalance(cardIn);
		pettycash.setCards(cards);
		pettycash.setDepositNo(depositno);
		pettycash.setInvoiceNo(invoiceno);
		pettycash.setRemark(remark);
		pettycash.setStatus("1");
		pettycash.setRecordTime(new Date());
		pettycash.setRecordUser(shifterid);
		LeftmenuService.save(pettycash);
		LeftmenuService.upatecashbox(branchid, boxname,recordUser);
		JSONUtil.responseJSON(response, new AjaxResult(1, null));
	}
	
		
		/*@RequestMapping("/cashUpdate.do")
		public ModelAndView cashUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception{
			ModelAndView mv = new ModelAndView();
			mv.setViewName("page/ipmspage/leftmenu/pettycash/cashupdate");
			Pagination pagination = SqlBuilder.buildPagination(request);
			LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
			String branchid = loginuser.getStaff().getBranchId();
			if(branchid.equals(SystemConstants.User.BRANCH_ID)){
				branchid = "%";
			}
			List<?> cash = LeftmenuService.findBySQLWithPagination("getcash",new String[]{branchid}, pagination, true);
			List<?> cashA = LeftmenuService.getCashA(branchid);
			List<?> cashB = LeftmenuService.getCashB(branchid);
			request.setAttribute("cash", cash);
			request.setAttribute("cash", JSONUtil.fromObject(cash));
			return mv;
		}*/
	
}
