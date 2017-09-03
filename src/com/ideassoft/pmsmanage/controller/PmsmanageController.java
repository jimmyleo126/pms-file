package com.ideassoft.pmsmanage.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.ideassoft.bean.Branch;
import com.ideassoft.bean.OperateLog;
import com.ideassoft.bean.Room;
import com.ideassoft.bean.RoomKey;
import com.ideassoft.bean.RoomPrice;
import com.ideassoft.bean.RoomPriceId;
import com.ideassoft.bean.RoomStatus;
import com.ideassoft.bean.RoomType;
import com.ideassoft.bean.Staff;
import com.ideassoft.bean.SysParam;
import com.ideassoft.core.ajax.AjaxResult;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.pmsmanage.service.PmsmanageService;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.RequestUtil;

@Transactional
@Controller
public class PmsmanageController {
	@Autowired
	private PmsmanageService PmsmanageService;

	// 自定义房间管理删除
	@RequestMapping("/delRoom.do")
	public void delRoom(HttpServletRequest request, HttpServletResponse response, String theme, String branchid,
			String roomid, String roomtype, String roomstatus) throws UnknownHostException {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginUser.getStaff();
		String recordUser = staff.getStaffId();
		/*
		 * Date day = new Date(); SimpleDateFormat df = new
		 * SimpleDateFormat("yyyy/MM/dd");
		 */
		/* String recordTime = df.format(day); */
		String[] roomids = roomid.split(",");
		String[] branchids = branchid.split(",");
		String[] roomtypes = roomtype.split(",");
		String[] roomstatuss = roomstatus.split(",");
		String[] themeids = theme.split(",");
		/* Room room; */
		for (int i = 0; i < roomids.length; i++) {
			Branch branchId = (Branch) PmsmanageService.findOneByProperties(Branch.class, "branchName", branchids[i]);
			String branchiD = branchId.getBranchId();
			String rooomiD = roomids[i];
			SysParam sysParamstatus = ((SysParam) (PmsmanageService.findOneByProperties(SysParam.class, "paramType",
					"ROOMSTATUS", "paramName", roomstatuss[i])));
			String statusdelete = sysParamstatus.getContent().toString();
			if (roomids[i] != null) {
				RoomKey roomKeyid = new RoomKey();
				roomKeyid.setRoomId(roomids[i]);
				roomKeyid.setBranchId(branchiD);
				PmsmanageService.upateroomstatus(branchiD, rooomiD,statusdelete, recordUser);
			}
			SysParam sysParam = ((SysParam) (PmsmanageService.findOneByProperties(SysParam.class, "paramType", "THEME",
					"paramName", themeids[i])));
			String themeid = sysParam.getContent().toString();
			RoomType roomType = ((RoomType) (PmsmanageService.findOneByProperties(RoomType.class, "roomName",
					roomtypes[i], "theme", themeid)));
			String roomtypeid = roomType.getRoomType().toString();
			if (statusdelete.equals("T")) {
				RoomStatus roomStatus = ((RoomStatus) (PmsmanageService.findOneByProperties(RoomStatus.class,
						"branchId", branchiD, "roomType", roomtypeid)));
				String oldcount = roomStatus.getCount().toString();
				String oldstopnum = roomStatus.getStopnum().toString();
				int x = Integer.parseInt(oldcount) - 1;
				int v = Integer.parseInt(oldstopnum) - 1;
				Integer newcount = Integer.valueOf(x);
				Integer newstopnum = Integer.valueOf(v);
				roomStatus.setCount(newcount);
				roomStatus.setStopnum(newstopnum);
				roomStatus.setRecordTime(new Date());
				roomStatus.setRecordUser(staff.getStaffId());
				PmsmanageService.save(roomStatus);

			} else if (statusdelete.equals("F")) {

			} else {

				RoomStatus roomStatus = ((RoomStatus) (PmsmanageService.findOneByProperties(RoomStatus.class,
						"branchId", branchiD, "roomType", roomtypeid)));
				String oldcount = roomStatus.getCount().toString();
				String oldsellnum = roomStatus.getSellnum().toString();
				int x = Integer.parseInt(oldcount) - 1;
				int v = Integer.parseInt(oldsellnum) - 1;
				Integer newcount = Integer.valueOf(x);
				Integer newsellnum = Integer.valueOf(v);
				roomStatus.setCount(newcount);
				roomStatus.setSellnum(newsellnum);
				roomStatus.setRecordTime(new Date());
				roomStatus.setRecordUser(staff.getStaffId());
				PmsmanageService.save(roomStatus);
			}
		}
		JSONUtil.responseJSON(response, new AjaxResult(1, null));
	}
	/*
	// 房租价申请before
	@RequestMapping("/rpJudge.do")
	public void rpJudge(HttpServletRequest request, HttpServletResponse response, String Mnumber) throws Exception {
		List<?> rpsetup = PmsmanageService.getRpsetup();
		List<?> rpbranchid = PmsmanageService.getRpbranchid();
		List<?> rproomtype = PmsmanageService.getRproomtype();
		String discount = ((Map<?, ?>) rpsetup.get(0)).get("DISCOUNT")==null ?"":((Map<?, ?>) rpsetup.get(0)).get("DISCOUNT").toString();
		String a = null;
		if (null == discount || discount.equals("")) {
			a = "请先去配置会员等级等相关数据！";
		}else if(null == rpbranchid || rpbranchid.size() == 0) {
			a = "请先去配置门店等相关数据！";
		}else if(null == rproomtype || rproomtype.size() == 0) {
			a = "请先去配置房型等相关数据！";
		}
		JSONUtil.responseJSON(response, new AjaxResult(1, a));
	}*/
	
