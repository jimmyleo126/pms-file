package com.ideassoft.core.server;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import com.ideassoft.core.notifier.NotifyServer;
import com.ideassoft.core.notifier.config.TimeoutSetConfig;
import com.ideassoft.core.task.TaskManager;

public class Server {
	
	private final static Logger logger = Logger.getLogger("Server");
	
	public final static String VERSION = "V1.0.0";
	
	private static Server instance = null;
	
	private TaskManager taskManager = null;
	
	private NotifyServer notifyServer = null;
	
	public static ApplicationContext Context = null;
	
	private static final ServerContext serverContext = new ServerContext();
	
	public static Config config = null;
	
	public final static ServerContext getServerContext() {
		return serverContext;
	}

	private Server() {

	}

	public static void shutdownService() {
		new Thread() {
			public void run() {
				try {
					sleep(100);
				} catch (InterruptedException e) {
				}
			}
		}.start();
	}

	public static void init(ApplicationContext context) {
		if (instance == null) {
			instance = new Server();
		}

		config = Config.getNewInstance();
		logger.info("Server: Start....");
		logger.info("init Config ...");
		Server.Context = context;
		
		TimeoutSetConfig instance = TimeoutSetConfig.getNewInstance();
		instance.init();
		
		logger.info("Config init Success...");

	}

	public static Server getInstance() {
		return instance;
	}

	public void loadSysTask() {
	}
	
	public TaskManager getTaskManager() {
		return taskManager;
	}

	public synchronized static void run() {
		instance.taskManager = TaskManager.getInstance();
		instance.taskManager.start();
		
		instance.notifyServer = NotifyServer.getInstance();
		instance.notifyServer.start();
	}

	public NotifyServer getNotifyServer() {
		return notifyServer;
	}

}
