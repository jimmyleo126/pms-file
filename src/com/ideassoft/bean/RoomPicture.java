package com.ideassoft.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "TB_P_ROOMPICTURE", schema = "UCR_PMS")
public class RoomPicture implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3615506264633982661L;
	private String pictureId;
	private String style;
	private String roomType;
	private String branchId;
	private String status;
	private Date recordTime;
	private String recordUser;
	private String remark;
	
	@Id
	@Column(name = "PIC_ID", unique = true,nullable = false, length = 12)
	public String getPictureId() {
		return pictureId;
	}
	public void setPictureId(String pictureId) {
		this.pictureId = pictureId;
	}
	@Column(name = "PIC_STYLE", nullable = false, length = 2)
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	@Column(name = "ROOM_TYPE",nullable = false, length = 3)
	public String getRoomType() {
		return roomType;
	}
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	
	@Column(name = "STATUS", nullable = false, length = 2)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RECORD_TIME",  length = 7)
	public Date getRecordTime() {
		return recordTime;
	}
	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}
	@Column(name = "RECORD_USER", nullable = false, length = 8)
	public String getRecordUser() {
		return recordUser;
	}
	public void setRecordUser(String recordUser) {
		this.recordUser = recordUser;
	}
	@Column(name = "REMARK", length = 200)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name = "BRANCH_ID",nullable = false, length = 6)
	public String getBranchId() {
		return branchId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
}
