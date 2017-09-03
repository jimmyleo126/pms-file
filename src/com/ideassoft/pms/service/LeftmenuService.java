package com.ideassoft.pms.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

import com.ideassoft.bean.CashKey;
import com.ideassoft.bean.RoomKey;
import com.ideassoft.core.dao.GenericDAOImpl;
import com.ideassoft.core.page.Pagination;

@Service
public class LeftmenuService extends GenericDAOImpl {
	public List<?> getMemberdata(String Mnumber) throws Exception {
		List<?> result = findBySQL("selectmember", new String[] { Mnumber }, true);
		return result;
	}

	public List<?> getRoomtype() throws Exception {
		String sql = "orderselectroomtype";
		return findBySQL(sql, true);
	}

	public List<?> getTheme() throws Exception {
		String sql = "orderselecttheme";
		return findBySQL(sql, true);
	}

	public List<?> getRoomid(String theme, String roomtype) throws Exception {
		List<?> result = findBySQL("roomselect", new String[] { theme, roomtype }, true);
		return result;
	}

	public List<?> getTypename(String roomtype) throws Exception {
		List<?> result = findBySQL("roomtypeselect", new String[] { roomtype }, true);
		return result;
	}

	public List<?> getRoomprice(String rpId,String branchid) throws Exception {
		List<?> result = findBySQL("selectroomprice", new String[] { rpId,branchid }, true);
		return result;
	}

	public List<?> getGuarantee() throws Exception {
		String sql = "orderselectguarantee";
		return findBySQL(sql, true);
	}

	public List<?> getOrderdata() throws Exception {
		String sql = "ordersearchdata";
		return findBySQL(sql, true);
	}

	public List<?> getRooms() throws Exception {
		String sql = "ordergetrooms";
		return findBySQL(sql, true);
	}

	public List<?> getOrdercondition(String orderid, String orderuser, String usercheckin, String mobile,String branchId,
			String memberid, Pagination pagination) throws Exception {
		List<?> result = findBySQLWithPagination("ordersearchcondition", new String[] { orderid, orderuser, usercheckin, mobile,
				memberid ,branchId}, pagination, true);
		return result;
	}

	public List<?> getGoodssale(Pagination pagination, String branchId) throws Exception {
		List<?> result = findBySQLWithPagination("goodssaledata", new String[]{branchId}, pagination, true);
		//String sql = "goodssaledata";
		return result;
	}

	
	public List<?> getCategorycondition(String branchid) throws Exception {
		List<?> result = findBySQL("categorycondition", new String[] { branchid}, true);
		return result;
	}

	public List<?> getGdsproject() throws Exception {
		String sql = "gdsproject";
		return findBySQL(sql, true);
	}

	public List<?> getGdsprojectpay() throws Exception {
		String sql = "gdsprojectpay";
		return findBySQL(sql, true);
	}

	public List<?> getWorkbill(String branchid) throws Exception {
		List<?> result = findBySQL("gdsworkbill", new String[] { branchid}, true);
		return result;
	}

	public List<?> getGdsalecondition(String goodsid, String goodsname, String categoryid,String branchId,Pagination pagination) throws Exception {
		List<?> result = findBySQLWithPagination("gdsalecondition", new String[] { goodsid, goodsname, categoryid ,branchId}, pagination, true);
		return result;
	}

	public List<?> getGdsroomid(String branchId) throws Exception {
		List<?> result = findBySQL("gdsroomid", new String[] { branchId}, true);
		return result;
	}

	public List<?> getMscdta(String mphone, String mcard) throws Exception {
		List<?> result = findBySQL("memberscondition", new String[] { mphone, mcard }, true);
		return result;
	}

