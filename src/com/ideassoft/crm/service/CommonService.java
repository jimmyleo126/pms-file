package com.ideassoft.crm.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ideassoft.bean.Department;
import com.ideassoft.bean.SysParam;
import com.ideassoft.core.constants.CommonConstants;
import com.ideassoft.core.constants.SystemConstants;
import com.ideassoft.core.dao.GenericDAOImpl;
import com.ideassoft.util.DateUtil;
import com.ideassoft.util.RemoteTransUtil;

@Service
public class CommonService extends GenericDAOImpl {
	
	public List<?> findTransData(String beanName, Date checkPoint, Date tempPoint) throws Exception {
		String hql = " from " + beanName + " where recordTime between ? and ?";
		return findByHQL(hql, new Object[] { checkPoint, tempPoint });
	}
	
	public Integer countSubDepartments(String departId) {
		List<?> departs = findByProperties(Department.class, "superDepart", departId);
		return departs != null ? departs.size() : 0;
	}
	
	public String UpDatePath(String beanName){
		
		Map<String, List<?>> dts = new HashMap<String, List<?>>();
		String tempPoint = DateUtil.nextDate("yyyy/MM/dd 00:00");
		DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		
		SysParam checkPoint = (SysParam) findOneByProperties(SysParam.class, "paramType", 
				CommonConstants.SystemParams.CHECK_POINT, new Object[0]);
		
		List<?> data;
		try {
			data = findTransData(beanName, 
					sdf.parse(checkPoint.getContent()), sdf.parse(tempPoint));
			dts.put(beanName, data);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String result = null;
		
		try {
			result = RemoteTransUtil.transData(SystemConstants.Path.RECIVEDATA, dts, 
					SystemConstants.RemoteTransDataType.OBJECT,
					SystemConstants.RemoteTransReturnType.STRING);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
}
