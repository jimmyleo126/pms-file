package com.ideassoft.pms.service;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ideassoft.bean.Room;
import com.ideassoft.bean.RoomKey;
import com.ideassoft.bean.SqlInfo;
import com.ideassoft.bean.wrapper.MultiQueryCheck;
import com.ideassoft.core.dao.GenericDAOImpl;
import com.ideassoft.core.page.Pagination;

@Service
public class RoomService extends GenericDAOImpl {
	
	public List<?> queryCheckData(String branchId, MultiQueryCheck multiquerycheck,Pagination pagination){
		
		//String sql = "loadOnlineCheckData";
		
		String sql = "select c.check_id checkid,c.branch_id branchid, c.room_id roomid, c.room_type roomtype,c.rp_id rpid, c.room_price roomprice," +
					 " c.check_user checkuser,transtitles(c.check_user, 'tb_c_member', 'member_id', 'member_name') checkusername," +
					 " substr(c.check_user, 0,decode(instr(c.check_user, ',') - 1, '-1', length(c.check_user), instr(c.check_user, ',') - 1)) firstcheckuser," +
					 "transtitles(substr(c.check_user,0,decode(instr(c.check_user, ',') - 1, '-1',length(c.check_user), instr(c.check_user, ',') - 1)), 'tb_c_member', 'member_id','member_name') firstcheckusername," +
					 " transtitles(substr(c.check_user,0, decode(instr(c.check_user, ',') - 1, '-1',length(c.check_user),instr(c.check_user, ',') - 1)),'tb_c_member','member_id','mobile') firstcheckuserphone, to_char(c.checkin_time, 'YYYY/MM/DD HH24:MI') checkintime," +
					 " to_char(c.checkout_time, 'YYYY/MM/DD HH24:MI') checkouttime, c.deposit deposit, c.ttv ttv, c.cost cost,c.pay pay,c.account_fee accountfee," +
					 " c.total_fee totalfee, c.pay_type paytype,c.pay_info payinfo,c.payer payer,c.switch_id switchid," +
					 " c.status status, decode(c.status, 1,'在住', 2, '离店', '已退未结') statusname, c.record_time recordtime," +
					 " c.record_user recorduser, c.remark remark, r.room_name roomname,o.order_user orderuser," +
					 "transtitles(o.order_user, 'tb_c_member', 'member_id', 'member_name') orderusername," +
					 "(select t2.rank_name from tb_c_member t1,tp_c_memberrank t2 where t1.member_rank=t2.member_rank and t1.member_id=o.order_user) rankname,o.m_phone mphone," +
					 " o.source source,decode(o.source, '1','app', '2','网站', '3','分店', '4','wap', '5','合作渠道','其他') decodesource, o.guarantee guarantee,decode(o.guarantee,'1','无','2','担保') decodeguarantee, o.limited limited from tb_p_check c, tp_p_roomtype r, tb_p_order o" +
					 " where c.room_type = r.room_type(+) and c.check_id = o.order_id {and c.status like '%' || ? || '%'}" +
					 " {and c.branch_id like '%' || ? || '%'}" +
					 " {and c.check_id like '%' || ? || '%'}" +
					 " {and c.room_id like '%' || ? || '%'} " +
					 " {and r.room_name like  '%' || ? || '%'} " +
					 " {and to_char(c.checkin_time, 'yyyy/MM/dd') like '%' || ? || '%'}" +
					 " {and to_char(c.checkout_time, 'yyyy/MM/dd') like '%' || ? || '%'}" +
					 " {and transtitles(substr(c.check_user,0,decode(instr(c.check_user, ',') - 1, '-1',length(c.check_user), instr(c.check_user, ',') - 1)), 'tb_c_member', 'member_id','member_name') like '%' || ? || '%'} " +
					 " {and o.guarantee like '%' || ? || '%'} " +
					 " {and transtitles(o.order_user, 'tb_c_member', 'member_id', 'member_name') like '%' || ? || '%'} " +
					 " {and o.m_phone like '%' || ? || '%'} " +
					 " {and o.record_time >= to_date(?, 'yyyy/MM/dd')}" + 
					 " {and o.record_time <= to_date(?, 'yyyy/MM/dd')}" +
					 " {and c.checkin_time >= to_date(?, 'yyyy/MM/dd')}" + 
					 " {and c.checkin_time <= to_date(?, 'yyyy/MM/dd')}" +
					 " {and c.checkout_time >= to_date(?, 'yyyy/MM/dd')}" + 
					 " {and c.checkout_time <= to_date(?, 'yyyy/MM/dd')}" + 
					 " order by c.check_id desc";
		return findBySQLWithPagination(sql,new String[]{multiquerycheck.getStatus(),branchId, multiquerycheck.getCheckid(), multiquerycheck.getRoomid(),
				multiquerycheck.getRoomtype(), multiquerycheck.getCheckintime(), multiquerycheck.getCheckouttime(), multiquerycheck.getCheckuser(),multiquerycheck.getGuarantee(),
				multiquerycheck.getOrderuser(), multiquerycheck.getMphone(),multiquerycheck.getOrdertimebegin(),multiquerycheck.getOrdertimeend(),
				multiquerycheck.getArrivaltimebegin(),multiquerycheck.getArrivaltimeend(),multiquerycheck.getLeavetimebegin(),multiquerycheck.getLeavetimeend()},pagination);
	} 
	
