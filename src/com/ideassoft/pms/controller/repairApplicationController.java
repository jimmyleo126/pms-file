package com.ideassoft.pms.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSON;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ideassoft.bean.Contrart;
import com.ideassoft.bean.MaintenanceLog;
import com.ideassoft.bean.Member;
import com.ideassoft.bean.RepairApply;
import com.ideassoft.bean.RoomType;
import com.ideassoft.bean.Staff;
import com.ideassoft.core.ajax.AjaxResult;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.exception.DAOException;
import com.ideassoft.core.jdbc.SqlBuilder;
import com.ideassoft.core.page.Pagination;
import com.ideassoft.pms.service.repairApplicationService;
import com.ideassoft.util.DateUtil;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.RequestUtil;

@Transactional
@Controller
public class repairApplicationController {
	@Autowired
	private repairApplicationService repairService;
	
	
	/*
	 * 页面加载时展示维修申请表数据
	 */
	@RequestMapping("/repairData.do")
	public ModelAndView repairData(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ModelAndView mv = new ModelAndView();
		Pagination pagination = SqlBuilder.buildPagination(request);
		pagination.setRows(14);
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginUser.getStaff().getBranchId();
		String sql= "loadRepairApply";
		List<?> result = repairService.findBySQLWithPagination(sql, new String[]{branchid}, pagination, true);
		/*Map<String,Object> map = new HashMap<String, Object>();
		for (int i = 0; i < result.size(); i++) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String applicationtime = sdf.format(((Map)result.get(i)).get("APPLICATIONDATE"));
			String recordtime = sdf.format(((Map)result.get(i)).get("RECORDTIME"));
			String repairtime = sdf.format(((Map)result.get(i)).get("REPAIRTIME"));
			((Map)result.get(i)).get("APPLICATIONDATE". 
			//List<String> list = new ArrayList<String>();
			//list.add(applicationtime);
			//list.add(recordtime);
			//list.add(repairtime);
			//map.put(Integer.toString(i), list);
		}*/
		mv.addObject("result", result);
		mv.addObject("pagination",pagination);
		//mv.addObject("map",map);
		mv.setViewName("page/ipmspage/leftmenu/repair/allRepairApplication");
		return mv;
	}
	/*
	 * 新增维修申请记录
	 */
	@RequestMapping("/addRepairApplicationRecord.do")
	public void addRepairApplicationRecord(HttpServletRequest request,HttpServletResponse response,String contrartid,String roomId,String person,String mobile,String equipment,String emergent,String repairtime,String remark){
		try {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		Date repairdate = null;
		System.out.println(sdf.parse(sdf.format(date)).getTime());
		System.out.println(sdf.parse(repairtime).getTime());
		if(sdf.parse(sdf.format(date)).getTime()>sdf.parse(repairtime).getTime()){
			JSONUtil.responseJSON(response, new AjaxResult(0,",添加失败,请确认维修申请日期是否正确!"));
			return;
		}else{
			repairdate = sdf.parse(repairtime);	
		}
		LoginUser loginuser = (LoginUser)request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String branchid = staff.getBranchId();
		Contrart ct = (Contrart) repairService.findOneByProperties(Contrart.class, "contrartId", contrartid);
		String roomtype = ct.getRoomType();
		Member mr = (Member)repairService.findOneByProperties(Member.class, "memberName", person);
		String memberid = mr.getMemberId();
		String[] eqsarr = equipment.split(",");
		for(String eq:eqsarr){
			//新增维修申请记录
			RepairApply ra = new RepairApply();
			String id = DateUtil.currentDate("yyMMdd")+branchid+repairService.getSequence("SEQ_REPAIRAPPLY_ID");
			ra.setRepairApplyId(id);
			ra.setBranchId(branchid);
			ra.setContractId(contrartid);
			ra.setRoomId(roomId);
			ra.setApplicationDate(new Date());
			ra.setReservedPerson(memberid);
			ra.setMobile(mobile);
			ra.setStatus("1");
			ra.setAuditDescription("");
			ra.setRecordTime(new Date());
			ra.setRecordUser(staff.getStaffId());
			ra.setRemark(remark);
			ra.setRepairTime(repairdate);
			ra.setEquipment(eq);
			ra.setEmergent(emergent);
			ra.setRoomType(roomtype);
			//新增维修操作日志记录
			MaintenanceLog mlog = new MaintenanceLog();
			String mlogId = DateUtil.currentDate("yyMMdd")+branchid+repairService.getSequence("SEQ_MAINTENANCELOG_ID");
			mlog.setLogId(mlogId);
			mlog.setBranchId(branchid);
			mlog.setEquipment(eq);
			mlog.setRecordUser(staff.getStaffId());
			mlog.setRecordTime(new Date());
			mlog.setStatus("1");
			mlog.setRoomId(roomId);
			mlog.setApplicationSource("2");
			mlog.setApplicationDate(new Date());//申请时间
			mlog.setRepairTime(repairdate);//申请维修日期
			mlog.setRepairApplyId(id);
			repairService.save(ra);//更新申请记录表
			repairService.save(mlog);//更新申请日志表
		}
			JSONUtil.responseJSON(response, new AjaxResult(1,"添加成功 "));
		} catch (Exception e) {
			e.printStackTrace();
			JSONUtil.responseJSON(response, new AjaxResult(2,"添加失败 "));
		}
	}
	/*
	 * 跳转新增维修申请表页面
	 */
	@RequestMapping("/addRepairApplication.do")
	public ModelAndView addRepairApplication(HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/ipmspage/leftmenu/repair/addRepairApplication");
		return mv;
	}
	/*
	 * 页面条件查询维修详情
	 */
	@RequestMapping("/queryRepairDetail.do")
	public ModelAndView queryRepairDetail(HttpServletRequest request,HttpServletResponse response,String startdate,String enddate,String roomid,String equipment,
			String checkstatus,String mobile ) throws Exception{
		if("开始日期".equals(startdate)){
			startdate = "";
		}
		if("结束日期".equals(enddate)){
			enddate = "";
		}
		ModelAndView mv = new ModelAndView();
		Pagination pagination = SqlBuilder.buildPagination(request);
		pagination.setRows(14);
		LoginUser loginuser = (LoginUser)request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String branchid = staff.getBranchId();
		String sql = "queryRepairDetail";
		List<?> result = repairService.findBySQLWithPagination(sql,new String[]{branchid,roomid,mobile,startdate,enddate,equipment,checkstatus},pagination,true);
		mv.addObject("result", result);
		mv.addObject("pagination", pagination);
		mv.setViewName("page/ipmspage/leftmenu/repair/queryRepairDetail");
		return mv;
	}
	
