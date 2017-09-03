package com.ideassoft.core.notifier;

import java.lang.Thread.State;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import org.apache.log4j.Logger;

//import cn.com.epatch.notification.ANotifyService;

//import cn.com.epatch.notification.NotificationConfig;
//import cn.com.epatch.notification.SmsNotifyMessage;
//import cn.com.epatch.notification.SmsNotifyService;


import com.ideassoft.bean.SmsSendLog;
import com.ideassoft.core.constants.SystemConstants;
import com.ideassoft.core.exception.QueueException;
import com.ideassoft.core.factory.ServiceFactory;
//import com.ideassoft.bean.CommParameter;
import com.ideassoft.core.notifier.bean.SendMessage;
import com.ideassoft.core.notifier.service.impl.NotifyService;
import com.ideassoft.core.task.BaseTask;
import com.ideassoft.core.task.TaskStatus;


public class NotifyServer extends BaseTask {

	public static final int STATUS_STARTING = 1;
	public static final int STATUS_RUN = 2;
	public static final int STATUS_STOPED = 0;
	public static final int STATUS_ERROR = -1;

	private Thread serverThread = null;
	private String instanceName;
	private int notifyStatus;
	private static NotifyServer instance = null;
	private boolean isStop = false;//是否停止该线程，停止其run方法
	//private final static String ALARMNOTIFY_TITLE = "来自禾悦会短信通知中心的消息：";
	private boolean isSuspend = false;//线程是否被挂起
	protected Logger log = Logger.getLogger(NotifyServer.class);
	private SendSMSThread smsThread = null;


	/**
	 * 加入新产生的短息
	 * 
	 * @param messages
	 */
	public void sendMessage(SendMessage sendMessage) {
		NotifyService dao = (NotifyService) ServiceFactory.getService("notifyService");
		log.info("通知管理线程增加一条信息");
		
		if (isSuspend) {
			return;
		}
		//短信线程
		if (smsThread != null) {
			if (smsThread.smsQueue.size() >= SystemConstants.NotifyConstants.SMS_MAX_DEAL_NUM) {// 短信通知服务最大处理数1000
				try {
					do{
						SendSMSThread.sleep(1000);
					}while(smsThread.smsQueue.size() <= (SystemConstants.NotifyConstants.SMS_MAX_DEAL_NUM)/4);
					//查找表中未发送的短信
					List<?> smsInfos = dao.findByProperties(SmsSendLog.class, "status", "1");
					for(int i=0;i<smsInfos.size();i++){
						//smsThread.smsQueue.add((SendMessage) smsInfos.get(i));
						smsThread.addSms((SendMessage) smsInfos.get(i));
					}
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			smsThread.addSms(sendMessage);
		}
		
		//邮件线程
	}

	/**
	 * 该实例构造必须在Spring初始化之后
	 * 
	 * @param name
	 *            实例名称
	 */
	public NotifyServer(String name) {
		super(name);
		this.instanceName = name;
		instance = this;
	}

	public static NotifyServer getInstance() {
		if (instance != null)
			return instance;
		else
			return new NotifyServer("通知服务处理");
	}

	private void initServer() {
		//TODO
		//CommParameter comm = null;
		try {
		//	NotifyService service = (NotifyService) ServiceFactory.getService("notifyService");
		//	comm = service.getNotifyCommParameter();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("初始化通知服务出错，错误内容：" + e);
		}

	}

	public void run() {
		initServer();
		notifyStatus = STATUS_RUN;
		log.info("事件通知用户服务线程成功启动......");

		smsThread = new SendSMSThread("短信通知线程");
		smsThread.start();

		while (!isStop) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				break;
			}

			if (isSuspend) {
				continue;
			}
			//if (config == null) {
			//	continue;
			//}

			try {
				sleep(5000);
				//开启短信线程
				if (smsThread.getState() == State.TERMINATED) {
					smsThread = new SendSMSThread("短信通知线程");
					smsThread.start();
				}
				//开启邮件线程
			} catch (InterruptedException e1) {
				break;
			}
		}

		/*serverThread = null;

		if (smsService != null && smsService.getStatus() != ANotifyService.STATUS_STOP) {
			smsService.stopService();
			smsService = null;
		}*/
		
	}

