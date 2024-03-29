package com.ideassoft.pms.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.page.Pagination;
import com.ideassoft.pms.service.ApartmentRentService;
import com.ideassoft.util.ReflectUtil;
import com.ideassoft.util.RequestUtil;

@Transactional
@Controller
public class ApartmentRentController {
	
	@Autowired
	private ApartmentRentService apartmentRentService;
	
	@RequestMapping("/apartmentRent.do")
	public ModelAndView apartmentRent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/ipmspage/leftmenu/apartmentrent/apartmentrent");
		return mv;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/queryApartmentRent.do")
	public ModelAndView queryApartmentRent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branchId = loginUser.getStaff().getBranchId();
		Pagination pagination = (Pagination) ReflectUtil.setBeanFromRequest(request, Pagination.class);
		if(pagination != null && pagination.getPageNum() == null){
			pagination.setPageNum(1);
			pagination.setRows(17);
		}
		String memberName = request.getParameter("memberName");
		String roomId = request.getParameter("roomId");
		String typeofpayment = request.getParameter("typeofpayment");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String date = sdf.format(new Date());
		Map<String, Map<String, String>> mapTime = new HashMap<String, Map<String, String>>();
		Map<String, Map<String, String>> rentMap = new HashMap<String, Map<String, String>>();
		Map<String, String> map = null;
		List<Object> list = apartmentRentService.findBySQLWithPagination("contractRent",new String[]{branchId, roomId, memberName, typeofpayment}, pagination, true);
		for(Object object : list){
			int startMonth = 1;
			int endMonth = 12;
			String rentTime = ((Map<?, ?>)object).get("CONTRARTENDTIME").toString();
			String startTime = ((Map<?, ?>)object).get("STARTTIME").toString();
			String endTime = ((Map<?, ?>)object).get("ENDTIME").toString();
			String[] rentTimes = rentTime.split("/");
			String[] startTimes = startTime.split("/");
			String[] endTimes = endTime.split("/");
			map = new HashMap<String, String>();
			if(Integer.parseInt(rentTimes[1]) < 10){
				rentTime = rentTimes[1].replace("0", "");
			}else{
				rentTime = rentTimes[1];
			}
			if(startTimes[0].equals(date)){
				startMonth = Integer.parseInt(startTimes[1]);
				for(int s = 1; s < startMonth; s++){
					map.put(Integer.toString(s), "2");
				}
			}
			if(endTimes[0].equals(date)){
				endMonth = Integer.parseInt(endTimes[1]);
				for(int e = endMonth; e <= 12; e++){
					map.put(Integer.toString(e), "2");
				}
			}
			for(int j = startMonth; j <= endMonth; j++){
				if(j < Integer.parseInt(rentTime)){
					map.put(Integer.toString(j), "0");
				}else{
					map.put(Integer.toString(j), "1");
				}
			}
			mapTime.put(((Map<?, ?>)object).get("CONTRARTID").toString(), map);
			rentMap.put(((Map<?, ?>)object).get("CONTRARTID").toString(), rentTiem(startTime, endTime, date, ((Map<?, ?>)object).get("TYPEOFPAYMENT").toString()));
		}
		mv.addObject("list", list);
		mv.addObject("memberName", memberName);
		mv.addObject("roomId", roomId);
		mv.addObject("contrart_end_time", typeofpayment);
		mv.addObject("mapTime", mapTime);
		mv.addObject("rentMap", rentMap);
		mv.addObject("pagination", pagination);
		mv.setViewName("page/ipmspage/leftmenu/apartmentrent/apartmentrentinfo");
		return mv;                                                           
	}
	
	public Map<String, String> rentTiem(String startTime, String endTime, String date, String num) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Map<String, String> map = new HashMap<String, String>();
		String[] startTimes = startTime.split("/");
		String[] endTimes = endTime.split("/");
		int startMonth = 1;
		int endMonth = 12;
		String key = "";
		if(Integer.parseInt(startTimes[0]) < Integer.parseInt(date)){
			for(int i=Integer.parseInt(startTimes[1]);i <= 12;i++){
				String[] starts = startTime.split("/");
				Calendar cl = Calendar.getInstance();  
		        cl.setTime(sdf.parse(startTime));
		        cl.add(Calendar.MONTH, Integer.parseInt(num));
		        startMonth = Integer.parseInt(starts[1]);
		        if(Integer.parseInt(starts[1]) < 10){
		        	key = starts[1].replace("0", "");
				}else{
					key = starts[1];
				}
		        map.put(key, starts[2]);
		        if(starts[0].equals(date))break;
		        startTime = sdf.format(cl.getTime());
			}
		}
		if(startTimes[0].equals(date)){
			startMonth = Integer.parseInt(startTimes[1]);
		}
		if(endTimes[0].equals(date)){
			endMonth = Integer.parseInt(endTimes[1]);
		}
		for(int j = startMonth ;j < endMonth; j++){
			if(Integer.parseInt(startTimes[1]) < 10){
				map.put(startTimes[1].replace("0", ""), startTimes[2]);
			}else{
				map.put(startTimes[1], startTimes[2]);
			}
			Calendar cl = Calendar.getInstance();
	        cl.setTime(sdf.parse(startTime));
	        cl.add(Calendar.MONTH, Integer.parseInt(num));
	        startTime = sdf.format(cl.getTime());
	        String rentTime[] = startTime.split("/");
        	if(Integer.parseInt(rentTime[0]) > Integer.parseInt(date))break;
        	if(Integer.parseInt(rentTime[1]) < 10){
	        	key = rentTime[1].replace("0", "");
			}else{
				key = rentTime[1];
			}
        	map.put(key, rentTime[2]);
		}
		return map;
	}
	
}