	/*// 房租价申请
	@RequestMapping("/rpBasicApplye.do")
	public void rpJudge(HttpServletRequest request, HttpServletResponse response, String themename,String branchname,String rpkindname,String roomtypename,String status) throws Exception {
		List<?> rpbranchid = PmsmanageService.getRpbranchid();
		List<?> rptheme = PmsmanageService.getRptheme();
		List<?> rproomtype = PmsmanageService.getRproomtype();
		List<?> rppack = PmsmanageService.getRppack();
		List<?> rpsetup = PmsmanageService.getRpsetup();
		mv.setViewName("page/pmsmanage/roomprice/rpapply");
		request.setAttribute("rpbranchid", rpbranchid);
		request.setAttribute("rptheme", rptheme);
		request.setAttribute("rproomtype", rproomtype);
		request.setAttribute("rppack", rppack);
		request.setAttribute("rpsetup", rpsetup);
		JSONUtil.responseJSON(response, new AjaxResult(1, a));
	}*/

	// 房租价申请judgeRp.do
	@RequestMapping("/rpBasicApply.do")
	public ModelAndView rpBasicApply(HttpServletRequest request, HttpServletResponse response, String themename,String branchname,String rpkindname,String roomtypename,String status) throws Exception {
		ModelAndView mv = new ModelAndView();
		List<?> rptheme = PmsmanageService.getRptheme();
		List<?> rpbranchid = PmsmanageService.getRpbranchid();
		List<?> rpkind = PmsmanageService.getRpkind();
		List<?> rppack = PmsmanageService.getRppack();
		List<?> rpstatus = PmsmanageService.getRpapplystatus();
		/*List<?> rpsetup = PmsmanageService.getRpsetup();*/
		SysParam sysParam = ((SysParam) (PmsmanageService.findOneByProperties(SysParam.class, "paramType", "THEME",
				"paramName", themename)));
		String themeid = sysParam.getContent().toString();
		Branch branchId = (Branch) PmsmanageService.findOneByProperties(Branch.class, "branchName",branchname);
		String branchid = branchId.getBranchId().toString();
		SysParam sysParamkind = ((SysParam) (PmsmanageService.findOneByProperties(SysParam.class, "paramType", "RPKIND",
				"paramName", rpkindname)));
		String rpking = sysParamkind.getContent().toString();
		SysParam sysParamstatus = ((SysParam) (PmsmanageService.findOneByProperties(SysParam.class, "paramType",
				"RPSTATUS", "paramName", status)));
		String statusid = sysParamstatus.getContent().toString();
		String rpthemeid = themeid;
		List<?> rproomtype = PmsmanageService.getRpapplyroomtype(themeid,rpthemeid,branchid,rpking,statusid);
		mv.setViewName("page/pmsmanage/roomprice/rpapply");
		request.setAttribute("rptheme", rptheme);
		request.setAttribute("rpbranchid", rpbranchid);
		request.setAttribute("rpkind", rpkind);
		request.setAttribute("rproomtype", rproomtype);
		request.setAttribute("rppack", rppack);
		request.setAttribute("rpstatus", rpstatus);
		request.setAttribute("themeid", themeid);
		request.setAttribute("branchid", branchid);
		request.setAttribute("rpking", rpking);
		request.setAttribute("statusid", statusid);
		/*request.setAttribute("rpsetup", rpsetup);*/
		return mv;
	}

