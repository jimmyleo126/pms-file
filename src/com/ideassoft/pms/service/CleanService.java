package com.ideassoft.pms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ideassoft.core.dao.GenericDAOImpl;
import com.ideassoft.core.page.Pagination;

@Service
public class CleanService extends GenericDAOImpl {
	// 查询默认可预约房间数
	public List<?> getDefaultParam() throws Exception {
		List<?> result = findBySQL("getDefaultParam", true);
		return result;
	}

	// 保洁
	public List<?> getApplicationdata(String branchid, Pagination pagination)
			throws Exception {
		List<?> result = findBySQLWithPagination("getApplicationdata",
				new String[] { branchid }, pagination, true);
		return result;
	}

	// 查询保洁申请带条件
	public List<?> getApplicationdataCondition(String branchid,
			String startdate, String enddate, String timeArea, String status,
			Pagination pagination) throws Exception {
		
		List<?> result = findBySQLWithPagination(
				"getApplydataCon",
				new String[] { branchid, startdate, enddate, timeArea, status },
				pagination, true);
		return result;
	}

	public List<?> findRoomByBranchId(String branchid) throws Exception {
		List<?> result = findBySQL("findRoomByBranchId",
				new String[] { branchid }, true);
		return result;
	}

	public List<?> findstaffByBranchId(String branchid) throws Exception {
		List<?> result = findBySQL("findstaffByBranchId",
				new String[] { branchid }, true);
		return result;
	}

	// 查所有的保洁记录

	public List<?> queryAllOfRecord(String branchid, Pagination pagination)
			throws Exception {
		List<?> result = findBySQLWithPagination("queryAllOfRecord",
				new String[] { branchid }, pagination, true);
		return result;
	}

	// 按条件查询保洁记录
	public List<?> queryRecordByCondition(String branchid, String time,
			String status, Pagination pagination) throws Exception {
		List<?> result = findBySQLWithPagination("queryRecordByCon",
				new String[] { branchid, time, status }, pagination, true);
		return result;
	}

}
