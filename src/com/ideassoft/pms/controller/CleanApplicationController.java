package com.ideassoft.pms.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ideassoft.bean.Clean;
import com.ideassoft.bean.CleanApply;
import com.ideassoft.bean.CleanInfo;
import com.ideassoft.bean.CleanRecord;
import com.ideassoft.bean.ExceptionMessage;
import com.ideassoft.core.ajax.AjaxResult;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.jdbc.SqlBuilder;
import com.ideassoft.core.page.Pagination;
import com.ideassoft.crm.service.LogService;
import com.ideassoft.pms.service.CleanService;
import com.ideassoft.pms.service.LeftmenuService;
import com.ideassoft.pms.service.LogServiceInPms;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.ReflectUtil;
import com.ideassoft.util.RequestUtil;

@Transactional
@Controller
public class CleanApplicationController {
	@Autowired
	private CleanService CleanService;
	@Autowired
	private LogServiceInPms logServiceInPms;

	// 保洁
	@RequestMapping("/handleApply.do")
	public ModelAndView handleApply(HttpServletRequest request)
			throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/ipmspage/leftmenu/handleapply/apply");
		return mv;
	}

	@RequestMapping("/loadapply.do")
	public ModelAndView loadapply(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/ipmspage/leftmenu/handleapply/loadapply");
		return mv;
	}

	// 加载所有申请数据
	@RequestMapping("/querycleanapply.do")
	public ModelAndView querycleanapply(HttpServletRequest request)
			throws Exception {
		ModelAndView mv = new ModelAndView();
		Pagination pagination = (Pagination) ReflectUtil.setBeanFromRequest(request, Pagination.class);
		if(pagination != null && pagination.getPageNum() == null){
			pagination.setPageNum(1);
			pagination.setRows(12);
		}
		LoginUser loginuser = (LoginUser) request.getSession(true)
				.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginuser.getStaff().getBranchId();
		List<?> applicationdata = CleanService.getApplicationdata(branchid,
				pagination);

		mv.setViewName("page/ipmspage/leftmenu/handleapply/applydata");
		mv.addObject("branchId", branchid);
		mv.addObject("applicationdata", applicationdata);
		mv.addObject("pagination", pagination);
		return mv;
	}

	@RequestMapping("/setamount.do")
	public void setAmount(HttpServletRequest request,
			HttpServletResponse response, String time, String timearea,
			String restamount) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date cleanDate = sdf.parse(time);
		try {
			Clean clean = (Clean) CleanService.findOneByProperties(Clean.class,
					"cleanDate", cleanDate, "timeArea", timearea);
			clean.setRestAmount(Integer.parseInt(restamount));
			CleanService.update(clean);
			 JSONUtil.responseJSON(response, new AjaxResult(1, "修改成功!"));
		} catch (Exception e) {
			e.printStackTrace();
			 JSONUtil.responseJSON(response, new AjaxResult(2, "修改失败!"));
		}

	}

	@RequestMapping("/resetamount.do")
	public void resetAmount(HttpServletRequest request,
			HttpServletResponse response, String time, String timearea,
			String amount) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date cleanDate = sdf.parse(time);
		if ("上午".equals(timearea)) {
			timearea = "0";
		} else {
			timearea = "1";
		}
		try {
			Clean clean = (Clean) CleanService.findOneByProperties(Clean.class,
					"cleanDate", cleanDate, "timeArea", timearea);
			clean.setRestAmount(Integer.parseInt(amount));
			CleanService.update(clean);
			JSONUtil.responseJSON(response, new AjaxResult(1,
					"这是后台重新修改数量时传来的成功消息!"));
		} catch (Exception e) {
			e.printStackTrace();
			JSONUtil.responseJSON(response, new AjaxResult(2,
					"这是后台重新修改数量时传来的失败消息!"));
		}

	}

	@RequestMapping("/querycleanapplycondition.do")
	public ModelAndView querycleanapplycondition(HttpServletRequest request,
			String startdate, String enddate, String timeArea, String status)
			throws Exception {
		ModelAndView mv = new ModelAndView();
		Pagination pagination = (Pagination) ReflectUtil.setBeanFromRequest(request, Pagination.class);
		if(pagination != null && pagination.getPageNum() == null){
			pagination.setPageNum(1);
			pagination.setRows(12);
		}
		LoginUser loginuser = (LoginUser) request.getSession(true)
				.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginuser.getStaff().getBranchId();
		List<?> applicationdataCondition = CleanService.getApplicationdataCondition(branchid, startdate, enddate,
						timeArea, status, pagination);
		//System.out.println(applicationdataCondition);
		mv.setViewName("page/ipmspage/leftmenu/handleapply/applydatacondition");
		mv.addObject("applicationdataCondition", applicationdataCondition);
		mv.addObject("pagination", pagination);
		mv.addObject("startdate", startdate);
		mv.addObject("enddate", enddate);
		mv.addObject("timeArea", timeArea);
		mv.addObject("status", status);
		mv.addObject("branchId", branchid);
		return mv;
	}

	// 查找当前门店下的所有房间号

	@RequestMapping("/selectallroomid.do")
	public void selectallroomid(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		LoginUser loginuser = (LoginUser) request.getSession(true)
				.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginuser.getStaff().getBranchId();
		// String timer=time.trim();
		List<?> allroomid = CleanService.findRoomByBranchId(branchid);
		// System.out.println(allroomid);
		JSONUtil.responseJSON(response, allroomid);

	}

	// 查找当前门店下的所有员工
	@RequestMapping("/selectallstaff.do")
	public void selectallstaff(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginuser.getStaff().getBranchId();
		// String timer=time.trim();
		List<?> allstaff = CleanService.findstaffByBranchId(branchid);
		// System.out.println(allstaff);
		JSONUtil.responseJSON(response, allstaff);

	}

	// 保洁记录插入
	@RequestMapping("/updatecleanrecord.do")
	public void updatecleanrecord(HttpServletRequest request,
			HttpServletResponse response, String recordId, String cleanTime,
			String timeArea, String roomId, String cleanPerson, String remark)
			throws Exception {
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginuser.getStaff().getBranchId();
		String recordUser = loginuser.getStaff().getStaffName();
		CleanApply cleanApply = (CleanApply) CleanService.findOneByProperties(CleanApply.class, "cleanApplyId", recordId);
		cleanApply.setStatus("2");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date cleandate = sdf.parse(cleanTime);
		// String cleandate2 = sdf2.format(cleandate);
		if ("上午".equals(timeArea)) {
			timeArea = "0";
		} else {
			timeArea = "1";
		}
		Clean clean = (Clean) CleanService.findOneByProperties(Clean.class,
				"cleanDate", cleandate, "timeArea", timeArea);
		//System.out.println(clean);
		clean.setBranchId(branchid);
		clean.setCleanDate(cleandate);
		if (" ".equals(clean.getRoomId())) {
			clean.setRoomId(roomId);
		} else {
			// System.out.println(clean.getRoomId());
			clean.setRoomId(clean.getRoomId() + "," + roomId);
		}
		if (" ".equals(clean.getCleanPerson())) {
			clean.setCleanPerson(cleanPerson);
		} else {
			clean.setCleanPerson(clean.getCleanPerson() + "," + cleanPerson);
		}
		logServiceInPms.updatecleanapplyrecord((LoginUser) request.getSession(
				true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY), remark,"6");
		try {
			CleanService.update(clean);
			CleanService.update(cleanApply);
			JSONUtil.responseJSON(response, new AjaxResult(0, "处理成功!"));
		} catch (Exception e) {
			e.printStackTrace();
			JSONUtil.responseJSON(response, new AjaxResult(1, "处理失败!"));
		}

	}

	// 加载保洁记录页面
	@RequestMapping("/loadcleanrecord.do")
	public ModelAndView loadcleanrecord(HttpServletRequest request)
			throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/ipmspage/leftmenu/handleapply/loadrecord");
		return mv;
	}

	// 加载所有记录
	@RequestMapping("/querycleanrecord.do")
	public ModelAndView querycleanrecord(HttpServletRequest request)
			throws Exception {
		ModelAndView mv = new ModelAndView();
		Pagination pagination = SqlBuilder.buildPagination(request);
		LoginUser loginuser = (LoginUser) request.getSession(true)
				.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginuser.getStaff().getBranchId();
		List<?> record = CleanService.queryAllOfRecord(branchid, pagination);
		mv.setViewName("page/ipmspage/leftmenu/cleanrecord/recorddata");
		mv.addObject("pagination", pagination);
		mv.addObject("record", record);
		return mv;
	}

	// 带条件查询保洁记录
	@RequestMapping("/querycleanrecordcondition.do")
	public ModelAndView querycleanrecordcondition(HttpServletRequest request,
			String time, String status) throws Exception {
		ModelAndView mv = new ModelAndView();
		Pagination pagination = SqlBuilder.buildPagination(request);
		LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchid = loginuser.getStaff().getBranchId();
		List<?> recorddataCondition = CleanService.queryRecordByCondition(
				branchid, time, status, pagination);
		mv.setViewName("page/ipmspage/leftmenu/cleanrecord/recorddatacondition");
		mv.addObject("recorddataCondition", recorddataCondition);
		return mv;
	}

	// 处理保洁记录状态
	@RequestMapping("/updaterecordstatus.do")
	public void updaterecordstatus(HttpServletRequest request,
			HttpServletResponse response, String recordid) throws Exception {
		CleanRecord cleanRecord = (CleanRecord) CleanService
				.findOneByProperties(CleanRecord.class, "recordId", recordid);
		cleanRecord.setStatus("1");
		try {
			CleanService.save(cleanRecord);
			JSONUtil.responseJSON(response, new AjaxResult(1, "处理成功！"));
		} catch (Exception e) {
			e.printStackTrace();
			JSONUtil.responseJSON(response, new AjaxResult(2, "处理失败！"));

		}
	}

	// 剩余次数查询
	@RequestMapping("/queryrest.do")
	public void checkrest(HttpServletRequest request,
			HttpServletResponse response, String cleanTime, String timeArea)
			throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date cleandate = sdf.parse(cleanTime);
		if ("上午".equals(timeArea)) {
			timeArea = "0";
		} else {
			timeArea = "1";
		}
		Clean clean = (Clean) CleanService.findOneByProperties(Clean.class,
				"cleanDate", cleandate, "timeArea", timeArea);
		int count = clean.getRestAmount();
		JSONUtil.responseJSON(response, new AjaxResult(1, "", count));
	}
	
	
	//pms新增申請
	@RequestMapping("/addnewapply.do")
	public ModelAndView addnewapply(HttpServletRequest request)
			throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/ipmspage/leftmenu/handleapply/addnewapply");
		return mv;
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/getallroom.do")
	public void getroom(HttpServletRequest request, HttpServletResponse response)throws Exception{
		LoginUser loginuser = (LoginUser) request.getSession(true)
		.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
        String branchid = loginuser.getStaff().getBranchId();
		String sqltype = "clean_getallroomtype";
		String sqlroomidbytype = "clean_getallroomid";
		List<Map<String ,String>> allroomtype = CleanService.findBySQL(sqltype,true );
		Map<String, List<Map<String,String>>> resultallroomid = new HashMap<String, List<Map<String,String>>>();
		for(Map<String ,String> map :allroomtype){
			String roomtype = map.get("ROOMTYPE");
			String roomname = map.get("ROOMNAME");
			List<Map<String,String>> roomidandType = CleanService.findBySQL(sqlroomidbytype, new String[]{branchid,roomtype},true);
				resultallroomid.put(roomname, roomidandType);	
		}
		//System.out.println(resultallroomid);
		JSONUtil.responseJSON(response, resultallroomid);
		}
	
		@SuppressWarnings("unchecked")
		@RequestMapping("/submitnewcleanapplication.do")
		public void  submitnewcleanapplication(HttpServletRequest request,HttpServletResponse response,String roomid,String contrartid, String reservedperson, String mobile,
				String cleantime, String  timeArea,String cleanstyle ,String remark,String memberId)throws Exception{
			LoginUser loginuser = (LoginUser) request.getSession(true)
			.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
	        String branchid = loginuser.getStaff().getBranchId();
	        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
	        SimpleDateFormat sdf2 = new SimpleDateFormat("yy/MM/dd");
	        Date cleanDate = sdf2.parse(cleantime);
	        Date today = new Date();
	        String strdate =  sdf.format(today);
	       //System.out.println(strdate);
	        Clean clean = (Clean) CleanService.findOneByProperties(Clean.class,
					"cleanDate", cleanDate, "timeArea", timeArea);
			int restamount = clean.getRestAmount();
	        
			List<Map<String ,String>> notdealamount = CleanService.findBySQL("findrestamount_pms", new String[]{cleantime,timeArea,branchid}, true);
	        int statusamount = Integer.parseInt(((Map<String ,String>)notdealamount.get(0)).get("NOTDEALAMOUNT").toString());
	        if(restamount - statusamount > 0 ){
	        	CleanApply cleanapply = new CleanApply();
				cleanapply.setCleanApplyId(strdate+branchid+CleanService.getSequence("SEQ_CLEANAPPLY_ID"));
				cleanapply.setBranchId(branchid);
	     		cleanapply.setContractId(contrartid);
				cleanapply.setCleanTime(cleanDate);
				cleanapply.setCleanStyle(cleanstyle);
				cleanapply.setRoomId(roomid);
				cleanapply.setReservedPerson(memberId);
				cleanapply.setMobile(mobile);
				cleanapply.setStatus("1");
				cleanapply.setApplicationTime(today);
				cleanapply.setRecordTime(today);
				cleanapply.setRemark(remark);
				cleanapply.setTimeArea(timeArea);
				try{
					CleanService.save(cleanapply);
					JSONUtil.responseJSON(response, new AjaxResult(1, "保洁申请成功！"));
				}catch(Exception e){
					e.printStackTrace();
					JSONUtil.responseJSON(response, new AjaxResult(2, "保洁申请失败！"));
				}
	        }else{
	        	
	        	JSONUtil.responseJSON(response, new AjaxResult(3, "保洁申请已达上限！"));
	        }
			
		}
		
		
		
		//查询保洁申请将数据刷到页面
			
			@SuppressWarnings("unchecked")
			@RequestMapping("/querycleanCondition.do")
			public ModelAndView queryinfo(HttpServletRequest request, HttpServletResponse response,String time) throws ParseException{
				ModelAndView mv = new ModelAndView();
				LoginUser loginuser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
				String branchId = loginuser.getStaff().getBranchId();
				String sql5 = "findcleaninforecord";
				Map<String, Map<String, CleanInfo>> maps = new HashMap<String, Map<String, CleanInfo>>();
				Map<String, CleanInfo> map = null;
				List<CleanInfo> list = new ArrayList<CleanInfo>();
				//查出预排表日期用于遍历
				List<Clean> dateFromClean = CleanService.findBySQL("findcleandate",new String[]{branchId,time}, true);
				
				//查申请表日期和timearea用作map
				//List<Map<String,String>> dateFromCleanApply = CleanService.findBySQL("findcleanapplydate",new String[]{branchId,time}, true);
				List<Map<String,String>> dateFromCleanApply = CleanService.findBySQL("findcleandate",new String[]{branchId,time}, true);
				for(Map<String,String> map1: dateFromCleanApply){
					String cleanapply_cleantime = map1.get("CLEANTIME");//日期
					
					//获取周几
					SimpleDateFormat sdf = new SimpleDateFormat("EEEE",Locale.CHINA);
					SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");
					
					Date date = sdf2.parse(cleanapply_cleantime);
					String weekday = sdf.format(date);
					for(int i = 0; i<=1;i++){
						CleanInfo cleanInfo = new CleanInfo();
						List<Object> cleaninfolist = CleanService.findBySQL(sql5,new String[]{branchId,cleanapply_cleantime,Integer.toString(i)}, true);				
						cleanInfo.setCleanPerson(((Map<String,String>)cleaninfolist.get(0)).get("CLEANPERSON").toString());
						cleanInfo.setRoomId(((Map<String,String>)cleaninfolist.get(0)).get("ROOMID").toString());
						cleanInfo.setCleanTime(cleanapply_cleantime);
						cleanInfo.setTimeArea(Integer.toString(i));
						cleanInfo.setRestAmount(((Map<String,String>)cleaninfolist.get(0)).get("RESTAMOUNT").toString());
						cleanInfo.setWeekday(weekday);
						list.add(cleanInfo);
					}
				}
				for(int k=0;k<list.size();k++){
//					if(null != maps.get(list.get(k).getCleanTime())){
//						//map = maps.get(list.get(k).getCleanTime());
//					}else{
//						map = new HashMap<String, CleanInfo>();
//						maps.put(list.get(k).getCleanTime(), map);
//					}
//					map.put(list.get(k).getTimeArea().toString(), list.get(k));
					if(null == maps.get(list.get(k).getCleanTime())){
						map = new HashMap<String, CleanInfo>();
						maps.put(list.get(k).getCleanTime(), map);
						
					}
					map.put(list.get(k).getTimeArea().toString(), list.get(k));
				}
				System.out.println(maps);
				mv.addObject("dateFromClean",dateFromClean);
				mv.addObject("maps",maps);
				mv.setViewName("/page/ipmspage/leftmenu/handleapply/loadcleanrecord");
				return mv;
			}
			
			
	//取消保洁申请
			@RequestMapping("/canclecleanapply")
			public void canclecleanapply(HttpServletRequest request,
					HttpServletResponse response, String cleanApplicationId)
					throws Exception {
				CleanApply cleanApply = (CleanApply) CleanService.findOneByProperties(CleanApply.class, "cleanApplyId", cleanApplicationId);
				cleanApply.setStatus("0");
				try {
					CleanService.update(cleanApply);
					JSONUtil.responseJSON(response, new AjaxResult(0, "申请撤销成功!"));
				} catch (Exception e) {
					e.printStackTrace();
					JSONUtil.responseJSON(response, new AjaxResult(1, "申请撤销失败!"));
				}

			}
	
}