	/**
	 * 发短信时调用短信接口
	 * */
	private void processMessage(SendMessage[] sendMessages) {
		if (sendMessages == null || sendMessages.length == 0)
			return;
		for (SendMessage sendMessage : sendMessages) {
			log.info("向手机号为[" + sendMessage.getMessageRecipentno()+ "]发送短信息" + sendMessage.getMessageContent());
			//TODO
			//TODO
			//TODO
		//	SmsNotifyMessage smsMessage = new SmsNotifyMessage(new String[] { sendMessage.getMessageRecipentno() },
		//			ALARMNOTIFY_TITLE, sendMessage.getMessageContent(), 0);
		//	smsService.addMessage(smsMessage);
			//发送完要返回码，若发送失败再插回到队列里
			//TODO
			//int returnCode = send();
			//if(returnCode==""){
			//dealSmsInfo(sendMessage);}
		}
			
	}

	public void start() {
		isStop = false;
		serverThread = new Thread(this);
		serverThread.setName(instanceName);
		notifyStatus = STATUS_STARTING;
		serverThread.start();
	}

	public void stop() {
		if (!isStop) {
			isStop = true;
			serverThread.interrupt();
			try {
				serverThread.join();
			} catch (InterruptedException e) {
			}
			/*serverThread = null;
			
			if (smsService != null && smsService.getStatus() != ANotifyService.STATUS_STOP) {
				smsService.stopService();
				smsService = null;
			}*/
		}
	}

	/**
	 * 通知通知线程通信参数变化
	 * 
	 * @param commParameter
	 */


	/**
	 * 通知线程暂停服务
	 * 
	 * @param isSuspend
	 *            true 表示暂停，false 表示恢复运行
	 * */
	public synchronized void suspendServer(boolean isSuspend) {
		this.isSuspend = isSuspend;
	}

	@Override
	public void addTaskObj(String key, Object obj) throws QueueException {

	}

	@Override
	public void removeTaskObj(String key) {

	}

	public int pauseTask() {
		return 0;
	}

	public int resetTask() {
		return 0;
	}

	public int resumeTask() {
		return 0;
	}

	public int stopTask() {
		return 0;
	}

	public TaskStatus getStatus() {
		return null;
	}

	public synchronized int getNotifyStatus() {
		return notifyStatus;
	}

	public synchronized void setNotifyStatus(int notifyStatus) {
		this.notifyStatus = notifyStatus;
	}
	
    //发送短信的线程
	public class SendSMSThread extends Thread {
		 protected boolean isFree = true;
		 protected volatile int status = 0;
		NotifyService dao = (NotifyService) ServiceFactory.getService("notifyService");
		private Queue<SendMessage> smsQueue = new PriorityQueue<SendMessage>(1000,
				new Comparator<SendMessage>() {
			//创建匿名类来重写排序方法
			public int compare(SendMessage o1, SendMessage o2) {
			//返回排序规则  
				return  Integer.parseInt(o1.getMemberLevel()) - Integer.parseInt(o2.getMemberLevel());
			}
	});

		public SendSMSThread(String name) {
			super("通知线程[" + name + "]");
		}

		/**
		 * 加入新产生的短信
		 * 
		 * @param messages
		 */
		public void addSms(SendMessage sendMessage) {
			synchronized (smsQueue) {
				if (isSuspend) {
					return;
				}
				log.info("短信通知线程增加一条信息");
				boolean addFlag = smsQueue.offer(sendMessage);
				if(!addFlag){
					log.info("短信通知线程增加信息失败");	
					addSms(sendMessage);//可能有死循环
				}
			 
				smsQueue.notify();
			}
		}

