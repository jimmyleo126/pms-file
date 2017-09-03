package com.ideassoft.core.notifier.service.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import org.springframework.stereotype.Service;


import com.ideassoft.bean.EventType;
import com.ideassoft.bean.SmsSendLog;
import com.ideassoft.bean.SmsTemplate;
import com.ideassoft.bean.Staff;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.constants.SystemConstants;
import com.ideassoft.core.dao.GenericDAOImpl;
import com.ideassoft.core.factory.ServiceFactory;
import com.ideassoft.core.notifier.NotifyServer;
import com.ideassoft.bean.CommParameter;
import com.ideassoft.core.notifier.bean.SendMessage;
import com.ideassoft.core.notifier.service.INotifyService;



@Service
public class NotifyService extends GenericDAOImpl implements INotifyService {
	protected Logger log = Logger.getLogger(NotifyServer.class);
	//发短信服务起时就加载相应配置的数据
	@SuppressWarnings("unchecked")
	public CommParameter getNotifyCommParameter() throws Exception {
		//String sql = "select * from TP_P_COMMPARAMETER";
		//List<CommParameter> list = findBySQL(sql, CommParameter.class);
		String hql = " from CommParameter";
		List<CommParameter> list = findByHQL(hql);
		return list != null && list.size() >= 0 ? list.get(0) : null;
	}
    //获取所有的被通知对象（从中获取各被通知对象的手机号码或是邮箱地址）（可以重载，传递不同的参数，查询条件）
	@SuppressWarnings("unchecked")
	public List<Staff> getAllNotiUsers() throws Exception {
		String hql = " from Staff where status = '1'";
		return findByHQL(hql);
	}
	
	//将要发短信的列表里数据，包装成smssendlog对象保存到TL_C_SMSSENDLOG表里去
	public boolean addSms(SmsSendLog[] smsInfos) throws Exception {
		if(smsInfos!=null&&smsInfos.length!=0)
		{
			for(int i=0;i<smsInfos.length;i++)
			{
				getHibernateTemplate().save(smsInfos[i]);
			}
		}
		return false;
	}
	
	//获取额外的通知所需的数据（不同的业务查询不同的数据，再将不同的数据替换到模板里 ）
	//--------------------------------------------------------------------------------------------------------------------------
	
  public static String getRealSmsContent(Map<String,String> data, String templateContent){
	  
	  String realSmsContent = "";
	 
	  Iterator<Entry<String,String>> entries = data.entrySet().iterator();
	   while(entries.hasNext()){
		   Entry<String,String> entry = entries.next();
		   String key = entry.getKey();
		   String value = entry.getValue();
		   if(templateContent != null && !templateContent.equals("")){
			   realSmsContent = templateContent.replace(key, value); 
		   }
		   templateContent = realSmsContent;
	  }
        return realSmsContent;
    }    
	
	
  public static void main(String[] args) throws Exception {
	
	  
	 /* Map<String,String> data = new HashMap<String,String>();
	  data.put("{加油}", "come on");
	  data.put("{蒋顺敏}", "jiangshunmin");
	  data.put("{你可以的}", "you can do it");
	  String kkk="新年愿望是{加油}，姓名是{蒋顺敏}，今年你一定是{你可以的}";
	 String d = getRealSmsContent(data,kkk);
	 System.out.println("结果 为："+d);*/
	  Map<String,String> data = new HashMap<String,String>();
	  data.put("{加油}", "come on");
	  data.put("{蒋顺敏}", "jiangshunmin");
	  data.put("{你可以的}", "you can do it");
	  for(int i = 0;i <= 5; i++){
	   // happenSendSms(null,null,null,"10000001",data,"18205253515");
	  }
	  
  }

  
public List<EventType> findEventTypes() {
	// TODO Auto-generated method stub
	return null;
}
	
	
/**
 * 添加待发的短信到smsSendLog表里去
 * @param loginUser 登录用户
 * @param eventCode 
 * @param dataId 
 * @param templateId 模板号  不同的信息模板号
 * @param varInTemplate	模板中值   Map<String, String>
 * @param smsRecipentno 接收号码
 * @param memberLevel 会员等级
 * @return
 */
public SmsSendLog happenSendSms(LoginUser loginUser,String eventCode, String dataId,String templateId, Map<String, String> varInTemplate,String smsRecipentno, String memberLevel){
	String realSmsContent= "";
	String operateUser = "";
	String branchId = "";
	String status = "1";
	NotifyService dao = (NotifyService) ServiceFactory.getService("notifyService");
	//数据保存到表里
	SmsSendLog smsInfo = new SmsSendLog();
	if(dataId == null){
		if(null != loginUser){
			operateUser = loginUser.getStaff().getStaffId();
			branchId = loginUser.getStaff().getBranchId();
		}else{
			operateUser = "-1";
			branchId = "100001";
		}
		dataId = getSequence("SEQ_SMSSENDLOG_ID", null);
		smsInfo.setDataId(dataId);
		smsInfo.setRecordUser(operateUser);
		smsInfo.setTemplateId(templateId);
		smsInfo.setBranchId(branchId);
		smsInfo.setRecordTime(new Date());
		smsInfo.setSmsRecipentno(smsRecipentno);
		String templateContent = ((SmsTemplate)(dao.findOneByProperties(SmsTemplate.class, "templateId", templateId))).getTemplateContent();
		realSmsContent = NotifyService.getRealSmsContent(varInTemplate,templateContent);
		smsInfo.setSmsContent(realSmsContent);
		smsInfo.setStatus(status);
	}else{
		smsInfo = (SmsSendLog)(dao.findOneByProperties(SmsSendLog.class, "dataId", dataId));
		dataId = getSequence("SEQ_SMSSENDLOG_ID", null);
		smsInfo.setDataId(dataId);
		realSmsContent = smsInfo.getSmsContent();
		smsRecipentno = smsInfo.getSmsRecipentno();	
		//可能逻辑还是有误的
	}
	
	//数据封装到sendMessage类里添加到队列中去
	SendMessage sendMessage = new SendMessage();
	sendMessage.setDataId(dataId);
	sendMessage.setMessageContent(realSmsContent);
	sendMessage.setMessageRecipentno(smsRecipentno);
	sendMessage.setMemberLevel(memberLevel);
	if(eventCode == null){
		eventCode = String.valueOf(SystemConstants.EventConst.EventCode.SMS_SERVER_START);
	}
	sendMessage.setMessageEventCode(String.valueOf(eventCode));
	try {
		dao.addSms(new SmsSendLog[] { smsInfo });
		log.info("记录事件");
		if ("1".equals(status)) {//待发中的短信
			if (NotifyServer.getInstance() != null){
				NotifyServer.getInstance().sendMessage(sendMessage);
			}
		}
	} catch (Exception e) {
		log.error("保存事件 出错!");
		e.printStackTrace();
	}
	return smsInfo;
}
	
	
	
}
