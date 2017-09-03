package com.ideassoft.pmsmanage.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.ideassoft.bean.CashKey;
import com.ideassoft.bean.RoomKey;
import com.ideassoft.bean.RoomPriceId;
import com.ideassoft.core.dao.GenericDAOImpl;

@Service
public class PmsmanageService extends GenericDAOImpl {
	public void upateroomstatus(String branchiD, String rooomiD,String statusdelete, String recordUser) {
		RoomKey roomKey = new RoomKey();
		roomKey.setRoomId(rooomiD);
		roomKey.setBranchId(branchiD);
		roomKey.setStatus(statusdelete);
		String hql1 = "update Room set status = :STATUS,recordUser = :RECORDUSER where roomKey = :ROOMKEY";
		this.executeUpdateHQL(hql1, new String[] { "STATUS", "ROOMKEY", "RECORDUSER" }, new Object[] { "F", roomKey,
				recordUser });
	}

	/*
	 * public List<?> getRpsetup() throws Exception { return
	 * findBySQL("rpsetup", true); }
	 */

	public List<?> getRpbranchid() throws Exception {
		return findBySQL("rpbranchid", true);
	}
	
	public List<?> getRptheme() throws Exception {
		return findBySQL("rptheme", true);
	}
	
	public List<?> getRpkind() throws Exception {
		return findBySQL("rpkind", true);
	}

	public List<?> getRproomtype() throws Exception {
		return findBySQL("rproomtype", true);
	}

	public List<?> getRppack() throws Exception {
		return findBySQL("rppack", true);
	}

	public List<?> getRpsetup() throws Exception {
		return findBySQL("rpsetup", true);
	}

	public List<?> getStatus() throws Exception {
		return findBySQL("rpstatus", true);
	}

	public List<?> getRpdata(String branchid, String theme, String roomtype) throws Exception {
		List<?> result = findBySQL("rpdatajudge", new String[] { branchid, theme, roomtype }, true);
		return result;
	}

	public List<?> getShiftcontent() throws Exception {
		return findBySQL("shiftcontent", true);
	}

	public List<?> getUniqueroom(String branchid, String roomid) throws Exception {
		List<?> result = findBySQL("uniqueroom", new String[] { branchid, roomid }, true);
		return result;
	}

	public List<?> getExitroomstatus(String branchid, String rproomtype) throws Exception {
		List<?> result = findBySQL("exitroomstatus", new String[] { branchid, rproomtype }, true);
		return result;
	}

	public List<?> getRename(String theme, String branchid, String roomtype, String status) throws Exception {
		List<?> result = findBySQL("roomeditrename", new String[] { theme, branchid, roomtype, status }, true);
		return result;
	}

	public void upateroomedit(String theme, String branchid, String rproomtype, String floor, String roomid,
			Short areadata, String status, String remark, String editUser) {
		RoomKey roomKey = new RoomKey();
		roomKey.setRoomId(roomid);
		roomKey.setBranchId(branchid);
		roomKey.setStatus(status);
		String hql1 = "update Room set roomType = :ROOMTYPE,floor = :FLOOR,area = :AREA, remark = :REMARK,recordUser = :RECORDUSER where roomKey = :ROOMKEY and theme = :THEME";
		this.executeUpdateHQL(hql1, new String[] { "ROOMTYPE", "FLOOR", "AREA",  "REMARK", "RECORDUSER",
				"ROOMKEY", "THEME" }, new Object[] { rproomtype, floor, areadata,  remark, editUser, roomKey,
				theme });
	}

	public void upateroomprice(String themeid, String branchiD, String roomtypeid, String rpId, String recordUser) {
		RoomPriceId roompriceid = new RoomPriceId();
		roompriceid.setBranchId(branchiD);
		roompriceid.setRoomType(roomtypeid);
		roompriceid.setRpId(rpId);
		String hql1 = "update RoomPrice set status = :STATUS,recordUser = :RECORDUSER where roomPriceId = :ROOMPRICEID and theme = :THEME";
		this.executeUpdateHQL(hql1, new String[] { "STATUS", "RECORDUSER", "ROOMPRICEID", "THEME" }, new Object[] {
				"0", recordUser, roompriceid, themeid });
	}
	
	public void upatecashbox(String cashboxid, String branchiD, String recordUser) {
		CashKey cashkey = new CashKey();
		cashkey.setBranchId(branchiD);
		cashkey.setCashBox(cashboxid);
		String hql1 = "update CashBox set status = :STATUS,recordUser = :RECORDUSER where cashKey = :CASHKEY ";
		this.executeUpdateHQL(hql1, new String[] { "STATUS", "RECORDUSER", "CASHKEY" }, new Object[] {
				"0", recordUser, cashkey});
	}

	public List<?> getRproomtypeInitialize(String theme) throws Exception {
		List<?> result = findBySQL("roomtypeInitialize", new String[] {theme}, true);
		return result;
	}
	
	public List<?> getRpidInitialize() throws Exception {
		return findBySQL("rpidInitialize", true);
	}
	
	public List<?> getRpInitializejudge(String branchId,String theme) throws Exception {
		List<?> result = findBySQL("rpidInitializejudge", new String[] {branchId,theme}, true);
		return result;
	}
	
	public List<?> getRpapplyroomtype(String themeid,String rpthemeid,String branchid,String rpking,String statusid) throws Exception {
		List<?> result = findBySQL("rpapplyroomtype", new String[] {themeid,rpthemeid,branchid,rpking,statusid}, true);
		return result;
	}
	
	public List<?> getRpapplystatus() throws Exception {
		return findBySQL("rpapplystatus", true);
	}

}
