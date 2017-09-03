package com.ideassoft.bean;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "TB_P_ORDER", schema = "UCR_PMS")
public class Order implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 696917437296140958L;
	private String orderId;
	private String branchId;
	private String source;
	private String theme;
	private String activity;
	private String saleStaff;
	private String roomId;
	private String roomType;
	private String orderUser;
	private String MPhone;
	private String rpId;
	private Date arrivalTime;
	private Date leaveTime;
	private Date checkoutTime;
	private Double roomPrice;
	private String guarantee;
	private String limited;
	private String userCheckin;
	private String paymentType;
	private Double advanceCash;
	private Double advanceCard;
	private Integer advanceScore;
	private String voucher;
	private String roomRemark;
	private String receptionRemark;
	private String cashRemark;
	private String status;
	private Date orderTime;
	private Date recordTime;
	private String recordUser;
	private String remark;

	// Constructors

	/** default constructor */
	public Order() {
	}

	/** minimal constructor */
	public Order(String orderId, String branchId, String source, String theme, String roomType, String orderUser,
			String MPhone, String rpId, Date arrivalTime, Date leaveTime, Double roomPrice, String guarantee,
			String limited, String userCheckin, String cashRemark, String status, String recordUser) {
		this.orderId = orderId;
		this.branchId = branchId;
		this.source = source;
		this.theme = theme;
		this.roomType = roomType;
		this.orderUser = orderUser;
		this.MPhone = MPhone;
		this.rpId = rpId;
		this.arrivalTime = arrivalTime;
		this.leaveTime = leaveTime;
		this.roomPrice = roomPrice;
		this.guarantee = guarantee;
		this.limited = limited;
		this.userCheckin = userCheckin;
		this.cashRemark = cashRemark;
		this.status = status;
		this.recordUser = recordUser;
	}

	/** full constructor */
	public Order(String orderId, String branchId, String source, String theme, String activity, String saleStaff,
			String roomId, String roomType, String orderUser, String MPhone, String rpId, Date arrivalTime,
			Date leaveTime, Date checkoutTime, Double roomPrice, String guarantee, String limited, String userCheckin,
			String paymentType, Double advanceCash,Double advanceCard, Integer advanceScore, String voucher,String roomRemark, String receptionRemark,
			String cashRemark, String status, Date orderTime, Date recordTime, String recordUser, String remark) {
		this.orderId = orderId;
		this.branchId = branchId;
		this.source = source;
		this.theme = theme;
		this.activity = activity;
		this.saleStaff = saleStaff;
		this.roomId = roomId;
		this.roomType = roomType;
		this.orderUser = orderUser;
		this.MPhone = MPhone;
		this.rpId = rpId;
		this.arrivalTime = arrivalTime;
		this.leaveTime = leaveTime;
		this.checkoutTime = checkoutTime;
		this.roomPrice = roomPrice;
		this.guarantee = guarantee;
		this.limited = limited;
		this.userCheckin = userCheckin;
		this.paymentType = paymentType;
		this.advanceCash = advanceCash;
		this.advanceCard = advanceCard;
		this.advanceScore = advanceScore;
		this.voucher = voucher;
		this.roomRemark = roomRemark;
		this.receptionRemark = receptionRemark;
		this.cashRemark = cashRemark;
		this.status = status;
		this.orderTime = orderTime;
		this.recordTime = recordTime;
		this.recordUser = recordUser;
		this.remark = remark;
	}

	// Property accessors
	@Id
	@Column(name = "ORDER_ID", unique = true, nullable = false, length = 16)
	public String getOrderId() {
		return this.orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@Column(name = "BRANCH_ID", nullable = false, length = 6)
	public String getBranchId() {
		return this.branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	@Column(name = "SOURCE", nullable = false, length = 2)
	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Column(name = "THEME", nullable = false, length = 1)
	public String getTheme() {
		return this.theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	@Column(name = "ACTIVITY", length = 5)
	public String getActivity() {
		return this.activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	@Column(name = "SALE_STAFF", length = 8)
	public String getSaleStaff() {
		return this.saleStaff;
	}

	public void setSaleStaff(String saleStaff) {
		this.saleStaff = saleStaff;
	}

	@Column(name = "ROOM_ID", length = 6)
	public String getRoomId() {
		return this.roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	@Column(name = "ROOM_TYPE", nullable = false, length = 2)
	public String getRoomType() {
		return this.roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	@Column(name = "ORDER_USER", nullable = false, length = 8)
	public String getOrderUser() {
		return this.orderUser;
	}

	public void setOrderUser(String orderUser) {
		this.orderUser = orderUser;
	}

	@Column(name = "M_PHONE", nullable = false, length = 11)
	public String getMPhone() {
		return this.MPhone;
	}

	public void setMPhone(String MPhone) {
		this.MPhone = MPhone;
	}

	@Column(name = "RP_ID", nullable = false, length = 3)
	public String getRpId() {
		return this.rpId;
	}

	public void setRpId(String rpId) {
		this.rpId = rpId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ARRIVAL_TIME", nullable = false, length = 7)
	public Date getArrivalTime() {
		return this.arrivalTime;
	}

	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LEAVE_TIME", nullable = false, length = 7)
	public Date getLeaveTime() {
		return this.leaveTime;
	}

	public void setLeaveTime(Date leaveTime) {
		this.leaveTime = leaveTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CHECKOUT_TIME", length = 7)
	public Date getCheckoutTime() {
		return this.checkoutTime;
	}

	public void setCheckoutTime(Date checkoutTime) {
		this.checkoutTime = checkoutTime;
	}

	@Column(name = "ROOM_PRICE", nullable = false, precision = 9)
	public Double getRoomPrice() {
		return this.roomPrice;
	}

	public void setRoomPrice(Double roomPrice) {
		this.roomPrice = roomPrice;
	}

	@Column(name = "GUARANTEE", nullable = false, length = 2)
	public String getGuarantee() {
		return this.guarantee;
	}

	public void setGuarantee(String guarantee) {
		this.guarantee = guarantee;
	}

	@Column(name = "LIMITED", nullable = false, length = 6)
	public String getLimited() {
		return this.limited;
	}

	public void setLimited(String limited) {
		this.limited = limited;
	}

	@Column(name = "USER_CHECKIN", length = 60)
	public String getUserCheckin() {
		return this.userCheckin;
	}

	public void setUserCheckin(String userCheckin) {
		this.userCheckin = userCheckin;
	}

	@Column(name = "PAYMENT_TYPE", length = 5,insertable=true,updatable = true)
	public String getPaymentType() {
		return this.paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	@Column(name = "ADVANCE_CASH", precision = 9)
	public Double getAdvanceCash() {
		return this.advanceCash;
	}

	public void setAdvanceCash(Double advanceCash) {
		this.advanceCash = advanceCash;
	}
	
	@Column(name = "ADVANCE_CARD", precision = 9)
	public Double getAdvanceCard() {
		return this.advanceCard;
	}

	public void setAdvanceCard(Double advanceCard) {
		this.advanceCard = advanceCard;
	}

	@Column(name = "ADVANCE_SCORE", precision = 9, scale = 0)
	public Integer getAdvanceScore() {
		return this.advanceScore;
	}
	
	@Column(name = "VOUCHER", length = 50)
	public String getVoucher() {
		return voucher;
	}

	public void setVoucher(String voucher) {
		this.voucher = voucher;
	}

	public void setAdvanceScore(Integer advanceScore) {
		this.advanceScore = advanceScore;
	}

	@Column(name = "ROOM_REMARK", length = 200)
	public String getRoomRemark() {
		return this.roomRemark;
	}

	public void setRoomRemark(String roomRemark) {
		this.roomRemark = roomRemark;
	}

	@Column(name = "RECEPTION_REMARK", length = 200)
	public String getReceptionRemark() {
		return this.receptionRemark;
	}

	public void setReceptionRemark(String receptionRemark) {
		this.receptionRemark = receptionRemark;
	}

	@Column(name = "CASH_REMARK", length = 200)
	public String getCashRemark() {
		return this.cashRemark;
	}

	public void setCashRemark(String cashRemark) {
		this.cashRemark = cashRemark;
	}

	@Column(name = "STATUS", nullable = false, length = 2)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ORDER_TIME", nullable = true,insertable=false,updatable = false)
	public Date getOrderTime() {
		return this.orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RECORD_TIME", nullable = true,insertable=false)
	public Date getRecordTime() {
		return this.recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	@Column(name = "RECORD_USER", nullable = false, length = 8)
	public String getRecordUser() {
		return this.recordUser;
	}

	public void setRecordUser(String recordUser) {
		this.recordUser = recordUser;
	}

	@Column(name = "REMARK", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}