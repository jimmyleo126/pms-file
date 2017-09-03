package com.ideassoft.pms.task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ideassoft.bean.Contrart;
import com.ideassoft.bean.Member;
import com.ideassoft.core.constants.SystemConstants.ProjectName;
import com.ideassoft.core.factory.ServiceFactory;
import com.ideassoft.core.notifier.service.impl.NotifyService;
import com.ideassoft.core.task.ScheduledTask;
import com.ideassoft.pms.service.ApartmentRentService;

public class ApartmentRentTask extends ScheduledTask implements ProjectName {
	
	private final Logger logger = Logger.getLogger(ApartmentRentTask.class);
	
	private static ApartmentRentService apartmentRentService = (ApartmentRentService) ServiceFactory.getService("apartmentRentService");
	private static NotifyService notifyService = (NotifyService) ServiceFactory.getService("notifyService");
    public ApartmentRentTask(String name) {
		super(name);
	}
    
	public void run() {  
        try {
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        	//30天
    		Calendar thirtyDays = Calendar.getInstance();
    		thirtyDays.add(Calendar.DAY_OF_MONTH, 30);
    		//10天
    		Calendar tenDays = Calendar.getInstance();
    		tenDays.add(Calendar.DAY_OF_MONTH, 10);
    		//3天
    		Calendar threeDays = Calendar.getInstance();
    		threeDays.add(Calendar.DAY_OF_MONTH, 3);
        	@SuppressWarnings("unchecked")
			List<Contrart> contracts = apartmentRentService.findByProperties(Contrart.class, "status","1");
        	Map<String, String> map = new HashMap<String, String>();
        	for(Contrart contract : contracts){
        		if(sdf.format(contract.getContrartEndTime()).equals(sdf.format(thirtyDays.getTime()))){
        			map.put("天数", "30");
            	}
    			if(sdf.format(contract.getContrartEndTime()).equals(sdf.format(tenDays.getTime()))){
    				map.put("天数", "10");
	        	}
    			if(sdf.format(contract.getContrartEndTime()).equals(sdf.format(threeDays.getTime()))){
    				map.put("天数", "3");
    			}
    			if(map.size() > 0){
    				Member member = (Member) apartmentRentService.findById(Member.class, contract.getMemberId());
    				map.put("会员", member.getMemberName());
    				//notifyService.happenSendSms(null,null,null,"123123",map,contract.getMobile(),member.getMemberRank());
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