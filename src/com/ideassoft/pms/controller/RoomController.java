package com.ideassoft.pms.controller;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.formula.functions.Offset;
import org.hsqldb.lib.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ideassoft.bean.Bill;
import com.ideassoft.bean.Campaign;
import com.ideassoft.bean.Check;
import com.ideassoft.bean.CheckinUserLog;
import com.ideassoft.bean.Contrart;
import com.ideassoft.bean.ExtensionLog;
import com.ideassoft.bean.HaltPlan;
import com.ideassoft.bean.Member;
import com.ideassoft.bean.MemberAccount;
import com.ideassoft.bean.MemberRank;
import com.ideassoft.bean.OffsetLog;
import com.ideassoft.bean.OperateLog;
import com.ideassoft.bean.Order;
import com.ideassoft.bean.Recording;
import com.ideassoft.bean.Room;
import com.ideassoft.bean.RoomPlan;
import com.ideassoft.bean.RoomPrice;
import com.ideassoft.bean.RoomPriceId;
import com.ideassoft.bean.RoomStatus;
import com.ideassoft.bean.RoomType;
import com.ideassoft.bean.Staff;
import com.ideassoft.bean.SwitchOrder;
import com.ideassoft.bean.SwitchRoom;
import com.ideassoft.bean.SysParam;
import com.ideassoft.bean.Tips;
import com.ideassoft.bean.Transfer;
import com.ideassoft.bean.WorkBill;
import com.ideassoft.bean.WorkBillDetail;
import com.ideassoft.bean.wrapper.CheckoutRoom;
import com.ideassoft.bean.wrapper.LoginGetShift;
import com.ideassoft.bean.wrapper.MultiQueryCheck;
import com.ideassoft.core.ajax.AjaxResult;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.exception.DAOException;
import com.ideassoft.core.jdbc.SqlBuilder;
import com.ideassoft.core.page.Pagination;
import com.ideassoft.pms.service.LeftmenuService;
import com.ideassoft.pms.service.RoomService;
import com.ideassoft.util.ChineseCharacterUtil;
import com.ideassoft.util.DateUtil;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.MD5Util;
import com.ideassoft.util.RequestUtil;

@Transactional
@Controller
public class RoomController {
	
	@Autowired
	private RoomService roomService;

	@Autowired
	private LeftmenuService LeftmenuService;

	//展示酒店页面
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/queryRoomList.do")
	public void queryRoomList(HttpServletRequest request, HttpServletResponse response, String roomtype, String roomfloor) throws Exception {
 		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		
//		if(StringUtil.isEmpty(branchId) || branchId.equals(SystemConstants.User.BRANCH_ID)){
//			branchId = "%";
//		}
		List<?> roomlist =  roomService.findBySQL("queryroomlistdata",new String[]{branchId, roomtype, roomfloor, branchId, 
				roomtype, roomfloor, branchId, roomtype, roomfloor,}, true);
		List<?> totalcount = roomService.findBySQL("querytotalcount", new String[]{branchId}, true);
		List<?> totallive = roomService.findBySQL("querylivecount", new String[]{branchId}, true);
		Map<Object, Object> roomdata = new HashMap<Object, Object>();
		
		List<Map<String, Object>> apartment = roomService.findBySQL("queryapartroom", new String[] {branchId, branchId},true);
		String type = "TYPE";
		for (Map<String, Object> map : apartment) {
			if ((map).get("STATUS").equals("2")) {
				Calendar cal = Calendar.getInstance();
			    int month = cal.get(Calendar.MONTH) + 1;
			    int year = cal.get(Calendar.YEAR);
			    String yearnow = Integer.toString(year);
			    String monthnow = Integer.toString(month); 
				if ((map).get("STARTYEAR").equals(yearnow)) {
					if (!(map).get("STARTMONTH").equals(monthnow)) {
						(map).put(type, "已租");
					} else {
						(map).put(type, "当月签约");
					}
				}
				if ((map).get("ENDYEAR").equals(yearnow)) {
					if ((map).get("ENDMONTH").equals(monthnow)) {
						(map).put(type, "当月预离");
					}
				}
			}
			if ((map).get("STATUS").equals("0")) {
				Calendar now = Calendar.getInstance();
			    List contract = (List) roomService.findByProperties(Contrart.class, "roomId", (map).get("RMID"), "branchId", branchId, "status", "2");
			    DateUtil.onlysetMonthYear(now);
			    now.getTime();
			    if (contract.size() > 0) {
				    for (int i = 0;i<contract.size();i++) {
				    	Calendar endtime = Calendar.getInstance();
				    	endtime.setTime(((Contrart) contract.get(i)).getEndTime());
				    	DateUtil.onlysetMonthYear(endtime);
				    	endtime.getTime();
						if (DateUtil.isSameDay(now, endtime)) {
							(map).put(type, "当月退租");
						} else {
							(map).put(type, "空置房");
						} 
				    }
			    } else {
					(map).put(type, "空置房");
				}
			}	
		}
		
		if (roomlist.size() <= 0 ) {
			roomdata.put("apartment", apartment);
			roomdata.put("state", 2);
		} else {
			roomdata.put("roomlist", roomlist);
			roomdata.put("totalcount", totalcount);
			roomdata.put("totallive", totallive);
			roomdata.put("state", 1);
		}
		JSONUtil.responseJSON(response, roomdata);
	}

	
	@RequestMapping("/showgetcontinue.do")
	public ModelAndView showGetContinue(HttpServletRequest request){
		String checkid = request.getParameter("checkid");
		Check check = (Check) roomService.findOneByProperties(Check.class, "checkId", checkid);
		ModelAndView mv = new ModelAndView();
		mv.addObject("checkid", checkid);
		Date checkouttimedate = check.getCheckoutTime();
		String checkoutTime = DateUtil.d2s(checkouttimedate, "yyyy/MM/dd HH:mm");
		
		mv.addObject("checkoutTime", checkoutTime);
		mv.setViewName("/page/ipmspage/room_statistics/getcontinue");
		return mv;
	}
	
	@RequestMapping("/showtransferorder.do")
	public ModelAndView orderSerach(HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/page/ipmspage/room_statistics/transferorder");
		return mv;
	}
	
	@RequestMapping("/showTransferBill.do")
	public ModelAndView showTransferBill(HttpServletRequest request){
		String checkid = request.getParameter("checkid");
		ModelAndView mv = new ModelAndView();
		mv.addObject("checkid", checkid);
		mv.setViewName("/page/ipmspage/room_statistics/transfer");
		return mv;
	}
	
	@RequestMapping("/showgetAvailableRoom.do")
	public ModelAndView showgetAvailableRoom(HttpServletRequest request){
		String checkid = request.getParameter("checkid");
		ModelAndView mv = new ModelAndView();
		mv.addObject("checkid", checkid);
		mv.setViewName("/page/ipmspage/room_statistics/AvailableRoom");
		return mv;
	}
	
	@RequestMapping("/showgetAddBill.do")
	public ModelAndView showgetAddBill(HttpServletRequest request){
		String checkid = request.getParameter("checkid");
		ModelAndView mv = new ModelAndView();
		mv.addObject("checkid", checkid);
		mv.setViewName("/page/ipmspage/room_statistics/add_bill");
		return mv;
	}
	
	@RequestMapping("/showgetAddWorkBill.do")
	public ModelAndView showgetAddWorkBill(HttpServletRequest request){
		String workbillid = request.getParameter("workbillid");
		ModelAndView mv = new ModelAndView();
		mv.addObject("workbillid", workbillid);
		mv.setViewName("/page/ipmspage/work_account/add_workbill");
		return mv;
	}
	
	@RequestMapping("/showupdateRoomPrice.do")
	public ModelAndView showupdateRoomPrice(HttpServletRequest request){
		String checkid = request.getParameter("checkid");
		ModelAndView mv = new ModelAndView();
		mv.addObject("checkid", checkid);
		mv.setViewName("/page/ipmspage/room_statistics/update_roomprice");
		return mv;
	}

	@RequestMapping("/showPromptRecord.do")
	public ModelAndView showPromptRecord(HttpServletRequest request){
		String checkid = request.getParameter("checkid");
		ModelAndView mv = new ModelAndView();
		mv.addObject("checkid", checkid);
		mv.setViewName("/page/ipmspage/order/addPromptRecord");
		return mv;
	}
	
	@RequestMapping("/showroommap.do")
	public ModelAndView showroommap(HttpServletRequest request) throws Exception{
		String checkid = request.getParameter("checkid");
		List<?> roomtype = LeftmenuService.getRoomtype();
		ModelAndView mv = new ModelAndView();
		mv.addObject("checkid", checkid);
		mv.addObject("roomtype", roomtype);
		mv.setViewName("/page/ipmspage/room_statistics/roommap");
		return mv;
	}
	
	@RequestMapping("/showroomlistcheckdata.do")
	public ModelAndView showroomlistcheckdata(HttpServletRequest request,MultiQueryCheck multiQuerycheck) throws Exception{
		LoginUser loginuser = (LoginUser) request.getSession(true)
		.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String branchId = staff.getBranchId();
		ModelAndView mv = new ModelAndView();
		Pagination pagination = SqlBuilder.buildPagination(request);
		List<?> list = roomService.queryCheckData(branchId,multiQuerycheck,pagination);
		mv.addObject("list", list);
		mv.addObject("pagination", pagination);
		mv.addObject("multiQuerycheck", multiQuerycheck);
		mv.setViewName("/page/ipmspage/room_statistics/roomlistCheckData");
		return mv;
	}
	
