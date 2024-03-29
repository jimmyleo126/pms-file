package com.ideassoft.pms.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hsqldb.lib.StringUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ideassoft.bean.RoomStatus;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.portal.DataDealPortalService;
import com.ideassoft.portal.IDataDealPortal;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.RequestUtil;

@Transactional
@Controller
public class RoomStatusController {
	
	@RequestMapping("cityroomstatus.do")
	public ModelAndView cityroomstatus(HttpServletRequest request, HttpServletResponse response) throws ParseException{
		ModelAndView mv = new ModelAndView();
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branch =  loginUser.getStaff().getBranchId();
		String branchId = request.getParameter("branchId");
		DataDealPortalService service=new DataDealPortalService ();
		IDataDealPortal portal =service.getDataDealPortalPort();
		if(StringUtil.isEmpty(branchId)){
			String branchIds=portal.call(1, 1, "{ function: \"roomStatus.queryBranch\", data:{branch:"+branch+" } }");
			try {
				if(!branchIds.equals("-1")){
					JSONArray jsonArray = new JSONArray(branchIds);
					JSONObject object=null;
					branchId = "";
					for(int i = 0;i < jsonArray.length();i++){
						object=jsonArray.getJSONObject(i);
						branchId += " " + object.getString("BRANCH_ID");
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		String data=portal.call(1, 1, "{ function: \"roomStatus.queryRoom\", data:{branchId:"+branchId+" } }");
		String room=portal.call(1, 1, "{ function: \"roomStatus.roomSum\", data:{branchId:"+branchId+" } }");
		List<RoomStatus> list = new ArrayList<RoomStatus>();
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> roomName = new HashMap<String, String>();
		try {
			if(!data.equals("-1")){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
				JSONArray jsonArray = new JSONArray(data);
				JSONObject object=null;
				for(int i = 0;i < jsonArray.length();i++){
					object=jsonArray.getJSONObject(i);
					RoomStatus roomstatus = new RoomStatus();
					roomstatus.setLogId(object.getString("LOG_ID"));
					roomstatus.setBranchId(object.getString("BRANCH_ID"));
					roomstatus.setBranchName(object.getString("BRANCH_NAME"));
					roomstatus.setRoomType(object.getString("ROOM_TYPE"));
					roomstatus.setCount(Integer.parseInt(object.getString("COUNT")));
					roomstatus.setRecordTime(sdf.parse(object.getString("RECORD_TIME")));
					roomstatus.setRecordUser(object.getString("RECORD_USER"));
					roomstatus.setRemark(object.getString("REMARK").equals("null") ? "" : object.getString("REMARK"));
					roomstatus.setCampaigns(object.getString("CAMPAIGNS").equals("null") ? 0 : Integer.parseInt(object.getString("CAMPAIGNS")));
					roomstatus.setSellnum(object.getString("SELLNUM").equals("null") ? 0 : Integer.parseInt(object.getString("SELLNUM")));
					roomstatus.setStopnum(object.getString("STOPNUM").equals("null") ? 0 : Integer.parseInt(object.getString("STOPNUM")));
					roomstatus.setNightnum(object.getString("NIGHTNUM").equals("null") ? 0 : Integer.parseInt(object.getString("NIGHTNUM")));
					roomstatus.setValidnum(object.getString("VALIDNUM").equals("null") ? 0 : Integer.parseInt(object.getString("VALIDNUM")));
					roomstatus.setInvalidnum(object.getString("INVALIDNUM").equals("null") ? 0 : Integer.parseInt(object.getString("INVALIDNUM")));
					roomstatus.setRoomPrice(object.getString("ROOM_PRICE").equals("null") ? "" : object.getString("ROOM_PRICE"));
					list.add(roomstatus);
				}
				mv.addObject("list", list);
			}
			if(!room.equals("-1")){
				JSONArray jsonArray = new JSONArray(room);
				JSONObject object=null;
				for(int i = 0;i < jsonArray.length();i++){
					object=jsonArray.getJSONObject(i);
					map.put(object.getString("BRANCH_ID"), object.getString("SUM"));
					roomName.put(object.getString("BRANCH_NAME"), object.getString("BRANCH_ID"));
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		mv.setViewName("page/ipmspage/room_urban/cityroomstatus");
		mv.addObject("map",JSONUtil.fromObject(map));
		mv.addObject("roomName",roomName);
		return mv;
	}
	
	@RequestMapping("queryRoom.do")
	public ModelAndView queryRoom(HttpServletRequest request, HttpServletResponse response){
		ModelAndView mv = new ModelAndView();
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String branch =  loginUser.getStaff().getBranchId();
		DataDealPortalService service=new DataDealPortalService ();
		IDataDealPortal portal =service.getDataDealPortalPort();
		String branchIds=portal.call(1, 1, "{ function: \"roomStatus.queryBranch\", data:{branch:"+branch+" } }");
		String branchId = "";
		try {
			if(!branchIds.equals("-1")){
				JSONArray jsonArray = new JSONArray(branchIds);
				JSONObject object=null;
				branchId = "";
				for(int i = 0;i < jsonArray.length();i++){
					object=jsonArray.getJSONObject(i);
					branchId += " " + object.getString("BRANCH_ID");
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		String room=portal.call(1, 1, "{ function: \"roomStatus.roomSum\", data:{branchId:"+branchId+" } }");
		Map<String, String> roomName = new HashMap<String, String>();
		if(!room.equals("-1")){
			try {
				if(!room.equals("-1")){
					JSONArray jsonArray = new JSONArray(room);
					JSONObject object=null;
					for(int i = 0;i < jsonArray.length();i++){
						object=jsonArray.getJSONObject(i);
						roomName.put(object.getString("BRANCH_NAME"), object.getString("BRANCH_ID"));
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		mv.setViewName("page/ipmspage/room_urban/room_urban");
		mv.addObject("roomName",roomName);
		return mv;
	} 
	
}
