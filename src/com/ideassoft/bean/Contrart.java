package com.ideassoft.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "TB_P_CONTRART", schema = "UCR_PMS")
public class Contrart implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 107475977691584884L;
	
	private String contrartId;
	private String contrart;
	private String memberId;
	private String roomId;
	private String roomType;
	private String mobile;
	private String branchId;
	private Date recordTime;
	private Date startTime;
	private Date endTime;
	private Date contrartEndTime;
	private String status;
	private String typeOfPayment;
	private String unitPrice;
	private String rentTime;
	
	
	@Id
	@Column(name = "CONTRART_ID", unique = true, nullable = false, length = 17)
	public String getContrartId() {
		return contrartId;
	}
	public void setContrartId(String contrartId) {
		this.contrartId = contrartId;
	}
	
	@Column(name = "CONTRART", nullable = false, length = 20)
	public String getContrart() {
		return contrart;
	}
	public void setContrart(String contrart) {
		this.contrart = contrart;
	}
	
	@Column(name = "MEMBER_ID", nullable = false, length = 8)
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	
	@Column(name = "ROOM_ID", length = 6)
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	
	@Column(name = "ROOM_TYPE", length = 3)
	public String getRoomType() {
		return roomType;
	}
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	
	@Column(name = "MOBILE", length = 11)
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@Column(name = "BRANCH_ID", length = 6)
	public String getBranchId() {
		return branchId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RECORD_TIME", nullable = false, length = 7)
	public Date getRecordTime() {
		return recordTime;
	}
	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "START_TIME", nullable = false, length = 7)
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "END_TIME", nullable = false, length = 7)
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CONTRART_END_TIME", nullable = false, length = 7)
	public Date getContrartEndTime() {
		return contrartEndTime;
	}
	public void setContrartEndTime(Date contrartEndTime) {
		this.contrartEndTime = contrartEndTime;
	}
	
	@Column(name = "STATUS", nullable = false, length = 1)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name = "TYPEOFPAYMENT", nullable = false, length = 1)
	public String getTypeOfPayment() {
		return typeOfPayment;
	}
	public void setTypeOfPayment(String typeOfPayment) {
		this.typeOfPayment = typeOfPayment;
	}
	
	@Column(name = "UNITPRICE", nullable = false, length = 10)
	public String getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}
	
	@Transient
	public String getRentTime() {
		return rentTime;
	}
	public void setRentTime(String rentTime) {
		this.rentTime = rentTime;
	}

	
}