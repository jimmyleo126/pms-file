package com.ideassoft.crm.controller;

import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.ideassoft.bean.AuditLog;
import com.ideassoft.bean.CheckOut;
import com.ideassoft.bean.Goods;
import com.ideassoft.bean.GoodsCategory;
import com.ideassoft.bean.MaintenanceLog;
import com.ideassoft.bean.Member;
import com.ideassoft.bean.OperateLog;
import com.ideassoft.bean.Purchase;
import com.ideassoft.bean.RepairApply;
import com.ideassoft.bean.RoomType;
import com.ideassoft.bean.Staff;
import com.ideassoft.bean.SysParam;
import com.ideassoft.core.ajax.AjaxResult;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.exception.DAOException;
import com.ideassoft.core.notifier.service.impl.NotifyService;
import com.ideassoft.core.page.Pagination;
import com.ideassoft.crm.service.AuditService;
import com.ideassoft.pmsmanage.service.PmsmanageService;
import com.ideassoft.util.DateUtil;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.RequestUtil;

@Transactional
@Controller
public class AuditController {
	@Autowired
	private AuditService AuditService;

	@Autowired
	private PmsmanageService PmsmanageService;

	@Autowired
	private NotifyService notifyService;
	
	// 默认审核人页面
	@RequestMapping("/turnTodefaultUserSet.do")
	public ModelAndView turnTodefaultUserSet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/page/crm/audit/turnTodefaultUserSet");
		/* List<?> audit = AuditService.getAudit(); */
		SysParam sysParam = (SysParam) AuditService.getAudit();
		String auditid = sysParam.getContent();
		List<?> saletype = AuditService.querysaleType();
		/*
		 * String auditid = ((Map<?, ?>)
		 * audit.get(0)).get("CONTENT").toString();
		 */
		Staff staff = (Staff) AuditService.getAuditName(auditid);
		String auditname = staff.getStaffName();
		/*
		 * List<?> auditName = AuditService.getAuditName(auditid); String
		 * auditname = ((Map<?, ?>)
		 * auditName.get(0)).get("STAFF_NAME").toString();
		 */
		request.setAttribute("auditid", auditid);
		request.setAttribute("auditname", auditname);
		request.setAttribute("saletype", saletype);
		return mv;
	}

	// select审核人页面
	@RequestMapping("/selectAuditer.do")
	public ModelAndView selectAuditer(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/page/crm/audit/selectAuditer");
		return mv;
	}

	@RequestMapping("/loaddefaultauditGrid.do")
	public void loaddefaultauditGrid(String queryData, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Pagination pagination;
		Integer pageNum = Integer.parseInt(request.getParameter("page"));
		Integer rows = Integer.parseInt(request.getParameter("rows"));
		pagination = new Pagination(pageNum, rows);
		List<?> result = AuditService.queryDefaultauditDialogData(queryData, pagination);
		JSONUtil.responseJSON(response, JSONUtil.buildReportJSONByPagination(result, pagination));
	}

	// 默认审核人修改
	@RequestMapping("/auditUserSure.do")
	public void auditUserSure(HttpServletRequest request, HttpServletResponse response, String auditId)
			throws Exception {
		SysParam sysParam = ((SysParam) (AuditService.findOneByProperties(SysParam.class, "paramType", "AUDIT")));
		sysParam.setContent(auditId);
		AuditService.save(sysParam);
		JSONUtil.responseJSON(response, new AjaxResult(1, null));
	}

	/*
	 * Pagination pagination = null;
	 * 
	 * Integer pageNum = Integer.parseInt(request.getParameter("page")); Integer
	 * rows = Integer.parseInt(request.getParameter("rows")); if (pageNum !=
	 * null && rows != null) { pagination = new Pagination(pageNum, rows); }
	 */

	// 采购审核信息页面
	@RequestMapping("/auditInfoDetail.do")
	public ModelAndView auditInfoDetail(HttpServletRequest request, HttpServletResponse response, String operid,
			String pagetype, String audittype) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/page/crm/audit/auditInfoDetail");
		List<?> pur = AuditService.getPur(operid);
		String applystaff = ((Map<?, ?>) pur.get(0)).get("RECORD_USER").toString();
		String applystaffname = ((Map<?, ?>) pur.get(0)).get("APPLYSTAFFNAME").toString();
		String branchid = ((Map<?, ?>) pur.get(0)).get("BRANCH_ID").toString();
		String branchname = ((Map<?, ?>) pur.get(0)).get("BRANCHNAME").toString();
		String applytime = ((Map<?, ?>) pur.get(0)).get("APPLYTIME").toString();
		String purchasetype = ((Map<?, ?>) pur.get(0)).get("PURCHASE_CATEGORY").toString();
		String purchaseamount = ((Map<?, ?>) pur.get(0)).get("PURCHASE_AMOUNT").toString();
		String applyreason = ((Map<?, ?>) pur.get(0)).get("REMARK").toString();
		List<?> purinfo = AuditService.getPurinfo(operid);
		request.setAttribute("pagetype", pagetype);
		request.setAttribute("audittype", audittype);
		request.setAttribute("operid", operid);
		request.setAttribute("applystaff", applystaff);
		request.setAttribute("applystaffname", applystaffname);
		request.setAttribute("branchid", branchid);
		request.setAttribute("branchname", branchname);
		request.setAttribute("applytime", applytime);
		request.setAttribute("purchasetype", purchasetype);
		request.setAttribute("purchaseamount", purchaseamount);
		request.setAttribute("applyreason", applyreason);
		request.setAttribute("purinfo", purinfo);
		return mv;
	}
    
	// 退房审核信息页面
	@RequestMapping("/checkoutInfoDetail.do")
	public ModelAndView checkoutInfoDetail(HttpServletRequest request, HttpServletResponse response, String operid,
			String pagetype, String audittype) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/page/crm/audit/checkoutInfoDetail");
		List<?> checkdata = AuditService.getCheckdata(operid);
		String branchid = ((Map<?, ?>) checkdata.get(0)).get("BRANCHID").toString();
		String roomid = ((Map<?, ?>) checkdata.get(0)).get("ROOMID").toString();
		List<?> checkroomtype = AuditService.getCheckroomtype(branchid, roomid);
		String roomtype = ((Map<?, ?>) checkroomtype.get(0)).get("ROOM_type").toString();
		RoomType roomType = ((RoomType) (AuditService.findOneByProperties(RoomType.class, "roomType", roomtype)));
		String roomname = roomType.getRoomName().toString();
		request.setAttribute("checkdata", checkdata);
		request.setAttribute("roomtype", roomtype);
		request.setAttribute("roomname", roomname);
		request.setAttribute("operid", operid);
		request.setAttribute("pagetype", pagetype);
		request.setAttribute("audittype", audittype);
		return mv;
	}
	
	// 维修审核信息页面
	@RequestMapping("/repairapplyInfoDetail.do")
	public ModelAndView repairapplyInfoDetail(HttpServletRequest request, HttpServletResponse response, String operid,
			String pagetype, String audittype) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/page/crm/audit/repairapplyInfoDetail");
		List<?> repairdata = AuditService.getRepairdata(operid);
		request.setAttribute("repairdata", repairdata);
		request.setAttribute("operid", operid);
		request.setAttribute("pagetype", pagetype);
		request.setAttribute("audittype", audittype);
		return mv;
	}

	// 审核通过且记录审核日志表
	@RequestMapping("/updateAuditSubmitOk.do")
	public void updateAuditSubmitOk(HttpServletRequest request, HttpServletResponse response, String audittype,
			String operid, String branchId, String applystaff, String auditMessage) throws Exception {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		/*
		 * LoginGetShift loginGetShift = (LoginGetShift)
		 * request.getSession(true).getAttribute("loginGetShift"); String
		 * shiftid = loginGetShift.getShiftId(); String cashbox =
		 * loginGetShift.getCashbox();
		 */
		Staff staff = loginUser.getStaff();
		String aurecordUser = staff.getStaffId();
		/* Date autime = new Date(); */
		if ("采购申请".equals(audittype)) {
			Purchase purchase = ((Purchase) (AuditService.findOneByProperties(Purchase.class, "purchaseId", operid)));
			purchase.setStatus("2");
			AuditService.save(purchase);
			String logId = this.AuditService.getSequence("SEQ_NEW_AUDITLOG", null);
			Date day = new Date();
			SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
			String t = df.format(day);
			String datId = t + branchId + logId;
			AuditLog auditLog = new AuditLog();
			auditLog.setLogId(datId);
			auditLog.setOperId(operid);
			auditLog.setOperType("1");
			auditLog.setStatus("2");
			auditLog.setRecordUser(applystaff);
			auditLog.setRemark(auditMessage);
			this.AuditService.saveAuditLog(auditLog);
			JSONUtil.responseJSON(response, new AjaxResult(1, null));
		} else if ("房租价申请".equals(audittype)) {
			OperateLog operatelog = ((OperateLog) (AuditService.findOneByProperties(OperateLog.class, "logId", operid)));
			operatelog.setRemark("2");
			AuditService.save(operatelog);
			String content = operatelog.getContent().toString();
			String branchid = content.substring(3, 9);
			String theme = content.substring(13, 14);
			String roomType = content.substring(18, 21);
			String roomype = roomType.replace("]", "");
			List<?> rpauditdata = PmsmanageService.getRpdata(branchid, theme, roomype);
			for (int i = 0; i < rpauditdata.size(); i++) {
				String bid = ((Map<?, ?>) rpauditdata.get(i)).get("branch_id").toString();
				String rid = ((Map<?, ?>) rpauditdata.get(i)).get("rp_id").toString();
				String tid = ((Map<?, ?>) rpauditdata.get(i)).get("theme").toString();
				String rt = ((Map<?, ?>) rpauditdata.get(i)).get("room_type").toString();
				String status = "1";
				AuditService.upateroompricedata(bid, rid, tid, rt, aurecordUser, status);
			}
			String logId = this.AuditService.getSequence("SEQ_NEW_AUDITLOG", null);
			Date day = new Date();
			SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
			String t = df.format(day);
			String datId = t + branchId + logId;
			AuditLog auditLog = new AuditLog();
			auditLog.setLogId(datId);
			auditLog.setOperId(operid);
			auditLog.setOperType("2");
			auditLog.setStatus("2");
			auditLog.setRecordUser(applystaff);
			auditLog.setRemark(auditMessage);
			this.AuditService.saveAuditLog(auditLog);
			JSONUtil.responseJSON(response, new AjaxResult(1, null));
		}else if("退房申请".equals(audittype)){
			CheckOut checkOut = (CheckOut) AuditService.findOneByProperties(CheckOut.class, "checkOutId", operid);
			Member member = (Member) AuditService.findById(Member.class, checkOut.getMemberId());
			checkOut.setStatus("2");
			AuditService.update(checkOut);
			String logId = this.AuditService.getSequence("SEQ_NEW_AUDITLOG", null);
			Date day = new Date();
			SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
			String t = df.format(day);
			String datId = t + branchId + logId;
			AuditLog auditLog = new AuditLog();
			auditLog.setLogId(datId);
			auditLog.setOperId(operid);
			auditLog.setOperType("3");
			auditLog.setStatus("2");
			auditLog.setRecordUser(applystaff);
			auditLog.setRemark(auditMessage);
			this.AuditService.saveAuditLog(auditLog);
			Map<String, String> map = new HashMap<String, String>();
			map.put("会员", member.getMemberName());
			map.put("审核", "通过");
			//notifyService.happenSendSms(null,null,null,"123123",map,member.getMobile(),member.getMemberRank());
			JSONUtil.responseJSON(response, new AjaxResult(1, null));
		}else if("维修申请".equals(audittype)){
			RepairApply  ra = (RepairApply)AuditService.findOneByProperties(RepairApply.class, "repairApplyId", operid);
			String recorduser = ((LoginUser)request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY)).getStaff().getStaffId();
				ra.setStatus("2");
				ra.setRecordTime(new Date());
				ra.setRecordUser(recorduser);
				String logId = this.AuditService.getSequence("SEQ_NEW_AUDITLOG", null);
				Date day = new Date();
				SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
				String t = df.format(day);
				String datId = t + branchId + logId;
				AuditLog auditLog = new AuditLog();
				auditLog.setLogId(datId);
				auditLog.setOperId(operid);
				auditLog.setOperType("4");
				auditLog.setStatus("2");
				auditLog.setRecordUser(applystaff);
				auditLog.setRemark(auditMessage);
				try {
					AuditService.update(ra);
					this.AuditService.saveAuditLog(auditLog);
					JSONUtil.responseJSON(response, new AjaxResult(1,"审核成功"));
				} catch (DAOException e) {
					e.printStackTrace();
					JSONUtil.responseJSON(response, new AjaxResult(2,"审核失败"));
				}
		}
	}

	// 审核打回且记录审核日志表
	@RequestMapping("/updateAuditSubmitback.do")
	public void updateAuditSubmitback(HttpServletRequest request, HttpServletResponse response, String audittype,
			String operid, String branchId, String applystaff, String auditMessage) throws Exception {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginUser.getStaff();
		String aurecordUser = staff.getStaffId();
		if ("采购申请".equals(audittype)) {
			Purchase purchase = ((Purchase) (AuditService.findOneByProperties(Purchase.class, "purchaseId", operid)));
			purchase.setStatus("3");
			AuditService.save(purchase);
			String logId = this.AuditService.getSequence("SEQ_NEW_AUDITLOG", null);
			Date day = new Date();
			SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
			String t = df.format(day);
			String datId = t + branchId + logId;
			AuditLog auditLog = new AuditLog();
			auditLog.setLogId(datId);
			auditLog.setOperId(operid);
			auditLog.setOperType("1");
			auditLog.setStatus("3");
			auditLog.setRecordUser(applystaff);
			auditLog.setRemark(auditMessage);
			this.AuditService.saveAuditLog(auditLog);
			JSONUtil.responseJSON(response, new AjaxResult(1, null));
		} else if ("房租价申请".equals(audittype)) {
			OperateLog operatelog = ((OperateLog) (AuditService.findOneByProperties(OperateLog.class, "logId", operid)));
			operatelog.setRemark("3");
			AuditService.save(operatelog);
			String content = operatelog.getContent().toString();
			String branchid = content.substring(3, 9);
			String theme = content.substring(13, 14);
			String roomType = content.substring(18, 21);
			String roomype = roomType.replace("]", "");
			List<?> rpauditdata = PmsmanageService.getRpdata(branchid, theme, roomype);
			for (int i = 0; i < rpauditdata.size(); i++) {
				String bid = ((Map<?, ?>) rpauditdata.get(i)).get("branch_id").toString();
				String rid = ((Map<?, ?>) rpauditdata.get(i)).get("rp_id").toString();
				String tid = ((Map<?, ?>) rpauditdata.get(i)).get("theme").toString();
				String rt = ((Map<?, ?>) rpauditdata.get(i)).get("room_type").toString();
				String status = "4";
				AuditService.upateroompricedata(bid, rid, tid, rt, aurecordUser, status);
			}
			String logId = this.AuditService.getSequence("SEQ_NEW_AUDITLOG", null);
			Date day = new Date();
			SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
			String t = df.format(day);
			String datId = t + branchId + logId;
			AuditLog auditLog = new AuditLog();
			auditLog.setLogId(datId);
			auditLog.setOperId(operid);
			auditLog.setOperType("2");
			auditLog.setStatus("3");
			auditLog.setRecordUser(applystaff);
			auditLog.setRemark(auditMessage);
			this.AuditService.saveAuditLog(auditLog);
			JSONUtil.responseJSON(response, new AjaxResult(1, null));
		} 
			
		}

	// 审核驳回且记录审核日志表
	@RequestMapping("/updateAuditSubmitFail.do")
	public void updateAuditSubmitFail(HttpServletRequest request, HttpServletResponse response, String audittype,
			String operid, String branchId, String applystaff, String auditMessage) throws Exception {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginUser.getStaff();
		String aurecordUser = staff.getStaffId();
		if ("采购申请".equals(audittype)) {
			Purchase purchase = ((Purchase) (AuditService.findOneByProperties(Purchase.class, "purchaseId", operid)));
			purchase.setStatus("4");
			AuditService.save(purchase);
			String logId = this.AuditService.getSequence("SEQ_NEW_AUDITLOG", null);
			Date day = new Date();
			SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
			String t = df.format(day);
			String datId = t + branchId + logId;
			AuditLog auditLog = new AuditLog();
			auditLog.setLogId(datId);
			auditLog.setOperId(operid);
			auditLog.setOperType("1");
			auditLog.setStatus("4");
			auditLog.setRecordUser(applystaff);
			auditLog.setRemark(auditMessage);
			this.AuditService.saveAuditLog(auditLog);
			JSONUtil.responseJSON(response, new AjaxResult(1, null));
		} else if ("房租价申请".equals(audittype)) {
			OperateLog operatelog = ((OperateLog) (AuditService.findOneByProperties(OperateLog.class, "logId", operid)));
			operatelog.setRemark("4");
			AuditService.save(operatelog);
			String content = operatelog.getContent().toString();
			String branchid = content.substring(3, 9);
			String theme = content.substring(13, 14);
			String roomType = content.substring(18, 21);
			String roomype = roomType.replace("]", "");
			List<?> rpauditdata = PmsmanageService.getRpdata(branchid, theme, roomype);
			for (int i = 0; i < rpauditdata.size(); i++) {
				String bid = ((Map<?, ?>) rpauditdata.get(i)).get("branch_id").toString();
				String rid = ((Map<?, ?>) rpauditdata.get(i)).get("rp_id").toString();
				String tid = ((Map<?, ?>) rpauditdata.get(i)).get("theme").toString();
				String rt = ((Map<?, ?>) rpauditdata.get(i)).get("room_type").toString();
				String status = "5";
				AuditService.upateroompricedata(bid, rid, tid, rt, aurecordUser, status);
			}
			String logId = this.AuditService.getSequence("SEQ_NEW_AUDITLOG", null);
			Date day = new Date();
			SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
			String t = df.format(day);
			String datId = t + branchId + logId;
			AuditLog auditLog = new AuditLog();
			auditLog.setLogId(datId);
			auditLog.setOperId(operid);
			auditLog.setOperType("2");
			auditLog.setStatus("4");
			auditLog.setRecordUser(applystaff);
			auditLog.setRemark(auditMessage);
			this.AuditService.saveAuditLog(auditLog);
			JSONUtil.responseJSON(response, new AjaxResult(1, null));
		}else if("退房申请".equals(audittype)){
			CheckOut checkOut = (CheckOut) AuditService.findOneByProperties(CheckOut.class, "checkOutId", operid);
			Member member = (Member) AuditService.findById(Member.class, checkOut.getMemberId());
			checkOut.setStatus("0");
			AuditService.update(checkOut);
			String logId = this.AuditService.getSequence("SEQ_NEW_AUDITLOG", null);
			Date day = new Date();
			SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
			String t = df.format(day);
			String datId = t + branchId + logId;
			AuditLog auditLog = new AuditLog();
			auditLog.setLogId(datId);
			auditLog.setOperId(operid);
			auditLog.setOperType("3");
			auditLog.setStatus("4");
			auditLog.setRecordUser(applystaff);
			auditLog.setRemark(auditMessage);
			this.AuditService.saveAuditLog(auditLog);
			Map<String, String> map = new HashMap<String, String>();
			map.put("会员", member.getMemberName());
			map.put("审核", "驳回");
			//notifyService.happenSendSms(null,null,null,"123123",map,member.getMobile(),member.getMemberRank());
			JSONUtil.responseJSON(response, new AjaxResult(1, null));
		}else if("维修申请".equals(audittype)){//另外记维修日志已删除记录
			RepairApply  ra = (RepairApply)AuditService.findOneByProperties(RepairApply.class, "repairApplyId", operid);
			MaintenanceLog mlog = new MaintenanceLog();
			String recorduser = ((LoginUser)request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY)).getStaff().getStaffId();
				ra.setStatus("0");
				ra.setRecordTime(new Date());
				ra.setRecordUser(recorduser);
				String logId = this.AuditService.getSequence("SEQ_NEW_AUDITLOG", null);
				Date day = new Date();
				SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
				String t = df.format(day);
				String datId = t + branchId + logId;
				AuditLog auditLog = new AuditLog();
				auditLog.setLogId(datId);
				auditLog.setOperId(operid);
				auditLog.setOperType("4");
				auditLog.setStatus("4");
				auditLog.setRecordUser(applystaff);
				auditLog.setRemark(auditMessage);
				String mlogId = DateUtil.currentDate("yyMMdd")+branchId+this.AuditService.getSequence("SEQ_MAINTENANCELOG_ID");
				mlog.setLogId(mlogId);
				mlog.setBranchId(branchId);
				mlog.setEquipment(ra.getEquipment());
				mlog.setRecordUser(staff.getStaffId());
				mlog.setRecordTime(new Date());
				mlog.setStatus("0");
				mlog.setRoomId(ra.getRoomId());
				mlog.setApplicationSource("2");
				mlog.setApplicationDate(ra.getApplicationDate());//申请时间
				mlog.setRepairTime(ra.getRepairTime());//申请维修日期
				mlog.setRepairApplyId(ra.getRepairApplyId());
				try {
					AuditService.update(ra);
					this.AuditService.saveAuditLog(auditLog);
					this.AuditService.save(mlog);
					JSONUtil.responseJSON(response, new AjaxResult(1,"维修申请已驳回"));
				} catch (DAOException e) {
					e.printStackTrace();
					JSONUtil.responseJSON(response, new AjaxResult(2,"驳回失败"));
				}
		}
	}

	/*
	 * @RequestMapping("/saleform.do") public void saleform(HttpServletRequest
	 * request, HttpServletResponse response, String SaleJson) throws Exception
	 * {
	 * 
	 * List list1 = Arrays.asList(SaleJson); list1 = new ArrayList(list1);
	 * ArrayList<String> als = new ArrayList<String>(list1); List data = new
	 * ArrayList(); String d = "saletype"; //String[] nameArray =
	 * request.getParameterValues("saletype"); //String[] s =
	 * request.getParameterValues("saletype"); JSONArray saletypearray = new
	 * JSONArray(SaleJson);
	 * 
	 * for (int i = 0; i < saletypearray.length(); i++) { JSONObject jsonsale =
	 * (JSONObject) saletypearray.get(i);
	 * //if(!((jsonsale.has("saletype"))&&(jsonsale.has("saletype2")))){
	 * if(!list1.contains(d)){ String content = jsonsale.getString("content");
	 * String paramname = jsonsale.getString("paramname"); SysParam sysParam =
	 * ((SysParam) (AuditService.findOneByProperties(SysParam.class,
	 * "paramType", "SALETYPE","content", content)));
	 * sysParam.setParamName(URLDecoder.decode(paramname, "UTF-8"));
	 * AuditService.save(sysParam); } else if(list1.contains(d)){ //else
	 * if(!((jsonsale.has("content"))&&(jsonsale.has("paramname")))){
	 * 
	 * data.add(URLDecoder.decode(jsonsale.getString("saletype"), "UTF-8"));
	 * JSONArray st = new JSONArray(s); for(int a = 0; a < st.length(); a++){
	 * 
	 * } }else
	 * if(((jsonsale.has("content"))&&(jsonsale.has("paramname")))&&list1
	 * .contains(d)){ String content = jsonsale.getString("content"); String
	 * paramname = jsonsale.getString("paramname"); SysParam sysParam =
	 * ((SysParam) (AuditService.findOneByProperties(SysParam.class,
	 * "paramType", "SALETYPE","content", content)));
	 * sysParam.setParamName(URLDecoder.decode(paramname, "UTF-8"));
	 * AuditService.save(sysParam);
	 * data.add(jsonsale.getString(URLDecoder.decode
	 * (jsonsale.getString("saletype"), "UTF-8")));
	 * 
	 * }
	 * 
	 * String logId = this.AuditService.getSequence("SEQ_NEW_PARAMID",null);
	 * String contentseq =
	 * this.AuditService.getSequence("SEQ_SALETYPE_CONTENT",null); SysParam
	 * sysparam = new SysParam(); sysparam.setParamId(logId);
	 * sysparam.setParamName(URLDecoder.decode(saletype,"UTF-8"));
	 * sysparam.setParamType("SALETYPE"); sysparam.setStatus("1");
	 * sysparam.setContent(contentseq);
	 * 
	 * Map<String, String> map = new HashMap<String, String>(); map.put(content,
	 * URLDecoder.decode(paramname, "UTF-8")); data.add(map); }
	 * URLDecoder.decode(, "UTF-8")
	 * 
	 * 
	 * JSONUtil.responseJSON(response, new AjaxResult(1, null)); }
	 */

	// 商品删除
	@RequestMapping("/delGoods.do")
	public void delGoods(HttpServletRequest request, HttpServletResponse response, String goodsid)
			throws UnknownHostException {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginUser.getStaff();
		String recordUser = staff.getStaffId();
		String[] goodsids = goodsid.split(",");
		for (int i = 0; i < goodsids.length; i++) {
			if (goodsids[i] != null) {
				Goods goods = ((Goods) (AuditService.findOneByProperties(Goods.class, "goodsId", goodsids[i])));
				goods.setStatus("0");
				goods.setRecordUser(recordUser);
				goods.setRecordTime(new Date());
			}
		}
		JSONUtil.responseJSON(response, new AjaxResult(1, null));
	}

	// 商品分类删除
	@RequestMapping("/delCategory.do")
	public void delCategory(HttpServletRequest request, HttpServletResponse response, String categoryid)
			throws UnknownHostException {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginUser.getStaff();
		String recordUser = staff.getStaffId();
		String[] categoryids = categoryid.split(",");
		for (int i = 0; i < categoryids.length; i++) {
			if (categoryids[i] != null) {
				GoodsCategory goodscategory = ((GoodsCategory) (AuditService.findOneByProperties(GoodsCategory.class,
						"categoryId", categoryids[i])));
				goodscategory.setStatus("0");
				goodscategory.setRecordUser(recordUser);
				goodscategory.setRecordTime(new Date());
			}
		}
		JSONUtil.responseJSON(response, new AjaxResult(1, null));
	}

}