	public List<?> getGdmanagecondition(String goodsid, String goodsname, String categoryid, String status,String branchId, Pagination pagination)
			throws Exception {
		List<?> result = findBySQLWithPagination("gdmanagecondition", new String[] { goodsid, goodsname, categoryid, status ,branchId}, pagination, true);
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<?> getRoomids(String orderswitch,String orderroomtype,String orderarrivedate, String orderleavedate)
	throws Exception {
		List result =new ArrayList();
		if(orderswitch.equals("1")){
               result = findBySQL("selectroomids", new String[] {orderroomtype, orderarrivedate,orderleavedate}, true);
		}else {
			    result = findBySQL("switchroomids", new String[] {orderroomtype}, true);	
		}
       return result;
    }
	
	
	public List<?> getExceptiontype() throws Exception {
		return findBySQL("exceptiontype", true);
	}

	public List<?> getExceptiondata(String branchid, Pagination pagination) throws Exception {
		List<?> result = findBySQLWithPagination("exceptiontypedata", new String[] { branchid} , pagination, true);
		return result;
	}
    
	public List<?> getExceptioncondition(String exbegintime,String exendtime,String exceptiontype,String exceptionstatus,String branchid, Pagination pagination) throws Exception {
		List<?> result = findBySQLWithPagination("exceptiontypecd", new String[] {exbegintime,exendtime, exceptiontype,exceptionstatus,branchid}, pagination, true);
		return result;
	}
	
	public List<?> getCashiercashdata(String branchid, String cashierstaff, String shiftid, String cashbox) throws Exception {
		String sql = "select sum(sh.cashinpay) cashin,sum(sh.cashoutpay) cashout,sh.shift from(  "
            +"select DISTINCT (select sum(b.pay)  from tb_p_bill b   "
            +"where b.shift = '"+ shiftid + "'   "
            +"and  b.cash_box = '"+ cashbox + "'   "
            +"and b.branch_id = '"+ branchid + "'   "
            +"and b.record_user = '"+ cashierstaff + "'   "
            +"and to_char( b.record_time, 'YYYY/MM/DD') = to_char(sysdate, 'YYYY/MM/DD')   "
            +"and  b.payment = '1'   "
            +"and b.pay>=0  "
            +"group by b.shift) cashinpay,(select sum(b.pay) from  tb_p_bill b   "
            +"where b.shift = '"+ shiftid + "'   "
            +"and  b.cash_box = '"+ cashbox + "'   "
            +"and b.branch_id = '"+ branchid + "'   "
            +"and b.record_user = '"+ cashierstaff + "'   "
            +"and to_char( b.record_time, 'YYYY/MM/DD') = to_char(sysdate, 'YYYY/MM/DD')   "
            +"and  b.payment = '1'   "
            +"and b.pay<0  "
            +"group by b.shift) cashoutpay,b.shift shift   "
            +"from tb_p_bill b   "
            +"where b.shift = '"+ shiftid + "'   "
            +"and  b.cash_box = '"+ cashbox + "'   "
            +"and b.branch_id = '"+ branchid + "'   "
            +"and b.record_user = '"+ cashierstaff + "'   "
            +"and to_char( b.record_time, 'YYYY/MM/DD') = to_char(sysdate, 'YYYY/MM/DD')   "
            +"and  b.payment = '1'   "
            +"union all   "
            +"(select (select sum(w.pay)  from tb_p_workbilldetail w    "
            +"where w.shift = '"+ shiftid + "'   "
            +"and  w.cash_box = '"+ cashbox + "'   "
            +"and w.branch_id = '"+ branchid + "'   "
            +"and w.record_user = '"+ cashierstaff + "'   "
            +"and to_char( w.record_time, 'YYYY/MM/DD') = to_char(sysdate, 'YYYY/MM/DD')   "
            +"and  w.payment = '1'   "
            +"and w.pay>=0  "
            +"group by w.shift) cashinpay, (select sum(w.pay)  from tb_p_workbilldetail w    "
            +"where w.shift = '"+ shiftid + "'   "
            +"and  w.cash_box = '"+ cashbox + "'   "
            +"and w.branch_id = '"+ branchid + "'   "
            +"and w.record_user = '"+ cashierstaff + "'   "
            +"and to_char( w.record_time, 'YYYY/MM/DD') = to_char(sysdate, 'YYYY/MM/DD')   "
            +"and  w.payment = '1'   "
            +"and w.pay<0  "
            +"group by w.shift) cashoutpay, w.shift shift   "
            +"from tb_p_workbilldetail w   "
            +"where w.shift = '"+ shiftid + "'  "
            +"and  w.cash_box = '"+ cashbox + "'   "
            +"and w.branch_id = '"+ branchid + "'     "
            +"and w.record_user = '"+ cashierstaff + "'   "
            +"and to_char( w.record_time, 'YYYY/MM/DD') = to_char(sysdate, 'YYYY/MM/DD')   "
            +"and  w.payment = '1')   "
            +")sh group by sh.shift ";
		return findBySQL(sql);
	}
	
	public List<?> getCashiercarddata(String branchid, String cashierstaff, String shiftid, String cashbox) throws Exception {
		String sql = "select sum(rd.cardpay) card,rd.shift from(   "
            +"select b.pay cardpay,b.shift shift   "
            +"from tb_p_bill b   "
            +"where b.shift = '"+ shiftid + "'  "
            +"and  b.cash_box = '"+ cashbox + "'  "
            +"and b.branch_id = '"+ branchid + "'   "
            +"and b.record_user = '"+ cashierstaff + "'   "
            +"and to_char( b.record_time, 'YYYY/MM/DD') = to_char(sysdate, 'YYYY/MM/DD')   "
            +"and  b.payment = '2'   "
            +"union all   "
            +"(select w.pay cardpay,w.shift shift   "
            +"from tb_p_workbilldetail w   "
            +"where w.shift = '"+ shiftid + "'   "
            +"and  w.cash_box = '"+ cashbox + "'   "
            +"and w.branch_id = '"+ branchid + "'   "
            +"and w.record_user = '"+ cashierstaff + "'    "
            +"and to_char( w.record_time, 'YYYY/MM/DD') = to_char(sysdate, 'YYYY/MM/DD')   "
            +"and  w.payment = '2')   "
            +")rd group by rd.shift  ";
		return findBySQL(sql);
	}
	
	public List<?> getLastshift(String branchid) throws Exception {
		List<?> result = findBySQL("lastshift", new String[] { branchid}, true);
		return result;
	}
	
	public List<?> getLastshiftvalue(String lastbranchidlast,String currshift,String curruser,String recordtime) throws Exception {
		List<?> result = findBySQL("lastshiftvalue", new String[] { lastbranchidlast, currshift ,curruser,recordtime}, true);
		return result;
	}
	
	public List<?> getSubmitperson(String branchid) throws Exception {
		List<?> result = findBySQL("submitperson", new String[] { branchid }, true);
		return result;
	}
	
	/*public List<?> getCashA(String branchid) throws Exception {
		List<?> result = findBySQL("getcasha", new String[] { branchid }, true);
		return result;
	}
	
	public List<?> getCashB(String branchid) throws Exception {
		List<?> result = findBySQL("getcashb", new String[] { branchid }, true);
		return result;
	}*/
	
	public List<?> getCashboxstatus(String lastbranchid,String cashbox) throws Exception {
		List<?> result = findBySQL("cashboxstatus", new String[] { lastbranchid,cashbox}, true);
		return result;
	}
	
	public List<?> getBillcard(String branchid,String cashierstaff,String shiftid,String cashbox) throws Exception {
		List<?> result = findBySQL("billcard", new String[] { shiftid,cashbox,branchid,cashierstaff}, true);
		return result;
	}
	
	public List<?> getWorkcard(String branchid,String cashierstaff,String shiftid,String cashbox) throws Exception {
		List<?> result = findBySQL("workcard", new String[] {branchid,cashierstaff,shiftid,cashbox}, true);
		return result;
	}
	
	public void upatecashbox(String branchid, String boxname, String recordUser) {
		CashKey cashKey = new CashKey();
		cashKey.setBranchId(branchid);
		cashKey.setCashBox(boxname);
		String hql1 = "update CashBox set cashStatus = :CASHSTATUS,recordUser = :RECORDUSER where cashKey = :CASHKEY";
		this.executeUpdateHQL(hql1, new String[] { "CASHSTATUS", "CASHKEY", "RECORDUSER" }, new Object[] { "0", cashKey,
				recordUser });
	}

}