package com.ideassoft.pms.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ideassoft.bean.Bill;
import com.ideassoft.bean.Check;
import com.ideassoft.bean.HaltPlan;
import com.ideassoft.bean.Room;
import com.ideassoft.bean.Staff;
import com.ideassoft.bean.wrapper.MultiQueryHalt;
import com.ideassoft.core.ajax.AjaxResult;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.jdbc.SqlBuilder;
import com.ideassoft.core.page.Pagination;
import com.ideassoft.pms.service.RoomService;
import com.ideassoft.pms.service.SellService;
import com.ideassoft.util.DateUtil;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.RequestUtil;

@Transactional
@Controller
public class SellController {
	@Autowired
	private SellService sellService;
	
	@Autowired
	private RoomService roomService;
	
	@RequestMapping("/showstophouse.do")
	public ModelAndView showgetAvailableRoom(HttpServletRequest request, MultiQueryHalt multiqueryhalt) throws Exception{
		LoginUser loginuser = (LoginUser) request.getSession(true)
		.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String branchId = staff.getBranchId();
		ModelAndView mv = new ModelAndView();
		String sql = "loadhaltroom";
		Pagination pagination = SqlBuilder.buildPagination(request);
		List<?> result = sellService.findBySQLWithPagination(sql, new String[]{multiqueryhalt.getLogid(), branchId, multiqueryhalt.getRoomid(),multiqueryhalt.getHalttype(),multiqueryhalt.getHaltreason(),multiqueryhalt.getStarttime(),multiqueryhalt.getEndtime(),multiqueryhalt.getStatus()}, pagination, true);
		mv.addObject("result", JSONUtil.fromObject(result));
		mv.addObject("multiqueryhalt", multiqueryhalt);
		mv.addObject("pagination", pagination);
		mv.setViewName("/page/ipmspage/stophouse/stopHousedata");
		return mv;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/submithaltpan.do")
	public void submithaltpan(HttpServletRequest request,HttpServletResponse response, String roomId, String haltType, String haltReason, String startTime,
								String endTime, String remark) throws Exception{
		LoginUser loginuser = (LoginUser) request.getSession(true)
		.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginuser.getStaff();
		String branchId = staff.getBranchId();
		String logId = DateUtil.currentDate("yyMMdd") + branchId + sellService.getSequence("SEQ_NEW_HALTPLAN");
		HaltPlan haltplan = new HaltPlan();
		haltplan.setLogId(logId);
		haltplan.setBranchId(branchId);
		haltplan.setRoomId(roomId);
		haltplan.setHaltType(haltType);
		haltplan.setHaltReason(haltReason);
		haltplan.setStartTime(DateUtil.s2d(startTime, "yyyy/MM/dd"));
		haltplan.setEndTime(DateUtil.s2d(endTime, "yyyy/MM/dd"));
		haltplan.setStatus("1");
		haltplan.setRecordUser(staff.getStaffId());
		haltplan.setRemark(remark);
		List<Map<String, String>> counts = (List<Map<String, String>>) sellService.queryroomcounts(roomId, branchId);
		try{
			if(counts.size() == 0){//判断是否有这个房间
				JSONUtil.responseJSON(response, new AjaxResult(0, "无此房号!"));
			}else {
				//Room room = roomService.selectroomstatus(branchId, roomId);
				Room room = (Room) sellService.findOneByProperties(Room.class, "roomKey.branchId", branchId, "roomKey.roomId", roomId);
				if("1".equals(room.getRoomKey().getStatus()) || "2".equals(room.getRoomKey().getStatus())){
					JSONUtil.responseJSON(response, new AjaxResult(0, "房间正在使用!"));	
				}else{
					//更改房间状态
					String status = (String) counts.get(0).get("STATUS");
					if("T".equals(status) || "W".equals(status)){
						JSONUtil.responseJSON(response, new AjaxResult(0, roomId + "号已列入停售计划!"));
					}else{
						sellService.save(haltplan);
						roomService.upateroomstatus(branchId, roomId, "1".equals(haltType)?"T":"W");
						JSONUtil.responseJSON(response, new AjaxResult(1, "添加成功!"));	
					}
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		
	}
	
	@RequestMapping("/cancelhaltplan.do")
	public void cancelhaltplan(HttpServletRequest request, HttpServletResponse response,String  logid) throws Exception{
		HaltPlan haltplan = (HaltPlan) roomService.findOneByProperties(HaltPlan.class, "logId", logid);
		haltplan.setStatus("0");
		try{
			roomService.save(haltplan);
			JSONUtil.responseJSON(response, new AjaxResult(1, "取消成功!"));	
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/updatehaltpan.do")
	public void updatehaltpan(HttpServletRequest request, HttpServletResponse response, HaltPlan haltplan, String strstartTime, String strendTime, String strrecordTime) throws Exception{
		//HaltPlan haltplandata = (HaltPlan) roomService.findOneByProperties(HaltPlan.class, "logId", haltplan.getLogId());
		haltplan.setStartTime(DateUtil.s2d(strstartTime, "yyyy/MM/dd"));
		haltplan.setEndTime(DateUtil.s2d(strendTime, "yyyy/MM/dd"));
		haltplan.setRecordTime(DateUtil.s2d(strrecordTime, "yyyy-MM-dd HH:mm:ss"));
		try{
			roomService.update(haltplan);
			JSONUtil.responseJSON(response, new AjaxResult(1, "修改成功!"));	
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