	public void upateroomstatus(String branchid, String roomid, String status){
		//RoomKey id = new RoomKey();
		//id.setRoomId(roomid);
		//id.setBranchId(branchid);
		String sql = "update tb_p_room set status = '" + status + "' where branch_id = " + branchid + " and room_id = " + roomid;
		//String hql1 = "update Room set roomKey.status = :STATUS where id = :ID";
		//this.executeUpdateHQL(hql1,new String[]{"STATUS", "ID"}, new Object[]{status, id});
		this.executeUpdateSQL(sql);
	}
	
	public Room selectroomstatus(String branchid, String roomid){
		RoomKey id = new RoomKey();
		id.setRoomId(roomid);
		id.setBranchId(branchid);
		//String hql1 = "from Room where id = ?";
		return (Room) this.getSession().get(Room.class, id);
		//return (Room) this.findByHQL(hql1, new Object[]{ id});
		//this.executeUpdateHQL(hql1,new String[]{ "ID"}, new Object[]{ id});
	}
	
	public void updatecheck(String rpid, String roomid, String roomtype, double roomprice, String checkid){
		String hql = "update Check set rpId = :RPID, roomId = :ROOMID, roomType = :ROOMTYPE, roomPrice = :ROOMPRICE where checkId = :CHECKID";
		this.executeUpdateHQL(hql, new String[]{"RPID", "ROOMID", "ROOMTYPE", "ROOMPRICE", "CHECKID"}, new Object[]{rpid, roomid, roomtype, roomprice, checkid});
	}
	
	public void updatecheckcheckuser(String strguest, String checkid){
		String hql = "update Check set checkUser = :CHECKUSER where checkId = :CHECKID";
		this.executeUpdateHQL(hql, new String[]{"CHECKUSER", "CHECKID"}, new Object[]{strguest, checkid});
	}

	public void updatestatus(String checkid, double pay, double cost, Date checkoutTime){
		String hql = "update Check set status = :STATUS, pay = :PAY, cost = :COST  where checkId = :CHECKID";
		this.executeUpdateHQL(hql, new String[]{"STATUS", "PAY", "COST", "CHECKID"}, new Object[]{"2", pay, cost, checkid});
	}
	
	public void updateorderstatus(String orderid, String status){
		String hql = "update Order set status = :STATUS where orderId = :ORDERID";
		this.executeUpdateHQL(hql, new String[]{"STATUS", "ORDERID"}, new Object[]{status, orderid});
	}
	
