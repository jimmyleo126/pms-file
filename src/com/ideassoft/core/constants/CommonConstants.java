package com.ideassoft.core.constants;

public class CommonConstants {
	
	public interface SystemFunctions {
		public final static String EDIT = "编辑";
		
		public final static String ADD = "新增";
		
		public final static String DELETE = "删除";

		public final static String SAVE = "保存";
		
		public final static String PRINT = "打印";
		
		public final static String IMPORT = "导入";
		
		public final static String EXCEL = "EXCEL导出";
		
		public final static String CAMPAIGHSADD = "活动新增"; 
		
		public final static String FRESH = "刷新";
		
		public final static String RPAPPLY = "房租申请";
		
		public final static String TEMPLATEADD = "模板新增";
		
	}
	
	public interface SubSystemNames {
		public final static String crm = "客户关系管理系统";
		
		public final static String ems = "设备管理系统";
		
		public final static String upms = "权限管理系统";
		
		public final static String ipms = "门店管理系统";
		
	}
	
	public interface SystemParams {
		public final static String PAY = "PAY";

		public final static String REMOTE_PATH = "REMOTE_PATH";
		
		public final static String TRANSPORT_OBJECT = "TRANSPORTOBJECT";
		
		public final static String CHECK_POINT = "CHECKPOINT";
		
	}
	
	public interface AJAXRESULT {
		public final static Integer FALSE =	0;

		public final static Integer SUCESS = 1;
		
	}
	
	public interface STATUS {
		public final static String WASTED =	"0";

		public final static String NORMAL =	"1";
		
	}
	public interface PortalResultCode {
		final public static Integer NULL = -1;
		
		final public static Integer FAILED = -400;
		
		final public static Integer DONE = 1;
		
		final public static Integer EXIST = 3;
	}

}