		public void run() {
			log.info("短信通知线程启动......");

			while (!isStop) {
				SendMessage[] sendMessage = null;

				try {
					// log.info("短信通知线程休眠5秒......");
					sleep(5000);
				} catch (InterruptedException e1) {
					break;
				}

				try
			      {
			        this.isFree = false;
			        initProcessMessage();
			        this.isFree = true;
			      } catch (Throwable t)
			      {
			        log.error("Service运行出错", t);
			      }

				/*synchronized (smsQueue) {
					while (smsQueue.isEmpty()) {
						try {
							smsQueue.wait();
						} catch (InterruptedException e) {
							isStop = true;
							return;
						}
					}
					
				}*/
			      
			      
			      if (smsThread.isFree()) {
						int removeNum = SystemConstants.NotifyConstants.SMS_GET_NUM;

						if (smsQueue.size() > removeNum) {
							sendMessage = new SendMessage[removeNum];
						} else {
							sendMessage = new SendMessage[smsQueue.size()];
						}
						for (int i = 0; i < sendMessage.length; i++) {
							sendMessage[i] = smsQueue.poll();
						}
					}
				//短信记录队列里则一直发短信
				try {
					if (sendMessage != null && sendMessage.length != 0) {
						log.info("发短信去；；；；；；；；；；");	
						processMessage(sendMessage);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

			serverThread = null;

			if (smsThread != null ) {
				smsThread.stop();
				smsThread = null;
			}
		}
		
		private void initProcessMessage() {
			//刚启动时去处理遗留的未发短信，使其isFree
			// TODO Auto-generated method stub
			
		}

		public void dealSendMessage(String eventcode) {
			try {
				SendMessage[] sendMessage = null;
				if(smsQueue.size() > 0){
					for (int i = 0; i < smsQueue.size(); i++) {
						//重新封装到队列里
						sendMessage[i] = smsQueue.poll();
						sendMessage[i].setMessageEventCode(eventcode);
						dealSmsInfo(sendMessage[i]);
					}				
				}
				
				if (smsThread.isFree()) {
					int removeNum = SystemConstants.NotifyConstants.SMS_GET_NUM;

					if (smsQueue.size() > removeNum) {
						sendMessage = new SendMessage[removeNum];
					} else {
						sendMessage = new SendMessage[smsQueue.size()];
					}
					for (int i = 0; i < sendMessage.length; i++) {
						sendMessage[i] = smsQueue.poll();
					}
				}
			} catch (Exception e) {
				log.error("处理事件 出错!");
				e.printStackTrace();
			}
		}
		
		public void dealSmsInfo(SendMessage s){
			String oldSendNo = s.getDataId();
			String newSendNo = dao.getSequence("SEQ_SMSSENDLOG_ID", null);
			s.setDataId(newSendNo);
			smsQueue.add(s);
			//修改原流水单status为3失败
			SmsSendLog smsInfos = (SmsSendLog) dao.findOneByProperties(SmsSendLog.class, "dataId", oldSendNo);
			smsInfos.setStatus("3");
			dao.update(smsInfos);
			//记到一条新短信的流水表里待发
			SmsSendLog smsInfoNew = (SmsSendLog) dao.findOneByProperties(SmsSendLog.class, "dataId", oldSendNo);
			smsInfoNew.setRemark("上一次发送失败的流水编号为："+oldSendNo);
			smsInfoNew.setDataId(newSendNo);		
			dao.save(smsInfoNew);
		}
		
		public void start() {
			isStop = false;
			serverThread = new Thread(this);
			serverThread.setName(instanceName);
			notifyStatus = STATUS_STARTING;
			serverThread.start();
		}
		
		  public boolean isFree() {
			    return this.isFree;
			  }
		
	}

	//TODO
    //记载记载’警告日志，并发邮件
	public void recordSendWarning(String eventcode) {
		
	
	}
	/*public static void main(String[] args) {
		Queue<Object> smsQueue = new PriorityQueue<Object>();
		smsQueue.add("");
		smsQueue.poll();
		smsQueue.peek();
		smsQueue.offer("");
	}*/
	
}
