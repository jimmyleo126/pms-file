package com.ideassoft.bean;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "TB_P_HOUSE", schema = "UCR_PMS")
public class House implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 5555693991233071064L;
	private String houseId;
	private String houseNo;
	private String houseType;
	private Short area;
	private String floor;
	private Byte beds;
	private String bedDesc;
	private String broadband;
	private String label;
	private String houseDesc;
	private String tips;
	private String position;
	private String address;
	private String status;
	private Date recordTime;
	private String recordUser;
	private String remark;

	// Constructors

	/** default constructor */
	public House() {
	}

	/** minimal constructor */
	public House(String houseId, String houseNo, String houseType, String floor, Byte beds, String bedDesc,
			String broadband, String recordUser) {
		this.houseId = houseId;
		this.houseNo = houseNo;
		this.houseType = houseType;
		this.floor = floor;
		this.beds = beds;
		this.bedDesc = bedDesc;
		this.broadband = broadband;
		this.recordUser = recordUser;
	}

	/** full constructor */
	public House(String houseId, String houseNo, String houseType, Short area, String floor, Byte beds,
			String bedDesc, String broadband, String label, String houseDesc, String tips, String position,
			String address, String status, Date recordTime, String recordUser, String remark) {
		this.houseId = houseId;
		this.houseNo = houseNo;
		this.houseType = houseType;
		this.area = area;
		this.floor = floor;
		this.beds = beds;
		this.bedDesc = bedDesc;
		this.broadband = broadband;
		this.label = label;
		this.houseDesc = houseDesc;
		this.tips = tips;
		this.position = position;
		this.address = address;
		this.status = status;
		this.recordTime = recordTime;
		this.recordUser = recordUser;
		this.remark = remark;
	}

	// Property accessors
	@Id
	@Column(name = "HOUSE_ID", unique = true, nullable = false, length = 6)
	public String getHouseId() {
		return this.houseId;
	}

	public void setHouseId(String houseId) {
		this.houseId = houseId;
	}

	@Column(name = "HOUSE_NO", nullable = false, length = 7)
	public String getHouseNo() {
		return this.houseNo;
	}

	public void setHouseNo(String houseNo) {
		this.houseNo = houseNo;
	}

	@Column(name = "HOUSE_TYPE", nullable = false, length = 3)
	public String getHouseType() {
		return this.houseType;
	}

	public void setHouseType(String houseType) {
		this.houseType = houseType;
	}

	@Column(name = "AREA", precision = 3, scale = 0)
	public Short getArea() {
		return this.area;
	}

	public void setArea(Short area) {
		this.area = area;
	}

	@Column(name = "FLOOR", nullable = false, length = 2)
	public String getFloor() {
		return this.floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	@Column(name = "BEDS", nullable = false, precision = 2, scale = 0)
	public Byte getBeds() {
		return this.beds;
	}

	public void setBeds(Byte beds) {
		this.beds = beds;
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

	@Column(name = "LABEL", length = 40)
	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Column(name = "HOUSE_DESC", length = 2000)
	public String getHouseDesc() {
		return this.houseDesc;
	}

	public void setHouseDesc(String houseDesc) {
		this.houseDesc = houseDesc;
	}

	@Column(name = "TIPS", length = 40)
	public String getTips() {
		return this.tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	@Column(name = "POSITION", length = 1)
	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	@Column(name = "ADDRESS", length = 60)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "STATUS", length = 2)
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