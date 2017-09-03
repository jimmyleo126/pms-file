
package com.ideassoft.core.constants;

public class SystemConstants {
	
	public interface SubSystemName {
		final public static String CDDS = "cdds";
		
		final public static String PMS = "ipms";
	}
	
	public interface SystemStyle {
		final public static Integer DEFAULT = 0x00;
		
		final public static Integer PC = 0x00;
		
		final public static Integer APP = 0x01;
	}

	public interface User {
		final public static String ADMIN_ID = "1";

		final public static String ADMIN_NAME = "admin";
		
		final public static String BRANCH_ID = "100001";
		
		final public static String DEPART_ID = "1001";
	}

	public interface Pagination {
		final public static int FIRST_PAGE = 1;

		final public static int DEFAULT_ROWS = 20;
	}
	
	public interface QueryType {
		final public static String QUERY = "QUERY";

		final public static String EXCEL = "EXCEL";

		final public static String CHART = "CHART";
	}

	public interface TaskConfig {
		final public static String AUTO_START = "true";

		final public static String IS_DEAMON = "true";

		final public static String TASK_TYPE = "1";
	}

	public interface TaskGroup {
		final public static String SYS_GROUP = "1";

		final public static String USER_GROUP = "2";
	}

	public interface DatabaseOperation {
		final public static String SAVE = "SAVE";

		final public static String UPDATE = "UPDATE";

		final public static String DELETE = "DELETE";
	}

	public interface SQLType {
		final public static String SQL = "sql";

		final public static String HQL = "hql";

		final public static String PROCEDURE = "procedure";
	}

	public interface NotifyConstants {
		final static int SMS_MAX_DEAL_NUM = 1000; // 短信通知服务最大处理数

		final static int MAIL_MAX_DEAL_NUM = 500; // 邮件通知服务最大处理数

		final static int SMS_GET_NUM = 10; // 短信通知服务每次处理数

		final static int MAIL_GET_NUM = 10; // 邮件通知服务每次处理数

		final static int NOTIFY_USER_SMS = 1; // 短信通知用户

		final static int NOT_NOTIFY_USER_SMS = 2; // 不通知短信用户

		final static int NOTIFY_USER_MAIL = 1; // 邮件通知用户

		final static int NOT_NOTIFY_USER_MAIL = 2; // 不通知邮件用户

		final static int IS_SMS_SERVER_OPEN = 1; // 短信通知服务开启

		final static int IS_SMS_SERVER_CLOSED = 0; // 短信通知服务不开启

		final static int IS_MAIL_SERVER_OPEN = 1; // 邮件通知服务开启

		final static int IS_MAIL_SERVER_CLOSED = 0; // 邮件通知服务不开启
	}

	public interface EventConst {
		public interface EventType {
			int INFO = 0; // 信息

			int WARN = 1; // 告警

			int SECONDARYWARN = 2;// 次要告警
		}

		public interface EventCode {
			int SERVER_ONLIN = 1;// 服务在线

			int SERVER_OFFLINE = 2;// 服务离线

			int SMS_SERVER_ERROR = 1001;// 短信服务异常

			int SMS_SERVER_START = 1002;// 短信服务启动成功

			int EMAIL_SERVER_ERROE = 1003;// 邮件服务异常

			int EMAIL_SERVER_START = 1004;// 邮件服务启动成功
		}
		
		public interface EventSource {
			String SERVER = "IDEAS";
		}
	}

	public interface NotifyMSGType {
		final public static int EVENTADD = 1;
	}
	
	public interface PortStatus {
		final public static int PORT_NORMAL = 1;
		
		final public static int PORT_INUSE = 2;
		
		final public static int PORT_INAVAILABLE = 3;
		
		final public static int PORT_UNSUPPORTED = 4;
		
		final public static int PORT_NOTEXISTS = 5;
	}
	
	public interface RemoteTransDataResult {
		final public static String FAILED = "0";
		
		final public static String DONE = "1";
	}
	
	public interface RemoteTransDataType {
		final public static int FILE = 1;
		
		final public static int OBJECT = 2;

		final public static int STRING = 3;

		final public static int MIXED = 4;
	}
	
	public interface RemoteTransReturnType {
		final public static String FILE = "file";
		
		final public static String TXT = "txt";

		final public static String EXCEL = "xlsx";

		final public static String IMAGE = "png";

		final public static String STRING = "string";
	}
	
	public interface ImportFileType {
		final public static String EXCEL = "xlsx";
		
		final public static String TXT = "txt";
	}
	
	public interface SqlStrategy {
		final public static String DEFAULT = "default";
	
		final public static String RIGHT_DEPARTMENT = "department";
		
		final public static String RIGHT_LEVEL = "level";	
	}
	
	/**
	 * 操作模块
	 * @author Administrator
	 *
	 */
	public interface LogModule{
		final public static String SAVE = "添加会员";
		final public static String DEL = "删除会员";
		final public static String UPDATE = "修改会员";
		final public static String CHANGESCORE = "储值卡充值";
		final public static String CHANGEINTEGRAL = "积分调整";
		final public static String MEMBERINFO = "会员资料";
		final public static String MEMBERQUERY = "会员查询";
		final public static String INTEGRALOPERATION ="积分操作";
		final public static String CHANGERANK ="会员升级";
		final public static String CLEANAPPLY ="保洁申请";
	}
	
	/**
	 * 操作类型
	 * @author Administrator
	 *
	 */
	public interface LogType{
		final public static String ONE = "1";
		
	}
	
	public interface ProjectName{
		final public static String USER_ID = "00000000";
	}
	
	public interface Path{
		final public static String RECIVEDATA = "http://localhost:8080/cdds/reciveData.do";
		final public static String RECIVEFILE = "http://localhost:8080/cdds/reciveFile.do";
	}
}
