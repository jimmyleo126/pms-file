package com.ideassoft.pms.service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.ideassoft.bean.OperateLog;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.constants.SystemConstants;
import com.ideassoft.core.dao.GenericDAOImpl;

@Service
public class LogServiceInPms extends GenericDAOImpl {

	//插入保洁记录日志
	public void updatecleanapplyrecord(LoginUser loginUser, String remark, String type) throws UnknownHostException {
		try {
			String sequences = getSequence("SEQ_OPERATELOG_ID");
			SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
			String strdate = sdf.format(new Date());
			String operid = InetAddress.getLocalHost().toString();//IP地址
			operid = (String) operid.subSequence(operid.indexOf("/")+1, operid.length());
			String logid = strdate+loginUser.getStaff().getBranchId()+sequences;
			OperateLog operatelog = new OperateLog();
			operatelog.setLogId(logid);
			operatelog.setOperType("7");
			operatelog.setOperModule(SystemConstants.LogModule.CLEANAPPLY);			
			operatelog.setContent(loginUser.getStaff().getStaffName()+"处理了"+SystemConstants.LogModule.CLEANAPPLY);
			operatelog.setRecordUser(loginUser.getStaff().getStaffId());
			operatelog.setRecordTime(new Date());
			operatelog.setOperIp(operid);
			operatelog.setRemark(remark);
			operatelog.setBranchId(loginUser.getStaff().getBranchId());
			save(operatelog);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}
	
}