	@RequestMapping("/showupdateMember.do")
	public ModelAndView showupdateMember(HttpServletRequest request, String memberId) throws Exception{
		LoginUser loginuser = (LoginUser) request.getSession(true)
		.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		//String branchId = staff.getBranchId();
		Member member = (Member) roomService.findOneByProperties(Member.class, "memberId", memberId);
		MemberRank memberrank = (MemberRank) roomService.findOneByProperties(MemberRank.class, "memberRank", member.getMemberRank());
		String rankName = memberrank.getRankName();
		String validTime = DateUtil.d2s(member.getValidTime(), "yyyy/MM/dd HH:mm");
		String invalidTime = DateUtil.d2s(member.getInvalidTime(), "yyyy/MM/dd HH:mm");
		String esource = member.getSource();
		String decodesource = "";
		int intsource = Integer.parseInt(esource);
		switch(intsource){
			case 1:
				decodesource = "app";
				break;
			case 2:
				decodesource = "网站";
				break;
			case 3:
				decodesource = "分店";
				break;
			case 4:
				decodesource = "wap";
				break;
			case 5:
				decodesource = "合作渠道";
				break;
			case 6:
				decodesource = "其他";
				break;
			case 7:
				decodesource = "微信";
				break;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("member", member);
		map.put("rankName", rankName);
		map.put("validTime", validTime);
		map.put("invalidTime", invalidTime);
		map.put("decodesource", decodesource);
		JSONObject jsonmember = JSONUtil.fromObject(map);
		ModelAndView mv = new ModelAndView();
		mv.addObject("member", jsonmember);
		mv.setViewName("/page/ipmspage/room_statistics/updatemember");
		return mv;
	}
	//展示页面
	
	@RequestMapping("/loadCheckData.do")
	public void loadCheckData(HttpServletRequest request, HttpServletResponse response,
				MultiQueryCheck multiQuerycheck){
		String sql = "loadOnlineCheckData";
		List<?> list = roomService.findBySQL(sql,new String[]{multiQuerycheck.getStatus(), multiQuerycheck.getCheckid(), multiQuerycheck.getRoomid(), multiQuerycheck.getRoomtype(), 
				multiQuerycheck.getGuarantee(), multiQuerycheck.getMphone()}, true);
		JSONUtil.responseJSON(response, list);
	}

	//预抵
	@RequestMapping("/queryArrival.do")
	public void queryArrival(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<?> arrivalList = roomService.findBySQL("queryarrival", true);;
		JSONUtil.responseJSON(response, arrivalList);
	}

	@RequestMapping("submitgetcontinuebill.do")
	public void submitgetcontinuebill(HttpServletRequest request, HttpServletResponse response,
			String checkId, String project, String cuurent_checkouttime, String getcontinuetime,
			String amount){
		LoginUser loginuser = (LoginUser) request.getSession(true)
			.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		getcontinuetime = getcontinuetime + " 00:00";
		Staff staff = loginuser.getStaff();
		Bill bill = new Bill();
		Check check = (Check) roomService.findOneByProperties(Check.class, "checkId", checkId);
		
		check.setCheckoutTime(DateUtil.s2d(getcontinuetime, "yyyy/MM/dd HH:mm"));
		
		String branchid = check.getBranchId();
		String billId = DateUtil.currentDate("yyMMdd") + branchid + roomService.getSequence("SEQ_NEW_BILL");
		bill.setBillId(billId);
 		bill.setBranchId(branchid);
		bill.setCheckId(checkId);
		bill.setProjectId("111");//gai 
		bill.setProjectName(project);
		bill.setDescribe("续住");
		bill.setPay(Double.valueOf((amount.equals("")?"0":amount)));
		bill.setCost(0.00);
		bill.setPayment("1");//gai 
		bill.setStatus("1");
		bill.setRecordUser(staff.getStaffId());
		
		ExtensionLog extensionlog = new ExtensionLog();
		String logId = DateUtil.currentDate("yyMMdd") + branchid + roomService.getSequence("SEQ_NEW_EXTENSIONLOG");
		extensionlog.setLogId(logId);
		extensionlog.setBranchId(branchid);
		extensionlog.setCheckId(checkId);
		extensionlog.setAddDay(DateUtil.s2d(getcontinuetime, "yyyy/MM/dd hh:mm"));
		extensionlog.setLastDay(DateUtil.s2d(cuurent_checkouttime, "yyyy/MM/dd hh:mm"));
		extensionlog.setRpId(check.getRpId());
		extensionlog.setRoomPrice(check.getRoomPrice());
		extensionlog.setRecordUser(staff.getStaffId());
		
		try{
			if(!"".equals(project)){
				roomService.save(bill);
			}
			roomService.save(check);
			roomService.save(extensionlog);
			JSONUtil.responseJSON(response, new AjaxResult(0, "续住成功!"));
			
		}catch(Exception e){
			e.printStackTrace();
			JSONUtil.responseJSON(response, new AjaxResult(1, "续住失败!"));
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/changeRoom.do")
	public void changeRoom(HttpServletRequest request, HttpServletResponse response,
				String roomid, String checkid){
		LoginUser loginuser = (LoginUser) request.getSession(true)
				.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginuser.getStaff().getBranchId();

		String sqlroomprice = "selectchangeroom"; 
		List<Map<String, ?>> listroomprice = roomService.findBySQL(sqlroomprice, new String[]{roomid, branchid, checkid}, true);
		String rpid = (String) listroomprice.get(0).get("RPID");
		String roomtype = (String) listroomprice.get(0).get("ROOMTYPE");
		String roomprice = listroomprice.get(0).get("ROOMPRICE").toString();
		/*	String sqlroom = "queryroom";
		List<?> roomlist = roomservice.findBySQL(sqlroom, new String[]{roomid}, true);
		Order order = (Order) roomservice.findOneByProperties(Order.class, "orderId", checkid);
		Member member = (Member) roomservice.findOneByProperties(Member.class, "memberId", order.getOrderUser());
		RoomPrice roomprice = (RoomPrice) roomservice.findOneByProperties(RoomPrice.class, "roomType",(String)((Map) roomlist.get(0)).get("ROOM_TYPE"), "mmeberRank", member.getMemberRank());
		*/
		Check lastcheck = (Check) roomService.findOneByProperties(Check.class, "checkId", checkid);
		String lastroomid = lastcheck.getRoomId();
		
		SwitchRoom switchroom = new SwitchRoom();
		String logId = DateUtil.currentDate("yyMMdd") + branchid + roomService.getSequence("SEQ_NEW_SWITCHROOM");
		switchroom.setLogId(logId);
		switchroom.setBranchId(branchid);
		switchroom.setCheckId(checkid);
		switchroom.setLastrpId(lastcheck.getRpId());
		switchroom.setLastRoomid(lastroomid);
		switchroom.setLastRoomtype(lastcheck.getRoomType());
		switchroom.setLastrRoomprice(lastcheck.getRoomPrice());
		switchroom.setCurrrpId(rpid);
		switchroom.setCurrRoomid(roomid);
		switchroom.setCurrRoomtype(roomtype);
		switchroom.setCurrRoomprice(Double.parseDouble(roomprice));
		switchroom.setRecordUser(loginuser.getStaff().getStaffId());
		
		try{
			roomService.save(switchroom);
			roomService.upateroomstatus(branchid, roomid, "2");
			roomService.upateroomstatus(branchid, lastroomid, "0");
			roomService.updatecheck(rpid, roomid, roomtype, Double.parseDouble(roomprice), checkid);			
		}catch(Exception e){
			e.printStackTrace();
		}
		//JSONUtil.responseJSON(response, list);
	}
	
	@RequestMapping("/loadRoomBillData.do")
	public void loadroomlistbill(HttpServletRequest request, HttpServletResponse response,
				String checkid, String status){
		LoginUser loginuser = (LoginUser) request.getSession(true)
		.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginuser.getStaff().getBranchId();
		String sql = "loadroomlistbill";
		List<?> alllist = roomService.findBySQL(sql, new String[]{checkid, branchid, null}, true);
		List<?> normallist = roomService.findBySQL(sql, new String[]{checkid, branchid, "1"}, true);
		List<?> list = roomService.findBySQL(sql, new String[]{checkid, branchid, status}, true);
		if(alllist.size() <= normallist.size()){
			JSONUtil.responseJSON(response, new AjaxResult(0,	"账单无冲减/转账记录!", list));
		}else{
			JSONUtil.responseJSON(response, new AjaxResult(1,	"", list));
		}
	}
	
	@RequestMapping("/loadWorkBillData.do")
	public void loadWorkBillData(HttpServletRequest request, HttpServletResponse response,
				String workbillid, String status){
		LoginUser loginuser = (LoginUser) request.getSession(true)
		.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginuser.getStaff().getBranchId();
		String sql = "loadworkbilldetail";
		List<?> list = roomService.findBySQL(sql, new String[]{status, workbillid, branchid}, true);
		JSONUtil.responseJSON(response, list);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/showallroomtype.do")
	public void queryalltype(HttpServletRequest request, HttpServletResponse response, String theme, String status){
		LoginUser loginuser = (LoginUser) request.getSession(true)
		.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginuser.getStaff().getBranchId();
		String sql = "queryallroomtype";
		String sql1 = "queryoneroomtype";
		List<Map<String, String>> roomtypelist = roomService.findBySQL(sql,new String[]{status, theme, branchId}, true);
		Map<String, List<String>> result = new HashMap<String, List<String>>();
		for (Map<String, String> map : roomtypelist) {
			String type = (String) map.get("ROOMTYPE");
			String typename = (String) map.get("ROOMNAME");
			List<Map<String, String>> maptype = roomService.findBySQL(sql1, new String[]{status, theme, type, branchId}, true); 
			List<String> listroomid = new ArrayList<String>();
			for (Map map2 : maptype) {
				listroomid.add((String) map2.get("ROOMID"));
			}
			result.put(typename, listroomid);
		}
		JSONUtil.responseJSON(response, result);
	}
	
	@RequestMapping("/addPromptRecord.do")
	public void addPromptRecord(HttpServletRequest request,HttpServletResponse response,
			String checkId,String content){
			LoginUser loginuser = (LoginUser)request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
			Order orderdata = (Order) roomService.findOneByProperties(Order.class, "orderId", checkId);
			Staff staff = loginuser.getStaff();
			Tips tips = new Tips();
			String logid = DateUtil.currentDate("yyMMdd")+roomService.getSequence("SEQ_TIPS_ID");
			tips.setLogId(logid);
			tips.setCheckId(checkId);
			tips.setContent(content);
			tips.setStatus("1");
			
			if (orderdata.getStatus().equals("1")) {
				tips.setType("1");
			}
			tips.setRecordUser(staff.getStaffId());
			List<?> list = roomService.findBySQL("countPrompt", new String[]{checkId}, true);
			Map<String,String> message = new HashMap<String,String>();
			try {
				roomService.save(tips);
				JSONUtil.responseJSON(response, new AjaxResult(1,"添加成功 "));
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	@RequestMapping("/addbill.do")
	public void add_bill(HttpServletRequest request, HttpServletResponse response,
		String checkId, String project, String projectid, String amount, String remark){
		LoginUser loginuser = (LoginUser) request.getSession(true)
		.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		LoginGetShift loginGetShift = (LoginGetShift) request.getSession(true).getAttribute("loginGetShift");
		Staff staff = loginuser.getStaff();
		Bill bill = new Bill();
		Check check = (Check) roomService.findOneByProperties(Check.class, "checkId", checkId);
		String branchid = check.getBranchId();
		String billId = DateUtil.currentDate("yyMMdd") + branchid + roomService.getSequence("SEQ_NEW_BILL");
		bill.setBillId(billId);
		bill.setBranchId(branchid);
		bill.setCheckId(checkId);
		bill.setProjectId(projectid);//gai 
		bill.setProjectName(project);
		bill.setDescribe("入账");
		if(projectid.startsWith("2")){
			if("2001".equals(projectid)){
				bill.setPay(-Double.valueOf(amount));
			}else {
				bill.setPay(Double.valueOf(amount));
			}
			bill.setCost(0.00);
		}else {
			bill.setCost(Double.valueOf(amount));
			bill.setPay(0.00);
		}
		if("2001".equals(projectid)){				
			bill.setPayment("1");
		}else if("2002".equals(projectid)){
			bill.setPayment("1");
		}else if("2003".equals(projectid)){
			bill.setPayment("2");
		}else {
			bill.setPayment("0");
		} 
		bill.setStatus("1");
		bill.setRemark(remark);
		bill.setRecordUser(staff.getStaffId());
		bill.setShift(loginGetShift.getShiftId());
		bill.setCashBox(loginGetShift.getCashbox());
		
		Recording recording = new Recording();
		String recordId = DateUtil.currentDate("yyMMdd") + branchid + roomService.getSequence("SEQ_NEW_RECORDING");
		recording.setRecordId(recordId);
		recording.setBranchId(branchid);
		recording.setCheckId(checkId);
		recording.setRecordType("1");
		recording.setProjectId(projectid);
		recording.setFee(Double.valueOf(amount));
		recording.setRecordUser(staff.getStaffId());
		
		
		try{
			roomService.save(bill);
			roomService.save(recording);
			JSONUtil.responseJSON(response, new AjaxResult(1,	"入账成功!"));
		}catch(Exception e){
			e.printStackTrace();
			JSONUtil.responseJSON(response, new AjaxResult(0,	"入账失败!"));
		}
	}
	
	@RequestMapping("/addworkbill.do")
	public void addworkbill(HttpServletRequest request, HttpServletResponse response,
		String workbillid, String project, String projectid, String amount, String remark){
		LoginUser loginuser = (LoginUser) request.getSession(true)
		.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		LoginGetShift loginGetShift = (LoginGetShift) request.getSession(true).getAttribute("loginGetShift");
		Staff staff = loginuser.getStaff();
		WorkBillDetail workbilldetail = new WorkBillDetail();
		String branchid = staff.getBranchId();
		String detailid = DateUtil.currentDate("yyMMdd") + branchid + roomService.getSequence("SEQ_NEW_WORKBILLDETAIL");
		workbilldetail.setDetailId(detailid);
		workbilldetail.setBranchId(branchid);
		workbilldetail.setWorkbillId(workbillid);
		workbilldetail.setProjectId(projectid);//gai 
		workbilldetail.setProjectName(project);
		workbilldetail.setDescribe("入账");
		if(projectid.startsWith("2")){
			workbilldetail.setPay(Double.valueOf(amount));
			workbilldetail.setCost(0.00);
		}else {
			workbilldetail.setCost(Double.valueOf(amount));
			workbilldetail.setPay(0.00);
		}
		workbilldetail.setPayment("1");//gai 
		workbilldetail.setStatus("1");
		workbilldetail.setRemark(remark);
		workbilldetail.setRecordUser(staff.getStaffId());
		workbilldetail.setCashBox(loginGetShift.getCashbox());
		workbilldetail.setShift(loginGetShift.getShiftId());
		
		Recording recording = new Recording();
		String recordId = DateUtil.currentDate("yyMMdd") + branchid + roomService.getSequence("SEQ_NEW_RECORDING");
		recording.setRecordId(recordId);
		recording.setBranchId(branchid);
		recording.setCheckId(workbillid);
		recording.setRecordType("2");
		recording.setProjectId(projectid);
		recording.setFee(Double.valueOf(amount));
		recording.setRecordUser(staff.getStaffId());
		
		
		try{
			roomService.save(workbilldetail);
			roomService.save(recording);
			JSONUtil.responseJSON(response, new AjaxResult(1,	"入账成功!"));
		}catch(Exception e){
			e.printStackTrace();
			JSONUtil.responseJSON(response, new AjaxResult(0,	"入账失败!"));
		}
	}
	
	@RequestMapping("/loadCustomerData.do")
	public void loadCustomerData(HttpServletRequest request, HttpServletResponse response,
			String checkid,String checkusername){
		LoginUser loginuser = (LoginUser) request.getSession(true)
		.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String branchId = staff.getBranchId();
		//String sql = "loadCustomerData";
		String strcheckid="";
		Check check = (Check) roomService.findOneByProperties(Check.class, "checkId", checkid);
		String roomid = check.getRoomId();
		List<Map<String, Object>> listroommapping = roomService.findallroommapping(roomid);
		//List<RoomMapping> listroommapping = roomService.findByProperties(RoomMapping.class, "roomId", roomid);
		for (Map<String, Object> map : listroommapping) {
			strcheckid = strcheckid + map.get("RELAROOMID") + ",";
		}
		strcheckid = strcheckid.substring(0, strcheckid.length()-1);
		List<?> result = roomService.loadcustomer(strcheckid, branchId);
		JSONUtil.responseJSON(response, result);
	}
	
	//房单日志
	@RequestMapping("/showLogData.do")
	public ModelAndView loadLogData(HttpServletRequest request, HttpServletResponse response, String type, String checkid) throws Exception{
		ModelAndView mv = new ModelAndView();
		LoginUser loginUser = (LoginUser)request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Pagination pagination = SqlBuilder.buildPagination(request);
		List<?> result = roomService.getLog(checkid,type,pagination);
		mv.addObject("checkid",checkid);
		mv.addObject("type",type);
		mv.addObject("result",result);
		mv.addObject("pagination", pagination);
		mv.setViewName("page/ipmspage/room_statistics/logData");
		return mv;
	} 

	
	@RequestMapping("/loadRoomPlanData.do")
	public void loadRoomPlanData(HttpServletRequest request, HttpServletResponse response, String roomid){
		LoginUser loginUser = (LoginUser)request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		String sql = "loadroomplan";
		List<?> result = roomService.findBySQL(sql, new String[]{branchId, roomid}, true);
		JSONUtil.responseJSON(response, result);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/loadOrderPromptData.do")
	public ModelAndView loadOrderPrompt(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ModelAndView mv = new ModelAndView();
		Pagination pagination = SqlBuilder.buildPagination(request);
		pagination.setRows(13);
		String checkid = request.getParameter("checkid");
		Order orderdata = (Order) roomService.findOneByProperties(Order.class, "orderId", checkid);
		String sql = "";
		if (orderdata.getStatus().equals("1")) {
			sql = "queryordertips";
		} else {
			sql = "loadorderprompt";
		}
		List<Tips> result = roomService.findBySQLWithPagination(sql,new String[]{checkid}, pagination, true);
		mv.addObject("result",result);
		mv.addObject("checkid",checkid);
		mv.addObject("pagination", pagination);
		mv.setViewName("page/ipmspage/order/prompt");
		return mv;
	}
	
	@RequestMapping("/loadtransferorderData.do")
	public void loadtransferorderData(HttpServletRequest request, HttpServletResponse response, String checkid){
		Check check = (Check) roomService.findOneByProperties(Check.class, "checkId", checkid);
		Order order = (Order) roomService.findOneByProperties(Order.class, "orderId", checkid);
		String sql = "querytransferorder";
		List<?> result = roomService.findBySQL(sql, new String[]{order.getOrderUser(), order.getSource(), check.getCheckId()}, true);
		JSONUtil.responseJSON(response, result);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/transferbill.do")
	public void transferbill(HttpServletRequest request, HttpServletResponse response,
			String checkid, String targetcheckid, String amount){
		LoginUser loginuser = (LoginUser) request.getSession(true)
		.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String branchId = staff.getBranchId();
		//String sqlcost = "totalcostfromcheck";
		//List<Map<String, BigDecimal>> costresult = roomService.findBySQL(sqlcost, new String[]{targetcheckid}, true);
		String sqlpay = "totalpayfromcheck";
		List<Map<String, BigDecimal>> payresult = roomService.findBySQL(sqlpay, new String[]{checkid, branchId}, true);
		if( payresult.get(0).get("totalpay").doubleValue() < Double.parseDouble(amount)){
			JSONUtil.responseJSON(response, new AjaxResult(0, "账户金额不足!"));
		}else {
			Bill bill = new Bill();
			Check check = (Check) roomService.findOneByProperties(Check.class, "checkId", checkid);
			String branchid = check.getBranchId();
			String billId = DateUtil.currentDate("yyMMdd") + branchid + roomService.getSequence("SEQ_NEW_BILL");
			bill.setBillId(billId);
			bill.setBranchId(branchid);
			bill.setCheckId(checkid);
			bill.setProjectId("2005");//gai 
			bill.setProjectName("转账");
			bill.setDescribe("内部交易");
			bill.setPay(-Double.valueOf(amount));
			bill.setCost(0.00);
			bill.setPayment("1");//gai 
			bill.setStatus("3");
			bill.setRecordUser(staff.getStaffId());
			
			Bill targetbill = new Bill();
			Check targetcheck = (Check) roomService.findOneByProperties(Check.class, "checkId", targetcheckid);
			String targetbranchid = targetcheck.getBranchId();
			String targetbillId = DateUtil.currentDate("yyMMdd") + branchid + roomService.getSequence("SEQ_NEW_BILL");
			targetbill.setBillId(targetbillId);
			targetbill.setBranchId(targetbranchid);
			targetbill.setCheckId(targetcheckid);
			targetbill.setProjectId("2005");//gai 
			targetbill.setProjectName("转账");
			targetbill.setDescribe("内部交易");
			targetbill.setPay(Double.valueOf(amount));
			targetbill.setCost(0.00);
			targetbill.setPayment("1");//gai 
			targetbill.setStatus("1");
			targetbill.setRecordUser(staff.getStaffId());
			
			Transfer transfer = new Transfer();
			String transferId = DateUtil.currentDate("yyMMdd") + branchId + roomService.getSequence("SEQ_NEW_TRANSFER");
			transfer.setTransferId(transferId);
			transfer.setBranchId(branchId);
			transfer.setLastCheckid(checkid);
			transfer.setCurrCheckid(targetcheckid);
			transfer.setRecordUser(staff.getStaffId());
			
			
			try{
				roomService.save(bill);
				roomService.save(targetbill);
				roomService.save(transfer);
				JSONUtil.responseJSON(response, new AjaxResult(1, "转账成功!"));
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
/*		if( payresult.get(0).get("totalpay") == 0 || payresult.get(0).get("totalpay") <= costresult.get(0).get("totalcost")){
			JSONUtil.responseJSON(response, new AjaxResult(0, "转账金额不足!"));
		}*/
		
		//JSONUtil.responseJSON(response, result);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/autoautotransferbill.do")
	public void autoautotransferbill(HttpServletRequest request, HttpServletResponse response,
			String checkid){
		LoginUser loginuser = (LoginUser) request.getSession(true)
		.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String branchId = staff.getBranchId();
		Check check = (Check) roomService.findOneByProperties(Check.class, "checkId", checkid);
		String hostroomid = check.getRoomId();
		List<Map<String, Object>> listroommapping = roomService.findallroommapping(hostroomid);
		if(listroommapping.size() > 0){
			List<Map<String, Double>> listmapsubpaycost = new ArrayList<Map<String, Double>>();
			double totalfee = 0; //循环得出被关联方的总账面价格
			for (Map<String, Object> map : listroommapping) {
				String relaroomid = (String) map.get("RELAROOMID");
				Check relacheck = (Check) roomService.findOneByProperties(Check.class, "roomId", relaroomid, "branchId", branchId);
				String sqlcost = "totalcostfromcheck";
				List<Map<String, BigDecimal>> costresult = roomService.findBySQL(sqlcost, new String[]{relacheck.getCheckId(), staff.getBranchId()}, true);
				String sqlpay = "totalpayfromcheck";
				List<Map<String, BigDecimal>> payresult = roomService.findBySQL(sqlpay, new String[]{relacheck.getCheckId(), staff.getBranchId()}, true);
				double subpaycost = payresult.get(0).get("totalpay").doubleValue() - costresult.get(0).get("totalcost").doubleValue();
				if(subpaycost > 0){//当某个房间结算大于消费，便不用平账
					subpaycost = 0;
				}else{
					Map<String, Double> mapsubsubpaycost = new HashMap<String, Double>();
					mapsubsubpaycost.put(relacheck.getCheckId(), subpaycost);
					listmapsubpaycost.add(mapsubsubpaycost);
					totalfee = totalfee + subpaycost;				
				}
			}
			
			if(totalfee < 0){
				String sqlcost = "totalcostfromcheck";
				List<Map<String, BigDecimal>> costresult = roomService.findBySQL(sqlcost, new String[]{checkid, staff.getBranchId()}, true);
				String sqlpay = "totalpayfromcheck";
				List<Map<String, BigDecimal>> payresult = roomService.findBySQL(sqlpay, new String[]{checkid, staff.getBranchId()}, true);
				if( payresult.get(0).get("totalpay").doubleValue() >= (costresult.get(0).get("totalcost").doubleValue() - totalfee)){
					for (Map<String, Double> mapsubsubpaycost : listmapsubpaycost) {
						Set<String> set = mapsubsubpaycost.keySet();
						for (String subpaycostcheckid : set) {
							double subpaycost = mapsubsubpaycost.get(subpaycostcheckid);
							Bill bill = new Bill();
							String branchid = staff.getBranchId();
							String billId = DateUtil.currentDate("yyMMdd") + branchid + roomService.getSequence("SEQ_NEW_BILL");
							bill.setBillId(billId);
							bill.setBranchId(branchid);
							bill.setCheckId(subpaycostcheckid);
							bill.setProjectId("2501");//gai 
							bill.setProjectName("内部交易");
							bill.setDescribe("自动转账");
							bill.setPay(-subpaycost);
							bill.setCost(0.00);
							bill.setPayment("1");//gai 
							bill.setStatus("1");
							bill.setRecordUser(staff.getStaffId());
							
							Bill targetbill = new Bill();
							String targetbranchid = staff.getBranchId();
							String targetbillId = DateUtil.currentDate("yyMMdd") + branchid + roomService.getSequence("SEQ_NEW_BILL");
							targetbill.setBillId(targetbillId);
							targetbill.setBranchId(targetbranchid);
							targetbill.setCheckId(checkid);
							targetbill.setProjectId("2501");//gai 
							targetbill.setProjectName("内部交易");
							targetbill.setDescribe("自动转账");
							targetbill.setPay(subpaycost);
							targetbill.setCost(0.00);
							targetbill.setPayment("1");//gai 
							targetbill.setStatus("3");
							targetbill.setRecordUser(staff.getStaffId());
							
							Transfer transfer = new Transfer();
							String transferId = DateUtil.currentDate("yyMMdd") + branchId + roomService.getSequence("SEQ_NEW_TRANSFER");
							transfer.setTransferId(transferId);
							transfer.setBranchId(branchId);
							transfer.setLastCheckid(checkid);
							transfer.setCurrCheckid(subpaycostcheckid);
							transfer.setRecordUser(staff.getStaffId());
							
							try{
								roomService.save(bill);
								roomService.save(targetbill);
								roomService.save(transfer);
							}catch(Exception e){
								e.printStackTrace();
							}
							
						}
					}
					JSONUtil.responseJSON(response, new AjaxResult(0, "自动转账成功!"));
				}else {
					JSONUtil.responseJSON(response, new AjaxResult(1, "主账金额不足!"));
				}
			}else{
				JSONUtil.responseJSON(response, new AjaxResult(1, "无需自动转账!"));
			}
		}else{
			JSONUtil.responseJSON(response, new AjaxResult(2, "无关联房!"));
		}
		
/*		if( payresult.get(0).get("totalpay") == 0 || payresult.get(0).get("totalpay") <= costresult.get(0).get("totalcost")){
			JSONUtil.responseJSON(response, new AjaxResult(0, "转账金额不足!"));
		}*/
		
		//JSONUtil.responseJSON(response, result);
	}
	
	@RequestMapping("/submitupdateroomprice.do")
	public void submitupdateroomprice(HttpServletRequest request, HttpServletResponse response){
		
	}

	//预离
	@RequestMapping("/queryLeavelive.do")
	public void queryLeavelive(HttpServletRequest request, HttpServletResponse response, String status, String roomtype, 
			String roomfloor) throws Exception {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		List<?> arrivalList = new ArrayList<Object>();
		if (("1").equals(status)) {
			arrivalList = roomService.findBySQL("queryleave", new String[]{branchId, roomtype, roomfloor}, true);
		} else if (("2").equals(status)) {
			arrivalList = roomService.findBySQL("querylive", new String[]{branchId, roomtype, roomfloor}, true);
		}
		JSONUtil.responseJSON(response, arrivalList);
	}
	
	@RequestMapping("/queryRoomByStatus.do")
	public void queryRoomByStatus(HttpServletRequest request, HttpServletResponse response, String status, String roomtype, String roomfloor) throws Exception {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		List<?> arrivalList = new ArrayList<Object>();
		if (("empty").equals(status)) {
			arrivalList = roomService.findBySQL("queryroomempty", new String[]{branchId, roomtype, roomfloor}, true);
		} else if (("dirty").equals(status)) {
			arrivalList = roomService.findBySQL("queryroomdirty", new String[]{branchId, roomtype, roomfloor}, true);
		} else if (("stop").equals(status)) {
			arrivalList = roomService.findBySQL("queryroomestop", new String[]{branchId, roomtype, roomfloor}, true);
		}
		JSONUtil.responseJSON(response, arrivalList);
	}

	@RequestMapping("/queryByRoomType.do")
	public void queryByRoomType(HttpServletRequest request, HttpServletResponse response, String roomfloor, String roomType,
				String label) throws Exception {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		List<?> roomlList = new ArrayList<Object>();
		
		if ("预离".equals(label)) {
			roomlList = roomService.findBySQL("queryleave", new String[]{branchId, roomType, roomfloor}, true);
		} else if ("在住".equals(label)) {
			roomlList = roomService.findBySQL("querylive", new String[]{branchId, roomType, roomfloor}, true);
		} else if ("空净房".equals(label)) {
			roomlList = roomService.findBySQL("queryroomempty", new String[]{branchId, roomType, roomfloor}, true);
		} else if ("脏房".equals(label)) {
			roomlList = roomService.findBySQL("queryroomdirty", new String[]{branchId, roomType, roomfloor}, true);
		} else if ("停售".equals(label)) {
			roomlList = roomService.findBySQL("queryroomestop", new String[]{branchId, roomType, roomfloor}, true);
		} else {
			roomlList = roomService.findBySQL("queryroomtype", new String[]{branchId, roomType, roomfloor, roomType, branchId, roomfloor}, true);
		}
		JSONUtil.responseJSON(response, roomlList); 
	}
	
	@RequestMapping("/queryByRoomFloor.do")
	public void queryByRoomFloor(HttpServletRequest request, HttpServletResponse response, String roomFloor, String roomtype, 
			String label) throws Exception {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		List<?> roomlList = new ArrayList<Object>();
		if (StringUtil.isEmpty(roomFloor)) {
			roomFloor = "";
		}
		
		if ("预离".equals(label)) {
			roomlList = roomService.findBySQL("queryleave", new String[]{branchId, roomtype, roomFloor}, true);
		} else if ("在住".equals(label)) {
			roomlList = roomService.findBySQL("querylive", new String[]{branchId, roomtype, roomFloor}, true);
		} else if ("空净房".equals(label)) {
			roomlList = roomService.findBySQL("queryroomempty", new String[]{branchId, roomtype, roomFloor}, true);
		} else if ("脏房".equals(label)) {
			roomlList = roomService.findBySQL("queryroomdirty", new String[]{branchId, roomtype, roomFloor}, true);
		} else if ("停售".equals(label)) {
			roomlList = roomService.findBySQL("queryroomestop", new String[]{branchId, roomtype, roomFloor}, true);
		} else {
			roomlList = roomService.findBySQL("queryroomfloor", new String[]{branchId, roomFloor, roomFloor, branchId}, true);
		}
		
		Map<Object, Object> roomdata = new HashMap<Object, Object>();
		roomdata.put("roomlist", roomlList);
		roomdata.put("roomfloorcount", roomlList.size());
		JSONUtil.responseJSON(response, roomdata); 
	}
	
	@RequestMapping("/queryByRoomId.do")
	public void queryByRoomId(HttpServletRequest request, HttpServletResponse response, String roomId) throws Exception {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		List<?> roomlList = roomService.findBySQL("queryroomid", new String[]{branchId, roomId, roomId, branchId}, true);
		JSONUtil.responseJSON(response, roomlList); 
	}
	
	@RequestMapping("/setRoomStatus.do")
	public void setRoomStatus(HttpServletRequest request, HttpServletResponse response, String roomstatus, String roomId) throws Exception {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		Room roomdata = (Room) roomService.findOneByProperties(Room.class, "roomKey.roomId", roomId, "roomKey.branchId", branchId, "theme", "1");
		RoomStatus roomstatusdata = (RoomStatus) roomService.findOneByProperties(RoomStatus.class, "branchId", branchId, "roomType", roomdata.getRoomType());
		SysParam sysparam = (SysParam) roomService.findOneByProperties(SysParam.class, "paramType", "ROOMSTATUS", "content", roomstatus);
		
		if (roomdata == null || StringUtil.isEmpty(roomdata.toString())) {
			JSONUtil.responseJSON(response, new AjaxResult(0, "无此房信息!") );
		} else {
			if (roomstatus.equals("0")) {
				roomstatusdata.setCount(roomstatusdata.getCount() + 1);
				roomstatusdata.setSellnum(roomstatusdata.getSellnum() + 1);
				Check checkdata = (Check) roomService.findOneByProperties(Check.class, "roomId", roomId, "status", "1");
				
			} else if (roomstatus.equals("T")) {
				if (roomdata.getRoomKey().getStatus().equals("Z")) {
					roomstatusdata.setSellnum(roomstatusdata.getSellnum() - 1);
					roomstatusdata.setStopnum(roomstatusdata.getStopnum() + 1);
				} else {
					roomstatusdata.setCount(roomstatusdata.getCount() - 1);
					roomstatusdata.setSellnum(roomstatusdata.getSellnum() - 1);
					roomstatusdata.setStopnum(roomstatusdata.getStopnum() + 1);
				}
			} 
			//roomdata.getRoomKey().setStatus(roomstatus);
			Date date = new Date();
			OperateLog operlog = new OperateLog();
			operlog.setBranchId(branchId);
			operlog.setLogId(DateUtil.currentDate("yyMMdd") + branchId + roomService.getSequence("SEQ_OPERATELOG_ID"));
			String operid = InetAddress.getLocalHost().toString();//IP地址
			operid = (String) operid.subSequence(operid.indexOf("/")+1, operid.length());
			operlog.setOperIp(operid);
			operlog.setOperType("6");
			operlog.setOperModule("房态操作");
			operlog.setRecordUser(loginUser.getStaff().getStaffId());
			operlog.setRecordTime(date);
			operlog.setContent(loginUser.getStaff().getStaffId() + "设置房间状态为" + sysparam.getParamName());
			
			try {
				//roomService.save(roomdata);
				roomService.upateroomstatus(branchId, roomId, roomstatus);
				roomService.save(operlog);
				roomService.save(roomstatusdata);
			} catch (Exception e) {
				e.printStackTrace();
			}
			JSONUtil.responseJSON(response, new AjaxResult(1, "操作成功!") );
		}
	}
	
	
	@RequestMapping("CurrentRoom.do")
	public ModelAndView CurrentRoom(HttpServletRequest request, HttpServletResponse response, String madate) throws Exception{
		ModelAndView mv = new ModelAndView();
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		Pagination pagination = SqlBuilder.buildPagination(request);
		List<?> list = roomService.getRoomStatus(madate, branchId, pagination);
		//String sql = "querycurrentstatus";
		//return findBySQLWithPagination(sql, new String []{branchId, madate, branchId, madate, branchId, branchId},pagination);
		mv.addObject("list", list);
		mv.addObject("pagination", pagination);
        mv.setViewName("page/ipmspage/room_statistics/current_room");
		return mv;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("QueryCampaign.do")
	public ModelAndView querycampaign(HttpServletRequest request, HttpServletResponse response, String madate) throws Exception{
		ModelAndView mv = new ModelAndView();
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		List<Campaign> campaign = (List<Campaign>) roomService.findBySQL("querycampaigndata", new String[]{branchId}, true);
		mv.addObject("list", campaign);
        mv.setViewName("page/ipmspage/room_statistics/pagecampaign");
		return mv;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("ForwardRoom.do")
	public ModelAndView ForwardRoom(HttpServletRequest request, HttpServletResponse response, String madate, String days) throws Exception{
		ModelAndView mv = new ModelAndView();
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		Pagination pagination = SqlBuilder.buildPagination(request);
		List<Object> list = new ArrayList<Object>();
		List<Object> listdate = new ArrayList<Object>();
		List<Object> listroomtype = new ArrayList<Object>();
		Map<String, Object> newlistdata = new HashMap<String, Object>();
		Map<String, List<Object>> listinfo = new HashMap<String, List<Object>>();
		int day = Integer.parseInt(days);
		String roomType = null;
		String sumNum = null;
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<?> rt = roomService.findByProperties(RoomType.class, "theme", "1");
		
		for (int x = 0; x < rt.size(); x++){
			if (madate.indexOf("-") > 0) {
				date = sdf.parse(madate); 
			} else {
				date = DateUtil.s2d(madate, "MM/dd/yyyy");
			}
			Calendar c = Calendar.getInstance(); 
			c.setTime(date);
			Map<String, String> newlist = new HashMap<String, String>();
			
			for (int y = 0; y < day; y++){
				String newdate = sdf.format(c.getTime());
				list = roomService.findBySQLWithPagination("queryforward", new String []{branchId, branchId, newdate, 
						branchId, newdate, branchId, newdate, branchId, branchId, newdate}, pagination, true);
				c.add(Calendar.DAY_OF_MONTH,1);
				if (x == 0) {
					listdate.add(newdate);
				}
				for (int a=0; a<list.size(); a++) {
					roomType = ((Map<?, ?>)list.get(a)).get("ROOM_TYPE").toString();
					sumNum = ((Map<?, ?>)list.get(a)).get("SUMNUM").toString();
					if (((RoomType) rt.get(x)).getRoomType().toString().equals(roomType)) {
						newlist.put(newdate, sumNum);
					}
				}
				newlistdata.put(((RoomType) rt.get(x)).getRoomName().toString(), newlist);
			}
			listroomtype.add(((RoomType) rt.get(x)).getRoomName());
		}
		
		JSONUtil.fromObject(newlistdata);
		mv.addObject("listdate", listdate);
		mv.addObject("listinfo", listinfo);
		mv.addObject("listroomtype", listroomtype);
		mv.addObject("newlistdata", newlistdata);
		mv.addObject("pagination", pagination);
        mv.setViewName("page/ipmspage/room_statistics/forward_room");
        return mv;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/checkoutbill.do")
	public void checkoutbill(HttpServletRequest request, HttpServletResponse response, CheckoutRoom checkoutroom ){
		String checkid = checkoutroom.getCheckId();
		LoginUser loginuser = (LoginUser) request.getSession(true)
		.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		LoginGetShift loginGetShift = (LoginGetShift) request.getSession(true).getAttribute("loginGetShift");
		Staff staff = loginuser.getStaff();
		String branchId = staff.getBranchId();
		String recordUser = staff.getStaffId();
		Check check = (Check) roomService.findOneByProperties(Check.class, "checkId", checkid);
		Order order = (Order) roomService.findOneByProperties(Order.class, "orderId", checkid);
		String roomid = check.getRoomId();
		String sqlcost = "totalcostfromcheck";
		List<Map<String, BigDecimal>> costresult = roomService.findBySQL(sqlcost, new String[]{checkid, branchId}, true);
		String sqlpay = "totalpayfromcheck";
		List<Map<String, BigDecimal>> payresult = roomService.findBySQL(sqlpay, new String[]{checkid, branchId}, true);
		double pay = 0;
		double cost = 0;
		if(!costresult.isEmpty()){
			pay = payresult.get(0).get("totalpay").doubleValue();
		}
		if(!payresult.isEmpty()){
			cost = costresult.get(0).get("totalcost").doubleValue();
		}
		if("2001".equals(checkoutroom.getProjectid())){
			checkoutroom.setProject("现金支出");
		}else if("".equals(checkoutroom.getProjectid())){
			//checkoutroom.setProject("");
		}else if("2002".equals(checkoutroom.getProjectid())){
			checkoutroom.setProject("现金");
		}else if(checkoutroom.getProjectid() == null){
			//checkoutroom.setProject("");
		}else if("2003".equals(checkoutroom.getProjectid())){
			checkoutroom.setProject("银行卡");
		}
		List<Bill> listbill = roomService.findByProperties(Bill.class, "checkId", checkid, "projectId", "1234");
		List<Bill> listbillclock = roomService.findByProperties(Bill.class, "checkId", checkid, "projectId", "2004");
		
		Calendar fourtime = Calendar.getInstance();
		fourtime.set(Calendar.HOUR_OF_DAY, 4);
		fourtime.set(Calendar.MINUTE, 0);
		fourtime.set(Calendar.SECOND, 0);
		Calendar sixtime = Calendar.getInstance();
		sixtime.set(Calendar.HOUR_OF_DAY, 6);
		sixtime.set(Calendar.MINUTE, 0);
		sixtime.set(Calendar.SECOND, 0);
		Calendar twelvetime = Calendar.getInstance();
		twelvetime.set(Calendar.HOUR_OF_DAY, 12);
		twelvetime.set(Calendar.MINUTE, 0);
		twelvetime.set(Calendar.SECOND, 0);
		Calendar twelvetime15min = Calendar.getInstance();
		twelvetime15min.set(Calendar.HOUR_OF_DAY, 12);
		twelvetime15min.set(Calendar.MINUTE, 15);
		twelvetime15min.set(Calendar.SECOND, 0);
		Calendar fourteentime = Calendar.getInstance();
		fourteentime.set(Calendar.HOUR_OF_DAY, 14);
		fourteentime.set(Calendar.MINUTE, 0);
		fourteentime.set(Calendar.SECOND, 0);
		Calendar sixteen15min = Calendar.getInstance();
		sixteen15min.set(Calendar.HOUR_OF_DAY, 16);
		sixteen15min.set(Calendar.MINUTE, 15);
		sixteen15min.set(Calendar.SECOND, 0);
		Calendar eighteentime = Calendar.getInstance();
		eighteentime.set(Calendar.HOUR_OF_DAY, 18);
		eighteentime.set(Calendar.MINUTE, 0);
		eighteentime.set(Calendar.SECOND, 0);
		
		Calendar currenttime = Calendar.getInstance();
		
		Calendar checkIntime = Calendar.getInstance();
		checkIntime.setTime(check.getCheckinTime());
		
		String activity = order.getActivity();
		//checkoutbilladdbill(request, response, branchId, checkid, checkoutroom.getAmount(), recordUser, pay, cost, roomid, checkoutroom);
		JSONUtil.responseJSON(response, new AjaxResult(1, "结算成功!"));
		
		
		if(StringUtils.isEmpty(activity)){//无担保StringUtils.isEmpty(activity)
			
			if(listbill.size() <= 0){//判断是否是钟点房
//				if(DateUtil.isSameDay(currenttime, checkIntime)){//判断是否是同一天，（全天房）
//					
//				}
				if( listbill.size() <= 0){//判断是全日租
					try{
						if(!"0".equals(checkoutroom.getSubprice())){
							Bill costbill = new Bill();
							String costbillId = DateUtil.currentDate("yyMMdd") + branchId + roomService.getSequence("SEQ_NEW_BILL");
							costbill.setBillId(costbillId);
							costbill.setBranchId(branchId);
							costbill.setCheckId(checkid);
							costbill.setProjectId("1234");
							costbill.setProjectName("房费");
							costbill.setDescribe("手动日结");
							costbill.setCost(Double.valueOf(checkoutroom.getSubprice()));
							costbill.setPay(0.00);
							costbill.setPayment("1");
							costbill.setStatus("1");
							costbill.setRecordUser(recordUser);
							costbill.setShift(loginGetShift.getShiftId());
							costbill.setCashBox(loginGetShift.getCashbox());
							roomService.save(costbill);
						}
						
						if(!(checkoutroom.getAmount() == null || "".equals(checkoutroom.getAmount()) || checkoutroom.getProjectid() == null || "".equals(checkoutroom.getProjectid()))){
							Bill bill = new Bill();
							String billId = DateUtil.currentDate("yyMMdd") + branchId + roomService.getSequence("SEQ_NEW_BILL");
							bill.setBillId(billId);
							bill.setBranchId(branchId);
							bill.setCheckId(checkid);
							bill.setProjectId(checkoutroom.getProjectid());
							bill.setProjectName(checkoutroom.getProject());
							bill.setDescribe("结账");
							bill.setCost(0.00);
							if("2001".equals(checkoutroom.getProjectid())){
								bill.setPay(-Double.valueOf(checkoutroom.getAmount()));				
								bill.setPayment("1");
							}else if("2002".equals(checkoutroom.getProjectid())){
								bill.setPay(Double.valueOf(checkoutroom.getAmount()));
								bill.setPayment("1");
							}else if("2003".equals(checkoutroom.getProjectid())){
								bill.setPay(Double.valueOf(checkoutroom.getAmount()));
								bill.setPayment("2");
							}else {
								bill.setPay(Double.valueOf(checkoutroom.getAmount()));
								bill.setPayment("3");
							}
							bill.setStatus("1");
							bill.setRecordUser(recordUser);
							bill.setShift(loginGetShift.getShiftId());
							bill.setCashBox(loginGetShift.getCashbox());
							roomService.save(bill);
						}
						roomService.updatestatus(checkid, pay, cost, new Date());
						roomService.upateroomstatus(branchId, roomid, "Z");
						roomService.updateorderstatus(checkid, "4");
					}catch(Exception e){
						e.printStackTrace();
					}
					//checkoutbilladdbill(request, response, branchId, checkid, checkoutroom.getAmount(), recordUser, pay, cost, roomid, checkoutroom);
				}else {// 判断不是全日租,不是钟点房，是全天房
					if(currenttime.compareTo(twelvetime15min) < 0){//12点15分钟之前
						if(!(checkoutroom.getAmount() == null || "".equals(checkoutroom.getAmount()) || checkoutroom.getProjectid() == null || "".equals(checkoutroom.getProjectid()))){
							Bill bill = new Bill();
							String billId = DateUtil.currentDate("yyMMdd") + branchId + roomService.getSequence("SEQ_NEW_BILL");
							bill.setBillId(billId);
							bill.setBranchId(branchId);
							bill.setCheckId(checkid);
							bill.setProjectId(checkoutroom.getProjectid());
							bill.setProjectName(checkoutroom.getProject());
							bill.setDescribe("结账");
							bill.setCost(0.00);
							if("2001".equals(checkoutroom.getProjectid())){
								bill.setPay(-Double.valueOf(checkoutroom.getAmount()));				
								bill.setPayment("1");
							}else if("2002".equals(checkoutroom.getProjectid())){
								bill.setPay(Double.valueOf(checkoutroom.getAmount()));
								bill.setPayment("1");
							}else if("2003".equals(checkoutroom.getProjectid())){
								bill.setPay(Double.valueOf(checkoutroom.getAmount()));
								bill.setPayment("2");
							}else {
								bill.setPay(Double.valueOf(checkoutroom.getAmount()));
								bill.setPayment("3");
							}
							bill.setStatus("1");
							bill.setRecordUser(recordUser);
							bill.setShift(loginGetShift.getShiftId());
							bill.setCashBox(loginGetShift.getCashbox());
							roomService.save(bill);
						}
							roomService.updatestatus(checkid, pay, cost, new Date());
							roomService.upateroomstatus(branchId, roomid, "Z");
							roomService.updateorderstatus(checkid, "4");
							
					}else if(currenttime.compareTo(twelvetime15min) >= 0 && currenttime.compareTo(sixteen15min) < 0){
						Bill costbill = new Bill();
						String costbillId = DateUtil.currentDate("yyMMdd") + branchId + roomService.getSequence("SEQ_NEW_BILL");
						costbill.setBillId(costbillId);
						costbill.setBranchId(branchId);
						costbill.setCheckId(checkid);
						costbill.setProjectId("1001");
						costbill.setProjectName("赔偿");
						costbill.setDescribe("逾期罚金");
						costbill.setCost(Double.valueOf(checkoutroom.getSubprice()));
						costbill.setPay(0.00);
						costbill.setPayment("1");
						costbill.setStatus("1");
						costbill.setRecordUser(recordUser);
						costbill.setShift(loginGetShift.getShiftId());
						costbill.setCashBox(loginGetShift.getCashbox());
						roomService.save(costbill);
						
						if(!(checkoutroom.getAmount() == null || "".equals(checkoutroom.getAmount()) || checkoutroom.getProjectid() == null || "".equals(checkoutroom.getProjectid()))){
							Bill bill = new Bill();
							String billId = DateUtil.currentDate("yyMMdd") + branchId + roomService.getSequence("SEQ_NEW_BILL");
							bill.setBillId(billId);
							bill.setBranchId(branchId);
							bill.setCheckId(checkid);
							bill.setProjectId(checkoutroom.getProjectid());
							bill.setProjectName(checkoutroom.getProject());
							bill.setDescribe("结账");
							bill.setCost(0.00);
							if("2001".equals(checkoutroom.getProjectid())){
								bill.setPay(-Double.valueOf(checkoutroom.getAmount()));				
								bill.setPayment("1");
							}else if("2002".equals(checkoutroom.getProjectid())){
								bill.setPay(Double.valueOf(checkoutroom.getAmount()));
								bill.setPayment("1");
							}else if("2003".equals(checkoutroom.getProjectid())){
								bill.setPay(Double.valueOf(checkoutroom.getAmount()));
								bill.setPayment("2");
							}else {
								bill.setPay(Double.valueOf(checkoutroom.getAmount()));
								bill.setPayment("3");
							}
							bill.setStatus("1");
							bill.setRecordUser(recordUser);
							bill.setShift(loginGetShift.getShiftId());
							bill.setCashBox(loginGetShift.getCashbox());
							roomService.save(bill);
						}
							roomService.updatestatus(checkid, pay, cost, new Date());
							roomService.upateroomstatus(branchId, roomid, "Z");
							roomService.updateorderstatus(checkid, "4");
					}else if(currenttime.compareTo(sixteen15min) >= 0){//超过4小时之后
						Bill costbill = new Bill();
						String costbillId = DateUtil.currentDate("yyMMdd") + branchId + roomService.getSequence("SEQ_NEW_BILL");
						costbill.setBillId(costbillId);
						costbill.setBranchId(branchId);
						costbill.setCheckId(checkid);
						costbill.setProjectId("1001");
						costbill.setProjectName("赔偿");
						costbill.setDescribe("逾期罚金");
						costbill.setCost(Double.valueOf(checkoutroom.getSubprice()));
						costbill.setPay(0.00);
						costbill.setPayment("1");
						costbill.setStatus("1");
						costbill.setRecordUser(recordUser);
						costbill.setShift(loginGetShift.getShiftId());
						costbill.setCashBox(loginGetShift.getCashbox());
						roomService.save(costbill);
						
						if(!(checkoutroom.getAmount() == null || "".equals(checkoutroom.getAmount()) || checkoutroom.getProjectid() == null || "".equals(checkoutroom.getProjectid()))){
							Bill bill = new Bill();
							String billId = DateUtil.currentDate("yyMMdd") + branchId + roomService.getSequence("SEQ_NEW_BILL");
							bill.setBillId(billId);
							bill.setBranchId(branchId);
							bill.setCheckId(checkid);
							bill.setProjectId(checkoutroom.getProjectid());
							bill.setProjectName(checkoutroom.getProject());
							bill.setDescribe("结账");
							bill.setCost(0.00);
							if("2001".equals(checkoutroom.getProjectid())){
								bill.setPay(-Double.valueOf(checkoutroom.getAmount()));				
								bill.setPayment("1");
							}else if("2002".equals(checkoutroom.getProjectid())){
								bill.setPay(Double.valueOf(checkoutroom.getAmount()));
								bill.setPayment("1");
							}else if("2003".equals(checkoutroom.getProjectid())){
								bill.setPay(Double.valueOf(checkoutroom.getAmount()));
								bill.setPayment("2");
							}else {
								bill.setPay(Double.valueOf(checkoutroom.getAmount()));
								bill.setPayment("3");
							}
							bill.setStatus("1");
							bill.setRecordUser(recordUser);
							bill.setShift(loginGetShift.getShiftId());
							bill.setCashBox(loginGetShift.getCashbox());
							roomService.save(bill);
						}
							roomService.updatestatus(checkid, pay, cost, new Date());
							roomService.upateroomstatus(branchId, roomid, "Z");
							roomService.updateorderstatus(checkid, "4");
					}
				}
			}
		}else {
			if( listbill.size() <= 0){//判断是全日租
				try{
					if(!"0".equals(checkoutroom.getSubprice())){
						Bill costbill = new Bill();
						String costbillId = DateUtil.currentDate("yyMMdd") + branchId + roomService.getSequence("SEQ_NEW_BILL");
						costbill.setBillId(costbillId);
						costbill.setBranchId(branchId);
						costbill.setCheckId(checkid);
						costbill.setProjectId("1234");
						costbill.setProjectName("房费");
						costbill.setDescribe("手动日结");
						costbill.setCost(Double.valueOf(checkoutroom.getSubprice()));
						costbill.setPay(0.00);
						costbill.setPayment("1");
						costbill.setStatus("1");
						costbill.setRecordUser(recordUser);
						costbill.setShift(loginGetShift.getShiftId());
						costbill.setCashBox(loginGetShift.getCashbox());
						roomService.save(costbill);
					}
					
					if(!(checkoutroom.getAmount() == null || "".equals(checkoutroom.getAmount()) || checkoutroom.getProjectid() == null || "".equals(checkoutroom.getProjectid()))){
						Bill bill = new Bill();
						String billId = DateUtil.currentDate("yyMMdd") + branchId + roomService.getSequence("SEQ_NEW_BILL");
						bill.setBillId(billId);
						bill.setBranchId(branchId);
						bill.setCheckId(checkid);
						bill.setProjectId(checkoutroom.getProjectid());
						bill.setProjectName(checkoutroom.getProject());
						bill.setDescribe("结账");
						bill.setCost(0.00);
						if("2001".equals(checkoutroom.getProjectid())){
							bill.setPay(-Double.valueOf(checkoutroom.getAmount()));				
							bill.setPayment("1");
						}else if("2002".equals(checkoutroom.getProjectid())){
							bill.setPay(Double.valueOf(checkoutroom.getAmount()));
							bill.setPayment("1");
						}else if("2003".equals(checkoutroom.getProjectid())){
							bill.setPay(Double.valueOf(checkoutroom.getAmount()));
							bill.setPayment("2");
						}else {
							bill.setPay(Double.valueOf(checkoutroom.getAmount()));
							bill.setPayment("3");
						}
						bill.setStatus("1");
						bill.setRecordUser(recordUser);
						bill.setShift(loginGetShift.getShiftId());
						bill.setCashBox(loginGetShift.getCashbox());
						roomService.save(bill);
					}
					roomService.updatestatus(checkid, pay, cost, new Date());
					roomService.upateroomstatus(branchId, roomid, "Z");
					roomService.updateorderstatus(checkid, "4");
				}catch(Exception e){
					e.printStackTrace();
				}
				//checkoutbilladdbill(request, response, branchId, checkid, checkoutroom.getAmount(), recordUser, pay, cost, roomid, checkoutroom);
			}else {// 判断不是全日租,不是钟点房，是全天房
				if(currenttime.compareTo(twelvetime15min) < 0){//12点15分钟之前
					if(!(checkoutroom.getAmount() == null || "".equals(checkoutroom.getAmount()) || checkoutroom.getProjectid() == null || "".equals(checkoutroom.getProjectid()))){
						Bill bill = new Bill();
						String billId = DateUtil.currentDate("yyMMdd") + branchId + roomService.getSequence("SEQ_NEW_BILL");
						bill.setBillId(billId);
						bill.setBranchId(branchId);
						bill.setCheckId(checkid);
						bill.setProjectId(checkoutroom.getProjectid());
						bill.setProjectName(checkoutroom.getProject());
						bill.setDescribe("结账");
						bill.setCost(0.00);
						if("2001".equals(checkoutroom.getProjectid())){
							bill.setPay(-Double.valueOf(checkoutroom.getAmount()));				
							bill.setPayment("1");
						}else if("2002".equals(checkoutroom.getProjectid())){
							bill.setPay(Double.valueOf(checkoutroom.getAmount()));
							bill.setPayment("1");
						}else if("2003".equals(checkoutroom.getProjectid())){
							bill.setPay(Double.valueOf(checkoutroom.getAmount()));
							bill.setPayment("2");
						}else {
							bill.setPay(Double.valueOf(checkoutroom.getAmount()));
							bill.setPayment("3");
						}
						bill.setStatus("1");
						bill.setRecordUser(recordUser);
						bill.setShift(loginGetShift.getShiftId());
						bill.setCashBox(loginGetShift.getCashbox());
						roomService.save(bill);
					}
						roomService.updatestatus(checkid, pay, cost, new Date());
						roomService.upateroomstatus(branchId, roomid, "Z");
						roomService.updateorderstatus(checkid, "4");
						
				}else if(currenttime.compareTo(twelvetime15min) >= 0 && currenttime.compareTo(sixteen15min) < 0){
					Bill costbill = new Bill();
					String costbillId = DateUtil.currentDate("yyMMdd") + branchId + roomService.getSequence("SEQ_NEW_BILL");
					costbill.setBillId(costbillId);
					costbill.setBranchId(branchId);
					costbill.setCheckId(checkid);
					costbill.setProjectId("1001");
					costbill.setProjectName("赔偿");
					costbill.setDescribe("逾期罚金");
					costbill.setCost(Double.valueOf(checkoutroom.getSubprice()));
					costbill.setPay(0.00);
					costbill.setPayment("1");
					costbill.setStatus("1");
					costbill.setRecordUser(recordUser);
					costbill.setShift(loginGetShift.getShiftId());
					costbill.setCashBox(loginGetShift.getCashbox());
					roomService.save(costbill);
					
					if(!(checkoutroom.getAmount() == null || "".equals(checkoutroom.getAmount()) || checkoutroom.getProjectid() == null || "".equals(checkoutroom.getProjectid()))){
						Bill bill = new Bill();
						String billId = DateUtil.currentDate("yyMMdd") + branchId + roomService.getSequence("SEQ_NEW_BILL");
						bill.setBillId(billId);
						bill.setBranchId(branchId);
						bill.setCheckId(checkid);
						bill.setProjectId(checkoutroom.getProjectid());
						bill.setProjectName(checkoutroom.getProject());
						bill.setDescribe("结账");
						bill.setCost(0.00);
						if("2001".equals(checkoutroom.getProjectid())){
							bill.setPay(-Double.valueOf(checkoutroom.getAmount()));				
							bill.setPayment("1");
						}else if("2002".equals(checkoutroom.getProjectid())){
							bill.setPay(Double.valueOf(checkoutroom.getAmount()));
							bill.setPayment("1");
						}else if("2003".equals(checkoutroom.getProjectid())){
							bill.setPay(Double.valueOf(checkoutroom.getAmount()));
							bill.setPayment("2");
						}else {
							bill.setPay(Double.valueOf(checkoutroom.getAmount()));
							bill.setPayment("3");
						}
						bill.setStatus("1");
						bill.setRecordUser(recordUser);
						bill.setShift(loginGetShift.getShiftId());
						bill.setCashBox(loginGetShift.getCashbox());
						roomService.save(bill);
					}
						roomService.updatestatus(checkid, pay, cost, new Date());
						roomService.upateroomstatus(branchId, roomid, "Z");
						roomService.updateorderstatus(checkid, "4");
				}else if(currenttime.compareTo(sixteen15min) >= 0){//超过4小时之后
					Bill costbill = new Bill();
					String costbillId = DateUtil.currentDate("yyMMdd") + branchId + roomService.getSequence("SEQ_NEW_BILL");
					costbill.setBillId(costbillId);
					costbill.setBranchId(branchId);
					costbill.setCheckId(checkid);
					costbill.setProjectId("1001");
					costbill.setProjectName("赔偿");
					costbill.setDescribe("逾期罚金");
					costbill.setCost(Double.valueOf(checkoutroom.getSubprice()));
					costbill.setPay(0.00);
					costbill.setPayment("1");
					costbill.setStatus("1");
					costbill.setRecordUser(recordUser);
					costbill.setShift(loginGetShift.getShiftId());
					costbill.setCashBox(loginGetShift.getCashbox());
					roomService.save(costbill);
					
					if(!(checkoutroom.getAmount() == null || "".equals(checkoutroom.getAmount()) || checkoutroom.getProjectid() == null || "".equals(checkoutroom.getProjectid()))){
						Bill bill = new Bill();
						String billId = DateUtil.currentDate("yyMMdd") + branchId + roomService.getSequence("SEQ_NEW_BILL");
						bill.setBillId(billId);
						bill.setBranchId(branchId);
						bill.setCheckId(checkid);
						bill.setProjectId(checkoutroom.getProjectid());
						bill.setProjectName(checkoutroom.getProject());
						bill.setDescribe("结账");
						bill.setCost(0.00);
						if("2001".equals(checkoutroom.getProjectid())){
							bill.setPay(-Double.valueOf(checkoutroom.getAmount()));				
							bill.setPayment("1");
						}else if("2002".equals(checkoutroom.getProjectid())){
							bill.setPay(Double.valueOf(checkoutroom.getAmount()));
							bill.setPayment("1");
						}else if("2003".equals(checkoutroom.getProjectid())){
							bill.setPay(Double.valueOf(checkoutroom.getAmount()));
							bill.setPayment("2");
						}else {
							bill.setPay(Double.valueOf(checkoutroom.getAmount()));
							bill.setPayment("3");
						}
						bill.setStatus("1");
						bill.setRecordUser(recordUser);
						bill.setShift(loginGetShift.getShiftId());
						bill.setCashBox(loginGetShift.getCashbox());
						roomService.save(bill);
					}
						roomService.updatestatus(checkid, pay, cost, new Date());
						roomService.upateroomstatus(branchId, roomid, "Z");
						roomService.updateorderstatus(checkid, "4");
				}
			}
		
		}
	
		//double pary = payresult.get(0).get("totalpay").doubleValue();
		//if( payresult.get(0).get("totalpay").doubleValue() >= (costresult.get(0).get("totalcost").doubleValue())){
//		roomService.updatestatus(checkid, pay, cost);
//		roomService.upateroomstatus(branchId, roomid, "Z");
//		JSONUtil.responseJSON(response, new AjaxResult(1, "结算成功!"));
		//}else {
		//	JSONUtil.responseJSON(response, new AjaxResult(0, "账户金额不足!"));
		//}
	}
	
	public void checkoutbilladdbill(HttpServletRequest request, HttpServletResponse response, 
			String branchId, String checkid, String roomPrice, String recordUser, double pay, double cost, String roomid, CheckoutRoom checkoutroom){
		LoginGetShift loginGetShift = (LoginGetShift) request.getSession(true).getAttribute("loginGetShift");
		if(!(roomPrice == null || "".equals(roomPrice) || checkoutroom.getProjectid() == null || "".equals(checkoutroom.getProjectid()))){
			Bill bill = new Bill();
			String billId = DateUtil.currentDate("yyMMdd") + branchId + roomService.getSequence("SEQ_NEW_BILL");
			bill.setBillId(billId);
			bill.setBranchId(branchId);
			bill.setCheckId(checkid);
			bill.setProjectId(checkoutroom.getProjectid());
			bill.setProjectName(checkoutroom.getProject());
			bill.setDescribe("结账");
			bill.setCost(0.00);
			if("2001".equals(checkoutroom.getProjectid())){
				bill.setPay(-Double.valueOf(roomPrice));				
				bill.setPayment("1");
			}else if("2002".equals(checkoutroom.getProjectid())){
				bill.setPay(Double.valueOf(roomPrice));
				bill.setPayment("1");
			}else if("2003".equals(checkoutroom.getProjectid())){
				bill.setPay(Double.valueOf(roomPrice));
				bill.setPayment("2");
			}else {
				bill.setPay(Double.valueOf(roomPrice));
				bill.setPayment("3");
			}
			bill.setStatus("1");
			bill.setRecordUser(recordUser);
			bill.setShift(loginGetShift.getShiftId());
			bill.setCashBox(loginGetShift.getCashbox());
			
			Bill costbill = new Bill();
			String costbillId = DateUtil.currentDate("yyMMdd") + branchId + roomService.getSequence("SEQ_NEW_BILL");
			costbill.setBillId(costbillId);
			costbill.setBranchId(branchId);
			costbill.setCheckId(checkid);
			costbill.setProjectId(checkoutroom.getProjectid());
			costbill.setProjectName(checkoutroom.getProject());
			costbill.setDescribe("结账");
			costbill.setCost(Double.valueOf(roomPrice));
			costbill.setPay(0.00);
			costbill.setPayment("1");
			costbill.setStatus("1");
			costbill.setRecordUser(recordUser);
			costbill.setShift(loginGetShift.getShiftId());
			costbill.setCashBox(loginGetShift.getCashbox());
			
			roomService.save(bill);
			roomService.save(costbill);
		}
		roomService.updatestatus(checkid, pay, cost, new Date());
		roomService.upateroomstatus(branchId, roomid, "Z");
		roomService.updateorderstatus(checkid, "4");
	}
	
	@RequestMapping("/checkoutworkbill.do")
	public void checkoutworkbill(HttpServletRequest request, HttpServletResponse response, String workbillid){
		LoginUser loginuser = (LoginUser) request.getSession(true)
		.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		//String branchId = staff.getStaffId();
		String recordUser = staff.getStaffId();
		WorkBill workbill = (WorkBill) roomService.findOneByProperties(WorkBill.class, "workbillId", workbillid);
		workbill.setFinalTime(new Date());
		workbill.setFinalUser(recordUser);
		workbill.setStatus("2");;
		try{
			roomService.save(workbill);
			JSONUtil.responseJSON(response, new AjaxResult(1, "结账成功!"));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/loadpayandcost.do")
	public void loadpayandcost(HttpServletRequest request, HttpServletResponse response, String checkid){
		LoginUser loginuser = (LoginUser) request.getSession(true)
		.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String branchId = staff.getBranchId();
		Check check = (Check) roomService.findOneByProperties(Check.class, "checkId", checkid);
		Order order = (Order) roomService.findOneByProperties(Order.class, "orderId", checkid);
		String sqlcost = "totalcostfromcheck";
		List<Map<String, BigDecimal>> costresult = roomService.findBySQL(sqlcost, new String[]{checkid, branchId}, true);
		String sqlpay = "totalpayfromcheck";
		List<Map<String, BigDecimal>> payresult = roomService.findBySQL(sqlpay, new String[]{checkid, branchId}, true);
		double pay = 0;
		double cost = 0;
		if(!costresult.isEmpty()){
			pay = payresult.get(0).get("totalpay").doubleValue();
		}
		if(!payresult.isEmpty()){
			cost = costresult.get(0).get("totalcost").doubleValue();
		}
		
		List<Bill> listbill = roomService.findByProperties(Bill.class, "checkId", checkid, "projectId", "1234");
		List<Bill> listbillclock = roomService.findByProperties(Bill.class, "checkId", checkid, "projectId", "2004");
		
		Calendar fourtime = Calendar.getInstance();
		fourtime.set(Calendar.HOUR_OF_DAY, 4);
		fourtime.set(Calendar.MINUTE, 0);
		fourtime.set(Calendar.SECOND, 0);
		Calendar sixtime = Calendar.getInstance();
		sixtime.set(Calendar.HOUR_OF_DAY, 6);
		sixtime.set(Calendar.MINUTE, 0);
		sixtime.set(Calendar.SECOND, 0);
		Calendar twelvetime = Calendar.getInstance();
		twelvetime.set(Calendar.HOUR_OF_DAY, 12);
		twelvetime.set(Calendar.MINUTE, 0);
		twelvetime.set(Calendar.SECOND, 0);
		Calendar twelvetime15min = Calendar.getInstance();
		twelvetime15min.set(Calendar.HOUR_OF_DAY, 12);
		twelvetime15min.set(Calendar.MINUTE, 15);
		twelvetime15min.set(Calendar.SECOND, 0);
		Calendar fourteentime = Calendar.getInstance();
		fourteentime.set(Calendar.HOUR_OF_DAY, 14);
		fourteentime.set(Calendar.MINUTE, 0);
		fourteentime.set(Calendar.SECOND, 0);
		Calendar sixteen15min = Calendar.getInstance();
		sixteen15min.set(Calendar.HOUR_OF_DAY, 16);
		sixteen15min.set(Calendar.MINUTE, 15);
		sixteen15min.set(Calendar.SECOND, 0);
		Calendar eighteentime = Calendar.getInstance();
		eighteentime.set(Calendar.HOUR_OF_DAY, 18);
		eighteentime.set(Calendar.MINUTE, 0);
		eighteentime.set(Calendar.SECOND, 0);
		
		Calendar currenttime = Calendar.getInstance();
		
		Calendar checkIntime = Calendar.getInstance();
		checkIntime.setTime(check.getCheckinTime());
		
		String activity = order.getActivity();
//		if("2".equalsIgnoreCase(check.getStatus())){
//			JSONUtil.responseJSON(response, new AjaxResult(1, "当前已结算!"));
//			return;
//		}
		
		double hourprice = 10;
		double subprice = 0;
		
		if(StringUtils.isEmpty(activity)){//无担保StringUtils.isEmpty(activity)
			
			if(listbillclock.size() > 0){//判断是不是钟点房
//				if(DateUtil.isSameDay(currenttime, checkIntime)){//判断是否是同一天，（全天房）
//					
//				}
				if( listbill.size() <= 0){//判断是（全日租）
					//subprice = check.getRoomPrice();
					Calendar checkIntime15min = (Calendar) checkIntime.clone();
					checkIntime15min.add(Calendar.MINUTE, 15);
					Calendar checkIntime4hour15min = (Calendar) checkIntime15min.clone();
					checkIntime4hour15min.add(Calendar.HOUR_OF_DAY, 4);
					if(currenttime.compareTo(checkIntime15min) < 0){
						subprice = 0;
					}else if(currenttime.compareTo(checkIntime15min) >= 0 && currenttime.compareTo(checkIntime4hour15min) < 0){
						subprice = DateUtil.subHour(currenttime, checkIntime15min) * hourprice;
					}else if(currenttime.compareTo(checkIntime4hour15min) >= 0){
						subprice = check.getRoomPrice();
					}
				}else {// 判断不是全日租,不是钟点房，是全天房
					if(currenttime.compareTo(twelvetime15min) < 0){//12点15分钟之前
						subprice = 0;
					}else if(currenttime.compareTo(twelvetime15min) >= 0 && currenttime.compareTo(sixteen15min) < 0){
						subprice = DateUtil.subHour(currenttime, twelvetime15min) * hourprice;
					}else if(currenttime.compareTo(sixteen15min) >= 0){
						subprice = check.getRoomPrice();
					}
					
				}
			}else {//是钟点房
				subprice = DateUtil.subHour(currenttime, checkIntime) * hourprice;
			}
		}else {//有担保，自动checkin
			if( listbill.size() <= 0){//判断是（全日租）
				subprice = check.getRoomPrice();
			}else {// 判断不是全日租,不是钟点房，是全天房
				if(currenttime.compareTo(twelvetime15min) < 0){//12点15分钟之前
					subprice = 0;
				}else if(currenttime.compareTo(twelvetime15min) >= 0 && currenttime.compareTo(sixteen15min) < 0){
					subprice = DateUtil.subHour(currenttime, twelvetime15min) * hourprice;
				}else if(currenttime.compareTo(sixteen15min) >= 0){
					subprice = check.getRoomPrice();
				}
				
			}
		}
		
		
		
		if( !"1".equals(check.getStatus())){
			subprice = 0;
		}
		Map<String, Double> mappayandcost = new HashMap<String, Double>();
		mappayandcost.put("pay", pay);
		mappayandcost.put("cost", cost);
		mappayandcost.put("TTV", check.getTtv()==null?0:check.getTtv());
		mappayandcost.put("price", check.getRoomPrice());
		mappayandcost.put("subprice", subprice);
		JSONUtil.responseJSON(response, mappayandcost);
	}
	
	@RequestMapping("/checkroompayoff.do")
	public void checkroompayoff(HttpServletRequest request, HttpServletResponse response, String  checkid ){
		Check check = (Check) roomService.findOneByProperties(Check.class, "checkId", checkid);
		if("2".equalsIgnoreCase(check.getStatus())){
			JSONUtil.responseJSON(response, new AjaxResult(0, "当前已结算!"));
		}else {
			JSONUtil.responseJSON(response, new AjaxResult(1, "可以结算!"));
		}
	}


	@SuppressWarnings("unchecked")
	@RequestMapping("/loadpayandcostworkbill.do")
	public void loadpayandcostworkbill(HttpServletRequest request, HttpServletResponse response, String workbillid){
		LoginUser loginuser = (LoginUser) request.getSession(true)
		.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String branchId = staff.getBranchId();
		String sqlcost = "totalcostfromwork";
		List<Map<String, BigDecimal>> costresult = roomService.findBySQL(sqlcost, new String[]{workbillid, branchId}, true);
		String sqlpay = "totalpayfromwork";
		List<Map<String, BigDecimal>> payresult = roomService.findBySQL(sqlpay, new String[]{workbillid, branchId}, true);
		double pay = 0;
		double cost = 0;
		if(!costresult.isEmpty()){
			pay = payresult.get(0).get("totalpay").doubleValue();
		}
		if(!payresult.isEmpty()){
			cost = costresult.get(0).get("totalcost").doubleValue();
		}
		Map<String, Double> mappayandcost = new HashMap<String, Double>();
		mappayandcost.put("pay", pay);
		mappayandcost.put("cost", cost);
		JSONUtil.responseJSON(response, mappayandcost);
	}
	
	@RequestMapping("/consumption.do")
	public void consumption(HttpServletRequest request, HttpServletResponse response, String strbillid, String offset){
		LoginUser loginuser = (LoginUser) request.getSession(true)
		.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		LoginGetShift loginGetShift = (LoginGetShift) request.getSession(true).getAttribute("loginGetShift");
		Staff staff = loginuser.getStaff();
		String[] arrbill = strbillid.split(",");
		for (String billid : arrbill) {
			Bill consumebill = new Bill();
			Bill bill = (Bill) roomService.findOneByProperties(Bill.class, "billId", billid);
			String billId = DateUtil.currentDate("yyMMdd") + staff.getBranchId() + roomService.getSequence("SEQ_NEW_BILL");
			consumebill.setBillId(billId);
			consumebill.setBranchId(staff.getBranchId());
			consumebill.setCheckId(bill.getCheckId());
			consumebill.setDescribe(bill.getDescribe());
			consumebill.setOffset(offset);
			consumebill.setPayment(bill.getPayment());
			consumebill.setProjectId(bill.getProjectId());
			consumebill.setProjectName(bill.getProjectName());
			consumebill.setRecordUser(staff.getStaffId());
			consumebill.setRemark(bill.getBillId());
			consumebill.setStatus("2");
			consumebill.setShift(loginGetShift.getShiftId());
			consumebill.setCashBox(loginGetShift.getCashbox());
			
			String projectid = bill.getProjectId();
			//记日志
			OffsetLog otlog = new OffsetLog();
			String logid = DateUtil.currentDate("yyMMdd")+staff.getBranchId()+roomService.getSequence("SEQ_OFFSETLOG_ID");
			otlog.setLogId(logid);
			otlog.setBranchId(staff.getBranchId());
			otlog.setCheckId(bill.getCheckId());
			otlog.setLastBillId(bill.getBillId());
			otlog.setCurrBillId(consumebill.getBillId());
			otlog.setRecordUser(staff.getStaffId());
			otlog.setRecordTime(new Date());
			if(projectid.startsWith("2")){
				double price = bill.getPay();
				if(price <= 0){
					JSONUtil.responseJSON(response, new AjaxResult(0, "选中了错误的冲减金额!"));
					continue;
				}
				consumebill.setPay(-price);
				consumebill.setCost(0.00);
				otlog.setOffsetFee(bill.getPay());//记冲减金额
			}else {
				double price = bill.getCost();
				if(price <= 0){
					JSONUtil.responseJSON(response, new AjaxResult(0, "选中了错误的冲减金额!"));
					continue;
				}
				consumebill.setCost(-price);
				consumebill.setPay(0.00);
				otlog.setOffsetFee(bill.getCost());	//记冲减金额
			}
			bill.setStatus("2");
			
			try{
				roomService.save(consumebill);
				roomService.save(bill);
				roomService.save(otlog);//记冲减日志
				JSONUtil.responseJSON(response, new AjaxResult(1, "冲减金额成功!"));
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		
	}
	
	@RequestMapping("/consumptionworkbill.do")
	public void consumptionworkbill(HttpServletRequest request, HttpServletResponse response, String strdetailid, String offset){
		LoginUser loginuser = (LoginUser) request.getSession(true)
		.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		LoginGetShift loginGetShift = (LoginGetShift) request.getSession(true).getAttribute("loginGetShift");
		Staff staff = loginuser.getStaff();
		String[] arrdetail = strdetailid.split(",");
		for (String detailid : arrdetail) {
			WorkBillDetail consumedetail = new WorkBillDetail();
			WorkBillDetail workbilldetail = (WorkBillDetail) roomService.findOneByProperties(WorkBillDetail.class, "detailId", detailid);
			String detailId = DateUtil.currentDate("yyMMdd") + staff.getBranchId() + roomService.getSequence("SEQ_NEW_BILL");
			consumedetail.setDetailId(detailId);
			consumedetail.setBranchId(staff.getBranchId());
			consumedetail.setWorkbillId(workbilldetail.getWorkbillId());
			consumedetail.setDescribe(workbilldetail.getDescribe());
			consumedetail.setOffset(offset);
			consumedetail.setPayment(workbilldetail.getPayment());
			consumedetail.setProjectId(workbilldetail.getProjectId());
			consumedetail.setProjectName(workbilldetail.getProjectName());
			consumedetail.setRecordUser(staff.getStaffId());
			consumedetail.setRemark(workbilldetail.getDetailId());
			consumedetail.setStatus("2");
			consumedetail.setShift(loginGetShift.getShiftId());
			consumedetail.setCashBox(loginGetShift.getCashbox());
			
			String projectid = workbilldetail.getProjectId();
			if(projectid.startsWith("2")){
				double price = workbilldetail.getPay();
				if(price <= 0){
					JSONUtil.responseJSON(response, new AjaxResult(0, "选中了错误的冲减金额!"));
					continue;
				}
				consumedetail.setPay(-price);
				consumedetail.setCost(0.00);
			}else {
				double price = workbilldetail.getCost();
				if(price <= 0){
					JSONUtil.responseJSON(response, new AjaxResult(0, "选中了错误的冲减金额!"));
					continue;
				}
				consumedetail.setCost(-price);
				consumedetail.setPay(0.00);
			}
			workbilldetail.setStatus("2");
			
			try{
				roomService.save(consumedetail);
				roomService.save(workbilldetail);
				JSONUtil.responseJSON(response, new AjaxResult(1, "冲减金额成功!"));
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		
	}
	
	@RequestMapping("/committransferorder.do")
	public void committransferorder(HttpServletRequest request, HttpServletResponse response,
				String checkid, String orderid){
		LoginUser loginuser = (LoginUser) request.getSession(true)
		.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String branchid = staff.getBranchId();
		Check check = (Check) roomService.findOneByProperties(Check.class, "checkId", checkid);
		Order order = (Order) roomService.findOneByProperties(Order.class, "orderId", orderid);
		check.setBranchId(order.getBranchId());
		check.setRoomId(order.getRoomId());
		check.setRoomType(order.getRoomType());
		check.setRpId(order.getRpId());
		check.setRoomPrice(order.getRoomPrice());
		check.setCheckUser(order.getUserCheckin());
		check.setCheckoutTime(order.getCheckoutTime());
		check.setSwitchId(order.getOrderId());
		order.setStatus("3");
		
		SwitchOrder switchorder = new SwitchOrder();
		String logId = DateUtil.currentDate("yyMMdd") + branchid + roomService.getSequence("SEQ_NEW_SWITCHORDER");
		switchorder.setLogId(logId);
		switchorder.setBranchId(branchid);
		switchorder.setCheckId(checkid);
		switchorder.setOrderId(orderid);
		switchorder.setStatus("1");
		switchorder.setRecordUser(staff.getStaffId());
		try{
			roomService.save(order);
			roomService.save(check);
			roomService.save(switchorder);
			if(check.getRoomId() != order.getRoomId()){//可以执行换房操作
				roomService.upateroomstatus(branchid, check.getRoomId(), "0");
				roomService.upateroomstatus(branchid, order.getRoomId(), "2");
			}
			JSONUtil.responseJSON(response, new AjaxResult(1, "转单成功!"));
		}catch(Exception e){
			e.printStackTrace();
			JSONUtil.responseJSON(response, new AjaxResult(0, "转单失败!"));
		}

	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/getproject.do")
	public void getproject(HttpServletRequest request, HttpServletResponse response){
		String sqltype = "queryprojecttype";
		String sqlallproejct = "queryallproject";
		List<Map<String, Object>> projecttype = roomService.findBySQL(sqltype, true);
		Map<Object, Map<String, String>> allkingmap = new HashMap<Object, Map<String, String>>();
		for (Map<String, Object> map : projecttype) {
			Object orderno = map.get("ORDERNO");
			Object paramdesc = map.get("PARAMDESC");
			List<Map<String, String>> allkindproject = roomService.findBySQL(sqlallproejct, new String[]{orderno.toString()}, true);
			Map<String, String> resultallkind = new HashMap<String, String>();
			for (Map<String, String> map2 : allkindproject) {
				String content = map2.get("CONTENT");
				String paramname = map2.get("PARAMNAME");
				resultallkind.put(content, paramname);
				allkingmap.put(paramdesc, resultallkind);
			}
		}
		JSONUtil.responseJSON(response, allkingmap );
	}
	
	@RequestMapping("/setroommap.do")
	public void setroommap(HttpServletRequest request, HttpServletResponse response,String strcheckid, String hostcheckid){
		LoginUser loginuser = (LoginUser) request.getSession(true)
		.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String[] arrcheck = strcheckid.split(",");
		Check hostcheck = (Check) roomService.findOneByProperties(Check.class, "checkId", hostcheckid);
		String hostroomid = hostcheck.getRoomId();
		try{
			for (String checkid : arrcheck) {
				Check check = (Check) roomService.findOneByProperties(Check.class, "checkId", checkid);
				String roomid = check.getRoomId();
				roomService.insertroommapping(hostroomid, roomid);
			}			
			JSONUtil.responseJSON(response, new AjaxResult(1, "添加成功!") );
		}catch(Exception e){
			JSONUtil.responseJSON(response, new AjaxResult(0, "添加失败!") );
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/cancelroommapping.do")
	public void cancelroommapping(HttpServletRequest request, HttpServletResponse response,String checkid, String delroomid){
		LoginUser loginuser = (LoginUser) request.getSession(true)
		.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String branchId = staff.getBranchId();
		Check check = (Check) roomService.findOneByProperties(Check.class, "checkId", checkid);
		String roomid = check.getRoomId();
		String sql = "loadroommapping";
		
		String hostroomid = check.getRoomId();
		List<Map<String, Object>> roommappinglist = roomService.findallroommapping(hostroomid);
		if( roommappinglist.size() == 0){
			JSONUtil.responseJSON(response, new AjaxResult(0, "无关联房!") );
		}else{
			try{
				roomService.deleteroommappingbyroomid(hostroomid, delroomid);
				List<Map<String, String>> result = roomService.findBySQL(sql,new String[]{branchId, roomid}, true);
				JSONUtil.responseJSON(response, new AjaxResult(1, "添加成功!", result) );
			}catch(Exception e){
				JSONUtil.responseJSON(response, new AjaxResult(0, "添加失败!") );
			}
		}			
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/loadroommapping.do")
	public void loadroommapping(HttpServletRequest request, HttpServletResponse response,String checkid){
		LoginUser loginuser = (LoginUser) request.getSession(true)
		.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String branchId = staff.getBranchId();
		Check check = (Check) roomService.findOneByProperties(Check.class, "checkId", checkid);
		String roomid = check.getRoomId();
		String sql = "loadroommapping";
		List<Map<String, String>> result = roomService.findBySQL(sql,new String[]{branchId, roomid}, true);
		/*List<RoomMapping> listroommapping = roomService.findByProperties(RoomMapping.class, "roomId", roomid);
		Map<String, String> result = new HashMap<String, String>();
		
		for (RoomMapping roomMapping : listroommapping) {
			String relaRoomid = roomMapping.getRelaRoomid();
			Check relacheck = (Check) roomService.findOneByProperties(Check.class, "roomId", relaRoomid, "status", "1");
			if(relacheck != null){
				String checkuser = relacheck.getCheckUser();
				String firstuser = checkuser.split(",")[0];
				Member member = (Member) roomService.findOneByProperties(Member.class, "memberId", firstuser);
				String firstusername = member.getMemberName();
				result.put(relaRoomid, firstusername);
			}
		}*/
		JSONUtil.responseJSON(response, result);
	}
	
	@RequestMapping("/selectroommapping.do")
	public void selectroommapping(HttpServletRequest request, HttpServletResponse response,String hostcheckid,
				MultiQueryCheck multiQuerycheck){
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginuser.getStaff().getBranchId();
		Check check = (Check) roomService.findOneByProperties(Check.class, "checkId", hostcheckid);
		String hostroomid = check.getRoomId();
		//List<RoomMapping> roomid = roomService.findByProperties(RoomMapping.class, "roomId", hostroomid);
		List<Map<String, Object>> roomid = roomService.findallroommapping(hostroomid);
		String strroomid = hostroomid;
		for (Map<String, Object> map : roomid) {
			strroomid = strroomid + "," + map.get("RELAROOMID");
		}
		//String sql = "selectroommapping";
		List<?> result = roomService.selectroommapping(strroomid, branchId, multiQuerycheck);
		//List<?> result = roomService.findBySQL(sql, new String[]{multiQuerycheck.getStatus(), multiQuerycheck.getCheckid(), multiQuerycheck.getRoomid(), multiQuerycheck.getRoomtype(), multiQuerycheck.getOrderuser(), strcheckid}, true);
		JSONUtil.responseJSON(response, result);
	}
	
	@RequestMapping("/querycampaign.do")
	public void querycampaign(HttpServletRequest request, HttpServletResponse response) {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginuser.getStaff().getBranchId();
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd"); 
	    String newdate = sdf.format(date);
		List<?> campaignlist =  roomService.findBySQL("querycampaign",new String[]{branchId, newdate}, true);

		List<?> campaignnum =  roomService.findBySQL("querycampaigncount",new String[]{branchId, newdate}, true);
		JSONUtil.responseJSON(response, campaignlist);
		JSONUtil.responseJSON(response, campaignnum);
	}
	

	@SuppressWarnings("unchecked")
	@RequestMapping("/checkroommappingnull.do")
	public void checkroommappingnull(HttpServletRequest request, HttpServletResponse response,
				String checkid){
		LoginUser loginuser = (LoginUser) request.getSession(true)
		.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String branchId = staff.getBranchId();
		Check check = (Check) roomService.findOneByProperties(Check.class, "checkId", checkid);
		String roomid = check.getRoomId();
		String sql = "loadroommapping";
		List<? > listroommapping = roomService.findBySQL(sql,new String[]{branchId, roomid}, true);
		//List<RoomMapping> listroommapping = roomService.findByProperties(RoomMapping.class, "roomId", roomid);
		if(listroommapping.size() == 0){
			JSONUtil.responseJSON(response, new AjaxResult(0, "账户无关联房!"));
		}else {
			JSONUtil.responseJSON(response, new AjaxResult(1, "账户有关联房!"));
		}
	}
	@RequestMapping("/loadGuestData.do")
	public void loadGuestData(HttpServletRequest request, HttpServletResponse response,String memberid){
		Member member = (Member) roomService.findOneByProperties(Member.class, "memberId", memberid);
		MemberRank memberrank = (MemberRank) roomService.findOneByProperties(MemberRank.class, "memberRank", member.getMemberRank());
		String rankName = memberrank.getRankName();
		String birthday = "";
		if(member.getBirthday() != null){
			birthday = DateUtil.d2s(member.getBirthday(), "yyyy/MM/dd");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("member", member);
		map.put("rankName", rankName);
		map.put("birthday", birthday);
		JSONUtil.responseJSON(response, map);
	}
	
	@RequestMapping("/queryGuest.do")
	public void queryGuest(HttpServletRequest request, HttpServletResponse response,String memberid) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (memberid.isEmpty()) {
			JSONUtil.responseJSON(response, null);
		} else {
			if (memberid.indexOf(",") > 0) {
				String[] ids = memberid.split(",");
				for (String id : ids) {
					String birthday = "";
					Member member = (Member) roomService.findOneByProperties(Member.class, "memberId", id);
					MemberRank memberrank = (MemberRank) roomService.findOneByProperties(MemberRank.class, "memberRank", member.getMemberRank());
					map.put(id, member);
					map.put("rankName", memberrank.getRankName());
					map.put("birthday", birthday);
				}	
			} else {
				String birthday = "";
				Member member = (Member) roomService.findOneByProperties(Member.class, "memberId", memberid);
				MemberRank memberrank = (MemberRank) roomService.findOneByProperties(MemberRank.class, "memberRank", member.getMemberRank());
				map.put(memberid, member);
				map.put("rankName", memberrank.getRankName());
				map.put("birthday", birthday);
			}
		}
		JSONUtil.responseJSON(response, map);
	}
	
	@RequestMapping("/searchmember.do")
	public void searchmember(HttpServletRequest request, HttpServletResponse response,String datamember){
		String sql = "searchmember";
		List<?> result = roomService.findBySQL(sql, new String[]{datamember, datamember, datamember}, true);
		if(result.isEmpty()){
			JSONUtil.responseJSON(response, new AjaxResult(0, "无此会员，请注册!", ""));
		}else{
			if(result.size() > 1){
				JSONUtil.responseJSON(response, new AjaxResult(0, "查出多个会员!", result));
			}else {
				JSONUtil.responseJSON(response, new AjaxResult(1, "查询成功!", result));				
			}
		}
	}
	
	@RequestMapping("/updateCustomer.do")
	public void updateCustomer(HttpServletRequest request, HttpServletResponse response,String checkid, String strguest){
		Check check = (Check) roomService.findOneByProperties(Check.class, "checkId", checkid);
		String checkuser = check.getCheckUser();
		int checknum = checkuser.split(",").length;
		int strguesthnum = strguest.split(",").length;
		LoginUser loginuser = (LoginUser)request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String branchid = staff.getBranchId();
		if(!strguest.equals(checkuser)){
			try {
				CheckinUserLog addlog = new CheckinUserLog();
				String logId = DateUtil.currentDate("yyMMdd")+branchid+roomService.getSequence("SEQ_CHECKINUSERLOG_ID");
				addlog.setLogId(logId);
				addlog.setBranchId(branchid);
				addlog.setCheckId(checkid);
				addlog.setLastCheckUser(checkuser);
				addlog.setCurrCheckUser(strguest);
				addlog.setRecordUser(staff.getStaffId());
				addlog.setRecordTime(new Date());
				roomService.save(addlog);
				if(checknum < strguesthnum){
						roomService.updatecheckcheckuser(strguest, checkid);
						JSONUtil.responseJSON(response, new AjaxResult(1, "入住成功!"));							
				}else{
						roomService.updatecheckcheckuser(strguest, checkid);
						JSONUtil.responseJSON(response, new AjaxResult(1, "设置成功!"));	
				}
			} catch (DAOException e) {
				e.printStackTrace();
			}
		}else {
			JSONUtil.responseJSON(response, new AjaxResult(0, "客人已入住!"));	
		}
	}
	
	@RequestMapping("/loadsearchroomtype.do")
	public void loadsearchroomtype(HttpServletRequest request, HttpServletResponse response,String checkid, String strguest){
		try{
			List<?> roomtype = LeftmenuService.getRoomtype();
			JSONUtil.responseJSON(response, roomtype);	
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/loadRoomFloor.do")
	public void loadRoomFloor(HttpServletRequest request, HttpServletResponse response,String checkid, String strguest){
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginuser.getStaff().getBranchId();
		String sql = "querycountfloor";
		try{
			List<?> roomtype = roomService.findBySQL(sql, new String[]{branchId}, true);
			JSONUtil.responseJSON(response, roomtype);	
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping("/addmember.do")
	public void addmember(HttpServletRequest request, HttpServletResponse response, Member member){
		Member existmember = (Member) roomService.findOneByProperties(Member.class, "mobile", member.getMobile());
		if(existmember != null){
			JSONUtil.responseJSON(response, new AjaxResult(0, "手机号已注册!", null));
			return;
		}
		String memberId = roomService.getSequence("SEQ_MEMBER_ID");
		String memberName = member.getMemberName();
		String loginName = ChineseCharacterUtil.convertHanzi2Pinyin(memberName);
		Calendar calendar = Calendar.getInstance();
		member.setMemberId(memberId);
		member.setLoginName(loginName);
		member.setPassword(MD5Util.getCryptogram("888888"));
		member.setMemberRank("1");
		member.setSource("1");
		member.setValidTime(calendar.getTime());
		member.setRecordTime(calendar.getTime());
		member.setRegisterTime(calendar.getTime());
		calendar.add(Calendar.YEAR, 1);
		member.setInvalidTime(calendar.getTime());
		member.setStatus("1");
		
		MemberAccount memberaccount = new MemberAccount();
		String accountId = roomService.getSequence("SEQ_ACCOUNT_ID");
		memberaccount.setAccountId(accountId);
		memberaccount.setMemberId(memberId);
		memberaccount.setCurrBalance(0.00);
		memberaccount.setCurrIntegration((long)0);
		memberaccount.setTotalRecharge(0.00);
		memberaccount.setDiscountGift(0.00);
		memberaccount.setChargeGift(0.00);
		memberaccount.setTotalConsume(0.00);
		memberaccount.setTotalIntegration((long)0);
		memberaccount.setIngegrationGift((long)0);
		memberaccount.setTotalConsIntegration((long)0);
		memberaccount.setTotalRoomnights(0);
		memberaccount.setCurrRoomnights(0);
		memberaccount.setTotalNoshow((short)0);
		memberaccount.setCurrNoshow((short)0);
		memberaccount.setRecordTime(new Date());
		memberaccount.setStatus("1");
		memberaccount.setThisYearIntegration((long)0);
		try{
			roomService.save(member);
			roomService.save(memberaccount);
			JSONUtil.responseJSON(response, new AjaxResult(1, "注册成功!", memberId));	
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/updatemember.do")
	public void updatemember(HttpServletRequest request, HttpServletResponse response, Member member){
		member.setStatus("1");
		String memberId = member.getMemberId();
		String memberName = member.getMemberName();
		String loginName = ChineseCharacterUtil.convertHanzi2Pinyin(memberName);
		String idcard = member.getIdcard();
		String gendor = member.getGendor();
		String mobile = member.getMobile();
		String eamil = member.getEmail();
		String address = member.getAddress();
		try{
			roomService.updateMember(memberId, memberName, loginName, idcard, gendor, mobile, eamil, address);
			JSONUtil.responseJSON(response, new AjaxResult(1, "更改成功!"));	
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/isCheckout.do")
	public void isCheckout(HttpServletRequest request, HttpServletResponse response, String checkid){
		Check check = (Check) roomService.findOneByProperties(Check.class, "checkId", checkid);
		try{
			if( check == null){
				JSONUtil.responseJSON(response, new AjaxResult(0, "未入住!"));
			}else {
				if("2".equalsIgnoreCase(check.getStatus())){
					JSONUtil.responseJSON(response, new AjaxResult(1, "当前已结算!"));
				}else {
					JSONUtil.responseJSON(response, new AjaxResult(0, "未结算!"));
				}				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/resetprice.do")
	public void resetprice (HttpServletRequest request, HttpServletResponse response, String checkid, String amount){
		LoginUser loginuser = (LoginUser) request.getSession(true)
		.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String branchId = staff.getBranchId();
		String departId = staff.getDepartId();
		if("1001".equals(departId)){
			Check check = (Check) roomService.findOneByProperties(Check.class, "checkId", checkid);
			check.setRoomPrice(check.getRoomPrice() + Double.valueOf(amount));
			
			RoomPlan roomplan = new RoomPlan();
			String logId = DateUtil.currentDate("yyMMdd")+staff.getBranchId()+roomService.getSequence("SEQ_NEW_ROOMPLAN");
			roomplan.setLogId(logId);
			roomplan.setBranchId(branchId);
			roomplan.setValidDay(new Date());
			roomplan.setRoomId(check.getRoomId());
			roomplan.setRpId(check.getRpId());
			roomplan.setRoomPrice(check.getRoomPrice());
			roomplan.setCashback(Double.valueOf(amount));
			roomplan.setStatus("1");
			roomplan.setRecordUser(staff.getStaffId());
			try{
				roomService.save(roomplan);
				roomService.save(check);
				JSONUtil.responseJSON(response, new AjaxResult(1, "调整成功!"));				
			}catch(Exception e){
				e.printStackTrace();
			}
		}else {
			JSONUtil.responseJSON(response, new AjaxResult(0, "抱歉,权限不够!"));
		}
		
	}
	
	@RequestMapping("/autosaveRemark.do")
	public void autosaveRemark (HttpServletRequest request, HttpServletResponse response, String checkid, String remark){
//		LoginUser loginuser = (LoginUser) request.getSession(true)
//		.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
//		Staff staff = loginuser.getStaff();
		Check check = (Check) roomService.findOneByProperties(Check.class, "checkId", checkid);
		check.setRemark(remark);
		try{
			roomService.save(check);
		}catch (Exception e){
			e.printStackTrace();
			JSONUtil.responseJSON(response, new AjaxResult(0, "备注丢失!"));
		}
	}
	
	@RequestMapping("/loadsinglehaltplan.do")
	public void loadsinglehaltplan (HttpServletRequest request, HttpServletResponse response, String logid){
//		LoginUser loginuser = (LoginUser) request.getSession(true)
//		.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
//		Staff staff = loginuser.getStaff();
		HaltPlan haltplan = (HaltPlan) roomService.findOneByProperties(HaltPlan.class, "logId", logid);
		
		
		try{
			JSONUtil.responseJSON(response, new AjaxResult(1, "加载成功!", haltplan));
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/resetvip.do")
	public void resetvip (HttpServletRequest request, HttpServletResponse response, String checkid, String orderuser){
		LoginUser loginuser = (LoginUser) request.getSession(true)
		.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String branchId = staff.getBranchId();
		Member member = (Member) roomService.findOneByProperties(Member.class, "memberId", orderuser);
		Check check = (Check) roomService.findOneByProperties(Check.class, "checkId", checkid);
		Order order = (Order) roomService.findOneByProperties(Order.class, "orderId", checkid);
		if( !StringUtil.isEmpty(order.getActivity())){
			JSONUtil.responseJSON(response, new AjaxResult(0,"活动价不能重置!"));
		}else {
			String roomType = check.getRoomType();
			String memberRank = member.getMemberRank();
			RoomPriceId roompriceid = new RoomPriceId();
			roompriceid.setBranchId(branchId);
			roompriceid.setRoomType(roomType);
			roompriceid.setStatus("1");
			RoomPrice roomprice = (RoomPrice) roomService.findOneByProperties(RoomPrice.class, "memberRank", memberRank, "roomPriceId.branchId", branchId , "roomPriceId.roomType", roomType, "roomPriceId.status", "1" , "roomPriceId.rpKind", "1");
			double price = roomprice.getRoomPrice();
			if(price == check.getRoomPrice()){
				JSONUtil.responseJSON(response, new AjaxResult(0,"无需重置!"));
			}else{
				double subprice = price - check.getRoomPrice();
				check.setRoomPrice(price);
				
				RoomPlan roomplan = new RoomPlan();
				String logId = DateUtil.currentDate("yyMMdd")+staff.getBranchId()+roomService.getSequence("SEQ_NEW_ROOMPLAN");
				roomplan.setLogId(logId);
				roomplan.setBranchId(branchId);
				roomplan.setValidDay(new Date());
				roomplan.setRoomId(check.getRoomId());
				roomplan.setRpId(check.getRpId());
				roomplan.setRoomPrice(check.getRoomPrice());
				roomplan.setCashback(subprice);
				roomplan.setStatus("1");
				roomplan.setRecordUser(staff.getStaffId());
				try{
					roomService.save(roomplan);
					roomService.save(check);
					JSONUtil.responseJSON(response, new AjaxResult(1,"重置成功!"));
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}
	
	
	
	
	//双击显示提示信息详情页面
	@SuppressWarnings("unchecked")
	@RequestMapping("/showPromptRecordDetail.do")
	public ModelAndView showPromptRecordDetail(HttpServletRequest request,String logid,String checkid) throws Exception{
		ModelAndView mv = new ModelAndView();
		Pagination pagination = SqlBuilder.buildPagination(request);
		Tips tips = (Tips) roomService.findOneByProperties(Tips.class, "logId", logid);
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginUser.getStaff();
		tips.setReader(staff.getStaffId());
		tips.setReadTime(new Date());
		tips.setStatus("2");
		roomService.update(tips);
		String sql = "loadpromptdetail";
		List<Tips> result = roomService.findBySQLWithPagination(sql, new String[]{checkid,logid},pagination,true);
		mv.addObject("result",result.get(0) );
		mv.addObject("pagination",pagination);
		mv.setViewName("page/ipmspage/order/prompt_details");
		return mv;
	}
	

	//统计提示信息日志未阅读数量
	@SuppressWarnings("unchecked")
	@RequestMapping("/countPrompt.do")
	public void countPrompt(HttpServletRequest request,HttpServletResponse response,String checkid){
		Order orderdata = (Order) roomService.findOneByProperties(Order.class, "orderId", checkid);
		String sql = "";
		if (orderdata.getStatus().equals("1")) {
			sql = "countordertips";
		} else {
			sql ="countPrompt";
		}
		List<?> list = roomService.findBySQL(sql, new String[]{checkid}, true);
		//System.out.println(((Map) list.get(0)).get("NUM"));
		JSONUtil.responseJSON(response,list);
	}
	
	//提示信息新增取消操作
	@RequestMapping("/cancelPromptRecord.do")
	public void cancelPromptRecord(HttpServletRequest request,HttpServletResponse response,String logid){
		Tips tip = (Tips) roomService.findOneByProperties(Tips.class, "logId", logid);
		tip.setStatus("0");
		try {
			roomService.update(tip);
			JSONUtil.responseJSON(response, new AjaxResult(1,"取消成功"));
		} catch (DAOException e) {
			e.printStackTrace();
			JSONUtil.responseJSON(response, new AjaxResult(2,"取消失败"));
		}
		
	}

//	@RequestMapping("/turnToCheck.do")
//	public void turnToCheck (HttpServletRequest request, HttpServletResponse response){
//		
//	}

}
