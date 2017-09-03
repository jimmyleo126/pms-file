package com.ideassoft.bean;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "TL_E_CONTROLLOG", schema = "UCR_PMS")
public class ControlLog implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 2188330120592329033L;
	private String logId;
	private String equipId;
	private String operCommand;
	private String recordUser;
	private Date recordTime;
	private String status;
	private String remark;

	// Constructors

	/** default constructor */
	public ControlLog() {
	}

	/** minimal constructor */
	public ControlLog(String logId, String equipId, String operCommand, String recordUser, String status) {
		this.logId = logId;
		this.equipId = equipId;
		this.operCommand = operCommand;
		this.recordUser = recordUser;
		this.status = status;
	}

	/** full constructor */
	public ControlLog(String logId, String equipId, String operCommand, String recordUser, Date recordTime,
			String status, String remark) {
		this.logId = logId;
		this.equipId = equipId;
		this.operCommand = operCommand;
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

	@Column(name = "OPER_COMMAND", nullable = false, length = 2)
	public String getOperCommand() {
		return this.operCommand;
	}

	public void setOperCommand(String operCommand) {
		this.operCommand = operCommand;
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