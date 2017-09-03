package com.ideassoft.bean;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "TL_E_WARNINGLOG", schema = "UCR_PMS")
public class WarningLog implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = -9086240626876110152L;
	private String logId;
	private String equipId;
	private String warningType;
	private Integer warningValue;
	private String recordUser;
	private Date recordTime;
	private String status;
	private String remark;

	// Constructors

	/** default constructor */
	public WarningLog() {
	}

	/** minimal constructor */
	public WarningLog(String logId, String equipId, String warningType, Integer warningValue, String recordUser,
			String status) {
		this.logId = logId;
		this.equipId = equipId;
		this.warningType = warningType;
		this.warningValue = warningValue;
		this.recordUser = recordUser;
		this.status = status;
	}

	/** full constructor */
	public WarningLog(String logId, String equipId, String warningType, Integer warningValue, String recordUser,
			Date recordTime, String status, String remark) {
		this.logId = logId;
		this.equipId = equipId;
		this.warningType = warningType;
		this.warningValue = warningValue;
		this.recordUser = recordUser;
		this.recordTime = recordTime;
		this.status = status;
		this.remark = remark;
	}

	// Property accessors
	@Id
	@Column(name = "LOG_ID", unique = true, nullable = false, length = 20)
	public String getLogId() {
		return this.logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	@Column(name = "EQUIP_ID", nullable = false, length = 10)
	public String getEquipId() {
		return this.equipId;
	}

	public void setEquipId(String equipId) {
		this.equipId = equipId;
	}

	@Column(name = "WARNING_TYPE", nullable = false, length = 2)
	public String getWarningType() {
		return this.warningType;
	}

	public void setWarningType(String warningType) {
		this.warningType = warningType;
	}

	@Column(name = "WARNING_VALUE", nullable = false, precision = 6, scale = 0)
	public Integer getWarningValue() {
		return this.warningValue;
	}

	public void setWarningValue(Integer warningValue) {
		this.warningValue = warningValue;
	}

	@Column(name = "RECORD_USER", nullable = false, length = 8)
	public String getRecordUser() {
		return this.recordUser;
	}

	public void setRecordUser(String recordUser) {
		this.recordUser = recordUser;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RECORD_TIME", length = 7)
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