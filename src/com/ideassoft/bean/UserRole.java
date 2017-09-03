package com.ideassoft.bean;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "TP_C_USERROLE", schema = "UCR_PMS")
public class UserRole implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = -192220525882820631L;
	private String dataId;
	private String userId;
	private String roleId;
	private String recordUser;
	private Date recordTime;
	private String status;
	private String remark;

	// Constructors

	/** default constructor */
	public UserRole() {
	}

	/** minimal constructor */
	public UserRole(String dataId, String userId, String roleId, String recordUser, String status,String remark) {
		this.dataId = dataId;
		this.userId = userId;
		this.roleId = roleId;
		this.recordUser = recordUser;
		this.status = status;
		this.remark = remark;
	}

	/** full constructor */
	public UserRole(String dataId, String userId, String roleId, String recordUser, Date recordTime, String status,String remark) {
		this.dataId = dataId;
		this.userId = userId;
		this.roleId = roleId;
		this.recordUser = recordUser;
		this.recordTime = recordTime;
		this.status = status;
		this.remark = remark;
	}

	// Property accessors
	@Id
	@Column(name = "DATA_ID", unique = true, nullable = false, length = 8)
	public String getDataId() {
		return this.dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	@Column(name = "USER_ID", nullable = false, length = 8)
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "ROLE_ID", nullable = false, length = 8)
	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@Column(name = "RECORD_USER", nullable = false, length = 8)
	public String getRecordUser() {
		return this.recordUser;
	}

	public void setRecordUser(String recordUser) {
		this.recordUser = recordUser;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RECORD_TIME",insertable = false, updatable = false)
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