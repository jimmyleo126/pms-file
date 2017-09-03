package com.ideassoft.bean;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "TB_C_CASHBOX", schema = "UCR_PMS")
public class CashBox implements java.io.Serializable {

	private static final long serialVersionUID = -4050833414114258654L;
	// Fields
	private String dataId;
	private CashKey cashKey;
	private Double cashCount;
	private String cashStatus;
	private String recordUser;
	private Date recordTime;
	private String status;
	private String remark;

	// Constructors
	/** default constructor */
	public CashBox() {
	}

	/** minimal constructor */
	public CashBox(CashKey cashKey,String dataId,  Double cashCount) {
		this.cashKey = cashKey;
		this.dataId = dataId;
		this.cashCount = cashCount;
	}

	/** full constructor */
	public CashBox(CashKey cashKey,String dataId,  Double cashCount, String cashStatus,
			String recordUser, Date recordTime, String status,   
			 String remark) {
		this.dataId = dataId;
		this.cashKey = cashKey;
		this.cashCount = cashCount;
		this.cashStatus = cashStatus;
		this.recordUser = recordUser;
		this.recordTime = recordTime;
		this.status = status;
		this.remark = remark;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "branchId", column = @Column(name = "BRANCH_ID", nullable = false, length = 6)),
			@AttributeOverride(name = "cashBox", column = @Column(name = "CASH_BOX", nullable = false, length = 1)) })
	public CashKey getCashKey() {
		return this.cashKey;
	}

	public void setCashKey(CashKey cashKey) {
		this.cashKey = cashKey;
	}

	@Column(name = "CASH_COUNT", nullable = false, precision = 12)
	public Double getCashCount() {
		return this.cashCount;
	}

	public void setCashCount(Double cashCount) {
		this.cashCount = cashCount;
	}
	
	@Column(name = "CASH_STATUS", nullable = false, length = 1)
	public String getCashStatus() {
		return this.cashStatus;
	}

	public void setCashStatus(String cashStatus) {
		this.cashStatus = cashStatus;
	}

	@Column(name = "RECORD_USER", nullable = false, length = 8)
	public String getRecordUser() {
		return this.recordUser;
	}

	public void setRecordUser(String recordUser) {
		this.recordUser = recordUser;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RECORD_TIME", length = 7, insertable = false,updatable = false)
	public Date getRecordTime() {
		return this.recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	@Column(name = "STATUS", nullable = false, length = 1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "REMARK", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}