	/*
	 * 双击显示详情页面
	 */
	@RequestMapping("/dblRepairDetail.do")
	public ModelAndView dblRepairDetail(HttpServletRequest request,String applyId,String repairLogstatus,String logId) throws Exception{//日志状态要转为数字logId不同
		
		if(repairLogstatus.equals("已删除")){
			repairLogstatus = "0";
		}else if(repairLogstatus.equals("未修复")){
			repairLogstatus = "1";
		}else{
			repairLogstatus = "2";
		}
		Pagination pagination = SqlBuilder.buildPagination(request);
		LoginUser loginuser = (LoginUser)request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String branchid = staff.getBranchId();
		String sql = "dblshowRepair";
		List<?> result = repairService.findBySQLWithPagination(sql,new String[]{branchid,applyId,repairLogstatus,logId},pagination,true);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/ipmspage/leftmenu/repair/dblRepairDetail");
		//mv.addObject("pagination",pagination);
		mv.addObject("result",result);
		return mv;
	}
	/*
	 * 该操作针对前台新增申请之后需要立刻取消的情况
	 */
	@RequestMapping("/cancel.do")
	public void cancel(HttpServletResponse response,HttpServletRequest request,String applyId){
		RepairApply  ra = (RepairApply)repairService.findOneByProperties(RepairApply.class, "repairApplyId", applyId);
		LoginUser loginuser = (LoginUser)request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String branchid = staff.getBranchId();
		MaintenanceLog mlog = new MaintenanceLog();
		String recorduser = ((LoginUser)request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY)).getStaff().getStaffId();
		if(ra.getStatus().equals("1")){
			ra.setStatus("0");
			ra.setRecordTime(new Date());
			ra.setRecordUser(recorduser);
			String mlogId = DateUtil.currentDate("yyMMdd")+branchid+repairService.getSequence("SEQ_MAINTENANCELOG_ID");
			mlog.setLogId(mlogId);
			mlog.setBranchId(branchid);
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
				repairService.update(ra);
				repairService.save(mlog);//新增日志记录
				JSONUtil.responseJSON(response, new AjaxResult(3,"维修申请已取消"));
			} catch (DAOException e) {
				e.printStackTrace();
				JSONUtil.responseJSON(response, new AjaxResult(4,"取消失败"));
			}
			
		}else if(ra.getStatus().equals("2")){
			JSONUtil.responseJSON(response, new AjaxResult(1,"该申请已通过审核,无法取消"));
		}else{
			JSONUtil.responseJSON(response, new AjaxResult(2,"该申请已取消,请勿重复操作"));
		}
	}
	/*
	 * 显示极简户型D1下的所有房间
	 */
	@RequestMapping("/showSimpleRoomD1.do")
	public void showSimpleRoomD1(HttpServletRequest request,HttpServletResponse response){
		List<?> result = getRoom(request, response,"showFlatRoomId","D1");
		JSONUtil.responseJSON(response, result);
	}
	/*
	 * 显示极简户型D2下的所有房间
	 */
	@RequestMapping("/showSimpleRoomD2.do")
	public void showSimpleRoomD2(HttpServletRequest request,HttpServletResponse response){
		List<?> result = getRoom(request, response,"showFlatRoomId","D2");
		JSONUtil.responseJSON(response, result);
	}
	/*
	 * 显示雅居户型D3下所有房间
	 */
	@RequestMapping("/showAgileRoom.do")
	public void showAgileRoom(HttpServletRequest request,HttpServletResponse response){
		List<?> result = getRoom(request, response,"showFlatRoomId","D3");
		JSONUtil.responseJSON(response, result);
	}
	/*
	 * 显示奢华户型D4下所有房间
	 */
	@RequestMapping("/showLuxuryRoom.do")
	public void showLuxuryRoom(HttpServletRequest request,HttpServletResponse response){
		List<?> result = getRoom(request, response,"showFlatRoomId","D4");
		JSONUtil.responseJSON(response, result);
	}
	/**
	 * @param request
	 * @param response
	 * @throws DAOException
	 */
	private List<?> getRoom(HttpServletRequest request,
			HttpServletResponse response,String sql,String roomtype) throws DAOException {
		LoginUser loginuser = (LoginUser)request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String branchid = staff.getBranchId();
		List<?> result = repairService.findBySQL(sql, new String[]{branchid,"1",roomtype}, true);
		return  result;
	}
	/*
	 *自动获取新增申请记录数据 
	 */
	@RequestMapping("/getApplicationDate.do")
	public void getApplicationDate(HttpServletRequest request,HttpServletResponse response,String roomid,String roomtype){//此处应将房型传来并且在合同中的状态为1,有效
		LoginUser loginuser = (LoginUser)request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String branchid = staff.getBranchId();
		String [] rooms = roomid.split(",");
		Map<String,Object> map = new HashMap<String,Object>();
		
		Contrart ct = (Contrart) repairService.findOneByProperties(Contrart.class, "branchId", branchid, "roomId",roomid,"roomType",roomtype,"status","1");
		String contractId = ct.getContrartId();
		String memberId = ct.getMemberId();
		Member mr = (Member) repairService.findOneByProperties(Member.class, "memberId", memberId);
		String memberName = mr.getMemberName();
		String mobile = ct.getMobile();	
		map.put("contrartid", contractId);
		map.put("membername", memberName);
		map.put("mobile", mobile);
		map.put("memberId", memberId);
		JSONUtil.responseJSON(response, map);
		/*for(String roomId:rooms){
		 	//批量添加
			Contrart ct = (Contrart) repairService.findOneByProperties(Contrart.class, "branchId", branchid, "roomId",roomId,"roomType",roomtype,"status","1");//此处暂停
			contractId += ct.getContrartId()+",";
			memberId += ct.getMemberId()+",";
			//Member mr = (Member) repairService.findOneByProperties(Member.class, "memberId", memberId.substring(0,memberId.length()-1));
			//memberName += mr.getMemberName()+",";
			mobile += ct.getMobile()+",";	
		}
		String memberName="";
		String[] memberid = memberId.split(",");
		for(String m:memberid){
			Member mr = (Member) repairService.findOneByProperties(Member.class, "memberId", m);
			memberName += mr.getMemberName()+",";
		}
		map.put("contrartid", contractId.substring(0,contractId.length()-1));
		map.put("membername", memberName.substring(0,memberName.length()-1));
		map.put("mobile", mobile.substring(0,mobile.length()-1));*/
	}
	/*
	 * 页面点击修复按钮需要验证该记录是否已修复
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/checkDone.do")
	public void checkDone(HttpServletRequest request,HttpServletResponse response,String applyId){
		List<MaintenanceLog> mlogs =
		repairService.findByProperties(MaintenanceLog.class, "repairApplyId", applyId);
		for(MaintenanceLog m: mlogs){
			if("2".equals(m.getStatus())){
				JSONUtil.responseJSON(response, new AjaxResult(1,"该申请已修复,请勿重复操作!"));	
				return;
			}
			//System.out.println(m.getStatus());
		}
		//System.out.println(mlogs.get(0).getClass().getClassLoader());
	}
	/*
	 * 处理已修复申请记录
	 */
	@RequestMapping("/doneApplication.do")
	public ModelAndView doneApplication(HttpServletRequest request,String applyId){
		RepairApply  ra = (RepairApply)repairService.findOneByProperties(RepairApply.class, "repairApplyId", applyId);
		ModelAndView mv = new ModelAndView();
		if("2".equals(ra.getStatus())){
			mv.addObject("applyId",applyId);
			mv.setViewName("page/ipmspage/leftmenu/repair/doneApplication");
		}else{
		}
		return mv;
	}
	/*
	 * 处理修复申请添加新日志记录
	 */
	@RequestMapping("/addRepairLogRecord.do")
	public void addRepairLogRecord(HttpServletRequest request,HttpServletResponse response,
									String applyId,String problemtab,String problemdesc,String repairTime,
									String repairPerson,String timearea,String status,String repairLogRemark){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			Date date= new Date();
			if("0".equals(status)){
				JSONUtil.responseJSON(response, new AjaxResult(0,"请确认是否已修复!"));
				return;
			}
		RepairApply  ra = (RepairApply)repairService.findOneByProperties(RepairApply.class, "repairApplyId", applyId);
		if(repairTime ==null || "".equals(repairTime)){
			JSONUtil.responseJSON(response,new AjaxResult(5,"请输入维修日期!"));
			return;
		}else if(date.getTime()<sdf.parse(repairTime).getTime() || sdf.parse(sdf.format(ra.getApplicationDate())).getTime()>sdf.parse(repairTime).getTime()){
			JSONUtil.responseJSON(response, new AjaxResult(3,"请确认维修日期是否正确!"));
			return;
		}
		
		MaintenanceLog mlog = new MaintenanceLog();
		LoginUser loginuser = (LoginUser)request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String branchid = staff.getBranchId();
		String mlogid = DateUtil.currentDate("yyMMdd")+branchid+repairService.getSequence("SEQ_MAINTENANCELOG_ID");
		mlog.setLogId(mlogid);
		mlog.setBranchId(branchid);
		mlog.setEquipment(ra.getEquipment());
		mlog.setProblemTag(problemtab);
		mlog.setDescribe(problemdesc);
		mlog.setRecordUser(staff.getStaffId());
		mlog.setRecordTime(new Date());
		mlog.setStatus(status);
		mlog.setRemark(repairLogRemark);
		mlog.setRoomId(ra.getRoomId());
		mlog.setApplicationSource("2");
		mlog.setRepairPerson(repairPerson);
		mlog.setApplicationDate(ra.getApplicationDate());
		mlog.setRepairTimearea(timearea);
		mlog.setRepairTime(sdf.parse(repairTime));
		mlog.setRepairApplyId(applyId);
		mlog.setAcrepairTime(sdf.parse(repairTime));
		repairService.save(mlog);
		JSONUtil.responseJSON(response, new AjaxResult(1,"处理成功!"));
		} catch (Exception e) {
			e.printStackTrace();
			JSONUtil.responseJSON(response, new AjaxResult(2,"处理失败!"));
		}
	}
	
	
	
	
	
	
	
	
	
	
	
}