	// 房租价申请记录操作日志
	@RequestMapping("/rpOperate.do")
	public void rpOperate(HttpServletRequest request, HttpServletResponse response, String operateid) throws Exception {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = operateid.substring(0, 6);
		String theme = operateid.substring(6, 7);
		String roomtype = operateid.substring(7);
		List<?> rpdata = PmsmanageService.getRpdata(branchid, theme, roomtype);
		String a = null;
		if (null == rpdata || rpdata.size() == 0) {
			try {
				String sequences = this.PmsmanageService.getSequence("SEQ_OPERATELOG_ID", null);
				SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
				String strdate = sdf.format(new Date());
				String operid = InetAddress.getLocalHost().toString();// IP地址
				operid = (String) operid.subSequence(operid.indexOf("/") + 1, operid.length());
				String logid = strdate + loginUser.getStaff().getBranchId() + sequences;
				String content = "门店[" + branchid + "]场景[" + theme + "]房型[" + roomtype + "]";
				OperateLog operatelog = new OperateLog();
				operatelog.setLogId(logid);
				operatelog.setOperType("1");
				operatelog.setOperModule("房租申请");
				operatelog.setContent(content);
				operatelog.setRecordUser(loginUser.getStaff().getStaffId());
				operatelog.setRecordTime(new Date());
				operatelog.setOperIp(operid);
				operatelog.setRemark("1");
				operatelog.setBranchId(loginUser.getStaff().getBranchId());
				PmsmanageService.save(operatelog);
			} catch (RuntimeException e) {
				e.printStackTrace();
			}

		} else {
			a = "该条房价数据已经存在了，无需重复申请！";

		}
		JSONUtil.responseJSON(response, new AjaxResult(1, a));
	}

	// 房租价申请保存申请数据
	@RequestMapping("/auditRp.do")
	public void auditRp(HttpServletRequest request, HttpServletResponse response, String rbbranchid, String rpid,
			String rpname, String rproomtype, String roomprice, String packid, String discount, String memberrank,
			String rptheme) throws Exception {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		Double romprice = Double.parseDouble(roomprice);
		Double d = new Double(discount) / 100;
		Format f = new DecimalFormat("0.00");
		Double dscount = Double.parseDouble(f.format(d));
		RoomPrice rprice = new RoomPrice();
		RoomPriceId roompriceid = new RoomPriceId();
		roompriceid.setBranchId(rbbranchid);
		roompriceid.setRoomType(rproomtype);
		roompriceid.setRpId(rpid);
		roompriceid.setStatus("3");
		rprice.setRoomPriceId(roompriceid);
		rprice.setRpName(rpname);
		rprice.setRoomPrice(romprice);
		rprice.setPackId(packid);
		rprice.setRpType("1");
		rprice.setDiscount(dscount);
	/*	rprice.setStatus("3");*/
		rprice.setRecordTime(new Date());
		rprice.setRecordUser(staff.getStaffId());
		rprice.setMemberRank(memberrank);
		rprice.setTheme(rptheme);
		PmsmanageService.save(rprice);
		JSONUtil.responseJSON(response, new AjaxResult(1, null));
	}

