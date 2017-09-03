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
import com.ideassoft.bean.CleanInfo;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.pms.service.CleanService;
import com.ideassoft.util.RequestUtil;



@Transactional
@Controller
public class CleanApplicationController2 {
	@Autowired
	private CleanService CleanService;
	
	
//查询保洁申请将数据刷到页面
	
	@SuppressWarnings("unchecked")
	//@RequestMapping("/querycleanCondition.do")
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
//			if(null != maps.get(list.get(k).getCleanTime())){
//				//map = maps.get(list.get(k).getCleanTime());
//			}else{
//				map = new HashMap<String, CleanInfo>();
//				maps.put(list.get(k).getCleanTime(), map);
//			}
//			map.put(list.get(k).getTimeArea().toString(), list.get(k));
			if(null == maps.get(list.get(k).getCleanTime())){
				map = new HashMap<String, CleanInfo>();
				maps.put(list.get(k).getCleanTime(), map);
				
			}
			map.put(list.get(k).getTimeArea().toString(), list.get(k));
		}
		//System.out.println(maps);
		mv.addObject("dateFromClean",dateFromClean);
		mv.addObject("maps",maps);
		mv.setViewName("/page/ipmspage/leftmenu/handleapply/loadcleanrecord");
		return mv;
	}
		
}
