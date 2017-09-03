package com.ideassoft.pms.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ideassoft.bean.Check;
import com.ideassoft.bean.Staff;
import com.ideassoft.bean.WorkBill;
import com.ideassoft.bean.wrapper.MultiQueryWorkbill;
import com.ideassoft.core.ajax.AjaxResult;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.jdbc.SqlBuilder;
import com.ideassoft.core.page.Pagination;
import com.ideassoft.pms.service.RoomService;
import com.ideassoft.pms.service.WorkService;
import com.ideassoft.util.DateUtil;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.RequestUtil;

@Transactional
@Controller
public class WorkController {

	@Autowired
	private WorkService workservice;
	
	@RequestMapping("/showworkaccountdetail.do")
	public ModelAndView showworkaccountdetail(MultiQueryWorkbill multiqueryworkbill, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		LoginUser loginuser = (LoginUser) request.getSession(true)
		.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String branchId = staff.getBranchId();
		Pagination pagination = SqlBuilder.buildPagination(request);
		String recorduser = staff.getStaffId();
		//String branchid = staff.getBranchId();
		//String workbillid = sdf.format(currentdate);
		if("self".equals(multiqueryworkbill.getRecorduser())){
			multiqueryworkbill.setRecorduser(recorduser);
		}
		String sql = "loadworkbill";
		List<?> list = workservice.findBySQLWithPagination(sql, new String[]{multiqueryworkbill.getWorkbillid(), branchId,  multiqueryworkbill.getStatus(), multiqueryworkbill.getCurrentdate(), multiqueryworkbill.getName(), multiqueryworkbill.getRecorduser()}, pagination, true);
		ModelAndView mv = new ModelAndView();
		mv.addObject("list", list);
		mv.addObject("pagination", pagination);
		mv.addObject("multiqueryworkbill", multiqueryworkbill);
		mv.setViewName("/page/ipmspage/work_account/workaccountData");
		return mv;
	}
	
	
	@RequestMapping("/getcreatworkbillinfo.do")
	public void getcreatworkbillinfo(HttpServletRequest request,
			HttpServletResponse response) {
		LoginUser loginuser = (LoginUser) request.getSession(true)
				.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		WorkBill workbill = new WorkBill();
		Calendar calendar = Calendar.getInstance();
		String workbillid = null;//DateUtil.d2s(calendar.getTime(), "yyMMdd")  + workservice.getSequence("SEQ_NEW_WORKBILL");
		workbill.setWorkbillId(workbillid);
		workbill.setRecordTime(calendar.getTime());
		workbill.setRecordUser(staff.getStaffId());
		String recordTime = DateUtil.d2s(calendar.getTime(), "yyyy/MM/dd HH:mm");
		String staffName = staff.getStaffName();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("workbill", workbill);
		map.put("recordTime", recordTime);
		map.put("staffName", staffName);
		try{
			JSONUtil.responseJSON(response, JSONUtil.fromObject(map));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/creatworkbill.do")
	public void creatworkbill(WorkBill workbill, HttpServletRequest request,
			HttpServletResponse response) {
		LoginUser loginuser = (LoginUser) request.getSession(true)
				.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String branchId = staff.getBranchId();
		String recorduser = staff.getStaffId();
		List<WorkBill> listworkbill = workservice.findByProperties(WorkBill.class, "branchId", branchId, "recordUser", recorduser, "status", "1");
		if(listworkbill.size() > 0){
			String workbillid = DateUtil.d2s(new Date(), "yyMMdd")  + workservice.getSequence("SEQ_NEW_WORKBILL");
			String branchid = staff.getBranchId();
			workbill.setWorkbillId(workbillid);
			workbill.setRecordUser(recorduser);
			workbill.setBranchId(branchid);
			workbill.setStatus("1");
			try {
				workservice.save(workbill);
				JSONUtil.responseJSON(response, new AjaxResult(1, "创建成功!"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			JSONUtil.responseJSON(response, new AjaxResult(0, "已有未结工作账!"));
		}
	}
	
	@RequestMapping("/updateworkbill.do")
	public void updateworkbill(String name, String describe, String workbillid, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			workservice.updateworkbill(name, describe, workbillid);
			JSONUtil.responseJSON(response, new AjaxResult(1, "修改成功!"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/loadworkbill.do")
	public void loadworkbill(MultiQueryWorkbill multiqueryworkbill, HttpServletRequest request,
			HttpServletResponse response) {
		LoginUser loginuser = (LoginUser) request.getSession(true)
				.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String recorduser = staff.getStaffId();
		//String branchid = staff.getBranchId();
		//String workbillid = sdf.format(currentdate);
		if("self".equals(multiqueryworkbill.getRecorduser())){
			multiqueryworkbill.setRecorduser(recorduser);
		}
		
		try {
			String sql = "loadworkbill";
			List<?> list = workservice.findBySQL(sql, new String[]{multiqueryworkbill.getWorkbillid(), multiqueryworkbill.getStatus(), multiqueryworkbill.getCurrentdate(), multiqueryworkbill.getName(), multiqueryworkbill.getRecorduser()}, true);
			JSONUtil.responseJSON(response, list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/getworkbillinfo.do")
	public void getworkbillinfo(HttpServletRequest request, HttpServletResponse response,String hostcheckid,
				String workbillid){
		WorkBill workbill = (WorkBill) workservice.findOneByProperties(WorkBill.class, "workbillId", workbillid);
		Staff recordUser = (Staff) workservice.findOneByProperties(Staff.class, "staffId", workbill.getRecordUser());
		Staff finalUser = (Staff) workservice.findOneByProperties(Staff.class, "staffId", workbill.getFinalUser());
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("workbill", workbill);
		String finalUserName = "";
		String recordUserName = "";
		if(finalUser != null){
			finalUserName = finalUser.getStaffName();
		}
		if(recordUser != null){
			recordUserName = recordUser.getStaffName();
		}
		result.put("finalUserName", finalUserName);
		result.put("recordUserName", recordUserName);
		JSONUtil.responseJSON(response, result);
	}
	
	@RequestMapping("/loadworkbilllogdata.do")
	public void loadworkbilllogdata(HttpServletRequest request, HttpServletResponse response,String hostcheckid,
				String workbillid){
		WorkBill workbill = (WorkBill) workservice.findOneByProperties(WorkBill.class, "workbillId", workbillid);
		JSONUtil.responseJSON(response, workbill);
	}
	

}