	public void updateMember(String memberId, String memberName, String loginName, String idcard, String gendor, String mobile, String email, String address){
		String hql = "update Member set status = 1";
		List<String> listname = new ArrayList<String>();
		List<String> listvalue = new ArrayList<String>();
		
		if(memberName != null && !"".equals(memberName)) {
			hql = hql + ", memberName = :MEMBERNAME";
			listname.add("MEMBERNAME");
			listvalue.add(memberName);
		}
		if(loginName != null && !"".equals(loginName)) {
			hql = hql + ", loginName = :LOGINNAME";
			listname.add("LOGINNAME");
			listvalue.add(loginName);
		}
		if(idcard != null && !"".equals(idcard)) {
			hql = hql + ", idcard = :IDCARD";
			listname.add("IDCARD");
			listvalue.add(idcard);
		}
		if(gendor != null && !"".equals(gendor)) {
			hql = hql + ", gendor = :GENDOR";
			listname.add("GENDOR");
			listvalue.add(gendor);
		}
		if(mobile != null && !"".equals(mobile)) {
			hql = hql + ", mobile = :MOBILE";
			listname.add("MOBILE");
			listvalue.add(mobile);
		}
		if(email != null && !"".equals(email)) {
			hql = hql + ", email = :EMAIL";
			listname.add("EMAIL");
			listvalue.add(email);
		}
		if(address != null && !"".equals(address)) {
			hql = hql + ", address = :ADDRESS";
			listname.add("ADDRESS");
			listvalue.add(address);
		}
		
		hql = hql + " where memberId = :MEMBERID";
		String[] strhql = new String[listname.size()+1];
		Object[] valhql = new Object[listvalue.size()+1];
		for(int i = 0; i < listname.size(); i++){
			strhql[i] = listname.get(i);
		}
		for(int i = 0; i < listvalue.size(); i++){
			valhql[i] = listvalue.get(i);
		}
		strhql[listname.size()] = "MEMBERID";
		valhql[listvalue.size()] = memberId;
		//String hql = "update Check set status = :STATUS, pay = :PAY, cost = :COST, checkoutTime = :CHECKOUTTIME  where checkId = :CHECKID";
		this.executeUpdateHQL(hql, strhql, valhql);
	}
	
	public void updateBillStatus(String status, String billid){
		String hql = "update Bill set status = :STATUS where billId = :BILLID";
		this.executeUpdateHQL(hql, new String[]{"STATUS", "BILLID"}, new Object[]{"2", billid});
	}
	
	public void insertroommapping(String hostroomid, String roomid){
		String sql = "insert into tb_p_roommapping (room_id, rela_roomid) values ('" + hostroomid + "', '" + roomid + "')";
		this.executeUpdateSQL(sql);
	}
	
	public List<?> selectroommapping(String strcheckid, String branchId, MultiQueryCheck multiQuerycheck){
		SqlInfo sqlinfo = (SqlInfo) this.findOneByProperties(SqlInfo.class, "sqlName", "selectroommapping");
		String sql = sqlinfo.getSqlInfo();
		sql = sql + " and c.branch_id = " + branchId + " and o.branch_id = " + branchId;
		if(multiQuerycheck.getStatus() != null && !"".equals(multiQuerycheck.getStatus())){
			sql = sql + " and c.status like '%' || '" + multiQuerycheck.getStatus() + "' || '%' ";
		}
		if(multiQuerycheck.getCheckid() != null && !"".equals(multiQuerycheck.getCheckid())){
			sql = sql + " and c.check_id like '%' || '"+ multiQuerycheck.getCheckid() + "' || '%' ";
		}
		if(multiQuerycheck.getRoomid() != null && !"".equals(multiQuerycheck.getRoomid())){
			sql = sql + " and c.room_id like '%' || '"+ multiQuerycheck.getRoomid() + "' || '%' ";
		}
		if(multiQuerycheck.getRoomtype() != null && !"".equals(multiQuerycheck.getRoomtype())){
			sql = sql + " and r.room_name like  '%' || '"+ multiQuerycheck.getRoomtype() + "' || '%'";
		}
		if(multiQuerycheck.getOrderuser() != null && !"".equals(multiQuerycheck.getOrderuser())){
			sql = sql + " and transtitles(o.order_user, 'tb_c_member', 'member_id', 'member_name') like '%' || '"+ multiQuerycheck.getOrderuser() + "' || '%'";
		}
		if(multiQuerycheck.getCheckuser() != null && !"".equals(multiQuerycheck.getCheckuser())){
			sql = sql + " and transtitles(c.check_user, 'tb_c_member', 'member_id', 'member_name') like '%' || '" + multiQuerycheck.getCheckuser() + "' || '%'";
		}
		
		sql = sql + " and c.room_id not in ("+ strcheckid +")";
		
		return this.findBySQL(sql);
	}
	
