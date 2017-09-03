package com.ideassoft.crm.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.ideassoft.bean.AuditLog;
import com.ideassoft.bean.RoomPriceId;
import com.ideassoft.bean.Staff;
import com.ideassoft.bean.SysParam;
import com.ideassoft.core.dao.GenericDAOImpl;
import com.ideassoft.core.page.Pagination;

@Service
public class AuditService extends GenericDAOImpl {
	// 查询默认审核人
	public List<?> queryDefaultauditDialogData(String queryData, Pagination pagination) throws Exception {
		String sql = "selectaudit";
		return findBySQLWithPagination(sql, pagination, true);
	}

	public Object getAudit() throws Exception {
		return findOneByProperties(SysParam.class, "paramType", "AUDIT", "paramName", "默认审核人");
	}

	public Object getAuditName(String auditid) throws Exception {
		return findOneByProperties(Staff.class, "staffId", auditid);
	}

	/*
	 * public List<?> querysaleType() throws Exception { List<?> result =
	 * findBySQL("selectpurchase", new String[] { purchaseId }, true); return
	 * result; }
	 */

	public List<?> querysaleType() throws Exception {
		String sql = "querysaletype";
		return findBySQL(sql, true);
	}

	// 查询采购表信息
	public List<?> getPur(String operid) throws Exception {
		/*
		 * "select * from a231 where abc = ? and baa = ?";
		 * "select * from a231 where abc = :ABC and baa = :CBC";
		 */
		/* map.put("purchaseId", purchaseId); */
		List<?> result = findBySQL("selectpurchase", new String[] { operid }, true);
		return result;
		/* map.put("CBC", aads) */
		/*
		 * String sql = ((SqlInfo) findOneByProperties(SqlInfo.class, "sqlName",
		 * "selectpurchase")).getSqlInfo()+ purchaseId + "'"; List<?> result =
		 * findBySQLWithPagination(sql, null); return result;
		 */
	}

	// 查询采购副表信息
	public List<?> getPurinfo(String operid) throws Exception {
		List<?> result = findBySQL("selectpurchasedetali", new String[] { operid }, true);
		return result;
	}

	// 查询退房申请信息
	public List<?> getCheckdata(String operid) throws Exception {
		List<?> result = findBySQL("selectcheckputdetali", new String[] { operid }, true);
		return result;
	}
	
	// 查询维修申请信息
	public List<?> getRepairdata(String operid) throws Exception {
		List<?> result = findBySQL("selectrepairdetali", new String[] { operid }, true);
		return result;
	}

	// 查询退房申请信息
	public List<?> getCheckroomtype(String branchid, String roomid) throws Exception {
		List<?> result = findBySQL("selectcheckroomtype", new String[] { branchid, roomid }, true);
		return result;
	}

	// 审核日志保存
	public void saveAuditLog(AuditLog auditLog) {
		this.save(auditLog);
	}

	public void upateroompricedata(String bid, String rid, String tid, String rt, String aurecordUser, String status) {
		RoomPriceId roomPriceId = new RoomPriceId();
		roomPriceId.setBranchId(bid);
		roomPriceId.setRoomType(rt);
		roomPriceId.setRpId(rid);
		String hql1 = "update RoomPrice set status = :STATUS,recordUser = :RECORDUSER where roomPriceId = :ROOMPRICEID";
		this.executeUpdateHQL(hql1, new String[] { "STATUS", "ROOMPRICEID", "RECORDUSER" }, new Object[] { status,
				roomPriceId, aurecordUser });
	}

}