	// 房租价申请审核信息页面
	@RequestMapping("/rpapplyInfoDetail")
	public ModelAndView rpauditInfoDetail(HttpServletRequest request, HttpServletResponse response, String operid,
			String pagetype, String audittype) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/page/pmsmanage/audit/rpapplyInfoDetail");
		OperateLog operateLog = ((OperateLog) (PmsmanageService.findOneByProperties(OperateLog.class, "logId", operid)));
		String content = operateLog.getContent().toString();
		String staff = operateLog.getRecordUser().toString();
		String applytime = operateLog.getRecordTime().toString();
		String applybranch = operateLog.getBranchId().toString();
		String branchid = content.substring(3, 9);
		String theme = content.substring(13, 14);
		String roomType = content.substring(18, 21);
		String roomype = roomType.replace("]", "");
		Staff staffName = ((Staff) (PmsmanageService.findOneByProperties(Staff.class, "staffId", staff)));
		String staffname = staffName.getStaffName();
		Branch branchName = ((Branch) (PmsmanageService.findOneByProperties(Branch.class, "branchId", applybranch)));
		String branchname = branchName.getBranchName().toString();
		Branch branchNameid = ((Branch) (PmsmanageService.findOneByProperties(Branch.class, "branchId", branchid)));
		String branchnameid = branchNameid.getBranchName().toString();
		SysParam sysParam = ((SysParam) (PmsmanageService.findOneByProperties(SysParam.class, "content", theme,
				"paramType", "THEME")));
		String themename = sysParam.getParamName().toString();
		RoomType roomTypename = ((RoomType) (PmsmanageService.findOneByProperties(RoomType.class, "roomType", roomype)));
		String roontypename = roomTypename.getRoomName().toString();
		List<?> rpauditdata = PmsmanageService.getRpdata(branchid, theme, roomype);
		request.setAttribute("pagetype", pagetype);
		request.setAttribute("audittype", audittype);
		request.setAttribute("operid", operid);
		request.setAttribute("staff", staff);
		request.setAttribute("staffname", staffname);
		request.setAttribute("applytime", applytime);
		request.setAttribute("applybranch", applybranch);
		request.setAttribute("branchname", branchname);
		request.setAttribute("branchid", branchid);
		request.setAttribute("branchnameid", branchnameid);
		request.setAttribute("theme", theme);
		request.setAttribute("themename", themename);
		request.setAttribute("roomype", roomype);
		request.setAttribute("roontypename", roontypename);
		request.setAttribute("rpauditdata", rpauditdata);
		return mv;
	}

	// 班次页面跳转
	@RequestMapping("/turnToShiftTime.do")
	public ModelAndView turnToShiftTime(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/page/pmsmanage/shift/shifttime");
		List<?> shiftcontent = PmsmanageService.getShiftcontent();
		request.setAttribute("shiftcontent", shiftcontent);
		return mv;
	}

	// 自定义房间管理新增页面跳转
	@RequestMapping("/roomAdd.do")
	public ModelAndView roomAdd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/page/pmsmanage/room/roomadd");
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginuser.getStaff().getBranchId();
		Branch branchid = (Branch) PmsmanageService.findOneByProperties(Branch.class, "branchId", branchId);
		String theme = branchid.getBranchType();
		List<?> rpbranchid = PmsmanageService.getRpbranchid();
		List<?> rptheme = PmsmanageService.getRptheme();
		List<?> rproomtype = PmsmanageService.getRproomtype();
		List<?> rpstatus = PmsmanageService.getStatus();
		request.setAttribute("rpbranchid", rpbranchid);
		request.setAttribute("rptheme", rptheme);
		request.setAttribute("rproomtype", rproomtype);
		request.setAttribute("rpstatus", rpstatus);
		request.setAttribute("branchId", branchId);
		request.setAttribute("theme", theme);
		return mv;
	}

	// 自定义房间管理新增
	@RequestMapping("/adddataRoom.do")
	public void adddataRoom(HttpServletRequest request, HttpServletResponse response, String theme, String branchid,
			String rproomtype, String floor, String roomid, String area, String status, String remark) throws Exception {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String branchId = loginuser.getStaff().getBranchId();
		List<?> uniqueroom = PmsmanageService.getUniqueroom(branchid, roomid);
		String a = null;
		if (null == uniqueroom || uniqueroom.size() == 0) {
			RoomKey roomKey = new RoomKey();
			roomKey.setBranchId(branchid);
			roomKey.setRoomId(roomid);
			roomKey.setStatus(status);
			Short areadata = null;
			if (area.equals("")) {
			} else {
				areadata = new Short(area);
			}
			Room room = new Room();
			room.setArea(areadata);
			room.setFloor(floor);
			room.setRecordTime(new Date());
			room.setRecordUser(staff.getStaffId());
			room.setRemark(remark);
			room.setRoomKey(roomKey);
			room.setRoomType(rproomtype);
			room.setTheme(theme);
			PmsmanageService.save(room);
			if (status.equals("T")) {
				List<?> exitroomstatus = PmsmanageService.getExitroomstatus(branchid, rproomtype);

				if (null == exitroomstatus || exitroomstatus.size() == 0) {
					String logId = this.PmsmanageService.getSequence("SEQ_NEW_ROOMSTATUS", null);
					Date day = new Date();
					SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
					String t = df.format(day);
					String datId = t + branchId + logId;
					RoomStatus roomstatus = new RoomStatus();
					roomstatus.setLogId(datId);
					roomstatus.setBranchId(branchid);
					roomstatus.setRoomType(rproomtype);
					roomstatus.setCount(1);
					roomstatus.setRecordTime(new Date());
					roomstatus.setRecordUser(staff.getStaffId());
					roomstatus.setSellnum(0);
					roomstatus.setStopnum(1);
					roomstatus.setNightnum(0);
					roomstatus.setValidnum(0);
					roomstatus.setInvalidnum(0);
					roomstatus.setCampaigns(0);
					PmsmanageService.save(roomstatus);
				} else {
					RoomStatus roomStatus = ((RoomStatus) (PmsmanageService.findOneByProperties(RoomStatus.class,
							"branchId", branchid, "roomType", rproomtype)));
					String oldcount = roomStatus.getCount().toString();
					String oldstopnum = roomStatus.getStopnum().toString();
					int x = Integer.parseInt(oldcount) + 1;
					int v = Integer.parseInt(oldstopnum) + 1;
					Integer newcount = Integer.valueOf(x);
					Integer newstopnum = Integer.valueOf(v);
					roomStatus.setCount(newcount);
					roomStatus.setStopnum(newstopnum);
					roomStatus.setRecordTime(new Date());
					roomStatus.setRecordUser(staff.getStaffId());
					PmsmanageService.save(roomStatus);
				}

			} else if (status.equals("F")) {

			} else {
				List<?> exitroomstatus = PmsmanageService.getExitroomstatus(branchid, rproomtype);
				if (null == exitroomstatus || exitroomstatus.size() == 0) {
					String logId = this.PmsmanageService.getSequence("SEQ_NEW_ROOMSTATUS", null);
					Date day = new Date();
					SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
					String t = df.format(day);
					String datId = t + branchId + logId;
					RoomStatus roomstatus = new RoomStatus();
					roomstatus.setLogId(datId);
					roomstatus.setBranchId(branchid);
					roomstatus.setRoomType(rproomtype);
					roomstatus.setCount(1);
					roomstatus.setRecordTime(new Date());
					roomstatus.setRecordUser(staff.getStaffId());
					roomstatus.setSellnum(1);
					roomstatus.setStopnum(0);
					roomstatus.setNightnum(0);
					roomstatus.setValidnum(0);
					roomstatus.setInvalidnum(0);
					roomstatus.setCampaigns(0);
					PmsmanageService.save(roomstatus);
				} else {
					RoomStatus roomStatus = ((RoomStatus) (PmsmanageService.findOneByProperties(RoomStatus.class,
							"branchId", branchid, "roomType", rproomtype)));
					String oldcount = roomStatus.getCount().toString();
					String oldsellnum = roomStatus.getSellnum().toString();
					int x = Integer.parseInt(oldcount) + 1;
					int v = Integer.parseInt(oldsellnum) + 1;
					Integer newcount = Integer.valueOf(x);
					Integer newsellnum = Integer.valueOf(v);
					roomStatus.setCount(newcount);
					roomStatus.setSellnum(newsellnum);
					roomStatus.setRecordTime(new Date());
					roomStatus.setRecordUser(staff.getStaffId());
					PmsmanageService.save(roomStatus);
				}
			}
		} else {

			a = "该门店该房号的数据已存在，请重新添加";
		}

		JSONUtil.responseJSON(response, new AjaxResult(1, a));
	}

	// 自定义房间管理编辑页面跳转
	@RequestMapping("/roomEdit.do")
	public ModelAndView roomEdit(HttpServletRequest request, HttpServletResponse response, String theme, String branch,
			String roomtype, String floor, String roomid, String area, String status, String remark) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/page/pmsmanage/room/roomedit");
		SysParam sysParam = ((SysParam) (PmsmanageService.findOneByProperties(SysParam.class, "paramType", "THEME",
				"paramName", theme)));
		Branch branchName = ((Branch) (PmsmanageService.findOneByProperties(Branch.class, "branchName", branch)));
		String themeid = sysParam.getContent().toString();
		String branchid = branchName.getBranchId().toString();
		RoomType roomType = ((RoomType) (PmsmanageService.findOneByProperties(RoomType.class, "roomName", roomtype,
				"theme", themeid)));
		SysParam sysParamstatus = ((SysParam) (PmsmanageService.findOneByProperties(SysParam.class, "paramType",
				"ROOMSTATUS", "paramName", status)));
		String statusedit = sysParamstatus.getContent().toString();
		String roomtypeid = roomType.getRoomType().toString();
		List<?> rpbranchid = PmsmanageService.getRpbranchid();
		List<?> rptheme = PmsmanageService.getRptheme();
		List<?> rproomtype = PmsmanageService.getRproomtype();
		List<?> rpstatus = PmsmanageService.getStatus();
		request.setAttribute("rpbranchid", rpbranchid);
		request.setAttribute("rptheme", rptheme);
		request.setAttribute("rproomtype", rproomtype);
		request.setAttribute("statusedit", statusedit);
		request.setAttribute("rpstatus", rpstatus);
		request.setAttribute("themeid", themeid);
		request.setAttribute("branchid", branchid);
		request.setAttribute("roomtypeid", roomtypeid);
		request.setAttribute("floor", floor);
		request.setAttribute("roomid", roomid);
		request.setAttribute("area", area);
		request.setAttribute("roomid", roomid);
		request.setAttribute("remark", remark);
		return mv;
	}

	// 自定义房间管理编辑
	@RequestMapping("/editdataRoom.do")
	public void editdataRoom(HttpServletRequest request, HttpServletResponse response, String theme, String branchid,
			String rproomtype, String floor, String roomid, String area, String status, String oldstatus, String remark)
			throws Exception {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String editUser = staff.getStaffId();
		RoomKey roomKey = new RoomKey();
		roomKey.setBranchId(branchid);
		roomKey.setRoomId(roomid);
		Short areadata = null;
		if (area.equals("")) {
		} else {
			areadata = new Short(area);
		}
		PmsmanageService.upateroomedit(theme, branchid, rproomtype, floor, roomid, areadata, status, remark, editUser);
		if (oldstatus.equals("T")) {
			RoomStatus roomStatus = ((RoomStatus) (PmsmanageService.findOneByProperties(RoomStatus.class, "branchId",
					branchid, "roomType", rproomtype)));
			String oldstopnum = roomStatus.getStopnum().toString();
			int v = Integer.parseInt(oldstopnum) - 1;
			Integer newstopnum = Integer.valueOf(v);
			roomStatus.setStopnum(newstopnum);
			roomStatus.setRecordTime(new Date());
			roomStatus.setRecordUser(staff.getStaffId());
			PmsmanageService.save(roomStatus);

		} else if (status.equals("F")) {

		} else {

			RoomStatus roomStatus = ((RoomStatus) (PmsmanageService.findOneByProperties(RoomStatus.class, "branchId",
					branchid, "roomType", rproomtype)));
			String oldsellnum = roomStatus.getSellnum().toString();
			int v = Integer.parseInt(oldsellnum) - 1;
			Integer newsellnum = Integer.valueOf(v);
			roomStatus.setSellnum(newsellnum);
			roomStatus.setRecordTime(new Date());
			roomStatus.setRecordUser(staff.getStaffId());
			PmsmanageService.save(roomStatus);
		}
		if (status.equals("T")) {

			RoomStatus roomStatus = ((RoomStatus) (PmsmanageService.findOneByProperties(RoomStatus.class, "branchId",
					branchid, "roomType", rproomtype)));
			String oldstopnum = roomStatus.getStopnum().toString();
			int v = Integer.parseInt(oldstopnum) + 1;
			Integer newstopnum = Integer.valueOf(v);
			roomStatus.setStopnum(newstopnum);
			roomStatus.setRecordTime(new Date());
			roomStatus.setRecordUser(staff.getStaffId());
			PmsmanageService.save(roomStatus);

		} else if (status.equals("F")) {

		} else {

			RoomStatus roomStatus = ((RoomStatus) (PmsmanageService.findOneByProperties(RoomStatus.class, "branchId",
					branchid, "roomType", rproomtype)));
			String oldsellnum = roomStatus.getSellnum().toString();
			int v = Integer.parseInt(oldsellnum) + 1;
			Integer newsellnum = Integer.valueOf(v);
			roomStatus.setSellnum(newsellnum);
			roomStatus.setRecordTime(new Date());
			roomStatus.setRecordUser(staff.getStaffId());
			PmsmanageService.save(roomStatus);

		}

		JSONUtil.responseJSON(response, new AjaxResult(1, null));
	}

	// 自定义房租价删除
	@RequestMapping("/delRoomPrice.do")
	public void delRoomPrice(HttpServletRequest request, HttpServletResponse response, String theme, String branchid,
			String roomtype, String rpid) throws UnknownHostException {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginUser.getStaff();
		String recordUser = staff.getStaffId();
		String[] themeids = theme.split(",");
		String[] branchids = branchid.split(",");
		String[] roomtypes = roomtype.split(",");
		String[] rpids = rpid.split(",");
		for (int i = 0; i < rpids.length; i++) {
			SysParam sysParam = ((SysParam) (PmsmanageService.findOneByProperties(SysParam.class, "paramType", "THEME",
					"paramName", themeids[i])));
			String themeid = sysParam.getContent().toString();
			Branch branchId = (Branch) PmsmanageService.findOneByProperties(Branch.class, "branchName", branchids[i]);
			String branchiD = branchId.getBranchId();
			RoomType roomType = ((RoomType) (PmsmanageService.findOneByProperties(RoomType.class, "roomName",
					roomtypes[i], "theme", themeid)));
			String roomtypeid = roomType.getRoomType().toString();
			String rpId = rpids[i];
			PmsmanageService.upateroomprice(themeid, branchiD, roomtypeid, rpId, recordUser);

		}
		JSONUtil.responseJSON(response, new AjaxResult(1, null));
	}
	
	
	// 自定义金柜删除
	@RequestMapping("/delCash.do")
	public void delCash(HttpServletRequest request, HttpServletResponse response, String branchId, String cashBox) throws UnknownHostException {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginUser.getStaff();
		String recordUser = staff.getStaffId();
		String[] branchids = branchId.split(",");
		String[] cashboxs = cashBox.split(",");
		for (int i = 0; i < branchids.length; i++) {
			SysParam sysParam = ((SysParam) (PmsmanageService.findOneByProperties(SysParam.class, "paramType", "CASHBOX",
					"paramName", cashboxs[i])));
			String cashboxid = sysParam.getContent().toString();
			Branch BranchId = (Branch) PmsmanageService.findOneByProperties(Branch.class, "branchName", branchids[i]);
			String branchiD = BranchId.getBranchId();
			PmsmanageService.upatecashbox(cashboxid, branchiD,recordUser);

		}
		JSONUtil.responseJSON(response, new AjaxResult(1, null));
	}
	
	
	@RequestMapping("/rpInitialize.do")
	public void rpInitialize(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String branchId = loginuser.getStaff().getBranchId();
		Branch branchid = (Branch) PmsmanageService.findOneByProperties(Branch.class, "branchId", branchId);
		String theme = branchid.getBranchType();
		List<?> rpInitializejudge = PmsmanageService.getRpInitializejudge(branchId,theme);
		String a = null;
		List<?> rpsetup = PmsmanageService.getRpsetup();
		List<?> rpbranchid = PmsmanageService.getRpbranchid();
		List<?> rproomtype = PmsmanageService.getRproomtype();
		String discountdata = ((Map<?, ?>) rpsetup.get(0)).get("DISCOUNT")==null ?"":((Map<?, ?>) rpsetup.get(0)).get("DISCOUNT").toString();
		if (null == discountdata || discountdata.equals("")) {
			a = "请先去配置会员等级等相关数据！";
		}else if(null == rpbranchid || rpbranchid.size() == 0) {
			a = "请先去配置门店等相关数据！";
		}else if(null == rproomtype || rproomtype.size() == 0) {
			a = "请先去配置房型等相关数据！";
		}else if(null == rpInitializejudge || rpInitializejudge.size() == 0){
		List<?> rproomtypeInitialize = PmsmanageService.getRproomtypeInitialize(theme);
	    String rpKind = null;
		if(theme.equals("1")){
			rpKind = "1";
			for (int i = 0; i < rproomtypeInitialize.size(); i++) {
				String roomtype = ((Map<?, ?>) rproomtypeInitialize.get(i)).get("ROOMTYPE").toString();
				List<?> rpidInitialize = PmsmanageService.getRpidInitialize();
				Double romprice = Double.parseDouble("200");
				for (int j = 0; j < rpidInitialize.size();j++) {
					String discount = ((Map<?, ?>) rpidInitialize.get(j)).get("DISCOUNT").toString();
					String rpid = ((Map<?, ?>) rpidInitialize.get(j)).get("PARAMNAME").toString();
					String rpname = ((Map<?, ?>) rpidInitialize.get(j)).get("PARAMDESC").toString();
					String memberrank = ((Map<?, ?>) rpidInitialize.get(j)).get("CONTENT").toString();
					Double d = new Double(discount) / 100;
					Format f = new DecimalFormat("0.00");
					Double dscount = Double.parseDouble(f.format(d));
					RoomPrice rprice = new RoomPrice();
					RoomPriceId roompriceid = new RoomPriceId();
					roompriceid.setBranchId(branchId);
					roompriceid.setRoomType(roomtype);
					roompriceid.setRpId(rpid);
					roompriceid.setRpKind("2");
					roompriceid.setStatus("1");
					rprice.setRoomPriceId(roompriceid);
					rprice.setRpName(rpname);
					rprice.setRoomPrice(romprice*dscount);
					rprice.setRpType("1");
					rprice.setDiscount(dscount);
					rprice.setRecordTime(new Date());
					rprice.setRecordUser(staff.getStaffId());
					rprice.setMemberRank(memberrank);
					rprice.setTheme(theme);
					PmsmanageService.save(rprice);
				
				}
			}
			
		}else if(theme.equals("2")){
			rpKind = "3";
		}else if(theme.equals("3")){
			
		}
		for (int i = 0; i < rproomtypeInitialize.size(); i++) {
			String roomtype = ((Map<?, ?>) rproomtypeInitialize.get(i)).get("ROOMTYPE").toString();
			List<?> rpidInitialize = PmsmanageService.getRpidInitialize();
			Double romprice = Double.parseDouble("200");
			for (int j = 0; j < rpidInitialize.size();j++) {
				String discount = ((Map<?, ?>) rpidInitialize.get(j)).get("DISCOUNT").toString();
				String rpid = ((Map<?, ?>) rpidInitialize.get(j)).get("PARAMNAME").toString();
				String rpname = ((Map<?, ?>) rpidInitialize.get(j)).get("PARAMDESC").toString();
				String memberrank = ((Map<?, ?>) rpidInitialize.get(j)).get("CONTENT").toString();
				Double d = new Double(discount) / 100;
				Format f = new DecimalFormat("0.00");
				Double dscount = Double.parseDouble(f.format(d));
				RoomPrice rprice = new RoomPrice();
				RoomPriceId roompriceid = new RoomPriceId();
				roompriceid.setBranchId(branchId);
				roompriceid.setRoomType(roomtype);
				roompriceid.setRpId(rpid);
				roompriceid.setRpKind(rpKind);
				roompriceid.setStatus("1");
				rprice.setRoomPriceId(roompriceid);
				rprice.setRpName(rpname);
				rprice.setRoomPrice(romprice*dscount);
				rprice.setRpType("1");
				rprice.setDiscount(dscount);
				rprice.setRecordTime(new Date());
				rprice.setRecordUser(staff.getStaffId());
				rprice.setMemberRank(memberrank);
				rprice.setTheme(theme);
				PmsmanageService.save(rprice);
			}
		}
		
		
	}else{
		 a = "当前门店房价数据已配置，无须初始化";
	  }
	JSONUtil.responseJSON(response, new AjaxResult(1, a));
	}
}