	public void deleteroommappingbyroomid(String hostroomid, String delroomid){
		String sql = "delete from tb_p_roommapping rm where rm.room_id =" + hostroomid + "and rm.rela_roomid = " + delroomid;
		this.executeUpdateSQL(sql);
	}
	
	//日志
    public List<?> getLog(String checkid, String type,Pagination pagination) {
    	String sql = "";
    	if("check".equals(type)){
    		sql  =
    			"select * from("+
    			"select cul.check_id checkid, '入住' type, " +
    			"transTitles(cul.record_user, 'TB_C_STAFF', 'staff_id', 'staff_name') recorduser, " +
    			"to_char(cul.record_time,'yyyy/MM/dd HH24:mi:ss')  recordtime, " +
    			"( '原入住客人：' || transTitles(cul.last_checkinuser, 'tb_c_member', 'member_id','member_name') || " +
    			" '；新入住客人：' || transTitles(cul.curr_checkinuser, 'tb_c_member', 'member_id','member_name') || " +
    			" '；当前主客人：' || transtitles(substr(cul.curr_checkinuser,0,decode(instr(cul.curr_checkinuser, ',') - 1, '-1',length(cul.curr_checkinuser), instr(cul.curr_checkinuser, ',') - 1)), 'tb_c_member', 'member_id','member_name') ) content " +
    			"from tl_p_checkinuserlog cul where cul.check_id = '" + checkid+"'"+
    			" union all " + 
    			"select t.check_id checkid,"+
    			" '转单' type,"+
    			"transTitles(t.record_user, 'TB_C_STAFF', 'staff_id', 'staff_name') recorduser,"+
    			" to_char(t.record_time,'yyyy/MM/dd HH24:mi:ss')  recordtime,"+
    			" ("+
    			" '已转订单号：' || t.order_id "+
    			" ) content "+
    			"from TL_P_SWITCHORDER t where t.check_id = '"+checkid+"'"+
    			" union all"+
    			" select e.check_id checkid,"+
    			" '续住 ' type,"+
    			"  transTitles(e.record_user, 'TB_C_STAFF', 'staff_id', 'staff_name') recorduser,"+
    			"  to_char(e.record_time,'yyyy/MM/dd HH24:mi:ss')  recordtime,"+
    			"  ("+
    			"  '原预离日期：' || to_char(e.last_day,'yyyy/MM/dd HH24:mi') || "+
    			"  '；新预离日期：' || to_char(e.add_day,'yyyy/MM/dd HH24:mi') || '；房价：' ||"+
    			"  e.room_price || ' 元') content "+
    			"  from TL_P_EXTENSIONLOG e  where e.check_id = '"+checkid+"'"+
    			" union all"+
    			" select s.check_id checkid,"+
    			" '换房' type,"+
    			"   transTitles(s.record_user, 'TB_C_STAFF', 'staff_id', 'staff_name') recorduser,"+
    			"  to_char(s.record_time,'yyyy/MM/dd HH24:mi:ss')  recordtime,"+
    			"    ("+
    			"    '原房价：' || s.last_roomprice ||"+
    			"    '；现房价：' || s.curr_roomprice || '；原房间类型：' || s.last_roomtype ||"+
    			"    '；现房间类型：' || s.curr_roomtype || '；原房号：' || s.last_roomid ||"+
    			"    '；现房号：' || s.curr_roomid) content "+
    			" from TL_P_SWITCHROOM s  where s.check_id = '"+checkid+"'"+
    			" union all"+
    			" select r.check_id checkid,"+
    			"    '入账 ' type,"+
    			"    transTitles(r.record_user, 'TB_C_STAFF', 'staff_id', 'staff_name') recorduser,"+
    			"   to_char(r.record_time,'yyyy/MM/dd HH24:mi:ss')  recordtime,"+
    			"     ("+
    			"    '项目编号：' || (select sy.param_name from tp_c_sysparam sy where sy.param_type ='PROJECT' and sy.content = r.project_id) || '；金额：' || r.fee || ' 元'"+
    			"     ) content from TL_P_RECORDING r  where r.check_id = '"+checkid+"'"+
    			" union all "+
    			" select os.check_id checkid, '冲减' type, " +
    			" transTitles(os.record_user, 'TB_C_STAFF', 'staff_id', 'staff_name') recorduser, " +
    			" to_char(os.record_time,'yyyy/MM/dd HH24:mi:ss')  recordtime, " + 
    			" ( '被冲减账单号：' || os.last_billid || '；冲减抵消账单号：' || os.curr_billid || '；冲销金额：' || os.offset_fee) content" + 
    			" from tl_p_offsetlog os where os.check_id = '" + checkid+"'" + 
    			" union all " + 
    			" select  tf.last_checkid checkid,"+
    			"    '转账' type,"+
    			"    transTitles(tf.record_user, 'TB_C_STAFF', 'staff_id', 'staff_name') recorduser,"+
    			"    to_char(tf.record_time,'yyyy/MM/dd HH24:mi:ss')  recordtime,"+
    			"    ('转账单号：' || tf.last_checkid || '；目标单号：' || tf.curr_checkid "+
    			"    ) content from TL_P_TRANSFER tf  where tf.last_checkid = '"+checkid+"'"+
    			")r1 order by r1.recordtime desc";
    	}else if("workbill".equals(type)){
	    		sql = " select r.check_id checkid,"+
	    		"    '工作账单' type,"+
	    		"    transTitles(r.record_user, 'TB_C_STAFF', 'staff_id', 'staff_name') recorduser,"+
	    		"    to_char(r.record_time,'yyyy/MM/dd HH24:mi:ss')  recordtime,"+
	    		"    ('入账号：' || r.record_id || '；工作账号：' || r.check_id ||"+
	    		"    '；门店编号：' || r.branch_id || '；入账类型：' || r.record_type ||"+
	    		"    '；项目编号：' || r.project_id || '；金额：' || r.fee ||"+
	    		"    '；备注：' || nvl(r.remark,'')) content from TL_P_RECORDING r where r.record_type = '2' and r.check_id= '"+ checkid + "' order by r.record_time desc";
    	}

       return findBySQLWithPagination(sql,pagination);
	}
    
