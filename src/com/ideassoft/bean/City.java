package com.ideassoft.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "TB_P_CITY", schema = "UCR_PMS")
public class City implements java.io.Serializable {

	private static final long serialVersionUID = 7424797237856292041L;
	
	private String adminiCode;
	private String adminiName;
	private String rank;
	private String recordUser;
	private Date RecordTime;
	private String status;
	private String remark;
	public City() {
		
	}
	public City(String adminiCode, String adminiName, String rank,
			String recordUser, Date recordTime, String status, String remark) {
		super();
		this.adminiCode = adminiCode;
		this.adminiName = adminiName;
		this.rank = rank;
		this.recordUser = recordUser;
		RecordTime = recordTime;
		this.status = status;
		this.remark = remark;
	}
	
	@Id
	@Column(name = "ADMINI_CODE", unique = true, nullable = false, length = 12)
	public String getAdminiCode() {
		return adminiCode;
	}
	public void setAdminiCode(String adminiCode) {
		this.adminiCode = adminiCode;
	}
	
	@Column(name = "ADMINI_NAME", nullable = false, length = 200)
	public String getAdminiName() {
		return adminiName;
	}
	public void setAdminiName(String adminiName) {
		this.adminiName = adminiName;
	}
	
	@Column(name = "RANK", nullable = false, length = 1)
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	@Column(name = "RECORD_USER", nullable = false, length = 8)
	public String getRecordUser() {
		return recordUser;
	}
	public void setRecordUser(String recordUser) {
		this.recordUser = recordUser;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RECORD_TIME", length = 7)
	public Date getRecordTime() {
		return RecordTime;
	}
	public void setRecordTime(Date recordTime) {
		RecordTime = recordTime;
	}
	@Column(name = "STATUS", nullable = false, length = 1)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Column(name = "REMARK", nullable = false, length = 200)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
	
}
