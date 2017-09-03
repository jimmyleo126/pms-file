package com.ideassoft.bean;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "TB_E_EQUIPMENT", schema = "UCR_PMS")
public class Equipment implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = -6172797762258216851L;
	private String equipId;
	private String equipName;
	private String equipCategory;
	private String branchId;
	private String roomId;
	private String serialNo;
	private String ip;
	private String recordUser;
	private Date recordTime;
	private String status;
	private String remark;

	// Constructors

	/** default constructor */
	public Equipment() {
	}

	/** minimal constructor */
	public Equipment(String equipId, String equipName, String equipCategory, String roomId, String recordUser,
			String status) {
		this.equipId = equipId;
		this.equipName = equipName;
		this.equipCategory = equipCategory;
		this.roomId = roomId;
		this.recordUser = recordUser;
		this.status = status;
	}

	/** full constructor */
	public Equipment(String equipId, String equipName, String equipCategory, String branchId, String roomId,
			String serialNo, String ip, String recordUser, Date recordTime, String status, String remark) {
		this.equipId = equipId;
		this.equipName = equipName;
		this.equipCategory = equipCategory;
		this.branchId = branchId;
		this.roomId = roomId;
		this.serialNo = serialNo;
		this.ip = ip;
		this.recordUser = recordUser;
		this.recordTime = recordTime;
		this.status = status;
		this.remark = remark;
	}

	// Property accessors
	@Id
	@Column(name = "EQUIP_ID", unique = true, nullable = false, length = 10)
	public String getEquipId() {
		return this.equipId;
	}

	public void setEquipId(String equipId) {
		this.equipId = equipId;
	}

	@Column(name = "EQUIP_NAME", nullable = false, length = 30)
	public String getEquipName() {
		return this.equipName;
	}

	public void setEquipName(String equipName) {
		this.equipName = equipName;
	}

	@Column(name = "EQUIP_CATEGORY", nullable = false, length = 4)
	public String getEquipCategory() {
		return this.equipCategory;
	}

	public void setEquipCategory(String equipCategory) {
		this.equipCategory = equipCategory;
	}

	@Column(name = "BRANCH_ID", length = 4)
	public String getBranchId() {
		return this.branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	@Column(name = "ROOM_ID", nullable = false, length = 4)
	public String getRoomId() {
		return this.roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	@Column(name = "SERIAL_NO", length = 20)
	public String getSerialNo() {
		return this.serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	@Column(name = "IP", length = 15)
	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
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