	public List<?> loadcustomer(String strcheckid, String branchId){
		String sql = "select c.check_id checkid,c.branch_id branchid, c.room_id roomid, c.room_type roomtype,c.rp_id rpid, " +
					 "c.room_price roomprice, c.check_user checkuser,transtitles(c.check_user, 'tb_c_member', 'member_id', 'member_name') checkusername, substr(c.check_user, 0,decode(instr(c.check_user, ',') - 1, '-1', length(c.check_user), instr(c.check_user, ',') - 1)) firstcheckuser," +
					 "transtitles(substr(c.check_user,0,decode(instr(c.check_user, ',') - 1, '-1',length(c.check_user), instr(c.check_user, ',') - 1)), 'tb_c_member', 'member_id','member_name') firstcheckusername, transtitles(substr(c.check_user,0, decode(instr(c.check_user, ',') - 1, '-1',length(c.check_user),instr(c.check_user, ',') - 1)),'tb_c_member','member_id','mobile') firstcheckuserphone," +
					 " c.checkin_time checkintime, c.checkout_time checkouttime, c.deposit deposit, c.ttv ttv, c.cost cost," +
					 "c.pay pay,c.account_fee accountfee, c.total_fee totalfee, c.pay_type paytype,c.pay_info payinfo," +
					 "c.payer payer,c.switch_id switchid, c.status status, c.record_time recordtime, c.record_user recorduser, " +
					 "c.remark remark from tb_p_check c where c.status = '1' and c.branch_id = '"+ branchId + "' and c.room_id in(" + strcheckid + ")";
		return this.findBySQL(sql);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> findallroommapping(String roomid){
		String sql = "select room_id roomid, rela_roomid relaroomid from tb_p_roommapping where room_id = ?";
		return this.findBySQL(sql, new String[]{roomid});
	}
    
    public List<?> getRoomStatus(String madate, String branchId, Pagination pagination) {
		String sql = "select rt.room_type roomtype,rt.room_name roomname,count(rm.room_id) roomid,a.rmid sell,b.roomid stop," +
		"c.roomid night,d.neworder vaild,e.allorder invaild from tp_p_roomtype rt,tb_p_room rm,(select rt.room_type roomtype, " +
		"nvl(p.roomid,0) rmid from tp_p_roomtype rt left join(select rm.room_type roomtype, count(rm.room_id) roomid from tb_p_room rm" +
		" where rm.status = '0'  {and rm.branch_id = ?} group by rm.room_type) p  on rt.room_type = p.roomtype and rt.theme = '1') a, " +
		"(select rt.room_type roomtype, nvl(a.roomid, 0) roomid " + "from tp_p_roomtype rt Left Join (select rm.room_type roomtype, " +
		"count(rm.room_id) roomid from tp_p_roomtype rt, " + "tb_p_room rm where rt.room_type = rm.room_type and rm.status = 'T' group by rm.room_type" +
		" order by rm.room_type) a on rt.room_type = a.roomtype) b,(select rt.room_type roomtype, nvl(newrt.rmid, 0) roomid " +
		"from tp_p_roomtype rt left join (select rt.room_type roomtype, count(che.room_id) rmid from tb_p_check che, tp_p_roomtype rt " +
		"where rt.room_type = che.room_type and che.status = '1'and trunc(sysdate) between trunc(che.checkin_time) " +
		"and trunc(che.checkout_time) group by rt.room_type) newrt on rt.room_type = newrt.roomtype) c,(select rt.room_type roomtype, " +
		"nvl(neworder.countnum, 0) neworder from tp_p_roomtype rt left join (select rt.room_type roomtype,count(ord.room_type) countnum " +
		"from tb_p_order ord, tp_p_roomtype rt where ord.room_type = rt.room_type and ord.status = '1' " +
		"{and to_char(ord.order_time, 'yyyy/MM/dd') =?} {and branch_id = ?} group by rt.room_type) neworder on " +
		"rt.room_type = neworder.roomtype) d, (select rt.room_type roomtype, nvl(allorder.countnum, 0) allorder  " +
		"from tp_p_roomtype rt left join (select rt.room_type roomtype, count(ord.room_type) countnum " +
		"from tb_p_order ord, tp_p_roomtype rt  where ord.room_type = rt.room_type {and to_char(ord.order_time, 'yyyy/MM/dd') =?} " +
		"{and branch_id = ?} group by rt.room_type) allorder on rt.room_type = allorder.roomtype) e where rt.room_type = rm.room_type " +
		"and rt.room_type = a.roomtype and rt.room_type = b.roomtype and rt.room_type = c.roomtype and rt.room_type = d.roomtype " +
		"and rt.room_type = e.roomtype and rt.theme = '1' {and rm.branch_id = ?} group by rt.room_type, rt.room_name, a.rmid, b.roomid, c.roomid,d.neworder,e.allorder " +
		"order by rt.room_type";
		return findBySQLWithPagination(sql, new String []{branchId, madate, branchId, madate, branchId, branchId},pagination);
	} 
}
