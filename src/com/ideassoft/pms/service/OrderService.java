package com.ideassoft.pms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ideassoft.bean.Order;
import com.ideassoft.bean.wrapper.MultiQueryOrder;
import com.ideassoft.core.dao.GenericDAOImpl;
import com.ideassoft.core.page.Pagination;

@Service
public class OrderService extends GenericDAOImpl {
	public List<?> queryOrderData(String branchId, MultiQueryOrder order, Pagination pagination) {
		String sql = "select ord.order_id orderid,ord.branch_id branchid,transTitles(ord.room_type, 'tp_p_roomtype', 'room_type', 'room_name') roomname," +
				"ord.room_price roomprice,decode(ord.guarantee, '1', '无担保', '2', '有担保') guarantee,ord.limited limited,transTitles(ord.order_user," +
				"'tb_c_member','member_id','member_name') orderuser,to_char(ord.order_time,'yyyy/MM/dd') ordertime,to_char(ord.arrival_time,'yyyy/MM/dd') arrivaltime," +
				"to_char(ord.leave_time,'yyyy/MM/dd') leavetime,ord.m_phone phone,decode(ord.source,'1','app','2','网站','3','分店','4','wap'," +
				"'5','合作渠道','6','其他','7','微信') source,decode(ord.status,'0','取消','1','新订','2','未到','3','在住/转单','4','离店','5','删除') status," +
				"ord.remark remark from tb_p_order ord, tp_p_roomtype rt where ord.room_type = rt.room_type and ord.theme = '1' {and ord.branch_id = ?} {and ord.order_id like '%' || ? || '%'}" +
				"{and rt.room_name like '%' || ? || '%'}{ and ord.status like '%' || ? || '%'}{ and transtitles(ord.record_user, 'tb_c_staff', " +
				"'staff_id', 'staff_name') like '%' || ? || '%'}{ and ord.source like '%' || ? || '%' }{and transtitles(substr(ord.user_checkin,0," +
				"decode(length(ord.user_checkin),instr(ord.user_checkin, ',') - 1)), 'tb_c_member','member_id','member_name') like '%' || ? || '%' }{and " +
				"ord.guarantee like '%' || ? || '%' }{and transtitles(ord.order_user, 'tb_c_member', 'member_id', 'member_name') like '%' || ? || '%' }{" +
				"and ord.m_phone like '%' || ? || '%' }" +
				" {and ord.record_time >= to_date(?, 'yyyy/MM/dd')}" + 
				 " {and ord.record_time <= to_date(?, 'yyyy/MM/dd')}" +
				 " {and ord.arrival_time >= to_date(?, 'yyyy/MM/dd')}" + 
				 " {and ord.arrival_time  <= to_date(?, 'yyyy/MM/dd')}" +
				 " {and ord.leave_time >= to_date(?, 'yyyy/MM/dd')}" + 
				 " {and ord.leave_time <= to_date(?, 'yyyy/MM/dd')}";
	return findBySQLWithPagination(sql,new String[]{branchId, order.getOrderId(), order.getRoomType(), order.getStatus(), order.getSaleStaff(),
			order.getSource(), order.getUserCheckin(), order.getGuarantee(),  order.getOrderUser(), order.getMphone(),
			order.getOrderTime(), order.getOrdtimes(), order.getArrivalTime(), order.getArrtimes(), 
			order.getLeaveTime(), order.getLeavetimes()},pagination);
	}
}
