package com.ideassoft.pms.task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ideassoft.bean.Clean;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.constants.SystemConstants;

import com.ideassoft.core.factory.ServiceFactory;
import com.ideassoft.core.task.ScheduledTask;
import com.ideassoft.crm.task.TransportDataTask;
import com.ideassoft.pms.service.CleanService;
import com.ideassoft.pms.service.DailyService;
import com.ideassoft.util.RequestUtil;

public class CleanTask extends ScheduledTask {
	private final Logger logger = Logger.getLogger(CleanTask.class);
	private static CleanService cleanService = (CleanService) ServiceFactory.getService("cleanService");

	public CleanTask(String name) {
		super(name);
	}

	@Override
	public void initScheduledData() {
        logger.debug("schedule task [test1111] init...............");

	}
	
	
    @SuppressWarnings("unchecked")
	public void run() { 
    	//System.out.println(123);
    	try{
    	SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
    	SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");
    	Clean clean = new Clean();
    	Date today = new Date();
    	String strdate = null;
    	String sequences = null;
    	String  timeArea = null;
    	List<?> restAmount = cleanService.getDefaultParam();
    	Calendar a = Calendar.getInstance();  
        a.set(Calendar.DATE, 1);//把日期设置为当月第一天  
        a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天  
        int maxDate = a.get(Calendar.DATE);
        //System.out.println("最大天数"+maxDate);
        Boolean flag = true;
        String branchID = SystemConstants.User.BRANCH_ID;
        //System.out.println("门店号"+branchID);

		 for(int i =0;i<maxDate;i++){
			 a.setTime(today);
			 a.add(Calendar.DAY_OF_MONTH, i);  
			 Date nextDate = a.getTime();
			 strdate = sdf.format(nextDate);
			 String  strdate2 = sdf2.format(nextDate);
		        //System.out.println("今天是"+strdate);
            Date cleandate = sdf2.parse(strdate2);
			 for(int j=0;j<2;j++){
				 sequences = cleanService.getSequence("SEQ_CLEAN_ID");
			       // System.out.println("序列是"+sequences);

				 clean.setCleanId(strdate+branchID+sequences);
				 clean.setBranchId(branchID);
				 
				 clean.setCleanDate(cleandate);
				 clean.setRoomId(" ");
				 clean.setCleanPerson(" ");
				 clean.setRecordUser(" ");
				 clean.setRecordTime(new Date());
				 clean.setCleanApplyId(" ");
				 
				 if(flag){
					  timeArea = "0";
					flag = false;
				 }else{
					  timeArea = "1";
					flag = true;
				 }
				 clean.setTimeArea(timeArea);
				 clean.setRestAmount(Integer.parseInt(((Map<?, ?>)restAmount.get(0)).get("CONTENT").toString()));
				 cleanService.save(clean);

			 }
		 }
    }catch(Exception e) {
		e.printStackTrace();
	}
	} 


}
