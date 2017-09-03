package com.ideassoft.pms.task;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ideassoft.bean.Campaign;
import com.ideassoft.bean.Check;
import com.ideassoft.bean.ExceptionMessage;
import com.ideassoft.bean.MemberAccount;
import com.ideassoft.bean.Order;
import com.ideassoft.bean.RoomStatus;
import com.ideassoft.core.constants.SystemConstants.ProjectName;
import com.ideassoft.core.factory.ServiceFactory;
import com.ideassoft.core.task.ScheduledTask;
import com.ideassoft.pms.service.DailyService;

public class TimingTask extends ScheduledTask implements ProjectName {
	
	private final Logger logger = Logger.getLogger(TimingTask.class);
	
	private static DailyService dailyService = (DailyService) ServiceFactory.getService("dailyService");
    public TimingTask(String name) {
		super(name);
	}
    
    @SuppressWarnings("unchecked")
	public void run() {  
        try {
        	//刷新房间数量
        	List<Object> list = dailyService.findBySQL("sumroom",true);
        	List<Object> start_campaigns_room = dailyService.findBySQL("startCampaignsRoom", true);//特惠房开始
        	List<Object> end_campaigns_room = dailyService.findBySQL("endCampaignsRoom", true);//特惠房结束
        	String branchId = ((Map<?, ?>)(list.get(0))).get("BRANCH_ID").toString();
        	List<Object> haltplanRoom = dailyService.findBySQL("haltplanRoom", new String[]{branchId}, true);//停售房
    		List<Object> validOrder = dailyService.findBySQL("validOrder", new String[]{branchId}, true);//有效订单
    		List<Object> invalidOrder = dailyService.findBySQL("invalidOrder", new String[]{branchId}, true);//有效订单
    		Map<String, String> start_campaigns_rooms = new HashMap<String, String>();
    		Map<String, String> end_campaigns_rooms = new HashMap<String, String>();
    		Map<String, String> haltplanRooms = new HashMap<String, String>();
    		Map<String, String> validOrders = new HashMap<String, String>();
    		Map<String, String> invalidOrders = new HashMap<String, String>();
    		//特惠房活动开始
    		if(start_campaigns_room.size() > 0){
    			for(Object campaigns : start_campaigns_room){
    				start_campaigns_rooms.put(((Map<?, ?>)campaigns).get("ROOM_TYPE").toString(), ((Map<?, ?>)campaigns).get("EXPERIENCE_COUNT").toString());
    			}
    		}
    		//特惠房活动结束
    		if(end_campaigns_room.size() > 0){
    			for(Object campaigns : end_campaigns_room){
    				end_campaigns_rooms.put(((Map<?, ?>)campaigns).get("ROOM_TYPE").toString(), ((Map<?, ?>)campaigns).get("EXPERIENCE_COUNT").toString());
    			}
    		}
    		//停售房
    		if(haltplanRoom.size() > 0){
    			for(Object campaigns : haltplanRoom){
    				haltplanRooms.put(((Map<?, ?>)campaigns).get("ROOM_TYPE").toString(), ((Map<?, ?>)campaigns).get("SUM").toString());
    			}
    		}
    		//有效订单
    		if(validOrder.size() > 0){
    			for(Object campaigns : validOrder){
    				validOrders.put(((Map<?, ?>)campaigns).get("ROOM_TYPE").toString(), ((Map<?, ?>)campaigns).get("SUM").toString());
    			}
    		}
    		//无效订单
    		if(invalidOrder.size() > 0){
    			for(Object campaigns : invalidOrder){
    				invalidOrders.put(((Map<?, ?>)campaigns).get("ROOM_TYPE").toString(), ((Map<?, ?>)campaigns).get("SUM").toString());
    			}
    		}
        	if(list.size() > 0 ){
        		Map<String, String> types = new HashMap<String, String>();
        		for(Object type : list){
        			types.put(((Map<?, ?>)type).get("ROOM_TYPE").toString(), ((Map<?, ?>)type).get("SUM").toString());
        		}
        		if(!start_campaigns_rooms.isEmpty()){
        			for(int i = 0; i < list.size(); i++){
        				int num = dailyService.findByProperties(Check.class, "roomType", ((Map<?, ?>)list.get(i)).get("ROOM_TYPE"), "status", "1").size();
            			RoomStatus roomstatus = (RoomStatus) dailyService.findOneByProperties(RoomStatus.class, "roomType", ((Map<?, ?>)list.get(i)).get("ROOM_TYPE"));
            			roomstatus.setCount(Integer.parseInt(types.get(((Map<?, ?>)list.get(i)).get("ROOM_TYPE"))) - num);
            			roomstatus.setSellnum(Integer.parseInt(types.get(((Map<?, ?>)list.get(i)).get("ROOM_TYPE"))) - num);
            			if(start_campaigns_rooms.get(((Map<?, ?>)list.get(i)).get("ROOM_TYPE")) != null){
            				roomstatus.setCampaigns(Integer.parseInt(start_campaigns_rooms.get(((Map<?, ?>)list.get(i)).get("ROOM_TYPE"))));
            			}
            			if(validOrders.get(((Map<?, ?>)list.get(i)).get("ROOM_TYPE")) != null){
            				roomstatus.setValidnum(Integer.parseInt(validOrders.get(((Map<?, ?>)list.get(i)).get("ROOM_TYPE"))));
            				roomstatus.setSellnum(roomstatus.getSellnum() - Integer.parseInt(validOrders.get(((Map<?, ?>)list.get(i)).get("ROOM_TYPE"))));
            			}
            			if(invalidOrders.get(((Map<?, ?>)list.get(i)).get("ROOM_TYPE")) != null){
            				roomstatus.setValidnum(Integer.parseInt(invalidOrders.get(((Map<?, ?>)list.get(i)).get("ROOM_TYPE"))));
            			}
            			if(haltplanRooms.get(((Map<?, ?>)list.get(i)).get("ROOM_TYPE")) != null){
            				roomstatus.setValidnum(Integer.parseInt(haltplanRooms.get(((Map<?, ?>)list.get(i)).get("ROOM_TYPE"))));
            				roomstatus.setSellnum(roomstatus.getSellnum() - Integer.parseInt(haltplanRooms.get(((Map<?, ?>)list.get(i)).get("ROOM_TYPE"))));
            			}
            			if(!end_campaigns_rooms.isEmpty()){
            				if(end_campaigns_rooms.get(((Map<?, ?>)list.get(i)).get("ROOM_TYPE")) != null){
                				roomstatus.setCampaigns(0);
                			}
            			}
            			dailyService.update(roomstatus);
            		}
        		}else if(!end_campaigns_rooms.isEmpty()){
        			for(int i = 0; i < list.size(); i++){
        				int num = dailyService.findByProperties(Check.class, "roomType", ((Map<?, ?>)list.get(i)).get("ROOM_TYPE"), "status", "1").size();
            			RoomStatus roomstatus = (RoomStatus) dailyService.findOneByProperties(RoomStatus.class, "roomType", ((Map<?, ?>)list.get(i)).get("ROOM_TYPE"));
            			roomstatus.setCount(Integer.parseInt(types.get(((Map<?, ?>)list.get(i)).get("ROOM_TYPE"))) - num);
            			roomstatus.setSellnum(Integer.parseInt(types.get(((Map<?, ?>)list.get(i)).get("ROOM_TYPE"))) - num);
            			if(!end_campaigns_room.isEmpty()){
            				if(end_campaigns_rooms.get(((Map<?, ?>)list.get(i)).get("ROOM_TYPE")) != null){
                				roomstatus.setCampaigns(0);
                			}
            			}
            			if(validOrders.get(((Map<?, ?>)list.get(i)).get("ROOM_TYPE")) != null){
            				roomstatus.setValidnum(Integer.parseInt(validOrders.get(((Map<?, ?>)list.get(i)).get("ROOM_TYPE"))));
            				roomstatus.setSellnum(roomstatus.getSellnum() - Integer.parseInt(validOrders.get(((Map<?, ?>)list.get(i)).get("ROOM_TYPE"))));
            			}
            			if(invalidOrders.get(((Map<?, ?>)list.get(i)).get("ROOM_TYPE")) != null){
            				roomstatus.setValidnum(Integer.parseInt(invalidOrders.get(((Map<?, ?>)list.get(i)).get("ROOM_TYPE"))));
            			}
            			if(haltplanRooms.get(((Map<?, ?>)list.get(i)).get("ROOM_TYPE")) != null){
            				roomstatus.setValidnum(Integer.parseInt(haltplanRooms.get(((Map<?, ?>)list.get(i)).get("ROOM_TYPE"))));
            				roomstatus.setSellnum(roomstatus.getSellnum() - Integer.parseInt(haltplanRooms.get(((Map<?, ?>)list.get(i)).get("ROOM_TYPE"))));
            			}
            			dailyService.update(roomstatus);
            		}
    			}else{
    				for(int i = 0; i < list.size(); i++){
        				int num = dailyService.findByProperties(Check.class, "roomType", ((Map<?, ?>)list.get(i)).get("ROOM_TYPE"), "status", "1").size();
            			RoomStatus roomstatus = (RoomStatus) dailyService.findOneByProperties(RoomStatus.class, "roomType", ((Map<?, ?>)list.get(i)).get("ROOM_TYPE"));
            			roomstatus.setCount(Integer.parseInt(types.get(((Map<?, ?>)list.get(i)).get("ROOM_TYPE")))-num);
            			roomstatus.setSellnum(Integer.parseInt(types.get(((Map<?, ?>)list.get(i)).get("ROOM_TYPE"))) - num);
            			if(validOrders.get(((Map<?, ?>)list.get(i)).get("ROOM_TYPE")) != null){
            				roomstatus.setValidnum(Integer.parseInt(validOrders.get(((Map<?, ?>)list.get(i)).get("ROOM_TYPE"))));
            				roomstatus.setSellnum(roomstatus.getSellnum() - Integer.parseInt(validOrders.get(((Map<?, ?>)list.get(i)).get("ROOM_TYPE"))));
            			}
            			if(invalidOrders.get(((Map<?, ?>)list.get(i)).get("ROOM_TYPE")) != null){
            				roomstatus.setValidnum(Integer.parseInt(invalidOrders.get(((Map<?, ?>)list.get(i)).get("ROOM_TYPE"))));
            			}
            			if(haltplanRooms.get(((Map<?, ?>)list.get(i)).get("ROOM_TYPE")) != null){
            				roomstatus.setValidnum(Integer.parseInt(haltplanRooms.get(((Map<?, ?>)list.get(i)).get("ROOM_TYPE"))));
            				roomstatus.setSellnum(roomstatus.getSellnum() - Integer.parseInt(haltplanRooms.get(((Map<?, ?>)list.get(i)).get("ROOM_TYPE"))));
            			}
            			dailyService.update(roomstatus);
            		}
    			}
    		}
        	if(end_campaigns_room.size() > 0){
    			for(Object campaigns : end_campaigns_room){
    				Campaign campaign = (Campaign) dailyService.findById(Campaign.class, ((Map<?, ?>)campaigns).get("DATA_ID").toString());
    				campaign.setStatus("2");
    				dailyService.update(campaign);
    			}
    		}
        	//NoShow异常
        	List<Order> orders = dailyService.findBySQL("orderStatus", true);
    		if(orders.size() > 0){
    			for(int i=0;i<orders.size();i++){
    				if(((Map<?, ?>)orders.get(i)).get("ADVANCE_CASH") == null || ((Map<?, ?>)orders.get(i)).get("ADVANCE_CARD") == null){
    					Date date = new Date();
    					Order order = (Order) dailyService.findOneByProperties(Order.class, "orderId", ((Map<?, ?>)orders.get(i)).get("ORDER_ID"));
    					order.setStatus("0");
    					MemberAccount memberaccount = (MemberAccount) dailyService.findOneByProperties(MemberAccount.class, "memberId", order.getOrderUser());
    					memberaccount.setCurrNoshow((short) (memberaccount.getCurrNoshow() + 1));
    					memberaccount.setTotalNoshow((short) (memberaccount.getTotalNoshow() + 1));
    					ExceptionMessage exceptionMessage = new ExceptionMessage();
    					exceptionMessage.setExceptionId(dailyService.getSequence("SEQ_EXCEPTION_ID"));
    					exceptionMessage.setExceptionType("1");
    					exceptionMessage.setDailyTime(date);
    					exceptionMessage.setRecordTime(date);
    					exceptionMessage.setStatus("0");
    					exceptionMessage.setRemark("订单没有入住.没有预付款,取消");
    					exceptionMessage.setBranchId(order.getBranchId());
    					dailyService.update(order);
    					dailyService.update(memberaccount);
    					dailyService.save(exceptionMessage);
    				}else{
    					Date date = new Date();
    					Order order = (Order) dailyService.findOneByProperties(Order.class, "orderId", ((Map<?, ?>)orders.get(i)).get("ORDER_ID"));
    					order.setStatus("3");
    					RoomStatus roomstatus = (RoomStatus) dailyService.findOneByProperties(RoomStatus.class, "roomType", order.getRoomType(), "branchId", order.getBranchId());
    					roomstatus.setCount(roomstatus.getCount()-1);
    					ExceptionMessage exceptionMessage = new ExceptionMessage();
    					exceptionMessage.setExceptionId(dailyService.getSequence("SEQ_EXCEPTION_ID"));
    					exceptionMessage.setExceptionType("1");
    					exceptionMessage.setDailyTime(date);
    					exceptionMessage.setRecordTime(date);
    					exceptionMessage.setStatus("0");
    					exceptionMessage.setRemark("订单没有入住.有预付款,转入住");
    					exceptionMessage.setBranchId(order.getBranchId());
    					Check check = new Check();
    					check.setCheckId(order.getOrderId());
    					check.setBranchId(order.getBranchId());
    					check.setRoomId(order.getRoomId());
    					check.setRoomType(order.getRoomType());
    					check.setRpId(order.getRpId());
    					check.setRoomPrice(order.getRoomPrice());
    					check.setCheckUser(order.getOrderUser());
    					check.setCheckinTime(date);
    					check.setCheckoutTime(order.getLeaveTime());
    					check.setStatus("1");
    					check.setRecordTime(date);
    					check.setRecordUser("1");
    					dailyService.update(order);
    					dailyService.update(roomstatus);
    					dailyService.save(exceptionMessage);
    					dailyService.save(check);
    				}
    			}
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }  

	@Override
	public void initScheduledData() {
		logger.debug("schedule task [test1111] init...............");
	}  
}  