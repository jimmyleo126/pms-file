package com.ideassoft.bean;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "TP_P_ROOMTYPE", schema = "UCR_PMS")
public class RoomType implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = -3640299641264413577L;
	private String roomType;
	private String roomName;
	private String theme;
	private Byte roomBed;
	private String bedDesc;
	private String broadband;
	private String roomLabel;
	private String roomDesc;
	private String tips;
	private String roomPosition;
	private String status;
	private Date recordTime;
	private String recordUser;
	private String remark;

	// Constructors

	/** default constructor */
	public RoomType() {
	}

	/** minimal constructor */
	public RoomType(String roomType, String roomName, String theme, Byte roomBed, String bedDesc, String broadband,
			String status, String recordUser) {
		this.roomType = roomType;
		this.roomName = roomName;
		this.theme = theme;
		this.roomBed = roomBed;
		this.bedDesc = bedDesc;
		this.broadband = broadband;
		this.status = status;
		this.recordUser = recordUser;
	}

	/** full constructor */
	public RoomType(String roomType, String roomName, String theme, Byte roomBed, String bedDesc, String broadband,
			String roomLabel, String roomDesc, String tips, String roomPosition, String status, Date recordTime,
			String recordUser, String remark) {
		this.roomType = roomType;
		this.roomName = roomName;
		this.theme = theme;
		this.roomBed = roomBed;
		this.bedDesc = bedDesc;
		this.broadband = broadband;
		this.roomLabel = roomLabel;
		this.roomDesc = roomDesc;
		this.tips = tips;
		this.roomPosition = roomPosition;
		this.status = status;
		this.recordTime = recordTime;
		this.recordUser = recordUser;
		this.remark = remark;
	}

	// Property accessors
	@Id
	@Column(name = "ROOM_TYPE", unique = true, nullable = false, length = 3)
	public String getRoomType() {
		return this.roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	@Column(name = "ROOM_NAME", nullable = false, length = 16)
	public String getRoomName() {
		return this.roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	@Column(name = "THEME", nullable = false, length = 1)
	public String getTheme() {
		return this.theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	@Column(name = "ROOM_BED", nullable = false, precision = 2, scale = 0)
	public Byte getRoomBed() {
		return this.roomBed;
	}

	public void setRoomBed(Byte roomBed) {
		this.roomBed = roomBed;
	}

	@Column(name = "BED_DESC", nullable = false, length = 16)
	public String getBedDesc() {
		return this.bedDesc;
	}

	public void setBedDesc(String bedDesc) {
		this.bedDesc = bedDesc;
	}

	@Column(name = "BROADBAND", nullable = false, length = 1)
	public String getBroadband() {
		return this.broadband;
	}

	public void setBroadband(String broadband) {
		this.broadband = broadband;
	}

	@Column(name = "ROOM_LABEL", length = 40)
	public String getRoomLabel() {
		return this.roomLabel;
	}

	public void setRoomLabel(String roomLabel) {
		this.roomLabel = roomLabel;
	}

	@Column(name = "ROOM_DESC", length = 2000)
	public String getRoomDesc() {
		return this.roomDesc;
	}

	public void setRoomDesc(String roomDesc) {
		this.roomDesc = roomDesc;
	}

	@Column(name = "TIPS", length = 40)
	public String getTips() {
		return this.tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	@Column(name = "ROOM_POSITION", length = 1)
	public String getRoomPosition() {
		return this.roomPosition;
	}

	public void setRoomPosition(String roomPosition) {
		this.roomPosition = roomPosition;
	}

	@Column(name = "STATUS", nullable = false, length = 1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RECORD_TIME", length = 7)
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