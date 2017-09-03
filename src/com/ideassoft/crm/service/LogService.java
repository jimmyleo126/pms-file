package com.ideassoft.crm.service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.ideassoft.bean.Member;
import com.ideassoft.bean.OperateLog;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.constants.SystemConstants;
import com.ideassoft.core.constants.SystemConstants.LogModule;
import com.ideassoft.core.dao.GenericDAOImpl;

@SuppressWarnings("unchecked")
@Service
public class LogService extends GenericDAOImpl {
	//保存,修改,删除会员日志
	public void memberLog(LoginUser loginUser, Member bean, String type) throws UnknownHostException {
		try {
			String sequences = getSequence("SEQ_OPERATELOG_ID");
			SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
			String strdate = sdf.format(new Date());
			String operid = InetAddress.getLocalHost().toString();//IP地址
			operid = (String) operid.subSequence(operid.indexOf("/")+1, operid.length());
			String logid = strdate+loginUser.getStaff().getBranchId()+sequences;
			OperateLog operatelog = new OperateLog();
			operatelog.setLogId(logid);
			operatelog.setOperType(type);
			operatelog.setOperModule(SystemConstants.LogModule.MEMBERINFO);
			if("1".equals(type))
				operatelog.setContent(SystemConstants.LogModule.SAVE+loginUser.getStaff().getStaffName());
			else if("2".equals(type))
				operatelog.setContent(SystemConstants.LogModule.DEL+loginUser.getStaff().getStaffName());
			else if("3".equals(type))
				operatelog.setContent(SystemConstants.LogModule.UPDATE+loginUser.getStaff().getStaffName());
			operatelog.setRecordUser(loginUser.getStaff().getStaffId());
			operatelog.setRecordTime(new Date());
			operatelog.setOperIp(operid);
			operatelog.setRemark(bean.getRemark());
			operatelog.setBranchId(loginUser.getStaff().getBranchId());
			save(operatelog);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}
	//储值卡充值
	public void changeMemberScoreAndReserveLog(LoginUser loginUser, String remark, String type) throws UnknownHostException {
		try {
			String sequences = getSequence("SEQ_OPERATELOG_ID");
			SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
			String strdate = sdf.format(new Date());
			String operid = InetAddress.getLocalHost().toString();//IP地址
			operid = (String) operid.subSequence(operid.indexOf("/")+1, operid.length());
			String logid = strdate+loginUser.getStaff().getBranchId()+sequences;
			OperateLog operatelog = new OperateLog();
			operatelog.setLogId(logid);
			operatelog.setOperType("4");
			operatelog.setOperModule(SystemConstants.LogModule.MEMBERQUERY);
			if("4".equals(type))
				operatelog.setContent(SystemConstants.LogModule.CHANGERANK+loginUser.getStaff().getStaffName());
			else
			operatelog.setContent(SystemConstants.LogModule.CHANGESCORE+loginUser.getStaff().getStaffName());
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
	//修改积分
	public void changeIntegrationLog(LoginUser loginUser, String remark) throws UnknownHostException {
		try {
			String sequences = getSequence("SEQ_OPERATELOG_ID");
			SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
			String strdate = sdf.format(new Date());
			String operid = InetAddress.getLocalHost().toString();//IP地址
			operid = (String) operid.subSequence(operid.indexOf("/")+1, operid.length());
			String logid = strdate+loginUser.getStaff().getBranchId()+sequences;
			OperateLog operatelog = new OperateLog();
			operatelog.setLogId(logid);
			operatelog.setOperType("3");
			operatelog.setOperModule(SystemConstants.LogModule.INTEGRALOPERATION);
			operatelog.setContent(SystemConstants.LogModule.CHANGEINTEGRAL+loginUser.getStaff().getStaffName());
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
